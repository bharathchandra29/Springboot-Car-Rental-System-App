-- Insert data into Car table
INSERT INTO Car (car_number, make, model, year, price_per_day, available) VALUES
('AB123Cd', 'Toyotaa', 'Camry', 2020, 50.00, TRUE),
('XY456ZT', 'Honda', 'Civic', 2019, 40.00, TRUE),
('LM789OP', 'Ford', 'Mustang', 2021, 70.00, TRUE),
('QR012ST', 'Chevrolet', 'Impala', 2018, 45.00, FALSE),
('UV345WX', 'Nissan', 'Altima', 2022, 55.00, TRUE),
('GH678IJ', 'BMW', '3 Series', 2020, 65.00, TRUE),
('KL910MN', 'Audi', 'A4', 2021, 60.00, TRUE),
('OP112QR', 'Mercedes-Benz', 'C-Class', 2019, 75.00, FALSE),
('ST314UV', 'Tesla', 'Model 3', 2021, 80.00, TRUE),
('WX516YZ', 'Hyundai', 'Elantra', 2020, 50.00, TRUE);


-- Insert data into Customer table
INSERT INTO Customer (first_name, last_name, email, phone_number, address) VALUES
('Bharath', 'Chandra', 'bharathchandra.ila@gmail.com', '9160348766', '224 Lelle Vari St, Chirala'),
('Sunil', 'Kumar', 'sunilkumar@gmail.com', '9836567563', '456 Ramnager St, Ongole'),
('Murali', 'Ram', 'muraliram@gmail.com', '8965536956', '789 Houseing Board St, Chirala'),
('Siva', 'Shanker', 'sivashanker@gmail.com', '9765854266', '101 Sivalayam St, Chirala');

-- Insert data into Lease table
INSERT INTO Lease (car_id, customer_id, lease_start_date, lease_end_date, is_active) VALUES
(1, 1, '2024-08-01', '2024-08-07', TRUE),
(2, 2, '2024-08-10', '2024-08-15', TRUE),
(3, 3, '2024-08-05', '2024-08-12', FALSE),
(4, 4, '2024-08-15', '2024-08-20', TRUE);

-- Insert data into Payment table
INSERT INTO Payment (lease_id, amount, payment_date, payment_method) VALUES
(1, 350.00, '2024-08-01', 'Credit Card'),
(2, 200.00, '2024-08-10', 'Cash'),
(3, 490.00, '2024-08-05', 'Credit Card'),
(4, 225.00, '2024-08-15', 'Debit Card');
