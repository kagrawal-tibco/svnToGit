package com.tibco.cep.studio.core.jdt;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.ClasspathContainerInitializer;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

import com.tibco.cep.studio.common.configuration.IStudioProjectConfigurationChangeListener;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.common.configuration.StudioProjectConfigurationChangeEvent;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.core.util.StudioJavaUtil;

public class BEThirdPartyLibraryContainerInitializer extends ClasspathContainerInitializer implements IStudioProjectConfigurationChangeListener {

	public BEThirdPartyLibraryContainerInitializer() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(IPath containerPath, IJavaProject project) throws CoreException {
		StudioProjectConfiguration spc = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(project.getElementName());
		List<IClasspathEntry> projectLibs = StudioJavaUtil.getThirdPartyCustomClassPathEntries(spc);
		BEThirdPartyLibraryContainer container = new BEThirdPartyLibraryContainer(containerPath, projectLibs.toArray(new IClasspathEntry[0]));
		if (container.isValid()) {
			JavaCore.setClasspathContainer(containerPath, new IJavaProject[] { project }, new IClasspathContainer[] { container }, null);
		}
		StudioProjectConfigurationManager.getInstance().addConfigurationChangeListener(this);
	}
	
	

	
	@Override
	public void requestClasspathContainerUpdate(IPath containerPath, IJavaProject project, IClasspathContainer containerSuggestion) throws CoreException {
		StudioProjectConfiguration studioProjectConfig = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(project.getElementName());
		List<IClasspathEntry> projectLibs = StudioJavaUtil.getThirdPartyCustomClassPathEntries(studioProjectConfig);
		BEThirdPartyLibraryContainer container = new BEThirdPartyLibraryContainer(containerPath, projectLibs.toArray(new IClasspathEntry[0]));
		if (container.isValid()) {
			JavaCore.setClasspathContainer(containerPath, new IJavaProject[] { project }, new IClasspathContainer[] { container }, null);
		}
	}

	@Override
	public void configurationChanged(StudioProjectConfigurationChangeEvent event) {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(event.getDelta().getAffectedChild().getName());
		IJavaProject javaProject = JavaCore.create(project);
		
		IPath containerPath = new Path(BEThirdPartyLibraryContainer.BE_THIRD_PARTY_LIBRARY_CONTAINER);
		IClasspathContainer cpContainer;
		try {
			cpContainer = JavaCore.getClasspathContainer(containerPath, javaProject);
			ClasspathContainerInitializer initializer = JavaCore.getClasspathContainerInitializer(BEThirdPartyLibraryContainer.BE_THIRD_PARTY_LIBRARY_CONTAINER);
			initializer.requestClasspathContainerUpdate(containerPath, javaProject, cpContainer);
		} catch (JavaModelException e) {
			e.printStackTrace();
			StudioCorePlugin.log(e.getMessage(), e);
		} catch (CoreException e) {
			StudioCorePlugin.log(e.getMessage(), e);
		}
		
	}

}
