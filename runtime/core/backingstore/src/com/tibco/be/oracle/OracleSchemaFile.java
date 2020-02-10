package com.tibco.be.oracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.be.parser.codegen.CGConstants;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Sep 20, 2006
 * Time: 10:30:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class OracleSchemaFile {
	
    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
    

    private static final String BRK = CGConstants.BRK;
    private Map typeMapping;
    // Stores mapping only for new types, for benefit of alter-script
    @SuppressWarnings("unused")
    private Map newTypeMapping;
    private Map tableMapping;
    private Properties aliases;

    private String name;
    private Map types = new HashMap();
    private ArrayList tables = new ArrayList();
    private ArrayList removeScript = new ArrayList();
    //private List<OracleTable> unchangedTables = null;
    // Map of class name to concept
    private Map clzToCeptMap = new HashMap();
    // Map of concept name to referring concepts
    private Map ceptRefs = new HashMap();
    private boolean ceptRefsInited;
    // Environment properties
    private Properties env;

    public OracleSchemaFile(String shortName) {
        this.name = shortName;
    }

    public void setMapping(Map typeMapping) {
        this.typeMapping = typeMapping;
    }

    public void setNewTypesMapping(Map typeMapping) {
        this.newTypeMapping = typeMapping;
    }

    public void setTableMapping(Map tableMapping) {
        this.tableMapping = tableMapping;
    }

    public void setAliases(Properties aliases) {
        this.aliases=aliases;
    }

    public void setClzToCeptMapping(Map clzToCeptMap) {
        this.clzToCeptMap = clzToCeptMap;
    }

    public String getShortName() {
        return name;
    }

    public void addType(OracleType type) {
        types.put(type.getName(), type);
    }

    public Iterator getTypes() {
        return types.values().iterator();
    }

    public void addTable(OracleTable table) {
        tables.add(table);
    }

    public void setProperties (Properties env) {
        this.env = env;
    }

    //public void setUnchangedTables(List<OracleTable> list) {
    //      unchangedTables = list;
    //}

    public OracleTable getTable(String tablename) throws Exception {
        Iterator alltab = getTables();
        OracleTable otab = null;
        while (alltab.hasNext()) {
            otab = (OracleTable) alltab.next();
            if (otab.getName() .equals((tablename))) {
                return otab;
            }
        }
        throw new Exception("No Table found for name =" + tablename);
    }

    public Iterator getTables() {
        return tables.iterator();
    }

    public StringBuffer deleteQuery() {
        StringBuffer ret = new StringBuffer();
        for (Iterator it = tables.iterator(); it.hasNext();) {
            OracleTable table = (OracleTable) it.next();
            ret.append("truncate table " + table.getName() + ";" + BRK);
        }

        ret.append("truncate table StateMachineTimeout$$ ;" + BRK);
        ret.append("truncate table ObjectTable ;" + BRK);
        ret.append("truncate table WorkItems ;" + BRK);

        ret.append("commit" + ";" + BRK);
        return ret;
    }

    public StringBuffer removeQuery() {
        StringBuffer ret = new StringBuffer();
        for (int si = removeScript.size(); si > 0; si--) {
            ret.append("drop " + removeScript .get(si - 1) + ";" + BRK);
        }
        ret.append("TRUNCATE TABLE ClassToOracleType ;" + BRK);
        ret.append("TRUNCATE TABLE BEAliases ;" + BRK);
        ret.append("TRUNCATE TABLE ObjectTable ;" + BRK);
        ret.append("TRUNCATE TABLE WorkItems ;" + BRK);
        return ret;
    }

    void writeType_Pass1 (OracleType type, List written, StringBuffer ret, boolean forAlterScript) {
        // First recurse and create super-types
        if (!type.superType.equalsIgnoreCase("T_CONCEPT") &&
            !type.superType.equalsIgnoreCase("T_SIMPLE_EVENT") &&
            !type.superType.equalsIgnoreCase("T_TIME_EVENT") &&
            !type.superType.equalsIgnoreCase("T_STATEMACHINE_CONCEPT")) {
            OracleType superType = (OracleType) types.get(type.superType);
            if (!written.contains(superType)) {
                if (!type.getName().equalsIgnoreCase("T_CONCEPT") &&
                    !type.getName().equalsIgnoreCase("T_EVENT") &&
                    !type.getName().equalsIgnoreCase("T_STATEMACHINE_CONCEPT")) {
                    writeType_Pass1(superType, written, ret, forAlterScript);
                } else {
                    return;
                }
            }
        }

        // Now create 'this' type
        if (!written.contains(type)) {
            // If called for alter-script, generate script for only new types
            if (!forAlterScript || type.state == OracleType.State.NEW) {
                ret.append("begin DBMS_OUTPUT.put_line('Creating Dummy Type " + type.getName() + "'); end;" + BRK + "/" + BRK);
                ret.append("CREATE OR REPLACE TYPE " + type.getName() + " UNDER " + type.superType + " () NOT FINAL;" + BRK + "/" + BRK);
            }
            written.add(type);
            removeScript.add("type " + type.getName());
        }
    }

    @SuppressWarnings("deprecation")
    public StringBuffer toStringBuffer(boolean iot) {
        StringBuffer ret = new StringBuffer();
        List written = new LinkedList();
        // Create forward references
        for (Iterator it = types.values().iterator(); it.hasNext();) {
            OracleType type = (OracleType) it.next();
            boolean forAlterScript = false;
            writeType_Pass1(type, written, ret, forAlterScript);
        }

        // Create the actual types
        for (Iterator it = types.values().iterator(); it.hasNext();) {
            OracleType type = (OracleType) it.next();
            ret.append("begin DBMS_OUTPUT.put_line('Creating Type " + type.getName() + "'); end;" + BRK + "/" + BRK);
            ret.append(type.toStringBuffer() + BRK);
            ret.append("show errors" + BRK);
        }

        ret.append(BRK);

        for (Iterator it = tables.iterator(); it.hasNext();) {
            OracleTable table = (OracleTable) it.next();
            ret.append(table.toStringBuffer(iot));
            removeScript.add("table " + table.getName());
        }

        ret.append(BRK);

        for (Iterator it = tables.iterator(); it.hasNext();) {
            OracleTable table = (OracleTable) it.next();
            ret.append(table.indexToStringBuffer(iot));
            removeScript.add("index " + table.getIndexName());
        }

        ret.append(BRK);

        for (Iterator it = tables.iterator(); it.hasNext();) {
            OracleTable table = (OracleTable) it.next();
            ret.append(table.viewToStringBuffer());
            removeScript.add("view " + table.getViewName());
        }

        ret.append(BRK);

        if (typeMapping != null) {
            Iterator allTypes = typeMapping.keySet().iterator();
            while (allTypes.hasNext()) {
                String oracleType = (String) allTypes.next();
                String beClass = (String) typeMapping.get(oracleType);
                String oracleTable= (String) tableMapping.get(beClass);
                ret.append("INSERT INTO ClassToOracleType VALUES ('" + beClass + "', '" + oracleType + "', '" + oracleTable +  "');" + BRK);
            }
        }

        if (aliases != null && aliases.size() >0) {
            //Delete existing entries in DB
            ret.append("DELETE BEAliases;" + BRK);
            Iterator allAliases=aliases.keySet().iterator();
            while (allAliases.hasNext()) {
                String key= (String) allAliases.next();
                String al = (String) aliases.get(key);
                ret.append("INSERT INTO BEAliases VALUES ('" + key + "', '" + al + "');" + BRK);
            }
        }
        ret.append("commit" + ";" + BRK);
        return ret;
    }

    public StringBuffer migrateObjectTable(Connection conn, String schemaOwner) throws Exception {
        StringBuffer ret = new StringBuffer();
        try {
            Map<Long, ArrayList<ObjTableTuple>> idMap = new HashMap<Long, ArrayList<ObjTableTuple>>();
            Set<Long> duplicateIds = new HashSet<Long>();
            long maxId = -1L;
            for (Object obj : tableMapping.entrySet()) {
                Map.Entry entry = (Map.Entry) obj;
                System.out.println("Processing table " + entry.getValue());
                String className = (String) entry.getKey();
                String qry = (schemaOwner == null) ? "SELECT T.ENTITY.ID$, T.ENTITY.EXTID$  FROM " + entry.getValue() + " T":
                                 "SELECT T.ENTITY.ID$, T.ENTITY.EXTID$  FROM " + schemaOwner + "." + entry.getValue() + " T";
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

            //Now all dups are in duplicateIds list
            if (duplicateIds.size() > 0) {
                System.out.println("Duplicate Ids found across tables. See x_dups.txt for details. Skipping population of ObjectTable  ");
                ret.append("-- Following Duplicate Ids found across tables. Please fix them before proceeding." + BRK);
                ret.append("--        ID                       EXT-ID                               CLASSNAME " + BRK);
                long nextId = maxId + 1;
                boolean fixDups = Boolean.parseBoolean(env.getProperty("be.oracle.schemamigration.fixDups", "false").trim());
                if (fixDups) {
                    for (Long dupId : duplicateIds) {
                        ArrayList<ObjTableTuple> dupRecs = idMap.get(dupId);
                        Collections.sort(dupRecs);
                        ObjTableTuple[] dups = dupRecs.toArray(new ObjTableTuple[0]);
                        for (int i = 0; i < dupRecs.size(); i++) {
                            ret.append("-- " + dups[i].Id + "             " + dups[i].extId + "               " + dups[i].className + BRK);
                            if (i == 1) {
                                dups[i].Id = nextId++;
                                ret.append("update " + tableMapping.get(dups[i].className) + " t set t.entity.id$=" + dups[i].Id + " where t.entity.id$="
                                    + dups[0].Id + ";" + BRK);
                                updateRefs(dups[i].className, ret, dups[0].Id, dups[i].Id, conn);
                            }
                            if (i > 1) {
                                System.out.println ("There are " + dupRecs.size() + " duplicates for id=" + dups[0].Id + ", can only fix 1 in one run.");
                                break;
                            }
                        }
                    }
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
                    ddl="CREATE INDEX i_ObjectTable2 on ObjectTable(EXTID)";
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

            //Now generate the insert script:
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
       ret.append ("-- The following script can be used for deleting entities that have been marked as deleted in ObjectTable" + BRK +BRK);
           for (Object obj : tableMapping.entrySet()) {
               Map.Entry entry = (Map.Entry) obj;
               //System.out.println("Processing table " + entry.getValue());
               String sql = "DELETE FROM " + entry.getValue()  + " T WHERE T.ENTITY.ID$ IN (SELECT GLOBALID FROM OBJECTTABLE WHERE ISDELETED=1 and CLASSNAME='" +  entry.getKey() + "');" + BRK;
               ret.append(sql);
           }
       //Now delete 1-day old entries from ObjectTable
       ret.append("DELETE FROM OBJECTTABLE WHERE ISDELETED = 1 AND TIMEDELETED <= (SELECT (sysdate-1 - to_date('01-JAN-1970','DD-MON-YYYY')) * (86400000) FROM dual);" + BRK);
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
        // Create forward references
        List written = new LinkedList();
        for (Iterator it = types.values().iterator(); it.hasNext();) {
            OracleType type = (OracleType) it.next();
            boolean forAlterScript = true;
            writeType_Pass1(type, written, ret, forAlterScript);
        }

        // Create the actual types
        for (Iterator it = types.values().iterator(); it.hasNext();) {
            OracleType type = (OracleType) it.next();
            if (type.state == OracleType.State.NEW || type.state == OracleType.State.MODIFIED) {
                 if (type.state == OracleType.State.NEW)
                    ret.append("begin DBMS_OUTPUT.put_line('Creating Type " + type.getName() + "'); end;" + BRK + "/" + BRK);
                else
                    ret.append("begin DBMS_OUTPUT.put_line('Modifying Type " + type.getName() + "'); end;" + BRK + "/" + BRK);

                ret.append(type.genAlterScript() + BRK);
                ret.append("show errors" + BRK);
            }
            else if (type.state == OracleType.State.UNCHANGED)
                ret.append("begin DBMS_OUTPUT.put_line('Type " + type.getName() + " not modified. Leaving unchanged'); end;" + BRK + "/" + BRK);

            //ret.append(";" + BRK);
        }

        ret.append(BRK);

        for (Iterator it = tables.iterator(); it.hasNext();) {
            OracleTable table = (OracleTable) it.next();
            if (table.state == OracleTable.State.NEW)
                ret.append(table.toStringBuffer());
            else
                ret.append("begin DBMS_OUTPUT.put_line(' Table " + table.getName() + " not altered. Leaving table/index unchanged'); end;" + BRK + "/" + BRK);
        }

        for (Iterator it = tables.iterator(); it.hasNext();) {
            OracleTable table = (OracleTable) it.next();
            if (table.state == OracleTable.State.NEW)
                ret.append(table.indexToStringBuffer(false));
        }

        // Always generate views afresh, even if it existed before. That is safer
        for (Iterator it = tables.iterator(); it.hasNext();) {
            OracleTable table = (OracleTable) it.next();
            ret.append(table.viewToStringBuffer());
        }

        ret.append("truncate table ClassToOracleType;" + BRK);
        if (typeMapping != null) {
            Iterator allTypes = typeMapping.keySet().iterator();
            while (allTypes.hasNext()) {
                String oracleType = (String) allTypes.next();
                String beClass = (String) typeMapping.get(oracleType);
                String oracleTable= (String) tableMapping.get(beClass);
                ret.append("INSERT INTO ClassToOracleType VALUES ('" + beClass + "', '" + oracleType + "', '" + oracleTable +  "');" + BRK);
            }
        }

        /*
        if (newTypeMapping != null) {
            Iterator allTypes = newTypeMapping.keySet().iterator();
            while (allTypes.hasNext()) {
                String oracleType = (String) allTypes.next();
                String beClass = (String) newTypeMapping.get(oracleType);
                String oracleTable= (String) tableMapping.get(beClass);
                ret.append("INSERT INTO ClassToOracleType VALUES ('" + beClass + "', '" + oracleType + "', ' " + oracleTable +  "');" + BRK);
            }
        }
        */

       if (aliases != null && aliases.size() >0) {
            //Delete existing entries in DB
            ret.append("DELETE BEAliases;" + BRK);
            Iterator allAliases=aliases.keySet().iterator();
            while (allAliases.hasNext()) {
                String key= (String) allAliases.next();
                String al = (String) aliases.get(key);
                ret.append("INSERT INTO BEAliases VALUES ('" + key + "', '" + al + "');" + BRK);
            }
        }
        ret.append("commit" + ";" + BRK);
        return ret;
    }
    public String toString(boolean iot) {
        return toStringBuffer(iot).toString();
    }

    public String toString() {
        return toString(false);
    }
    private void updateRefs (String clzName, StringBuffer buf, long oldId, long newId, Connection conn) {
        createCeptRefs();

        CeptRefs ceptRef = (CeptRefs) ceptRefs.get(clzName);
        if (ceptRef == null) {
            return;
        }

        updateFwdRefs(ceptRef, buf, oldId, newId, conn);
        updateRevRefs(ceptRef, buf, oldId, newId, conn);

    }

    private void createCeptRefs() {
        if (ceptRefsInited) {
            return;
        }
        Iterator i = clzToCeptMap.values().iterator();
        while (i.hasNext()) {
            Concept cept = (Concept) i.next();
            List pds = cept.getAllPropertyDefinitions();
            for (int j=0; j<pds.size(); j++) {
                PropertyDefinition pd = (PropertyDefinition) pds.get(j);
                Concept pdCept = pd.getConceptType();
                if (pdCept == null) {
                    continue;
                }

                //create links to referring concepts, so that their fwdRefs can be updated
                String clzName =ModelNameUtil.modelPathToGeneratedClassName(pdCept.getFullPath());
                CeptRefs cRefs = (CeptRefs) ceptRefs.get(clzName);
                if (cRefs == null) {
                    cRefs = new CeptRefs();
                    cRefs.clzName = clzName;
                    cRefs.cept = pdCept;
                    ceptRefs.put(clzName, cRefs);
                }
                String key = cept.getFullPath() + ":" + pd.getName();
                CeptRef rCept = (CeptRef) cRefs.referringCepts.get(key);
                if (rCept == null) {
                    rCept = new CeptRef();
                    rCept.concept = cept;
                    rCept.propName = pd.getName();
                    rCept.clzName = ModelNameUtil.modelPathToGeneratedClassName(cept.getFullPath());
                    rCept.isArray = pd.isArray();
                    rCept.isContained = pd.getType() == PropertyDefinition.PROPERTY_TYPE_CONCEPT;
                    cRefs.referringCepts.put(key, rCept);
                } else {
                    System.out.println("referencing key " + key + " already present..");
                }

                //create links to referred concepts, so that their reverseRefs can be updated
                clzName = ModelNameUtil.modelPathToGeneratedClassName(cept.getFullPath());
                cRefs = (CeptRefs) ceptRefs.get(clzName);
                if (cRefs == null) {
                    cRefs = new CeptRefs();
                    cRefs.clzName = clzName;
                    cRefs.cept = cept;
                    ceptRefs.put(clzName, cRefs);
                }
                key = cept.getFullPath() + ":" + pd.getName();
                CeptRef rdCept = (CeptRef) cRefs.referredCepts.get(key);
                if (rdCept == null) {
                    rdCept = new CeptRef();
                    rdCept.clzName = ModelNameUtil.modelPathToGeneratedClassName(pdCept.getFullPath());
                    rdCept.propName = pd.getName();
                    rdCept.concept = pdCept;
                    rdCept.isContained = pd.getType() == PropertyDefinition.PROPERTY_TYPE_CONCEPT;
                    rdCept.isArray = pd.isArray();
                    cRefs.referredCepts.put(key, rdCept);
                } else {
                    System.out.println("referred key " + key + " already present..");
                }
            }
        }
        boolean printRefs = Boolean.parseBoolean(env.getProperty("be.oracle.schemamigration.printRefs", "false").trim());
        if (printRefs) {
            String debugStr = printCeptRefMap();
            System.out.println (debugStr);
        }
        ceptRefsInited = true;
    }

    private String printCeptRefMap() {
        StringBuffer buf = new StringBuffer(1000);

        Iterator i = ceptRefs.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry e = (Entry) i.next();
            String key = (String) e.getKey();
            CeptRefs cRef = (CeptRefs) e.getValue();
            buf.append("Key = " + key + " Value = " + cRef.toString());

        }
        return buf.toString();
    }

    private void updateFwdRefs (CeptRefs ceptRef, StringBuffer buf, long oldId, long newId, Connection conn) {

        Iterator i = ceptRef.referringCepts.entrySet().iterator();
        while(i.hasNext()) {
            Entry e = (Entry) i.next();
            String clzNm = (String) e.getKey();
            CeptRef rc = (CeptRef) e.getValue();
            String tblName = (String)tableMapping.get(rc.clzName);
            if (!rc.isArray) {
                String updtStmt = "update " + tblName + " d set d.entity.\"" + rc.propName + "\".id$=" + newId
                        + " where d.entity.\"" + rc.propName + "\".id$=" + oldId + ";" + BRK;
                buf.append(updtStmt);
            } else {
                // sample query: select distinct d.entity.id$ from d_campaign d, table (d.entity."waveList") t where t.id$ = 44;
                String qry = "select distinct d.entity.id$ from " + tblName + " d, table (d.entity.\"" + rc.propName + "\") t where t.id$=" + oldId;
                System.out.println(qry);
                Statement stmt = null;
                ResultSet rs = null;
                try {
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(qry);
                    while (rs.next()) {
                        Long id = rs.getLong(1);
                        // sample update: update table(select d.waveList from d_wave d where d.entity.id$ = <id>) set id=<newid> where id=<oldid>
                        String updtStmt = "update table (select d.entity.\"" + rc.propName + "\" from " + tblName + " d where d.entity.id$=" + id + ") set id$="
                                + newId + " where id$=" + oldId + ";" + BRK;
                        buf.append(updtStmt);
                    }
                    rs.close();
                    stmt.close();

                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    closeStmt(stmt, rs);
                }
            }
        }
    }

    private void updateRevRefs (CeptRefs ceptRef, StringBuffer buf, long oldId, long newId, Connection conn) {
        Iterator i = ceptRef.referredCepts.entrySet().iterator();
        while(i.hasNext()) {
            Entry e = (Entry) i.next();
            CeptRef rc = (CeptRef) e.getValue();
            String tblName = (String)tableMapping.get(rc.clzName);

            if (rc.isContained) {
                //sample update: update d_wave d set d.entity.parent$.id$ = <new> where d.entity.parent$.id$=<old>
                String updtStmt = "update " + tblName + " d set d.entity.parent$.id$=" + newId + " where d.entity.parent$.id$=" + oldId + ";" + BRK;
                buf.append(updtStmt);
            } else {
                // sample query: select distinct d.entity.id$ from d_wave d, table (d.entity.reverserefs$) t where t.id$ =<oldId>;
                String qry = "select distinct d.entity.id$ from " + tblName + " d, table (d.entity.reverserefs$) t where t.id$=" + oldId;
                System.out.println(qry);
                Statement stmt = null;
                ResultSet rs = null;
                try {
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(qry);
                    while (rs.next()) {
                        Long id = rs.getLong(1);
                        String updtStmt = "update table (select d.entity.reverseRefs$ from " + tblName + " d where d.entity.id$="
                                + id + ") set id$=" + newId + " where id$=" + oldId + ";" + BRK;
                        buf.append(updtStmt);
                    }
                    rs.close();
                    stmt.close();

                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    closeStmt(stmt, rs);
                }
            }
        }
    }

    private void closeStmt (Statement stmt, ResultSet rs) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (Exception e) {}
        }
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {}
        }
    }

    static class CeptRefs {
        Concept cept;
        String clzName;
        //key = propName + ceptName, val = concept
        Map referringCepts = new HashMap();
        Map referredCepts = new HashMap();
        public String toString() {
            String str = "clzName = " + clzName + " ceptName = " + cept.getName() + "\n";
            Iterator i = referringCepts.entrySet().iterator();
            while (i.hasNext()) {
                Entry e = (Entry) i.next();
                String key = (String) e.getKey();
                CeptRef rc = (CeptRef) e.getValue();
                str += "   referring cept: " + key + " value="+rc.clzName + " prop="+ rc.propName + "\n";
            }
            i = referredCepts.entrySet().iterator();
            while (i.hasNext()) {
                Entry e = (Entry) i.next();
                String key = (String) e.getKey();
                CeptRef rc = (CeptRef) e.getValue();
                str += "   referred cept: " + key + " value="+rc.clzName + " prop="+ rc.propName + " isCon=" + rc.isContained + "\n";
            }
            str += "***\n";
            return str;
        }
    }

    static class CeptRef {
        String propName;
        Concept concept;
        String clzName;
        boolean isContained;
        boolean isArray;
    }

    public static void main(String args[]) {
        OracleSchemaFile schemaFile = new OracleSchemaFile("test");
        System.out.println(schemaFile.toStringBuffer(false));
    }
}
