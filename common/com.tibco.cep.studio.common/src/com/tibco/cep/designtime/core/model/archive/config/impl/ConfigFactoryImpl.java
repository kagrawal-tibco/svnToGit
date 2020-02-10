/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.archive.config.impl;

import com.tibco.cep.designtime.core.model.archive.config.*;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.tibco.cep.designtime.core.model.archive.config.Archives;
import com.tibco.cep.designtime.core.model.archive.config.ConfigFactory;
import com.tibco.cep.designtime.core.model.archive.config.ConfigPackage;
import com.tibco.cep.designtime.core.model.archive.config.EngineConfig;
import com.tibco.cep.designtime.core.model.archive.config.EntityResources;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ConfigFactoryImpl extends EFactoryImpl implements ConfigFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ConfigFactory init() {
		try {
			ConfigFactory theConfigFactory = (ConfigFactory)EPackage.Registry.INSTANCE.getEFactory(ConfigPackage.eNS_URI);
			if (theConfigFactory != null) {
				return theConfigFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ConfigFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConfigFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case ConfigPackage.ENGINE_CONFIG: return createEngineConfig();
			case ConfigPackage.ARCHIVES: return createArchives();
			case ConfigPackage.ENTITY_RESOURCES: return createEntityResources();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EngineConfig createEngineConfig() {
		EngineConfigImpl engineConfig = new EngineConfigImpl();
		return engineConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Archives createArchives() {
		ArchivesImpl archives = new ArchivesImpl();
		return archives;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EntityResources createEntityResources() {
		EntityResourcesImpl entityResources = new EntityResourcesImpl();
		return entityResources;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConfigPackage getConfigPackage() {
		return (ConfigPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ConfigPackage getPackage() {
		return ConfigPackage.eINSTANCE;
	}

} //ConfigFactoryImpl
