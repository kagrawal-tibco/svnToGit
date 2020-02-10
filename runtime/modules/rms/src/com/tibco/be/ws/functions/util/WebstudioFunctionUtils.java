package com.tibco.be.ws.functions.util;

import static com.tibco.cep.decisionproject.util.DTDomainUtil.getValArray;
import static com.tibco.cep.decisionproject.util.DTDomainUtil.getValDescDropDownArray;
import static com.tibco.cep.studio.core.index.utils.CommonIndexUtils.DOMAIN_EXTENSION;
import static com.tibco.cep.studio.core.index.utils.CommonIndexUtils.deserializeEObjectFromString;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import com.tibco.be.util.CommandStreamReader;
import com.tibco.be.ws.functions.WebstudioServerCommonFunctions;
import com.tibco.be.ws.functions.WebstudioServerSCSFunctions;
import com.tibco.be.ws.rt.model.AbstractSymbolChildNode;
import com.tibco.be.ws.scs.ISCSIntegration;
import com.tibco.be.ws.scs.SCSException;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.studio.common.StudioProjectCache;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexBuilder;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 20/3/12
 * Time: 12:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class WebstudioFunctionUtils {
	
	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(WebstudioFunctionUtils.class);

    /**
     * Get all domain entries for a domain including all super domains.
     *
     * @param scsIntegrationClass
     * @param repoRootURL
     * @param projectName
     * @param baseDomain          - The actual {@link Domain} matching the one used in the RT/RTI view.
     * @return
     * @throws Exception
     */
    public static String[] getDomainEntries(String scsIntegrationClass,
                                            String repoRootURL,
                                            String projectName,
                                            Domain baseDomain) throws Exception {
        //TODO Take into account the case where it is already loaded into user WS.
        List<String> valList = new ArrayList<String>();
        Domain superDomain = baseDomain;

        while (superDomain != null) {
            List<String> vals = getValArray(superDomain,
                    superDomain.getEntries(),
                    "",
                    false,
                    false);
            valList.addAll(vals);
            String superDomainPath = superDomain.getSuperDomainPath();
            /**
             * This is done to get updated value from index,
             * and not the cached in-memory one
             */
            if (superDomainPath != null && !superDomainPath.isEmpty()) {
                String superDomainModelContents =
                        WebstudioServerSCSFunctions.showArtifactContents(scsIntegrationClass,
                                repoRootURL,
                                projectName,
                                superDomainPath,
                                DOMAIN_EXTENSION,
                                null, null);
                superDomain = (Domain) deserializeEObjectFromString(superDomainModelContents);
            } else {
                superDomain = null;
            }
        }
        String[] stringValues = new String[valList.size()];
        return valList.toArray(stringValues);
    }

    public static Object[] getDomainEntriesAndDescriptions(String scsIntegrationClass,
												    	   String repoRootURL,
												    	   String projectName,
												    	   Domain baseDomain) throws Exception {
    	Domain superDomain = baseDomain;
    	Object[] mainEntries = new Object[3];
		List<String> valList = new ArrayList<String>();
		List<String> descList = new ArrayList<String>();
		List<Boolean> isRangeTypeList = new ArrayList<Boolean>();
    	while (superDomain != null) {
    		getValDescDropDownArray(superDomain,
						    			  superDomain.getEntries(),
						    			  "",
						    			  valList,
										  descList,
										  isRangeTypeList,
						    			  false);
    		String superDomainPath = superDomain.getSuperDomainPath();
    		/**
    		 * This is done to get updated value from index,
    		 * and not the cached in-memory one
    		 */
    		if (superDomainPath != null && !superDomainPath.isEmpty()) {
    			String superDomainModelContents =
    					WebstudioServerSCSFunctions.showArtifactContents(scsIntegrationClass,
    							repoRootURL,
    							projectName,
    							superDomainPath,
    							DOMAIN_EXTENSION,
    							null, null);
    			superDomain = (Domain) deserializeEObjectFromString(superDomainModelContents);
    		} else {
    			superDomain = null;
    		}
    	}
    	String[] values = new String[valList.size()];
    	valList.toArray(values);
		
		String[] descriptions = new String[descList.size()];
		descList.toArray(descriptions);

		Boolean[] isRangeType = new Boolean[isRangeTypeList.size()];
		isRangeTypeList.toArray(isRangeType);

		mainEntries[0] = values;
		mainEntries[1] = descriptions;
		mainEntries[2] = isRangeType;
		
    	return mainEntries;
    }
    
    /**
     * Return symbol's extension (concept|scorecard|event).
     * @param scsIntegration
     * @param repoRootURL
     * @param projectName
     * @param symbol
     * @return
     * @throws com.tibco.be.ws.scs.SCSException
     */
    public static <S extends Symbol> String resolveSymbolTypeExtension(ISCSIntegration scsIntegration,
    		String repoRootURL,
    		String projectName,
    		S symbol,
    		String earPath) throws SCSException {
    	String symbolType = symbol.getType();
    	return resolveSymbolTypeExtension(scsIntegration, repoRootURL, projectName, symbolType, earPath);
    }

    /**
     * Return symbol's extension (concept|scorecard|event).
     * @param scsIntegration
     * @param repoRootURL
     * @param projectName
     * @param symbol
     * @return
     * @throws com.tibco.be.ws.scs.SCSException
     */
    public static <S extends AbstractSymbolChildNode> String resolveSymbolTypeExtension(ISCSIntegration scsIntegration,
    		String repoRootURL,
    		String projectName,
    		S symbol,
    		String earPath) throws SCSException {
    	String symbolType = symbol.getType();
    	return resolveSymbolTypeExtension(scsIntegration, repoRootURL, projectName, symbolType, earPath);
    }

    /**
     *
     * @param scsIntegration
     * @param repoRootURL
     * @param projectName
     * @param symbolType
     * @return
     * @throws SCSException
     */
    private static String resolveSymbolTypeExtension(ISCSIntegration scsIntegration,
    		String repoRootURL,
    		String projectName,
    		String symbolType,
    		String earPath) throws SCSException {
    	String symbolExtension = null;
    	LOGGER.log(Level.DEBUG, "Checking Symbol Type [%s]", symbolType);
    	//Check if primitive
    	PROPERTY_TYPES property_types = PROPERTY_TYPES.get(symbolType);
    	if (property_types != null) {
    		symbolExtension = null;
    	} else {
    		LOGGER.log(Level.DEBUG, "Not primitive so checking for complex Type");
    		for (RULETEMPLATE_PARTICIPANT_EXTENSIONS ruleParticipantExtn : RULETEMPLATE_PARTICIPANT_EXTENSIONS.values()) {
    			if (scsIntegration.fileExists(repoRootURL, projectName, symbolType, ruleParticipantExtn.getLiteral())) {
    				//Found
    				symbolExtension = ruleParticipantExtn.getLiteral();
    			} else {
    				// check if it exits in a project library
    				String entityContent = WebstudioFunctionUtils.getEntityContentFromProjectLibrary(repoRootURL, projectName, symbolType, ruleParticipantExtn.getLiteral(), earPath);
    				if (entityContent != null && !entityContent.isEmpty()) symbolExtension = ruleParticipantExtn.getLiteral();
    			}
    			if (symbolExtension != null) break;
    		}
    	}
    	LOGGER.log(Level.DEBUG, "Final symbolExtension - [%s]", symbolExtension);
    	return symbolExtension;
    }
    
    /**
     * Executes the studio tools command
     * 
     * @param studioToolsExecutable
     * @param projectPath
     * @param extendedClassPath
     * @return
     */
    public static String executeCommand(List<String> studioToolsExecutable, String projectPath, String extendedClassPath) throws Exception {
    	ProcessBuilder stProcessBuilder = new ProcessBuilder(studioToolsExecutable);
    	Map<String, String> env = stProcessBuilder.environment();
    	/**
    	 * Ported BE-20321 - In case of RMS running as a Windows service the below tra properties are getting passed on to
    	 * the subprocess (studio-tools) and are causing problem with DT validation.
    	 * Thus these should be removed from the subprocess env. 
    	 */
    	env.remove("APP_ARGS");
    	env.remove("CUSTOM_EXT_APPEND_CP");
    	
    	if (extendedClassPath != null) {
    		setExtendedClassPath(extendedClassPath, projectPath, stProcessBuilder);
    	}
    	Process studioToolsProcess = stProcessBuilder.start();
    	CommandStreamReader errorStream = new CommandStreamReader(studioToolsProcess.getErrorStream());
    	errorStream.start();
    	CommandStreamReader outputStream = new CommandStreamReader(studioToolsProcess.getInputStream());
    	outputStream.start();
    	
    	int exitValue = studioToolsProcess.waitFor();

    	String cmdOutput = (errorStream.getCommandOutput() != null) ? errorStream.getCommandOutput() : "";
    	cmdOutput += (outputStream.getCommandOutput() != null) ? outputStream.getCommandOutput() : "";
    	    	
    	return cmdOutput;
    }

    /**
     * Sets the extended class path
     * 
     * @param projectPath
     * @param stProcessBuilder
     */
    private static void setExtendedClassPath(String extendedClassPath, String projectPath, ProcessBuilder stProcessBuilder){
    	if (extendedClassPath != null && extendedClassPath.length() > 0) {
    		String key = projectPath.replaceAll("\\\\", "/");
    		key += "_extended.build.class.path";
    		
    		extendedClassPath = extendedClassPath.replaceAll("\\\\", "/");
    		
    		stProcessBuilder.environment().put(key, extendedClassPath);
    	}
    }
    
    /**
     * Load/create project Index
     * 
     * @param rootURL SCS root URL
     * @param projectName
     * @throws MalformedURLException
     * @throws IOException
     */
    public static void loadProjectIndex(String rootURL, String projectName, String archivePath) throws MalformedURLException, IOException {
    	File archiveFile = null;
    	if (archivePath != null) {
    		archiveFile = new File(archivePath);
    	}	
		if (archiveFile != null && archiveFile.exists() && archiveFile.isFile()) {
			loadProjectIndex(projectName, archiveFile);
		} else {
			IndexBuilder builder = new IndexBuilder(new File(rootURL + File.separator + projectName));
			DesignerProject index = builder.loadProject();
			StudioProjectCache.getInstance().putIndex(projectName, index);
		}   	
    } 	
	
    /**
     * Parse through all project artifacts and load/create project index
     * 
     * @param projectName
     * @param archivePath Path of the EAR
     * @throws MalformedURLException
     * @throws IOException
     */
	private static void loadProjectIndex(String projectName, File archiveFile) throws MalformedURLException, IOException {
    	DesignerProject index = StudioProjectCache.getInstance().getIndex(projectName);
    	if (index == null) {
	    	java.net.URI earURI = archiveFile.toURI();
	    	ResourceSet resourceSet = new ResourceSetImpl();
	        final Resource resource = resourceSet.createResource(
	                URI.createURI("archive:jar:" + earURI.toString() + "!/Shared%20Archive.sar!/" + ".idx"));
	
	        final File earFile = new File (earURI);
	        final int length = (int) earFile.length();
	        final byte[] fileAsBytes = new byte[length];
	        
	        final InputStream is = earURI.toURL().openStream();
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
	                                if (".idx".equals(sarEntry.getName())) {
	                                    resource.load(sarZis, null);
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
		
	        DesignerProject designerProject = null;
	        if (resource.getContents().size() > 0) {
	        	designerProject = (DesignerProject) resource.getContents().get(0);
	        	StudioProjectCache.getInstance().putIndex(projectName, designerProject);
	        }     	
    	}    
    }
	
	/**
	 * Get the list of project libraries associated to the given project
	 * 
	 * @param rootURL
	 * @param projectName
	 * @param archivePath
	 * @return
	 * @throws Exception
	 */
	public static String[] getProjectLibraries(String rootURL, String projectName, String archivePath) throws Exception {
		loadProjectIndex(rootURL, projectName, archivePath);
		DesignerProject designerProject = StudioProjectCache.getInstance().getIndex(projectName);
		
		List<String> projectLibraryPaths = new ArrayList<String>();
		
		List<DesignerProject> referenceProjects = designerProject.getReferencedProjects();
		if (referenceProjects != null && referenceProjects.size() > 0) {
			for (DesignerProject dProject : referenceProjects) {
				projectLibraryPaths.add(dProject.getRootPath());
			}
		}
		
		return projectLibraryPaths.toArray(new String[projectLibraryPaths.size()]);
	}
	
	/**
	 * Retrieve the artifact content from project lib
	 * 
	 * @param projectLibPath
	 * @param artifactPath
	 * @param artifactExtn
	 * @return
	 * @throws Exception
	 */
	public static String getContentFromProjectLibrary(String projectLibPath, String artifactPath, String artifactExtn) throws Exception {
		String artifactContent = null;
		if (projectLibPath != null && !projectLibPath.isEmpty()) {
			String artifactToCompare = artifactPath + "." + artifactExtn;
	        
			JarEntry matchingJarEntry = null;
			
			JarFile projectLibJar = new JarFile(projectLibPath);
			Enumeration<JarEntry> entries = projectLibJar.entries();
	        while (entries.hasMoreElements()) {
	            JarEntry entry = entries.nextElement();
	            String entryName = (entry.getName().startsWith("/")) ? entry.getName() : "/" + entry.getName();
	            if (entryName.equals(artifactToCompare)) {
	            	matchingJarEntry = entry;
	            	break;
	            }
	        }
	        
	        if (matchingJarEntry != null) {
	        	artifactContent = CommonIndexUtils.getJarEntryText(projectLibJar, matchingJarEntry);
	        }
		}
		
		return artifactContent;
	}
	
	public static String getEntityContentFromProjectLibrary(String repoRootURL, String projectName, String artifactPath, String artifactType, String earPath) {
		String entityContents = null;
		
		String[] projectLibraries = WebstudioServerCommonFunctions.getProjectLibraries(repoRootURL, projectName, earPath);
    	if (projectLibraries != null) {
    		if (artifactType != null) {
    			entityContents = getEntityOffProjectLibraries(artifactPath, artifactType, projectLibraries);
    		} else {
    			for (RULETEMPLATE_PARTICIPANT_EXTENSIONS ruleParticipantExtension : RULETEMPLATE_PARTICIPANT_EXTENSIONS.values()) {
    				entityContents = getEntityOffProjectLibraries(artifactPath, ruleParticipantExtension.getLiteral(), projectLibraries);
    				if (entityContents != null && !entityContents.isEmpty()) break;
    			}
    		}
    	}
    	
    	return entityContents;
	}
	
	private static String getEntityOffProjectLibraries(String artifactPath, String artifactType, String[] projectLibraries) {
		String entityContents = null;

		if (projectLibraries != null) {
			for (String projectLib : projectLibraries) {
				try {
    				entityContents = getContentFromProjectLibrary(projectLib, artifactPath, artifactType);
    			} catch (Exception e) {
    				throw new RuntimeException(e);
    			}
				if (entityContents != null && !entityContents.isEmpty()) break;
			}
		}

		return entityContents;
   }
}