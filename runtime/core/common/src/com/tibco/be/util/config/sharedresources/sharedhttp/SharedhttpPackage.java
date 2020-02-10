/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.sharedhttp;

import org.eclipse.emf.ecore.EAttribute;
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
 * @see com.tibco.be.util.config.sharedresources.sharedhttp.SharedhttpFactory
 * @model kind="package"
 *        annotation="http://www.w3.org/XML/1998/namespace lang='en'"
 * @generated
 */
public interface SharedhttpPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "sharedhttp";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "www.tibco.com/shared/HTTPConnection";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "sharedhttp";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SharedhttpPackage eINSTANCE = com.tibco.be.util.config.sharedresources.sharedhttp.impl.SharedhttpPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.sharedresources.sharedhttp.impl.ConfigImpl <em>Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.sharedresources.sharedhttp.impl.ConfigImpl
	 * @see com.tibco.be.util.config.sharedresources.sharedhttp.impl.SharedhttpPackageImpl#getConfig()
	 * @generated
	 */
	int CONFIG = 0;

	/**
	 * The feature id for the '<em><b>Host</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIG__HOST = 0;

	/**
	 * The feature id for the '<em><b>Port</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIG__PORT = 1;

	/**
	 * The feature id for the '<em><b>Enable Lookups</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIG__ENABLE_LOOKUPS = 2;

	/**
	 * The feature id for the '<em><b>Use Ssl</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIG__USE_SSL = 3;

	/**
	 * The feature id for the '<em><b>Ssl</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIG__SSL = 4;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIG__DESCRIPTION = 5;

	/**
	 * The number of structural features of the '<em>Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIG_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.sharedresources.sharedhttp.impl.HttpSharedResourceImpl <em>Http Shared Resource</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.sharedresources.sharedhttp.impl.HttpSharedResourceImpl
	 * @see com.tibco.be.util.config.sharedresources.sharedhttp.impl.SharedhttpPackageImpl#getHttpSharedResource()
	 * @generated
	 */
	int HTTP_SHARED_RESOURCE = 1;

	/**
	 * The feature id for the '<em><b>Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HTTP_SHARED_RESOURCE__CONFIG = 0;

	/**
	 * The number of structural features of the '<em>Http Shared Resource</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HTTP_SHARED_RESOURCE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.sharedresources.sharedhttp.impl.SharedHttpRootImpl <em>Shared Http Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.sharedresources.sharedhttp.impl.SharedHttpRootImpl
	 * @see com.tibco.be.util.config.sharedresources.sharedhttp.impl.SharedhttpPackageImpl#getSharedHttpRoot()
	 * @generated
	 */
	int SHARED_HTTP_ROOT = 2;

	/**
	 * The feature id for the '<em><b>Mixed</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_HTTP_ROOT__MIXED = 0;

	/**
	 * The feature id for the '<em><b>XMLNS Prefix Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_HTTP_ROOT__XMLNS_PREFIX_MAP = 1;

	/**
	 * The feature id for the '<em><b>XSI Schema Location</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_HTTP_ROOT__XSI_SCHEMA_LOCATION = 2;

	/**
	 * The feature id for the '<em><b>Http Shared Resource</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_HTTP_ROOT__HTTP_SHARED_RESOURCE = 3;

	/**
	 * The number of structural features of the '<em>Shared Http Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_HTTP_ROOT_FEATURE_COUNT = 4;


	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.sharedresources.sharedhttp.Config <em>Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Config</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedhttp.Config
	 * @generated
	 */
	EClass getConfig();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.sharedhttp.Config#getHost <em>Host</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Host</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedhttp.Config#getHost()
	 * @see #getConfig()
	 * @generated
	 */
	EAttribute getConfig_Host();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.sharedhttp.Config#getPort <em>Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Port</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedhttp.Config#getPort()
	 * @see #getConfig()
	 * @generated
	 */
	EAttribute getConfig_Port();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.sharedhttp.Config#getEnableLookups <em>Enable Lookups</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Enable Lookups</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedhttp.Config#getEnableLookups()
	 * @see #getConfig()
	 * @generated
	 */
	EAttribute getConfig_EnableLookups();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.sharedhttp.Config#getUseSsl <em>Use Ssl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Use Ssl</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedhttp.Config#getUseSsl()
	 * @see #getConfig()
	 * @generated
	 */
	EAttribute getConfig_UseSsl();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.sharedresources.sharedhttp.Config#getSsl <em>Ssl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Ssl</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedhttp.Config#getSsl()
	 * @see #getConfig()
	 * @generated
	 */
	EReference getConfig_Ssl();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.sharedhttp.Config#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedhttp.Config#getDescription()
	 * @see #getConfig()
	 * @generated
	 */
	EAttribute getConfig_Description();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.sharedresources.sharedhttp.HttpSharedResource <em>Http Shared Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Http Shared Resource</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedhttp.HttpSharedResource
	 * @generated
	 */
	EClass getHttpSharedResource();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.sharedresources.sharedhttp.HttpSharedResource#getConfig <em>Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Config</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedhttp.HttpSharedResource#getConfig()
	 * @see #getHttpSharedResource()
	 * @generated
	 */
	EReference getHttpSharedResource_Config();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.sharedresources.sharedhttp.SharedHttpRoot <em>Shared Http Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Shared Http Root</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedhttp.SharedHttpRoot
	 * @generated
	 */
	EClass getSharedHttpRoot();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.util.config.sharedresources.sharedhttp.SharedHttpRoot#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedhttp.SharedHttpRoot#getMixed()
	 * @see #getSharedHttpRoot()
	 * @generated
	 */
	EAttribute getSharedHttpRoot_Mixed();

	/**
	 * Returns the meta object for the map '{@link com.tibco.be.util.config.sharedresources.sharedhttp.SharedHttpRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedhttp.SharedHttpRoot#getXMLNSPrefixMap()
	 * @see #getSharedHttpRoot()
	 * @generated
	 */
	EReference getSharedHttpRoot_XMLNSPrefixMap();

	/**
	 * Returns the meta object for the map '{@link com.tibco.be.util.config.sharedresources.sharedhttp.SharedHttpRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XSI Schema Location</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedhttp.SharedHttpRoot#getXSISchemaLocation()
	 * @see #getSharedHttpRoot()
	 * @generated
	 */
	EReference getSharedHttpRoot_XSISchemaLocation();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.sharedresources.sharedhttp.SharedHttpRoot#getHttpSharedResource <em>Http Shared Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Http Shared Resource</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedhttp.SharedHttpRoot#getHttpSharedResource()
	 * @see #getSharedHttpRoot()
	 * @generated
	 */
	EReference getSharedHttpRoot_HttpSharedResource();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SharedhttpFactory getSharedhttpFactory();

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
		 * The meta object literal for the '{@link com.tibco.be.util.config.sharedresources.sharedhttp.impl.ConfigImpl <em>Config</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.util.config.sharedresources.sharedhttp.impl.ConfigImpl
		 * @see com.tibco.be.util.config.sharedresources.sharedhttp.impl.SharedhttpPackageImpl#getConfig()
		 * @generated
		 */
		EClass CONFIG = eINSTANCE.getConfig();

		/**
		 * The meta object literal for the '<em><b>Host</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFIG__HOST = eINSTANCE.getConfig_Host();

		/**
		 * The meta object literal for the '<em><b>Port</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFIG__PORT = eINSTANCE.getConfig_Port();

		/**
		 * The meta object literal for the '<em><b>Enable Lookups</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFIG__ENABLE_LOOKUPS = eINSTANCE.getConfig_EnableLookups();

		/**
		 * The meta object literal for the '<em><b>Use Ssl</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFIG__USE_SSL = eINSTANCE.getConfig_UseSsl();

		/**
		 * The meta object literal for the '<em><b>Ssl</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONFIG__SSL = eINSTANCE.getConfig_Ssl();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFIG__DESCRIPTION = eINSTANCE.getConfig_Description();

		/**
		 * The meta object literal for the '{@link com.tibco.be.util.config.sharedresources.sharedhttp.impl.HttpSharedResourceImpl <em>Http Shared Resource</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.util.config.sharedresources.sharedhttp.impl.HttpSharedResourceImpl
		 * @see com.tibco.be.util.config.sharedresources.sharedhttp.impl.SharedhttpPackageImpl#getHttpSharedResource()
		 * @generated
		 */
		EClass HTTP_SHARED_RESOURCE = eINSTANCE.getHttpSharedResource();

		/**
		 * The meta object literal for the '<em><b>Config</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HTTP_SHARED_RESOURCE__CONFIG = eINSTANCE.getHttpSharedResource_Config();

		/**
		 * The meta object literal for the '{@link com.tibco.be.util.config.sharedresources.sharedhttp.impl.SharedHttpRootImpl <em>Shared Http Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.util.config.sharedresources.sharedhttp.impl.SharedHttpRootImpl
		 * @see com.tibco.be.util.config.sharedresources.sharedhttp.impl.SharedhttpPackageImpl#getSharedHttpRoot()
		 * @generated
		 */
		EClass SHARED_HTTP_ROOT = eINSTANCE.getSharedHttpRoot();

		/**
		 * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SHARED_HTTP_ROOT__MIXED = eINSTANCE.getSharedHttpRoot_Mixed();

		/**
		 * The meta object literal for the '<em><b>XMLNS Prefix Map</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SHARED_HTTP_ROOT__XMLNS_PREFIX_MAP = eINSTANCE.getSharedHttpRoot_XMLNSPrefixMap();

		/**
		 * The meta object literal for the '<em><b>XSI Schema Location</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SHARED_HTTP_ROOT__XSI_SCHEMA_LOCATION = eINSTANCE.getSharedHttpRoot_XSISchemaLocation();

		/**
		 * The meta object literal for the '<em><b>Http Shared Resource</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SHARED_HTTP_ROOT__HTTP_SHARED_RESOURCE = eINSTANCE.getSharedHttpRoot_HttpSharedResource();

	}

} //SharedhttpPackage
