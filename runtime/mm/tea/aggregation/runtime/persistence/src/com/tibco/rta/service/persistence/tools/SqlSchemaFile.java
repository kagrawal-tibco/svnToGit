package com.tibco.rta.service.persistence.tools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import com.tibco.rta.service.persistence.db.DBConstant;


public class SqlSchemaFile {    

    private static final String BRK = JdbcDeployment.BRK;
    private String schemaName;
    private Properties aliases;
    private ArrayList<JdbcTable> tables = new ArrayList<JdbcTable>();

    public SqlSchemaFile(String schemaName) {
    	this.schemaName = schemaName;
    }

    public void setAliases(Properties aliases) {
        this.aliases = aliases;
    }

    public String getName() {
        return schemaName;
    }   

    public void addTable(JdbcTable table) {
        tables.add(table);
    }

    public JdbcTable getTable(String tablename) throws Exception {
        Iterator<JdbcTable> alltab = getTables();
        JdbcTable otab = null;
        while (alltab.hasNext()) {
            otab =  alltab.next();
            //VWC - Changed from case sensitive to case insensitive
            //    - Why case sensitive in the first place?
            if (otab.getName().equalsIgnoreCase((tablename))) {
                return otab;
            }
        }
        //throw new Exception("No Table found for name =" + tablename);
        return null;
    }


    public Iterator<JdbcTable> getTables() {
        return tables.iterator();
    }

    public StringBuffer deleteQuery() {
        return deleteRemoveQuery("DELETE FROM");
    }

    public StringBuffer removeQuery() {
    	 return deleteRemoveQuery("DROP TABLE");
    }
    
    private StringBuffer deleteRemoveQuery(String deleteOrDrop){
    	StringBuffer queryBuffer = new StringBuffer();
        for (Iterator<JdbcTable> it = tables.iterator(); it.hasNext();) {
            JdbcTable table =  it.next();
            queryBuffer.append(deleteOrDrop + " " + table.getName() + RDBMSType.sSqlType.getNewline());
            queryBuffer.append(RDBMSType.sSqlType.getExecuteCommand());
        }

//        queryBuffer.append(RDBMSType.sSqlType.getCommitCommand() + RDBMSType.sSqlType.getNewline());
        return queryBuffer;
    }

    public StringBuffer toStringBuffer() {
        StringBuffer buff = new StringBuffer();
        buff.append(BRK);

        for (Iterator<JdbcTable> it = tables.iterator(); it.hasNext();) {
            JdbcTable table = (JdbcTable) it.next();
            buff.append(table.toStringBuffer());
        }

        if (aliases != null && aliases.size() > 0) {
            // Delete existing entries in DB
            buff.append("DELETE SPM_Aliases" + RDBMSType.sSqlType.getNewline());
            Map sortedAliases = new TreeMap(aliases);
            Iterator allAliases = sortedAliases.keySet().iterator();
            while (allAliases.hasNext()) {
                String key = (String) allAliases.next();
                String ali = (String) sortedAliases.get(key);
                buff.append("INSERT INTO SPM_Aliases VALUES ('" + key + "', '" + ali + "')" + RDBMSType.sSqlType.getNewline());
            }
        }
 //       buff.append(RDBMSType.sSqlType.getCommitCommand() + RDBMSType.sSqlType.getNewline());
        
        // Only for MS SQL server when there is not primary key
		while (buff.indexOf(DBConstant.METRIC_ID_FIELD + " bigint NOT NULL") != -1) {
			buff.replace(buff.indexOf(DBConstant.METRIC_ID_FIELD + " bigint NOT NULL"),
					buff.indexOf(DBConstant.METRIC_ID_FIELD + " bigint NOT NULL") + 25, DBConstant.METRIC_ID_FIELD
							+ " bigint IDENTITY(1,1) PRIMARY KEY");
		}
		// Only for MS SQL server when there is not primary key
		while (buff.indexOf(DBConstant.RULE_METRIC_ID_FIELD + " bigint NOT NULL") != -1) {
			buff.replace(buff.indexOf(DBConstant.RULE_METRIC_ID_FIELD + " bigint NOT NULL"),
					buff.indexOf(DBConstant.RULE_METRIC_ID_FIELD + " bigint NOT NULL") + 30, DBConstant.RULE_METRIC_ID_FIELD
							+ " bigint IDENTITY(1,1) PRIMARY KEY");
		}
		
        return buff;
    }

    public String toString() {
        return toStringBuffer().toString();
    }

}
