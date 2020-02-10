/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl;

import com.tibco.be.util.config.sharedresources.sharedjndiconfig.*;

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
public class SharedjndiconfigFactoryImpl extends EFactoryImpl implements SharedjndiconfigFactory {
	/**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public static SharedjndiconfigFactory init() {
        try
        {
            SharedjndiconfigFactory theSharedjndiconfigFactory = (SharedjndiconfigFactory)EPackage.Registry.INSTANCE.getEFactory("platform:/resource/BE/runtime/core/common/src/sharedjndiconfig.xsd"); 
            if (theSharedjndiconfigFactory != null)
            {
                return theSharedjndiconfigFactory;
            }
        }
        catch (Exception exception)
        {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new SharedjndiconfigFactoryImpl();
    }

	/**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public SharedjndiconfigFactoryImpl() {
        super();
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	public EObject create(EClass eClass) {
        switch (eClass.getClassifierID())
        {
            case SharedjndiconfigPackage.BW_SHARED_RESOURCE: return createBwSharedResource();
            case SharedjndiconfigPackage.CONFIG: return createConfig();
            case SharedjndiconfigPackage.OPTIONAL_JNDI_PROPERTIES: return createOptionalJndiProperties();
            case SharedjndiconfigPackage.ROW: return createRow();
            case SharedjndiconfigPackage.SHARED_JNDI_CONFIG_ROOT: return createSharedJndiConfigRoot();
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
        switch (eDataType.getClassifierID())
        {
            case SharedjndiconfigPackage.RESOURCE_TYPE:
                return createResourceTypeFromString(eDataType, initialValue);
            case SharedjndiconfigPackage.RESOURCE_TYPE_OBJECT:
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
        switch (eDataType.getClassifierID())
        {
            case SharedjndiconfigPackage.RESOURCE_TYPE:
                return convertResourceTypeToString(eDataType, instanceValue);
            case SharedjndiconfigPackage.RESOURCE_TYPE_OBJECT:
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
	public OptionalJndiProperties createOptionalJndiProperties() {
        OptionalJndiPropertiesImpl optionalJndiProperties = new OptionalJndiPropertiesImpl();
        return optionalJndiProperties;
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
	public SharedJndiConfigRoot createSharedJndiConfigRoot() {
        SharedJndiConfigRootImpl sharedJndiConfigRoot = new SharedJndiConfigRootImpl();
        return sharedJndiConfigRoot;
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
        return createResourceTypeFromString(SharedjndiconfigPackage.Literals.RESOURCE_TYPE, initialValue);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public String convertResourceTypeObjectToString(EDataType eDataType, Object instanceValue) {
        return convertResourceTypeToString(SharedjndiconfigPackage.Literals.RESOURCE_TYPE, instanceValue);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public SharedjndiconfigPackage getSharedjndiconfigPackage() {
        return (SharedjndiconfigPackage)getEPackage();
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
	@Deprecated
	public static SharedjndiconfigPackage getPackage() {
        return SharedjndiconfigPackage.eINSTANCE;
    }

} //SharedjndiconfigFactoryImpl
