CREATE TABLE People (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    has_license BOOLEAN NOT NULL
);

CREATE TABLE Cars (
    id SERIAL PRIMARY KEY,
    brand VARCHAR(100) NOT NULL,
    model VARCHAR(100) NOT NULL,
    cost DECIMAL(10, 2) NOT NULL
);

CREATE TABLE People_Cars (
    people_id INT,
    car_id INT,
    PRIMARY KEY (people_id, car_id),
    FOREIGN KEY (people_id) REFERENCES People(id),
    FOREIGN KEY (car_id) REFERENCES Cars(id)
);
