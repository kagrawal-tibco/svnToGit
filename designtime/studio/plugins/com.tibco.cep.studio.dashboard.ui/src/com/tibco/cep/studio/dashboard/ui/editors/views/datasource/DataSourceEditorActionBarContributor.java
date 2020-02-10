package com.tibco.cep.studio.dashboard.ui.editors.views.datasource;

import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.EditorActionBarContributor;

public class DataSourceEditorActionBarContributor extends EditorActionBarContributor {

	public DataSourceEditorActionBarContributor() {
	}
	
	@Override
	public void setActiveEditor(IEditorPart targetEditor) {
		super.setActiveEditor(targetEditor);
		IActionBars actionBars = getActionBars();
		if (actionBars != null) {
			actionBars.setGlobalActionHandler(ActionFactory.UNDO.getId(), new DataSourceQueryUndoAction(targetEditor));
			actionBars.setGlobalActionHandler(ActionFactory.REDO.getId(), new DataSourceQueryRedoAction(targetEditor));
			actionBars.updateActionBars();
		}
	}

}
