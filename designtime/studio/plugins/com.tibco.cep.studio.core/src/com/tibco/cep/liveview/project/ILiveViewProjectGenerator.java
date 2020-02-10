/**
 * 
 */
package com.tibco.cep.liveview.project;

import java.util.Map;

import org.eclipse.core.resources.IProject;

/**
 * @author vpatil
 */
public interface ILiveViewProjectGenerator {
	// LV Project Generator
	public static final String BE_LIVEVIEW_EXTENSION_POINT_PROJECT_GENERATOR = "com.tibco.cep.studio.core.lvProjectGenerator";//$NON-NLS-1$
	public static final String BE_LIVEVIEW_EXTENSION_POINT_ATTR_GENERATOR = "generator";
	
	void generateLVConfigFiles(Map<String, Map<String,String>> entityUriToTrimmingMap, IProject project, String outputPath, boolean isSharedNothing, Map<String, Map<String, String>> overrideModelMap);
}
