/**
 * 
 */
package com.tibco.cep.studio.core.configuration.manager;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;

import com.tibco.cep.studio.core.validation.DependentResources;
import com.tibco.cep.studio.core.validation.ValidationUtils;

public class StudioProjectChangeListener implements IResourceChangeListener {

	/**
	 * 
	 */
	private final StudioProjectConfigurationManager studioProjectConfigurationManager;

	/**
	 * @param studioProjectConfigurationManager
	 */
	public StudioProjectChangeListener(
			StudioProjectConfigurationManager studioProjectConfigurationManager) {
		this.studioProjectConfigurationManager = studioProjectConfigurationManager;
	}

	@Override
	public void resourceChanged(final IResourceChangeEvent event) {
		IResourceDelta delta = event.getDelta();
		
		IResourceDeltaVisitor visitor = new IResourceDeltaVisitor() {
		
			@Override
			public boolean visit(IResourceDelta delta) throws CoreException {
				int type = delta.getKind();
				if (type == IResourceDelta.REMOVED) {
					if (delta.getResource() instanceof IProject) {
						IProject proj = (IProject) delta.getResource();
						try {
							StudioProjectChangeListener.this.studioProjectConfigurationManager.removeConfiguration(proj.getName());
						} catch (Exception e) {
							e.printStackTrace();
						}
						return false;
					} else {
						IResource resource = (IResource) delta.getResource();
						QualifiedName key = ValidationUtils.getDependentQN(resource);
						if (resource != null && resource.getProject().isOpen()) {
							Object sessionProperty = resource.getProject().getSessionProperty(key);
							if (sessionProperty instanceof DependentResources) {
								((DependentResources) sessionProperty).setActivated(true);
							}
						}
					}
				}
				return true;
			}
		};
		
		try {
			delta.accept(visitor);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
	
}