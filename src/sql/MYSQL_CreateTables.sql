
-- Indexes for primary keys have been explicitly created.
SET foreign_key_checks = 0;
DROP TABLE IF EXISTS Event;
DROP TABLE IF EXISTS Role;
DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS UserCase;
DROP TABLE IF EXISTS Activity;
DROP TABLE IF EXISTS User_Activity;
DROP TABLE IF EXISTS Role_User;
DROP TABLE IF EXISTS Role_UserCase;
DROP TABLE IF EXISTS NewsItem;
DROP TABLE IF EXISTS Registration;
DROP TABLE IF EXISTS Event_NewsItem;
SET foreign_key_checks = 1;

-- ---------- Table for validation queries from the connection pool. ----------

DROP TABLE IF EXISTS PingTable;
CREATE TABLE PingTable (foo CHAR(1));

-- ------------------------------ EVENT -------------------------------------

CREATE TABLE Event ( 
	Event_id                   bigint UNSIGNED NOT NULL  AUTO_INCREMENT,
	Event_name                 varchar(150)  NOT NULL  ,
	Event_description          varchar(255)    ,
	Event_num_participants     int   DEFAULT 1 ,
	Event_date_start           date  NOT NULL  ,
	Event_date_end             date  NOT NULL  ,
	Event_reg_date_open        datetime  NOT NULL  ,
	Event_reg_date_close       datetime  NOT NULL  ,
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
	User_id                   bigint UNSIGNED NOT NULL  AUTO_INCREMENT,
	User_login                varchar(200)  NOT NULL  ,
	User_password             varchar(200)  NOT NULL  ,
	User_checked              bit   DEFAULT 0 ,
	User_name                 varchar(250)  NOT NULL  ,
	User_dni                  varchar(11)  NOT NULL ,
	User_email                varchar(200)  NOT NULL  ,
	User_telf                 varchar(15) , 
	User_shirtSize			  int ,
	CONSTRAINT pk_user PRIMARY KEY ( User_id ) ,
	CONSTRAINT User_login_UNIQUE UNIQUE ( User_login )  ,
	CONSTRAINT User_dni_UNIQUE UNIQUE ( User_dni )  ,
	CONSTRAINT User_email_UNIQUE UNIQUE ( User_email )
 ) engine=InnoDB;
 
 CREATE INDEX UserIndexByUser_login ON User (User_login);
 CREATE INDEX UserIndexByUser_dni ON User (User_dni); 
 
  -- ------------------------------ USERCase -------------------------------------
 
 CREATE TABLE UserCase ( 
	UserCase_id                   bigint UNSIGNED NOT NULL  AUTO_INCREMENT,
	UserCase_name                 varchar(150)  NOT NULL  ,
	CONSTRAINT pk_user_case PRIMARY KEY ( UserCase_id )  ,
	CONSTRAINT UserCase_name_UNIQUE UNIQUE ( UserCase_name )
 ) engine=InnoDB;
 
 CREATE INDEX UserCaseIndexByUserCase_name ON UserCase (UserCase_name);
 
  -- ------------------------------ ACTIVITY -------------------------------------
 
 CREATE TABLE Activity ( 
	Activity_id                   bigint UNSIGNED NOT NULL  AUTO_INCREMENT,
	Activity_organizer_id         bigint UNSIGNED   ,
	Activity_event_id			  bigint UNSIGNED NOT NULL  ,
	Activity_name                 varchar(150)  NOT NULL  ,
	Activity_description          varchar(200)    ,
	Activity_num_participants     int  NOT NULL DEFAULT 1 ,
	Activity_type_activity        int UNSIGNED   ,
	Activity_kind                 int UNSIGNED   ,
	Activity_date_start           datetime    ,
	Activity_date_end             datetime  NOT NULL  ,
	Activity_reg_date_open        datetime    ,
	Activity_reg_date_close       datetime  NOT NULL  ,
	CONSTRAINT pk_activity PRIMARY KEY ( Activity_id )  ,
	CONSTRAINT Activity_name_UNIQUE UNIQUE ( Activity_name )
 ) engine=InnoDB;
 
 CREATE INDEX ActivityIndexByActivity_name ON Activity (Activity_name);
 CREATE INDEX ActivityIndexByActivity_organizer_id  ON Activity (Activity_organizer_id);
 CREATE INDEX ActivityIndexByActivity_event_id  ON Activity (Activity_event_id);
 
 -- ------------------------------ USER_ACTIVITY -------------------------------------
 
 CREATE TABLE User_Activity (
	User_Activity_id				bigint UNSIGNED NOT NULL  AUTO_INCREMENT  ,
	User_Activity_User_id			bigint UNSIGNED NOT NULL  ,
	User_Activity_Activity_id		bigint UNSIGNED NOT NULL  ,
	CONSTRAINT pk_User_Activity PRIMARY KEY ( User_Activity_id )
 )  engine=InnoDB;
 
 CREATE INDEX User_ActivityIndexByUser_Activity_User_id  ON User_Activity (User_Activity_User_id);
 CREATE INDEX User_ActivityIndexByUser_Activity_Activity_id  ON User_Activity (User_Activity_Activity_id);
 
 -- ------------------------------ ROLE_USER -------------------------------------
 
 CREATE TABLE Role_User ( 
	Role_User_id					bigint UNSIGNED NOT NULL  AUTO_INCREMENT,
	Role_User_Role_id            	bigint UNSIGNED NOT NULL  ,
	Role_User_User_id     	bigint UNSIGNED NOT NULL  ,
	CONSTRAINT pk_role_user PRIMARY KEY ( Role_User_id )
 ) engine=InnoDB;
 
 CREATE INDEX Role_UserIndexByRole_User_Role_id ON Role_User (Role_User_Role_id);
 CREATE INDEX Role_UserIndexByRole_User_User_id  ON Role_User (Role_User_User_id);
 
  -- ------------------------------ ROLE_USERCASE ----------------------------------

 CREATE TABLE Role_UserCase ( 
	Role_UserCase_id					bigint UNSIGNED NOT NULL AUTO_INCREMENT,
	Role_UserCase_UserCase_id         	bigint UNSIGNED NOT NULL  ,
	Role_UserCase_Role_id             	bigint UNSIGNED NOT NULL  ,
	CONSTRAINT pk_ole_UserCase PRIMARY KEY ( Role_UserCase_id )
 );

 CREATE INDEX Role_UserCaseIndexByRole_UserCase_Role_id ON Role_UserCase (Role_UserCase_Role_id);
 CREATE INDEX Role_UserCaseIndexByRole_UserCase_UserPrifile_id  ON Role_UserCase (Role_UserCase_UserCase_id);
 
-- ------------------------------ NEWSITEM -------------------------------------
 
CREATE TABLE NewsItem ( 
	NewsItem_id                   bigint UNSIGNED NOT NULL  AUTO_INCREMENT,
	NewsItem_Event_id			  bigint UNSIGNED NOT NULL  ,
	NewsItem_user_id              bigint UNSIGNED   ,
	NewsItem_title                varchar(200)  NOT NULL  ,
	NewsItem_date_created         datetime    ,
	NewsItem_date_publish         datetime  NOT NULL  ,
	NewsItem_url                  varchar(250)    ,
	NewsItem_hours_priority       int UNSIGNED  DEFAULT 1 ,
	CONSTRAINT pk_news PRIMARY KEY ( NewsItem_id )
 ) engine=InnoDB;
 
 CREATE INDEX NewsItemIndexByNewsItemEvent_id ON NewsItem (NewsItem_Event_id);
 CREATE INDEX NewsItemIndexByNewsItemUser_id ON NewsItem (NewsItem_user_id);
 
-- ------------------------------ REGISTRATION -------------------------------------
 
 CREATE TABLE Registration ( 
	Registration_id					  bigint UNSIGNED NOT NULL  AUTO_INCREMENT,
	Registration_User_id              bigint UNSIGNED NOT NULL  ,
	Registration_Event_id             bigint UNSIGNED NOT NULL ,
	Registration_state                int UNSIGNED  DEFAULT 0 ,
	Registration_date_created         datetime NOT NULL  ,
	Registration_date_paid            datetime    ,
	Registration_paid                 bool   DEFAULT 0   ,
	CONSTRAINT pk_registration PRIMARY KEY ( Registration_id )
 ) engine=InnoDB;
 
 CREATE INDEX RegistrationIndexByRegistrationUser_id ON Registration (Registration_User_id);
 CREATE INDEX RegistrationIndexByRegistrationEvent_id ON Registration (Registration_Event_id);

 
 ALTER TABLE Activity ADD CONSTRAINT fk_activity_organizer FOREIGN KEY ( Activity_organizer_id ) REFERENCES User( User_id ) ON DELETE SET NULL ON UPDATE CASCADE;
 ALTER TABLE Activity ADD CONSTRAINT fk_activity_event FOREIGN KEY ( Activity_event_id ) REFERENCES Event( Event_id ) ON DELETE CASCADE ON UPDATE CASCADE;
 
 ALTER TABLE User_Activity ADD CONSTRAINT fk_user_activity_user FOREIGN KEY ( User_Activity_User_id ) REFERENCES User( User_id ) ON DELETE CASCADE ON UPDATE CASCADE;
 ALTER TABLE User_Activity ADD CONSTRAINT fk_user_activity_activity FOREIGN KEY ( User_Activity_Activity_id ) REFERENCES Activity( Activity_id ) ON DELETE CASCADE ON UPDATE CASCADE;
 
 ALTER TABLE Role_User ADD CONSTRAINT fk_role_user_role FOREIGN KEY ( Role_User_Role_id ) REFERENCES Role( Role_id ) ON DELETE CASCADE ON UPDATE CASCADE;
 ALTER TABLE Role_User ADD CONSTRAINT fk_role_user_user FOREIGN KEY ( Role_User_User_id ) REFERENCES User( User_id ) ON DELETE CASCADE ON UPDATE CASCADE;
 
 ALTER TABLE Role_UserCase ADD CONSTRAINT fk_role_userCase_userCase FOREIGN KEY ( Role_UserCase_UserCase_id ) REFERENCES UserCase( UserCase_id ) ON DELETE CASCADE ON UPDATE CASCADE;
 ALTER TABLE Role_UserCase ADD CONSTRAINT fk_role_userCase_role FOREIGN KEY ( Role_UserCase_Role_id  ) REFERENCES Role( Role_id ) ON DELETE CASCADE ON UPDATE CASCADE;
 
 ALTER TABLE NewsItem ADD CONSTRAINT fk_newsItem_event FOREIGN KEY ( NewsItem_Event_id ) REFERENCES Event( Event_id ) ON DELETE CASCADE ON UPDATE CASCADE;
 ALTER TABLE NewsItem ADD CONSTRAINT fk_newsItem_user FOREIGN KEY ( NewsItem_user_id ) REFERENCES User( User_id ) ON DELETE SET NULL ON UPDATE CASCADE;
 
 ALTER TABLE Registration ADD CONSTRAINT fk_registration_user FOREIGN KEY ( Registration_User_id ) REFERENCES User( User_id ) ON DELETE CASCADE ON UPDATE CASCADE;
 ALTER TABLE Registration ADD CONSTRAINT fk_registration_event FOREIGN KEY ( Registration_Event_id ) REFERENCES Event( Event_id ) ON DELETE CASCADE ON UPDATE CASCADE;