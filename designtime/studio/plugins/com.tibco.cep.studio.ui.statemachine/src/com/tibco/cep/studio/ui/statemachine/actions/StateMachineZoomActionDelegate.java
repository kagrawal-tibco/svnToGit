package com.tibco.cep.studio.ui.statemachine.actions;

import org.eclipse.jface.action.IAction;

import com.tibco.cep.diagramming.actions.ZoomActionDelegate;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

/**
 * 
 * @author sasahoo
 *
 */
public class StateMachineZoomActionDelegate extends ZoomActionDelegate {


	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.actions.ZoomActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		super.run(action);
		StudioUIUtils.resetPaletteSelection();
	}
}
