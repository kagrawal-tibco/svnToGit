package com.tibco.cep.webstudio.client.portal;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.core.Rectangle;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.AnimationCallback;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.events.KeyPressEvent;
import com.smartgwt.client.widgets.events.KeyPressHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.tibco.cep.webstudio.client.PortalColumn;
import com.tibco.cep.webstudio.client.PortalLayout;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.groups.GroupContentsWindow;
import com.tibco.cep.webstudio.client.groups.MyGroups;
import com.tibco.cep.webstudio.client.model.DashboardPortlet;
import com.tibco.cep.webstudio.client.model.User;
import com.tibco.cep.webstudio.client.model.UserPreferences;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;
import com.tibco.cep.webstudio.client.util.WebStudioShortcutUtil;
  
public class WebStudioPortalPage extends HLayout {  
    protected static final String ATTR_PORTLET_NAME = "portletName";
    private static final String DOCUMENTATION_URL = "https://docs.tibco.com/products/tibco-businessevents";
  
	private PortalLayout portalLayout;
	private Map<String, WebStudioPortlet> homePortlets = new HashMap<String, WebStudioPortlet>();

	private boolean numColumnChanged = false;
	
	public WebStudioPortalPage(User user) {
		super(2);
        setWidth100();
        setHeight100();
		setCanDragResize(false);
		setOverflow(Overflow.AUTO);
		this.addKeyPressHandler(new KeyPressHandler() {
				
				@Override
				public void onKeyPress(KeyPressEvent event) {
					WebStudioShortcutUtil.handleKeyPressEvent(event);
				}
			});
		this.addDrawHandler(new DrawHandler() {
			
			@Override
			public void onDraw(DrawEvent event) {
				WebStudioPortalPage.this.focus();
				WebStudioPortalPage.this.setCanFocus(true);				
			}
		});
		initializePortalPage(user);
		
	}

	private void initializePortalPage(User user) {
		HLayout layout = new HLayout();
		layout.setWidth100();
		layout.setHeight100();
        layout.setStyleName("ws-portal");

        createPortalLayout(layout, user);
        layout.addKeyPressHandler(new KeyPressHandler() {
			
			@Override
			public void onKeyPress(KeyPressEvent event) {
				WebStudioShortcutUtil.handleKeyPressEvent(event);
			}
		});
        addMember(layout);
	}

	public Map<String, WebStudioPortlet> getHomePortlets() {
		return homePortlets;
	}

    public void createPortalLayout(HLayout conentsCanvas, final User user) {  
//    	setTitle(user.getUserName()+"'s home page");

    	UserPreferences userPreference = WebStudio.get().getUserPreference();
    	portalLayout = new PortalLayout(userPreference.getPortalColumns());  
    	// create portlets...
        createPortlets(portalLayout);  
  
        conentsCanvas.addMember(portalLayout);  
    }

    public static int getNumColumns() {
    	return WebStudio.get().getUserPreference().getPortalColumns();
    }

	private void createPortlets(final PortalLayout portalLayout) {
		List<DashboardPortlet> dashboardPortletList = WebStudio.get().getUserPreference().getDashboardPortlets();

		createDocumentationPortlet(portalLayout);
		DashboardPortlet portlet = new DashboardPortlet(DASHBOARD_PORTLETS.DOCUMENTATION.getTitle());
		if (!dashboardPortletList.contains(portlet)) {
			homePortlets.get(DASHBOARD_PORTLETS.DOCUMENTATION.getTitle()).hideInPortal();
		}
		
		createTibbrPortlet(portalLayout);
		portlet = new DashboardPortlet(DASHBOARD_PORTLETS.TIBBR.getTitle());
		if (!dashboardPortletList.contains(portlet)) {
			homePortlets.get(DASHBOARD_PORTLETS.TIBBR.getTitle()).hideInPortal();
		}

		createProjectsPortlet(portalLayout);
		portlet.setPortletId(DASHBOARD_PORTLETS.AVALIABLE_PROJECTS.getTitle());
    	if (!dashboardPortletList.contains(portlet)) {
    		homePortlets.get(DASHBOARD_PORTLETS.AVALIABLE_PROJECTS.getTitle()).hideInPortal();
    	}

    	createRecentlyOpenedPortlet(portalLayout);
    	portlet.setPortletId(DASHBOARD_PORTLETS.RECENTLY_OPENED.getTitle());
    	if (!dashboardPortletList.contains(portlet)) {
    		homePortlets.get(DASHBOARD_PORTLETS.RECENTLY_OPENED.getTitle()).hideInPortal();
    	}

    	createWorklistPortlet(portalLayout);
    	portlet.setPortletId(DASHBOARD_PORTLETS.WORKLIST.getTitle());
    	if (!dashboardPortletList.contains(portlet)) {
    		homePortlets.get(DASHBOARD_PORTLETS.WORKLIST.getTitle()).hideInPortal();
    	}

    	// The notifications portlet is just demo-ware.  Remove it
//    	createNotificationsPortlet(portalLayout);
//    	portlet.setPortletId(DASHBOARD_PORTLETS.NOTIFICATIONS.getTitle());
//    	if (!dashboardPortletList.contains(portlet)) {
//    		homePortlets.get(DASHBOARD_PORTLETS.NOTIFICATIONS.getTitle()).hideInPortal();
//    	}

    	createGroupsPortlet(portalLayout);
    	portlet.setPortletId(DASHBOARD_PORTLETS.GROUPS.getTitle());
    	if (!dashboardPortletList.contains(portlet)) {   		
    		homePortlets.get(DASHBOARD_PORTLETS.GROUPS.getTitle()).hideInPortal();
    	}
		
		for (DashboardPortlet dashboardPortlet : dashboardPortletList) {
			String portletId = dashboardPortlet.getPortletId();
			if (portletId.startsWith(WebPagePortlet.CUSTOM_PAGE_PREFIX)) {
				String url = portletId.substring(WebPagePortlet.CUSTOM_PAGE_PREFIX.length());
				createCustomWebpagePortlet(portalLayout, url, portletId);
			}
		}

//		createWebPagePortlet(portalLayout);
//		createFavoritesPortlet(portalLayout);
//		createConfigurationPortlet(portalLayout);
//		createPreferencesPortlet(portalLayout);
    }

	private void createDocumentationPortlet(PortalLayout portalLayout) {
		WebStudioPortlet portlet = new WebPagePortlet(DOCUMENTATION_URL, DASHBOARD_PORTLETS.DOCUMENTATION.getTitle(), false);
    	portalLayout.addPortlet(portlet);
    	
    	homePortlets.put(DASHBOARD_PORTLETS.DOCUMENTATION.getTitle(), (WebStudioPortlet) portlet);
	}

	public void addCustomHomePortlet(WebStudioPortlet portlet, String id) {
		portalLayout.addPortlet(portlet);
		
		homePortlets.put(id, portlet);
	}
	
    private void createFavoritesPortlet(PortalLayout portalLayout) {
    	DashboardArtifactsPortlet portlet = new DashboardArtifactsPortlet(DASHBOARD_PORTLETS.MY_FAVORITES.getTitle(), ServerEndpoints.RMS_GET_FAVORITE_ARTIFACTS);
    	portalLayout.addPortlet(portlet);
    	
    	homePortlets.put(DASHBOARD_PORTLETS.MY_FAVORITES.getTitle(), (WebStudioPortlet) portlet);
	}
    
    private void createGroupsPortlet(PortalLayout portalLayout) {
    	GroupsPortlet portlet = new GroupsPortlet();
    	portalLayout.addPortlet(portlet);
    	
    	homePortlets.put(DASHBOARD_PORTLETS.GROUPS.getTitle(), (WebStudioPortlet) portlet);
	}

    private void createWorklistPortlet(PortalLayout portalLayout) {
    	WorklistPortlet portlet = new WorklistPortlet(DASHBOARD_PORTLETS.WORKLIST.getTitle(), ServerEndpoints.RMS_GET_WORKLIST);
    	portalLayout.addPortlet(portlet);
    	
    	homePortlets.put(DASHBOARD_PORTLETS.WORKLIST.getTitle(), (WebStudioPortlet) portlet);
    }
    
	private void createNotificationsPortlet(final PortalLayout portalLayout) {
    	NotificationsPortlet portlet = new NotificationsPortlet();  
    	portalLayout.addPortlet(portlet);
    	
    	homePortlets.put(DASHBOARD_PORTLETS.NOTIFICATIONS.getTitle(), (WebStudioPortlet) portlet);
    }
    
	private void createProjectsPortlet(final PortalLayout portalLayout) {
		WebStudioPortlet portlet = new ProjectsPortlet();  
    	portalLayout.addPortlet(portlet);

    	homePortlets.put(DASHBOARD_PORTLETS.AVALIABLE_PROJECTS.getTitle(), (WebStudioPortlet) portlet);
	}

	private void createRecentlyOpenedPortlet(final PortalLayout portalLayout) {
		DashboardArtifactsPortlet portlet = new DashboardArtifactsPortlet(DASHBOARD_PORTLETS.RECENTLY_OPENED.getTitle(), ServerEndpoints.RMS_GET_RECENTLYOPENED_ARTIFACTS);
		portalLayout.addPortlet(portlet);
    	
    	homePortlets.put(DASHBOARD_PORTLETS.RECENTLY_OPENED.getTitle(), (WebStudioPortlet) portlet);
	}

//	private void createWebPagePortlet(final PortalLayout portalLayout) {
//		WebStudioPortlet portlet = new WebPagePortlet("https://tucon.tibco.com");
//		portalLayout.addPortlet(portlet);
//    	
//     	homePortlets.put(DASHBOARD_PORTLETS.CUSTOM_WEBPAGE.getTitle(), (WebStudioPortlet) portlet);
//	}
  
	private void createCustomWebpagePortlet(final PortalLayout portalLayout, final String url, final String id) {
		WebStudioPortlet portlet = new WebPagePortlet(url, true/* validate URL just in case embedding settings have changed */);
		portalLayout.addPortlet(portlet);
		
		homePortlets.put(id, (WebStudioPortlet) portlet);
	}
	
	private void createTibbrPortlet(final PortalLayout portalLayout) {
		WebStudioPortlet portlet = new TibbrPortlet();
		portalLayout.addPortlet(portlet);
		
		homePortlets.put(DASHBOARD_PORTLETS.TIBBR.getTitle(), (WebStudioPortlet) portlet);
	}
	
	@Override
	public void hide() {
		super.hide();
	}

	@Override
	public void show() {
		reflowPortlets();
		super.show();
	}

	public void hidePortalPage(Rectangle rectangle) {
		final Rectangle rect = WebStudio.get().getMainLayout().getRect();

        // create an outline around the clicked button  
        final Canvas outline = new Canvas();  
        outline.setLeft(rect.getLeft());  
        outline.setTop(rect.getTop());  
        outline.setWidth(rect.getWidth());  
        outline.setHeight(rect.getHeight());  
        outline.setBorder("2px solid #8289A6");  
        outline.draw();  
        outline.bringToFront();  

		outline.animateRect(rectangle.getLeft(), rectangle.getTop(), rectangle.getWidth(), rectangle.getHeight(), new AnimationCallback() {
			
			@Override
			public void execute(boolean earlyFinish) {
				
				hide();
				outline.destroy();
			}
		});
	}

	public void showPortalPage(Rectangle rectangle) {
		final Rectangle rect = WebStudio.get().getMainLayout().getRect();
		
        // create an outline around the clicked button  
        final Canvas outline = new Canvas();  
        outline.setLeft(rectangle.getLeft());  
        outline.setTop(rectangle.getTop());  
        outline.setWidth(rectangle.getWidth());  
        outline.setHeight(rectangle.getHeight());  
        outline.setBorder("2px solid #8289A6");  
        outline.draw();  
        outline.bringToFront();  

        outline.animateRect(rect.getLeft(), rect.getTop(), rect.getWidth(), rect.getHeight(),  
                new AnimationCallback() {  
                    public void execute(boolean earlyFinish) {  
                        // callback at end of animation - destroy outline; show the portlet  
                        outline.destroy();
                        show();
                    }  
                }, 750);  
        
	}
	
	/**
	 * Add's artifacts to Dashboard Artifact portlets i.e. (Favorites/Recently
	 * Opened)
	 * 
	 * @param projectName
	 * @param artifactPath
	 * @param portlet
	 */
	public void addToDashboardArtifactPortlet(String projectName, String artifactPath, DASHBOARD_PORTLETS portlet) {
		DashboardArtifactsPortlet dashboardPortlet = (DashboardArtifactsPortlet) homePortlets.get(portlet.getTitle());
		if (dashboardPortlet != null) {
			dashboardPortlet.addDashboardArtifact(projectName, artifactPath);
		}
	}
	
	/**
	 * Removes artifacts to Dashboard Artifact portlets i.e. (Favorites/Recently
	 * Opened)
	 * 
	 * @param projectName
	 * @param artifactPath
	 * @param portlet
	 */
	public void removeFromDashboardArtifactPortlet(String projectName, String artifactPath, DASHBOARD_PORTLETS portlet) {
		DashboardArtifactsPortlet dashboardPortlet = (DashboardArtifactsPortlet) homePortlets.get(portlet.getTitle());
		if (dashboardPortlet != null) {
			dashboardPortlet.removeDashboardArtifact(projectName, artifactPath);
		}
	}
	
	/**
	 * Update Dashboard artifact portlet to display artifacts based on perference values
	 * @param portlet
	 */
	public void updateDashboardArtifactPortlet(DASHBOARD_PORTLETS portlet) {
		DashboardArtifactsPortlet dashboardPortlet = (DashboardArtifactsPortlet) homePortlets.get(portlet.getTitle());
		if (dashboardPortlet != null) {
			dashboardPortlet.updateDashboardArtifacts();
		}
	}
	
	/**
	 * Check if the specified Dashboard artifact exists
	 * 
	 * @param projectName
	 * @param artifactPath
	 * @param portlet
	 * 
	 * @return
	 */
	public boolean checkIfDashboardArtifactExists(String projectName, String artifactPath, DASHBOARD_PORTLETS portlet) {
		DashboardArtifactsPortlet dashboardPortlet = (DashboardArtifactsPortlet) homePortlets.get(portlet.getTitle());
		if (dashboardPortlet != null) {
			return dashboardPortlet.checkIfArtifactExists(projectName, artifactPath);
		}
		
		return false;
	}
	
	/**
	 * Add projects to avaliable projects portlet
	 * 
	 * @param projectTitle
	 */
	public void addToAvailableProjectsPortlet(String projectTitle) {
		ProjectsPortlet availableProjectsPortlet = (ProjectsPortlet) homePortlets
				.get(DASHBOARD_PORTLETS.AVALIABLE_PROJECTS.getTitle());
		
		if (availableProjectsPortlet != null) {
			availableProjectsPortlet.addProject(projectTitle);
		}
	}
	
	/**
	 * Removes the project from the available projects portlet
	 * 
	 * @param projectTitle
	 */
	public void removeFromAvailableProjectsPortlet(String projectTitle) {
		ProjectsPortlet availableProjectsPortlet = (ProjectsPortlet) homePortlets
				.get(DASHBOARD_PORTLETS.AVALIABLE_PROJECTS.getTitle());

		if (availableProjectsPortlet != null) {
			availableProjectsPortlet.removeProject(projectTitle);
		}
	}
	
	/**
	 * Check if the give project exists in the available project list
	 * 
	 * @param projectName
	 * @return
	 */
	public boolean projectExistsInAvailableProjectsPortlet(String projectName) {
		ProjectsPortlet availableProjectsPortlet = (ProjectsPortlet) homePortlets
				.get(DASHBOARD_PORTLETS.AVALIABLE_PROJECTS.getTitle());

		if (availableProjectsPortlet == null) {
			return false;
		}
		return availableProjectsPortlet.projectExists(projectName);
	}
	
	/**
	 * Fetch the list of available projects
	 * 
	 * @return
	 */
	public int getTotalAvailableProjectsPortlet() {
		ProjectsPortlet availableProjectsPortlet = (ProjectsPortlet) homePortlets
				.get(DASHBOARD_PORTLETS.AVALIABLE_PROJECTS.getTitle());

		if (availableProjectsPortlet == null) {
			return -1;
		}
		return availableProjectsPortlet.getTotalAvailableProjects();
	}

	/**
	 * Remove artifact from group portlet
	 * 
	 * @param artifactId
	 */
	public void removeArtifactFromGroupPortlet(String artifactId) {
		GroupsPortlet groupsPortlet = (GroupsPortlet) homePortlets
				.get(DASHBOARD_PORTLETS.GROUPS.getTitle());
		groupsPortlet.removeGroupArtifact(artifactId);
	}
	
	/**
	 * Restore the specified portlet back to its original state
	 * 
	 * @param portletName
	 */
	public void restorePortlet(String portletName) {
		WebStudioPortlet webstudioPortlet = (WebStudioPortlet) homePortlets
				.get(portletName);
		if (webstudioPortlet.getMaximized() != null && webstudioPortlet.getMaximized()) {
			webstudioPortlet.restore();
		}
	}
	
	/**
	 * Get Group content Window from group portlet
	 */
	public GroupContentsWindow getGroupContentWindowtFromGroupPortlet() {
		GroupsPortlet groupsPortlet = (GroupsPortlet) homePortlets
				.get(DASHBOARD_PORTLETS.GROUPS.getTitle());
		return groupsPortlet.getGroupContentsWindow();
	}
	
	/**
	 * Get Groups from group portlet
	 */
	public MyGroups getMyGroupsFromGroupPortlet() {
		GroupsPortlet groupsPortlet = (GroupsPortlet) homePortlets
				.get(DASHBOARD_PORTLETS.GROUPS.getTitle());
		return groupsPortlet.getMyGroups();
	}
	
	/**
	 * Rmove portlets from the dashboard
	 * 
	 * @param portletName
	 */
	public void removeDashboardPortal(String portletName) {
		WebStudioPortlet portlet = getHomePortlets().get(portletName);
		if (portlet != null) {
			portlet.hideInPortal();
		}
	}

	/**
	 * Displays Dashboard portal page
	 * 
	 * @param portletName
	 */
	public void showDashboardPortal(String portletName) {
		WebStudioPortlet portlet = getHomePortlets().get(portletName);
		if (portlet != null && portlet.isHiddenInPortal()) {
			portlet.showInPortal();
		}
	}

	/**
	 * Set the total number of portlet columns
	 * 
	 * @param numColumns
	 */
	public void setNumColumns(int numColumns) {
		if (numColumns != getNumColumns()) {
			numColumnChanged = true;
		}
	}

	/**
	 * Rearrange the available and visible portlets
	 */
	private void reflowPortlets() {
		if (!numColumnChanged) {
			return; // nothing needs to be done
		}
		int numColumns = getNumColumns();
		Canvas[] members = portalLayout.getMembers();
		portalLayout.removeMembers(members);
		for (int i = 0; i < numColumns; i++) {
			PortalColumn col = new PortalColumn();
			col.setWidth(PortalLayout.getColWidth(numColumns, i == numColumns-1));
            portalLayout.addMember(col);  
		}
		Collection<WebStudioPortlet> values = getHomePortlets().values();
		Iterator<WebStudioPortlet> iterator = values.iterator();
		while (iterator.hasNext()) {
			WebStudioPortlet next = iterator.next();
			next.restore();
			if (!portalLayout.hasPortlet(next)) {
				portalLayout.addPortlet(next);
			}
		}
		portalLayout.reflow();
		numColumnChanged = false;
	}
}  