package com.tibco.be.ws.functions.decisiontable;

import static com.tibco.be.model.functions.FunctionDomain.*;

import com.tibco.cep.decision.table.model.dtmodel.Argument;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;

@com.tibco.be.model.functions.BEPackage(
		catalog = "BRMS",
        category = "WS.Decision.TableModel.Arguments",
        synopsis = "Functions to read the Decision Table arguments",
        enabled = @com.tibco.be.model.functions.Enabled(property="TIBCO.BE.function.catalog.WS.Decision.TableModel.Arguments", value=true))

public class WebStudioDecisionTableModelArgumentFunctions {

	@com.tibco.be.model.functions.BEFunction(
            name = "getDirection",
            synopsis = "",
            signature = "String getDirection(Object argumentObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "argumentObject", type = "Object", desc = "Decision Table Argument object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Decision Table Argument direction."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the Decision Table Argument direction.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )    
    public static String getDirection(Object argumentObject) {
		if (!(argumentObject instanceof Argument)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Argument.class.getName()));
		}
		Argument argument = (Argument) argumentObject;
		return argument.getDirection();
    }    	

	@com.tibco.be.model.functions.BEFunction(
            name = "getPath",
            synopsis = "",
            signature = "String getPath(Object argumentObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "argumentObject", type = "Object", desc = "Decision Table Argument object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Decision Table Argument entity path."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the Decision Table Argument entity path.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )    
    public static String getPath(Object argumentObject) {
		if (!(argumentObject instanceof Argument)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Argument.class.getName()));
		}
		Argument argument = (Argument) argumentObject;
		return argument.getProperty().getPath();
    }    	

	@com.tibco.be.model.functions.BEFunction(
            name = "getAlias",
            synopsis = "",
            signature = "String getAlias(Object argumentObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "argumentObject", type = "Object", desc = "Decision Table Argument object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Decision Table Argument entity alias."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the Decision Table Argument entity alias.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )    
    public static String getAlias(Object argumentObject) {
		if (!(argumentObject instanceof Argument)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Argument.class.getName()));
		}
		Argument argument = (Argument) argumentObject;
		return argument.getProperty().getAlias();
    }    	

	
	@com.tibco.be.model.functions.BEFunction(
            name = "getResourceType",
            synopsis = "",
            signature = "String getResourceType(Object argumentObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "argumentObject", type = "Object", desc = "Decision Table Argument object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Decision Table Argument entity path."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the Decision Table Argument entity path.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )    
    public static String getResourceType(Object argumentObject) {
		if (!(argumentObject instanceof Argument)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Argument.class.getName()));
		}
		Argument argument = (Argument) argumentObject;
		return argument.getProperty().getResourceType().getLiteral();
    }    		

	@com.tibco.be.model.functions.BEFunction(
            name = "isArray",
            synopsis = "",
            signature = "boolean isArray(Object argumentObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "argumentObject", type = "Object", desc = "Decision Table Argument object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "Is Decision Table Argument an array."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns true if the Decision Table Argument is an array, false other-wise.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )    
    public static boolean isArray(Object argumentObject) {
		if (!(argumentObject instanceof Argument)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Argument.class.getName()));
		}
		Argument argument = (Argument) argumentObject;
		return argument.getProperty().isArray();
    }
	
	@com.tibco.be.model.functions.BEFunction(
            name = "getPropertyName",
            synopsis = "",
            signature = "String getPropertyName(Object propertyObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyObject", type = "Object", desc = "Argument property object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Argument property name."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the Argument property name.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )    
    public static String getPropertyName(Object propertyObject) {
		if (!(propertyObject instanceof PropertyDefinition)) {
			throw new IllegalArgumentException(String.format("Argument property should be of type [%s]", PropertyDefinition.class.getName()));
		}
		PropertyDefinition property = (PropertyDefinition) propertyObject;
		return property.getName();
    }    		
	
	@com.tibco.be.model.functions.BEFunction(
            name = "getPropertyType",
            synopsis = "",
            signature = "String getPropertyType(Object propertyObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyObject", type = "Object", desc = "Argument property object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Argument property type."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the Argument property type.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )    
    public static String getPropertyType(Object propertyObject) {
		if (!(propertyObject instanceof PropertyDefinition)) {
			throw new IllegalArgumentException(String.format("Argument property should be of type [%s]", PropertyDefinition.class.getName()));
		}
		PropertyDefinition property = (PropertyDefinition) propertyObject;
		return property.getType().getLiteral();
    }
	
	@com.tibco.be.model.functions.BEFunction(
            name = "getPropertyConceptTypePath",
            synopsis = "",
            signature = "String getPropertyConceptTypePath(Object propertyObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyObject", type = "Object", desc = "Argument property object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Argument property concept type path."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the Argument property concept type path.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )    
    public static String getPropertyConceptTypePath(Object propertyObject) {
		if (!(propertyObject instanceof PropertyDefinition)) {
			throw new IllegalArgumentException(String.format("Argument property should be of type [%s]", PropertyDefinition.class.getName()));
		}
		PropertyDefinition property = (PropertyDefinition) propertyObject;
		return property.getConceptTypePath();
    }
	
	@com.tibco.be.model.functions.BEFunction(
            name = "getPropertyOwnerPath",
            synopsis = "",
            signature = "String getPropertyOwnerPath(Object propertyObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyObject", type = "Object", desc = "Argument property object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Argument property owner path."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the Argument property owner path.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )    
    public static String getPropertyOwnerPath(Object propertyObject) {
		if (!(propertyObject instanceof PropertyDefinition)) {
			throw new IllegalArgumentException(String.format("Argument property should be of type [%s]", PropertyDefinition.class.getName()));
		}
		PropertyDefinition property = (PropertyDefinition) propertyObject;
		return property.getOwnerPath();
    }
	
	@com.tibco.be.model.functions.BEFunction(
            name = "isPropertyArray",
            synopsis = "",
            signature = "String isPropertyArray(Object propertyObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyObject", type = "Object", desc = "Argument property object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "true if argument property is array, false otherwise."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns true if argument property is array, false otherwise.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )    
    public static boolean isPropertyArray(Object propertyObject) {
		if (!(propertyObject instanceof PropertyDefinition)) {
			throw new IllegalArgumentException(String.format("Argument property should be of type [%s]", PropertyDefinition.class.getName()));
		}
		PropertyDefinition property = (PropertyDefinition) propertyObject;
		return property.isArray();
    }
	
	@com.tibco.be.model.functions.BEFunction(
            name = "hasPropertyDomainAssociation",
            synopsis = "",
            signature = "String hasPropertyDomainAssociation(Object propertyObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyObject", type = "Object", desc = "Argument property object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "true if argument property is has domain, false otherwise."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns true if argument property has domain, false otherwise.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )    
    public static boolean hasPropertyDomainAssociation(Object propertyObject) {
		if (!(propertyObject instanceof PropertyDefinition)) {
			throw new IllegalArgumentException(String.format("Argument property should be of type [%s]", PropertyDefinition.class.getName()));
		}
		PropertyDefinition property = (PropertyDefinition) propertyObject;
		return (property.getDomainInstances() != null && !property.getDomainInstances().isEmpty());
    }	
}
