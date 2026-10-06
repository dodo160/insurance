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

/*==============================================================*/
/* Table: USER                                                  */
/*==============================================================*/
create table IF NOT EXISTS USER
(
   id               BIGINT not null AUTO_INCREMENT,
   firstName            VARCHAR(255) NOT NULL,
   lastName             VARCHAR(255) NOT NULL,
   userType             VARCHAR(255) NOT NULL,
   city                 VARCHAR(255) NOT NULL,
   address              VARCHAR(255) NOT NULL,
   postCode             VARCHAR(255) NOT NULL,
   identityId           VARCHAR(255) NOT NULL,
   createdDate          datetime NOT NULL,
   lastUpdatedDate      datetime,
   deletedDate          datetime,
   primary key (id),
   UNIQUE KEY(identityId)
);

create table IF NOT EXISTS INSURANCE
(
   id                   BIGINT not null AUTO_INCREMENT,
   tariff_id            BIGINT not null,
   user_id              BIGINT not null,
   startDate            DATE not null,
   endDate              DATE not null,
   person               INT not null,
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
   id                   BIGINT not null AUTO_INCREMENT,
   insurance_id         BIGINT NOT NULL,
   reinsuranceType      VARCHAR(255) NOT NULL,
   createdDate          datetime NOT NULL,
   lastUpdatedDate      datetime,
   deletedDate          datetime,
   primary key (id),
   FOREIGN KEY (insurance_id) REFERENCES insurance(id) ON DELETE CASCADE,
   UNIQUE KEY(insurance_id, reinsuranceType)
);

/*==============================================================*/
/* Table: TEMPORAL_ENTITY                                       */
/*==============================================================*/
create table IF NOT EXISTS TEMPORAL_ENTITY
(
   id                   BIGINT not null AUTO_INCREMENT,
   user_id              BIGINT not null,
   entityClass          VARCHAR(255) not null,
   mediaType            VARCHAR(255) not null,
   entity               LONGTEXT not null,
   primary key (id),
   FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
   UNIQUE KEY(user_id, entityClass, mediaType, entity(255)),
   FULLTEXT(entity)
);




