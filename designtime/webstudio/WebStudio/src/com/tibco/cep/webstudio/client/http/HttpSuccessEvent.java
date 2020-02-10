/**
 * 
 */
package com.tibco.cep.webstudio.client.http;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.xml.client.Element;

/**
 * Event indicating server API's successful execution
 * 
 * @author Vikram Patil
 */
public class HttpSuccessEvent extends GwtEvent<HttpSuccessHandler> {
	private String url;
	private Element data;

	public HttpSuccessEvent(String url, Element data) {
		super();
		this.url = url;
		this.data = data;
	}

	public static final Type<HttpSuccessHandler> TYPE = new Type<HttpSuccessHandler>();

	@Override
	protected void dispatch(HttpSuccessHandler handler) {
		handler.onSuccess(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<HttpSuccessHandler> getAssociatedType() {
		return TYPE;
	}

	public String getUrl() {
		return this.url;
	}

	public Element getData() {
		return this.data;
	}
}
