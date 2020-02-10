package com.tibco.cep.studio.ui.statemachine.validation;

import static com.tibco.cep.studio.ui.overview.OverviewUtils.refreshOverviewAndDiagram;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.getStateGraphPath;

import java.util.HashSet;
import java.util.Set;

import javax.swing.SwingUtilities;

import org.eclipse.core.resources.IResource;

import com.tibco.cep.designtime.core.model.states.StateTransition;
import com.tibco.cep.studio.ui.statemachine.diagram.StateMachineDiagramManager;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEPolylineEdgeUI;

/**
 * This validator only reads the State Molder and refreshes canvas
 * during opening editor from Problem View(problem marker)  and Project Explorer
 * @author sasahoo
 *
 */
public class ReadOnlyStateModelValidator extends StateModelValidator{

	private StateMachineDiagramManager stateMachineDiagramManager;
//	TSEPolylineEdgeUI edgeUI;
	/**
	 * @param stateMachineDiagramManager
	 */
	public ReadOnlyStateModelValidator(StateMachineDiagramManager stateMachineDiagramManager) {
		this.stateMachineDiagramManager = stateMachineDiagramManager;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.validation.DefaultResourceValidator#reportProblem(org.eclipse.core.resources.IResource, java.lang.String, int, int, int, int)
	 */
	protected void reportProblem(IResource resource, String message, int lineNumber, int start, int length, int severity) {
		
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.validation.DefaultResourceValidator#reportProblem(org.eclipse.core.resources.IResource, java.lang.String, int)
	 */
	protected void reportProblem(IResource resource, String message, int severity) {
	
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.validation.DefaultResourceValidator#reportProblem(org.eclipse.core.resources.IResource, java.lang.String, int, java.lang.String)
	 */
	protected void reportProblem(IResource resource, String message, int severity, String type) {
	
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.validation.DefaultResourceValidator#reportProblem(org.eclipse.core.resources.IResource, java.lang.String, java.lang.String, int)
	 */
	protected void reportProblem(IResource resource, String message, String location, int severity) {
	
	}
	
	//For State Modeler in general
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.validation.DefaultResourceValidator#reportProblem(org.eclipse.core.resources.IResource, java.lang.String, java.lang.String, java.lang.String, boolean, int)
	 */
	protected void reportProblem(IResource resource, String message, String location, String stateGraphPath, boolean isTransition, int severity) {
	
	}
	
	//For State Modeler Rules
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.validation.DefaultResourceValidator#reportProblem(org.eclipse.core.resources.IResource, java.lang.String, java.lang.String, java.lang.String, boolean, int, int, int, int)
	 */
	protected void reportProblem(IResource resource, String message, String location, String stateGraphPath,  boolean isTransition, int lineNumber, int start, int length,int severity) {
	
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.validation.DefaultResourceValidator#deleteMarkers(org.eclipse.core.resources.IResource)
	 */
	protected void deleteMarkers(IResource resource) {
	
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.validation.StateMachineResourceValidator#stateModelDiagramUIRefresh()
	 */
	protected void stateModelDiagramUIRefresh(){
		// State Model Diagram UI Transition Change
		if (stateMachineDiagramManager.getGraphManager() == null) {
			return;
		}
		final Set<TSEEdge> edgeSet = new HashSet<TSEEdge>(); // Existing Diagram Graph Set
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				traverseStateModelDiagram((TSEGraph) stateMachineDiagramManager.getGraphManager().getMainDisplayGraph(), edgeSet);
				for (TSEEdge edge : edgeSet) {
					StateTransition stateTransition = (StateTransition) edge.getUserObject();
					String path = getStateGraphPath(stateTransition);
					final TSEPolylineEdgeUI edgeUI = (TSEPolylineEdgeUI) edge.getEdgeUI();
					if (stateGraphPathErrorSet.contains(path)) {
						if (!edgeUI.getLineColor().toString().equals("255 0 0")) {
							edgeUI.setLineColor(new TSEColor(255, 0, 0));
							edgeUI.setLineWidth(edgeUI.getDefaultLineWidth() + 1);
							edgeUI.setAntiAliasingEnabled(true);
						}
					} else {
						if (edgeUI.getLineColor().toString().equals("255 0 0")) {
							edgeUI.setLineColor(edgeUI.getDefaultLineColor());
							edgeUI.setLineWidth(edgeUI.getDefaultLineWidth());
							edgeUI.setAntiAliasingEnabled(true);
						}
						// For changing the Transition UI for having valid Rule Condition/Action
						if(stateTransition != null){
							if (stateTransition.getGuardRule() != null) {
								String cond = stateTransition.getGuardRule().getConditionText();
								String action = stateTransition.getGuardRule().getActionText();
								if ((cond != null && !cond.trim().equalsIgnoreCase(""))
										|| (action != null && !action.trim().equalsIgnoreCase(""))) {
									if (edgeUI.getLineWidth() != edgeUI.getDefaultLineWidth() + 1) {
										edgeUI.setLineWidth(edgeUI.getDefaultLineWidth() + 1);
										//edgeUI.setAntiAliasingEnabled(true);
									}
								} else {
									edgeUI.setLineWidth(edgeUI.getDefaultLineWidth());
									edgeUI.setAntiAliasingEnabled(true);
								}
							}
						}
					}
				}
			}
		});
		// Refresh Overview and diagram after validation
		refreshOverviewAndDiagram(stateMachineDiagramManager.getEditor().getEditorSite(), true, true,
				stateMachineDiagramManager, true);
	}
	
	/**
	 * @param graph
	 * @param edgeSet
	 */
	@SuppressWarnings("unchecked")
	private void traverseStateModelDiagram(TSEGraph graph, Set<TSEEdge> edgeSet){
		edgeSet.addAll(graph.edges());
		for (Object obj : graph.nodes()) {
			TSENode node = (TSENode) obj;
			if (node.getChildGraph() != null) {
				traverseStateModelDiagram((TSEGraph) node.getChildGraph(), edgeSet);
			}
		}
	}
	
}
