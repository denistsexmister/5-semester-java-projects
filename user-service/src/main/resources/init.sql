create database if not exists cardealership_user;
use cardealership_user;

CREATE TABLE user (
                      user_id INT AUTO_INCREMENT,
                      login VARCHAR(32) UNIQUE NOT NULL,
                      password VARCHAR(32) NOT NULL,
                      full_name VARCHAR(100) NOT NULL,
                      phone VARCHAR(14),
                      PRIMARY KEY (user_id)
);

insert into user(login, password, full_name, phone) VALUES
                                                        ("user1", "user1", "Denis Denisovich", "+380950764328"),
                                                        ("user2", "user2", "Oleg Olegovich", "+380950754128"),
                                                        ("user3", "user3", "Andriy Shevchenko", "+380950994328"),
                                                        ("user4", "user4", "John Boldrick", "+380660991728");