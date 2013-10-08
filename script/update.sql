create table Task(
  id int auto_increment unique,
  name VARCHAR (200),
  command VARCHAR(200),
  type VARCHAR(200),
  station_id int
)