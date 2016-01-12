START TRANSACTION;
INSERT INTO Address VALUES (0, "email@user.is", "PutYourPasswordHere");
SELECT @AddressId := LAST_INSERT_ID();

-- How to add a New role to a user manually (as first action to have an functional admin user)
-- INSERT INTO Role_User (Role_User_Role_id, Role_User_User_id) VALUES ((SELECT Role_id FROM Role WHERE Role_name = 'RegistrationController'), (SELECT User_id FROM User WHERE User_login = 'admin'));
-- How to manually check user roles
-- SELECT User_login, Role_name  FROM Role_User ru INNER JOIN Role r ON r.Role_id = ru.Role_User_Role_id JOIN User u ON ru.Role_User_User_id = u.User_id  WHERE User_login = 'admin'

-- Use this to set Admin roles once created
-- INSERT INTO Role_User (Role_User_Role_id, Role_User_User_id) VALUES ((SELECT Role_id FROM Role WHERE Role_name = 'User'), (SELECT User_id FROM User WHERE User_login = 'admin'));
-- INSERT INTO Role_User (Role_User_Role_id, Role_User_User_id) VALUES ((SELECT Role_id FROM Role WHERE Role_name = 'EventController'), (SELECT User_id FROM User WHERE User_login = 'admin'));
-- INSERT INTO Role_User (Role_User_Role_id, Role_User_User_id) VALUES ((SELECT Role_id FROM Role WHERE Role_name = 'ActivityController'), (SELECT User_id FROM User WHERE User_login = 'admin'));
-- INSERT INTO Role_User (Role_User_Role_id, Role_User_User_id) VALUES ((SELECT Role_id FROM Role WHERE Role_name = 'RegistrationController'), (SELECT User_id FROM User WHERE User_login = 'admin'));
-- INSERT INTO Role_User (Role_User_Role_id, Role_User_User_id) VALUES ((SELECT Role_id FROM Role WHERE Role_name = 'NewsController'), (SELECT User_id FROM User WHERE User_login = 'admin'));
-- INSERT INTO Role_User (Role_User_Role_id, Role_User_User_id) VALUES ((SELECT Role_id FROM Role WHERE Role_name = 'SponsorController'), (SELECT User_id FROM User WHERE User_login = 'admin'));
-- INSERT INTO Role_User (Role_User_Role_id, Role_User_User_id) VALUES ((SELECT Role_id FROM Role WHERE Role_name = 'UserController'), (SELECT User_id FROM User WHERE User_login = 'admin'));

-- Roles básicos
INSERT INTO Role (Role_name) VALUES ('Anonymous'), ('Admin'), ('User');

-- Casos de uso
INSERT INTO UseCase (UseCase_name) VALUES ('login'), ('addUser'), ('closeSession'), ('closeAllUserSessions'), ('passwordRecover'), ('changeUserPassword'), ('getAllUsers'), ('changeUserData'), ('removeUser'),
                                          ('getEvent'),  ('getAllEvents'), ('createEvent'), ('removeEvent'), ('changeEventData'), ('addParticipantToEvent'), ('getEventRegistrationState'),
                                          ('getNewsItem'), ('getAllNewsItem'), ('addNews'), ('changeNewsData'), ('removeNews'),
                                          ('getActivity'), ('addActivity'), ('removeActivity'), ('changeActivityData'), ('getAllActivities'),
                                          ('getRegistration'), ('changeRegistrationState'), ('getRegistrationByEvent'), ('setPaid')
                                          ('addSponsor'), ('removeSponsor'), ('getSponsors');

-- Configuración de los Roles
INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "login" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getAllEvents" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "findEventByName" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getActivity" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getEventRules" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "eventIsOpen" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getAllActivities" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getActivitiesByEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getNewsItem" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getLastNews" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getLastNewsFromEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getSponsors" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getSponsorsByEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "passwordRecover" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getAllPublishedNewsItemFormEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getAllPublishedNewsItemFromEventTam" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "addUser" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getActivitiesByEventTAM" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );


INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "closeSession" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getEventRegistrationState" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getAllEvents" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "findEventByName" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getEventRules" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "eventIsOpen" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getActivity" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getAllActivities" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getActivitiesByEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getActivityParticipants" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getNewsItem" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getLastNews" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getLastNewsFromEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getSponsors" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getSponsorsByEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getAllPublishedNewsItemFormEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getAllPublishedNewsItemFromEventTam" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "sendRegistrationMail" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getActivitiesByEventTAM" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );


INSERT INTO Role VALUES ( 0, "EventController");

INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "createEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "EventController" ) );
INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "removeEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "EventController" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "EventController" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getEventRules" ), ( SELECT Role_id FROM Role WHERE Role_name =  "EventController" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "eventIsOpen" ), ( SELECT Role_id FROM Role WHERE Role_name =  "EventController" ) );
INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "changeEventData" ), ( SELECT Role_id FROM Role WHERE Role_name =  "EventController" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getAllEvents" ), ( SELECT Role_id FROM Role WHERE Role_name =  "EventController" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "eventNumParticipantsChanged" ), ( SELECT Role_id FROM Role WHERE Role_name =  "EventController" ) );


INSERT INTO Role VALUES ( 0, "ActivityController");

INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "addActivity" ), ( SELECT Role_id FROM Role WHERE Role_name =  "ActivityController" ) );
INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "removeActivity" ), ( SELECT Role_id FROM Role WHERE Role_name =  "ActivityController" ) );
INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "changeActivityData" ), ( SELECT Role_id FROM Role WHERE Role_name =  "ActivityController" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getActivity" ), ( SELECT Role_id FROM Role WHERE Role_name =  "ActivityController" ) );
INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getAllActivities" ), ( SELECT Role_id FROM Role WHERE Role_name =  "ActivityController" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getActivitiesByEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "ActivityController" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "addParticipantToActivity" ), ( SELECT Role_id FROM Role WHERE Role_name =  "ActivityController" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "removeParticipantFromActivity" ), ( SELECT Role_id FROM Role WHERE Role_name =  "ActivityController" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getActivityParticipants" ), ( SELECT Role_id FROM Role WHERE Role_name =  "ActivityController" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getActivitiesByEventTAM" ), ( SELECT Role_id FROM Role WHERE Role_name =  "ActivityController" ) );

INSERT INTO Role VALUES ( 0, "RegistrationController");

-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "addParticipantToEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "RegistrationController" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "removeParticipantFromEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "RegistrationController" ) );
INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "setPaid" ), ( SELECT Role_id FROM Role WHERE Role_name =  "RegistrationController" ) );
INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getRegistration" ), ( SELECT Role_id FROM Role WHERE Role_name =  "RegistrationController" ) );
INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "changeRegistrationState" ), ( SELECT Role_id FROM Role WHERE Role_name =  "RegistrationController" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "eventNumParticipantsChanged" ), ( SELECT Role_id FROM Role WHERE Role_name =  "RegistrationController" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getShirtSizes" ), ( SELECT Role_id FROM Role WHERE Role_name =  "RegistrationController" ) );
INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getRegistrationByEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "RegistrationController" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getRegistrationByEventTAM" ), ( SELECT Role_id FROM Role WHERE Role_name =  "RegistrationController" ) );

-- INSERT INTO Role VALUES ( 0, "MailController");
-- 
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getAllAddress" ), ( SELECT Role_id FROM Role WHERE Role_name =  "MailController" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "addAddress" ), ( SELECT Role_id FROM Role WHERE Role_name =  "MailController" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getAddress" ), ( SELECT Role_id FROM Role WHERE Role_name =  "MailController" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "modifyAddress" ), ( SELECT Role_id FROM Role WHERE Role_name =  "MailController" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "deleteAddress" ), ( SELECT Role_id FROM Role WHERE Role_name =  "MailController" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "createEmailTemplate" ), ( SELECT Role_id FROM Role WHERE Role_name =  "MailController" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "removeEmailTemplate" ), ( SELECT Role_id FROM Role WHERE Role_name =  "MailController" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "changeEmailTemplate" ), ( SELECT Role_id FROM Role WHERE Role_name =  "MailController" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getAllEmailTemplate" ), ( SELECT Role_id FROM Role WHERE Role_name =  "MailController" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "findEmailTemplateByName" ), ( SELECT Role_id FROM Role WHERE Role_name =  "MailController" ) );


INSERT INTO Role VALUES ( 0, "NewsController");

INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "addNews" ), ( SELECT Role_id FROM Role WHERE Role_name =  "NewsController" ) );
INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "changeNewsData" ), ( SELECT Role_id FROM Role WHERE Role_name =  "NewsController" ) );
INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getNewsItem" ), ( SELECT Role_id FROM Role WHERE Role_name =  "NewsController" ) );
INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getAllNewsItem" ), ( SELECT Role_id FROM Role WHERE Role_name =  "NewsController" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getAllNewsItemFormEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "NewsController" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getAllPublishedNewsItemFormEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "NewsController" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getAllPublishedNewsItemFromEventTam" ), ( SELECT Role_id FROM Role WHERE Role_name =  "NewsController" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getAllNewsItemFromEventTam" ), ( SELECT Role_id FROM Role WHERE Role_name =  "NewsController" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getLastNews" ), ( SELECT Role_id FROM Role WHERE Role_name =  "NewsController" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getLastNewsFromEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "NewsController" ) );
INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "removeNews" ), ( SELECT Role_id FROM Role WHERE Role_name =  "NewsController" ) );


INSERT INTO Role VALUES ( 0, "SponsorController");

INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "addSponsor" ), ( SELECT Role_id FROM Role WHERE Role_name =  "SponsorController" ) );
INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "removeSponsor" ), ( SELECT Role_id FROM Role WHERE Role_name =  "SponsorController" ) );
INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getSponsors" ), ( SELECT Role_id FROM Role WHERE Role_name =  "SponsorController" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getSponsorsTAM" ), ( SELECT Role_id FROM Role WHERE Role_name =  "SponsorController" ) );


INSERT INTO Role VALUES ( 0, "UserController");

INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "closeAllUserSessions" ), ( SELECT Role_id FROM Role WHERE Role_name =  "UserController" ) );
INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "changeUserData" ), ( SELECT Role_id FROM Role WHERE Role_name =  "UserController" ) );
INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "changeUserPassword" ), ( SELECT Role_id FROM Role WHERE Role_name =  "UserController" ) );
INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getAllUsers" ), ( SELECT Role_id FROM Role WHERE Role_name =  "UserController" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getAllUsersTAM" ), ( SELECT Role_id FROM Role WHERE Role_name =  "UserController" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "findUsersByName" ), ( SELECT Role_id FROM Role WHERE Role_name =  "UserController" ) );
INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "removeUser" ), ( SELECT Role_id FROM Role WHERE Role_name =  "UserController" ) );


-- INSERT INTO Role VALUES ( 0, "BlackListController");

-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "addUserToBlackList" ), ( SELECT Role_id FROM Role WHERE Role_name =  "BlackListController" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "removeUserFromBlackList" ), ( SELECT Role_id FROM Role WHERE Role_name =  "BlackListController" ) );
-- INSERT INTO Role_UseCase VALUES ( 0, ( SELECT UseCase_id FROM UseCase WHERE UseCase_name =  "getBlacklistedUsers" ), ( SELECT Role_id FROM Role WHERE Role_name =  "BlackListController" ) );




INSERT INTO EmailTemplate VALUES ( 0, "passwordRecover", @AddressId, "", "", "Recuperación contraseña plataforma Freak's Party webs",
 "La nueva contraseña para la cuenta #loginusuario es #nuevapas esta contraseña solo será válida durante #tiemporestante minutos, recuerda cambiarla.");
 
 
INSERT INTO EmailTemplate VALUES ( 0, "OnQueueTemplate", @AddressId, "", "", "Estado del registro FicOnLan",
 "El estado actual de tu registro es En cola.

Tu puesto en la cola de espera es #plazaencola.");
 
 
 INSERT INTO EmailTemplate VALUES ( 0, "OutstandingTemplate", @AddressId, "", "", "Estado del registro FicOnLan",
 "Felicidades #nombreusuario , has conseguido la plaza número #plazaenevento en #nombreevento . 

El estado actual de tu registro es Pendiente de pago, así que para confirmar tu inscripción deberás realizar un ingreso de #precio€  poniendo como concepto tu DNI en la cuenta Nº CUENTA: 0049 6795 53 2395124147 del banco Santander con titular ASOCIACION CULTURAL FREAKS PARTY, en un plazo de tres días. Si pasado ese tiempo no hemos recibido ese ingreso la organización entenderá que renuncias a a tu plaza y tu registro será borrado.

Recordamos que para acceder al evento hay que ser mayor de 16 años y que los menores de 18 años tienen que traer una autorización firmada por sus padres o tutores a la entrada, o no se les dejará entrar.

La autorización se puede descargar de este enlace http://ficonlan.es/download/autorizacion.pdf");


 INSERT INTO EmailTemplate VALUES ( 0, "FromQueueToOutstanding", @AddressId, "", "", "Estado del registro FicOnLan",
 "Felicidades #nombreusuario , has conseguido la plaza número #plazaenevento en #nombreevento . 

El estado actual de tu registro es Pendiente de pago, así que para confirmar tu inscripción deberás realizar un ingreso de #precio€  poniendo como concepto tu DNI en la cuenta Nº CUENTA: 0049 6795 53 2395124147 del banco Santander con titular ASOCIACION CULTURAL FREAKS PARTY, en un plazo de tres días. Si pasado ese tiempo no hemos recibido ese ingreso la organización entenderá que renuncias a a tu plaza y tu registro será borrado.

Recordamos que para acceder al evento hay que ser mayor de 16 años y que los menores de 18 años tienen que traer una autorización firmada por sus padres o tutores a la entrada, o no se les dejará entrar.

La autorización se puede descargar de este enlace http://ficonlan.es/download/autorizacion.pdf");


INSERT INTO EmailTemplate VALUES ( 0, "SetPaidTemplate", @AddressId, "", "", "Estado del registro FicOnLan",
 "Felicidades #nombreusuario, el pago de la entrada de #nombreevento ha sido confirmado.

Recordamos que para acceder al evento hay que ser mayor de 16 años y que los menores de 18 años tienen que traer una autorización firmada por sus padres o tutores a la entrada, o no se les dejará entrar.

La autorización se puede descargar de este enlace http://ficonlan.es/download/autorizacion.pdf");

 INSERT INTO EmailTemplate VALUES ( 0, "OutOfDateTemplate", @AddressId, "", "", "Estado del registro FicOnLan",
 "No has realizado el pago de la entrada a tiempo, por ello tu estado ha pasado de Pendiente de pago a No registrado. 
 Si tienes algún problema ponte en contacto con  nosotros a través de la web.");
 
 
 
 
 
 INSERT INTO Event (Event_id,Event_name,Event_description,Event_num_participants,Event_minimunAge,Event_date_start,Event_date_end,Event_reg_date_open,Event_reg_date_close,Event_rules,
 Event_setPaidTemplate_id,Event_onQueueTemplate_id,Event_outstandingTemplate_id,Event_outOfDateTemplate_id,Event_fromQueueToOutstanding_id,Event_price)
  VALUES (0, "Fic OnLan VI", "<div style=\"text-align: center;\"><br/></div><h2 style=\"text-align: center;\">La FicOnLan VI es un evento informático que se celebra durante los carnavales 2015 en la Facultad de Informática de A Coruña.&#10;<br/>La fecha de apertura es el día 13 de febrero a las 20:00 y la de clausura el 17 de febrero a las 17:00.</h2>", 142, 16, '2015-02-13 20:00:00', '2015-02-17 17:00:00', '2015-01-26 20:00:00', '2015-02-12 00:00:00',
"<p>La adquisición de la entrada al evento FIC OnLan 2015 se considerará como un acto de aprobación de la normas descritas en este documento. En caso de que usted no este conforme a alguno de los apartados y/o normas descritas en este documento, tiene a su disposición el correo electrónico de contacto. El pago de dicha entrada será considerada como una donación a la asociación para la realización del evento y no se admitirá la devolución de dicha donación a no ser que no se realice el evento, por causa de fuerza mayor.</p><br/><p>La asociación Freak's Party en calidad de organizadora y promotora del evento FIC OnLan 2015; se reserva el derecho de modificar total o parcialmente las normas descritas en este documento. Así mismo; la asociación se compromete a comunicar cualquier cambio en las presentes normas a todos los usuarios del evento, que se hayan registrado con fecha anterior a la fecha de modificación de la[s] norma[s].</p><h3>Definición de Roles/Conceptos:</h3><ol><li> <b>Organización:</b> Todo el personal que se encarga de la organización, administración y vigilancia del evento.</li><li> <b>Evento:</b> En referencia a FIC OnLan 2013; y todos sus asociados: FIC OnDev, FIC OnNet, etc.</li><li> <b>Acreditación:</b> Documento que identifica a todos y cada uno de los implicados en el evento, ya sean Usuarios o Organización.</li><li> <b>Usuario/Participante:</b> Toda persona registrada y participante del evento FIC OnLan 2013, que no se puede considerar parte de la Organización.</li><li> <b>Recinto:</b> Lugar en donde se efectuará el evento FIC OnLan 2013.</li><li> <b>Equipo:</b> Ordenador personal de los Usuarios u Organización.</li><li> <b>Troll:</b> Cualquier persona física considerada molesta o “non-grata” por la asociación. La cual, puede perder el derecho a participar en el evento.</li></ol><h3>Lista de Normas:</h3><br/><p><b>0.- Datos Personales:</b> La asociación Freak’s Party y toda la organización de FIC OnLan, y todos sus eventos asociados; se compromete a la no divulgación de datos personales de todos los participantes y miembros de la asociación a ninguna entidad, pública o privada; sin permiso expreso de cada personal individual y física que se encuentre en las bases de datos pertinentes, salvo orden judicial. Como así estipula la L.O.P.D., y se compromete también a cumplir toda norma sobre privacidad de datos vigente en estos momentos en España. El uso de dichos datos será única y exclusivamente para controlar la entrada al evento y solo los miembros de Freak's Party encargados de esto tendrán acceso a ellos.</p><br/> <p><b>1.- Admisión:</b> Freak's Party se reserva el derecho de admisión de los participantes al evento y notifica que para podrán participar todas aquellas personas físicas que justifiquen documentalmente; mediante DNI y/o pasaporte, ser mayor de edad o en caso de ser menor de edad, adjuntar a su solicitud una autorización firmada por su tutor legal. Aunque siempre deberá tener una edad igual o superior a 16 años en el momento del inicio del evento.<br/>De igual forma, la organización se reserva el derecho a revocar la admisión a un participante que de forma deliberada y/o accidental incurra en un delito de falsedad documental.</p><br/><p><b>2.- Plazas:</b> Las plazas quedarán adjudicadas por orden de entrada en la base de datos del evento; por lo cual, todo aquel solicitante de pre-inscripción y posterior inscripción al evento autoriza a Freak's Party a disponer de sus datos personales; obligatorios en el registro de pre-inscripción como estime oportuno para el cumplimiento de forma normal del evento. No serán admitidas a tramite de plaza todas aquellas inscripciones que se efectúen fuera de plazo o una vez llenado el cupo del evento.</p><br/><p><b>3.-Identificación de los Participantes:</b> Todos los participantes y todo personal que la organización estime oportuno deberá llevar en todo momento y de forma visible la documentación que lo identifique como Participante, Colaborador o Cualquier Rol que desempeñe en el evento. De ser necesario y conforme a estas normas, la organización se reserva el derecho de no permitir el acceso y/o expulsión a toda persona física que no se identifique documentalmente con alguno de los Roles del evento.<br/>En caso de perdida o deterioro de la acreditación facilitada por la organización, esta se reserva el derecho del cobro de una cantidad simbólica a modo de multa y/o advertencia, por no conservar dicha acreditación de forma natural.<br/>Se comunica que no está permitido el trasvase o préstamo de la identificación de cualquier participante, por considerarse un documento personal e intransferible.</p><br/><p><b>4.- Convivencia:</b> Por motivos de una convivencia agradable para la mayoría de los asistentes al evento. Podrá prohibirse el acceso, inscripción a toda persona considerada “non grata” por parte de la asociación; así como a cualquier persona que haya sido expulsada del evento con anterioridad.<br/>Se prohíbe el uso de aparatos de música de cualquier tipo; así como el escándalo publico en cualquiera de los lugares designados como dormitorios.<br/>El recinto está cedido para el evento por la Universidad de la Coruña, pese a esto sigue considerándose un recinto escolar; por lo cual no está permitido el consumo de alcohol en todo el recinto, ni tampoco se permite fumar dentro del mismo. De igual forma, no está permitido el consumo, tenencia o venta de cualquier sustancia considerada ilegal.<br/>No está permitida la agresión física o verbal por parte de los participantes a ninguna persona física dentro o en el contorno del recinto. La organización se reserva el derecho de llamar a las autoridades en caso de considerarlo necesario.</p><br/><p><b>5.- Participación en los Concursos:</b> Los concursos poseen normas especificas para los participantes, y todo participante que desee inscribirse a alguno de los concursos y/o conferencias deberá acogerse a las normas particulares. En caso de que alguna de las normas de esos concursos contradiga otra norma estipulada en este documento, los participantes deberán acogerse a la norma más especifica.</p><br/><p><b>6.- Equipo:</b> Todo participante en el evento deberá disponer y traer por sus propios medios del equipo informático necesario para el evento; equipo que será especificado en la pagina web del evento de forma clara. La Organización no se hace responsable de la estravío y/o sustracción de cualquier tipo de equipo informático; por lo que se recomienda que los participantes vigilen de forma personal su propio material.</p><br/><p><b>7.- Electricidad:</b> Queda terminantemente prohibido la manipulación de cualquier tipo de cable y/o aparato de la organización. De igual forma, queda bajo la autorización de la organización el privilegio de conectar cualquier electrodoméstico de cualquier tipo de potencia y/o uso, que no sea considerado equipo informático.</p><br/><p><b>8.- Entrada al recinto:</b> La entrada al recinto será totalmente libre durante la duración de todo el evento a todas aquellas personas que lleven su acreditación de forma visible y esta sea totalmente legible. Salvo orden expresa de la organización y/o causas de fuerza mayor.</p><br/><p><b>9.- Uso adecuado:</b> No está permitido el uso de altavoces por parte de los participantes del evento; se ruega el uso de auriculares. La organización pone a disposición de los participantes ciertos enseres como pueden ser baños o zonas aclimatadas para dormir, se ruega un uso responsable de las mismas.<br/>La organización no se hace responsable del uso que los participantes hacen de la red interna y/o externa; pero recuerda que es delito la distribución de cualquier material con derecho de copyright sin la autorización expresa y escrita de su autor. Y que cada participante es el único responsable del uso que se le de a su equipo informático, tal y como se manifiesta en la Actual Ley de Propiedad Intelectual vigente en España.<br/>La organización considerará como troll a cualquier persona física que de forma deliberada o accidental provoque fallos en la red del evento; así como cualquier acción considerada dentro de los términos de “hacking”.</p><br/><p><b>10.- Normas de Seguridad:</b> Está terminantemente prohibido el consumo de cualquier tipo de bebida en el recinto de los equipos informáticos que este envasada en recipiente de cristal. El derrame intencionado y/o accidental de cualquier tipo de liquido sobre los equipos informáticos de la organización y/o cualquiera de los participantes del evento conllevará; bajo juicio de la organización, la expulsión del evento, así como el abono parcial o total del equipo deteriorado.<br/>No está permitido entrar al recinto con cualquier elemento cortante; o que la organización considere peligroso para la realización normal del evento, que no este aprobado por la organización.<br/>La organización pondrá a disposición de los participantes contenedores y/o lugares para el deposito de cualquier tipo de material desechable que los participantes generen durante o en la conclusión del evento; se ruega hacer un uso responsable de los mismos, con forme a las normas de salubridad que rigen en los centros Escolares.<br/>Cualquier participante que este con un tratamiento medico y/o padezca alguna enfermedad de tipo crónico o contagiosa deberá comunicárselo expresamente a la organización. Realizando la misma todos los ajustes que estime oportunos según el caso.</p><br/><p><b>11.- Inicio y Fin de Actividad:</b> La organización pondrá a disposición de todos los participantes; así como cualquier persona física que lo solicite, el horario de inicio y clausura del evento. Ninguna persona ajena a la organización podrá incorporarse al evento antes de la fecha y hora indicadas como Inicio; así mismo, tampoco podrá irse del evento de forma posterior a la fecha y hora indicadas como clausura.</p>",
  (SELECT EmailTemplate_id FROM EmailTemplate WHERE EmailTemplate_name =  "SetPaidTemplate"),
  (SELECT EmailTemplate_id FROM EmailTemplate WHERE EmailTemplate_name =  "OnQueueTemplate"),
  (SELECT EmailTemplate_id FROM EmailTemplate WHERE EmailTemplate_name =  "OutstandingTemplate"),
  (SELECT EmailTemplate_id FROM EmailTemplate WHERE EmailTemplate_name =  "OutOfDateTemplate"),
  (SELECT EmailTemplate_id FROM EmailTemplate WHERE EmailTemplate_name =  "FromQueueToOutstanding"),25);
  
  
  
  
  
  UPDATE EmailTemplate 
SET EmailTemplate_body =  "Felicidades #nombreusuario , has conseguido la plaza número #plazaenevento en #nombreevento . 

El estado actual de tu registro es Pendiente de pago, así que para confirmar tu inscripción deberás realizar un ingreso de #precio€  poniendo como concepto tu DNI en la cuenta Nº CUENTA: 0049 6795 53 2395124147 del banco Santander con titular ASOCIACION CULTURAL FREAKS PARTY, en un plazo de tres días. Si pasado ese tiempo no hemos recibido ese ingreso la organización entenderá que renuncias a a tu plaza y tu registro será borrado.

Recordamos que para acceder al evento hay que ser mayor de 16 años y que los menores de 18 años tienen que traer una autorización firmada por sus padres o tutores a la entrada, o no se les dejará entrar.

La autorización se puede descargar de este enlace http://ficonlan.es/download/autorizacion.pdf"
WHERE EmailTemplate_name = "OutstandingTemplate";



UPDATE EmailTemplate 
SET EmailTemplate_body =  "Felicidades #nombreusuario , has conseguido la plaza número #plazaenevento en #nombreevento . 

El estado actual de tu registro es Pendiente de pago, así que para confirmar tu inscripción deberás realizar un ingreso de #precio€  poniendo como concepto tu DNI en la cuenta Nº CUENTA: 0049 6795 53 2395124147 del banco Santander con titular ASOCIACION CULTURAL FREAKS PARTY, en un plazo de tres días. Si pasado ese tiempo no hemos recibido ese ingreso la organización entenderá que renuncias a a tu plaza y tu registro será borrado.

Recordamos que para acceder al evento hay que ser mayor de 16 años y que los menores de 18 años tienen que traer una autorización firmada por sus padres o tutores a la entrada, o no se les dejará entrar.

La autorización se puede descargar de este enlace http://ficonlan.es/download/autorizacion.pdf"
WHERE EmailTemplate_name = "FromQueueToOutstanding";


UPDATE EmailTemplate 
SET EmailTemplate_body =  "Felicidades #nombreusuario, el pago de la entrada de #nombreevento ha sido confirmado.

Recordamos que para acceder al evento hay que ser mayor de 16 años y que los menores de 18 años tienen que traer una autorización firmada por sus padres o tutores a la entrada, o no se les dejará entrar.

La autorización se puede descargar de este enlace http://ficonlan.es/download/autorizacion.pdf"
WHERE EmailTemplate_name = "SetPaidTemplate";


UPDATE Event
SET Event_price = 25
WHERE Event_name = "Fic OnLan VI";
COMMIT;