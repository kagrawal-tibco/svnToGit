/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.ModelFactory;
import com.tibco.cep.designtime.core.model.ModelPackage;
import com.tibco.cep.designtime.core.model.ObjectProperty;
import com.tibco.cep.designtime.core.model.PropertyMap;
import com.tibco.cep.designtime.core.model.RuleParticipant;
import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.designtime.core.model.archive.ArchivePackage;
import com.tibco.cep.designtime.core.model.archive.config.ConfigPackage;
import com.tibco.cep.designtime.core.model.archive.config.impl.ConfigPackageImpl;
import com.tibco.cep.designtime.core.model.archive.impl.ArchivePackageImpl;
import com.tibco.cep.designtime.core.model.designtimelib.DesigntimelibPackage;
import com.tibco.cep.designtime.core.model.designtimelib.impl.DesigntimelibPackageImpl;
import com.tibco.cep.designtime.core.model.domain.DomainPackage;
import com.tibco.cep.designtime.core.model.domain.impl.DomainPackageImpl;
import com.tibco.cep.designtime.core.model.element.ElementPackage;
import com.tibco.cep.designtime.core.model.element.impl.ElementPackageImpl;
import com.tibco.cep.designtime.core.model.event.EventPackage;
import com.tibco.cep.designtime.core.model.event.impl.EventPackageImpl;
import com.tibco.cep.designtime.core.model.java.JavaPackage;
import com.tibco.cep.designtime.core.model.java.impl.JavaPackageImpl;
import com.tibco.cep.designtime.core.model.rule.RulePackage;
import com.tibco.cep.designtime.core.model.rule.impl.RulePackageImpl;
import com.tibco.cep.designtime.core.model.service.channel.ChannelPackage;
import com.tibco.cep.designtime.core.model.service.channel.impl.ChannelPackageImpl;
import com.tibco.cep.designtime.core.model.states.StatesPackage;
import com.tibco.cep.designtime.core.model.states.impl.StatesPackageImpl;
import com.tibco.cep.designtime.core.model.validation.ValidationPackage;
import com.tibco.cep.designtime.core.model.validation.impl.ValidationPackageImpl;
import java.lang.Comparable;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ModelPackageImpl extends EPackageImpl implements ModelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass entityEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass simplePropertyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass objectPropertyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ruleParticipantEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass propertyMapEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass comparableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum propertY_TYPESEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum timeouT_UNITSEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum metriC_TYPEEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum metriC_AGGR_TYPEEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum historY_POLICYEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum domaiN_DATA_TYPESEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum timE_UNITSEEnum = null;

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
	 * @see com.tibco.cep.designtime.core.model.ModelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ModelPackageImpl() {
		super(eNS_URI, ModelFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link ModelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ModelPackage init() {
		if (isInited) return (ModelPackage)EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI);

		// Obtain or create and register package
		ModelPackageImpl theModelPackage = (ModelPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ModelPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ModelPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		ElementPackageImpl theElementPackage = (ElementPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ElementPackage.eNS_URI) instanceof ElementPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ElementPackage.eNS_URI) : ElementPackage.eINSTANCE);
		EventPackageImpl theEventPackage = (EventPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(EventPackage.eNS_URI) instanceof EventPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(EventPackage.eNS_URI) : EventPackage.eINSTANCE);
		ChannelPackageImpl theChannelPackage = (ChannelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ChannelPackage.eNS_URI) instanceof ChannelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ChannelPackage.eNS_URI) : ChannelPackage.eINSTANCE);
		RulePackageImpl theRulePackage = (RulePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(RulePackage.eNS_URI) instanceof RulePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(RulePackage.eNS_URI) : RulePackage.eINSTANCE);
		StatesPackageImpl theStatesPackage = (StatesPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(StatesPackage.eNS_URI) instanceof StatesPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(StatesPackage.eNS_URI) : StatesPackage.eINSTANCE);
		ArchivePackageImpl theArchivePackage = (ArchivePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ArchivePackage.eNS_URI) instanceof ArchivePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ArchivePackage.eNS_URI) : ArchivePackage.eINSTANCE);
		ConfigPackageImpl theConfigPackage = (ConfigPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ConfigPackage.eNS_URI) instanceof ConfigPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ConfigPackage.eNS_URI) : ConfigPackage.eINSTANCE);
		DomainPackageImpl theDomainPackage = (DomainPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(DomainPackage.eNS_URI) instanceof DomainPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(DomainPackage.eNS_URI) : DomainPackage.eINSTANCE);
		ValidationPackageImpl theValidationPackage = (ValidationPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ValidationPackage.eNS_URI) instanceof ValidationPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ValidationPackage.eNS_URI) : ValidationPackage.eINSTANCE);
		DesigntimelibPackageImpl theDesigntimelibPackage = (DesigntimelibPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(DesigntimelibPackage.eNS_URI) instanceof DesigntimelibPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(DesigntimelibPackage.eNS_URI) : DesigntimelibPackage.eINSTANCE);
		JavaPackageImpl theJavaPackage = (JavaPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(JavaPackage.eNS_URI) instanceof JavaPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(JavaPackage.eNS_URI) : JavaPackage.eINSTANCE);

		// Create package meta-data objects
		theModelPackage.createPackageContents();
		theElementPackage.createPackageContents();
		theEventPackage.createPackageContents();
		theChannelPackage.createPackageContents();
		theRulePackage.createPackageContents();
		theStatesPackage.createPackageContents();
		theArchivePackage.createPackageContents();
		theConfigPackage.createPackageContents();
		theDomainPackage.createPackageContents();
		theValidationPackage.createPackageContents();
		theDesigntimelibPackage.createPackageContents();
		theJavaPackage.createPackageContents();

		// Initialize created meta-data
		theModelPackage.initializePackageContents();
		theElementPackage.initializePackageContents();
		theEventPackage.initializePackageContents();
		theChannelPackage.initializePackageContents();
		theRulePackage.initializePackageContents();
		theStatesPackage.initializePackageContents();
		theArchivePackage.initializePackageContents();
		theConfigPackage.initializePackageContents();
		theDomainPackage.initializePackageContents();
		theValidationPackage.initializePackageContents();
		theDesigntimelibPackage.initializePackageContents();
		theJavaPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theModelPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ModelPackage.eNS_URI, theModelPackage);
		return theModelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEntity() {
		return entityEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEntity_Namespace() {
		return (EAttribute)entityEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEntity_Folder() {
		return (EAttribute)entityEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEntity_Name() {
		return (EAttribute)entityEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEntity_Description() {
		return (EAttribute)entityEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEntity_LastModified() {
		return (EAttribute)entityEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEntity_GUID() {
		return (EAttribute)entityEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEntity_Ontology() {
		return (EReference)entityEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEntity_ExtendedProperties() {
		return (EReference)entityEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEntity_HiddenProperties() {
		return (EReference)entityEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEntity_TransientProperties() {
		return (EReference)entityEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEntity_OwnerProjectName() {
		return (EAttribute)entityEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSimpleProperty() {
		return simplePropertyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimpleProperty_Value() {
		return (EAttribute)simplePropertyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimpleProperty_Mandatory() {
		return (EAttribute)simplePropertyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimpleProperty_Type() {
		return (EAttribute)simplePropertyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimpleProperty_DisplayName() {
		return (EAttribute)simplePropertyEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getObjectProperty() {
		return objectPropertyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getObjectProperty_Value() {
		return (EReference)objectPropertyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRuleParticipant() {
		return ruleParticipantEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPropertyMap() {
		return propertyMapEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPropertyMap_Properties() {
		return (EReference)propertyMapEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getComparable() {
		return comparableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getPROPERTY_TYPES() {
		return propertY_TYPESEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getTIMEOUT_UNITS() {
		return timeouT_UNITSEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getMETRIC_TYPE() {
		return metriC_TYPEEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getMETRIC_AGGR_TYPE() {
		return metriC_AGGR_TYPEEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getHISTORY_POLICY() {
		return historY_POLICYEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getDOMAIN_DATA_TYPES() {
		return domaiN_DATA_TYPESEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getTIME_UNITS() {
		return timE_UNITSEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelFactory getModelFactory() {
		return (ModelFactory)getEFactoryInstance();
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
		entityEClass = createEClass(ENTITY);
		createEAttribute(entityEClass, ENTITY__NAMESPACE);
		createEAttribute(entityEClass, ENTITY__FOLDER);
		createEAttribute(entityEClass, ENTITY__NAME);
		createEAttribute(entityEClass, ENTITY__DESCRIPTION);
		createEAttribute(entityEClass, ENTITY__LAST_MODIFIED);
		createEAttribute(entityEClass, ENTITY__GUID);
		createEReference(entityEClass, ENTITY__ONTOLOGY);
		createEReference(entityEClass, ENTITY__EXTENDED_PROPERTIES);
		createEReference(entityEClass, ENTITY__HIDDEN_PROPERTIES);
		createEReference(entityEClass, ENTITY__TRANSIENT_PROPERTIES);
		createEAttribute(entityEClass, ENTITY__OWNER_PROJECT_NAME);

		simplePropertyEClass = createEClass(SIMPLE_PROPERTY);
		createEAttribute(simplePropertyEClass, SIMPLE_PROPERTY__VALUE);
		createEAttribute(simplePropertyEClass, SIMPLE_PROPERTY__MANDATORY);
		createEAttribute(simplePropertyEClass, SIMPLE_PROPERTY__TYPE);
		createEAttribute(simplePropertyEClass, SIMPLE_PROPERTY__DISPLAY_NAME);

		objectPropertyEClass = createEClass(OBJECT_PROPERTY);
		createEReference(objectPropertyEClass, OBJECT_PROPERTY__VALUE);

		ruleParticipantEClass = createEClass(RULE_PARTICIPANT);

		propertyMapEClass = createEClass(PROPERTY_MAP);
		createEReference(propertyMapEClass, PROPERTY_MAP__PROPERTIES);

		comparableEClass = createEClass(COMPARABLE);

		// Create enums
		propertY_TYPESEEnum = createEEnum(PROPERTY_TYPES);
		timeouT_UNITSEEnum = createEEnum(TIMEOUT_UNITS);
		metriC_TYPEEEnum = createEEnum(METRIC_TYPE);
		metriC_AGGR_TYPEEEnum = createEEnum(METRIC_AGGR_TYPE);
		historY_POLICYEEnum = createEEnum(HISTORY_POLICY);
		domaiN_DATA_TYPESEEnum = createEEnum(DOMAIN_DATA_TYPES);
		timE_UNITSEEnum = createEEnum(TIME_UNITS);
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
		ElementPackage theElementPackage = (ElementPackage)EPackage.Registry.INSTANCE.getEPackage(ElementPackage.eNS_URI);
		EventPackage theEventPackage = (EventPackage)EPackage.Registry.INSTANCE.getEPackage(EventPackage.eNS_URI);
		ChannelPackage theChannelPackage = (ChannelPackage)EPackage.Registry.INSTANCE.getEPackage(ChannelPackage.eNS_URI);
		RulePackage theRulePackage = (RulePackage)EPackage.Registry.INSTANCE.getEPackage(RulePackage.eNS_URI);
		StatesPackage theStatesPackage = (StatesPackage)EPackage.Registry.INSTANCE.getEPackage(StatesPackage.eNS_URI);
		ArchivePackage theArchivePackage = (ArchivePackage)EPackage.Registry.INSTANCE.getEPackage(ArchivePackage.eNS_URI);
		DomainPackage theDomainPackage = (DomainPackage)EPackage.Registry.INSTANCE.getEPackage(DomainPackage.eNS_URI);
		ValidationPackage theValidationPackage = (ValidationPackage)EPackage.Registry.INSTANCE.getEPackage(ValidationPackage.eNS_URI);
		DesigntimelibPackage theDesigntimelibPackage = (DesigntimelibPackage)EPackage.Registry.INSTANCE.getEPackage(DesigntimelibPackage.eNS_URI);
		JavaPackage theJavaPackage = (JavaPackage)EPackage.Registry.INSTANCE.getEPackage(JavaPackage.eNS_URI);

		// Add subpackages
		getESubpackages().add(theElementPackage);
		getESubpackages().add(theEventPackage);
		getESubpackages().add(theChannelPackage);
		getESubpackages().add(theRulePackage);
		getESubpackages().add(theStatesPackage);
		getESubpackages().add(theArchivePackage);
		getESubpackages().add(theDomainPackage);
		getESubpackages().add(theValidationPackage);
		getESubpackages().add(theDesigntimelibPackage);
		getESubpackages().add(theJavaPackage);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		simplePropertyEClass.getESuperTypes().add(this.getEntity());
		objectPropertyEClass.getESuperTypes().add(this.getEntity());
		ruleParticipantEClass.getESuperTypes().add(this.getEntity());

		// Initialize classes and features; add operations and parameters
		initEClass(entityEClass, Entity.class, "Entity", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEntity_Namespace(), ecorePackage.getEString(), "namespace", null, 1, 1, Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntity_Folder(), ecorePackage.getEString(), "folder", null, 1, 1, Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntity_Name(), ecorePackage.getEString(), "name", null, 1, 1, Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntity_Description(), ecorePackage.getEString(), "description", null, 1, 1, Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntity_LastModified(), ecorePackage.getEString(), "lastModified", null, 1, 1, Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntity_GUID(), ecorePackage.getEString(), "GUID", null, 1, 1, Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntity_Ontology(), ecorePackage.getEObject(), null, "ontology", null, 0, 1, Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntity_ExtendedProperties(), this.getPropertyMap(), null, "extendedProperties", null, 0, 1, Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntity_HiddenProperties(), this.getPropertyMap(), null, "hiddenProperties", null, 0, 1, Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntity_TransientProperties(), this.getPropertyMap(), null, "transientProperties", null, 0, 1, Entity.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntity_OwnerProjectName(), ecorePackage.getEString(), "ownerProjectName", null, 0, 1, Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(entityEClass, ecorePackage.getEString(), "getFullPath", 1, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(entityEClass, theValidationPackage.getModelError(), "getModelErrors", 0, -1, IS_UNIQUE, IS_ORDERED);

		addEOperation(entityEClass, ecorePackage.getEBoolean(), "isValid", 1, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(simplePropertyEClass, SimpleProperty.class, "SimpleProperty", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSimpleProperty_Value(), ecorePackage.getEString(), "value", null, 1, 1, SimpleProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimpleProperty_Mandatory(), ecorePackage.getEBoolean(), "mandatory", null, 0, 1, SimpleProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimpleProperty_Type(), ecorePackage.getEString(), "type", null, 0, 1, SimpleProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimpleProperty_DisplayName(), ecorePackage.getEString(), "displayName", null, 0, 1, SimpleProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(objectPropertyEClass, ObjectProperty.class, "ObjectProperty", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getObjectProperty_Value(), ecorePackage.getEObject(), null, "value", null, 1, 1, ObjectProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ruleParticipantEClass, RuleParticipant.class, "RuleParticipant", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(propertyMapEClass, PropertyMap.class, "PropertyMap", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPropertyMap_Properties(), this.getEntity(), null, "properties", null, 0, -1, PropertyMap.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(comparableEClass, Comparable.class, "Comparable", IS_ABSTRACT, IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);

		// Initialize enums and add enum literals
		initEEnum(propertY_TYPESEEnum, com.tibco.cep.designtime.core.model.PROPERTY_TYPES.class, "PROPERTY_TYPES");
		addEEnumLiteral(propertY_TYPESEEnum, com.tibco.cep.designtime.core.model.PROPERTY_TYPES.STRING);
		addEEnumLiteral(propertY_TYPESEEnum, com.tibco.cep.designtime.core.model.PROPERTY_TYPES.INTEGER);
		addEEnumLiteral(propertY_TYPESEEnum, com.tibco.cep.designtime.core.model.PROPERTY_TYPES.LONG);
		addEEnumLiteral(propertY_TYPESEEnum, com.tibco.cep.designtime.core.model.PROPERTY_TYPES.DOUBLE);
		addEEnumLiteral(propertY_TYPESEEnum, com.tibco.cep.designtime.core.model.PROPERTY_TYPES.BOOLEAN);
		addEEnumLiteral(propertY_TYPESEEnum, com.tibco.cep.designtime.core.model.PROPERTY_TYPES.DATE_TIME);
		addEEnumLiteral(propertY_TYPESEEnum, com.tibco.cep.designtime.core.model.PROPERTY_TYPES.CONCEPT);
		addEEnumLiteral(propertY_TYPESEEnum, com.tibco.cep.designtime.core.model.PROPERTY_TYPES.CONCEPT_REFERENCE);

		initEEnum(timeouT_UNITSEEnum, com.tibco.cep.designtime.core.model.TIMEOUT_UNITS.class, "TIMEOUT_UNITS");
		addEEnumLiteral(timeouT_UNITSEEnum, com.tibco.cep.designtime.core.model.TIMEOUT_UNITS.MILLISECONDS);
		addEEnumLiteral(timeouT_UNITSEEnum, com.tibco.cep.designtime.core.model.TIMEOUT_UNITS.SECONDS);
		addEEnumLiteral(timeouT_UNITSEEnum, com.tibco.cep.designtime.core.model.TIMEOUT_UNITS.MINUTES);
		addEEnumLiteral(timeouT_UNITSEEnum, com.tibco.cep.designtime.core.model.TIMEOUT_UNITS.HOURS);
		addEEnumLiteral(timeouT_UNITSEEnum, com.tibco.cep.designtime.core.model.TIMEOUT_UNITS.DAYS);
		addEEnumLiteral(timeouT_UNITSEEnum, com.tibco.cep.designtime.core.model.TIMEOUT_UNITS.WEEK_DAYS);
		addEEnumLiteral(timeouT_UNITSEEnum, com.tibco.cep.designtime.core.model.TIMEOUT_UNITS.WEEKS);
		addEEnumLiteral(timeouT_UNITSEEnum, com.tibco.cep.designtime.core.model.TIMEOUT_UNITS.MONTHS);
		addEEnumLiteral(timeouT_UNITSEEnum, com.tibco.cep.designtime.core.model.TIMEOUT_UNITS.YEARS);

		initEEnum(metriC_TYPEEEnum, com.tibco.cep.designtime.core.model.METRIC_TYPE.class, "METRIC_TYPE");
		addEEnumLiteral(metriC_TYPEEEnum, com.tibco.cep.designtime.core.model.METRIC_TYPE.REGULAR);
		addEEnumLiteral(metriC_TYPEEEnum, com.tibco.cep.designtime.core.model.METRIC_TYPE.ROLLING_TIME);
		addEEnumLiteral(metriC_TYPEEEnum, com.tibco.cep.designtime.core.model.METRIC_TYPE.ROLLING_COUNT);
		addEEnumLiteral(metriC_TYPEEEnum, com.tibco.cep.designtime.core.model.METRIC_TYPE.PERIODIC_TIME);
		addEEnumLiteral(metriC_TYPEEEnum, com.tibco.cep.designtime.core.model.METRIC_TYPE.PERIODIC_COUNT);
		addEEnumLiteral(metriC_TYPEEEnum, com.tibco.cep.designtime.core.model.METRIC_TYPE.WINDOW_TIME);
		addEEnumLiteral(metriC_TYPEEEnum, com.tibco.cep.designtime.core.model.METRIC_TYPE.WINDOW_COUNT);

		initEEnum(metriC_AGGR_TYPEEEnum, com.tibco.cep.designtime.core.model.METRIC_AGGR_TYPE.class, "METRIC_AGGR_TYPE");
		addEEnumLiteral(metriC_AGGR_TYPEEEnum, com.tibco.cep.designtime.core.model.METRIC_AGGR_TYPE.SET);
		addEEnumLiteral(metriC_AGGR_TYPEEEnum, com.tibco.cep.designtime.core.model.METRIC_AGGR_TYPE.COUNT);
		addEEnumLiteral(metriC_AGGR_TYPEEEnum, com.tibco.cep.designtime.core.model.METRIC_AGGR_TYPE.MAXIMUM);
		addEEnumLiteral(metriC_AGGR_TYPEEEnum, com.tibco.cep.designtime.core.model.METRIC_AGGR_TYPE.MINIMUM);
		addEEnumLiteral(metriC_AGGR_TYPEEEnum, com.tibco.cep.designtime.core.model.METRIC_AGGR_TYPE.SUM);
		addEEnumLiteral(metriC_AGGR_TYPEEEnum, com.tibco.cep.designtime.core.model.METRIC_AGGR_TYPE.SUM_OF_SQUARES);
		addEEnumLiteral(metriC_AGGR_TYPEEEnum, com.tibco.cep.designtime.core.model.METRIC_AGGR_TYPE.AVERAGE);
		addEEnumLiteral(metriC_AGGR_TYPEEEnum, com.tibco.cep.designtime.core.model.METRIC_AGGR_TYPE.VARIANCE);
		addEEnumLiteral(metriC_AGGR_TYPEEEnum, com.tibco.cep.designtime.core.model.METRIC_AGGR_TYPE.STANDARD_DEVIATION);

		initEEnum(historY_POLICYEEnum, com.tibco.cep.designtime.core.model.HISTORY_POLICY.class, "HISTORY_POLICY");
		addEEnumLiteral(historY_POLICYEEnum, com.tibco.cep.designtime.core.model.HISTORY_POLICY.CHANGES_ONLY);
		addEEnumLiteral(historY_POLICYEEnum, com.tibco.cep.designtime.core.model.HISTORY_POLICY.ALL_VALUES);

		initEEnum(domaiN_DATA_TYPESEEnum, com.tibco.cep.designtime.core.model.DOMAIN_DATA_TYPES.class, "DOMAIN_DATA_TYPES");
		addEEnumLiteral(domaiN_DATA_TYPESEEnum, com.tibco.cep.designtime.core.model.DOMAIN_DATA_TYPES.STRING);
		addEEnumLiteral(domaiN_DATA_TYPESEEnum, com.tibco.cep.designtime.core.model.DOMAIN_DATA_TYPES.INTEGER);
		addEEnumLiteral(domaiN_DATA_TYPESEEnum, com.tibco.cep.designtime.core.model.DOMAIN_DATA_TYPES.LONG);
		addEEnumLiteral(domaiN_DATA_TYPESEEnum, com.tibco.cep.designtime.core.model.DOMAIN_DATA_TYPES.DOUBLE);
		addEEnumLiteral(domaiN_DATA_TYPESEEnum, com.tibco.cep.designtime.core.model.DOMAIN_DATA_TYPES.BOOLEAN);
		addEEnumLiteral(domaiN_DATA_TYPESEEnum, com.tibco.cep.designtime.core.model.DOMAIN_DATA_TYPES.DATE_TIME);

		initEEnum(timE_UNITSEEnum, com.tibco.cep.designtime.core.model.TIME_UNITS.class, "TIME_UNITS");
		addEEnumLiteral(timE_UNITSEEnum, com.tibco.cep.designtime.core.model.TIME_UNITS.MILLISECONDS);
		addEEnumLiteral(timE_UNITSEEnum, com.tibco.cep.designtime.core.model.TIME_UNITS.SECONDS);
		addEEnumLiteral(timE_UNITSEEnum, com.tibco.cep.designtime.core.model.TIME_UNITS.MINUTES);
		addEEnumLiteral(timE_UNITSEEnum, com.tibco.cep.designtime.core.model.TIME_UNITS.HOURS);
		addEEnumLiteral(timE_UNITSEEnum, com.tibco.cep.designtime.core.model.TIME_UNITS.DAYS);
		addEEnumLiteral(timE_UNITSEEnum, com.tibco.cep.designtime.core.model.TIME_UNITS.WEEK_DAYS);
		addEEnumLiteral(timE_UNITSEEnum, com.tibco.cep.designtime.core.model.TIME_UNITS.WEEKS);
		addEEnumLiteral(timE_UNITSEEnum, com.tibco.cep.designtime.core.model.TIME_UNITS.MONTHS);
		addEEnumLiteral(timE_UNITSEEnum, com.tibco.cep.designtime.core.model.TIME_UNITS.YEARS);

		// Create resource
		createResource(eNS_URI);
	}

} //ModelPackageImpl
