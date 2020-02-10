package com.tibco.cep.decisionproject.adapter;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EContentAdapter;

import com.tibco.cep.decision.table.model.domainmodel.Domain;
import com.tibco.cep.decision.table.model.domainmodel.DomainEntry;

public class DomainModelAdapter extends EContentAdapter {

	@Override
	public void notifyChanged(Notification notification) {
		Object notifier = (Object)notification.getNotifier();
		if (notifier instanceof DomainEntry){
			DomainEntry domainEntry = (DomainEntry)notifier;
			EObject container = domainEntry.eContainer();
			if (container instanceof Domain){
				Domain domain = (Domain)container;
				if (!domain.isModified()){
					domain.setModified(true);
				}
			}
		}
		else if (notifier instanceof Domain){
			Domain domain = (Domain)notifier;
			if (!domain.isModified()){
				domain.setModified(true);
			}
		}
	}
	
	

}
