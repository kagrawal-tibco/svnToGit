package com.tibco.be.ws.scs.impl.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.tibco.be.ws.scs.IArtifactFilter;
import com.tibco.be.ws.scs.SCSException;
import com.tibco.be.ws.scs.impl.AbstractSCSIntegration;
import com.tibco.be.ws.scs.impl.filter.IFilterContext;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 20/3/12
 * Time: 1:27 PM
 * Works of file system used as SCS.
 */
public class FileSystemIntegration extends AbstractSCSIntegration {

    /**
     * @param repoRootURL  - The baselocation path
     * @param projectName  - Name of the managed project
     * @param updatePath - Name of folder | file to be updated. Can be null.
     * @param userName
     * @param password
     * @param filterContext
     * @param artifactFilters
     * @param <F>
     * @param <I>
     * 
     * @return
     * 
     * @throws com.tibco.be.ws.scs.SCSException
     *
     */
    @SuppressWarnings("unchecked")
    @Override
    public <F extends IFilterContext, I extends IArtifactFilter> String listManagedProjectArtifacts(String repoRootURL,
                                                                                                    String projectName,
                                                                                                    String updatePath,
                                                                                                    String userName,
                                                                                                    String password,
                                                                                                    F filterContext,
                                                                                                    I... artifactFilters) throws SCSException {
        return getArtifactListFromFileSCS(repoRootURL, projectName, updatePath, filterContext, artifactFilters);
    }

    /**
     * List all the projects managed by this webstudio server instance.
     * @param repoRootURL The url of the repository in which projects exist as immediate children
     * @param userName
     * @param password
     * 
     * @return  An xml representation of all the managed projects.
     * 
     * @throws com.tibco.be.ws.scs.SCSException
     *
     */
    @Override
    public String listManagedProjects(String repoRootURL, String userName, String password) throws SCSException {
    	return getManagedProjectsFromFileSCS(repoRootURL);
    }

    /**
     * Return contents of the file read from file based SCS.
     * @param repoRootURL - The baselocation path .
     * @param projectName - Name  of the managed project.
     * @param artifactPath  - The FQN of the artifact.
     * @param artifactExtension  - File extension of artifact.
     * @param userName
     * @param password
     * 
     * @return  contents of the file
     * 
     * @throws com.tibco.be.ws.scs.SCSException
     *
     */
    @Override
    public String showFileContents(String repoRootURL,
                                   String projectName,
                                   String artifactPath,
                                   String artifactExtension,
                                   String userName,
                                   String password) throws SCSException {
        return getFileContents(repoRootURL, projectName, artifactPath, artifactExtension);
    }

    /**
     * Check if a file exists in SCS.
     * 
     * @param repoRootURL
     * @param projectName  - Name of the project in SCS.
     * @param artifactPath
     * @param artifactExtension
     * 
     * @return
     * 
     * @throws com.tibco.be.ws.scs.SCSException
     *
     */
    @Override
    public boolean fileExists(String repoRootURL, 
                              String projectName,
                              String artifactPath,
                              String artifactExtension) throws SCSException {
       return checkIfFileExists(repoRootURL, projectName, artifactPath, artifactExtension);
    }

    /**
     * @param repoRootURL
     * @param projectName
     * @param artifactPath
     * @param artifactExtension
     * @param artifactContents
     * @param commitComments
     * @param revisionId
     * 
     * @throws com.tibco.be.ws.scs.SCSException
     *
     */
    @Override
    public void commitFileContents(String repoRootURL,
                                   String projectName,
                                   String artifactPath,
                                   String artifactExtension,
                                   byte[] artifactContents,
                                   String commitComments,
                                   Long revisionId) throws SCSException {
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

        if (!artifactFile.isAbsolute()) {
            throw new SCSException(String.format("File path specified by %s needs to be absolute", absoluteFilePath));
        }
        FileOutputStream fos = null;
        try {
        	artifactFile.getParentFile().mkdirs();
        	fos = new FileOutputStream(artifactFile);
            fos.write(artifactContents);
        } catch (IOException e) {
            throw new SCSException(e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                	throw new SCSException(String.format("Error closing output stream while writing artifact (%s) to SCS repository", artifactFile.getAbsolutePath()));
                }
            }
        }
    }

    /**
     * @param repoRootURL
     * @param projectName  - Name of the project in SCS.
     * @param artifactPath
     * @param artifactExtension
     * @param deleteComments
     * @param revisionId
     * 
     * @throws com.tibco.be.ws.scs.SCSException
     */
    @Override
    public void deleteFile(String repoRootURL,
                              String projectName,
                              String artifactPath,
                              String artifactExtension,
                              String deleteComments,
                              Long revisionId) throws SCSException {
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

        if (!artifactFile.isAbsolute()) {
            throw new SCSException(String.format("File path specified by %s needs to be absolute", absoluteFilePath));
        }
        if (artifactFile.isDirectory()) {
            throw new SCSException(String.format("File path specified by %s cannot be deleted as it is a directory", absoluteFilePath));
        }
        //The parent folder should have delete permissions
        File parentDir = artifactFile.getParentFile();
        if (!parentDir.canWrite()) {
            throw new SCSException(String.format("File %s cannot be deleted as it its parent directory %s does not have write permissions", absoluteFilePath, parentDir.getAbsolutePath()));
        }
        
        artifactFile.delete();
    }
    
    @Override
	public boolean checkoutProjectArtifacts(String repoRootURL,
			String projectName, String destinationDirectory, String userName, String password)
			throws SCSException {
    	throw new UnsupportedOperationException("This operation is not supported by this implementation. For Filestore checkout is not necessary.");
	}
    
    @Override
    public String flushContentsToSCS(String repoRootURL, Long revisionId,
    		String scsUserName, String scsPassword) throws SCSException {
    	throw new UnsupportedOperationException("This operation is not supported by this implementation. For Filestore flushing content is not necessary.");
    }
    
    @Override
    public void revertChangesFromSCS(String repoRootURL, Long revisionId, String scsUserName, String scsPassword)
    		throws SCSException {
    	throw new UnsupportedOperationException("This operation is not supported by this implementation. For Filestore reverting changes is not necessary.");
    }
    
    @Override
    public void updateProjectArtifacts(String repoRootURL, String projectName, String scsUserName, String scsPassword)
    		throws SCSException {
    	throw new UnsupportedOperationException("This operation is not supported by this implementation. For Filestore updating changes is not necessary.");
    	
    }	
}
