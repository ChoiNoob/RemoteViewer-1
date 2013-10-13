CREATE TABLE Task (
  id         INT AUTO_INCREMENT UNIQUE,
  name       VARCHAR(200),
  command    VARCHAR(200),
  type       VARCHAR(200),
  station_id INT
);

CREATE TABLE UIPositions (
  ui_id    INT AUTO_INCREMENT UNIQUE,
  x        INT NOT NULL,
  y        INT NOT NULL,
  ref_id   INT NOT NULL,
  ref_type VARCHAR(20),
  PRIMARY KEY (ui_id)
);

CREATE TABLE images (
  TYPE VARCHAR(20) UNIQUE,
  DATA LONGBLOB,
  PRIMARY KEY (TYPE)
)