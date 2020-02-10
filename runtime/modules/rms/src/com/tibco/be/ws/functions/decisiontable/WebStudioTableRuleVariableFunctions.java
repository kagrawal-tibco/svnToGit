package com.tibco.be.ws.functions.decisiontable;

import static com.tibco.be.model.functions.FunctionDomain.*;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;

@com.tibco.be.model.functions.BEPackage(
		catalog = "BRMS",
		category = "WS.Decision.TableModel.Rules.Variables",
        synopsis = "Functions to read the Conditions/Actions data.",
        enabled = @com.tibco.be.model.functions.Enabled(property="TIBCO.BE.function.catalog.WS.Decision.TableModel.Rules.Variables", value=true))

public class WebStudioTableRuleVariableFunctions {
	
    @com.tibco.be.model.functions.BEFunction(
            name = "getID",
            synopsis = "",
            signature = "String getID(Object ruleVariableObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ruleVariableObject", type = "Object", desc = "TableRule Condition/Action object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "TableRule Condition/Action ID."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns TableRule Condition/Action ID.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )    
    public static String getID(Object ruleVariableObject) {
		if (!(ruleVariableObject instanceof TableRuleVariable)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", TableRuleVariable.class.getName()));
		}
		TableRuleVariable tableRuleVar = (TableRuleVariable) ruleVariableObject;
		return tableRuleVar.getId();
    } 
    
    @com.tibco.be.model.functions.BEFunction(
            name = "getColumnID",
            synopsis = "",
            signature = "String getColumnID(Object ruleVariableObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ruleVariableObject", type = "Object", desc = "TableRule Condition/Action object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "TableRule Condition/Action Column ID."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns TableRule Condition/Action Column ID.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )    
    public static String getColumnID(Object ruleVariableObject) {
		if (!(ruleVariableObject instanceof TableRuleVariable)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", TableRuleVariable.class.getName()));
		}
		TableRuleVariable tableRuleVar = (TableRuleVariable) ruleVariableObject;
		return tableRuleVar.getColId();
    }     

    @com.tibco.be.model.functions.BEFunction(
            name = "getExpression",
            synopsis = "",
            signature = "String getExpression(Object ruleVariableObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ruleVariableObject", type = "Object", desc = "TableRule Condition/Action object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = "TableRule Condition/Action expression."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns TableRule Condition/Action expression.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )        
    public static String getExpression(Object ruleVariableObject) {
		if (!(ruleVariableObject instanceof TableRuleVariable)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", TableRuleVariable.class.getName()));
		}
		TableRuleVariable tableRuleVar = (TableRuleVariable) ruleVariableObject;
		return tableRuleVar.getExpr();
    }     

    @com.tibco.be.model.functions.BEFunction(
            name = "getDisplayValue",
            synopsis = "",
            signature = "String getDisplayValue(Object ruleVariableObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ruleVariableObject", type = "Object", desc = "TableRule Condition/Action object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = "TableRule Condition/Action display value."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns TableRule Condition/Action display value.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )        
    public static String getDisplayValue(Object ruleVariableObject) {
		if (!(ruleVariableObject instanceof TableRuleVariable)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", TableRuleVariable.class.getName()));
		}
		TableRuleVariable tableRuleVar = (TableRuleVariable) ruleVariableObject;
		return tableRuleVar.getDisplayValue();
    }
    
    @com.tibco.be.model.functions.BEFunction(
            name = "getComment",
            synopsis = "",
            signature = "String getComment(Object ruleVariableObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ruleVariableObject", type = "Object", desc = "TableRule Condition/Action object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = "TableRule Condition/Action comment."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns TableRule Condition/Action comment.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )        
    public static String getComment(Object ruleVariableObject) {
		if (!(ruleVariableObject instanceof TableRuleVariable)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", TableRuleVariable.class.getName()));
		}
		TableRuleVariable tableRuleVar = (TableRuleVariable) ruleVariableObject;
		return tableRuleVar.getComment();
    }	
    
    @com.tibco.be.model.functions.BEFunction(
            name = "isEnabled",
            synopsis = "",
            signature = "boolean isEnabled(Object ruleVariableObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ruleVariableObject", type = "Object", desc = "TableRule Condition/Action object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "TableRule Condition/Action isEnabled."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns true if TableRule Condition/Action is enabled, false other-wise.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )        
    public static boolean isEnabled(Object ruleVariableObject) {
		if (!(ruleVariableObject instanceof TableRuleVariable)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", TableRuleVariable.class.getName()));
		}
		TableRuleVariable tableRuleVar = (TableRuleVariable) ruleVariableObject;
		return tableRuleVar.isEnabled();
    }			    

}
