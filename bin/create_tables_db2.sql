--*****************************************************************************
--    Description: This file defines all necessary tables needed to manage
--    IBM DB2 persistence of BusinessEvents® working memory objects
--*****************************************************************************

/** This table stores all the mappings between BE classes (full generated path) and corresponding DB2 tables*/
DROP TABLE ClassToTable;
CREATE TABLE ClassToTable (className varchar (255), fieldName varchar(255), tableName varchar(255) );

/** This table persists the EAR wide next cache Id */
DROP TABLE CacheIds;
CREATE TABLE CacheIds (cacheIdGeneratorName varchar (255), nextCacheId integer);

DROP TABLE BEAliases;
CREATE TABLE BEAliases (beName varchar(255), alias varchar(255));

DROP TABLE ClassRegistry;
CREATE TABLE ClassRegistry (className varchar(255), typeId integer);

DROP TABLE StateMachineTimeout$$;
CREATE TABLE StateMachineTimeout$$ (CACHEID bigint, smid bigint, propertyName varchar(255), currentTime bigint, nextTime bigint, closure varchar(255), ttl bigint, fired bigint, time_created$ timestamp, id$ bigint, extId$ varchar(2000), state$ char(1));

DROP TABLE WorkItems;
CREATE TABLE WorkItems (workKey varchar(2000), workQueue varchar(255), workStatus integer, scheduledTime bigint, work blob);

CREATE INDEX i_WorkItems on WorkItems (workQueue, scheduledTime);

-- DB2 Server has index key length limitation
--CREATE UNIQUE INDEX i_WorkItemsU on WorkItems(workQueue, workKey);

DROP TABLE ObjectTable;
CREATE TABLE ObjectTable (GLOBALID bigint, SITEID bigint, ID bigint, EXTID varchar(255), CLASSNAME varchar(255), ISDELETED integer, TIMEDELETED bigint);

CREATE UNIQUE INDEX i_ObjectTable1 on ObjectTable(GLOBALID);

CREATE INDEX i_ObjectTable2 on ObjectTable(EXTID);

DROP TABLE ProcessLoopState;
CREATE TABLE ProcessLoopState (loopKey varchar(256) not null, jobKey varchar(256), taskName varchar(256), counter integer, maxCounter integer, isComplete smallint, primary key (loopkey));

DROP TABLE ProcessMergeState;
CREATE TABLE ProcessMergeState (mergeKey varchar(256), tokenCount smallint, expectedTokenCount smallint, isComplete smallint, processId bigint, processTime bigint, transitionName varchar(256), isError smallint);
CREATE INDEX i_ProcessMergeState1 on ProcessMergeState(mergeKey);
