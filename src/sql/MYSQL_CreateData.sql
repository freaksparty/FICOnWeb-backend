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

INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "closeSession" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "changeUserData" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "changeUserPassword" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "setDefaultLanguage" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
INSERT INTO Role_UserCase VALUES ( 0, ( SELECT UserCase_id FROM UserCase WHERE UserCase_name =  "removeOwnUser" ), ( SELECT Role_id FROM Role WHERE Role_name =  "User" ) );
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


--INSERT INTO `Event` (Event_id, Event_name, Event_description, Event_num_participants, Event_date_start, Event_date_end, Event_reg_date_open, Event_reg_date_close)
--	VALUES (0,'Evento 1','Descripcion Evento 1',5,'2014-12-1 12:11:03','2014-12-7 12:11:03','2014-10-25 12:11:03','2014-11-20 12:11:03');
