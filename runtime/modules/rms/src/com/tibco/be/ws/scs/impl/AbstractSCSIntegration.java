package com.tibco.be.ws.scs.impl;

import static com.tibco.be.ws.scs.SCSConstants.ATTR_MANAGED_PROJECTS_ENTRY_TYPE;
import static com.tibco.be.ws.scs.SCSConstants.MANAGED_PROJECTS_ENTRY_NAME;
import static com.tibco.be.ws.scs.SCSConstants.MANAGED_PROJECTS_ENTRY_PATH_NAME;
import static com.tibco.be.ws.scs.SCSConstants.MANAGED_PROJECTS_NAME;
import static com.tibco.be.ws.scs.SCSConstants.MANAGED_PROJECT_ARTIFACTS_NAME;
import static com.tibco.be.ws.scs.SCSConstants.MANAGED_PROJECT_ARTIFACT_PATH_NAME;
import static com.tibco.be.ws.scs.SCSConstants.MANAGED_PROJECT_ARTIFACT_NAME;
import static com.tibco.be.ws.scs.SCSConstants.MANAGED_PROJECT_ARTIFACT_TYPE_NAME;
import static com.tibco.be.ws.scs.SCSConstants.MANAGED_PROJECT_ARTIFACT_LAST_UPDATED;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.be.functions.file.FileHelper;
import com.tibco.be.rms.functions.ArtifactsHelper;
import com.tibco.be.ws.scs.IArtifactFilter;
import com.tibco.be.ws.scs.ISCSIntegration;
import com.tibco.be.ws.scs.SCSCommitEntry;
import com.tibco.be.ws.scs.SCSException;
import com.tibco.be.ws.scs.impl.filter.CheckoutCacheFilter;
import com.tibco.be.ws.scs.impl.filter.DefaultFilterContext;
import com.tibco.be.ws.scs.impl.filter.IFilterContext;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiFactoryFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiSerializer;

/**
 * Abstract Integration class providing common implementation methods which can be used by the concrete implementation classes.
 * 
 * @author vpatil
 */
public abstract class AbstractSCSIntegration implements ISCSIntegration {
	
	private String scsCommandPath;
	
	// to maintain a list of commit entries per revisionId before its flushed to the SCS repository
	private Map<Long, List<SCSCommitEntry>> svnCommitEntriesByUser = new HashMap<Long, List<SCSCommitEntry>>();
	

	@Override
	public final void setSCSCommandPath(String command) {
		scsCommandPath = command;
	}

	/**
	 * Return's the SCS command path
	 * @return
	 */
	protected final String getSCSCommandPath() {
		return scsCommandPath;
	}

	/**
	 * Returns the Map of user to svn commit entries
	 * 
	 * @return
	 */
	protected final Map<Long, List<SCSCommitEntry>> getSvnCommitEntriesByUser() {
		return svnCommitEntriesByUser;
	}

	/**
	 * Serialize the XML node into string
	 * 
	 * @param node
	 * @return
	 */
    protected final String serializeXiNode(XiNode node) {
        StringWriter stringWriter = new StringWriter();
        XiSerializer.serialize(node, stringWriter);
        return stringWriter.toString();
    }
    
    /**
    * Check if the base directory exists and can be read
    * 
    * @param baseLocationDirectory
    * @throws SCSException
    */
   protected final void check(File baseLocationDirectory) throws SCSException {
       //Check if it is a directory and can be read
       if (!baseLocationDirectory.exists()) {
           throw new SCSException(String.format("Base Location URL %s not found", baseLocationDirectory));
       }
       if (!baseLocationDirectory.isDirectory()) {
           throw new SCSException(String.format("Base Location URL %s is not a valid directory", baseLocationDirectory));
       }
       if (!baseLocationDirectory.canRead()) {
           throw new SCSException(String.format("Base Location URL %s is not readable. Please check the permissions", baseLocationDirectory));
       }
   }
   
   /**
    * Get the contents of the file from the SCS repository
    * 
    * @param repoRootURL
    * @param projectName
    * @param artifactPath
    * @param artifactExtension
    * @return
    * @throws SCSException
    */
   protected final String getFileContents(String repoRootURL, 
						           String projectName,
						           String artifactPath,
						           String artifactExtension) throws SCSException {
	   File baseLocationDirectory = new File(repoRootURL);
       check(baseLocationDirectory);

       StringBuilder stringBuilder = new StringBuilder(repoRootURL);
       stringBuilder.append(File.separatorChar);
       stringBuilder.append(projectName);
       stringBuilder.append(artifactPath);
       stringBuilder.append(".");
       stringBuilder.append(artifactExtension);

       String absoluteFilePath = stringBuilder.toString();
       File artifactFile = new File(absoluteFilePath);
       if (!artifactFile.exists()) {
           throw new SCSException(String.format("File path specified by %s does not exist", absoluteFilePath));
       }
       //Check if it exists and is not directory
       if (artifactFile.isDirectory()) {
           throw new SCSException(String.format("File path specified by %s is a directory", absoluteFilePath));
       }
       if (!artifactFile.canRead()) {
           throw new SCSException(String.format("File path specified by %s cannot be read. Please verify file permissions", absoluteFilePath));
       }
       if (!artifactFile.isAbsolute()) {
           throw new SCSException(String.format("File path specified by %s needs to be absolute", absoluteFilePath));
       }
       return FileHelper.readFileAsString(absoluteFilePath, "UTF-8");
   }
   
   /**
    * Get a list of managed project from File SCS
    * 
    * @param repoRootURL
    * @return
    * @throws SCSException
    */
   protected final String getManagedProjectsFromFileSCS(String repoRootURL) throws SCSException {
	   File baseLocationDirectory = new File(repoRootURL);
       check(baseLocationDirectory);
       //List all projects in it
       File[] projectDirs = baseLocationDirectory.listFiles(new FileFilter() {
           @Override
           public boolean accept(File pathname) {
               return pathname.isDirectory() && !pathname.isHidden();
           }
       });
       Arrays.sort(projectDirs);
       
       //Create response xml
       XiFactory factory = XiFactoryFactory.newInstance();
       XiNode document = factory.createDocument();
       XiNode managedProjectsNode = factory.createElement(MANAGED_PROJECTS_NAME);
       document.appendChild(managedProjectsNode);
       for (File projectDir : projectDirs) {
           XiNode entryNode =
               factory.createElement(MANAGED_PROJECTS_ENTRY_NAME);
           entryNode.setAttributeStringValue(ATTR_MANAGED_PROJECTS_ENTRY_TYPE, "dir");
           XiNode pathNode =
               factory.createElement(MANAGED_PROJECTS_ENTRY_PATH_NAME);
           pathNode.setStringValue(projectDir.getName());
           entryNode.appendChild(pathNode);
           managedProjectsNode.appendChild(entryNode);
       }
       return serializeXiNode(document);
   }
   
   /**
    * Get the artifact list from File SCS
    * 
    * @param repoRootURL
    * @param projectName
    * @param updatePath
    * @param filterContext
    * @param artifactFilters
    * @return
    * @throws SCSException
    */
   protected final <F extends IFilterContext, I extends IArtifactFilter> String getArtifactListFromFileSCS(String repoRootURL,
													                                                String projectName,
													                                                String updatePath,
													                                                F filterContext,
													                                                I... artifactFilters) throws SCSException {
	   File baseLocationDirectory = new File(repoRootURL);
       check(baseLocationDirectory);

       XiFactory factory = XiFactoryFactory.newInstance();
       XiNode document = factory.createDocument();
       XiNode projectArtifactsNode =
               factory.createElement(MANAGED_PROJECT_ARTIFACTS_NAME);
       document.appendChild(projectArtifactsNode);

       File projectDirectory = new File(repoRootURL, projectName);
       File updateDirectory = null;
       String updateDirPath = null;
       if (artifactFilters[0] instanceof CheckoutCacheFilter) {
    	   //split updatePath and loop through each
    	   String[] paths = updatePath.split("\\$\\$");
    	   for (String path : paths) {
    		   if (path.isEmpty() || path == null) {
    			   continue;
    		   }
    		   try {
    			   File checkedoutArtifact = new File(projectDirectory.getCanonicalPath() , path);
    			   ((CheckoutCacheFilter) artifactFilters[0]).add(checkedoutArtifact.getCanonicalPath());
    		   } catch (IOException e) {
    			   e.printStackTrace();
    		   }
    	   }
    	   updateDirectory = projectDirectory;
    	   updateDirPath = updateDirectory.getAbsolutePath();
       }
       else {
    	   updateDirectory = (updatePath == null || updatePath.isEmpty()) ? projectDirectory : new File(repoRootURL, projectName + updatePath);
    	   updateDirPath = updateDirectory.getAbsolutePath();
       }
       String projectDirPath;
       try {
           projectDirPath = projectDirectory.getCanonicalPath();
       } catch (IOException e) {
           throw new SCSException(e);
       }

       if (filterContext instanceof DefaultFilterContext) {
           ((DefaultFilterContext)filterContext).setContainerPathFile(updateDirectory);
       }
       String[] allEntries = ArtifactsHelper.listFilesRecursive(updateDirPath, filterContext, artifactFilters);

       for (String fileEntry : allEntries) {
           XiNode artifactNode =
               factory.createElement(MANAGED_PROJECT_ARTIFACT_NAME);
           XiNode artifactTypeNode = factory.createElement(MANAGED_PROJECT_ARTIFACT_TYPE_NAME);
           
           String[] fileEntryPair = fileEntry.split("@");
           
           String fullyQualifiedPath = fileEntryPair[0];
           String lastUpdated = fileEntryPair[1];
           
           String fileExtension = fullyQualifiedPath.substring(fullyQualifiedPath.lastIndexOf('.') + 1, fullyQualifiedPath.length());
           artifactTypeNode.setStringValue(fileExtension);

           XiNode pathNode =
               factory.createElement(MANAGED_PROJECT_ARTIFACT_PATH_NAME);
           String pathWithoutExtn = fullyQualifiedPath.substring(0, fullyQualifiedPath.lastIndexOf('.'));
           //Remove upto project path and reverse path separators (if any)
           pathWithoutExtn = pathWithoutExtn.replace(projectDirPath, "").replace("\\", "/");
           pathNode.setStringValue(pathWithoutExtn);
           
           XiNode lastUpdatedNode =
                   factory.createElement(MANAGED_PROJECT_ARTIFACT_LAST_UPDATED);
           lastUpdatedNode.setStringValue(lastUpdated);
           
           artifactNode.appendChild(artifactTypeNode);
           artifactNode.appendChild(pathNode);
           artifactNode.appendChild(lastUpdatedNode);
           
           projectArtifactsNode.appendChild(artifactNode);
       }
       return serializeXiNode(document);
   }
   
   /**
    * Check if the file exists in the given SCS location
    * 
    * @param repoRootURL
    * @param projectName
    * @param artifactPath
    * @param artifactExtension
    * @param userName
    * @param password
    * 
    * @return
    * 
    * @throws SCSException
    */
   protected final boolean checkIfFileExists(String repoRootURL,
		   String projectName,
		   String artifactPath,
		   String artifactExtension) throws SCSException {
	   
	   File baseLocationDirectory = new File(repoRootURL);
       check(baseLocationDirectory);

       StringBuilder stringBuilder = new StringBuilder(repoRootURL);
       stringBuilder.append(File.separatorChar);
       stringBuilder.append(projectName);
       stringBuilder.append(artifactPath);
       stringBuilder.append(".");
       stringBuilder.append(artifactExtension);

       String absoluteFilePath = stringBuilder.toString();
       File artifactFile = new File(absoluteFilePath);
       return artifactFile.exists();
   }
}
