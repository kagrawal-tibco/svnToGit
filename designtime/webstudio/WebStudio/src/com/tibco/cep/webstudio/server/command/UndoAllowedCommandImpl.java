package com.tibco.cep.webstudio.server.command;

import java.io.Serializable;

import com.tomsawyer.util.gwtclient.command.TSServiceCommand;
import com.tomsawyer.util.shared.TSServiceException;
import com.tomsawyer.view.drawing.TSModelDrawingView;
import com.tomsawyer.web.client.command.TSCustomCommand;
import com.tomsawyer.web.server.command.TSServiceCommandImpl;
import com.tomsawyer.web.server.service.TSPerspectivesViewService;

/**
 * This command is used to check whether undo is allowed or not.
 * 
 * @author dijadhav
 * 
 */
public class UndoAllowedCommandImpl implements TSServiceCommandImpl {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tomsawyer.web.server.command.TSServiceCommandImpl#doAction(com.tomsawyer
	 * .web.server.service.TSPerspectivesViewService,
	 * com.tomsawyer.util.gwtclient.command.TSServiceCommand)
	 */
	@Override
	public Serializable doAction(TSPerspectivesViewService service,
			TSServiceCommand serviceCommand) throws TSServiceException {
		TSModelDrawingView drawingView = (TSModelDrawingView) service.getView(
				((TSCustomCommand) serviceCommand).getProjectID(),
				((TSCustomCommand) serviceCommand).getViewID(), false);
		boolean isUndoAllowd = false;
		if (drawingView != null) {
			isUndoAllowd = drawingView.getModel().getCommandManager().canUndo();
		}
		return isUndoAllowd;
	}

}
