package com.tibco.be.oracle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.tibco.be.parser.codegen.CGConstants;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Sep 26, 2006
 * Time: 11:15:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class OracleTable {

    protected String name;
    private static final String BRK = CGConstants.BRK;

    protected boolean isPrimitive = false;
    protected String superTableName = null;

    //To help generate migration script. This is the state of this Table
    public static enum State { NEW, PREEXISTING };
    protected  State state = State.NEW;


    public String getSuperTableName() {
        return superTableName;
    }

    public void setSuperTableName(String superTableName) {
        this.superTableName = superTableName;
    }

    public OracleTable getSuperTable() {
        return superTable;
    }

    public void setSuperTable(OracleTable superTable) {
        this.superTable = superTable;
    }

    protected OracleTable superTable = null;
    protected String tableOf=null;

    protected List members       = new ArrayList();
    protected List nestedTables  = new ArrayList();
    protected List constraints   = new ArrayList();

    public List getViewMembers() {
        List members=new ArrayList(viewMembers);
        if (superTable!=null) {
            members.addAll(superTable.getViewMembers());
        }
        return members;
    }

    protected List viewMembers  = new ArrayList();

    public void setParentTable(String parentTable) {
        this.parentTable = parentTable;
    }

    protected String parentTable;

    protected static HashSet indexNames = new HashSet();
    protected static HashSet viewNames = new HashSet();
    private String indexCreator;
    private String indexName=null;
    private String viewName=null;
    protected ArrayList indexForNestedTables = new ArrayList();

    /**
     *
     * @param name
     */
    public OracleTable(String name) {
        this.name = name;
    }

    /**
     *
     * @param memberName
     * @param memberType
     */
    public void addMember(String memberName, String memberType, String nestedTableName) {
        members.add(memberName + " " + memberType);
        if (nestedTableName != null) {
            nestedTables.add("NESTED TABLE  " + memberName + " STORE AS " + nestedTableName);
        }
    }

    public void addViewMember(String memberName) {
        addViewMember(memberName,memberName,null);
    }

    public void addViewMember(String memberName, String memberFullName) {
        addViewMember(memberName,memberFullName,null);
    }

    public void addViewMember(String memberName, String memberFullName, String nestedTableName) {
        viewMembers.add(memberFullName+" as "+memberName);
    }

    public StringBuffer addNestedTable(String memberName, String nestedTableName) {
        StringBuffer nestedTable= new StringBuffer("NESTED TABLE " + memberName + " STORE AS "  + nestedTableName);
        nestedTables.add(nestedTable);
        indexForNestedTables.add(" CREATE INDEX " + getIndexName(nestedTableName) + " ON " + nestedTableName + "(nested_table_id) ");
        return nestedTable;
    }

    public StringBuffer addNestedTable(StringBuffer parentTable, String memberName, String nestedTableName) {
        String nestedTable=" (NESTED TABLE " + memberName + " STORE AS "  + nestedTableName + " )";
        parentTable.append(nestedTable);
        indexForNestedTables.add(" CREATE INDEX " + getIndexName(nestedTableName) + " ON " + nestedTableName + "(nested_table_id) ");
        return parentTable;
    }

    /**
     *
     * @param constraintName
     * @param columns
     */
    public void addPrimaryKeys(String constraintName, String columns) {
        constraints.add("ALTER TABLE " + name + " ADD CONSTRAINT " + constraintName + " (" + columns + ")");
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    public StringBuffer toStringBuffer() {
        return toStringBuffer(false);
    }

    public StringBuffer toStringBuffer(boolean iot) {
        StringBuffer ret = new StringBuffer();

        ret.append("begin DBMS_OUTPUT.put_line('Dropping Table " + name + "'); end;" + BRK + "/" + BRK);

        ret.append("DROP TABLE " + name + ";" + BRK);

        ret.append("begin DBMS_OUTPUT.put_line('Creating Table " + name + "'); end;" + BRK + "/" + BRK);

        ret.append("CREATE TABLE " + name + " (");

        for (int i=0; i < members.size();i++) {
            if (i > 0) ret.append(",");
            ret.append(members.get(i));
        }

        if (iot) {
           ret.append(", CONSTRAINT " + getIndexName() + " PRIMARY KEY (entity.ID$))" + BRK);
           ret.append("ORGANIZATION INDEX PCTTHRESHOLD 1 OVERFLOW PARTITION BY HASH(entity.ID$) PARTITIONS 5" + BRK );
        }
        else
            ret.append(")" + BRK);


        for (int i=0; i < nestedTables.size();i++) {
            if (i > 0) ret.append(",");
            ret.append(nestedTables.get(i));
            ret.append(BRK);
        }

        ret.append(";" + BRK);

        for (int i=0; i < constraints.size();i++) {
            ret.append(constraints.get(i));
            ret.append(";" + BRK);
        }

        if (!iot)
            this.indexCreator="create unique index "+getIndexName()+" on "+name+"( entity.ID$ ) ;"+BRK;
        else
            this.indexCreator="";

        return ret;
    }

    StringBuffer viewToStringBuffer() {
        StringBuffer ret = new StringBuffer();
        if (viewMembers.size() <= 0) {
            return ret.append("");
        }

        int lineStart = ret.length();
        //ret.append ("create or replace view "+ getMangledViewName(name));
        ret.append("create or replace view " + getViewName());
        ret.append(" as select ");

        List members = new ArrayList();
        members.add("t.ENTITY.\"ID$\" as \"ID$\"");
        members.add("t.ENTITY.\"EXTID$\" as \"EXTID$\"");
        members.add("t.ENTITY.\"STATE$\" as \"STATE$\"");
        members.add("t.ENTITY.\"TIME_CREATED$\" as \"TIME_CREATED$\"");
        //members.add("t.ENTITY.\"time_last_modified$\" as \"TIME_LAST_MODIFIED$\"");
        members.addAll(viewMembers);

        if (superTable != null) {
            List smembers = superTable.getViewMembers();
            for (int i = 0; i < smembers.size(); i++) {
                if (!members.contains(smembers.get(i))) {
                    members.add((smembers.get(i)));
                }
            }
        }

        if (members.size() > 0) {
            for (int i = 0; i < members.size(); i++) {
                if (i > 0)
                    ret.append(",");

                if ((ret.length() - lineStart) > 170) {
                    ret.append(BRK);
                    lineStart = ret.length();
                }

                ret.append(members.get(i));
            }
            ret.append(" from " + name + " t ; " + BRK + BRK);
        } else {
            ret = new StringBuffer("" + BRK + BRK);
        }
        return ret;
    }

    public String getViewName() {
        if (viewName == null) {
            viewName = getMangledViewName(name);
        }
        return  viewName ;
    }

    public String getIndexName() {
        if (indexName == null) {
            indexName = getMangledIndexName(name);
        }
        return  indexName ;
    }

    public String getIndexName(String tableName) {
        return getMangledIndexName(tableName);
    }

    StringBuffer indexToStringBuffer(boolean iot) {
        StringBuffer ret = new StringBuffer();
        if (!iot) {
            if (this.indexCreator == null) {
                toStringBuffer(iot);
            }
            ret.append(this.indexCreator);
        }

        for (int j = 0; j < indexForNestedTables.size(); j++) {
            ret.append(indexForNestedTables.get(j) + ";" + BRK);
        }

        return ret;
    }

    private String getMangledIndexName(String tablename) {
        String indexName="i_"+tablename;
        if (indexName .length() >30) {
            indexName=indexName .substring(0,30);
        }
        int co=0;
        while (indexNames.contains(indexName ) ) {
            indexName=indexName.substring(0,Math.min(indexName.length() ,25))+co++;
        }
        indexNames.add(indexName);
        return indexName;
    }

    private String getMangledViewName(String tablename) {
        String indexName="v_"+tablename;
        if (indexName.length() >30) {
            indexName=indexName .substring(0,30);
        }
        int co=0;
        while (viewNames.contains(indexName ) ) {
            indexName=indexName.substring(0,Math.min(indexName.length() ,25))+co++;
        }
        viewNames.add(indexName);
        return indexName;
    }

    public String toString() {
        return toStringBuffer().toString();
    }
}
