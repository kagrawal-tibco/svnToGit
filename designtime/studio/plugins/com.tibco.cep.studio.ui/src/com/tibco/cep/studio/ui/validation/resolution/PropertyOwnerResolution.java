package com.tibco.cep.studio.ui.validation.resolution;

import java.io.IOException;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IMarkerResolution2;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.core.util.ResourceHelper;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

public class PropertyOwnerResolution implements IMarkerResolution2 {
	
	public PropertyOwnerResolution() {
	}

	@Override
	public String getDescription() {
		return Messages.getString("Entity.validate.property.owner.quickfix.desc");
	}

	@Override
	public Image getImage() {
		return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FILE);
	}

	@Override
	public String getLabel() {
		return Messages.getString("Entity.validate.property.owner.quickfix.label");
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
		boolean changed = false;

		TreeIterator<EObject> allContents = entity.eAllContents();
		while (allContents.hasNext()) {
			EObject object = (EObject) allContents.next();
			if (object instanceof PropertyDefinition) {
				PropertyDefinition prop = (PropertyDefinition) object;
				if (prop.getOwner() == null) {
					EObject container = prop.eContainer();
					if (container instanceof Entity) {
						changed = true;
						prop.setOwnerPath(((Entity) container).getFullPath());
						if(prop.getOwnerProjectName() == null) {//Also setting ownerProjectName if not already present.
							prop.setOwnerProjectName(((Entity) container).getOwnerProjectName());
						}
					}
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
	}

}
