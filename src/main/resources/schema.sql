/*Initialisation*/

DROP DATABASE paymybuddy;
CREATE DATABASE paymybuddy;
USE paymybuddy;

/*Creation des différentes tables*/
CREATE TABLE user
(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	login VARCHAR (30) NOT NULL,
    password VARCHAR (250) NOT NULL,
    first_name VARCHAR (30) NOT NULL,
    last_name VARCHAR (30) NOT NULL
);

CREATE TABLE authorities (
  email VARCHAR(50) NOT NULL,
  authority VARCHAR(50) NOT NULL
);

CREATE UNIQUE INDEX ix_auth_email on authorities (email,authority);

CREATE TABLE operation
(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    user_id INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id),
    buddy_id INTEGER NOT NULL,
    FOREIGN KEY (buddy_id) REFERENCES user (id),
    date DATE,
    amount FLOAT,
    description VARCHAR (250)
);

CREATE TABLE user_buddy
(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    user_id INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id),
    buddy_id INTEGER NOT NULL,
    FOREIGN KEY (buddy_id) REFERENCES user (id)
);