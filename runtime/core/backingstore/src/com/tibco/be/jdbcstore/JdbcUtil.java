package com.tibco.be.jdbcstore;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.be.util.BEProperties;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.element.stategraph.StateMachine;
import com.tibco.cep.designtime.model.event.EventPropertyDefinition;

public class JdbcUtil {

    public final static String PROP_CACHEMODE = "mode";
    public final static String PROP_HASBACKINGSTORE = "hasBackingStore";
    public final static String PROP_NESTEDTABLENAME = "nestedTableName";
    public final static String PROP_TABLENAME = "tableName";
    public final static String PROP_PROPERTYNAME = "property";
    public final static String PROP_TYPENAME = "type";
    public final static String PROP_COLUMNNAME = "columnName";
    public final static String PROP_MAXLENGTH = "maxLength";
    public final static String PROP_MAXSIZE = "maxSize";

    private Map<String,String> classNameToJdbcTable = new HashMap<String,String>();
    private Map<String,String> jdbcTableToClassName = new HashMap<String,String>();

    private Properties aliases = new Properties();
    private BEProperties properties = null;

    public static JdbcUtil _instance = new JdbcUtil();

    public static JdbcUtil getInstance() {
        return _instance;
    }

    public void setProperties(BEProperties props) {
        this.properties = props;
    }

    public void setJdbcTableToClassNameFromDB(Map<String,String> map) {
        jdbcTableToClassName.putAll(map);
    }

    public Map<String,String> getClassNameToJdbcTable() {
        return classNameToJdbcTable;
    }

    public String getAssignedJdbcTable(Entity entity) {
        String beClz = ModelNameUtil.modelPathToGeneratedClassName(entity.getFullPath());
        return classNameToJdbcTable.get(beClz);
    }

    public Properties getAliases() {
        return aliases;
    }

    public void setAliases(Properties props) {
        aliases = props;
    }

    public String mangleJdbcType(Entity entity, String jdbcType) {
    	String aliasJdbcType = null;
    	if (jdbcType.length() > RDBMSType.sSqlType.getMaxIdentifierLength()) {
            aliasJdbcType=(String)JdbcUtil.getInstance().getAliases().get("TABLE." + jdbcType +".alias");
        }
    	String beClass = null;
    	if(aliasJdbcType==null){
    		beClass = jdbcTableToClassName.get(jdbcType.toUpperCase());
    	}else{
    		beClass = jdbcTableToClassName.get(aliasJdbcType.toUpperCase());
    	}
        String beClz = ModelNameUtil.modelPathToGeneratedClassName(entity.getFullPath());
        if (beClass != null) {
            if (beClz.equals(beClass)) {
                return jdbcType;
            } else {
                return mangleJdbcType(entity, jdbcType + "$");
            }
        }
        jdbcTableToClassName.put(jdbcType.toUpperCase(), beClz);
        return jdbcType;
    }

    public String name2JdbcType(Entity cept) {
        return "D_" + cept.getName().replaceAll("\\.", "\\$");
    }

    public String name2JdbcTable(String jdbcType) {
        return "D" + jdbcType.substring(1).replaceAll("\\.", "\\$");
    }

    /* This method is used for JDBC backing store - creates primary table names */
    public String name2JdbcTable(Entity cept) {
        String name = null;
        boolean isSM = false;

        if (cept instanceof StateMachine) {
            // FIX THIS VWC - Will there always be a owner concept??
            // If not, we need to make sure the SM name is unique
            Entity ownerCept = ((StateMachine)cept).getOwnerConcept();
            String ownerName = null;
            name = cept.getName();
            ownerName = this.mangleJdbcType(ownerCept, name2JdbcType(ownerCept));
            name = ownerName + "$" + name;
            isSM = true;
        } else {
            name = this.mangleJdbcType(cept, name2JdbcType(cept));
        }

        String ret = name2JdbcTable(name);
        String alias = getJdbcTableAlias(cept, ret);
        String projectValue = getEntityDeploymentProperty(cept, JdbcUtil.PROP_TABLENAME, null);
        String tableName = null;

        if (ret.equalsIgnoreCase(alias)) {
            if ((projectValue != null) && (projectValue.trim().length() > 0)) {
                tableName = projectValue;
            } else {
                tableName = ret;
            }
        } else {
            tableName = alias;
        }
        if (tableName.length() > RDBMSType.sSqlType.getMaxIdentifierLength()) {
            tableName = NameUtil.mangleName(tableName, RDBMSType.sSqlType.getMaxIdentifierLength()).toString();
            setJdbcTableAliasWithValue(cept, ret, tableName);
        }
        String beClz = null;
        if (isSM) {
            StateMachine sm = (StateMachine) cept;
            beClz = ModelNameUtil.modelPathToGeneratedClassName(sm.getOwnerConcept().getFullPath()) + "$" + sm.getOwnerConcept().getName() + "$" + sm.getName();
        }
        else {
            beClz = ModelNameUtil.modelPathToGeneratedClassName(cept.getFullPath());
        }
        classNameToJdbcTable.put(beClz, tableName);
        jdbcTableToClassName.put(tableName.toUpperCase(), beClz);
        return tableName;
    }

    public String name2JdbcTable(Entity entity, String jdbcType) {
        String ret = name2JdbcTable(jdbcType);
        String alias = getJdbcTableAlias(entity, ret);
        String projectValue = getEntityDeploymentProperty(entity, JdbcUtil.PROP_TABLENAME, null);
        String tableName = null;

        if (ret.equalsIgnoreCase(alias)) {
            if ((projectValue != null) && (projectValue.trim().length() > 0)) {
                tableName = projectValue;
            } else {
                tableName = ret;
            }
        } else {
            tableName = alias;
        }

        if (tableName.length() > RDBMSType.sSqlType.getMaxIdentifierLength()) {
            tableName = NameUtil.mangleName(tableName, RDBMSType.sSqlType.getMaxIdentifierLength()).toString();
            setJdbcTableAliasWithValue(entity, ret, tableName);
        }
        String beClz = ModelNameUtil.modelPathToGeneratedClassName(entity.getFullPath());
        classNameToJdbcTable.put(beClz, tableName);
        return tableName;
    }

    /* This method is used for JDBC backing store - creates secondary table names */
    public String name2JdbcTableWithAlias(Entity cept, String memberName) {
        String name = null;
        if (cept instanceof StateMachine) {
            // FIX THIS VWC - Will there always be a owner concept??
            // If not, we need to make sure the SM name is unique
            Entity ownerCept = ((StateMachine)cept).getOwnerConcept();
            String ownerName = null;
            name = cept.getName();
            ownerName = this.mangleJdbcType(ownerCept, name2JdbcType(ownerCept));
            name = ownerName + "$" + name;
        } else {
            name = this.mangleJdbcType(cept, name2JdbcType(cept));
        }

        name = name + "_" + memberName;
        String ret = name2JdbcTable(name);
        String alias = getJdbcTableAlias(cept, ret);
        String projectValue = getEntityDeploymentProperty(cept, JdbcUtil.PROP_TABLENAME, null);
        String tableName = null;

        if (ret.equalsIgnoreCase(alias)) {
            if ((projectValue != null) && (projectValue.trim().length() > 0)) {
                tableName = projectValue + "_" + memberName;
            } else {
                tableName = ret;
            }
        } else {
            tableName = alias;
        }

        if (tableName.length() > RDBMSType.sSqlType.getMaxIdentifierLength()) {
            int tryCnt = 0;
            String tryText = "";
            do {
            	tableName = NameUtil.mangleName(tableName+tryText, RDBMSType.sSqlType.getMaxIdentifierLength()).toString();
                tryText = String.valueOf(tryCnt++);
                if (tryCnt > 1024) {
                    System.err.println("##### WARNING: Failed to generate unique alias for table " + name);
                    System.err.println("  Examine aliases created and provide one manually for " + name);
                    return name;
                }
            } 
			while (setJdbcTableAliasWithValue(null, ret, tableName));
        }
        return tableName;
    }

    /**
     * Validates and substitutes property names, and returns
     * alias value if a substitution was actually performed.
     */
    public String getPDName(Entity entity, String name) {
        String pdName = null;
        String dbKeyword = (String) RDBMSType.sDBKeywordMap.get(name.toLowerCase());
        if (dbKeyword != null) {
            pdName = getPropertyAlias(entity, name, dbKeyword);
            setPropertyAliasWithValue(entity, name, pdName);
        }
        else {
            pdName = getPropertyAlias(entity, name, name);
        }
        String dbSanitized = RDBMSType.sSqlType.sanitizeNameIfNeeded(pdName);
        if (dbSanitized != null) {
            pdName = getPropertyAlias(entity, name, dbSanitized);
            setPropertyAliasWithValue(entity, name, pdName);
        }
        if (pdName.length() > RDBMSType.sSqlType.getMaxIdentifierLength()) {
            int tryCnt = 0;
            String tryText = "";
            do {
                pdName = NameUtil.mangleName(name+tryText, RDBMSType.sSqlType.getMaxIdentifierLength()).toString();
                tryText = String.valueOf(tryCnt++);
                if (tryCnt > 1024) {
                    System.err.println("##### WARNING: Failed to generate unique alias for field " + name);
                    System.err.println("  Examine aliases created and provide one manually for " + name);
                    return name;
                }
            } 
            while (setPropertyAliasWithValue(entity, name, pdName));
        }
        return pdName;
    }

    // Used during runtime to resolve field name mapped to column
    public String getPDName(String name, Map<String,String> aliasMap) {
        String pdName = aliasMap.get("COLUMN." + name + ".alias");
        if (pdName != null) {
            return pdName;
        }
        return name;
    }

    public String getSMPDName(StateMachine sm) {
        String ret=sm.getOwnerConcept().getName() + "$" + sm.getName();
        return ret;
    }

    private String getPropertyAlias(Entity entity, String name, String defaultValue) {
        // FIX THIS - Need to add logic to use the keyword maps to first transform the
        // name to replace the defaultValue
        //return aliases.getProperty(entity.getFullPath() + ".PROPERTY." + name + ".alias", defaultValue);
        return aliases.getProperty("COLUMN." + name + ".alias", defaultValue);
    }

    // Used by database keyword mapping only
    private boolean setPropertyAliasWithValue(Entity entity, String column, String value) {
        if (aliases.getProperty("COLUMN." + column + ".alias") == null) {
            if (aliases.containsValue(value)) {
                return true;
            }
            //aliases.setProperty(entity.getFullPath() + ".PROPERTY." + column + ".alias", name);
            aliases.setProperty("COLUMN." + column + ".alias", value);
        }
        return false;
    }

    private String getJdbcTableAlias(Entity entity, String defaultValue) {
        //return aliases.getProperty(entity.getFullPath() + ".TABLE.alias", defaultValue);
        return aliases.getProperty("TABLE." + defaultValue + ".alias", defaultValue);
    }

    private boolean setJdbcTableAliasWithValue(Entity entity, String table, String value) {
        if (aliases.getProperty("TABLE." + table + ".alias") == null) {
            if (aliases.containsValue(value)) {
                return true;
            }
            aliases.setProperty("TABLE." + table + ".alias", value);
        }
    	return false;
    }

    public String getNonAliasedName(String type, String aliasedName) {
        Enumeration propertyNames = aliases.propertyNames();
        while (propertyNames.hasMoreElements()) {
            String property = (String)propertyNames.nextElement();
            String value = aliases.getProperty(property);
            if (value.equalsIgnoreCase(aliasedName)) {
                if (property.startsWith(type)) {
                    int start = type.length() + 1;
                    int end = property.length() - ".alias".length();
                    String result = property.substring(start, end);
                    return result;
                }
            }
        }
        return aliasedName;
    }

    public String getEntityDeploymentProperty(Entity entity, String property, String defaults) {
        String fulldesc = "be.engine.cluster." +
            ModelNameUtil.modelPathToGeneratedClassName(entity.getFullPath()) + "." + property;
        if ((this.properties.getProperty(fulldesc) != null)&&(this.properties.getProperty(fulldesc).isEmpty() == false)) {
            //System.err.println(fulldesc + "=" + this.properties.getProperty(fulldesc));
            return this.properties.getString(fulldesc);
        } else {
            return defaults;
        }
    }

    public String getAttributeDeploymentProperty(PropertyDefinition attribute, String property, String defaults) {
        if (attribute.getOwner() == null) {
            //System.err.println("Parent concept is not found for " + attribute.getFullPath());
            return defaults;
        }
        String fulldesc = "be.engine.cluster." +
            ModelNameUtil.modelPathToGeneratedClassName(attribute.getOwner().getFullPath()) +
            ".property." + attribute.getName() + "." + property;
        if ((this.properties.getProperty(fulldesc) != null)&&(this.properties.getProperty(fulldesc).isEmpty() == false)) {
            //System.err.println(fulldesc + "=" + this.properties.getProperty(fulldesc));
            return this.properties.getString(fulldesc);
        } else {
            return defaults;
        }
    }

    public String getAttributeDeploymentProperty(EventPropertyDefinition attribute, String property, String defaults) {
        if (attribute.getOwner() == null) {
            //System.err.println("Parent event is not found for " + attribute.getPropertyName());
            return defaults;
        }
        String fulldesc = "be.engine.cluster." +
            ModelNameUtil.modelPathToGeneratedClassName(attribute.getOwner().getFullPath()) +
            ".property." + attribute.getPropertyName() + "." + property;
        if ((this.properties.getProperty(fulldesc) != null)&&(this.properties.getProperty(fulldesc).isEmpty() == false)) {
            //System.err.println(fulldesc + "=" + this.properties.getProperty(fulldesc));
            return this.properties.getString(fulldesc);
        } else {
            return defaults;
        }
    }

    void clear() {
    	this.aliases.clear();
    	this.classNameToJdbcTable.clear();
    	this.jdbcTableToClassName.clear();
    	this.properties.clear();
    }
}
