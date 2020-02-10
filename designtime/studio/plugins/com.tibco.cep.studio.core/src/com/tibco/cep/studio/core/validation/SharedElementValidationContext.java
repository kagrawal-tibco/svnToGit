/**
 * 
 */
package com.tibco.cep.studio.core.validation;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

import com.tibco.cep.studio.core.index.model.SharedElement;

/**
 */
public class SharedElementValidationContext {
	
	private SharedElement element;
	private IProject project;
	private int modificationType;
	private int buildType;
	private List<IFile> dependentResources = new ArrayList<IFile>();
	
	public SharedElementValidationContext(){
		
	}

	public SharedElementValidationContext(IProject project, SharedElement element, int modificationType, int buildType){
		this.project = project;
		this.element = element;
		this.modificationType = modificationType;
		this.buildType = buildType;
	}
	
	/**
	 * @return the resource
	 */
	public IProject getProject() {
		return project;
	}

	public SharedElement getElement() {
		return element;
	}

	/**
	 * @return the modificationType
	 */
	public int getModificationType() {
		return modificationType;
	}
	/**
	 * @param modificationType the modificationType to set
	 */
	public void setModificationType(int modificationType) {
		this.modificationType = modificationType;
	}
	
	public List<IFile> getDependentResources() {
		return dependentResources;
	}
	
	public void addDependentResource(IFile dependentResource) {
		if (!dependentResources.contains(dependentResource)) {
			dependentResources.add(dependentResource);
		}
	}
	
	/**
	 * @return the buildType
	 */
	public int getBuildType() {
		return buildType;
	}
	
	/**
	 * @param buildType the buildType to set
	 */
	public void setBuildType(int buildType) {
		this.buildType = buildType;
	}

}