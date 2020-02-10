package com.tibco.cep.studio.ui.editors.clusterconfig;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/*
@author ssailapp
@date Nov 24, 2009 3:28:06 PM
 */

public class ClusterConfigEditorTableModelListener implements TableModelListener {

	private ClusterConfigEditor editor;

	public ClusterConfigEditorTableModelListener(ClusterConfigEditor editor) {
		this.editor = editor;
	}

	@Override
	public void tableChanged(TableModelEvent evt) {
		if (evt.getType() == TableModelEvent.UPDATE) {
			editor.modified();
		}
	}

}
