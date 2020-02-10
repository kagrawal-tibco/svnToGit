package com.tibco.cep.webstudio.client.handler;

import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.tab.events.TabDeselectedEvent;
import com.smartgwt.client.widgets.tab.events.TabDeselectedHandler;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.decisiontable.AbstractTableEditor;
import com.tibco.cep.webstudio.client.decisiontable.DecisionTableEditor;
import com.tibco.cep.webstudio.client.editor.AbstractEditor;
import com.tibco.cep.webstudio.client.editor.RuleTemplateInstanceEditorFactory.RuleTemplateInstanceEditor;
import com.tibco.cep.webstudio.client.process.AbstractProcessEditor;
import com.tibco.cep.webstudio.client.process.ProcessConstants;
import com.tibco.cep.webstudio.client.process.ProcessEditor;
import com.tibco.cep.webstudio.client.widgets.WebStudioEditorPanel;
import com.tibco.cep.webstudio.client.widgets.WebStudioToolbar;

/**
 * 
 * @author sasahoo
 */
public class EditorPaneTabChangeHandler implements TabSelectedHandler, TabDeselectedHandler, ClickHandler {
	
	private WebStudioEditorPanel editorPanel;
	
	boolean isLeftPaneVisible = false; //later separate for Table Analyzer/Palette Panes etc.
	boolean isBottomPaneVisible = false; //later seprate for Properties/Problems Panes
	
	/**
	 * @param editorPanel
	 */
	public EditorPaneTabChangeHandler(WebStudioEditorPanel editorPanel) {
		this.editorPanel = editorPanel;
	}

	/* (non-Javadoc)
	 * @see com.smartgwt.client.widgets.tab.events.TabSelectedHandler#onTabSelected(com.smartgwt.client.widgets.tab.events.TabSelectedEvent)
	 */
	@Override
	public void onTabSelected(TabSelectedEvent event) {
		if (event.getTab() instanceof AbstractEditor) {
			AbstractEditor editor = (AbstractEditor)event.getTab();
			String Id = editor.getSelectedResource().getId();
			// enable save for the selected editor if dirty
			if (editor.isDirty()) {
				WebStudio.get().getApplicationToolBar().enableButton(WebStudioToolbar.TOOL_STRIP_SAVE_ID);
			} else {
				WebStudio.get().getApplicationToolBar().disableButton(WebStudioToolbar.TOOL_STRIP_SAVE_ID, true);
			}
			
			if (editor instanceof RuleTemplateInstanceEditor) {
				updateDependentPanes(true);
				RuleTemplateInstanceEditor rtieditor = (RuleTemplateInstanceEditor) editor;
				updatePropertyPane(rtieditor);
			}
			if (editor instanceof DecisionTableEditor) {
				DecisionTableEditor dteditor = (DecisionTableEditor)editor;
				if (dteditor.isReadOnly()) {
					updateDependentPanes(true);
					updatePropertyPane(dteditor);
				} else {
					updatePropertyPane(dteditor);
					if (WebStudio.get().getEditorPanel().getLeftPane().isVisible() 
							&& WebStudio.get().getEditorPanel().getDecisionTableAnalyzerPane() != null 
							&& WebStudio.get().getEditorPanel().getDecisionTableAnalyzerPane().isVisible()) {
						if (dteditor.getTable() != null) {
							WebStudio.get().getEditorPanel().getDecisionTableAnalyzerPane().refresh(dteditor.getTable());
						}
					}
				}
			}
			if (editor instanceof ProcessEditor) {
				ProcessEditor processEditor = (ProcessEditor) editor;
				if(null!=processEditor){
					if(processEditor.isShowPropertyPane()){
						processEditor.fetchProperties(ProcessConstants.PROCESS_ID,
								ProcessConstants.GENERAL_PROPERTY);
					}
					if(processEditor.isShowPalettePane()){
						WebStudio.get().getEditorPanel().getLeftPane().setVisible(true);
					}
				}
			}else{
				WebStudio.get().getEditorPanel().getLeftPane().setVisible(false);
			}
			WebStudio.get().setCurrentlySelectedArtifact(Id);
		}
		if (event.getTab() == editorPanel.getPropertiesPane()) {
			if (editorPanel.getSelectedTab() instanceof AbstractEditor) {
				AbstractEditor editor = (AbstractEditor)editorPanel.getSelectedTab();
				if (editor instanceof RuleTemplateInstanceEditor) {
					//editorPanel.getPropertiesPane().fillProperties(null);
					RuleTemplateInstanceEditor rtieditor = (RuleTemplateInstanceEditor) editor;
					updatePropertyPane(rtieditor);
				}
				if (editor instanceof DecisionTableEditor) {
					DecisionTableEditor dteditor = (DecisionTableEditor)editor;
					updatePropertyPane(dteditor);
				}if (editor instanceof AbstractProcessEditor) {
					AbstractProcessEditor processEditor = (AbstractProcessEditor)editor;
					updatePropertyPane(processEditor);
				}
			} else {
				editorPanel.getPropertiesPane().fillProperties(null);
			}
		}
		if (event.getTab() == editorPanel.getProblemsPane()) {
			//TODO
		}
	}
	
	private void updatePropertyPane(RuleTemplateInstanceEditor rtiEditor) {
		if (rtiEditor.isShowPropertyPane()  && rtiEditor.getRtiPropertyTab() != null) {
			WebStudio.get().getEditorPanel().getBottomPane().setVisible(true);
			editorPanel.getPropertiesPane().fillProperties(rtiEditor.getRtiPropertyTab());
			WebStudio.get().getEditorPanel().getBottomContainer().selectTab(0);
		} else {
			editorPanel.getPropertiesPane().fillProperties(null);
			WebStudio.get().getEditorPanel().getBottomPane().setVisible(false);
		}
	}
	private void updatePropertyPane(AbstractProcessEditor processEditor) {

		if (processEditor.isShowPropertyPane() && processEditor.getProcessPropertyTab() != null) {
			editorPanel.getPropertiesPane().fillProperties(processEditor.getProcessPropertyTab());
			WebStudio.get().getEditorPanel().getBottomPane().setVisible(true);
		} else {
			editorPanel.getPropertiesPane().fillProperties(null);
			WebStudio.get().getEditorPanel().getBottomPane().setVisible(false);
		}
			
	}

	private void updateDependentPanes(boolean isDecisionTableEditor) {
		if (WebStudio.get().getEditorPanel().getDecisionTableAnalyzerPane() != null) {
			WebStudio.get().getEditorPanel().getDecisionTableAnalyzerPane().clearComponents();
			WebStudio.get().getEditorPanel().getDecisionTableAnalyzerPane().close();
		}
		WebStudio.get().getEditorPanel().getLeftPane().setVisible(false);
		WebStudio.get().getEditorPanel().setShowTableAnalyzer(false);
		if (!isDecisionTableEditor) {
			editorPanel.getPropertiesPane().fillProperties(null);
			WebStudio.get().getEditorPanel().getBottomPane().setVisible(false);
		}
	}
	
	private void updatePropertyPane(AbstractTableEditor dteditor) {
		if (dteditor.getShowProperties() && dteditor.getDecisionTablePropertyTab() != null) {
			editorPanel.getPropertiesPane().fillProperties(dteditor.getDecisionTablePropertyTab());
			WebStudio.get().getEditorPanel().getBottomPane().setVisible(true);
		} else {
			editorPanel.getPropertiesPane().fillProperties(null);
			WebStudio.get().getEditorPanel().getBottomPane().setVisible(false);
		}
	}

	/* (non-Javadoc)
	 * @see com.smartgwt.client.widgets.tab.events.TabDeselectedHandler#onTabDeselected(com.smartgwt.client.widgets.tab.events.TabDeselectedEvent)
	 */
	@Override
	public void onTabDeselected(TabDeselectedEvent event) {
		if (event.getTab() instanceof AbstractEditor) {
			//TODO
		}
	}

	@Override
	public void onClick(ClickEvent event) {
		if (event.getSource() == editorPanel.getMaximizebutton()) {
			if (WebStudio.get().getEditorPanel().getBottomPane().isVisible()) {
				isBottomPaneVisible = true;
				WebStudio.get().getEditorPanel().getBottomPane().setVisible(false);
			}
			if (WebStudio.get().getEditorPanel().getDecisionTableAnalyzerPane() != null 
					&& WebStudio.get().getEditorPanel().getDecisionTableAnalyzerPane().isVisible()) {
				WebStudio.get().getEditorPanel().getDecisionTableAnalyzerPane().setVisible(false);
			}
			
			if (WebStudio.get().getEditorPanel().getLeftPane().isVisible()) {
				isLeftPaneVisible = true;
				WebStudio.get().getEditorPanel().getLeftPane().setVisible(false);
			}
			
			if (WebStudio.get().getWorkspacePage().getGroupsSectionStack().isVisible()) {
				WebStudio.get().getWorkspacePage().getGroupsSectionStack().setVisible(false);
			}
			if (editorPanel.getSelectedTab() instanceof DecisionTableEditor) {
				WebStudio.get().getApplicationToolBar().disableButton(WebStudioToolbar.TOOL_STRIP_VALIDATE_ID, true);
				((DecisionTableEditor)editorPanel.getSelectedTab()).getShowTableAnalyzerViewButton().setDisabled(true);
				((DecisionTableEditor)editorPanel.getSelectedTab()).getShowPropertiesViewButton().setDisabled(true);
			}
			editorPanel.getMaximizebutton().setVisible(false);
			editorPanel.getRestorebutton().setVisible(true);
		} else if (event.getSource() == editorPanel.getRestorebutton()) {
			WebStudio.get().getWorkspacePage().getGroupsSectionStack().setVisible(true);
			if (editorPanel.getSelectedTab() instanceof DecisionTableEditor) {
				if (isBottomPaneVisible) {
					WebStudio.get().getEditorPanel().getBottomPane().setVisible(true);
				}
				if (isLeftPaneVisible) {
					if (WebStudio.get().getEditorPanel().getDecisionTableAnalyzerPane() != null) {
						WebStudio.get().getEditorPanel().getLeftPane().setVisible(true);
						WebStudio.get().getEditorPanel().getDecisionTableAnalyzerPane().setVisible(true);
					}
				}
			}
			if (editorPanel.getSelectedTab() instanceof DecisionTableEditor) {
				WebStudio.get().getApplicationToolBar().disableButton(WebStudioToolbar.TOOL_STRIP_VALIDATE_ID, false);
				((DecisionTableEditor)editorPanel.getSelectedTab()).getShowTableAnalyzerViewButton().setDisabled(false);
				((DecisionTableEditor)editorPanel.getSelectedTab()).getShowPropertiesViewButton().setDisabled(false);
			}
			editorPanel.getMaximizebutton().setVisible(true);
			editorPanel.getRestorebutton().setVisible(false);
		}
	}

}
