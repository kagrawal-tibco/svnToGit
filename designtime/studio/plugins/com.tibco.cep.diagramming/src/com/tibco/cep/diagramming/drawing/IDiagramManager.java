package com.tibco.cep.diagramming.drawing;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.IEditorPart;

import com.tibco.cep.diagramming.tool.ReconnectEdgeTool;
import com.tomsawyer.graph.TSGraphObject;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.interactive.swing.editing.tool.TSECreateEdgeTool;
import com.tomsawyer.interactive.swing.editing.tool.TSECreateNodeTool;
import com.tomsawyer.interactive.swing.editing.tool.TSEEditTextTool;
import com.tomsawyer.interactive.swing.editing.tool.TSEMoveSelectedTool;
import com.tomsawyer.interactive.swing.editing.tool.TSEPasteTool;
import com.tomsawyer.interactive.swing.editing.tool.TSETransferSelectedTool;
import com.tomsawyer.interactive.swing.viewing.tool.TSESelectTool;

public interface IDiagramManager extends IAdaptable {

	boolean canDelete();

	boolean canCut();

	boolean canCopy();

	boolean canPaste();

	IEditorPart getEditor();

	TSGraphObject getCurrentSelection();

	<T extends TSETransferSelectedTool> T getTransferSelectedTool();

	<T extends TSEEditTextTool> T getEditTextTool();

	<T extends TSEPasteTool> T getPasteTool();
	
	ReconnectEdgeTool getReconnectEdgeTool();

	<T extends TSESelectTool> T getSelectTool();

	<T extends TSECreateNodeTool> T getNodeTool();

	<T extends TSECreateEdgeTool> T getEdgeTool();
	
	<T extends TSEMoveSelectedTool> T getMoveSelectedTool();

	SelectionChangeListener getDiagramSelectionListener();

	DiagramChangeListener<? extends DiagramManager> getDiagramChangeListener();

	TSEGraphManager getGraphManager();

	DrawingCanvas getDrawingCanvas();

	void openModel() throws Exception;
	
	boolean getRefreshAction();
	
	public void setRefreshAction(boolean refresh);

}
