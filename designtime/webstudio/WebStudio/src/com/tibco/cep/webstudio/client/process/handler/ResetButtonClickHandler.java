package com.tibco.cep.webstudio.client.process.handler;

import static com.tibco.cep.webstudio.client.process.ProcessConstants.drawingViewName;
import static com.tibco.cep.webstudio.client.process.ProcessConstants.moduleName;
import static com.tibco.cep.webstudio.client.process.ProcessConstants.projectID;

import java.util.LinkedList;

import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.process.AbstractProcessEditor;
import com.tibco.cep.webstudio.client.process.ProcessConstants;
import com.tomsawyer.web.client.command.TSCustomCommand;
import com.tomsawyer.web.client.view.TSWebModelViewCoordinators;

/**
 * This class is used to handle the click event on Reset button in general
 * properties of text annotation.
 * 
 * @author dijadhav
 * 
 */
public class ResetButtonClickHandler implements ClickHandler {

	private String value = "Note";
	private IButton applyButton;
	private IButton resetButton;
	private TextAreaItem textArea;

	public ResetButtonClickHandler(String value, IButton applyButton,
			IButton resetButton, TextAreaItem textArea) {
		this.value = value;
		this.applyButton = applyButton;
		this.resetButton = resetButton;
		this.textArea = textArea;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event
	 * .dom.client.ClickEvent)
	 */
	@Override
	public void onClick(ClickEvent event) {
		this.textArea.setValue(this.value);
		this.applyButton.setDisabled(true);
		this.resetButton.setDisabled(true);
		LinkedList<String> args = new LinkedList<String>();
		args.add((String) value);
		args.add(ProcessConstants.TEXT);
		AbstractProcessEditor processEditor = (AbstractProcessEditor) WebStudio
				.get().getEditorPanel().getSelectedTab();
		if (!processEditor.isDirty()) {
			processEditor.makeDirty();
		}
		TSCustomCommand populate = new TSCustomCommand(
				projectID,
				moduleName,
				processEditor.getModelId(),
				"view#" + processEditor.getProcessName(),
				drawingViewName,
				"com.tibco.cep.webstudio.server.command.GeneralPropertyUpdateCommandImpl",
				args);
		TSWebModelViewCoordinators.get(processEditor.getModelId()).invokeCommandAndUpdateAll(
				populate);
	}

}
