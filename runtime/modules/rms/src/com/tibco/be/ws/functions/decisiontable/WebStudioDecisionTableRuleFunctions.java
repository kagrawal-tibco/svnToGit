package com.tibco.be.ws.functions.decisiontable;

import static com.tibco.be.model.functions.FunctionDomain.*;

import java.util.List;

import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;

@com.tibco.be.model.functions.BEPackage(
		catalog = "BRMS",
		category = "WS.Decision.TableModel.Rules",
        synopsis = "Functions to read the Table Rules data.",
        enabled = @com.tibco.be.model.functions.Enabled(property="TIBCO.BE.function.catalog.WS.Decision.TableModel.Rules", value=true))

public class WebStudioDecisionTableRuleFunctions {
    
    @com.tibco.be.model.functions.BEFunction(
            name = "getID",
            synopsis = "",
            signature = "long getID(Object tableRuleObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "tableRuleObject", type = "Object", desc = "TableRule object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "long", desc = "Table Rule ID."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns Table Rule ID.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )    
    public static long getID(Object tableRuleObject) {
		if (!(tableRuleObject instanceof TableRule)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", TableRule.class.getName()));
		}
		TableRule tableRule = (TableRule) tableRuleObject;
		long ruleId = Long.parseLong(tableRule.getId());
		return ruleId;
    } 
    	    
    @com.tibco.be.model.functions.BEFunction(
            name = "getTableRuleConditions",
            synopsis = "",
            signature = "Object[] getTableRuleConditions(Object tableRuleObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "tableRuleObject", type = "Object", desc = "TableRule object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = "Array of TableRule condition objects."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns an array of TableRule condition objects.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )    
    public static Object[] getTableRuleConditions(Object tableRuleObject) {
		if (!(tableRuleObject instanceof TableRule)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", TableRule.class.getName()));
		}
		TableRule tableRule = (TableRule) tableRuleObject;
		List<TableRuleVariable> conditions = tableRule.getCondition();
		if (conditions != null) {
			return conditions.toArray();
		}
		
		return new Object[0];
    }
    
    @com.tibco.be.model.functions.BEFunction(
            name = "getTableRuleActions",
            synopsis = "",
            signature = "Object[] getTableRuleActions(Object tableRuleObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "tableRuleObject", type = "Object", desc = "TableRule object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = "Array of TableRule action objects."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns an array of TableRule action objects.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )    
    public static Object[] getTableRuleActions(Object tableRuleObject) {
		if (!(tableRuleObject instanceof TableRule)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", TableRule.class.getName()));
		}
		TableRule tableRule = (TableRule) tableRuleObject;
		List<TableRuleVariable> actions = tableRule.getAction();
		if (actions != null) {
			return actions.toArray();
		}
		
		return new Object[0];
    }    	
}
