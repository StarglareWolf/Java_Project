CREATE DATABASE todo_app;
USE todo_app;

CREATE TABLE users(
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      username VARCHAR(50) NOT NULL UNIQUE,
                      password VARCHAR(255) NOT NULL,
                      create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE todolist(
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         user_id INT NOT NULL,
                         title VARCHAR(100) NOT NULL DEFAULT '无标题',
                         content VARCHAR(255) NOT NULL,
                         is_done BOOLEAN DEFAULT FALSE,
                         create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
                         FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);