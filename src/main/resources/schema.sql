CREATE TABLE IF NOT EXISTS Customer (
  id VARCHAR(20) PRIMARY KEY,
  name VARCHAR(100),
  address VARCHAR(300)
);
CREATE TABLE IF NOT EXISTS Teacher
(
    id      VARCHAR(20) PRIMARY KEY,
    name    VARCHAR(100),
    address VARCHAR(300)
);
CREATE TABLE IF NOT EXISTS Employee(
                                       id VARCHAR(10) PRIMARY KEY,
                                       name VARCHAR(100) NOT NUlL,
                                       address VARCHAR(300) NOT NULL
);

