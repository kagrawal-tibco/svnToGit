package com.tibco.cep.studio.ui.editors.domain;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.swt.widgets.Display;

import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.designtime.core.model.domain.DomainPackage;
import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;

/**
 * 
 * @author sasahoo
 *
 */
public class DomainAdapterImpl extends AdapterImpl{
	
	private  AbstractSaveableEntityEditorPart editor;
	public DomainAdapterImpl( AbstractSaveableEntityEditorPart editor){
		this.editor = editor;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.notify.impl.AdapterImpl#notifyChanged(org.eclipse.emf.common.notify.Notification)
	 */
	@Override
    public void notifyChanged(Notification notification) {
		   int featureId = notification.getFeatureID(Domain.class);
           if (featureId == DomainPackage.DOMAIN__ENTRIES) {
        	   if (notification.getEventType() == Notification.ADD) {
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
	
	private void modified(){
		editor.modified();
	}
	
	private void asyncModified(){
		Display.getDefault().asyncExec(new Runnable(){
			public void run() {
			 	 modified();
				
		}});
	}
}
