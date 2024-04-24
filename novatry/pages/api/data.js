import { poolPromise } from './db'; // Import the database connection

export default async function handler(req, res) {
  try {
    const pool = await poolPromise;
    const result = await pool.request().query('SELECT * FROM UserTable');
    res.status(200).json(result.recordset);
  } catch (err) {
    console.error('SQL error', err);
    res.status(500).json({ error: 'Failed to fetch data' });
  }
}
