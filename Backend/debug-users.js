const mongoose = require('mongoose');
const dotenv = require('dotenv');
dotenv.config();

async function checkUsers() {
    try {
        await mongoose.connect(process.env.MONGO_URI);
        console.log('Connected to AI_FARM_users');

        const User = mongoose.model('User', new mongoose.Schema({
            fullName: String,
            email: String,
            tenantDbName: String
        }));

        const users = await User.find({});
        console.log('Users found:', users.length);
        users.forEach(u => {
            console.log(`- ${u.fullName} (${u.email}): tenantDbName = ${u.tenantDbName}`);
        });

    } catch (err) {
        console.error('Error:', err);
    } finally {
        await mongoose.disconnect();
    }
}

checkUsers();
