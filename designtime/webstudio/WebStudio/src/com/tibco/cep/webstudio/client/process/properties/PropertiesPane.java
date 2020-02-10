package com.tibco.cep.webstudio.client.process.properties;

import com.smartgwt.client.util.Page;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.tab.Tab;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.logging.WebStudioClientLogger;

/**
 * Widget to represent properties panel
 * @author sasahoo
 */
public class PropertiesPane extends Tab {
	
	protected Canvas innerCanvas;
	private GlobalMessages globalMsgBundle = (GlobalMessages) I18nRegistry
			.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	private WebStudioClientLogger logger = WebStudioClientLogger.getLogger(PropertiesPane.class.getName());
			
	public PropertiesPane() {
		super();
		logger.info("Init Web Studio Properties Pane()");
		setTitle(Canvas.imgHTML(Page.getAppImgDir() + "icons/16/property_pane.gif") + globalMsgBundle.text_properties());
	}
	
	public void fillProperties(Canvas canvas) {
		this.innerCanvas = canvas;
		setPane(canvas);
	}
	
}