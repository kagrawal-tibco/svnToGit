package com.tibco.cep.studio.cluster.topology.actions;

import org.eclipse.ui.IWorkbenchPage;

import com.tibco.cep.diagramming.actions.SelectAction;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

/**
 * 
 * @author hitesh
 *
 */

public class ClusterTopologySelectAction extends SelectAction {

	public ClusterTopologySelectAction(IWorkbenchPage page) {
		super(page);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.actions.SelectAction#run()
	 */
	public void run() {
		super.run();
		StudioUIUtils.resetPaletteSelection();
	}
}
