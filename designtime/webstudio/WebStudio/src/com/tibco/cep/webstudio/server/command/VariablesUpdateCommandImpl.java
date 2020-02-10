package com.tibco.cep.webstudio.server.command;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

import com.tibco.cep.webstudio.server.model.process.Process;
import com.tibco.cep.webstudio.server.model.process.Properties;
import com.tibco.cep.webstudio.server.model.process.Property;
import com.tibco.cep.webstudio.server.ui.ProcessWebDiagramDataRepository;
import com.tomsawyer.util.gwtclient.command.TSServiceCommand;
import com.tomsawyer.util.shared.TSServiceException;
import com.tomsawyer.web.client.command.TSCustomCommand;
import com.tomsawyer.web.server.command.TSServiceCommandImpl;
import com.tomsawyer.web.server.drawing.TSWebDrawingViewServer;
import com.tomsawyer.web.server.service.TSPerspectivesViewService;

/**
 * This class is used to update the process variables/properties
 * 
 * @author dijadhav
 * 
 */
public class VariablesUpdateCommandImpl implements TSServiceCommandImpl {

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
		List<String> args = customCommand.getCustomArgs();
		if (args == null || args.isEmpty()) {
			throw new TSServiceException("No arguments found for Process");
		}
		if (args.size() != 1) {
			throw new TSServiceException("Invalid number of arguments.");
		}
		String value = args.get(0);

		/**
		 * Get the DrawingView
		 */
		TSWebDrawingViewServer drawingView = (TSWebDrawingViewServer) service
				.getView(((TSCustomCommand) customCommand).getProjectID(),
						((TSCustomCommand) customCommand).getViewID(), false);
		if (null != drawingView) {
			Process process = ProcessWebDiagramDataRepository.getInstance()
					.getProcess(((TSCustomCommand) customCommand).getViewID());
			Properties properties = process.getProperties();

			if (null != value && !value.trim().isEmpty()) {
				String[] propertyArr = value.split(",");

				List<Property> newProperties = new LinkedList<Property>();
				for (String property : propertyArr) {
					String[] propertyFields = property.split("#");

					Property variable = new Property();
					variable.setId(new BigInteger(propertyFields[0]));
					variable.setName(propertyFields[1]);
					variable.setType(propertyFields[2]);
					if ("ContainedConcept".equals(propertyFields[2])
							|| "ConceptReference".equals(propertyFields[2])) {
						variable.setPath(propertyFields[3]);
					}
					variable.setIsMultiple(Boolean
							.getBoolean(propertyFields[4]));
					newProperties.add(variable);
				}
				List<Property> propertyList = properties.getProperty();
				properties.getProperty().removeAll(propertyList);
				properties.getProperty().addAll(newProperties);
			} else {
				List<Property> propertyList = properties.getProperty();
				properties.getProperty().removeAll(propertyList);
			}
		}
		return Boolean.TRUE;
	}
}
