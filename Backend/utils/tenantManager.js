const mongoose = require('mongoose');
const schemas = require('../models/TenantSchemas');

// Cache to store connections or connection promises
const connections = {};

/**
 * Get or create a connection for a specific tenant (user)
 */
const getTenantConnection = async (userId, retryCount = 0) => {
    const idStr = userId.toString();

    // Return existing connection if it's already established and healthy
    if (connections[idStr] && !(connections[idStr] instanceof Promise) && connections[idStr].readyState === 1) {
        return connections[idStr];
    }

    // If a connection attempt is already in progress, wait for it
    if (connections[idStr] instanceof Promise) {
        try {
            return await connections[idStr];
        } catch (err) {
            // If the previous attempt failed, the catch block below will handle a new attempt
            console.warn(`[Multi-Tenant] Previous connection attempt for user ${idStr} failed, retrying...`);
        }
    }

    // Start a new connection attempt
    connections[idStr] = (async () => {
        try {
            const User = require('../models/User');
            const user = await User.findById(idStr);

            if (!user) {
                throw new Error(`User ${idStr} not found`);
            }

            const dbIdentifier = user.tenantDbName ? user.tenantDbName : `AI_FARM_user_${idStr}`;
            const dbName = dbIdentifier.startsWith('AI_FARM_user_') ? dbIdentifier : `AI_FARM_user_${dbIdentifier}`;

            const url = new URL(process.env.MONGO_URI);
            url.pathname = `/${dbName}`;
            const uri = url.toString();

            console.log(`[Multi-Tenant] Connecting to: ${dbName} (Attempt ${retryCount + 1})`);

            const conn = await mongoose.createConnection(uri, {
                serverSelectionTimeoutMS: 5000, // Timeout after 5s
                heartbeatFrequencyMS: 10000,     // Check connection every 10s
            }).asPromise();

            console.log(`[Multi-Tenant] Successfully connected to ${dbName}`);

            // Register all schemas on this connection
            for (const modelName of Object.keys(schemas)) {
                conn.model(modelName, schemas[modelName]);
            }

            // Connection health monitoring
            conn.on('error', (err) => {
                console.error(`[Multi-Tenant] Connection Error for ${dbName}:`, err);
                if (connections[idStr] === conn) delete connections[idStr];
            });

            conn.on('disconnected', () => {
                console.warn(`[Multi-Tenant] Connection lost for ${dbName}`);
                if (connections[idStr] === conn) delete connections[idStr];
            });

            connections[idStr] = conn;
            return conn;
        } catch (err) {
            console.error(`[Multi-Tenant] Connection Failure for user ${idStr}:`, err.message);
            delete connections[idStr];

            // Selective retry logic for network/timeout errors
            if (retryCount < 2 && (err.name === 'MongooseServerSelectionError' || err.code === 'ETIMEDOUT')) {
                console.log(`[Multi-Tenant] Retrying connection for user ${idStr} in 2s...`);
                await new Promise(resolve => setTimeout(resolve, 2000));
                return getTenantConnection(userId, retryCount + 1);
            }

            throw err;
        }
    })();

    return await connections[idStr];
};

module.exports = {
    getTenantConnection
};
