package com.tibco.be.ws.functions.decisiontable;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLHelperImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLMapImpl;

import com.tibco.be.model.functions.FunctionParamDescriptor;
import com.tibco.be.ws.functions.util.WebstudioFunctionUtils;
import com.tibco.cep.decision.table.model.dtmodel.Argument;
import com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.DtmodelFactory;
import com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage;
import com.tibco.cep.decision.table.model.dtmodel.MetaData;
import com.tibco.cep.decision.table.model.dtmodel.Property;
import com.tibco.cep.decision.table.model.dtmodel.ResourceType;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.model.dtmodel.impl.TableImpl;
import com.tibco.cep.studio.core.index.model.DecisionTableElement;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.impl.DecisionTableElementImpl;
import com.tibco.cep.studio.core.index.model.impl.SharedDecisionTableElementImpl;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;

@com.tibco.be.model.functions.BEPackage(
		catalog = "BRMS",
        category = "WS.Decision.TableModel",
        synopsis = "Functions to create the Decision Table EMF model.",
        enabled = @com.tibco.be.model.functions.Enabled(property="TIBCO.BE.function.catalog.RMS", value=true))

public class WebStudioDecisionTableEMFFunctions {

    @com.tibco.be.model.functions.BEFunction(
            name = "createDecisionTableEMFObject",
            synopsis = "",
            signature = "Object createDecisionTableEMFObject(String name, String folder, String implementsPath)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "name", type = "String", desc = "Decision table name."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "folder", type = "String", desc = "Decision table containing folder."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "implementsPath", type = "String", desc = "Decision table VRF path.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Decision table EMF Object."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Creates a new Decision table EMF Object.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    public static Object createDecisionTableEMFObject(String name, String folder, String implementsPath) {
		Table tableModel = DtmodelFactory.eINSTANCE.createTable();
		tableModel.setName(name);
		tableModel.setFolder(folder);
		tableModel.setImplements(implementsPath);
		tableModel.setSince("BE 4.0");
		return tableModel;
    }
    
    @com.tibco.be.model.functions.BEFunction(
            name = "createArgument",
            synopsis = "",
            signature = "Object createArgument(String alias, String path, String direction, String resourceType)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "alias", type = "String", desc = "Argument alias."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "path", type = "String", desc = "Argument resource path."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "direction", type = "String", desc = "Argument direction."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "resourceType", type = "String", desc = "Argument resource type."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "isArray", type = "boolean", desc = "Argument is array.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Argument Object."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Creates a new Decision Table Argument.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    public static Object createArgument(String alias, String path, String direction, String resourceType, boolean isArray) {
    	Argument argument = DtmodelFactory.eINSTANCE.createArgument();
    	argument.setDirection(direction);
    	ArgumentProperty argProperty = DtmodelFactory.eINSTANCE.createArgumentProperty();
    	argProperty.setAlias(alias);
    	argProperty.setPath(path);
    	argProperty.setResourceType(ResourceType.get(resourceType));
    	argProperty.setArray(isArray);
    	
    	argument.setProperty(argProperty);
    	return argument;
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "addArgument",
            synopsis = "",
            signature = "addArgument(Object decisionTableEMFObject, Object argumentEMFObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "decisionTableEMFObject", type = "Object", desc = "Decision table EMF Model object."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "argumentEMFObject", type = "Object", desc = "Argument EMF Model object."),
            },
            freturn = @FunctionParamDescriptor(type = "void", desc = "", name = ""),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Adds the argument to the Decision table.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    public static void addArgument(Object decisionTableEMFObject, Object argumentEMFObject) {
		if (!(decisionTableEMFObject instanceof Table)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Table.class.getName()));
		}
		if (!(argumentEMFObject instanceof Argument)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Argument.class.getName()));
		}
		
		Table tableEModel = (Table) decisionTableEMFObject;
		EList<Argument> argList = tableEModel.getArgument();
    	argList.add((Argument) argumentEMFObject);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "createMetadata",
            synopsis = "",
            signature = "Object createMetadata()",
            params = { },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "MetaData Object."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the MetaData Object.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    public static Object createMetadata() {
    	MetaData metadata = DtmodelFactory.eINSTANCE.createMetaData();
    	return metadata;
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "createMetadataProperty",
            synopsis = "",
            signature = "Object createMetadataProperty(String name, String type, String value)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "name", type = "String", desc = "Property name."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "type", type = "String", desc = "Property type."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "String", desc = "Property value.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Metadata Property."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Creates the Metadata property.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    public static Object createMetadataProperty(String name, String type, String value) {
    	Property property = DtmodelFactory.eINSTANCE.createProperty();
    	property.setName(name);
    	property.setType(type);
    	property.setValue(value);

    	return property;
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "addMetadataProperty",
            synopsis = "",
            signature = "addMetadataProperty(Object metadataEMFObject, Object propertyEMFObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "metadataEMFObject", type = "Object", desc = "MetaData EMF Model object."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyEMFObject", type = "Object", desc = "MetaData Property EMF Model object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Adds the Property to MetaData Object.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    public static void addMetadataProperty(Object metadataEMFObject, Object propertyEMFObject) {
		if (!(metadataEMFObject instanceof MetaData)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", MetaData.class.getName()));
		}
		if (!(propertyEMFObject instanceof Property)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Property.class.getName()));
		}
		
		MetaData metadata = (MetaData) metadataEMFObject;
		Property property = (Property) propertyEMFObject;
		EList<Property> propList = metadata.getProp();
		propList.add(property);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "addTableMetadata",
            synopsis = "",
            signature = "addTableMetadata(Object decisionTableEMFObject, Object metadataEMFObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "decisionTableEMFObject", type = "Object", desc = "Decision table EMF Model object."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "metadataEMFObject", type = "Object", desc = "Decision table Metadata EMF Model object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Adds the MetaData to the Decision Table.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    public static void addTableMetadata(Object decisionTableEMFObject, Object metadataEMFObject) {
		if (!(decisionTableEMFObject instanceof Table)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Table.class.getName()));
		}
		if (!(metadataEMFObject instanceof MetaData)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", MetaData.class.getName()));
		}

		Table tableEModel = (Table) decisionTableEMFObject;
		tableEModel.setMd((MetaData) metadataEMFObject);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "createTableRuleSet",
            synopsis = "",
            signature = "Object createTableRuleSet()",
            params = {},
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Table RuleSet."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Creates the TableRule Set.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    public static Object createTableRuleSet() {
    	return DtmodelFactory.eINSTANCE.createTableRuleSet();
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "addDecisionTableRuleSet",
            synopsis = "",
            signature = "addDecisionTableRuleSet(Object decisionTableEMFObject, Object decisionTableRuleSetEMFObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "decisionTableEMFObject", type = "Object", desc = "Decision Table EMF Model object."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "decisionTableRuleSetEMFObject", type = "Object", desc = "Decision Table RuleSet EMF Model object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Adds the DecisionTable RuleSet to the DecisionTable EMF Object.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    public static void addDecisionTableRuleSet(Object decisionTableEMFObject, Object decisionTableRuleSetEMFObject) {
		if (!(decisionTableEMFObject instanceof Table)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Table.class.getName()));
		}
		if (!(decisionTableRuleSetEMFObject instanceof TableRuleSet)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", TableRuleSet.class.getName()));
		}

		Table tableEModel = (Table) decisionTableEMFObject;
		tableEModel.setDecisionTable((TableRuleSet) decisionTableRuleSetEMFObject);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "addExceptionTableRuleSet",
            synopsis = "",
            signature = "addExceptionTableRuleSet(Object decisionTableEMFObject, Object decisionTableRuleSetEMFObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "decisionTableEMFObject", type = "Object", desc = "Decision Table EMF Model object."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "exceptionTableRuleSetEMFObject", type = "Object", desc = "Exception Table RuleSet EMF Model object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Adds the Exception Table RuleSet to the DecisionTable EMF Object.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    public static void addExceptionTableRuleSet(Object decisionTableEMFObject, Object exceptionTableRuleSetEMFObject) {
		if (!(decisionTableEMFObject instanceof Table)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Table.class.getName()));
		}
		if (!(exceptionTableRuleSetEMFObject instanceof TableRuleSet)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", TableRuleSet.class.getName()));
		}

		Table tableEModel = (Table) decisionTableEMFObject;
		tableEModel.setExceptionTable((TableRuleSet) exceptionTableRuleSetEMFObject);
    }
    
    @com.tibco.be.model.functions.BEFunction(
            name = "getColumnName",
            synopsis = "",
            signature = "getColumnName(Object decisionTableEMFObject, String colId)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "decisionTableEMFObject", type = "Object", desc = "Decision Table EMF Model object."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "colId", type = "String", desc = "Column Id.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Get the column Name",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    public static String getColumnName(Object decisionTableEMFObject, String colId) {
		if (!(decisionTableEMFObject instanceof Table)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Table.class.getName()));
		}
		String columnName = null;
		Table tableEModel = (Table) decisionTableEMFObject;
		Columns columns = tableEModel.getDecisionTable().getColumns();
		Column column = columns.search(colId);
		if(column != null) {
			columnName = column.getName();
		}
		return columnName;
    }
    
    @com.tibco.be.model.functions.BEFunction(
            name = "createTableRule",
            synopsis = "",
            signature = "Object createTableRule(String ruleId)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ruleId", type = "String", desc = "TableRule Id.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "TableRule."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Creates the TableRule Object.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    public static Object createTableRule(String ruleId) {
    	TableRule tableRule = DtmodelFactory.eINSTANCE.createTableRule();
    	tableRule.setId(ruleId);
    	return tableRule;
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "addTableRules",
            synopsis = "",
            signature = "addTableRules(Object tableRuleSetEMFObject, Object tableRuleEMFObjects)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "tableRuleSetEMFObject", type = "Object", desc = "TableRuleSet EMF Model object."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "tableRuleEMFObjects", type = "Object", desc = "List of TableRule EMF Model objects.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Adds the TableRules to the TableRuleSet Object.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    public static void addTableRules(Object tableRuleSetEMFObject, Object tableRuleEMFObjects) {
		if (!(tableRuleSetEMFObject instanceof TableRuleSet)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", TableRuleSet.class.getName()));
		}
		if (!(tableRuleEMFObjects instanceof List<?>)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", List.class.getName()));
		}

		TableRuleSet tableRuleSet = (TableRuleSet) tableRuleSetEMFObject;
		tableRuleSet.getRule().addAll((List<TableRule>) tableRuleEMFObjects);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "sortTableRulesById",
            synopsis = "",
            signature = "sortTableRulesById(Object[] tableRuleEMFObjects)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "tableRuleEMFObjects", type = "Object", desc = "List of TableRule EMF Model objects.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Sorts the TableRules by Id Ascending.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    public static void sortTableRulesById(Object tableRuleEMFObjects) {
		if (!(tableRuleEMFObjects instanceof List<?>)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", List.class.getName()));
		}
		
		List<TableRule> tableRules = (List<TableRule>) tableRuleEMFObjects;
		Collections.sort(tableRules, new Comparator<TableRule>() {

			@Override
			public int compare(TableRule tableRule1, TableRule tableRule2) {
				long ruleId1 = Long.parseLong(tableRule1.getId());
				long ruleId2 = Long.parseLong(tableRule2.getId());
				if (ruleId1 < ruleId2) {
					return -1;
				} else if (ruleId1 > ruleId2) {
					return 1;
				} 				
				return 0;
			}
			
		});
    }
    
    @com.tibco.be.model.functions.BEFunction(
            name = "addTableRuleMetadata",
            synopsis = "",
            signature = "addTableRuleMetadata(Object tableRuleEMFObject, Object metadataEMFObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "tableRuleEMFObject", type = "Object", desc = "TableRule EMF Model object."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "metadataEMFObject", type = "Object", desc = "TableRule Metadata EMF Model object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Adds the Metadata to TableRule object.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    public static void addTableRuleMetadata(Object tableRuleEMFObject, Object metadataEMFObject) {
		if (!(tableRuleEMFObject instanceof TableRule)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", TableRule.class.getName()));
		}
		if (!(metadataEMFObject instanceof MetaData)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", MetaData.class.getName()));
		}

		TableRule tableRule = (TableRule) tableRuleEMFObject;
		tableRule.setMd((MetaData) metadataEMFObject);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "createTableRulevariable",
            synopsis = "",
            signature = "Object createTableRulevariable(String ruleId, String varId, String columnId, String expression, String displayValue, String comment, boolean isEnabled)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ruleId", type = "String", desc = "TableRule Id."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "varId", type = "String", desc = "TableRuleVariable Id."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "columnId", type = "String", desc = "Table Column Id."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "expression", type = "String", desc = "TableRuleVariable expression."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "displayValue", type = "String", desc = "TableRuleVariable DisplayValue."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "comment", type = "String", desc = "TableRuleVariable comment."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "isEnabled", type = "String", desc = "TableRuleVariable is Enabled.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "TableRuleVariable object."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Creates a TableRuleVariable Object.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    public static Object createTableRulevariable(String ruleId, String varId, String columnId, String expression, String displayValue, String comment, boolean isEnabled) {
    	TableRuleVariable ruleVariable = DtmodelFactory.eINSTANCE.createTableRuleVariable();
    	ruleVariable.setId(varId);
    	ruleVariable.setColId(columnId);
    	ruleVariable.setExpr(expression);
    	ruleVariable.setDisplayValue(displayValue);
    	ruleVariable.setComment(comment);
    	ruleVariable.setEnabled(isEnabled);
    	
    	return ruleVariable;
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "addCondition",
            synopsis = "",
            signature = "addCondition(Object tableRuleEMFObject, Object ruleVariableEMFObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "tableRuleEMFObject", type = "Object", desc = "TableRule EMF Model object."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ruleVariableEMFObject", type = "Object", desc = "TableRuleVariable condition EMF Model object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Add the condition to the TableRule Object.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    public static void addCondition(Object tableRuleEMFObject, Object ruleVariableEMFObject) {
		if (!(tableRuleEMFObject instanceof TableRule)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", TableRule.class.getName()));
		}
		if (!(ruleVariableEMFObject instanceof TableRuleVariable)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", TableRuleVariable.class.getName()));
		}

		TableRule tableRule = (TableRule) tableRuleEMFObject;
		EList<TableRuleVariable> conditions = tableRule.getCondition();
		conditions.add((TableRuleVariable) ruleVariableEMFObject);
    }    

    @com.tibco.be.model.functions.BEFunction(
            name = "addAction",
            synopsis = "",
            signature = "addAction(Object tableRuleEMFObject, Object ruleVariableEMFObject)",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "tableRuleEMFObject", type = "Object", desc = "TableRule EMF Model object."),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ruleVariableEMFObject", type = "Object", desc = "TableRuleVariable action EMF Model object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Name of the Decison Table."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Add the action to the TableRule Object.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    public static void addAction(Object tableRuleEMFObject, Object ruleVariableEMFObject) {
		if (!(tableRuleEMFObject instanceof TableRule)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", TableRule.class.getName()));
		}
		if (!(ruleVariableEMFObject instanceof TableRuleVariable)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", TableRuleVariable.class.getName()));
		}

		TableRule tableRule = (TableRule) tableRuleEMFObject;
		EList<TableRuleVariable> actions = tableRule.getAction();
		actions.add((TableRuleVariable) ruleVariableEMFObject);
    }    

    @com.tibco.be.model.functions.BEFunction(
            name = "createTableColumn",
            synopsis = "",
            signature = "Object createTableColumn(String columnId, String name, String property, String columnType, String columnAlias, boolean isArray, int propertyType, boolean isSubstitution, String defaultText)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "columnId", type = "String", desc = "Column Id."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "name", type = "String", desc = "Column name."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "property", type = "String", desc = "Column property path."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "columnType", type = "String", desc = "Column type."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "columnAlias", type = "String", desc = "Column alias."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "isArray", type = "String", desc = "Column property isArray."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyType", type = "String", desc = "Column property type."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "isSubstitution", type = "String", desc = "Column Is substitution."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "defaultText", type = "String", desc = "defaultValue")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Name of the Decison Table."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Creates the Table Column Object.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    public static Object createTableColumn(String columnId, String name, String property, String columnType, String columnAlias, boolean isArray, int propertyType, boolean isSubstitution, String defaultText) {
    	Column column = DtmodelFactory.eINSTANCE.createColumn();
    	column.setId(columnId);
    	column.setName(name);
    	column.setPropertyPath(property);
    	column.setColumnType(ColumnType.get(columnType));
    	column.setAlias(columnAlias);
    	column.setArrayProperty(isArray);
    	column.setPropertyType(propertyType);
    	column.setSubstitution(isSubstitution);
    	column.setDefaultCellText(defaultText);
    	return column;
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "addColumn",
            synopsis = "",
            signature = "addColumn(Object tableRuleSetEMFModelObject, Object columnEMFObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "tableRuleSetEMFModelObject", type = "Object", desc = "TableRuleSet EMF Model object."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "columnEMFObject", type = "Object", desc = "Column EMF Model object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Adds the column to the TableRuleSet object.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    public static void addColumn(Object tableRuleSetEMFModelObject, Object columnEMFObject) {
		if (!(tableRuleSetEMFModelObject instanceof TableRuleSet)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", TableRuleSet.class.getName()));
		}
		if (!(columnEMFObject instanceof Column)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Column.class.getName()));
		}

		TableRuleSet tableRuleSet = (TableRuleSet) tableRuleSetEMFModelObject;
		Columns columns = tableRuleSet.getColumns();
		if (columns == null) {
			columns = DtmodelFactory.eINSTANCE.createColumns();
			tableRuleSet.setColumns(columns);
		}
		columns.add((Column) columnEMFObject);
    }    

    @com.tibco.be.model.functions.BEFunction(
            name = "serializeTableRuleEMFObject",
            synopsis = "",
            signature = "serializeTableRuleEMFObject(Object tableRuleEMFObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "tableRuleEMFObject", type = "Object", desc = "TableRule EMF Model object."),
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Adds the column to the TableRuleSet object.",
            cautions = "",
            domain = "",
            example = ""
        )
    public static String serializeTableRuleEMFObject(Object tableRuleEMFObject) {
		if (!(tableRuleEMFObject instanceof TableRule)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", TableRule.class.getName()));
		}

		TableRule tableRule = (TableRule) tableRuleEMFObject;
		Map<String, Boolean> options = new HashMap<String, Boolean>();
		options.put(XMLResource.OPTION_DECLARE_XML, Boolean.FALSE);
		options.put(XMLResource.OPTION_FORMATTED, Boolean.FALSE);
		
		XMLHelperImpl xmlHelper = new XMLHelperImpl();
		ArrayList<TableRule> arrayList = new ArrayList<TableRule>();
		arrayList.add(tableRule);
		String xmlContents = null;
		try {
			xmlContents = XMLHelperImpl.saveString(options, arrayList, "UTF-8", xmlHelper);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return xmlContents;
    }    
    
    @com.tibco.be.model.functions.BEFunction(
        name = "deserializeTableRuleEMFObject",
        signature = "deserializeTableRuleEMFObject(String tableRuleContents)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "tableRuleContents", type = "String", desc = "TableRule Contents."),
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Deserialize TableRule contents to its EMF object.",
        cautions = "",
        domain = "",
        example = ""
    )
    public static Object deserializeTableRuleEMFObject(String tableRuleContents) {
		EObject eObject = null;
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(tableRuleContents.getBytes("UTF-8"));			
			XMLResource.XMLMap xmlMap = new XMLMapImpl();
			xmlMap.setNoNamespacePackage(DtmodelPackage.eINSTANCE);
			Map<String, XMLResource.XMLMap> options = new HashMap<String, XMLResource.XMLMap>();
			options.put(XMLResource.OPTION_XML_MAP, xmlMap);			
			eObject = CommonIndexUtils.deserializeEObject(bais, options);
			if (eObject instanceof TableRule) {
				return eObject;
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return null;
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "getVRFImplementations",
        signature = "Object getVRFImplementations(String scsRootURL, String projectName, String vrfPath, String earPath)",
        params = {
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "scsRootURL", type = "String", desc = "SCS root URL."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectName", type = "String", desc = "Project Name."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "vrfPath", type = "String", desc = "VRF Path."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "earPath", type = "String", desc = "Ear Path")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets all the implementations associated with the VRF.",
        cautions = "",
        domain = "",
        example = ""
    )
    public static Object getVRFImplementations(String scsRootURL, String projectName, String vrfPath, String earPath) {
    	List<String> dtsToUpdate = new ArrayList<String>();
		try {
			WebstudioFunctionUtils.loadProjectIndex(scsRootURL, projectName, earPath);
			
			List<DesignerElement> decisionTables = CommonIndexUtils.getAllElements(projectName, ELEMENT_TYPES.DECISION_TABLE);
			
			TableImpl table = null;
			for (DesignerElement dt : decisionTables) {
				if (dt instanceof DecisionTableElement) {
					table = (TableImpl) ((dt instanceof SharedDecisionTableElementImpl) ? ((SharedDecisionTableElementImpl)dt).getSharedImplementation() : ((DecisionTableElementImpl)dt).getImplementation());
						if (table.getImplements().equals(vrfPath)) {
							dtsToUpdate.add(table.getFolder() + table.getName());
						}
					}
				}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return dtsToUpdate.toArray(new String[dtsToUpdate.size()]);
    }    
}
