package com.tibco.cep.studio.dashboard.ui.editors.views.datasource;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IEditorPart;

public class DataSourceQueryRedoAction extends Action {

	private DataSourceEditor targetEditor;

	public DataSourceQueryRedoAction(IEditorPart targetEditor) {
		this.targetEditor = (DataSourceEditor) targetEditor;
	}
	
	@Override
	public void run() {
		DataSourcePage page = (DataSourcePage) this.targetEditor.getActivePageInstance();
		if (page.getQueryTextViewer().getTextWidget().isFocusControl() == true){
			page.getQueryTextViewer().getUndoManager().redo();
		}
	}

}
