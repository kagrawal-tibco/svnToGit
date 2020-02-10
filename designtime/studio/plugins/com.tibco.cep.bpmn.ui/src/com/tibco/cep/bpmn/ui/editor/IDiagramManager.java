package com.tibco.cep.bpmn.ui.editor;

import org.eclipse.core.runtime.IAdaptable;

import com.tibco.cep.bpmn.ui.graph.tool.BpmnCreateEdgeTool;
import com.tibco.cep.diagramming.drawing.DiagramChangeListener;
import com.tibco.cep.diagramming.drawing.DiagramManager;
import com.tibco.cep.diagramming.drawing.SelectionChangeListener;
import com.tibco.cep.diagramming.tool.ReconnectEdgeTool;
import com.tibco.cep.diagramming.tool.SelectTool;
import com.tomsawyer.graph.TSGraphObject;
import com.tomsawyer.interactive.swing.editing.tool.TSECreateNodeTool;
import com.tomsawyer.interactive.swing.editing.tool.TSEEditTextTool;
import com.tomsawyer.interactive.swing.editing.tool.TSEPasteTool;
import com.tomsawyer.interactive.swing.editing.tool.TSETransferSelectedTool;

public interface IDiagramManager extends IAdaptable {

	public boolean canDelete();

	public boolean canCut();

	public boolean canCopy();

	public boolean canPaste();

	public BpmnEditor getEditor();

	public TSEPasteTool getPasteTool();

	public TSGraphObject getCurrentSelection();

	public TSETransferSelectedTool getTransferSelectedTool();

	public TSEEditTextTool getEditTextTool();

	public ReconnectEdgeTool getReconnectEdgeTool();

	public SelectTool getSelectTool();

	public TSECreateNodeTool getNodeTool();

	public BpmnCreateEdgeTool getEdgeTool();

	public SelectionChangeListener getDiagramSelectionListener();

	public DiagramChangeListener<? extends DiagramManager> getDiagramChangeListener();

}
