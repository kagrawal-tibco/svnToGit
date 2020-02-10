/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.archive.config;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.tibco.cep.designtime.core.model.archive.config.ConfigFactory
 * @model kind="package"
 * @generated
 */
public interface ConfigPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "config";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http:///com/tibco/cep/designtime/core/model/archive/config";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "config";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ConfigPackage eINSTANCE = com.tibco.cep.designtime.core.model.archive.config.impl.ConfigPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.archive.config.impl.EngineConfigImpl <em>Engine Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.archive.config.impl.EngineConfigImpl
	 * @see com.tibco.cep.designtime.core.model.archive.config.impl.ConfigPackageImpl#getEngineConfig()
	 * @generated
	 */
	int ENGINE_CONFIG = 0;

	/**
	 * The feature id for the '<em><b>Archives</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENGINE_CONFIG__ARCHIVES = 0;

	/**
	 * The feature id for the '<em><b>Entity Resources</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENGINE_CONFIG__ENTITY_RESOURCES = 1;

	/**
	 * The number of structural features of the '<em>Engine Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENGINE_CONFIG_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.archive.config.impl.ArchivesImpl <em>Archives</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.archive.config.impl.ArchivesImpl
	 * @see com.tibco.cep.designtime.core.model.archive.config.impl.ConfigPackageImpl#getArchives()
	 * @generated
	 */
	int ARCHIVES = 1;

	/**
	 * The feature id for the '<em><b>Be Archive</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARCHIVES__BE_ARCHIVE = 0;

	/**
	 * The number of structural features of the '<em>Archives</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARCHIVES_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.archive.config.impl.EntityResourcesImpl <em>Entity Resources</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.archive.config.impl.EntityResourcesImpl
	 * @see com.tibco.cep.designtime.core.model.archive.config.impl.ConfigPackageImpl#getEntityResources()
	 * @generated
	 */
	int ENTITY_RESOURCES = 2;

	/**
	 * The feature id for the '<em><b>Entity</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_RESOURCES__ENTITY = 0;

	/**
	 * The number of structural features of the '<em>Entity Resources</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_RESOURCES_FEATURE_COUNT = 1;


	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.archive.config.EngineConfig <em>Engine Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Engine Config</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.config.EngineConfig
	 * @generated
	 */
	EClass getEngineConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.archive.config.EngineConfig#getArchives <em>Archives</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Archives</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.config.EngineConfig#getArchives()
	 * @see #getEngineConfig()
	 * @generated
	 */
	EReference getEngineConfig_Archives();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.archive.config.EngineConfig#getEntityResources <em>Entity Resources</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Entity Resources</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.config.EngineConfig#getEntityResources()
	 * @see #getEngineConfig()
	 * @generated
	 */
	EReference getEngineConfig_EntityResources();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.archive.config.Archives <em>Archives</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Archives</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.config.Archives
	 * @generated
	 */
	EClass getArchives();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.archive.config.Archives#getBeArchive <em>Be Archive</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Be Archive</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.config.Archives#getBeArchive()
	 * @see #getArchives()
	 * @generated
	 */
	EReference getArchives_BeArchive();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.archive.config.EntityResources <em>Entity Resources</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Entity Resources</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.config.EntityResources
	 * @generated
	 */
	EClass getEntityResources();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.archive.config.EntityResources#getEntity <em>Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Entity</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.config.EntityResources#getEntity()
	 * @see #getEntityResources()
	 * @generated
	 */
	EReference getEntityResources_Entity();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ConfigFactory getConfigFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.archive.config.impl.EngineConfigImpl <em>Engine Config</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.archive.config.impl.EngineConfigImpl
		 * @see com.tibco.cep.designtime.core.model.archive.config.impl.ConfigPackageImpl#getEngineConfig()
		 * @generated
		 */
		EClass ENGINE_CONFIG = eINSTANCE.getEngineConfig();

		/**
		 * The meta object literal for the '<em><b>Archives</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENGINE_CONFIG__ARCHIVES = eINSTANCE.getEngineConfig_Archives();

		/**
		 * The meta object literal for the '<em><b>Entity Resources</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENGINE_CONFIG__ENTITY_RESOURCES = eINSTANCE.getEngineConfig_EntityResources();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.archive.config.impl.ArchivesImpl <em>Archives</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.archive.config.impl.ArchivesImpl
		 * @see com.tibco.cep.designtime.core.model.archive.config.impl.ConfigPackageImpl#getArchives()
		 * @generated
		 */
		EClass ARCHIVES = eINSTANCE.getArchives();

		/**
		 * The meta object literal for the '<em><b>Be Archive</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ARCHIVES__BE_ARCHIVE = eINSTANCE.getArchives_BeArchive();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.archive.config.impl.EntityResourcesImpl <em>Entity Resources</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.archive.config.impl.EntityResourcesImpl
		 * @see com.tibco.cep.designtime.core.model.archive.config.impl.ConfigPackageImpl#getEntityResources()
		 * @generated
		 */
		EClass ENTITY_RESOURCES = eINSTANCE.getEntityResources();

		/**
		 * The meta object literal for the '<em><b>Entity</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENTITY_RESOURCES__ENTITY = eINSTANCE.getEntityResources_Entity();

	}

} //ConfigPackage
