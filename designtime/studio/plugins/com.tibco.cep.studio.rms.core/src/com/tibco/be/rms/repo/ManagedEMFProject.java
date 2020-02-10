package com.tibco.be.rms.repo;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Collection;
import java.util.EnumMap;
import java.util.LinkedHashSet;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decisionproject.ontology.OntologyPackage;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.ModelPackage;
import com.tibco.cep.designtime.core.model.archive.ArchivePackage;
import com.tibco.cep.designtime.core.model.element.ElementPackage;
import com.tibco.cep.designtime.core.model.event.EventPackage;
import com.tibco.cep.designtime.core.model.java.JavaPackage;
import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.designtime.core.model.rule.RulePackage;
import com.tibco.cep.designtime.core.model.service.channel.ChannelPackage;
import com.tibco.cep.designtime.core.model.states.StatesPackage;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.registry.AddOnType;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.provider.OntologyResourceProvider;
import com.tibco.cep.security.authz.utils.IOUtils;
import com.tibco.cep.studio.common.StudioProjectCache;
import com.tibco.cep.studio.common.configuration.ConfigurationFactory;
import com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.IndexPackage;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexBuilder;
import com.tibco.cep.studio.core.repo.EMFTnsCache;
import com.tibco.cep.studio.core.repo.emf.EMFProject;
import com.tibco.cep.studio.core.repo.emf.providers.CoreIndexResourceProvider;
import com.tibco.cep.studio.core.utils.CommonRuleCreator;
import com.tibco.cep.studio.rms.artifacts.ArtifactsType;


/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: Sep 14, 2010
 * Time: 12:15:58 PM
 * <!--
 * Add Description of the class here
 * -->
 */
public class ManagedEMFProject extends EMFProject {

    private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(ManagedEMFProject.class);

    static {
        ResourceFactoryRegistryImpl.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
        final EPackage.Registry registry = EPackage.Registry.INSTANCE;
        registry.put("http:///com/tibco/cep/designtime/core/model/designtime_ontology.ecore", ModelPackage.eINSTANCE);
        registry.put("http:///com/tibco/cep/designer/index/core/model/ontology_index.ecore", IndexPackage.eINSTANCE);
        registry.put("http:///com/tibco/cep/designtime/core/model/element", ElementPackage.eINSTANCE);
        registry.put("http:///com/tibco/cep/designtime/core/model/event", EventPackage.eINSTANCE);
        registry.put("http:///com/tibco/cep/designtime/core/model/service/channel", ChannelPackage.eINSTANCE);
        registry.put("http:///com/tibco/cep/designtime/core/model/rule", RulePackage.eINSTANCE);
        registry.put("http:///com/tibco/cep/designtime/core/model/states", StatesPackage.eINSTANCE);
        registry.put("http:///com/tibco/cep/designtime/core/model/archive", ArchivePackage.eINSTANCE);
        registry.put("http:///com/tibco/cep/decision/table/model/DecisionTable.ecore", DtmodelPackage.eINSTANCE);
        registry.put("http:///com/tibco/cep/decisionproject/model/ontology.ecore", OntologyPackage.eINSTANCE);
        registry.put("http:///com/tibco/cep/designtime/core/model/java", JavaPackage.eINSTANCE);
    }

    /**
     * Absolute path of the managed project
     */
    private String projectPath;

    private String projectName;

    /**
     * The container directory hosting managed artifacts.
     * defaults to "decisiondata"
     */
    private String projectContainerDirectory;

    /**
     * Keep cache of all entities in this project.
     * TODO Handle new ones
     */
    private EnumMap<ArtifactsType, Collection<EObject>> allArtifacts = new EnumMap<ArtifactsType, Collection<EObject>>(ArtifactsType.class);

    /**
     * Root directory of a managed project
     * @param projectPath
     */
    public ManagedEMFProject(String projectPath) {
        this(projectPath, "decisiondata");
    }

    public CoreIndexResourceProvider getIndexProvider() {
        throw new UnsupportedOperationException("Index Provider not supported");
    }

    @Override
    public StudioProjectConfiguration getProjectConfiguration() {
    	return this.projectConfig;
    }
    

    @Override
    public EObject getRuntimeIndex(AddOnType type) {
       return super.getRuntimeIndex(type);
    }

    @SuppressWarnings("unchecked")
	@Override
    public EMFTnsCache getTnsCache() {
        throw new UnsupportedOperationException("TnsCache Not Supported");
    }

    @Override
    public boolean isValidDesignerProject() {
        return false;
    }

    @Override
    public StudioProjectConfiguration loadProjectConfiguration(String string) throws Exception {
        throw new UnsupportedOperationException("Project Configuration absent for this project");
    }

    /**
     * Root directory of a managed project
     * @param projectPath
     */
    public ManagedEMFProject(String projectPath, String projectContainerDirectory) {
        super(projectPath);

        this.projectPath = projectPath;
        this.projectContainerDirectory = projectContainerDirectory;
        
        File projectFile = new File(projectPath);
        projectName = projectFile.getName();
    }

    @Override
    protected void init() {
    	super.init();
    }
    

    @Override
    protected void createOntologyProvider() {
    	if (this.ontologyProvider == null){
    		this.ontologyProvider = new ManagedOntologyResourceProvider(this);
    		try {
    			ontologyProvider.init();
    		} catch (Exception e) {
    			throw new RuntimeException(e);
    		}
    	}
    }

    @Override
    public void load() throws Exception {
        this.projectConfig = ConfigurationFactory.eINSTANCE.createStudioProjectConfiguration();
        this.projectConfig.setName(projectName);
        
        EnterpriseArchiveEntry eaconfig = ConfigurationFactory.eINSTANCE.createEnterpriseArchiveEntry();
		this.projectConfig.setEnterpriseArchiveConfiguration(eaconfig);
		this.projectConfig.getEnterpriseArchiveConfiguration().setName(this.projectConfig.getName());
		eaconfig.setName(this.projectConfig.getName());
		
        IndexBuilder indexBuilder = new IndexBuilder(new File(projectPath), this.projectConfig);
        //Create and cache index
        DesignerProject designerProject = indexBuilder.loadProject(false);
        StudioProjectCache.getInstance().putIndex(projectName, designerProject);
        //Read all project artifacts here and cache them
        File projectContainer = new File(projectPath, projectContainerDirectory);

        if (!projectContainer.exists()) {
            throw new Exception("Project path " + projectContainer.getAbsolutePath() + " is invalid");
        }
        if (!projectContainer.isDirectory()) {
            throw new Exception("Project path " + projectContainer.getAbsolutePath() + " is not a directory");
        }
        List<ArtifactsType> allTypes = ArtifactsType.VALUES;

        for (ArtifactsType artifactsType : allTypes) {
            allArtifacts.put(artifactsType, loadFiles(projectContainer, artifactsType));
        }
    }

    private Collection<EObject> loadFiles(File rootDirectory, ArtifactsType artifactsType) {
        String artifactExtension = artifactsType.getLiteral();

        final Collection<EObject> allArtifactFiles = new LinkedHashSet<EObject>();

        listFilesRecursive(allArtifactFiles, rootDirectory, artifactExtension);

        return allArtifactFiles;
    }

    @Override
    public OntologyResourceProvider<?> getOntologyProvider() {
        return ontologyProvider;
    }

    @Override
    public Ontology getOntology() {
        return ontologyProvider.getOntology();
    }

    /**
     *
     * @param artifactsFileslist
     * @param rootDir
     * @param artifactExtension
     */
    private void listFilesRecursive(final Collection<EObject> artifactsFileslist,
                                    File rootDir,
                                    final String artifactExtension) {
        String[] children = rootDir.list(new FilenameFilter() {
            public boolean accept(File dir, String file) {
                //All directories
                File tempFile = new File(dir, file);
                if (!tempFile.isDirectory() &&
                        tempFile.getAbsolutePath().endsWith(artifactExtension)) {
                    try {
                        if (ArtifactsType.RULE.getLiteral().intern() == artifactExtension.intern()
                                || ArtifactsType.RULEFUNCTION.getLiteral() == artifactExtension.intern()) {
                            CommonRuleCreator commonRuleCreator = new CommonRuleCreator(true, true);
                            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                                LOGGER.log(Level.DEBUG, "Reading Rule/RF File %s", tempFile.getAbsolutePath());
                            }
                            Compilable compilable = commonRuleCreator.createRule(tempFile, projectName);
                            if (compilable != null) {
                                artifactsFileslist.add(compilable);
                            }
                        } else {
                            //Check if it is supported
                            ArtifactsType supportedArtifactsType = ArtifactsType.get(artifactExtension);
                            if (supportedArtifactsType == null) {
                                return false;
                            }
                            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                                LOGGER.log(Level.DEBUG, "Reading File %s", tempFile.getAbsolutePath());
                            }
                            //Get byte contents
                      //      byte[] contents = ArtifactsHelper.readFileContents(tempFile.getAbsolutePath(), 0, -1);
                            byte[] contents = IOUtils.readBytes(tempFile.getAbsolutePath());
                            EObject eObject = CommonIndexUtils.deserializeEObject(contents);
                            if (eObject != null) {
                                artifactsFileslist.add(eObject);
                            }
                        }
                        return false;
                    } catch (Exception e) {
                        LOGGER.log(Level.WARN, "Error Reading File %s", tempFile.getAbsolutePath());
                        if (LOGGER.isEnabledFor(Level.DEBUG)) {
                            LOGGER.log(Level.DEBUG, e, null);
                        }
                    }
                }
                return true;
            }
        });
        if (children == null) {
            return;
        }
        for (String child : children) {
            //Assuming it is a directory
            listFilesRecursive(artifactsFileslist, new File(rootDir, child), artifactExtension);
        }
    }

    /**
     *
     * @param artifactsTypes
     * @return
     */
    public Collection<Entity> getEntities(ArtifactsType[] artifactsTypes) {
        Collection<Entity> matchingEntities = new LinkedHashSet<Entity>();
        for (ArtifactsType artifactsType : artifactsTypes) {
            Collection<EObject> eObjects = allArtifacts.get(artifactsType);
            if (eObjects != null) {
                for (EObject eObject : eObjects) {
                    if (eObject instanceof Entity) {
                        matchingEntities.add((Entity)eObject);
                    }
                }
            }
        }
        return matchingEntities;
    }

    /**
     *
     * @param artifactsType
     * @param artifactPath
     * @return
     */
    public Entity getEntity(ArtifactsType artifactsType, String artifactPath) {
        if (artifactPath == null) {
            return null;
        }
        Collection<EObject> matchingEntities = allArtifacts.get(artifactsType);
        for (EObject matchingEObject : matchingEntities) {
            if (matchingEObject instanceof Entity) {
                Entity matchingEntity = (Entity)matchingEObject;
                if (matchingEntity.getFullPath().intern() == artifactPath.intern()) {
                    return matchingEntity;
                }
            }
        }
        return null;
    }

    /**
     *
     * @param artifactsTypes
     * @param artifactPath
     * @return
     */
    public Entity getEntity(ArtifactsType[] artifactsTypes, String artifactPath) {
        for (ArtifactsType artifactsType : artifactsTypes) {
            Entity matchingEntity = getEntity(artifactsType, artifactPath);
            if (matchingEntity != null) {
                return matchingEntity;
            }
        }
        return null;
    }

    /**
     *
     * @param artifactPath
     * @return
     */
    public Entity getEntity(String artifactPath) {
        for (ArtifactsType artifactsType : ArtifactsType.values()) {
            Entity matchingEntity = getEntity(artifactsType, artifactPath);
            if (matchingEntity != null) {
                return matchingEntity;
            }
        }
        return null;
    }

    /**
     *
     * @param artifactsType
     * @param artifactPath
     * @return
     */
    public Compilable getRuleElement(ArtifactsType artifactsType, String artifactPath) {
        Collection<EObject> ruleElements = allArtifacts.get(artifactsType);

        for (EObject eObject : ruleElements) {
            Compilable compilable = (Compilable)eObject;
            if (compilable.getFullPath().intern() == artifactPath.intern()) {
                return compilable;
            }
        }
        return null;
    }

    /**
     *
     * @param artifactPath
     * @return
     */
    public Table getDecisionTable(String artifactPath) {
        if (artifactPath == null) {
            return null;
        }
        Collection<EObject> matchingArtifacts = allArtifacts.get(ArtifactsType.DECISIONTABLE);
        for (EObject eObject : matchingArtifacts) {
            Table table = (Table)eObject;
            String tablePath = (table.getFolder() + table.getName()).intern();
            if (tablePath == artifactPath.intern()) {
                return table;
            }
        }
        return null;
    }

    /* (non-Javadoc)
	 * @see com.tibco.cep.repo.BEProject#getName()
	 */
	@Override
	public String getName() {
        return projectName;
    }

    public static void main(String[] args) throws Exception {
        ManagedEMFProject managedEMFProject = new ManagedEMFProject("C:\\tibco\\be\\4.0\\4.0.1_014\\be\\4.0\\rms\\examples\\CreditCardApplication");
        managedEMFProject.init();
        managedEMFProject.load();
        System.out.println(managedEMFProject.getEntities(ArtifactsType.values()).size());
    }
}
