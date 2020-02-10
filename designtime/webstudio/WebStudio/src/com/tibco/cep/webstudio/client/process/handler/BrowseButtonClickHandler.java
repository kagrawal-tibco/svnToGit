package com.tibco.cep.webstudio.client.process.handler;

import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.tibco.cep.webstudio.client.process.widgets.SelectResourceDialog;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;

/**
 * This class is used to handle the click event on Browse button in general
 * properties in different types of node..
 * 
 * @author dijadhav
 * 
 */
public class BrowseButtonClickHandler implements ClickHandler {
	private String resourceType;
	private String taskType;
	private TextItem resourceItem;
	private ListGrid rulesListGrid;
	private GlobalMessages globalMsgBundle = (GlobalMessages)I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);

	public BrowseButtonClickHandler(String resourceType, String taskType,
			TextItem resourceTextBox, ListGrid rulesListGrid) {
		super();
		this.resourceType = resourceType;
		this.taskType = taskType;
		this.resourceItem = resourceTextBox;
		this.rulesListGrid = rulesListGrid;
		if(rulesListGrid!=null){
			this.rulesListGrid.setEmptyMessage(globalMsgBundle.message_browseButton_emptyMessage());	
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt
	 * .event .dom.client.ClickEvent)
	 */
	@Override
	public void onClick(ClickEvent event) {
		new SelectResourceDialog(resourceType, taskType, resourceItem,
				rulesListGrid).draw();
	}
}