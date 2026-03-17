-- Crear la base de datos
CREATE DATABASE minipigs;

-- Conectarse a la base de datos
\c minipigs;

-- Crear la tabla minipig
CREATE TABLE minipig (
    code            VARCHAR(50)      PRIMARY KEY,
    name            VARCHAR(100)     NOT NULL,
    gender          VARCHAR(20)      NOT NULL,
    microchipId     VARCHAR(100)     UNIQUE NOT NULL,
    race            VARCHAR(100)     NOT NULL,
    color           VARCHAR(50)      NOT NULL,
    weight          DOUBLE PRECISION NOT NULL,
    height          DOUBLE PRECISION NOT NULL,
    characteristic1 VARCHAR(255),
    characteristic2 VARCHAR(255),
    photoUrl        TEXT
);

