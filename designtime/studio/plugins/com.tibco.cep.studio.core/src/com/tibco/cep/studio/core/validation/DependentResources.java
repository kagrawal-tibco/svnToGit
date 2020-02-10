package com.tibco.cep.studio.core.validation;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;

/** 
 * This class is used to track dependent resources to be validated.  The 
 * typical use is for files that are being deleted, so that when a validation
 * occurs, validation of the deleted file's dependent resources can still be
 * done, as the dependent resources cannot be calculated after the file
 * has been deleted.
 *  
 * @author rhollom
 *
 */
public class DependentResources {

	// The file undergoing the refactoring operation
	private IFile fFile;
	private List<IResource> fDependentResources;

	/**
	 * Whether or not this has been 'activated'.  
	 * That is, whether or not the file was actually deleted (true), 
	 * or whether the delete wizard was cancelled (false)<br><br>
	 * The default value is false
	 * 
	 * @return boolean
	 */
	public boolean isActivated() {
		return fActivated;
	}

	/**
	 * Set whether or not this has been 'activated'.  
	 * That is, whether or not the file was actually deleted (true), 
	 * or whether the delete wizard was cancelled (false).<br><br>
	 * The default value is false
	 * 
	 * @return
	 */
	public void setActivated(boolean activated) {
		fActivated = activated;
	}

	private boolean fActivated = false;
	
	public DependentResources(IFile file, List<IResource> dependentResources) {
		this.fFile = file;
		this.fDependentResources = dependentResources;
	}

	public List<IResource> getDependentResources() {
		return fDependentResources;
	}

	public void setDependentResources(List<IResource> dependentResources) {
		fDependentResources = dependentResources;
	}

	public void addDependentResources(IResource dependentResource) {
		if (!fDependentResources.contains(dependentResource)) {
			fDependentResources.add(dependentResource);
		}
	}
	
	public IFile getFile() {
		return fFile;
	}
	
	
}
