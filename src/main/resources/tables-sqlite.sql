PRAGMA foreign_keys = ON;
-- Create the priceTiers table
CREATE TABLE IF NOT EXISTS priceTiers (
                            tierId INTEGER PRIMARY KEY,
                            name VARCHAR(255) NOT NULL
);

-- Create the categories table
CREATE TABLE IF NOT EXISTS categories (
                            categoryId INTEGER PRIMARY KEY,
                            name VARCHAR(255) NOT NULL,
                            description TEXT,
                            brands BOOLEAN,
                            size BOOLEAN,
                            voltage BOOLEAN,
                            ampere BOOLEAN,
                            madeIn BOOLEAN,
                            viscosity BOOLEAN,
                            lifetime BOOLEAN
);

-- Create the companies table
CREATE TABLE IF NOT EXISTS companies (
                           companyId INTEGER PRIMARY KEY,
                           priceTierId INT,
                           isActive BOOLEAN DEFAULT TRUE,
                           commercialName VARCHAR(255) NOT NULL,
                           email VARCHAR(255),
                           contactName VARCHAR(255),
                           contactPhone VARCHAR(20),
                           FOREIGN KEY (priceTierId) REFERENCES priceTiers(tierId)
);

-- Create the products table
CREATE TABLE IF NOT EXISTS products (
                          productId INTEGER PRIMARY KEY,
                          description TEXT,
                          categoryId INT,
                          quantity INT,
                          visible BOOLEAN DEFAULT TRUE,
                          size INT,
                          voltage INT,
                          ampere INT,
                          viscosity BOOLEAN,
                          name VARCHAR(255) NOT NULL,
                          brands VARCHAR(255),
                          madeIn VARCHAR(255),
                          lifetime VARCHAR(255),
                          FOREIGN KEY (categoryId) REFERENCES categories(categoryId)
);

-- Junction table for products and priceTiers (many-to-many relationship)
CREATE TABLE IF NOT EXISTS  productPriceTiers (
                                   productId INT,
                                   tierId INT,
                                   PRIMARY KEY (productId, tierId),
                                   FOREIGN KEY (productId) REFERENCES products(productId),
                                   FOREIGN KEY (tierId) REFERENCES priceTiers(tierId)
);

-- Create the orders table with a foreign key for the user who made it
CREATE TABLE IF NOT EXISTS  orders (
                        orderId INTEGER PRIMARY KEY,
                        companyId INT,
                        userId INT,
                        status VARCHAR(50),
                        FOREIGN KEY (companyId) REFERENCES companies(companyId),
                        FOREIGN KEY (userId) REFERENCES users(userId)
);

-- Create the orderProducts table (junction table for orders and products)
CREATE TABLE IF NOT EXISTS orderProducts (
                               orderId INT,
                               productId INT,
                               quantity INT,
                               totalPrice INT,
                               PRIMARY KEY (orderId, productId),
                               FOREIGN KEY (orderId) REFERENCES orders(orderId),
                               FOREIGN KEY (productId) REFERENCES products(productId)
);

-- Create the users table
CREATE TABLE IF NOT EXISTS users (
                       userId INTEGER PRIMARY KEY,
                       username VARCHAR(255) UNIQUE NOT NULL,
                       passwordHash VARCHAR(255) NOT NULL,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       fullName VARCHAR(255),
                       role VARCHAR(50), -- e.g., 'admin', 'employee', etc.
                       isActive BOOLEAN DEFAULT TRUE
);
