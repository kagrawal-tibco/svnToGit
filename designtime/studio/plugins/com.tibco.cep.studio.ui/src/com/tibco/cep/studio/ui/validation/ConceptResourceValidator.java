package com.tibco.cep.studio.ui.validation;
/**
 * @author rmishra
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.validation.ModelError;
import com.tibco.cep.studio.common.validation.utils.CommonValidationUtils;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.SharedElement;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.ResourceHelper;
import com.tibco.cep.studio.core.validation.IResourceValidatorExtension;
import com.tibco.cep.studio.core.validation.SharedElementValidationContext;
import com.tibco.cep.studio.core.validation.ValidationContext;
import com.tibco.cep.studio.core.validation.ValidationUtils;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

public class ConceptResourceValidator extends EntityResourceValidator implements IResourceValidatorExtension {

	private static final String ATTR_SHARED_PATH = "SharedPath";
	
	@Override
	public boolean canContinue() {
		// can continue if any concept resource got any validation problem
		return true;
	}

	@Override
	public boolean validate(ValidationContext validationContext) {		
		IResource resource = validationContext.getResource();	
		if (resource == null) return true;
//		int modificationType = validationContext.getModificationType();
		int buildType = validationContext.getBuildType();
		super.validate(validationContext);
		DesignerElement modelObj = getModelObject(resource);
		if (modelObj instanceof EntityElement){
			EntityElement enityElmt = (EntityElement) modelObj;
			if (enityElmt.getEntity() instanceof Concept) {
				Concept concept = (Concept)enityElmt.getEntity();
				List<ModelError> errorList = concept.getModelErrors();
				validateDomainInstances(concept, errorList);
				for (ModelError error : errorList){
					if (error.isWarning()){
						reportProblem(resource, error.getMessage(), IMarker.SEVERITY_WARNING);
					} else {
						reportProblem(resource, error.getMessage(), IMarker.SEVERITY_ERROR);
					}
				}

				Map<PropertyDefinition, String> map = new HashMap<PropertyDefinition, String>();
				StudioUIUtils.collectPropertyDomainTypeMismatch(concept.getOwnerProjectName(), concept.getProperties(), map);
				if(map.size() > 0){
					for(PropertyDefinition pd:map.keySet()){
						reportProblem(resource, Messages.getString("associate_domain_error", 
								pd.getName(), pd.getType().getName(), map.get(pd)), IMarker.SEVERITY_ERROR);
					}
				}
				List<PropertyDefinition> parents = new ArrayList<>();
				collectContainmentErrors(concept, parents);
				if (parents.size() > 1) {
					reportProblem(resource, Messages.getString("concept.multiple.containment.error", 
							concept.getFullPath(), parents.get(0).getFullPath(), parents.get(1).getFullPath()), IMarker.SEVERITY_ERROR);
				}
			}
		}
		// Concept got deleted 
		// The affected entities are ,
		// 1: StateMachine it was associated with --> needs to be validated
		// 2: all Rules/RuleFunctions that have reference to this concept need to be validated 
//		if ((IResourceDelta.CHANGED == modificationType || IResourceDelta.REMOVED == modificationType) 
//				&& IncrementalProjectBuilder.INCREMENTAL_BUILD == buildType){
//			validateAllDependentResources(validationContext);
//		}
			
//		if(IncrementalProjectBuilder.INCREMENTAL_BUILD == buildType){
		if(StudioCorePlugin.getDefault().isStateMachineBundleInstalled()){
			validateAllDependentResources(validationContext);
		}
//		}
		
		//Validate CDD resources, as they have property definitions
		if (validationContext.getBuildType() != IncrementalProjectBuilder.FULL_BUILD) {
			ValidationUtils.validateResourceByExtension(resource.getProject(), "cdd");
		}

		return true;
	}

	private void collectContainmentErrors(Concept concept, List<PropertyDefinition> parents) {
		if (concept.isContained()) {
			// BE-26270 : Check whether this concept is contained by multiple parents
			List<Entity> list = CommonIndexUtils.getAllEntities(concept.getOwnerProjectName(), ELEMENT_TYPES.CONCEPT);
			for(Entity entity:list){
				Concept con = (Concept)entity;
				for(PropertyDefinition propertyDefinition:con.getAllPropertyDefinitions()){
					if(propertyDefinition.getType() == PROPERTY_TYPES.CONCEPT && concept.getFullPath().equals(propertyDefinition.getConceptTypePath())) {
						if (!parents.contains(propertyDefinition)) {
							parents.add(propertyDefinition);
						}
					}
				}
			}
		}
	}	

	
	private void validateDomainInstances(Concept concept, List<ModelError> errorList){
		List<Object> argList = new ArrayList<Object>();
	    // Validate Doamin Model Instance associated with property Definitions
        for (DomainInstance di : concept.getAllDomainInstances()){
        	String diPath = di.getResourcePath();
        	if (diPath == null || diPath.trim().length() == 0) continue;
       		String ext = CommonIndexUtils.getFileExtension(ELEMENT_TYPES.DOMAIN);
       		if (!diPath.endsWith("."+ext)){
       			diPath = diPath + "." + ext;
       		}
        	if (!CommonValidationUtils.resolveReference(diPath, concept.getOwnerProjectName())){
        		//Checking for Shared Domain Element exists
        		if(StudioUIUtils.sharedElementExists(concept.getOwnerProjectName(), di.getResourcePath())){
        			continue;
        		}
        		// dangling references 
        		argList.clear();
        		argList.add(di.getOwnerProperty().getName());
        		argList.add("Concept");
        		argList.add(concept.getFullPath());
        		argList.add(diPath);
        		ModelError me = CommonValidationUtils.constructModelError(concept, "RuleParticipant.error.property.hasDanglingDomainReference", argList, false);
        		errorList.add(me);
        	}
        }
	}

	@Override
	protected void reportProblem(IResource resource, String message,
			int lineNumber, int start, int length, int severity) {
		super.reportProblem(resource, message, lineNumber, start, length, severity);
	}



	@Override
	protected void reportProblem(IResource resource, String message,
			int severity) {
		super.reportProblem(resource, message, severity);
	}
	
	/**
	 * @param ownerProjectName
	 */
	protected void validateStateMachines(String ownerProjectName, ValidationContext vlContext){
		StateMachineResourceValidator smValidator = new StateMachineResourceValidator();
		// Validate State Machine
		List<Entity> stateMachineList = IndexUtils.getAllEntities(ownerProjectName, ELEMENT_TYPES.STATE_MACHINE); 
		for (Entity entity : stateMachineList){
			ValidationContext vldContext = new ValidationContext(IndexUtils.getFile(ownerProjectName, entity), IResourceDelta.CHANGED , vlContext.getBuildType());
			smValidator.validate(vldContext);
		}
	}
	
	/**
	 * validates all dependent resources
	 * @param resource
	 * @return
	 */
	protected boolean validateAllDependentResources(final ValidationContext vlContext){
		IResource resource = vlContext.getResource();
		if (resource == null) return true;
		IPath resPath = resource.getFullPath();
		Resource emfRes = null;
		if (resource.isLinked(IResource.CHECK_ANCESTORS)) {
			java.net.URI resourceURI = ResourceHelper.getLocationURI(resource);
			URI resURI = URI.createURI(resourceURI.toString());
			ResourceSet rs = new ResourceSetImpl();
			emfRes = rs.getResource(resURI, true);
			

		} else {
			IPath root = resource.getProject().getLocation();
			resPath = resPath.removeFirstSegments(1);
			IPath resAbsolutePath = root.append(resPath);
			URI resURI = URI.createFileURI(resAbsolutePath.toOSString());
			ResourceSet rs = new ResourceSetImpl();
			emfRes = rs.getResource(resURI, true);
		}
		Concept concept = null;
		for (EObject eobject : emfRes.getContents()){
			if (eobject instanceof Concept){
				concept = (Concept)eobject;
				break;
			}
		}
		if (concept == null) return true;
		// get state machine reference
		for (String smPath : concept.getStateMachinePaths()){
			if (smPath == null) continue;
			// SM Path is relative to the Project
			String smExt = IndexUtils.getFileExtension(ELEMENT_TYPES.STATE_MACHINE);
			smPath = smPath + "." + smExt;
			IResource resolvedSMResource = ValidationUtils.resolveResourceReference(smPath, concept.getOwnerProjectName());
			if (resolvedSMResource == null) {
				// No Problem
				continue;
			}
			// Validate State Machine
			StateMachineResourceValidator smValidator = new StateMachineResourceValidator(concept);
			ValidationContext vldContext = new ValidationContext(resolvedSMResource,IResourceDelta.CHANGED , vlContext.getBuildType());
			smValidator.validate(vldContext);
		}
		
		// TODO : make this smarter, and only validate rules that refer to this concept (?)
//		validateAllRules(vlContext, resource);
		
		return true;
	}

	private void validateAllRules(final ValidationContext vlContext,
			IResource resource) {
		// validate all the rules / rule functions
		DesignerProject index = IndexUtils.getIndex(resource);
		if (index == null) return;
		for (RuleElement re : index.getRuleElements()){
			if (re == null) continue;
			ELEMENT_TYPES reType = re.getElementType();
			if (!(ELEMENT_TYPES.RULE == reType || ELEMENT_TYPES.RULE_FUNCTION == reType)) continue;
			String folder = re.getFolder();
			String name = re.getName();
			String ext = IndexUtils.getFileExtension(reType);
			String ruleResPath = folder + name + "." + ext;
			IResource resolvedRuleResource = ValidationUtils.resolveResourceReference(ruleResPath, re.getIndexName());
			if (resolvedRuleResource == null) continue;
			RuleResourceValidator rrv = new RuleResourceValidator();
			ValidationContext vldContext = new ValidationContext(resolvedRuleResource , IResourceDelta.CHANGED , vlContext.getBuildType());
			rrv.validate(vldContext);
		}
	}

	@Override
	public boolean validate(SharedElementValidationContext validationContext) {
		if (validationContext == null) return true;
		IProject project = validationContext.getProject();
		if (project == null) return true;
		SharedElement element = validationContext.getElement();
		deleteMarkers(project, element);
		
		if (element.getElementType() == ELEMENT_TYPES.CONCEPT) {
			Concept con = (Concept) ((SharedEntityElement)element).getEntity();
			List<PropertyDefinition> parents = new ArrayList<>();
			collectContainmentErrors(con, parents);
			if (parents.size() > 1) {
				// the concept is from a project library, find the local concept resource and
				// report the problem there

				String elPath = getElementPath(element);
				String message = SHARED_ELEMENT_PREFIX + Messages.getString("concept.multiple.containment.error",
						con.getFullPath(), parents.get(0).getFullPath(), parents.get(1).getFullPath());

				IMarker marker = addMarker(project, message, elPath, -1, -1, -1, IMarker.SEVERITY_ERROR,
						SHARED_ELEMENT_VALIDATION_MARKER_TYPE);
				setSharedPathAttr(elPath, marker);
			}
		}
		return false;
	}

	private String getElementPath(SharedElement element) {
		return element.getArchivePath()+"/"+element.getEntryPath() + element.getFileName();
	}
	
	private void deleteMarkers(IProject project, SharedElement element) {
		try {
			IMarker[] existingMarkers = project.findMarkers(SHARED_ELEMENT_VALIDATION_MARKER_TYPE, false, IResource.DEPTH_INFINITE);
			String elPath = getElementPath(element);
			for (IMarker marker : existingMarkers) {
				String sharedPath = (String) marker.getAttribute(ATTR_SHARED_PATH);
				if (sharedPath != null && sharedPath.equals(elPath)) {
					marker.delete();
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean enablesFor(SharedElement element) {
		return element.getElementType() == ELEMENT_TYPES.CONCEPT;
	}

	private void setSharedPathAttr(String elPath, IMarker marker) {
		try {
			marker.setAttribute(ATTR_SHARED_PATH, elPath);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

}
