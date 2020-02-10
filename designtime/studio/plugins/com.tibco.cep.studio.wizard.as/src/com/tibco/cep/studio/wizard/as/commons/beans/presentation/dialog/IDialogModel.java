package com.tibco.cep.studio.wizard.as.commons.beans.presentation.dialog;

import com.tibco.cep.studio.wizard.as.commons.beans.presentation.IModel;

public interface IDialogModel extends IModel
{

	void setDialogParent(Object dialogParent);

	Object getDialogParent();

}
