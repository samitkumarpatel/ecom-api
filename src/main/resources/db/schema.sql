CREATE TABLE users (
       id SERIAL PRIMARY KEY,
       name VARCHAR(255) NOT NULL,
       email VARCHAR(255) UNIQUE NOT NULL
);

INSERT INTO users (name, email) VALUES ('John Doe', 'johndoe@dev.net');
INSERT INTO users (name, email) VALUES ('Jane Doe', 'janedoe@dev.net');

CREATE TABLE categories (
        id SERIAL PRIMARY KEY,
        name VARCHAR(255) NOT NULL,
        description TEXT
);
CREATE TABLE products (
      id SERIAL PRIMARY KEY,
      category INT REFERENCES categories(id) ON DELETE SET NULL,
      name VARCHAR(255) NOT NULL,
      description TEXT,
      price DECIMAL(10, 2) NOT NULL,
      quantity INT NOT NULL
);

CREATE TABLE carts (
       id SERIAL PRIMARY KEY,
       user_id INT REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE cart_product_refs (
       cart_id INT REFERENCES carts(id) ON DELETE CASCADE,
       product_id INT REFERENCES products(id) ON DELETE CASCADE,
       quantity INT NOT NULL,
       PRIMARY KEY (cart_id, product_id)
);

CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    status VARCHAR(255) NOT NULL
);

CREATE TABLE order_product_refs (
    order_id INT REFERENCES orders(id) ON DELETE CASCADE,
    product_id INT REFERENCES products(id) ON DELETE CASCADE,
    quantity INT NOT NULL,
    PRIMARY KEY (order_id, product_id)
);
