const { URL } = require('url');

const MONGO_URI = 'mongodb+srv://naveenraj69205_db_user:KRq06YqGq5qilxS8@cluster0.sjh3hqm.mongodb.net/AI_FARM_users?retryWrites=true&w=majority&appName=Cluster0';

try {
    const dbName = 'AI_FARM_user_naveen';
    const url = new URL(MONGO_URI);
    url.pathname = `/${dbName}`;
    const uri = url.toString();
    console.log('Original:', MONGO_URI);
    console.log('Modified:', uri);

    // Check if the protocol is still correct
    if (!uri.startsWith('mongodb+srv://')) {
        console.error('ERROR: Protocol changed or lost!');
    }
} catch (err) {
    console.error('URL construction failed:', err.message);
}
