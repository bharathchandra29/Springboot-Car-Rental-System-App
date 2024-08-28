-- Drop foreign key constraints before dropping tables
ALTER TABLE Lease DROP FOREIGN KEY lease_ibfk_1;
ALTER TABLE Payment DROP FOREIGN KEY payment_ibfk_1;

-- Drop tables
DROP TABLE IF EXISTS Lease;
DROP TABLE IF EXISTS Payment;
DROP TABLE IF EXISTS Car;
DROP TABLE IF EXISTS Customer;

-- Recreate tables
CREATE TABLE Car (
    car_id INT AUTO_INCREMENT PRIMARY KEY,
    car_number VARCHAR(50) UNIQUE NOT NULL,
    make VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    year INT NOT NULL,
    price_per_day DOUBLE NOT NULL,
    available BOOLEAN NOT NULL
);

CREATE TABLE Customer (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone_number VARCHAR(20),
    address TEXT
);

CREATE TABLE Lease (
    lease_id INT AUTO_INCREMENT PRIMARY KEY,
    car_id INT NOT NULL,
    customer_id INT NOT NULL,
    lease_start_date DATE NOT NULL,
    lease_end_date DATE,
    is_active BOOLEAN NOT NULL,
    FOREIGN KEY (car_id) REFERENCES Car(car_id),
    FOREIGN KEY (customer_id) REFERENCES Customer(customer_id)
);

CREATE TABLE Payment (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    lease_id INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    payment_date DATE NOT NULL,
    payment_method VARCHAR(50) NOT NULL,
    FOREIGN KEY (lease_id) REFERENCES Lease(lease_id)
);
