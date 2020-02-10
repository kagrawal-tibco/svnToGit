package com.tibco.cep.studio.core.jdt;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.ClasspathContainerInitializer;
import org.eclipse.jdt.core.IClasspathAttribute;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

import com.tibco.cep.cep_commonVersion;
import com.tibco.cep.studio.common.configuration.CoreJavaLibEntry;
import com.tibco.cep.studio.common.configuration.CustomFunctionLibEntry;
import com.tibco.cep.studio.common.configuration.IStudioProjectConfigurationChangeListener;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.common.configuration.StudioProjectConfigurationChangeEvent;
import com.tibco.cep.studio.core.BEClassPathContainer;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;

public class BEClasspathContainerInitializer extends ClasspathContainerInitializer implements IStudioProjectConfigurationChangeListener {

	public static final String JAVADOC_LOCATION = "https://docs.tibco.com/pub/businessevents-enterprise/"+cep_commonVersion.version+"/doc/api/javadoc/";

	public BEClasspathContainerInitializer() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(IPath containerPath, IJavaProject project) throws CoreException {

		List<IClasspathEntry> projectLibs = getLibPaths(project);
		BEClassPathContainer container = new BEClassPathContainer(containerPath, projectLibs.toArray(new IClasspathEntry[0]));
		if (container.isValid()) {
			JavaCore.setClasspathContainer(containerPath, new IJavaProject[] { project }, new IClasspathContainer[] { container }, null);
		}

	}

	private List<IClasspathEntry> getLibPaths(IJavaProject project) {
		List<IClasspathEntry> entries = new ArrayList<IClasspathEntry>();
		StudioProjectConfiguration studioProjectConfig = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(project.getElementName());
		if (studioProjectConfig != null) {
			List<CoreJavaLibEntry> internalLibs = studioProjectConfig.getCoreInternalLibEntries();
			if (internalLibs != null) {
				for (CoreJavaLibEntry libEntry : internalLibs) {
					IPath path = new Path(libEntry.getPath());
					if (path.isAbsolute()) {
						if (path.lastSegment().startsWith("cep-") || path.lastSegment().startsWith("be-")) {
							// attach javadoc location for BE jars
							IClasspathAttribute atts[] = new IClasspathAttribute[] {
							    JavaCore.newClasspathAttribute(IClasspathAttribute.JAVADOC_LOCATION_ATTRIBUTE_NAME, JAVADOC_LOCATION),
							};
							entries.add(JavaCore.newLibraryEntry(path, null, null, null, atts, false));
						} else {
							entries.add(JavaCore.newLibraryEntry(path, null, null));
						}
					} else {
						StudioCorePlugin.log(new Status(IStatus.WARNING, StudioCorePlugin.PLUGIN_ID, "Path for Core Internal Library must be absolute: ["+project.getElementName()+"] "+libEntry.getPath()));
					}
				}
			}
//			// third party
//			List<ThirdPartyLibEntry> thirdPartyLibs = studioProjectConfig.getThirdpartyLibEntries();
//			if (internalLibs != null) {
//				for (ThirdPartyLibEntry libEntry : thirdPartyLibs) {
//					IPath path = new Path(libEntry.getPath());
//					if (path.isAbsolute()) {
//						entries.add(JavaCore.newLibraryEntry(path, null, null));
//					} else {
//						StudioCorePlugin.log(new Status(IStatus.WARNING, StudioCorePlugin.PLUGIN_ID, "Path for Third Party Library must be absolute: ["+project.getElementName()+"] "+libEntry.getPath()));
//					}
//				}
//			}
			// custom functions
			List<CustomFunctionLibEntry> customFunctionLibs = studioProjectConfig.getCustomFunctionLibEntries();
			if (internalLibs != null) {
				for (CustomFunctionLibEntry libEntry : customFunctionLibs) {
					IPath path = new Path(libEntry.getPath());
					if (path.isAbsolute()) {
						entries.add(JavaCore.newLibraryEntry(path, null, null));
					} else {
						StudioCorePlugin.log(new Status(IStatus.WARNING, StudioCorePlugin.PLUGIN_ID, "Path for Custom Function Library must be absolute: ["+project.getElementName()+"] "+libEntry.getPath()));
					}
				}
			}
			
			
		}
		return entries;
	}

	@Override
	public void configurationChanged(StudioProjectConfigurationChangeEvent event) {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(event.getDelta().getAffectedChild().getName());
		IJavaProject javaProject = JavaCore.create(project);
//		List<IClasspathEntry> projectLibs = getLibPaths(javaProject);
		IPath containerPath = new Path(BEClassPathContainer.BE_CLASSPATH_CONTAINER);
		IClasspathContainer cpContainer;
		try {
			cpContainer = JavaCore.getClasspathContainer(containerPath, javaProject);
			ClasspathContainerInitializer initializer = JavaCore.getClasspathContainerInitializer(BEClassPathContainer.BE_CLASSPATH_CONTAINER);
			initializer.requestClasspathContainerUpdate(containerPath, javaProject, cpContainer);
		} catch (JavaModelException e) {
			StudioCorePlugin.log(e.getMessage(), e);
		} catch (CoreException e) {
			StudioCorePlugin.log(e.getMessage(), e);
		}
		
	}

}
