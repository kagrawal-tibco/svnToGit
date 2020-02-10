package com.tibco.cep.studio.ui.filter;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
/**
 * 
 * @author smarathe
 *
 */
public class EventNonPayloadFilter extends ViewerFilter {

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if(element instanceof IResource) {
			IResource resource = (IResource) element;
			Entity entity = IndexUtils.getEntity(resource.getProject().getName(), resource.getFullPath().toOSString());
			if(entity instanceof Event) {
				Event event = (Event)entity;
				if(event.isSoapEvent()) {
					return false;
				} else if(event.getPayload() != null) {
					return false;
				}
			}
		} else if (element instanceof SharedEntityElement && ((SharedEntityElement) element).getElementType() == ELEMENT_TYPES.SIMPLE_EVENT) {
			Event event = (Event) ((SharedEntityElement) element).getEntity();
			if(event.isSoapEvent()) {
				return false;
			} else if(event.getPayloadString() != null) {
				return false;
			}
		}
		
		return true;
	}

}
