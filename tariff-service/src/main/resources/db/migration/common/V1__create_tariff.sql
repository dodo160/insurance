/*==============================================================*/
/* Table: TARIFF                                                */
/*==============================================================*/
create table IF NOT EXISTS TARIFF
(
   id                   BIGINT not null AUTO_INCREMENT,
   insuranceType        VARCHAR(255) NOT NULL,
   packet               VARCHAR(255) NOT NULL,
   price                decimal(10,2) NOT NULL,
   active               BOOLEAN NOT NULL,
   createdDate          datetime NOT NULL,
   lastUpdatedDate      datetime,
   deletedDate          datetime,
   primary key (id)
);

