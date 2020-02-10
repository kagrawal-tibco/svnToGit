package com.tibco.cep.studio.core.dependency;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.TypeElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;

public abstract class EntityDependencyCalculator extends DefaultDependencyCalculator implements IDependencyCalculatorExtension {

	@Override
	public List<IResource> calculateDependencies(IResource resource) {
		if (!enablesFor(resource)) {
			return null;
		}
		List<Object> dependencies = new ArrayList<Object>();
		DesignerElement element = IndexUtils.getElement(resource);
		if (element instanceof EntityElement) {
			processEntityElement(null, resource.getProject().getName(), (EntityElement) element, dependencies);
		}
		List<IResource> files = new ArrayList<IResource>();
		for (Object object : dependencies) {
			if (object instanceof IResource) {
				files.add((IResource) object);
			}
		}
		return files;
	}

	@Override
	public List<File> calculateDependencies(File projectDir,
			File resource) {
		if (!enablesFor(resource)) {
			return null;
		}
		IPath projPath = new Path(projectDir.getAbsolutePath());
		IPath filePath = new Path(resource.getAbsolutePath());
		if (projPath.isPrefixOf(filePath)) {
			filePath = filePath.removeFirstSegments(projPath.segmentCount());
			filePath = filePath.setDevice(null);
			filePath = filePath.removeFileExtension();
		}
		List<Object> dependencies = new ArrayList<Object>();
		DesignerElement element = CommonIndexUtils.getElement(projectDir.getName(), filePath.toString());
		if (element instanceof EntityElement) {
			processEntityElement(projectDir, projectDir.getName(), (EntityElement) element, dependencies);
		}
		List<File> files = new ArrayList<File>();
		for (Object object : dependencies) {
			if (object instanceof File) {
				files.add((File) object);
			}
		}

		return files;
	}

	protected void processAndAddDependency(List<Object> dependencies, String projectName, Entity entity) {
		IFile file = IndexUtils.getFile(projectName, entity);
		if (file == null || !file.exists()) {
			if (entity.eIsProxy()) {
				URI proxyURI = ((EObjectImpl)entity).eProxyURI();
				String fileName = proxyURI.toFileString();
				addDependency(dependencies, new File(fileName));
			} else {
				URI uri = entity.eResource().getURI();
				if (uri == null) {
					StudioCorePlugin.log(new Status(IStatus.WARNING, StudioCorePlugin.PLUGIN_ID, "[Project Library Export] Could not find URI for dependent entity '"+entity.getFullPath()+"'.  This entity might already exist in a project library"));
				} else {
					String fileName = uri.toFileString();
					addDependency(dependencies, new File(fileName));
				}
			}
		} else {
			addDependency(dependencies, file);
		}
	}

	protected void processAndAddDependency(File projectDir, List<Object> dependencies, String projectName, TypeElement element) {
		if (element instanceof EntityElement) {
			processAndAddDependency(dependencies, projectName, ((EntityElement) element).getEntity());
			return;
		}
		IFile file = IndexUtils.getFile(projectName, element);
		if (file != null && file.getLocation() == null) {
			IPath relPath = file.getFullPath().removeFirstSegments(1);
			File depFile = new File(projectDir.getAbsolutePath()+File.separator+relPath.toString());
			addDependency(dependencies, depFile);
			return;
		}
		addDependency(dependencies, file);
	}
	
	protected void processAndAddDependency(File projectDir, List<Object> dependencies, String projectName, Path path) {
		IProject proj = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		if (proj != null) {
			IFile file = proj.getFile(path);
			if (file != null) {
				if (file.getLocation() != null) {
					addDependency(dependencies, file);
				} else {
					String fileName = projectDir.getAbsolutePath() + File.separator + file.getFullPath().removeFirstSegments(1).toString();
					addDependency(dependencies, new File(fileName));
				}
			}
		}
	}

	protected abstract void processEntityElement(File projectDir, String projectName, EntityElement element,
			List<Object> dependencies);

}
