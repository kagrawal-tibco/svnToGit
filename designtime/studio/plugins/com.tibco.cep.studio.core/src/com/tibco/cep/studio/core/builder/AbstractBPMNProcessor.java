package com.tibco.cep.studio.core.builder;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessElement;
import com.tibco.cep.studio.core.refactoring.StudioRefactoringParticipant;
import com.tibco.cep.studio.core.validation.DefaultSharedResourceValidator;
/**
 * 
 * @author sasahoo
 *
 */
public abstract class AbstractBPMNProcessor {
	
	private String accessIdentifier;
	
	protected List<String> startupShutdownProcesses = new ArrayList<String>();

	/**
	 * Refactor Processes
	 * @param elementToRefactor
	 * @param processElements
	 * @param process
	 * @param refactoringParticipant
	 * @return
	 */
	public abstract boolean refactor(Object elementToRefactor, ArrayList<ProcessElement> processElements, StudioRefactoringParticipant refactoringParticipant);
	
	/**
	 * Get Processes based on public/private access
	 * @param project
	 * @return
	 */
	public abstract List<String> getProcesses(IProject project);
	
	/**
	 * Validate Process definitions
	 * @param process
	 * @param resource
	 * @param isAgentClass
	 * @param validator
	 */
	public abstract void validate(List<String> processURIs, IResource resource, boolean isAgentClass, DefaultSharedResourceValidator validator);
	
	public String getAccessIdentifier() {
		return accessIdentifier;
	}

	public void setAccessIdentifier(String accessIdentifier) {
		this.accessIdentifier = accessIdentifier;
	}
	
	public List<String> getStartupShudownFunctions() {
		return startupShutdownProcesses;
	}
	


}
