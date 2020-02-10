package com.tibco.cep.webstudio.client.portal;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.Window;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.http.HttpFailureEvent;
import com.tibco.cep.webstudio.client.http.HttpFailureHandler;
import com.tibco.cep.webstudio.client.http.HttpRequest;
import com.tibco.cep.webstudio.client.http.HttpSuccessEvent;
import com.tibco.cep.webstudio.client.http.HttpSuccessHandler;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.model.ManagedProject;
import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.util.ArtifactDeletionHelper;
import com.tibco.cep.webstudio.client.util.ArtifactUtil;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil.ARTIFACT_TYPES;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;
import com.tibco.cep.webstudio.client.widgets.RMSCheckoutDialog;

/**
 * Portlet for listing out all the projects available for the end user.
 * 
 * @author Vikram Patil
 */
public class ProjectsPortlet extends WebStudioPortlet implements HttpSuccessHandler, HttpFailureHandler {

	private static GlobalMessages globalMsgBundle = (GlobalMessages)I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);

	private DynamicForm form;
	private HttpRequest request;
	private VLayout projectContainer;
	private List<ManagedProject> managedProjectList;

	/**
	 * Base Constructor
	 */
	public ProjectsPortlet() {
		super();
		request = new HttpRequest();
		managedProjectList = new ArrayList<ManagedProject>();

		initialize();
	}

	/**
	 * Initialize the widget
	 */
	protected void initialize() {
		if (initialized) {
			return;
		}
		this.setTitle(DASHBOARD_PORTLETS.AVALIABLE_PROJECTS.getTitle());

		form = new DynamicForm();
		form.setWidth100();
		form.setHeight100();

		projectContainer = new VLayout(10);
		projectContainer.setWidth100();
		projectContainer.setHeight100();
		projectContainer.setMargin(10);

		this.setModularCanvas(projectContainer);

		showProjectListing();

		initialized = true;
	}

	/**
	 * Show all the projects maintained on the server
	 */
	private void showProjectListing() {
		ArtifactUtil.addHandlers(this);
		request.submit(ServerEndpoints.RMS_GET_WORKSPACE_CONTENTS.getURL());
	}

	@Override
	public void onSuccess(HttpSuccessEvent event) {
		if (event.getUrl().indexOf(ServerEndpoints.RMS_GET_WORKSPACE_CONTENTS.getURL()) != -1) {
			getProjects(event.getData());
			createProjectLinks();
			ArtifactUtil.removeHandlers(this);
		}
	}

	/**
	 * Get a list of projects
	 * 
	 * @param docElement
	 */
	private void getProjects(Element docElement) {
		NodeList projectNames = docElement.getElementsByTagName("projectName");
		for (int i = 0; i < projectNames.getLength(); i++) {
			ManagedProject managedProject = new ManagedProject(projectNames.item(i).getFirstChild().getNodeValue());
			managedProjectList.add(managedProject);
		}
	}

	/**
	 * Create avaliable project links
	 * 
	 * @param projects
	 */
	private void createProjectLinks() {
		projectContainer.removeMembers(projectContainer.getMembers());
		
		if (managedProjectList.size() > 0) {
			for (ManagedProject project : managedProjectList) {
				projectContainer.addMember(createProjectLink(project.getProjectName()));
			}
		} else {
			projectContainer.addMember(createProjectLink(""));
		}
	}

	@Override
	public void onFailure(HttpFailureEvent event) {
		boolean validRequest = false;
		if (event.getUrl().indexOf(ServerEndpoints.RMS_GET_WORKSPACE_CONTENTS.getURL()) != -1) {
			validRequest = true;
			createProjectLinks();
		}

		if (validRequest) {
			ArtifactUtil.removeHandlers(this);
		}
	}

	/**
	 * Create the link widget for Avaliable projects
	 * 
	 * @param projectTitle
	 * @return
	 */
	private HLayout createProjectLink(final String projectTitle) {
		HLayout projectPanel = new HLayout();
		projectPanel.setLayoutLeftMargin(5);
		projectPanel.setLayoutRightMargin(5);
		projectPanel.setBackgroundColor("lightgray");
		projectPanel.setWidth100();
		projectPanel.setHeight(30);

		if (!projectTitle.isEmpty()) {
			projectPanel.setMembersMargin(5);
			
			VLayout imgHolder = new VLayout();
			imgHolder.setHeight100();
			imgHolder.setWidth(18);
			imgHolder.setLayoutTopMargin(6);
			Img projectIcon = new Img(Page.getAppImgDir() + "icons/16/tibco16-32.gif", 16, 16);
			projectIcon.setValign(VerticalAlignment.CENTER);
			imgHolder.addMember(projectIcon);
			projectPanel.addMember(imgHolder);
		} else {
			projectPanel.setMembersMargin(3);
		}

		String title = projectTitle;
		if (projectTitle.isEmpty()) {
			title = globalMsgBundle.noProjects_checkedOut_preText();
		}
		Label projectLabel = new Label(title);
		projectLabel.setStyleName(projectTitle.isEmpty() ? "ws-filtertext" : "ws-filterlink");
		projectLabel.setWrap(false);
		projectLabel.setValign(VerticalAlignment.CENTER);
		projectPanel.addMember(projectLabel);
		if (!projectTitle.isEmpty()) {
			projectLabel.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
				public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
					if (ProjectsPortlet.this.getMaximized() != null && ProjectsPortlet.this.getMaximized()) {
						ProjectsPortlet.this.restore();
					}
					WebStudio.get().showWorkSpacePage();
					WebStudio.get().getWorkspacePage().selectDefaultGroups();
					WebStudio.get().setCurrentlySelectedProject(projectTitle);
				}
			});
			
			//Append a button to close the project.
			HLayout deleteProjImgHolder = new HLayout();
			deleteProjImgHolder.setHeight100();
			deleteProjImgHolder.setWidth100();
			deleteProjImgHolder.setLayoutTopMargin(6);
			Img deleteProjIcon;
			String userAgent = Window.Navigator.getUserAgent().toLowerCase();
	        boolean isFireFox = false;
	        if(userAgent.matches(".*firefox/[0-9].*") && !userAgent.matches(".*seamonkey/.*")){
	        	isFireFox = true;
	        }
			if(LocaleInfo.getCurrentLocale().isRTL() && isFireFox){
				deleteProjImgHolder.setAlign(Alignment.LEFT);
				deleteProjIcon = new Img(Page.getAppImgDir() + "icons/16/delete.png");
				deleteProjIcon.setBaseStyle("imageButtonDashBoard");
			}
			else if(LocaleInfo.getCurrentLocale().isRTL()){
				deleteProjImgHolder.setAlign(Alignment.LEFT);
				deleteProjIcon = new Img(Page.getAppImgDir() + "icons/16/delete.png", 16,16);
			}
			else{
				deleteProjImgHolder.setAlign(Alignment.RIGHT);
				deleteProjIcon = new Img(Page.getAppImgDir() + "icons/16/delete.png", 16,16);
			}
			deleteProjIcon.setValign(VerticalAlignment.CENTER);
			deleteProjIcon.setTooltip(globalMsgBundle.portlet_remove_project());
			deleteProjIcon.setCursor(Cursor.POINTER);
			deleteProjIcon.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
				public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
					NavigatorResource resource = new NavigatorResource(projectTitle, null, projectTitle, ARTIFACT_TYPES.PROJECT.getValue(), null, ARTIFACT_TYPES.PROJECT);
					WebStudio.get().setCurrentlySelectedProject(projectTitle);
					ArtifactDeletionHelper.getInstance().deleteArtifact(new NavigatorResource[]{resource});
				}
			});
			deleteProjImgHolder.addMember(deleteProjIcon);
			projectPanel.addMember(deleteProjImgHolder);
		}

		if (projectTitle.isEmpty()) {
			Label projectCheckoutLabel = new Label(globalMsgBundle.noProjects_checkedOut_linkText());
			projectCheckoutLabel.setStyleName("ws-filterlink");
			projectCheckoutLabel.setValign(VerticalAlignment.CENTER);
			projectCheckoutLabel.setWrap(false);
			projectCheckoutLabel.setWidth(15);
			projectCheckoutLabel.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
				public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
					if (ProjectsPortlet.this.getMaximized() != null && ProjectsPortlet.this.getMaximized()) {
						ProjectsPortlet.this.restore();
					}
					
					WebStudio.get().getHeader().selectWorkspacePage();
					RMSCheckoutDialog chkoutDialog = new RMSCheckoutDialog();
					chkoutDialog.setBackToDashboard(true);
					chkoutDialog.show();
				}
			});
			projectPanel.addMember(projectCheckoutLabel);
		}

		return projectPanel;
	}

	/**
	 * Add newly checked out projects
	 */
	public void addProject(String projectName) {
		ManagedProject newProject = new ManagedProject(projectName);
		if (!managedProjectList.contains(newProject)) {
			if (managedProjectList.size() == 0) {
				projectContainer.removeMembers(projectContainer.getMembers());
			}
			managedProjectList.add(newProject);
			projectContainer.addMember(createProjectLink(newProject.getProjectName()));
		}
	}
	
	/**
	 * Remove the selected project from the project portal
	 * 
	 * @param projectName
	 */
	public void removeProject(String projectName) {
		ManagedProject newProject = new ManagedProject(projectName);
		if (managedProjectList.contains(newProject)) {
			managedProjectList.remove(newProject);
			createProjectLinks();
		}
	}
	
	/**
	 * Check if given project exists in the list of managed projects
	 * 
	 * @param projectName
	 * @return
	 */
	public boolean projectExists(String projectName) {
		ManagedProject newProject = new ManagedProject(projectName);
		if (managedProjectList.contains(newProject)) {
			return true;
		}

		return false;
	}
	
	/**
	 * Get the total available projects
	 * @return
	 */
	public int getTotalAvailableProjects() {
		return managedProjectList.size();
	}
}
