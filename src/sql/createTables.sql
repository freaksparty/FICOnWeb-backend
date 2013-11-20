CREATE SCHEMA ficon;

CREATE TABLE ficon.event ( 
	id                   bigint UNSIGNED NOT NULL  AUTO_INCREMENT,
	name                 varchar(150)  NOT NULL  ,
	description          varchar(255)    ,
	num_participants     int   DEFAULT 1 ,
	date_start           date  NOT NULL  ,
	date_end             date  NOT NULL  ,
	reg_date_open        datetime  NOT NULL  ,
	reg_date_close       datetime  NOT NULL  ,
	CONSTRAINT pk_event PRIMARY KEY ( id )
 ) engine=InnoDB;

ALTER TABLE ficon.event COMMENT 'Evento General';

ALTER TABLE ficon.event MODIFY name varchar(150)  NOT NULL   COMMENT 'Titulo de Evento: FicOnDev 13, FicOnLan 14, etc';

ALTER TABLE ficon.event MODIFY description varchar(255)     COMMENT 'Descripcion General del Evento';

ALTER TABLE ficon.event MODIFY num_participants int   DEFAULT 1  COMMENT 'Plazas Vacantes';

ALTER TABLE ficon.event MODIFY reg_date_open datetime  NOT NULL   COMMENT 'Fecha comienzo Inscripcion';

ALTER TABLE ficon.event MODIFY reg_date_close datetime  NOT NULL   COMMENT 'Fecha fin inscripcion';

CREATE TABLE ficon.role ( 
	id                   bigint UNSIGNED NOT NULL  AUTO_INCREMENT,
	name                 varchar(150)  NOT NULL  ,
	CONSTRAINT pk_role PRIMARY KEY ( id )
 ) engine=InnoDB;

ALTER TABLE ficon.role COMMENT 'Role a desempeñar';

CREATE TABLE ficon.user ( 
	id                   bigint UNSIGNED NOT NULL  AUTO_INCREMENT,
	login                varchar(200)  NOT NULL  ,
	password             varchar(200)  NOT NULL  ,
	checked              bit   DEFAULT 0 ,
	CONSTRAINT pk_user PRIMARY KEY ( id )
 ) engine=InnoDB;

ALTER TABLE ficon.user COMMENT 'Tabla con los datos de los usuarios';

ALTER TABLE ficon.user MODIFY checked bit   DEFAULT 0  COMMENT 'Marcado';

CREATE TABLE ficon.user_case ( 
	id                   bigint UNSIGNED NOT NULL  AUTO_INCREMENT,
	name                 varchar(150)  NOT NULL  ,
	CONSTRAINT pk_user_case PRIMARY KEY ( id )
 ) engine=InnoDB;

CREATE TABLE ficon.user_data ( 
	id_user              bigint UNSIGNED NOT NULL  ,
	name                 varchar(250)  NOT NULL  ,
	dni                  varchar(11)  NOT NULL  
 ) engine=InnoDB;

CREATE INDEX idx_user_data ON ficon.user_data ( id_user );

CREATE TABLE ficon.user_info ( 
	id_user              bigint UNSIGNED NOT NULL  ,
	email                varchar(200)  NOT NULL  ,
	telf                 varchar(15)    
 ) engine=InnoDB;

CREATE INDEX idx_user_info ON ficon.user_info ( id_user );

CREATE TABLE ficon.activity ( 
	id                   bigint UNSIGNED NOT NULL  AUTO_INCREMENT,
	id_organizer         bigint UNSIGNED NOT NULL  ,
	name                 varchar(150)  NOT NULL  ,
	description          varchar(200)    ,
	num_participants     int  NOT NULL DEFAULT 1 ,
	type_activity        int UNSIGNED   ,
	kind                 int UNSIGNED   ,
	date_start           datetime    ,
	date_end             datetime  NOT NULL  ,
	reg_date_open        datetime    ,
	reg_date_close       datetime  NOT NULL  ,
	CONSTRAINT pk_activity PRIMARY KEY ( id )
 ) engine=InnoDB;

CREATE INDEX idx_activity ON ficon.activity ( id_organizer );

ALTER TABLE ficon.activity MODIFY id_organizer bigint UNSIGNED NOT NULL   COMMENT 'ID User';

ALTER TABLE ficon.activity MODIFY num_participants int  NOT NULL DEFAULT 1  COMMENT 'Plazas Vacantes';

ALTER TABLE ficon.activity MODIFY type_activity int UNSIGNED    COMMENT 'ENUMERADO según Evento';

ALTER TABLE ficon.activity MODIFY kind int UNSIGNED    COMMENT 'subtipo de actividad: ENUMERADO segun actividad';

ALTER TABLE ficon.activity MODIFY date_start datetime     COMMENT 'Fecha comienzo';

ALTER TABLE ficon.activity MODIFY date_end datetime  NOT NULL   COMMENT 'Fecha fin actividad';

ALTER TABLE ficon.activity MODIFY reg_date_open datetime     COMMENT 'Comienzo de Inscripcion';

CREATE TABLE ficon.mn_activity_user ( 
	id_activity          bigint UNSIGNED NOT NULL  ,
	id_user              bigint UNSIGNED NOT NULL  
 ) engine=InnoDB;

CREATE INDEX idx_mn_activity_user_act ON ficon.mn_activity_user ( id_activity );

CREATE INDEX idx_mn_activity_user_user ON ficon.mn_activity_user ( id_user );

ALTER TABLE ficon.mn_activity_user COMMENT 'Participantes de Actividad';

CREATE TABLE ficon.mn_event_activities ( 
	id_event             bigint UNSIGNED NOT NULL  ,
	id_activity          bigint UNSIGNED NOT NULL  
 ) engine=InnoDB;

CREATE INDEX idx_mn_event_activities_event ON ficon.mn_event_activities ( id_event );

CREATE INDEX idx_mn_event_activities_activity ON ficon.mn_event_activities ( id_activity );

CREATE TABLE ficon.mn_role_user ( 
	id_role              bigint UNSIGNED NOT NULL  ,
	id_user              bigint UNSIGNED NOT NULL  
 ) engine=InnoDB;

CREATE INDEX idx_mn_role_user_role ON ficon.mn_role_user ( id_role );

CREATE INDEX idx_mn_role_user_user ON ficon.mn_role_user ( id_user );

CREATE TABLE ficon.mn_role_user_case ( 
	id_user_case         bigint UNSIGNED NOT NULL  ,
	id_role              bigint UNSIGNED NOT NULL  
 );

CREATE INDEX idx_mn_role_user_case_user_case ON ficon.mn_role_user_case ( id_user_case );

CREATE INDEX idx_mn_role_user_case_role ON ficon.mn_role_user_case ( id_role );

CREATE TABLE ficon.news ( 
	id                   bigint UNSIGNED NOT NULL  AUTO_INCREMENT,
	id_user              bigint UNSIGNED NOT NULL  ,
	title                varchar(200)  NOT NULL  ,
	message              text  NOT NULL  ,
	date_created         datetime    ,
	date_publish         datetime  NOT NULL  ,
	url                  varchar(250)    ,
	hours_priority       int UNSIGNED  DEFAULT 1 ,
	CONSTRAINT pk_news PRIMARY KEY ( id )
 ) engine=InnoDB;

CREATE INDEX idx_news ON ficon.news ( id_user );

ALTER TABLE ficon.news MODIFY hours_priority int UNSIGNED  DEFAULT 1  COMMENT 'Horas durante las que es prioritaria';

CREATE TABLE ficon.registration ( 
	id_user              bigint UNSIGNED NOT NULL  ,
	id_event             bigint UNSIGNED   ,
	state                int UNSIGNED  DEFAULT 0 ,
	date_created         datetime    ,
	date_paid            datetime    ,
	paid                 bool   DEFAULT 0 
 ) engine=InnoDB;

CREATE INDEX idx_registration_event ON ficon.registration ( id_event );

CREATE INDEX idx_registration_user ON ficon.registration ( id_user );

ALTER TABLE ficon.registration COMMENT 'Registro de usuario al evento';

ALTER TABLE ficon.registration MODIFY state int UNSIGNED  DEFAULT 0  COMMENT 'Estado del registro del Usuario: ENUMERATE segun evento';

ALTER TABLE ficon.registration MODIFY date_created datetime     COMMENT 'Fecha creación del registro';

CREATE TABLE ficon.mn_event_news ( 
	id_event             bigint UNSIGNED NOT NULL  ,
	id_news              bigint UNSIGNED NOT NULL  
 ) engine=InnoDB;

CREATE INDEX idx_mn_event_news_event ON ficon.mn_event_news ( id_event );

CREATE INDEX idx_mn_event_news_news ON ficon.mn_event_news ( id_news );

ALTER TABLE ficon.activity ADD CONSTRAINT fk_activity_user FOREIGN KEY ( id_organizer ) REFERENCES ficon.user( id ) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE ficon.mn_activity_user ADD CONSTRAINT fk_mn_activity_user_activity FOREIGN KEY ( id_activity ) REFERENCES ficon.activity( id ) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE ficon.mn_activity_user ADD CONSTRAINT fk_mn_activity_user_user FOREIGN KEY ( id_user ) REFERENCES ficon.user( id ) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE ficon.mn_event_activities ADD CONSTRAINT fk_mn_event_activities_event FOREIGN KEY ( id_event ) REFERENCES ficon.event( id ) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE ficon.mn_event_activities ADD CONSTRAINT fk_mn_event_activities FOREIGN KEY ( id_activity ) REFERENCES ficon.activity( id ) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE ficon.mn_event_news ADD CONSTRAINT fk_mn_event_news_event FOREIGN KEY ( id_event ) REFERENCES ficon.event( id ) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE ficon.mn_event_news ADD CONSTRAINT fk_mn_event_news_news FOREIGN KEY ( id_news ) REFERENCES ficon.news( id ) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE ficon.mn_role_user ADD CONSTRAINT fk_mn_role_user_role FOREIGN KEY ( id_role ) REFERENCES ficon.role( id ) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE ficon.mn_role_user ADD CONSTRAINT fk_mn_role_user_user FOREIGN KEY ( id_user ) REFERENCES ficon.user( id ) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE ficon.mn_role_user_case ADD CONSTRAINT fk_mn_role_user_case_role FOREIGN KEY ( id_role ) REFERENCES ficon.role( id ) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE ficon.mn_role_user_case ADD CONSTRAINT fk_mn_role_user_case_user_case FOREIGN KEY ( id_user_case ) REFERENCES ficon.user_case( id ) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE ficon.news ADD CONSTRAINT fk_news_user FOREIGN KEY ( id_user ) REFERENCES ficon.user( id ) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE ficon.registration ADD CONSTRAINT fk_registration_event FOREIGN KEY ( id_event ) REFERENCES ficon.event( id ) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE ficon.registration ADD CONSTRAINT fk_registration_user FOREIGN KEY ( id_user ) REFERENCES ficon.user( id ) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE ficon.user_data ADD CONSTRAINT fk_user_data_user FOREIGN KEY ( id_user ) REFERENCES ficon.user( id ) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE ficon.user_info ADD CONSTRAINT fk_user_info_user FOREIGN KEY ( id_user ) REFERENCES ficon.user( id ) ON DELETE CASCADE ON UPDATE CASCADE;

