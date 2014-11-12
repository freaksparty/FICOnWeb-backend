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
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getAllNewsItem" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getAllNewsItemFormEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getLastNews" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getLastNewsFromEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getSponsors" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getSponsorsByEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "passwordRecover" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getAllPublishedNewsItemFormEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "Anonymous" ) );

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
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getAllNewsItem" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getAllNewsItemFormEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getLastNews" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getLastNewsFromEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getSponsors" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getSponsorsByEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getAllUserEmails" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getUserLastEventEmail" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "sendUserMail" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "getAllPublishedNewsItemFormEvent" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );

INSERT INTO Adress VALUES (0, "patrocinio@ficonlan.es", "patrocinioficonlan");

INSERT INTO EmailTemplate VALUES ( 0, "passwordRecover", ( SELECT Adress_id FROM Adress WHERE Adress_user =  "patrocinio@ficonlan.es" ), "", "", "Recuperación contraseña plataforma Freak's Party webs",
 "La nueva contraseña para la cuenta #loginusuario es #nuevapas esta contraseña solo será válida durante #tiemporestante minutos, recuerda cambiarla.");
 
 INSERT INTO Event (Event_id,Event_name,Event_description,Event_num_participants,Event_minimunAge,Event_date_start,Event_date_end,Event_reg_date_open,Event_reg_date_close,Event_rules)
  VALUES (0, "NOMBRE Evento TEST", "DESCRIPCION Evento TEST", 5, 0, '2014-11-20 12:11:03', '2014-11-30 12:11:03', '2014-11-10 12:11:03', '2014-11-20 12:11:03',"REGALS REGLAS");
  
  
  INSERT INTO NewsItem (NewsItem_Event_id,NewsItem_User_id,NewsItem_title,NewsItem_date_created,NewsItem_date_publish,NewsItem_content) 
  VALUES ((SELECT Event_id FROM Event WHERE Event_Name = "NOMBRE Evento TEST"),1,"Titulo de noticia TEST",NOW(),NOW(),"CONTENIDO DE LA NOTICIA TEST EN TEXTO PLANO");
