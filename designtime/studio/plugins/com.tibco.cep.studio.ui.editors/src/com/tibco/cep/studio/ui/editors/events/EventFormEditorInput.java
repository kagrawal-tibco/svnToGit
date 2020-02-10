package com.tibco.cep.studio.ui.editors.events;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.designtime.core.model.event.SimpleEvent;

/**
 * @author sasahoo
 */
public class EventFormEditorInput extends FileEditorInput {

	@SuppressWarnings("unused")
	private IFile file;
	
	private SimpleEvent event;
	
	public SimpleEvent getSimpleEvent() {
		return event;
	}

	public void setSimpleEvent(SimpleEvent event) {
		this.event = event;
	}
	
	public EventFormEditorInput(IFile file, SimpleEvent event) {
		super(file);
		this.file = file;
		this.event = event;
	}
}
