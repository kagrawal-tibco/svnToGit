/**
 * 
 */
package com.tibco.cep.webstudio.client.portal;

import com.smartgwt.client.types.ContentsType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.HTMLFlow;

/**
 * 
 * @author Vikram Patil
 */
public class TibbrPortlet extends WebStudioPortlet {
	
	private HTMLFlow htmlPane;
	
	public TibbrPortlet() {
		super();
		initialize();
	}
	
	@Override
	protected void initialize() {
		if (initialized) {
			return;
		}
		setTitle(DASHBOARD_PORTLETS.TIBBR.getTitle());
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
		String myurl = "https://tibco.tibbr.com/tibbr/";

		htmlPane.setContentsURL(myurl);
	}

}
