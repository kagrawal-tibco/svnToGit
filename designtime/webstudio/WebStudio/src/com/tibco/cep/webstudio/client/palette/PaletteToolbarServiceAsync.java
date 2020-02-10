package com.tibco.cep.webstudio.client.palette;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * 
 * @author sasahoo
 *
 */
public interface PaletteToolbarServiceAsync
{
	/**
	 * 
	 * @param msg
	 * @param async
	 */
	void getMessage(String msg, AsyncCallback<String> async);

	/**
	 * 
	 * @param palette
	 * @param asyncCallback
	 */
	void getToolbar(String palette, AsyncCallback<Palette> asyncCallback);

	void getPalette(AsyncCallback<String> callback);

}
