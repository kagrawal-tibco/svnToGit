package com.tibco.cep.studio.ui.validation.resolution;

import java.io.IOException;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IMarkerResolution2;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.core.util.ResourceHelper;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

public class EntityMarkerNamespaceResolution implements IMarkerResolution2 {
	
	public EntityMarkerNamespaceResolution() {
	}

	@Override
	public String getDescription() {
		return "This will change the namespace/ownerproject/folder";
	}

	@Override
	public Image getImage() {
		return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FILE);
	}

	@Override
	public String getLabel() {
		return Messages.getString("Entity.validate.quickfix.label");
	}

	@Override
	public void run(IMarker marker) {
		IResource resource = marker.getResource();
		if (!StudioUIUtils.saveDirtyEditor(resource)) {
			return;
		}
		Object element = IndexUtils.getElement(resource);
		if (element == null) {
			// the name of the entity might not match that of the file.  Try to load the file directly into an Entity.
			// If this is successful, then most likely the names do not match, so try to validate
			EObject eObject = IndexUtils.loadEObject(ResourceHelper.getLocationURI(resource));
			if (eObject instanceof Entity) {
				element = eObject;
			}
		}
		if (element == null) {
			StudioUIPlugin.log("Could not obtain element for resource "+resource.getName()+".  Cannot perform quick fix");
		}
		if (element instanceof EntityElement) {
			Entity entity = ((EntityElement) element).getEntity();
			processEntity(entity, resource);
		} else if (element instanceof Entity) {
			processEntity((Entity) element, resource);
		}
	}

	protected void processEntity(Entity entity, IResource entityFile) {
		String fileName = entityFile.getFullPath().removeFileExtension().lastSegment();
		String projectName = entityFile.getProject().getName();
		String folderPath = entityFile.getParent().getFullPath().removeFirstSegments(1).toString();
		if (folderPath.length() == 0) {
			folderPath = "/"; // the parent is the project itself
		}
		if (folderPath.charAt(0) != '/') {
			folderPath = "/"+folderPath;
		}
		if (folderPath.charAt(folderPath.length()-1) != '/') {
			folderPath += "/";
		}
		boolean changed = false;
		boolean rebuildIndex = false;
		if (!fileName.equals(entity.getName())) {
			// request a clean build after this, else the index entries might not be cleaned up properly
			entity.setName(fileName);
			changed = true;
			rebuildIndex = true;
		}
		if (!projectName.equals(entity.getOwnerProjectName())) {
			entity.setOwnerProjectName(projectName);
			changed = true;
		}
		
		String namespace = entity.getNamespace();
		if (namespace.length() > 0 && namespace.charAt(namespace.length()-1) != '/') {
			namespace += "/"; // sometimes the namespace does not end with trailing '/'.  Not sure if this should be marked as an error?
		}
		if (!folderPath.equals(namespace)) {
			entity.setNamespace(folderPath);
			changed = true;
		}
		if (!folderPath.equals(entity.getFolder())) {
			entity.setFolder(folderPath);
			changed = true;
		}
		
		TreeIterator<EObject> allContents = entity.eAllContents();
		while (allContents.hasNext()) {
			EObject object = (EObject) allContents.next();
			if (object instanceof Entity) {
				if (((Entity) object).getOwnerProjectName() != null && !projectName.equals(((Entity) object).getOwnerProjectName())) {
					((Entity) object).setOwnerProjectName(projectName);
					changed = true;
				}
			}
		}
		
		if (changed) {
			try {
				ModelUtils.saveEObject(entity);
			} catch (IOException e) {
				e.printStackTrace();
			}
			CommonUtil.refresh(entityFile, 1, false);
		}
		if (rebuildIndex) {
			try {
				entityFile.getProject().build(IncrementalProjectBuilder.CLEAN_BUILD, new NullProgressMonitor());
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}

}
