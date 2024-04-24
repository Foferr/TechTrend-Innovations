// db.js

const sql = require('mssql');

// Configure the database connection
const config = {
  user: 'NovaAdmin',
  password: 'TechTrends1',
  server: 'novatechtrends.database.windows.net',
  database: 'NovaDB',
  options: {
    encrypt: true, // Use encryption
  }
};

// Create a pool of connections
const poolPromise = new sql.ConnectionPool(config)
  .connect()
  .then(pool => {
    console.log('Connected to SQL Server');
    return pool;
  })
  .catch(err => {
    console.error('Database connection failed:', err);
    process.exit(1); // Exit the app if the connection fails
  });

module.exports = {
  sql,
  poolPromise
};
