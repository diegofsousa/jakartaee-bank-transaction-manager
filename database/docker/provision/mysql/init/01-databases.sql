# create databases
CREATE DATABASE IF NOT EXISTS `appdb`;
CREATE DATABASE IF NOT EXISTS `analytics`;

# create root user and grant rights
CREATE USER 'root'@'localhost' IDENTIFIED BY 'local';
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%';