CREATE USER rstracker WITH PASSWORD 'tracker123';

-- Create the database
CREATE DATABASE rstracker_db WITH OWNER rstracker;

-- Grant privileges to the user
GRANT ALL PRIVILEGES ON DATABASE rstracker_db TO rstracker;

-- Connect to the ecom_db
\c rstracker_db;

-- Grant schema permissions
GRANT ALL ON SCHEMA public TO rstracker_db;

-- Grant permissions for future tables
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO rstracker;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON SEQUENCES TO rstracker;