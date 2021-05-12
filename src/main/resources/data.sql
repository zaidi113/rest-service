DROP TABLE IF EXISTS rent;
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS user;


CREATE TABLE book (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  title VARCHAR(250) NOT NULL,
  isbn VARCHAR(250) NOT NULL,
  author VARCHAR(250) NOT NULL,
  quantity INT NOT NULL,
  constraint isbn_unq UNIQUE (isbn)
);

CREATE TABLE user (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) NOT NULL,
  address VARCHAR(1000) NOT NULL
);

CREATE TABLE rent (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  book_id INT NOT NULL,
  user_id INT NOT NULL,
  rent_date DATETIME NOT NULL,
  period INT NOT NULL
);

INSERT INTO book (title, isbn, author, quantity) VALUES
  ('Introduction To Java', 'ISBN123432', 'H Simpson', 2),
  ('Java in 2 hours', 'ISBN007', 'H Simpson', 2),
  ('Spring', 'ISBN6543', 'B Simpson', 1),
  ('Junit 5', 'TEST', 'B Simpson', 1),
  ('C++', 'ISBN90888', 'Someone', 3);


INSERT INTO user (first_name, last_name, address) VALUES
('qasim', 'zaidi', '46 Some Road, DR1');