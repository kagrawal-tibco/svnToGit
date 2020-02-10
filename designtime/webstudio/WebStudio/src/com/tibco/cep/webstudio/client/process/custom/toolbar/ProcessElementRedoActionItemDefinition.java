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
 * This the definition of custom redo button of tom swayer tool bar
 * 
 * @author dijadhav
 * 
 */
public class ProcessElementRedoActionItemDefinition extends
		TSDrawingViewActionItemDefinition implements
		TSDesktopActionItemImplementer, TSWebActionItemImplementer,
		TSWebClientSideActionItemImplementer {

	/**
	 * Serail Version UID
	 */
	private static final long serialVersionUID = 897351090468076033L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tomsawyer.view.behavior.TSWebActionItemImplementer#newWebImplementation
	 * ()
	 */
	@Override
	public TSWebActionItem newWebImplementation() {
		return new ProcessElementRedoActionItem();
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
					.canRedo();
		}
		return shouldEnabled;
	}
}
