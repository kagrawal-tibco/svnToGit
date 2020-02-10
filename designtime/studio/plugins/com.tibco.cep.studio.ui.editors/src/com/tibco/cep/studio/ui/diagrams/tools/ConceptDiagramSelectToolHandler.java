package com.tibco.cep.studio.ui.diagrams.tools;

import javax.swing.AbstractButton;

import com.tibco.cep.diagramming.tool.SelectTool;
import com.tibco.cep.diagramming.tool.popup.EntityResourceConstants;
import com.tibco.cep.diagramming.tool.popup.SelectToolHandler;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.ui.diagrams.EntityNodeData;
import com.tomsawyer.graph.TSGraphMember;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSEObject;
import com.tomsawyer.graphicaldrawing.complexity.TSEHidingManager;

public class ConceptDiagramSelectToolHandler extends SelectToolHandler {


	/**
	 * @param button
	 * @param graph
	 * @param tool
	 * @param isEditorEnabled
	 */
	public static void chooseState(AbstractButton button, TSEGraph graph, SelectTool tool, boolean isEditorEnabled)
	{
		String command = button.getActionCommand();
		TSEHidingManager hidingManager = (TSEHidingManager) TSEHidingManager.getManager(tool.getSwingCanvas().getGraphManager());
		
		if(EntityResourceConstants.DELETE_SELECTED.equals(command)){
			if(!isEditorEnabled){
				button.setEnabled(false);
				return;
			}
			enableDisableMenu(EntityResourceConstants.DELETE_SELECTED, tool, button);
		}else if(EntityResourceConstants.EDIT_NODE.equals(command)){
			if(!isEditorEnabled){
				button.setEnabled(false);
				return;
			}
			enableDisableMenu(EntityResourceConstants.EDIT_NODE, tool, button);
		}
}
	public static void enableDisableMenu(String type, SelectTool tool, AbstractButton button) {
		TSEObject object = tool.hitObject;
		final TSGraphMember member = (TSGraphMember) object;	
		if (member instanceof TSENode)
		{
			TSENode node = (TSENode)member;
			/*Object userObject = node.getUserObject();*/
			if( !(node.getUserObject() instanceof SharedEntityElement)){
			EntityNodeData data = (EntityNodeData) node.getUserObject();
			Object userObject = data.getEntity().getUserObject();
			button.setEnabled(true);
			}else{
			button.setEnabled(false);
		}
	}
	}
}
