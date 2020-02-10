package com.tibco.cep.studio.ui.notification;

import javax.swing.event.TableModelEvent;

import org.eclipse.emf.common.notify.Notification;

import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.event.EventPackage;
import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;

/**
 * 
 * @author sasahoo
 *
 */
public class EventAdapterImpl extends ModelAdapterImpl{
	
	/**
	 * @param editor
	 */
	public EventAdapterImpl( AbstractSaveableEntityEditorPart editor){
		super(editor);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.notify.impl.AdapterImpl#notifyChanged(org.eclipse.emf.common.notify.Notification)
	 */
	@Override
	public void notifyChanged(Notification notification) {
		int featureId = notification.getFeatureID(Event.class);
		if (featureId == EventPackage.EVENT__PROPERTIES) {
			if (notification.getEventType() == Notification.ADD) {
				if(editor.getPropertyTableModifictionType() == TableModelEvent.UPDATE){
					return;
				}
				asyncModified();
			}
			if (notification.getEventType() == Notification.REMOVE || notification.getEventType() == Notification.REMOVE_MANY ) {
				asyncModified();
			}
			if (notification.getEventType() == Notification.SET) {
				asyncModified();
			}
		}
	}

}
