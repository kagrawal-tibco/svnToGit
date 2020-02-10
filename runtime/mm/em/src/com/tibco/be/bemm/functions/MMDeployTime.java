package com.tibco.be.bemm.functions;

import com.tibco.be.util.XiSupport;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.datamodel.helpers.XiSerializer;
import org.xml.sax.InputSource;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: hlouro
 * Date: 4/26/12
 * Time: 1:58 PM
 * To change this template use File | Settings | File Templates.
 */

public class MMDeployTime {
    private static final String DEPLOY_DIR_REL_PATH =
            File.separator+ "mm" + File.separator + "deployment";

    private static final String DEPLOY_FILE_REL_PATH =
            DEPLOY_DIR_REL_PATH + File.separator + "deploy_history.xml";

    private static final Logger logger =
            LogManagerFactory.getLogManager().getLogger(MMDeployTime.class);

    private static final MMDeployTime INSTANCE = new MMDeployTime();

    private final Site SITE = new Site();

    private static File depTimeFile;

    static {
        readDepTimesAndSetMap();
    }

    private static void readDepTimesAndSetMap() {
        depTimeFile = new File(
                System.getProperty(SystemProperty.BE_HOME.getPropertyName()) +      //BE_HOME
                        DEPLOY_FILE_REL_PATH );                                //DEPLOY_FILE_REL_PATH

        FileReader fr = null;
        try {
            fr =  new FileReader(depTimeFile);
        } catch (FileNotFoundException e) {
            logger.log(Level.DEBUG, String.format(
                    "Deployments historic file not found at %s",
                    depTimeFile.getAbsolutePath()));
            return;
        }

        parseFileAndPutMap(fr);
    }

    private static void parseFileAndPutMap(FileReader fr) {
        final InputSource is = new InputSource(fr);

        XiNode depHistXML = null;
        try {
            depHistXML = XiSupport.getParser().parse(is);
        } catch (Exception e) {
            logger.log(Level.ERROR, e, String.format(
                    "Exception occurred while parsing file with deployments history: %s ",
                    depTimeFile.getAbsolutePath()) );
            return;
        }

        XiNode siteNode = XiChild.getChild(depHistXML, XML.Element.DEPLOY_HISTORY);

        INSTANCE.SITE.addCluster(siteNode);
    }

    public static synchronized String saveDeployTime(String clustName,
                                                     String hostName,
                                                     String duName) {

        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String depTime = dateFormat.format(new Date());

        INSTANCE.SITE.addCluster(clustName).addHost(hostName).addDu(duName, depTime);

        saveToFile();

        return depTime;
    }

    private static void saveToFile() {
        //createXML
        XiNode dtNode =  XiSupport.getXiFactory().createElement(XML.Element.DEPLOY_HISTORY);

        for (String clustName : INSTANCE.SITE.clustNameToCluster.keySet() ) {
            XiNode clustNode =  XiSupport.getXiFactory().createElement(XML.Element.CLUSTER);

            clustNode.setAttributeStringValue(XML.Attribute.NAME, clustName);

            for (String hostName : INSTANCE.SITE.getCluster(clustName).hostNameToHost.keySet()) {
                XiNode hostNode =  XiSupport.getXiFactory().createElement(XML.Element.HOST);

                hostNode.setAttributeStringValue(XML.Attribute.NAME, hostName);

                for (DuTime duTime : INSTANCE.SITE.getCluster(clustName).getHost(hostName).duNameToDuTime.values()) {
                    XiNode duNode =  XiSupport.getXiFactory().createElement(XML.Element.DU);

                    duNode.setAttributeStringValue(XML.Attribute.NAME, duTime.getDuName());
                    duNode.setAttributeStringValue(XML.Attribute.DEP_TIME, duTime.getDepTime());

                    hostNode.appendChild(duNode);
                }

                clustNode.appendChild(hostNode);
            }

            dtNode.appendChild(clustNode);
        }


        //write to file
        try {
            //if dir does not exist, creates it
            if (!depTimeFile.getParentFile().exists())
                if(!depTimeFile.getParentFile().mkdirs())
                    throw new FileNotFoundException();

            FileWriter fr = new FileWriter(depTimeFile);
            fr.write(XiSerializer.serialize(dtNode));
            fr.close();
        } catch (FileNotFoundException fnfe) {
            final String msg = String.format(
                    "FAILED to create directory %s. Deployments historic file not saved!",
                    depTimeFile.getParentFile().getAbsolutePath());

            logger.log(Level.WARN, msg);
            logger.log(Level.DEBUG, fnfe, "" );
        } catch (IOException e) {
            logger.log(Level.ERROR, e, String.format(
                    "Exception occurred while saving deployments historic file to %s ",
                    depTimeFile.getAbsolutePath()) );
        }
    }

    /** Function exposed as catalog function via TopologyHelper*/
    public static synchronized String getDeployTime(String clustName, String hostName, String duNameAndPuStId) {
        //[0] = duName, [1] is puStId
        final String duName = duNameAndPuStId.split("~")[0];
        String puStId="";

        if (duNameAndPuStId.split("~").length > 1)
            puStId= duNameAndPuStId.split("~")[1];

        try {
            return INSTANCE.SITE.getCluster(clustName).getHost(hostName).getDu(duName).getDepTime();
        } catch (Exception e) {
            logger.log(Level.DEBUG, "DU deployment time not retrieved for entity: " +
                       "cluster = %s, host = %s, du = %s, pu_st_id = ",
                        clustName, hostName, duName, puStId);
            return "";
        }
    }


    private static boolean isArgValid(String argName, String argVal) {
        if (argVal != null && !argVal.trim().isEmpty())
            return true;

        logger.log(Level.WARN, "Illegal argument found: %s = %s", argName, argVal);

        return false;
    }

    private enum PARAM {
        CLUSTER("Cluster"),
        HOST("Host"),
        DU("Deployment Unit");

        private String name;

        PARAM(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }
    }

    private static String getAttrVal(XiNode node, ExpandedName attrName) {         //TODO: check
        final XiNode childAttr = node.getAttribute(attrName);

        return childAttr == null ?
                null :
                childAttr.getStringValue().trim();
    }

    private class Site {
        private HashMap<String, Cluster> clustNameToCluster;

        Site() {
            clustNameToCluster = new HashMap<String, Cluster>();
        }

        Cluster getCluster(String clustName) {
            if ( isArgValid(PARAM.CLUSTER.getName(), clustName) )
                return clustNameToCluster.get(clustName);

            return null;
        }

        Cluster addCluster(String clustName) {
            return addCluster(clustName, null);
        }

        private Cluster addCluster(String clustName, XiNode clustXiNode) {
            if (getCluster(clustName) == null) {
                if (clustXiNode == null)
                    clustNameToCluster.put(clustName, new Cluster());
                else
                    clustNameToCluster.put(clustName, new Cluster(clustXiNode));

            }

            return getCluster(clustName);
        }

        void addCluster(XiNode siteNode) {
            for (Iterator iter = XiChild.getIterator(siteNode, XML.Element.CLUSTER); iter.hasNext(); ) {

                final XiNode clustNode = (XiNode) iter.next();

                final String clustName = getAttrVal(clustNode, XML.Attribute.NAME);

                addCluster(clustName, clustNode);
            }
        }
    }

    private class Cluster {
        //One cluster has multiple hosts
        HashMap<String, Host> hostNameToHost;

        Cluster() {
            hostNameToHost = new HashMap<String, Host>();
        }

        Cluster(XiNode clustXiNode) {
            hostNameToHost = new HashMap<String, Host>();
            addHost(clustXiNode);
        }

        Host getHost(String hostName) {
            if ( isArgValid(PARAM.HOST.getName(), hostName) )
                return hostNameToHost.get(hostName);

            return null;
        }

        Host addHost(String hostName) {
            return addHost(hostName, null);
        }

        private Host addHost(String hostName, XiNode hostXiNode) {
            if (getHost(hostName) == null) {
                if (hostXiNode == null)
                    hostNameToHost.put(hostName, new Host());
                else
                    hostNameToHost.put(hostName, new Host(hostXiNode));
            }

            return getHost(hostName);
        }

        private void addHost(XiNode clustXiNode) {
            for (Iterator iter = XiChild.getIterator(clustXiNode, XML.Element.HOST); iter.hasNext(); ) {

                final XiNode hostNode = (XiNode) iter.next();

                final String hostName = getAttrVal(hostNode, XML.Attribute.NAME);

                addHost(hostName, hostNode);
            }
        }
    }

    private class Host {
        HashMap<String, DuTime> duNameToDuTime;

        Host() {
            duNameToDuTime = new HashMap<String, DuTime>();
        }

        public Host(XiNode duXiNode) {
            duNameToDuTime = new HashMap<String, DuTime>();
            addDu(duXiNode);
        }

        DuTime getDu(String duName) {
            if ( isArgValid(PARAM.DU.getName(), duName) )
                return duNameToDuTime.get(duName);

            return null;
        }

        DuTime addDu(String duName, String depTime) {
            if ( isArgValid("DU Name", duName) && isArgValid("Dep Time", depTime) )
                duNameToDuTime.put(duName, new DuTime(duName, depTime));

            return getDu(duName);
        }

        private void addDu(XiNode hostXiNode) {
            for (Iterator iter = XiChild.getIterator(hostXiNode, XML.Element.DU); iter.hasNext(); ) {

                final XiNode duXiNode = (XiNode) iter.next();

                final String duName = getAttrVal(duXiNode, XML.Attribute.NAME);
                final String depTime = getAttrVal(duXiNode, XML.Attribute.DEP_TIME);

                duNameToDuTime.put(duName, new DuTime(duName, depTime));
            }
        }
    }


    private class DuTime {
        private String depTime;
        private String duName;


        DuTime(String duName, String depTime) {
            this.duName = duName;
            this.depTime = depTime;
        }

        public String getDuName() {
            return duName;
        }

        public String getDepTime() {
            return depTime;
        }

        public void setDepTime(String depTime) {
            this.depTime = depTime;
        }
    }

    //XML elements
    interface XML {
        interface Element {
            ExpandedName DEPLOY_HISTORY = ExpandedName.makeName("deploy_history");
            ExpandedName CLUSTER = ExpandedName.makeName("cluster");
            ExpandedName HOST = ExpandedName.makeName("host");
            ExpandedName DU = ExpandedName.makeName("du");
        }

        interface Attribute {
            ExpandedName DEP_TIME = ExpandedName.makeName("dep_time");
            ExpandedName NAME = ExpandedName.makeName("name");
        }
    }


    //------- Test -----------
    public static void main(String[] args) {
        depTimeFile = new File("./config/deploy_times_new.xml");

        saveDeployTime("CLUSTER_1", "host1.na.tibco.com", "DU_NAME_1");
        saveDeployTime("CLUSTER_2", "HOST1.na.TIBCO.com", "DU_NAME_2");
        saveDeployTime("CLUSTER_1", "host2.na.tibco.com", "DU_NAME_3");
        saveDeployTime("CLUSTER_3", "host3.facebook.com", "DU_NAME_4");                       // TODO build
        saveDeployTime("CLUSTER_1", "host3.facebook.com", "DU_NAME_3");
        saveDeployTime("CLUSTER_2", "host3.facebook.com", "DU_NAME_1");
        saveDeployTime("CLUSTER_1", "host4.Google.com", "DU_NAME_3");
        saveDeployTime("CLUSTER_3", "host5.Cnn.com", "DU_NAME_3");
        saveDeployTime("", "", "DU_NAME_3");
        saveDeployTime(null, null, "DU_NAME_3");
        saveDeployTime("CLUSTER_3", "host5.Cnn.com", "");
        saveDeployTime("CLUSTER_1", "host5.Cnn.com", null);

        logger.log(Level.INFO, "time = " + getDeployTime("CLUSTER_1", "host1.na.tibco.com", "DU_NAME_1"));
        logger.log(Level.INFO, "time = " + getDeployTime("CLUSTER_1", "host1.na.tibco.com", "DU_NAME_XX"));
        logger.log(Level.INFO, "time = " + getDeployTime("CLUSTER_2", "HOST2.na.tibco.com", "DU_NAME_3"));
        logger.log(Level.INFO, "time = " + getDeployTime("CLUSTER_1", "host3.facebook.com", "DU_NAME_4"));
        logger.log(Level.INFO, "time = " + getDeployTime("CLUSTER_1", "host4.Google.com", "DU_NAME_3"));
        logger.log(Level.INFO, "time = " + getDeployTime("CLUSTER_1", "host5.cnn.com", "DU_NAME_3"));
    }

}
