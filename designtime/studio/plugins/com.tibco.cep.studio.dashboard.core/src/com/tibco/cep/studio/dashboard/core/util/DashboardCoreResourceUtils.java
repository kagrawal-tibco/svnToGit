package com.tibco.cep.studio.dashboard.core.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.core.utils.ModelUtils;

public class DashboardCoreResourceUtils {
		
	public static void persistEntities(List<Entity> entities, String fileName, IProgressMonitor monitor) throws IOException {
		Map<?, ?> options = ModelUtils.getPersistenceOptions();
		if (monitor != null) {
			monitor.subTask("persisting multiple elements");
		}
		URI uri = URI.createFileURI(fileName);
		// using XMI
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = resourceSet.createResource(uri);
		resource.getContents().addAll(entities);
		resource.save(options);
		if (monitor != null) {
			monitor.worked(1);
		}
	}
	
	public static EList<EObject> loadMultipleElements(String fileName) {
		// using XMI instead of XML
		ResourceSet resourceSet = new ResourceSetImpl();
		URI uri = URI.createFileURI(fileName);
		Resource resource = resourceSet.getResource(uri, true);
		return resource.getContents();
	}

}
