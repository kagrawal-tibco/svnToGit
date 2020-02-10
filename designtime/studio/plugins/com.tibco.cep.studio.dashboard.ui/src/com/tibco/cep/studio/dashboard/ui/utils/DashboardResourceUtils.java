package com.tibco.cep.studio.dashboard.ui.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.dashboard.core.util.LocalECoreUtils;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;

public class DashboardResourceUtils extends StudioResourceUtils{

	public static void persistEntity(Entity entity, String baseURI,
			IProject project, XMIResource.URIHandler handler,
			IProgressMonitor monitor) throws IOException {
		Map<Object, Object> options = new HashMap<Object, Object>(
				ModelUtils.getPersistenceOptions());
		if (handler != null) {
			options.put(XMIResource.OPTION_URI_HANDLER, handler);
		}
		if (monitor != null) {
			monitor.subTask("persisting " + entity.getName());
		}
		String folder = entity.getFolder();
		String extension = LocalECoreUtils.getExtensionFor(entity);
		URI uri = URI.createFileURI(baseURI + "/" + project.getName() + folder
				+ entity.getName() + "." + extension);
		ResourceSet resourceSet = new ResourceSetImpl();// using XMI
		Resource resource = resourceSet.createResource(uri);
		resource.getContents().add(entity);
		resource.save(options);
		if (monitor != null) {
			monitor.worked(1);
		}
	}

	public static void persistEntity(Entity entity, String baseURI,
			IProject project, IProgressMonitor monitor) throws IOException {
		persistEntity(entity, baseURI, project, null, monitor);
	}

}