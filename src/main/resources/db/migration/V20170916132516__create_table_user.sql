CREATE TABLE
honblack.USER
(
     id MEDIUMINT AUTO_INCREMENT,
     email VARCHAR(255) NOT NULL,
     password_hash VARCHAR(255) NOT NULL,
     PRIMARY KEY (id)
);
