package com.tibco.cep.studio.cluster.topology.editors;

import com.tibco.cep.diagramming.drawing.DiagramManager;
import com.tibco.cep.diagramming.drawing.EntityLayoutManager;
import com.tomsawyer.drawing.TSGraphTailor;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.interactive.command.TSCommand;
import com.tomsawyer.service.layout.TSHierarchicalLayoutInputTailor;
import com.tomsawyer.service.layout.TSLayoutConstants;
import com.tomsawyer.service.layout.TSLayoutInputTailor;

/**
 * 
 * @author ggrigore
 *
 */
public class ClusterTopologyLayoutManager extends EntityLayoutManager {

	public ClusterTopologyLayoutManager(DiagramManager diagramManager) {
		super(diagramManager);
		this.layoutInputTailor = new TSLayoutInputTailor(this.inputData);
	}

	public void setHierarchicalOptions() {
		super.setHierarchicalOptions();
		TSEGraph graph = (TSEGraph) this.graphManager.getMainDisplayGraph();

		TSHierarchicalLayoutInputTailor hierarchicalInputTailor = 
			new TSHierarchicalLayoutInputTailor(this.inputData);

		hierarchicalInputTailor.setGraph(graph);
		hierarchicalInputTailor.setLevelDirection(TSLayoutConstants.DIRECTION_TOP_TO_BOTTOM);
		// TODO: should be preference!
		hierarchicalInputTailor.setKeepNodeSizes(true);
	}
	
	public void setIncrementalOptions() {
		super.setIncrementalOptions();

		TSEGraph graph = (TSEGraph) this.graphManager.getMainDisplayGraph();
		TSHierarchicalLayoutInputTailor hierarchicalInputTailor = new TSHierarchicalLayoutInputTailor(
				this.inputData);

		hierarchicalInputTailor.setGraph(graph);
		hierarchicalInputTailor.setLevelDirection(TSLayoutConstants.DIRECTION_TOP_TO_BOTTOM);
	}	
	
	public void configureChildGraphLayoutOptions(TSEGraph graph) {

		TSHierarchicalLayoutInputTailor hierarchicalLayoutInputTailor =
			new TSHierarchicalLayoutInputTailor(this.getInputData());

		hierarchicalLayoutInputTailor.setGraph(graph);
		hierarchicalLayoutInputTailor.setAsCurrentLayoutStyle();
		hierarchicalLayoutInputTailor.setOrthogonalRouting(true);
		hierarchicalLayoutInputTailor.setKeepNodeSizes(true);
		hierarchicalLayoutInputTailor.setLevelDirection(
			TSLayoutConstants.DIRECTION_LEFT_TO_RIGHT);
		TSGraphTailor graphTailor = graph.getTailor();
//		graphTailor.setMargin(5.0);
        graphTailor.setNestedViewSpacing(2.0);
        graphTailor.setTopNestedViewSpacing(15.0);		
	}	
	
	protected void setPerCallGlobalLayoutOptions() {	}
	
	protected void setPerCallIncrementalLayoutOptions() {	}
	 
    protected void executeLayoutCommand(TSCommand command,boolean layout_on_change){
		if(!layout_on_change){
			super.executeLayoutCommand(command, false);
			return;
		}
	
		command.execute();
	}

}
