package com.tibco.cep.studio.ui.forms;

import org.eclipse.core.resources.IFile;

import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.event.TimeEvent;
import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;
import com.tibco.cep.studio.ui.editors.events.EventFormEditorInput;
import com.tibco.cep.studio.ui.editors.events.TimeEventFormEditorInput;

/**
 * 
 * @author sasahoo
 *
 */
public abstract class AbstractEventSaveableEditorPart extends AbstractSaveableEntityEditorPart{

    protected SimpleEvent simpleEvent;
    protected TimeEvent timeEvent;
    protected IFile file = null;
    protected TimeEventFormEditorInput timeEventFormEditorInput;
    protected EventFormEditorInput simpleEventFormEditorInput;
    
    public SimpleEvent getSimpleEvent() {
		return (SimpleEvent) getEntity();
	}
    
    public TimeEvent getTimeEvent() {
		return (TimeEvent)getEntity();
	}
    
    /**
	 * This is how the framework determines which interfaces we implement.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getAdapter(Class key) {
		if (key.equals(SimpleEvent.class))
			return getSimpleEvent();
		if (key.equals(TimeEvent.class))
			return getSimpleEvent();
		return super.getAdapter(key);
	}
	
	public void setSimpleEvent(SimpleEvent simpleEvent) {
		this.simpleEvent = simpleEvent;
	}
	
	public void setTimeEvent(TimeEvent timeEvent) {
		this.timeEvent = timeEvent;
	}
}