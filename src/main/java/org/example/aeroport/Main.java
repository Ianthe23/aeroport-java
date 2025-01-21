package org.example.aeroport;
/*
CREATE TABLE Client (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    username VARCHAR(50) NOT NULL,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE Flight (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    from_city VARCHAR(50) NOT NULL,
    to_city VARCHAR(50) NOT NULL,
    departureTime TIMESTAMP NOT NULL,
    landingTime TIMESTAMP NOT NULL,
    seats INT NOT NULL
);

CREATE TABLE Ticket (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    username VARCHAR(50) NOT NULL,
    flight_id BIGINT NOT NULL,
    purchaseTime TIMESTAMP NOT NULL,
    FOREIGN KEY (flight_id) REFERENCES Flight(id) ON DELETE CASCADE
);

INSERT INTO Client(username, name) VALUES
   ('ivo.pastin', 'Maria'),
   ('ana.pastin', 'Ana'),
   ('david.gatea', 'David'),
   ('cosmii', 'Cosmina'),
   ('nico', 'Nico');

INSERT INTO Flight(from_city, to_city, departureTime, landingTime, seats) VALUES
    ('Cluj-Napoca', 'Bucuresti', '2021-06-01 12:00:00', '2021-06-01 13:00:00', 100),
    ('Cluj-Napoca', 'Timisoara', '2021-06-01 14:00:00', '2021-06-01 15:00:00', 100),
    ('Cluj-Napoca', 'Iasi', '2021-06-01 16:00:00', '2021-06-01 17:00:00', 100),
    ('Cluj-Napoca', 'Constanta', '2021-06-01 18:00:00', '2021-06-01 19:00:00', 100),
    ('Cluj-Napoca', 'Sibiu', '2021-06-01 20:00:00', '2021-06-01 21:00:00', 100);

INSERT INTO Ticket(username, flight_id, purchaseTime) VALUES
    ('ivo.pastin', 1, CURRENT_TIMESTAMP),
    ('ana.pastin', 2, CURRENT_TIMESTAMP),
    ('david.gatea', 3, CURRENT_TIMESTAMP),
    ('cosmii', 4, CURRENT_TIMESTAMP),
    ('nico', 5, CURRENT_TIMESTAMP);

INSERT INTO Ticket(username, flight_id, purchaseTime) VALUES
    ('ivo.pastin', 2, '2024-01-24 12:00:00');

SELECT * FROM Client;
SELECT * FROM Flight;
SELECT * FROM Ticket;

DROP TABLE Ticket;
*/
public class Main {
    public static void main(String[] args) {
        App.main(args);
    }
}
