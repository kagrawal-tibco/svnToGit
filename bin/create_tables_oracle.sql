/******************************************************************************
    Description: This file defines all necessary tables needed to manage
    Oracle persistence of BusinessEvents® working memory objects
 *****************************************************************************/

/** This table stores all the mappings between BE classes (full generated path) and corresponding Oracle types*/
DROP TABLE ClassToTable CASCADE CONSTRAINTS;
CREATE TABLE ClassToTable (className varchar2 (4000), fieldName varchar2(4000), tableName varchar2(4000) );

/** This table persists the EAR wide next cache Id */
DROP TABLE CacheIds CASCADE CONSTRAINTS;
CREATE TABLE CacheIds (cacheIdGeneratorName varchar2 (4000), nextCacheId number);

DROP SEQUENCE S_CACHEID;
CREATE SEQUENCE S_CACHEID;

DROP TABLE BEAliases CASCADE CONSTRAINTS;
CREATE TABLE BEAliases (beName varchar2(4000), alias varchar2(4000));

DROP TABLE ClassRegistry CASCADE CONSTRAINTS;
CREATE TABLE ClassRegistry (className varchar2(4000), typeId number);

DROP TABLE StateMachineTimeout$$ CASCADE CONSTRAINTS;
CREATE TABLE StateMachineTimeout$$ (CACHEID number, smid number, propertyName varchar2(4000), currentTime number, nextTime number, closure varchar2(4000), ttl number, fired number, time_created$ timestamp, id$ number, extId$ varchar2(2000), state$ char(1));

DROP TABLE WorkItems CASCADE CONSTRAINTS;
CREATE TABLE WorkItems (workKey varchar2(2000), workQueue varchar2(255), workStatus number, scheduledTime number, work BLOB);

CREATE INDEX i_WorkItems on WorkItems (workQueue, scheduledTime);

CREATE UNIQUE INDEX i_WorkItemsU on WorkItems(workQueue, workKey);

DROP TABLE ObjectTable CASCADE CONSTRAINTS;
CREATE TABLE ObjectTable (GLOBALID number, SITEID number, ID number, EXTID varchar2(255), CLASSNAME varchar2(255), ISDELETED number, TIMEDELETED number);

CREATE UNIQUE INDEX i_ObjectTable1 on ObjectTable(GLOBALID);

CREATE INDEX i_ObjectTable2 on ObjectTable(EXTID);

DROP TABLE ProcessLoopState CASCADE CONSTRAINTS;
CREATE TABLE ProcessLoopState (loopKey varchar2(256) primary key, jobKey varchar2(256), taskName varchar2(256), counter number(10), maxCounter number(10), isComplete number(1));

DROP TABLE ProcessMergeState CASCADE CONSTRAINTS;
CREATE TABLE ProcessMergeState (mergeKey varchar2(256), tokenCount number(5), expectedTokenCount number(5), isComplete number(1), processId number(19), processTime number(19), transitionName varchar2(256), isError number(1));
CREATE INDEX i_ProcessMergeState1 on ProcessMergeState(mergeKey);
