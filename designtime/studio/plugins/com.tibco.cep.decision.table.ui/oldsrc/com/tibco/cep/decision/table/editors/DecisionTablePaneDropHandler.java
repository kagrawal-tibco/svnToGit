package com.tibco.cep.decision.table.editors;

import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.text.MessageFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;

import com.jidesoft.decision.DecisionConstants;
import com.jidesoft.decision.DecisionDataModel;
import com.jidesoft.decision.DecisionField;
import com.jidesoft.decision.DecisionFieldArea;
import com.jidesoft.decision.DecisionTablePane;
import com.jidesoft.grid.TableScrollPane;
import com.jidesoft.grid.TableUtils;
import com.tibco.cep.decision.table.command.CommandFacade;
import com.tibco.cep.decision.table.command.ICommandCreationListener;
import com.tibco.cep.decision.table.command.impl.AddCommand;
import com.tibco.cep.decision.table.command.listeners.DecisionTableColumnAdditionCommandListener;
import com.tibco.cep.decision.table.command.memento.ColumnPositionStateMemento;
import com.tibco.cep.decision.table.editors.dnd.DefaultDecisionTableDnDDataConverter;
import com.tibco.cep.decision.table.editors.dnd.IDecisionTableDnDDataConverter;
import com.tibco.cep.decision.table.model.DecisionTableModelManager;
import com.tibco.cep.decision.table.model.dtmodel.Argument;
import com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.decision.table.ui.utils.Messages;
import com.tibco.cep.decision.table.utils.DTActionsSecurity;
import com.tibco.cep.decisionproject.ontology.Concept;
import com.tibco.cep.decisionproject.ontology.Event;
import com.tibco.cep.decisionproject.ontology.PrimitiveHolder;
import com.tibco.cep.decisionproject.ontology.Property;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.element.impl.PropertyDefinitionImpl;
import com.tibco.cep.security.authz.permissions.actions.Permit;
import com.tibco.cep.security.authz.utils.ResourceType;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.navigator.model.ArgumentTransfer;
import com.tibco.cep.studio.ui.navigator.model.EventPropertyNode;
import com.tibco.cep.studio.ui.navigator.model.PropertyNode;

/**
 * 
 * @author sasahoo
 * 
 */
public class DecisionTablePaneDropHandler implements DropTargetListener {
	private final static String DECISION_MANAGER_ENABLE_FLAG = "ACTIVATE_BUSINESS_USER"; 

    private DecisionTableModelManager decisionTableModelManager;

    private DecisionTableDesignViewer decisionTableDesignViewer;

    private DecisionFieldArea fieldArea;

    private TableTypes tableType;

    private DefaultDecisionTableDnDDataConverter defaultDecisoinTableDnDDataConverter =
            new DefaultDecisionTableDnDDataConverter();

    /**
     * @param pivotTablePane
     * @param tdecisionTableModelManager
     * @param decisionTableDesignViewer
     * @param fieldArea
     * @param tableType
     */
    public DecisionTablePaneDropHandler(
            DecisionTableModelManager tdecisionTableModelManager,
            DecisionTableDesignViewer decisionTableDesignViewer,
            DecisionFieldArea fieldArea, TableTypes tableType) {
        this.decisionTableModelManager = tdecisionTableModelManager;
        this.decisionTableDesignViewer = decisionTableDesignViewer;
        this.fieldArea = fieldArea;
        this.tableType = tableType;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.dnd.DropTargetListener#drop(java.awt.dnd.DropTargetDropEvent)
     */
    @Override
    public synchronized void drop(DropTargetDropEvent dropTargetDropEvent) {
        try {
            Transferable tr = dropTargetDropEvent.getTransferable();
            if (tr != null) {

                DecisionDataModel decisionDataModel = null;

                switch (tableType) {
                case DECISION_TABLE:
                    decisionDataModel =
                            decisionTableModelManager.getDtTableModel();
                    break;
                case EXCEPTION_TABLE:
                    decisionDataModel =
                            decisionTableModelManager.getExceptionTableModel();
                    break;
                }

                IDecisionTableEditorInput dtEditorInput =
                        (IDecisionTableEditorInput) decisionTableDesignViewer
                                .getEditor().getEditorInput();
                Object[] data =
                        getDndConverter().convertInputData(dropTargetDropEvent,
                                dtEditorInput);

                if (data == null) {
                    dropTargetDropEvent.rejectDrop();
                    return;
                }

                tr.getTransferDataFlavors();
                for (Object abs : data) {
                    if (abs instanceof Property) {
                        processProperty((Property) abs, decisionDataModel);
                    } else if (abs instanceof PrimitiveHolder) {
                        processPrimitiveHolder(abs,
                                decisionDataModel,
                                dropTargetDropEvent);
                    }
                    // } else if (abs instanceof RuleSetToolTreeObject) {
                    // processRuleSetToolTreeObject((RuleSetToolTreeObject)abs,
                    // decisionDataModel, dropTargetDropEvent);
                    // }
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
                        showMessageDialog("Drop Error", message, null);
                        dropTargetDropEvent.rejectDrop();
                    }
                }
                dropTargetDropEvent.dropComplete(true);

                ArgumentTransfer.getInstance().setSelection(null);
            } else {
                dropTargetDropEvent.rejectDrop();
            }

        } catch (Exception e) {
        	DecisionTableUIPlugin.log(e);
            dropTargetDropEvent.rejectDrop();
        }
    }

    private void processPrimitiveHolder(Object abs,
            DecisionDataModel decisionDataModel,
            DropTargetDropEvent dropTargetDropEvent) {
        // Supporting primitive types @since 3.0.2
        PrimitiveHolder holder = (PrimitiveHolder) abs;
        String propertyPath = holder.getFolder() + holder.getName();
        // Check for valid declaration arguments
        Table tableEModel = decisionTableModelManager.getTabelEModel();
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
        boolean foundColName = isColumnPresent(ruleFunctionPath, resourcePath);
        int areaType = fieldArea.getAreaType();
        String colName = holder.getName();
        colName = (isArray) ? colName + "[]" : colName;
        if (foundColName) {
            if (areaType == DecisionConstants.AREA_ACTION) {
                // Do not allow drop of primitive on action area as they are
                // supposed to be immutable
                String message =
                        Messages.getString("EditTable_message_primitive_action_drop_error",
                                colName);
                showMessageDialog("Drop Error", message, null);
            } else if (!isColumnAdded(decisionDataModel, colName)) {
                addField(colName,
                        propertyPath,
                        holder.getPrimitiveType(),
                        false,
                        decisionDataModel,
                        false,
                        true,
                        false);
            } else {
                String message =
                        MessageFormat
                                .format(Messages
                                        .getString("EditTable_message_column_exists_error"),
                                        colName,
                                        (areaType == DecisionConstants.AREA_CONDITION) ? "Condition"
                                                : "Action");
                showMessageDialog("Drop Error", message, null);
            }
        } else {
            dropTargetDropEvent.rejectDrop();
            showMessageDialog(Messages.getString("EditTable_title"),
                    "Invalid Argument",
                    propertyPath);
        }
    }

    /**
     * Check if a column has already been added to the table or not
     * 
     * @param dataModel
     * @param columnName
     * @return
     */
    private boolean isColumnAdded(DecisionDataModel dataModel, String columnName) {
        int areaType = fieldArea.getAreaType();
        List<DecisionField> decisionFields = null;
        switch (areaType) {
        case DecisionConstants.AREA_CONDITION:
            decisionFields = dataModel.getConditionFields();
            break;
        case DecisionConstants.AREA_ACTION:
            decisionFields = dataModel.getActionFields();
            break;
        }
        for (DecisionField decisionField : decisionFields) {
            if (decisionField.getName().equals(columnName)) {
                // The column is already added
                return true;
            }
        }
        return false;
    }

    /**
     * Read all decision table properties including those in the
     * contained/referenced types
     * 
     * @param tableEModel
     *            the {@link Table} model object
     * @param tableProps
     *            map of property to containment/reference types
     * @param argProps
     *            table arguments
     * @param projectName
     *            project name
     * @return Map containing all the properties mapped to there
     *         containment/reference types
     */
    private Map<PropertyDefinition, PROPERTY_TYPES> getAllTableProperties(
            Table tableEModel,
            Map<PropertyDefinition, PROPERTY_TYPES> tableProps,
            List<ArgumentProperty> argProps, String projectName) {

        List<Argument> args = tableEModel.getArgument();

        for (Argument arg : args) {
            ArgumentProperty prop = arg.getProperty();
            argProps.add(prop);
            String propPath = prop.getPath();
            com.tibco.cep.decision.table.model.dtmodel.ResourceType resourceType =
                    prop.getResourceType();
            List<PropertyDefinition> propertyDefs = null;
            switch (resourceType) {
            case CONCEPT:
                collectConceptProperties(tableProps, prop, projectName);
                break;
            case EVENT:
                com.tibco.cep.designtime.core.model.event.Event event = null;
                event = IndexUtils.getSimpleEvent(projectName, propPath);
                //Get all of the Properties of the Event (including those of Super events, if any) 
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
                collectConceptProperties(propDef,
                        tableProps,
                        projectName,
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
        List<PropertyDefinition> propertyDefs =
                concept.getAllPropertyDefinitions();
        for (PropertyDefinition propDef : propertyDefs) {
            if (propDef.getType() == PROPERTY_TYPES.CONCEPT
                    || propDef.getType() == PROPERTY_TYPES.CONCEPT_REFERENCE) {
                String propPath = propDef.getConceptTypePath();
                if (!conceptPaths.contains(propPath)) { // catch cyclic ref
                    conceptPaths.push(propPath); // push on stack
                    collectConceptProperties(propDef,
                            tableProps,
                            projectName,
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

    private void processProperty(Property property, DecisionDataModel datamodel)
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

        IDecisionTableEditorInput dtEditorInput =
                (IDecisionTableEditorInput) decisionTableDesignViewer
                        .getEditor().getEditorInput();
        Table tableEModel = dtEditorInput.getTableEModel();
        String projectName = dtEditorInput.getProjectName();
        Map<PropertyDefinition, PROPERTY_TYPES> tableProps =
                new HashMap<PropertyDefinition, PROPERTY_TYPES>();
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
            showMessageDialog(Messages.getString("EditTable_message_resource_not_scoped_error"),
                    Messages.getString("EditTable_message_resource_not_scoped_error"),
                    resourcePath);
            return;
        }
        // Add it back again
        final String propertyPath = resourcePath + "/" + propertyName;
        // check if compatible with rule function argument

        // check for ACL
        int areaType = fieldArea.getAreaType();
        
        String isEnabled = System.getProperty(DECISION_MANAGER_ENABLE_FLAG, "false");
		
        // If user is logged in as business user, he needs to be logged in RMS as well
        if (areaType == DecisionConstants.AREA_ACTION && "true".equalsIgnoreCase(isEnabled)) {
        	DecisionTableUIPlugin.debug(DecisionTablePaneDropHandler.class
                    .getName(),
                    "Checking modify permission on resource::{0}",
                    propertyPath);
            Permit permit = Permit.DENY;
            permit =
                    DTActionsSecurity.verifyAccess(propertyPath,
                            ResourceType.PROPERTY,
                            "modify");
            permit = Permit.ALLOW;
            if (Permit.DENY == permit) {
            	DecisionTableUIPlugin
                        .log("Drop not allowed ::No modify permission for resource::{0}",
                                propertyPath);
                showMessageDialog(Messages.getString("EditTable_title"),
                        Messages.getString("EditTable_message_aclcheckerror"),
                        propertyPath);
                return;
            }
        }
        String colName = null;

        /**
         * there is no need to check if declaration table has this alias but if
         * alias is not there then it's contained or referenced concept property
         */
        // if (foundColName && identifier != null) {
        if (identifier != null) {
            colName = identifier + "." + property.getName();
        }
        if (!isColumnAdded(datamodel, colName)) {
            addField(colName,
                    propertyPath,
                    propertyDataType,
                    isArrayProperty,
                    datamodel,
                    false,
                    false,
                    containedRefConcepProp);
        } else {
            // dropTargetDropEvent.rejectDrop();
            String message =
                    MessageFormat
                            .format(Messages
                                    .getString("EditTable_message_column_exists_error"),
                                    colName,
                                    (areaType == DecisionConstants.AREA_CONDITION) ? "Condition"
                                            : "Action");
            showMessageDialog("Drop Error", message, null);
        }
    }

    /**
     * @param ruleFunctionPath
     * @param resourcePath
     * @return
     * @since 3.0.2
     */
    private boolean isColumnPresent(String ruleFunctionPath, String resourcePath) {
        TableModel declTableModel =
                decisionTableModelManager.getDeclarationTableModel();
        if (declTableModel != null) {
            int rowCount = declTableModel.getRowCount();
            int columnCount = declTableModel.getColumnCount();
            for (int i = 0; i < rowCount; ++i) {
                for (int j = 0; j < columnCount; ++j) {
                    String value = (String) declTableModel.getValueAt(i, j);
                    if (value != null && ruleFunctionPath != null) {
                        value = ruleFunctionPath + "/" + value;
                        if (value != null && value.equals(resourcePath)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * @since 3.0.2
     * @param colName
     * @param propertyPath
     * @param propertyDataType
     * @param isArrayProperty
     * @param decisionDataModel
     * @param substitutionColumn
     * @param isPrimitive
     * @param isContainedRefConcepProp
     */
    private void addField(String colName, String propertyPath,
            int propertyDataType, boolean isArrayProperty,
            DecisionDataModel decisionDataModel, boolean substitutionColumn,
            boolean isPrimitive, boolean isContainedRefConcepProp) {

        DecisionTablePane decisionTablePane =
                decisionTableDesignViewer.getDecisionTablePane();
        DecisionTablePane exceptionTablePane =
                decisionTableDesignViewer.getExceptionTablePane();

        TableScrollPane tableScrollPane =
                (tableType == TableTypes.DECISION_TABLE) ? decisionTablePane
                        .getTableScrollPane() : exceptionTablePane
                        .getTableScrollPane();

        if (fieldArea.getAreaType() == DecisionConstants.AREA_CONDITION) {
            addField(colName,
                    tableScrollPane,
                    propertyPath,
                    propertyDataType,
                    isArrayProperty,
                    DecisionConstants.AREA_CONDITION,
                    decisionDataModel,
                    false,
                    isPrimitive,
                    substitutionColumn,
                    isContainedRefConcepProp);
        }
        /*
         * if (fieldArea.getAreaType() == PivotConstants.AREA_COLUMN) {
         * addField(colName,propertyPath,propertyDataType,
         * PivotConstants.AREA_COLUMN,defaultmodel, pivotDataModel,
         * substitutionColumn , isContainedRefConcepProp); }
         */
        if (fieldArea.getAreaType() == DecisionConstants.AREA_ACTION) {
            addField(colName,
                    tableScrollPane,
                    propertyPath,
                    propertyDataType,
                    isArrayProperty,
                    DecisionConstants.AREA_ACTION,
                    decisionDataModel,
                    false,
                    isPrimitive,
                    substitutionColumn,
                    isContainedRefConcepProp);
        }
        decisionDataModel.updateConditionFields();
        decisionDataModel.updateActionFields();

        if (decisionTablePane != null) {
            TableUtils.autoResizeAllColumns(decisionTablePane
                    .getTableScrollPane().getMainTable());
        }
        if (exceptionTablePane != null) {
            TableUtils.autoResizeAllColumns(exceptionTablePane
                    .getTableScrollPane().getMainTable());
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
                IEditorPart editor = decisionTableDesignViewer.getEditor();
                if (editor != null) {
                    shell = editor.getEditorSite().getShell();
                }
                if (shell != null) {
                    MessageDialog.openError(shell, title, newDesc);
                }
            }
        });

    }

    /**
     * @since 3.0.2
     * @param value
     * @param tableScrollPane
     * @param propertyPath
     * @param propertyDataType
     * @param isArrayProperty
     * @param areaType
     * @param decisionDataModel
     * @param isCustom
     * @param isPrimitive
     * @param substitutionColumn
     * @param isContainedRefConcepProp
     */
    private void addField(String value, TableScrollPane tableScrollPane,
            String propertyPath, int propertyDataType, boolean isArrayProperty,
            int areaType, DecisionDataModel decisionDataModel,
            boolean isCustom, boolean isPrimitive, boolean substitutionColumn,
            boolean isContainedRefConcepProp) {
        Table tableEModel = decisionTableModelManager.getTabelEModel();
        DecisionTableEditor decisionTableEditor =
                decisionTableDesignViewer.getEditor();
        IDecisionTableEditorInput decisionTableEditorInput =
                (IDecisionTableEditorInput) decisionTableEditor
                        .getEditorInput();
        String project = decisionTableEditorInput.getProjectName();
        // Check if at least one condition is present
        int conditionFieldCount = decisionDataModel.getConditionFieldCount();
        if (areaType == DecisionConstants.AREA_ACTION
                && conditionFieldCount == 0) {
            JOptionPane.showMessageDialog(null,
                    Messages.getString("ERROR_ADD_ACTION"),
                    Messages.getString("MESSAGE_TITLE_ERROR"),
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        /**
         * The command listener responsible for handling column creation
         */
        ICommandCreationListener<AddCommand<ColumnPositionStateMemento>, ColumnPositionStateMemento> columnCreationCommandListener =
                new DecisionTableColumnAdditionCommandListener(
                        decisionDataModel, tableEModel, decisionTableEditor,
                        value, propertyPath, propertyDataType, isArrayProperty,
                        areaType, isCustom, substitutionColumn);
        CommandFacade.getInstance().executeColumnAddition(project,
                tableEModel,
                tableType,
                columnCreationCommandListener);
    }

    /**
     * @param value
     * @return
     */
    @SuppressWarnings("unused")
    private Argument getArgument(String value) {
        Table tableEModel = decisionTableModelManager.getTabelEModel();
        if (tableEModel != null) {
            EList<Argument> argList = tableEModel.getArgument();
            for (Argument arg : argList) {
                if (arg.getProperty().getAlias().equalsIgnoreCase(value)) {
                    return arg;
                }
            }
        }
        return null;
    }

    @Override
    public void dragEnter(DropTargetDragEvent dropTargetEvent) {
        IDecisionTableEditorInput dtEditorInput =
                (IDecisionTableEditorInput) decisionTableDesignViewer
                        .getEditor().getEditorInput();

        getDndConverter().dragEnter(dropTargetEvent, dtEditorInput);
    }

    @Override
    public void dragExit(DropTargetEvent dropTargetEvent) {
        IDecisionTableEditorInput dtEditorInput =
                (IDecisionTableEditorInput) decisionTableDesignViewer
                        .getEditor().getEditorInput();

        getDndConverter().dragExit(dropTargetEvent, dtEditorInput);
    }

    @Override
    public void dragOver(DropTargetDragEvent dropTargetEvent) {
        IDecisionTableEditorInput dtEditorInput =
                (IDecisionTableEditorInput) decisionTableDesignViewer
                        .getEditor().getEditorInput();

        getDndConverter().dragOver(dropTargetEvent, dtEditorInput);
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dropTargetEvent) {
        IDecisionTableEditorInput dtEditorInput =
                (IDecisionTableEditorInput) decisionTableDesignViewer
                        .getEditor().getEditorInput();

        getDndConverter().dropActionChanged(dropTargetEvent, dtEditorInput);
    }

    /**
     * @return The drag and drop data converter from the editor inmput provider
     *         or <code>null</code> if the latter is to be used.
     */
    private IDecisionTableDnDDataConverter getDndConverter() {
        /*
         * First try to get the converter from the decision table editor input.
         */
        IDecisionTableEditorInput dtEditorInput =
                (IDecisionTableEditorInput) decisionTableDesignViewer
                        .getEditor().getEditorInput();

        IDecisionTableDnDDataConverter dndConverter =
                dtEditorInput.getDnDDataConverter();

        if (dndConverter == null) {
            /* Else revert to default. */
            dndConverter = defaultDecisoinTableDnDDataConverter;
        }

        return dndConverter;
    }

}
