package com.tibco.cep.webstudio.client.handler;

import static com.tibco.cep.webstudio.client.IWebStudioWorkbenchConstants.BUSINESS_EVENTS_DECISION_MANAGER;
import static com.tibco.cep.webstudio.client.IWebStudioWorkbenchConstants.BUSINESS_EVENTS_DEVELOPMENT;
import static com.tibco.cep.webstudio.client.IWebStudioWorkbenchConstants.BUSINESS_EVENTS_DIAGRAM;

import com.google.gwt.user.client.Window;
import com.smartgwt.client.widgets.form.events.ItemChangeEvent;
import com.smartgwt.client.widgets.form.events.ItemChangeHandler;
import com.smartgwt.client.widgets.form.events.ItemChangedEvent;
import com.smartgwt.client.widgets.form.events.ItemChangedHandler;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.tibco.cep.webstudio.client.widgets.WebStudioPerspectivePanel;

/**
 * Handler catering to changes in perspective
 * 
 * @author Ryan
 * @author Vikram Patil
 */
public class PerspectiveChangeHandler implements ItemChangeHandler, ItemChangedHandler {

	private boolean status = false;
	
	/* (non-Javadoc)
	 * @see com.smartgwt.client.widgets.form.events.ItemChangeHandler#onItemChange(com.smartgwt.client.widgets.form.events.ItemChangeEvent)
	 */
	@Override
	public void onItemChange(ItemChangeEvent event) {
		WebStudioPerspectivePanel panel = (WebStudioPerspectivePanel)event.getSource();
		String perspectiveName = panel.getSelectItem().getValueAsString();
		status = Window.confirm("This editor is associated with " + perspectiveName + " perspective. \nDo you want to open perspective?");
		if (!status) {
			event.cancel();
		}
	}

	/* (non-Javadoc)
	 * @see com.smartgwt.client.widgets.form.events.ItemChangedHandler#onItemChanged(com.smartgwt.client.widgets.form.events.ItemChangedEvent)
	 */
	@Override
	public void onItemChanged(ItemChangedEvent event) {
		WebStudioPerspectivePanel panel = (WebStudioPerspectivePanel)event.getSource();
		if (status) {
			String name = panel.getSelectItem().getValueField();
			SelectItem item = panel.getSelectItem();
			if (name.equals(BUSINESS_EVENTS_DEVELOPMENT)) {
				item.setWidth(215);
			} else if (name.equals(BUSINESS_EVENTS_DIAGRAM)) {
				item.setWidth(190);
			} else if (name.equals(BUSINESS_EVENTS_DECISION_MANAGER)) {
				item.setWidth(240);
			}
		}
	}
}