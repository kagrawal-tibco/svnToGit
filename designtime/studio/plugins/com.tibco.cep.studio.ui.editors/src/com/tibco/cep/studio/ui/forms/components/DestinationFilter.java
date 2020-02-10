package com.tibco.cep.studio.ui.forms.components;

import java.util.Set;

import org.eclipse.core.resources.IResource;

import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.studio.ui.StudioNavigatorNode;
import com.tibco.cep.studio.ui.wizards.OnlyFileInclusionFilter;

public class DestinationFilter extends OnlyFileInclusionFilter {
	
	public DestinationFilter(Set<String> inclusions) {
		super(inclusions);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.wizards.OnlyFileInclusionFilter#showSubNode(java.lang.Object)
	 */
	protected boolean showSubNode(Object element){
		if(!(element instanceof IResource)){
			if (element instanceof StudioNavigatorNode) {
				element = ((StudioNavigatorNode) element).getEntity();
			}
			if (element instanceof Destination) {
				return true;
			}
			return false;
		}
		return true;
	}
}
