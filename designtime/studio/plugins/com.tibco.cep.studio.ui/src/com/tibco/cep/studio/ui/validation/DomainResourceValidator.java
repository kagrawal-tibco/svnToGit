/**
 * 
 */
package com.tibco.cep.studio.ui.validation;

import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.element.Scorecard;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.validation.ModelError;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.InstanceElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.validation.DefaultResourceValidator;
import com.tibco.cep.studio.core.validation.ValidationContext;
import com.tibco.cep.studio.ui.util.Messages;

/**
 * @author rmishra
 *
 */
public class DomainResourceValidator extends EntityResourceValidator {

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.validation.DefaultResourceValidator#canContinue()
	 */
	@Override
	public boolean canContinue() {
		
		return super.canContinue();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.validation.DefaultResourceValidator#enablesFor(org.eclipse.core.resources.IResource)
	 */
	@Override
	public boolean enablesFor(IResource resource) {	
		return super.enablesFor(resource);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.validation.DefaultResourceValidator#validate(org.eclipse.core.resources.IResource)
	 */
	@Override
	public boolean validate(ValidationContext validationContext) {
		IResource resource = validationContext.getResource();
		if (resource == null) {
			return true;
		}
		super.validate(validationContext);
		deleteMarkers(resource);
		DesignerElement designerElement = getModelObject(resource);
		if (designerElement instanceof EntityElement) {
			Domain domain = (Domain)((EntityElement)designerElement).getEntity();
			if (domain.getSuperDomainPath() != null && IndexUtils.getDomain(domain.getOwnerProjectName(), domain.getSuperDomainPath()) ==  null) {
				reportProblem(resource, Messages.getString("invalid.domain.super.path", domain.getSuperDomainPath()), IMarker.SEVERITY_ERROR);
			}
			List<ModelError> errorList = domain.getModelErrors();
			for (ModelError error : errorList) {
				if (error.isWarning()) {
					reportProblem(resource, error.getMessage(), IMarker.SEVERITY_WARNING);
				} else {
					reportProblem(resource, error.getMessage(), IMarker.SEVERITY_ERROR);
				}
			}
		} 
		else {
			// domain is deleted if it's deleted the validate the owner concepts and event property 
			// definitions
			DesignerProject index = IndexUtils.getIndex(resource);
			DefaultResourceValidator drv = null;
			
			IProject project = resource.getProject();
			EList<InstanceElement<?>> elements = index.getDomainInstanceElements();
			for (InstanceElement<?> instanceElement : elements) {
				EList<?> instances = instanceElement.getInstances();
				for (Object obj : instances) {
					DomainInstance dmi = (DomainInstance) obj;
					if (dmi == null) continue;
					String resPath = dmi.getResourcePath();					
					String fullPath = "/" + project.getName() + resPath + "." + IndexUtils.getFileExtension(ELEMENT_TYPES.DOMAIN);
					IPath dmPath = Path.fromOSString(fullPath);
					if (!resource.getFullPath().equals(dmPath)) continue;
					PropertyDefinition ownerProp = dmi.getOwnerProperty();
					EObject container = ownerProp.eContainer();
					String ext = null;
					if (container instanceof Scorecard){
						ext = IndexUtils.getFileExtension(ELEMENT_TYPES.SCORECARD);
						drv = new ConceptResourceValidator();
					} else if (container instanceof Concept){
						ext = IndexUtils.getFileExtension(ELEMENT_TYPES.CONCEPT);
						drv = new ConceptResourceValidator();
					} else if (container instanceof Event){
						ext = IndexUtils.getFileExtension(ELEMENT_TYPES.SIMPLE_EVENT);
						drv = new EventResourceValidator();
					}
					if (ext == null) continue;
					Entity ent = (Entity)container;
					String  conceptPath = ent.getFullPath();	
					
					String relPath = conceptPath + "." + IndexUtils.getFileExtension(ELEMENT_TYPES.CONCEPT);
					IPath path = Path.fromOSString(relPath);
					IResource conceptResource = project.findMember(path);
					ValidationContext vldContext = new ValidationContext(conceptResource , IResourceDelta.CHANGED,validationContext.getBuildType());				
					drv.validate(vldContext);
				}
			}
		}
		return true;
	}
}