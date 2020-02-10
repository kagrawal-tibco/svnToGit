/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.serversettingsmodel.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.tibco.cep.serversettingsmodel.Authentication;
import com.tibco.cep.serversettingsmodel.AuthenticationURL;
import com.tibco.cep.serversettingsmodel.CheckoutURL;
import com.tibco.cep.serversettingsmodel.ServersettingsmodelFactory;
import com.tibco.cep.serversettingsmodel.ServersettingsmodelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ServersettingsmodelFactoryImpl extends EFactoryImpl implements ServersettingsmodelFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ServersettingsmodelFactory init() {
		try {
			ServersettingsmodelFactory theServersettingsmodelFactory = (ServersettingsmodelFactory)EPackage.Registry.INSTANCE.getEFactory("http:///com/tibco/cep/bui/setting/model/serversettings.ecore"); 
			if (theServersettingsmodelFactory != null) {
				return theServersettingsmodelFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ServersettingsmodelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ServersettingsmodelFactoryImpl() {
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
			case ServersettingsmodelPackage.AUTHENTICATION_URL: return createAuthenticationURL();
			case ServersettingsmodelPackage.CHECKOUT_URL: return createCheckoutURL();
			case ServersettingsmodelPackage.AUTHENTICATION: return createAuthentication();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AuthenticationURL createAuthenticationURL() {
		AuthenticationURLImpl authenticationURL = new AuthenticationURLImpl();
		return authenticationURL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CheckoutURL createCheckoutURL() {
		CheckoutURLImpl checkoutURL = new CheckoutURLImpl();
		return checkoutURL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Authentication createAuthentication() {
		AuthenticationImpl authentication = new AuthenticationImpl();
		return authentication;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ServersettingsmodelPackage getServersettingsmodelPackage() {
		return (ServersettingsmodelPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ServersettingsmodelPackage getPackage() {
		return ServersettingsmodelPackage.eINSTANCE;
	}

} //ServersettingsmodelFactoryImpl
