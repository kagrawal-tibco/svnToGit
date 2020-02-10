/******************************************************************************
 Description: This file first cleans the persistence table spaces (by dropping
 the database & user), creates them again and gives the user dba rights.
 Customize by changing the user name or privileges as needed.
 *****************************************************************************/

use master
go
drop database be_user
go
create database be_user
go
sp_droplogin be_user
go
create login be_user with password  = 'be_user', check_policy = off, default_database = be_user
go

use be_user
create user be_user for login be_user
go
grant control, alter, connect to be_user
go
