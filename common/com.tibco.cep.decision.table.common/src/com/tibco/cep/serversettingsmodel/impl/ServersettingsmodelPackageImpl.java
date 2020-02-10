/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.serversettingsmodel.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import com.tibco.cep.serversettingsmodel.Authentication;
import com.tibco.cep.serversettingsmodel.AuthenticationURL;
import com.tibco.cep.serversettingsmodel.CheckoutURL;
import com.tibco.cep.serversettingsmodel.ServersettingsmodelFactory;
import com.tibco.cep.serversettingsmodel.ServersettingsmodelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ServersettingsmodelPackageImpl extends EPackageImpl implements ServersettingsmodelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass authenticationURLEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass checkoutURLEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass authenticationEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see com.tibco.cep.serversettingsmodel.ServersettingsmodelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ServersettingsmodelPackageImpl() {
		super(eNS_URI, ServersettingsmodelFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this
	 * model, and for any others upon which it depends.  Simple
	 * dependencies are satisfied by calling this method on all
	 * dependent packages before doing anything else.  This method drives
	 * initialization for interdependent packages directly, in parallel
	 * with this package, itself.
	 * <p>Of this package and its interdependencies, all packages which
	 * have not yet been registered by their URI values are first created
	 * and registered.  The packages are then initialized in two steps:
	 * meta-model objects for all of the packages are created before any
	 * are initialized, since one package's meta-model objects may refer to
	 * those of another.
	 * <p>Invocation of this method will not affect any packages that have
	 * already been initialized.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ServersettingsmodelPackage init() {
		if (isInited) return (ServersettingsmodelPackage)EPackage.Registry.INSTANCE.getEPackage(ServersettingsmodelPackage.eNS_URI);

		// Obtain or create and register package
		ServersettingsmodelPackageImpl theServersettingsmodelPackage = (ServersettingsmodelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof ServersettingsmodelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new ServersettingsmodelPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theServersettingsmodelPackage.createPackageContents();

		// Initialize created meta-data
		theServersettingsmodelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theServersettingsmodelPackage.freeze();

		return theServersettingsmodelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAuthenticationURL() {
		return authenticationURLEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAuthenticationURL_URL() {
		return (EAttribute)authenticationURLEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAuthenticationURL_Authentication() {
		return (EReference)authenticationURLEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCheckoutURL() {
		return checkoutURLEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCheckoutURL_URL() {
		return (EAttribute)checkoutURLEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAuthentication() {
		return authenticationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAuthentication_Username() {
		return (EAttribute)authenticationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAuthentication_Password() {
		return (EAttribute)authenticationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAuthentication_SavePassword() {
		return (EAttribute)authenticationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ServersettingsmodelFactory getServersettingsmodelFactory() {
		return (ServersettingsmodelFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		authenticationURLEClass = createEClass(AUTHENTICATION_URL);
		createEAttribute(authenticationURLEClass, AUTHENTICATION_URL__URL);
		createEReference(authenticationURLEClass, AUTHENTICATION_URL__AUTHENTICATION);

		checkoutURLEClass = createEClass(CHECKOUT_URL);
		createEAttribute(checkoutURLEClass, CHECKOUT_URL__URL);

		authenticationEClass = createEClass(AUTHENTICATION);
		createEAttribute(authenticationEClass, AUTHENTICATION__USERNAME);
		createEAttribute(authenticationEClass, AUTHENTICATION__PASSWORD);
		createEAttribute(authenticationEClass, AUTHENTICATION__SAVE_PASSWORD);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(authenticationURLEClass, AuthenticationURL.class, "AuthenticationURL", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAuthenticationURL_URL(), ecorePackage.getEString(), "URL", null, 0, -1, AuthenticationURL.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAuthenticationURL_Authentication(), this.getAuthentication(), null, "authentication", null, 0, 1, AuthenticationURL.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(checkoutURLEClass, CheckoutURL.class, "CheckoutURL", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCheckoutURL_URL(), ecorePackage.getEString(), "URL", null, 0, -1, CheckoutURL.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(authenticationEClass, Authentication.class, "Authentication", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAuthentication_Username(), ecorePackage.getEString(), "username", null, 0, 1, Authentication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAuthentication_Password(), ecorePackage.getEByteArray(), "password", null, 0, 1, Authentication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAuthentication_SavePassword(), ecorePackage.getEBoolean(), "savePassword", null, 0, 1, Authentication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //ServersettingsmodelPackageImpl
