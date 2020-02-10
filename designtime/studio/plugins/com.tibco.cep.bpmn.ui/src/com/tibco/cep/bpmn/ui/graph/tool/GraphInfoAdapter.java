package com.tibco.cep.bpmn.ui.graph.tool;

import org.eclipse.jface.viewers.ISelection;

import com.tibco.cep.bpmn.ui.editor.GraphSelection;
import com.tibco.cep.bpmn.ui.editor.IGraphInfo;
import com.tomsawyer.drawing.geometry.shared.TSConstPoint;
import com.tomsawyer.graphicaldrawing.TSEObject;
import com.tomsawyer.interactive.swing.TSEHitTesting;
import com.tomsawyer.interactive.swing.tool.TSToolPreferenceTailor;

public class GraphInfoAdapter implements IGraphInfo{

	private BpmnSelectTool tool;
	
	public GraphInfoAdapter(BpmnSelectTool bpmnSelectTool) {
		this.tool = bpmnSelectTool;
	}

	@Override
	public TSConstPoint getLocationLastMouseButtonActivity() {
		return tool.getLastMousePoint();
	}
	
	public TSEObject getGraphObjectAt(TSConstPoint point) {
		// get the point where the mouse is pressed.
		if(tool.getSwingCanvas()!=null)
		{
			TSEHitTesting hitTesting = tool.getHitTesting();
			TSToolPreferenceTailor tailor = new TSToolPreferenceTailor(tool.getSwingCanvas().getPreferenceData());
			TSEObject object = hitTesting.getGraphObjectAt(point, tool.getGraph(), tailor.isNestedGraphInteractionEnabled());
			return object;
		}
		return null;
	}
	
	@Override
	public ISelection getSelectionAt(TSConstPoint point) {
		TSEObject obj = getGraphObjectAt(point);
		return new GraphSelection(obj);
	}
	

}
