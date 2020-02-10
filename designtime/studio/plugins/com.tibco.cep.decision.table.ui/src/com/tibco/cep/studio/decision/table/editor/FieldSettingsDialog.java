package com.tibco.cep.studio.decision.table.editor;

import java.util.ArrayList;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.nebula.widgets.nattable.ui.menu.MenuItemProviders;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.decision.table.command.CommandFacade;
import com.tibco.cep.decision.table.command.ICommandCreationListener;
import com.tibco.cep.decision.table.command.impl.RenameCommand;
import com.tibco.cep.decision.table.command.memento.ColumnAliasStateMemento;
import com.tibco.cep.decision.table.command.memento.ColumnDefaultCellTextStateMemento;
import com.tibco.cep.decision.table.command.memento.ColumnNameStateMemento;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.DtmodelFactory;
import com.tibco.cep.decision.table.model.dtmodel.PropertyType;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.decisionproject.util.DTDomainUtil;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.domain.DomainEntry;
import com.tibco.cep.designtime.core.model.domain.impl.RangeImpl;
import com.tibco.cep.studio.decision.table.command.listeners.DecisionTableColumnAliasCommandListener;
import com.tibco.cep.studio.decision.table.command.listeners.DecisionTableColumnDefaultCellTextCommandListener;
import com.tibco.cep.studio.decision.table.command.listeners.DecisionTableColumnRenameCommandListener;
import com.tibco.cep.studio.decision.table.constraintpane.FieldSettingMultiSelectComboBox;
import com.tibco.cep.studio.decision.table.providers.DTColumnPropertyAccessor;
import com.tibco.cep.studio.decision.table.utils.DecisionTableUtil;
import com.tibco.cep.studio.decision.table.validation.FieldValidationContext;
import com.tibco.cep.studio.decision.table.validation.FieldValidationContext.FieldValidationContextType;
import com.tibco.cep.studio.decision.table.validation.FieldValidationResult;
import com.tibco.cep.studio.decision.table.validation.IFieldValidationListener;
import com.tibco.cep.studio.decision.table.validation.impl.DefaultFieldNameValidationListener;



public class FieldSettingsDialog extends Dialog{

	private Text nameText;
	private Text aliasText;
	private Text IDText;
	private Text defaultFieldText;
	private SelectionEvent event;
	private NatTable natTable;
	private int colPos;
	private DTColumnPropertyAccessor columnPropertyAccessor;
	private TableTypes tableType;
	private DecisionTableDesignViewer formViewer;
	private IFieldValidationListener<Column> initialFieldValidationListener = new DefaultFieldNameValidationListener();
	private ArrayList<String> domainValueArray = new ArrayList<String>();
	private FieldSettingMultiSelectComboBox multiselectCombo;
	private Combo defaultTextBoolCombo;
	private Button applyToButton;

	protected FieldSettingsDialog(Shell parentShell, SelectionEvent e, NatTable natTable, TableTypes tableType, DecisionTableDesignViewer formViewer) {
		super(parentShell);
		this.event = e;
		this.natTable = natTable;
		this.colPos = MenuItemProviders.getNatEventData(event).getColumnPosition();
		DTColumnHeaderLayerStack<TableRule> columnHeaderLayer = (DTColumnHeaderLayerStack<TableRule>) ((GridLayer)natTable.getLayer()).getColumnHeaderLayer();
		this.columnPropertyAccessor = (DTColumnPropertyAccessor) columnHeaderLayer.getColumnPropertyAccessor();
		this.tableType = tableType;
		this.formViewer = formViewer;
		setBlockOnOpen(true);
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		int columnIdx = natTable.getColumnIndexByPosition(colPos);
		Column column = columnPropertyAccessor.getColumnObject(columnIdx);

		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout gl_container = new GridLayout(2, false);
		gl_container.marginRight = 5;
		gl_container.marginLeft = 10;
		container.setLayout(gl_container);

		Label lblName = new Label(container, SWT.NONE);
		lblName.setText("Name:");

		nameText = new Text(container, SWT.BORDER);
		nameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));
		nameText.setText(column.getName());

		Label lblAlias = new Label(container, SWT.NONE);
		GridData gd_lblNewLabel = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 1);
		gd_lblNewLabel.horizontalIndent = 1;
		lblAlias.setLayoutData(gd_lblNewLabel);
		lblAlias.setText("Alias:");

		aliasText = new Text(container, SWT.BORDER);
		aliasText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		if (column.getAlias() != null) {
			aliasText.setText(column.getAlias());
		}else {
			aliasText.setText("");
		}

		Label lblID = new Label(container, SWT.NONE);
		lblID.setText("ID:");
		IDText = new Text(container, SWT.BORDER);
		IDText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		IDText.setText(String.valueOf(colPos));
		IDText.setEnabled(false);

		// default text for fields

		Label defaulttext = new Label(container, SWT.NONE);
		defaulttext.setText("Default Expr:");

		if(column.getPropertyType() == PropertyType.PROPERTY_TYPE_BOOLEAN_VALUE){
			defaultTextBoolCombo = new Combo(container, SWT.BORDER);
			defaultTextBoolCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
					1, 1));
			String[] items = {"true", "false"}; 
			defaultTextBoolCombo.setItems(items);

		}else{

			// domain model associated column

			Map<String, DomainEntry> domainEntries = DTDomainUtil.getDomainEntries(formViewer.getTable().getOwnerProjectName(), column);
			if (domainEntries != null && domainEntries.size() > 1 ){

				ArrayList<String> domainValues = DTDomainUtil.getDomainEntriesList(DTDomainUtil.getDomains(column.getPropertyPath(), formViewer.getTable().getOwnerProjectName()),formViewer.getTable().getOwnerProjectName()); 	

				if(column.getPropertyType() == PROPERTY_TYPES.STRING_VALUE){
					for(String domainValue : domainValues ){
						if(!(domainValue.equalsIgnoreCase("*") || domainEntries.get(domainValue) instanceof RangeImpl) || column.getColumnType().isConditon())
							domainValueArray.add("\""+domainValue+"\"");
					}

					multiselectCombo = new FieldSettingMultiSelectComboBox(container, SWT.NONE,
							new ArrayList<Object>(domainValueArray),column);

				}else{

					for(String domainValue : domainValues ){
						if(!(domainValue.equalsIgnoreCase("*") || domainEntries.get(domainValue) instanceof RangeImpl) || column.getColumnType().isConditon())
							domainValueArray.add(domainValue);   
					}
					multiselectCombo = new FieldSettingMultiSelectComboBox(container, SWT.NONE,
							new ArrayList<Object>(domainValueArray),column);

				}

			}else{

				defaultFieldText = new Text(container, SWT.BORDER);
				defaultFieldText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
						1, 1));

			}
		}

		Label applyTo = new Label(container, SWT.NONE);
		applyTo.setText("Include existing rules: ");

		applyToButton = new Button(container, SWT.CHECK);

		if(column.getDefaultCellText() == null){
			applyToButton.setEnabled(false);
		}else{
			applyToButton.setEnabled(true);
		}

		initializeValue(column); 
		addHandler();
		return container;
	}
	
	

	private void initializeValue(Column column) {
		if(defaultTextBoolCombo!=null){
			if(column.getDefaultCellText()!=null){
	    		defaultTextBoolCombo.setText(column.getDefaultCellText());
	    	}else{
	    		defaultTextBoolCombo.setText("false");
	    	}
		}
		if(defaultFieldText!=null){
			if (column.getDefaultCellText() != null) {
    			defaultFieldText.setText(column.getDefaultCellText());
    		}else {
    			defaultFieldText.setText("");
    		}
		}
		if(multiselectCombo!=null){
			multiselectCombo.setapplyToButton(applyToButton);
			if (column.getDefaultCellText() != null) {
				multiselectCombo.settextLableText(column.getDefaultCellText());
			}else{
				multiselectCombo.settextLableText("");
			}
		}
		
	}

	private void addHandler() {
		if(defaultFieldText!=null){
			defaultFieldText.addModifyListener(new ModifyListener() {

				@Override
				public void modifyText(ModifyEvent arg0) {
					if(defaultFieldText.getText().equalsIgnoreCase("") || defaultFieldText.getText().isEmpty()){
						applyToButton.setEnabled(false);
					}else{
						applyToButton.setEnabled(true);
					}

				}
			});
		}
		
		if(defaultTextBoolCombo!=null){
			defaultTextBoolCombo.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					if(defaultTextBoolCombo.getText().equalsIgnoreCase("false") || defaultTextBoolCombo.getText().isEmpty()){
						applyToButton.setEnabled(false);
					}else{
						applyToButton.setEnabled(true);
					}
					
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		}	
	}

	@Override
	  protected void createButtonsForButtonBar(Composite parent) {
	    createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
	        true);
	    createButton(parent, IDialogConstants.CANCEL_ID,
	        IDialogConstants.CANCEL_LABEL, false);
	  }
	
	 @Override
	  protected Point getInitialSize() {
		getShell().setText("Field Settings");
		return getShell().computeSize(425, 185, true);
	  }
	 
	 @Override
	  protected void okPressed() {
		int columnIdx = natTable.getColumnIndexByPosition(colPos);
		Column column = columnPropertyAccessor.getColumnObject(columnIdx);
//		column.setName(nameText.getText());
//		column.setAlias(aliasText.getText());
		boolean renamed = false;
		nameText.setText(nameText.getText().trim());
		if (!nameText.getText().isEmpty() && !nameText.getText().equals(column.getName())) {
			boolean isValid = validate(nameText.getText());
			if (!isValid) {
				return;
			}
			ICommandCreationListener<RenameCommand<ColumnNameStateMemento>, ColumnNameStateMemento> 
			commandCreationListener = new DecisionTableColumnRenameCommandListener(formViewer.getTable().getOwnerProjectName(), column, formViewer, nameText.getText(), tableType);
			CommandFacade.getInstance().
			executeColumnRename(formViewer.getTable().getOwnerProjectName(), 
					formViewer.getTable(),
					tableType,
					commandCreationListener);
			renamed = true;
		}
		
		if(defaultFieldText!=null){
			defaultFieldText.setText(defaultFieldText.getText().trim());

			if ((column.getDefaultCellText() != null || !defaultFieldText.getText().isEmpty())) {

				ICommandCreationListener<RenameCommand<ColumnDefaultCellTextStateMemento>, ColumnDefaultCellTextStateMemento> 
				commandCreationListener = new DecisionTableColumnDefaultCellTextCommandListener(formViewer.getTable().getOwnerProjectName(), column, formViewer, defaultFieldText.getText(), tableType);
				CommandFacade.getInstance().
				executeColumnDefaultCellTextChange(formViewer.getTable().getOwnerProjectName(), 
						formViewer.getTable(),
						tableType,
						commandCreationListener);
				//column.setDefaultCellText(defaultFieldText.getText());
				renamed = true;

				// put default expression to empty cell for column
				if(applyToButton.isEnabled() && applyToButton.getSelection()){

					EList<TableRule> ruleList = formViewer.getTable().getDecisionTable().getRule();
					for(TableRule trule : ruleList){
						if(column.getColumnType().equals(ColumnType.CONDITION) || column.getColumnType().equals(ColumnType.CUSTOM_CONDITION)){
							boolean present = false;
							for(TableRuleVariable truleVariable : trule.getCondition()){

								if(truleVariable.getColId().equalsIgnoreCase(column.getId())){
									if(truleVariable.getExpr().equalsIgnoreCase("")){
										truleVariable.setExpr(column.getDefaultCellText());
									}
									present = true;
								}
							}

							if(!present){

								TableRuleVariable ruleVariable = DtmodelFactory.eINSTANCE.createTableRuleVariable();
								ruleVariable.setColId(column.getId());
								ruleVariable.setId(trule.getId()+"_"+column.getId());
								ruleVariable.setExpr(column.getDefaultCellText());
								trule.getCondition().add(ruleVariable);
							}
						}else{

							// Action implementation

							boolean present1 = false;
							for(TableRuleVariable truleVariable : trule.getAction()){

								if(truleVariable.getColId().equalsIgnoreCase(column.getId())){
									if(truleVariable.getExpr().equalsIgnoreCase("")){
										truleVariable.setExpr(column.getDefaultCellText());
									}
									present1 = true;
								}
							}

							if(!present1){

								TableRuleVariable ruleVariable = DtmodelFactory.eINSTANCE.createTableRuleVariable();
								ruleVariable.setColId(column.getId());
								ruleVariable.setId(trule.getId()+"_"+column.getId());
								ruleVariable.setExpr(column.getDefaultCellText());
								trule.getAction().add(ruleVariable);
							}

						}
					}

				}



			}
			
		}else if(defaultTextBoolCombo!=null){
			if(column.getDefaultCellText() != "null" && !defaultTextBoolCombo.getText().equals(column.getDefaultCellText())){
				
				ICommandCreationListener<RenameCommand<ColumnDefaultCellTextStateMemento>, ColumnDefaultCellTextStateMemento> 
				commandCreationListener = new DecisionTableColumnDefaultCellTextCommandListener(formViewer.getTable().getOwnerProjectName(), column, formViewer, defaultTextBoolCombo.getText(), tableType);
				CommandFacade.getInstance().
				executeColumnDefaultCellTextChange(formViewer.getTable().getOwnerProjectName(), 
						formViewer.getTable(),
						tableType,
						commandCreationListener);
				//column.setDefaultCellText(defaultTextBoolCombo.getText());
			}
			
		}else{

			if ((column.getDefaultCellText() != null || !multiselectCombo.gettextLableText().isEmpty())) {

				ICommandCreationListener<RenameCommand<ColumnDefaultCellTextStateMemento>, ColumnDefaultCellTextStateMemento> 
				commandCreationListener = new DecisionTableColumnDefaultCellTextCommandListener(formViewer.getTable().getOwnerProjectName(), column, formViewer, multiselectCombo.gettextLableText(), tableType);
				CommandFacade.getInstance().
				executeColumnDefaultCellTextChange(formViewer.getTable().getOwnerProjectName(), 
						formViewer.getTable(),
						tableType,
						commandCreationListener);
				//column.setDefaultCellText(multiselectCombo.gettextLableText());
				renamed = true;

				// put default expression to empty cell for column
				if(applyToButton.isEnabled() && applyToButton.getSelection()){
					EList<TableRule> ruleList = formViewer.getTable().getDecisionTable().getRule();
					for(TableRule trule : ruleList){

						boolean present = false;
						for(TableRuleVariable truleVariable : trule.getCondition()){

							if(truleVariable.getColId().equalsIgnoreCase(column.getId())){
								if(truleVariable.getExpr().equalsIgnoreCase("")){
									truleVariable.setExpr(column.getDefaultCellText());
								}
								present = true;
							}
						}

						if(!present){

							TableRuleVariable ruleVariable = DtmodelFactory.eINSTANCE.createTableRuleVariable();
							ruleVariable.setColId(column.getId());
							ruleVariable.setId(trule.getId()+"_"+column.getId());
							ruleVariable.setExpr(column.getDefaultCellText());
							trule.getCondition().add(ruleVariable);
						}

						// Action implementation

						boolean present1 = false;
						for(TableRuleVariable truleVariable : trule.getAction()){

							if(truleVariable.getColId().equalsIgnoreCase(column.getId())){
								if(truleVariable.getExpr().equalsIgnoreCase("")){
									truleVariable.setExpr(column.getDefaultCellText());
								}
								present1 = true;
							}
						}

						if(!present1){

							TableRuleVariable ruleVariable = DtmodelFactory.eINSTANCE.createTableRuleVariable();
							ruleVariable.setColId(column.getId());
							ruleVariable.setId(trule.getId()+"_"+column.getId());
							ruleVariable.setExpr(column.getDefaultCellText());
							trule.getAction().add(ruleVariable);
						}


					}

				}

			}
		}
		
		aliasText.setText(aliasText.getText().trim());
		if ((column.getAlias() != null || !aliasText.getText().isEmpty())//allow column alias to be cleared if previously had value.
				&& !aliasText.getText().equals(column.getAlias())) {
			ICommandCreationListener<RenameCommand<ColumnAliasStateMemento>, ColumnAliasStateMemento> 
			commandCreationListener = new DecisionTableColumnAliasCommandListener(formViewer.getTable().getOwnerProjectName(), column, formViewer, aliasText.getText(), tableType);
			CommandFacade.getInstance().
			executeColumnAliasChange(formViewer.getTable().getOwnerProjectName(), 
					formViewer.getTable(),
					tableType,
					commandCreationListener);
			renamed = true;
		}
		
		if (renamed) {
			switch(tableType) {
			case DECISION_TABLE:
				formViewer.getDecisionTable().refresh();
				break;
			case EXCEPTION_TABLE:
				formViewer.getExceptionTable().refresh();
				break;
			}
		}
	    super.okPressed();
	  }
	 
	 private boolean validate(String changedFieldName) {
		 int columnIdx = natTable.getColumnIndexByPosition(colPos);
			Column column = columnPropertyAccessor.getColumnObject(columnIdx);
			FieldValidationContext<Column> fieldValidationContext = 
				new FieldValidationContext<Column>(column, formViewer, FieldValidationContextType.FIELD_NAME,tableType);
			fieldValidationContext.setChangedFieldName(changedFieldName);
			FieldValidationResult fieldValidationResult = initialFieldValidationListener.validate(fieldValidationContext);
			int resultCode = fieldValidationResult.getResultCode();
			boolean isValid = resultCode == FieldValidationResult.SUCCESS_CODE;
			if (!isValid) {
				displayError(fieldValidationResult);
			}
			return isValid;
		}
	 
	 private void displayError(final FieldValidationResult fieldValidationResult) {
			DecisionTableUtil.invokeOnDisplayThread(new Runnable() {
				public void run() {
					Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
					MessageDialog.openError(shell, "Field Rename Error", fieldValidationResult.getMessage());
				}
			}, false);
		}
}
