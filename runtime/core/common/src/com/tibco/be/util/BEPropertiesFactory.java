package com.tibco.be.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Properties;

import org.eclipse.emf.common.util.EList;

import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.AgentClassConfig;
import com.tibco.be.util.config.cdd.AgentConfig;
import com.tibco.be.util.config.cdd.AgentsConfig;
import com.tibco.be.util.config.cdd.CacheAgentClassConfig;
import com.tibco.be.util.config.cdd.ProcessingUnitConfig;
import com.tibco.be.util.config.factories.ClusterConfigFactory;
import com.tibco.be.util.packaging.Constants;
import com.tibco.cep.runtime.util.SystemProperty;

/*
 * User: nprade
 * Date: 2/15/11
 * Time: 11:55 AM
 */
public class BEPropertiesFactory {

    private static final BEPropertiesFactory INSTANCE = new BEPropertiesFactory();
    private static final String PROP_FILE_PFX = "be.property.file";

    public static BEPropertiesFactory getInstance() {
        return INSTANCE;
    }

    private BEPropertiesFactory() {}


    public BEProperties makeBEProperties(
            Properties cmdProperties) {

        cmdProperties = getTrimmedProperties(cmdProperties);

        final BEProperties beProps = new BEProperties();

        overwriteMapEntries(beProps, getPropertiesFromTraFile(cmdProperties));
        overwriteMapEntries(beProps, getPropertiesFromPropertyFiles(cmdProperties));
        overwriteMapEntries(beProps, System.getProperties());
        overwriteMapEntries(beProps, cmdProperties);

        beProps.setProperty(SystemProperty.CLUSTER_CONFIG_PATH.getPropertyName(), getCddLocation(beProps));

        final String puid = getPUID(beProps);
        if (null == puid) {
            beProps.remove(SystemProperty.PROCESSING_UNIT_ID.getPropertyName());
        } else {
            beProps.setProperty(SystemProperty.PROCESSING_UNIT_ID.getPropertyName(), puid);
        }

        try {
            beProps.put(SystemProperty.CLUSTER_CONFIG.getPropertyName(), readCdd(beProps));
            beProps.addNewProperties(getPropertiesFromCdd(beProps));
        } catch (Exception e) {
            throw new RuntimeException("Cannot read CDD: " + getCddLocation(beProps), e);
        }

        updateLogProperties(beProps);

        beProps.addNewProperties(BEProperties.loadDefault());

        beProps.substituteTibcoEnvironmentVariables();

        if (Boolean.valueOf(beProps.getProperty("__update.system.property__", "true"))) {
            beProps.updateSystemProperties();
        }

        return beProps;
    }


    public BEProperties makeBEProperties(
            Properties cmdProperties,
            String instanceName) {

        final BEProperties beProperties = makeBEProperties(cmdProperties);

        if ((null == instanceName) || instanceName.trim().isEmpty()) {
            instanceName = setupInstanceName(beProperties);
        }
        beProperties.remove(SystemProperty.ENGINE_NAME.getPropertyName());
        beProperties.put(SystemProperty.ENGINE_NAME.getPropertyName(), instanceName);

        //if we don't do this here, and the engine name is not set in the command line,
        //the engine name ends up never being set in the System Properties.
        System.setProperty(SystemProperty.ENGINE_NAME.getPropertyName(), instanceName);

        return beProperties;
    }


    private String getCddLocation(
            Properties props) {

        String cddLocation = props.getProperty(SystemProperty.CLUSTER_CONFIG_PATH.getPropertyName());
        if ((null == cddLocation) || "".equals(cddLocation.trim())) {
            cddLocation = props.getProperty("tibco.clientVar." + Constants.DD.CDD, "default.cdd");
        }
        return cddLocation;
    }

    /**
     * Validate the supplied engine properties and return the proper agent/instance name.  This
     * will return the agent key if it is specified, or if agentName is null, will return the
     * first agent found in the cluster config
     * @param cmdProperties
     * @param agentName
     * @return
     * @throws IllegalArgumentException
     */
    public String validateProperties(Properties cmdProperties, String agentName) throws IllegalArgumentException {
        final String repoUrl = cmdProperties.getProperty("tibco.repourl");
        if (null != repoUrl) {
        	final File earFile = new File(repoUrl);
        	if (!earFile.exists()) {
        		throw new IllegalArgumentException("Project location '" + repoUrl+"' not found");
        	}
        } else {
        	throw new IllegalArgumentException("Project location cannot be empty");
        }
        
        com.tibco.be.util.config.cdd.ClusterConfig clusterConfig = null;
    	cmdProperties = getTrimmedProperties(cmdProperties);

        final BEProperties beProps = new BEProperties();

        overwriteMapEntries(beProps, getPropertiesFromTraFile(cmdProperties));
        overwriteMapEntries(beProps, getPropertiesFromPropertyFiles(cmdProperties));
        overwriteMapEntries(beProps, System.getProperties());
        overwriteMapEntries(beProps, cmdProperties);

        beProps.setProperty(SystemProperty.CLUSTER_CONFIG_PATH.getPropertyName(), getCddLocation(beProps));

        final String puid = getPUID(beProps);
        if (null == puid) {
            beProps.remove(SystemProperty.PROCESSING_UNIT_ID.getPropertyName());
        } else {
            beProps.setProperty(SystemProperty.PROCESSING_UNIT_ID.getPropertyName(), puid);
        }
        try {
			clusterConfig = readCdd(beProps);
		} catch (Exception e) {
			throw new IllegalArgumentException("Unable to read CDD file '"+getCddLocation(beProps)+"'", e);
		}
        if (clusterConfig != null) {
            if ((null != puid)  && !puid.trim().isEmpty()) {
            	
                final ProcessingUnitConfig puc = (ProcessingUnitConfig) CddTools.findById(
                		clusterConfig.getProcessingUnits().getProcessingUnit(),
                		puid);            
                if (null == puc) {
                    throw new IllegalArgumentException("Processing unit '" + puid+"' not found in CDD");
                }
                
                EList<AgentConfig> agent = puc.getAgents().getAgent();
                boolean found = false;
                boolean cacheEnabled = clusterConfig.getObjectManagement() != null && clusterConfig.getObjectManagement().getCacheManager() != null;
                for (AgentConfig agentConfig : agent) {
                	String key = CddTools.getValueFromMixed(agentConfig.getKey());
                	if (null == agentName || agentName.trim().isEmpty()) {
                		return (!cacheEnabled && key != null) ? key : agentConfig.getRef().getId();
                	}
                	if (agentName.equals(agentConfig.getRef().getId()) || agentName.equals(key)) {
                		return (!cacheEnabled && key != null) ? key : agentConfig.getRef().getId();
                	}
				}
                if (!found) {
                    throw new IllegalArgumentException("Agent name '"+agentName+"' not found in CDD");
                }
            } else {
            	throw new IllegalArgumentException("Processing unit cannot be empty");
            }
        }
        return agentName;
    }
    
    private Properties getPropertiesFromCdd(
            BEProperties beProps)
            throws Exception {

        final Properties cddProps = new Properties();

//        final ClusterConfig clusterConfig = readCdd(beProps);

        final com.tibco.be.util.config.cdd.ClusterConfig clusterConfig = readCdd(beProps);

        // Cluster settings (OM < message encoding < custom properties)
        overwriteMapEntries(cddProps, clusterConfig.toProperties());

        // Active P.U.
        final String puid = getPUID(beProps);
        if ((null != puid)  && !puid.trim().isEmpty()) {
        	
            final ProcessingUnitConfig puc = (ProcessingUnitConfig) CddTools.findById(
            		clusterConfig.getProcessingUnits().getProcessingUnit(),
            		puid);            
            if (null == puc) {
                throw new RuntimeException("Cannot find processing unit name: " + puid);
            }
            overwriteMapEntries(cddProps, puc.toProperties());

            // Cache server or not.
            final AgentsConfig agentsConfig = puc.getAgents();
            int countCS = 0;
            for (final AgentConfig agentConfig : agentsConfig.getAgent()) {
                final AgentClassConfig agentClassConfig = agentConfig.getRef();
                if (agentClassConfig instanceof CacheAgentClassConfig) {
                    countCS++;
                }
            }
            if ((countCS > 1) || ((countCS > 0) && agentsConfig.getAgent().size() > 1)) {
                throw new IllegalArgumentException("Cache server with other agent(s).");
            }
            if (countCS > 0) {
                cddProps.setProperty(SystemProperty.CACHE_SERVER.getPropertyName(), Boolean.TRUE.toString());
                cddProps.setProperty(SystemProperty.CLUSTER_NODE_ISSEEDER.getPropertyName(), Boolean.TRUE.toString());
            }

        }
        return cddProps;
    }


    private Properties getPropertiesFromPropertyFiles(
            Properties properties) {

        final Properties props = new Properties();
        for (Object filePath : new BEProperties(properties).getBranch(PROP_FILE_PFX).values()) {
            final File file = new File((String) filePath);
            if (file.exists()) {
                try {
                    props.load(new FileInputStream(file));
                } catch (IOException e) {
                    throw new RuntimeException("Cannot open property file: " + file.getPath(), e);
                }
            }
        }
        return getTrimmedProperties(props);
    }


    private Properties getPropertiesFromTraFile(
            Properties beProperties) {

        final String path = beProperties.getProperty("be.bootstrap.property.file", "be-engine.tra");
        if (null == path) {
            return null;
        }

        final Properties traProps = new Properties();

        final File propertyFile = new File(path);
        if (propertyFile.exists()) {
            try {
                traProps.load(new FileInputStream(propertyFile));
            } catch (IOException e) {
                throw new RuntimeException("Cannot open bootStrap property file:" + propertyFile, e);
            }
        }
        return getTrimmedProperties(traProps);
    }


    private String getPUID(
            Properties beProperties) {

        final String puid = beProperties.getProperty(SystemProperty.PROCESSING_UNIT_ID.getPropertyName(),
                beProperties.getProperty("tibco.clientVar." + Constants.DD.PUID,
                        beProperties.getProperty(SystemProperty.PROCESSING_UNIT_ID_DEFAULT.getPropertyName())));

        if ((null == puid) || puid.trim().isEmpty()) {
            return null;
        }
        return puid.trim();
    }

    private Properties getTrimmedProperties(
            Properties properties) {

        if (null == properties) {
            return null;
        }

        final Properties p = new Properties();
        for (final Map.Entry<Object, Object> entry : properties.entrySet()) {
            Object value = entry.getValue();
            if ((value instanceof String) && !"line.separator".equals(entry.getKey())) {
                p.put(entry.getKey(), ((String) value).trim());
            } else {
                p.put(entry.getKey(), entry.getValue());
            }
        }
        return p;
    }


    private void overwriteMapEntries(
            Map<Object, Object> targetMap,
            Map<Object, Object> overwritingEntries) {

        if (null != overwritingEntries) {
            targetMap.putAll(overwritingEntries);
        }
    }

    private com.tibco.be.util.config.cdd.ClusterConfig readCdd(Properties env) throws Exception {
    	com.tibco.be.util.config.cdd.ClusterConfig clusterConfig 
    		= (com.tibco.be.util.config.cdd.ClusterConfig) env.get(SystemProperty.CLUSTER_CONFIG.getPropertyName());
    	if (null == clusterConfig) {
    		final String cddPath = env.getProperty(SystemProperty.CLUSTER_CONFIG_PATH.getPropertyName());
    		final ClusterConfigFactory factory = new ClusterConfigFactory();  

            // First tries in the local file system.
            if (new File(cddPath).isFile()) {
            	return factory.newConfig(cddPath);
            }
            // Then tries in the SAR.
            final String repoUrl = env.getProperty("tibco.repourl");
            if (null != repoUrl) {
            	final File earFile = new File(repoUrl);
            	
            	if (earFile.isFile()) {
            		//System.setProperty("org.eclipse.emf.common.util.URI.archiveSchemes", "ear bar sar");
            		String path = cddPath.startsWith("/") ? cddPath.substring(1):cddPath;
            		path = "archive:jar:"+earFile.toURI().toString() + "!/Shared%20Archive.sar!/"+path;
            		clusterConfig = factory.newConfig(path);
//            		final ZipFile earZip = new ZipFile(earFile);
//            		final ZipEntry sarEntry = earZip.getEntry("Shared Archive.sar");
//            		final ZipInputStream sarZip = new ZipInputStream(earZip.getInputStream(sarEntry));
//            		for (ZipEntry e = sarZip.getNextEntry(); null != e; e = sarZip.getNextEntry()) {
//            			if (cddPath.equals(e.getName())) {
//            				clusterConfig = loadCdd(sarZip);                        		
//            				break;
//            			}
//            		}
//            		sarZip.close();
//            		earZip.close();
            		
            	} else if (earFile.isDirectory()) {
            		clusterConfig = factory.newConfig(earFile.getPath() + File.separator + cddPath);
            	}
            }
    	}   
    	
    	if (null == clusterConfig) {
            throw new Exception("CDD not found");
        }

        return clusterConfig;
    	
    }

//    private com.tibco.be.util.config.cdd.ClusterConfig loadCdd(URI uri) throws IOException {
//    	ResourceSet resourceSet = new ResourceSetImpl();
//		// add file extension to registry
//    	 CddPackageImpl.eINSTANCE.eClass();
//		ResourceFactoryRegistryImpl.INSTANCE.getExtensionToFactoryMap().put("cdd", new GenericXMLResourceFactoryImpl());
//        Resource resource = resourceSet.createResource(uri);
//        Map<Object,Object> options = new HashMap<Object,Object>();
//        options.put(XMLResource.OPTION_ENCODING, "UTF-8");
//        resource.load(options);
//		CddRoot dr = (CddRoot) resource.getContents().get(0);
//		return dr.getCluster();
//	}

//	private ClusterConfig readCdd(Properties env) throws Exception {
////		com.tibco.be.util.config.cdd.ClusterConfig cc = readCdd_(env);
////		cc.getResolveMap();
//        ClusterConfig clusterConfig = (ClusterConfig) env.get(SystemProperty.CLUSTER_CONFIG.getPropertyName());
//        if (null == clusterConfig) {
//            final String cddPath = env.getProperty(SystemProperty.CLUSTER_CONFIG_PATH.getPropertyName());
//
//            // First tries in the local file system.
//            if (new File(cddPath).isFile()) {
//                return new ClusterConfigFactory().newConfig(cddPath);
//            }
//
//            // Then tries in the SAR.
//            final String repoUrl = env.getProperty("tibco.repourl");
//            if (null != repoUrl) {
//                final File earFile = new File(repoUrl);
//
//                if (earFile.isFile()) {
//                    final ZipFile earZip = new ZipFile(earFile);
//                    final ZipEntry sarEntry = earZip.getEntry("Shared Archive.sar");
//                    final ZipInputStream sarZip = new ZipInputStream(earZip.getInputStream(sarEntry));
//                    for (ZipEntry e = sarZip.getNextEntry(); null != e; e = sarZip.getNextEntry()) {
//                        if (cddPath.equals(e.getName())) {
//                            clusterConfig = new ClusterConfigFactory().newConfig(new InputSource(sarZip));
//                            break;
//                        }
//                    }
//                    sarZip.close();
//                    earZip.close();
//
//                } else if (earFile.isDirectory()) {
//                    clusterConfig = new ClusterConfigFactory().newConfig(earFile.getPath() + File.separator + cddPath);
//                }
//            }
//        }
//
//        if (null == clusterConfig) {
//            throw new Exception("CDD not found");
//        }
//
//        return clusterConfig;
//    }


    private String setupInstanceName(
            BEProperties beProperties) {

        String name = beProperties.getString(SystemProperty.ENGINE_NAME.getPropertyName());

        if ((null == name) || "".equals(name.trim())) {
            // Name not provided explicitly => tries the HMA name.
            name = beProperties.getString("tibco.hawk.microagent.name", beProperties.getString("Hawk.AMI.DisplayName"));
            if ((null == name) || "".equals(name.trim())) {
                // No HMA name => tries the host name.
                try {
                    name = InetAddress.getLocalHost().getCanonicalHostName();
                } catch (UnknownHostException uhe) {
                    // Host name not found => gives a default name.
                    name = "engine";
                }// catch
            } else {
                // Name based on HMA name.
                final String prefix = "COM.TIBCO.ADAPTER." + com.tibco.cep.cep_commonVersion.container_id;
                final int prefixLength = prefix.length();
                if (name.startsWith(prefix) && (prefixLength < name.length())) {
                    name = name.substring(prefixLength + 1);
                    // Now removes domain name
                    final int indexOfDotInName = name.indexOf('.');
                    if ((indexOfDotInName != -1) && (indexOfDotInName < name.length())) {
                        name = name.substring(indexOfDotInName + 1);
                    }// if
                }// if
            }// else
        }// else

        return name.replace(' ', '_');
    }// setupInstanceName


    private void updateLogProperties(
            BEProperties beProps) {

        // trace dir
        final String traceDir = beProps.getProperty("Engine.Log.Dir",
                beProps.getProperty(SystemProperty.TRACE_FILE_DIR.getPropertyName(), "./logs"));
        beProps.remove(SystemProperty.TRACE_FILE_DIR.getPropertyName());
        beProps.put(SystemProperty.TRACE_FILE_DIR.getPropertyName(), traceDir);

        // max nb of trace files
        final String nbFiles = beProps.getString("Engine.Log.MaxNum",
                beProps.getString("be.trace.maxnum", "5"));
        beProps.remove("be.trace.maxnum");
        beProps.put("be.trace.maxnum", nbFiles);

        // max size of trace files
        final String maxSize = beProps.getString("Engine.Log.MaxSize",
                beProps.getString("be.trace.maxsize", "20480000"));
        beProps.remove("be.trace.maxsize");
        beProps.put("be.trace.maxsize", maxSize);

        // trace file name
        final String traceName = beProps.getString(SystemProperty.TRACE_NAME.getPropertyName());
        if (traceName == null) {
            beProps.put(SystemProperty.TRACE_NAME.getPropertyName(), beProps);
        }
    }

}
