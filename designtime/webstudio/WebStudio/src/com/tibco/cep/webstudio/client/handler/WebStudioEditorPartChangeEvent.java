package com.tibco.cep.webstudio.client.handler;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Handler for Editor related changes.
 * 
 * @author Vikram Patil
 * @author Ryan
 */
public class WebStudioEditorPartChangeEvent extends GwtEvent<IWebStudioPartChangeHandler> {

	public static final Type<IWebStudioPartChangeHandler> TYPE = new Type<IWebStudioPartChangeHandler>();

	@Override
	protected void dispatch(IWebStudioPartChangeHandler handler) {
		handler.fireChange(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<IWebStudioPartChangeHandler> getAssociatedType() {
		return TYPE;
	}
}
