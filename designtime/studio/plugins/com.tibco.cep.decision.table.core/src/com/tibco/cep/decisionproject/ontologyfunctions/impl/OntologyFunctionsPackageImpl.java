/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionproject.ontologyfunctions.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import com.tibco.cep.decisionproject.ontologyfunctions.AbstractResource;
import com.tibco.cep.decisionproject.ontologyfunctions.Concept;
import com.tibco.cep.decisionproject.ontologyfunctions.Event;
import com.tibco.cep.decisionproject.ontologyfunctions.Folder;
import com.tibco.cep.decisionproject.ontologyfunctions.FunctionLabel;
import com.tibco.cep.decisionproject.ontologyfunctions.OntologyFunctions;
import com.tibco.cep.decisionproject.ontologyfunctions.OntologyFunctionsFactory;
import com.tibco.cep.decisionproject.ontologyfunctions.OntologyFunctionsPackage;
import com.tibco.cep.decisionproject.ontologyfunctions.OntologyFunctionsRoot;
import com.tibco.cep.decisionproject.ontologyfunctions.ParentResource;
import com.tibco.cep.decisionproject.ontologyfunctions.RuleFunction;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class OntologyFunctionsPackageImpl extends EPackageImpl implements OntologyFunctionsPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ontologyFunctionsRootEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ontologyFunctionsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractResourceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass conceptEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass parentResourceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass folderEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eventEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ruleFunctionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass functionLabelEClass = null;

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
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.OntologyFunctionsPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private OntologyFunctionsPackageImpl() {
		super(eNS_URI, OntologyFunctionsFactory.eINSTANCE);
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
	public static OntologyFunctionsPackage init() {
		if (isInited) return (OntologyFunctionsPackage)EPackage.Registry.INSTANCE.getEPackage(OntologyFunctionsPackage.eNS_URI);

		// Obtain or create and register package
		OntologyFunctionsPackageImpl theOntologyFunctionsPackage = (OntologyFunctionsPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof OntologyFunctionsPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new OntologyFunctionsPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theOntologyFunctionsPackage.createPackageContents();

		// Initialize created meta-data
		theOntologyFunctionsPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theOntologyFunctionsPackage.freeze();

		return theOntologyFunctionsPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOntologyFunctionsRoot() {
		return ontologyFunctionsRootEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOntologyFunctionsRoot_OntologyFunctions() {
		return (EReference)ontologyFunctionsRootEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOntologyFunctions() {
		return ontologyFunctionsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOntologyFunctions_Resource() {
		return (EReference)ontologyFunctionsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAbstractResource() {
		return abstractResourceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAbstractResource_Name() {
		return (EAttribute)abstractResourceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConcept() {
		return conceptEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConcept_FunctionLabel() {
		return (EReference)conceptEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getParentResource() {
		return parentResourceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFolder() {
		return folderEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFolder_Resource() {
		return (EReference)folderEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEvent() {
		return eventEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEvent_FunctionLabel() {
		return (EReference)eventEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRuleFunction() {
		return ruleFunctionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRuleFunction_FunctionLabel() {
		return (EReference)ruleFunctionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleFunction_Validity() {
		return (EAttribute)ruleFunctionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFunctionLabel() {
		return functionLabelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OntologyFunctionsFactory getOntologyFunctionsFactory() {
		return (OntologyFunctionsFactory)getEFactoryInstance();
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
		ontologyFunctionsRootEClass = createEClass(ONTOLOGY_FUNCTIONS_ROOT);
		createEReference(ontologyFunctionsRootEClass, ONTOLOGY_FUNCTIONS_ROOT__ONTOLOGY_FUNCTIONS);

		ontologyFunctionsEClass = createEClass(ONTOLOGY_FUNCTIONS);
		createEReference(ontologyFunctionsEClass, ONTOLOGY_FUNCTIONS__RESOURCE);

		abstractResourceEClass = createEClass(ABSTRACT_RESOURCE);
		createEAttribute(abstractResourceEClass, ABSTRACT_RESOURCE__NAME);

		conceptEClass = createEClass(CONCEPT);
		createEReference(conceptEClass, CONCEPT__FUNCTION_LABEL);

		parentResourceEClass = createEClass(PARENT_RESOURCE);

		folderEClass = createEClass(FOLDER);
		createEReference(folderEClass, FOLDER__RESOURCE);

		eventEClass = createEClass(EVENT);
		createEReference(eventEClass, EVENT__FUNCTION_LABEL);

		ruleFunctionEClass = createEClass(RULE_FUNCTION);
		createEReference(ruleFunctionEClass, RULE_FUNCTION__FUNCTION_LABEL);
		createEAttribute(ruleFunctionEClass, RULE_FUNCTION__VALIDITY);

		functionLabelEClass = createEClass(FUNCTION_LABEL);
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
		ontologyFunctionsEClass.getESuperTypes().add(this.getParentResource());
		conceptEClass.getESuperTypes().add(this.getAbstractResource());
		parentResourceEClass.getESuperTypes().add(this.getAbstractResource());
		folderEClass.getESuperTypes().add(this.getParentResource());
		eventEClass.getESuperTypes().add(this.getAbstractResource());
		ruleFunctionEClass.getESuperTypes().add(this.getAbstractResource());
		functionLabelEClass.getESuperTypes().add(this.getAbstractResource());

		// Initialize classes and features; add operations and parameters
		initEClass(ontologyFunctionsRootEClass, OntologyFunctionsRoot.class, "OntologyFunctionsRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getOntologyFunctionsRoot_OntologyFunctions(), this.getAbstractResource(), null, "ontologyFunctions", null, 0, 1, OntologyFunctionsRoot.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ontologyFunctionsEClass, OntologyFunctions.class, "OntologyFunctions", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getOntologyFunctions_Resource(), this.getAbstractResource(), null, "resource", null, 0, -1, OntologyFunctions.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(abstractResourceEClass, AbstractResource.class, "AbstractResource", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAbstractResource_Name(), ecorePackage.getEString(), "name", null, 0, 1, AbstractResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(conceptEClass, Concept.class, "Concept", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getConcept_FunctionLabel(), this.getFunctionLabel(), null, "functionLabel", null, 0, 1, Concept.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(parentResourceEClass, ParentResource.class, "ParentResource", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(folderEClass, Folder.class, "Folder", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFolder_Resource(), this.getAbstractResource(), null, "resource", null, 0, -1, Folder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eventEClass, Event.class, "Event", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEvent_FunctionLabel(), this.getFunctionLabel(), null, "functionLabel", null, 0, 1, Event.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ruleFunctionEClass, RuleFunction.class, "RuleFunction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRuleFunction_FunctionLabel(), this.getFunctionLabel(), null, "functionLabel", null, 0, 1, RuleFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleFunction_Validity(), ecorePackage.getEString(), "validity", null, 1, 1, RuleFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(functionLabelEClass, FunctionLabel.class, "FunctionLabel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //OntologyFunctionsPackageImpl
