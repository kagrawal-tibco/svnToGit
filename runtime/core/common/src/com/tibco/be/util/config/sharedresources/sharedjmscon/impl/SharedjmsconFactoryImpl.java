/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.sharedjmscon.impl;

import com.tibco.be.util.config.sharedresources.sharedjmscon.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SharedjmsconFactoryImpl extends EFactoryImpl implements SharedjmsconFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SharedjmsconFactory init() {
		try {
			SharedjmsconFactory theSharedjmsconFactory = (SharedjmsconFactory)EPackage.Registry.INSTANCE.getEFactory("platform:/resource/BE/runtime/core/common/src/sharedjmscon.xsd"); 
			if (theSharedjmsconFactory != null) {
				return theSharedjmsconFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new SharedjmsconFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SharedjmsconFactoryImpl() {
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
			case SharedjmsconPackage.BW_SHARED_RESOURCE: return createBwSharedResource();
			case SharedjmsconPackage.CONFIG: return createConfig();
			case SharedjmsconPackage.CONNECTION_ATTRIBUTES: return createConnectionAttributes();
			case SharedjmsconPackage.JNDI_PROPERTIES: return createJndiProperties();
			case SharedjmsconPackage.NAMING_ENVIRONMENT: return createNamingEnvironment();
			case SharedjmsconPackage.ROW: return createRow();
			case SharedjmsconPackage.SHARED_JMS_CON_ROOT: return createSharedJmsConRoot();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case SharedjmsconPackage.RESOURCE_TYPE:
				return createResourceTypeFromString(eDataType, initialValue);
			case SharedjmsconPackage.RESOURCE_TYPE_OBJECT:
				return createResourceTypeObjectFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case SharedjmsconPackage.RESOURCE_TYPE:
				return convertResourceTypeToString(eDataType, instanceValue);
			case SharedjmsconPackage.RESOURCE_TYPE_OBJECT:
				return convertResourceTypeObjectToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BwSharedResource createBwSharedResource() {
		BwSharedResourceImpl bwSharedResource = new BwSharedResourceImpl();
		return bwSharedResource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Config createConfig() {
		ConfigImpl config = new ConfigImpl();
		return config;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConnectionAttributes createConnectionAttributes() {
		ConnectionAttributesImpl connectionAttributes = new ConnectionAttributesImpl();
		return connectionAttributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JndiProperties createJndiProperties() {
		JndiPropertiesImpl jndiProperties = new JndiPropertiesImpl();
		return jndiProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NamingEnvironment createNamingEnvironment() {
		NamingEnvironmentImpl namingEnvironment = new NamingEnvironmentImpl();
		return namingEnvironment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Row createRow() {
		RowImpl row = new RowImpl();
		return row;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SharedJmsConRoot createSharedJmsConRoot() {
		SharedJmsConRootImpl sharedJmsConRoot = new SharedJmsConRootImpl();
		return sharedJmsConRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResourceType createResourceTypeFromString(EDataType eDataType, String initialValue) {
		ResourceType result = ResourceType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertResourceTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResourceType createResourceTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createResourceTypeFromString(SharedjmsconPackage.Literals.RESOURCE_TYPE, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertResourceTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertResourceTypeToString(SharedjmsconPackage.Literals.RESOURCE_TYPE, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SharedjmsconPackage getSharedjmsconPackage() {
		return (SharedjmsconPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static SharedjmsconPackage getPackage() {
		return SharedjmsconPackage.eINSTANCE;
	}

} //SharedjmsconFactoryImpl
