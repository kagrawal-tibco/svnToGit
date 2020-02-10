package com.tibco.cep.studio.ui.statemachine.actions;

import org.eclipse.ui.IWorkbenchPage;

import com.tibco.cep.diagramming.actions.SelectAction;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

/**
 * 
 * @author sasahoo
 *
 */
public class StateMachineSelectAction extends SelectAction{

	/**
	 * @param page
	 */
	public StateMachineSelectAction(IWorkbenchPage page) {
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
