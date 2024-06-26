CREATE DATABASE IF NOT EXISTS `gps` ;
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
INSERT INTO activity (name, picture, max_participant, start_time, end_time) VALUES ('activity1','https://web-tlias-guyu.oss-cn-beijing.aliyuncs.com/9d5fe231-cce1-4d93-b541-6f48025b9a3f.jpg',50,'2024-04-14T00:00:00','2024-04-15T00:00:00');

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
INSERT INTO course (name, picture, resource, type, duration, calorie) VALUES ('course1', 'https://web-tlias-guyu.oss-cn-beijing.aliyuncs.com/3c41b231-d593-43f4-8fa1-09d22ec4f4ca.png', 'https://web-tlias-guyu.oss-cn-beijing.aliyuncs.com/a786a06b-7d58-41fa-a9b1-c09a67c8195a.mp4', 'Weight-losing', '10', '50');
INSERT INTO course (name, picture, resource, type, duration, calorie) VALUES ('course2', 'https://web-tlias-guyu.oss-cn-beijing.aliyuncs.com/39d26661-e5dc-47b3-9aef-9e21356aaa41.png', 'https://web-tlias-guyu.oss-cn-beijing.aliyuncs.com/e225bb29-5978-4568-a91c-911661cf79e3.mp4', 'Muscle-building', '10', '50');
INSERT INTO course (name, picture, resource, type, duration, calorie) VALUES ('course3', 'https://web-tlias-guyu.oss-cn-beijing.aliyuncs.com/ccb73e8c-c1df-4bc9-84c0-1952042fb97a.png', 'https://web-tlias-guyu.oss-cn-beijing.aliyuncs.com/473fe89a-13cc-4484-82f5-471446b86e89.mp4', 'Rehabilitation', '10', '50');
INSERT INTO course (name, picture, resource, type, duration, calorie) VALUES ('course4', 'https://web-tlias-guyu.oss-cn-beijing.aliyuncs.com/f4d0ea75-b70c-4d17-b461-ae6f00385595.png', 'https://web-tlias-guyu.oss-cn-beijing.aliyuncs.com/a4260d48-1f61-4cf3-a4a8-4e5dca7bf169.mp4', 'Yoga', '10', '50');
INSERT INTO course (name, picture, resource, type, duration, calorie) VALUES ('course5', 'https://web-tlias-guyu.oss-cn-beijing.aliyuncs.com/b7dcc362-953f-402f-b3be-e77c89683767.png', 'https://web-tlias-guyu.oss-cn-beijing.aliyuncs.com/4e0e702c-902c-4f4c-982d-8cff53240afa.mp4', 'Dancing', '10', '50');


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
INSERT INTO `order` (user_id, type, price, status) VALUES (1, 1, 80, 1);

DROP TABLE IF EXISTS revenue;
CREATE TABLE revenue (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        order_id BIGINT NOT NULL,
                        money INT NOT NULL,
                        time DATETIME DEFAULT CURRENT_TIMESTAMP
);
INSERT INTO revenue (order_id, money, time) VALUES (0, 300, '2024-03-25T12:46:31');
INSERT INTO revenue (order_id, money, time) VALUES (0, 30, '2024-01-24T03:55:07');
INSERT INTO revenue (order_id, money, time) VALUES (0, 80, '2024-04-11T05:05:00');
INSERT INTO revenue (order_id, money, time) VALUES (0, 80, '2024-01-20T13:28:39');
INSERT INTO revenue (order_id, money, time) VALUES (0, 80, '2024-03-23T17:34:10');
INSERT INTO revenue (order_id, money, time) VALUES (0, 300, '2024-01-28T18:28:13');
INSERT INTO revenue (order_id, money, time) VALUES (0, 80, '2024-01-14T08:54:26');
INSERT INTO revenue (order_id, money, time) VALUES (0, 30, '2024-03-01T20:52:23');
INSERT INTO revenue (order_id, money, time) VALUES (0, 30, '2024-01-24T12:07:20');
INSERT INTO revenue (order_id, money, time) VALUES (0, 30, '2024-02-16T19:13:23');
INSERT INTO revenue (order_id, money, time) VALUES (0, 30, '2024-01-11T06:45:04');
INSERT INTO revenue (order_id, money, time) VALUES (0, 80, '2024-02-29T07:38:32');
INSERT INTO revenue (order_id, money, time) VALUES (0, 80, '2024-03-25T20:45:09');
INSERT INTO revenue (order_id, money, time) VALUES (0, 300, '2024-01-20T22:51:32');
INSERT INTO revenue (order_id, money, time) VALUES (0, 30, '2024-04-21T12:24:02');
INSERT INTO revenue (order_id, money, time) VALUES (0, 80, '2024-04-08T21:54:45');
INSERT INTO revenue (order_id, money, time) VALUES (0, 30, '2024-01-21T05:14:13');
INSERT INTO revenue (order_id, money, time) VALUES (0, 300, '2024-01-19T10:08:28');
INSERT INTO revenue (order_id, money, time) VALUES (0, 80, '2024-02-04T08:20:07');
INSERT INTO revenue (order_id, money, time) VALUES (0, 30, '2024-01-14T18:35:21');
INSERT INTO revenue (order_id, money, time) VALUES (0, 300, '2024-04-26T11:22:55');
INSERT INTO revenue (order_id, money, time) VALUES (0, 80, '2024-02-14T12:16:43');
INSERT INTO revenue (order_id, money, time) VALUES (0, 30, '2024-01-11T01:36:46');
INSERT INTO revenue (order_id, money, time) VALUES (0, 80, '2024-04-22T05:31:41');
INSERT INTO revenue (order_id, money, time) VALUES (0, 30, '2024-02-23T15:43:43');
INSERT INTO revenue (order_id, money, time) VALUES (0, 80, '2024-03-26T10:46:49');
INSERT INTO revenue (order_id, money, time) VALUES (0, 30, '2024-02-06T23:28:29');
INSERT INTO revenue (order_id, money, time) VALUES (0, 300, '2024-03-08T06:29:39');
INSERT INTO revenue (order_id, money, time) VALUES (0, 30, '2024-01-20T18:27:34');
INSERT INTO revenue (order_id, money, time) VALUES (0, 30, '2024-03-03T12:02:16');
INSERT INTO revenue (order_id, money, time) VALUES (0, 300, '2024-03-08T08:11:42');
INSERT INTO revenue (order_id, money, time) VALUES (0, 80, '2024-04-13T15:05:01');
INSERT INTO revenue (order_id, money, time) VALUES (0, 30, '2024-01-02T18:42:27');
INSERT INTO revenue (order_id, money, time) VALUES (0, 80, '2024-01-09T12:57:58');
INSERT INTO revenue (order_id, money, time) VALUES (0, 300, '2024-04-03T16:18:47');
INSERT INTO revenue (order_id, money, time) VALUES (0, 80, '2024-02-19T09:12:42');
INSERT INTO revenue (order_id, money, time) VALUES (0, 80, '2024-03-09T19:18:28');
INSERT INTO revenue (order_id, money, time) VALUES (0, 300, '2024-03-29T11:53:36');
INSERT INTO revenue (order_id, money, time) VALUES (0, 30, '2024-02-17T06:14:48');
INSERT INTO revenue (order_id, money, time) VALUES (0, 80, '2024-01-24T16:18:33');

DROP TABLE IF EXISTS user;
CREATE TABLE user (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      username VARCHAR(255) UNIQUE,
                      email VARCHAR(255) NOT NULL UNIQUE,
                      password CHAR(255),
                      avatar VARCHAR(255),
                      create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
                      update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
INSERT INTO user (username, email, password, avatar) VALUES ('user1', 'user@email.com', MD5('123456'), 'http://avatar.jpg');

DROP TABLE IF EXISTS member;
CREATE TABLE member(
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       user_id INT NOT NULL,
                       registration_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                       expiration_date DATETIME
);
INSERT INTO member (user_id, Expiration_date) VALUES (1, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 30 DAY));

DROP TABLE IF EXISTS admin_log;
CREATE TABLE admin_log(
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        user_id BIGINT NOT NULL,
                        method VARCHAR(255) NOT NULL,
                        object_id BIGINT,
                        parameter VARCHAR(255),
                        time DATETIME DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS post;
CREATE TABLE post(
                     post_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                     userId BIGINT NOT NULL,
                     title VARCHAR(255) NOT NULL,
                     content TEXT NOT NULL,
                     create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
                     update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
INSERT INTO post (userId, title, content) VALUES (1, 'post1', 'content1');

DROP TABLE IF EXISTS comment;
CREATE TABLE comment (
                         comment_id INT AUTO_INCREMENT PRIMARY KEY,
                         post_id INT NOT NULL,
                         user_id INT NOT NULL,
                         content TEXT NOT NULL,
                         create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
                         update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
INSERT INTO comment (post_id, user_id, content) VALUES (1, 1, 'content1');

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
