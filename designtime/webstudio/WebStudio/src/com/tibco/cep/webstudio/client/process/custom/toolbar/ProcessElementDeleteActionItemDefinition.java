package com.tibco.cep.webstudio.client.process.custom.toolbar;

import java.util.List;

import com.tomsawyer.model.selection.TSModelSelection;
import com.tomsawyer.util.shared.TSUserAgent;
import com.tomsawyer.view.TSModelView;
import com.tomsawyer.view.action.TSDrawingViewActionItemDefinition;
import com.tomsawyer.view.behavior.TSDesktopActionItemImplementer;
import com.tomsawyer.view.behavior.TSViewBehaviorObject;
import com.tomsawyer.view.behavior.TSWebActionItem;
import com.tomsawyer.view.behavior.TSWebActionItemImplementer;
import com.tomsawyer.view.behavior.TSWebClientSideActionItemImplementer;
import com.tomsawyer.view.drawing.TSModelDrawingView;
import com.tomsawyer.web.server.drawing.TSWebDrawingViewServer;

/**
 * This the definition of custom delete button of tom swayer tool bar
 * 
 * @author dijadhav
 * 
 */
public class ProcessElementDeleteActionItemDefinition extends
		TSDrawingViewActionItemDefinition implements
		TSDesktopActionItemImplementer, TSWebActionItemImplementer,
		TSWebClientSideActionItemImplementer {

	/**
	 *Serial version UID
	 */
	private static final long serialVersionUID = -7117255243921962800L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tomsawyer.view.behavior.TSWebActionItemImplementer#newWebImplementation
	 * ()
	 */
	@Override
	public TSWebActionItem newWebImplementation() {
		// TODO Auto-generated method stub
		return new ProcessElementDeleteActionItem();
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

	/**
	 * This method returns whether, in the current state of the given view, this
	 * action item should be enabled.
	 * 
	 * @param view
	 *            Given view.
	 * @return Whether action item should be enabled.
	 */
	public boolean shouldBeEnabled(TSModelView view) {
		boolean rc;

		if (view instanceof TSWebDrawingViewServer) {
			TSWebDrawingViewServer drawingViewServer = (TSWebDrawingViewServer) view;
			TSModelSelection modelSelection = drawingViewServer
					.getModelSelection();

			rc = shouldBeEnabled(view, modelSelection);
		} else {
			rc = false;
		}

		return rc;
	}

	/**
	 * This method returns whether, in the current state of the given view, this
	 * action item should be enabled.
	 * 
	 * @param view
	 *            Given view.
	 * @param modelSelection
	 *            The current model selection.
	 * @return Whether action item should be enabled.
	 */
	@SuppressWarnings("rawtypes")
	protected boolean shouldBeEnabled(TSModelView view,
			TSModelSelection modelSelection) {
		boolean rc = false;
		if (modelSelection != null) {
			List selectedObjects = ((TSModelDrawingView) view)
					.getSelectedAttributedObjects();

			if (!selectedObjects.isEmpty()) {
				rc = true;
			}
		}

		return rc;
	}

}
