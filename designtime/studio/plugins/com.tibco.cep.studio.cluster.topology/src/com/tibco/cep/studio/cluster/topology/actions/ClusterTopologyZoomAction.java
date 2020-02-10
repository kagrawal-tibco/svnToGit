package com.tibco.cep.studio.cluster.topology.actions;

import org.eclipse.ui.IWorkbenchPage;

import com.tibco.cep.diagramming.actions.ZoomAction;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
/**
 * 
 * @author hitesh
 * 
 **/
public class ClusterTopologyZoomAction extends ZoomAction {

	public ClusterTopologyZoomAction(IWorkbenchPage page) {
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
