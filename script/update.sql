CREATE TABLE Task (
  id         INT AUTO_INCREMENT UNIQUE,
  name       VARCHAR(200),
  command    VARCHAR(200),
  type       VARCHAR(200),
  station_id INT
);

CREATE TABLE uipositions (
  x        INT NOT NULL,
  y        INT NOT NULL,
  ref_id   INT NOT NULL,
  ref_type VARCHAR(20),
  PRIMARY KEY (ref_type, ref_id)
);

CREATE TABLE images (
  TYPE VARCHAR(20) UNIQUE,
  DATA LONGBLOB,
  PRIMARY KEY (TYPE)
);

CREATE TABLE labels (
  id   INT AUTO_INCREMENT UNIQUE,
  name VARCHAR(200),
  PRIMARY KEY (id)
);