-- Configuración de los Roles
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getAllEvents" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "findEventByName" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getActivity" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getAllActivities" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getActivitiesByEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getNewsItem" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getAllNewsItem" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getAllNewsItemFormEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getLastNews" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getLastNewsFromEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getSponsors" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getSponsorsByEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "passwordRecover" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );

INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "closeSession" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "changeUserData" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "changeUserPassword" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "setDefaultLanguage" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "removeOwnUser" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getUserRoles" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getAllEvents" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "findEventByName" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "addParticipantToEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getRegistration" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getActivity" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getAllActivities" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getActivitiesByEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "addParticipantToActivity" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "removeParticipantFromActivity" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getActivityParticipants" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getNewsItem" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getAllNewsItem" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getAllNewsItemFormEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getLastNews" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getLastNewsFromEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getSponsors" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getSponsorsByEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getAllUserEmails" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getUserLastEventEmail" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "sendUserMail" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );


INSERT INTO Adress VALUES (0, "patrocinio@ficonlan.es", "patrocinioficonlan");

INSERT INTO EmailTemplate VALUES ( 0, "passwordRecover", ( SELECT Adress_id FROM Adress WHERE Adress_user =  "patrocinio@ficonlan.es" ), "", "", "Recuperación contraseña plataforma Freak's Party webs",
 "La nueva contraseña para la cuenta #loginusuario es #nuevapas esta contraseña solo será válida durante #tiemporestante minutos, recuerda cambiarla.");
