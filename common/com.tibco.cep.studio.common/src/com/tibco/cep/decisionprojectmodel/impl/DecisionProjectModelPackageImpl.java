/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionprojectmodel.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage;
import com.tibco.cep.decision.table.model.dtmodel.impl.DtmodelPackageImpl;
import com.tibco.cep.decisionproject.ontology.OntologyPackage;
import com.tibco.cep.decisionproject.ontology.impl.OntologyPackageImpl;
import com.tibco.cep.decisionprojectmodel.DecisionProject;
import com.tibco.cep.decisionprojectmodel.DecisionProjectModelFactory;
import com.tibco.cep.decisionprojectmodel.DecisionProjectModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DecisionProjectModelPackageImpl extends EPackageImpl implements DecisionProjectModelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass decisionProjectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass domainModelEClass = null;

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
	 * @see com.tibco.cep.decisionprojectmodel.DecisionProjectModelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private DecisionProjectModelPackageImpl() {
		super(eNS_URI, DecisionProjectModelFactory.eINSTANCE);
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
	public static DecisionProjectModelPackage init() {
		if (isInited) return (DecisionProjectModelPackage)EPackage.Registry.INSTANCE.getEPackage(DecisionProjectModelPackage.eNS_URI);

		// Obtain or create and register package
		DecisionProjectModelPackageImpl theDecisionProjectModelPackage = (DecisionProjectModelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof DecisionProjectModelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new DecisionProjectModelPackageImpl());

		isInited = true;

		// Initialize simple dependencies

		// Obtain or create and register interdependencies
		OntologyPackageImpl theOntologyPackage = (OntologyPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(OntologyPackage.eNS_URI) instanceof OntologyPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(OntologyPackage.eNS_URI) : OntologyPackage.eINSTANCE);
		DtmodelPackageImpl theDtmodelPackage = (DtmodelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(DtmodelPackage.eNS_URI) instanceof DtmodelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(DtmodelPackage.eNS_URI) : DtmodelPackage.eINSTANCE);
		//DomainModelPackageImpl theDomainModelPackage = (DomainModelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(DomainModelPackage.eNS_URI) instanceof DomainModelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(DomainModelPackage.eNS_URI) : DomainModelPackage.eINSTANCE);

		// Create package meta-data objects
		theDecisionProjectModelPackage.createPackageContents();
		theOntologyPackage.createPackageContents();
		theDtmodelPackage.createPackageContents();
		//theDomainModelPackage.createPackageContents();

		// Initialize created meta-data
		theDecisionProjectModelPackage.initializePackageContents();
		theOntologyPackage.initializePackageContents();
		theDtmodelPackage.initializePackageContents();
		//theDomainModelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theDecisionProjectModelPackage.freeze();

		return theDecisionProjectModelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDecisionProject() {
		return decisionProjectEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDecisionProject_Ontology() {
		return (EReference)decisionProjectEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDecisionProject_DomainModel() {
		return (EReference)decisionProjectEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDecisionProject_AuthToken() {
		return (EAttribute)decisionProjectEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDecisionProject_Name() {
		return (EAttribute)decisionProjectEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDecisionProject_Md() {
		return (EReference)decisionProjectEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDecisionProject_Version() {
		return (EAttribute)decisionProjectEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDomainModel() {
		return domainModelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDomainModel_Domain() {
		return (EReference)domainModelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDomainModel_Version() {
		return (EAttribute)domainModelEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDomainModel_LastModifiedBy() {
		return (EAttribute)domainModelEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DecisionProjectModelFactory getDecisionProjectModelFactory() {
		return (DecisionProjectModelFactory)getEFactoryInstance();
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
		decisionProjectEClass = createEClass(DECISION_PROJECT);
		createEReference(decisionProjectEClass, DECISION_PROJECT__ONTOLOGY);
		createEReference(decisionProjectEClass, DECISION_PROJECT__DOMAIN_MODEL);
		createEAttribute(decisionProjectEClass, DECISION_PROJECT__AUTH_TOKEN);
		createEAttribute(decisionProjectEClass, DECISION_PROJECT__NAME);
		createEReference(decisionProjectEClass, DECISION_PROJECT__MD);
		createEAttribute(decisionProjectEClass, DECISION_PROJECT__VERSION);

		domainModelEClass = createEClass(DOMAIN_MODEL);
		createEReference(domainModelEClass, DOMAIN_MODEL__DOMAIN);
		createEAttribute(domainModelEClass, DOMAIN_MODEL__VERSION);
		createEAttribute(domainModelEClass, DOMAIN_MODEL__LAST_MODIFIED_BY);
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

		// Obtain other dependent packages
		OntologyPackage theOntologyPackage = (OntologyPackage)EPackage.Registry.INSTANCE.getEPackage(OntologyPackage.eNS_URI);
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);
		DtmodelPackage theDtmodelPackage = (DtmodelPackage)EPackage.Registry.INSTANCE.getEPackage(DtmodelPackage.eNS_URI);
		//DomainModelPackage theDomainModelPackage = (DomainModelPackage)EPackage.Registry.INSTANCE.getEPackage(DomainModelPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		domainModelEClass.getESuperTypes().add(theOntologyPackage.getAccessControlCandidate());

		// Initialize classes and features; add operations and parameters
		initEClass(decisionProjectEClass, DecisionProject.class, "DecisionProject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDecisionProject_Ontology(), theOntologyPackage.getOntology(), null, "ontology", null, 0, 1, DecisionProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDecisionProject_DomainModel(), this.getDomainModel(), null, "domainModel", null, 0, 1, DecisionProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDecisionProject_AuthToken(), theEcorePackage.getEString(), "authToken", null, 0, 1, DecisionProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDecisionProject_Name(), theEcorePackage.getEString(), "name", null, 1, 1, DecisionProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDecisionProject_Md(), theDtmodelPackage.getMetaData(), null, "md", null, 0, 1, DecisionProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDecisionProject_Version(), theEcorePackage.getEDouble(), "version", null, 0, 1, DecisionProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		/*initEClass(domainModelEClass, DomainModel.class, "DomainModel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDomainModel_Domain(), theDomainModelPackage.getDomain(), null, "domain", null, 0, -1, DomainModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDomainModel_Version(), theEcorePackage.getEDouble(), "version", "0", 1, 1, DomainModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDomainModel_LastModifiedBy(), theEcorePackage.getEString(), "lastModifiedBy", null, 0, 1, DomainModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
*/
		// Create resource
		createResource(eNS_URI);
	}

} //DecisionProjectModelPackageImpl
