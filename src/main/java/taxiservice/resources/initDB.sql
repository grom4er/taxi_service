CREATE DATABASE `Taxi_service`;
CREATE TABLE `manufacturers`
(
    `manufacturer_id`      int(11) NOT NULL AUTO_INCREMENT,
    `name`    varchar(45) NOT NULL,
    `country` varchar(45) NOT NULL,
    `deleted`              tinyint(4) DEFAULT '0',
    PRIMARY KEY (`manufacturer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

