DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = 'wallet_db') THEN
        EXECUTE 'CREATE DATABASE wallet_db';
    END IF;
END $$;