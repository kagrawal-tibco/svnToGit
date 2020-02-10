package com.tibco.cep.webstudio.client.process.handler;

import static com.tibco.cep.webstudio.client.process.ProcessConstants.drawingViewName;
import static com.tibco.cep.webstudio.client.process.ProcessConstants.moduleName;
import static com.tibco.cep.webstudio.client.process.ProcessConstants.projectID;

import java.util.LinkedList;

import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.events.BlurEvent;
import com.smartgwt.client.widgets.form.fields.events.BlurHandler;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.process.AbstractProcessEditor;
import com.tomsawyer.web.client.command.TSCustomCommand;
import com.tomsawyer.web.client.view.TSWebModelViewCoordinators;

/**
 * This class is used to handle the process ,node and edge documentation related
 * changes
 * 
 * @author dijadhav
 * 
 */
public class DocumentationChangedHandler implements BlurHandler {
	private String type;

	public DocumentationChangedHandler(String type) {
		this.type = type;
	}

	public DocumentationChangedHandler() {
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public void onBlur(BlurEvent event) {


		// Get documentation text
		TextAreaItem textAreaItem = (TextAreaItem) event.getItem();
		LinkedList<String> args = new LinkedList<String>();
		args.add((String) textAreaItem.getValue());
		args.add((String) this.type);

		// Make editor as dirty if it is not dirty
		AbstractProcessEditor processEditor = (AbstractProcessEditor) WebStudio
				.get().getEditorPanel().getSelectedTab();
		if (!processEditor.isDirty()) {
			processEditor.makeDirty();
		}

		// Invoke the command
		TSCustomCommand populate = new TSCustomCommand(
				projectID,
				moduleName,
				processEditor.getModelId(),
				"view#" + processEditor.getProcessName(),
				drawingViewName,
				"com.tibco.cep.webstudio.server.command.DocumentationPropertyUpdateCommandImpl",
				args);
		TSWebModelViewCoordinators.get(processEditor.getModelId()).invokeCommandAndUpdateAll(
				populate);		
	}	
}
