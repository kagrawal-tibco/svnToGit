package com.tibco.cep.diagramming.drawing;

import java.util.List;

import com.tomsawyer.drawing.TSDGraph;
import com.tomsawyer.graphicaldrawing.TSEConnector;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.service.layout.TSAlignmentConstraint;
import com.tomsawyer.service.layout.TSClosedGroupConstraint;
import com.tomsawyer.service.layout.TSGeneralLayoutInputTailor;
import com.tomsawyer.service.layout.TSHierarchicalLayoutInputTailor;
import com.tomsawyer.service.layout.TSLayoutConstants;
import com.tomsawyer.service.layout.TSLayoutConstraint;
import com.tomsawyer.service.layout.TSLayoutInputTailor;
import com.tomsawyer.service.layout.TSOrthogonalLayoutInputTailor;
import com.tomsawyer.service.layout.TSSeparationConstraint;
import com.tomsawyer.service.layout.TSSequenceConstraint;

/**
 * 
 * @author ggrigore
 *
 */
public class EntityLayoutManager extends LayoutManager {

	private int levelDirection = TSLayoutConstants.DIRECTION_BOTTOM_TO_TOP;
	
	public EntityLayoutManager(BaseDiagramManager diagramManager) {
		super(diagramManager);
	}
	
	// for concept diagrams we want a different orientation so we override 
	// this method.
    public void setHierarchicalOptions() {
    	this.setHierarchicalOptions(this.graphManager.getMainDisplayGraph());
    }
    
    public void setHierarchicalOptions(TSDGraph graph) {
    	super.setHierarchicalOptions();
    	
        TSHierarchicalLayoutInputTailor hierarchicalInputTailor =
            new TSHierarchicalLayoutInputTailor(this.inputData);

        hierarchicalInputTailor.setGraph(graph);
        hierarchicalInputTailor.setOrthogonalRouting(true);
        hierarchicalInputTailor.setUndirected(false);
        hierarchicalInputTailor.setLevelDirection(getLevelDirection());
        // we should set aspect ratio to be that of the window
        // TSLayoutInputTailor layoutTailor = new TSLayoutInputTailor(this.inputData);
        // layoutTailor.setAspectRatio(1.0);
        hierarchicalInputTailor.setEdgeSpacing(10.0);
        hierarchicalInputTailor.setVerticalSpacingBetweenEdges(10.0);
    }
    
    public void setOrthogonalOptions() {
    	super.setOrthogonalOptions();
    	// overwrite keep node sizes (TODO):
    }

    public void setMainGraphHierarchicalOptions(TSDGraph graph) {
        TSHierarchicalLayoutInputTailor hierarchicalInputTailor =
            new TSHierarchicalLayoutInputTailor(this.inputData);
        hierarchicalInputTailor.setGraph(graph);
        hierarchicalInputTailor.setOrthogonalRouting(true);
        hierarchicalInputTailor.setLevelDirection(
        	TSLayoutConstants.DIRECTION_TOP_TO_BOTTOM);
    }

    public void setMainGraphOrthogonalOptions(TSDGraph graph) {
    	TSOrthogonalLayoutInputTailor hierarchicalInputTailor =
    		new TSOrthogonalLayoutInputTailor(this.inputData);
    	hierarchicalInputTailor.setGraph(graph);
    	hierarchicalInputTailor.setAsCurrentLayoutStyle();
    	hierarchicalInputTailor.setKeepNodeSizes(true);    
    }  
    
    public void setNoOrientationHierarchicalOptions(TSDGraph graph) {
        TSHierarchicalLayoutInputTailor hierarchicalInputTailor =
            new TSHierarchicalLayoutInputTailor(this.inputData);

        hierarchicalInputTailor.setGraph(graph);
        hierarchicalInputTailor.setOrthogonalRouting(true);
    }
    
    public void setAttachmentSide(TSENode node, int attachmentSide) {
        TSHierarchicalLayoutInputTailor hierarchicalInputTailor =
            new TSHierarchicalLayoutInputTailor(this.inputData);
        hierarchicalInputTailor.setGraph((TSDGraph)node.getOwnerGraph());
        hierarchicalInputTailor.setAttachmentSide(node, attachmentSide);
    }
    
    
    public TSClosedGroupConstraint addGroupConstraint(List<?> nodes) {
    	
    	TSHierarchicalLayoutInputTailor hierarchicalLayoutInputTailor =
    		new TSHierarchicalLayoutInputTailor(getInputData());

		hierarchicalLayoutInputTailor.setGraph(this.graphManager.getMainDisplayGraph());
		hierarchicalLayoutInputTailor.setAsCurrentLayoutStyle();
		
		this.layoutInputTailor = new TSLayoutInputTailor(super.inputData);
    	//FIXME 3.0.2 merge: look for the new method signature
		return this.layoutInputTailor.addClosedGroupConstraint(nodes, 1);
    }
    
    public TSAlignmentConstraint addAlignmentConstraint(List<?> nodes, int orientation) {
    	
    	TSHierarchicalLayoutInputTailor hierarchicalLayoutInputTailor =
    		new TSHierarchicalLayoutInputTailor(getInputData());
		hierarchicalLayoutInputTailor.setGraph(this.graphManager.getMainDisplayGraph());
		hierarchicalLayoutInputTailor.setAsCurrentLayoutStyle();		
		this.layoutInputTailor = new TSLayoutInputTailor(super.inputData);
    	return this.layoutInputTailor.addAlignmentConstraint(nodes,
    		orientation,
    		TSLayoutConstants.CENTER_ALIGNED,
    		0);
    }

    public TSAlignmentConstraint addAlignmentConstraintTopAligned(List<?> nodes, int orientation) {
    	
    	TSHierarchicalLayoutInputTailor hierarchicalLayoutInputTailor =
    		new TSHierarchicalLayoutInputTailor(getInputData());
		hierarchicalLayoutInputTailor.setGraph(this.graphManager.getMainDisplayGraph());
		hierarchicalLayoutInputTailor.setAsCurrentLayoutStyle();		
		this.layoutInputTailor = new TSLayoutInputTailor(super.inputData);
    	return this.layoutInputTailor.addAlignmentConstraint(nodes,
    		orientation,
    		TSLayoutConstants.TOP_ALIGNED,
    		0);
    }
    
    public TSSequenceConstraint addSequenceConstraint(List<?> nodes, int direction) {
    	
    	TSHierarchicalLayoutInputTailor hierarchicalLayoutInputTailor =
    		new TSHierarchicalLayoutInputTailor(getInputData());
		hierarchicalLayoutInputTailor.setGraph(this.graphManager.getMainDisplayGraph());
		hierarchicalLayoutInputTailor.setAsCurrentLayoutStyle();	
		this.layoutInputTailor = new TSLayoutInputTailor(super.inputData);
    	return this.layoutInputTailor.addSequenceConstraint(nodes, direction, 40.0, 0);
    }

    public TSSeparationConstraint addSeparationConstraint(List<?> first, List<?> second, int direction) {
    	
    	TSHierarchicalLayoutInputTailor hierarchicalLayoutInputTailor =
    		new TSHierarchicalLayoutInputTailor(getInputData());
		hierarchicalLayoutInputTailor.setGraph(this.graphManager.getMainDisplayGraph());
		hierarchicalLayoutInputTailor.setAsCurrentLayoutStyle();	
		this.layoutInputTailor = new TSLayoutInputTailor(super.inputData);
    	return this.layoutInputTailor.addSeparationConstraint(first, second, direction, 40.0, 0);
    }
    
    public void removeConstraint(TSLayoutConstraint constraint) {
    	TSHierarchicalLayoutInputTailor hierarchicalLayoutInputTailor =
    		new TSHierarchicalLayoutInputTailor(getInputData());
		hierarchicalLayoutInputTailor.setGraph(this.graphManager.getMainDisplayGraph());
		hierarchicalLayoutInputTailor.setAsCurrentLayoutStyle();	
		this.layoutInputTailor = new TSLayoutInputTailor(super.inputData);
		this.layoutInputTailor.removeConstraint(constraint);
    }
    
    public void setEdgeAttachmentSides(TSEEdge edge, int srcSide, int tgtSide) {
    	TSHierarchicalLayoutInputTailor hierarchicalLayoutInputTailor =
    		new TSHierarchicalLayoutInputTailor(getInputData());
		hierarchicalLayoutInputTailor.setGraph(this.graphManager.getMainDisplayGraph());
		hierarchicalLayoutInputTailor.setAsCurrentLayoutStyle();	
		this.layoutInputTailor = new TSLayoutInputTailor(super.inputData);
		hierarchicalLayoutInputTailor.setSourceAttachmentSide(edge, srcSide);
		hierarchicalLayoutInputTailor.setTargetAttachmentSide(edge, tgtSide);
    }
    
    @SuppressWarnings("unchecked")
	public void setMovableConnectors(TSENode node, boolean movable) {
    	TSGeneralLayoutInputTailor genInputTailor =
    		new TSGeneralLayoutInputTailor(this.inputData);
    	List<TSEConnector> connectorList = node.connectors();
    	for (TSEConnector connector : connectorList) {
        	genInputTailor.setMovable(connector, movable);
    	}
    }
	
	public int getLevelDirection() {
		return levelDirection;
	}

	public void setLevelDirection(int levelDirection) {
		this.levelDirection = levelDirection;
	}

}
