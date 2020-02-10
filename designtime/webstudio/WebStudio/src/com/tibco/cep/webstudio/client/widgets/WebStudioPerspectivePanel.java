package com.tibco.cep.webstudio.client.widgets;

import static com.tibco.cep.webstudio.client.IWebStudioWorkbenchConstants.BUSINESS_EVENTS_DECISION_MANAGER;
import static com.tibco.cep.webstudio.client.IWebStudioWorkbenchConstants.BUSINESS_EVENTS_DEVELOPMENT;
import static com.tibco.cep.webstudio.client.IWebStudioWorkbenchConstants.BUSINESS_EVENTS_DIAGRAM;

import java.util.LinkedHashMap;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.tibco.cep.webstudio.client.handler.PerspectiveChangeHandler;

public class WebStudioPerspectivePanel extends DynamicForm {
	
	private SelectItem selectItem;

	public WebStudioPerspectivePanel() {

		selectItem = new SelectItem();
		selectItem.setHeight(24);
//	    selectItem.setTitle("Select Perspective");
//	    selectItem.setHint("<nobr>NA</nobr>");
	    
	    LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
	    valueMap.put(BUSINESS_EVENTS_DEVELOPMENT, "<b>BusinessEvents Development</b>");
	    valueMap.put(BUSINESS_EVENTS_DIAGRAM, "<b>BusinessEvents Diagram</b>");
	    valueMap.put(BUSINESS_EVENTS_DECISION_MANAGER, "<b>BusinessEvents Decision Manager</b>");
	    
	    selectItem.setValueMap(valueMap);
	    
	    selectItem.setImageURLPrefix(Page.getAppImgDir() + "icons/16/");
	    selectItem.setIconHeight(16);
//	    selectItem.setImageURLSuffix(".png");

	    LinkedHashMap<String, String> valueIcons = new LinkedHashMap<String, String>();
	    valueIcons.put(BUSINESS_EVENTS_DEVELOPMENT, "tibco16-32.gif");
	    valueIcons.put(BUSINESS_EVENTS_DIAGRAM, "diagram.gif");
	    valueIcons.put(BUSINESS_EVENTS_DECISION_MANAGER, "deploy_decision_table_16x16.png");
	    
	    selectItem.setShowTitle(false);
	    selectItem.setShowHint(false);
	    selectItem.setValueIcons(valueIcons);
	    selectItem.setDefaultToFirstOption(true);
	    
	    selectItem.setWidth(215);
	    	    
	    this.setFields(selectItem);
	    this.setWrapItemTitles(true);
	    this.setAlign(Alignment.RIGHT);
	    this.selectRecord(0);
	    this.setShowResizeBar(false);
	    
	    PerspectiveChangeHandler handler = new PerspectiveChangeHandler();
		 
	    this.addItemChangeHandler(handler);
	    this.addItemChangedHandler(handler);
	}
	
	public SelectItem getSelectItem() {
		return selectItem;
	}

}