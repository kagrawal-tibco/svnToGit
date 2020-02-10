package com.tibco.cep.webstudio.server.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.webstudio.client.process.ProcessConstants;
import com.tomsawyer.util.gwtclient.command.TSServiceCommand;
import com.tomsawyer.util.shared.TSAttributedObject;
import com.tomsawyer.util.shared.TSServiceException;
import com.tomsawyer.web.client.command.TSCustomCommand;
import com.tomsawyer.web.client.view.data.TSWebViewClientCommandData;
import com.tomsawyer.web.server.TSProjectSessionInfo;
import com.tomsawyer.web.server.command.TSServiceCommandImpl;
import com.tomsawyer.web.server.drawing.TSWebDrawingViewServer;
import com.tomsawyer.web.server.service.TSPerspectivesViewService;

/**
 * This class is used to check whether the delete is allowed or not.
 * 
 * @author dijadhav
 * 
 */
public class ProcessElementDeleteAllowedCommandImpl implements
		TSServiceCommandImpl {

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

		// create the web view client command data
		TSWebViewClientCommandData result = new TSWebViewClientCommandData();

		result.setCommand(ProcessConstants.NO_NATIVE_CALL);

		List<Serializable> commandData = new ArrayList<Serializable>();

		/**
		 * Get the DrawingView
		 */
		TSWebDrawingViewServer drawingView = null;
		try {
			TSProjectSessionInfo projectInfo = service
					.getProjectSessionInfo(customCommand.getProjectID());
			drawingView = (TSWebDrawingViewServer) projectInfo
					.getView(customCommand.getViewID());
		} catch (Exception exception) {
			// TODO: handle exception
		}

		if (drawingView != null) {
			List<TSAttributedObject> selectedObjects = drawingView
					.getSelectedAttributedObjects();
			if (null == selectedObjects || selectedObjects.isEmpty()) {
				commandData.add(Boolean.FALSE);
			} else {
				commandData.add(Boolean.TRUE);
			}
			result.setCommandData(commandData);
		}

		return result;
	}

}
