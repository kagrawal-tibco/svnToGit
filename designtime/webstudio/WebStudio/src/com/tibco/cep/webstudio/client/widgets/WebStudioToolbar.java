package com.tibco.cep.webstudio.client.widgets;

import static com.tibco.cep.webstudio.client.util.ArtifactUtil.isDecisionManagerInstalled;

import com.google.gwt.user.client.Window;
import com.smartgwt.client.types.SelectionType;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.toolbar.RibbonBar;
import com.smartgwt.client.widgets.toolbar.RibbonGroup;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.editor.AbstractEditor;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.i18n.RMSMessages;
import com.tibco.cep.webstudio.client.logging.WebStudioClientLogger;
import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.panels.CustomSC;
import com.tibco.cep.webstudio.client.util.ArtifactDeletionHelper;
import com.tibco.cep.webstudio.client.util.ArtifactUtil;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil.ARTIFACT_TYPES;
import com.tibco.cep.webstudio.client.util.ProjectValidationHelper;

/**
 * Application Toolbar with various actions supported, additionally support for handling each of these actions.
 * 
 * @author Vikram Patil
 */
public class WebStudioToolbar extends HLayout implements ClickHandler {
	
	private static final WebStudioClientLogger logger = WebStudioClientLogger.getLogger(WebStudioToolbar.class.getName());
	
	private static final int TOOL_BAR_HEIGHT = 16;
	private static final String TOOL_BUTTON_SIZE = "16px";
	
	// List of Id's associated to various Tool Strip buttons
	public static final String TOOL_STRIP_SAVE_ID = "id_save";
	public static final String TOOL_STRIP_DELETE_ID = "id_delete";
	public static final String TOOL_STRIP_CUT_ID = "id_cut";
	public static final String TOOL_STRIP_COPY_ID = "id_copy";
	public static final String TOOL_STRIP_PASTE_ID = "id_paste";
	public static final String TOOL_STRIP_EXECUTE_ID = "id_execute";
	public static final String TOOL_STRIP_ANALYSE_ID = "id_analyse";
	public static final String TOOL_STRIP_VALIDATE_ID = "id_validate";
	public static final String TOOL_STRIP_CHECKOUT_ID = "id_checkout";
	public static final String TOOL_STRIP_COMMIT_ID = "id_commit";
	public static final String TOOL_STRIP_REVERT_ID = "id_revert";
	public static final String TOOL_STRIP_WORKLIST_ID = "id_worklist";
	public static final String TOOL_STRIP_UPDATE_ID = "id_update";
	public static final String TOOL_STRIP_DEPLOY_ID = "id_deploy";
	public static final String TOOL_STRIP_EXPORT_ID = "id_export";
	public static final String TOOL_STRIP_IMPORT_ID = "id_import";
	
	
	private RibbonBar toolStrip;
	 
	private RMSMessages rmsMsgBundle = (RMSMessages) I18nRegistry.getResourceBundle(I18nRegistry.RMS_MESSAGES);
	private GlobalMessages globalMsgBundle = (GlobalMessages)I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	
	private ToolStripButton saveButton;
	private ToolStripButton deleteButton;
	private ToolStripButton validateButton;
	
	/**
	 * Default Constructor
	 */
	public WebStudioToolbar() {
		super();
		
		// initialize the layout container
		this.setHeight(TOOL_BAR_HEIGHT);
		this.setAutoWidth();
		this.setStyleName("ws-ApplicationTool");  

		this.addMember(createToolBar());
	}

	/**
	 * Create the toolbar and add all the tool bar buttons
	 * 
	 * @return
	 */
	public Canvas createToolBar() {
		toolStrip = new RibbonBar();
		toolStrip.setMembersMargin(2);
		
		// edit buttons
		RibbonGroup editGroup = new RibbonGroup();
		editGroup.setAutoWidth();
		editGroup.setStyleName("ws-toolbar-group");
		editGroup.setTitle(globalMsgBundle.toolbar_option_edit());
		
		saveButton = createButton(globalMsgBundle.toolbar_save(), TOOL_STRIP_SAVE_ID, "save.gif", true, SelectionType.BUTTON);
		editGroup.addControl(saveButton);
		saveButton.setShowDisabled(true);
		saveButton.setDisabled(true);
		saveButton.setStyleName("ws-toolbar-button");
		
		deleteButton = createButton(globalMsgBundle.toolbar_delete(), TOOL_STRIP_DELETE_ID, "remove.png", true, SelectionType.BUTTON);
		editGroup.addControl(deleteButton);
		toolStrip.addGroup(editGroup);
		deleteButton.setShowDisabled(true);
		deleteButton.setDisabled(true);
		deleteButton.setStyleName("ws-toolbar-button");
		
		validateButton = createButton(globalMsgBundle.toolbar_validate(), TOOL_STRIP_VALIDATE_ID, "validation_16x16.png", false, SelectionType.BUTTON);
		editGroup.addControl(validateButton);
		validateButton.setShowDisabled(true);
		validateButton.setDisabled(true);
		validateButton.setStyleName("ws-toolbar-button");		
		
		// RMS buttons
		RibbonGroup rmsGroup = new RibbonGroup();
		rmsGroup.setTitle(globalMsgBundle.toolbar_option_rms());
		rmsGroup.setStyleName("ws-toolbar-group");
		rmsGroup.setAutoWidth();
		
		ToolStripButton checkoutButton = createButton(globalMsgBundle.toolbar_rms_checkout(), TOOL_STRIP_CHECKOUT_ID, "checkout.png", false, SelectionType.BUTTON);
		checkoutButton.setStyleName("ws-toolbar-button");
		rmsGroup.addControl(checkoutButton);
		
		ToolStripButton commitButton  = createButton(globalMsgBundle.toolbar_rms_commit(), TOOL_STRIP_COMMIT_ID, "commit.png", true, SelectionType.BUTTON);
		commitButton.setStyleName("ws-toolbar-button");
		commitButton.setShowDisabled(true);
		commitButton.setDisabled(true);
		rmsGroup.addControl(commitButton);
		
		ToolStripButton syncButton  = createButton(globalMsgBundle.toolbar_rms_update(), TOOL_STRIP_UPDATE_ID, "synchronize.png", true, SelectionType.BUTTON);
		syncButton.setStyleName("ws-toolbar-button");
		syncButton.setShowDisabled(true);
		syncButton.setDisabled(true);
		rmsGroup.addControl(syncButton);
		
		ToolStripButton revertButton  = createButton(globalMsgBundle.toolbar_rms_revert(), TOOL_STRIP_REVERT_ID, "revert.png", true, SelectionType.BUTTON);
		revertButton.setStyleName("ws-toolbar-button");
		revertButton.setShowDisabled(true);
		revertButton.setDisabled(true);
		rmsGroup.addControl(revertButton);
		
		ToolStripButton worklistButton = createButton(globalMsgBundle.toolbar_rms_worklist(), TOOL_STRIP_WORKLIST_ID, "worklist.png", false, SelectionType.BUTTON);
		worklistButton.setStyleName("ws-toolbar-button");
		rmsGroup.addControl(worklistButton);
		
		ToolStripButton genDeployableButton = createButton(globalMsgBundle.toolbar_deploy(), TOOL_STRIP_DEPLOY_ID, "generatedeployable.gif", false, SelectionType.BUTTON);
		genDeployableButton.setStyleName("ws-toolbar-button");
		rmsGroup.addControl(genDeployableButton);

		toolStrip.addGroup(rmsGroup);
		
		disableButton(TOOL_STRIP_COMMIT_ID, true);
		disableButton(TOOL_STRIP_UPDATE_ID, true);
		disableButton(TOOL_STRIP_REVERT_ID, true);
		
		// Tools Group
		RibbonGroup toolsGroup = new RibbonGroup();
		toolsGroup.setTitle(globalMsgBundle.toolbar_option_tools());
		toolsGroup.setStyleName("ws-toolbar-group");
		toolsGroup.setAutoWidth();
		
		ToolStripButton exportButton  = createButton(globalMsgBundle.toolbar_export(), TOOL_STRIP_EXPORT_ID, "export.gif", false, SelectionType.BUTTON);
		exportButton.setStyleName("ws-toolbar-button");
		exportButton.setShowDisabled(true);
		exportButton.setDisabled(true);
		toolsGroup.addControl(exportButton);
		
		ToolStripButton importButton = createButton(globalMsgBundle.toolbar_import(), TOOL_STRIP_IMPORT_ID, "import.gif", false, SelectionType.BUTTON);
		importButton.setStyleName("ws-toolbar-button");
		importButton.setShowDisabled(true);
		importButton.setDisabled(true);
		toolsGroup.addControl(importButton);
		toolStrip.addGroup(toolsGroup);
		
		disableButton(TOOL_STRIP_EXPORT_ID, true);
		disableButton(TOOL_STRIP_IMPORT_ID, true);
	
		return toolStrip;
	}
	
	/**
	 * Create and set properties for a Tool bar button
	 * 
	 * @param tooltip
	 * @param id
	 * @param icon
	 * @param disabled
	 * @return
	 */
	private ToolStripButton createButton(String tooltip, String id, String icon, boolean disabled, SelectionType actionType) {
		ToolStripButton button = new ToolStripButton();
		button.setIcon(WebStudioMenubar.ICON_PREFIX + icon);
		button.setIconHeight(16);
		button.setIconWidth(16);
		button.setSize(TOOL_BUTTON_SIZE, TOOL_BUTTON_SIZE);
		button.setActionType(actionType);
		button.setShowDown(true);
		button.setShowRollOver(true);
		button.setShowDisabled(false);
		button.setDisabled(disabled);
		button.addClickHandler(this);
		button.setTooltip(tooltip);
		button.setID(id);

		return button;
	}

	@Override
	public void onClick(ClickEvent event) {
		ToolStripButton selectedButton = (ToolStripButton) event.getSource();
		
		if (selectedButton.getID().equals(TOOL_STRIP_SAVE_ID)) {
			AbstractEditor editor =  (AbstractEditor)WebStudio.get().getEditorPanel().getSelectedTab();
			if (editor != null && editor.isDirty()) {
				editor.save();
			}
			return;
		} else if (selectedButton.getID().equals(TOOL_STRIP_CHECKOUT_ID)) {
			new RMSCheckoutDialog().show();
			return;
		} else if (selectedButton.getID().equals(TOOL_STRIP_COMMIT_ID)) {
			if (WebStudio.get().getCurrentlySelectedProject() == null || WebStudio.get().getCurrentlySelectedProject().isEmpty()) {
				CustomSC.say(rmsMsgBundle.rms_selectProjectToCommit_message());
			} else {
				String[] selectedArtifacts = null;
				if (WebStudio.get().getWorkspacePage().isShownAsTree() && WebStudio.get().getCurrentlySelectedArtifact() != null) {
					selectedArtifacts = new String[]{WebStudio.get().getCurrentlySelectedArtifact()};
				} else {
					selectedArtifacts = new String[]{WebStudio.get().getCurrentlySelectedProject()};
				}
				new RMSCommitDialog(selectedArtifacts).show();
			}
			return;
		} else if (selectedButton.getID().equals(TOOL_STRIP_UPDATE_ID)) {
			if ((WebStudio.get().getCurrentlySelectedProject() == null || WebStudio.get().getCurrentlySelectedProject().isEmpty())) {
				CustomSC.say(rmsMsgBundle.rms_selectProjectToUpdate_message());
			} else {
				String[] selectedArtifacts = null;
				if (WebStudio.get().getWorkspacePage().isShownAsTree() && WebStudio.get().getCurrentlySelectedArtifact() != null) {
					selectedArtifacts = new String[]{WebStudio.get().getCurrentlySelectedArtifact()};
				} else {
					selectedArtifacts = new String[]{WebStudio.get().getCurrentlySelectedProject()};
				}
				new RMSUpdateDialog(selectedArtifacts).show();
			}
			return;
		} else if (selectedButton.getID().equals(TOOL_STRIP_REVERT_ID)) {
			if (WebStudio.get().getCurrentlySelectedProject() == null || WebStudio.get().getCurrentlySelectedProject().isEmpty()) {
				CustomSC.say(rmsMsgBundle.rms_selectProjectToRevert_message());
			} else {
				String[] selectedArtifacts = null;
				if (WebStudio.get().getWorkspacePage().isShownAsTree() && WebStudio.get().getCurrentlySelectedArtifact() != null) {
					selectedArtifacts = new String[]{WebStudio.get().getCurrentlySelectedArtifact()};
				} else {
					selectedArtifacts = new String[]{WebStudio.get().getCurrentlySelectedProject()};
				}
				new RMSRevertDialog(selectedArtifacts).show();
			}
		} else if (selectedButton.getID().equals(TOOL_STRIP_WORKLIST_ID)) {
			new RMSWorklistDialog().show();
			return;
		} else if (selectedButton.getID().equals(TOOL_STRIP_DEPLOY_ID)) {
			String selectedArtifact = WebStudio.get().getCurrentlySelectedArtifact();
			if (WebStudio.get().isRmsOptionViaContextMenu()
					&& selectedArtifact != null
					&& selectedArtifact.indexOf(".") != -1
					&& !selectedArtifact.endsWith(".ruletemplateinstance")
					&& !selectedArtifact.endsWith(".rulefunctionimpl")) {
				CustomSC.say(rmsMsgBundle.rms_cannotDeployArtifact_message());
			} else {
				String[] selectedArtifacts = new String[]{selectedArtifact};
				if (!WebStudio.get().isRmsOptionViaContextMenu()) {
					selectedArtifacts = null;
				}
				new RMSDeployArtifactDialog(selectedArtifacts).show();
			}
			return;
		} else if (selectedButton.getID().equals(TOOL_STRIP_DELETE_ID)) {
			this.disableButton(TOOL_STRIP_DELETE_ID, true);
			Tab selectedTab = WebStudio.get().getEditorPanel().getSelectedTab();
			NavigatorResource selectedResource = null;
			if (selectedTab == null) {
				logger.info("No tab selected, looking for selected artifact in the tree grid.");
				selectedResource = (NavigatorResource)WebStudio.get().getWorkspacePage()
						.getGroupContentsWindow().getArtifactTreeGrid().getSelectedRecord();
			}
			else if (selectedTab instanceof AbstractEditor) {
				AbstractEditor selectedEditor = (AbstractEditor) selectedTab;
				selectedResource = selectedEditor.getSelectedResource();
			}
			if (selectedResource != null) {
				logger.info("Deleting selected artifact - " + selectedResource.getId());
				ArtifactDeletionHelper.getInstance().deleteArtifact(new NavigatorResource[] {selectedResource});
			}
			else {
				logger.info("No artifact selected to delete.");
			}
			return;
		} else if (selectedButton.getID().equals(TOOL_STRIP_VALIDATE_ID)) {
			NavigatorResource resource = WebStudio.get().getWorkspacePage().getGroupContentsWindow().getArtifactTreeGrid().getResourceById(WebStudio.get().getCurrentlySelectedArtifact());
			if(resource.getType().equals(ARTIFACT_TYPES.PROJECT.getValue().toLowerCase())) {
				ProjectValidationHelper.getInstance().validateProject(resource.getName());
			} else {
				Tab selectedTab = WebStudio.get().getEditorPanel().getSelectedTab();
				if (selectedTab instanceof AbstractEditor) {
					AbstractEditor selectedEditor = (AbstractEditor) selectedTab;
					selectedEditor.onValidate();
				}
			}
		} else if (selectedButton.getID().equals(TOOL_STRIP_EXPORT_ID)) {
			Tab selectedTab = WebStudio.get().getEditorPanel().getSelectedTab();
			if (selectedTab instanceof AbstractEditor) {
				AbstractEditor selectedEditor = (AbstractEditor) selectedTab;
				selectedEditor.onExport();
			}
			if (selectedTab == null) {
				NavigatorResource selectedResource = (NavigatorResource)WebStudio.get().getWorkspacePage()
						.getGroupContentsWindow().getArtifactTreeGrid().getSelectedRecord();
				if (selectedResource.getType().equals(ARTIFACT_TYPES.RULEFUNCTIONIMPL.getValue().toLowerCase()) 
						&& !isDecisionManagerInstalled(true)) {
					return;
				}
				String rURL = ArtifactUtil.getArtifactURL(selectedResource);
				if (rURL != null) { 
					Window.open(rURL, "_self", null);
				}
			}
		} else if (selectedButton.getID().equals(TOOL_STRIP_IMPORT_ID)) {
			WebStudio.get().getWorkspacePage().getGroupContentsWindow()
			.getArtifactTreeGrid().getClickHandler().doImport();
		}
	}
	
	/**
	 * Disable a specific tool bar button
	 * @param id
	 * @param isDisabled
	 */
	@SuppressWarnings("static-access")
	public void disableButton(String id, boolean isDisabled){
		ToolStripButton button = (ToolStripButton)toolStrip.getById(id);
		button.setSelected(false);
		button.setDisabled(isDisabled);
	}
	
	/**
	 * Enable a specific tool bar button
	 * @param id
	 */
	@SuppressWarnings("static-access")
	public void enableButton(String id){
		ToolStripButton button = (ToolStripButton)toolStrip.getById(id);
		button.enable();
	}
	
	/**
	 * Mark a specific tool bar button as selected
	 * @param id
	 */
	@SuppressWarnings("static-access")
	public void selectButton(String id){
		ToolStripButton button = (ToolStripButton)toolStrip.getById(id);
		button.enable();
		button.setSelected(true);
	}
	/**
	 * @return the deleteButton
	 */
	public ToolStripButton getDeleteButton() {
		return deleteButton;
	}	
}