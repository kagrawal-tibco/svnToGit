package com.tibco.be.ws.functions.decisiontable;

import static com.tibco.be.model.functions.FunctionDomain.*;
import java.util.List;

import com.tibco.cep.decision.table.model.dtmodel.MetaData;
import com.tibco.cep.decision.table.model.dtmodel.Property;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;

@com.tibco.be.model.functions.BEPackage(
		catalog = "BRMS",
		category = "WS.Decision.TableModel.Rules.MetaData",
        synopsis = "Functions to read the Table Rules data.",
        enabled = @com.tibco.be.model.functions.Enabled(property="TIBCO.BE.function.catalog.WS.Decision.TableModel.Rules.MetaData", value=true))

public class WebStudioDecisionTableRuleMetadataFunctions {

    @com.tibco.be.model.functions.BEFunction(
            name = "getProperties",
            synopsis = "",
            signature = "Object[] getProperties(Object tableRuleObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "tableRuleObject", type = "Object", desc = "TableRule MetaData properties object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = "Array of TableRule Metadata properties."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns an Array of TableRule Metadata properties.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )    
    public static Object[] getProperties(Object tableRuleObject) {
		if (!(tableRuleObject instanceof TableRule)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", TableRule.class.getName()));
		}
		TableRule tableRule = (TableRule) tableRuleObject;
		MetaData md = tableRule.getMd();
		if (md != null) {
			List<Property> props = md.getProp();
			return props.toArray();
		}
		return new Object[0];
    }
    
    @com.tibco.be.model.functions.BEFunction(
            name = "getPropertyName",
            synopsis = "",
            signature = "Object[] getPropertyName(Object propertyObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyObject", type = "Object", desc = "MetaData property object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "MetaData property name."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns MetaData property name.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )    
    public static String getPropertyName(Object propertyObject) {
		if (!(propertyObject instanceof Property)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Property.class.getName()));
		}
		Property prop = (Property) propertyObject;
		return prop.getName();
    }    

    @com.tibco.be.model.functions.BEFunction(
            name = "getPropertyType",
            synopsis = "",
            signature = "String getMDPropertyType(Object propertyObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyObject", type = "Object", desc = "MetaData property object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "MetaData property type."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns MetaData property type.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )    
    public static String getPropertyType(Object propertyObject) {
		if (!(propertyObject instanceof Property)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Property.class.getName()));
		}
		Property prop = (Property) propertyObject;
		return prop.getType();
    } 
    
    @com.tibco.be.model.functions.BEFunction(
            name = "getPropertyValue",
            synopsis = "",
            signature = "Object[] getPropertyValue(Object propertyObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyObject", type = "Object", desc = "MetaData property object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "MetaData property value."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns MetaData property value.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )    
    public static String getPropertyValue(Object propertyObject) {
		if (!(propertyObject instanceof Property)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Property.class.getName()));
		}
		Property prop = (Property) propertyObject;
		return prop.getValue();
    }        	
}
