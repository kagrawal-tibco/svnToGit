package com.tibco.cep.studio.dashboard.ui.validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.ResourceHelper;
import com.tibco.cep.studio.core.validation.DefaultResourceValidator;
import com.tibco.cep.studio.core.validation.ValidationContext;
import com.tibco.cep.studio.dashboard.core.exception.SynValidationMessage;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalParticle;
import com.tibco.cep.studio.dashboard.core.util.DashboardCoreResourceUtils;
import com.tibco.cep.studio.dashboard.core.util.LocalECoreUtils;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.utils.DashboardResourceUtils;
import com.tibco.cep.studio.ui.validation.EntityResourceValidator;

public class DashboardResourceValidator extends EntityResourceValidator {

	private Map<String,String> customAttributes;

	public DashboardResourceValidator() {
		customAttributes = new HashMap<String, String>();
		customAttributes.put("owner", "beviews");
	}

	@Override
	public boolean enablesFor(IResource resource) {
		return true;
	}

	@Override
	public boolean canContinue() {
		return true;
	}

	@Override
	public boolean validate(ValidationContext validationContext) {
		boolean validate = super.validate(validationContext);
		IResource resource = validationContext.getResource();
		if (resource.exists() == true) {
			// System.out.println("DashboardResourceValidator.validate("+resource+")");
			try {
				LocalECoreFactory coreFactory = LocalECoreFactory.getInstance(resource.getProject());
				if (resource.getFileExtension().equals("system")) {
					// do system skin elements validation
					EList<EObject> multipleElements = DashboardCoreResourceUtils.loadMultipleElements(ResourceHelper.getLocationURI(resource).getPath());
					for (EObject eObject : multipleElements) {
						if (validate && !validateEntity(resource, coreFactory, (Entity) eObject)) {
							// Mark validate false if not already and validation has failed
							validate = false;
						}
					}
				} else {
					Entity entity = (Entity) IndexUtils.loadEObject(ResourceHelper.getLocationURI(resource));
					validate = validateEntity(resource, coreFactory, entity);
				}
//				if (coreFactory.isValid() == false) {
//					SynValidationMessage validationMessage = coreFactory.getValidationMessage();
//					IMarker[] markers = findMarkers(resource.getProject(), VALIDATION_MARKER_TYPE, customAttributes);
//					if (markers.length == 0) {
//						addMarker(resource.getProject(), validationMessage.getMessage(), null, -1, -1, -1, SEVERITY_ERROR, VALIDATION_MARKER_TYPE, customAttributes);
//					}
//				}
//				else {
//					//we have to remove the existing problem, if any
//					deleteMarkers(resource.getProject(), VALIDATION_MARKER_TYPE, customAttributes);
//				}
			} catch (Exception e) {
				String msg = e.getMessage();
				if (msg == null || msg.trim().length() == 0) {
					msg = e.toString();
				}
				msg = "could not validate due to a unrecoverable error [" + msg + "]";
				DashboardUIPlugin.getInstance().getLog().log(new Status(IStatus.ERROR, DashboardUIPlugin.PLUGIN_ID, msg, e));
				reportProblem(resource, msg, DefaultResourceValidator.SEVERITY_ERROR);
			}
		}
		return validate;
	}


	@Override
	protected void validateEntity(Entity entity, IFile entityFile) {
		if ("system".equals(entityFile.getFileExtension()) == false) {
			//call super validate entity for all non system element file
			super.validateEntity(entity, entityFile);
		}
	}

	private boolean validateEntity(IResource resource, LocalECoreFactory coreFactory, Entity entity) throws Exception {
		if (entity == null){
			return true;
		}
		//PATCH we are force loading latest entity and not going to cache
		LocalElement localElement = LocalECoreFactory.toLocalElement(coreFactory, entity);
//		String entityType = entity.eClass().getName();
//		if (entityType.equals(BEViewsElementNames.CHART_COMPONENT) || entityType.equals(BEViewsElementNames.TEXT_COMPONENT)) {
//			localElement = coreFactory.getElement(BEViewsElementNames.TEXT_CHART_COMPONENT, entity.getName(), entity.getFolder());
//			localElement = LocalECoreFactory.toLocalElement(coreFactory, entity);//coreFactory.getElement(BEViewsElementNames.TEXT_CHART_COMPONENT, entity.getName(), entity.getFolder());
//		} else {
//			localElement = coreFactory.getElement(entityType, entity.getName(), entity.getFolder());
//		}
		LocalECoreUtils.loadFully(localElement, false, true);
		boolean validate = localElement.isValid();
		if (!validate) {
			reportProblem(resource, localElement.getValidationMessage());
		}
		return validate;
	}

	@SuppressWarnings("unused")
	private void loadFully(LocalElement localElement) throws Exception {
		List<LocalParticle> particles = localElement.getParticles(true);
		for (LocalParticle particle : particles) {
			List<LocalElement> elements = localElement.getChildren(particle.getName());
			if (particle.isReference() == false) {
				for (LocalElement element : elements) {
					loadFully(element);
				}
			}
		}
	}

	private void reportProblem(IResource resource, SynValidationMessage validationMessage) {
		if (validationMessage != null) {
			if (validationMessage.isReportable() == true) {
				String message = validationMessage.getMessage();
				Object location = validationMessage.getData();
				if (location == null) {
					reportProblem(resource, message, validationMessage.getMessageType() == SynValidationMessage.MSG_TYPE_ERROR ? SEVERITY_ERROR : SEVERITY_WARNING);
				}
				else {
					reportProblem(resource, message, String.valueOf(location), validationMessage.getMessageType() == SynValidationMessage.MSG_TYPE_ERROR ? SEVERITY_ERROR : SEVERITY_WARNING);
				}
			}
			for (SynValidationMessage subMessage : validationMessage.getSubMessages()) {
				reportProblem(resource, subMessage);
			}
		}
	}

}
