package com.tibco.be.ws.functions.decisiontable;

import static com.tibco.be.model.functions.FunctionDomain.*;

import java.util.List;

import com.tibco.cep.decision.table.model.dtmodel.Argument;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;

@com.tibco.be.model.functions.BEPackage(
		catalog = "BRMS",
        category = "WS.Decision.TableModel",
        synopsis = "Functions to read the Decision Table model.",
        enabled = @com.tibco.be.model.functions.Enabled(property="TIBCO.BE.function.catalog.WS.Decision.TableModel", value=true))

public class WebStudioDecisionTableModelFunctions {

    @com.tibco.be.model.functions.BEFunction(
            name = "getName",
            synopsis = "",
            signature = "String getName(Object decisionTableEMFModelObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "decisionTableEMFModelObject", type = "Object", desc = "Decision table EMF Model object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Name of the Decison Table."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the name of the Decision Table.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    public static String getName(Object decisionTableEMFModelObject) {
		if (!(decisionTableEMFModelObject instanceof Table)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Table.class.getName()));
		}
		Table tableModel = (Table) decisionTableEMFModelObject;
		return tableModel.getName();
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "getFolder",
            synopsis = "",
            signature = "String getFolder(Object decisionTableEMFModelObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "decisionTableEMFModelObject", type = "Object", desc = "Decision table EMF Model object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Containing folder Name of the Decison Table."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns containing folder Name of the Decison Table.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )    
    public static String getFolder(Object decisionTableEMFModelObject) {
		if (!(decisionTableEMFModelObject instanceof Table)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Table.class.getName()));
		}
		Table tableModel = (Table) decisionTableEMFModelObject;
		return tableModel.getFolder();
    }
	
    @com.tibco.be.model.functions.BEFunction(
            name = "getImplementsPath",
            synopsis = "",
            signature = "String getImplementsPath(Object decisionTableEMFModelObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "decisionTableEMFModelObject", type = "Object", desc = "Decision table EMF Model object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Path of the Implemented VRF."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the Path of the Implemented VRF.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )    
    public static String getImplementsPath(Object decisionTableEMFModelObject) {
		if (!(decisionTableEMFModelObject instanceof Table)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Table.class.getName()));
		}
		Table tableModel = (Table) decisionTableEMFModelObject;
		return tableModel.getImplements();
    }
    
 
    
    @com.tibco.be.model.functions.BEFunction(
            name = "getArguments",
            synopsis = "",
            signature = "Object[] getArguments(Object decisionTableEMFModelObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "decisionTableEMFModelObject", type = "Object", desc = "Decision table EMF Model object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = "Array of Decision Table argument objects."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns an Array of Decision Table argument objects.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )    
    public static Object[] getArguments(Object decisionTableEMFModelObject) {
		if (!(decisionTableEMFModelObject instanceof Table)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Table.class.getName()));
		}
		Table tableModel = (Table) decisionTableEMFModelObject;
		List<Argument> arguments = tableModel.getArgument();
		if (arguments != null) {
			return arguments.toArray();
		}
		
		return new Object[0];
    }


    @com.tibco.be.model.functions.BEFunction(
            name = "getDecisionTableColumns",
            synopsis = "",
            signature = "Object[] getDecisionTableColumns(Object decisionTableEMFModelObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "decisionTableEMFModelObject", type = "Object", desc = "Decision Table EMF object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = "Array of DecisionTable column objects."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns an array of Decision Table Column objects.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )    
    public static Object[] getDecisionTableColumns(Object decisionTableEMFModelObject) {
		if (!(decisionTableEMFModelObject instanceof Table)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Table.class.getName()));
		}
		Table table = (Table) decisionTableEMFModelObject;
		TableRuleSet ruleSet = table.getDecisionTable();
		Columns columns = ruleSet.getColumns();
		if (columns != null) {
			return columns.getColumn().toArray();
		}
		
		return new Object[0];
    }

    
    @com.tibco.be.model.functions.BEFunction(
            name = "getExceptionTableColumns",
            synopsis = "",
            signature = "Object[] getExceptionTableColumns(Object decisionTableEMFModelObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "decisionTableEMFModelObject", type = "Object", desc = "Decision Table EMF object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = "Array of ExceptionTable column objects."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns an array of Exception Table Column objects.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )    
    public static Object[] getExceptionTableColumns(Object decisionTableEMFModelObject) {
		if (!(decisionTableEMFModelObject instanceof Table)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Table.class.getName()));
		}
		Table table = (Table) decisionTableEMFModelObject;
		TableRuleSet ruleSet = table.getExceptionTable();
		Columns columns = ruleSet.getColumns();

		if (columns != null) {
			return columns.getColumn().toArray();
		}
		return new Object[0];
    } 
    
    
    @com.tibco.be.model.functions.BEFunction(
            name = "getDecisionTableRules",
            synopsis = "",
            signature = "Object[] getDecisionTableRules(Object decisionTableEMFModelObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "decisionTableEMFModelObject", type = "Object", desc = "Decision Table EMF object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = "Array of Decision TableRule objects."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns array of Decision TableRule objects.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )    
    public static Object[] getDecisionTableRules(Object decisionTableEMFModelObject) {
		if (!(decisionTableEMFModelObject instanceof Table)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Table.class.getName()));
		}
		Table table = (Table) decisionTableEMFModelObject;
		TableRuleSet ruleSet = table.getDecisionTable();
		List<TableRule> tableRules = ruleSet.getRule();
		if (tableRules != null) {
			return tableRules.toArray();
		}
		
		return new Object[0];
    }

    
    @com.tibco.be.model.functions.BEFunction(
            name = "getExceptionTableRules",
            synopsis = "",
            signature = "Object[] getExceptionTableRules(Object decisionTableEMFModelObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "decisionTableEMFModelObject", type = "Object", desc = "Decision Table EMF object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = "Array of Exception TableRule objects."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns array of Exception TableRule objects.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )    
    public static Object[] getExceptionTableRules(Object decisionTableEMFModelObject) {
		if (!(decisionTableEMFModelObject instanceof Table)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Table.class.getName()));
		}
		Table table = (Table) decisionTableEMFModelObject;
		TableRuleSet ruleSet = table.getExceptionTable();
		List<TableRule> tableRules = ruleSet.getRule();
		if (tableRules != null) {
			return tableRules.toArray();
		}
		
		return new Object[0];
    }

}
