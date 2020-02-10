package com.tibco.cep.studio.ui.editors.events;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.designtime.core.model.event.TimeEvent;

public class TimeEventFormEditorInput extends FileEditorInput {

	@SuppressWarnings("unused")
	private IFile file;
	
	private TimeEvent event;
	
	public TimeEvent getTimeEvent() {
		return event;
	}

	public void setTimeEvent(TimeEvent event) {
		this.event = event;
	}
	
	public TimeEventFormEditorInput(IFile file, TimeEvent event) {
		super(file);
		this.file = file;
		this.event = event;
	}
}
