CREATE TABLE task (
  id         INT AUTO_INCREMENT UNIQUE,
  name       VARCHAR(200),
  command    VARCHAR(200),
  type       VARCHAR(200),
  imageId    INT,
  station_id INT
)
  DEFAULT CHARSET = utf8;

CREATE TABLE uipositions (
  x        INT NOT NULL,
  y        INT NOT NULL,
  ref_id   INT NOT NULL,
  ref_type VARCHAR(20),
  PRIMARY KEY (ref_type, ref_id)
)
  DEFAULT CHARSET = utf8;

CREATE TABLE images (
  id     INT UNIQUE,
  TYPE   VARCHAR(20) UNIQUE,
  width  INT NOT NULL,
  height INT NOT NULL,
  DATA   LONGBLOB,
  PRIMARY KEY (id)
)
  DEFAULT CHARSET = utf8;

CREATE TABLE labels (
  id   INT AUTO_INCREMENT UNIQUE,
  name VARCHAR(200),
  PRIMARY KEY (id)
);

CREATE TABLE `station` (
  `station_id`      BIGINT(20)   NOT NULL AUTO_INCREMENT,
  `comment`         VARCHAR(255) DEFAULT NULL,
  `deviceType`      VARCHAR(255) DEFAULT NULL,
  `host`            VARCHAR(255) NOT NULL,
  `login`           VARCHAR(255) DEFAULT NULL,
  `name`            VARCHAR(255) DEFAULT NULL,
  `password`        VARCHAR(255) DEFAULT NULL,
  `port`            VARCHAR(255) DEFAULT NULL,
  `allowStatistics` BIT(1) DEFAULT NULL,
  `status`          INT(11) DEFAULT NULL,
  imageId    INT,
  delay             INT DEFAULT NULL,
  PRIMARY KEY (`station_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


alter table viewer.labels add column imageId int
alter table viewer.labels add column hasImage tinyint