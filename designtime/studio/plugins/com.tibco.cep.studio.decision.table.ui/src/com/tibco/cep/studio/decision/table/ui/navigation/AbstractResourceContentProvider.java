package com.tibco.cep.studio.decision.table.ui.navigation;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.cep.decisionproject.ontology.provider.OntologyItemProviderAdapterFactory;

/**
 * 
 * @author sasahoo
 *
 */
public abstract class AbstractResourceContentProvider implements ITreeContentProvider {
	
	protected AdapterFactoryContentProvider adapterFactoryContentProvider = null;
	protected static final Object[] EMPTY_CHILDREN = new Object[0];
	
	/**
	 * @return
	 */
	protected AdapterFactoryContentProvider getAdapterFactoryContentProvider() {
		if(adapterFactoryContentProvider == null){
			adapterFactoryContentProvider = new AdapterFactoryContentProvider(
					new OntologyItemProviderAdapterFactory());
		}
		return adapterFactoryContentProvider;
	}
	
	@Override
	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return true;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub
		
	}
}