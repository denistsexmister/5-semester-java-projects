create database if not exists cardealership_deal;
use cardealership_deal;

CREATE TABLE deal (
                      deal_id INT AUTO_INCREMENT,
                      seller_id INT,
                      buyer_id INT,
                      deal_date TIMESTAMP NOT NULL default current_timestamp,
                      cost DECIMAL(12 , 2) NOT NULL,
                      PRIMARY KEY (deal_id)
);

CREATE TABLE sold_car (
                          vin VARCHAR(17),
                          deal_id INT,
                          brand VARCHAR(50) NOT NULL,
                          production_year YEAR NOT NULL,
                          engine_power DECIMAL(10 , 2 ) NOT NULL,
                          PRIMARY KEY (vin , deal_id),
                          FOREIGN KEY (deal_id)
                              REFERENCES deal (deal_id)
                              ON UPDATE CASCADE ON DELETE CASCADE
);