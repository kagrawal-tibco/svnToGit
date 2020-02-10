/******************************************************************************
 Description: This file first cleans the persistence table spaces (by dropping
 the database & user), creates them again and gives the user dba rights.
 Customize by changing the user name or privileges as needed.
 *****************************************************************************/

DROP USER 'BE_USER'@'%';

CREATE USER 'BE_USER'@'%' IDENTIFIED BY 'BE_USER';

GRANT ALL PRIVILEGES ON *.* TO 'BE_USER'@'%' WITH GRANT OPTION;
