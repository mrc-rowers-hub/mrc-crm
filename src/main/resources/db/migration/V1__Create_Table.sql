CREATE TABLE users
(
    user_id   INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(55) NOT NULL,
    password  VARCHAR(256) NOT NULL
);

CREATE TABLE user_squads
(
    user_id   INT AUTO_INCREMENT PRIMARY KEY,
    squad        ENUM('WOMENS', 'DEVELOPMENT', 'MENS') NOT NULL,
    level        ENUM('DEVELOPMENT', 'NOVICE', 'INTERMEDIATE', 'SENIOR') NOT NULL
);