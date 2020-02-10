package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.EditorPart;

import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.studio.dashboard.core.insight.model.LocalExternalReference;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateMachineComponent;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.helpers.StateMachineComponentHelper;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.DashboardStateMachineComponentPlugin;
import com.tibco.cep.studio.ui.statemachine.diagram.StateMachineSelectionChangeListener;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;

class StateMachineSelectionTranslator extends StateMachineSelectionChangeListener {

	private static final long serialVersionUID = 8922031737694224331L;

	private StateMachineComponentEditor stateMachineComponentEditor;

	private LocalStateMachineComponent stateMachineComponent;

	public StateMachineSelectionTranslator(ReadOnlyStateMachineDiagramManager manager) {
		super(manager);
		EditorPart editor = manager.getEditor();
		if (editor instanceof ReadOnlyEmbeddedStateMachineEditor){
			EditorPart embeddingEditor = ((ReadOnlyEmbeddedStateMachineEditor) editor).getEmbeddingEditor();
			if (embeddingEditor instanceof StateMachineComponentEditor){
				stateMachineComponentEditor = (StateMachineComponentEditor)embeddingEditor;
				stateMachineComponent = (LocalStateMachineComponent) stateMachineComponentEditor.getLocalElement();
			}
		}
	}

	protected void activateEditor()
	{
		Display.getDefault().asyncExec(new Runnable() {

			public void run()
			{
				stateMachineComponentEditor.getEditorSite().getPage().activate(stateMachineComponentEditor);
			}

		});
	}

	@Override
	protected void setWorkbenchSelection(Object object, IEditorPart editor) {
		activateEditor();
		if (object instanceof TSEEdge) {
			//select null to clear the properties view
			super.setWorkbenchSelection(null, stateMachineComponentEditor);
		}
		else if (object instanceof TSEGraph) {
			if (stateMachineComponent != null){
				//select the state model component to view the general properties
				super.setWorkbenchSelection(stateMachineComponent, stateMachineComponentEditor);
			}
		}
		else if (object instanceof TSENode) {
			if (stateMachineComponent != null){
				TSENode tsNode = (TSENode) object;
				Object userObject = tsNode.getUserObject();
				if (userObject instanceof State){
					State state = (State)userObject;
					try {
						super.setWorkbenchSelection(StateMachineComponentHelper.getStateVisualization(stateMachineComponent, new LocalExternalReference(state)), stateMachineComponentEditor);
					} catch (Exception e) {
						DashboardStateMachineComponentPlugin.getInstance().getLog().log(new Status(IStatus.WARNING,DashboardStateMachineComponentPlugin.PLUGIN_ID,"could not find state visualization for "+state.getName()+" in "+stateMachineComponentEditor.getEditorInput().getName(),e));
					}
				}
			}
		}

	}

}