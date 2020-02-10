package com.tibco.cep.studio.dashboard.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ContentViewer;

import com.tibco.cep.studio.dashboard.ui.forms.ExceptionHandler;

public abstract class UpdateableAction extends Action {

	protected ExceptionHandler exHandler;

	protected ContentViewer viewer;

	public UpdateableAction(ContentViewer viewer, String id, String text, ExceptionHandler exHandler) {
		super(text);
		setId(id);
		this.exHandler = exHandler;
		this.viewer = viewer;
	}


	public UpdateableAction(ContentViewer viewer, String id, String text, ExceptionHandler exHandler, int style) {
		super(text, style);
		setId(id);
		this.exHandler = exHandler;
		this.viewer = viewer;
	}

	public abstract void update();

}