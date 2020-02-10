package com.tibco.cep.studio.cluster.topology.handler;

import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.widgets.Display;

import com.tibco.cep.studio.cluster.topology.editors.ClusterTopologyEditor;

public class ClusterTopologyModelChangeListener implements Observer{

	private ClusterTopologyEditor editor;

	public ClusterTopologyModelChangeListener(ClusterTopologyEditor editor){
		this.editor = editor;
	}
	
	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		// TODO may be it can have other changes
		asyncEditorModified();
	}
	
	private void asyncEditorModified(){
		Display.getDefault().asyncExec(new Runnable(){
			@Override
			public void run() {
				editor.modified();
			}});
	}

}
