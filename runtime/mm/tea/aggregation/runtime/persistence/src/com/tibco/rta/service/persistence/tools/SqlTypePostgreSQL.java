package com.tibco.rta.service.persistence.tools;

import com.tibco.rta.model.DataType;

public class SqlTypePostgreSQL extends SqlType {

	@Override
	public int getMaxIdentifierLength() {
		return 63;
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
        case SHORT:
            return java.sql.Types.SMALLINT;
        case BYTE:
            return java.sql.Types.SMALLINT;			//postgres doesn't support TINYINT 
        case DOUBLE:
            return java.sql.Types.DOUBLE;
        case STRING:
            return java.sql.Types.VARCHAR;
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
        else if (primitiveType.equalsIgnoreCase("boolean")) {
            return ("char(1)");
        }
        else if (primitiveType.equalsIgnoreCase("blob")) {
            return ("bytea");
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
        else if (primitiveType.equalsIgnoreCase("short")) {
            return ("smallint");
        }
        else if (primitiveType.equalsIgnoreCase("byte")) {
            return ("smallint");
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
	            return ("float");
	        case STRING:
	            if (size <= 1)
	                return ("varchar2(256)");
	            if (size >= 4000)
	                return ("text");
	            return ("varchar2(" + size + ")");
	        case SHORT:
	        	return ("smallint");
	        case BYTE:
	        	return ("smallint");
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
	
    @Override
    public String getCommitCommand() {
        return "COMMIT";
    }
    
    @Override
    public String getBeginTxnCommand() {
    	return "begin" + ";" + JdbcDeployment.BRK;
    }


}
