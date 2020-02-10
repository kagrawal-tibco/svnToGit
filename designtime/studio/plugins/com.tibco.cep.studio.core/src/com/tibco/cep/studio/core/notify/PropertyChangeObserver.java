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
public class PropertyChangeObserver extends AbstractInstanceAssociationObserver implements INotifyChangedListener {
	

	
	public PropertyChangeObserver(final DesignerProject index, 
			                      final IFile targetFile) {
		super(index, targetFile);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.edit.provider.INotifyChangedListener#notifyChanged(org.eclipse.emf.common.notify.Notification)
	 */
	@Override
	public void notifyChanged(Notification notification) {
//		switch (notification.getFeatureID(PropertyDefinition.class)) {
//		case ElementPackage.PROPERTY_DEFINITION__DOMAIN_INSTANCES :
//			Object notifier = notification.getNotifier();
//			if (notifier instanceof PropertyDefinition) {
//				//Get its event type
//				int eventType = notification.getEventType();
//				
//				switch (eventType) {
//				case Notification.ADD : {
//					Object object = notification.getNewValue();
//					if (object instanceof DomainInstance) {
//						DomainInstance addedInstance = (DomainInstance)object;
//						//Update the index
//						addDomainInstance(addedInstance, targetFile);
//					}
//					break;
//				}
//				//TODO Remove not working properly
//				case Notification.REMOVE : {
//					Object object = notification.getNewValue();
//					if (object instanceof DomainInstance) {
//						DomainInstance removedInstance = (DomainInstance)object;
//						//Update the index
//						removeDomainInstance(removedInstance, targetFile);
//					}
//					break;
//			    }
//				}
//			}
//		}
	}
	
	/**
	 * Add a new {@link DomainInstance} to the index
	 * @param addedInstance
	 * @param file
	 */
//	private void addDomainInstance(DomainInstance addedInstance,
//                                   IFile file) {
//		if (index != null) {
//			InstanceElement<DomainInstance> instanceElement = IndexFactory.eINSTANCE
//						.createInstanceElement();
//			instanceElement.setElementType(ELEMENT_TYPES.DOMAIN_INSTANCE);
//			instanceElement.setInstance(addedInstance);
//			instanceElement.setName(file.getName());
//			List<InstanceElement<DomainInstance>> domainInstances = 
//				index.getDomainInstanceElements();
//			// Add to index if one doesnt exist
//			if (!contains(domainInstances, 
//					      instanceElement, 
//					      ELEMENT_TYPES.DOMAIN_INSTANCE)) {
//				domainInstances.add(instanceElement);
//				insertElement(instanceElement);
//			}
//		}
//	}
	
	/**
	 * Remove an existing {@link DomainInstance} from the index
	 * @param runtimeIndex
	 * @param removedInstance
	 * @param file
	 */
//	private void removeDomainInstance(DomainInstance removedInstance,
//                                      IFile file) {
//		if (index != null) {
//			//Remove from index
//			InstanceElement<DomainInstance> instanceElement = IndexFactory.eINSTANCE
//						.createInstanceElement();
//			instanceElement.setElementType(ELEMENT_TYPES.DOMAIN_INSTANCE);
//			instanceElement.setInstance(removedInstance);
//			instanceElement.setName(file.getName());
//			index.getDomainInstanceElements().remove(instanceElement);
//			removeElement(instanceElement);
//		}
//	}
}
