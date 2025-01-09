Create database if not exists cardealership;
use cardealership;
drop table if exists sold_car, deal, car_on_sale, user;

CREATE TABLE user (
                      user_id INT AUTO_INCREMENT,
                      login VARCHAR(32) UNIQUE NOT NULL,
                      password VARCHAR(32) NOT NULL,
                      full_name VARCHAR(100) NOT NULL,
                      phone VARCHAR(14),
                      PRIMARY KEY (user_id)
);

CREATE TABLE car_on_sale (
                             vin VARCHAR(17),
                             owner_id INT NOT NULL,
                             brand VARCHAR(50) NOT NULL,
                             production_year YEAR NOT NULL,
                             engine_power DECIMAL(10 , 2 ) NOT NULL,
                             price DECIMAL(12 , 2 ) NOT NULL,
                             PRIMARY KEY (vin),
                             FOREIGN KEY (owner_id)
                                 REFERENCES user (user_id)
                                 ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE deal (
                      deal_id INT AUTO_INCREMENT,
                      seller_id INT,
                      buyer_id INT,
                      deal_date TIMESTAMP NOT NULL default current_timestamp,
                      cost DECIMAL(12 , 2) NOT NULL,
                      PRIMARY KEY (deal_id),
                      FOREIGN KEY (seller_id)
                          REFERENCES user (user_id) on update cascade on delete set null,
                      FOREIGN KEY (buyer_id)
                          REFERENCES user (user_id) on update cascade on delete set null
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

insert into user(login, password, full_name, phone) VALUES
                                                        ("user1", "user1", "Denis Denisovich", "+380950764328"),
                                                        ("user2", "user2", "Oleg Olegovich", "+380950754128"),
                                                        ("user3", "user3", "Andriy Shevchenko", "+380950994328"),
                                                        ("user4", "user4", "John Boldrick", "+380660991728");
insert into car_on_sale(vin, owner_id, brand, production_year, engine_power, price) VALUES
                                                                                        ("1GNEK13Z62R298984", 1, "Honda", 2020, 120, 17000000),
                                                                                        ("JH4DC4450RS000001", 1, "Toyota", 2010, 150, 20000000),
                                                                                        ("5YJSA1CNXEFP01234", 2, "Honda", 2012, 100, 5000000),
                                                                                        ("WBAGN63412DR12345", 3, "Toyota", 2009, 94, 2000000);

