package com.tibco.cep.studio.wizard.as.commons.beans.presentation.dialog;

import com.tibco.cep.studio.wizard.as.commons.beans.presentation.AModel;

public abstract class ADialogModel extends AModel implements IDialogModel
{

	private Object	dialogParent;

	@Override
	public Object getDialogParent()
	{
		return dialogParent;
	}

	@Override
	public void setDialogParent(Object dialogParent)
	{
		Object oldValue = this.dialogParent;
		this.dialogParent = dialogParent;
		firePropertyChange("dialogParent", oldValue, dialogParent); //$NON-NLS-1$
	}

}
