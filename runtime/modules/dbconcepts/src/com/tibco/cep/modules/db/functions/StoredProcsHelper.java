package com.tibco.cep.modules.db.functions;

import static com.tibco.be.model.functions.FunctionDomain.*;

import com.tibco.be.model.functions.Enabled;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.modules.db.model.runtime.DBConcept;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Array;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import oracle.jdbc.OracleTypes;

@com.tibco.be.model.functions.BEPackage(
		catalog = "RDBMS",
        category = "Database.sp",
        synopsis = "Database stored procedure support access functions")
public class StoredProcsHelper {

	static com.tibco.cep.kernel.service.logging.Logger logger;

	//fetchsize for resultsets
	static int fetchSize = 0;

	static {
		try {
			logger = RuleServiceProviderManager.getInstance().getDefaultProvider().getLogger(StoredProcsHelper.class);
			String fetchSizeStr = RuleServiceProviderManager.getInstance().getDefaultProvider().getProperties().
				getProperty("tibco.be.jdbc.resultset.fetchsize", "0");
			fetchSize = Integer.parseInt(fetchSizeStr);
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

	protected static final ThreadLocal currentConnections = new ThreadLocal(){
    	protected synchronized Object initialValue(){
    		return new HashMap();
    	}
    };

	@com.tibco.be.model.functions.BEFunction(
        name = "initStoredProc",
        synopsis = "Initializes the given stored procedure",
        signature = "Object initStoredProc (String storedProc)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "storedProc", type = "String", desc = "call sp_name () or call sp_name (?, ?)")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Initializes the given stored procedure",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object initStoredProc (String storedProc) {
		CallableStatement stmt = null;
		try {
			Connection conn = JDBCHelper.getCurrentConnection();
			stmt = conn.prepareCall(storedProc);

			return stmt;
		} catch (Exception e) {
            logger.log(Level.ERROR, e, "queryUsingPreparedStmt exception");
			throw new RuntimeException(e);
		}
	}

	@com.tibco.be.model.functions.BEFunction(
        name = "setInputParameter",
        synopsis = "Sets input parameter of the stored procedure at the given index.\nIndices are 1-based.",
        signature = "void setInputParameter(Object handle, int index, Object value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "handle", type = "Object", desc = "initStoredProc()"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "index", type = "int", desc = "Index at which to set input parameter."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "Object", desc = "Value to set at the given index.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sets input parameter of the stored procedure at the given index.\nIndices are 1-based.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static void setInputParameter(Object stmt, int index, Object value) {
		if (stmt == null || (stmt != null && !(stmt instanceof CallableStatement))) {
			throw new RuntimeException("Object passed must be a valid CallableStatement reference");
		}

		CallableStatement cs = (CallableStatement)stmt;
		try {
			if (value instanceof Calendar) {
				java.sql.Timestamp ts = new Timestamp(((Calendar)value).getTimeInMillis());
				value = ts;
			}
			cs.setObject(index, value);
		} catch (Exception e) {
            logger.log(Level.ERROR, e, "Error while setting input parameter on stored procedure");
			throw new RuntimeException(e);
		}
	}

	@com.tibco.be.model.functions.BEFunction(
        name = "setOutputParameterType",
        synopsis = "For stored procedure with $1out$1 parameters, sets the sqltype of the return type at the given index.",
        signature = "void setOutputParameterType(Object handle, int index, int beType)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "handle", type = "Object", desc = "initStoredProc()"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "index", type = "int", desc = "Index at which to set the parameter type"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "beType", type = "int", desc = "STRING=0; INT=1; LONG=2; DOUBLE=3; BOOLEAN=4; DATETIME=5; CONCEPT=6; CLOB=7;")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "For stored procedure with $1out$1 parameters, sets the sqltype of the return type at the given index.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static void setOutputParameterType(Object stmt, int index, int beType) {
		if (stmt == null || (stmt != null && !(stmt instanceof CallableStatement))) {
			throw new RuntimeException("Object passed must be a valid CallableStatement reference");
		}

		CallableStatement cs = (CallableStatement)stmt;
		try {
			int sqlTypeToUse = convertBETypeToJDBCTypes(cs, beType);
			logger.log(Level.DEBUG, "BeTypeId-" + beType + "; JDBCTypeId-" + sqlTypeToUse);
			cs.registerOutParameter(index, sqlTypeToUse);
		} catch (Exception e) {
            logger.log(Level.ERROR, e, "Error while setting output parameter on stored procedure at index %s", index);
			throw new RuntimeException(e);
		}
	}

	@com.tibco.be.model.functions.BEFunction(
        name = "getConceptsAtIndex",
        synopsis = "This function is to be used where the output at the given index is a database cursor and where the \ncontents of the underlying cursor can be mapped to a db concept.",
        signature = "Concept[] getConceptsAtIndex(Object handle, int index, String conceptURI)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "handle", type = "Object", desc = "initStoredProc()"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "index", type = "int", desc = "Index at which output is to be obtained"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "conceptURI", type = "String", desc = "available at this index.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept[]", desc = ""),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This function is to be used where the output at the given index is a database cursor and where the \ncontents of the underlying cursor can be mapped to a db concept.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Concept[] getConceptsAtIndex(Object stmt, int index, String conceptURI) {
		if (stmt == null || (stmt != null && !(stmt instanceof CallableStatement))) {
			throw new RuntimeException("Object passed must be a valid CallableStatement reference");
		}

		CallableStatement cs = (CallableStatement)stmt;
		try {
			Object result =  cs.getObject(index);
			if (result instanceof ResultSet) {
				Concept cept = FunctionHelper.createConcept(conceptURI);
				if (!(cept instanceof DBConcept)) {
					throw new Exception("Specified concept is not a database concept: "
							+ conceptURI);
				} else {
					List cepts = new ArrayList();
					ResultSet rs = (ResultSet)result;
					rs.setFetchSize(fetchSize);
					while (rs.next()) {
						String ceptName = ((ConceptImpl) cept).getType();
						ceptName = ceptName
								.substring(TypeManager.DEFAULT_BE_NAMESPACE_URI
										.length());
						DBConcept dbconcept = (DBConcept) FunctionHelper
								.createConcept(ceptName);
						dbconcept.setProperties(rs);
						cepts.add(dbconcept);
					}
					Concept dummyInstance = null;
					if(cepts.size()>0){
						dummyInstance = (Concept)cepts.get(0);
					} else {
						dummyInstance = FunctionHelper.createConcept(conceptURI);
					}
					if(dummyInstance != null) {
						Class ceptClass = dummyInstance.getClass();
						return (Concept[])cepts.toArray((Concept[])Array.newInstance(ceptClass, cepts.size()));
					}else {
						return (Concept[])cepts.toArray( new Concept[]{});
					}
				}
			} else {
				throw new RuntimeException("Output from stored procedure not a result set at index " + index);
			}
		} catch (Exception e) {
            logger.log(Level.ERROR, e, "");
			throw new RuntimeException(e);
		}
	}

	@com.tibco.be.model.functions.BEFunction(
        name = "getObjectAtIndex",
        synopsis = "Queries the database using supplied prepared statement.",
        signature = "Object getObjectAtIndex(Object handle, int index)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "handle", type = "Object", desc = "initStoredProc()"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "index", type = "int", desc = "Index at which output is to be obtained.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Returns Calendar object for Date/Timestamp datatypes.</br>" +
        		"For Clob datatype, returns the CLOB as stream (Use readClobContent() to read content)."),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Queries the database using supplied prepared statement.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object getObjectAtIndex(Object stmt, int index) {
		if (stmt == null || (stmt != null && !(stmt instanceof CallableStatement))) {
			throw new RuntimeException("Object passed must be a valid CallableStatement reference");
		}

		CallableStatement cs = (CallableStatement)stmt;
		try {
			Object result =  cs.getObject(index);
			if (result instanceof java.sql.Date) {
				Calendar cal = Calendar.getInstance();
				cal.setTime((java.sql.Date)result);
				return cal;
			} else if (result instanceof java.sql.Timestamp) {
				Calendar cal = Calendar.getInstance();
				cal.setTime((java.sql.Timestamp)result);
				return cal;
			} else if (result instanceof Clob) {
				Clob clob = (Clob)result;
				return clob.getCharacterStream();
			}
			return result;
		} catch (Exception e) {
			logger.log(Level.ERROR, e, "");
			throw new RuntimeException(e);
		}
	}

	@com.tibco.be.model.functions.BEFunction(
	        name = "readClobContent",
	        synopsis = "Reads content from the specified CLOB.",
	        signature = "String readClobContent(Object clob)",
	        params = {
	            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "clob", type = "Object", desc = "")
	        },
	        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Returns next available data(as string) from the CLOB.<br/>" +
	        		"Returns <code>null</code> when no-more content is available to read."),
	        version = "",
	        see = "",
	        mapper = @com.tibco.be.model.functions.BEMapper(),
	        description = "Reads content from the specified CLOB.",
	        cautions = "",
	        fndomain = {ACTION},
	        example = "Code Snippet:<br/>" +
	        		"<code>Object clob = Database.sp.getObjectAtIndex(handle, 1);<br/>" +
	        		"String content = Database.sp.readClobContent(clob);<br/>" +
	        		"while (content != null) {<br/>" +
	        		"	//Use content as per your requirement</br>" +
	        		"	content = Database.sp.readClobContent(clob);<br/>" +
	        		"}</code>"
	)
	public static String readClobContent(Object readerObject) {
		if (!(readerObject instanceof Reader)) {
			throw new RuntimeException("Object passed must represent a valid CLOB.");
		}
		String content = null;
		try {
			Reader reader = (Reader)readerObject;
			char buffer[] = new char[16777216];//buffer of 16M
			int readCount = reader.read(buffer);
			if (readCount > 0) {
				content = new String(buffer, 0, readCount);
			}
		} catch (IOException e) {
			logger.log(Level.ERROR, e, "Exception in readClobContent().");
			throw new RuntimeException(e);
		}
		return content;
	}

	@com.tibco.be.model.functions.BEFunction(
        name = "closeStoredProc",
        synopsis = "Closes the stored procedure.",
        signature = "void closeStoredProc(Object handle)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "handle", type = "Object", desc = "initStoredProc()")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Closes the stored procedure.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static void closeStoredProc(Object stmt) {
		if (stmt == null || (stmt != null && !(stmt instanceof CallableStatement))) {
			throw new RuntimeException("Object passed must be a valid CallableStatement reference");
		}

		CallableStatement cs = (CallableStatement)stmt;
		try {
			cs.close();
		} catch (Exception e) {
            logger.log(Level.ERROR, e, "");
			throw new RuntimeException(e);
		}
	}

	@com.tibco.be.model.functions.BEFunction(
        name = "executeStoredProc",
        synopsis = "Executes the given stored procedure.",
        signature = "void executeStoredProc(Object handle)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "handle", type = "Object", desc = "initStoredProc()")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Executes the given stored procedure.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static void executeStoredProc(Object stmt) {
		if (stmt == null || (stmt != null && !(stmt instanceof CallableStatement))) {
			throw new RuntimeException("Object passed must be a valid CallableStatement reference");
		}

		CallableStatement cs = (CallableStatement)stmt;
		try {
			cs.execute();
		} catch (Exception e) {
            logger.log(Level.ERROR, e, "Error while executing stored procedure");
			throw new RuntimeException(e);
		}
	}
	
	private static enum OutputParamBEType {
		STRING_TYPE(0), INTEGER_TYPE(1), LONG_TYPE(2), DOUBLE_TYPE(3), BOOLEAN_TYPE(4), DATETIME_TYPE(5), CONCEPT_TYPE(6), CLOB_TYPE(7);
		
		private int beTypeId;
		private OutputParamBEType(int beTypeId) {
			this.beTypeId = beTypeId;
		}
		public int getBeTypeId() {
			return this.beTypeId;
		}
	}
	
	private static int convertBETypeToJDBCTypes (CallableStatement stmt, int beType) {
		OutputParamBEType outputParamBEType = null;
		for (OutputParamBEType param : OutputParamBEType.values()) {
			if (param.getBeTypeId() == beType) {
				outputParamBEType = param;
				break;
			}
		}
		if (outputParamBEType != null) {
			switch (outputParamBEType) {
			case STRING_TYPE:
				return Types.VARCHAR;
			case INTEGER_TYPE:
				return Types.INTEGER;
			case DOUBLE_TYPE:
				return Types.DOUBLE;
			case BOOLEAN_TYPE:
				return Types.BOOLEAN;
			case DATETIME_TYPE:
				return Types.TIMESTAMP;
			case LONG_TYPE:
				return Types.BIGINT;
			case CONCEPT_TYPE:
			{	
				if ((stmt.getClass().getName().contains("Oracle") ||
						stmt.getClass().getName().contains("oracle")) ||
						stmt.getClass().getName().contains("com.sun.proxy")) {
					return OracleTypes.CURSOR;
				}
			}
			case CLOB_TYPE:
				return Types.CLOB;
			}
		}
		logger.log(Level.DEBUG, beType + " is not a valid beTypeId, treating it as JDBC type id.");
		return beType;
	}
}
