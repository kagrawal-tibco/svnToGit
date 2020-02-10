package com.tibco.cep.studio.dashboard.ui.navigator.providers;

import org.eclipse.swt.graphics.Image;

import com.tibco.cep.designtime.core.model.element.Metric;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.navigator.providers.EntityLabelProvider;
import com.tibco.cep.studio.ui.util.StudioUIUtils;


public class MetricAttributeLabelProvider extends EntityLabelProvider {

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.navigator.providers.EntityLabelProvider#getImage(java.lang.Object)
	 */
	public Image getImage(Object element) {
		if (element instanceof MetricPropertyNode) {
			MetricPropertyNode propNode = (MetricPropertyNode) element;
			return StudioUIUtils.getPropertyImage(propNode.getType());
		}
		return super.getImage(element);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.navigator.providers.EntityLabelProvider#getText(java.lang.Object)
	 */
	public String getText(Object element) {
		if (element instanceof MetricPropertyNode) {
			MetricPropertyNode propNode = (MetricPropertyNode) element;
			return propNode.getName();
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.navigator.providers.EntityLabelProvider#getDescription(java.lang.Object)
	 */
	@Override
	public String getDescription(Object anElement) {
		if (anElement instanceof MetricPropertyNode) {
			MetricPropertyNode propNode = (MetricPropertyNode) anElement;
			PropertyDefinition propertyDefinition = (PropertyDefinition)propNode.getEntity();
			Metric metric = (Metric)propertyDefinition.eContainer();
			String name = metric.getFullPath()+"." +IndexUtils.getFileExtension(metric)+"/"+propNode.getName();
			return name;
		}
		return null;
	}
}
