package com.tibco.cep.webstudio.server.command;

import java.io.Serializable;
import java.util.List;

import com.tibco.cep.webstudio.client.process.ProcessConstants;
import com.tomsawyer.drawing.geometry.shared.TSSize;
import com.tomsawyer.model.TSModelElement;
import com.tomsawyer.util.gwtclient.command.TSServiceCommand;
import com.tomsawyer.util.shared.TSAttributedObject;
import com.tomsawyer.util.shared.TSServiceException;
import com.tomsawyer.web.client.command.TSCustomCommand;
import com.tomsawyer.web.server.command.TSServiceCommandImpl;
import com.tomsawyer.web.server.drawing.TSWebDrawingViewServer;
import com.tomsawyer.web.server.service.TSPerspectivesViewService;

/**
 * This command class is used to process the loop characteristic related
 * chnages.
 * 
 * @author dijadhav
 * 
 */
public class LoopCharacteristicsUpdateCommandImpl implements
		TSServiceCommandImpl {

	boolean isLoop = false;
	boolean isMultiple = false;
	boolean isCompensation = false;
	boolean isSequential = false;
	protected static final int DEFAULT_ARC_SIZE = 11;
	protected static final TSSize DEFAULT_ARC_TS_SIZE = new TSSize(
			DEFAULT_ARC_SIZE, DEFAULT_ARC_SIZE);
	public static final int DEFAULT_BREAKPOINT_OFFSET = 2;
	public static final int DEFAULT_BREAKPOINT_SIZE = 8;
	public static final int DEFAULT_SHADOW_WIDTH = 5;

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
			TSServiceCommand command) throws TSServiceException {
		TSCustomCommand customCommand = (TSCustomCommand) command;
		List<String> args = customCommand.getCustomArgs();
		if (args == null || args.isEmpty()) {
			throw new TSServiceException("No arguments found");
		}
		String type = args.get(0);
		String value = args.get(1);
		/**
		 * Get the DrawingView
		 */
		TSWebDrawingViewServer drawingView = (TSWebDrawingViewServer) service
				.getView(((TSCustomCommand) customCommand).getProjectID(),
						((TSCustomCommand) customCommand).getViewID(), false);

		if (drawingView != null) {

			List<TSAttributedObject> selectedObjects = drawingView
					.getSelectedAttributedObjects();
			if (null != selectedObjects && !selectedObjects.isEmpty()) {
				TSModelElement element = (TSModelElement) selectedObjects.get(0);
				element.setAttribute(ProcessConstants.LOOP, value);
				drawingView.updateView(element);
			}
		}
		
		return Boolean.TRUE;
	}

}
