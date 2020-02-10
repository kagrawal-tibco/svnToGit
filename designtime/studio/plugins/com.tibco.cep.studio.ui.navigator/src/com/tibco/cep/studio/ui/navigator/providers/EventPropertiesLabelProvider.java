package com.tibco.cep.studio.ui.navigator.providers;

import org.eclipse.swt.graphics.Image;

import com.tibco.cep.studio.ui.navigator.NavigatorPlugin;
import com.tibco.cep.studio.ui.navigator.model.DomainInstanceNode;

/**
 * 
 * @author sasahoo
 *
 */
public class EventPropertiesLabelProvider extends EntityLabelProvider {

	public Image getImage(Object element) {
		if (element instanceof DomainInstanceNode) {
			return NavigatorPlugin.getDefault().getImage("icons/associateDomainModel.png");
		}
		return super.getImage(element);
	}

	public String getText(Object element) {
		if (element instanceof DomainInstanceNode) {
			return ((DomainInstanceNode)element).getPath();
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.navigator.providers.EntityLabelProvider#getDescription(java.lang.Object)
	 */
	@Override
	public String getDescription(Object anElement) {
		if (anElement instanceof DomainInstanceNode) {
			return ((DomainInstanceNode)anElement).getPath();
		}
		return null;
	}
}
