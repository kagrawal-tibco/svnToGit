/**
 * 
 */
package com.tibco.cep.modules.db.functions;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import java.util.List;

import com.tibco.cep.modules.db.functions.PreparedStatementParam.ParamDataType;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;

/**
 * Database Utility functions
 * 
 * @author vpatil
 */
@com.tibco.be.model.functions.BEPackage(
		catalog = "RDBMS",
        category = "Database.Util",
        synopsis = "Database Utility functions")
public class DBUtilFunctions {
	static com.tibco.cep.kernel.service.logging.Logger logger;

	static {
		try {
			if (RuleServiceProviderManager.getInstance() != null &&
					RuleServiceProviderManager.getInstance().getDefaultProvider() != null	) {
				logger = RuleServiceProviderManager.getInstance().getDefaultProvider().getLogger(JDBCHelper.class);
			}
		} catch (Exception e) {
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "addIntParam",
		synopsis = "Adds the specified value of type int to the passed PreparedStatement parameter list.",
		signature = "void addIntParam (List list, Object value)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "list", type = "List", desc = "The list of PreparedStatement parameters.") ,
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "Object", desc = "int value.")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.2",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Adds the specified integer element to the PreparedStatement parameters list.",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void addIntParam(Object list, Object value) {
		List paramList = (List)list;
		paramList.add(new PreparedStatementParam(ParamDataType.INT, value));
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "addStringParam",
		synopsis = "Adds the specified string element to the PreparedStatement parameters list.",
		signature = "void addStringParam (List paramList, Object value)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "list", type = "List", desc = "The list of PreparedStatement parameters.") ,
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "Object", desc = "string value.")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.2",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Adds the specified string element to the PreparedStatement parameters list.",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void addStringParam(Object list, Object value) {
		List paramList = (List)list;
		paramList.add(new PreparedStatementParam(ParamDataType.STRING, value));
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "addDoubleParam",
		synopsis = "Adds the specified value of type double to the passed PreparedStatement parameter list.",
		signature = "void addDoubleParam (List paramList, Object value)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "list", type = "List", desc = "The list of PreparedStatement parameters.") ,
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "Object", desc = "double value.")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.2",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Adds the specified double element to the PreparedStatement parameters list.",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void addDoubleParam(Object list, Object value) {
		List paramList = (List)list;
		paramList.add(new PreparedStatementParam(ParamDataType.DOUBLE, value));
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "addParam",
		synopsis = "Adds the specified value of type Object to the passed PreparedStatement parameter list. Works for all dataTypes.",
		signature = "void addParam ( List paramList, Object value)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "list", type = "List", desc = "The list of PreparedStatement parameters.") ,
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "Object", desc = "Object value.")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.2",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Adds the specified Object element to the PreparedStatement parameters list.",
		cautions = "For improved performance use methods specific to dataTypes such as - addIntPreparedStmtParam, addStringPreparedStmtParam etc",
		fndomain = {ACTION},
		example = ""
	)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void addParam(Object list, Object value) {
		List paramList = (List)list;
		paramList.add(new PreparedStatementParam(ParamDataType.OBJECT, value));
	}
}
