package com.tibco.cep.decision.tree.ui.actions;

import org.eclipse.ui.IWorkbenchPage;

import com.tibco.cep.diagramming.actions.MagnifyAction;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

/*
@author ssailapp
@date Sep 14, 2011
 */

public class DecisionTreeMagnifyAction extends MagnifyAction {

	/**
	 * @param page
	 */
	public DecisionTreeMagnifyAction(IWorkbenchPage page) {
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
