package com.tibco.cep.webstudio.client.portal;

import com.smartgwt.client.widgets.events.VisibilityChangedEvent;
import com.smartgwt.client.widgets.events.VisibilityChangedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.groups.GroupContentsWindow;
import com.tibco.cep.webstudio.client.groups.MyGroups;

public class GroupsPortlet extends WebStudioPortlet {
	private GroupContentsWindow contentsWindow;
	private MyGroups myGroups;

	public GroupsPortlet() {
		super();
		initialize();
	}

	protected void initialize() {
		if (initialized) {
			return;
		}
		setTitle(DASHBOARD_PORTLETS.GROUPS.getTitle());
		
		HLayout parentLayout = new HLayout();
		parentLayout.setWidth100();
		parentLayout.setHeight100();
		
		contentsWindow = new GroupContentsWindow();
		contentsWindow.setShowAsTree(WebStudio.get().getUserPreference().getItemView().equals("Tree") ? true : false);
		contentsWindow.setWidth("50%");
		contentsWindow.setHeight100();
		
		myGroups = new MyGroups(contentsWindow);
		myGroups.setWidth("50%");
		myGroups.setHeight100();
		
		parentLayout.addMember(myGroups);
		parentLayout.addMember(contentsWindow);
		
    	setModularCanvas(parentLayout);
    	this.addVisibilityChangedHandler(new VisibilityChangedHandler() {
		
			@Override
			public void onVisibilityChanged(VisibilityChangedEvent event) {
				if (event.getIsVisible()) {
				}
			}
		});
    	initialized = true;
	}
	
	public void removeGroupArtifact(String artifactId) {
		contentsWindow.getArtifactTreeGrid().removeRecord(artifactId, true);
	}
	
	public GroupContentsWindow getGroupContentsWindow() {
		return contentsWindow;
	}
	
	public MyGroups getMyGroups() {
		return myGroups;
	}
	
}
