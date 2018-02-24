 DROP SCHEMA IF EXISTS Mitchell;
create schema Mitchell;
 SET SCHEMA Mitchell;
  DROP TABLE IF EXISTS Mitchell.Vehicle;
CREATE TABLE Mitchell.Vehicle(id INT PRIMARY KEY,
                  make VARCHAR(255),  model VARCHAR(255),  year INT);
/*
INSERT INTO Mitchell.Vehicle VALUES(1, 'Toyota', 'Camry', 2005);
 INSERT INTO Mitchell.Vehicle VALUES(2, 'Honda', 'Civic', 2005);*/
