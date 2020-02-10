package com.tibco.be.ws.functions.decisiontable;

import static com.tibco.be.model.functions.FunctionDomain.*;
import com.tibco.cep.decision.table.model.dtmodel.Column;

@com.tibco.be.model.functions.BEPackage(
		catalog = "BRMS",
		category = "WS.Decision.TableModel.Columns",
        synopsis = "Functions to read the Table Columns data.",
        enabled = @com.tibco.be.model.functions.Enabled(property="TIBCO.BE.function.catalog.WS.Decision.TableModel.Columns", value=true))

public class WebStudioDecisionTableColumnFunctions {

	@com.tibco.be.model.functions.BEFunction(
        name = "getID",
        signature = "String getID(Object columnObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "columnObject", type = "Object", desc = "Table Column object.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Table Column ID."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the ID of the .",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )    
    public static String getID(Object columnObject) {
		if (!(columnObject instanceof Column)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Column.class.getName()));
		}
		Column column = (Column) columnObject;
		return column.getId();
    }
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getName",
        signature = "String getName(Object columnObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "columnObject", type = "Object", desc = "Table Column object.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Table Column ID."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the name of the Table Column.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )    
    public static String getName(Object columnObject) {
		if (!(columnObject instanceof Column)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Column.class.getName()));
		}
		Column column = (Column) columnObject;
		return column.getName();
    }
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getPropertyPath",
        signature = "String getPropertyPath(Object columnObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "columnObject", type = "Object", desc = "Table Column object.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Table Column property path."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the path of the Table Column property.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )    
    public static String getPropertyPath(Object columnObject) {
		if (!(columnObject instanceof Column)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Column.class.getName()));
		}
		Column column = (Column) columnObject;
		return column.getPropertyPath();
    }    	

	@com.tibco.be.model.functions.BEFunction(
        name = "getPropertyEntityPath",
        signature = "String getPropertyEntityPath(Object columnObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "columnObject", type = "Object", desc = "Table Column object.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Table Column property path."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the entity path of the Table Column, returns NULL for primitive types.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )    
    public static String getPropertyEntityPath(Object columnObject) {
		if (!(columnObject instanceof Column)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Column.class.getName()));
		}
		Column column = (Column) columnObject;

		String entityPath = null; 
		String propertyPath = column.getPropertyPath();
		
		if (propertyPath != null) {
			int lastIndex = propertyPath.lastIndexOf("/");
			if (lastIndex != -1) {
				entityPath = propertyPath.substring(0, lastIndex);
			}	
		}
		
		return entityPath;
	}    	

	@com.tibco.be.model.functions.BEFunction(
        name = "getPropertyName",
        signature = "String getPropertyName(Object columnObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "columnObject", type = "Object", desc = "Table Column object.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Table Column property path."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the name of the Table Column entity property.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )    
    public static String getPropertyName(Object columnObject) {
		if (!(columnObject instanceof Column)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Column.class.getName()));
		}
		Column column = (Column) columnObject;

		String propertyName = null; 
		String propertyPath = column.getPropertyPath();		
		if (propertyPath != null) {
			int lastIndex = propertyPath.lastIndexOf("/");
			if (lastIndex != -1) {
				propertyName = propertyPath.substring(lastIndex + 1, propertyPath.length());
				String[] splits = propertyName.split("\\[\\]");
				//Get first value
				propertyName = splits[0];
			}	
		}
		
		return propertyName;
	}    	
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getColumnType",
        signature = "String getColumnType(Object columnObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "columnObject", type = "Object", desc = "Table Column object.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Table Column type."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the type of the Table Column.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )    
    public static String getColumnType(Object columnObject) {
		if (!(columnObject instanceof Column)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Column.class.getName()));
		}
		Column column = (Column) columnObject;
		return column.getColumnType().getLiteral();
    }    	

	@com.tibco.be.model.functions.BEFunction(
        name = "getAlias",
        signature = "String getAlias(Object columnObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "columnObject", type = "Object", desc = "Table Column object.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Table Column alias."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the path of the Table Column alias.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )    
    public static String getAlias(Object columnObject) {
		if (!(columnObject instanceof Column)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Column.class.getName()));
		}
		Column column = (Column) columnObject;
		return column.getAlias();
    }    	

	@com.tibco.be.model.functions.BEFunction(
        name = "isSubstitution",
        signature = "boolean isSubstitution(Object columnObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "columnObject", type = "Object", desc = "Table Column object.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "Is Substitution Table Column."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns true if the Table Column has substitution, false other-wise.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )    
    public static boolean isSubstitution(Object columnObject) {
		if (!(columnObject instanceof Column)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Column.class.getName()));
		}
		Column column = (Column) columnObject;
		return column.isSubstitution();
    }
	
	@com.tibco.be.model.functions.BEFunction(
        name = "isArrayProperty",
        signature = "boolean getAlias(Object columnObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "columnObject", type = "Object", desc = "Table Column object.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Is Table Column an array property."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns true if the Table Column is an array property, false other-wise.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )    
    public static boolean isArrayProperty(Object columnObject) {
		if (!(columnObject instanceof Column)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Column.class.getName()));
		}
		Column column = (Column) columnObject;
		return column.isArrayProperty();
    }    	    	

	@com.tibco.be.model.functions.BEFunction(
        name = "getPropertyType",
        signature = "int getPropertyType(Object columnObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "columnObject", type = "Object", desc = "Table Column object.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Table Column property type."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the Table Column property type.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )    
    public static int getPropertyType(Object columnObject) {
		if (!(columnObject instanceof Column)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Column.class.getName()));
		}
		Column column = (Column) columnObject;
		return column.getPropertyType();
    }
	
	@com.tibco.be.model.functions.BEFunction(
	        name = "getDefaultCellText",
	        signature = "String getDefaultCellText(Object columnObject)",
	        params = {
	            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "columnObject", type = "Object", desc = "Table Column object.")
	        },
	        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Table Column default cell text."),
	        version = "5.2.0",
	        see = "",
	        mapper = @com.tibco.be.model.functions.BEMapper(),
	        description = "Returns the Table Column default cell text.",
	        cautions = "",
	        fndomain = {ACTION},
	        example = ""
	    )
	
	public static String getDefaultCellText(Object columnObject) {
		if (!(columnObject instanceof Column)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Column.class.getName()));
		}
		Column column = (Column) columnObject;
		return column.getDefaultCellText();
    }
}
