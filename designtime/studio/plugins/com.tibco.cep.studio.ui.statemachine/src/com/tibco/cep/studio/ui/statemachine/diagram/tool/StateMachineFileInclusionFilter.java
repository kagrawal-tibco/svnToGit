package com.tibco.cep.studio.ui.statemachine.diagram.tool;

import static com.tibco.cep.studio.core.index.utils.CommonIndexUtils.getEntity;
import static com.tibco.cep.studio.core.index.utils.IndexUtils.getFullPath;
import static com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.getCallStateModelOwners;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.getSubConcepts;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.forms.components.FileInclusionFilter;
import com.tibco.cep.studio.ui.navigator.model.StateEntityNode;

/**
 * 
 * @author sasahoo
 *
 */
public class StateMachineFileInclusionFilter extends FileInclusionFilter{

	private boolean isCallStateMachine = false;
	private String ownerConcept = null;


	/**
	 * @param inclusions
	 * @param baseAbsolutePath
	 * @param ownerConcept
	 * @param isCallStateMachine
	 */
	public StateMachineFileInclusionFilter(Set<?> inclusions,
										   String baseAbsolutePath,
										   String ownerConcept, 
										   boolean isCallStateMachine) {
		super(inclusions, baseAbsolutePath);
		this.isCallStateMachine = isCallStateMachine;
		this.ownerConcept = ownerConcept;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.components.FileInclusionFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof IAdaptable) {
			IResource res = (IResource) ((IAdaptable) element)
					.getAdapter(IResource.class);
			if (res instanceof IFile) {
				IFile file = (IFile) res;
				String path = getFullPath(file);
				if(isCallStateMachine){
					return isValidStateModel(file, path);
				}else{
					return isEntityFile(file) && isBaseEntityFile(baseAbsolutePath, path);
				}
			}
			if (res instanceof IFolder){
			    IFolder folder = (IFolder)res;
			    visible = false;
                return isVisible(folder);
			}
		}
		if(isCallStateMachine){
			if(element instanceof StateEntityNode){
				return false;
			}
		}
		return true;
	}
	

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.components.FileInclusionFilter#isValidResource(java.lang.Object)
	 */
	protected boolean isValidResource(Object res){
		IFile file = (IFile) res;
		String path = getFullPath(file);
		if(isCallStateMachine){
			return isValidStateModel(file, path);
		}else{
			return isEntityFile(file) && isBaseEntityFile(baseAbsolutePath, path);
		}
	}
	
	/**
	 * @param file
	 * @param path
	 * @return
	 */
	private boolean isValidStateModel(IFile file, String path){
		StateMachine stateMachine = null;
		String fileExtension = file.getFileExtension();
		//Files may not always have extensions like those on Unix Systems
		if ("statemachine".equals(fileExtension)) {
			stateMachine = 
				(StateMachine)getEntity(file.getProject().getName(), path, ELEMENT_TYPES.STATE_MACHINE);
		}
		return isEntityFile(file) && !isBaseEntityFile(baseAbsolutePath, path) 
		                          && (isSameOwnerConcept(stateMachine) || isOwnerStateMachineInherit(stateMachine));
	}
	
	/**
	 * @param file
	 * @return
	 */
	private boolean isSameOwnerConcept(StateMachine stateMachine ){
		if( (stateMachine!= null) && stateMachine.getOwnerConceptPath().equalsIgnoreCase(ownerConcept)){
			return true;
		}
		return false;
	}
	
	/**
	 * Check for Owner State Model 
	 * @param file
	 * @return
	 */
	protected boolean isOwnerStateModel(StateMachine stateMachine){
		Set<String> ownerStateModels = new HashSet<String>();
		getCallStateModelOwners(stateMachine, stateMachine.getOwnerProjectName(), ownerStateModels);
		for(String ownerStateModelPath:ownerStateModels){
			ownerStateModelPath = ownerStateModelPath + "." +IndexUtils.getFileExtension(stateMachine);
			if(ownerStateModelPath.equalsIgnoreCase(baseAbsolutePath)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param stateMachine
	 * @return
	 */
	private boolean isOwnerStateMachineInherit(StateMachine stateMachine){
		try{
			if(stateMachine!= null && stateMachine.getOwnerConcept() != null){
				Concept concept = stateMachine.getOwnerConcept();
				List<String> subConceptsPaths = getSubConcepts(concept.getFullPath(), concept.getOwnerProjectName());
				if(subConceptsPaths.contains(ownerConcept)){
					return true;
				}
			}}
		catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
}