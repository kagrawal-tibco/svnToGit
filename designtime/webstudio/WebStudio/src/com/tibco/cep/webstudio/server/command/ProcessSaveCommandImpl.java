package com.tibco.cep.webstudio.server.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tibco.cep.webstudio.server.model.process.Process;
import com.tibco.cep.webstudio.server.ui.ProcessWebDiagramDataRepository;
import com.tibco.cep.webstudio.server.ui.utils.ProcessDataSerializer;
import com.tomsawyer.util.gwtclient.command.TSServiceCommand;
import com.tomsawyer.util.shared.TSServiceException;
import com.tomsawyer.web.client.command.TSCustomCommand;
import com.tomsawyer.web.client.view.data.TSWebViewClientCommandData;
import com.tomsawyer.web.server.command.TSServiceCommandImpl;
import com.tomsawyer.web.server.service.TSPerspectivesViewService;

/**
 * 
 * @author sasahoo
 *
 */
public class ProcessSaveCommandImpl implements TSServiceCommandImpl {

	protected String projectName;
	protected String processArtifactPath;
	protected String artifactExtention;

	/**
	 * Default Constructor
	 */
	public ProcessSaveCommandImpl() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tomsawyer.web.server.command.TSServiceCommandImpl#doAction(com.tomsawyer
	 * .web.server.service.TSPerspectivesViewService,
	 * com.tomsawyer.util.gwtclient.command.TSServiceCommand)
	 */
	public Serializable doAction(TSPerspectivesViewService service,
			TSServiceCommand command) throws TSServiceException {
		TSCustomCommand customCommand = (TSCustomCommand) command;
		List<String> args = customCommand.getCustomArgs();
		if (args == null || args.isEmpty()) {
			throw new TSServiceException("No arguments found for Process");
		} /*
		 * else if (args.size() != 3) { throw new
		 * TSServiceException("Wrong number of arguments found for Process"); }
		 */

		projectName = args.get(0);
		processArtifactPath = args.get(1);
		artifactExtention = args.get(2);

		TSWebViewClientCommandData result = new TSWebViewClientCommandData();
		List<Serializable> commandData = new ArrayList<Serializable>();
		Process process = ProcessWebDiagramDataRepository.getInstance()
				.getProcess(((TSCustomCommand) customCommand).getViewID());
		process.setLastModificationDate(new Date().toString());
		String processData;
		try {
			processData = ProcessDataSerializer.serialize(process);		
			commandData.add(processData);
			result.setCommandData(commandData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
