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
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getAllUserEmails" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getUserLastEventEmail" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "sendUserMail" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getAllPublishedNewsItemFormEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getAllPublishedNewsItemFromEventTam" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );

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


INSERT INTO Role VALUES ( 0, "MailController");

INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getAllAdress" ), ( SELECT Role_id FROM Role WHERE Role_name =  "MailController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "addAdress" ), ( SELECT Role_id FROM Role WHERE Role_name =  "MailController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getAdress" ), ( SELECT Role_id FROM Role WHERE Role_name =  "MailController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "modifyAdress" ), ( SELECT Role_id FROM Role WHERE Role_name =  "MailController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "deleteAdress" ), ( SELECT Role_id FROM Role WHERE Role_name =  "MailController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getAllMails" ), ( SELECT Role_id FROM Role WHERE Role_name =  "MailController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getConfirmedMails" ), ( SELECT Role_id FROM Role WHERE Role_name =  "MailController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getNoConfirmedMails" ), ( SELECT Role_id FROM Role WHERE Role_name =  "MailController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getEmail" ), ( SELECT Role_id FROM Role WHERE Role_name =  "MailController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "addEmail" ), ( SELECT Role_id FROM Role WHERE Role_name =  "MailController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "modifyEmail" ), ( SELECT Role_id FROM Role WHERE Role_name =  "MailController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "deleteEmail" ), ( SELECT Role_id FROM Role WHERE Role_name =  "MailController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "sendEmail" ), ( SELECT Role_id FROM Role WHERE Role_name =  "MailController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getAllUserEmails" ), ( SELECT Role_id FROM Role WHERE Role_name =  "MailController" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getUserLastEventEmail" ), ( SELECT Role_id FROM Role WHERE Role_name =  "MailController" ) );
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





INSERT INTO Adress VALUES (0, "patrocinio@ficonlan.es", "patrocinioficonlan");

INSERT INTO EmailTemplate VALUES ( 0, "passwordRecover", ( SELECT Adress_id FROM Adress WHERE Adress_user =  "patrocinio@ficonlan.es" ), "", "", "Recuperación contraseña plataforma Freak's Party webs",
 "La nueva contraseña para la cuenta #loginusuario es #nuevapas esta contraseña solo será válida durante #tiemporestante minutos, recuerda cambiarla.");
 
 INSERT INTO Event (Event_id,Event_name,Event_description,Event_num_participants,Event_minimunAge,Event_date_start,Event_date_end,Event_reg_date_open,Event_reg_date_close,Event_rules)
  VALUES (0, "NOMBRE Evento TEST", "DESCRIPCION Evento TEST", 5, 0, '2014-11-20 12:11:03', '2014-11-30 12:11:03', '2014-11-10 12:11:03', '2014-11-20 12:11:03',"REGALS REGLAS");
  
  
  INSERT INTO NewsItem (NewsItem_Event_id,NewsItem_User_id,NewsItem_title,NewsItem_date_created,NewsItem_date_publish,NewsItem_content) 
  VALUES ((SELECT Event_id FROM Event WHERE Event_Name = "NOMBRE Evento TEST"),1,"Titulo de noticia TEST",NOW(),NOW(),"CONTENIDO DE LA NOTICIA TEST EN TEXTO PLANO");
