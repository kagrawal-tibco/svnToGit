/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.aemetaservices.impl;

import com.tibco.be.util.config.sharedresources.aemetaservices.*;

import org.eclipse.emf.ecore.EClass;
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
public class AemetaservicesFactoryImpl extends EFactoryImpl implements AemetaservicesFactory {
	/**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public static AemetaservicesFactory init() {
        try
        {
            AemetaservicesFactory theAemetaservicesFactory = (AemetaservicesFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.tibco.com/xmlns/aemeta/services/2002"); 
            if (theAemetaservicesFactory != null)
            {
                return theAemetaservicesFactory;
            }
        }
        catch (Exception exception)
        {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new AemetaservicesFactoryImpl();
    }

	/**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public AemetaservicesFactoryImpl() {
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
            case AemetaservicesPackage.AE_META_SERVICES_ROOT: return createAeMetaServicesRoot();
            case AemetaservicesPackage.CERT_TYPE: return createCertType();
            case AemetaservicesPackage.IDENTITY_TYPE: return createIdentityType();
            case AemetaservicesPackage.SSL: return createSsl();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public AeMetaServicesRoot createAeMetaServicesRoot() {
        AeMetaServicesRootImpl aeMetaServicesRoot = new AeMetaServicesRootImpl();
        return aeMetaServicesRoot;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public CertType createCertType() {
        CertTypeImpl certType = new CertTypeImpl();
        return certType;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public IdentityType createIdentityType() {
        IdentityTypeImpl identityType = new IdentityTypeImpl();
        return identityType;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public Ssl createSsl() {
        SslImpl ssl = new SslImpl();
        return ssl;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public AemetaservicesPackage getAemetaservicesPackage() {
        return (AemetaservicesPackage)getEPackage();
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
	@Deprecated
	public static AemetaservicesPackage getPackage() {
        return AemetaservicesPackage.eINSTANCE;
    }

} //AemetaservicesFactoryImpl
