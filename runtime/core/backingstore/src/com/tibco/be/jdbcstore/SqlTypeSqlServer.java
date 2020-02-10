package com.tibco.be.jdbcstore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.tibco.be.model.rdf.RDFTypes;

public class SqlTypeSqlServer extends SqlType {

    public int getMaxIdentifierLength() {
        return 128;
    }

    public String getExecuteCommand() {
        return "go" + getNewline();
    }

    public String getCommitCommand() {
        return "go" + getNewline();
    }
    
    public String getNewline() {
        return com.tibco.be.parser.codegen.CGConstants.BRK;
    }

    public String formatCreateIndexSql(String tableName, String indexName, String columns, boolean isUnique) {
        if (isUnique) {
            String primaryKeyName = indexName.replaceFirst("i_", "pk_");
            return "ALTER TABLE " + tableName + " ADD CONSTRAINT " + primaryKeyName + " PRIMARY KEY (" + columns + ")";
        } else {
            return "CREATE INDEX " + indexName + " ON " + tableName + "(" + columns + ")";
        }
    }
    
    public String formatDropIndexSql(String tableName, String indexName, String columns, boolean isUnique) {
        if (isUnique) {
            String primaryKeyName = indexName.replaceFirst("i_", "pk_");
            return "ALTER TABLE " + tableName + " DROP CONSTRAINT " + primaryKeyName;
        } else {
            return "DROP INDEX " + indexName + " ON " + tableName;
        }
    }

    public String formatObjectTableDeleteTimeCondition() {
        return "SELECT CONVERT(DECIMAL, DATEDIFF(ss,'Jan 2 1970', current_timestamp)) * 1000";
    }

    @Override
    public List getMatchingTypes(String primitiveType) {
        BLOB_TYPES = Arrays.asList(new String[] { "IMAGE" });
        BOOLEAN_TYPES = Arrays.asList(new String[] { "BIT" });
        TIMESTAMP_TYPES = Arrays.asList(new String[] { "DATETIME" });
        return super.getMatchingTypes(primitiveType);
    }

    @Override
    public List getCompatibleTypes(String primitiveType) {
        return super.getCompatibleTypes(primitiveType);
    }

    public int getSqlTypeForPrimitiveType(int primitiveTypeIntId) {
        switch (primitiveTypeIntId) {
        case RDFTypes.BOOLEAN_TYPEID:
            return java.sql.Types.INTEGER;
        case RDFTypes.INTEGER_TYPEID:
            return java.sql.Types.INTEGER;
        case RDFTypes.LONG_TYPEID:
            return java.sql.Types.BIGINT;
        case RDFTypes.DOUBLE_TYPEID:
            return java.sql.Types.DOUBLE;
        case RDFTypes.STRING_TYPEID:
            return java.sql.Types.VARCHAR;
            /* This is mapped to a complex type
            case RDFTypes.DATETIME_TYPEID:
                return java.sql.Types.TIMESTAMP;
             */
        default:
            System.err.println("Unsupported type integer Id '" + String.valueOf(primitiveTypeIntId) + "' to convert to SQL data type.");
            return java.sql.Types.VARCHAR;
        }
    }

    public String getColumnTypeForPrimitiveType(String primitiveType, long size) {
        // FIX THIS - Need to add logic to create clob/varchar based on size
        if (RDBMSType.sUseAnsiType) {
            if (primitiveType.equalsIgnoreCase("String") && size > 8000) {
                return ("varchar(max)");
            } else if (primitiveType.equalsIgnoreCase("blob")) {
                return ("varbinary(max)");
            } else if (primitiveType.equalsIgnoreCase("timestamp")) {
                return ("datetime");
            }

            return RDBMSType.sSqlTypeAnsi.getColumnTypeForPrimitiveType(primitiveType, size);
        }
        if (primitiveType.equalsIgnoreCase("String")) {
            if (size <= 1)
                return ("varchar(255)");
            if (size > 8000)
                return ("text");
            return ("varchar(" + size + ")");
        }
        else if (primitiveType.equalsIgnoreCase("Integer")) {
            return ("integer");
        }
        else if (primitiveType.equalsIgnoreCase("long")) {
            return ("number(19,0)"); // TODO: Fix this even Integer is number(38)
        }
        else if (primitiveType.equalsIgnoreCase("double")) {
            return ("double precision");
        }
        else if (primitiveType.equalsIgnoreCase("boolean")) {
            return ("tinyint");
        }
        else if (primitiveType.equalsIgnoreCase("blob")) {
            return ("blob");
        }
        else if (primitiveType.equalsIgnoreCase("timestamp")) {
            return ("timestamp(3)");
        }
        else if (primitiveType.equalsIgnoreCase("char")) {
            return ("char(1)");
        }
        else {
            System.err.println("Unsupported type '" + primitiveType + "' to convert to SQL column type.");
            return "varchar(128)";
        }
    }

    public String getColumnTypeForPrimitiveType(int primitiveTypeIntId, long size) {
        switch (primitiveTypeIntId) {
        case RDFTypes.BOOLEAN_TYPEID:
            return ("tinyint");
        case RDFTypes.INTEGER_TYPEID:
            return ("integer");
        case RDFTypes.LONG_TYPEID: // assume signed value
            return ("numeric(19,0)"); // TODO: Fix this even Integer is number(38)
        case RDFTypes.DOUBLE_TYPEID:
            return ("double precision");
        case RDFTypes.STRING_TYPEID:
            if (size <= 1)
                return ("varchar(128)");
            if (size >= 4000)
                return ("text");
            return ("varchar(" + size + ")");
        default:
            System.err.println("Unsupported type integer Id '" + String.valueOf(primitiveTypeIntId) + "' to convert to SQL column type.");
            return "varchar(128)";
        }
    }

    /**
     * If underlying database has name restrictions for the default column-name;
     * then sanitize the column name and return a valid one.
     * If the default name is acceptable, then return 'null' to signal no change
     * was necessary.
     * Note: Ignore name-length restrictions here, since that is checked later.
     */
    public String sanitizeNameIfNeeded(String columnName) {
        return null;
    }

    private static HashMap<String,Integer> sequences = new HashMap<String,Integer>();
    
    @Override
    public String getSequenceCheckQuery(String sequenceName, int increment) {
        sequences.put(sequenceName, increment);
        return "SELECT cacheIdGeneratorName FROM CacheIds WHERE cacheIdGeneratorName='" + sequenceName + "'";
    }

    @Override
    public String getSequenceCreateQuery(String sequenceName, long minValue, long maxValue, long start, int increment) {
        sequences.put(sequenceName, increment);
        return "INSERT INTO CacheIds (cacheIdGeneratorName, nextCacheId) VALUES ('" + sequenceName + "', '" + start + "')";
    }

    @Override
    public String getSequenceDropQuery(String sequenceName) {
        return "DELETE FROM CacheIds WHERE cacheIdGeneratorName='" + sequenceName + "'";
    }

    @Override
    public String getSequenceNextQuery(String sequenceName, boolean forUpdate) {
        int increment = 100;
        if (sequences.containsKey(sequenceName)) {
            increment = sequences.get(sequenceName);
        }
        if (forUpdate) {
            return "UPDATE CacheIds SET nextCacheId=" + increment + 
                    " + (SELECT nextCacheId FROM CacheIds WHERE cacheIdGeneratorName='" + sequenceName + "')" +
                    " WHERE cacheIdGeneratorName='" + sequenceName + "'";
        }
        return "SELECT nextCacheId FROM CacheIds WHERE cacheIdGeneratorName='" + sequenceName + "'";
    }

    /*
     * Provides hints for MSQL to optimize operations
     */
    public String optimizeInsertStatement(String original) {
    	if ((optimizeWrites == false) || (original.toLowerCase().startsWith("insert") == false)) {
    		return original;
    	}
		return original;
    }

    public String optimizeSelectStatement(String original) {
    	if ((optimizeReads == false) || (original.toLowerCase().startsWith("select") == false)) {
    		return original;
    	}
    	int whereCls = original.toLowerCase().indexOf(" where ");
    	int orderCls = original.toLowerCase().indexOf(" order by ");
    	if (whereCls > 0) {
    		return original.substring(0, whereCls) + " with (nolock) " + original.substring(whereCls);
    	} else if (orderCls > 0) {
    		return original.substring(0, orderCls) + " with (rowlock) " + original.substring(orderCls);
    	} else {
        	return original + " with (nolock) ";
    	}
    }

    public String optimizeUpdateStatement(String original) {
    	if ((optimizeWrites == false) || (original.toLowerCase().startsWith("update") == false)) {
    		return original;
    	}
    	int setCls = original.toLowerCase().indexOf(" set ");
    	if (setCls > 0) {
    		return original.substring(0, setCls) + " with (rowlock) " + original.substring(setCls);
    	} else {
        	return original + " with (nolock) ";
    	}
    }
    
    public String optimizeDeleteStatement(String original) {
    	if ((optimizeWrites == false) || (original.toLowerCase().startsWith("delete") == false)) {
    		return original;
    	}
    	int whereCls = original.toLowerCase().indexOf(" where ");
    	int orderCls = original.toLowerCase().indexOf(" order by ");
    	if (whereCls > 0) {
    		return original.substring(0, whereCls) + " with (rowlock) " + original.substring(whereCls);
    	} else if (orderCls > 0) {
    		return original.substring(0, orderCls) + " with (rowlock) " + original.substring(orderCls);
    	} else {
        	return original + " with (nolock) ";
    	}
    }
    
    public boolean optimizable() {
        return (optimizeReads || optimizeWrites);
    }
}
