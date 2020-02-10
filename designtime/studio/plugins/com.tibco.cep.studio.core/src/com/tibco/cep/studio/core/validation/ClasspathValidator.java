package com.tibco.cep.studio.core.validation;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.tibco.cep.studio.common.configuration.BuildPathEntry;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.core.StudioCore;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;

/*
 * Validates the raw classpath format and the resolved classpath of this project,
 * updating markers if necessary.
 */
public class ClasspathValidator {
	
	private IProject project;
	
	public ClasspathValidator(IProject project) {
		this.project = project;
	}
	
	public boolean validate() {
		flushClasspathProblemMarkers();
		boolean validated = true;
		StudioProjectConfiguration projectConfiguration = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(project.getName());
		List<BuildPathEntry> libPaths = new ArrayList<BuildPathEntry>();
		
//		libPaths.addAll(projectConfiguration.getCoreInternalLibEntries());
		libPaths.addAll(projectConfiguration.getThirdpartyLibEntries());
		libPaths.addAll(projectConfiguration.getCustomFunctionLibEntries());
		libPaths.addAll(projectConfiguration.getProjectLibEntries());
		
		for (BuildPathEntry buildPathEntry : libPaths) {
			String pathStr = buildPathEntry.getPath();
			IPath path = new Path(pathStr);
			IWorkspaceRoot root= ResourcesPlugin.getWorkspace().getRoot();
			boolean pathError = false;
			if(buildPathEntry.isVar()){
				String unresolvedPathStr = buildPathEntry.getPath(false);
				Path unresolvedPath = new Path(unresolvedPathStr);
				IPath resolvedVariablePath = StudioCore.getResolvedVariablePath(unresolvedPath);
				pathError = (resolvedVariablePath == null );
				if(!pathError)
					path = resolvedVariablePath;
			}
			if(!pathError){
				// paths are not expected to be inside the project
				// the minimum requirement is that it is a valid file path
//				pathError=  root.findMember(path) == null || !path.toFile().exists();
				pathError=  !path.toFile().exists();
				if(pathError){
					validated = false;
					reportProblem(project, "Project "+"'"+project.getName()+"' is missing required library: '"+pathStr+"'",
									"Build path",IMarker.SEVERITY_ERROR,
									IResourceValidator.BUILD_PATH_MARKER_TYPE);
				}
			}else{
				validated = false;
				reportProblem(project,"Unbound classpath variable: '"+path+ "'in project "+"' "+project.getName()+"'",
						"Build path",IMarker.SEVERITY_ERROR,
						IResourceValidator.BUILD_PATH_MARKER_TYPE);
			}
			
		}
		if(!validated)
			reportProblem(project, "The project cannot be built until build path errors are resolved",
					null ,IMarker.SEVERITY_ERROR,
					IResourceValidator.BUILD_PATH_MARKER_TYPE);
		
		return validated;
	}
	
	
	protected void reportProblem(IResource resource, String message, String location, int severity, String type) {
		addMarker(resource, message, location, severity, type);
	}
	

	/**
	 * @param resource
	 * @param message
	 * @param location
	 * @param lineNumber
	 * @param start
	 * @param length
	 * @param severity
	 * @return
	 */
	//FIXME we should delegate to com.tibco.cep.studio.core.validation.DefaultResourceValidator.addMarker(IResource, String, String, int, int, int, int, String, Map<String, String>) - Anand 10/04/2010
	protected IMarker addMarker(IResource resource, String message, String location,
			int severity, String type) {
		try {
			IMarker marker = resource.createMarker(type);
			Map attributes = new HashMap();
			attributes.put(IMarker.MESSAGE,message);
			attributes.put(IMarker.SEVERITY, (Integer)severity);
			if(location != null){
				attributes.put(IMarker.LOCATION, location);
			}
			marker.setAttributes(attributes);
			return marker;
		} catch (CoreException e) {
		}
		return null;
	}
	
	
	protected void flushClasspathProblemMarkers() {
		try {
			if (this.project.isAccessible()) {
				this.project.deleteMarkers(IResourceValidator.BUILD_PATH_MARKER_TYPE, true, IResource.DEPTH_ZERO);
			}
		} catch (CoreException e) {
			StudioCorePlugin.debug(e.getLocalizedMessage());
		}
	}


}
