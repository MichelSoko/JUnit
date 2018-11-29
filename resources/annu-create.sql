CREATE TABLE city (
   id_city SERIAL PRIMARY KEY,
   name VARCHAR (50) UNIQUE NOT NULL,
   mayor VARCHAR (100),
   inhabitants INT,
   postalcode VARCHAR (20)
);

CREATE TABLE person (
   id_person SERIAL PRIMARY KEY,
   id_city INT REFERENCES city ( id_city ),
   firstname VARCHAR (50),
   lastname VARCHAR (50),
   emails VARCHAR (100) UNIQUE NOT NULL,
   phone VARCHAR (20)
);

