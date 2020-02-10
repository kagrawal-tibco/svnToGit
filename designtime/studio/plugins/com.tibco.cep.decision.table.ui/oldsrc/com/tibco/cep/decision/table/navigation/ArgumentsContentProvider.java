package com.tibco.cep.decision.table.navigation;

import com.tibco.cep.decisionproject.ontology.ParentResource;

/**
 * 
 * @author sasahoo
 *
 */
public class ArgumentsContentProvider extends AbstractResourceContentProvider {
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 */
	public Object [] getChildren(Object object)
	{
		if (object instanceof ParentResource) {
			try {
				return 	getAdapterFactoryContentProvider().getChildren((ParentResource)object);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return EMPTY_CHILDREN;
	}
}
