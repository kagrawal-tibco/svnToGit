package com.tibco.cep.studio.decision.table.ui.dnd;

import java.text.MessageFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.command.LayerCommandUtil;
import org.eclipse.nebula.widgets.nattable.coordinate.ColumnPositionCoordinate;
import org.eclipse.nebula.widgets.nattable.coordinate.PositionCoordinate;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.nebula.widgets.nattable.group.ColumnGroupHeaderLayer;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.tibco.cep.decision.table.command.CommandFacade;
import com.tibco.cep.decision.table.command.ICommandCreationListener;
import com.tibco.cep.decision.table.command.impl.AddCommand;
import com.tibco.cep.decision.table.command.memento.ColumnPositionStateMemento;
import com.tibco.cep.decision.table.model.dtmodel.Argument;
import com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.decision.table.utils.Messages;
import com.tibco.cep.decisionproject.ontology.Concept;
import com.tibco.cep.decisionproject.ontology.Event;
import com.tibco.cep.decisionproject.ontology.PrimitiveHolder;
import com.tibco.cep.decisionproject.ontology.Property;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.element.impl.PropertyDefinitionImpl;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.decision.table.command.listeners.DecisionTableColumnAdditionCommandListener;
import com.tibco.cep.studio.decision.table.configuration.DTLabelAccumulator;
import com.tibco.cep.studio.decision.table.editor.DTBodyLayerStack;
import com.tibco.cep.studio.decision.table.editor.DTColumnHeaderLayerStack;
import com.tibco.cep.studio.decision.table.editor.IDecisionTableEditor;
import com.tibco.cep.studio.decision.table.providers.SpanningGlazedListsDataProvider;
import com.tibco.cep.studio.ui.navigator.model.ArgumentTransfer;
import com.tibco.cep.studio.ui.navigator.model.EventPropertyNode;
import com.tibco.cep.studio.ui.navigator.model.PropertyNode;

public class DTDropListener implements DropTargetListener {

	private final NatTable natTable;
	private final IDecisionTableEditor editor;
	private final TableTypes tableType;
	private ColumnGroupHeaderLayer columnHeaderStack ;
	private	ILayer selectionLayer;
	private DTBodyLayerStack<TableRule> bodyLayerStack;
	
	public DTDropListener(final NatTable fNatTable,
			IDecisionTableEditor editor,
			TableTypes tableType) {
		this.natTable = fNatTable;
		this.editor = editor;
		this.tableType = tableType;
		columnHeaderStack = ((DTColumnHeaderLayerStack<TableRule>) ((GridLayer)natTable.getLayer()).getColumnHeaderLayer()).getColumnGroupHeaderLayer();
		bodyLayerStack = (DTBodyLayerStack<TableRule>) ((GridLayer)natTable.getLayer()).getBodyLayer();
		selectionLayer = bodyLayerStack.getSelectionLayer();
		
	}

	@Override
	public void dragEnter(DropTargetEvent event) {
		// System.out.println("1");

	}

	@Override
	public void dragLeave(DropTargetEvent event) {
		// System.out.println("2");
	}

	@Override
	public void dragOperationChanged(DropTargetEvent event) {
		// System.out.println("3");

	}

	@Override
	public void dragOver(DropTargetEvent event) {
		// System.out.println("4");
	}

	@Override
	public void drop(final DropTargetEvent event) {
		try {
			
			Point point = natTable.toControl(event.x, event.y);
			ColumnPositionCoordinate columnCoords = new ColumnPositionCoordinate(columnHeaderStack, natTable.getColumnPositionByX(point.x));
			int index = natTable.getColumnIndexByPosition(columnCoords.columnPosition);
			
			PositionCoordinate positionCoords = new PositionCoordinate(
                    natTable,
                    natTable.getColumnPositionByX(point.x),
                    natTable.getRowPositionByY(point.y));
			positionCoords = LayerCommandUtil.convertPositionToTargetContext(positionCoords, selectionLayer);
    
			if(positionCoords != null){
				Object trv = editor.getModelDataByPosition(positionCoords.getColumnPosition(), positionCoords.getRowPosition(), natTable);
				if (trv == null) {
					// no value has been set - create a new TRV and set the value
					DTBodyLayerStack<TableRule> targetLayer = (DTBodyLayerStack<TableRule>) ((GridLayer)natTable.getLayer()).getBodyLayer();
					SpanningGlazedListsDataProvider<TableRule> dataProvider = targetLayer.getBodyDataProvider();
					dataProvider.setDataValue(positionCoords.getColumnPosition(), positionCoords.getRowPosition(), "");
					trv = editor.getModelDataByPosition(positionCoords.getColumnPosition(), positionCoords.getRowPosition(), natTable);
				}
				if (trv != null && trv instanceof TableRuleVariable) {
					TableRuleVariable ruleVariable = (TableRuleVariable) trv;
					String newExpr = ruleVariable.getExpr() != null ? ruleVariable.getExpr() : "";
					newExpr += (String)event.data;
					if(event.data instanceof String) {
						ruleVariable.setExpr(newExpr);
					}
					natTable.refresh();
					editor.modified();
				}
				return;
			}
    
			TableRuleSet targetRuleSet = null;
			switch (tableType) {
			case DECISION_TABLE:
				targetRuleSet = editor.getTable().getDecisionTable();
				break;
			case EXCEPTION_TABLE:
				targetRuleSet = editor.getTable().getExceptionTable();
				break;
			}

			ColumnType colType = null;
			if(targetRuleSet.getColumns() != null && !targetRuleSet.getColumns().getColumn().isEmpty()){
				if(index < 0) {//Fields dropped towards right of of Condition/Action area should be treated as New action. (Irrespective of isActionAdded()) 
					colType = ColumnType.ACTION;
				} else{
					Column col = targetRuleSet.getColumns().getColumn().get(index);
					colType = col.getColumnType();
					if(ColumnType.CONDITION.equals(colType) || ColumnType.CUSTOM_CONDITION.equals(colType)){
						colType = ColumnType.CONDITION;
					} else{
						colType = ColumnType.ACTION;
					}
				}
			} else{
				colType = ColumnType.CONDITION;
			}
			
			Object[] data = ArgumentTransfer.getInstance().getTransferData();
			if (data == null) {
				return;
			}
			for (Object abs : data) {
				if (abs instanceof Property) {
					if(ColumnType.ACTION.equals(colType) || ColumnType.CUSTOM_ACTION.equals(colType)){
						if(!isConditionAdded()) {
							String message = Messages.getString("ERROR_ADD_ACTION");
							showMessageDialog("Drop Error", message, null);
							return;
						}
					}

					processProperty((Property) abs, targetRuleSet, colType);
				} else if (abs instanceof PrimitiveHolder) {
					processPrimitiveHolder(abs, targetRuleSet, colType);
				}
				else {
					String message = null;
					if(abs instanceof PropertyNode && ((PropertyNode)abs).getEntity() instanceof PropertyDefinitionImpl){
						PropertyDefinitionImpl prop = (PropertyDefinitionImpl)((PropertyNode)abs).getEntity();
						message = Messages.getString("EditTable_message_can_not_drag_drop", prop.getName(), prop.getOwner().getName());
					} else if(abs instanceof EventPropertyNode && ((EventPropertyNode)abs).getEntity() instanceof PropertyDefinitionImpl ){
						PropertyDefinitionImpl prop = (PropertyDefinitionImpl)((EventPropertyNode)abs).getEntity();
						message = Messages.getString("EditTable_message_can_not_drag_drop", prop.getName(), prop.getOwner().getName());
					} else{
						message =  Messages.getString("EditTable_message_resource_not_scoped_error");
					}

				}
			}
			DTLabelAccumulator labelAccumulator 
			= new DTLabelAccumulator(bodyLayerStack, editor, natTable, editor.getTable().getDecisionTable(), editor.getTable().getOwnerProjectName());
			bodyLayerStack.getBodyDataLayer().setConfigLabelAccumulator(labelAccumulator);

		} catch(Exception e){
			e.printStackTrace();
		}
	}


	private void addColumn(TableRuleSet targetRuleSet, 
			NatTable targetTable, 
			ColumnType colType,
			String projectName,
			String columnName,
			String propertyPath, 
			int propertyDataType, 
			boolean isArrayProperty,
			boolean isCustom, 
			boolean isSubstitutionColumn) {
		
		ICommandCreationListener<AddCommand<ColumnPositionStateMemento>, ColumnPositionStateMemento> columnCreationCommandListener = new DecisionTableColumnAdditionCommandListener(
				editor, targetRuleSet, targetTable, colType, projectName, columnName, propertyPath, propertyDataType, isArrayProperty, isCustom, isSubstitutionColumn);
		CommandFacade.getInstance().executeColumnAddition(
				editor.getTable().getOwnerProjectName(), editor.getTable(),
				tableType, columnCreationCommandListener);
	}

	@Override
	public void dropAccept(DropTargetEvent event) {
	}

	private void processProperty(Property property, TableRuleSet targetRuleSet, ColumnType columnType)
			throws Exception {
		String identifier = null;
		int propertyDataType = property.getDataType();
		String resourcePath = null;
		String propertyName = property.getName();

		boolean isArrayProperty = property.isMultiple();
		if (isArrayProperty) {
			propertyName = propertyName.substring(0, propertyName.indexOf('['));
		}
		if (property.eContainer() instanceof Concept) {
			Concept concept = (Concept) property.eContainer();
			identifier = concept.getAlias();
			resourcePath = concept.getFolder() + concept.getName();
		} else if (property.eContainer() instanceof Event) {
			/**
			 * Please put a check whether the rule function is referenced as a
			 * PP for any destination. If and only if the above is true, the
			 * following should be commented.
			 */
			/*
			 * CR 1-A3XS57 if (fieldArea.getAreaType() ==
			 * DecisionConstants.AREA_ACTION) {
			 * showMessageDialog(Messages.EditTable_title
			 * ,Messages.EditTable_message_eventdroperror, ""); return; }
			 */
			Event event = (Event) (property.eContainer());
			resourcePath = event.getFolder() + event.getName();
			identifier = event.getAlias();
		}

		Table tableEModel = editor.getTable();
		String projectName = editor.getProjectName();
		Map<PropertyDefinition, PROPERTY_TYPES> tableProps = new HashMap<PropertyDefinition, PROPERTY_TYPES>();
		List<ArgumentProperty> argProps = new ArrayList<ArgumentProperty>();
		getAllTableProperties(tableEModel, tableProps, argProps, projectName);

		boolean resourceScoped = false;
		boolean containedRefConcepProp = false;

		for (ArgumentProperty prop : argProps) { // check argument validity
			String propAlias = prop.getAlias();
			if (propAlias.equals(identifier)
					|| identifier.startsWith(propAlias + '.')) {
				for (PropertyDefinition propDef : tableProps.keySet()) {
					// Check property validity
					PROPERTY_TYPES propType = tableProps.get(propDef);
					if (propDef.getName().equals(propertyName)) {
						if (PROPERTY_TYPES.CONCEPT == propType
								|| PROPERTY_TYPES.CONCEPT_REFERENCE == propType) {
							containedRefConcepProp = true;
						}
						resourceScoped = true;
					}
				}
				if (prop.isArray()) {
					// Replace propAlias if exists in identifier
					identifier = identifier.replace(propAlias, "");
					identifier = propAlias + "[]" + identifier;
				}
			}
		}

		if (!resourceScoped) {
			/*
			 * showMessageDialog(Messages.getString(
			 * "EditTable_message_resource_not_scoped_error"),
			 * Messages.getString
			 * ("EditTable_message_resource_not_scoped_error"), resourcePath);
			 */
			return;
		}
		// Add it back again
		final String propertyPath = resourcePath + "/" + propertyName;
		// check if compatible with rule function argument

		/*
		 * // check for ACL int areaType = fieldArea.getAreaType();
		 * 
		 * String isEnabled = System.getProperty(DECISION_MANAGER_ENABLE_FLAG,
		 * "false");
		 */
		// If user is logged in as business user, he needs to be logged in RMS
		// as well
		/*
		 * if (areaType == DecisionConstants.AREA_ACTION &&
		 * "true".equalsIgnoreCase(isEnabled)) {
		 * DecisionTableUIPlugin.debug(DecisionTablePaneDropHandler.class
		 * .getName(), "Checking modify permission on resource::{0}",
		 * propertyPath); Permit permit = Permit.DENY; permit =
		 * DTActionsSecurity.verifyAccess(propertyPath, ResourceType.PROPERTY,
		 * "modify"); permit = Permit.ALLOW; if (Permit.DENY == permit) {
		 * DecisionTableUIPlugin
		 * .log("Drop not allowed ::No modify permission for resource::{0}",
		 * propertyPath);
		 * showMessageDialog(Messages.getString("EditTable_title"),
		 * Messages.getString("EditTable_message_aclcheckerror"), propertyPath);
		 * return; } }
		 */
		String colName = null;

		/**
		 * there is no need to check if declaration table has this alias but if
		 * alias is not there then it's contained or referenced concept property
		 */
		// if (foundColName && identifier != null) {
		if (identifier != null) {
			colName = identifier + "." + property.getName();
		}
		 
		if (!isColumnAdded(colName, columnType)) {
			addColumn(targetRuleSet, natTable, columnType, projectName, colName, propertyPath, propertyDataType, isArrayProperty, false, false);
		} else {
			String message = MessageFormat
					.format(Messages
							.getString("EditTable_message_column_exists_error"),
							colName,
							(columnType.equals(ColumnType.CONDITION)) ? "Condition"	: "Action");
			showMessageDialog("Drop Error", message, null);
		}
		
	}

	private boolean isColumnAdded(String colName, ColumnType colType) {
		TableRuleSet tableRuleSet = (tableType == TableTypes.DECISION_TABLE) ? editor.getTable().getDecisionTable() : editor.getTable().getExceptionTable();
		Columns columnsModel = tableRuleSet.getColumns();
		if (columnsModel != null) {
			EList<Column> columns = columnsModel.getColumn();
			for (Column column : columns) {
				if (colName.equals(column.getName())
						&& colType.equals(column.getColumnType())) {
					return true;
				}
			}
		}

		return false;
	}
	
	private boolean isConditionAdded() {
		TableRuleSet tableRuleSet = (tableType == TableTypes.DECISION_TABLE) ? editor.getTable().getDecisionTable() : editor.getTable().getExceptionTable();
		Columns columnsModel = tableRuleSet.getColumns();
		if (columnsModel != null) {
			EList<Column> columns = columnsModel.getColumn();
			for (Column column : columns) {
				if (ColumnType.CONDITION.equals(column.getColumnType()) || ColumnType.CUSTOM_CONDITION.equals(column.getColumnType())) {
					return true;
				}
			}
		}

		return false;
	}
	
	private Map<PropertyDefinition, PROPERTY_TYPES> getAllTableProperties(
			Table tableEModel,
			Map<PropertyDefinition, PROPERTY_TYPES> tableProps,
			List<ArgumentProperty> argProps, String projectName) {

		List<Argument> args = tableEModel.getArgument();

		for (Argument arg : args) {
			ArgumentProperty prop = arg.getProperty();
			argProps.add(prop);
			String propPath = prop.getPath();
			com.tibco.cep.decision.table.model.dtmodel.ResourceType resourceType = prop
					.getResourceType();
			List<PropertyDefinition> propertyDefs = null;
			switch (resourceType) {
			case CONCEPT:
				collectConceptProperties(tableProps, prop, projectName);
				break;
			case EVENT:
				com.tibco.cep.designtime.core.model.event.Event event = null;
				event = IndexUtils.getSimpleEvent(projectName, propPath);
				// Get all of the Properties of the Event (including those of
				// Super events, if any)
				propertyDefs = event.getAllUserProperties();
				for (PropertyDefinition propDef : propertyDefs) {
					tableProps.put(propDef, propDef.getType());
				}
				break;
			default:
				break;
			}
		}

		return tableProps;
	}

	/**
	 * Delegate method
	 * 
	 * @param tableProps
	 *            map of property to containment/reference types
	 * @param argProp
	 *            the argument property object for the selected value
	 * @param projectName
	 *            project name
	 * @see #collectConceptProperties(PropertyDefinition, Map, String, Deque)
	 */
	private void collectConceptProperties(
			Map<PropertyDefinition, PROPERTY_TYPES> tableProps,
			ArgumentProperty argProp, String projectName) {

		com.tibco.cep.designtime.core.model.element.Concept concept = null;
		String conceptPath = argProp.getPath();
		concept = IndexUtils.getConcept(projectName, conceptPath);

		Deque<String> conceptPaths = new ArrayDeque<String>();
		conceptPaths.push(conceptPath);

		for (PropertyDefinition propDef : concept.getAllPropertyDefinitions()) {
			if (propDef.getType() == PROPERTY_TYPES.CONCEPT
					|| propDef.getType() == PROPERTY_TYPES.CONCEPT_REFERENCE) {
				conceptPaths.push(propDef.getConceptTypePath()); // stack push
				collectConceptProperties(propDef, tableProps, projectName,
						conceptPaths);
				conceptPaths.pop(); // stack pop
			} else {
				tableProps.put(propDef, propDef.getType());
			}
		}
	}

	/**
	 * Collects properties of contained and referenced concepts
	 * 
	 * @param parentPropertyDef
	 *            the {@link PropertyDefinition} object
	 * @param tableProps
	 *            map of property to containment/reference types
	 * @param projectName
	 *            project name
	 */
	private void collectConceptProperties(PropertyDefinition parentPropertyDef,
			Map<PropertyDefinition, PROPERTY_TYPES> tableProps,
			String projectName, Deque<String> conceptPaths) {

		PROPERTY_TYPES propType = parentPropertyDef.getType();
		String parentPropPath = parentPropertyDef.getConceptTypePath();
		com.tibco.cep.designtime.core.model.element.Concept concept = null;
		concept = IndexUtils.getConcept(projectName, parentPropPath);
		List<PropertyDefinition> propertyDefs = concept
				.getAllPropertyDefinitions();
		for (PropertyDefinition propDef : propertyDefs) {
			if (propDef.getType() == PROPERTY_TYPES.CONCEPT
					|| propDef.getType() == PROPERTY_TYPES.CONCEPT_REFERENCE) {
				String propPath = propDef.getConceptTypePath();
				if (!conceptPaths.contains(propPath)) { // catch cyclic ref
					conceptPaths.push(propPath); // push on stack
					collectConceptProperties(propDef, tableProps, projectName,
							conceptPaths);
					conceptPaths.pop(); // pop from stack
				} else {
					// Nothing, don't collect the properties, they are already
					// being added
				}
			} else {
				tableProps.put(propDef, propType);
			}
		}
	}
	
	  private void processPrimitiveHolder(Object abs, TableRuleSet targetRuleSet, ColumnType columnType) {
	        // Supporting primitive types @since 3.0.2
	        PrimitiveHolder holder = (PrimitiveHolder) abs;
	        String propertyPath = holder.getFolder() + holder.getName();
	        // Check for valid declaration arguments
	        Table tableEModel = editor.getTable();
	        String ruleFunctionPath = tableEModel.getImplements();

	        boolean isArray = false;
	        for (Argument argument : tableEModel.getArgument()) {
	            ArgumentProperty argumentProperty = argument.getProperty();
	            if (!argumentProperty.getAlias().equals(holder.getName())) {
	                continue;
	            }
	            isArray = argumentProperty.isArray();
	        }
	        String resourcePath = ruleFunctionPath + "/" + propertyPath;
	        String colName = holder.getName();
	        colName = (isArray) ? colName + "[]" : colName;
	            if (ColumnType.ACTION.equals(columnType)) {
	                // Do not allow drop of primitive on action area as they are
	                // supposed to be immutable
	                String message =
	                        Messages.getString("EditTable_message_primitive_action_drop_error",
	                                colName);
	                showMessageDialog("Drop Error", message, null);
	            } else if (!isColumnAdded(colName, columnType)) {
	                addColumn(targetRuleSet, natTable, columnType, tableEModel.getOwnerProjectName(), colName, propertyPath,  holder.getPrimitiveType(), isArray, false, false);
	           
	        } else {
	            showMessageDialog(Messages.getString("EditTable_title"),
	                    "Invalid Argument",
	                    propertyPath);
	        }
	    }
	  
	  
	  /**
     * @param title
     * @param desc
     * @param propertyPath
     * @since 3.0.2
     */
    private void showMessageDialog(final String title, final String desc,
            final String propertyPath) {
        final String newDesc =
                (propertyPath != null) ? desc + " " + propertyPath : desc;
        Display.getDefault().asyncExec(new Runnable() {
            @Override
            public void run() {
                // Shell shell =
                // PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
                Shell shell = null;
                if (editor != null) {
                    shell = editor.getEditorSite().getShell();
                }
                if (shell != null) {
                    MessageDialog.openError(shell, title, newDesc);
                }
            }
        });

    }


}
