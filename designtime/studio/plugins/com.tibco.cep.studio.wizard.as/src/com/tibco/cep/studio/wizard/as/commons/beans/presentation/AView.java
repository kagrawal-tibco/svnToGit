package com.tibco.cep.studio.wizard.as.commons.beans.presentation;

import com.tibco.cep.studio.wizard.as.commons.beans.factory.IBeanFactory;

abstract public class AView<C extends IController<M>, M extends IModel>
		implements IView<C, M>
{

	private C				controller;
	private IBeanFactory	componentFactory;

	protected AView(C controller, IBeanFactory compFactory)
	{
		this.controller = controller;
		this.componentFactory = compFactory;
	}

	@Override
	public Object createComponent(Object[] args)
	{
		return componentFactory.create(args/*
											 * [0], Parent Container: Container/Frame for AWT, Composite/Shell for SWT
											 * [1], Controller
											 */);
	}

	@Override
	public final C getController()
	{
		return controller;
	}

	protected final IBeanFactory getComponentFactory()
	{
		return componentFactory;
	}

}
