/******************************************************************************
    Description: This file defines all necessary tables needed to manage
    Sql Server persistence of BusinessEvents® working memory objects
 *****************************************************************************/

/** This table stores all the mappings between BE classes (full generated path) and corresponding Sql Server tables*/
DROP TABLE ClassToTable
go
CREATE TABLE ClassToTable (className nvarchar (4000), fieldName nvarchar(4000), tableName nvarchar(4000) );
go

/** This table persists the EAR wide next cache Id */
DROP TABLE CacheIds
go
CREATE TABLE CacheIds (cacheIdGeneratorName nvarchar (4000), nextCacheId numeric(10));
go

DROP TABLE BEAliases
go
CREATE TABLE BEAliases (beName nvarchar(4000), alias nvarchar(4000));
go

DROP TABLE ClassRegistry
go
CREATE TABLE ClassRegistry (className nvarchar(4000), typeId numeric(10));
go

DROP TABLE StateMachineTimeout$$
go
CREATE TABLE StateMachineTimeout$$ (CACHEID numeric(10), smid numeric(19), propertyName nvarchar(4000), currentTime numeric(19), nextTime numeric(19), closure nvarchar(4000), ttl numeric(19), fired numeric(19), time_created$ timestamp, id$ numeric(19), extId$ nvarchar(2000), state$ nchar(1));
go

DROP TABLE WorkItems
go
CREATE TABLE WorkItems (workKey nvarchar(2000), workQueue nvarchar(255), workStatus numeric(19), scheduledTime numeric(19), work varbinary(max));
go

CREATE INDEX i_WorkItems on WorkItems (workQueue, scheduledTime);
go

-- SQL Server has index key length limitation (900 bytes)
-- CREATE UNIQUE INDEX i_WorkItemsU on WorkItems(workQueue, workKey);
go

DROP TABLE ObjectTable
go
CREATE TABLE ObjectTable (GLOBALID numeric(19), SITEID numeric(19), ID numeric(19), EXTID nvarchar(255), CLASSNAME nvarchar(255), ISDELETED numeric(10), TIMEDELETED numeric(19));
go

CREATE UNIQUE INDEX i_ObjectTable1 on ObjectTable(GLOBALID);
go

CREATE INDEX i_ObjectTable2 on ObjectTable(EXTID);
go

DROP TABLE ProcessLoopState
go
CREATE TABLE ProcessLoopState (loopKey nvarchar(256) not null, jobKey nvarchar(256), taskName nvarchar(256), counter numeric(10), maxCounter numeric(10), isComplete numeric(1) constraint pk_processloop primary key (loopKey))
go

DROP TABLE ProcessMergeState
go
CREATE TABLE ProcessMergeState (mergeKey nvarchar(256), tokenCount numeric(5), expectedTokenCount numeric(5), isComplete numeric(1), processId numeric(19), processTime numeric(19), transitionName nvarchar(256), isError numeric(1))
go
CREATE INDEX i_ProcessMergeState1 on ProcessMergeState(mergeKey)
go
