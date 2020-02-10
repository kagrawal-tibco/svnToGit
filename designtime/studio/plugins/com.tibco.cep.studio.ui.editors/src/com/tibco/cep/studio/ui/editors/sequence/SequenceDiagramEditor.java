package com.tibco.cep.studio.ui.editors.sequence;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.IEditorInput;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.diagramming.drawing.IDiagramManager;
import com.tibco.cep.diagramming.tool.PALETTE;
import com.tibco.cep.studio.ui.diagrams.SequenceDiagramManager;
import com.tibco.cep.studio.ui.editors.EntityDiagramEditor;

/**
 * 
 * @author ggrigore
 * 
 */
public class SequenceDiagramEditor extends EntityDiagramEditor<Entity, SequenceDiagramManager> {

	public final static String ID = "com.tibco.cep.studio.project.editors.SequenceDiagramEditor";

	public IDiagramManager getDiagramManager() {
		if (entityDiagramManager == null) {
			entityDiagramManager = new SequenceDiagramManager(this);
		}
		return entityDiagramManager;
	}

	@Override
	public void setFocus() {
		super.setFocus();
		if (getFile() != null) {
			setPartName(getFile().getName() + " sequence");
		}
	}

	/**
	 * Update the editor's title based upon the content being edited.
	 */
	@Override
	protected void updateTitle() {
		if (getEditorInput() instanceof SequenceDiagramEditorInput) {
			SequenceDiagramEditorInput input = (SequenceDiagramEditorInput) getEditorInput();
			IFile file = input.getFile();
			// String name = file.getName().substring(0,
			// file.getName().indexOf("."));
			setPartName(file.getName() + "sequenceview");
			setTitleToolTip(input.getToolTipText());
		} else {
			IEditorInput input = getEditorInput();
			setPartName(input.getName());
			setTitleToolTip(input.getToolTipText());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.diagramming.drawing.IGraphDrawing#getPalette()
	 */
	@Override
	public PALETTE getPalette() {
		return PALETTE.SEQUENCE;
	}

	@Override
	public String getPerspectiveId() {
		return "com.tibco.cep.diagramming.diagram.perspective";
	}
}