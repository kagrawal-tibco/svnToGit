package com.tibco.cep.webstudio.client.handler;

import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.events.CloseClickHandler;
import com.smartgwt.client.widgets.tab.events.TabCloseClickEvent;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.editor.AbstractEditor;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.panels.CustomSC;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil;
import com.tibco.cep.webstudio.client.widgets.WebStudioEditorPanel;
import com.tibco.cep.webstudio.client.widgets.WebStudioToolbar;

/**
 * Close handler for Editor Tabs
 * 
 * @author Vikram Patil
 */
public class EditorPaneCloseHandler implements CloseClickHandler {
	private WebStudioEditorPanel containerPanel;
	private static GlobalMessages globalMsgBundle = (GlobalMessages) I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);

	public EditorPaneCloseHandler(WebStudioEditorPanel containerPanel) {
		this.containerPanel = containerPanel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.smartgwt.client.widgets.tab.events.CloseClickHandler#onCloseClick
	 * (com.smartgwt.client.widgets.tab.events.TabCloseClickEvent)
	 */
	@Override
	public void onCloseClick(final TabCloseClickEvent event) {
		// Cancel the event first, for some reason, it runs to completion
		// without waiting for input
		event.cancel();

		final Tab selectedTab = event.getTab();
		if (selectedTab != null) {
			if (selectedTab instanceof AbstractEditor) {
				final NavigatorResource record = (NavigatorResource) WebStudio.get().getWorkspacePage().getGroupContentsWindow()
						.getArtifactTreeGrid().getSelectedRecord();
		
				final AbstractEditor editor = (AbstractEditor) selectedTab;
//				if (editor.isMerge() && !editor.isMergeComplete()) {
//					SC.confirm("Confirm", "Are you sure you want to discard the merge operation?", new BooleanCallback() {
//						public void execute(Boolean value) {
//							if (value) {
//								editor.close();
//								containerPanel.removeTab(selectedTab);
//								toggleToolbarButtons(containerPanel.getTabs().length, record);
//							}
//						}
//					});					
//				} else 
				if (editor.isDirty()) {
					CustomSC.confirm(globalMsgBundle.text_confirm(), globalMsgBundle.message_confirm_discardChanges(), new BooleanCallback() {
						public void execute(Boolean value) {
							if (value) {
								if (editor.isNewArtifact()) {
									WebStudio.get().getWorkspacePage().getGroupContentsWindow().getArtifactTreeGrid()
											.removeRecord(editor.getSelectedResource().getId(), true);
									WebStudio.get().getPortalPage().getGroupContentWindowtFromGroupPortlet().getArtifactTreeGrid()
									.removeRecord(editor.getSelectedResource().getId(), true);
								}
								editor.close();
								containerPanel.removeTab(selectedTab);
								toggleToolbarButtons(containerPanel.getTabs().length, record);
							}
						}
					});
				} else {					
					editor.close();					
					this.containerPanel.removeTab(selectedTab);
					toggleToolbarButtons(this.containerPanel.getTabs().length, record);
				}
							
				// hide Bottom panel on close
				WebStudio.get().getEditorPanel().getBottomPane().setVisible(false);
				
			}
		}
	}
	
	/**
	 * Toggle toolbar button selection based on the artifact type selected
	 * 
	 * @param noOfTabs
	 * @param validArtifactType
	 */
	private void toggleToolbarButtons(int noOfTabs, NavigatorResource record) {
		if (noOfTabs == 0) {
			WebStudio.get().getApplicationToolBar().disableButton(WebStudioToolbar.TOOL_STRIP_SAVE_ID, true);
			WebStudio.get().getApplicationToolBar().disableButton(WebStudioToolbar.TOOL_STRIP_VALIDATE_ID, true);
			
			ProjectExplorerUtil.toggleToolbarOptions(record, WebStudioToolbar.TOOL_STRIP_EXPORT_ID);
		}
	}
}