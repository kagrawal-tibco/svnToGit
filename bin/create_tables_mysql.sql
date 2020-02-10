/******************************************************************************
    Description: This file defines all necessary tables needed to manage
    My Sql persistence of BusinessEvents® working memory objects
 *****************************************************************************/

/** This table stores all the mappings between BE classes (full generated path) and corresponding MySQL tables*/
DROP TABLE IF EXISTS ClassToTable;
CREATE TABLE ClassToTable (className varchar(4000), fieldName varchar(4000), tableName varchar(4000) );

/** This table persists the EAR wide next cache Id */
DROP TABLE IF EXISTS CacheIds;
CREATE TABLE CacheIds (cacheIdGeneratorName varchar (4000), nextCacheId integer);

DROP TABLE IF EXISTS BEAliases;
CREATE TABLE BEAliases (beName varchar(4000), alias varchar(4000));

DROP TABLE IF EXISTS ClassRegistry;
CREATE TABLE ClassRegistry (className varchar(4000), typeId integer);


DROP TABLE IF EXISTS StateMachineTimeout$$;
CREATE TABLE StateMachineTimeout$$ (CACHEID integer, smid BIGINT, propertyName varchar(4000), currentTime BIGINT, nextTime BIGINT, closure varchar(4000), ttl BIGINT, fired BIGINT, time_created$ timestamp, id$ BIGINT, extId$ varchar(2000), state$ char(1));

DROP TABLE IF EXISTS WorkItems;
CREATE TABLE WorkItems (workKey varchar(2000), workQueue varchar(255), workStatus BIGINT, scheduledTime BIGINT, work blob);

CREATE INDEX i_WorkItems on WorkItems (workQueue, scheduledTime);
-- MySQL has index key length limitation (3072 bytes)
-- CREATE UNIQUE INDEX i_WorkItemsU on WorkItems(workQueue, workKey);


DROP TABLE IF EXISTS ObjectTable;
CREATE TABLE ObjectTable (GLOBALID BIGINT, SITEID BIGINT, ID BIGINT, EXTID varchar(255), CLASSNAME varchar(255), ISDELETED integer, TIMEDELETED BIGINT);

CREATE UNIQUE INDEX i_ObjectTable1 on ObjectTable(GLOBALID);
CREATE INDEX i_ObjectTable2 on ObjectTable(EXTID);

DROP TABLE IF EXISTS ProcessLoopState;
CREATE TABLE ProcessLoopState (loopKey varchar(256) not null, jobKey varchar(256), taskName varchar(256), counter integer, maxCounter integer, isComplete integer,  primary key (loopKey));

DROP TABLE IF EXISTS ProcessMergeState;
CREATE TABLE ProcessMergeState (mergeKey varchar(256), tokenCount integer, expectedTokenCount integer, isComplete integer, processId BIGINT, processTime BIGINT, transitionName varchar(256), isError integer);
CREATE INDEX i_ProcessMergeState1 on ProcessMergeState(mergeKey);
