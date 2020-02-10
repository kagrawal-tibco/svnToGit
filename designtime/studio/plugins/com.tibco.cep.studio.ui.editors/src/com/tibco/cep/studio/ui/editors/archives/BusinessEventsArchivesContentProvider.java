package com.tibco.cep.studio.ui.editors.archives;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource;
import com.tibco.cep.designtime.core.model.archive.EnterpriseArchive;

/**
 * 
 * @author sasahoo
 *
 */
public class BusinessEventsArchivesContentProvider implements IStructuredContentProvider {
	public Object[] getElements(Object inputElement) {
		
		if(inputElement instanceof EnterpriseArchive){
			EList<BusinessEventsArchiveResource> businessEventsArchives= ((EnterpriseArchive)inputElement).getBusinessEventsArchives();
			return businessEventsArchives.toArray();
		}
		
		return new Object[0];
	}
	public void dispose() {
	}
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}
}