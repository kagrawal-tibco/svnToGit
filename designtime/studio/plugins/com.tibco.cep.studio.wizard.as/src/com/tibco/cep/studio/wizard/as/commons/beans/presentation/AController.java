package com.tibco.cep.studio.wizard.as.commons.beans.presentation;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.studio.wizard.as.commons.beans.AObservableWithServiceInjectionSupport;

public abstract class AController<M extends IModel/* , V extends IView */>
		extends AObservableWithServiceInjectionSupport implements IController<M>
{

	private M						model;

	private List<IController<?>>	controllers;

	private IController<?>			parent;

	protected AController(M model/* , V view */)
	{
		this(null, model);
	}

	protected AController(IController<?> parent, M model/* , V view */)
	{
		this.parent = parent;
		this.model = model;
		if (null != parent) {
			parent.add(this);
		}
	}

	@Override
	public M getModel()
	{
		return model;
	}

	@Override
	public void setModel(M model)
	{
		M oldValue = this.model;
		this.model = model;
		firePropertyChange("model", oldValue, model); //$NON-NLS-1$
	}

	@Override
	public IController<?>[] getControllers()
	{
		IController<?>[] children;
		if (null != controllers)
		{
			children = controllers.toArray(new IController<?>[controllers
					.size()]);
		}
		else
		{
			children = new IController<?>[0];
		}
		return children;
	}

	@Override
	public IController<?> getParent()
	{
		return parent;
	}

	@Override
	public IController<?> add(IController<?> controller)
	{
		if (null == controllers)
		{
			controllers = new ArrayList<IController<?>>();
		}
		controllers.add(controller);
		return controller;
	}

	@Override
	public void remove(IController<?> controller)
	{
		if (null == controllers)
		{
			return;
		}
		controllers.remove(controller);
	}

}
