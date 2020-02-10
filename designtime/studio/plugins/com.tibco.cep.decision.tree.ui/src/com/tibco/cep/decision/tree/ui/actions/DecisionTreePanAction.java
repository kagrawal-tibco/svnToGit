package com.tibco.cep.decision.tree.ui.actions;

import org.eclipse.ui.IWorkbenchPage;

import com.tibco.cep.diagramming.actions.PanAction;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

/*
@author ssailapp
@date Sep 14, 2011
 */

public class DecisionTreePanAction extends PanAction {

	/**
	 * 
	 * @param page
	 */
	public DecisionTreePanAction(IWorkbenchPage page) {
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
