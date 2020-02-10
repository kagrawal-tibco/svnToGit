/**
 * 
 */
package com.tibco.cep.webstudio.client.http;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.xml.client.Element;

/**
 * Event indicating server API's unsuccessful execution
 * 
 * @author Vikram Patil
 */
public class HttpFailureEvent extends GwtEvent<HttpFailureHandler> {
	private String url;
	private Element data;

	public HttpFailureEvent(String url, Element data) {
		super();
		this.url = url;
		this.data = data;
	}

	public static final Type<HttpFailureHandler> TYPE = new Type<HttpFailureHandler>();

	@Override
	protected void dispatch(HttpFailureHandler handler) {
		handler.onFailure(this);

	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<HttpFailureHandler> getAssociatedType() {
		return TYPE;
	}

	public String getUrl() {
		return this.url;
	}

	public Element getData() {
		return this.data;
	}
}
