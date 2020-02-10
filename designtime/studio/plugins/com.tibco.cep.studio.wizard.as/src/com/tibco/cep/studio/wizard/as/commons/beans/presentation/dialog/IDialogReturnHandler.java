package com.tibco.cep.studio.wizard.as.commons.beans.presentation.dialog;

public interface IDialogReturnHandler
{

	void handleDialogReturn(int returnOption, Throwable error, Object ... args);

}
