package com.tibco.cep.webstudio.client;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * A JSON overlay class. Represents an element of <code>browserCompatibility</code> JSON.
 * @author moshaikh
 *
 */
public class BrowserCompatibility extends JavaScriptObject {
	/**
	 * Overlay types should have a protected default constructor.
	 */
	protected BrowserCompatibility() { }
	
	public final native boolean isCompatibleVersion(float browserVersion) /*-{
		if ((this.minCompatibleVer == -1 || browserVersion >= this.minCompatibleVer)
				&& (this.maxCompatibleVer == -1 || browserVersion <= this.maxCompatibleVer)) {
			return true;
		}
		return false;
	}-*/;
	
	//For any change to browserCompatibility json also change the "Recommended Browser" message text in properties files and Webstudio.html
	public static native BrowserCompatibility getInstance(String browserName) /*-{
		return $wnd.browserCompatibilityMatrix[browserName];
	}-*/;
}