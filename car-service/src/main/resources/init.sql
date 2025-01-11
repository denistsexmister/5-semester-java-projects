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


insert into car_on_sale(vin, owner_id, brand, production_year, engine_power, price) VALUES
                                                                                        ("1GNEK13Z62R298984", 1, "Honda", 2020, 120, 17000000),
                                                                                        ("JH4DC4450RS000001", 1, "Toyota", 2010, 150, 20000000),
                                                                                        ("5YJSA1CNXEFP01234", 2, "Honda", 2012, 100, 5000000),
                                                                                        ("WBAGN63412DR12345", 3, "Toyota", 2009, 94, 2000000);

