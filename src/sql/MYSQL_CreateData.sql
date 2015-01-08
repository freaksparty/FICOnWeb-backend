-- Configuración de los Roles
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getAllEvents" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "findEventByName" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getActivity" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getEventRules" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "eventIsOpen" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getAllActivities" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getActivitiesByEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getNewsItem" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getLastNews" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getLastNewsFromEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getSponsors" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getSponsorsByEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "passwordRecover" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getAllPublishedNewsItemFormEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getAllPublishedNewsItemFromEventTam" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "addUser" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "passwordRecover" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );

INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "closeSession" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getAllEvents" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "findEventByName" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getEventRules" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "eventIsOpen" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getActivity" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getAllActivities" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getActivitiesByEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getActivityParticipants" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getNewsItem" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getLastNews" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getLastNewsFromEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getSponsors" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getSponsorsByEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getAllPublishedNewsItemFormEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getAllPublishedNewsItemFromEventTam" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "sendRegistrationMail" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );

INSERT INTO Role VALUES ( 0, "EventController");

INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "createEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "EventController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "removeEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "EventController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "EventController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getEventRules" ), ( SELECT Role_id FROM Role WHERE Role_name =  "EventController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "eventIsOpen" ), ( SELECT Role_id FROM Role WHERE Role_name =  "EventController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "changeEventData" ), ( SELECT Role_id FROM Role WHERE Role_name =  "EventController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getAllEvents" ), ( SELECT Role_id FROM Role WHERE Role_name =  "EventController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "eventNumParticipantsChanged" ), ( SELECT Role_id FROM Role WHERE Role_name =  "EventController" ) );


INSERT INTO Role VALUES ( 0, "ActivityController");

INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "addActivity" ), ( SELECT Role_id FROM Role WHERE Role_name =  "ActivityController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "removeActivity" ), ( SELECT Role_id FROM Role WHERE Role_name =  "ActivityController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "changeActivityData" ), ( SELECT Role_id FROM Role WHERE Role_name =  "ActivityController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getActivity" ), ( SELECT Role_id FROM Role WHERE Role_name =  "ActivityController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getAllActivities" ), ( SELECT Role_id FROM Role WHERE Role_name =  "ActivityController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getActivitiesByEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "ActivityController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "addParticipantToActivity" ), ( SELECT Role_id FROM Role WHERE Role_name =  "ActivityController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "removeParticipantFromActivity" ), ( SELECT Role_id FROM Role WHERE Role_name =  "ActivityController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getActivityParticipants" ), ( SELECT Role_id FROM Role WHERE Role_name =  "ActivityController" ) );


INSERT INTO Role VALUES ( 0, "RegistrationController");

INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "addParticipantToEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "RegistrationController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "removeParticipantFromEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "RegistrationController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "setPaid" ), ( SELECT Role_id FROM Role WHERE Role_name =  "RegistrationController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getRegistration" ), ( SELECT Role_id FROM Role WHERE Role_name =  "RegistrationController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "changeRegistrationState" ), ( SELECT Role_id FROM Role WHERE Role_name =  "RegistrationController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "eventNumParticipantsChanged" ), ( SELECT Role_id FROM Role WHERE Role_name =  "RegistrationController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getShirtSizes" ), ( SELECT Role_id FROM Role WHERE Role_name =  "RegistrationController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getRegistrationByEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "RegistrationController" ) );

INSERT INTO Role VALUES ( 0, "MailController");

INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getAllAdress" ), ( SELECT Role_id FROM Role WHERE Role_name =  "MailController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "addAdress" ), ( SELECT Role_id FROM Role WHERE Role_name =  "MailController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getAdress" ), ( SELECT Role_id FROM Role WHERE Role_name =  "MailController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "modifyAdress" ), ( SELECT Role_id FROM Role WHERE Role_name =  "MailController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "deleteAdress" ), ( SELECT Role_id FROM Role WHERE Role_name =  "MailController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "createEmailTemplate" ), ( SELECT Role_id FROM Role WHERE Role_name =  "MailController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "removeEmailTemplate" ), ( SELECT Role_id FROM Role WHERE Role_name =  "MailController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "changeEmailTemplate" ), ( SELECT Role_id FROM Role WHERE Role_name =  "MailController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getAllEmailTemplate" ), ( SELECT Role_id FROM Role WHERE Role_name =  "MailController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "findEmailTemplateByName" ), ( SELECT Role_id FROM Role WHERE Role_name =  "MailController" ) );


INSERT INTO Role VALUES ( 0, "NewsController");

INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "addNews" ), ( SELECT Role_id FROM Role WHERE Role_name =  "NewsController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "changeNewsData" ), ( SELECT Role_id FROM Role WHERE Role_name =  "NewsController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getNewsItem" ), ( SELECT Role_id FROM Role WHERE Role_name =  "NewsController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getAllNewsItem" ), ( SELECT Role_id FROM Role WHERE Role_name =  "NewsController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getAllNewsItemFormEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "NewsController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getAllPublishedNewsItemFormEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "NewsController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getAllPublishedNewsItemFromEventTam" ), ( SELECT Role_id FROM Role WHERE Role_name =  "NewsController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getAllNewsItemFromEventTam" ), ( SELECT Role_id FROM Role WHERE Role_name =  "NewsController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getLastNews" ), ( SELECT Role_id FROM Role WHERE Role_name =  "NewsController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getLastNewsFromEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "NewsController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "removeNews" ), ( SELECT Role_id FROM Role WHERE Role_name =  "NewsController" ) );


INSERT INTO Role VALUES ( 0, "SponsorController");

INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "addSponsor" ), ( SELECT Role_id FROM Role WHERE Role_name =  "SponsorController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "removeSponsor" ), ( SELECT Role_id FROM Role WHERE Role_name =  "SponsorController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getSponsors" ), ( SELECT Role_id FROM Role WHERE Role_name =  "SponsorController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getSponsorsTAM" ), ( SELECT Role_id FROM Role WHERE Role_name =  "SponsorController" ) );


INSERT INTO Role VALUES ( 0, "UserController");

INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "closeAllUserSessions" ), ( SELECT Role_id FROM Role WHERE Role_name =  "UserController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "changeUserData" ), ( SELECT Role_id FROM Role WHERE Role_name =  "UserController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "changeUserPassword" ), ( SELECT Role_id FROM Role WHERE Role_name =  "UserController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getAllUsers" ), ( SELECT Role_id FROM Role WHERE Role_name =  "UserController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getAllUsersTAM" ), ( SELECT Role_id FROM Role WHERE Role_name =  "UserController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "findUsersByName" ), ( SELECT Role_id FROM Role WHERE Role_name =  "UserController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "removeUser" ), ( SELECT Role_id FROM Role WHERE Role_name =  "UserController" ) );


INSERT INTO Role VALUES ( 0, "BlackListController");

INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "addUserToBlackList" ), ( SELECT Role_id FROM Role WHERE Role_name =  "BlackListController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "removeUserFromBlackList" ), ( SELECT Role_id FROM Role WHERE Role_name =  "BlackListController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getBlacklistedUsers" ), ( SELECT Role_id FROM Role WHERE Role_name =  "BlackListController" ) );





INSERT INTO Adress VALUES (0, "no-responder@freaksparty.org", "e3MCq5>P2");

INSERT INTO EmailTemplate VALUES ( 0, "passwordRecover", ( SELECT Adress_id FROM Adress WHERE Adress_user =  "no-responder@freaksparty.org" ), "", "", "Recuperación contraseña plataforma Freak's Party webs",
 "La nueva contraseña para la cuenta #loginusuario es #nuevapas esta contraseña solo será válida durante #tiemporestante minutos, recuerda cambiarla.");
 
 
 INSERT INTO EmailTemplate VALUES ( 0, "OnQueueTemplate", ( SELECT Adress_id FROM Adress WHERE Adress_user =  "no-responder@freaksparty.org" ), "", "", "Estado del registro FicOnLan",
 "El estado actual de tu registro es En cola.

Tu puesto en la cola de espera es #plazaencola.");
 
 
 INSERT INTO EmailTemplate VALUES ( 0, "OutstandingTemplate", ( SELECT Adress_id FROM Adress WHERE Adress_user =  "no-responder@freaksparty.org" ), "", "", "Estado del registro FicOnLan",
 "Felicidades #nombreusuario , has conseguido la plaza número #plazaenevento en #nombreevento . 

El estado actual de tu registro es Pendiente de pago, así que para confirmar tu inscripción deberás realizar un ingreso de #precio€  poniendo como concepto tu DNI en la cuenta @CUENTA del banco Santander en un plazo de tres días. Si pasado ese tiempo no hemos recibido ese ingreso la organización entenderá que renuncias a a tu plaza y tu registro será borrado.

Recordamos que para acceder al evento hay que ser mayor de 16 años y que los menores de 18 años tienen que traer una autorización firmada por sus padres o tutores a la entrada, o no se les dejará entrar.

La autorización se puede descargar de este enlace @ENLACE");


 INSERT INTO EmailTemplate VALUES ( 0, "FromQueueToOutstanding", ( SELECT Adress_id FROM Adress WHERE Adress_user =  "no-responder@freaksparty.org" ), "", "", "Estado del registro FicOnLan",
 "Felicidades #nombreusuario , has conseguido la plaza número #plazaenevento en #nombreevento . 

El estado actual de tu registro es Pendiente de pago, así que para confirmar tu inscripción deberás realizar un ingreso de #precio€  poniendo como concepto tu DNI en la cuenta @CUENTA del banco Santander en un plazo de tres días. Si pasado ese tiempo no hemos recibido ese ingreso la organización entenderá que renuncias a a tu plaza y tu registro será borrado.

Recordamos que para acceder al evento hay que ser mayor de 16 años y que los menores de 18 años tienen que traer una autorización firmada por sus padres o tutores a la entrada, o no se les dejará entrar.

La autorización se puede descargar de este enlace @ENLACE");


INSERT INTO EmailTemplate VALUES ( 0, "SetPaidTemplate", ( SELECT Adress_id FROM Adress WHERE Adress_user =  "no-responder@freaksparty.org" ), "", "", "Estado del registro FicOnLan",
 "Felicidades #nombreusuario, el pago de la entrada de #nombreevento ha sido confirmado.

Recordamos que para acceder al evento hay que ser mayor de 16 años y que los menores de 18 años tienen que traer una autorización firmada por sus padres o tutores a la entrada, o no se les dejará entrar.

La autorización se puede descargar de este enlace @ENLACE");

 INSERT INTO EmailTemplate VALUES ( 0, "OutOfDateTemplate", ( SELECT Adress_id FROM Adress WHERE Adress_user =  "no-responder@freaksparty.org" ), "", "", "Estado del registro FicOnLan",
 "No has realizado el pago de la entrada a tiempo, por ello tu estado ha pasado de Pendiente de pago a No registrado. 
 Si tienes algún problema ponte en contacto con  nosotros a través de la web.");
 
 
 
 
 
 INSERT INTO Event (Event_id,Event_name,Event_description,Event_num_participants,Event_minimunAge,Event_date_start,Event_date_end,Event_reg_date_open,Event_reg_date_close,Event_rules,
 Event_setPaidTemplate_id,Event_onQueueTemplate_id,Event_outstandingTemplate_id,Event_outOfDateTemplate_id,Event_fromQueueToOutstanding_id)
  VALUES (0, "Fic OnLan VI", "La FicOnLan VI es un evento informático que se celebra durante los carnavales 2015 en la Facultad de Informática de A Coruña
La fecha de apertura es el día 13 de febrero a las 20:00 y la de clausura el 17 de febrero a las 17:00", 142, 16, '2015-02-13 20:00:00', '2015-02-17 17:00:00', '2015-01-26 20:00:00', '2015-02-12 00:00:00',
"REGALS REGLAS",
  (SELECT EmailTemplate_id FROM EmailTemplate WHERE EmailTemplate_name =  "SetPaidTemplate"),
  (SELECT EmailTemplate_id FROM EmailTemplate WHERE EmailTemplate_name =  "OnQueueTemplate"),
  (SELECT EmailTemplate_id FROM EmailTemplate WHERE EmailTemplate_name =  "OutstandingTemplate"),
  (SELECT EmailTemplate_id FROM EmailTemplate WHERE EmailTemplate_name =  "OutOfDateTemplate"),
  (SELECT EmailTemplate_id FROM EmailTemplate WHERE EmailTemplate_name =  "FromQueueToOutstanding"));