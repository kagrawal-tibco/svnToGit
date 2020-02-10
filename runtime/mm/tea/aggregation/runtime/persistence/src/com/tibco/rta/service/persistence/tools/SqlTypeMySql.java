package com.tibco.rta.service.persistence.tools;

import com.tibco.rta.model.DataType;

public class SqlTypeMySql extends SqlType{

	@Override
    public int getMaxIdentifierLength() {
	    return 64;
    }

	@Override
    public int getSqlTypeForPrimitiveType(DataType primitiveType) {
        switch (primitiveType) {
        case BOOLEAN:
            return java.sql.Types.CHAR;
        case INTEGER:
            return java.sql.Types.INTEGER;
        case LONG:
            return java.sql.Types.BIGINT;
        case DOUBLE:
            return java.sql.Types.DOUBLE;
        case STRING:
            return java.sql.Types.VARCHAR;
        case SHORT:
        	return java.sql.Types.SMALLINT;
        case BYTE:
        	return java.sql.Types.TINYINT;
        case CLOB:
        	return java.sql.Types.CLOB;
            /*
             * This is mapped to a complex type case RDFTypes.DATETIME_TYPEID:
             * return java.sql.Types.TIMESTAMP;
             */
        default:
            System.err.println("Unsupported type integer Id '" + String.valueOf(primitiveType) + "' to convert to SQL data type.");
            return java.sql.Types.VARCHAR;
        }
    }

	@Override
    public String getColumnTypeForPrimitiveType(String primitiveType, long size) {
		if (RDBMSType.sUseAnsiType) {
            if (primitiveType.equalsIgnoreCase("String") && size > 4000) {
                primitiveType = "clob";
            }
            return RDBMSType.sSqlTypeAnsi.getColumnTypeForPrimitiveType(primitiveType, size);
        }
        if (primitiveType.equalsIgnoreCase("String")) {
            if (size <= 1)
                return ("varchar(255)");
            if (size > 4000)
                return ("text");
            return ("varchar(" + size + ")");
        }
        else if (primitiveType.equalsIgnoreCase("Integer")) {
            return ("integer");
        }
        else if (primitiveType.equalsIgnoreCase("long")) {
            return ("bigint"); 
        }
        else if (primitiveType.equalsIgnoreCase("double")) {
            return ("float");
        }
        else if (primitiveType.equalsIgnoreCase("short")) {
            return ("smallint");
        }
        else if (primitiveType.equalsIgnoreCase("byte")) {
            return ("tinyint");
        }
        else if (primitiveType.equalsIgnoreCase("boolean")) {
            return ("char(1)");
        }
        else if (primitiveType.equalsIgnoreCase("blob")) {
            return ("blob");
        }
        else if (primitiveType.equalsIgnoreCase("clob")) {
            return ("text");
        }
        else if (primitiveType.equalsIgnoreCase("timestamp")) {
            return ("timestamp"); 
        }
        else if (primitiveType.equalsIgnoreCase("char")) {
            return ("char(1)");
        }
        else {
            System.err.println("Unsupported type '" + primitiveType + "' to convert to SQL column type.");
            return "varchar(128)";
        }
    }

	@Override
    public String getColumnTypeForPrimitiveType(DataType primitiveType, long size) {
		 switch (primitiveType) {
		        case BOOLEAN:
		            return ("char(1)");
		        case INTEGER:
		            return ("integer");
		        case LONG: 
		            return ("bigint"); 
		        case DOUBLE:
		            return ("double");
		        case STRING:
		            if (size <= 1)
		                return ("varchar2(256)");
		            if (size >= 4000)
		                return ("text");
		            return ("varchar2(" + size + ")");
		        case SHORT:
		        	return("smallint");
		        case BYTE:
		        	return("tinyint");
		        case CLOB:
		        	return ("text");
		        default:
		            System.err.println("Unsupported type integer Id '" + String.valueOf(primitiveType) + "' to convert to SQL column type.");
		            return "varchar2(128)";
		        }
    }

	@Override
    public String sanitizeNameIfNeeded(String columnName) {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
    public String getSequenceCheckQuery(String sequenceName, int increment) {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
    public String getSequenceCreateQuery(String sequenceName, long minValue, long maxValue, long start, int increment) {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
    public String getSequenceDropQuery(String sequenceName) {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
    public String getSequenceNextQuery(String sequenceName, boolean forUpdate) {
	    // TODO Auto-generated method stub
	    return null;
    }

}
