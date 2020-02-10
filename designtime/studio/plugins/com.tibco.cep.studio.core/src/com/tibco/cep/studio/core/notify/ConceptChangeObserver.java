/**
 * 
 */
package com.tibco.cep.studio.core.notify;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.provider.INotifyChangedListener;

import com.tibco.cep.studio.core.index.model.DesignerProject;

/**
 * @author aathalye
 *
 */
public class ConceptChangeObserver extends AbstractInstanceAssociationObserver implements
		INotifyChangedListener {
	
	
	public ConceptChangeObserver(final DesignerProject index, 
                                 final IFile targetFile) {
		super(index, targetFile);
		
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.emf.edit.provider.INotifyChangedListener#notifyChanged(org.eclipse.emf.common.notify.Notification)
	 */
	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.notify.impl.AdapterImpl#notifyChanged(org.eclipse.emf.common.notify.Notification)
	 */
	@Override
	public void notifyChanged(Notification notification) {
//		switch (notification.getFeatureID(Concept.class)) {
//		case ElementPackage.CONCEPT__STATE_MACHINES :
//			Object notifier = notification.getNotifier();
//			if (notifier instanceof Concept) {
//				//Get its event type
//				int eventType = notification.getEventType();
//				switch (eventType) {
//				case Notification.ADD : {
//					Object object = notification.getNewValue();
//					if (object instanceof SMInstance) {
//						SMInstance addedInstance = (SMInstance)object;
//						//Update the index
//						addStateMachineInstance(addedInstance);
//						break;
//					}
//				}
//				case Notification.REMOVE : {
//					Object object = notification.getNewValue();
//					if (object instanceof SMInstance) {
//						SMInstance addedInstance = (SMInstance)object;
//						//Update the index
//						removeStateMachineInstance(addedInstance);
//						break;
//					}
//				}
//			}
//		}
//		}
	}
	
}
