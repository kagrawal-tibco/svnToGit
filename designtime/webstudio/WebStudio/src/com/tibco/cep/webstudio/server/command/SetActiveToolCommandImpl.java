package com.tibco.cep.webstudio.server.command;

import java.io.Serializable;

import com.tibco.cep.webstudio.server.ui.ProcessActiveToolRepository;
import com.tomsawyer.util.gwtclient.command.TSServiceCommand;
import com.tomsawyer.util.shared.TSServiceException;
import com.tomsawyer.web.client.command.TSCustomCommand;
import com.tomsawyer.web.server.command.TSServiceCommandImpl;
import com.tomsawyer.web.server.service.TSPerspectivesViewService;

/**
 * This class is used to to set the active tool related data in the model.
 * 
 * @author sasahoo.dijadhav
 * 
 */
public class SetActiveToolCommandImpl implements TSServiceCommandImpl {

	@Override
	public Serializable doAction(TSPerspectivesViewService service,
			TSServiceCommand command) throws TSServiceException {
		TSCustomCommand customCommand = (TSCustomCommand) command;
		ProcessActiveToolRepository activeToolManager = ProcessActiveToolRepository
				.getInstance();
		activeToolManager.setActiveToolEmfType(customCommand.getCustomArgs()
				.get(0));
		activeToolManager.setActiveToolExtendedType(customCommand
				.getCustomArgs().get(1));
		activeToolManager.setActiveToolType(customCommand.getCustomArgs()
				.get(2));

		activeToolManager.setArtifactName(customCommand.getCustomArgs().get(3));
		activeToolManager.setArifactPath(customCommand.getCustomArgs().get(4));
		activeToolManager.setViewId(customCommand.getViewID());
		return Boolean.TRUE;
	}
}