
INSERT INTO `tariff`(`insurancetype`, `packet`, `price`, `active` ,`createddate`) VALUES ('DAY','BASIC',1.2, true,now());
INSERT INTO `tariff`(`insurancetype`, `packet`, `price`, `active` ,`createddate`) VALUES ('DAY','EXTEND',1.8, true,now());
INSERT INTO `tariff`(`insurancetype`, `packet`, `price`, `active` ,`createddate`) VALUES ('DAY','EXTRA',2.4, true,now());

INSERT INTO `tariff`(`insurancetype`, `packet`, `price`, `active` ,`createddate`) VALUES ('YEAR','BASIC',39, true,now());
INSERT INTO `tariff`(`insurancetype`, `packet`, `price`, `active` ,`createddate`) VALUES ('YEAR','EXTEND',49, true,now());
INSERT INTO `tariff`(`insurancetype`, `packet`, `price`, `active` ,`createddate`) VALUES ('YEAR','EXTRA',59, true,now());


insert into user(firstName,lastName,userType,city,address,postCode,identityId,createdDate) values ('John', 'Whick', 'EMPLOYEE','New York','Street 2', '111', '12345', now());
insert into user(firstName,lastName,userType,city,address,postCode,identityId,createdDate) values ('Tom', 'Smith', 'CLIENT','New York','Street 7', '2222', '654122', now());
