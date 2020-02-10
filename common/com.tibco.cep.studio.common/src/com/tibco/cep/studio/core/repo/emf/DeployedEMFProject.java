package com.tibco.cep.studio.core.repo.emf;

import static com.tibco.cep.studio.core.repo.RepoConstants.REPO_PATH;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.ArtifactConfig;
import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.be.util.config.cdd.ProcessingUnitConfig;
import com.tibco.cep.designtime.model.AbstractOntologyAdapter;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.EntityInputSource;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.registry.AddOnType;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.BEArchiveResource;
import com.tibco.cep.repo.ChangeListener;
import com.tibco.cep.repo.DeployedProject;
import com.tibco.cep.repo.hotdeploy.DirectoryDeployer;
import com.tibco.cep.repo.hotdeploy.HotDeployer;
import com.tibco.cep.repo.provider.AbstractAddOnIndexResourceProvider;
import com.tibco.cep.repo.provider.BEArchiveResourceProvider;
import com.tibco.cep.repo.provider.JavaArchiveResourceProvider;
import com.tibco.cep.repo.provider.SMapContentProvider;
import com.tibco.cep.repo.provider.SharedArchiveResourceProvider;
import com.tibco.cep.repo.provider.impl.JavaArchiveResourceProviderImpl;
import com.tibco.cep.repo.provider.impl.SMapContentProviderImpl;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.impl.RuleServiceProviderImpl;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.cep.studio.common.configuration.ConfigurationFactory;
import com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry;
import com.tibco.cep.studio.common.configuration.JavaSourceFolderEntry;
import com.tibco.cep.studio.common.configuration.LIBRARY_PATH_TYPE;
import com.tibco.cep.studio.common.configuration.ProjectLibraryEntry;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.common.configuration.StudioProjectConfigurationCache;
import com.tibco.cep.studio.common.resources.ecore.ANTLRResourceFactory;
import com.tibco.cep.studio.common.util.Path;
import com.tibco.cep.studio.core.adapters.CoreOntologyAdapter;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.repo.emf.providers.DeployedEMFBEArchiveResourceProvider;
import com.tibco.cep.studio.core.utils.GlobalVarUtils;
import com.tibco.cep.util.ResourceManager;
import com.tibco.objectrepo.vfile.tibrepo.RepoVFileFactory;

/**
 * We will require this class for hotdeployment and such needs.
 * <p>
 * The reason to create this class is we cannot simply use the
 * base class in the RSP as it works with Mutable Ontology.
 * </p>
 * @author aathalye
 *
 */
public class DeployedEMFProject extends EMFProject implements DeployedProject {

	protected Properties beProperties;

    protected ProcessingUnitConfig puConfig;

    protected ChangeListener changeListener;

	protected JavaArchiveResourceProvider jarProvider;

	protected DeployedEMFBEArchiveResourceProvider beArchiveResourceProvider;

	protected SMapContentProvider smapContentProvider;

    protected boolean hotDeployEnabled;
    protected HotDeployer hotDeployer;


    /**
	 * @param envProps
	 * @param changeListener
	 * @throws Exception
	 */
	public DeployedEMFProject(Properties envProps,
			                  ChangeListener changeListener) throws Exception {
		//TODO remove hard-coding
		super(envProps.getProperty(REPO_PATH));
		this.beProperties = envProps;
		this.changeListener = changeListener;
	}


    protected void init() {
		super.init();
		beArchiveResourceProvider = new DeployedEMFBEArchiveResourceProvider();
		jarProvider = new JavaArchiveResourceProviderImpl();
    	providerFactory.registerProvider(beArchiveResourceProvider);
    	providerFactory.registerProvider(jarProvider);
    	if (System.getProperty(SystemProperty.DEBUGGER_SERVICE_ENABLED.getPropertyName(), Boolean.FALSE.toString()).trim().equalsIgnoreCase(Boolean.TRUE.toString())) {
    		smapContentProvider = new SMapContentProviderImpl();
    		providerFactory.registerProvider(smapContentProvider);
        }
    }


	public BEArchiveResourceProvider getBEArchiveResourceProvider() {
		return beArchiveResourceProvider;
	}


	public Date getCreationDate() {
		return null;
	}


	/* (non-Javadoc)
	 * @see com.tibco.cep.repo.DeployedProject#getDeployedBEArchives()
	 */
	public Collection<BEArchiveResource> getDeployedBEArchives() {
		return this.beArchiveResourceProvider.getBEArchives();
	}


	public Properties getProperties() {
        return beProperties;
    }


    public ClusterConfig getClusterConfig() {
        return (ClusterConfig) this.beProperties.get(SystemProperty.CLUSTER_CONFIG.getPropertyName());
    }


    public ProcessingUnitConfig getProcessingUnitConfig() {
        if (null == this.puConfig) {
            final String id = this.beProperties.getProperty(SystemProperty.PROCESSING_UNIT_ID.getPropertyName());
            if (this.getClusterConfig() != null) {
            	this.puConfig = (ProcessingUnitConfig) CddTools.findById(
            			(EList<? extends ArtifactConfig>) this.getClusterConfig().getProcessingUnits().getProcessingUnit(), id);
            }
        }
        return this.puConfig;
    }


    /**
     * @return
     */
    public String getRepoPath() {
        return repoPath;
    }


	/* (non-Javadoc)
	 * @see com.tibco.cep.repo.DeployedProject#getJavaArchiveResourceProvider()
	 */
	public JavaArchiveResourceProvider getJavaArchiveResourceProvider() {
		return jarProvider;
	}


	public String getOwner() {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see com.tibco.cep.repo.DeployedProject#getSharedArchiveResourceProvider()
	 */
	public SharedArchiveResourceProvider getSharedArchiveResourceProvider() {
		return this.sarProvider;
	}


	/* (non-Javadoc)
	 * @see com.tibco.cep.repo.DeployedProject#getSMapContenProvider()
	 */
	@Override
	public SMapContentProvider getSMapContenProvider() {
		return smapContentProvider;
	}


	public String getVersion() {
        return this.globalvariables.getProjectVersion();
    }


	/* (non-Javadoc)
	 * @see com.tibco.cep.repo.DeployedProject#isCacheEnabled()
	 */
	public boolean isCacheEnabled() {
        return null != this.getClusterConfig().getObjectManagement().getCacheManager();
	}


	/* (non-Javadoc)
	 * @see com.tibco.cep.repo.DeployedBEProject#load()
	 */
	public void load()
            throws Exception {

        if (this.getClusterConfig() != null) {
            final String name = CddTools.getValueFromMixed(this.getClusterConfig().getName());
            System.setProperty("tangosol.coherence.cluster", name);
            System.setProperty(SystemProperty.CLUSTER_NAME.getPropertyName(), name);
        }

        super.load();
        if (this.beArchiveResourceProvider.getBEArchives().size() < 1) {
            // No BAR entry in the EAR, this is likely an Admin deployed project, so trigger the BAR building now.
            this.beArchiveResourceProvider.deserializeBARResource(null, null, this);
        }
        this.globalvariables.overwriteGlobalVariables(this.getProperties());
        this.globalvariables.validateGlobalVariables();
    }

	public void addObjectToTnsCache(Entity entity){
		final EntityInputSource source = new EntityInputSource(entity);
		if(entity instanceof Concept) {
			if(((Concept)entity).isAScorecard()) {
				final String path = entity.getFullPath()+".scorecard";
				source.setSystemId(path);
			} else {
				final String path = entity.getFullPath()+".concept";
				source.setSystemId(path);
			}
		} else if( entity instanceof Event) {
			final String path = entity.getFullPath()+".event";
			source.setSystemId(path);
		}
        getTnsCache().documentAddedOrChanged(source);
	}


    protected void loadVFiles() throws Exception {
        this.scanDirectory(vfFactory.getRootDirectory(), "", null);
    }


    public void startHotDeploy(RuleServiceProvider serviceProvider) throws Exception {
        this.checkHotDeployment(serviceProvider);
        if (this.hotDeployEnabled) {
            if (this.vfFactory instanceof RepoVFileFactory) {
            	//this.hotDeployer = new RepoDeployer(this, changeListener);
                throw new UnsupportedOperationException(); //todo?
            } else {
                this.hotDeployer = new DirectoryDeployer(this, changeListener);
            }
            this.hotDeployer.start();
            serviceProvider.getLogger(this.getClass()).log(Level.INFO, "Started Hot Deployer");
        }
    }


    public void stopHotDeploy(RuleServiceProvider serviceProvider) {
        if (this.hotDeployEnabled && (null != this.hotDeployer)) {
            this.hotDeployer.stop();
            serviceProvider.getLogger(this.getClass()).log(Level.INFO, "Stopped Hot Deployer...");
        }
    }


    private void checkHotDeployment(RuleServiceProvider serviceProvider) throws Exception {
        this.hotDeployEnabled = (! ((RuleServiceProviderImpl) serviceProvider).isCacheServerMode())
                && Boolean.valueOf(this.beProperties.getProperty("be.engine.hotDeploy.enabled"));
        final Logger logger = serviceProvider.getLogger(this.getClass());
        if (this.hotDeployEnabled) {
            if (((BEClassLoader) serviceProvider.getTypeManager()).useJDIforHotdeploy()
                    && Boolean.valueOf(this.beProperties.getProperty("tibco.env.be.debug"))) {
                logger.log(Level.WARN, ResourceManager.getInstance().getMessage("hotdeploy.debugger"));
            } else {
                final String msg = ((BEClassLoader) serviceProvider.getTypeManager()).classesNotRedefineableMsg();
                if (msg == null) {
                    logger.log(Level.INFO, ResourceManager.getInstance().formatMessage("hotdeploy.enabled",
                            ((BEClassLoader) serviceProvider.getTypeManager()).hotdeployMethod()));
                } else {
                    logger.log(Level.WARN, msg);
                    logger.log(Level.WARN, ResourceManager.getInstance().getMessage("hotdeploy.noModify"));
                }
            }
        } else {
            logger.log(Level.INFO, ResourceManager.getInstance().getMessage("hotdeploy.disabled"));
        }
    }


    protected void preload() throws Exception {
		final java.net.URI repoUri = new File(this.getRepoPath()).toURI();

        if (repoUri != null) {
            this.projectConfig = loadProjectConfiguration(repoUri);
            if (this.projectConfig != null) {
            	// TODO : enable to setup resource factory for source based resources
            	//ANTLRResourceFactory factory = new ANTLRResourceFactory(projectConfig.getName());
        		//ResourceFactoryRegistryImpl.INSTANCE.getExtensionToFactoryMap().put("concept", factory);
        		//ResourceFactoryRegistryImpl.INSTANCE.getExtensionToFactoryMap().put("event", factory);

            	/**
            	 * using delegated indexing for each addon
            	 */
    			for(AbstractAddOnIndexResourceProvider arp: indexResourceProviderMap.values()) {
    				arp.preload();
    				arp.setName(projectConfig.getName());
    				arp.loadProjectLibraries(projectConfig);
    			}

            }else{
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

    }

    @Override
    protected void postload() throws Exception {
    	super.postload(); // caching smElements
    	/**
    	 * using delegated indexing for each addon
    	 */
		for(AbstractAddOnIndexResourceProvider arp: indexResourceProviderMap.values()) {
			arp.postLoad(); //resolve any emf models that are dependent on tnscache
		}
    }


    protected StudioProjectConfiguration loadProjectConfiguration(java.net.URI repoUri) throws Exception {
        ResourceSet resourceSet = new ResourceSetImpl();
        final Resource resource = resourceSet.createResource(
                URI.createURI("archive:jar:" + repoUri.toString() + "!/Shared%20Archive.sar!/" + BE_PROJECT_CONFIG));
        Map<String,String> options = new HashMap<String,String>();
        options.put(XMIResource.OPTION_PROCESS_DANGLING_HREF, XMIResource.OPTION_PROCESS_DANGLING_HREF_DISCARD);

        final File repoFile = new File (repoUri);
        if (repoFile.isDirectory()) {
            final InputStream is = new FileInputStream(new File(repoFile, BE_PROJECT_CONFIG));
            try {
                resource.load(is, options);
            } finally {
                is.close();
            }

        } else {
            final int length = (int) repoFile.length();
            final byte[] fileAsBytes = new byte[length];
            final InputStream is = repoUri.toURL().openStream();
            try {
                for (int numRead = 0, offset = 0; (numRead >= 0) && (length > offset); offset += numRead) {
                    numRead = is.read(fileAsBytes, offset, length - offset);
                }
            } finally {
                is.close();
            }

            final ByteArrayInputStream bais = new ByteArrayInputStream(fileAsBytes);
            try {
                final ZipInputStream earZis = new ZipInputStream(bais);
                try {
                    for (ZipEntry earEntry = earZis.getNextEntry(); null != earEntry; earEntry = earZis.getNextEntry()) {
                        if ("Shared Archive.sar".equals(earEntry.getName())) {
                            final ZipInputStream sarZis = new ZipInputStream(earZis);
                            try {
                                for (ZipEntry sarEntry = sarZis.getNextEntry(); null != sarEntry; sarEntry = sarZis.getNextEntry()) {
                                    if (BE_PROJECT_CONFIG.equals(sarEntry.getName())) {
                                        resource.load(sarZis, options);
                                        break;
                                    }
                                }
                            } finally {
                                sarZis.close();
                            }
                            break;
                        }
                    }
                } finally {
                    earZis.close();
                }
            } finally {
                bais.close();
            }

        }

        StudioProjectConfiguration projectConfig = null;
        if (resource.getContents().size() > 0) {
        	projectConfig = (StudioProjectConfiguration) resource.getContents().get(0);
        	/**
        	 * project config must have a name
        	 */
        	if(projectConfig.getName()==null ||projectConfig.getName().isEmpty()) {
        		Path rpath = new Path(repoUri.toString());
        		projectConfig.setName(rpath.removeFileExtension().lastSegment());
        	}
        	StudioProjectConfigurationCache.getInstance().put(projectConfig.getName(), projectConfig);
        }
        return projectConfig;
    }

    public AbstractOntologyAdapter<EObject> getAbstractOntologyAdapter(AddOnType type) {
    	DesignerProject depIndex = (DesignerProject) getRuntimeIndex(type);
    	switch(type) {
    	case CORE:
    	case DATAMODELLING:
    	case EVENTSTREAMPROCESSING:
    	case DECISIONMANAGER:
    	case VIEWS:
    		return (AbstractOntologyAdapter) new CoreOntologyAdapter(depIndex);
    	case PROCESS:
    	default:
    		return null;
    	}
    	
    }
}
