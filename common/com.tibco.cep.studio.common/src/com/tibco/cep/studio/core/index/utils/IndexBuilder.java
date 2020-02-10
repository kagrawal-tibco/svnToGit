package com.tibco.cep.studio.core.index.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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

import com.tibco.cep.decisionproject.ontology.Implementation;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.ModelPackage;
import com.tibco.cep.designtime.core.model.archive.ArchivePackage;
import com.tibco.cep.designtime.core.model.archive.ArchiveResource;
import com.tibco.cep.designtime.core.model.element.ElementPackage;
import com.tibco.cep.designtime.core.model.event.EventPackage;
import com.tibco.cep.designtime.core.model.java.JavaPackage;
import com.tibco.cep.designtime.core.model.rule.RulePackage;
import com.tibco.cep.designtime.core.model.service.channel.ChannelPackage;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StatesPackage;
import com.tibco.cep.studio.common.configuration.ConfigurationFactory;
import com.tibco.cep.studio.common.configuration.ConfigurationPackage;
import com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry;
import com.tibco.cep.studio.common.configuration.JavaSourceFolderEntry;
import com.tibco.cep.studio.common.configuration.LIBRARY_PATH_TYPE;
import com.tibco.cep.studio.common.configuration.ProjectLibraryEntry;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.common.configuration.StudioProjectConfigurationCache;
import com.tibco.cep.studio.common.util.Path;
import com.tibco.cep.studio.core.index.model.ArchiveElement;
import com.tibco.cep.studio.core.index.model.DecisionTableElement;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.ElementContainer;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.IndexFactory;
import com.tibco.cep.studio.core.index.model.IndexPackage;
import com.tibco.cep.studio.core.index.model.JavaElement;
import com.tibco.cep.studio.core.index.model.JavaResourceElement;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.StateMachineElement;
import com.tibco.cep.studio.core.rules.IProblemHandler;

public class IndexBuilder {

	public static final String BE_PROJECT_CONFIG = ".beproject";
	static {
		ResourceFactoryRegistryImpl.INSTANCE.getExtensionToFactoryMap().put(
				"*", new XMIResourceFactoryImpl());
		final EPackage.Registry i = EPackage.Registry.INSTANCE;
		i.put("http:///com/tibco/cep/studio/common/configuration/model/project_configuration.ecore",
				ConfigurationPackage.eINSTANCE);
		i.put("http:///com/tibco/cep/designtime/core/model/designtime_ontology.ecore",
				ModelPackage.eINSTANCE);
		i.put("http:///com/tibco/cep/designer/index/core/model/ontology_index.ecore",
				IndexPackage.eINSTANCE);
		i.put("http:///com/tibco/cep/designtime/core/model/element",
				ElementPackage.eINSTANCE);
		i.put("http:///com/tibco/cep/designtime/core/model/event",
				EventPackage.eINSTANCE);
		i.put("http:///com/tibco/cep/designtime/core/model/service/channel",
				ChannelPackage.eINSTANCE);
		i.put("http:///com/tibco/cep/designtime/core/model/rule",
				RulePackage.eINSTANCE);
		i.put("http:///com/tibco/cep/designtime/core/model/states",
				StatesPackage.eINSTANCE);
		i.put("http:///com/tibco/cep/designtime/core/model/archive",
				ArchivePackage.eINSTANCE);
		i.put("http:///com/tibco/cep/designtime/core/model/java",
				JavaPackage.eINSTANCE);
	}

	private File projectPath;
	
	private StudioProjectConfiguration projectConfig;

	public IndexBuilder(File projectPath) {
		this(projectPath, null);
	}
	
	public IndexBuilder(File projectPath, StudioProjectConfiguration projectConfig) {
		this.projectPath = projectPath;
		this.projectConfig = projectConfig;
	}

	public boolean isAValidStudioProject() {
		try {
			return getProjectConfig() != null;
		} catch (IOException e) {
			return false;
		}
	}
	
	/**
	 * Same as {@link #loadProject(true)}
	 * @return
	 */
	public DesignerProject loadProject() {
		return loadProject(true);
	}
	
	/**
	 * 
	 * @param checkValidStudioProject -> If set to false, check for studio project is omitted.
	 * @return
	 */
	public DesignerProject loadProject(boolean checkValidStudioProject) {
		DesignerProject index = null;
		if (!checkValidStudioProject || isAValidStudioProject()) {
			List<File> resources =  new ArrayList<File>();
			getResources(projectPath, resources);
			index = IndexFactory.eINSTANCE.createDesignerProject();
			index.setName(projectConfig.getName());
			index.setRootPath(projectPath.getPath());
			for (File resource : resources) {
				Path resourcePath = new Path(resource.getPath());
				if (CommonIndexUtils.isEMFType(resourcePath.getFileExtension())) {
//					if(CommonIndexUtils.isJavaType(resourcePath.getFileExtension())){
//						indexJava(index, resource);
//					} else {
						EObject eObj = CommonIndexUtils.loadEObject(resource.toURI());
						indexEObject(index, resource, eObj);
//					}
					continue;
				}
				if (CommonIndexUtils.isRuleType(resourcePath.getFileExtension())) {
					indexRule(index, resource);
					continue;
				}
			}
			indexJavaSources(index, projectPath);
			indexProjectLibraries(index, new File(projectPath.getPath()));
		}
		return index;
	}

	private StudioProjectConfiguration getProjectConfig() throws IOException {
		if (this.projectConfig == null) {
			if (projectPath.exists()) {
				File projectConfigFile = new File(projectPath, BE_PROJECT_CONFIG);
				if(projectConfigFile.exists()){
					ResourceSet resourceSet = new ResourceSetImpl();
					Resource resource = resourceSet.createResource(URI
							.createFileURI(projectConfigFile.getPath()));
					resource.load(null);
					this.projectConfig = (StudioProjectConfiguration) resource
							.getContents().get(0);
				}else{
					Path p = new Path(projectPath.getAbsolutePath());
					projectConfig = ConfigurationFactory.eINSTANCE.createStudioProjectConfiguration();
					projectConfig.setName(p.removeFileExtension().lastSegment());
					EnterpriseArchiveEntry eaconfig = ConfigurationFactory.eINSTANCE.createEnterpriseArchiveEntry();
					projectConfig.setEnterpriseArchiveConfiguration(eaconfig);
					projectConfig.getEnterpriseArchiveConfiguration().setName(projectConfig.getName());
					eaconfig.setName(projectConfig.getName());
					File fXmlFile = new File(p+"/.classpath"); //read custom JavaSrc folders from .classpath file
					if(!fXmlFile.exists()){
						JavaSourceFolderEntry srcFolderEntry = ConfigurationFactory.eINSTANCE.createJavaSourceFolderEntry();
						srcFolderEntry.setEntryType(LIBRARY_PATH_TYPE.JAVA_SOURCE_FOLDER);
						srcFolderEntry.setPath("/" + p.removeFileExtension().lastSegment() + "/JavaSrc");
						srcFolderEntry.setReadOnly(true);
						projectConfig.getJavaSourceFolderEntries().add(srcFolderEntry);
					}else{
						DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
						try {
							DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
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
										projectConfig.getJavaSourceFolderEntries().add(srcFolderEntry);
									}
								}
							}
						} catch (SAXException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (ParserConfigurationException e) {
							e.printStackTrace();
						}
					}
					StudioProjectConfigurationCache.getInstance().put(projectConfig.getName() , projectConfig);
				}
			}
		}
		return this.projectConfig;
	}
	
	public static void indexJavaSources(DesignerProject index, File projectPath) {
		
		try {
			String parentPath  = projectPath.getParent();
			StudioProjectConfiguration studioProjectConfig = getProjectConfig(projectPath);
			for (JavaSourceFolderEntry entry : studioProjectConfig.getJavaSourceFolderEntries()) {
				 File javaRootFolder = new File(parentPath + entry.getPath(true));
				 List<File> javaFiles =  new ArrayList<File>();
				 List<File> javaResources =  new ArrayList<File>();
				 getJavaPackageResources(javaRootFolder, javaFiles, javaResources);
				 
				 for (File resource : javaFiles) {
					 indexJava(index, resource);
				 }
				 
				 for (File resource : javaResources) {
					 indexJavaResources(index, resource);
				 }
				 
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static StudioProjectConfiguration getProjectConfig(File projectPath) throws IOException {
		File projectConfigFile = new File(projectPath, BE_PROJECT_CONFIG);
		if (projectConfigFile.exists() && projectConfigFile.canRead()) {
			ResourceSet resourceSet = new ResourceSetImpl();
			Resource resource = resourceSet.createResource(URI.createFileURI(projectConfigFile.getPath()));
			resource.load(null);
			StudioProjectConfiguration config = (StudioProjectConfiguration) resource.getContents().get(0);
			return config;
		}else{
			Path p = new Path(projectPath.getAbsolutePath());
			StudioProjectConfiguration projectConfig = ConfigurationFactory.eINSTANCE.createStudioProjectConfiguration();
			projectConfig.setName(p.removeFileExtension().lastSegment());
			EnterpriseArchiveEntry eaconfig = ConfigurationFactory.eINSTANCE.createEnterpriseArchiveEntry();
			projectConfig.setEnterpriseArchiveConfiguration(eaconfig);
			projectConfig.getEnterpriseArchiveConfiguration().setName(projectConfig.getName());
			eaconfig.setName(projectConfig.getName());
			File fXmlFile = new File(p+"/.classpath"); //read custom JavaSrc folders from .classpath file
			if(!fXmlFile.exists()){
				JavaSourceFolderEntry srcFolderEntry = ConfigurationFactory.eINSTANCE.createJavaSourceFolderEntry();
				srcFolderEntry.setEntryType(LIBRARY_PATH_TYPE.JAVA_SOURCE_FOLDER);
				srcFolderEntry.setPath("/" + p.removeFileExtension().lastSegment() + "/JavaSrc");
				srcFolderEntry.setReadOnly(true);
				projectConfig.getJavaSourceFolderEntries().add(srcFolderEntry);
			}else{
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				try {
					DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
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
								projectConfig.getJavaSourceFolderEntries().add(srcFolderEntry);
							}
						}
					}
				} catch (SAXException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					e.printStackTrace();
				}
			}
			StudioProjectConfigurationCache.getInstance().put(projectConfig.getName() , projectConfig);
			return projectConfig;
		}
	}

	private void getResources(File rootFolder, List<File> files) {
		File[] members = rootFolder.listFiles();
		for (File resource : members) {
			if (resource.isDirectory()) {
				getResources(resource, files);
			} else {
				Path p = new Path(resource.getPath());
				if (CommonIndexUtils.isEMFType(p.getFileExtension())
						|| CommonIndexUtils.isRuleType(p.getFileExtension())) {
					files.add(resource);
				}
			}
		}
	}

	private static void getJavaPackageResources(File rootFolder, List<File> javaFiles, List<File> javaResources) {
		File[] members = rootFolder.listFiles();
		if (members == null) {
			return;
		}
		for (File resource : members) {
			if (resource.isDirectory()) {
				getJavaPackageResources(resource, javaFiles, javaResources);
			} else {
				Path p = new Path(resource.getPath());
				if (CommonIndexUtils.isJavaType(p.getFileExtension())) {
					javaFiles.add(resource);
				} else {
					javaResources.add(resource);
				}
			}
		}
	}
	
	public static JavaElement indexJava(DesignerProject index, String uri,InputStream is) {
		JavaElement javaIndexEntry = new BaseJavaElementCreator(true)
		.createJavaElement(index.getName(),index.getRootPath(), uri, is);
		if (javaIndexEntry != null) {
			index.getEntityElements().add(javaIndexEntry);
			insertElement(index, javaIndexEntry, uri);
		}
		return javaIndexEntry;
	}
	
	public static JavaElement indexJava(DesignerProject index, File file) {
		JavaElement javaIndexEntry = new BaseJavaElementCreator(true)
			.createJavaElement(index.getName(), new File(index.getRootPath()), file);
		if (javaIndexEntry != null) {
			index.getEntityElements().add(javaIndexEntry);
			insertElement(index, javaIndexEntry, file);
		}
		return javaIndexEntry;
	}
	
	public static JavaResourceElement indexJavaResources(DesignerProject index, File file) {
		JavaResourceElement javaResourceIndexEntry = new BaseJavaElementCreator(true)
			.createJavaResourceElement(index.getName(), new File(index.getRootPath()), file);
		if (javaResourceIndexEntry != null) {
			index.getEntityElements().add(javaResourceIndexEntry);
			insertElement(index, javaResourceIndexEntry, file);
		}
		return javaResourceIndexEntry;
	}
	
	public static JavaResourceElement indexJavaResources(DesignerProject index, String uri,InputStream is) {
		JavaResourceElement javaResourceIndexEntry = new BaseJavaElementCreator(true)
		.createJavaResourceElement(index.getName(),index.getRootPath(), uri, is);
		if (javaResourceIndexEntry != null) {
			index.getEntityElements().add(javaResourceIndexEntry);
			insertElement(index, javaResourceIndexEntry, uri);
		}
		return javaResourceIndexEntry;
	}
	

	public static RuleElement indexRule(DesignerProject fIndex, File file) {
		RuleElement ruleIndexEntry = new BaseRuleElementCreator(true)
				.createRuleElement(fIndex.getName(), new File(fIndex
						.getRootPath()), file);
		if (ruleIndexEntry != null) {
			fIndex.getRuleElements().add(ruleIndexEntry);
			insertElement(fIndex, ruleIndexEntry, file);
		}
		return ruleIndexEntry;
	}

	public static RuleElement indexRule(DesignerProject fIndex, String uri,
			InputStream is, IProblemHandler problemHandler) {
		RuleElement ruleIndexEntry = new BaseRuleElementCreator(true)
				.createRuleElement(fIndex.getName(), fIndex.getRootPath(), uri,
						is, problemHandler);
		if (ruleIndexEntry != null) {
			fIndex.getRuleElements().add(ruleIndexEntry);
			insertElement(fIndex, ruleIndexEntry, uri);
		}
		return ruleIndexEntry;
	}

	public static void indexEObject(DesignerProject fIndex, File file,
			EObject eObj) {
		if (eObj instanceof StateMachine) {
			StateMachineElement smIndexEntry = new BaseStateMachineElementCreator()
					.createStateMachineElement(fIndex.getName(),
							(StateMachine) eObj);
			fIndex.getEntityElements().add(smIndexEntry);
			if (smIndexEntry.eResource() != null) {
				smIndexEntry.eResource().unload();
			}
			insertElement(fIndex, smIndexEntry, file);
		} else if (eObj instanceof Entity) {
			Entity entity = (Entity) eObj;
			EntityElement indexEntry = IndexFactory.eINSTANCE
					.createEntityElement();
			indexEntry.setEntity(entity);
			indexEntry.setName(entity.getName());
			indexEntry.setFolder(entity.getFolder());
			indexEntry.setElementType(CommonIndexUtils.getElementType(entity));
			fIndex.getEntityElements().add(indexEntry);
			if (entity.eResource() != null) {
				entity.eResource().unload();
			}
			insertElement(fIndex, indexEntry, file);
		} else if (eObj instanceof ArchiveResource) {
			ArchiveResource archive = (ArchiveResource) eObj;
			ArchiveElement archiveIndexEntry = IndexFactory.eINSTANCE
					.createArchiveElement();
			archiveIndexEntry.setName(archive.getName());
			archiveIndexEntry.setArchive(archive);
			archiveIndexEntry.setFolder(CommonIndexUtils.getFileFolder(
					new Path(fIndex.getRootPath()), new Path(file.getPath())));
			archiveIndexEntry.setElementType(ELEMENT_TYPES.ENTERPRISE_ARCHIVE);
			if (archive.eResource() != null) {
				archive.eResource().unload();
			}
			fIndex.getArchiveElements().add(archiveIndexEntry);
			insertElement(fIndex, archiveIndexEntry, file);
		} else if (eObj instanceof Implementation) {
			Implementation impl = (Implementation) eObj;
			DecisionTableElement decisionTableElement = IndexFactory.eINSTANCE
					.createDecisionTableElement();
			decisionTableElement.setName(impl.getName());
			decisionTableElement.setFolder(impl.getFolder());
			decisionTableElement.setElementType(ELEMENT_TYPES.DECISION_TABLE);
			decisionTableElement.setImplementation(impl);
			fIndex.getDecisionTableElements().add(decisionTableElement);
			insertElement(fIndex, decisionTableElement, file);
		}
	}

	public static DesignerElement indexEObject(DesignerProject fIndex,
			String entityUri, EObject eObj) {
		if (eObj instanceof StateMachine) {
			StateMachineElement smIndexEntry = new BaseStateMachineElementCreator()
					.createStateMachineElement(fIndex.getName(),
							(StateMachine) eObj);
			fIndex.getEntityElements().add(smIndexEntry);
			if (smIndexEntry.eResource() != null) {
				smIndexEntry.eResource().unload();
			}
			return insertElement(fIndex, smIndexEntry, entityUri);
		} else if (eObj instanceof Entity) {
			Entity entity = (Entity) eObj;
			EntityElement indexEntry = IndexFactory.eINSTANCE
					.createEntityElement();
			indexEntry.setEntity(entity);
			indexEntry.setName(entity.getName());
			indexEntry.setFolder(entity.getFolder());
			indexEntry.setElementType(CommonIndexUtils.getElementType(entity));
			fIndex.getEntityElements().add(indexEntry);
			if (entity.eResource() != null) {
				entity.eResource().unload();
			}
			return insertElement(fIndex, indexEntry, entityUri);
		} else if (eObj instanceof ArchiveResource) {
			ArchiveResource archive = (ArchiveResource) eObj;
			ArchiveElement archiveIndexEntry = IndexFactory.eINSTANCE
					.createArchiveElement();
			archiveIndexEntry.setName(archive.getName());
			archiveIndexEntry.setArchive(archive);
			archiveIndexEntry.setFolder(CommonIndexUtils.getFileFolder(
					new Path(fIndex.getRootPath()), new Path(entityUri)));
			archiveIndexEntry.setElementType(ELEMENT_TYPES.ENTERPRISE_ARCHIVE);
			if (archive.eResource() != null) {
				archive.eResource().unload();
			}
			fIndex.getArchiveElements().add(archiveIndexEntry);
			return insertElement(fIndex, archiveIndexEntry, entityUri);
		} else if (eObj instanceof Implementation) {
			Implementation impl = (Implementation) eObj;
			DecisionTableElement decisionTableElement = IndexFactory.eINSTANCE
					.createDecisionTableElement();
			decisionTableElement.setName(impl.getName());
			decisionTableElement.setFolder(impl.getFolder());
			decisionTableElement.setElementType(ELEMENT_TYPES.DECISION_TABLE);
			decisionTableElement.setImplementation(impl);
			fIndex.getDecisionTableElements().add(decisionTableElement);
			return insertElement(fIndex, decisionTableElement, entityUri);
		}
		return null;
	}

	public static void indexProjectLibraries(DesignerProject fIndex,
			                                 File projectPath) {
		/*
		 * Get all project libs For each lib, get the jar file For each jar
		 * file, invoke a jar file visitor to index entries
		 */
		try {
			StudioProjectConfiguration configuration = getProjectConfig(projectPath);
			if (configuration != null) {
				for (ProjectLibraryEntry ple : configuration.getProjectLibEntries()) {
					String libRef = ple.getPath(ple.isVar());
					JarFile file = new JarFile(libRef);
					System.out.println("indexing " + file.getName());
					DesignerProject project = IndexFactory.eINSTANCE
							.createDesignerProject();
					project.setName(file.getName());
					project.setRootPath(libRef);
					project.setArchivePath(libRef);
					BaseBinaryStorageIndexCreator creator = new BaseBinaryStorageIndexCreator(
							configuration.getName(), project, file);
					creator.index(projectPath);
					fIndex.getReferencedProjects().add(project);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param fIndex
	 * @param entry
	 * @param file
	 */
	public static String insertElement(DesignerProject fIndex,
			DesignerElement entry, File file) {
		String filePath = CommonIndexUtils.getRelativeFilePath(new Path(fIndex
				.getRootPath()), new Path(file.getPath()));
		Path parentPath = new Path(filePath);
		parentPath = parentPath.removeLastSegments(1);
		ElementContainer folder = CommonIndexUtils.getFolderForFile(fIndex,
				parentPath.toPortableString(), true);
		folder.getEntries().add(entry);
		return filePath;
	}

	/**
	 * 
	 * @param fIndex
	 * @param entry
	 * @param uri
	 */
	public static DesignerElement insertElement(DesignerProject fIndex,
			DesignerElement entry, String uri) {
		String filePath = CommonIndexUtils.getRelativeFilePath(new Path(fIndex
				.getRootPath()), new Path(uri));
		Path parentPath = new Path(filePath);
		parentPath = parentPath.removeLastSegments(1);
		ElementContainer folder = CommonIndexUtils.getFolderForFile(fIndex,
				parentPath.toPortableString(), true);
		folder.getEntries().add(entry);
		return entry;
	}

	/**
	 * 
	 * @param uri
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static StudioProjectConfiguration loadProjectConfig(String uri,
			InputStream is) throws IOException {
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = resourceSet.createResource(URI.createURI(uri));
		Map options = new HashMap();
		options.put(XMIResource.OPTION_PROCESS_DANGLING_HREF,
				XMIResource.OPTION_PROCESS_DANGLING_HREF_DISCARD);
		resource.load(is, options);
		StudioProjectConfiguration config = (StudioProjectConfiguration) resource
				.getContents().get(0);
		return config;
	}

}
