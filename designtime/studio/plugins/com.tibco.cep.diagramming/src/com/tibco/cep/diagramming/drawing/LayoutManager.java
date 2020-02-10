package com.tibco.cep.diagramming.drawing;

import javax.swing.SwingUtilities;

import com.tibco.cep.diagramming.DiagrammingPlugin;
import com.tibco.cep.diagramming.utils.DiagramConstants;
import com.tomsawyer.graphicaldrawing.TSEEdgeLabel;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSENodeLabel;
import com.tomsawyer.interactive.command.TSCommand;
import com.tomsawyer.interactive.command.TSGroupCommand;
import com.tomsawyer.interactive.command.editing.TSEGlobalLayoutCommand;
import com.tomsawyer.interactive.command.editing.TSEIncrementalLayoutCommand;
import com.tomsawyer.interactive.command.editing.TSELabelingCommand;
import com.tomsawyer.interactive.command.editing.TSERoutingCommand;
import com.tomsawyer.interactive.service.TSEAllOptionsServiceInputData;
import com.tomsawyer.service.TSServiceException;
import com.tomsawyer.service.TSServiceInputDataInterface;
import com.tomsawyer.service.TSServiceOutputData;
import com.tomsawyer.service.client.TSServiceProxy;
import com.tomsawyer.service.layout.TSCircularLayoutInputTailor;
import com.tomsawyer.service.layout.TSGeneralLayoutInputTailor;
import com.tomsawyer.service.layout.TSHierarchicalLayoutInputTailor;
import com.tomsawyer.service.layout.TSLabelingInputTailor;
import com.tomsawyer.service.layout.TSLayoutConstants;
import com.tomsawyer.service.layout.TSLayoutInputTailor;
import com.tomsawyer.service.layout.TSOrthogonalLayoutInputTailor;
import com.tomsawyer.service.layout.TSRoutingInputTailor;
import com.tomsawyer.service.layout.TSSymmetricLayoutInputTailor;
import com.tomsawyer.service.layout.client.TSApplyLayoutResults;
import com.tomsawyer.service.layout.client.TSLayoutProxy;

/**
 * 
 * @author ggrigore, hitesh
 * 
 */
public class LayoutManager {

	protected TSEGraphManager graphManager;
	protected TSEAllOptionsServiceInputData inputData;
//	protected TSServiceInputDataInterface inputData;
	protected TSLayoutInputTailor layoutInputTailor;	
	protected TSServiceProxy layoutProxy;
	protected DrawingCanvas drawingCanvas;
	protected BaseDiagramManager diagramManager;

	public LayoutManager(BaseDiagramManager diagramManager) {
		this.diagramManager = diagramManager;
		this.drawingCanvas = diagramManager.getDrawingCanvas();
		this.graphManager = diagramManager.getGraphManager();
		this.initLayout();
	}
	
	public void setServiceInputData(TSServiceInputDataInterface inputData) {
		if (inputData instanceof TSEAllOptionsServiceInputData) {
			this.inputData = (TSEAllOptionsServiceInputData) inputData;
		}
		else {
			System.err.println("Wrong type for servince input data.");
		}
	}

	/**
	 * This method initializes layout.
	 */
	public void initLayout() {
		this.layoutProxy = new TSLayoutProxy();
		this.inputData = new TSEAllOptionsServiceInputData(this.graphManager);
		//this.layoutInputTailor = new TSLayoutInputTailor(this.inputData);

		/*
		TSLayoutInputTailor tailor = new TSLayoutInputTailor(this.inputData);
		tailor.setGraphManager(this.graphManager);
		tailor.setAsCurrentOperation();
		*/

		this.setHierarchicalOptions();
	}

	protected void setPerCallGlobalLayoutOptions() { }
	protected void setPerCallIncrementalLayoutOptions() { }
	
	/**
	 * This method calls global layout (not incremental) using commands so that
	 * it can be undone.
	 */
	public void callInteractiveGlobalLayout() {
		try{
		this.setPerCallGlobalLayoutOptions();
		TSEGlobalLayoutCommand layoutCommand = new TSEGlobalLayoutCommand(this.drawingCanvas, this.inputData);
		TSGroupCommand command = new TSGroupCommand();
		command.add(layoutCommand);
		executeLayoutCommand(command,diagramManager.layout_On_Diagram_Change);
		diagramManager.layout_On_Diagram_Change=false;
		}catch(TSServiceException exception){
			DiagrammingPlugin.log("Global Layout failed. Error code: {0}", exception.getErrorCode());
		}
		// TODO: enable certain menu items?
	}

	/**
	 * This method calls routing (not layout) using commands so it can be
	 * undone.
	 */
	public void callInteractiveRouting() {
		try{
			TSERoutingCommand routingCommand = new TSERoutingCommand(this.drawingCanvas, this.inputData);
			TSGroupCommand command = new TSGroupCommand();
			command.add(routingCommand);
			this.drawingCanvas.getCommandManager().transmit(command);	
		}catch(TSServiceException exception){
			DiagrammingPlugin.log("Routing failed. Error code: (0)", exception.getErrorCode());
		}
		// TODO: enable certain menu items?
	}

	/**
	 * This method calls incremental layout (not global) using commands so that
	 * it can be undone.
	 */
	public void callInteractiveIncrementalLayout() {
		try{
			this.setPerCallIncrementalLayoutOptions();
			TSEIncrementalLayoutCommand layoutCommand = new TSEIncrementalLayoutCommand(this.drawingCanvas, this.inputData);
			TSGroupCommand command = new TSGroupCommand();
			command.add(layoutCommand);
			executeLayoutCommand(command,diagramManager.layout_On_Diagram_Change);
			diagramManager.layout_On_Diagram_Change=false;
		}catch(TSServiceException exception){
			//hitesh - temporary - to track TSServiceException
//			try {
//				TSEVisualizationXMLWriter writer;
//				writer = new TSEVisualizationXMLWriter("C:\\layoutCrash.tsv");
//				writer.setGraphManager(this.drawingCanvas.getGraphManager());
//				writer.setServiceInputData(this.inputData);
//				writer.write();
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			DiagrammingPlugin.log("Incremental Layout failed. Error code: {0}", exception.getErrorCode());
		}
		// TODO: enable certain menu items?
	}

	public void callInteractiveLabeling() {
		try {
			TSELabelingCommand labelingCommand = new TSELabelingCommand(this.drawingCanvas, this.inputData);
			TSGroupCommand command = new TSGroupCommand();
			command.add(labelingCommand);
			this.drawingCanvas.getCommandManager().transmit(command);
		} catch (TSServiceException exception) {
			DiagrammingPlugin.log("Labeling failed. Error code: {0}", exception.getErrorCode());
		}
	}
	
	
	public void callBatchLabeling() {
		try {
			TSELabelingCommand labelingCommand = new TSELabelingCommand(this.drawingCanvas, this.inputData);
			TSGroupCommand command = new TSGroupCommand();
			command.add(labelingCommand);
			this.drawingCanvas.getCommandManager().transmit(command);
		} catch (TSServiceException exception) {
			DiagrammingPlugin.log("Labeling failed. Error code: {0} ", exception.getErrorCode());
		}
		
		try {
			callLayoutService();
		} catch (TSServiceException exception) {
			exception.printStackTrace();
		}		
	}

	/**
	 * This method invokes a batch layout on the drawing. It cannot be undone.
	 */
	public void callBatchGlobalLayout() {
		this.setPerCallGlobalLayoutOptions();
		try {
			TSLayoutInputTailor layoutInputTailor = new TSLayoutInputTailor(this.inputData);
			layoutInputTailor.setIncrementalLayout(false);
			callLayoutService();
		} catch (TSServiceException exception) {
			exception.printStackTrace();
		}
	}
	
	/**
	 * This method invokes a batch layout on the drawing. It cannot be undone.
	 */
	public void callBatchIncrementalLayout() {
		this.setPerCallIncrementalLayoutOptions();
		try {
			TSLayoutInputTailor hierarchicalInputTailor = new TSLayoutInputTailor(this.inputData);
			hierarchicalInputTailor.setIncrementalLayout(true);
			callLayoutService();
		} catch (TSServiceException exception) {
			DiagrammingPlugin.log("Batch Incremental Layout failed. Error code: {0} ", exception.getErrorCode());
		}
	}

	public void callLayoutService() throws TSServiceException {
		TSServiceOutputData outputData = new TSServiceOutputData();
		this.layoutProxy.run(this.inputData, outputData);

		TSApplyLayoutResults result = new TSApplyLayoutResults();
		result.apply(this.inputData, outputData);
	}
	
	public void setEdgeLabelOptions(TSEEdgeLabel edgeLabel, int type) {
		TSLabelingInputTailor labelingInputTailor = new TSLabelingInputTailor(this.inputData);
		labelingInputTailor.setGraphManager(this.graphManager);

		if (type == DiagramConstants.ACTION_ENTITY_EDGE) {
			labelingInputTailor.setAssociation(edgeLabel,
			// TSLayoutConstants.EDGE_LABEL_ASSOCIATION_TARGET);
					TSLayoutConstants.EDGE_LABEL_ASSOCIATION_CENTER);

		} else if (type == DiagramConstants.ENTITY_CONDITION_EDGE) {
			labelingInputTailor.setAssociation(edgeLabel,
			// TSLayoutConstants.EDGE_LABEL_ASSOCIATION_SOURCE);
					TSLayoutConstants.EDGE_LABEL_ASSOCIATION_CENTER);
		}
	}
	
	public void setNodeLabelOptions(TSENodeLabel nodeLabel) {
		TSLabelingInputTailor labelingInputTailor = new TSLabelingInputTailor(this.inputData);
		labelingInputTailor.setGraphManager(this.graphManager);
		labelingInputTailor.setRegion(nodeLabel, TSLayoutConstants.NODE_LABEL_REGION_BELOW);
	}

	public void setEdgeLabelOptions(TSEEdgeLabel edgeLabel) {
		TSLabelingInputTailor labelingInputTailor = new TSLabelingInputTailor(this.inputData);
		labelingInputTailor.setGraphManager(this.graphManager);
		labelingInputTailor.setRegion(edgeLabel, TSLayoutConstants.EDGE_LABEL_REGION_BELOW);
	}
	
	public void setFitNestedGraph(TSENode expandedNode, boolean fitNestedGraph) {
	    TSGeneralLayoutInputTailor genInputTailor =
	    	new TSGeneralLayoutInputTailor(this.inputData);
	    genInputTailor.setFitNestedGraph(expandedNode, fitNestedGraph);
	}
	
	/**
	 * This method sets layout options on the drawing.
	 */
	public void setHierarchicalOptions() {
		TSEGraph graph = (TSEGraph) this.graphManager.getMainDisplayGraph();
		TSHierarchicalLayoutInputTailor hierarchicalInputTailor = new TSHierarchicalLayoutInputTailor(this.inputData);
		hierarchicalInputTailor.setGraph(graph);

		// PREFERENCE
		// hierarchicalInputTailor.setLevelDirection(TSLayoutConstants.
		// DIRECTION_LEFT_TO_RIGHT);
		// hierarchicalInputTailor.setQuality(TSLayoutConstants.
		// LAYOUT_QUALITY_MEDIUM);

		// TSENode node = (TSENode) graph.nodes().get(4);
		// hierarchicalInputTailor.setAttachmentSide(node,
		// TSLayoutConstants.ATTACHMENT_SIDE_RIGHT);

		// TSEEdge edge = (TSEEdge) graph.edges().get(1);
		// hierarchicalInputTailor.setTargetAttachmentSide(edge,
		// TSLayoutConstants.ATTACHMENT_SIDE_LEFT);

		// node = (TSENode) graph.nodes().get(0);
		// TSEConnector connector = (TSEConnector) node.connectors().get(0);
		// hierarchicalInputTailor.setMovable(connector, true);

		// TODO: when we fully implement preferences, this "temporary" setting
		// should be reverted back to whatever the user requested in the
		// preferences. We change the setting here because that's what the
		// user clicked on, but the question is whether this implies a
		// preference change (of how to do routing) or is just temporary for
		// the duration of this layout
		hierarchicalInputTailor.setOrthogonalRouting(true);
		hierarchicalInputTailor.setKeepNodeSizes(true);
		hierarchicalInputTailor.setAsCurrentLayoutStyle();
	}
	
	public void setLeftToRightOrthHierarchicalOptions(TSEGraph graph) {
		TSHierarchicalLayoutInputTailor hierarchicalInputTailor = new TSHierarchicalLayoutInputTailor(this.inputData);
		hierarchicalInputTailor.setGraph(graph);	
		hierarchicalInputTailor.setLevelDirection(TSLayoutConstants.DIRECTION_LEFT_TO_RIGHT);
		hierarchicalInputTailor.setOrthogonalRouting(true);
		hierarchicalInputTailor.setKeepNodeSizes(true);		
	}
	
	public void setOrthHierarchicalOptions(int direction) {
		TSHierarchicalLayoutInputTailor hierarchicalInputTailor = new TSHierarchicalLayoutInputTailor(this.inputData);
		hierarchicalInputTailor.setGraph(this.graphManager.getMainDisplayGraph());	
		hierarchicalInputTailor.setLevelDirection(direction);
		hierarchicalInputTailor.setOrthogonalRouting(true);
		hierarchicalInputTailor.setKeepNodeSizes(true);		
	}
	
	public void setLeftToRightOrthHierarchicalOptions() {
		this.setLeftToRightOrthHierarchicalOptions((TSEGraph) this.graphManager.getMainDisplayGraph());
	}
	
	public void setAllowNodeResizeHierarchicalOptions() {
		TSEGraph graph = (TSEGraph) this.graphManager.getMainDisplayGraph();
		TSHierarchicalLayoutInputTailor hierarchicalInputTailor = new TSHierarchicalLayoutInputTailor(this.inputData);
		hierarchicalInputTailor.setGraph(graph);
		hierarchicalInputTailor.setOrthogonalRouting(true);
		hierarchicalInputTailor.setKeepNodeSizes(false);
		hierarchicalInputTailor.setAsCurrentLayoutStyle();
	}	


	public void setRegularHierarchicalOptions() {
		TSEGraph graph = (TSEGraph) this.graphManager.getMainDisplayGraph();
		TSHierarchicalLayoutInputTailor hierarchicalInputTailor = new TSHierarchicalLayoutInputTailor(this.inputData);
		hierarchicalInputTailor.setGraph(graph);

		// PREFERENCE
		// hierarchicalInputTailor.setLevelDirection(TSLayoutConstants.
		// DIRECTION_LEFT_TO_RIGHT);
		// hierarchicalInputTailor.setQuality(TSLayoutConstants.
		// LAYOUT_QUALITY_MEDIUM);

		// TODO: when we fully implement preferences, this "temporary" setting
		// should be reverted back to whatever the user requested in the
		// preferences. We change the setting here because that's what the
		// user clicked on, but the question is whether this implies a
		// preference change (of how to do routing) or is just temporary for
		// the duration of this layout
		hierarchicalInputTailor.setOrthogonalRouting(false);

		hierarchicalInputTailor.setAsCurrentLayoutStyle();
	}

	/**
	 * This method sets layout options on the execution view only! It should not
	 * be called from anywhere else! It does not look at hierachical layout
	 * options because we want something specialized for the rule execution
	 * window.
	 */
	public void setNonPreferenceHierarchicalOptions() {
		TSEGraph graph = (TSEGraph) this.graphManager.getMainDisplayGraph();
		TSHierarchicalLayoutInputTailor hierarchicalInputTailor = new TSHierarchicalLayoutInputTailor(this.inputData);
		hierarchicalInputTailor.setGraph(graph);
		hierarchicalInputTailor.setLevelDirection(TSLayoutConstants.DIRECTION_LEFT_TO_RIGHT);
		hierarchicalInputTailor.setQuality(TSLayoutConstants.LAYOUT_QUALITY_MEDIUM);
		hierarchicalInputTailor.setAsCurrentLayoutStyle();
	}

	/**
	 * This method sets layout options on the drawing.
	 */
	public void setCircularOptions() {
		TSEGraph graph = (TSEGraph) this.graphManager.getMainDisplayGraph();
		TSCircularLayoutInputTailor circularInputTailor = new TSCircularLayoutInputTailor(this.inputData);
		circularInputTailor.setGraph(graph);
		circularInputTailor.setAsCurrentLayoutStyle();
	}

	/**
	 * This method sets layout options on the drawing.
	 */
	/* TODO - Decision Tree
	public void setDecisionOptions() {
		TSEGraph graph = (TSEGraph) this.graphManager.getMainDisplayGraph();
		TSDecisionLayoutInputTailor decisionInputTailor = new TSDecisionLayoutInputTailor(this.inputData);
		decisionInputTailor.setGraph(graph);
		decisionInputTailor.setLevelAlignment(TSLayoutConstants.DIRECTION_TOP_TO_BOTTOM);
		decisionInputTailor.setQuality(TSLayoutConstants.LAYOUT_QUALITY_MEDIUM);
		decisionInputTailor.setAsCurrentLayoutStyle();
	}
	*/
	
	/**
	 * This method sets layout options on the drawing.
	 */
	public void setSymmetricOptions() {
		TSEGraph graph = (TSEGraph) this.graphManager.getMainDisplayGraph();
		TSSymmetricLayoutInputTailor symmetricInputTailor = new TSSymmetricLayoutInputTailor(this.inputData);
		symmetricInputTailor.setGraph(graph);
		symmetricInputTailor.setAsCurrentLayoutStyle();
	}

	/**
	 * This method sets layout options on the drawing.
	 */
	public void setOrthogonalOptions() {
		TSEGraph graph = (TSEGraph) this.graphManager.getMainDisplayGraph();

		TSOrthogonalLayoutInputTailor orthogonalInputTailor = new TSOrthogonalLayoutInputTailor(this.inputData);

		// PREFERENCE
		// orthogonalInputTailor.setQuality(TSLayoutConstants.
		// LAYOUT_QUALITY_MEDIUM);

		orthogonalInputTailor.setGraph(graph);
		orthogonalInputTailor.setAsCurrentLayoutStyle();
		orthogonalInputTailor.setKeepNodeSizes(true);
	}

	/**
	 * This method sets layout options on the drawing.
	 */
	public void setRoutingOptions() {
		TSRoutingInputTailor routingInputTailor = new TSRoutingInputTailor(this.inputData);
		// TODO PREFERENCE
		// routingInputTailor.setFixedPositions(true);
		routingInputTailor.setFixedSizes(true);
		
		routingInputTailor.setGraphManager(this.graphManager);
		routingInputTailor.setAsCurrentOperation();
	}
	
	public void setIncrementalOptions() {
		TSEGraph graph = (TSEGraph) this.graphManager.getMainDisplayGraph();
		TSHierarchicalLayoutInputTailor hierarchicalInputTailor = new TSHierarchicalLayoutInputTailor(this.inputData);
		hierarchicalInputTailor.setGraph(graph);
		hierarchicalInputTailor.setOrthogonalRouting(true);
		hierarchicalInputTailor.setKeepNodeSizes(false);
		hierarchicalInputTailor.setAsCurrentLayoutStyle();

	}

	public TSEAllOptionsServiceInputData getInputData() {
		return this.inputData;
	}

	public void setInputData(TSEAllOptionsServiceInputData inputData) {
		this.inputData = inputData;
	}
	
	public BaseDiagramManager getDiagramManager() {
		return this.diagramManager;
	}
	
	protected void executeLayoutCommand(final TSCommand command, boolean layout_On_Diagram_Change){
		if(!layout_On_Diagram_Change) {
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					drawingCanvas.getCommandManager().transmit(command);
				}
			});
		}
		
	}

	public void dispose() {
		graphManager = null;
		drawingCanvas = null;
		diagramManager = null;		
	}

}
