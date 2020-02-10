/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.sharedhttp.impl;

import com.tibco.be.util.config.sharedresources.sharedhttp.*;

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
public class SharedhttpFactoryImpl extends EFactoryImpl implements SharedhttpFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SharedhttpFactory init() {
		try {
			SharedhttpFactory theSharedhttpFactory = (SharedhttpFactory)EPackage.Registry.INSTANCE.getEFactory("www.tibco.com/shared/HTTPConnection"); 
			if (theSharedhttpFactory != null) {
				return theSharedhttpFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new SharedhttpFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SharedhttpFactoryImpl() {
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
			case SharedhttpPackage.CONFIG: return createConfig();
			case SharedhttpPackage.HTTP_SHARED_RESOURCE: return createHttpSharedResource();
			case SharedhttpPackage.SHARED_HTTP_ROOT: return createSharedHttpRoot();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
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
	public HttpSharedResource createHttpSharedResource() {
		HttpSharedResourceImpl httpSharedResource = new HttpSharedResourceImpl();
		return httpSharedResource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SharedHttpRoot createSharedHttpRoot() {
		SharedHttpRootImpl sharedHttpRoot = new SharedHttpRootImpl();
		return sharedHttpRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SharedhttpPackage getSharedhttpPackage() {
		return (SharedhttpPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static SharedhttpPackage getPackage() {
		return SharedhttpPackage.eINSTANCE;
	}

} //SharedhttpFactoryImpl
