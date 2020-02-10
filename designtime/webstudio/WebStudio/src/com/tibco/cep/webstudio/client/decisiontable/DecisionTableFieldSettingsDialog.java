package com.tibco.cep.webstudio.client.decisiontable;

import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.getActionColumns;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.VLayout;
import com.tibco.cep.webstudio.client.decisiontable.model.ArgumentData;
import com.tibco.cep.webstudio.client.decisiontable.model.ColumnType;
import com.tibco.cep.webstudio.client.decisiontable.model.PROPERTY_TYPES;
import com.tibco.cep.webstudio.client.decisiontable.model.TableColumn;
import com.tibco.cep.webstudio.client.decisiontable.model.TableRuleVariable;
import com.tibco.cep.webstudio.client.model.RequestParameter;
import com.tibco.cep.webstudio.client.widgets.AbstractWebStudioDialog;
import com.tibco.cep.webstudio.client.widgets.MultiSelectListBox;
import com.tibco.cep.webstudio.client.widgets.WebStudioMenubar;
import com.tibco.cep.webstudio.client.i18n.DTMessages;

public class DecisionTableFieldSettingsDialog extends AbstractWebStudioDialog {

	private AbstractDecisionTableEditor editor = null;
	private TextItem columnNameText = null;
	private TextItem columnAliasText = null;
	private TextItem idLabel = null;
	private TextItem item = null;
	private ListGridField columnField = null;
	
	private TableColumn column = null;
	private MultiSelectListBox domainComboBox;
	private ComboBoxItem boolItem;
	private CheckboxItem checkBoxItem;
	
	public DecisionTableFieldSettingsDialog(AbstractDecisionTableEditor editor, ListGridField columnField, TableColumn column) {
		this.editor = editor;
		this.columnField = columnField;
		this.column = column;
		this.setDialogWidth(500);
		this.setDialogHeight(150);
		this.setDialogTitle(dtMessages.dt_fieldSettings_dialog_title());
		this.setDialogHeaderIcon(WebStudioMenubar.ICON_PREFIX + "generatedeployable.gif");
		this.initialize();
		this.okButton.enable();
	}
	
	@Override
	public List<Widget> getWidgetList() {
		List<Widget> widgetList = new ArrayList<Widget>();
		widgetList.add(this.createFieldSettingsForm());
		return widgetList;
	}

	private VLayout createFieldSettingsForm() {
		VLayout fieldSettingsFormContainer = new VLayout(5);

		DynamicForm fieldSettingsForm = new DynamicForm();
		fieldSettingsForm.setWidth100();
		fieldSettingsForm.setNumCols(2);

		columnNameText = new TextItem("name", dtMessages.dt_fieldSettings_dialog_nameField());
		columnNameText.setValue(DecisionTableUtils.unEscapeColumnSubstitutionExpression(columnField.getName()));
		
		// column alias impl
		
		columnAliasText = new TextItem("alias", dtMessages.dt_fieldSettings_dialog_aliasField());
		columnAliasText.setValue(column.getAlias()!= null ? column.getAlias() : "");
		
		idLabel = new TextItem("id", dtMessages.dt_fieldSettings_dialag_idField());
		idLabel.setValue(column.getId());
		idLabel.setDisabled(true);
		
		// default implementation
		
		PROPERTY_TYPES type = PROPERTY_TYPES.get(Integer.parseInt(column.getPropertyType()));
		
        checkBoxItem = new CheckboxItem("includeExistingRules", dtMessages.dt_fieldSettings_dialag_includeExisitingRules());
        
        if(column.getDefaultCellText()==null){
        	checkBoxItem.disable();
        	
        }else if(column.getDefaultCellText().isEmpty() || column.getDefaultCellText().equalsIgnoreCase("") || column.getDefaultCellText().equalsIgnoreCase("null")){
			checkBoxItem.disable();
		}else{
			checkBoxItem.enable();
		}
        
		
		if(type == PROPERTY_TYPES.BOOLEAN){
			boolItem = new ComboBoxItem("defaultexpr" , dtMessages.dt_fieldSettings_dialag_defaultExpr());
			boolItem.setValueMap("True","False");
			boolItem.setValue("False");
			if(column.getDefaultCellText() != null){
				if(!(column.getDefaultCellText().isEmpty() || column.getDefaultCellText().equalsIgnoreCase("") || column.getDefaultCellText().equalsIgnoreCase("null"))){
					boolItem.setValue(column.getDefaultCellText());
				}
			}
			boolItem.addChangedHandler(new ChangedHandler() {
				
				@Override
				public void onChanged(ChangedEvent event) {
					if(boolItem.getValue().toString().equalsIgnoreCase("false") || boolItem.getValue().toString().equalsIgnoreCase("")){
						checkBoxItem.disable();
					}else{
						checkBoxItem.enable();
					}
					
				}
			});
			fieldSettingsForm.setItems(columnNameText, columnAliasText, idLabel, boolItem, checkBoxItem);
		}else{
		
		item = new TextItem("defaultexpr" , dtMessages.dt_fieldSettings_dialag_defaultExpr());
		if(column.getDefaultCellText() != null){
			if(!(column.getDefaultCellText().isEmpty() || column.getDefaultCellText().equalsIgnoreCase("") || column.getDefaultCellText().equalsIgnoreCase("null"))){
				item.setValue(column.getDefaultCellText());
			}
		}
		
		item.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				if(item.getValue()==null){
					checkBoxItem.disable();
				}else if(item.getValue().toString().isEmpty() || item.getValue().toString().equalsIgnoreCase("")){
					checkBoxItem.disable();
				}else{
					checkBoxItem.enable();
				}
				
			}
		});
			 
		if(column.hasAssociatedDomain()){

			String columnDelimiter = null;
			if (com.tibco.cep.webstudio.client.decisiontable.model.ColumnType.ACTION.getName().equalsIgnoreCase(column.getColumnType())) {
				columnDelimiter = DecisionTableConstants.ACTION_COLUMN_DELIMITER;
			} else {
				columnDelimiter = DecisionTableConstants.CONDITION_COLUMN_DELIMITER;
			}

			String argumentPath =  "", argumentType = "";
			String columnName = column.getName();			
			int index = columnName.indexOf(columnDelimiter);
			String alias =  columnName.substring(0, index);
			String propertyName = columnName.substring(index + columnDelimiter.length());
			for (ArgumentData argData : editor.getTable().getArguments()) {
				if (argData.getAlias().equals(alias)) {
					argumentPath = argData.getPropertyPath();
					argumentType = argData.getResourceType();
					break;
				}
			}

			List<TableColumn> cols = getActionColumns(editor.getTable().getDecisionTable());
			boolean isActionCol = false;
			String domainPath = editor.getProjectName() + argumentPath + "/" + propertyName;
			domainPath = domainPath.replace("/", "$");
			if (cols.contains(column)) {
				isActionCol = true;
				domainPath = domainPath + "$$";
			}
			DomainDataSource domainDataSource = DomainDataSource.getInstance(domainPath);
			domainDataSource.clearRequestParameters();		
			domainDataSource.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PROJECT_NAME, editor.getProjectName()));
			domainDataSource.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_ARGUMENT_PATH, argumentPath));
			domainDataSource.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_ARGUMENT_TYPE, argumentType));
			domainDataSource.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_ARGUMENT_PROPERTY_NAME, propertyName));			
			domainDataSource.addRequestParameters(new RequestParameter("isConditionColumn", !isActionCol));

			domainComboBox = new MultiSelectListBox(item, domainDataSource, !isActionCol, "value", column.getDefaultCellText() , " || ", null, type, column, editor, true);
			domainComboBox.setIgnoreExistingCheckbox(checkBoxItem);
		}
		 
		
		fieldSettingsForm.setItems(columnNameText, columnAliasText, idLabel, item, checkBoxItem);
		}
		fieldSettingsFormContainer.addMember(fieldSettingsForm);
		return fieldSettingsFormContainer;
	}

	
	@Override
	public void onAction() {
		String columnName = columnNameText.getValueAsString();
		boolean isVarString = (ColumnType.CONDITION.getLiteral().equals(this.column.getColumnType()) || ColumnType.CUSTOM_CONDITION.getLiteral().equals(this.column.getColumnType())) ? 
				DecisionTableUtils.isConditionVarString(columnName) : DecisionTableUtils.isActionVarString(columnName);
		if (isVarString) {
			columnName = DecisionTableUtils.escapeColumnSubstitutionExpression(columnName);
		}
		this.column.setSubstitution(isVarString);
		this.column.setName(columnName);
		this.column.setAlias(columnAliasText.getValueAsString());
		if(PROPERTY_TYPES.get(Integer.parseInt(column.getPropertyType())) == PROPERTY_TYPES.BOOLEAN ){
			this.column.setDefaultCellText(boolItem.getValueAsString());
		}else{
			this.column.setDefaultCellText(item.getValueAsString());
		}
		
		// default field setting
		
		if(column.getDefaultCellText()!=null){
			
			if(checkBoxItem.getValueAsBoolean() && !checkBoxItem.isDisabled()){

			List<com.tibco.cep.webstudio.client.decisiontable.model.TableRule> ruleList = editor.getTable().getDecisionTable().getTableRules();

			for(com.tibco.cep.webstudio.client.decisiontable.model.TableRule trule : ruleList){
                boolean isruleVariablePresent = false; 
				for(com.tibco.cep.webstudio.client.decisiontable.model.TableRuleVariable truleVariable : trule.getConditions()){
					if(truleVariable.getColumnId().equalsIgnoreCase(column.getId())){
						isruleVariablePresent = true;
						if(truleVariable.getExpression().equalsIgnoreCase("")){
							truleVariable.setExpression(column.getDefaultCellText()); 
						}
					}
				}

				for(com.tibco.cep.webstudio.client.decisiontable.model.TableRuleVariable truleVariable : trule.getActions()){
					if(truleVariable.getColumnId().equalsIgnoreCase(column.getId())){
						isruleVariablePresent = true;
						if(truleVariable.getExpression().equalsIgnoreCase("")){
							truleVariable.setExpression(column.getDefaultCellText()); 
						}

					}
				}
				if(!isruleVariablePresent){
					if(ColumnType.CONDITION.getLiteral().equals(column.getColumnType()) || ColumnType.CUSTOM_CONDITION.getLiteral().equals(column.getColumnType())){
						trule.addCondition(new TableRuleVariable(column.getName(), column.getId(), column.getDefaultCellText(), true, "", trule));
					}else{
						trule.addAction(new TableRuleVariable(column.getName(), column.getId(), column.getDefaultCellText(), true, "", trule));
					}
				}
			}
		  }
		}
		
		editor.loadDecisionTable(false, false, false, this.column.getId(),  this.column.getName());
		editor.makeDirty();
		this.preDestroy();
		destroy();
		this.postDestory();		
	}

}
