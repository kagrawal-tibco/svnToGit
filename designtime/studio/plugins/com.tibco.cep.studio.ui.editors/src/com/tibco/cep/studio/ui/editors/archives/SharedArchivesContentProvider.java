package com.tibco.cep.studio.ui.editors.archives;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.cep.designtime.core.model.archive.EnterpriseArchive;
import com.tibco.cep.designtime.core.model.archive.SharedArchive;

/**
 * 
 * @author sasahoo
 *
 */
public class SharedArchivesContentProvider implements IStructuredContentProvider {
	public Object[] getElements(Object inputElement) {
		
		if(inputElement instanceof EnterpriseArchive){
			EList<SharedArchive> sharedArchives= ((EnterpriseArchive)inputElement).getSharedArchives();
			return sharedArchives.toArray();
		}
		
		return new Object[0];
	}
	public void dispose() {
	}
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}
}