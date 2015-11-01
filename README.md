#Freak's Party Event Manager Backend
=======
This backend provides a (trying to be) REST API for Event management. Check also the related [Web Frontend](https://github.com/freaksparty/frontend) to check it working.

##DEPLOY
After cloning the project create one database in your MySQL/MariaDB instance. following are the example for the provided config files, you may want to change them:

```
$ mysql -u root -p
> create database backend;
> grant all privileges on backend.* to fpbackend@* identified by 'fpbackendtestps'; --this will only allow local connections
```

Now, lets create skeleton for the DB:

```
$ mysql -u fpbackend -pfpbackendtestps backend < src/sql/MYSQL_CreateTables.sql
```

First run the app, some data will be created (UserCase, i.e):

```
$ mvn exec:java
```

Now you can create the Event, edit src/sql/MYSQL_CreateData.sql as your needs and run it:

```
$ mysql -u fpbackend -pfpbackendtestps backend < src/sql/MYSQL_CreateData.sql
```

Default admin user is: "admin" 