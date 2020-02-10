package com.tibco.cep.studio.wizard.as.commons.beans.presentation.dialog;

import com.tibco.cep.studio.wizard.as.commons.beans.presentation.AController;

public abstract class ADialogController<M extends IDialogModel> extends
		AController<M> implements IDialogController<M>
{

	private IDialogOperationHandler	handler;

	protected ADialogController(M model)
	{
		super(model);
	}

	@Override
	public final IDialogOperationHandler getDialogOperationHandler()
	{
		return handler;
	}

	@Override
	public final void setDialogOperationHandler(IDialogOperationHandler handler)
	{
		IDialogOperationHandler oldValue = this.handler;
		this.handler = handler;
		firePropertyChange("handler", oldValue, handler); //$NON-NLS-1$
	}

}
