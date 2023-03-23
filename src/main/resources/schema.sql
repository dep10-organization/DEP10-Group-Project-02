CREATE TABLE IF NOT EXISTS Employee(
    id VARCHAR(10) PRIMARY KEY,
    name VARCHAR(100) NOT NUlL,
    address VARCHAR(300) NOT NULL
);

INSERT INTO Employee (id, name, address) VALUES ('E001','Saman','Panadura');