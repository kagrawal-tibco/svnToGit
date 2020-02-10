/**
 * 
 */
package com.tibco.cep.webstudio.client.portal;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.groups.ContentModelManager;
import com.tibco.cep.webstudio.client.http.HttpFailureEvent;
import com.tibco.cep.webstudio.client.http.HttpFailureHandler;
import com.tibco.cep.webstudio.client.http.HttpRequest;
import com.tibco.cep.webstudio.client.http.HttpSuccessEvent;
import com.tibco.cep.webstudio.client.http.HttpSuccessHandler;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.model.DashboardArtifact;
import com.tibco.cep.webstudio.client.model.UserPreferences;
import com.tibco.cep.webstudio.client.util.ArtifactUtil;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil.ARTIFACT_TYPES;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;
import com.tibco.cep.webstudio.client.widgets.WebStudioNavigatorGrid;

/**
 * Portlet to display favorites and recently opened artifacts.
 * 
 * @author Vikram Patil
 */
public class DashboardArtifactsPortlet extends WebStudioPortlet implements HttpSuccessHandler, HttpFailureHandler {
	
	private static final GlobalMessages globalMsgBundle = (GlobalMessages) I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	private VLayout projectContainer;
	private String fqnSelectedArtifact;
	private String portletTitle;
	private ServerEndpoints serverUrl;
	private String currentColor;
	private List<DashboardArtifact> dashboardArtifactList;
	
	/**
	 * Base Constructor
	 * 
	 * @param portletTitle
	 * @param serverURL
	 */
	public DashboardArtifactsPortlet(String portletTitle, ServerEndpoints serverURL) {
		super();
		this.portletTitle = portletTitle;
		this.serverUrl = serverURL;
		this.dashboardArtifactList = new ArrayList<DashboardArtifact>();
		
		initialize();
	}
	
	@Override
	protected void initialize() {
		if (initialized) {
			return;
		}
		
		setTitle(portletTitle);
		
		projectContainer = new VLayout(0);
		projectContainer.setWidth100();
		projectContainer.setHeight100();
		projectContainer.setMargin(10);
        projectContainer.setOverflow(Overflow.AUTO);  

    	setModularCanvas(projectContainer);
    	
    	getDashboardArtifacts();
    	
    	initialized = true;
	}

	/**
	 * Fetch dashboard artifacts from the server
	 */
	private void getDashboardArtifacts() {
		HttpRequest request = new HttpRequest();

		ArtifactUtil.addHandlers(this);
		request.submit(serverUrl.getURL());
	}
	
	/**
	 * Create links for Dashboard artifacts
	 */
	private void createDashboardLinks() {
		projectContainer.removeMembers(projectContainer.getMembers());
		
		if (dashboardArtifactList.size() > 0) {
			for (DashboardArtifact artifact : dashboardArtifactList) {
				projectContainer.addMember(createDashboardLink(artifact.getProjectName(), artifact.getArtifactPath()));
			}
		} else {
			projectContainer.addMember(createEmptySection());
		}
	}

	/**
	 * Create links for Dashboard Artifacts
	 * 
	 * @param projectName
	 * @param artifactPath
	 * @return
	 */
	private HLayout createDashboardLink(final String projectName, final String artifactPath) {
		HLayout recentlyOpenedPanel = new HLayout(5);
		recentlyOpenedPanel.setLayoutLeftMargin(5);
		recentlyOpenedPanel.setLayoutRightMargin(5);
		recentlyOpenedPanel.setBackgroundColor(getToggleColor());
		recentlyOpenedPanel.setWidth100();
		recentlyOpenedPanel.setHeight(20);

		String projName = projectName.replaceAll("\\\\", "/");
		int idx = projName.lastIndexOf('/');
		if (idx != -1) {
			projName = projectName.substring(idx+1);
		}
		Label projectLabel = new Label("[" + projName + "]");
		projectLabel.setVAlign();
		projectLabel.setWidth("7%");
		projectLabel.setHeight(30);
		projectLabel.setStyleName("ws-filtertext-bold");
		recentlyOpenedPanel.addMember(projectLabel);

		String extn = artifactPath.substring(artifactPath.indexOf(".") + 1, artifactPath.length());
		VLayout imgHolder = new VLayout();
		imgHolder.setHeight100();
		imgHolder.setWidth("18");
		imgHolder.setLayoutTopMargin(3);
		Img projectIcon = new Img(ProjectExplorerUtil.getIcon(extn), 16, 16);
		projectIcon.setValign(VerticalAlignment.CENTER);
		if (LocaleInfo.getCurrentLocale().isRTL()) {
			projectIcon.setStyleName("ws-projecticon");
		}
		imgHolder.addMember(projectIcon);
		recentlyOpenedPanel.addMember(imgHolder);

		Label artifactLabel = new Label(artifactPath);
		artifactLabel.setVAlign();
		artifactLabel.setWidth("30%");
		artifactLabel.setStyleName("ws-filterlink");
		artifactLabel.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				fqnSelectedArtifact = projectName + artifactPath;
				
				if (DashboardArtifactsPortlet.this.getMaximized() != null && DashboardArtifactsPortlet.this.getMaximized()) {
					DashboardArtifactsPortlet.this.restore();
				}
				WebStudio.get().showWorkSpacePage();
				
				String groupId = ContentModelManager.PROJECTS_GROUP_ID;
				if (fqnSelectedArtifact.endsWith(ARTIFACT_TYPES.RULETEMPLATEINSTANCE.getValue().toLowerCase())) {
					groupId = ContentModelManager.BUSINESS_RULES_GROUP_ID;
				} else if (fqnSelectedArtifact.endsWith(ARTIFACT_TYPES.RULEFUNCTIONIMPL.getValue().toLowerCase())) {
					groupId = ContentModelManager.DECISION_TABLES_GROUP_ID;
				} else if (fqnSelectedArtifact.endsWith(ARTIFACT_TYPES.PROCESS.getValue().toLowerCase())) {
					groupId = ContentModelManager.PROCESSES_GROUP_ID;
				}				
				
				if (ContentModelManager.getInstance().getGroup(groupId).getLocalResources() == null) {
					ContentModelManager.getInstance().getGroup(groupId).setSelectedProject(projectName);
					ContentModelManager.getInstance().getGroup(groupId).setArtifactToOpen(fqnSelectedArtifact);
					// Deselect the group first so multiple projects can be included in the group content area via the subsequent selection call
					WebStudio.get().getWorkspacePage().selectDefaultGroups();
				} else {
					WebStudioNavigatorGrid navGrid = WebStudio.get().getWorkspacePage().getGroupContentsWindow()
							.getArtifactTreeGrid();
					String artifactId = fqnSelectedArtifact.replace("/", "$");
					
					if (navGrid.getResourceById(artifactId) == null) {
						WebStudio.get().getWorkspacePage().selectDefaultGroups();
					}
					
					WebStudio.get().getWorkspacePage().openResource(fqnSelectedArtifact);
				}
			}
		});
		recentlyOpenedPanel.addMember(artifactLabel);

		return recentlyOpenedPanel;
	}

	/**
	 * Change the color for alternating rows
	 * 
	 * @return
	 */
	private String getToggleColor() {
		if (this.currentColor == "#F7F7F7") {
			this.currentColor = "#D3E5FD";
		} else {
			this.currentColor = "#F7F7F7";
		}
		return this.currentColor;
	}

	/**
	 * Create empty section when there are no artifacts to display
	 * 
	 * @return
	 */
	private HLayout createEmptySection() {
		HLayout emptyPanel = new HLayout(5);

		emptyPanel.setLayoutLeftMargin(5);
		emptyPanel.setLayoutRightMargin(5);
		emptyPanel.setBackgroundColor("lightgray");
		emptyPanel.setWidth100();
		emptyPanel.setHeight(30);

		Label emptyLabel = new Label(globalMsgBundle.message_noData());
		emptyLabel.setVAlign();
		emptyLabel.setStyleName("ws-filtertext");
		emptyLabel.setWidth("20%");

		emptyPanel.addMember(emptyLabel);

		return emptyPanel;
	}

	@Override
	public void onSuccess(HttpSuccessEvent event) {
		boolean validRequest = false;
		if (event.getUrl().indexOf(serverUrl.getURL()) != -1) {
			validRequest = true;
			Element docElement = event.getData();

			NodeList dashboardArtifactElements = docElement.getElementsByTagName("record");
			if (dashboardArtifactElements.getLength() > 0) {
				for (int i = 0; i < dashboardArtifactElements.getLength(); i++) {
					NodeList dashboardArtifactDetails = dashboardArtifactElements.item(i).getChildNodes();

					DashboardArtifact artifact = null;
					for (int j = 0; j < dashboardArtifactDetails.getLength(); j++) {
						if (!dashboardArtifactDetails.item(j).toString().trim().isEmpty()) {
							if (artifact == null) artifact = new DashboardArtifact();
							
							String nodeName = dashboardArtifactDetails.item(j).getNodeName();
							String nodeValue = dashboardArtifactDetails.item(j).getFirstChild().getNodeValue();

							if (nodeName.equals("projectName"))
								artifact.setProjectName(nodeValue);
							if (nodeName.equals("artifactPath"))
								artifact.setArtifactPath(nodeValue);
						}
					}
					if (artifact != null) {
						dashboardArtifactList.add(artifact);
					}
				}
			}
			
			createDashboardLinks();
		} 

		if (validRequest) {
			ArtifactUtil.removeHandlers(this);
		}
	}
	
	/**
	 * Add newly selected Artifacts
	 * 
	 * @param projectName
	 * @param artifactPath
	 */
	public void addDashboardArtifact(String projectName, String artifactPath) {
		DashboardArtifact newArtifact = new DashboardArtifact(projectName, artifactPath);
		if (!dashboardArtifactList.contains(newArtifact)) {
			dashboardArtifactList.add(0, newArtifact);
			
			updateDashboardArtifacts();
		}
	}
	
	/**
	 * Remove the selected artifacts from dashboard portlet
	 * 
	 * @param projectName
	 */
	public void removeDashboardArtifact(String projectName, String artifactPath) {
		List<DashboardArtifact> artifactsToDelete = new ArrayList<DashboardArtifact>();
				
		for (DashboardArtifact artifact : dashboardArtifactList) {
			if (projectName.trim().equals(artifact.getProjectName())) {
				// Case where project itself is removed
				if (projectName.equals(artifactPath)) {
					artifactsToDelete.add(artifact);
				} else if (artifact.getArtifactPath().startsWith(artifactPath)) {
					artifactsToDelete.add(artifact);
				}
			}
		}

		for (DashboardArtifact artifact : artifactsToDelete) {
			if (dashboardArtifactList.contains(artifact)) {
				dashboardArtifactList.remove(artifact);
			}
		}
		
		if (artifactsToDelete.size() > 0) {
			createDashboardLinks();
		}
	}

	@Override
	public void onFailure(HttpFailureEvent event) {
		if (event.getUrl().indexOf(serverUrl.getURL()) != -1) {
			projectContainer.addMember(createEmptySection());

			ArtifactUtil.removeHandlers(this);
		}
	}
	
	/**
	 * Update Dashboard artifact links based on the user preference values
	 */
	public void updateDashboardArtifacts() {
		UserPreferences userPreferences = WebStudio.get().getUserPreference();
		int artifactCnt = (this.serverUrl.equals(ServerEndpoints.RMS_GET_FAVORITE_ARTIFACTS)) ? userPreferences.getFavoriteArtifactLimit() : userPreferences.getRecentlyOpenedArtifactLimit();
		
		while (dashboardArtifactList.size() > artifactCnt) {
			dashboardArtifactList.remove(dashboardArtifactList.size()-1);
		}
		
		createDashboardLinks();
	}
	
	/**
	 * Check's if the given Dashboard artifact exists
	 * 
	 * @param projectName
	 * @param artifactPath
	 * @return
	 */
	public boolean checkIfArtifactExists(String projectName, String artifactPath) {
		DashboardArtifact newArtifact = new DashboardArtifact(projectName,
				artifactPath);
		return dashboardArtifactList.contains(newArtifact);
	}
}
