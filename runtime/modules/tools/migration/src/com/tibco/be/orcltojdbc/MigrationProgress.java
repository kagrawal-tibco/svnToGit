package com.tibco.be.orcltojdbc;

/*
  SQL Used to create/populate migration table:
    drop table BE_JDBC.MIGRATIONPROGRESS;
    create table BE_JDBC.MIGRATIONPROGRESS(className VARCHAR2(4000), typeId INTEGER, maxEntityId number, done number);
    insert into BE_JDBC.MIGRATIONPROGRESS(select className, typeId, 0, 0 from BE_ORCL.CLASSREGISTRY);
    commit;

  SQL Used to compare object tables following migration:
    select count(*) cnt, className from BE_JDBC.OBJECTTABLE group by className order by cnt;
    
    insert into BE_JDBC.OBJECTTABLE(select GLOBALID, SITEID, ID, EXTID, CLASSNAME, ISDELETED, TIMEDELETED from BE_ORCL.OBJECTTABLE);
*/

/*
    TODO: Examine migration needs for...
    CREATE SEQUENCE S_CACHEID;
    CREATE TABLE CacheIds (cacheIdGeneratorName VARCHAR2 (4000), nextCacheId NUMBER);
    CREATE TABLE StateMachineTimeout$$ (CACHEID NUMBER, ENTITY T_STATEMACHINE_TIMEOUT);
    CREATE TABLE WorkItems (workKey VARCHAR2(2000), workQueue VARCHAR2(255),  workStatus NUMBER, scheduledTime NUMBER, work BLOB);
 */
public class MigrationProgress implements Comparable {
    
    public static MigrationProgress WORKITEMS = new MigrationProgress(Integer.MIN_VALUE, "WorkItems", Long.MIN_VALUE, 0);
    
    public int typeId;
    public String className;
    public long maxEntityId;
    public int totalEntityCnt;
    public int done;
    public boolean isPersisted;
    
    public MigrationProgress() {
        
    }
    
    public MigrationProgress(int typeId, String className, long maxEntityId, int totalEntityCnt) {
        this.typeId = typeId;
        this.className = className;
        this.maxEntityId = maxEntityId;
        this.totalEntityCnt = totalEntityCnt;
        this.done = 0;
        this.isPersisted = false;
    }
    
    public int compareTo(Object obj) {
        MigrationProgress mp = (MigrationProgress)obj;
        if (mp.totalEntityCnt != totalEntityCnt) {
            // Sort from high to low ...
            return (mp.totalEntityCnt-totalEntityCnt);
        } else {
            // Then from a-A to z-Z ...
            return (mp.className.compareTo(className));
        }
    }
}
