/**
 * 
 */
package com.tibco.cep.studio.ui.diagrams;

import static com.tibco.cep.studio.ui.util.StudioUIUtils.setWorkbenchSelection;

import org.eclipse.swt.SWT;
import org.eclipse.ui.part.EditorPart;

import com.tibco.cep.diagramming.drawing.SelectionChangeListener;
import com.tibco.cep.studio.ui.editors.events.EventDiagramEditor;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSENode;

/**
 * @author mgujrath
 *
 */
public class EventDiagramSelectionChangeListener extends SelectionChangeListener{
	
	private EditorPart editor;
	private EventDiagramManager manager;
	private static final long serialVersionUID = 1L;
	
	public EventDiagramSelectionChangeListener(EventDiagramManager manager) {
		super(manager);
		this.manager = manager;
		this.editor = manager.getEditor();
	}

	@Override
	protected void onEdgeSelected(TSEEdge tsEdge) {
		setWorkbenchSelection(tsEdge, editor);
	
	
	((EventDiagramEditor)editor).enableEdit(true);
	}

	@Override
	protected void onNodeSelected(TSENode tsNode) {
		if ("gtk".equals(SWT.getPlatform())) {
			super.onNodeSelected(tsNode);
		} else {
			setWorkbenchSelection(tsNode, editor);
		}
		
		((EventDiagramEditor)editor).enableEdit(true);
	}

	
	

}
