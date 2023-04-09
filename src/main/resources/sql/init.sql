CREATE DATABASE itstory;
CREATE USER guest@localhost IDENTIFIED BY 'itstory!@#';
GRANT ALL PRIVILEGES ON itstory.* TO 'guest'@'localhost';
FLUSH PRIVILEGES;