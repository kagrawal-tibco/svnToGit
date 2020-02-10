/******************************************************************************
 Description: This file first cleans the persistence table spaces (by dropping
 the database & user), creates them again and gives the user dba rights.
 Customize by changing the user name or privileges as needed.
 *****************************************************************************/

DROP USER IF EXISTS "BE_USER";

CREATE USER "BE_USER" WITH PASSWORD 'BE_USER';

ALTER USER "BE_USER" WITH SUPERUSER;
