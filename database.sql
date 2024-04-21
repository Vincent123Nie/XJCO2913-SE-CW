CREATE DATABASE  IF NOT EXISTS `gps` ;
USE `gps`;

DROP TABLE IF EXISTS employee;
CREATE TABLE employee (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(50) NOT NULL UNIQUE,
                        username VARCHAR(50) NOT NULL UNIQUE,
                        password CHAR(64) NOT NULL,
                        phone VARCHAR(20) NOT NULL UNIQUE,
                        level int NOT NULL,
                        status int NOT NULL DEFAULT '1',
                        create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
                        update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        create_user BIGINT DEFAULT '1',
                        update_user BIGINT DEFAULT '1'
);
INSERT INTO employee (username, name, password, phone, level) VALUES ('boss','BOSS',MD5('123456'),'18932490273',1);

DROP TABLE IF EXISTS activity;
CREATE TABLE activity (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL UNIQUE,
                        picture VARCHAR(255) NOT NULL,
                        actual_participant INT DEFAULT '0',
                        max_participant INT NOT NULL,
                        start_time DATETIME NOT NULL,
                        end_time DATETIME NOT NULL,
                        status int NOT NULL DEFAULT '0',
                        description TEXT,
                        create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
                        update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        create_user BIGINT DEFAULT '1',
                        update_user BIGINT DEFAULT '1'
);
INSERT INTO activity (name, picture, max_participant, start_time, end_time) VALUES ('activity1','picture1',50,'2024-04-14T00:00:00','2024-04-15T00:00:00');

DROP TABLE IF EXISTS activity_user;
CREATE TABLE activity_user (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            activity_id BIGINT NOT NULL,
                            user_id BIGINT NOT NULL,
                            status INT DEFAULT '0'
);

DROP TABLE IF EXISTS course;
CREATE TABLE course (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL UNIQUE,
                        picture VARCHAR(255) NOT NULL,
                        resource VARCHAR(255) NOT NULL,
                        type VARCHAR(255),
                        duration INT NOT NULL,
                        calorie INT NOT NULL,
                        description TEXT,
                        create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
                        update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        create_user BIGINT DEFAULT '1',
                        update_user BIGINT DEFAULT '1'
);
INSERT INTO course (name, picture, resource, type, duration, calorie) VALUES ('course1', 'picture1', 'resource1', 'Yoga', '10', '50');


DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        user_id BIGINT NOT NULL,
                        type INT NOT NULL,
                        price INT NOT NULL,
                        status INT NOT NULL,
                        create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
                        update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
INSERT INTO `order` (user_id, type, price, status) VALUES (1, 1, 30, 1);

DROP TABLE IF EXISTS revenue;
CREATE TABLE revenue (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        order_id BIGINT NOT NULL,
                        money INT NOT NULL,
                        time DATETIME DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS user;
CREATE TABLE user (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      username VARCHAR(255) NOT NULL UNIQUE,
                      email VARCHAR(255) NOT NULL UNIQUE,
                      password CHAR(255) NOT NULL,
                      avatar VARCHAR(255) NOT NULL,
                      create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
                      update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
INSERT INTO user (username, email, password, avatar) VALUES ('user1', 'user@email.com', MD5('123456'), 'http://avatar.jpg');


DROP TABLE IF EXISTS route;
CREATE TABLE route (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(255),
                       userId BIGINT,
                       isShare TINYINT(1),
                       size INT,
                       distance DOUBLE,
                       time VARCHAR(10),
                       speed DOUBLE,
                       startTime BIGINT,
                       endTime BIGINT,
                       loc_longitude DOUBLE,
                       loc_latitude DOUBLE,
                       status int NOT NULL
);

DROP TABLE IF EXISTS location_point;
CREATE TABLE location_point (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                route_id BIGINT NOT NULL,
                                longitude DOUBLE,
                                latitude DOUBLE,
                                locTime BIGINT,
                                speed DOUBLE,
                                type INT NOT NULL DEFAULT '0'
);

DROP TABLE IF EXISTS user_location;
CREATE TABLE user_location (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               user_id BIGINT,
                               longitude DOUBLE,
                               latitude DOUBLE,
                               update_time datetime
);

DROP TABLE IF EXISTS friendship;
CREATE TABLE friendship (
                             user_id BIGINT NOT NULL,
                             friend_id BIGINT NOT NULL,
                             status ENUM('requested', 'accepted') NOT NULL,
                             action_user_id INT NOT NULL, -- 发起请求的用户ID
                             create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS run_route;
CREATE TABLE run_route (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          runRouteId BIGINT NOT NULL,
                          routeId BIGINT NOT NULL,
                          userId BIGINT NOT NULL,
                          status INT NOT NULL DEFAULT '1',
                          created DATETIME DEFAULT CURRENT_TIMESTAMP,
                          updated DATETIME DEFAULT CURRENT_TIMESTAMP
);
