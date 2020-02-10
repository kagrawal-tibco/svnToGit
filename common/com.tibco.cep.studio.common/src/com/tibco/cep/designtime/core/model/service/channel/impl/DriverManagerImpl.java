/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.service.channel.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.designtime.core.model.service.channel.ChannelDescriptor;
import com.tibco.cep.designtime.core.model.service.channel.ChannelPackage;
import com.tibco.cep.designtime.core.model.service.channel.Choice;
import com.tibco.cep.designtime.core.model.service.channel.ChoiceConfiguration;
import com.tibco.cep.designtime.core.model.service.channel.DRIVER_TYPE;
import com.tibco.cep.designtime.core.model.service.channel.DestinationDescriptor;
import com.tibco.cep.designtime.core.model.service.channel.DriverManager;
import com.tibco.cep.designtime.core.model.service.channel.DriverRegistration;
import com.tibco.cep.designtime.core.model.service.channel.SerializerConfig;
import com.tibco.cep.designtime.core.model.service.channel.SerializerInfo;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Driver Manager</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated NOT
 */
public class DriverManagerImpl extends EObjectImpl implements DriverManager {
	
	public static final String DRIVERS_FILE = "drivers.xml";
	
	private static final DocumentBuilderFactory FACTORY = DocumentBuilderFactory.newInstance();
	
	/**
	 * Maintain a map of {@linkplain DriverRegistration} objects in this manager.
	 */
	protected Map<DRIVER_TYPE, DriverRegistration> driverRegistrations = new HashMap<DRIVER_TYPE, DriverRegistration>();
	
	/**
	 * Map of project name to map of driverRegistrations
	 */
	protected Map<String, Map<DRIVER_TYPE, DriverRegistration>> projectRegistrationsMap = new HashMap<String, Map<DRIVER_TYPE, DriverRegistration>>(); 
	
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DriverManagerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ChannelPackage.Literals.DRIVER_MANAGER;
	}

	
	

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<String> getAllowedResourceTypes(DRIVER_TYPE driverType) {
		DriverRegistration driverRegistration = driverRegistrations.get(driverType);
		if (driverRegistration == null) {
			//Find a driver registration for the project
			Map<DRIVER_TYPE, DriverRegistration> projectRegistrations = getDriverRegistration();
			driverRegistration = projectRegistrations.get(driverType);
		}
		if (driverRegistration != null) {
			return driverRegistration.getResourcesAllowed();
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<SerializerInfo> getDestinationsSerializerClasses(DRIVER_TYPE driverType) {
		DriverRegistration driverRegistration = driverRegistrations.get(driverType);
		if (driverRegistration == null) {
			//Find a driver registration for the project
			Map<DRIVER_TYPE, DriverRegistration> projectRegistrations = getDriverRegistration();
			driverRegistration = projectRegistrations.get(driverType);
		}
		if (driverRegistration != null) {
			SerializerConfig serializerConfig = driverRegistration.getSerializerConfig();
			if (serializerConfig != null) {
				return serializerConfig.getSerializers();
			}
		}
		return null;
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public SerializerInfo getDefaultSerializer(DRIVER_TYPE driverType) {
		DriverRegistration driverRegistration = driverRegistrations.get(driverType);
		if (driverRegistration == null) {
			//Find a driver registration for the project
			Map<DRIVER_TYPE, DriverRegistration> projectRegistrations = getDriverRegistration();
			driverRegistration = projectRegistrations.get(driverType);
		}
		if (driverRegistration != null) {
			SerializerConfig serializerConfig = driverRegistration.getSerializerConfig();
			if (serializerConfig != null) {
				return serializerConfig.getDefaultSerializer();
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<DRIVER_TYPE> getDriverTypes() {
		BasicEList<DRIVER_TYPE> types = new BasicEList<DRIVER_TYPE>();
		Set<DRIVER_TYPE> keySet = driverRegistrations.keySet();
		types.addAll(keySet);
//		if (projectName != null) {
//			Map<DRIVER_TYPE, DriverRegistration> projectRegistration = getDriverRegistration(projectName);
//			types.addAll(projectRegistration.keySet());
//			if (customDriverManager != null) {
//				Set<DRIVER_TYPE> set = customDriverManager.updateCustomDriverTypes(projectName, projectRegistration.keySet(), driversCustomEntryMap);
//				if (set != null) {
//					types.removeAll(set);
//					if (set.size() > 0 ) {
//						for (DRIVER_TYPE type : set) {
//							if (projectRegistration.containsKey(type)) {
//								projectRegistration.remove(type);
//							}
//						}
//						projectRegistrationsMap.put(projectName, projectRegistration);
//					}
//				}
//			} 
//		}
		return types;
	}
	


	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public ChannelDescriptor getChannelDescriptor(DRIVER_TYPE driverType) {
		ChannelDescriptor channelDescriptor = null;
		DriverRegistration driverRegistration = driverRegistrations.get(driverType);
		if (driverRegistration == null) {
			//Find a driver registration for the project
			Map<DRIVER_TYPE, DriverRegistration> projectRegistrations = getDriverRegistration();
			driverRegistration = projectRegistrations.get(driverType);
		} 
		if (driverRegistration != null) {
			channelDescriptor = driverRegistration.getChannelDescriptor();
		}
		return channelDescriptor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public DestinationDescriptor getDestinationDescriptor(DRIVER_TYPE driverType) {
		DestinationDescriptor destinationDescriptor = null;
		DriverRegistration driverRegistration = driverRegistrations.get(driverType);
		if (driverRegistration == null) {
			//Find a driver registration for the project
			Map<DRIVER_TYPE, DriverRegistration> projectRegistrations = getDriverRegistration();
			driverRegistration = projectRegistrations.get(driverType);
		}
		if (driverRegistration != null) {
			destinationDescriptor = driverRegistration.getDestinationDescriptor();
		}
		return destinationDescriptor;
	}

	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<ChoiceConfiguration> getExtendedConfigurations(DRIVER_TYPE driverType) {
		EList<ChoiceConfiguration> extendedConfigurations = null;
		DriverRegistration driverRegistration = driverRegistrations.get(driverType);
		if (driverRegistration == null) {
			//Find a driver registration for the project
			Map<DRIVER_TYPE, DriverRegistration> projectRegistrations = getDriverRegistration();
			driverRegistration = projectRegistrations.get(driverType);
		}	
		if (driverRegistration != null) {
			extendedConfigurations = driverRegistration.getExtendedConfigurations();
		}
		return extendedConfigurations;
	}	
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<Choice> getExtendedConfigurationForChoice(final DRIVER_TYPE driverType) {
		DriverRegistration driverRegistration = driverRegistrations.get(driverType);
		if (driverRegistration == null) {
			//Find a driver registration for the project
			final Map<DRIVER_TYPE, DriverRegistration> projectRegistrations = getDriverRegistration();
			driverRegistration = projectRegistrations.get(driverType);
		}	
		if (driverRegistration != null) {
			final EList<Choice> choices = driverRegistration.getChoice();
			return choices;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<ChoiceConfiguration> getPropertyConfigurations(DRIVER_TYPE driverType) {
		EList<ChoiceConfiguration> choiceConfigurations = null;
		DriverRegistration driverRegistration = driverRegistrations.get(driverType);
		if (driverRegistration == null) {
			//Find a driver registration for the project
			Map<DRIVER_TYPE, DriverRegistration> projectRegistrations = getDriverRegistration();
			driverRegistration = projectRegistrations.get(driverType);
		}	
		if (driverRegistration != null) {
			choiceConfigurations = driverRegistration.getChoiceConfigurations();
		}
		return choiceConfigurations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getVersion(DRIVER_TYPE driverType) {
		String version = null;
		DriverRegistration driverRegistration = driverRegistrations.get(driverType);
		if (driverRegistration == null) {
			//Find a driver registration for the project
			Map<DRIVER_TYPE, DriverRegistration> projectRegistrations = getDriverRegistration();
			driverRegistration = projectRegistrations.get(driverType);
		}
		if (driverRegistration != null) {
			version = driverRegistration.getVersion();
		}
		return version;
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getDescription(DRIVER_TYPE driverType) {
		String description = null;
		DriverRegistration driverRegistration = driverRegistrations.get(driverType);
		if (driverRegistration == null) {
			//Find a driver registration for the project
			Map<DRIVER_TYPE, DriverRegistration> projectRegistrations = getDriverRegistration();
			driverRegistration = projectRegistrations.get(driverType);
		}
		if (driverRegistration != null) {
			description = driverRegistration.getDescription();
		}
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getLabel(DRIVER_TYPE driverType) {
		String label = null;
		DriverRegistration driverRegistration = driverRegistrations.get(driverType);
		if (driverRegistration == null) {
			//Find a driver registration for the project
			Map<DRIVER_TYPE, DriverRegistration> projectRegistrations = getDriverRegistration();
			driverRegistration = projectRegistrations.get(driverType);
		}
		if (driverRegistration != null) {
			label = driverRegistration.getLabel();
		}
		return label;
	}
	

	/**
	 * @generated NOT
	 * @param resourceName
	 * @return
	 */
	public Map<DRIVER_TYPE, DriverRegistration> getDriverRegistration() {
		return driverRegistrations;
	}
	
	/**
	 * @generated NOT
	 * @param jarDriverMap
	 */
	public void registerDrivers(Map<String,Map<DRIVER_TYPE,DriverRegistration>> jarDriverMap) {
		for (Entry<String, Map<DRIVER_TYPE, DriverRegistration>> jarEntry : jarDriverMap.entrySet()) {
			projectRegistrationsMap.put(jarEntry.getKey(), jarEntry.getValue());
			for (Entry<DRIVER_TYPE, DriverRegistration> driverEntry : jarEntry.getValue().entrySet()) {
				driverRegistrations.put(driverEntry.getKey(), driverEntry.getValue());
			}
		}
		
	}
	


	

} //DriverManagerImpl
