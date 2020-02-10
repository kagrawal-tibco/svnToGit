/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.table.model.domainmodel.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import com.tibco.cep.decision.table.model.domainmodel.Domain;
import com.tibco.cep.decision.table.model.domainmodel.DomainEntry;
import com.tibco.cep.decision.table.model.domainmodel.DomainModelFactory;
import com.tibco.cep.decision.table.model.domainmodel.DomainModelPackage;
import com.tibco.cep.decision.table.model.domainmodel.EntryValue;
import com.tibco.cep.decision.table.model.domainmodel.RangeInfo;
import com.tibco.cep.decision.table.model.domainmodel.SingleValue;
import com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage;
import com.tibco.cep.decision.table.model.dtmodel.impl.DtmodelPackageImpl;
import com.tibco.cep.decisionproject.ontology.OntologyPackage;
import com.tibco.cep.decisionproject.ontology.impl.OntologyPackageImpl;
import com.tibco.cep.decisionprojectmodel.DecisionProjectModelPackage;
import com.tibco.cep.decisionprojectmodel.impl.DecisionProjectModelPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DomainModelPackageImpl extends EPackageImpl implements DomainModelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass domainEntryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass rangeInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass singleValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass entryValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass domainEClass = null;

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
	 * @see com.tibco.cep.decision.table.model.domainmodel.DomainModelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private DomainModelPackageImpl() {
		super(eNS_URI, DomainModelFactory.eINSTANCE);
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
	public static DomainModelPackage init() {
		if (isInited) return (DomainModelPackage)EPackage.Registry.INSTANCE.getEPackage(DomainModelPackage.eNS_URI);

		// Obtain or create and register package
		DomainModelPackageImpl theDomainModelPackage = (DomainModelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof DomainModelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new DomainModelPackageImpl());

		isInited = true;

		// Initialize simple dependencies

		// Obtain or create and register interdependencies
		DecisionProjectModelPackageImpl theDecisionProjectModelPackage = (DecisionProjectModelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(DecisionProjectModelPackage.eNS_URI) instanceof DecisionProjectModelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(DecisionProjectModelPackage.eNS_URI) : DecisionProjectModelPackage.eINSTANCE);
		OntologyPackageImpl theOntologyPackage = (OntologyPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(OntologyPackage.eNS_URI) instanceof OntologyPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(OntologyPackage.eNS_URI) : OntologyPackage.eINSTANCE);
		DtmodelPackageImpl theDtmodelPackage = (DtmodelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(DtmodelPackage.eNS_URI) instanceof DtmodelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(DtmodelPackage.eNS_URI) : DtmodelPackage.eINSTANCE);

		// Create package meta-data objects
		theDomainModelPackage.createPackageContents();
		theDecisionProjectModelPackage.createPackageContents();
		theOntologyPackage.createPackageContents();
		theDtmodelPackage.createPackageContents();

		// Initialize created meta-data
		theDomainModelPackage.initializePackageContents();
		theDecisionProjectModelPackage.initializePackageContents();
		theOntologyPackage.initializePackageContents();
		theDtmodelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theDomainModelPackage.freeze();

		return theDomainModelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDomainEntry() {
		return domainEntryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDomainEntry_EntryName() {
		return (EAttribute)domainEntryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDomainEntry_EntryValue() {
		return (EReference)domainEntryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRangeInfo() {
		return rangeInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRangeInfo_LowerBound() {
		return (EAttribute)rangeInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRangeInfo_UpperBound() {
		return (EAttribute)rangeInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRangeInfo_LowerBoundIncluded() {
		return (EAttribute)rangeInfoEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRangeInfo_UpperBoundIncluded() {
		return (EAttribute)rangeInfoEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSingleValue() {
		return singleValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEntryValue() {
		return entryValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEntryValue_Multiple() {
		return (EAttribute)entryValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEntryValue_Value() {
		return (EAttribute)entryValueEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDomain() {
		return domainEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDomain_DomainEntry() {
		return (EReference)domainEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDomain_Type() {
		return (EAttribute)domainEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDomain_ResourceType() {
		return (EAttribute)domainEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDomain_Resource() {
		return (EAttribute)domainEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDomain_Override() {
		return (EAttribute)domainEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDomain_DbRef() {
		return (EAttribute)domainEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DomainModelFactory getDomainModelFactory() {
		return (DomainModelFactory)getEFactoryInstance();
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
		domainEntryEClass = createEClass(DOMAIN_ENTRY);
		createEAttribute(domainEntryEClass, DOMAIN_ENTRY__ENTRY_NAME);
		createEReference(domainEntryEClass, DOMAIN_ENTRY__ENTRY_VALUE);

		rangeInfoEClass = createEClass(RANGE_INFO);
		createEAttribute(rangeInfoEClass, RANGE_INFO__LOWER_BOUND);
		createEAttribute(rangeInfoEClass, RANGE_INFO__UPPER_BOUND);
		createEAttribute(rangeInfoEClass, RANGE_INFO__LOWER_BOUND_INCLUDED);
		createEAttribute(rangeInfoEClass, RANGE_INFO__UPPER_BOUND_INCLUDED);

		singleValueEClass = createEClass(SINGLE_VALUE);

		entryValueEClass = createEClass(ENTRY_VALUE);
		createEAttribute(entryValueEClass, ENTRY_VALUE__MULTIPLE);
		createEAttribute(entryValueEClass, ENTRY_VALUE__VALUE);

		domainEClass = createEClass(DOMAIN);
		createEReference(domainEClass, DOMAIN__DOMAIN_ENTRY);
		createEAttribute(domainEClass, DOMAIN__TYPE);
		createEAttribute(domainEClass, DOMAIN__RESOURCE_TYPE);
		createEAttribute(domainEClass, DOMAIN__RESOURCE);
		createEAttribute(domainEClass, DOMAIN__OVERRIDE);
		createEAttribute(domainEClass, DOMAIN__DB_REF);
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
		DtmodelPackage theDtmodelPackage = (DtmodelPackage)EPackage.Registry.INSTANCE.getEPackage(DtmodelPackage.eNS_URI);
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		rangeInfoEClass.getESuperTypes().add(this.getEntryValue());
		singleValueEClass.getESuperTypes().add(this.getEntryValue());
		domainEClass.getESuperTypes().add(theOntologyPackage.getAccessControlCandidate());

		// Initialize classes and features; add operations and parameters
		initEClass(domainEntryEClass, DomainEntry.class, "DomainEntry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDomainEntry_EntryName(), ecorePackage.getEString(), "entryName", null, 0, 1, DomainEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDomainEntry_EntryValue(), this.getEntryValue(), null, "entryValue", null, 0, 1, DomainEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(domainEntryEClass, ecorePackage.getEString(), "getDescription", 0, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(domainEntryEClass, ecorePackage.getEString(), "getActualValue", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(rangeInfoEClass, RangeInfo.class, "RangeInfo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRangeInfo_LowerBound(), ecorePackage.getEString(), "lowerBound", null, 0, 1, RangeInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRangeInfo_UpperBound(), ecorePackage.getEString(), "upperBound", null, 0, 1, RangeInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRangeInfo_LowerBoundIncluded(), ecorePackage.getEBoolean(), "lowerBoundIncluded", null, 0, 1, RangeInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRangeInfo_UpperBoundIncluded(), ecorePackage.getEBoolean(), "upperBoundIncluded", null, 0, 1, RangeInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(singleValueEClass, SingleValue.class, "SingleValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(entryValueEClass, EntryValue.class, "EntryValue", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEntryValue_Multiple(), ecorePackage.getEBoolean(), "multiple", null, 0, 1, EntryValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntryValue_Value(), ecorePackage.getEString(), "value", null, 0, 1, EntryValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(entryValueEClass, ecorePackage.getEString(), "getDescription", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(domainEClass, Domain.class, "Domain", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDomain_DomainEntry(), this.getDomainEntry(), null, "domainEntry", null, 0, -1, Domain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDomain_Type(), ecorePackage.getEString(), "type", null, 0, 1, Domain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDomain_ResourceType(), theDtmodelPackage.getResourceType(), "resourceType", null, 0, 1, Domain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDomain_Resource(), theEcorePackage.getEString(), "resource", null, 0, 1, Domain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDomain_Override(), theEcorePackage.getEBoolean(), "override", "true", 0, 1, Domain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDomain_DbRef(), theEcorePackage.getEString(), "dbRef", null, 0, 1, Domain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //DomainModelPackageImpl
