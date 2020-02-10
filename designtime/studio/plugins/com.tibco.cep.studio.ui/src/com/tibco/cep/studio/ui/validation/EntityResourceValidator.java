package com.tibco.cep.studio.ui.validation;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.validation.ModelError;
import com.tibco.cep.designtime.core.model.validation.ValidationFactory;
import com.tibco.cep.studio.common.validation.utils.CommonValidationUtils;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.ResourceHelper;
import com.tibco.cep.studio.core.validation.DefaultResourceValidator;
import com.tibco.cep.studio.core.validation.ValidationContext;
import com.tibco.cep.studio.ui.util.Messages;

public class EntityResourceValidator extends DefaultResourceValidator {

	@Override
	public boolean validate(ValidationContext validationContext) {
		IResource resource = validationContext.getResource();
		if (resource == null) return true;
		super.validate(validationContext);
		DesignerElement modelObj = getModelObject(resource);
		if (modelObj instanceof EntityElement && resource instanceof IFile) {
			Entity entity = ((EntityElement) modelObj).getEntity();
			validateEntity(entity, (IFile) resource);
		} else if (modelObj == null && resource instanceof IFile) {
			// the name of the entity might not match that of the file.  Try to load the file directly into an Entity.
			// If this is successful, then most likely the names do not match, so try to validate
			EObject eObject = IndexUtils.loadEObject(ResourceHelper.getLocationURI(resource));
			if (eObject instanceof Entity) {
				validateEntity((Entity) eObject, (IFile) resource);
			}
		}
		return true;
	}

	/**
	 * Check the namespace and ownerprojectname for all entity contents
	 * @param entity
	 * @param entityFile
	 */
	protected void validateEntity(Entity entity, IFile entityFile) {
		String fileName = entityFile.getFullPath().removeFileExtension().lastSegment();
		String projectName = entityFile.getProject().getName();
		String folderPath = null;
		if(entityFile.isLinked(IResource.CHECK_ANCESTORS)){
			IResource parent = ResourceHelper.getLinkedResourceParent(entityFile);
			IPath parentPath = parent.getFullPath();
			IPath resourcePath = entityFile.getFullPath().removeLastSegments(1);
			folderPath = resourcePath.makeRelativeTo(parentPath).makeAbsolute().toPortableString();
		} else {
			folderPath = entityFile.getParent().getFullPath().removeFirstSegments(1).toString();
		}
		if (folderPath.length() == 0) {
			folderPath = "/"; // the parent is the project itself
		}
		if (folderPath.charAt(0) != '/') {
			folderPath = "/"+folderPath;
		}
		if (folderPath.charAt(folderPath.length()-1) != '/') {
			folderPath += "/";
		}
		if (!fileName.equals(entity.getName())) {
			reportProblem(entityFile, Messages.getString("Entity.validate.filenamemismatch", entity.getName(), entityFile.getName()), IMarker.SEVERITY_ERROR, VALIDATION_NS_MARKER_TYPE);
		}
		if (!projectName.equals(entity.getOwnerProjectName())) {
			reportProblem(entityFile, Messages.getString("Entity.validate.ownerprojectmismatch", entity.getOwnerProjectName(), entity.getName()), IMarker.SEVERITY_ERROR, VALIDATION_NS_MARKER_TYPE);
		}
		String namespace = entity.getNamespace();
		if (namespace == null) {
			namespace = "";
		}
		if (namespace.length() > 0 && namespace.charAt(namespace.length()-1) != '/') {
			namespace += "/"; // sometimes the namespace does not end with trailing '/'.  Not sure if this should be marked as an error?
		}
		if (!folderPath.equals(namespace)) {
			reportProblem(entityFile, Messages.getString("Entity.validate.namespacemismatch", entity.getNamespace(), entity.getName()), IMarker.SEVERITY_ERROR, VALIDATION_NS_MARKER_TYPE);
		}
		if (!folderPath.equals(entity.getFolder())) {
			reportProblem(entityFile, Messages.getString("Entity.validate.foldermismatch", entity.getFolder(), entity.getName()), IMarker.SEVERITY_ERROR, VALIDATION_NS_MARKER_TYPE);
		}
		
		TreeIterator<EObject> allContents = entity.eAllContents();
		while (allContents.hasNext()) {
			EObject object = (EObject) allContents.next();
			if (object instanceof Entity) {
				if (((Entity) object).getOwnerProjectName() != null && !projectName.equals(((Entity) object).getOwnerProjectName())) {
					reportProblem(entityFile, Messages.getString("Entity.validate.ownerprojectmismatch", ((Entity) object).getOwnerProjectName(), ((Entity) object).getName()), IMarker.SEVERITY_ERROR, VALIDATION_NS_MARKER_TYPE);
				}
				if (object instanceof PropertyDefinition) {
					PropertyDefinition prop = (PropertyDefinition) object;
					if (prop.getOwner() == null) {
						String msg = Messages.getString("Entity.validate.property.owner.notfound", entity.getFullPath(), prop.getName(), prop.getOwnerPath());
						reportProblem(entityFile, msg, IMarker.SEVERITY_ERROR, VALIDATION_PROP_OWNER_MARKER_TYPE);
					}
				}
				// check namespace/folder here as well?
			}
		}
	}
}
