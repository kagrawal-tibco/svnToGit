package com.tibco.cep.bemm.management.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.management.service.BEApplicationCDDCacheService;
import com.tibco.cep.runtime.util.ClusterConfigCommonUtils;
import com.tibco.rta.common.service.impl.AbstractStartStopServiceImpl;
import com.tibco.tea.agent.be.version.be_teagentVersion;

/**
 * Implementation of BEApplicationCDDCacheService
 * 
 * @author dijadhav
 *
 */
public class BEApplicationCDDCacheServiceImpl extends AbstractStartStopServiceImpl
		implements BEApplicationCDDCacheService<File> {
	private Map<String, ClusterConfig> cddCache = new ConcurrentHashMap<String, ClusterConfig>();
	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(BEApplicationCDDCacheService.class);
	private Map<String,String>appNameVersion=  new ConcurrentHashMap<String, String>();
	/**
	 * Default constructor
	 */
	public BEApplicationCDDCacheServiceImpl() {
	}

	@Override
	public ClusterConfig fetchClusterConfigDetails(String applicationName, String applicationDataStoreLocation) {
		LOGGER.log(Level.DEBUG, "Fetched cached cdd data");
		if (!cddCache.containsKey(applicationName)) {
			String cddPath = applicationDataStoreLocation + File.separator + applicationName + File.separator
					+ applicationName + ".cdd";
			cacheClusterConfigDetails(applicationName, cddPath);
		}
		return cddCache.get(applicationName);
	}

	@Override
	public void cacheClusterConfigDetails(String applicationName, String cddPath) {
		LOGGER.log(Level.DEBUG, "Loading data from cdd :" + cddPath);
		BufferedReader br = null;
		StringBuffer buffer = new StringBuffer();
		try {

			String[] parts =  be_teagentVersion.version.split("\\.");
			String version = parts[0] + "." + parts[1];
			br = new BufferedReader(new FileReader(cddPath));
			String line = null;
			while (null != (line = br.readLine())) {
				if(line.trim().contains("<cluster")){
					if (line.trim().startsWith("<cluster")) {
						String ver=line.substring(line.lastIndexOf("/") + 1,line.lastIndexOf("/")+4
								);
						appNameVersion.put(applicationName, ver);
						line = line.substring(0, line.lastIndexOf("/") + 1);
						line += version + "\">";
					}else{
						String ver=line.substring(line.lastIndexOf("/") + 1,line.lastIndexOf("/")+4
								);
						appNameVersion.put(applicationName, ver);
						line = line.substring(0, line.lastIndexOf("/") + 1);
						line += version + "\">";
					}
				}
				
				buffer.append(line);
			}
		} catch (IOException e1) {

		} finally {
			if (null != br) {
				try {
					br.close();
				} catch (IOException e) {
				}
			}

		}

		ClusterConfig clusterConfig = null;

		try {
			clusterConfig = ClusterConfigCommonUtils.getClusterConfig(buffer.toString());
			cddCache.put(applicationName, clusterConfig);
			LOGGER.log(Level.DEBUG, "Loaded cdd data from :" + cddPath);
		} catch (IllegalArgumentException e) {
		}
	}

	@Override
	public void removeConfiguration(String applicationName) throws Exception {
		LOGGER.log(Level.DEBUG, "Remove configuration from cache for " + applicationName);
		cddCache.remove(applicationName);
	}

	@Override
	public String getApplicationVersion(String applicationName) {
		return appNameVersion.get(applicationName);
	}

}
