-- NOTE: Currently, the application is not configured to execute this script at initialization

-- Clean existing data
DELETE FROM products;

-- Reset identity sequence (PostgreSQL-specific)
ALTER SEQUENCE products_id_seq RESTART WITH 1;

-- Insert new dummy data
INSERT INTO products (name, price) VALUES ('Laptop', 3500.00);
INSERT INTO products (name, price) VALUES ('Mechanical Keyboard', 450.00);
INSERT INTO products (name, price) VALUES ('Gaming Mouse', 250.00);
INSERT INTO products (name, price) VALUES ('4K Monitor', 2200.00);
INSERT INTO products (name, price) VALUES ('USB-C Hub', 180.00);