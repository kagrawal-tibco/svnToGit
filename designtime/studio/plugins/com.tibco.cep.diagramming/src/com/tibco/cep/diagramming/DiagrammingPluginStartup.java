package com.tibco.cep.diagramming;

import org.eclipse.ui.IStartup;

import com.tibco.cep.diagramming.menu.popup.DiagramPopupMenuUtils;

/**
 * 
 * @author sasahoo
 *
 */
public class DiagrammingPluginStartup implements IStartup {
	

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IStartup#earlyStartup()
	 */
	public void earlyStartup() {
		//initialize popupmenu
		DiagramPopupMenuUtils.createDiagramPopupMenuInfos();
	}
}