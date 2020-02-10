/**
 * 
 */
package com.tibco.cep.webstudio.client.process.handler;

import static com.tibco.cep.webstudio.client.process.ProcessConstants.drawingViewName;
import static com.tibco.cep.webstudio.client.process.ProcessConstants.moduleName;
import static com.tibco.cep.webstudio.client.process.ProcessConstants.projectID;

import java.util.LinkedList;

import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.BlurEvent;
import com.smartgwt.client.widgets.form.fields.events.BlurHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.process.AbstractProcessEditor;
import com.tomsawyer.web.client.command.TSCustomCommand;
import com.tomsawyer.web.client.view.TSWebModelViewCoordinators;

/**
 * This handler class is used to handle the change in general properties.
 * 
 * @author dijadhav
 * 
 */
public class GeneralPropertyChangedHandler implements ChangedHandler,
		BlurHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.smartgwt.client.widgets.form.fields.events.ChangedHandler#onChanged
	 * (com.smartgwt.client.widgets.form.fields.events.ChangedEvent)
	 */
	@Override
	public void onChanged(ChangedEvent event) {
		LinkedList<String> args = new LinkedList<String>();
		FormItem item = event.getItem();
		if(item instanceof TextItem){
			TextItem textItem =(TextItem) item;
			args.add((String) textItem.getValue());
		}else if(item instanceof CheckboxItem){
			CheckboxItem checkboxItem = (CheckboxItem) item; 
			args.add(checkboxItem.getValue().toString());
		}
		args.add((String) item.getName());
		invokeCommand(args);
	}

	private void invokeCommand(LinkedList<String> args) {
		AbstractProcessEditor processEditor = (AbstractProcessEditor) WebStudio
				.get().getEditorPanel().getSelectedTab();
		if (!processEditor.isDirty()) {
			processEditor.makeDirty();
		}
		TSCustomCommand populate = new TSCustomCommand(
				projectID,
				moduleName,
				processEditor.getModelId(),
				processEditor.getDrawingViewID(),
				drawingViewName,
				"com.tibco.cep.webstudio.server.command.GeneralPropertyUpdateCommandImpl",
				args);
		TSWebModelViewCoordinators.get(processEditor.getModelId()).invokeCommandAndUpdateAll(
				populate);
	}

	@Override
	public void onBlur(BlurEvent event) {
		LinkedList<String> args = new LinkedList<String>();
		FormItem item = event.getItem();
		TextItem textItem = (TextItem) item;
		args.add((String) textItem.getValue());
		args.add((String) item.getName());
		invokeCommand(args);
	}
}
