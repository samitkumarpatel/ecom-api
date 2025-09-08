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
      price DECIMAL(10, 2) NOT NULL
);

CREATE TABLE product_offers (
    id SERIAL PRIMARY KEY,
    product_id INT REFERENCES products(id) ON DELETE CASCADE,
    discount_percent DECIMAL(5,2) NOT NULL CHECK (discount_percent >= 0 AND discount_percent <= 100),
    valid_from DATE NOT NULL,
    valid_to DATE NOT NULL,
    description TEXT
);

CREATE TABLE inventory (
       id SERIAL PRIMARY KEY,
       product_id INT REFERENCES products(id) ON DELETE CASCADE,
       quantity INT NOT NULL CHECK (quantity >= 0),
       quantity_reserved INT NOT NULL CHECK (quantity_reserved <= quantity)
);

CREATE TABLE carts (
       id SERIAL PRIMARY KEY,
       user_id INT REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE cart_product_refs (
       cart_id INT REFERENCES carts(id) ON DELETE CASCADE,
       product_id INT REFERENCES products(id) ON DELETE CASCADE,
       quantity INT NOT NULL CHECK (quantity >= 0),
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
    quantity INT NOT NULL CHECK (quantity >= 0),
    PRIMARY KEY (order_id, product_id)
);

CREATE TABLE favorites (
   id SERIAL PRIMARY KEY,
   user_id INT REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE favorite_product_refs (
   favorite_id INT REFERENCES favorites(id) ON DELETE CASCADE,
   product_id INT REFERENCES products(id) ON DELETE CASCADE,
   PRIMARY KEY (favorite_id, product_id)
);