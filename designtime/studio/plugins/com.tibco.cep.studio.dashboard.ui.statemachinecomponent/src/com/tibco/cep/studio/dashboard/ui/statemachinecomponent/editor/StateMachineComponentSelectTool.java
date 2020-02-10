package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor;

import java.awt.Point;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JPopupMenu;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.part.EditorPart;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateEnd;
import com.tibco.cep.designtime.core.model.states.StateStart;
import com.tibco.cep.diagramming.tool.popup.ContextMenuController;
import com.tibco.cep.studio.dashboard.core.insight.model.LocalExternalReference;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateMachineComponent;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateSeriesConfig;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateVisualization;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.helpers.StateMachineComponentHelper;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.DashboardStateMachineComponentPlugin;
import com.tibco.cep.studio.ui.statemachine.diagram.StateMachineDiagramManager;
import com.tibco.cep.studio.ui.statemachine.diagram.tool.StateMachineSelectTool;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSEObject;

public class StateMachineComponentSelectTool extends StateMachineSelectTool {

	private StateMachineComponentEditor embeddingStateMachineComponentEditor;

	private ReadOnlyEmbeddedStateMachineEditor embeddedStateMachineEditor;

	private LocalStateMachineComponent stateMachineComponent;

	public StateMachineComponentSelectTool(EditorPart editor) {
		super(null);
		if (editor instanceof ReadOnlyEmbeddedStateMachineEditor) {
			embeddedStateMachineEditor = (ReadOnlyEmbeddedStateMachineEditor) editor;
			stateMachineDiagramManager = (StateMachineDiagramManager) embeddedStateMachineEditor.getStateMachineDiagramManager();
			EditorPart embeddingEditor = embeddedStateMachineEditor.getEmbeddingEditor();
			if (embeddingEditor instanceof StateMachineComponentEditor) {
				embeddingStateMachineComponentEditor = (StateMachineComponentEditor) embeddingEditor;
				stateMachineComponent = (LocalStateMachineComponent) embeddingStateMachineComponentEditor.getLocalElement();
			}
		}
	}

	@Override
	protected void setNodeTooltip(TSENode node) {
		Object userObject = node.getUserObject();
		if (userObject == null){
			return;
		}
		if (userObject instanceof StateStart) {
			return;
		}
		if (userObject instanceof StateEnd) {
			return;
		}
		if (userObject instanceof State) {
			State state = (State) userObject;
			StringBuilder tooltip = new StringBuilder();
			if(state.getName() !=null){
				tooltip.append("<p><b>Name: </b>");
				tooltip.append(state.getName());
				tooltip.append("</p>");
			}
			String indicator = "Unknown";
			String content = "Unknown";
			try {
				LocalStateVisualization stateVisualization = (LocalStateVisualization) StateMachineComponentHelper.getStateVisualization(stateMachineComponent, new LocalExternalReference(state));
				if (stateVisualization != null){
					List<LocalElement> stateSeriesConfigs = stateVisualization.getChildren("StateSeriesConfig");
					indicator = "No";
					content = "No";
					for (LocalElement localElement : stateSeriesConfigs) {
						LocalStateSeriesConfig seriesConfig = (LocalStateSeriesConfig) localElement;
						if (StateMachineComponentHelper.isIndicatorSeriesConfig(seriesConfig) == true){
							indicator = "Yes";
						}
						else if (StateMachineComponentHelper.isProgressBarContentSeriesConfig(seriesConfig) == true){
							content = "ProgressBar";
						}
						else if (StateMachineComponentHelper.isTextContentSeriesConfig(seriesConfig) == true){
							content = "Text";
						}
					}

				}
			} catch (Exception e) {
				String message = "could not set tooltip on " + state.getFullPath() + " in " + embeddingStateMachineComponentEditor.getEditorInput().getName();
				DashboardStateMachineComponentPlugin.getInstance().getLog().log(new Status(IStatus.WARNING, DashboardStateMachineComponentPlugin.PLUGIN_ID, message, e));
			}
			tooltip.append("<p><b>Indicator: </b>");
			tooltip.append(indicator);
			tooltip.append("</p>");	
			tooltip.append("<p><b>Content: </b>");
			tooltip.append(content);
			tooltip.append("</p>");		
			node.setTooltipText(tooltip.toString());
		}
	}

	@Override
	protected void setEdgeTooltip(TSEEdge edge) {

	}

	@Override
	protected ContextMenuController getPopupMenuController() {
		if (popupMenuController == null) {
			popupMenuController = new StateMachineComponentContextMenuController();
		}
		return popupMenuController;
	}
	
	@Override
	public void showPopup(String menuName, Point point) {
		TSEObject actualHitObject = hitObject != null ? hitObject : hitGraph;
		try {
			LocalElement targetElement = null;
			if (actualHitObject instanceof TSEGraph) {
				// FIXME handle composite state graph within graphs
				targetElement = stateMachineComponent;
			} else if (actualHitObject instanceof TSENode) {
				targetElement = StateMachineComponentHelper.getStateVisualization(stateMachineComponent, new LocalExternalReference((Entity) this.hitObject.getUserObject()));
			}
			((StateMachineComponentContextMenuController)popupMenuController).showMenu(this.getSwingCanvas(),point,menuName,targetElement);
			setActiveMenu(menuName);
		} catch (Exception e) {
			String hitObjectFullPath = ((Entity) this.hitObject.getUserObject()).getFullPath();
			String message = "could not set state on popup menu for " + hitObjectFullPath + " in " + embeddingStateMachineComponentEditor.getEditorInput().getName();
			DashboardStateMachineComponentPlugin.getInstance().getLog().log(new Status(IStatus.WARNING, DashboardStateMachineComponentPlugin.PLUGIN_ID, message, e));
		}		
		
	}
	
	@Override
	public void setPopupState(JPopupMenu popup) {
		//do nothing
	}

	@Override
	public void setMenuState(JMenu menu) {
		// do nothing
	}	

	@Override
	public IWorkbenchPage getPage() {
		return stateMachineDiagramManager.getEditor().getSite().getPage();
	}

}