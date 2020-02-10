/**
 * 
 */
package com.tibco.cep.webstudio.client.editor;

import static com.tibco.cep.webstudio.client.IWebStudioWorkbenchConstants.PROP_DIRTY;
import static com.tibco.cep.webstudio.client.IWebStudioWorkbenchConstants.PROP_SAVE;
import static com.tibco.cep.webstudio.client.util.ErrorMessageDialog.showError;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.Window;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.KeyPressEvent;
import com.smartgwt.client.widgets.events.KeyPressHandler;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.editor.RuleTemplateInstanceEditorFactory.RuleTemplateInstanceEditor.TemplateInstanceChangeHandler;
import com.tibco.cep.webstudio.client.handler.IEditorPostCloseHandler;
import com.tibco.cep.webstudio.client.handler.IEditorPostSaveHandler;
import com.tibco.cep.webstudio.client.handler.IWebStudioPartChangeHandler;
import com.tibco.cep.webstudio.client.handler.WebStudioEditorPartChangeEvent;
import com.tibco.cep.webstudio.client.http.HttpFailureEvent;
import com.tibco.cep.webstudio.client.http.HttpFailureHandler;
import com.tibco.cep.webstudio.client.http.HttpMethod;
import com.tibco.cep.webstudio.client.http.HttpRequest;
import com.tibco.cep.webstudio.client.http.HttpSuccessHandler;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.model.RequestParameter;
import com.tibco.cep.webstudio.client.panels.CustomSC;
import com.tibco.cep.webstudio.client.problems.ProblemMarker;
import com.tibco.cep.webstudio.client.util.ArtifactUtil;
import com.tibco.cep.webstudio.client.util.LoadingMask;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;
import com.tibco.cep.webstudio.client.widgets.WebStudioToolbar;
/**
 * This class provides an template implementation of an editor. Currently
 * providing option to create a section based editor or a custom editor.
 * Additionally support to capturing onDirty and onSave events, implementation
 * of which is left to individual concrete editor classes.
 * 
 * @author Vikram Patil
 */
public abstract class AbstractEditor extends Tab implements IWebStudioPartChangeHandler, HttpSuccessHandler,
		HttpFailureHandler , 
        KeyPressHandler  {
	
	protected NavigatorResource selectedRecord;
	protected SectionStack sectionStack;
	protected VLayout layout;
	
	protected int propertyID = -1;
	protected boolean hasSections;
	protected boolean loadDataAtStartUp;
	protected boolean newArtifact;
	private String confirmSaveTitle;
	private String confirmSaveDescription;
	protected String projectName;
	
	protected HttpRequest request;
	
	protected boolean showLoading;
	
	private boolean readOnly;
	private boolean versionDiffContent;
	protected String revisionId;
	private boolean isMerge = false;
	private boolean isSyncMerge = false;
	
	protected ProblemMarker onOpenMarker;
	private List<RequestParameter> additionalRequestParams;

	private List<IEditorPostSaveHandler> postSaveHandlerList = null;	
	private List<IEditorPostCloseHandler> postCloseHandlerList = null;
	
	private GlobalMessages globalMsgBundle = (GlobalMessages) I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	
	/**
	 * Constructor
	 * 
	 * @param record
	 */
	public AbstractEditor(NavigatorResource record) {
		this(record, true, false, null, true, false);
	}

	/**
	 * Constructor
	 * 
	 * @param record
	 * @param loadDataAtStartup
	 */
	public AbstractEditor(NavigatorResource record, boolean loadDataAtStartup) {
		this(record, true, false, null, loadDataAtStartup, false);
	}

	/**
	 * Initialise the attributes
	 * 
	 * @param record
	 * @param hasSections
	 * @param isReadOnly
	 * @param revisionId
	 * @param loadDataAtStartup
	 */
	public AbstractEditor(NavigatorResource record, boolean isReadOnly, String revisionId, boolean loadDataAtStartup) {
		this(record, true, isReadOnly, revisionId, loadDataAtStartup, false);
	}
	
	/**
	 * Initialise the editor attributes
	 * 
	 * @param record
	 * @param hasSections
	 * @param isReadOnly
	 * @param revisionId
	 * @param loadDataAtStartup
	 * @param versionDiffContent
	 */
	public AbstractEditor(NavigatorResource record, boolean hasSections, boolean isReadOnly, String revisionId, boolean loadDataAtStartup, boolean versionDiffContent) {
		this.selectedRecord = record;
		this.hasSections = hasSections;
		this.loadDataAtStartUp = loadDataAtStartup;
		this.request = new HttpRequest();
		this.showLoading = true;
		this.readOnly = isReadOnly;
		this.revisionId = revisionId;
		this.versionDiffContent = versionDiffContent;
	}

	/**
	 * Initialize and setup an editor
	 */
	protected void initialize() {
		this.updateTitle();
		this.setCanClose(true);

		this.layout = new VLayout();
		this.layout.setWidth100();
		this.layout.setHeight100();
		this.layout.setStyleName("ws-templateInstanceContextArea");
		layout.setShowResizeBar(false);
		this.addConfigurationSection(this.layout);
		if (isVersionDiffContent()) {
			addArtifactDiffLegend(this.layout);
		}
		
		if (this.hasSections) {
			this.sectionStack = new SectionStack();
			this.sectionStack.setAnimateSections(true);
			this.sectionStack.setCanResizeSections(true);
			this.sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
			this.sectionStack.setOverflow(Overflow.AUTO);
			this.sectionStack.setWidth("100%");
			this.sectionStack.setHeight(this.getSectionStackHeight());
			this.sectionStack.setShowResizeBar(false);
			this.addSections(this.getSections());

			this.layout.addMember(this.sectionStack);
		} else {
			this.layout.addMember(this.getWidget());
		}

		this.setPane(this.layout);

		this.layout.addKeyPressHandler(this);
		this.doAddHandler(this, WebStudioEditorPartChangeEvent.TYPE);

		if (this.loadDataAtStartUp) {
			this.loadEditorData(this.selectedRecord);
		}
		
		setSaveConfirmationProperties();
	}

	public void addConfigurationSection(VLayout layout) {

	}
	
	public void addArtifactDiffLegend(VLayout layout) {
		
	}

	public String getSectionStackHeight() {
		return "100%";
	}

	/**
	 * Add sections to the container
	 * 
	 * @param sections
	 */
	public void addSections(SectionStackSection[] sections) {
		for (SectionStackSection section : sections) {
			this.sectionStack.addSection(section);
		}
	}

	/**
	 * Returns the Layout associated with the Editor Tab
	 * 
	 * @return
	 */
	public VLayout getEditorLayout() {
		return this.layout;
	}

	/**
	 * List of various sections supported by the target editor. Individual
	 * editors to provide with the list of sections.
	 * 
	 * @return
	 */
	protected abstract SectionStackSection[] getSections();

	/**
	 * Widget to be created if SectionStack is not used. Individual editors to
	 * provide with specific widget implementation.
	 * 
	 * @return
	 */
	protected abstract Canvas getWidget();

	//Override this
	public TemplateInstanceChangeHandler getChangeHandler() {
		return null;
	}

	/**
	 * Handle various events fired. Currently supported, onDirty: State change
	 * onSave: To persist new state
	 */
	public void fireChange(WebStudioEditorPartChangeEvent event) {
		AbstractEditor editor = (AbstractEditor) event.getSource();
		switch (editor.getPropertyID()) {
			case PROP_DIRTY:
				this.onDirty();
				//Update editor dirty
				this.updateTitle();
				break;
			case PROP_SAVE:
				onSave();
				break;
		}
	}

	/**
	 * Set attributes for showing save confirmation dialog
	 * 
	 * @param title
	 * @param description
	 */
	public void setShowSaveConfirmDialog(String title, String description) {
		this.confirmSaveTitle = title;
		this.confirmSaveDescription = description;
	}
	
	/**
	 * Set properties for Save confirmation dialog
	 */
	public abstract void setSaveConfirmationProperties();
	
	/**
	 * Display a confirmation dialog before saving
	 */
	protected void showConfirmDialog() {
		CustomSC.confirm(confirmSaveTitle, confirmSaveDescription, new BooleanCallback () {
			/* (non-Javadoc)
			 * @see com.smartgwt.client.util.BooleanCallback#execute(java.lang.Boolean)
			 */
			public void execute (Boolean value) {
				if (Boolean.TRUE.equals (value)) {
					onSave();
				} else {
					propertyID = PROP_DIRTY;
				}
			}
		});
	}
	
	/**
	 * Actions to do post successful save operation
	 */
	protected void postSave() {
		//Update editor dirty after save, 
		updateTitle();
		if (postSaveHandlerList != null) {
			for (int i = 0; i < postSaveHandlerList.size(); i++) {
				postSaveHandlerList.get(i).postSave();
			}	
		}
	}
	

	/**
	 * On dirty action to be performed if state of the data changed. Individual
	 * editors to provide specific implementation.
	 */
	public abstract void onDirty();

	/**
	 * Save action to be performed on Save toolbar button click. Individual
	 * editors to provide specific implementation.
	 */
	public abstract void onSave();
	
	/**
	 * Validate action to be performed on Validate toolbar button click.
	 * Individual artifact type editor to provide specific implementation.
	 * 
	 * @return
	 */
	public abstract boolean onValidate();
	
	/**
	 * Export action to be performed on Export toolbar button click.
	 * 
	 * @return
	 */
	public boolean onExport() {
		if (!newArtifact) {
			String url = ArtifactUtil.getArtifactURL(this.getSelectedResource());
			if (url != null) {
				Window.open(url, "_self", null);
			}
			return true;
		} else {
			String artifactName = this.getSelectedResource().getId().substring(this.getSelectedResource().getId().lastIndexOf("$") + 1, this.getSelectedResource().getId().indexOf("."));
			CustomSC.say("Save Artifact", "Need to save "+ artifactName + " before exporting");
			return false;
		}
	}

	/**
	 * Change Title of the editor
	 */
	public void updateTitle() {
		if (this.selectedRecord != null) {
			String imgHTML = Canvas.imgHTML(isReadOnly()? getDisabledIcon(this.selectedRecord.getIcon()) 
					: this.selectedRecord.getIcon(), 16, 16);
			String title = this.selectedRecord.getName();
			if (title.indexOf(".") != -1) {
				title = title.substring(0, title.indexOf("."));
			}
			boolean disabled = true;

			if (this.isDirty()) {
				title = "* " + title;
				disabled = false;
			}
			updateToolBar(disabled);
			
			String titleText = "<span> <style=\"color:grey\">" + imgHTML  + "&nbsp;" + title;
			if (isMerge()) {
				titleText += "<I>[Merge]</I></span>";
			} else {
				titleText += (isReadOnly())?"<I>" + globalMsgBundle.editorTitleReadonly_Message() + (revisionId != null ? "(" + globalMsgBundle.revisionInTitle_Tag() +":" + revisionId + ")" : "") + "</I></span>":"</span>";
			}	
			this.setTitle(titleText);
		}
	}
	
	protected String getDisabledIcon(String imgHTML) {
		if (imgHTML.endsWith(".png")) {
			imgHTML = imgHTML.replace(".png", "_Disabled.png");
		}
		if (imgHTML.endsWith(".gif")) {
			imgHTML = imgHTML.replace(".gif", "_Disabled.gif");
		}
		return imgHTML;
	}
	
	/**
	 * Load data for the specific editor
	 */
	protected void loadEditorData(NavigatorResource selectedRecord) {
		String artifactPath = selectedRecord.getId().replace("$", "/");
		projectName = artifactPath.substring(0, artifactPath.indexOf("/"));
		String artifactExtention = artifactPath.substring(artifactPath.indexOf(".") + 1, artifactPath.length());
		artifactPath = artifactPath.substring(artifactPath.indexOf("/"), artifactPath.indexOf("."));

		ArtifactUtil.addHandlers(this);

		this.request.clearRequestParameters();
		this.request.setMethod(HttpMethod.GET);
		this.request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PROJECT_NAME, projectName));
		this.request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_PATH, artifactPath));
		this.request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_FILE_EXTN,
				artifactExtention));
		if (getAdditionalRequestParams() != null) {
			for (RequestParameter rp : getAdditionalRequestParams()) {
				this.request.addRequestParameters(rp);
			}
		}
		
		String url = null;
		if (isVersionDiffContent()) {
			url = ServerEndpoints.RMS_GET_ARTIFACT_VERSION_DIFF.getURL();
			if (revisionId != null) {
				this.request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_REVISION_ID, revisionId));
			}
		} else if (isReadOnly() && !isVersionDiffContent()) {
			url = ServerEndpoints.RMS_GET_WORKLIST_ITEM_REVIEW.getURL();
			if (revisionId != null) {
				this.request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_REVISION_ID, revisionId));
			}
		} else if (getEditorUrl() != null) {
			url = getEditorUrl();
		} else {
			url = ServerEndpoints.RMS_GET_ARTIFACT_CONTENTS.getURL();
		}
		
		request.submit(url);
		
		if (showLoading) {
			LoadingMask.showMask();
		}
	}

	/**
	 * Update the toolbar to enable
	 * 
	 * @param boolean disabled
	 */
	protected void updateToolBar(boolean disabled) {
		WebStudio.get().getApplicationToolBar().disableButton(WebStudioToolbar.TOOL_STRIP_SAVE_ID, disabled);
		// Enable/disable more buttons
	}

	/**
	 * Check if editor is dirty
	 * 
	 * @return boolean
	 */
	public boolean isDirty() {
		return this.propertyID == PROP_DIRTY;
	}

	/**
	 * Fire a specified Event
	 * 
	 * @param propId
	 */
	public void firePropertyChange(int propertyID) {
		this.propertyID = propertyID;
		this.fireEvent(new WebStudioEditorPartChangeEvent());
	}

	/**
	 * Fire Save Event
	 */
	public void save() {
		this.firePropertyChange(PROP_SAVE);
	}

	/**
	 * Fire Dirty Event
	 */
	public void makeDirty() {
		this.firePropertyChange(PROP_DIRTY);
	}
	
	/**
	 * Provide read only support for Editor
	 */
	abstract public void makeReadOnly();
	
	/**
	 * Handle the failure cases.
	 */
	@Override
	public void onFailure(HttpFailureEvent event) {
		ArtifactUtil.removeHandlers(this);
		LoadingMask.clearMask();

		String responseMessage = event.getData().getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
		showError(responseMessage);
		if (isVersionDiffContent()) {
			this.close();
			WebStudio.get().getEditorPanel().removeTab(this);
		}
	}
	
	@Override
	public void onKeyPress(KeyPressEvent event) {
		if (event.isCtrlKeyDown() && event.getKeyName().equals("S")) {
			event.cancel();
			if (isDirty()) {
				this.propertyID = PROP_SAVE;
				onSave();
			}
		}
	}

	public int getPropertyID() {
		return this.propertyID;
	}

	public void setPropertyID(int propertyID) {
		this.propertyID = propertyID;
	}

	public boolean isHasSections() {
		return this.hasSections;
	}

	public void setHasSections(boolean hasSections) {
		this.hasSections = hasSections;
	}

	public NavigatorResource getSelectedResource() {
		return this.selectedRecord;
	}

	public SectionStack getSectionStack() {
		return this.sectionStack;
	}

	public boolean isNewArtifact() {
		return this.newArtifact;
	}

	public void setNewArtifact(boolean newArtifact) {
		this.newArtifact = newArtifact;
	}

	/**
	 * for tasks pre-close editors
	 */
	public void close() {
		if (postCloseHandlerList != null) {
			for (int i = 0; i < postCloseHandlerList.size(); i++) {
				postCloseHandlerList.get(i).onClose();
			}	
		}		
	}
	
	/**
	 * Individual Editors will override with their specific URL's
	 * @return
	 */
	public abstract String getEditorUrl();

	public boolean isShowLoading() {
		return showLoading;
	}

	public void setShowLoading(boolean showLoading) {
		this.showLoading = showLoading;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public String getRevisionId() {
		return revisionId;
	}

	public void setRevisionId(String revisionId) {
		this.revisionId = revisionId;
	}

	public boolean isMerge() {
		return isMerge;
	}

	public void setMerge(boolean isMerge) {
		this.isMerge = isMerge;
	}

	public boolean isSyncMerge() {
		return isSyncMerge;
	}

	public void setSyncMerge(boolean isSyncMerge) {
		this.isSyncMerge = isSyncMerge;
	}

	public String getConfirmSaveTitle() {
		return confirmSaveTitle;
	}

	public void setConfirmSaveTitle(String confirmSaveTitle) {
		this.confirmSaveTitle = confirmSaveTitle;
	}

	public String getConfirmSaveDescription() {
		return confirmSaveDescription;
	}

	public void setConfirmSaveDescription(String confirmSaveDescription) {
		this.confirmSaveDescription = confirmSaveDescription;
	}

	public String getURI() {
		return null;
	}
	
	public void gotoMarker(ProblemMarker marker) {
		//Override this
	}
	
	public void setOnOpenMarker(ProblemMarker marker) {
		this.onOpenMarker = marker;
	}
	
	public ProblemMarker getOnOpenMarker() {
		return onOpenMarker;
	}

	public boolean isVersionDiffContent() {
		return versionDiffContent;
	}

	public void setVersionDiffContent(boolean versionDiffContent) {
		this.versionDiffContent = versionDiffContent;
	}

	public List<RequestParameter> getAdditionalRequestParams() {
		return additionalRequestParams;
	}
	
	public void setAdditionalRequestParams(List<RequestParameter> additionalRequestParams) {
		this.additionalRequestParams = additionalRequestParams;
	}
	
	public boolean isMergeComplete() {
		return false;
	}
	
	public void addEditorPostSaveHandler(IEditorPostSaveHandler editorPostSaveHandler) {
		if (postSaveHandlerList == null)
			postSaveHandlerList = new ArrayList<IEditorPostSaveHandler>();
		postSaveHandlerList.add(editorPostSaveHandler);
	}

	public void addEditorPostCloseHandler(IEditorPostCloseHandler editorPostCloseHandler) {
		if (postCloseHandlerList == null)
			postCloseHandlerList = new ArrayList<IEditorPostCloseHandler>();
		postCloseHandlerList.add(editorPostCloseHandler);
	}
	
}
