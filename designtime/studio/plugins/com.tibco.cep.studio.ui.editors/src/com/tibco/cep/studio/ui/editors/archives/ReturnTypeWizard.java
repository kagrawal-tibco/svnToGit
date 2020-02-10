package com.tibco.cep.studio.ui.editors.archives;

import org.eclipse.jface.wizard.Wizard;

import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.property.PropertyTypeCombo;

/**
 * 
 * @author sasahoo
 *
 */
public class ReturnTypeWizard extends Wizard {

	private ReturnTypeWizardPage returnTypePage;
	private String returnType;
	private PropertyTypeCombo returnTypeText;

	public ReturnTypeWizard(PropertyTypeCombo returnTypeText, String propertyReturnType) {
		setWindowTitle(Messages.getString("rulefunction.return.type.wizard.title"));
		this.returnTypeText =returnTypeText;
		this.returnType = propertyReturnType;
	}

	@Override
	public void addPages() {
		returnTypePage = new ReturnTypeWizardPage(this);
		addPage(returnTypePage);
	}

	@Override
	public boolean performCancel() {
		return true;
	}

	@Override
	public boolean performFinish() {
//       setPopertyField(returnTypeText, returnTypePage.getPropertyCombo().getText(), returnTypePage.getTypeText().getText());
	   return true;
	}
	
	public PropertyTypeCombo getReturnTypeText() {
		return returnTypeText;
	}

	public void setReturnTypeText(PropertyTypeCombo returnTypeText) {
		this.returnTypeText = returnTypeText;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
}
