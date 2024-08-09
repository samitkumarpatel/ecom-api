-- User Table
CREATE TABLE users (
       id SERIAL PRIMARY KEY,
       username VARCHAR(50) UNIQUE NOT NULL,
       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-- user1
INSERT INTO users (username) VALUES ('user1');
-- user2
INSERT INTO users (username) VALUES ('user2');


-- Category Table
CREATE TABLE categories (
        id SERIAL PRIMARY KEY,
        name VARCHAR(100) NOT NULL,
        description TEXT
);

-- Product Table
CREATE TABLE products (
      id SERIAL PRIMARY KEY,
      name VARCHAR(100) NOT NULL,
      description TEXT,
      price NUMERIC(10, 2) NOT NULL,
      quantity INT NOT NULL,
      category_id INT REFERENCES categories(id) ON DELETE SET NULL,
      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Cart Table
CREATE EXTENSION hstore;
CREATE TABLE carts (
       id SERIAL PRIMARY KEY,
       user_id INT REFERENCES users(id) ON DELETE CASCADE,
       payload JSONB,  -- JSONB column to store additional cart-related data
       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_cart_user ON carts(user_id);
CREATE INDEX idx_cart_payload ON carts USING GIN (payload);

-- Order Table
CREATE TABLE orders (
        id SERIAL PRIMARY KEY,
        user_id INT REFERENCES users(id) ON DELETE CASCADE,
        total_amount NUMERIC(10, 2) NOT NULL,
        status VARCHAR(50) NOT NULL DEFAULT 'pending',
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Order Items Table (Associating Products with Orders)
CREATE TABLE order_items (
     id SERIAL PRIMARY KEY,
     order_id INT REFERENCES orders(id) ON DELETE CASCADE,
     product_id INT REFERENCES products(id) ON DELETE CASCADE,
     quantity INT NOT NULL,
     price_at_purchase NUMERIC(10, 2) NOT NULL,
     UNIQUE(order_id, product_id)
);
CREATE INDEX idx_order_user ON orders(user_id);

-- Example of Additional Indexes for Optimization (optional)
CREATE INDEX idx_user_username ON users(username);
CREATE INDEX idx_product_category ON products(category_id);


