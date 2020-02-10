package com.tibco.cep.studio.wizard.as.commons.beans.presentation.dialog;

import com.tibco.cep.studio.wizard.as.commons.beans.presentation.IController;
import com.tibco.cep.studio.wizard.as.commons.beans.presentation.exception.DialogException;

public interface IDialogController<M extends IDialogModel> extends
		IController<M>
{

	void open(IDialogReturnHandler returnHandler, Object... args)
			throws DialogException;

	void setDialogOperationHandler(IDialogOperationHandler handler);

	IDialogOperationHandler getDialogOperationHandler();

}
