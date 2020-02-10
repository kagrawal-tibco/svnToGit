package com.tibco.be.jdbcstore;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tibco.be.jdbcstore.JdbcTable.MemberDef;
import com.tibco.be.jdbcstore.impl.DBConceptMap;
import com.tibco.be.jdbcstore.impl.DBEventMap;
import com.tibco.be.parser.codegen.CGConstants;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.element.Concept;

public class JdbcTable {

    protected Entity ent;
    protected String name;

    protected boolean isPrimitive = false;
    protected boolean isAbstract = false;
    protected boolean isDVM = false;
    protected boolean isMultiDimension = false;
    protected String superTableName = null;
    protected Map systemTableMap = RDBMSType.sSystemTableMap;
    protected List<SecondaryTableInfo> secondaryTableInfoList = new ArrayList<SecondaryTableInfo>();
    protected JdbcUtil jdbcUtil = null;
    
    
    // To help generate migration script. This is the state of this Table
    protected static enum State { NEW, MODIFIED, UNCHANGED, DELETED };
    protected State state = State.NEW;

    public String getSuperTableName() {
        return superTableName;
    }

    public void setSuperTableName(String superTableName) {
        this.superTableName = superTableName;
    }

    public void setIsMultiDimension(boolean isMulti) {
        isMultiDimension = isMulti;
    }

    public void setIsDVM(boolean isDVM) {
        this.isDVM = isDVM;
    }

    public JdbcTable getSuperTable() {
        return superTable;
    }

    public void setSuperTable(JdbcTable superTable) {
        this.superTable = superTable;
    }

    private JdbcTable superTable = null;
    private String tableOf = null;

    private List<MemberDef> members = new ArrayList<MemberDef>();
    private List<MemberDef> complexMembers = new ArrayList<MemberDef>();
    private List<String> constraints = new ArrayList<String>();

    public void setTableType(String tableOf) {
        this.tableOf = tableOf;
    }

    public String getTableType() {
        return this.tableOf;
    }

    private String indexName = null;
    private String indexCreator = null;
    private static HashSet<String> indexNameSet = new HashSet<String>();
    protected ArrayList<IndexInfo> indexInfoList = new ArrayList<IndexInfo>();

    /**
     *
     * @param name
     */
    public JdbcTable(String name) {
        this.name = name;
        this.jdbcUtil = JdbcUtil.getInstance();
    }

    public JdbcTable(Entity ent) {
        this.ent = ent;
        this.jdbcUtil = JdbcUtil.getInstance();
        this.name = jdbcUtil.name2JdbcTable(ent);
    }

    // Used only for system type loading during jdbc deployment
    public JdbcTable(String name, boolean isAbstract) {
        this.name = name;
        this.isAbstract = isAbstract;
        this.jdbcUtil = JdbcUtil.getInstance();
    }

    /**
     *
     * @param memberName
     * @param memberType
     *  Note: By the time addMember is called, alias entry has been created
     *        using one of the getXXPDName method in jdbcUtil class. The
     *        exception is getSMPDName.
     */
    public void addMember(String memberName, String memberType) {
        addMember(memberName,memberType,false,0);
    }

    /**
     *
     * @param memberName
     * @param memberType
     * @param isArray
     * @param size
     */
    public void addMember(String memberName, String memberType, boolean isArray, int size) {
        String memberAlias = memberName; //jdbcUtil.getPDName(null, memberName);
        if (memberType.equals(JdbcType.DATETIME_TYPE) || memberType.equals(JdbcType.BASE_ENTITYREF_TYPE)) {
            members.add(new MemberDef(memberAlias, memberType));
        } else if (memberType.indexOf("T_") == 0 || memberType.indexOf("t_") == 0) {
            // For complex fields, do not use field alias/keyword replacement - use name as is.
            // TODO: Validate this works in all cases!!!
            complexMembers.add(new MemberDef(memberName, memberType.toUpperCase(), false, size, MemberDef.Added));
            if (ent != null) {
                String secTableName = jdbcUtil.name2JdbcTableWithAlias(ent, memberName);
                secondaryTableInfoList.add(new SecondaryTableInfo(secTableName, memberName, memberName));
            }
        } else {
            members.add(new MemberDef(memberAlias, memberType, isArray, size, MemberDef.Added));
        }
    }

    public void addMember(String memberName, MemberDef memberDef) {
        members.add(memberDef);
    }

    public List getMembers() {
        return members;
    }

    public List getComplexMembers() {
        return complexMembers;
    }

    public MemberDef findMember(String memberNameOrAlias) {
        // Find attribute name for database column (in case aliases were applied)
        String memberName = jdbcUtil.getNonAliasedName("COLUMN", memberNameOrAlias);

        // CacheId column is present in all tables.
        if (memberName.equalsIgnoreCase("cacheId")) {
            return MemberDef.CACHEID;
        }
        //System.err.println("Find-member:" + this.name + " - " + memberName);
        for (Iterator itr = members.iterator(); itr.hasNext(); ) {
            MemberDef memberDef = (MemberDef)itr.next();
            if (memberName.equalsIgnoreCase(memberDef.memberName)) {
                // Mark actual field as not-changed (if not marked for deletion)
                if (memberDef.state != JdbcTable.MemberDef.Deleted) {
                    memberDef.state = JdbcTable.MemberDef.NotChanged;
                }
                return memberDef;
            }
            // Entity reference column is stored as additional column (<name>_id$)
            if (memberName.equalsIgnoreCase(memberDef.memberName+"_id$")&&
                memberDef.memberType.equals(JdbcType.BASE_ENTITYREF_TYPE)) {
                // Return substitute member definition
                MemberDef memberRefs = new MemberDef(memberDef.memberName+"_id$", "LONG");
                // Mark actual field as not-changed (if not marked for deletion)
                if (memberDef.state != JdbcTable.MemberDef.Deleted) {
                    memberDef.state = JdbcTable.MemberDef.NotChanged;
                }
                return memberRefs;
            }
            // Timestamp column is represented with two columns (<name>_tm and <name>_tz)
            if (memberName.equalsIgnoreCase(memberDef.memberName+"_TM")&&
                memberDef.memberType.equals(JdbcType.DATETIME_TYPE)) {
                // Return substitute member definition
                MemberDef memberTime = new MemberDef(memberDef.memberName+"_TM", "TIMESTAMP");
                // Mark actual field as not-changed (if not marked for deletion)
                if (memberDef.state != JdbcTable.MemberDef.Deleted) {
                    memberDef.state = JdbcTable.MemberDef.NotChanged;
                }
                return memberTime;
            }
            if (memberName.equalsIgnoreCase(memberDef.memberName+"_TZ")&&
                memberDef.memberType.equals(JdbcType.DATETIME_TYPE)) {
                // Return substitute member definition
                MemberDef memberZone = new MemberDef(memberDef.memberName+"_TZ", "STRING");
                // Mark actual field as not-changed (if not marked for deletion)
                if (memberDef.state != JdbcTable.MemberDef.Deleted) {
                    memberDef.state = JdbcTable.MemberDef.NotChanged;
                }
                return memberZone;
            }
        }
        if (superTable != null) {
            return superTable.findMember(memberName);
        } else if (superTableName != null) {
            new Exception("Missing super table "+superTableName).printStackTrace();
        }
        return null;
    }

    public MemberDef findComplexMember(String memberName) {
        // CacheId column is present in all tables.
        if (memberName.equalsIgnoreCase("cacheId")) {
            return MemberDef.CACHEID;
        }
        //System.err.println("Find-complex-member:" + this.name + " - " + memberName);
        for (Iterator itr = complexMembers.iterator(); itr.hasNext(); ) {
            MemberDef memberDef = (MemberDef)itr.next();
            if (memberName.equalsIgnoreCase(memberDef.memberName)) {
                // Mark actual field as not-changed (if not marked for deletion)
                if (memberDef.state != JdbcTable.MemberDef.Deleted) {
                    memberDef.state  = JdbcTable.MemberDef.NotChanged;
                }
                return memberDef;
            }
            // Entity reference column is stored as additional column (<name>_id$)
            if (memberName.equalsIgnoreCase(memberDef.memberName+"_id$")&&
                memberDef.memberType.equals(JdbcType.BASE_ENTITYREF_TYPE)) {
                // Return substitute member definition
                MemberDef memberRefs = new MemberDef(memberDef.memberName+"_id$", "LONG");
                // Mark actual field as not-changed (if not marked for deletion)
                if (memberDef.state != JdbcTable.MemberDef.Deleted) {
                    memberDef.state  = JdbcTable.MemberDef.NotChanged;
                }
                return memberRefs;
            }
            // Timestamp column is represented with two columns (<name>_tm and <name>_tz)
            if (memberName.equalsIgnoreCase(memberDef.memberName+"_TM")&&
                memberDef.memberType.equals(JdbcType.DATETIME_TYPE)) {
                // Return substitute member definition
                MemberDef memberTime = new MemberDef(memberDef.memberName+"_TM", "TIMESTAMP");
                // Mark actual field as not-changed (if not marked for deletion)
                if (memberDef.state != JdbcTable.MemberDef.Deleted) {
                    memberDef.state  = JdbcTable.MemberDef.NotChanged;
                }
                return memberTime;
            }
            if (memberName.equalsIgnoreCase(memberDef.memberName+"_TZ")&&
                memberDef.memberType.equals(JdbcType.DATETIME_TYPE)) {
                // Return substitute member definition
                MemberDef memberZone = new MemberDef(memberDef.memberName+"_TZ", "STRING");
                // Mark actual field as not-changed (if not marked for deletion)
                if (memberDef.state != JdbcTable.MemberDef.Deleted) {
                    memberDef.state  = JdbcTable.MemberDef.NotChanged;
                }
                return memberZone;
            }
        }
        if (superTable != null) {
            return superTable.findMember(memberName);
        } else if (superTableName != null) {
            new Exception("Missing super table "+superTableName).printStackTrace();
        }
        return null;
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

    public String getEntityName() {
        if (ent != null) {
            return ent.getName();
        }
        return "";
    }
    
    public boolean isInstanceof(Class<?> clz) {
    	if(this.ent != null) {
    		return clz.isAssignableFrom(this.ent.getClass());
    	}
    	return false;
    }

    // generate add column method which handles the type translation to
    // db specific sql type
     private boolean appendColumn(String colName, String type, int size, StringBuffer buf, boolean addComma) {
        return appendColumn(colName, type, size, buf, addComma, null, true);
    }

    // This method is only used by id history tuple to control whether 'not null' constraint is used or not
     private boolean appendColumn(String colName, String type, int size, StringBuffer buf, boolean addComma, boolean idNotNull) {
        return appendColumn(colName, type, size, buf, addComma, null, idNotNull);
    }

     private boolean appendColumn(String colName, String type, int size, StringBuffer buf, boolean addComma, String modificationType, boolean idNotNull) {
        if (addComma) {
            buf.append(", ");
        }
        int lineStart = buf.lastIndexOf(CGConstants.BRK);
        if ((buf.length() - lineStart) > 500) {
            buf.append(CGConstants.BRK + "  ");
        }
        // VWC - do not wrap quote around it - buf.append("\"" + colName + "\" ");
        // FIX THIS - Need to handle reserved keyword such as 'start', 'end'
        buf.append(colName + " ");
        if (modificationType != null) {
            buf.append(modificationType + " ");
        }
        buf.append(RDBMSType.sSqlType.getColumnTypeForPrimitiveType(type, size));
        if (DBEventMap.ID_FIELD_NAME.equalsIgnoreCase(colName) ||
            DBConceptMap.ID_FIELD_NAME.equalsIgnoreCase(colName)) {
            if (idNotNull) {
            	buf.append(" not null");
        	}
        }
        return true; // true means comma is needed in subsequent calls
    }

    // Add complex type columns to the secondary table
    // In this method, no need to do column alias because all the field names should be Tibco
    // system type field names
    private boolean appendComplexColumn(MemberDef def, StringBuffer buf, String fieldName, int fieldSize, boolean addComma) {
        JdbcTable systemTable = (JdbcTable) systemTableMap.get(def.memberType);
        String tableOf = null; // check to see the type is an array type
        // check to see the type is a 2D array such at collection history type
        // which each data record has an array of history
        //VWC FIX THIS if (systemTable != null && systemTable.isMultiDimension) {
        if (systemTable != null && systemTable.getTableType() != null) {
            // need to add an extra field to index the data record(one of the dimension)
            //addComma = appendColumn(jdbcUtil.getPDName(null, fieldName + "Pid$"), "number", 0, buf, addComma);
            addComma = appendColumn("valPid$", "long", 0, buf, addComma);
            tableOf = systemTable.getTableType().toUpperCase();
            systemTable = (JdbcTable) systemTableMap.get(tableOf);
        }
        // Check to see the type is an array type which therefore will have the 'typeOf' field specified
        /*
        if (systemTable != null && systemTable.tableOf != null) {
            tableOf = systemTable.tableOf.toUpperCase();
            // locate the type of the array
            systemTable = (JdbcTable) systemTableMap.get(tableOf);
        }
         */
        if (systemTable != null) {
            // Handle members
            for (int k = 0; k < systemTable.members.size(); k++) {
                MemberDef sysDef = (MemberDef) systemTable.members.get(k);
                if (sysDef.memberType.equals(JdbcType.DATETIME_TYPE) || sysDef.memberType.equals(JdbcType.BASE_ENTITYREF_TYPE)) {
                    JdbcTable systemMemberTable = (JdbcTable) systemTableMap.get(sysDef.memberType);
                    if (systemMemberTable != null) {
                        // process the columns of the system table used
                        for (int l = 0; l < systemMemberTable.members.size(); l++) {
                            MemberDef sDef = (MemberDef) systemMemberTable.members.get(l);
                            // for ref history, it's possible that a null value to be part of the history
                            if (def.memberType.equals(JdbcType.BASE_ENTITYREF_HIST_TUPLE_TYPE)) {
                                addComma = appendColumn(sDef.memberName, sDef.memberType, sDef.size, buf, addComma, RDBMSType.getHistIdNotNull());
                            } else {
                            	addComma = appendColumn(sDef.memberName, sDef.memberType, sDef.size, buf, addComma);
                        	}
                        }
                    } else {
                        // This should not happen.  It implies the system table definitions are missing
                        addComma = appendColumn(def.memberName + "_ERROR", "String", 444, buf, addComma);
                    }
                } else {
                	if (def.memberType.equals(JdbcType.BASE_ENTITYREF_COL_TYPE)) {
                		addComma = appendColumn(sysDef.memberName, sysDef.memberType, Math.max(sysDef.size, fieldSize), buf, addComma, RDBMSType.getChildIdNotNull());
                	} else {
                		addComma = appendColumn(sysDef.memberName, sysDef.memberType, Math.max(sysDef.size, fieldSize), buf, addComma);
                	}
                }
            }
            // Handle complex members
            for (int k = 0; k < systemTable.complexMembers.size(); k++) {
                MemberDef sysDef = (MemberDef) systemTable.complexMembers.get(k);
                addComma = appendComplexColumn(sysDef, buf, sysDef.memberName, fieldSize, addComma);
            }
        }
        // If the type is not prefix with "T_", it must be a primitive type
        else if (tableOf.indexOf("T_") != 0) {
            // it must be of basic type
            // Not using the fieldName, use 'val' as the field name
            addComma = appendColumn("val", tableOf, def.size, buf, addComma);
        } else {
            addComma = appendColumn(fieldName + "_ERROR", "String", 444, buf, addComma);
        }
        return addComma;
    }

    // Adds simple type columns to the primary table
    private boolean addTableColumns(JdbcTable table, StringBuffer buf, boolean addComma) {
        for (int j = 0; j < table.members.size(); j++) {
            MemberDef def = (MemberDef) table.members.get(j);
            if (table.isAbstract == false) {
                //System.err.println("TABLE:" + table.getName() + ":=" + table.hashCode() + " - " + def);
            }
            if (def.state == JdbcTable.MemberDef.Deleted) {
                // Skip deleted fields (these are added if the user
                // provides a JDBC URL and extra columns are detected)
                continue;
            }
            if (def.memberType.equals(JdbcType.DATETIME_TYPE) || def.memberType.equals(JdbcType.BASE_ENTITYREF_TYPE)) {
                JdbcTable systemTable = (JdbcTable) systemTableMap.get(def.memberType);
                if (systemTable != null) {
                    // process the columns of the system table used
                    for (int k = 0; k < systemTable.members.size(); k++) {
                        MemberDef sysDef = (MemberDef) systemTable.members.get(k);
                        addComma = appendColumn(jdbcUtil.getPDName(null, def.memberName + "_" + sysDef.memberName), sysDef.memberType, sysDef.size, buf, addComma);
                    }
                } else {
                    // This should not happen. It implies the system table definitions are missing
                    addComma = appendColumn(def.memberName + "_ERROR", "String", 444, buf, addComma);
                }
            } else {
                addComma = appendColumn(jdbcUtil.getPDName(null, def.memberName), def.memberType, def.size, buf, addComma);
            }
        }
        return addComma;
    }

    // Create tables for complex types which needs to be in a separate table
    private void createSecondaryTable(JdbcTable table, StringBuffer buf, boolean buildIndexes) {
        for (int j = 0; j < table.complexMembers.size(); j++) {
            StringBuffer secBuf = new StringBuffer();
            StringBuffer secIndexBuf = new StringBuffer();
            MemberDef def = (MemberDef) table.complexMembers.get(j);
            //FIX THIS - May need a better solution is distinguish metric and metricDVM
            //If more places need to access the entity name, we need to handle this better
            if (isDVM) {
                ((Concept) ent).enableMetricTracking();
            }
            String secTableName = null;
            SecondaryTableInfo secTableInfo = getSecondaryTableInfo(def.memberName);

            // TODO: Remove this. Should not ever need this.
            if (secTableInfo == null) {
                String memberAlias = jdbcUtil.getPDName(null, def.memberName);
                secTableInfo = getSecondaryTableInfo(memberAlias);
                if (secTableInfo != null) {
                    System.err.println("Warning: Examine alias created for field "+def.memberName+" as "+memberAlias);
                }
            }

            if (secTableInfo != null) {
                secTableName = secTableInfo.tableName;
            } else {
                // Secondary table information is populated during initialization, except 'rrf$'
                // Associate the information for <ConceptTable>_rrf$ tables here
                secTableName = jdbcUtil.name2JdbcTableWithAlias(ent, def.memberName);
                secTableInfo = new SecondaryTableInfo(secTableName, def.memberName, def.memberName);
                // Expect the <ConceptTable>_rrf$ exists, if <ConceptTable> does
                if (this.state != JdbcTable.State.NEW) {
                    secTableInfo.state = JdbcTable.State.UNCHANGED;
                }
                secondaryTableInfoList.add(secTableInfo);
            }
            if (isDVM) {
                ((Concept) ent).disableMetricTracking();
            }

            secBuf.append(RDBMSType.sSqlType.formatDropTableSql(secTableName) + RDBMSType.sSqlType.getNewline());
            secBuf.append(RDBMSType.sSqlType.getExecuteCommand());

            // This logic should be moved to SqlType classes such that
            // db specific syntax can be added
            secBuf.append("CREATE TABLE " + secTableName + " (");
            boolean addComma = appendColumn("pid$", "long", 0, secBuf, false);
            addComma = appendComplexColumn(def, secBuf, def.memberName, def.size, addComma);
            secBuf.append(")" + RDBMSType.sSqlType.getNewline());
            secBuf.append(RDBMSType.sSqlType.getExecuteCommand());

            if (buildIndexes) {
                String secondaryIndex = getIndexName(secTableName);
                IndexInfo ii = new IndexInfo(secTableName, secondaryIndex, "PID$", false);
                indexInfoList.add(ii);
                secIndexBuf.append(RDBMSType.sSqlType.formatCreateIndexSql(ii.tableName, ii.indexName,
                            ii.indexColumns, ii.isUnique) + RDBMSType.sSqlType.getNewline());
            }
            secTableInfo.tableSql = secBuf.toString();
            secTableInfo.indexSql = secIndexBuf.toString();

            buf.append(secBuf);
        }
    }

    /**
     *
     * @param buildIndexes boolean Setting this flag to true builds the indexes
     *
     * @return
     */
    public StringBuffer toStringBuffer(boolean buildIndexes) {
        StringBuffer ret = new StringBuffer();
        List<JdbcTable> tableList = new ArrayList<JdbcTable>();
        boolean addComma = false;

        // Create a table list of inheritance
        JdbcTable parentTable = this;
        while (parentTable != null) {
            tableList.add(parentTable);
            parentTable = parentTable.getSuperTable();
        }

        // Drop the primary table first
        ret.append(RDBMSType.sSqlType.formatDropTableSql(name) + RDBMSType.sSqlType.getNewline());
        ret.append(RDBMSType.sSqlType.getExecuteCommand());

        // Re-create primary the table
        ret.append("CREATE TABLE " + name + " (");

        addComma = appendColumn("cacheId", "integer", 0, ret, addComma);

        // Create the primary table
        // 1) Create simple columns for all parents attributes

        // do the simple members for each parent table
        for (int i = 0; i < tableList.size(); i++) {
            parentTable = tableList.get(i);
            addComma = addTableColumns(parentTable, ret, addComma);
        }
        ret.append(")" + RDBMSType.sSqlType.getNewline());
        ret.append(RDBMSType.sSqlType.getExecuteCommand());
        // finished creating the primary table

        // Create secondary tables for the parent tables
        // 2) These are the complex columns which need to be
        // put into separate tables
        for (int i = 0; i < tableList.size(); i++) {
            parentTable = tableList.get(i);
            createSecondaryTable(parentTable, ret, buildIndexes);
        }

        // Create necessary constraints
        for (int i = 0; i < constraints.size(); i++) {
            ret.append(constraints.get(i));
            ret.append(RDBMSType.sSqlType.getNewline());
            ret.append(RDBMSType.sSqlType.getExecuteCommand());
        }

        if (buildIndexes) {
            String uniqueIndex = getIndexName();
            IndexInfo ii = new IndexInfo(name, uniqueIndex, "ID$", true);
            indexInfoList.add(ii);
            this.indexCreator = RDBMSType.sSqlType.formatCreateIndexSql(name, uniqueIndex, "ID$", true);
        }
        return ret;
    }

    public StringBuffer toAlterBuffer() {
        StringBuffer ret = new StringBuffer();
        List<JdbcTable> tableList = new ArrayList<JdbcTable>();

        // Create a table list of inheritance
        JdbcTable parentTable = this;
        while (parentTable != null) {
            tableList.add(parentTable);
            parentTable = parentTable.getSuperTable();
        }

        for (int i = 0; i < tableList.size(); i++) {
            parentTable = tableList.get(i);
            alterTableColumns(parentTable, ret);
            alterSecondaryTable(parentTable, ret);
        }
        return ret;
    }

    private void alterTableColumns(JdbcTable table, StringBuffer buf) {
        for (int j = 0; j < table.members.size(); j++) {
            MemberDef def = (MemberDef) table.members.get(j);
            if ((def.state == JdbcTable.MemberDef.Deleted) || (def.state == JdbcTable.MemberDef.Incompatible)) {
                buf.append("-- ALTER TABLE " + name + RDBMSType.sSqlType.getDropColumnCommand());
                buf.append(RDBMSType.sSqlType.getBeginDefinitionMarker());
                buf.append(def.memberName);
                buf.append(RDBMSType.sSqlType.getEndDefinitionMarker());
                buf.append(RDBMSType.sSqlType.getNewline());
                if (RDBMSType.sSqlType.getExecuteCommand().length() > 0) {
                    buf.append("-- " + RDBMSType.sSqlType.getExecuteCommand());
                }
            }
            if ((def.state == JdbcTable.MemberDef.Added) || (def.state == JdbcTable.MemberDef.Incompatible)) {
                if (def.memberType.equals(JdbcType.DATETIME_TYPE) || def.memberType.equals(JdbcType.BASE_ENTITYREF_TYPE)) {
                    JdbcTable systemTable = (JdbcTable) systemTableMap.get(def.memberType);
                    if (systemTable != null) {
                        List systemMembers = systemTable.members;
                        // Process the columns of the system table used
                        for (int k = 0; k < systemMembers.size(); k++) {
                            MemberDef sysDef = (MemberDef) systemMembers.get(k);
                            buf.append("ALTER TABLE " + name + RDBMSType.sSqlType.getAddColumnCommand());
                            buf.append(RDBMSType.sSqlType.getBeginDefinitionMarker());
                            appendColumn(jdbcUtil.getPDName(null, def.memberName + "_" + sysDef.memberName), sysDef.memberType, sysDef.size, buf, false);
                            buf.append(RDBMSType.sSqlType.getEndDefinitionMarker());
                            buf.append(RDBMSType.sSqlType.getNewline());
                            buf.append(RDBMSType.sSqlType.getExecuteCommand());
                        }
                    } else {
                        // This should not happen. It implies the system table definitions are missing
                        buf.append("ALTER TABLE " + name + RDBMSType.sSqlType.getAddColumnCommand());
                        buf.append(RDBMSType.sSqlType.getBeginDefinitionMarker());
                        appendColumn(def.memberName + "_ERROR", "String", 444, buf, false);
                        buf.append(RDBMSType.sSqlType.getEndDefinitionMarker());
                        buf.append(RDBMSType.sSqlType.getNewline());
                        buf.append(RDBMSType.sSqlType.getExecuteCommand());
                    }
                } else {
                    buf.append("ALTER TABLE " + name + RDBMSType.sSqlType.getAddColumnCommand());
                    buf.append(RDBMSType.sSqlType.getBeginDefinitionMarker());
                    appendColumn(jdbcUtil.getPDName(null, def.memberName), def.memberType, def.size, buf, false);
                    buf.append(RDBMSType.sSqlType.getEndDefinitionMarker());
                    buf.append(RDBMSType.sSqlType.getNewline());
                    buf.append(RDBMSType.sSqlType.getExecuteCommand());
                }
            }
            if (def.state == JdbcTable.MemberDef.Altered) {
                // We can support MODIFY (if underlying database supports it)
                // Database support may depend on the existing column values.
                buf.append("ALTER TABLE " + name + RDBMSType.sSqlType.getModifyColumnCommand());
                buf.append(RDBMSType.sSqlType.getBeginDefinitionMarker());
                appendColumn(jdbcUtil.getPDName(null, def.memberName), def.memberType, def.size, buf, false,
                                                        RDBMSType.sSqlType.getColumnModificationTypeCommand(), true);
                buf.append(RDBMSType.sSqlType.getEndDefinitionMarker());
                buf.append(RDBMSType.sSqlType.getNewline());
                buf.append(RDBMSType.sSqlType.getExecuteCommand());

                // Note: We can not support RENAME (since there is no tracking information)
            }
        }
    }

    // Alter tables for complex types which needs to be in a separate table
    /*
    Examples:
    CREATE TABLE CT (pid$ numeric(19), howMany numeric(10), timeIdx timestamp, val numeric(19));
    CREATE TABLE CT (pid$ numeric(19), valPid$ numeric(19), howMany numeric(10), timeIdx timestamp, val numeric(10));
    CREATE TABLE CT (pid$ numeric(19), valPid$ numeric(19), id$ numeric(19) not null);
    CREATE TABLE CT (pid$ numeric(19), valPid$ numeric(19), val numeric(10));
    */
    private void alterSecondaryTable(JdbcTable table, StringBuffer buf) {
        if (table.isAbstract) {
            return; // Abstract table schemas do NOT change
        }
        // System.err.println("COMPLEX-Table:" + table.getDetails());
        List complexMembers = table.complexMembers;
        for (int j = 0; j < complexMembers.size(); j++) {
            StringBuffer secBuf = new StringBuffer();
            MemberDef def = (MemberDef) complexMembers.get(j);
            String secTableName = null;
            SecondaryTableInfo secTableInfo = getSecondaryTableInfo(def.memberName);
            if (secTableInfo != null) {
                secTableName = secTableInfo.tableName;
                if (def.state != JdbcTable.MemberDef.NotChanged) {
                    if (def.state == JdbcTable.MemberDef.SecondaryAlteredCompatible
                            || def.state == JdbcTable.MemberDef.SecondaryAlteredIncompatible) {
                        // this is only for String columns if the column length
                        // changes
                        boolean isCompatible = (def.state == JdbcTable.MemberDef.SecondaryAlteredCompatible) ? true
                                : false;
                        alterComplexColumn(secTableName, def, isCompatible, buf);
                    } else {
                        secBuf.append(
                                RDBMSType.sSqlType.formatDropTableSql(secTableName) + RDBMSType.sSqlType.getNewline());
                        secBuf.append(RDBMSType.sSqlType.getExecuteCommand());
                        if (def.state != JdbcTable.MemberDef.Deleted) {
                            secBuf.append("CREATE TABLE " + secTableName + " (");
                            boolean addComma = appendColumn("pid$", "long", 0, secBuf, false);
                            addComma = appendComplexColumn(def, secBuf, def.memberName, def.size, addComma);
                            secBuf.append(")" + RDBMSType.sSqlType.getNewline());
                            secBuf.append(RDBMSType.sSqlType.getExecuteCommand());

                            String secondaryIndex = getIndexName(secTableName);
                            IndexInfo ii = new IndexInfo(secTableName, secondaryIndex, "PID$", false);
                            indexInfoList.add(ii);
                            secBuf.append(RDBMSType.sSqlType.formatCreateIndexSql(ii.tableName, ii.indexName,
                                    ii.indexColumns, ii.isUnique) + RDBMSType.sSqlType.getNewline());
                            secTableInfo.alterAdded = true;
                        }
                    }
                }
            }
            buf.append(secBuf);
        }
    }

    private void alterComplexColumn(String secTableName, MemberDef def, boolean isCompatible, StringBuffer secondaryAlterBuffer) {
        //StringBuffer secondaryAlterBuffer = new StringBuffer();
        JdbcTable systemTable = (JdbcTable) systemTableMap.get(def.memberType);
        List complexMembers = systemTable.getComplexMembers();
        for (int k = 0; k < complexMembers.size(); k++) {
            MemberDef sysDef = (MemberDef) complexMembers.get(k);
            JdbcTable innerTable = (JdbcTable) systemTableMap.get(sysDef.memberType);
            List innerMembers = innerTable.getMembers();
            for (int l = 0; l < innerMembers.size(); l++) {
                MemberDef innerDef = (MemberDef) innerMembers.get(l);
                if (innerDef.memberType.equals(JdbcType.TYPE_STRING.getName())) {
                    if (!isCompatible) {
                        secondaryAlterBuffer
                                .append("-- ALTER TABLE " + secTableName + RDBMSType.sSqlType.getDropColumnCommand());
                        secondaryAlterBuffer.append(RDBMSType.sSqlType.getBeginDefinitionMarker());
                        secondaryAlterBuffer.append(innerDef.memberName);
                        secondaryAlterBuffer.append(RDBMSType.sSqlType.getEndDefinitionMarker());
                        secondaryAlterBuffer.append(RDBMSType.sSqlType.getNewline());
                        if (RDBMSType.sSqlType.getExecuteCommand().length() > 0) {
                            secondaryAlterBuffer.append("-- " + RDBMSType.sSqlType.getExecuteCommand());
                        }
                        // Add the column
                        secondaryAlterBuffer
                                .append("ALTER TABLE " + secTableName + RDBMSType.sSqlType.getAddColumnCommand());
                        secondaryAlterBuffer.append(RDBMSType.sSqlType.getBeginDefinitionMarker());
                        appendColumn(innerDef.memberName, innerDef.memberType, def.size, secondaryAlterBuffer,
                                false);
                        secondaryAlterBuffer.append(RDBMSType.sSqlType.getEndDefinitionMarker());
                        secondaryAlterBuffer.append(RDBMSType.sSqlType.getNewline());
                        secondaryAlterBuffer.append(RDBMSType.sSqlType.getExecuteCommand());
                    } else {
                        // If the length change is greater than the current
                        // length
                        // then do the alter instead of drop
                        // We can support MODIFY (if underlying database
                        // supports
                        // it)
                        // Database support may depend on the existing column
                        // values.
                        secondaryAlterBuffer
                                .append("ALTER TABLE " + secTableName + RDBMSType.sSqlType.getModifyColumnCommand());
                        secondaryAlterBuffer.append(RDBMSType.sSqlType.getBeginDefinitionMarker());
                        appendColumn(innerDef.memberName, innerDef.memberType, def.size, secondaryAlterBuffer,
                                false, RDBMSType.sSqlType.getColumnModificationTypeCommand(), true);
                        secondaryAlterBuffer.append(RDBMSType.sSqlType.getEndDefinitionMarker());
                        secondaryAlterBuffer.append(RDBMSType.sSqlType.getNewline());
                        secondaryAlterBuffer.append(RDBMSType.sSqlType.getExecuteCommand());
                    }
                }
            }
        }
    }

    public String getIndexName() {
        if (indexName == null) {
            indexName = getMangledIndexName(this.name);
        }
        return indexName;
    }

    public String getIndexName(String tableName) {
        return getMangledIndexName(tableName);
    }

    public List getSecondaryTableInfoList() {
        return secondaryTableInfoList;
    }

    public SecondaryTableInfo getSecondaryTableInfo(String memberName) {
        for (int j = 0; j < secondaryTableInfoList.size(); j++) {
            SecondaryTableInfo secInfo = secondaryTableInfoList.get(j);
            if (secInfo.fieldName.equals(memberName)) {
                return secInfo;
            }
            if (secInfo.aliasName.equals(memberName)) {
                return secInfo;
            }
        }
        return null;
    }

    public StringBuffer indexToStringBuffer() {
        StringBuffer ret = new StringBuffer();

        if (this.indexCreator == null) {
            toStringBuffer(true);
        }

        for (int j = 0; j < indexInfoList.size(); j++) {
            IndexInfo ii = indexInfoList.get(j);
            ret.append(RDBMSType.sSqlType.formatCreateIndexSql(ii.tableName, ii.indexName,
                ii.indexColumns, ii.isUnique) + RDBMSType.sSqlType.getNewline());
            ret.append(RDBMSType.sSqlType.getExecuteCommand());
        }
        return ret;
    }

    private String getMangledIndexName(String tablename) {
        String indexName = "i_" + tablename;
        // FIX THIS - Need to be db specific
        if (indexName.length() > 30) {
            indexName = indexName.substring(0, 29) + "0";
        }
        int co = 0;
        while (indexNameSet.contains(indexName.toUpperCase())) {
        	if (co < 10) {
            	indexName = indexName.substring(0, Math.min(indexName.length(), 29)) + co++;
        	} else {
        		indexName = indexName.substring(0, Math.min(indexName.length(), 28)) + co++;
        	}
        }
        indexNameSet.add(indexName.toUpperCase());
        return indexName;
    }

    public String toString() {
        return toStringBuffer(true).toString();
    }

    public String getDetails() {
        return this.getName() + ":= " + this.hashCode() + "\n\t" + members + "\n\t" + complexMembers;
    }
    
    public static class MemberDef {

        public static final int Deleted =1;
        public static final int Added =2;
        public static final int Altered =3;
        public static final int Incompatible =4;
        public static final int NotChanged =5;
        //Only set for string columns in the secondary table
        public static final int SecondaryAlteredCompatible =6;
        public static final int SecondaryAlteredIncompatible =7;
        

        public static final MemberDef CACHEID = new MemberDef("cacheId", "INTEGER", false, 0, NotChanged);

        public String memberName;
        public String memberType;
        public boolean isArray = false;
        public int size = 0;
        public int state;

        public MemberDef(String name, String type) {
            this.memberName = name;
            this.memberType = type;
            this.state = Added;
        }
        
        public MemberDef(String name, String type, int size) {
            this(name, type);
            this.size = size;
        }

        public MemberDef(String name, String type, boolean isArray, int size, int state) {
            this.memberName = name; //JdbcUtil.getInstance().getPDName(null, name);
            this.memberType = type;
            this.isArray = isArray;
            this.size = size;
            this.state = state;
            validate(false);
        }

        public String toString() {
            return "Member [Name=" + this.memberName +
                          ",Type=" + this.memberType +
                          ",State=" + stateName(this.state) +
                          ",Size=" + this.size +"]";
        }

        private String stateName(int state) {
            switch(state) {
            case 1: return "Deleted";
            case 2: return "Added";
            case 3: return "Altered";
            case 4: return "Incompatible";
            default: return "NotChanged";
            }
        }

        private void validate(boolean debug) {
            // Used for debugging...
        }
    }

    class IndexInfo {
        String tableName;
        String indexName;
        String indexColumns;
        boolean isUnique;

        IndexInfo(String table, String index, String columns, boolean unique) {
            tableName = table;
            indexName = index;
            indexColumns = columns;
            isUnique = unique;
        }
    }

    class SecondaryTableInfo implements Comparable {
        public boolean alterAdded = false;
		String tableName;
        String fieldName;
        String aliasName;
        String tableSql;
        String indexSql;
        State state;

        SecondaryTableInfo(String table, String field, String alias) {
            tableName = table;
            fieldName = field;
            aliasName = alias;
            tableSql = "";
            indexSql = "";
            state = JdbcTable.State.NEW;
        }

        public int compareTo(Object o) {
            return fieldName.compareTo(((SecondaryTableInfo)o).fieldName);
        }
    }
}
