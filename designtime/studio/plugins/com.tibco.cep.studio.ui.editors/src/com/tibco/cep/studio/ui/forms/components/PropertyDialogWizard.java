package com.tibco.cep.studio.ui.forms.components;

import org.eclipse.jface.wizard.Wizard;

/**
 * @author sasahoo
 * 
 */

public class PropertyDialogWizard extends Wizard {

	private PropertyDialogWizardPage propertyDialogPage;
	private String value;
	private String propertyType;
//	private PropertyDialogTableCellEditor pdTableCellEditor;
	
	public PropertyDialogWizard(/*PropertyDialogTableCellEditor pdTableCellEditor,*/String value,String propertyType) {
//		setWindowTitle(Messages.getString("CONCEPT_PROPERTY_WIZARD_TITLE"));
//		this.value = value;
//		this.propertyType = propertyType;
//		this.pdTableCellEditor = pdTableCellEditor;
	}

	@Override
	public void addPages() {
		propertyDialogPage = new PropertyDialogWizardPage(this);
		addPage(propertyDialogPage);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	@Override
	public boolean performCancel() {
		return true;
	}

	@Override
	public boolean performFinish() {
//		pdTableCellEditor.setOkPressed(true);
//		pdTableCellEditor.setPropertyType(propertyDialogPage.getPropertyCombo().getText());
//		if(propertyDialogPage.getPropertyCombo().getText().equalsIgnoreCase(PROPERTY_TYPES.CONCEPT.getName())||
//				propertyDialogPage.getPropertyCombo().getText().equalsIgnoreCase((PROPERTY_TYPES.CONCEPT_REFERENCE.getName()))){
//			pdTableCellEditor.setValue(propertyDialogPage.getConceptText().getText());
//		}else{
//			pdTableCellEditor.setValue(propertyDialogPage.getPropertyCombo().getText());
//		}
		
		return true;
	}

	
}
