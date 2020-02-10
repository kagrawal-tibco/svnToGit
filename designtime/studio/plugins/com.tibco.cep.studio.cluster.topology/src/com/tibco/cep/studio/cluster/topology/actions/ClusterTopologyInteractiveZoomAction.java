/**
 * 
 */
package com.tibco.cep.studio.cluster.topology.actions;

import org.eclipse.ui.IWorkbenchPage;

import com.tibco.cep.diagramming.actions.InteractiveZoomAction;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

/**
 * @author hitesh
 *
 */
public class ClusterTopologyInteractiveZoomAction extends InteractiveZoomAction {

	/**
	 * @param page
	 */
	public ClusterTopologyInteractiveZoomAction(IWorkbenchPage page) {
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
