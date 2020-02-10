package com.tibco.cep.webstudio.server.command;

import java.io.Serializable;

import com.tomsawyer.util.gwtclient.command.TSServiceCommand;
import com.tomsawyer.util.shared.TSServiceException;
import com.tomsawyer.web.client.command.TSCustomCommand;
import com.tomsawyer.web.server.command.TSServiceCommandImpl;
import com.tomsawyer.web.server.drawing.TSWebDrawingViewServer;
import com.tomsawyer.web.server.service.TSPerspectivesViewService;

/**
 * This method is used to clear the undo history when view is closed.
 * 
 * @author dijadhav
 * 
 */
public class ClearUndoHistoryCommandImpl implements TSServiceCommandImpl {

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
		TSCustomCommand customCommand = (TSCustomCommand) serviceCommand;

		/**
		 * Get the DrawingView
		 */
		TSWebDrawingViewServer drawingView = (TSWebDrawingViewServer) service
				.getView(((TSCustomCommand) customCommand).getProjectID(),
						((TSCustomCommand) customCommand).getViewID(), false);

		if (drawingView != null) {
			drawingView.getModel().getCommandManager().clearUndoHistory();
		}
		return Boolean.TRUE;
	}

}
