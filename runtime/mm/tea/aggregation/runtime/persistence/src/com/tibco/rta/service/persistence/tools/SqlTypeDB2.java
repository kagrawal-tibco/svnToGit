package com.tibco.rta.service.persistence.tools;

import com.tibco.rta.model.DataType;

public class SqlTypeDB2 extends SqlType{

	@Override
	public int getMaxIdentifierLength() {
		// TODO V9.5
		return 128;
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
            } else if (primitiveType.equalsIgnoreCase("boolean")) {
                return ("char(1)");
            }
            return RDBMSType.sSqlTypeAnsi.getColumnTypeForPrimitiveType(primitiveType, size);
        }
        if (primitiveType.equalsIgnoreCase("String")) {
            if (size <= 1) {               
                return ("varchar(255)");
            }            
            if (size > 4000) {
                return ("clob");
            }
            return ("varchar(" + size + ")");
        } else if (primitiveType.equalsIgnoreCase("integer")) {
            return ("integer");
        } else if (primitiveType.equalsIgnoreCase("long")) {
            return ("bigint"); 
        } else if (primitiveType.equalsIgnoreCase("double")) {
            return ("float");
        } else if (primitiveType.equalsIgnoreCase("boolean")) {
            return ("char(1)");
        } else if (primitiveType.equalsIgnoreCase("blob")) {
            return ("blob");
		} else if (primitiveType.equalsIgnoreCase("clob")) {
			return ("clob");
		} else if (primitiveType.equalsIgnoreCase("timestamp")) {			
			return ("timestamp");
		} else if (primitiveType.equalsIgnoreCase("char")) {
			return ("char(1)");
		} else if (primitiveType.equalsIgnoreCase("short")) {
			return ("smallint");
		} else if (primitiveType.equalsIgnoreCase("byte")) {
			return ("smallint");
		} else {
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
        case LONG: // assume signed value
            return ("number(19,0)"); // TODO: Fix this even Integer is number(38)
        case DOUBLE:
            return ("float");
        case STRING:
            if (size <= 1)
                return ("varchar(128)");
            if (size >= 4000)
                return ("clob");
            return ("varchar(" + size + ")");
        case SHORT:
        	return ("smallint");
        case BYTE:
        	return ("smallint");
        case CLOB:
        	return ("clob");
        default:
            System.err.println("Unsupported type integer Id '" + String.valueOf(primitiveType) + "' to convert to SQL column type.");
            return "varchar(128)";
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


}
