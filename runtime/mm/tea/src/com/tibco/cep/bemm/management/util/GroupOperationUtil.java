package com.tibco.cep.bemm.management.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.management.service.BEServiceInstancesManagementService;
import com.tibco.cep.bemm.model.Application;
import com.tibco.cep.bemm.model.Host;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.cep.bemm.model.impl.DeploymentVariableType;
import com.tibco.cep.bemm.model.impl.DeploymentVariables;
import com.tibco.cep.bemm.model.impl.NameValuePair;
import com.tibco.cep.bemm.model.impl.NameValuePairs;
import com.tibco.tea.agent.be.ui.model.GroupDeploymentVariable;
import com.tibco.tea.agent.be.ui.model.impl.GroupDeploymentVariableImpl;

/**
 * Class populates which has same properties ,then check their effective value
 * or value
 * 
 * @author dijadhav
 *
 */
public class GroupOperationUtil {
	/**
	 * Logger instance
	 */
	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(GroupOperationUtil.class);

	/**
	 * Perform application, PU and Agent level group operation
	 * 
	 * @param application
	 * @param deploymentVariables
	 * @param deploymentVariableType
	 * @param instancesKey
	 * @param instancesService
	 */
	public static void perform(Application application, List<GroupDeploymentVariable> deploymentVariables,
			DeploymentVariableType deploymentVariableType, List<String> instancesKey,
			BEServiceInstancesManagementService instancesService) {
		Map<NameValuePair, List<ServiceInstance>> groupDeploymentVariableMap = new HashMap<NameValuePair, List<ServiceInstance>>();
		populateDeploymentVariableMap(application, groupDeploymentVariableMap, deploymentVariableType, instancesKey,
				instancesService);
		compareValue(application, deploymentVariables, groupDeploymentVariableMap, deploymentVariableType, instancesKey,
				instancesService);
	}

	/**
	 * Perform host level group operation
	 * 
	 * @param host
	 * @param deploymentVariables
	 * @param deploymentVariableType
	 * @param instancesKey
	 * @param groupDeploymentVariableMap
	 * @param instancesService
	 * @param instanceService
	 */
	public static void perform(Host host, List<GroupDeploymentVariable> deploymentVariables,
			DeploymentVariableType deploymentVariableType, List<String> instancesKey,
			Map<NameValuePair, List<ServiceInstance>> groupDeploymentVariableMap,
			BEServiceInstancesManagementService instancesService, BEServiceInstancesManagementService instanceService) {
		populateInstanceDeploymentVariableMap(host.getApplication(), groupDeploymentVariableMap, deploymentVariableType,
				instancesKey, instanceService, host);
		compareValue(host.getApplication(), deploymentVariables, groupDeploymentVariableMap, deploymentVariableType,
				instancesKey, instancesService);
	}

	/**
	 * Populate map of NameValuePair and collection of their instances
	 * 
	 * @param application
	 * @param instanceMap
	 * @param instancesKey
	 * @param instanceService
	 */
	private static void populateDeploymentVariableMap(Application application,
			Map<NameValuePair, List<ServiceInstance>> instanceMap, DeploymentVariableType deploymentVariableType,
			List<String> instancesKey, BEServiceInstancesManagementService instanceService) {

		for (Host host : application.getHosts()) {
			populateInstanceDeploymentVariableMap(application, instanceMap, deploymentVariableType, instancesKey,
					instanceService, host);
		}

	}

	/**
	 * @param application
	 * @param instanceMap
	 * @param deploymentVariableType
	 * @param instancesKey
	 * @param instanceService
	 * @param host
	 */
	private static void populateInstanceDeploymentVariableMap(Application application,
			Map<NameValuePair, List<ServiceInstance>> instanceMap, DeploymentVariableType deploymentVariableType,
			List<String> instancesKey, BEServiceInstancesManagementService instanceService, Host host) {
		for (ServiceInstance serviceInstance : host.getInstances()) {
			if (null != serviceInstance && instancesKey.contains(serviceInstance.getKey())) {
				try {
					DeploymentVariables deploymentVariables = null;
					deploymentVariables = loadDeoloymentVariables(application, deploymentVariableType, serviceInstance);
					if (null != deploymentVariables) {
						NameValuePairs nameValuePairs = deploymentVariables.getNameValuePairs();
						if (null != nameValuePairs) {
							List<NameValuePair> nameValuePair = nameValuePairs.getNameValuePair();
							if (null != nameValuePair && !nameValuePair.isEmpty()) {
								for (NameValuePair nameValue : nameValuePair) {

									if (null != nameValue) {

										if (DeploymentVariableType.BE_PROPERTIES.equals(deploymentVariableType)
												|| DeploymentVariableType.GLOBAL_VARIABLES
														.equals(deploymentVariableType)
												|| DeploymentVariableType.LOG_PATTERNS.equals(deploymentVariableType)
												|| DeploymentVariableType.SYSTEM_VARIABLES
														.equals(deploymentVariableType)
												|| DeploymentVariableType.JVM_PROPERTIES
														.equals(deploymentVariableType)) {
											addInstance(instanceMap, serviceInstance, nameValue);

										} else {
											if (!nameValue.isIsDeleted()) {
												addInstance(instanceMap, serviceInstance, nameValue);
											}
										}
									}

								}
							}
						}
					}
				} catch (Exception e) {
					LOGGER.log(Level.ERROR, e, e.getMessage());
				}
			}
		}

		clearAllDeletedProperties(instanceMap, deploymentVariableType, application, instanceService);

	}

	/**
	 * @param instanceMap
	 * @param serviceInstance
	 * @param nameValue
	 */
	private static void addInstance(Map<NameValuePair, List<ServiceInstance>> instanceMap,
			ServiceInstance serviceInstance, NameValuePair nameValue) {
		List<ServiceInstance> serviceInstances = null;
		if (instanceMap.containsKey(nameValue)) {
			serviceInstances = instanceMap.get(nameValue);
		} else {
			serviceInstances = new ArrayList<ServiceInstance>();
		}
		serviceInstances.add(serviceInstance);
		instanceMap.put(nameValue, serviceInstances);
	}

	/**
	 * Compare value of deployment variable
	 * 
	 * @param application
	 * @param deploymentVariables2
	 * @param groupInstanceMap
	 * @param deploymentVariableType
	 * @param instancesKey
	 * @param instanceService
	 */
	private static void compareValue(Application application, List<GroupDeploymentVariable> groupDeploymentVariables,
			Map<NameValuePair, List<ServiceInstance>> groupInstanceMap, DeploymentVariableType deploymentVariableType,
			List<String> instancesKey, BEServiceInstancesManagementService instanceService) {

		for (Entry<NameValuePair, List<ServiceInstance>> entry : groupInstanceMap.entrySet()) {
			List<ServiceInstance> serviceInstances = entry.getValue();
			NameValuePair key = entry.getKey();

			if (serviceInstances.size() != instancesKey.size()) {
				createGroupVariable(deploymentVariableType, groupDeploymentVariables, key, false, serviceInstances,
						key.getDeployedValue());

			} else {

				DeploymentVariables deploymentVariables = null;
				deploymentVariables = loadDeoloymentVariables(application, deploymentVariableType,
						serviceInstances.get(0));

				NameValuePair nameValuePair = getInstanceMatchedNameValuePair(deploymentVariables, key);
				if (null != nameValuePair) {
					if (serviceInstances.size() > 1) {
						String effectiveValue = null;

						if (DeploymentVariableType.BE_PROPERTIES.equals(deploymentVariableType)
								|| DeploymentVariableType.GLOBAL_VARIABLES.equals(deploymentVariableType)
								|| DeploymentVariableType.LOG_PATTERNS.equals(deploymentVariableType)
								|| DeploymentVariableType.SYSTEM_VARIABLES.equals(deploymentVariableType)
								|| DeploymentVariableType.JVM_PROPERTIES.equals(deploymentVariableType)) {
							
							effectiveValue = getEffectiveValue(nameValuePair);
						}

						boolean hasEqualValues = false;
						for (int i = 1; i < serviceInstances.size(); i++) {
							DeploymentVariables variables = null;
							variables = loadDeoloymentVariables(application, deploymentVariableType,
									serviceInstances.get(i));

							NameValuePair valuePair = getInstanceMatchedNameValuePair(variables, key);
							if (null != valuePair) {

								if (DeploymentVariableType.BE_PROPERTIES.equals(deploymentVariableType)
										|| DeploymentVariableType.GLOBAL_VARIABLES.equals(deploymentVariableType)
										|| DeploymentVariableType.LOG_PATTERNS.equals(deploymentVariableType)
										|| DeploymentVariableType.SYSTEM_VARIABLES.equals(deploymentVariableType)
										|| DeploymentVariableType.JVM_PROPERTIES.equals(deploymentVariableType)) {

									String effectiveValue1 = getEffectiveValue(valuePair);
									if ("Password".equals(valuePair.getType())
											&& "Password".equals(nameValuePair.getType())) {
										if (null != effectiveValue && null != effectiveValue1
												&& ManagementUtil.getDecodedPwd(effectiveValue)
														.equals(ManagementUtil.getDecodedPwd(effectiveValue1))) {
											if (!valuePair.isIsDeleted() && !nameValuePair.isIsDeleted())
												hasEqualValues = true;
											else
												hasEqualValues = false;
										}
									} else {
										if (null != effectiveValue && null != effectiveValue1
												&& effectiveValue.equals(effectiveValue1)) {
											if (!valuePair.isIsDeleted() && !nameValuePair.isIsDeleted())
												hasEqualValues = true;
											else
												hasEqualValues = false;
										} else if (null == effectiveValue && null == effectiveValue1) {
											hasEqualValues = true;
										} else if (null == effectiveValue || null == effectiveValue1) {
											hasEqualValues = false;
										} else if (effectiveValue.trim().isEmpty()
												&& effectiveValue1.trim().isEmpty()) {
											hasEqualValues = true;
										} else {
											hasEqualValues = false;
											break;
										}
									}

								} else {
									if (null != valuePair.getValue() && null != nameValuePair.getValue()
											&& valuePair.getValue().equals(nameValuePair.getValue())) {
										if (!valuePair.isIsDeleted() && !nameValuePair.isIsDeleted())
											hasEqualValues = true;
										else
											hasEqualValues = false;
										hasEqualValues = true;
									} else if (null == valuePair.getValue() && null == nameValuePair.getValue()) {
										hasEqualValues = true;
									} else if (null == valuePair.getValue() || null == nameValuePair.getValue()) {
										hasEqualValues = false;
									} else if (valuePair.getValue().isEmpty() && nameValuePair.getValue().isEmpty()) {
										hasEqualValues = true;
									} else {
										hasEqualValues = false;
										break;
									}
								}

							}
							if (!hasEqualValues) {
								break;
							}
						}

						createGroupVariable(deploymentVariableType, groupDeploymentVariables, nameValuePair,
								hasEqualValues, serviceInstances, effectiveValue);

					} else {
						String effectiveValue = nameValuePair.getDefaultValue();

						if (null != nameValuePair.getDeployedValue()
								&& !nameValuePair.getDeployedValue().trim().isEmpty()) {
							effectiveValue = nameValuePair.getDeployedValue();
						}
						if (null != nameValuePair.getValue() && !nameValuePair.getValue().trim().isEmpty()) {
							effectiveValue = nameValuePair.getValue();
						}
						createGroupVariable(deploymentVariableType, groupDeploymentVariables, nameValuePair, true,
								serviceInstances, effectiveValue);
					}
				}
			}

		}
	}

	public static String getEffectiveValue(NameValuePair nameValuePair) {
		String effectiveValue = nameValuePair.getDefaultValue();

		if (null != nameValuePair.getDeployedValue()
				&& !nameValuePair.getDeployedValue().trim().isEmpty()) {
			effectiveValue = nameValuePair.getDeployedValue();
		}
		if (null != nameValuePair.getValue() && !nameValuePair.getValue().trim().isEmpty()) {
			effectiveValue = nameValuePair.getValue();
		}
		
		return effectiveValue;
	}

	/**
	 * Create GroupVariable object
	 * 
	 * @param groupDeploymentVariables
	 *            - List of group deployment variable
	 * @param key
	 *            - Name value pair as key
	 * @param hasEqualValues
	 *            - Flag to indicate whether all instance have same value for
	 *            given NameValuePair
	 * @param instanceKeys
	 *            - Insatnc4
	 * @param effectiveValue
	 */
	private static void createGroupVariable(DeploymentVariableType deploymentVariableType,
			List<GroupDeploymentVariable> groupDeploymentVariables, NameValuePair key, boolean hasEqualValues,
			List<ServiceInstance> serviceInstances, String effectiveValue) {
		GroupDeploymentVariable deploymentVariable = new GroupDeploymentVariableImpl();
		deploymentVariable.setHasEqualValue(hasEqualValues);
		deploymentVariable.setName(key.getName());
		deploymentVariable.setDescription(key.getDescription());
		deploymentVariable.setType(key.getType());
		if (hasEqualValues) {
			deploymentVariable.setEffectiveValue(effectiveValue);
			deploymentVariable.setValue(key.getValue());
			deploymentVariable.setDeployedValue(key.getDeployedValue());
		}
		List<String> instanceKeys = new ArrayList<String>();
		for (ServiceInstance instance : serviceInstances) {
			instanceKeys.add(instance.getKey());
		}
		deploymentVariable.setSelectedInstances(instanceKeys);
		Map<String, Long> variablesVersions = getDeploymentVariablesVersions(deploymentVariableType, serviceInstances);
		deploymentVariable.setVariablesVersion(variablesVersions);
		groupDeploymentVariables.add(deploymentVariable);
	}

	/**
	 * Get name value pair from given instance matching with given name value
	 * pair
	 * 
	 * @param deploymentVariables
	 *            - Deployment Variables
	 * @param key
	 *            - NameValuePair to be matched
	 * @return Matched Name Value pair or null
	 */
	public static NameValuePair getInstanceMatchedNameValuePair(DeploymentVariables deploymentVariables,
			NameValuePair key) {
		NameValuePairs nameValuePairs = deploymentVariables.getNameValuePairs();
		NameValuePair valuePair = null;
		if (null != nameValuePairs) {
			List<NameValuePair> nameValuePair = nameValuePairs.getNameValuePair();
			if (null != nameValuePair && !nameValuePair.isEmpty()) {
				for (NameValuePair nameValue : nameValuePair) {
					if (null != nameValue && nameValue.equals(key)) {
						valuePair = nameValue;
						break;
					}
				}
			}
		}
		return valuePair;
	}

	/**
	 * Load deployment variables of given type.
	 * 
	 * @param application
	 *            - Application object
	 * @param deploymentVariableType
	 *            - Type of deployment variable
	 * @param serviceInstance
	 *            - Service Instance
	 * @return Deployment variables of given type
	 */
	private static DeploymentVariables loadDeoloymentVariables(Application application,
			DeploymentVariableType deploymentVariableType, ServiceInstance serviceInstance) {
		switch (deploymentVariableType) {
		case GLOBAL_VARIABLES:
			return serviceInstance.getGlobalVariables();
		case SYSTEM_VARIABLES:
			return serviceInstance.getSystemVariables();
		case JVM_PROPERTIES:
			return serviceInstance.getJVMProperties();
		case LOG_PATTERNS:
			return serviceInstance.getLoggerPatternAndLogLevel();
		case BE_PROPERTIES:
			return serviceInstance.getBEProperties();
		default:
			return null;
		}
	}

	/**
	 * Remove the mapping if for all instance given key is deleted
	 * 
	 * @param instanceMap
	 *            - Map of name value pair and the list of service instances
	 *            which contains the name value pair in give deployment variable
	 *            type
	 * @param deploymentVariableType
	 *            - Deployment variable type
	 * @param application
	 *            - Application object
	 * @param instanceService
	 *            - Instance service
	 */
	private static void clearAllDeletedProperties(Map<NameValuePair, List<ServiceInstance>> instanceMap,
			DeploymentVariableType deploymentVariableType, Application application,
			BEServiceInstancesManagementService instanceService) {
		if (null != instanceMap) {
			List<NameValuePair> keys = new ArrayList<NameValuePair>();
			for (NameValuePair nameValuePair : instanceMap.keySet()) {
				if (null != nameValuePair) {
					List<ServiceInstance> instances = instanceMap.get(nameValuePair);
					if (null != instances) {
						int deletedCount = 0;
						for (ServiceInstance serviceInstance : instances) {
							if (null != serviceInstance) {
								DeploymentVariables deploymentVariables = null;
								deploymentVariables = loadDeoloymentVariables(application, deploymentVariableType,
										serviceInstance);

								if (null != deploymentVariables) {
									NameValuePair matchedValue = getInstanceMatchedNameValuePair(deploymentVariables,
											nameValuePair);
									if (null != matchedValue) {
										if (matchedValue.isIsDeleted()) {
											deletedCount++;
										}
									}
								}
							}
						}
						if (deletedCount == instances.size()) {
							keys.add(nameValuePair);
						}
					}
				}
			}

			for (Iterator<NameValuePair> iterator = keys.iterator(); iterator.hasNext();) {
				NameValuePair nameValuePair = iterator.next();
				if (!nameValuePair.isHasDefaultValue())
					instanceMap.remove(nameValuePair);
			}
		}
	}

	/**
	 * Get deployment variable versions
	 * 
	 * @param deploymentVariableType
	 *            - Type of deployment variable
	 * @param serviceInstances
	 *            - List of service instances
	 * @return Map of deployment variable key and version
	 */
	private static Map<String, Long> getDeploymentVariablesVersions(DeploymentVariableType deploymentVariableType,
			List<ServiceInstance> serviceInstances) {
		Map<String, Long> variablesVersions = new HashMap<>();
		switch (deploymentVariableType) {
		case GLOBAL_VARIABLES:
			for (ServiceInstance serviceInstance : serviceInstances) {
				variablesVersions.put(serviceInstance.getGlobalVariables().getKey(),
						serviceInstance.getGlobalVariables().getVersion());
			}
			break;
		case SYSTEM_VARIABLES:
			for (ServiceInstance serviceInstance : serviceInstances) {
				variablesVersions.put(serviceInstance.getSystemVariables().getKey(),
						serviceInstance.getSystemVariables().getVersion());
			}
			break;
		case JVM_PROPERTIES:
			for (ServiceInstance serviceInstance : serviceInstances) {
				variablesVersions.put(serviceInstance.getJVMProperties().getKey(),
						serviceInstance.getJVMProperties().getVersion());
			}
			break;
		case BE_PROPERTIES:
			for (ServiceInstance serviceInstance : serviceInstances) {
				variablesVersions.put(serviceInstance.getBEProperties().getKey(),
						serviceInstance.getBEProperties().getVersion());
			}
			break;
		case LOG_PATTERNS:
			for (ServiceInstance serviceInstance : serviceInstances) {
				variablesVersions.put(serviceInstance.getLoggerPatternAndLogLevel().getKey(),
						serviceInstance.getLoggerPatternAndLogLevel().getVersion());
			}
			break;
		default:
			break;
		}
		return variablesVersions;
	}
}
