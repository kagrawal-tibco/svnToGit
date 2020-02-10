package com.tibco.cep.webstudio.client.process.custom.toolbar;

import java.io.Serializable;

import com.tomsawyer.view.behavior.TSAbstractItemDefinition;
import com.tomsawyer.web.client.view.data.TSWebViewClientCommandData;
import com.tomsawyer.web.server.action.TSWebDrawingViewActionItem;

/**
 * This is the custom delete button action item for tom swayer tool bar
 * 
 * @author dijadhav
 * 
 */

public class ProcessElementDeleteActionItem extends TSWebDrawingViewActionItem {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -1692890609762855241L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tomsawyer.view.behavior.TSWebActionItem#onAction(com.tomsawyer.view
	 * .behavior.TSAbstractItemDefinition)R
	 */
	@Override
	public Serializable onAction(
			TSAbstractItemDefinition tsabstractitemdefinition) {

		// create the web view client command data
		TSWebViewClientCommandData result = new TSWebViewClientCommandData();
		result.setCommand("deleteElement");
		result.setViewID(getDrawingView().getViewID());
		result.setCommandData(null);
		return result;
	}
}
