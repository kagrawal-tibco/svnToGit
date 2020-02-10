package com.tibco.be.jdbcstore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import com.tibco.be.parser.codegen.CGConstants;
import com.tibco.cep.designtime.model.process.ProcessModel;

public class SqlSchemaFile {

    private static final String BRK = CGConstants.BRK;

    private String name;
    private Properties aliases;
    private boolean includeProcessSupportTables = false; // ProcessLoopState and ProcessMergeState

    private Map<String,JdbcType> types = new HashMap<String,JdbcType>();
    private Map<String,JdbcType> systemTypes = new HashMap<String,JdbcType>();
    private ArrayList<JdbcTable> tables = new ArrayList<JdbcTable>();
    private ArrayList<String> removeScript = new ArrayList<String>();

    // Table names are all UPPERCASE
    private Map<String,String> tableMapping;

    public SqlSchemaFile(String shortName) {
        name = shortName;
    }

    public void setTableMapping(Map<String,String> tableMapping) {
        this.tableMapping = new TreeMap<String,String>();
        this.tableMapping.putAll(tableMapping);
    }

    public void setAliases(Properties aliases) {
        this.aliases = aliases;
    }

    public String getShortName() {
        return name;
    }

    public void includeProcessSupportTables() {
        includeProcessSupportTables = true;
    }

    public void addType(JdbcType type) {
        types.put(type.getName(), type);
    }

    public Iterator getTypes() {
        return types.values().iterator();
    }

    public void addSystemType(JdbcType type) {
        systemTypes.put(type.getName(), type);
    }

    public Iterator getSystemTypes() {
        return systemTypes.values().iterator();
    }

    public void addTable(JdbcTable table) {
        tables.add(table);
    }

    public JdbcTable getTable(String tablename) throws Exception {
        Iterator alltab = getTables();
        JdbcTable otab = null;
        while (alltab.hasNext()) {
            otab = (JdbcTable) alltab.next();
            //VWC - Changed from case sensitive to case insensitive
            //    - Why case sensitive in the first place?
            if (otab.getName().equalsIgnoreCase((tablename))) {
                return otab;
            }
        }
        //throw new Exception("No Table found for name =" + tablename);
        return null;
    }

    public JdbcTable getTableByEntityName(String name) throws Exception {
        Iterator alltab = getTables();
        JdbcTable otab = null;
        while (alltab.hasNext()) {
            otab = (JdbcTable) alltab.next();
            //VWC - Changed from case sensitive to case insensitive
            //    - Why case sensitive in the first place?
            if (otab.getEntityName().equalsIgnoreCase((name))) {
                return otab;
            }
        }
        throw new Exception("No Table found for entity name =" + name);
    }

    public Iterator getTables() {
        return tables.iterator();
    }

    public StringBuffer deleteQuery() {
        StringBuffer ret = new StringBuffer();
        for (Iterator it = tables.iterator(); it.hasNext();) {
            JdbcTable table = (JdbcTable) it.next();
            if (table.isAbstract) {
                continue;
            }
            ret.append(RDBMSType.sSqlType.getdeleteAllCommand(table.getName()) + RDBMSType.sSqlType.getNewline());
            for (int i=0; i<table.secondaryTableInfoList.size(); i++) {
                ret.append(RDBMSType.sSqlType.getdeleteAllCommand(((JdbcTable.SecondaryTableInfo) table.secondaryTableInfoList.get(i)).tableName) + RDBMSType.sSqlType.getNewline());
            }
            ret.append(RDBMSType.sSqlType.getExecuteCommand());
        }

        ret.append(RDBMSType.sSqlType.getdeleteAllCommand("StateMachineTimeout$$") + RDBMSType.sSqlType.getNewline());
        //ret.append("DELETE DeletedEntities" + RDBMSType.sSqlType.getNewline());
        ret.append(RDBMSType.sSqlType.getdeleteAllCommand("WorkItems") + RDBMSType.sSqlType.getNewline());
        ret.append(RDBMSType.sSqlType.getdeleteAllCommand("ObjectTable") + RDBMSType.sSqlType.getNewline());
        if (includeProcessSupportTables) {
            ret.append(RDBMSType.sSqlType.getdeleteAllCommand("ProcessLoopState") + RDBMSType.sSqlType.getNewline());
            ret.append(RDBMSType.sSqlType.getdeleteAllCommand("ProcessMergeState") + RDBMSType.sSqlType.getNewline());
        }
        ret.append(RDBMSType.sSqlType.getExecuteCommand());
        if (!RDBMSType.sSqlType.getCommitCommand().isEmpty())
        	ret.append(RDBMSType.sSqlType.getCommitCommand() + RDBMSType.sSqlType.getNewline());
        return ret;
    }

    public StringBuffer removeQuery() {
        StringBuffer ret = new StringBuffer();
        for (int si = removeScript.size(); si > 0; si--) {
            ret.append(removeScript .get(si - 1) + RDBMSType.sSqlType.getNewline());
            ret.append(RDBMSType.sSqlType.getExecuteCommand());
        }
        //ret.append("DELETE BE_METADATA$" + RDBMSType.sSqlType.getNewline());
        ret.append(RDBMSType.sSqlType.getdeleteAllCommand("StateMachineTimeout$$") + RDBMSType.sSqlType.getNewline());
        ret.append(RDBMSType.sSqlType.getdeleteAllCommand("ClassToTable") + RDBMSType.sSqlType.getNewline());
        ret.append(RDBMSType.sSqlType.getdeleteAllCommand("BEAliases") + RDBMSType.sSqlType.getNewline());
        //ret.append("DELETE DeletedEntities" + RDBMSType.sSqlType.getNewline());
        ret.append(RDBMSType.sSqlType.getdeleteAllCommand("ClassRegistry") + RDBMSType.sSqlType.getNewline());
        ret.append(RDBMSType.sSqlType.getdeleteAllCommand("WorkItems") + RDBMSType.sSqlType.getNewline());
        ret.append(RDBMSType.sSqlType.getdeleteAllCommand("ObjectTable") + RDBMSType.sSqlType.getNewline());
        if (includeProcessSupportTables) {
            ret.append(RDBMSType.sSqlType.getdeleteAllCommand("ProcessLoopState") + RDBMSType.sSqlType.getNewline());
            ret.append(RDBMSType.sSqlType.getdeleteAllCommand("ProcessMergeState") + RDBMSType.sSqlType.getNewline());
        }
        ret.append(RDBMSType.sSqlType.getExecuteCommand());
        return ret;
    }

    public StringBuffer toStringBuffer() {
        StringBuffer ret = new StringBuffer();
        ret.append(BRK);

        for (Iterator it = tables.iterator(); it.hasNext();) {
            JdbcTable table = (JdbcTable) it.next();
            if (table.isAbstract) {
                continue;
            }
            ret.append(table.toStringBuffer(true));
            removeScript.add(RDBMSType.sSqlType.formatDropTableSql(table.getName()));
            for (int i=0; i<table.secondaryTableInfoList.size(); i++) {
                removeScript.add(RDBMSType.sSqlType.formatDropTableSql(((JdbcTable.SecondaryTableInfo) table.secondaryTableInfoList.get(i)).tableName));
            }
        }

        for (Iterator it = tables.iterator(); it.hasNext();) {
            JdbcTable table = (JdbcTable) it.next();
            if (table.isAbstract) {
                continue;
            }
            ret.append(table.indexToStringBuffer());
            for (int i=0; i<table.indexInfoList.size(); i++) {
                JdbcTable.IndexInfo ii = (JdbcTable.IndexInfo) table.indexInfoList.get(i);
                removeScript.add(RDBMSType.sSqlType.formatDropIndexSql(ii.tableName, ii.indexName, ii.indexColumns, ii.isUnique));
            }
            /*
            removeScript.add(RDBMSType.sSqlType.formatDropIndexSql(table.getName(), table.getIndexName()));
            for (int i=0; i<table.indexNameForSecondaryTables.size(); i++) {
                removeScript.add(RDBMSType.sSqlType.formatDropIndexSql(table.getName(), table.getIndexName()));
                removeScript.add("DROP INDEX " + table.indexNameForSecondaryTables.get(i));
            }
            */
        }

        if (tableMapping != null) {
            // Delete existing entries in DB
            ret.append(RDBMSType.sSqlType.getdeleteAllCommand("ClassToTable") + RDBMSType.sSqlType.getNewline());
            ret.append(RDBMSType.sSqlType.getExecuteCommand());
            Iterator classItr = tableMapping.keySet().iterator();
            while (classItr.hasNext()) {
                String className = (String) classItr.next();
                String jdbcTable = (String) tableMapping.get(className);
                // Do not populate the type field anymore - it does not exist
                ret.append("INSERT INTO ClassToTable(classname, tablename) VALUES ('" + className + "', '" + jdbcTable +  "')" + RDBMSType.sSqlType.getNewline());
                for (int i=0; i<tables.size(); i++) {
                    JdbcTable jt = (JdbcTable) tables.get(i);
                    if (jt.getName().equals(jdbcTable)) {
                        // Sort fields to achieve comparable results
                        Collections.sort(jt.secondaryTableInfoList);
                        for (int j=0; j<jt.secondaryTableInfoList.size(); j++) {
                            ret.append("INSERT INTO ClassToTable(classname, fieldName, tablename) VALUES ('" +
                                className + "', '" +
                                ((JdbcTable.SecondaryTableInfo) jt.secondaryTableInfoList.get(j)).fieldName + "', '" +
                                ((JdbcTable.SecondaryTableInfo) jt.secondaryTableInfoList.get(j)).tableName + "')" +
                                RDBMSType.sSqlType.getNewline());
                        }
                    }
                }
            }
        }

        if (aliases != null && aliases.size() > 0) {
            // Delete existing entries in DB
            ret.append(RDBMSType.sSqlType.getdeleteAllCommand("BEAliases") + RDBMSType.sSqlType.getNewline());
            Map sortedAliases = new TreeMap(aliases);
            Iterator allAliases = sortedAliases.keySet().iterator();
            while (allAliases.hasNext()) {
                String key = (String) allAliases.next();
                String ali = (String) sortedAliases.get(key);
                ret.append("INSERT INTO BEAliases VALUES ('" + key + "', '" + ali + "')" + RDBMSType.sSqlType.getNewline());
            }
        }
        if (!RDBMSType.sSqlType.getCommitCommand().isEmpty())
        	ret.append(RDBMSType.sSqlType.getCommitCommand() + RDBMSType.sSqlType.getNewline());
        return ret;
    }

    public StringBuffer migrateObjectTable(Connection conn, String schemaOwner) throws Exception {
        StringBuffer ret = new StringBuffer();
        try {
            Map<Long, ArrayList<ObjTableTuple>> idMap = new HashMap<Long, ArrayList<ObjTableTuple>>();
            ArrayList<Long> duplicateIds = new ArrayList<Long>();
            long maxId = -1L;
            for (Object obj : tableMapping.entrySet()) {
                Map.Entry entry = (Map.Entry) obj;
                System.out.println("Processing table " + entry.getValue());
                String className = (String) entry.getKey();
                String qry = (schemaOwner == null) ? "SELECT T.ID$, T.EXTID$  FROM " + entry.getValue() + " T":
                                 "SELECT T.ID$, T.EXTID$  FROM " + schemaOwner + "." + entry.getValue() + " T";

                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(qry);
                while (rs.next()) {
                    ObjTableTuple otTuple = new ObjTableTuple(rs, className);
                    ArrayList<ObjTableTuple> lst = idMap.get(otTuple.Id);
                    if (lst == null) {
                        lst = new ArrayList<ObjTableTuple>();
                        idMap.put(otTuple.Id, lst);
                    } else {
                        duplicateIds.add(otTuple.Id);
                    }
                    lst.add(otTuple);
                    if (otTuple.Id > maxId)
                        maxId = otTuple.Id;
                }
                rs.close();
                stmt.close();
            }

            // Now all dups are in duplicateIds list
            if (duplicateIds.size() > 0) {
                System.out.println("Duplicate Ids found across tables. See x_dups.txt for details. Skipping population of ObjectTable  ");
                ret.append("-- Following Duplicate Ids found across tables. Please fix them before proceeding." + BRK);
                ret.append("--        ID                       EXT-ID                               CLASSNAME " + BRK);
                //long nextId = maxId + 1;
                for (Long dupId : duplicateIds) {
                    ArrayList<ObjTableTuple> dupRecs = idMap.get(dupId);
                    Collections.sort(dupRecs);
                    ObjTableTuple[] dups = dupRecs.toArray(new ObjTableTuple[0]);
                    for (int i = 0; i < dupRecs.size(); i++) {
                        ret.append(" " + dups[i].Id + "             " + dups[i].extId + "               " + dups[i].className + BRK);
                    }
                    /***  Imp Code: Do not Delete
                     *    This can be used to fix duplicates, but works only if the project does not use
                     *    referred or contained concepts.
                     for (int i = 0; i < dupRecs.size(); i++) {
                     if (i > 0) {
                     dups[i].Id = nextId++;
                     ret.append("Update " + tableMapping.get(dups[i].className) + " T  Set T.ID$ = " + dups[i].Id
                     + " where T.ID$ = " + dups[0].Id + ";"  + BRK);
                     }
                     }
                     */
                }
                return ret;
            } else {
                System.out.println("No Duplicate Ids found across tables.");
                populateObjTable(idMap, conn, schemaOwner);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return null;
    }

    private void populateObjTable(Map<Long, ArrayList<ObjTableTuple>> idMap, Connection conn, String schemaOwner) throws Exception {
        try {
            String qry = (schemaOwner == null) ? "SELECT count(*) from OBJECTTABLE":
                             "SELECT count(*) from " + schemaOwner + ".OBJECTTABLE";
            Statement stmt = conn.createStatement();
            ResultSet rs=null;
            try {
                rs = stmt.executeQuery(qry);
            }
            catch (SQLException sqe) {
                if (schemaOwner == null) {
                    System.out.println("Table OBJECTTABLE does not exist. Creating...");
    
                    String ddl = "CREATE TABLE ObjectTable (GLOBALID NUMBER, SITEID NUMBER, ID NUMBER, EXTID VARCHAR2(255), CLASSNAME VARCHAR2(255), ISDELETED INT, TIMEDELETED NUMBER)";
                    stmt.executeUpdate(ddl);
                    ddl = "CREATE UNIQUE INDEX i_ObjectTable1 on ObjectTable(GLOBALID)";
                    stmt.executeUpdate(ddl);
                    ddl = "CREATE INDEX i_ObjectTable2 on ObjectTable(EXTID)";
                    stmt.executeUpdate(ddl);
    
                    System.out.println("Table OBJECTTABLE created successfully");
                }
                else {
                    System.err.println("Table OBJECTTABLE does not exist. Please create it and re-run command...");
                }
            }

            if (rs != null) {
                rs.next();
                long n = rs.getLong(1);
                if (n > 0) {
                    System.out.println("OBJECTTABLE not empty. Aborting step to populate it");
                    return;
                }
            }

            conn.setAutoCommit(false);
            qry = (schemaOwner == null) ? "INSERT INTO OBJECTTABLE (GLOBALID, SITEID, ID, EXTID, CLASSNAME, ISDELETED) values (?, 0, ?, ?, ?, 0)":
                      "INSERT INTO " + schemaOwner + ".OBJECTTABLE (GLOBALID, SITEID, ID, EXTID, CLASSNAME, ISDELETED) values (?, 0, ?, ?, ?, 0)";
            PreparedStatement ps = conn.prepareStatement(qry);

            // Now generate the insert script:
            int count = 0, ttlCount = 0;
            for (ArrayList<ObjTableTuple> lst : idMap.values()) {
                for (ObjTableTuple tuple : lst) {
                    ps.setLong(1, tuple.Id);
                    ps.setLong(2, tuple.Id);
                    if (tuple.extId == null)
                        ps.setString(3, null);
                    else
                        ps.setString(3, tuple.extId);
                    ps.setString(4, tuple.className);

                    ps.addBatch();
                    count++;
                    ttlCount++;

                    if (count >= 1000) {
                        ps.executeBatch();
                        conn.commit();
                        System.out.println("Batch inserted records=" + count + ", Total inserted in ObjectTable=" + ttlCount);
                        count = 0;
                    }
                }
            }

            if (count > 0) {
                ps.executeBatch();
                conn.commit();
                System.out.println("Batch inserted records=" + count + ", Total inserted in ObjectTable=" + ttlCount);
                count = 0;
            }
        }
        catch (Exception e) {
            System.out.println("Exception during OBJECTTABLE population :" + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        finally {
            conn.setAutoCommit(true);
        }
    }

    public StringBuffer generateDeleteScript() throws Exception {
       StringBuffer ret = new StringBuffer();
       ret.append ("-- The following script can be used for deleting entities that have been marked as deleted in ObjectTable" + RDBMSType.sSqlType.getNewline());
       for (Object obj : tableMapping.entrySet()) {
           Map.Entry entry = (Map.Entry) obj;
           //System.out.println("Processing table " + entry.getValue());
           String sql = "DELETE FROM " + entry.getValue() + " WHERE id$ IN (SELECT GLOBALID FROM OBJECTTABLE WHERE ISDELETED=1 and CLASSNAME='" + entry.getKey() + "')" + RDBMSType.sSqlType.getNewline();
           ret.append(sql);
       }
       // Now delete 1-day old entries from ObjectTable
       ret.append("DELETE FROM OBJECTTABLE WHERE ISDELETED = 1 AND TIMEDELETED <= ("
           + RDBMSType.sSqlType.formatObjectTableDeleteTimeCondition() + ")" + RDBMSType.sSqlType.getNewline());
       return ret;
   }

    public static class ObjTableTuple implements Comparable {
        public String key;
        //public long globalId;
        public int siteId=0;
        public long Id;
        public String extId;
        public String className;
        public int isDeleted = 0;

        public ObjTableTuple(ResultSet rs, String clazz) throws Exception {
            //this.globalId=
            Id=rs.getLong(1);
            extId=rs.getString(2);
            className=clazz;
            this.key=""+Id+className;
        }

        public int compareTo(Object o) {
            ObjTableTuple other  =(ObjTableTuple)o;
            return this.key.compareTo(other.key);
        }
    }

    public StringBuffer getAlterBuffer() {
        StringBuffer ret = new StringBuffer();
        ret.append(BRK);

        for (Iterator it = tables.iterator(); it.hasNext();) {
            JdbcTable table = (JdbcTable) it.next();
            if (table.isAbstract) {
                continue;
            }
            if (table.state == JdbcTable.State.NEW) {
                ret.append(table.toStringBuffer(false));
            }
            else if (table.state == JdbcTable.State.MODIFIED) {
                ret.append(table.toAlterBuffer());
                for (Iterator st = table.secondaryTableInfoList.iterator(); st.hasNext();) {
                    JdbcTable.SecondaryTableInfo secTable = (JdbcTable.SecondaryTableInfo) st.next();
                    if (secTable.state == JdbcTable.State.NEW) {
                    	if (table.isInstanceof(ProcessModel.class)) {
                    		if (secTable.alterAdded == false) { 
                        		ret.append(secTable.tableSql);
                        		ret.append(secTable.indexSql);
                        	}
                    	} else {          		
                    		ret.append(secTable.tableSql);
                    		ret.append(secTable.indexSql);
                    	}
                    }
                }
            }
            else if (table.state == JdbcTable.State.DELETED) {
                continue;
            }
            else {
                continue;
            }
        }

        for (Iterator it = tables.iterator(); it.hasNext();) {
            JdbcTable table = (JdbcTable) it.next();
            if (table.isAbstract) {
                continue;
            }
            if (table.state == JdbcTable.State.NEW) {
                ret.append(table.indexToStringBuffer());
            }
        }

        if (tableMapping != null) {
            // Delete existing entries in DB
            ret.append(RDBMSType.sSqlType.getdeleteAllCommand("ClassToTable") + RDBMSType.sSqlType.getNewline());
            ret.append(RDBMSType.sSqlType.getExecuteCommand());
            Iterator classItr = tableMapping.keySet().iterator();
            while (classItr.hasNext()) {
                String className = (String) classItr.next();
                String jdbcTable = (String) tableMapping.get(className);
                // do not populate the type field anymore - it does not exist
                ret.append("INSERT INTO ClassToTable(classname, tablename) VALUES ('" + className + "', '" + jdbcTable +  "')" + RDBMSType.sSqlType.getNewline());
                for (int i=0; i<tables.size(); i++) {
                    JdbcTable jt = (JdbcTable) tables.get(i);
                    if (jt.getName().equals(jdbcTable)) {
                        for (int j=0; j<jt.secondaryTableInfoList.size(); j++) {
                            ret.append("INSERT INTO ClassToTable(classname, fieldName, tablename) VALUES ('" +
                                className + "', '" +
                                ((JdbcTable.SecondaryTableInfo) jt.secondaryTableInfoList.get(j)).fieldName + "', '" +
                                ((JdbcTable.SecondaryTableInfo) jt.secondaryTableInfoList.get(j)).tableName + "')" +
                                RDBMSType.sSqlType.getNewline());
                        }
                    }
                }
            }
        }

        if (aliases != null && aliases.size() > 0) {
            // Delete existing entries in DB
            ret.append(RDBMSType.sSqlType.getdeleteAllCommand("BEAliases")+ RDBMSType.sSqlType.getNewline());
            Map sortedAliases = new TreeMap(aliases);
            Iterator allAliases=sortedAliases.keySet().iterator();
            while (allAliases.hasNext()) {
                String key= (String) allAliases.next();
                String ali= (String) sortedAliases.get(key);
                ret.append("INSERT INTO BEAliases VALUES ('" + key + "', '" + ali + "')" + RDBMSType.sSqlType.getNewline());
            }
        }
        
        if (!RDBMSType.sSqlType.getCommitCommand().isEmpty())
        	ret.append(RDBMSType.sSqlType.getCommitCommand() + RDBMSType.sSqlType.getNewline());
        return ret;
    }

    public String toString() {
        return toStringBuffer().toString();
    }
}
