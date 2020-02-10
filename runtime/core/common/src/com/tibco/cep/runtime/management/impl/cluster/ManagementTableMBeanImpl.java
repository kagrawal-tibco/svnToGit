package com.tibco.cep.runtime.management.impl.cluster;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.tibco.be.util.BEProperties;
import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.management.ManagementTableMBean;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.impl.RuleServiceProviderImpl;
import com.tibco.cep.runtime.util.GvCommonUtils;
import com.tibco.cep.runtime.util.ProcessInfo;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
/**
 * @author vdhumal MBean interface implementation to get the instance info
 */
public class ManagementTableMBeanImpl implements ManagementTableMBean {

	public static final String OBJ_NAME_MNGMT_TABLE = "com.tibco.be:dir=Methods,Group=Management Table,name=InstanceInfo";

	private RuleServiceProvider ruleServiceProvider = null;
	private Map<String, Object> instanceInfo = null;
	private Map<String, Object> instanceDetails = null;

	public ManagementTableMBeanImpl() {
		instanceInfo = new HashMap<>();
		instanceDetails = new HashMap<>();
	}

	/**
	 * @param ruleServiceProvider
	 */
	public void init(RuleServiceProvider ruleServiceProvider) {
		this.ruleServiceProvider = ruleServiceProvider;

		String clusterName = null;
		Cluster cluster = ruleServiceProvider.getCluster();
		if (null != cluster) {
			clusterName = cluster.getClusterName();
		} else {
			Properties beProperties = this.ruleServiceProvider.getProperties();
			ClusterConfig clusterConfig = null;
			Map<String, String> gvs = new HashMap<>();
			if (beProperties != null) {
				clusterConfig = (ClusterConfig) beProperties.get(SystemProperty.CLUSTER_CONFIG.getPropertyName());
			}

			if (clusterConfig != null) {
				clusterName = CddTools.getValueFromMixed(clusterConfig.getName());
			}

			GlobalVariables globalVariables = this.ruleServiceProvider.getGlobalVariables();

			if (GvCommonUtils.isGlobalVar(clusterName)) {
				GlobalVariableDescriptor gv = globalVariables.getVariable(GvCommonUtils.stripGvMarkers(clusterName));
				if (null != gv)
					clusterName = gv.getValueAsString();
			}

		}

		String instanceName = System.getProperty(SystemProperty.ENGINE_NAME.getPropertyName());
		String hostProcessId = ProcessInfo.getProcessIdentifier();
		String puId = System.getProperty(SystemProperty.PROCESSING_UNIT_ID.getPropertyName());
		instanceInfo.put("CLUSTER_NAME", clusterName);
		instanceInfo.put("INSTANCE_NAME", instanceName);
		instanceInfo.put("HOST_PROCESS_ID", hostProcessId);

		instanceDetails.put("CLUSTER_NAME", clusterName);
		instanceDetails.put("INSTANCE_NAME", instanceName);
		instanceDetails.put("HOST_PROCESS_ID", hostProcessId);
		instanceDetails.put("PROCESSING_UNIT_ID", puId);
	}

	@Override
	public Map<String, Object> getInstanceInfo() {
		populateMemoryUsage();
		populateCPUUsage();
		if (ruleServiceProvider instanceof RuleServiceProviderImpl)
			instanceInfo.put("STATUS", ((RuleServiceProviderImpl) ruleServiceProvider).getStatus());
		return instanceInfo;
	}

	@Override
	public Map<String, Object> getInstanceDetails() {
		long max = Runtime.getRuntime().maxMemory();
		long free = max - Runtime.getRuntime().totalMemory() + Runtime.getRuntime().freeMemory();
		long used = max - free;
		double percentUsed = 100 * used / max;

		instanceDetails.put("INSTANCE_MAX_MEMORY", max);
		instanceDetails.put("INSTANCE_FREE_MEMORY", free);
		instanceDetails.put("INSTANCE_USED_MEMORY", used);
		instanceDetails.put("INSTANCE_PERCENT_USED_MEMORY", percentUsed);
		OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
		if (osBean != null && osBean instanceof com.sun.management.OperatingSystemMXBean) {
			double processCpuLoad = ((com.sun.management.OperatingSystemMXBean) osBean).getProcessCpuLoad();
			instanceDetails.put("INSTANCE_CPU_USAGE", (processCpuLoad * 100));
		}
		if (ruleServiceProvider instanceof RuleServiceProviderImpl)
			instanceDetails.put("STATUS", ((RuleServiceProviderImpl) ruleServiceProvider).getStatus());
		return instanceDetails;
	}

	@Override
	public Map<String, String> getInstanceAgentsInfo() {
		Map<String, String> instanceAgentsInfo = new HashMap<>();
		Cluster cluster = ruleServiceProvider.getCluster();
		if (cluster == null) {
			RuleSession[] ruleSessions = ruleServiceProvider.getRuleRuntime().getRuleSessions();
			for (int i = 0; i < ruleSessions.length; i++) {
				instanceAgentsInfo.put(ruleSessions[i].getName(), String.valueOf(i));
			}
		} else if (cluster.getAgentManager() != null) {
			CacheAgent[] allAgents = cluster.getAgentManager().getLocalAgents();
			if (allAgents != null) {
				for (CacheAgent agent : allAgents) {
					instanceAgentsInfo.put(agent.getAgentName(), String.valueOf(agent.getAgentId()));
				}
			}
		}
		return instanceAgentsInfo;
	}

	@Override
	public Map<String, String> getInstanceAgentsDetails() {
		Map<String, String> instanceAgentsInfo = new HashMap<>();
		Cluster cluster = ruleServiceProvider.getCluster();
		if (cluster == null) {
			RuleSession[] ruleSessions = ruleServiceProvider.getRuleRuntime().getRuleSessions();
			for (int i = 0; i < ruleSessions.length; i++) {
				instanceAgentsInfo.put(ruleSessions[i].getName(), i + ":" + CacheAgent.Type.INFERENCE.name());
			}
		} else if (cluster.getAgentManager() != null) {
			CacheAgent[] allAgents = cluster.getAgentManager().getLocalAgents();
			if (allAgents != null) {
				for (CacheAgent agent : allAgents) {
					instanceAgentsInfo.put(agent.getAgentName(),
							agent.getAgentId() + ":" + agent.getAgentType().name());
				}
			}
		}
		return instanceAgentsInfo;
	}

	/**
	 * Populate Memory Usage of the process
	 */
	private void populateMemoryUsage() {
		long max = Runtime.getRuntime().maxMemory();
		long free = max - Runtime.getRuntime().totalMemory() + Runtime.getRuntime().freeMemory();
		long used = max - free;
		double percentUsed = 100 * used / max;
		instanceInfo.put("INSTANCE_MAX_MEMORY", max);
		instanceInfo.put("INSTANCE_FREE_MEMORY", free);
		instanceInfo.put("INSTANCE_USED_MEMORY", used);
		instanceInfo.put("INSTANCE_PERCENT_USED_MEMORY", percentUsed);
	}

	/**
	 * Populate CPU usage of the process
	 */
	private void populateCPUUsage() {
		OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
		if (osBean != null && osBean instanceof com.sun.management.OperatingSystemMXBean) {
			double processCpuLoad = ((com.sun.management.OperatingSystemMXBean) osBean).getProcessCpuLoad();
			instanceInfo.put("INSTANCE_CPU_USAGE", (processCpuLoad * 100));
			instanceDetails.put("INSTANCE_CPU_USAGE", (processCpuLoad * 100));
		}
	}

	@Override
	public Map<String, Object> getGlobalVariables() {
		Map<String, Object> gvs = new HashMap<>();
		GlobalVariables globalVariables = this.ruleServiceProvider.getGlobalVariables();
		if (null != globalVariables) {
			Collection<GlobalVariableDescriptor> gvDescripters = globalVariables.getVariables();
			if (null != gvDescripters && !gvDescripters.isEmpty()) {
				for (GlobalVariableDescriptor globalVariableDescriptor : gvDescripters) {
					if (null != globalVariableDescriptor) {
						Map<String, Object> gvPropsMap = new HashMap<>();
						String name = globalVariableDescriptor.getName();
						boolean isServiceSettable = globalVariableDescriptor.isServiceSettable();
						boolean isDeploymentSettable = globalVariableDescriptor.isDeploymentSettable();
						String type = globalVariableDescriptor.getType();
						String value = globalVariableDescriptor.getValueAsString();
						String description = globalVariableDescriptor.getDescription();
						long modificationTime = globalVariableDescriptor.getModificationTime();

						gvPropsMap.put(GlobalVariableDescriptor.FIELD_NAME, name);
						gvPropsMap.put(GlobalVariableDescriptor.FIELD_TYPE, type);
						gvPropsMap.put(GlobalVariableDescriptor.FIELD_VALUE, value);
						gvPropsMap.put(GlobalVariableDescriptor.FIELD_MODTIME, modificationTime);
						gvPropsMap.put(GlobalVariableDescriptor.FIELD_ISSERVICE, isServiceSettable);
						gvPropsMap.put(GlobalVariableDescriptor.FIELD_ISDEPLOY, isDeploymentSettable);
						gvPropsMap.put(GlobalVariableDescriptor.FIELD_DESCRIPTION, description);
						gvs.put(name, gvPropsMap);
					}
				}
			}
		}
		return gvs;
	}

	@Override
	public Map<String, Object> getSystemProperties() {
		Map<String, Object> beProps = new HashMap<>();

		Properties properties = this.ruleServiceProvider.getProperties();
		if (properties instanceof BEProperties) {
			BEProperties beProperties = ((BEProperties) properties);
			Set<Object> keySet = beProperties.keySet();
			if (null != keySet && !keySet.isEmpty()) {
				for (Object object : keySet) {
					if (null != object) {
						if (object.toString().trim().startsWith("java.property."))
							beProps.put(object.toString(), properties.getProperty(object.toString()));
					}
				}
			}
		}
		return beProps;
	}

	@Override
	public Map<String, Object> getBEProperties() {
		Map<String, Object> beProps = new HashMap<>();

		Properties properties = this.ruleServiceProvider.getProperties();
		if (properties instanceof BEProperties) {
			BEProperties beProperties = ((BEProperties) properties);
			Set<Object> keySet = beProperties.keySet();
			if (null != keySet && !keySet.isEmpty()) {
				for (Object object : keySet) {
					if (null != object) {
						if (object.toString().trim().startsWith("be."))
							beProps.put(object.toString(), properties.getProperty(object.toString()));
					}
				}
			}
		}
		return beProps;
	}

	@Override
	public Map<String, Object> getJVMProperties() {
		Map<String, Object> beProps = new HashMap<>();

		Properties properties = this.ruleServiceProvider.getProperties();
		if (properties instanceof BEProperties) {
			BEProperties beProperties = ((BEProperties) properties);
			String extendedJavaProperties = beProperties.getString("java.extended.properties");
			String xms = null;
			String xmx = null;

			if (null != extendedJavaProperties && !extendedJavaProperties.trim().isEmpty()) {
				String[] parts = extendedJavaProperties.split(" ");
				for (String part : parts) {
					if (null != part) {
						if (part.contains("-Xms")) {
							xms = part.replace("-Xms", "").trim();
						} else if (part.contains("-Xmx")) {
							xmx = part.replace("-Xmx", "").trim();
						}
					}
				}
			}
			beProps.put("Initial Heap Size", xms);
			beProps.put("Max Heap Size", xmx);
		}
		return beProps;
	}

	@Override
	public void stopInstance() {
		new Thread() {
			@Override
			public void run() {
				Runtime.getRuntime().exit(0); // it calls the shutdown hook
			}
		}.start();
	}

}
