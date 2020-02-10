package com.tibco.cep.bpmn.ui.editor;

import javax.swing.SwingUtilities;

import com.tibco.cep.diagramming.drawing.BaseDiagramManager;
import com.tibco.cep.diagramming.drawing.EntityLayoutManager;
import com.tomsawyer.drawing.TSGraphTailor;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.command.TSCommand;
import com.tomsawyer.interactive.service.TSEAllOptionsServiceInputData;
import com.tomsawyer.service.layout.TSHierarchicalLayoutInputTailor;
import com.tomsawyer.service.layout.TSLayoutConstants;
import com.tomsawyer.service.layout.TSLayoutInputTailor;

/**
 * @author pdhar
 *
 */
public class BpmnLayoutManager extends EntityLayoutManager {
	
	public static final int MARGIN = 1;
	
	/**
	 * @param diagramManager
	 */
	public BpmnLayoutManager(BaseDiagramManager diagramManager) {
		super(diagramManager);
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				layoutInputTailor = new TSLayoutInputTailor(inputData);
			}
		});
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.EntityLayoutManager#setHierarchicalOptions()
	 */
	public void setHierarchicalOptions() {
		super.setHierarchicalOptions();
		TSEGraph graph = (TSEGraph) this.graphManager.getMainDisplayGraph();

		TSHierarchicalLayoutInputTailor hierarchicalInputTailor = 
			new TSHierarchicalLayoutInputTailor(this.inputData);

		hierarchicalInputTailor.setGraph(graph);
		hierarchicalInputTailor.setLevelDirection(TSLayoutConstants.DIRECTION_LEFT_TO_RIGHT);
		// TODO: should be preference!
		hierarchicalInputTailor.setKeepNodeSizes(true);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.LayoutManager#setIncrementalOptions()
	 */
	public void setIncrementalOptions() {
		super.setIncrementalOptions();

		TSEGraph graph = (TSEGraph) this.graphManager.getMainDisplayGraph();
		TSHierarchicalLayoutInputTailor hierarchicalInputTailor = new TSHierarchicalLayoutInputTailor(
				this.inputData);

		hierarchicalInputTailor.setGraph(graph);
		hierarchicalInputTailor.setLevelDirection(TSLayoutConstants.DIRECTION_LEFT_TO_RIGHT);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.LayoutManager#setPerCallGlobalLayoutOptions()
	 */
	protected void setPerCallGlobalLayoutOptions() {
		TSHierarchicalLayoutInputTailor hierarchicalLayoutInputTailor =
			new TSHierarchicalLayoutInputTailor(this.inputData);		
		hierarchicalLayoutInputTailor.setGraph(this.graphManager.getMainDisplayGraph());
		
		if (this.graphManager.getMainDisplayGraph().numberOfNodes() > 1200) {
			hierarchicalLayoutInputTailor.setOrthogonalRouting(false);
//			BpmnUIPlugin.debug("Turning off orthogonal edge routing due to large tree size.");
		}
		else {
			hierarchicalLayoutInputTailor.setOrthogonalRouting(true);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.LayoutManager#setPerCallIncrementalLayoutOptions()
	 */
	protected void setPerCallIncrementalLayoutOptions() {
		TSHierarchicalLayoutInputTailor hierarchicalLayoutInputTailor =
			new TSHierarchicalLayoutInputTailor(this.inputData);		
		hierarchicalLayoutInputTailor.setGraph(this.graphManager.getMainDisplayGraph());

		if (this.graphManager.getMainDisplayGraph().numberOfNodes() > 1200) {
			hierarchicalLayoutInputTailor.setOrthogonalRouting(false);
		}
		else {
			hierarchicalLayoutInputTailor.setOrthogonalRouting(true);
		}
	}
	
	/**
	 * @param poolNode
	 */
	public void setLayoutOptionsForPool(TSENode poolNode) {
		TSEGraph graph = (TSEGraph) poolNode.getChildGraph();
		TSEAllOptionsServiceInputData inputData = this.getInputData();
		TSHierarchicalLayoutInputTailor hierarchicalLayoutInputTailor = new TSHierarchicalLayoutInputTailor(inputData);		
		hierarchicalLayoutInputTailor.setGraph(graph);
		hierarchicalLayoutInputTailor.setAsCurrentLayoutStyle();
		hierarchicalLayoutInputTailor.setOrthogonalRouting(false);
		hierarchicalLayoutInputTailor.setKeepNodeSizes(true);
		hierarchicalLayoutInputTailor.setLevelDirection(TSLayoutConstants.DIRECTION_LEFT_TO_RIGHT);
		hierarchicalLayoutInputTailor.setConstantVerticalSpacingBetweenNodes(4.0);
		hierarchicalLayoutInputTailor.setVariableLevelSpacing(false);

		TSGraphTailor graphTailor = graph.getTailor();
//		graphTailor.setMargin(5.0);
        graphTailor.setNestedViewSpacing(2.0);
        graphTailor.setTopNestedViewSpacing(MARGIN);
        graphTailor.setLeftNestedViewSpacing(20.0); /// 20.0
        graphTailor.setRightNestedViewSpacing(MARGIN);
        graphTailor.setBottomNestedViewSpacing(MARGIN);
        
		// also add constraint so regions are one under the other and they are left-aligned

		// first we set a sequence constraint:
		// NOTE: because iterator gives us order of nodes in opposite
		// order, we set the direction bottom to top instead of top to bottom
		TSLayoutInputTailor layoutInputTailor = new TSLayoutInputTailor(inputData);        
		layoutInputTailor.addSequenceConstraint(graph.nodes(), 
				TSLayoutConstants.DIRECTION_TOP_TO_BOTTOM,
				0.0, // spacing
				1); // priority

		// and now set an alignment constraint:
		layoutInputTailor.addAlignmentConstraint(graph.nodes(), 
				TSLayoutConstants.ORIENTATION_VERTICAL,
				TSLayoutConstants.LEFT_ALIGNED,
				1);
	}	
	
	/**
	 * @param laneNode
	 */
	public void setLayoutOptionsForLane(TSENode laneNode) {
		TSEGraph graph = (TSEGraph) laneNode.getChildGraph();
		TSEAllOptionsServiceInputData inputData = this.getInputData();
		TSHierarchicalLayoutInputTailor hierarchicalLayoutInputTailor = new TSHierarchicalLayoutInputTailor(inputData);		
		hierarchicalLayoutInputTailor.setGraph(graph);
		hierarchicalLayoutInputTailor.setAsCurrentLayoutStyle();
		hierarchicalLayoutInputTailor.setOrthogonalRouting(true);
		hierarchicalLayoutInputTailor.setKeepNodeSizes(true);
		hierarchicalLayoutInputTailor.setLevelDirection(TSLayoutConstants.DIRECTION_LEFT_TO_RIGHT);
		hierarchicalLayoutInputTailor.setConstantVerticalSpacingBetweenNodes(4.0);
		hierarchicalLayoutInputTailor.setVariableLevelSpacing(false);

		TSGraphTailor graphTailor = graph.getTailor();
		graphTailor.setMargin(MARGIN*2);
        graphTailor.setNestedViewSpacing(2.0);
        graphTailor.setTopNestedViewSpacing(MARGIN);
        graphTailor.setLeftNestedViewSpacing(20.0);
        graphTailor.setRightNestedViewSpacing(MARGIN+4);
        graphTailor.setBottomNestedViewSpacing(MARGIN);
	}		
	
	/**
	 * @param graph
	 */
	public void setLayoutOptionsForSubProcess(TSEGraph graph) {
		TSEAllOptionsServiceInputData inputData = this.getInputData();
		TSHierarchicalLayoutInputTailor hierarchicalLayoutInputTailor = new TSHierarchicalLayoutInputTailor(inputData);		
		hierarchicalLayoutInputTailor.setGraph(graph);
		hierarchicalLayoutInputTailor.setAsCurrentLayoutStyle();
		hierarchicalLayoutInputTailor.setOrthogonalRouting(true);
		hierarchicalLayoutInputTailor.setKeepNodeSizes(true);
		hierarchicalLayoutInputTailor.setLevelDirection(TSLayoutConstants.DIRECTION_LEFT_TO_RIGHT);		
		
		TSGraphTailor graphTailor = graph.getTailor();
		//		graphTailor.setMargin(5.0);
        graphTailor.setNestedViewSpacing(5.0);
//        graphTailor.setTopNestedViewSpacing(MARGIN);
//        graphTailor.setLeftNestedViewSpacing(20.0);
//        graphTailor.setRightNestedViewSpacing(MARGIN);
        graphTailor.setBottomNestedViewSpacing(10);
		
	}
	
	protected void executeLayoutCommand(TSCommand command,boolean layout_on_change){
		if(!layout_on_change){
			super.executeLayoutCommand(command, false);
			return;
		}
	
		command.execute();
	}

}
