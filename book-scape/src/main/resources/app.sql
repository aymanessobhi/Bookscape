CREATE database bookscapedb;
CREATE USER 'bookscapedb'@'localhost' IDENTIFIED  BY 'bookscapedb!';

GRANT ALL PRIVILEGES ON gymcomdb.* TO 'bookscapedb'@'localhost';

FLUSH PRIVILEGES;