/******************************************************************************
 Description: This file first cleans the persistence table spaces (by dropping
 the user) and creates the user again and gives the user dba rights.
 Customize by changing the user name or privileges as needed.
 *****************************************************************************/

DROP USER BE_USER CASCADE
/

CREATE USER BE_USER IDENTIFIED BY BE_USER
/

GRANT dba TO BE_USER
/
