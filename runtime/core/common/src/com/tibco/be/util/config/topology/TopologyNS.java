/**
 * 
 */
package com.tibco.be.util.config.topology;

import com.tibco.xml.data.primitive.ExpandedName;

/**
 * @author Nick
 *
 */
public interface TopologyNS {

	String URL = "http://www.tibco.com/businessevents/BEMM/2009/09/BEMMTopology.xsd";
    String SITE_NAME = "Site";
    String CACHESERVER_SUFFIX= "$CacheServer";

    interface Attributes {
        ExpandedName VERSION = ExpandedName.makeName("version");
        ExpandedName NAME = ExpandedName.makeName("name");
        ExpandedName REF = ExpandedName.makeName("ref");
        ExpandedName ID = ExpandedName.makeName("id");
        ExpandedName PUID = ExpandedName.makeName("puid");
        ExpandedName LOCAL = ExpandedName.makeName("local");
        ExpandedName IP = ExpandedName.makeName("ip");
        ExpandedName USERNAME = ExpandedName.makeName("username");
        ExpandedName PWD = ExpandedName.makeName("password");
        ExpandedName PORT = ExpandedName.makeName("port");
        ExpandedName PATH = ExpandedName.makeName("path");
        ExpandedName VALUE = ExpandedName.makeName("value");
        ExpandedName TYPE = ExpandedName.makeName("type");
        ExpandedName HOST_REF = ExpandedName.makeName("host-ref");
        ExpandedName DU_REF = ExpandedName.makeName("deployment-unit-ref");
        ExpandedName JMX_PORT = ExpandedName.makeName("jmxport");
        ExpandedName USE_AS_ENGINE_NAME = ExpandedName.makeName("use-as-engine-name");
    }


    interface Elements {

    	ExpandedName SITE = ExpandedName.makeName(URL, "site");
        ExpandedName CLUSTERS = ExpandedName.makeName(URL, "clusters");
        ExpandedName HOST_RESOURCES = ExpandedName.makeName(URL, "host-resources");
        ExpandedName CLUSTER = ExpandedName.makeName(URL, "cluster");
        ExpandedName MASTER_FILES = ExpandedName.makeName(URL, "master-files");
        ExpandedName CDD_MASTER = ExpandedName.makeName(URL, "cdd-master");
        ExpandedName EAR_MASTER = ExpandedName.makeName(URL, "ear-master");
        ExpandedName RUN_VERSION = ExpandedName.makeName(URL, "run-version");
        ExpandedName BE = ExpandedName.makeName(URL, "be");
        ExpandedName BE_RUNTIME = ExpandedName.makeName(URL, "be-runtime");
        ExpandedName HOST_RESOURCE = ExpandedName.makeName(URL, "host-resource");
        ExpandedName DEPLOY_FILES = ExpandedName.makeName(URL, "deployed-files"); 
        ExpandedName CDD_DEPLOYED = ExpandedName.makeName(URL, "cdd-deployed");
        ExpandedName EAR_DEPLOYED = ExpandedName.makeName(URL, "ear-deployed");
        ExpandedName DEPLOY_UNITS = ExpandedName.makeName(URL, "deployment-units");
        ExpandedName DEPLOY_UNIT = ExpandedName.makeName(URL, "deployment-unit");
        ExpandedName DEPLOY_MAPPINGS = ExpandedName.makeName(URL, "deployment-mappings");
        ExpandedName DEPLOY_MAPPING = ExpandedName.makeName(URL, "deployment-mapping");
        ExpandedName PROCESS_UNITS = ExpandedName.makeName(URL, "processing-units-config");
        ExpandedName PROCESS_UNIT = ExpandedName.makeName(URL, "processing-unit-config");
        ExpandedName PROPERTIES = ExpandedName.makeName(URL, "properties");
        ExpandedName PROPERTY = ExpandedName.makeName(URL, "property");
        ExpandedName HOSTNAME = ExpandedName.makeName(URL, "hostname");
        ExpandedName IP = ExpandedName.makeName(URL, "ip");
        ExpandedName CREDENTIALS = ExpandedName.makeName(URL, "user-credentials");
        ExpandedName OS_TYPE = ExpandedName.makeName(URL, "os-type");
        ExpandedName SOFTWARE = ExpandedName.makeName(URL, "software");
        ExpandedName TRA = ExpandedName.makeName(URL, "tra");
        ExpandedName HOME = ExpandedName.makeName(URL, "home");
        ExpandedName START_PU_METHOD = ExpandedName.makeName(URL, "start-pu-method");       
    }
}
