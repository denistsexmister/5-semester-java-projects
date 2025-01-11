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