package com.tibco.cep.studio.decision.table.ui.navigation;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.decisionproject.ontology.provider.OntologyItemProviderAdapterFactory;
import com.tibco.cep.studio.decision.table.ui.DecisionTableUIPlugin;

/**
 * 
 * @author sasahoo
 *
 */
public abstract class AbstractResourceLabelProvider implements ILabelProvider {

	protected AdapterFactoryLabelProvider adapterFactoryLabelProvider = null;
	
	/**
	 * @return
	 */
	protected AdapterFactoryLabelProvider getAdapterFactoryLabelProvider(){
		if(adapterFactoryLabelProvider == null){
			adapterFactoryLabelProvider = 
				new AdapterFactoryLabelProvider(new OntologyItemProviderAdapterFactory());
		}
		return adapterFactoryLabelProvider;
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @param image
	 * @return
	 */
	protected Image getOverlayImage(Image image){
		return new DecorationOverlayIcon(image, 
				DecisionTableUIPlugin.getImageDescriptor("icons/argument.png"),IDecoration.TOP_LEFT).createImage();
		
	}
}