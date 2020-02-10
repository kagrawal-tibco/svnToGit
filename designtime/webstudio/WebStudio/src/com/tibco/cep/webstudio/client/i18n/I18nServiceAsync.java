/**
 * 
 */
package com.tibco.cep.webstudio.client.i18n;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author vpatil
 *
 */
public interface I18nServiceAsync {

	/**
	 * 
	 * @see com.tibco.cep.webstudio.client.i18n.I18nService#getMessages(java.lang.String)
	 */
	void getMessages(String locale, AsyncCallback<I18nRegistry> callback);

}
