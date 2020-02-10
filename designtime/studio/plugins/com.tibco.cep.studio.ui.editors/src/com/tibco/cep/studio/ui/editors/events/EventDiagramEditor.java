package com.tibco.cep.studio.ui.editors.events;

import java.util.HashMap;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.actions.ActionFactory;

import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.diagramming.drawing.IDiagramManager;
import com.tibco.cep.diagramming.tool.PALETTE;
import com.tibco.cep.studio.ui.diagrams.EventDiagramManager;
import com.tibco.cep.studio.ui.editors.EntityDiagramEditor;


public class EventDiagramEditor extends EntityDiagramEditor<Event, EventDiagramManager> {
	
	public final static String ID = "com.tibco.cep.event.editors.EventDiagramEditor";
	private HashMap<String, Action> handler;
		
	public IDiagramManager getDiagramManager() {
		if (entityDiagramManager == null) {
			entityDiagramManager = new EventDiagramManager(this);
		}
		return entityDiagramManager;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.IGraphDrawing#getPalette()
	 */
	@Override
	public PALETTE getPalette() {
		return PALETTE.EVENT;
	}
	
	

	public Action getActionHandler(String id, EventDiagramEditor editor) {
		if (handler == null) {
			handler = new HashMap<String, Action>();
		}
		if (handler.get(id) == null) {
			handler.put(id, new EventDiagramEditHandler(id,editor));
		}
		return handler.get(id);
	}
	
	
	@Override
	public String getPerspectiveId() {
		return "com.tibco.cep.diagramming.diagram.perspective";
	}
	
	public void doSave(IProgressMonitor monitor) {
		saving = true;
		try {
			entityDiagramManager.save();
			if (isDirty()) {
				isModified = false;
				firePropertyChange(IEditorPart.PROP_DIRTY);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			saving = false;
		}
	}

	public void enableEdit(boolean enabled) {
		
		this.getActionHandler(ActionFactory.UNDO.getId(), this).setEnabled(	entityDiagramManager.canUndo());
		this.getActionHandler(ActionFactory.REDO.getId(), this).setEnabled(	entityDiagramManager.canRedo());
	
		
		//enableUndoRedoContext();
	}
}