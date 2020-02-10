package com.tibco.cep.bpmn.ui.graph.tool;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.MouseEvent;
import java.io.IOException;

import org.eclipse.emf.ecore.EClass;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.editor.BpmnDiagramManager;
import com.tibco.cep.bpmn.ui.graph.model.command.BpmnPasteCommand;
import com.tibco.cep.bpmn.ui.transfer.BPMNProcessTransfer;
import com.tomsawyer.drawing.TSDGraph;
import com.tomsawyer.drawing.geometry.shared.TSConstPoint;
import com.tomsawyer.drawing.geometry.shared.TSPoint;
import com.tomsawyer.graph.TSGraphObject;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.command.editing.TSEPasteCommand;
import com.tomsawyer.interactive.editing.control.TSCutCopyPasteControl;
import com.tomsawyer.interactive.swing.TSEHitTesting;
import com.tomsawyer.interactive.swing.editing.tool.TSEPasteTool;
import com.tomsawyer.interactive.swing.editing.tool.TSEditingToolHelper;
import com.tomsawyer.interactive.swing.viewing.tool.TSViewingToolHelper;
import com.tomsawyer.util.option.TSOptionData;


/**
 * 
 * @author majha
 *
 */
public class BpmnPasteTool extends TSEPasteTool {
	
	private BpmnDiagramManager diagramManager;
	public BpmnPasteTool(BpmnDiagramManager diagramManager){
		this.diagramManager = diagramManager;
	}
	@Override
	protected TSEPasteCommand newPasteCommand(TSGraphObject paramTSGraphObject,
			TSCutCopyPasteControl paramTSCutCopyPasteControl,
			TSPoint paramTSPoint, TSOptionData paramTSOptionData) {
		return new BpmnPasteCommand( diagramManager, paramTSGraphObject, paramTSCutCopyPasteControl,
				paramTSPoint, paramTSOptionData);
	}
	
	@Override
	public void onMousePressed(MouseEvent event) {
		if (event.getButton() == MouseEvent.BUTTON3 || event.isPopupTrigger()) {
			//On right click, the default select tool reset. 
			getSwingCanvas().getToolManager().setActiveTool(TSViewingToolHelper.getSelectTool(getSwingCanvas().getToolManager()));
		}else {
			Clipboard clipboard = BPMNProcessTransfer.getInstance().getClipboard();
			if (clipboard != null)
				getSwingCanvas().setClipboard(clipboard);
			//For Region Paste Validation
			TSEHitTesting hitTesting = this.getHitTesting();
			// get the point where the mouse is pressed.
			TSConstPoint point = this.getNonalignedWorldPoint(event);
			hitTesting.getGraphAt(point, this.getGraph());
			DataFlavor[] availableDataFlavors = getSwingCanvas().getClipboard().getAvailableDataFlavors();
			if (availableDataFlavors != null
					&& availableDataFlavors.length != 0) {
				for (DataFlavor fv : getSwingCanvas().getClipboard()
						.getAvailableDataFlavors()) {
					if (fv.getRepresentationClass() == TSDGraph.class) {
						try {
							Object object = getSwingCanvas().getClipboard()
									.getData(fv);
							TSEGraphManager manager = (TSEGraphManager) object;
							TSDGraph graph = (TSDGraph) manager
									.getAnchorGraph();
							for (Object obj : graph.nodes()) {
								TSENode node = (TSENode) obj;
								EClass nodeType = (EClass)node.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
								if(nodeType == null)
									continue;
								if(BpmnModelClass.LANE.isSuperTypeOf(nodeType)){
									cancelAction();
									return;
								}
							}
						} catch (UnsupportedFlavorException e) {
							BpmnUIPlugin.log(e);
						} catch (IOException e) {
							BpmnUIPlugin.log(e);
						}
					}
				}
				super.onMousePressed(event);
			}else{
				cancelAction();
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tomsawyer.interactive.swing.editing.tool.TSEPasteTool#onMouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void onMouseReleased(MouseEvent event){
		if (event.getButton() == MouseEvent.BUTTON3 || event.isPopupTrigger()) {
			//On right click, the default select tool reset. 
			getSwingCanvas().getToolManager().setActiveTool(TSViewingToolHelper.getSelectTool(getSwingCanvas().getToolManager()));
		}else {
			super.onMouseReleased(event);
			//For resetting the state machine paste tool after each paste operation
			getSwingCanvas().getToolManager().setActiveTool(TSEditingToolHelper.getPasteTool(getSwingCanvas().getToolManager()));
		}
	}

}
