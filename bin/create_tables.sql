/******************************************************************************
    Description: This file defines all necessary tables needed to manage Oracle
    persistence of BusinessEvents® working memory objects
 *****************************************************************************/

/** This table stores all the mappings between BE classes (full generated path) and corresponding Oracle types */
CREATE TABLE ClassToOracleType (className VARCHAR2 (4000), oracleType varchar2(4000), oracleTable varchar2(4000) );

/** This table persists the EAR wide next cache Id */
CREATE TABLE CacheIds (cacheIdGeneratorName VARCHAR2 (4000), nextCacheId NUMBER);

CREATE SEQUENCE S_CACHEID;

CREATE TABLE BEAliases (beName VARCHAR2(4000), alias VARCHAR2(4000));

CREATE TABLE ClassRegistry (className VARCHAR2(4000), typeId INTEGER);

CREATE TABLE StateMachineTimeout$$ (CACHEID NUMBER, ENTITY T_STATEMACHINE_TIMEOUT);

CREATE TABLE WorkItems (workKey VARCHAR2(2000), workQueue VARCHAR2(255),  workStatus NUMBER, scheduledTime NUMBER, work BLOB);

CREATE INDEX i_WorkItems on WorkItems (workQueue, scheduledTime);

CREATE UNIQUE INDEX i_WorkItemsU on WorkItems(workQueue, workKey);

CREATE TABLE ObjectTable (GLOBALID NUMBER, SITEID NUMBER, ID NUMBER, EXTID VARCHAR2(255), CLASSNAME VARCHAR2(255), ISDELETED INT, TIMEDELETED NUMBER);

CREATE UNIQUE INDEX i_ObjectTable1 on ObjectTable(GLOBALID);

CREATE INDEX i_ObjectTable2 on ObjectTable(EXTID);

/**  Script for creating index-organized ObjectTable:
CREATE TABLE ObjectTable (GLOBALID NUMBER, SITEID NUMBER, ID NUMBER, EXTID VARCHAR2(255), CLASSNAME VARCHAR2(255), ISDELETED INT, TIMEDELETED NUMBER,
CONSTRAINT i_ObjectTable1 PRIMARY KEY (GLOBALID))
ORGANIZATION INDEX
PARTITION BY HASH(GLOBALID)
PARTITIONS 10
INITRANS 10
;
*/
