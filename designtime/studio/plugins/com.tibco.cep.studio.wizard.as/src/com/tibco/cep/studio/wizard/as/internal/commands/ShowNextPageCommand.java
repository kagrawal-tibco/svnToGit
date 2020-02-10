package com.tibco.cep.studio.wizard.as.internal.commands;

import com.tibco.cep.studio.wizard.as.commons.commands.ACommand;
import com.tibco.cep.studio.wizard.as.commons.commands.exception.CommandException;
import com.tibco.cep.studio.wizard.as.presentation.controllers.IASWizardPageController;
import com.tibco.cep.studio.wizard.as.wizard.IASWizardPage;

public class ShowNextPageCommand extends ACommand {


	public ShowNextPageCommand(String id) {
	    super(id);
    }

	@Override
	public void execute(Object value) throws CommandException {
		IASWizardPageController<?> controller = (IASWizardPageController<?>) value;
		IASWizardPage<?, ?> wizardPage = controller.getModel().getASWizardPage();
		if (wizardPage.canFlipToNextPage()) {
			wizardPage.getWizardContainer().showPage(wizardPage.getNextPage());
		}
	}

}
