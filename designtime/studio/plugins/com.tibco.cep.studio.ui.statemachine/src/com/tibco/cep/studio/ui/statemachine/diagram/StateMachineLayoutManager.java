package com.tibco.cep.studio.ui.statemachine.diagram;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.tibco.cep.diagramming.drawing.DiagramManager;
import com.tibco.cep.diagramming.drawing.LayoutManager;
import com.tomsawyer.drawing.TSGraphTailor;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.command.TSCommand;
import com.tomsawyer.interactive.service.TSEAllOptionsServiceInputData;
import com.tomsawyer.service.layout.TSHierarchicalLayoutInputTailor;
import com.tomsawyer.service.layout.TSLayoutConstants;
import com.tomsawyer.service.layout.TSLayoutInputTailor;
import com.tomsawyer.service.layout.TSSequenceConstraint;

/**
 * 
 * @author ggrigore
 * 
 */
@SuppressWarnings({ "rawtypes","unchecked"})
public class StateMachineLayoutManager extends LayoutManager {

	public StateMachineLayoutManager(DiagramManager diagramManager) {
		super(diagramManager);
		this.layoutInputTailor = new TSLayoutInputTailor(this.inputData);
	}

	// for state machines we want a different orientation so we override this method.
	public void setHierarchicalOptions() {
		super.setHierarchicalOptions();
		TSEGraph graph = (TSEGraph) this.graphManager.getMainDisplayGraph();

		TSHierarchicalLayoutInputTailor hierarchicalInputTailor = 
			new TSHierarchicalLayoutInputTailor(this.inputData);

		hierarchicalInputTailor.setGraph(graph);
		hierarchicalInputTailor.setLevelDirection(TSLayoutConstants.DIRECTION_LEFT_TO_RIGHT);

		// // concurrent child graph stuff
		// hierarchicalLayoutInputTailor.setGraph(this.concurrentGraph);
		// hierarchicalLayoutInputTailor.setAsCurrentLayoutStyle();
		// hierarchicalLayoutInputTailor.setOrthogonalRouting(false);
		// hierarchicalLayoutInputTailor
		// .setLevelDirection(TSLayoutConstants.DIRECTION_TOP_TO_BOTTOM);
		// TSGraphTailor grContailor = this.concurrentGraph.getTailor();
		// grContailor.setMargin(3.0);
		// grContailor.setNestedViewSpacing(3.0);
		//
		// // TSSequenceConstraint sequenceConstraint =
		// // this.layoutInputTailor.addSequenceConstraint(
		// // this.concurrentGraph.nodes(),
		// // TSLayoutConstants.DIRECTION_TOP_TO_BOTTOM,
		// // 1.0 /* spacing */,
		// // 1 /* priority */);
		//
		// TSAlignmentConstraint alignmentConstraint = this.layoutInputTailor
		// .addAlignmentConstraint(this.concurrentGraph.nodes(),
		// TSLayoutConstants.ORIENTATION_VERTICAL,
		// TSLayoutConstants.LEFT_ALIGNED, 1);
	}

	//For region nodes 
	public void configureCompositeRegionOptions(TSENode regionNode) {

		TSEGraph graph = (TSEGraph) regionNode.getChildGraph();
		
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
        graphTailor.setMargin(5.0);
        graphTailor.setNestedViewSpacing(2.0);
        graphTailor.setTopNestedViewSpacing(5.0);
        graphTailor.setLeftNestedViewSpacing(3.0); /// 20
        graphTailor.setRightNestedViewSpacing(3.0);
        graphTailor.setBottomNestedViewSpacing(-15.0);
        
        //To arrange disconnected nodes also in the same manner - left to right
        TSLayoutInputTailor layoutInputTailor = new TSLayoutInputTailor(inputData);        
        layoutInputTailor.addSequenceConstraint(graph.nodes(),
        		TSLayoutConstants.DIRECTION_LEFT_TO_RIGHT,
        		0.0, // spacing
        		1); // priority

        // and now set an alignment constraint:
        layoutInputTailor.addAlignmentConstraint(graph.nodes(),
        		TSLayoutConstants.ORIENTATION_HORIZONTAL,
        		TSLayoutConstants.LEFT_ALIGNED,
        		1);

	}
	
//	public void sequenceConstraintOption(TSENode node) {
//		TSGraph graph = (TSGraph) node.getOwnerGraph();
//		
//		//To arrange disconnected nodes also in the same manner - left to right
//        TSLayoutInputTailor layoutInputTailor = new TSLayoutInputTailor(inputData);        
//        layoutInputTailor.addSequenceConstraint(graph.nodes(), TSLayoutConstants.DIRECTION_LEFT_TO_RIGHT,
//        0.0, // spacing
//        1); // priority
//
//        // and now set an alignment constraint:
//        layoutInputTailor.addAlignmentConstraint(graph.nodes(), TSLayoutConstants.ORIENTATION_HORIZONTAL, TSLayoutConstants.LEFT_ALIGNED,
//        1);
//	}
	
	//For composite/concurrent nodes
	public void configureConcurrentOptions(TSENode parentNode) {
		TSEGraph graph = (TSEGraph) parentNode.getChildGraph();
		
        TSEAllOptionsServiceInputData inputData = this.getInputData();
        TSHierarchicalLayoutInputTailor hierarchicalLayoutInputTailor = new TSHierarchicalLayoutInputTailor(inputData);           
        hierarchicalLayoutInputTailor.setGraph(graph);
        hierarchicalLayoutInputTailor.setAsCurrentLayoutStyle();
        hierarchicalLayoutInputTailor.setOrthogonalRouting(false);
        hierarchicalLayoutInputTailor.setKeepNodeSizes(true);
        hierarchicalLayoutInputTailor.setLevelDirection(TSLayoutConstants.DIRECTION_TOP_TO_BOTTOM);
        hierarchicalLayoutInputTailor.setConstantVerticalSpacingBetweenNodes(5.0);
        hierarchicalLayoutInputTailor.setVariableLevelSpacing(false);

        TSGraphTailor graphTailor = graph.getTailor();
        graphTailor.setMargin(5.0);
        graphTailor.setNestedViewSpacing(2.0);
    	graphTailor.setTopNestedViewSpacing(5.0);
    	graphTailor.setLeftNestedViewSpacing(3.0); /// 20.0
    	graphTailor.setRightNestedViewSpacing(3.0);
    	graphTailor.setBottomNestedViewSpacing(-5.0);
    	
    	/*
    	List constraints = layoutInputTailor.getConstraints();
    	Iterator iter = constraints.iterator();
    	while (iter.hasNext()) {
    		TSLayoutConstraint constraint = (TSLayoutConstraint) iter.next();
    		if (constraint instanceof TSSequenceConstraint) {
    			// TODO: check if we're about to add a constraint for the same graph which already
    			// has such a constraint
    			layoutInputTailor.removeConstraint(constraint);
    		}
    	}
    	*/
    	

        // also add constraint so regions are one under the other and they are left-aligned
        // first we set a sequence constraint:
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
	
	public void setIncrementalOptions() {
		super.setIncrementalOptions();
//		TSEGraph graph = (TSEGraph) this.graphManager.getMainDisplayGraph();
//		TSHierarchicalLayoutInputTailor hierarchicalInputTailor =
//			new TSHierarchicalLayoutInputTailor(this.inputData);
//		hierarchicalInputTailor.setGraph(graph);
//		hierarchicalInputTailor.setLevelDirection(TSLayoutConstants.DIRECTION_LEFT_TO_RIGHT);
	}
	
	List seqConstraints = new LinkedList();
	
   public TSSequenceConstraint addSequenceConstraint(List<?> nodes, int direction) {	
    	TSHierarchicalLayoutInputTailor hierarchicalLayoutInputTailor =
    		new TSHierarchicalLayoutInputTailor(getInputData());
		hierarchicalLayoutInputTailor.setGraph(this.graphManager.getMainDisplayGraph());
		hierarchicalLayoutInputTailor.setAsCurrentLayoutStyle();	
		this.layoutInputTailor = new TSLayoutInputTailor(super.inputData);
		TSSequenceConstraint constraint = this.layoutInputTailor.addSequenceConstraint(nodes, direction, 40.0, 0);
		this.seqConstraints.add(constraint);
    	return constraint;
    }
    
    public void clearSequenceConstraints() {
    	TSHierarchicalLayoutInputTailor hierarchicalLayoutInputTailor =
    		new TSHierarchicalLayoutInputTailor(getInputData());
		hierarchicalLayoutInputTailor.setGraph(this.graphManager.getMainDisplayGraph());
		hierarchicalLayoutInputTailor.setAsCurrentLayoutStyle();	
		this.layoutInputTailor = new TSLayoutInputTailor(super.inputData);
		TSSequenceConstraint constr;
		Iterator<?> iter = this.seqConstraints.iterator();
		while (iter.hasNext()) {
			constr = (TSSequenceConstraint) iter.next();
			this.layoutInputTailor.removeConstraint(constr);
		}
    }
    
    protected void executeLayoutCommand(TSCommand command,boolean layout_on_change){
		if(!layout_on_change){
			super.executeLayoutCommand(command, false);
			return;
		}
	
		command.execute();
	}

}
