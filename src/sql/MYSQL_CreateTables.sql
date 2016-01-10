
-- Indexes for primary keys have been explicitly created.
SET foreign_key_checks = 0;
DROP TABLE IF EXISTS Event;
DROP TABLE IF EXISTS Role;
DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS UserCase;
DROP TABLE IF EXISTS UseCase;
DROP TABLE IF EXISTS Activity;
DROP TABLE IF EXISTS User_Activity;
DROP TABLE IF EXISTS Role_User;
DROP TABLE IF EXISTS Role_UserCase;
DROP TABLE IF EXISTS Role_UseCase;
DROP TABLE IF EXISTS NewsItem;
DROP TABLE IF EXISTS Registration;
-- DROP TABLE IF EXISTS Event_NewsItem;
DROP TABLE IF EXISTS Language;
DROP TABLE IF EXISTS Adress;
DROP TABLE IF EXISTS Address;
-- DROP TABLE IF EXISTS Email;
DROP TABLE IF EXISTS EmailTemplate;
DROP TABLE IF EXISTS Sponsor;
SET foreign_key_checks = 1;

-- ---------- Table for validation queries from the connection pool. ----------

DROP TABLE IF EXISTS PingTable;
CREATE TABLE PingTable (foo CHAR(1));

-- ------------------------------ EVENT -------------------------------------

CREATE TABLE Event ( 
	Event_id                           bigint UNSIGNED NOT NULL AUTO_INCREMENT,
	Event_name                         varchar(150) NOT NULL  ,
	Event_description                  TEXT    ,
	Event_num_participants             int DEFAULT 1 ,
	Event_minimunAge                   int DEFAULT 0 ,
	Event_price                        int DEFAULT 0 ,
	Event_date_start                   date NOT NULL  ,
	Event_date_end                     date NOT NULL  ,
	Event_reg_date_open                datetime NOT NULL  ,
	Event_reg_date_close               datetime NOT NULL  ,
	Event_setPaidTemplate_id           bigint UNSIGNED ,
	Event_onQueueTemplate_id           bigint UNSIGNED ,
	Event_outstandingTemplate_id       bigint UNSIGNED ,
	Event_outOfDateTemplate_id         bigint UNSIGNED ,
	Event_fromQueueToOutstanding_id    bigint UNSIGNED ,
	Event_rules                        TEXT,
	CONSTRAINT pk_event PRIMARY KEY ( Event_id ) ,
	CONSTRAINT Event_name_UNIQUE UNIQUE ( Event_name )
 ) engine=InnoDB;
 
 CREATE INDEX EventIndexByEvent_Name ON Event (Event_name);

 -- ------------------------------ ROLE -------------------------------------
 
 CREATE TABLE Role ( 
	Role_id                   bigint UNSIGNED NOT NULL  AUTO_INCREMENT,
	Role_name                 varchar(150)  NOT NULL  ,
	CONSTRAINT pk_role PRIMARY KEY ( Role_id )  ,
	CONSTRAINT Role_name_UNIQUE UNIQUE ( Role_name )
 ) engine=InnoDB;
 
 CREATE INDEX RoleIndexByRole_Name ON Role (Role_name);
 
 -- ------------------------------ USER -------------------------------------
 
 CREATE TABLE User ( 
	User_id                      bigint UNSIGNED NOT NULL  AUTO_INCREMENT,
	User_login                   varchar(200) NOT NULL  ,
	User_password                varchar(200) NOT NULL  ,
	User_secondPassword          varchar(200) NOT NULL  ,
	User_secondPasswordExpDate   datetime ,
	User_checked                 bit   DEFAULT 0 ,
	User_name                    varchar(250)  NOT NULL  ,
	User_dni                     varchar(11)  NOT NULL ,
	User_email                   varchar(200)  NOT NULL  ,
	User_telf                    varchar(15) , 
	User_shirtSize               varchar(3) ,
	User_inBlackList             bit DEFAULT 0 ,
	User_defaultLanguage         bigint UNSIGNED  ,
	User_borndate                datetime ,
	CONSTRAINT pk_user PRIMARY KEY ( User_id ) ,
	CONSTRAINT User_login_UNIQUE UNIQUE ( User_login )  ,
	CONSTRAINT User_dni_UNIQUE UNIQUE ( User_dni )  ,
	CONSTRAINT User_email_UNIQUE UNIQUE ( User_email )  
 ) engine=InnoDB;
 
 CREATE INDEX UserIndexByUser_login ON User (User_login);
 CREATE INDEX UserIndexByUser_dni ON User (User_dni); 
 CREATE INDEX UserIndexByUser_email ON User (User_email);
 
  -- ------------------------------ USERCase -------------------------------------
 
 CREATE TABLE UseCase ( 
	UseCase_id                   bigint UNSIGNED NOT NULL  AUTO_INCREMENT,
	UseCase_name                 varchar(150)  NOT NULL  ,
	CONSTRAINT pk_user_case PRIMARY KEY ( UseCase_id )  ,
	CONSTRAINT UseCase_name_UNIQUE UNIQUE ( UseCase_name )
 ) engine=InnoDB;
 
 CREATE INDEX UseCaseIndexByUseCase_name ON UseCase (UseCase_name);
 
  -- ------------------------------ ACTIVITY -------------------------------------
 
 CREATE TABLE Activity ( 
	Activity_id                   bigint UNSIGNED NOT NULL  AUTO_INCREMENT,
	Activity_Event_id             bigint UNSIGNED NOT NULL  ,
	Activity_name                 varchar(150)  NOT NULL  ,
	Activity_description          MEDIUMTEXT ,
	Activity_imageurl             varchar(256),
	Activity_num_participants     int  NOT NULL DEFAULT 1 ,
	Activity_type_activity        int UNSIGNED   ,
	Activity_official             bool DEFAULT 0   ,
	Activity_date_start           datetime    ,
	Activity_date_end             datetime  NOT NULL  ,
	Activity_reg_date_open        datetime    ,
	Activity_reg_date_close       datetime  NOT NULL  ,
	CONSTRAINT pk_activity PRIMARY KEY ( Activity_id )  ,
	CONSTRAINT Activity_name_UNIQUE UNIQUE ( Activity_name )
 ) engine=InnoDB;
 
 CREATE INDEX ActivityIndexByActivity_name ON Activity (Activity_name);
 CREATE INDEX ActivityIndexByActivity_event_id  ON Activity (Activity_Event_id);
 
 -- ------------------------------ USER_ACTIVITY -------------------------------------
 
 CREATE TABLE User_Activity (
	User_Activity_id                bigint UNSIGNED NOT NULL  AUTO_INCREMENT  ,
	User_Activity_User_id           bigint UNSIGNED NOT NULL  ,
	User_Activity_Activity_id       bigint UNSIGNED NOT NULL  ,
	CONSTRAINT pk_User_Activity PRIMARY KEY ( User_Activity_id )
 )  engine=InnoDB;
 
 CREATE INDEX User_ActivityIndexByUser_Activity_User_id  ON User_Activity (User_Activity_User_id);
 CREATE INDEX User_ActivityIndexByUser_Activity_Activity_id  ON User_Activity (User_Activity_Activity_id);
 
 -- ------------------------------ ROLE_USER -------------------------------------
 
 CREATE TABLE Role_User ( 
	Role_User_id            bigint UNSIGNED NOT NULL  AUTO_INCREMENT,
	Role_User_Role_id       bigint UNSIGNED NOT NULL  ,
	Role_User_User_id       bigint UNSIGNED NOT NULL  ,
	CONSTRAINT pk_role_user PRIMARY KEY ( Role_User_id )
 ) engine=InnoDB;
 
 CREATE INDEX Role_UserIndexByRole_User_Role_id ON Role_User (Role_User_Role_id);
 CREATE INDEX Role_UserIndexByRole_User_User_id  ON Role_User (Role_User_User_id);
 
  -- ------------------------------ ROLE_USERCASE ----------------------------------

 CREATE TABLE Role_UseCase ( 
	Role_UseCase_id                    bigint UNSIGNED NOT NULL AUTO_INCREMENT,
	Role_UseCase_UseCase_id            bigint UNSIGNED NOT NULL  ,
	Role_UseCase_Role_id               bigint UNSIGNED NOT NULL  ,
	CONSTRAINT pk_Role_UseCase PRIMARY KEY ( Role_UseCase_id )
 );

 CREATE INDEX Role_UseCaseIndexByRole_UseCase_Role_id ON Role_UseCase (Role_UseCase_Role_id);
 CREATE INDEX Role_UseCaseIndexByRole_UseCase_UserPrifile_id  ON Role_UseCase (Role_UseCase_UseCase_id);
 
-- ------------------------------ NEWSITEM -------------------------------------
 
CREATE TABLE NewsItem ( 
	NewsItem_id                   bigint UNSIGNED NOT NULL  AUTO_INCREMENT,
	NewsItem_Event_id             bigint UNSIGNED NOT NULL  ,
	NewsItem_User_id              bigint UNSIGNED   ,
	NewsItem_title                varchar(255)  NOT NULL  ,
	NewsItem_image                varchar(255) ,
	NewsItem_date_created         datetime    ,
	NewsItem_date_publish         datetime  NOT NULL  ,
	NewsItem_content              MEDIUMTEXT   ,
	NewsItem_hours_priority       int UNSIGNED  DEFAULT 1 ,
	CONSTRAINT pk_news PRIMARY KEY ( NewsItem_id )
 ) engine=InnoDB;
 
 CREATE INDEX NewsItemIndexByNewsItemEvent_id ON NewsItem (NewsItem_Event_id);
 CREATE INDEX NewsItemIndexByNewsItemUser_id ON NewsItem (NewsItem_User_id);
 
-- ------------------------------ REGISTRATION -------------------------------------
 
 CREATE TABLE Registration ( 
	Registration_id                   bigint UNSIGNED NOT NULL  AUTO_INCREMENT,
	Registration_User_id              bigint UNSIGNED NOT NULL  ,
	Registration_Event_id             bigint UNSIGNED NOT NULL ,
	Registration_state                int UNSIGNED  DEFAULT 0 ,
	Registration_date_created         datetime NOT NULL  ,
	Registration_date_paid            datetime    ,
	Registration_paid                 bool DEFAULT 0  ,
	Registration_place                int DEFAULT -1 ,
	CONSTRAINT pk_registration PRIMARY KEY ( Registration_id )
 ) engine=InnoDB;
 
 CREATE INDEX RegistrationIndexByRegistrationUser_id ON Registration (Registration_User_id);
 CREATE INDEX RegistrationIndexByRegistrationEvent_id ON Registration (Registration_Event_id);

-- ------------------------------ LANGUAGE ----------------------------------

CREATE TABLE Language ( 
	Language_id                   bigint UNSIGNED NOT NULL  AUTO_INCREMENT,
	Language_name                 varchar(50)  NOT NULL  ,
	CONSTRAINT pk_language PRIMARY KEY ( Language_id )
) engine=InnoDB;


-- ------------------------------ Address -------------------------------------

CREATE TABLE Address (
    Address_id 				bigint UNSIGNED NOT NULL  AUTO_INCREMENT,
    Address_user 			varchar(64) NOT NULL,
	Address_password 		varchar(256) NOT NULL,
    CONSTRAINT pk_adress PRIMARY KEY(Address_id),
    CONSTRAINT CategoryUniqueKey UNIQUE (Address_user)
) engine=InnoDB;

-- ------------------------------ EmailTemplate -------------------------------------

CREATE TABLE EmailTemplate (
	EmailTemplate_id             bigint UNSIGNED NOT NULL  AUTO_INCREMENT,
	EmailTemplate_name           varchar(128) NOT NULL UNIQUE, 
	EmailTemplate_adress_id      bigint UNSIGNED ,
	EmailTemplate_file           varchar(128), 
	EmailTemplate_fileName       varchar(128), 
	EmailTemplate_case           varchar(128) NOT NULL,
	EmailTemplate_body           TEXT,
    CONSTRAINT pk_email PRIMARY KEY(EmailTemplate_id)
) engine=InnoDB;

 CREATE INDEX EmailTemplateByAddressId ON EmailTemplate (EmailTemplate_adress_id);
 CREATE INDEX EmailTemplateByname ON EmailTemplate (EmailTemplate_name);

-- ------------------------------ Sponsor -------------------------------------

CREATE TABLE Sponsor (
	Sponsor_id             bigint UNSIGNED NOT NULL  AUTO_INCREMENT,
	Sponsor_event_id       bigint UNSIGNED NOT NULL,
	Sponsor_name           varchar(128),
	Sponsor_url            varchar(255),
	Sponsor_imageurl       varchar(256),
    CONSTRAINT pk_sponsor PRIMARY KEY(Sponsor_id)
) engine=InnoDB;

 CREATE INDEX SponsorByEvent ON Sponsor (Sponsor_event_id);


 ALTER TABLE Event ADD CONSTRAINT fk_event_setPaidTemplate FOREIGN KEY ( Event_setPaidTemplate_id ) REFERENCES EmailTemplate (EmailTemplate_id) ON DELETE SET NULL ON UPDATE CASCADE;
 ALTER TABLE Event ADD CONSTRAINT fk_event_onQueueTemplate FOREIGN KEY ( Event_onQueueTemplate_id ) REFERENCES EmailTemplate (EmailTemplate_id) ON DELETE SET NULL ON UPDATE CASCADE;
 ALTER TABLE Event ADD CONSTRAINT fk_event_outstandingTemplate FOREIGN KEY (Event_outstandingTemplate_id ) REFERENCES EmailTemplate (EmailTemplate_id) ON DELETE SET NULL ON UPDATE CASCADE;
 ALTER TABLE Event ADD CONSTRAINT fk_event_outOfDateTemplate FOREIGN KEY ( Event_outOfDateTemplate_id ) REFERENCES EmailTemplate (EmailTemplate_id) ON DELETE SET NULL ON UPDATE CASCADE;
 ALTER TABLE Event ADD CONSTRAINT fk_event_fromQueueToOutstanding FOREIGN KEY ( Event_fromQueueToOutstanding_id ) REFERENCES EmailTemplate (EmailTemplate_id) ON DELETE SET NULL ON UPDATE CASCADE;

 ALTER TABLE Sponsor ADD CONSTRAINT fk_sponsor_event FOREIGN KEY ( Sponsor_event_id ) REFERENCES Event (Event_id) ON DELETE CASCADE ON UPDATE CASCADE;

 ALTER TABLE EmailTemplate ADD CONSTRAINT fk_emailtemplate_adress FOREIGN KEY ( EmailTemplate_adress_id ) REFERENCES Address (Address_id) ON DELETE SET NULL ON UPDATE CASCADE;
 
 ALTER TABLE Activity ADD CONSTRAINT fk_activity_event FOREIGN KEY ( Activity_Event_id ) REFERENCES Event( Event_id ) ON DELETE CASCADE ON UPDATE CASCADE;
 
 ALTER TABLE User_Activity ADD CONSTRAINT fk_user_activity_user FOREIGN KEY ( User_Activity_User_id ) REFERENCES User( User_id ) ON DELETE CASCADE ON UPDATE CASCADE;
 ALTER TABLE User_Activity ADD CONSTRAINT fk_user_activity_activity FOREIGN KEY ( User_Activity_Activity_id ) REFERENCES Activity( Activity_id ) ON DELETE CASCADE ON UPDATE CASCADE;
 
 ALTER TABLE Role_User ADD CONSTRAINT fk_role_user_role FOREIGN KEY ( Role_User_Role_id ) REFERENCES Role( Role_id ) ON DELETE CASCADE ON UPDATE CASCADE;
 ALTER TABLE Role_User ADD CONSTRAINT fk_role_user_user FOREIGN KEY ( Role_User_User_id ) REFERENCES User( User_id ) ON DELETE CASCADE ON UPDATE CASCADE;
 
 ALTER TABLE Role_UseCase ADD CONSTRAINT fk_role_userCase_userCase FOREIGN KEY ( Role_UseCase_UseCase_id ) REFERENCES UseCase( UseCase_id ) ON DELETE CASCADE ON UPDATE CASCADE;
 ALTER TABLE Role_UseCase ADD CONSTRAINT fk_role_userCase_role FOREIGN KEY ( Role_UseCase_Role_id  ) REFERENCES Role( Role_id ) ON DELETE CASCADE ON UPDATE CASCADE;
 
 ALTER TABLE NewsItem ADD CONSTRAINT fk_newsItem_event FOREIGN KEY ( NewsItem_Event_id ) REFERENCES Event( Event_id ) ON DELETE CASCADE ON UPDATE CASCADE;
 ALTER TABLE NewsItem ADD CONSTRAINT fk_newsItem_user FOREIGN KEY ( NewsItem_User_id ) REFERENCES User( User_id ) ON DELETE CASCADE ON UPDATE CASCADE;
 
 ALTER TABLE Registration ADD CONSTRAINT fk_registration_user FOREIGN KEY ( Registration_User_id ) REFERENCES User( User_id ) ON DELETE CASCADE ON UPDATE CASCADE;
 ALTER TABLE Registration ADD CONSTRAINT fk_registration_event FOREIGN KEY ( Registration_Event_id ) REFERENCES Event( Event_id ) ON DELETE CASCADE ON UPDATE CASCADE;
 
 ALTER TABLE User ADD CONSTRAINT fk_default_language FOREIGN KEY ( User_defaultLanguage ) REFERENCES Language( Language_id ) ON DELETE CASCADE ON UPDATE CASCADE;
