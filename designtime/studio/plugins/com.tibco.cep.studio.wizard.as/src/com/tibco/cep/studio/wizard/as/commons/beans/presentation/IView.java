package com.tibco.cep.studio.wizard.as.commons.beans.presentation;

public interface IView<C extends IController<M>, M extends IModel>
{

	Object createComponent(Object[] args);

	C getController();

}
