package com.tibco.cep.bemm.management.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.model.Application;
import com.tibco.cep.bemm.model.Host;
import com.tibco.cep.bemm.model.ProcessingUnit;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.cep.bemm.model.impl.DeploymentVariableType;
import com.tibco.cep.bemm.model.impl.DeploymentVariables;
import com.tibco.cep.bemm.model.impl.NameValuePair;
import com.tibco.cep.bemm.model.impl.NameValuePairs;
import com.tibco.cep.bemm.model.impl.ObjectFactory;
import com.tibco.cep.bemm.persistence.service.BEApplicationsManagementDataStoreService;
import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.runtime.util.SystemProperty;

/**
 * Used to resolve the deployment variable values from CDD,TRA and local changes
 * 
 * @author dijadhav
 *
 */
public class DeploymentVariableUtil {

	/**
	 * Logger instance
	 */
	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(DeploymentVariableUtil.class);

	/**
	 * Resolve the deployment variable values from CDD,TRA and local changes
	 * 
	 * @param globalVariableDescriptors
	 *            - Collection of GVS
	 * @param dataStoreService
	 *            - DataStore Service
	 * @param serviceInstance
	 *            - Service instance whose properties need to resolve
	 * @param cddProperties
	 *            - CDD properties
	 * @param traProperties
	 * @param isEdit TODO
	 * @throws ObjectCreationException
	 */
	public static void initDeploymentVariables(Collection<GlobalVariableDescriptor> globalVariableDescriptors,
			BEApplicationsManagementDataStoreService<?> dataStoreService, ServiceInstance serviceInstance,
			Map<Object, Object> cddProperties, Map<String, String> traProperties, boolean isEdit) throws ObjectCreationException {
		if (null == traProperties) {
			traProperties = new HashMap<>();
		}
		LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.INVOKED_DEPLOYMENT_METHOD ,  "initDeploymentVariables"));
		// Service instance should not be null
		if (null == serviceInstance)
			return;
		// Host should not be null
		Host host = serviceInstance.getHost();
		if (null == host)
			return;

		// Application should not be null
		Application application = host.getApplication();
		if (null == application)
			return;

		// if (!serviceInstance.getDeployed()) {
			LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.INITIALIZING_DEPLOYMENT_VARIABLES_FOR_UNDEPLOYED_INSTANCES));
		// Populate the global variables
		populateGlobalVariables(globalVariableDescriptors, serviceInstance, traProperties, dataStoreService,
				cddProperties);
		// Populate BusinessEvents Properties
		populateBEProperties(serviceInstance, dataStoreService, traProperties, cddProperties);

		// Populate System Properties
		populateSystemProperties(serviceInstance, dataStoreService, traProperties, cddProperties);

		// Populate JVM Properties
		populateJVMProperties(serviceInstance, dataStoreService, traProperties);

		// Populate JVM Properties
		populateLogPatternAndLevels(serviceInstance, dataStoreService, traProperties, globalVariableDescriptors);
			LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.INITIALIZED_DEPLOYMENT_VARIABLES_FOR_UNDEPLOYED_INSTANCES));
		/*
		 * } else { LOGGER.log(Level.DEBUG,
		 * "Initializing deployment variables for deployed instances");
		 * 
		 * serviceInstance.setGlobalVariables(loadLocalDeploymentVariables(
		 * dataStoreService, serviceInstance, application,
		 * DeploymentVariableType.GLOBAL_VARIABLES));
		 * serviceInstance.setSystemVariables(loadLocalDeploymentVariables(
		 * dataStoreService, serviceInstance, application,
		 * DeploymentVariableType.SYSTEM_VARIABLES));
		 * serviceInstance.setJVMProperties(loadLocalDeploymentVariables(
		 * dataStoreService, serviceInstance, application,
		 * DeploymentVariableType.JVM_PROPERTIES));
		 * serviceInstance.setBEProperties(loadLocalDeploymentVariables(
		 * dataStoreService, serviceInstance, application,
		 * DeploymentVariableType.BE_PROPERTIES));
		 * serviceInstance.setLoggerPatternAndLogLevel(
		 * loadLocalDeploymentVariables(dataStoreService, serviceInstance,
		 * application, DeploymentVariableType.LOG_PATTERNS));
		 * LOGGER.log(Level.DEBUG,
		 * "Initialized deployment variables for deployed instances"); }
		 */
	}

	/**
	 * Populate Global variables
	 * 
	 * @param globalVariableDescriptors
	 *            - Collections of global Variables descriptor
	 * @param serviceInstance
	 *            - Service instance
	 * @param traProperties
	 *            - Map of Properties from TRA
	 * @param dataStoreService
	 *            - Data Store Service
	 * @param cddProperties
	 * @return Global Variables
	 * @throws ObjectCreationException
	 */
	public static void populateGlobalVariables(Collection<GlobalVariableDescriptor> globalVariableDescriptors,
			ServiceInstance serviceInstance, Map<String, String> traProperties,
			BEApplicationsManagementDataStoreService<?> dataStoreService, Map<Object, Object> cddProperties) throws ObjectCreationException {

		Host host = serviceInstance.getHost();
		Application application = host.getApplication();

		LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.LOADING_VARIABLES, "global variables",
				serviceInstance.getName(), host.getName(), application.getName()));

		try {

			ObjectFactory deployVariablesObjectFactory = new ObjectFactory();
			List<String> gvs = new ArrayList<String>();
			Map<String, NameValuePair> globalVariableMap = new HashMap<>();

			LOGGER.log(Level.DEBUG,
					BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.CONVERTING_GV_TO_DEPLOYMENT_VARIABLES,
					serviceInstance.getName(), host.getName(), application.getName()));

			convertEARGlobalVariablesToDeploymentVariables(globalVariableDescriptors, traProperties, cddProperties,
					deployVariablesObjectFactory, gvs, globalVariableMap);

			LOGGER.log(Level.DEBUG,
					BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.CONVERTED_GV_TO_DEPLOYMENT_VARIABLES,
					serviceInstance.getName(), host.getName(), application.getName()));

			// Get local global variables
			DeploymentVariables globalVariables = dataStoreService.fetchDeploymentVaribles(application.getName(),
					serviceInstance, DeploymentVariableType.GLOBAL_VARIABLES);

			// Resolve global variables as per following preference
			// EAR->CDD->TRA->Local Changes

			if (null == globalVariables) {
				globalVariables = deployVariablesObjectFactory.createDeploymentVariables();
			}

			NameValuePairs nameValuePairs = globalVariables.getNameValuePairs();
			if (null == nameValuePairs) {
				nameValuePairs = deployVariablesObjectFactory.createNameValuePairs();
			}

			List<NameValuePair> nameValues = nameValuePairs.getNameValuePair();

			if (null == nameValues) {
				nameValues = new ArrayList<NameValuePair>();
			}
			// Add extra gvs from CDD
			for (Entry<Object, Object> entry : cddProperties.entrySet()) {
				if (entry.getKey().toString().startsWith(Constants.GLOBAL_VARIABL_PREFIX) && !gvs
						.contains(entry.getKey().toString().replace(Constants.GLOBAL_VARIABL_PREFIX, "").trim())) {
					NameValuePair nameValuePair = deployVariablesObjectFactory.createNameValuePair();
					nameValuePair.setName(entry.getKey().toString().replace(Constants.GLOBAL_VARIABL_PREFIX, "").trim());
					nameValuePair.setDefaultValue(entry.getValue().toString());
					nameValuePair.setIsDeleted(false);
					gvs.add(entry.getKey().toString());
					nameValuePair.setHasDefaultValue(true);
					globalVariableMap.put(nameValuePair.getName(), nameValuePair);
				}
			}
			// Add extra gvs from TRA
			for (Entry<String, String> entry : traProperties.entrySet()) {
				if (entry.getKey().startsWith(Constants.GLOBAL_VARIABL_PREFIX)
						&& !gvs.contains(entry.getKey().replace(Constants.GLOBAL_VARIABL_PREFIX, "").trim())) {
					NameValuePair nameValuePair = deployVariablesObjectFactory.createNameValuePair();
					nameValuePair.setName(entry.getKey().replace(Constants.GLOBAL_VARIABL_PREFIX, "").trim());
					nameValuePair.setValue(entry.getValue());
					nameValuePair.setIsDeleted(false);
					nameValuePair.setHasDefaultValue(false);
					globalVariableMap.put(nameValuePair.getName(), nameValuePair);
				}
			}

			// If there is nothing then create new
			if (nameValues.isEmpty()) {
				nameValues.addAll(globalVariableMap.values());
			} else {
				List<NameValuePair> propertiesToBeRemoved = new ArrayList<>();
				for (NameValuePair nameValuePair2 : nameValues) {

					// If master list contains the global variable, override
					// with the config value
					if (globalVariableMap.containsKey(nameValuePair2.getName())) {
						NameValuePair nameValuePair = globalVariableMap.get(nameValuePair2.getName());
						if (null != nameValuePair) {
							if ("Password".equals(nameValuePair.getType())) {
								if (null != nameValuePair.getValue() && !nameValuePair.getValue().trim().isEmpty())
									nameValuePair
											.setDefaultValue(ManagementUtil.getEncodedPwd(nameValuePair.getValue()));
							}
							nameValuePair2.setType(nameValuePair.getType());
							// User changed the default value in CDD/TRA/EAR
							if (nameValuePair2.isHasDefaultValue())
								nameValuePair2.setDefaultValue(nameValuePair.getDefaultValue());
							else {
								// if user has manually added the property and
								// then he added same in TRA/CDD and edited
								// application then manually added property has
								// default value came from CDD/TRA/EAR
								if (nameValuePair.isHasDefaultValue()) {
									nameValuePair2.setDefaultValue(nameValuePair.getDefaultValue());
									nameValuePair2.setHasDefaultValue(true);
								}
							}
						}
						globalVariableMap.remove(nameValuePair2.getName());
					} else { // If CDD,TRA,EAR doesn't have property and
								// property has default value then remove
								// such properties from master list
						if (nameValuePair2.isHasDefaultValue()) {
							propertiesToBeRemoved.add(nameValuePair2);
						}

					}

				}
				nameValues.removeAll(propertiesToBeRemoved);
				// Add new properties
				nameValues.addAll(globalVariableMap.values());

			}

			Collections.sort(nameValues, Collections.reverseOrder(new NameValueComparatorByHasDefault()));

			globalVariables.setNameValuePairs(nameValuePairs);
			globalVariables.setType(DeploymentVariableType.GLOBAL_VARIABLES);
			globalVariables.setName(application.getName());
			if (serviceInstance.getGlobalVariables() != null) {
				globalVariables.setVersion(serviceInstance.getGlobalVariables().getVersion());
			}
			globalVariables.setKey(serviceInstance.getKey() + "/" + DeploymentVariableType.GLOBAL_VARIABLES.name());

			dataStoreService.storeDeploymentVariables(application.getName(), serviceInstance.getName(),
					globalVariables);
			serviceInstance.setGlobalVariables(globalVariables);
			LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.LOADED_VARIABLES, "global variables",
					serviceInstance.getName(), host.getName(), application.getName()));

		} catch (Exception e) {
			LOGGER.log(Level.DEBUG, e, e.getMessage());
		}

	}

	/**
	 * Converts Global Variables from EAR to deployment variables
	 * 
	 * @param globalVariableDescriptors
	 *            - Collection of global variables from EAR
	 * @param traProperties
	 *            - TRA Properties
	 * @param cddProperties
	 *            -
	 * @param deployVariablesObjectFactory
	 * @param gvs
	 *            - List of global variable names which are in EAR
	 * @param globalVariableMap
	 *            - Map of Deployment variable name as key and Deployment
	 *            variable instance as value
	 */
	private static void convertEARGlobalVariablesToDeploymentVariables(
			Collection<GlobalVariableDescriptor> globalVariableDescriptors, Map<String, String> traProperties,
			Map<Object, Object> cddProperties, ObjectFactory deployVariablesObjectFactory, List<String> gvs,
			Map<String, NameValuePair> globalVariableMap) {
		if (null != globalVariableDescriptors) {
			for (GlobalVariableDescriptor globalVariableDescriptor : globalVariableDescriptors) {
				NameValuePair nameValuePair = deployVariablesObjectFactory.createNameValuePair();
				nameValuePair.setName((String) globalVariableDescriptor.getFullName());
				nameValuePair.setDescription(globalVariableDescriptor.getDescription());
				nameValuePair.setHasDefaultValue(true);
				nameValuePair.setType(globalVariableDescriptor.getType());
				String overriddenValue = null;
				Object cddValue = cddProperties.get(Constants.GLOBAL_VARIABL_PREFIX + nameValuePair.getName());
				if (null != cddValue && !cddValue.toString().trim().isEmpty()) {
					overriddenValue = cddValue.toString();
				}

				if (traProperties.containsKey(Constants.GLOBAL_VARIABL_PREFIX + nameValuePair.getName())) {
					String traValue = traProperties.get(Constants.GLOBAL_VARIABL_PREFIX + nameValuePair.getName());
					if (null != traValue && !traValue.trim().isEmpty()) {
						overriddenValue = traValue;
					}
				}
				if ("Password".equals(nameValuePair.getType())) {
					if (null != overriddenValue && !overriddenValue.trim().isEmpty()) {
						overriddenValue = ManagementUtil.getEncodedPwd(overriddenValue);
					}
				}

				if (null != overriddenValue && !overriddenValue.trim().isEmpty()) {
					nameValuePair.setDefaultValue(overriddenValue);
				} else {
					nameValuePair.setDefaultValue((String) globalVariableDescriptor.getValueAsString());
				}
				gvs.add(nameValuePair.getName());
				nameValuePair.setValue(null);
				nameValuePair.setIsDeleted(false);

				globalVariableMap.put(nameValuePair.getName(), nameValuePair);
			}
		}
	}

	/**
	 * Populates BusinessEvents Properties
	 * 
	 * @param application
	 *            - Application instance
	 * @param serviceInstance
	 *            - Service Instance
	 * @param dataStoreService
	 *            - Data Source Service
	 * @param traProps
	 *            - TRA Properties
	 * @param cddProps
	 *            -CDD Properties
	 * @throws Exception
	 */
	public static void populateBEProperties(ServiceInstance serviceInstance,
			BEApplicationsManagementDataStoreService<?> dataStoreService, Map<String, String> traProps,
			Map<Object, Object> cddProps) {
		try {

			Host host = serviceInstance.getHost();
			Application application = host.getApplication();

			LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.LOADING_VARIABLES, "BE properties",
					serviceInstance.getName(), host.getName(), application.getName()));

			ObjectFactory deployVariablesObjectFactory = new ObjectFactory();
			DeploymentVariables beProperties = dataStoreService.fetchDeploymentVaribles(application.getName(),
					serviceInstance, DeploymentVariableType.BE_PROPERTIES);
			if (null == beProperties) {
				beProperties = deployVariablesObjectFactory.createDeploymentVariables();
			}

			NameValuePairs nameValuePairs = beProperties.getNameValuePairs();
			if (null == nameValuePairs) {
				nameValuePairs = deployVariablesObjectFactory.createNameValuePairs();
			}

			List<NameValuePair> nameValues = nameValuePairs.getNameValuePair();

			if (null == nameValues) {
				nameValues = new ArrayList<NameValuePair>();
			}
			beProperties.setName(serviceInstance.getName());
			beProperties.setType(DeploymentVariableType.BE_PROPERTIES);
			beProperties.setNameValuePairs(nameValuePairs);

			// Excluded Properties from TRA
			Set<String> excludedProps = dataStoreService.getExcludedProperties();

			// Load existing BE Properties
			List<String> cddBEProps = new ArrayList<String>();
			Map<String, NameValuePair> beProperyMap = new HashMap<>();

			// Load PU properties
			ProcessingUnit processingUnit = application.getProcessingUnit(serviceInstance.getPuId());
			if (null != processingUnit) {
				Map<String, String> properties = processingUnit.getProperties();
				if (null != properties && !properties.isEmpty()) {
					for (Entry<String, String> entry : properties.entrySet()) {
						if (!(entry.getKey().startsWith(Constants.GLOBAL_VARIABL_PREFIX)
								|| entry.getKey().startsWith(Constants.SYSTEM_PROPERTY_PREFIX)
								|| entry.getKey().startsWith(Constants.TIBCO_ENV_VARIABLE_PREFIX))
								&& !excludedProps.contains(
										entry.getKey().replace(Constants.SYSTEM_PROPERTY_PREFIX, "").trim())) {

							// Create Property if not existing
							NameValuePair nameValuePair = deployVariablesObjectFactory.createNameValuePair();
							nameValuePair.setName(entry.getKey());
							nameValuePair.setHasDefaultValue(true);
							cddBEProps.add(nameValuePair.getName());
							String overriddenValue = entry.getValue();

							// Overridden value is from TRA
							if (traProps.containsKey(nameValuePair.getName())) {
								String traValue = traProps.get(nameValuePair.getName());
								if (null != traValue && !traValue.trim().isEmpty()) {
									overriddenValue = traValue;
								}
							}
							nameValuePair.setDefaultValue(overriddenValue);
							nameValuePair.setDeployedValue(overriddenValue);
							beProperyMap.put(nameValuePair.getName(), nameValuePair);

						}
					}
				}
			}

			// Add Properties defined only on CDD at cluster level
			for (Entry<Object, Object> entry : cddProps.entrySet()) {

				if (!(entry.getKey().toString().startsWith(Constants.GLOBAL_VARIABL_PREFIX)
						|| entry.getKey().toString().startsWith(Constants.SYSTEM_PROPERTY_PREFIX)
						|| entry.getKey().toString().startsWith(Constants.TIBCO_ENV_VARIABLE_PREFIX))
						&& !cddBEProps.contains(entry.getKey()) && !excludedProps.contains(
								entry.getKey().toString().replace(Constants.SYSTEM_PROPERTY_PREFIX, "").trim())) {
					NameValuePair nameValuePair = new NameValuePair();
					nameValuePair.setName(entry.getKey().toString());
					nameValuePair.setHasDefaultValue(true);
					String overriddenValue = entry.getValue().toString();

					if (traProps.containsKey(nameValuePair.getName())) {
						String traValue = traProps.get(nameValuePair.getName());
						if (null != traValue && !traValue.trim().isEmpty()) {
							overriddenValue = traValue;
						}
					}
					nameValuePair.setDefaultValue(overriddenValue);
					nameValuePair.setDeployedValue(overriddenValue);
					beProperyMap.put(nameValuePair.getName(), nameValuePair);
				}
			}
			// Add Properties defined only on TRA
			for (Entry<String, String> entry : traProps.entrySet()) {

				if (!(entry.getKey().startsWith(Constants.GLOBAL_VARIABL_PREFIX)
						|| entry.getKey().startsWith(Constants.SYSTEM_PROPERTY_PREFIX)
						|| entry.getKey().startsWith(Constants.TIBCO_ENV_VARIABLE_PREFIX))
						&& !cddBEProps.contains(entry.getKey()) && !excludedProps
								.contains(entry.getKey().replace(Constants.SYSTEM_PROPERTY_PREFIX, "").trim())) {
					NameValuePair nameValuePair = new NameValuePair();
					nameValuePair.setName(entry.getKey());
					nameValuePair.setHasDefaultValue(false);
					nameValuePair.setValue(entry.getValue());
					cddBEProps.add(nameValuePair.getName());
					beProperyMap.put(nameValuePair.getName(), nameValuePair);
				}
			}

			if (nameValues.isEmpty()) {
				nameValues.addAll(beProperyMap.values());
			} else {
				List<NameValuePair> propertiesToBeRemoved = new ArrayList<>();
				// Mark the config one as deleted
				for (NameValuePair nameValuePair2 : nameValues) {

					if (!beProperyMap.containsKey(nameValuePair2.getName())) {
						// Remove only those properties which has default value
						// and those are not yet exist in CDD,TRA
						if (nameValuePair2.isHasDefaultValue())
							propertiesToBeRemoved.add(nameValuePair2);

					} else {
						NameValuePair nameValuePair = beProperyMap.get(nameValuePair2.getName());
						if (null != nameValuePair) {
							// User changed the default value in CDD/TRA
							if (nameValuePair2.isHasDefaultValue()){
								nameValuePair2.setDefaultValue(nameValuePair.getDefaultValue());
								nameValuePair2.setDeployedValue(nameValuePair.getDeployedValue());
							}  
							else {
								// if user has manually added the property and
								// then he added same in TRA/CDD and edited
								// application then manually added property has
								// default value came from cdd
								if (nameValuePair.isHasDefaultValue()) {
									nameValuePair2.setDefaultValue(nameValuePair.getDefaultValue());
									nameValuePair2.setHasDefaultValue(true);
								}
							}
						}
						beProperyMap.remove(nameValuePair2.getName());
					}

				}
				nameValues.removeAll(propertiesToBeRemoved);
				// Add new properties
				nameValues.addAll(beProperyMap.values());
			}
			if (serviceInstance.getBEProperties() != null) {
				beProperties.setVersion(serviceInstance.getBEProperties().getVersion());
			}
			beProperties.setKey(serviceInstance.getKey() + "/" + DeploymentVariableType.BE_PROPERTIES.name());

			Collections.sort(nameValues, Collections.reverseOrder(new NameValueComparatorByHasDefault()));
			dataStoreService.storeDeploymentVariables(application.getName(), serviceInstance.getName(), beProperties);
			serviceInstance.setBEProperties(beProperties);
			LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.LOADED_VARIABLES, "BE properties",
					serviceInstance.getName(), host.getName(), application.getName()));
		} catch (Exception e) {
			LOGGER.log(Level.DEBUG, e, e.getMessage());
		}
	}

	/**
	 * Populates System Properties
	 * 
	 * @param application
	 *            - Application instance
	 * @param serviceInstance
	 *            - Service Instance
	 * @param dataStoreService
	 *            - Data Source Service
	 * @param traProps
	 *            - TRA Properties
	 * @param cddProps
	 *            -CDD Properties
	 * @throws Exception
	 */
	public static void populateSystemProperties(ServiceInstance serviceInstance,
			BEApplicationsManagementDataStoreService<?> dataStoreService, Map<String, String> traProps,
			Map<Object, Object> cddProps) {
		try {

			Host host = serviceInstance.getHost();
			Application application = host.getApplication();

			LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.LOADING_VARIABLES, "system properties",
					serviceInstance.getName(), host.getName(), application.getName()));

			ObjectFactory deployVariablesObjectFactory = new ObjectFactory();
			DeploymentVariables systemProperties = dataStoreService.fetchDeploymentVaribles(application.getName(),
					serviceInstance, DeploymentVariableType.SYSTEM_VARIABLES);
			if (null == systemProperties) {
				systemProperties = deployVariablesObjectFactory.createDeploymentVariables();
			}

			NameValuePairs nameValuePairs = systemProperties.getNameValuePairs();
			if (null == nameValuePairs) {
				nameValuePairs = deployVariablesObjectFactory.createNameValuePairs();
			}

			List<NameValuePair> nameValues = nameValuePairs.getNameValuePair();

			if (null == nameValues) {
				nameValues = new ArrayList<NameValuePair>();
			}

			// Excluded Properties from TRA
			Set<String> excludedProps = dataStoreService.getExcludedProperties();

			// Load existing BE Properties
			List<String> cddSystemProps = new ArrayList<String>();
			Map<String, NameValuePair> systemProperyMap = new HashMap<>();

			// Load PU properties
			ProcessingUnit processingUnit = application.getProcessingUnit(serviceInstance.getPuId());
			if (null != processingUnit) {
				Map<String, String> properties = processingUnit.getProperties();
				if (null != properties && !properties.isEmpty()) {
					for (Entry<String, String> entry : properties.entrySet()) {
						if ((entry.getKey().startsWith(Constants.SYSTEM_PROPERTY_PREFIX)) && !excludedProps
								.contains(entry.getKey().replace(Constants.SYSTEM_PROPERTY_PREFIX, "").trim())) {

							// Create Property if not existing
							NameValuePair nameValuePair = deployVariablesObjectFactory.createNameValuePair();
							nameValuePair.setName(entry.getKey().replace(Constants.SYSTEM_PROPERTY_PREFIX, "").trim());
							nameValuePair.setHasDefaultValue(true);
							cddSystemProps.add(nameValuePair.getName());
							String overriddenValue = entry.getValue();

							// Overridden value is from TRA
							if (traProps.containsKey(Constants.SYSTEM_PROPERTY_PREFIX + nameValuePair.getName())) {
								String traValue = traProps.get(Constants.SYSTEM_PROPERTY_PREFIX+nameValuePair.getName());
								if (null != traValue && !traValue.trim().isEmpty()) {
									overriddenValue = traValue;
								}
							}
							nameValuePair.setDefaultValue(overriddenValue);
							systemProperyMap.put(nameValuePair.getName(), nameValuePair);

						}
					}
				}
			}

			// Add Properties defined only on CDD at cluster level
			for (Entry<Object, Object> entry : cddProps.entrySet()) {

				if (entry.getKey().toString().startsWith(Constants.SYSTEM_PROPERTY_PREFIX)
						&& !cddSystemProps.contains(entry.getKey()) && !excludedProps.contains(
								entry.getKey().toString().replace(Constants.SYSTEM_PROPERTY_PREFIX, "").trim())) {
					NameValuePair nameValuePair = new NameValuePair();
					nameValuePair.setName(entry.getKey().toString().replace(Constants.SYSTEM_PROPERTY_PREFIX, "").trim());
					nameValuePair.setHasDefaultValue(true);
					String overriddenValue = entry.getValue().toString();

					if (traProps.containsKey(Constants.SYSTEM_PROPERTY_PREFIX + nameValuePair.getName())) {
						String traValue = traProps.get(Constants.SYSTEM_PROPERTY_PREFIX + nameValuePair.getName());
						if (null != traValue && !traValue.trim().isEmpty()) {
							overriddenValue = traValue;
						}
					}
					nameValuePair.setDefaultValue(overriddenValue);
					systemProperyMap.put(nameValuePair.getName(), nameValuePair);
				}
			}
			// Add Properties defined only on TRA
			for (Entry<String, String> entry : traProps.entrySet()) {
				if (entry.getKey().startsWith(Constants.SYSTEM_PROPERTY_PREFIX)
						&& !cddSystemProps.contains(entry.getKey()) && !excludedProps
								.contains(entry.getKey().replace(Constants.SYSTEM_PROPERTY_PREFIX, "").trim())) {
					NameValuePair nameValuePair = new NameValuePair();
					nameValuePair.setName(entry.getKey().replace(Constants.SYSTEM_PROPERTY_PREFIX, "").trim());
					nameValuePair.setHasDefaultValue(false);
					nameValuePair.setValue(entry.getValue());
					systemProperyMap.put(nameValuePair.getName(), nameValuePair);
				}
			}

			if (nameValues.isEmpty()) {
				nameValues.addAll(systemProperyMap.values());
			} else {
				List<NameValuePair> propertiesToBeRemoved = new ArrayList<>();
				for (NameValuePair nameValuePair2 : nameValues) {

					if (!systemProperyMap.containsKey(nameValuePair2.getName())) {
						// Remove only those properties which has default value
						// and those are not yet exist in CDD,TRA
						if (nameValuePair2.isHasDefaultValue())
							propertiesToBeRemoved.add(nameValuePair2);

					} else {
						NameValuePair nameValuePair = systemProperyMap.get(nameValuePair2.getName());
						if (null != nameValuePair) {
							// User changed the default value in CDD/TRA
							if (nameValuePair2.isHasDefaultValue())
								nameValuePair2.setDefaultValue(nameValuePair.getDefaultValue());
							else {
								// if user has manually added the property and
								// then he added same in TRA/CDD and edited
								// application then manually added property has
								// default value came from CDD/TRA
								if (nameValuePair.isHasDefaultValue()) {
									nameValuePair2.setDefaultValue(nameValuePair.getDefaultValue());
									nameValuePair2.setHasDefaultValue(true);
								}
							}
						}
						systemProperyMap.remove(nameValuePair2.getName());
					}
				}
				nameValues.removeAll(propertiesToBeRemoved);
				// Add New Properties
				nameValues.addAll(systemProperyMap.values());
			}
			systemProperties.setName(serviceInstance.getName());
			systemProperties.setType(DeploymentVariableType.SYSTEM_VARIABLES);
			systemProperties.setNameValuePairs(nameValuePairs);
			if (serviceInstance.getSystemVariables() != null) {
				systemProperties.setVersion(serviceInstance.getSystemVariables().getVersion());
			}
			systemProperties.setKey(serviceInstance.getKey() + "/" + DeploymentVariableType.SYSTEM_VARIABLES.name());
			Collections.sort(nameValues, Collections.reverseOrder(new NameValueComparatorByHasDefault()));
			dataStoreService.storeDeploymentVariables(application.getName(), serviceInstance.getName(),
					systemProperties);
			serviceInstance.setSystemVariables(systemProperties);
			LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.LOADED_VARIABLES, "system properties",
					serviceInstance.getName(), host.getName(), application.getName()));
		} catch (Exception e) {
			LOGGER.log(Level.DEBUG, e, e.getMessage());
		}
	}

	public static void populateJVMProperties(ServiceInstance serviceInstance,
			BEApplicationsManagementDataStoreService<?> dataStoreService, Map<String, String> traProperties) {
		Host host = serviceInstance.getHost();
		Application application = host.getApplication();
		try {
			LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.LOADING_VARIABLES ,"jvm properties",
					serviceInstance.getName(), host.getName(), application.getName()));
		} catch (ObjectCreationException e1) {
			e1.printStackTrace();
		}
		String extendedJavaProperties = traProperties.get("java.extended.properties");
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
		String intialHeap = traProperties.get(Constants.TRA_PROP_JAVA_HEAP_SIZE_INITIAL);
		String maxHeap = traProperties.get(Constants.TRA_PROP_JAVA_HEAP_SIZE_MAX);
		if (null == intialHeap || intialHeap.trim().isEmpty() && (null != xms && !xms.trim().isEmpty())) {
			intialHeap = xms;
		}
		if (null == maxHeap || maxHeap.trim().isEmpty() && (null != xmx && !xmx.trim().isEmpty())) {
			maxHeap = xmx;
		}

		Map<String, NameValuePair> nameValuePairMap = new HashMap<String, NameValuePair>();
		ObjectFactory deployVariablesObjectFactory = new ObjectFactory();
		NameValuePair nameValuePair1 = deployVariablesObjectFactory.createNameValuePair();
		nameValuePair1.setName(Constants.TRA_PROP_JAVA_HEAP_SIZE_INITIAL);
		nameValuePair1.setDescription("Initial Heap Size");
		nameValuePair1.setDefaultValue(null);
		nameValuePair1.setValue(intialHeap);
		if (serviceInstance.getDeployed())
			nameValuePair1.setDeployedValue(intialHeap);
		nameValuePair1.setIsDeleted(false);
		nameValuePairMap.put(nameValuePair1.getName(), nameValuePair1);

		NameValuePair nameValuePair2 = deployVariablesObjectFactory.createNameValuePair();
		nameValuePair2.setName(Constants.TRA_PROP_JAVA_HEAP_SIZE_MAX);
		nameValuePair2.setDescription("Max Heap Size");
		nameValuePair2.setDefaultValue(null);
		nameValuePair2.setValue(maxHeap);
		if (serviceInstance.getDeployed())
			nameValuePair2.setDeployedValue(maxHeap);
		nameValuePair2.setIsDeleted(false);
		nameValuePairMap.put(nameValuePair2.getName(), nameValuePair2);

		try {
			DeploymentVariables configJVMProperties = dataStoreService.fetchDeploymentVaribles(application.getName(),
					serviceInstance, DeploymentVariableType.JVM_PROPERTIES);

			if (null == configJVMProperties) {
				configJVMProperties = deployVariablesObjectFactory.createDeploymentVariables();
			}
			NameValuePairs nameValuePairs = configJVMProperties.getNameValuePairs();
			if (null == nameValuePairs) {
				nameValuePairs = deployVariablesObjectFactory.createNameValuePairs();
			}

			List<NameValuePair> nameValues = nameValuePairs.getNameValuePair();

			if (null == nameValues) {
				nameValues = new ArrayList<NameValuePair>();
			}

			if (nameValues.isEmpty()) {
				nameValues.addAll(nameValuePairMap.values());
			}
			configJVMProperties.setName(application.getName());
			configJVMProperties.setType(DeploymentVariableType.JVM_PROPERTIES);
			configJVMProperties.setNameValuePairs(nameValuePairs);
			if (serviceInstance.getJVMProperties() != null) {
				configJVMProperties.setVersion(serviceInstance.getJVMProperties().getVersion());
			}
			configJVMProperties.setKey(serviceInstance.getKey() + "/" + DeploymentVariableType.JVM_PROPERTIES.name());
			dataStoreService.storeDeploymentVariables(application.getName(), serviceInstance.getName(),
					configJVMProperties);
			serviceInstance.setJVMProperties(configJVMProperties);

			LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.LOADED_VARIABLES ,"jvm properties",
					serviceInstance.getName(), host.getName(), application.getName()));
		} catch (Exception e) {
			LOGGER.log(Level.DEBUG, e, e.getMessage());
		}

	}

	public static void populateLogPatternAndLevels(ServiceInstance serviceInstance,
			BEApplicationsManagementDataStoreService<?> dataStoreService, Map<String, String> traProperties, Collection<GlobalVariableDescriptor> globalVariableDescriptors) {
		Host host = serviceInstance.getHost();
		Application application = host.getApplication();
		try {
			LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.LOADING_VARIABLES ,"log pattern and level",
					serviceInstance.getName(), host.getName(), application.getName()));
		} catch (ObjectCreationException e1) {
			e1.printStackTrace();
		}

		ProcessingUnit processingUnit = application.getProcessingUnit(serviceInstance.getPuId());

		String logConfig = processingUnit.getLogConfig();
		
		List<NameValuePair> nameValues = new ArrayList<>();
		populateLogPatternAndLevel(resolveGV(globalVariableDescriptors, logConfig), nameValues);

		logConfig = traProperties.get(Constants.SYSTEM_PROPERTY_PREFIX + SystemProperty.TRACE_ROLES.getPropertyName());
		populateLogPatternAndLevel(resolveGV(globalVariableDescriptors, logConfig), nameValues);
		try {

			DeploymentVariables deploymentVariables = dataStoreService.fetchDeploymentVaribles(application.getName(),
					serviceInstance, DeploymentVariableType.LOG_PATTERNS);
			ObjectFactory deployVariablesObjectFactory = new ObjectFactory();

			if (null == deploymentVariables) {
				deploymentVariables = deployVariablesObjectFactory.createDeploymentVariables();
			}
			NameValuePairs nameValuePairs = deploymentVariables.getNameValuePairs();
			if (null == nameValuePairs) {
				nameValuePairs = deployVariablesObjectFactory.createNameValuePairs();
			}

			List<NameValuePair> nameValueList = nameValuePairs.getNameValuePair();

			if (null == nameValueList) {
				nameValueList = new ArrayList<NameValuePair>();
			}
			if (nameValueList.isEmpty()) {
				nameValueList.addAll(nameValues);
			} else {

				// Remove which are deleted
				for (NameValuePair nameValuePair : nameValueList) {
					int index = nameValues.lastIndexOf(nameValuePair);
					if (index < 0) {
						if (!nameValuePair.isIsDeleted())
							if (nameValuePair.isHasDefaultValue())
								nameValuePair.setIsDeleted(false);
					}
					nameValues.remove(index);
				}
				// Add New Pattern
				nameValueList.addAll(nameValues);

			}

			deploymentVariables.setName(serviceInstance.getName());
			deploymentVariables.setType(DeploymentVariableType.LOG_PATTERNS);
			deploymentVariables.setNameValuePairs(nameValuePairs);
			if (serviceInstance.getLoggerPatternAndLogLevel() != null) {
				deploymentVariables.setVersion(serviceInstance.getLoggerPatternAndLogLevel().getVersion());
			}
			deploymentVariables.setKey(serviceInstance.getKey() + "/" + DeploymentVariableType.LOG_PATTERNS.name());

			// instanceService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			// instanceService.getLockManager().checkVersion(serviceInstance.getLoggerPatternAndLogLevel(),
			// deploymentVariables.getVersion());

			dataStoreService.storeDeploymentVariables(application.getName(), serviceInstance.getName(),
					deploymentVariables);
			// instanceService.getLockManager().incrementVersion(serviceInstance.getLoggerPatternAndLogLevel());
			serviceInstance.setLoggerPatternAndLogLevel(deploymentVariables);

			LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.LOADED_VARIABLES , "log pattern and level",
					serviceInstance.getName(), host.getName(), application.getName()));
		} catch (Exception e) {
			LOGGER.log(Level.DEBUG, e.getMessage());
		}

	}
	
	private static String resolveGV(Collection<GlobalVariableDescriptor> globalVariableDescriptors, String property){
		if(property!=null && property.contains("%%")){
			for(GlobalVariableDescriptor des : globalVariableDescriptors ) {
				String name =des.getPath()+""+ des.getName();
				property = property.replaceAll("%", "");
				if(name.equals(property)){
					property = des.getValueAsString();
				}
			}
		}
		return property;
	}

	/**
	 * Populate Log pattern and level
	 * 
	 * @param logConfig
	 *            - Log Config
	 * @param nameValues
	 *            - List of name value pairs
	 */
	private static void populateLogPatternAndLevel(String logConfig, List<NameValuePair> nameValues) {
		if (null != logConfig && !logConfig.trim().isEmpty()) {

			String[] logConfigs = logConfig.split(" ");
			if (null != logConfigs) {
				for (String logs : logConfigs) {
					if (null != logs && !logs.trim().isEmpty()) {
						String[] logPatternsAndLevel = logConfig.split(":");
						NameValuePair pattern = new NameValuePair();
						pattern.setName(String.valueOf(logPatternsAndLevel[0].trim()));
						pattern.setValue(String.valueOf(logPatternsAndLevel[1].trim()));
						if (!contains(nameValues, pattern)) {
							nameValues.add(pattern);
						}
					}
				}
			}

		}
	}

	/**
	 * Check name value pair is already exist or not
	 * 
	 * @param nameValues
	 *            - List of name value pairs
	 * @param pattern
	 *            - Name Value pair
	 * @return True/False
	 */
	private static boolean contains(List<NameValuePair> nameValues, NameValuePair pattern) {
		for (NameValuePair nameValuePair : nameValues) {

			if (null != nameValuePair.getName() && null != pattern.getName() && null != nameValuePair.getValue()
					&& null != pattern.getValue() && nameValuePair.getName().trim().equalsIgnoreCase(pattern.getName())
					&& nameValuePair.getValue().trim().equalsIgnoreCase(pattern.getValue())) {
				return true;
			}
		}
		return false;
	}

	private static final class NameValueComparatorByHasDefault implements Comparator<NameValuePair> {
		@Override
		public int compare(NameValuePair o1, NameValuePair o2) {
			boolean b1 = o1.isHasDefaultValue();
			boolean b2 = o2.isHasDefaultValue();
			return Boolean.compare(b1, b2);
		}
	}

	/**
	 * Load deployment variables from local file system for deployed instance
	 * 
	 * @param dataStoreService
	 * @param serviceInstance
	 * @param application
	 */
	public static DeploymentVariables loadLocalDeploymentVariables(
			BEApplicationsManagementDataStoreService<?> dataStoreService, ServiceInstance serviceInstance,
			Application application, DeploymentVariableType type) {
		try {
			DeploymentVariables deploymentVariables = dataStoreService.fetchDeploymentVaribles(application.getName(),
					serviceInstance, type);
			if (null != deploymentVariables) {
				NameValuePairs nameValuePairs = deploymentVariables.getNameValuePairs();
				if (null != nameValuePairs) {
					List<NameValuePair> nameValues = nameValuePairs.getNameValuePair();
					if (null != nameValues && !nameValues.isEmpty())
						Collections.sort(nameValues, Collections.reverseOrder(new NameValueComparatorByHasDefault()));

				}
			}

			return deploymentVariables;
		} catch (Exception e) {
			LOGGER.log(Level.DEBUG, e.getMessage());
		}
		return null;
	}
}
