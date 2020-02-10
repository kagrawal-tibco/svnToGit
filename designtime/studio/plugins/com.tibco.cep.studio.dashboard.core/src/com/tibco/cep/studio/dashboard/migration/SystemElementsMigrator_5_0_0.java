package com.tibco.cep.studio.dashboard.migration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.core.migration.IStudioProjectMigrationContext;
import com.tibco.cep.studio.dashboard.core.DashboardCorePlugIn;
import com.tibco.cep.studio.dashboard.core.util.DashboardCoreResourceUtils;
import com.tibco.cep.studio.dashboard.utils.SystemElementsTemplate;

public class SystemElementsMigrator_5_0_0 implements IDashboardResourceMigrator {

	@Override
	public void migrate(IStudioProjectMigrationContext context, File resource, IProgressMonitor monitor) throws CoreException {
		String expression = "";
		// load the existing system skin elements
		try {
			boolean migrated = false;
			List<Entity> existingSystemElements = loadExistingSystemElements(resource);
			Node systemElementsTemplateRootNode = SystemElementsTemplate.getRootNode();
			for (EObject element : existingSystemElements) {
				String name = ((Entity)element).getName();
				expression = "config[@name='"+name+"']/attribute";
				NodeList matchingTemplateNodeAttributes = (NodeList) SystemElementsTemplate.get(systemElementsTemplateRootNode, expression, XPathConstants.NODESET);
				int cnt = matchingTemplateNodeAttributes.getLength();
				for (int i = 0; i < cnt; i++) {
					Node templateAttr = matchingTemplateNodeAttributes.item(i);
					String templateAttrName = templateAttr.getAttributes().getNamedItem("name").getNodeValue();
					EStructuralFeature feature = element.eClass().getEStructuralFeature(templateAttrName);
					if (feature instanceof EAttribute) {
						String templateAttrValue = templateAttr.getChildNodes().item(0).getNodeValue();
						if (element.eIsSet(feature) == true) {
							Object attrValue = element.eGet(feature);
							if (attrValue.equals(templateAttrValue) == false) {
								element.eSet(feature, templateAttrValue);
								migrated = true;
							}
						}
					}
				}
			}
			if (migrated == true) {
				DashboardCoreResourceUtils.persistEntities(existingSystemElements, resource.getAbsolutePath(), null);
			}
		} catch (XPathExpressionException e) {
			throw new CoreException(new Status(IStatus.ERROR, DashboardCorePlugIn.PLUGIN_ID, "could not search BEViewsSystemElement.xml for "+expression, e));
		} catch (IOException e) {
			throw new CoreException(new Status(IStatus.ERROR, DashboardCorePlugIn.PLUGIN_ID, "could not migrate dashboard system skin", e));
		}
	}

	private List<Entity> loadExistingSystemElements(File resource) {
		EList<EObject> contents = DashboardCoreResourceUtils.loadMultipleElements(resource.getAbsolutePath());
		List<Entity> entities = new ArrayList<Entity>(contents.size());
		for (EObject content : contents) {
			entities.add((Entity) content);
		}
		return entities;
	}

}