create database if not exists cardealership_car_on_sale;
use cardealership_car_on_sale;

CREATE TABLE car_on_sale (
                             vin VARCHAR(17),
                             owner_id INT NOT NULL,
                             brand VARCHAR(50) NOT NULL,
                             production_year YEAR NOT NULL,
                             engine_power DECIMAL(10 , 2 ) NOT NULL,
                             price DECIMAL(12 , 2 ) NOT NULL,
                             PRIMARY KEY (vin)
);