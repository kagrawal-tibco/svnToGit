/**
 * 
 */
package com.tibco.cep.webstudio.client.portal;

import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.types.ContentsType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.HTMLFlow;
import com.tibco.cep.webstudio.client.WebStudio;

/**
 * 
 * @author Vikram Patil
 */
public class WebPagePortlet extends WebStudioPortlet {
	
	public static final String CUSTOM_PAGE_PREFIX = DASHBOARD_PORTLETS.CUSTOM_WEBPAGE.getTitle() + "_";
	static final String SAME_ORIGIN = "SAMEORIGIN";

	private HTMLFlow htmlPane;
	private String url;
	private String title;
	private boolean validateURL;
	
	public WebPagePortlet(String url, boolean validateURL) {
		this(url, url, validateURL);
	}
	
	public WebPagePortlet(String url, String title, boolean validateURL) {
		super();
		this.url = url;
		this.title = title;
		this.validateURL = validateURL;
		initialize();
	}
	
	@Override
	protected void initialize() {
		if (initialized) {
			return;
		}
		setTitle(title);
		htmlPane = new HTMLFlow();
		htmlPane.setShowCustomScrollbars(true);
		htmlPane.setScrollbarSize(1); // hack right now to "hide" the 2nd scrollbar
    	htmlPane.setMargin(0);    	
        htmlPane.setContentsType(ContentsType.PAGE);  
        htmlPane.setWidth100();
        htmlPane.setHeight100();
        htmlPane.setOverflow(Overflow.AUTO);
        setModularCanvas(htmlPane);
    	setHeight(400);
    	setDefaultHeight(400);
    	
    	setCustomURL();
    	
    	initialized = true;
	}
	
	private void setCustomURL(){
		if (url == null) {
			url = WebStudio.get().getUserPreference().getCustomURL();
		}

		if (validateURL) {
			validateURL(url, new URLValidationRPCCallback() {
				
				@Override
				protected void validateEvent(boolean isValid) {
					if (!isValid) {
						url = WebStudio.get().getUserPreference().getCustomURL();
					}
					htmlPane.setContentsURL(url);
				}
			});
		} else {
			htmlPane.setContentsURL(url);
		}
	}

	public static void validateURL(String url, URLValidationRPCCallback callback) {
//		try 
//		{  
//			URL url = new URL(myurl);
//			URLConnection conn = url.openConnection();
//
//			String value = conn.getHeaderField("X-Frame-Options");
//			if (value != null && SAME_ORIGIN.equalsIgnoreCase(value)){
//				return false;
//			}
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
		RPCRequest request = new RPCRequest();
		request.setUseSimpleHttp(true);
		request.setActionURL(url);
		request.setAttribute("useHttpProxy", "true");
		RPCManager.sendRequest(request, callback);
	}

}
