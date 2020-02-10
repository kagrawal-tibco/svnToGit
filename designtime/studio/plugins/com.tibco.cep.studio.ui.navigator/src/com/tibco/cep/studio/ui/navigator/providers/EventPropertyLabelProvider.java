package com.tibco.cep.studio.ui.navigator.providers;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.navigator.IDescriptionProvider;

import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.navigator.model.EventNavigatorNode;
import com.tibco.cep.studio.ui.navigator.model.EventPropertyNode;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

public class EventPropertyLabelProvider implements ILabelProvider,IDescriptionProvider  {

	public Image getImage(Object element) {
		if (element instanceof EventPropertyNode) {
			EventPropertyNode propNode = (EventPropertyNode) element;
			return StudioUIUtils.getPropertyImage(propNode.getType());
		}
		return null;
	}

	public String getText(Object element) {
		if (element instanceof EventNavigatorNode) {
			return ((EventNavigatorNode)element).getName();
		}
		return null;
	}

	public void addListener(ILabelProviderListener listener) {
	}

	public void dispose() {
	}

	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	public void removeListener(ILabelProviderListener listener) {
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.navigator.providers.EntityLabelProvider#getDescription(java.lang.Object)
	 */
	@Override
	public String getDescription(Object anElement) {
		if (anElement instanceof EventPropertyNode) {
			EventPropertyNode propNode = (EventPropertyNode) anElement;
			PropertyDefinition propertyDefinition = (PropertyDefinition)propNode.getEntity();
			Event event = (Event)propertyDefinition.eContainer();
			String name = event.getFullPath()+"." +IndexUtils.getFileExtension(event)+"/"+propNode.getName();
			return name;
		}
		return null;
	}

}
