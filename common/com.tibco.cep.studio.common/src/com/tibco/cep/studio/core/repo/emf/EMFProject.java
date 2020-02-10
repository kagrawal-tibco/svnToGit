/**
 * 
 */
package com.tibco.cep.studio.core.repo.emf;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage;
import com.tibco.cep.designtime.model.AddOnRegistry;
import com.tibco.cep.designtime.model.IAddOnLoader;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.registry.AddOn;
import com.tibco.cep.designtime.model.registry.AddOnType;
import com.tibco.cep.repo.BEProject;
import com.tibco.cep.repo.Project;
import com.tibco.cep.repo.ResourceProvider;
import com.tibco.cep.repo.provider.AbstractAddOnIndexResourceProvider;
import com.tibco.cep.repo.provider.OntologyResourceProvider;
import com.tibco.cep.repo.provider.SharedArchiveResourceProvider;
import com.tibco.cep.repo.provider.impl.GlobalVariablesProviderImpl;
import com.tibco.cep.repo.provider.impl.SharedArchiveResourceProviderImpl;
import com.tibco.cep.studio.common.configuration.ConfigurationFactory;
import com.tibco.cep.studio.common.configuration.ConfigurationPackage;
import com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry;
import com.tibco.cep.studio.common.configuration.JavaSourceFolderEntry;
import com.tibco.cep.studio.common.configuration.LIBRARY_PATH_TYPE;
import com.tibco.cep.studio.common.configuration.ProjectLibraryEntry;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.common.configuration.StudioProjectConfigurationCache;
import com.tibco.cep.studio.common.util.Path;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.repo.EMFTnsCache;
import com.tibco.cep.studio.core.repo.emf.providers.EMFOntologyResourceProvider;
import com.tibco.cep.studio.core.utils.GlobalVarUtils;
import com.tibco.cep.util.PlatformUtil;
import com.tibco.objectrepo.vfile.VFileStream;

/**
 * The EMF version of a BE project.
 * @author aathalye
 *
 */
public class EMFProject extends BEProject implements Project {
	
	protected static final String BE_PROJECT_CONFIG = ".beproject";
    static {
        ResourceFactoryRegistryImpl.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
        final EPackage.Registry i = EPackage.Registry.INSTANCE;
        i.put("http:///com/tibco/cep/studio/common/configuration/model/project_configuration.ecore", ConfigurationPackage.eINSTANCE);        
        i.put("http:///com/tibco/cep/decision/table/model/DecisionTable.ecore", DtmodelPackage.eINSTANCE);        
    }
    
    protected AddOnRegistry registry = null;
			
	/**
	 * The name of the project
	 */
//	protected String projectName;
	/**
	 * The EMF ontology model index
	 */
	protected DesignerProject runtimeIndex;
	/**
	 * project configuration stored in .beproject
	 */
	protected StudioProjectConfiguration projectConfig;
	protected SharedArchiveResourceProvider sarProvider;
	protected Map<AddOnType,AbstractAddOnIndexResourceProvider> indexResourceProviderMap;
	ThreadLocal platformLocal = new ThreadLocal();

	protected String[] projLibPaths;
	
	
	/**
	 * @param providers
	 * @param path
	 */
	protected EMFProject(List<ResourceProvider> providers, String path) {
		super(providers, path);
	}
	
	

	/**
	 * @param path
	 */
	public EMFProject(String path) {
		super(path);		
	}
	


	/* (non-Javadoc)
	 * @see com.tibco.cep.repo.BEProject#getName()
	 */
	@Override
	public String getName() {
		if(projectConfig != null) {
			return projectConfig.getName();
		} else {
			return null;
		}
	}
	
	@Override
	public EMFTnsCache getTnsCache() {
		if(this.tnsCache == null) {
			this.tnsCache = new EMFTnsCache(getRepoPath(), getName());
		}
		return (EMFTnsCache) this.tnsCache;
	}
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.repo.BEProject#init()
	 */
	@Override
	protected void init() {
		/**
		 * I am not sure which implementation of TnsCache we want to bring here.
		 * The EMFTnsCache uses certain UI classes which we cannot bring here
		 */
//		this.tnsCache = new TargetNamespaceCache();
        globalvariables = new GlobalVariablesProviderImpl();
        indexResourceProviderMap = new HashMap<AddOnType,AbstractAddOnIndexResourceProvider>();
        try {
        	Collection<AddOn> addons = AddOnRegistry.getInstance().getAddons(); 
//        	Map<Class<?>, AbstractAddOnIndexResourceProvider> processedProviders = new HashMap<Class<?>, AbstractAddOnIndexResourceProvider>();
	        for(AddOn a: addons) {
	        		String ipClazzName = a.getIndexResourceProviderClass();
					Class<?> ipClazz = null;
					try {
						ipClazz = (Class<AbstractAddOnIndexResourceProvider>) Class.forName(ipClazzName);
					} catch (ClassNotFoundException e) {
						Map<AddOnType,IAddOnLoader> addonmap = AddOnRegistry.getInstance().getInstalledAddOnMap();
						ipClazz = addonmap.get(a.getType()).getAddonClass(ipClazzName);						
					}
					// WIP
//					if (processedProviders.containsKey(ipClazz)) {
//						indexResourceProviderMap.put(a.getType(), processedProviders.get(ipClazz));
//						continue;
//					}
					
					if(ipClazz != null) {
						Constructor<AbstractAddOnIndexResourceProvider> cons = (Constructor<AbstractAddOnIndexResourceProvider>) ipClazz.getConstructor(EMFProject.class);
						AbstractAddOnIndexResourceProvider ip = (AbstractAddOnIndexResourceProvider) cons.newInstance(this);
						indexResourceProviderMap.put(a.getType(), ip);
						providerFactory.registerProvider(ip);
//						processedProviders.put(ipClazz, ip);
					}
	        }
        } catch (Exception e) {				
        	e.printStackTrace();
        } 
        createOntologyProvider();
        
        this.sarProvider = new SharedArchiveResourceProviderImpl();
        providerFactory.registerProvider(ontologyProvider);
        providerFactory.registerProvider(globalvariables);
        providerFactory.registerProvider(sarProvider);
	}
	
	/**
	 * Create the Ontology Provider
	 */
	protected void createOntologyProvider(){
		if (this.ontologyProvider == null){
			ontologyProvider = new EMFOntologyResourceProvider(this);
		}
	}
	
	/**
	 * @return
	 */
	public Map<AddOnType, AbstractAddOnIndexResourceProvider> getIndexResourceProviderMap() {
		return indexResourceProviderMap;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.repo.BEProject#getOntologyProvider()
	 */
	@Override
	public OntologyResourceProvider getOntologyProvider() {
		return ontologyProvider;
	}
	
	


	/* (non-Javadoc)
	 * @see com.tibco.cep.repo.BEProject#isValidDesignerProject()
	 */
	@Override
	public boolean isValidDesignerProject() {
		try {
			return getProjectConfiguration() != null;
		} catch (Exception e) {
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.repo.BEProject#load()
	 */
	@Override
	public void load() throws Exception {
		preload();
		super.load();
		postload();
	}
	
	protected void scanFile(VFileStream file, String path, Ontology ontology)
			throws Exception {
		final String uri = file.getFullURI();
		for (Iterator r = providerFactory.getProviders().iterator(); r
				.hasNext();) {
			final ResourceProvider provider = (ResourceProvider) r.next();

			if (provider != null && provider.supportsResource(uri)) {
				final int ret = provider.deserializeResource(uri, file.getInputStream(), this, file);
				if ((ret & ResourceProvider.STOP) == ResourceProvider.STOP) {
					break;
				}
			}
		}

	}

	protected void preload() throws Exception {
		java.net.URI configUri = null;
		if(vfFactory.get(BE_PROJECT_CONFIG) != null) {
			String projectConfigFile = "file:"+vfFactory.get(BE_PROJECT_CONFIG).getFullURI();
			projectConfigFile = projectConfigFile.replaceAll(" ", "%20");
			configUri = new java.net.URI(projectConfigFile);
		} else {
			File repoFile = new File(this.getRepoPath());
			if (repoFile.isFile()) {
				final java.net.URI repoUri = repoFile.toURI();
				configUri = new java.net.URI("archive:jar:" + repoUri + "!/Shared%20Archive.sar!/"+BE_PROJECT_CONFIG);
			}
		}
		if(configUri != null) {
			
			this.projectConfig = loadProjectConfiguration(configUri.toString());
			
			GlobalVarUtils.loadProjectLibGlobalVariables(this.globalvariables, this.projectConfig);
			/**
        	 * using delegated indexing for each addon
        	 */
			for(AbstractAddOnIndexResourceProvider arp: indexResourceProviderMap.values()) {
				arp.preload();
				arp.setName(projectConfig.getName());
				arp.loadProjectLibraries(projectConfig);
				arp.postLoad();
			}
		} else {
			// for RMS buildear the deployed project does not have .beproject configuration
			// therefore the project config name should be derived from the repo path
			Path p = new Path(getRepoPath());
			this.projectConfig = ConfigurationFactory.eINSTANCE.createStudioProjectConfiguration();
			this.projectConfig.setName(p.removeFileExtension().lastSegment());
			EnterpriseArchiveEntry eaconfig = ConfigurationFactory.eINSTANCE.createEnterpriseArchiveEntry();
			this.projectConfig.setEnterpriseArchiveConfiguration(eaconfig);
			this.projectConfig.getEnterpriseArchiveConfiguration().setName(this.projectConfig.getName());
			eaconfig.setName(this.projectConfig.getName());
			if(getProjLibPaths() != null) {  //projectlib specified from -pl
				for(String plpath: getProjLibPaths()) {
					File f = new File(plpath);
					if(f.exists() && f.isFile()){
						ProjectLibraryEntry pl = ConfigurationFactory.eINSTANCE.createProjectLibraryEntry();
						pl.setEntryType(LIBRARY_PATH_TYPE.PROJECT_LIBRARY);
						pl.setPath(plpath);
						pl.setResolvedPath(plpath);
						pl.setReadOnly(true);
						pl.setVar(false);
						this.projectConfig.getProjectLibEntries().add(pl);
					}
				}
			}
			File fXmlFile = new File(p+"/.classpath"); //read custom JavaSrc folders from .classpath file
			if(!fXmlFile.exists()){
				JavaSourceFolderEntry srcFolderEntry = ConfigurationFactory.eINSTANCE.createJavaSourceFolderEntry();
				srcFolderEntry.setEntryType(LIBRARY_PATH_TYPE.JAVA_SOURCE_FOLDER);
				srcFolderEntry.setPath("/" + p.removeFileExtension().lastSegment() + "/JavaSrc");
				srcFolderEntry.setReadOnly(true);
				this.projectConfig.getJavaSourceFolderEntries().add(srcFolderEntry);
			}else{
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				try {
					Document projectFile = dBuilder.parse(fXmlFile);
					projectFile.getDocumentElement().normalize();
					NodeList srcfolderEntries = projectFile.getElementsByTagName("classpathentry");
					for (int temp = 0; temp < srcfolderEntries.getLength(); temp++) {
						Node nNode = srcfolderEntries.item(temp);
						if (nNode.getNodeType() == Node.ELEMENT_NODE) {
							Element eElement = (Element) nNode;
							if(eElement.getAttribute("kind").equalsIgnoreCase("src")){
								String srcPath = eElement.getAttribute("path");
								JavaSourceFolderEntry srcFolderEntry = ConfigurationFactory.eINSTANCE.createJavaSourceFolderEntry();
								srcFolderEntry.setEntryType(LIBRARY_PATH_TYPE.JAVA_SOURCE_FOLDER);
								srcFolderEntry.setPath("/" + p.removeFileExtension().lastSegment() + "/"+srcPath);
								srcFolderEntry.setReadOnly(true);
								this.projectConfig.getJavaSourceFolderEntries().add(srcFolderEntry);
							}
						}
					}
				} catch (SAXException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			StudioProjectConfigurationCache.getInstance().put(this.projectConfig.getName() , this.projectConfig);
			GlobalVarUtils.loadProjectLibGlobalVariables(this.globalvariables, this.projectConfig);
			for(AbstractAddOnIndexResourceProvider arp: indexResourceProviderMap.values()) {
				arp.preload();
				arp.setName(this.projectConfig.getName());
				arp.loadProjectLibraries(this.projectConfig);
				arp.postLoad();
			}
		}
	}
	
	protected void postload() throws Exception {
        this.cacheSmElements(); 
        for(AbstractAddOnIndexResourceProvider arp: indexResourceProviderMap.values()) {
			arp.postLoad();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.repo.BEProject#getOntology()
	 */
	@Override
	public Ontology getOntology() {
		return ontologyProvider.getOntology();
	}
	
	
	public EObject getRuntimeIndex(AddOnType type) {
		AbstractAddOnIndexResourceProvider ip=  indexResourceProviderMap.get(type);
		return ip.getIndex();
	}
	
	public String getRepoPath() {
		return this.repoPath;
	}

    
    public StudioProjectConfiguration getProjectConfiguration(){
		return this.projectConfig;
	}
    
    public StudioProjectConfiguration loadProjectConfiguration(String uri) throws Exception {
    	StudioProjectConfiguration projectConfig = null;
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = resourceSet.createResource(URI.createURI(uri));
		Map<Object,Object> options = new HashMap<Object,Object>();
		options.put( XMIResource.OPTION_PROCESS_DANGLING_HREF, XMIResource.OPTION_PROCESS_DANGLING_HREF_DISCARD );
		resource.load(options);
		if(resource.getContents().size() > 0) {
			projectConfig = (StudioProjectConfiguration) resource.getContents().get(0);
			if(projectConfig.getName()==null|| projectConfig.getName().isEmpty()) {
				Path p = new Path(uri);
				projectConfig.setName(p.removeFileExtension().lastSegment());
			}
			/* Override project libs if specified from command line */
			
			if(getProjLibPaths() != null) {
				projectConfig.getProjectLibEntries().clear();
				for(String plpath: getProjLibPaths()) {
					File f = new File(plpath);
					if(f.exists() && f.isFile()){
						ProjectLibraryEntry p = ConfigurationFactory.eINSTANCE.createProjectLibraryEntry();
						p.setEntryType(LIBRARY_PATH_TYPE.PROJECT_LIBRARY);
						p.setPath(plpath);
						p.setResolvedPath(plpath);
						p.setReadOnly(true);
						p.setVar(false);
						projectConfig.getProjectLibEntries().add(p);
					}
				}
				
			}
			StudioProjectConfigurationCache.getInstance().put(projectConfig.getName() , projectConfig);
		}		
		
		if(PlatformUtil.INSTANCE.isStudioPlatform()) {
			Object scmObj = platformLocal.get();
			if(scmObj != null) {
				Method m_loadCoreLib = scmObj.getClass().getDeclaredMethod("loadCoreInternalLibraries", StudioProjectConfiguration.class);
				m_loadCoreLib.invoke(null, projectConfig);
				Method m_resolveLibPath = scmObj.getClass().getDeclaredMethod("resolveLibraryEntriesPath", StudioProjectConfiguration.class);
				m_resolveLibPath.invoke(null, projectConfig);
			}			
			
		}

		return projectConfig;
    }
    
    public void setStudionProjectConfigManager(Object platform) {
    	if(platformLocal.get() == null) {
    		platformLocal.set(platform);
    	}
    }
    
    public void removeStudionProjectConfigManager() {
    	platformLocal.remove();
    }



	public void setProjectLibraries(String[] projLibPaths) {
		this.projLibPaths=projLibPaths;
		
	}
	
	public String[] getProjLibPaths() {
		return projLibPaths;
	}
	
    	
	
}
