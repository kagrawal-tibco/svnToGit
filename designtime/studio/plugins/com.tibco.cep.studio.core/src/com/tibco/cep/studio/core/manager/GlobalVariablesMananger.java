package com.tibco.cep.studio.core.manager;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.xml.sax.InputSource;

import com.tibco.cep.repo.provider.GlobalVariablesProvider;
import com.tibco.cep.repo.provider.impl.GlobalVariablesProviderImpl;
import com.tibco.cep.studio.common.configuration.ProjectLibraryEntry;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.core.index.model.SharedElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.utils.GlobalVarUtils;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParserFactory;


public class GlobalVariablesMananger {
	
	private class GlobalVariablesChangeListener implements IResourceChangeListener {

		private boolean reloaded = false;
		
		@Override
		public void resourceChanged(IResourceChangeEvent event) {
			IResourceDelta delta = event.getDelta();
			IResourceDeltaVisitor visitor = new IResourceDeltaVisitor() {
			

				@Override
				public boolean visit(IResourceDelta delta) throws CoreException {
					if (reloaded) {
						return false;
					}
					if (!(delta.getResource() instanceof IFile)) {
						return true;
					}
					IFile file = (IFile) delta.getResource();
					
					IMarkerDelta[] detas = delta.getMarkerDeltas();
					
					for(IMarkerDelta mdelta : detas){
						if(mdelta.getType().equals(GlobalVariablesValidator.GV_PROBBLEM_MARKER))
						{
							return false;
						}
					}
										
					String projectName = file.getProject().getName();
					GlobalVariablesProvider provider = fProviders.get(projectName);
					if (provider != null) {
						if (file.getProjectRelativePath().segment(0).equalsIgnoreCase(GLOBAL_VARS_DIR)
						&& provider.supportsResource(file.getName())) {
							try {
								updateGlobalVariables(file.getProject().getName());
								reloaded = true;
							} catch (Exception e) {
								throw new CoreException(new Status(IStatus.ERROR,StudioCorePlugin.PLUGIN_ID,"Failed to load GlobalVariables.",e));
							}
						}
					}
					return false;
				}
			};
			
			try {
				if(delta != null)
					delta.accept(visitor);
			} catch (CoreException e) {
				e.printStackTrace();
			}
			reloaded = false;
		}

	}
	
	private static final String GLOBAL_VARS_DIR = "defaultVars";
	public static final String GLOBAL_VAR_EXTENSION     = "substvar";
	public static final String GLOBAL_VAR_FILE_NAME     = "defaultVars/defaultVars.substvar";

	private HashMap<String, GlobalVariablesProvider> fProviders = new HashMap<String, GlobalVariablesProvider>();
	private List<IGlobalVariablesChangeListener> fChangeListeners = new ArrayList<IGlobalVariablesChangeListener>();
	private static GlobalVariablesValidator gvValidator;
	
	private static GlobalVariablesMananger fInstance;
	private static GlobalVariablesChangeListener fChangeListener;

	public static GlobalVariablesMananger getInstance() {
		if (fInstance == null) {
			fInstance = new GlobalVariablesMananger();
			fChangeListener = fInstance.new GlobalVariablesChangeListener();
			ResourcesPlugin.getWorkspace().addResourceChangeListener(fChangeListener);
			gvValidator = new GlobalVariablesValidator(fInstance);
		}
		return fInstance;
	}
	
	public void addChangeListener(IGlobalVariablesChangeListener listener) {
		if (!fChangeListeners.contains(listener)) {
			fChangeListeners.add(listener);
		}
	}
	
	public void removeChangeListener(IGlobalVariablesChangeListener listener) {
		if (fChangeListeners.contains(listener)) {
			fChangeListeners.remove(listener);
		}
	}
	
	private void fireChangeEvent(GlobalVariablesProvider provider, String projectName) {
		for (int i=0; i<fChangeListeners.size(); i++) {
			fChangeListeners.get(i).globalVariablesChanged(provider, projectName);
		}
	}
	
	public GlobalVariablesProvider getProvider(String projectName) {
		GlobalVariablesProvider globalVariablesProvider = fProviders.get(projectName);
		if (globalVariablesProvider == null) {
			GlobalVariablesProvider provider = new GlobalVariablesProviderImpl();
			try {
				globalVariablesProvider = loadGlobalVariables(projectName, provider);
			} catch (Exception e) {
				StudioCorePlugin.log(e);
			}
			fProviders.put(projectName, globalVariablesProvider);
		}
		return globalVariablesProvider;
	}
	
	public void updateGlobalVariables(String projectName) throws Exception {
		GlobalVariablesProvider provider = fProviders.get(projectName);
		if (provider != null) {
			provider.clear();
			loadGlobalVariables(projectName, provider);
		}
	}
	
	private GlobalVariablesProvider loadGlobalVariables(String projectName, final GlobalVariablesProvider provider) throws Exception{
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		IFolder folder = project.getFolder(new Path(GLOBAL_VARS_DIR));
		if (!folder.exists()) {
			return provider;
		}
		
		
		// load referenced projects first in reverse order, so that any duplicate entities are used
    	// from the local project
		List<SharedElement> gvElements = new ArrayList<SharedElement>();
		StudioProjectConfiguration config = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(projectName);
		if(config != null){
			loadProjectLibGlobalVariables(provider, config);
		} 
    	
		IResourceVisitor visitor = new IResourceVisitor() {
		
			@Override
			public boolean visit(IResource resource) throws CoreException {
				if (!(resource instanceof IFile)) {
					return true;
				}
				IFile file = (IFile) resource;
				processFile(file, provider);
				return false;
			}
			
		};
		folder.accept(visitor);
				
		fireChangeEvent(provider, project.getName());
		return provider;
	}

	
	
	
	
	/**
	 * @param file
	 * @param provider
	 * @throws CoreException
	 */
	protected void processFile(IFile file, GlobalVariablesProvider provider) throws CoreException {
		if (!provider.supportsResource(file.getName())) {
			return;
		}
		String path = file.getProjectRelativePath().removeFirstSegments(1).removeLastSegments(1).toString();
		if(!path.isEmpty()){
			path = path+"/";
		}
		try {
			GlobalVarUtils.processFile(path,file.getContents(),provider,file.getProject().getName());
		} catch (Exception e) {
			throw new CoreException(new Status(IStatus.ERROR,StudioCorePlugin.PLUGIN_ID,"Failed to process Global variables",e));
		}
	}

	
	
	/**
	 * @param projectName
	 * @param provider
	 */
	private void addProvider(String projectName, GlobalVariablesProvider provider) {
		fProviders.put(projectName, provider);
	}
	
	/**
	 * @param projectName
	 */
	private void removeProvider(String projectName) {
		fProviders.remove(projectName);
	}
	
	
	/**
	 * @param provider
	 * @param config
	 * @throws IOException
	 * @throws Exception
	 */
	private void loadProjectLibGlobalVariables(
			final GlobalVariablesProvider provider,
			StudioProjectConfiguration config) throws IOException, Exception {
		StudioProjectConfigurationManager.getInstance().resolveLibraryEntriesPath(config);
		EList<ProjectLibraryEntry> referencedProjects = config.getProjectLibEntries();
		if (referencedProjects.size() > 0) {
			for (int i=referencedProjects.size()-1;i >= 0;i--) {
				ProjectLibraryEntry refProject =  referencedProjects.get(i);
				String libRef = refProject.getPath(refProject.isVar());
				System.out.println("Loading global vars: " + libRef);
				JarFile file = new JarFile(libRef);
				processProjectLibrary(file,provider);					
			}
		}
	}
	
	/**
	 * @param fJarFile
	 * @param provider
	 * @throws Exception
	 */
	private void processProjectLibrary(JarFile fJarFile, GlobalVariablesProvider provider) throws Exception {
        if (fJarFile == null) {
            return;
        }
        Enumeration<JarEntry> entries = fJarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String fileName = entry.getName();
            if (fileName.lastIndexOf('\\') != -1) {
                fileName = fileName.substring(fileName.lastIndexOf('\\'));
            }
            if (fileName.lastIndexOf('/') != -1) {
                fileName = fileName.substring(fileName.lastIndexOf('/'));
            }
            if (fileName.lastIndexOf('.') != -1) {
                String extension = fileName.substring(fileName.lastIndexOf('.')+1);
                if (CommonIndexUtils.isGlobalVarType(extension) || 
                		provider.supportsResource(fileName)) {
                    processGlobalVarEntry(fJarFile,entry,provider);
                } 
            }
        }       
    }

	/**
	 * @param jarFile
	 * @param entry
	 * @param provider
	 * @throws Exception
	 */
	private void processGlobalVarEntry(JarFile jarFile,JarEntry entry, GlobalVariablesProvider provider) throws Exception {
		byte[] contents = CommonIndexUtils.getJarEntryContents(jarFile, entry);
		Path p = new Path(entry.getName());
		String pathStr = p.removeFirstSegments(1).removeLastSegments(1).toString();
		if(!pathStr.isEmpty() && !pathStr.endsWith("/")){
			pathStr = pathStr+"/";
		}
		try {
			processFile(pathStr,new ByteArrayInputStream(contents),provider,jarFile.getName());		
		} catch (Exception e) {
			StudioCorePlugin.log("Error processing Global Variables file "+p.toString(), e);
		}
		
	}
	
	/**
	 * @param path
	 * @param contents
	 * @param provider
	 * @throws Exception
	 */
	private void processFile(String path,InputStream contents, GlobalVariablesProvider provider,String projectSource) throws Exception {
		XiNode node = XiParserFactory.newInstance().parse(new InputSource(contents));
		node = node.getFirstChild();
		provider.buildGlobalVariablesUsingRemoteRepository(path, node,projectSource);
	}
}