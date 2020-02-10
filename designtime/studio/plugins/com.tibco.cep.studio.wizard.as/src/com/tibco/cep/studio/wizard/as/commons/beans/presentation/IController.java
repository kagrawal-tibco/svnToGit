package com.tibco.cep.studio.wizard.as.commons.beans.presentation;

public interface IController<M extends IModel>
{

	M getModel();

	void setModel(M model);

	IController<?> getParent();

	IController<?>[] getControllers();

	IController<?> add(IController<?> controller);

	void remove(IController<?> controller);

}
