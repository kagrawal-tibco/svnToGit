package com.tibco.cep.studio.ui.validation;

import static com.tibco.cep.studio.ui.util.StudioUIUtils.getStateGraphPath;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.parser.tree.NodeType;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tibco.cep.designtime.core.model.states.StateEnd;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StateStart;
import com.tibco.cep.designtime.core.model.states.StateSubmachine;
import com.tibco.cep.designtime.core.model.states.StateTransition;
import com.tibco.cep.designtime.core.model.validation.ModelError;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.validation.DefaultStateModelResourceValidator;
import com.tibco.cep.studio.core.validation.ValidationContext;
import com.tibco.cep.studio.core.validation.ValidationUtils;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.Messages;

/**
 * 
 * @author rmishra
 *
 */
public class StateMachineResourceValidator extends DefaultStateModelResourceValidator {

	private Concept ownerConcept;
	private Concept concept =  null;
	
	public StateMachineResourceValidator() {
		
	}
	
	public StateMachineResourceValidator(Concept ownerConcept) {
      this.ownerConcept = ownerConcept;
	}

	@Override
	public boolean canContinue() {
		// we can continue to the next rule if there was an error with this one
		return true;
	}

	@Override
	public boolean validate(ValidationContext validationContext) {
		if (validationContext == null){ 
			return true;
		}
		IResource resource = validationContext.getResource();
		if (resource == null) {
			return true;
		}
		deleteMarkers(resource);
		super.validate(validationContext);
		DesignerElement modelObj = getModelObject(resource);
		if (modelObj instanceof EntityElement){
			StateMachine sm = (StateMachine)((EntityElement)modelObj).getEntity();
			validateEntity(sm, (IFile) resource);
//			if(validationContext.getModificationType() != IResourceDelta.ADDED){
				processOwnerConceptProblem(sm, resource);
//			}
			processStateProblems(sm, resource);
			processStateTransitionProblems(sm,resource);
		} 
		else {
			// StateMachine is deleted from project
			// Concepts can have the dangling references so validate all the concepts which refers this state machine
			// get all StateMachine Instance from Designer project
			DesignerProject index = IndexUtils.getIndex(resource);
			// get all concept Elements  from Index and check if any one has State Machine Reference to this StateMachine
			List<Entity> conceptList = IndexUtils.getAllEntities(index.getName(), ELEMENT_TYPES.CONCEPT);
			for (Entity ent : conceptList){
				Concept concept = (Concept)ent;
				for (String smPath : concept.getStateMachinePaths()) {
					if (smPath == null) continue;
					if (!smPath.startsWith("/")){
						smPath = "/" + smPath;
					}					
					smPath = "/" + index.getName() + smPath + "." + IndexUtils.getFileExtension(ELEMENT_TYPES.STATE_MACHINE);
					IPath smiPath = Path.fromOSString(smPath);
					if (smiPath.equals(resource.getFullPath())){
						// concept has dangling reference 
						List<Object> args = new ArrayList<Object>();
						args.add(concept.getFullPath());
						args.add(smPath);
		        		ModelError me = ValidationUtils.constructModelError(concept, "Concept.error.hasDangligStateMachineReference", args, false);
		        		int severity = me.isWarning()  ? IMarker.SEVERITY_WARNING : IMarker.SEVERITY_ERROR;
		        		// resolve concept resource
		        		String conceptPath = concept.getFullPath() + "." + IndexUtils.getFileExtension(ELEMENT_TYPES.CONCEPT);
		        		IResource conceptRes = ValidationUtils.resolveResourceReference(conceptPath, concept.getOwnerProjectName());
		        		reportProblem(conceptRes, me.getMessage(), severity);
					}
				}
					
			}
		}
		return true;
	}
	
	// copied from EntityResourceValidator::validateEntity, added additional check for owner state machine != null
	private void validateEntity(Entity entity, IFile entityFile) {

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
		if (!fileName.equals(entity.getName())) {
			reportProblem(entityFile, Messages.getString("Entity.validate.filenamemismatch", entity.getName(), entityFile.getName()), IMarker.SEVERITY_ERROR, VALIDATION_NS_MARKER_TYPE);
		}
		if (!projectName.equals(entity.getOwnerProjectName())) {
			reportProblem(entityFile, Messages.getString("Entity.validate.ownerprojectmismatch", entity.getOwnerProjectName(), entity.getName()), IMarker.SEVERITY_ERROR, VALIDATION_NS_MARKER_TYPE);
		}
		String namespace = entity.getNamespace();
		if (namespace.length() > 0 && namespace.charAt(namespace.length()-1) != '/') {
			namespace += "/"; // sometimes the namespace does not end with trailing '/'.  Not sure if this should be marked as an error?
		}
		if (entity instanceof StateEntity) {
			if (((StateEntity) entity).getOwnerStateMachine() == null && ((StateEntity) entity).getOwnerStateMachinePath() != null) {
				reportProblem(entityFile, Messages.getString("Entity.validate.statemodel.owner.notfound", ((StateEntity) entity).getOwnerStateMachinePath(), ((StateEntity) entity).getFullPath()), IMarker.SEVERITY_ERROR, VALIDATION_SM_OWNER_MARKER_TYPE);
			}
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
				if (object instanceof StateEntity) {
					if (((StateEntity) object).getOwnerStateMachine() == null && ((StateEntity) object).getOwnerStateMachinePath() != null) {
						reportProblem(entityFile, Messages.getString("Entity.validate.statemodel.owner.notfound", ((StateEntity) object).getOwnerStateMachinePath(), ((StateEntity) object).getFullPath()), IMarker.SEVERITY_ERROR, VALIDATION_SM_OWNER_MARKER_TYPE);
					}
				}
				// check namespace/folder here as well?
			}
		}
	}
//	private void validateEntity(Entity entity, IFile entityFile) {
//		String projectName = entityFile.getProject().getName();
//		if (!projectName.equals(entity.getOwnerProjectName())) {
//			reportProblem(entityFile, "The owner project name does not match the containing project");
//		}
//		if (entity instanceof StateEntity) {
//			if (((StateEntity) entity).getOwnerStateMachine() == null && ((StateEntity) entity).getOwnerStateMachinePath() != null) {
//				reportProblem(entityFile, "The specified owner state machine '"+((StateEntity) entity).getOwnerStateMachinePath()+"' for state entity '" + ((StateEntity) entity).getFullPath() + "' cannot be found");
//			}
//		}
//		TreeIterator<EObject> allContents = entity.eAllContents();
//		while (allContents.hasNext()) {
//			EObject object = (EObject) allContents.next();
//			if (object instanceof Entity) {
//				if (!projectName.equals(entity.getOwnerProjectName())) {
//					reportProblem(entityFile, "The owner project name does not match the containing project");
//				}
//			}
//			if (object instanceof StateEntity) {
//				if (((StateEntity) object).getOwnerStateMachine() == null && ((StateEntity) object).getOwnerStateMachinePath() != null) {
//					reportProblem(entityFile, "The specified owner state machine '"+((StateEntity) object).getOwnerStateMachinePath()+"' for state entity '" + ((StateEntity) object).getFullPath() + "' cannot be found");
//				}
//			}
//		}
//	}

	protected void processStateProblems(StateEntity se ,IResource resource){
		if (se instanceof StateSubmachine){
			StateSubmachine ssm = (StateSubmachine)se;
			// process Model error for State SubMachine
			for (ModelError me : ssm.getModelErrors()){
				int severity = me.isWarning() ? IMarker.SEVERITY_WARNING : IMarker.SEVERITY_ERROR;
				reportProblem(resource, me.getMessage(), ssm.getName(), getStateGraphPath(ssm), false, severity);
			}
			// process Rule problems
			processStateRules(ssm, resource);
		} 
		else if (se instanceof StateComposite){
			StateComposite sc = (StateComposite)se;
			// process Model error for State Composite
			for (ModelError me : sc.getModelErrors()){
				int severity = me.isWarning() ? IMarker.SEVERITY_WARNING : IMarker.SEVERITY_ERROR;
				reportProblem(resource, me.getMessage(), sc.getName(), getStateGraphPath(sc), false, severity);
			}
			
			// process Rule problems
			processStateRules(sc, resource);
			
			for (StateEntity s : sc.getStateEntities()){
				processStateProblems(s , resource);
			}
			
			//Process Concurrent State and Regions
			if(((StateComposite) se).isConcurrentState()){
                for(StateComposite region:((StateComposite) se).getRegions()){
                	processStateProblems(region , resource);
                }
			}
			
			
		} else if (se instanceof StateStart){
			// report problem
			StateStart start = (StateStart)se;
			// process Model error for Start State
			for (ModelError me : start.getModelErrors()){
				int severity = me.isWarning() ? IMarker.SEVERITY_WARNING : IMarker.SEVERITY_ERROR;
				reportProblem(resource, me.getMessage(), start.getName(), getStateGraphPath(start), false, severity);
			}
			Rule exitAction = start.getExitAction();			
			validateCompilable(resource, start.getName(), getStateGraphPath(start),false, exitAction);
			
		} else if (se instanceof StateEnd){
			// report problem
			StateEnd end = (StateEnd)se;
			Rule entryAction = end.getEntryAction();			
			validateCompilable(resource, end.getName(), getStateGraphPath(end), false, entryAction);
			
		} else if (se instanceof State){			
			State s = (State)se;
			// process Model Error For State
			for (ModelError me : s.getModelErrors()){
				int severity = me.isWarning() ? IMarker.SEVERITY_WARNING : IMarker.SEVERITY_ERROR;
				reportProblem(resource, me.getMessage(), s.getName(), getStateGraphPath(s), false, severity);
			}
			// process Rule problems
			processStateRules(s, resource);
			
		}
	}
	
	protected void processStateTransitionProblems(StateMachine sm , IResource resource){
		
		stateGraphPathErrorSet.clear();
		
		for (StateTransition st : sm.getStateTransitions()){
			//Getting StateTransition Graph Path
			String stateTransitionGraphPath = getStateGraphPath(st);
			
			//Getting model errors for State Transition
			for (ModelError me : st.getModelErrors()){
//				if(!me.isWarning()){
//					stateGraphPathErrorSet.add(stateTransitionGraphPath);
//				}
				int severity = me.isWarning() ? IMarker.SEVERITY_WARNING : IMarker.SEVERITY_ERROR;
				reportProblem(resource, me.getMessage(), st.getName(), stateTransitionGraphPath, true, severity);
			}
			
			//Validating Guard Rule
			Rule rule = st.getGuardRule();			
			validateCompilable(resource, st.getName(),stateTransitionGraphPath, true, rule);
		}
		
		stateModelDiagramUIRefresh();
	}
	
	protected void stateModelDiagramUIRefresh(){
		//Override this	
	}
	
	protected void processStateRules(State s , IResource resource){
		// process rules error
		Rule rule = null;
		if (s.isInternalTransitionEnabled()){
			rule = s.getInternalTransitionRule();			
			validateCompilable(resource,s.getName(), getStateGraphPath(s), false, rule);
		}
		Rule entryRule = s.getEntryAction();		
		validateCompilable(resource, s.getName(), getStateGraphPath(s), false, entryRule);
		Rule exitRule = s.getExitAction();		
		validateCompilable(resource, s.getName(), getStateGraphPath(s),false, exitRule);
		Rule timeOutAction = s.getTimeoutAction();		
		validateCompilable(resource, s.getName(), getStateGraphPath(s), false, timeOutAction);
		RuleFunction timeOutExp = s.getTimeoutExpression();		
		validateCompilable(resource, s.getName(), getStateGraphPath(s),false, timeOutExp, new NodeType(NodeType.NodeTypeFlag.INT_FLAG, NodeType.TypeContext.PRIMITIVE_CONTEXT, false));
	}
   
	/**
	 * @param stateMachine
	 * @param resource
	 */
	private void processOwnerConceptProblem(StateMachine stateMachine, IResource resource){
		concept = null;
		if(!isValidOwnerConcept(stateMachine)){
			reportProblem(resource, Messages.getString("statemachine.validate.error"), IMarker.SEVERITY_ERROR);
			if(ownerConcept != null){
				if(stateMachine.getOwnerConceptPath() != null && !stateMachine.getOwnerConceptPath().equals(ownerConcept.getFullPath())){
					IResource res = IndexUtils.getFile(ownerConcept.getOwnerProjectName(), ownerConcept);
					deleteMarkers(res);
					List<Object> args = new ArrayList<Object>();
					args.add(ownerConcept.getFullPath());
					args.add(stateMachine.getFullPath());
		    		ModelError me = ValidationUtils.constructModelError(concept, "Concept.error.hasDangligStateMachineReference", args, false);
		    		int severity = me.isWarning()  ? IMarker.SEVERITY_WARNING : IMarker.SEVERITY_ERROR;
		    		reportProblem(res, me.getMessage(), severity);
//					conceptEditorRefresh((IFile)res);
				}
			}
		}
		if(concept == null) {
			return;
		}
		String conceptPath = concept.getFullPath() + "." + IndexUtils.getFileExtension(ELEMENT_TYPES.CONCEPT);
		IResource conceptRes = ValidationUtils.resolveResourceReference(conceptPath, concept.getOwnerProjectName());
		if ( conceptRes == null ) {
			conceptRes = IndexUtils.getLinkedResource(concept.getOwnerProjectName(), concept.getFullPath());
		}
		String smPath = IndexUtils.getFullPath(stateMachine);
		List<Object> args = new ArrayList<Object>();
		args.add(concept.getFullPath());
		args.add(smPath);
		ModelError me = ValidationUtils.constructModelError(concept, "Concept.error.hasDangligStateMachineReference", args, false);
		// delete only marker for this error to avoid remove all other markers on Concept
		deleteSMMarkers(conceptRes, me);
		if( !concept.getStateMachinePaths().contains(smPath)){
			// concept has dangling reference 
			int severity = me.isWarning()  ? IMarker.SEVERITY_WARNING : IMarker.SEVERITY_ERROR;
			reportProblem(conceptRes, me.getMessage(), severity);
		}
	}
	
	private void deleteSMMarkers(IResource conceptRes, ModelError me) {
		try {
			IMarker[] findMarkers = conceptRes.findMarkers(VALIDATION_MARKER_TYPE, false, IResource.DEPTH_ZERO);
			for (IMarker marker : findMarkers) {
				String msg = (String) marker.getAttribute(IMarker.MESSAGE);
				if (me.getMessage().equals(msg)) {
					marker.delete();
					return;
				}
			}
		} catch (CoreException e) {
			StudioUIPlugin.log(e);
		}
		
	}

	/**
	 * @param stateMachine
	 * @return
	 */
	private boolean isValidOwnerConcept(StateMachine stateMachine){
		concept = stateMachine.getOwnerConcept();
		String stateMachinePath = stateMachine.getFullPath();
		if(concept!= null){
			for(String path: concept.getStateMachinePaths()){
				if(path.equals(stateMachinePath)){
					return true;
				}
			}
		}
		return false;
	}
}