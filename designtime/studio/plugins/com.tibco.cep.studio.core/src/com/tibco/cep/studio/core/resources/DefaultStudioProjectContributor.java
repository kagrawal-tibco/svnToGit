package com.tibco.cep.studio.core.resources;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;

import com.tibco.cep.studio.common.configuration.XPATH_VERSION;
import com.tibco.cep.studio.core.IStudioProjectContributor;
import com.tibco.cep.studio.core.builder.StudioProjectBuilder;
import com.tibco.cep.studio.core.nature.StudioProjectNature;
import com.tibco.cep.studio.core.util.StudioCoreResourceUtils;

public class DefaultStudioProjectContributor implements IStudioProjectContributor {

	protected List<String> nautreList;
	protected List<String> builderList;
	
	public DefaultStudioProjectContributor() {
		nautreList = new ArrayList<String>();
		nautreList.add(StudioProjectNature.STUDIO_NATURE_ID);
		builderList = new ArrayList<String>();
		builderList.add(StudioProjectBuilder.BUILDER_ID);
	}

	@Override
	public List<String> getNauturesList() {
		return nautreList;
	}

	@Override
	public void createProject(IProject project, boolean isImport, XPATH_VERSION xpathVersion) {
		// Create default folders
		List<IFolder> folders = StudioCoreResourceUtils.createDefaultFolders(project);
		//Create default files
		List<IFile>files = StudioCoreResourceUtils.createDefaultFiles(project);
		StudioCoreResourceUtils.createDefaultArtifacts(project,folders,files);
		
	}

	@Override
	public List<String> getBuildersList() {
		return builderList;
	}

	@Override
	public void setContainsJavaNatureOnImport(boolean containsJavaNature) {
		// TODO Auto-generated method stub
		
	}
}
