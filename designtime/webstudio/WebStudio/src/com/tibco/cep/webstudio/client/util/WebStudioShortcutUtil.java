package com.tibco.cep.webstudio.client.util;

import static com.tibco.cep.webstudio.client.util.ArtifactUtil.isDecisionManagerInstalled;

import com.google.gwt.user.client.Window;
import com.smartgwt.client.util.EventHandler;
import com.smartgwt.client.widgets.events.KeyPressEvent;
import com.smartgwt.client.widgets.tab.Tab;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.editor.AbstractEditor;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.i18n.RMSMessages;
import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.panels.CustomSC;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil.ARTIFACT_TYPES;
import com.tibco.cep.webstudio.client.widgets.RMSCheckoutDialog;
import com.tibco.cep.webstudio.client.widgets.RMSCommitDialog;
import com.tibco.cep.webstudio.client.widgets.RMSDeployArtifactDialog;
import com.tibco.cep.webstudio.client.widgets.RMSRevertDialog;
import com.tibco.cep.webstudio.client.widgets.RMSWorklistDialog;

/**
 * This class is used to handle the shortcut keys operation in webstudio.
 * 
 * @author dijadhav
 * 
 */
public class WebStudioShortcutUtil {
	private static RMSMessages rmsMsgBundle = (RMSMessages) I18nRegistry
			.getResourceBundle(I18nRegistry.RMS_MESSAGES);

	/**
	 * This method is used to handle the key press event in WebStudio.
	 * 
	 * @param event
	 */
	public static void handleKeyPressEvent(KeyPressEvent event) {

		String key = EventHandler.getKey();
		if (EventHandler.shiftKeyDown() && EventHandler.altKeyDown()) {
			if ("R".equals(key) || "K".equals(key) || "P".equals(key)
					|| "L".equals(key) || "W".equals(key) || "G".equals(key)
					|| "J".equals(key) || "O".equals(key) || "U".equals(key)) {
				if (!WebStudio.get().getHeader().isWorkSpacePageSelected()) {
					WebStudio.get().getHeader().selectWorkspacePage();
				//	WebStudio.get().getWorkspacePage().focus();
					//WebStudio.get().getWorkspacePage().setCanFocus(true);
				}
			}
			if ("R".equals(key)) {
				event.cancel();
				if (WebStudio.get().getCurrentlySelectedProject() == null
						|| WebStudio.get().getCurrentlySelectedProject()
								.isEmpty()) {
					CustomSC.say(rmsMsgBundle
							.rms_selectProjectToRevert_message());
				} else {
					String[] selectedArtifacts = null;
					if (WebStudio.get().getWorkspacePage().isShownAsTree()
							&& WebStudio.get().getCurrentlySelectedArtifact() != null) {
						selectedArtifacts = new String[] { WebStudio.get()
								.getCurrentlySelectedArtifact() };
					} else {
						selectedArtifacts = new String[] { WebStudio.get()
								.getCurrentlySelectedProject() };
					}
					new RMSRevertDialog(selectedArtifacts).show();
				}

			} else if ("K".equals(key)) {
				event.cancel();
				new RMSCheckoutDialog().show();
			} else if ("P".equals(key)) {
				event.cancel();

				if (WebStudio.get().getCurrentlySelectedProject() == null
						|| WebStudio.get().getCurrentlySelectedProject()
								.isEmpty()) {
					CustomSC.say(rmsMsgBundle
							.rms_selectProjectToCommit_message());
				} else {
					String[] selectedArtifacts = null;
					if (WebStudio.get().getWorkspacePage().isShownAsTree()
							&& WebStudio.get().getCurrentlySelectedArtifact() != null) {
						selectedArtifacts = new String[] { WebStudio.get()
								.getCurrentlySelectedArtifact() };
					} else {
						selectedArtifacts = new String[] { WebStudio.get()
								.getCurrentlySelectedProject() };
					}
					new RMSCommitDialog(selectedArtifacts).show();
				}
			} else if ("L".equals(key)) {
				event.cancel();
			} else if ("W".equals(key)) {
				event.cancel();
				new RMSWorklistDialog().show();
			} else if ("G".equals(key)) {
				event.cancel();

				String selectedArtifact = WebStudio.get()
						.getCurrentlySelectedArtifact();
				if (WebStudio.get().isRmsOptionViaContextMenu()
						&& selectedArtifact != null
						&& selectedArtifact.indexOf(".") != -1
						&& !selectedArtifact.endsWith(".ruletemplateinstance")
						&& !selectedArtifact.endsWith(".rulefunctionimpl")) {
					CustomSC.say(rmsMsgBundle
							.rms_cannotDeployArtifact_message());
				} else {
					String[] selectedArtifacts = new String[] { selectedArtifact };
					if (!WebStudio.get().isRmsOptionViaContextMenu()) {
						selectedArtifacts = null;
					}
					new RMSDeployArtifactDialog(selectedArtifacts).show();
				}
				return;

			} else if ("J".equals(key)) {
				event.cancel();
				NavigatorResource resource = WebStudio
						.get()
						.getWorkspacePage()
						.getGroupContentsWindow()
						.getArtifactTreeGrid()
						.getResourceById(
								WebStudio.get().getCurrentlySelectedArtifact());
				if (resource.getType().equals(
						ARTIFACT_TYPES.PROJECT.getValue().toLowerCase())) {
					ProjectValidationHelper.getInstance().validateProject(
							resource.getName());
				} else {
					Tab selectedTab = WebStudio.get().getEditorPanel()
							.getSelectedTab();
					if (selectedTab instanceof AbstractEditor) {
						AbstractEditor selectedEditor = (AbstractEditor) selectedTab;
						selectedEditor.onValidate();
					}
				}
			} else if ("O".equals(key)) {
				event.cancel();

				Tab selectedTab = WebStudio.get().getEditorPanel()
						.getSelectedTab();
				if (selectedTab instanceof AbstractEditor) {
					AbstractEditor selectedEditor = (AbstractEditor) selectedTab;
					selectedEditor.onExport();
				}
				if (selectedTab == null) {
					NavigatorResource selectedResource = (NavigatorResource) WebStudio
							.get().getWorkspacePage().getGroupContentsWindow()
							.getArtifactTreeGrid().getSelectedRecord();
					if (selectedResource.getType().equals(
							ARTIFACT_TYPES.RULEFUNCTIONIMPL.getValue()
									.toLowerCase())
							&& !isDecisionManagerInstalled(true)) {
						return;
					}
					String rURL = ArtifactUtil.getArtifactURL(selectedResource);
					if (rURL != null) {
						Window.open(rURL, "_self", null);
					}
				}

			} else if ("U".equals(key)) {
				event.cancel();
				WebStudio.get().getWorkspacePage().getGroupContentsWindow()
						.getArtifactTreeGrid().getClickHandler().doImport();
			}
		}
	}
}
