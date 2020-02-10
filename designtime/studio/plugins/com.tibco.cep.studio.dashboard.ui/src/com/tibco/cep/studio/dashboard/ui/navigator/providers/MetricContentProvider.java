package com.tibco.cep.studio.dashboard.ui.navigator.providers;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Metric;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.attribute.LocalAttribute;
import com.tibco.cep.studio.dashboard.core.model.impl.metric.LocalMetric;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;
import com.tibco.cep.studio.ui.StudioNavigatorNode;
import com.tibco.cep.studio.ui.navigator.providers.ConceptContentProvider;

public class MetricContentProvider extends ConceptContentProvider {

	@Override
	protected Object[] getEntityChildren(Entity entity, boolean isSharedElement) {
		try {
			if (!(entity instanceof Metric)) {
				return EMPTY_CHILDREN;
			}
			Metric metric = (Metric) entity;
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(metric.getOwnerProjectName());
			LocalElement localMetric = LocalECoreFactory.getInstance(project).getElementByID(BEViewsElementNames.METRIC, metric.getGUID());
			List<LocalElement> fields = localMetric.getChildren(LocalMetric.ELEMENT_KEY_FIELD);
			List<StudioNavigatorNode> attributes = new LinkedList<StudioNavigatorNode>();
			for (LocalElement field : fields) {
				if (((LocalAttribute)field).isSystem() == false) {
					attributes.add(new MetricPropertyNode((PropertyDefinition) field.getEObject(), isSharedElement));
				}
			}
			return attributes.toArray();
		} catch (Exception e) {
			DashboardUIPlugin.getInstance().getLog().log(new Status(IStatus.WARNING,DashboardUIPlugin.PLUGIN_ID,"could not find children of "+entity.getFullPath(),e));
			return EMPTY_CHILDREN;
		}
	}

}