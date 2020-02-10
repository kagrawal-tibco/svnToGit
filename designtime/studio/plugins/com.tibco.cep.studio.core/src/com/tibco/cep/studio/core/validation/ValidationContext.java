/**
 * 
 */
package com.tibco.cep.studio.core.validation;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;

/**
 * @author rmishra
 *
 */
public class ValidationContext {
	
	private IResource resource;
	private int modificationType;
	private int buildType;
	private List<IFile> dependentResources = new ArrayList<IFile>();
	
	public ValidationContext(){
		
	}

	public ValidationContext(IResource resource , int modificationType , int buildType){
		this.resource = resource;
		this.modificationType = modificationType;
		this.buildType = buildType;
	}
	
	/**
	 * @return the resource
	 */
	public IResource getResource() {
		return resource;
	}

	/**
	 * @param resource the resource to set
	 */
	public void setResource(IResource resource) {
		this.resource = resource;
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