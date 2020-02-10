package com.tibco.cep.studio.ui.diagrams;

import static com.tibco.cep.studio.ui.util.StudioUIUtils.setWorkbenchSelection;

import org.eclipse.swt.SWT;
import org.eclipse.ui.part.EditorPart;

import com.tibco.cep.diagramming.drawing.SelectionChangeListener;
import com.tibco.cep.studio.ui.editors.concepts.ConceptDiagramEditor;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSENode;
/**
 *    mgoel
 *    
 */

public class ConceptDiagramSelectionChangeListener extends
		SelectionChangeListener {

	private EditorPart editor;
	private ConceptDiagramManager manager;
	private static final long serialVersionUID = 1L;
	
	public ConceptDiagramSelectionChangeListener(ConceptDiagramManager manager) {
		super(manager);
		this.manager = manager;
		this.editor = manager.getEditor();
	}

	@Override
	protected void onEdgeSelected(TSEEdge tsEdge) {
		setWorkbenchSelection(tsEdge, editor);
	
	
	((ConceptDiagramEditor)editor).enableEdit(true);
	}

	@Override
	protected void onNodeSelected(TSENode tsNode) {
		if ("gtk".equals(SWT.getPlatform())) {
			super.onNodeSelected(tsNode);
		} else {
			setWorkbenchSelection(tsNode, editor);
		}
		
		((ConceptDiagramEditor)editor).enableEdit(true);
	}

	
	

}
