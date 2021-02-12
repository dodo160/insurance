
drop table if exists REINSURANCE;
drop table if exists INSURANCE;
drop table if exists TARIFF;
drop table if exists USER;


/*==============================================================*/
/* Table: TARIFF                                                */
/*==============================================================*/
create table IF NOT EXISTS TARIFF
(
   id                   int not null AUTO_INCREMENT,
   insuranceType        varchar(255) NOT NULL,
   packet               varchar(255) NOT NULL,
   price                decimal(10,2) NOT NULL,
   createdDate          datetime NOT NULL,
   lastUpdatedDate      datetime,
   deletedDate          datetime,
   primary key (id)
);

/*==============================================================*/
/* Table: USER                                                  */
/*==============================================================*/
create table IF NOT EXISTS USER
(
   id               int not null AUTO_INCREMENT,
   firstName            varchar(255) NOT NULL,
   lastName             varchar(255) NOT NULL,
   userType             varchar(255) NOT NULL,
   city                 varchar(255) NOT NULL,
   address              varchar(255) NOT NULL,
   postCode             varchar(255) NOT NULL,
   identityId           varchar(255) NOT NULL,
   createdDate          datetime NOT NULL,
   lastUpdatedDate      datetime,
   deletedDate          datetime,
   primary key (id),
   UNIQUE KEY(identityId)
);

create table IF NOT EXISTS INSURANCE
(
   id                   int not null AUTO_INCREMENT,
   tariff_id            int not null,
   user_id              int not null,
   startDate            datetime not null,
   endDate              datetime not null,
   person               int not null,
   price                decimal(10,2) not null,
   createdDate          datetime not null,
   lastupdatedDate      datetime,
   deletedDate          datetime,
   primary key (id),
   FOREIGN KEY (tariff_id) REFERENCES TARIFF(id) ON DELETE CASCADE,
   FOREIGN KEY (user_id) REFERENCES USER(id) ON DELETE CASCADE
);

/*==============================================================*/
/* Table: REINSURANCE                                           */
/*==============================================================*/
create table IF NOT EXISTS REINSURANCE
(
   id                   int not null AUTO_INCREMENT,
   insurance_id         int NOT NULL,
   reinsuranceType      varchar(255) NOT NULL,
   createdDate          datetime NOT NULL,
   lastUpdatedDate      datetime,
   deletedDate          datetime,
   primary key (id),
   FOREIGN KEY (insurance_id) REFERENCES insurance(id) ON DELETE CASCADE,
   UNIQUE KEY(insurance_id, reinsuranceType)
);



