package com.tibco.cep.webstudio.client.process.custom.toolbar;

import com.tomsawyer.util.shared.TSUserAgent;
import com.tomsawyer.view.TSModelView;
import com.tomsawyer.view.action.TSDrawingViewActionItemDefinition;
import com.tomsawyer.view.behavior.TSDesktopActionItemImplementer;
import com.tomsawyer.view.behavior.TSViewBehaviorObject;
import com.tomsawyer.view.behavior.TSWebActionItem;
import com.tomsawyer.view.behavior.TSWebActionItemImplementer;
import com.tomsawyer.view.behavior.TSWebClientSideActionItemImplementer;

/**
 * This the definition of custom undo button of tom swayer tool bar
 * 
 * @author dijadhav
 * 
 */
public class ProcessElementUndoActionItemDefinition extends
		TSDrawingViewActionItemDefinition implements
		TSDesktopActionItemImplementer, TSWebActionItemImplementer,
		TSWebClientSideActionItemImplementer {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 5530709404532127117L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tomsawyer.view.behavior.TSWebActionItemImplementer#newWebImplementation
	 * ()
	 */
	@Override
	public TSWebActionItem newWebImplementation() {
		return new ProcessElementUndoActionItem();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tomsawyer.view.behavior.TSDesktopActionItemImplementer#
	 * newDesktopImplementation(com.tomsawyer.util.shared.TSUserAgent)
	 */
	@Override
	public TSViewBehaviorObject newDesktopImplementation(TSUserAgent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean shouldBeEnabled(TSModelView tsmodelview) {
		boolean shouldEnabled = super.shouldBeEnabled(tsmodelview);
		if (shouldEnabled) {
			shouldEnabled = tsmodelview.getModel().getCommandManager()
					.canUndo();
		}
		return shouldEnabled;
	}
}
