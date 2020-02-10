/******************************************************************************
    Description: This file defines all necessary tables needed to manage
    ANSI compatible database persistence of BusinessEvents® working memory objects
 *****************************************************************************/

/** This table stores all the mappings between BE classes (full generated path) and corresponding Oracle types*/
DROP TABLE ClassToTable;
CREATE TABLE ClassToTable (className char varying (4000), fieldName char varying(4000), tableName char varying(4000) );

/** This table persists the EAR wide next cache Id */
DROP TABLE CacheIds;
CREATE TABLE CacheIds (cacheIdGeneratorName char varying (4000), nextCacheId numeric(10));

DROP SEQUENCE S_CACHEID;
CREATE SEQUENCE S_CACHEID;

DROP TABLE BEAliases;
CREATE TABLE BEAliases (beName char varying(4000), alias char varying(4000));

DROP TABLE ClassRegistry;
CREATE TABLE ClassRegistry (className char varying(4000), typeId numeric(10));

DROP TABLE StateMachineTimeout$$;
CREATE TABLE StateMachineTimeout$$ (CACHEID numeric(10), smid numeric(19), propertyName char varying(4000), currentTime numeric(19), nextTime numeric(19), closure char varying(4000), ttl numeric(19), fired numeric(19), time_created$ timestamp, id$ numeric(19), extId$ char varying(2000), state$ char(1));

DROP TABLE WorkItems;
CREATE TABLE WorkItems (workKey char varying(2000), workQueue char varying(255), workStatus numeric(19), scheduledTime numeric(19), work blob);

CREATE INDEX i_WorkItems on WorkItems (workQueue, scheduledTime);

CREATE UNIQUE INDEX i_WorkItemsU on WorkItems(workQueue, workKey);

DROP TABLE ObjectTable;
CREATE TABLE ObjectTable (GLOBALID numeric(19), SITEID numeric(19), ID numeric(19), EXTID char varying(255), CLASSNAME char varying(255), ISDELETED numeric(10), TIMEDELETED numeric(19));

CREATE UNIQUE INDEX i_ObjectTable1 on ObjectTable(GLOBALID);

CREATE INDEX i_ObjectTable2 on ObjectTable(EXTID);

DROP TABLE ProcessLoopState CASCADE CONSTRAINTS;
CREATE TABLE ProcessLoopState (loopKey char varying(256) primary key, jobKey char varying(256), taskName char varying(256), counter numeric(10), maxCounter numeric(10), isComplete numeric(1));

DROP TABLE ProcessMergeState CASCADE CONSTRAINTS;
CREATE TABLE ProcessMergeState (mergeKey char varying(256), tokenCount numeric(5), expectedTokenCount numeric(5), isComplete numeric(1), processId numeric(19), processTime numeric(19), transitionName char varying(256), isError numeric(1));
CREATE INDEX i_ProcessMergeState1 on ProcessMergeState(mergeKey);
