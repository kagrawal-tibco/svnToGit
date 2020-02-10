/**
 * 
 */
package com.tibco.be.util.config.topology;

import com.tibco.be.util.XiSupport;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.schema.SmNamespaceProvider;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author hlouro
 *
 */
public class MMStMasterCddPathFinder {

	private static XiNode parse(String topology_file) {
		File topoXMLFileObj = new File(topology_file);
		InputStream is = null;
		try {
			is = new FileInputStream(topoXMLFileObj);
	        final SmNamespaceProvider smNsProvider = new TopoSchemaProvider();

	        final XiNode topoDoc = XiSupport.getParser().parse(new InputSource(is), smNsProvider);
	        final XiNode siteNode = XiChild.getChild(topoDoc, TopologyNS.Elements.SITE);

	        return siteNode;
	    }
	    catch (FileNotFoundException e) {
	    	throw new RuntimeException("could not find "+topoXMLFileObj,e);
	    }
	    catch (SAXException e) {
	    	throw new RuntimeException("could not parse " + topoXMLFileObj + ". Caused by:" + e.getMessage(), e);
	    }
	    catch (IOException e) {
	    	throw new RuntimeException("could not load "+topoXMLFileObj,e);
	    }
	    finally{
	    	if(is != null)
				try {
					is.close();
				} catch (IOException e) {
					throw new RuntimeException("could not close "+topoXMLFileObj,e);
				}
	    }
	}

    private static LinkedList<String> getMasterCddPaths(String stPath, Logger logger) throws Exception {
        final XiNode siteNode = parse(stPath);

        LinkedList<String> clustersMasterCddPath = new LinkedList<String>();

        final XiNode clustersNode = XiChild.getChild(siteNode, TopologyNS.Elements.CLUSTERS);
        //this iterator exists to handle the cases when we have multiple cluster nodes defined in the same .st file
        for (Iterator i = XiChild.getIterator(clustersNode, TopologyNS.Elements.CLUSTER); i.hasNext();) {
            final XiNode cluster = (XiNode) i.next();
            XiNode masterData = XiChild.getChild(cluster, TopologyNS.Elements.MASTER_FILES);
            String cddMaster = XiChild.getChild(masterData, TopologyNS.Elements.CDD_MASTER).getStringValue();

            if (cddMaster != null && !cddMaster.trim().isEmpty())
                clustersMasterCddPath.add(cddMaster);
            else
                logger.log(Level.WARN, "Found empty master CDD location for site topology '%s'. Master location IGNORED.", stPath);
        }
        return clustersMasterCddPath;
    }

    //TODO G11N support
    /** Returns all master CDD file paths declared for every ST file located under
     *  BE_HOME/mm/config. Although it is not recommended practice, one ST
     *  file can have more than one cluster configuration, hence more than one master CDD */
    public static List<String> getMasterCddsPathInAllStFiles(Logger logger) {
        final LinkedList<String> masterCddsPathInAllStFiles = new LinkedList<String>();
        final List<File> stFiles = MMStFilesFinder.getStFiles();

        for (File stFile : stFiles) {
            try {
                logger.log(Level.DEBUG, "Obtaining master CDD files path for ST file: %s", stFile.getAbsolutePath());
                masterCddsPathInAllStFiles.addAll(
                        MMStMasterCddPathFinder.getMasterCddPaths(stFile.getAbsolutePath(), logger));
            } catch(Exception e){
                logger.log(Level.ERROR, e.getMessage());
                throw new RuntimeException("Exception occurred while attempting to find master CDD " +
                        "file path for site topology file: " + stFile.getAbsolutePath());
            }
        }

    	return masterCddsPathInAllStFiles;
	}
}