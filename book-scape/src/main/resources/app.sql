CREATE database bookscapedb;
CREATE USER 'bookscape'@'localhost' IDENTIFIED  BY 'bookscape!';

GRANT ALL PRIVILEGES ON bookscapedb.* TO 'bookscape'@'localhost';

FLUSH PRIVILEGES;