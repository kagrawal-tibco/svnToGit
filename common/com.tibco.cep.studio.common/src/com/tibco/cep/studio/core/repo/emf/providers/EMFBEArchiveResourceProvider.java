/**
 * 
 */
package com.tibco.cep.studio.core.repo.emf.providers;


import static com.tibco.cep.studio.core.repo.RepoConstants.BAR_EXTENSION;
import static com.tibco.cep.studio.core.repo.RepoConstants.ZIP_EXTENSION;

import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.zip.ZipInputStream;

import org.xml.sax.InputSource;

import com.tibco.be.util.BEJarVersionsInspector;
import com.tibco.be.util.packaging.Constants;
import com.tibco.cep.repo.Project;
import com.tibco.cep.repo.provider.impl.BEArchiveResourceProviderImpl;
import com.tibco.cep.studio.core.repo.emf.DeployedEMFProject;
import com.tibco.cep.studio.core.repo.emf.EMFProject;
import com.tibco.objectrepo.vfile.VFileDirectory;
import com.tibco.objectrepo.vfile.VFileStream;
import com.tibco.objectrepo.vfile.zipfile.ZipVFileFactory;
import com.tibco.xml.XiNodeUtilities;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

/**
 * Designtime Provider class for BE Archive
 * @author aathalye
 *
 */
public class EMFBEArchiveResourceProvider
        extends BEArchiveResourceProviderImpl {
	
	//private Map<String, byte[]> resourceDataMap = new HashMap<String, byte[]>();

	/* (non-Javadoc)
	 * @see com.tibco.cep.repo.ArchiveResourceProvider#getAllResourceURI()
	 */
	
	public Collection<String> getAllResourceURI() {
		// TODO Auto-generated method stub
		return null;
	}


	/**
	 * @param uri
	 * @param is
	 * @param project -> An instance of {@linkplain DeployedEMFProject}
	 */
	public void deserializeBARResource(String uri, 
									   InputStream is,
			                           Project project) throws Exception {
		//The bar file is an archive. Read it using zip packages
		final ZipVFileFactory vFileFactory = new ZipVFileFactory(new ZipInputStream(is));
		
        final VFileDirectory dir = vFileFactory.getRootDirectory();
        
        if (project instanceof EMFProject) {
        	//This probably will be the case
        	EMFProject emfProject = (EMFProject)project;
        	//The ontology parameter below is redundant. It should not have been there
        	//The ontology can always be obtained from the resource provider
        	//tied to the project.
        	emfProject.scanDirectory(dir, uri, null);
        }
        vFileFactory.destroy();
	}


    private void deserializeDesignTimeVersions(
            String uri,
            InputStream is,
            Project project)
            throws Exception {

        is.reset();
        final InputSource source = new InputSource(is);
        XiNode node = PARSER.parse(source);
        XiNodeUtilities.cleanTextNodes(node);

        node = node.getFirstChild();
        if (null == node) {
            return;
        }

        for (final Iterator it = XiChild.getIterator(node, Constants.DD.XNames.NAME_VALUE_PAIRS); it.hasNext(); ) {
            final XiNode nameValuePairsNode = (XiNode) it.next();
            node = XiChild.getChild(nameValuePairsNode, Constants.DD.XNames.NAME);
            if ((null != node) && "versions".equals(node.getStringValue())) {
                this.designTimeVersions.putAll(
                        new BEJarVersionsInspector().parseFromNameValuePairs(nameValuePairsNode));
                break;
            }
        }
    }


	/**
	 * This should be overridden by subclass for runtime 
	 */
	public void deserializePackedProject(String uri, 
			                             InputStream is,
			                             Project project) throws Exception {

		//todo: ???
    }




	/* (non-Javadoc)
	 * @see com.tibco.cep.repo.ArchiveResourceProvider#getResourceAsByteArray(java.lang.String)
	 */
	
	public byte[] getResourceAsByteArray(String resourceURI) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.repo.ArchiveResourceProvider#getResourceAsXiNode(java.lang.String)
	 */
	
	public XiNode getResourceAsXiNode(String arg0) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.repo.ArchiveResourceProvider#removeResource(java.lang.String)
	 */
	
	public void removeResource(String arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.repo.ResourceProvider#deserializeResource(java.lang.String, java.io.InputStream, com.tibco.cep.repo.Project, com.tibco.objectrepo.vfile.VFileStream)
	 */
	//TODO Override this for runtime
	public int deserializeResource(String fileURI, 
			                       InputStream is, 
			                       Project project,
			                       VFileStream vFileStream) throws Exception {
		//Courtesy existing impl
	
		String lower = fileURI.toLowerCase();
        if (lower.endsWith(BAR_EXTENSION)) {
        	deserializeBARResource(fileURI, is, project);
        }
        else if (lower.endsWith(ZIP_EXTENSION)) {
        	deserializePackedProject(fileURI, is, project);
        }
        else if (fileURI.endsWith(Constants.DD.FILENAME)) {
        	deserializeDesignTimeVersions(fileURI, is, project);
        }
        return 0;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.repo.ResourceProvider#init()
	 */
	
	public void init() throws Exception {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.repo.ResourceProvider#supportsResource(java.lang.String)
	 */
	
	public boolean supportsResource(String uri) {
		//Courtesy existing impl
		final String lower = uri.toLowerCase();

        return lower.endsWith(BAR_EXTENSION)
                || lower.endsWith(ZIP_EXTENSION)
                || uri.endsWith(Constants.DD.FILENAME);
	}

}
