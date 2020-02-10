package com.tibco.cep.studio.cluster.topology.actions;

import org.eclipse.ui.IWorkbenchPage;

import com.tibco.cep.diagramming.actions.MagnifyAction;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
/**
 * 
 * @author hitesh
 * 
 **/
public class ClusterTopologyMagnifyAction extends MagnifyAction {

	public ClusterTopologyMagnifyAction(IWorkbenchPage page) {
		super(page);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.actions.SelectAction#run()
	 */
	public void run() {
		super.run();
		StudioUIUtils.resetPaletteSelection();
	}
}
