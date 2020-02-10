package com.tibco.cep.studio.ui.navigator.providers;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.archive.ArchiveResource;
import com.tibco.cep.designtime.core.model.archive.EnterpriseArchive;
import com.tibco.cep.studio.ui.navigator.model.ArchiveNode;

public class EnterpriseArchiveContentProvider extends EntityContentProvider {

	public boolean hasChildren(Object element) {
		if (!(element instanceof IFile)) {
			return false;
		}
		return true;
	}

	@Override
	protected Object[] getObjectChildren(EObject entity) {
		if (!(entity instanceof EnterpriseArchive)) {
			return EMPTY_CHILDREN;
		}
		EnterpriseArchive ear = (EnterpriseArchive) entity;
		EList<ArchiveResource> archives = new BasicEList<ArchiveResource>();
		archives.addAll(ear.getBusinessEventsArchives());
		archives.addAll(ear.getProcessArchives());
		archives.addAll(ear.getSharedArchives());
		
		ArchiveNode[] attributes = new ArchiveNode[archives.size()];
		for (int i = 0; i < archives.size(); i++) {
			attributes[i] = new ArchiveNode(archives.get(i));
		}
		
		return attributes;
	}

	@Override
	protected Object[] getEntityChildren(Entity entity, boolean isSharedElement) {
		return EMPTY_CHILDREN;
	}

}
