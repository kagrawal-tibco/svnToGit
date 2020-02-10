/**
 * 
 */
package com.tibco.cep.studio.core.api;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import com.tibco.cep.designtime.model.registry.AddOnType;
import com.tibco.cep.repo.provider.AbstractAddOnIndexResourceProvider;
import com.tibco.cep.studio.common.StudioProjectCache;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.IndexPackage;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.repo.emf.EMFProject;


public class IndexOps {

	
	/**
	 * 
	 * @param projectRootDir
	 */
	public static void loadIndex(String projectRootDir) throws Exception {
		registerEPackages();

		System.out.println();
		// Get projectName
		File projectFile = new File(projectRootDir);
		String projectName = projectFile.getName();
		//Load EMF Project
		final EMFProject project = new EMFProject(projectRootDir);
		project.setStudionProjectConfigManager(StudioProjectConfigurationManager.getInstance());
		project.load();
		project.removeStudionProjectConfigManager();
		
		Map<AddOnType , AbstractAddOnIndexResourceProvider>  indexResourceProviderMap = project.getIndexResourceProviderMap();
		AbstractAddOnIndexResourceProvider indexprovider =  indexResourceProviderMap.get(AddOnType.CORE);
		StudioProjectCache.getInstance().putIndex(projectName, (DesignerProject)indexprovider.getIndex());
		
	}
	/**
	 * 
	 * @param workspacePath
	 * @param projectRootDir
	 */
	public static void loadIndex(String workspacePath, String projectRootDir) throws Exception {
		registerEPackages();
		System.out.printf("Loading index from workspace %s", workspacePath);
		System.out.println();
		// Get projectName
		File projectFile = new File(projectRootDir);
		String projectName = projectFile.getName();
		// Load index and cache it
		String indexLocation = getIndexLocation(workspacePath, projectName);
		URI uri = URI.createFileURI(indexLocation);

		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = resourceSet.createResource(uri);
		try {
			resource.load(null);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			throw new Exception(e);
		}
		if (resource.getContents().size() > 0) {
			DesignerProject index = (DesignerProject) resource.getContents().get(0);
			StudioProjectCache.getInstance().putIndex(projectName, index);
		}
	}
	
	/**
	 * 
	 * @param workspacePath
	 * @param projectName
	 * @return
	 */
	private static String getIndexLocation(String workspacePath, String projectName) {
		String indexLocation = 
			new StringBuilder(workspacePath).
				append(File.separator).
				append(".metadata").
				append(File.separator).
				append(".plugins").
				append(File.separator).
				append("com.tibco.cep.studio.core").
				append(File.separator).
				append("index").
				append(File.separator).
				append(projectName).
				append(IndexUtils.IDX_EXTENSION).toString();
		return indexLocation;
	}
	
	private static void registerEPackages() {
		ResourceFactoryRegistryImpl.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		EPackage.Registry.INSTANCE.put("http:///com/tibco/cep/designer/index/core/model/ontology_index.ecore",
				IndexPackage.eINSTANCE);
	}
}
