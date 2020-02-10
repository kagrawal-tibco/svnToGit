package com.tibco.cep.bpmn.core.index.update;

import com.tibco.cep.studio.core.index.update.IStudioElementDelta;

public interface IBpmnModelChangedListener {

	/**
	 * Notifies client that a StudioProject has been added,
	 * changed, or deleted.  This change event contains a 
	 * <code>StudioModelDelta</code> with all affected 
	 * StudioProjects<br>
	 * NOTE: This could be called from a non-UI thread, so
	 * care must be taken if UI updates are done based on this
	 * change event.
	 * @see IStudioElementDelta.ADDED
	 * @see IStudioElementDelta.CHANGED
	 * @see IStudioElementDelta.REMOVED
	 * 
	 * @param event
	 */
	public void modelChanged(BpmnModelChangedEvent event);
	
}
