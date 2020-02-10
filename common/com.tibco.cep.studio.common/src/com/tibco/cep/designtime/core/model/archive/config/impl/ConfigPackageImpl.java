/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.archive.config.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import com.tibco.cep.designtime.core.model.ModelPackage;
import com.tibco.cep.designtime.core.model.archive.ArchivePackage;
import com.tibco.cep.designtime.core.model.archive.config.Archives;
import com.tibco.cep.designtime.core.model.archive.config.ConfigFactory;
import com.tibco.cep.designtime.core.model.archive.config.ConfigPackage;
import com.tibco.cep.designtime.core.model.archive.config.EngineConfig;
import com.tibco.cep.designtime.core.model.archive.config.EntityResources;
import com.tibco.cep.designtime.core.model.archive.impl.ArchivePackageImpl;
import com.tibco.cep.designtime.core.model.designtimelib.DesigntimelibPackage;
import com.tibco.cep.designtime.core.model.designtimelib.impl.DesigntimelibPackageImpl;
import com.tibco.cep.designtime.core.model.domain.DomainPackage;
import com.tibco.cep.designtime.core.model.domain.impl.DomainPackageImpl;
import com.tibco.cep.designtime.core.model.element.ElementPackage;
import com.tibco.cep.designtime.core.model.element.impl.ElementPackageImpl;
import com.tibco.cep.designtime.core.model.event.EventPackage;
import com.tibco.cep.designtime.core.model.event.impl.EventPackageImpl;
import com.tibco.cep.designtime.core.model.impl.ModelPackageImpl;
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

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ConfigPackageImpl extends EPackageImpl implements ConfigPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass engineConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass archivesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass entityResourcesEClass = null;

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
	 * @see com.tibco.cep.designtime.core.model.archive.config.ConfigPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ConfigPackageImpl() {
		super(eNS_URI, ConfigFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link ConfigPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ConfigPackage init() {
		if (isInited) return (ConfigPackage)EPackage.Registry.INSTANCE.getEPackage(ConfigPackage.eNS_URI);

		// Obtain or create and register package
		ConfigPackageImpl theConfigPackage = (ConfigPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ConfigPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ConfigPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		ModelPackageImpl theModelPackage = (ModelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI) instanceof ModelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI) : ModelPackage.eINSTANCE);
		ElementPackageImpl theElementPackage = (ElementPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ElementPackage.eNS_URI) instanceof ElementPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ElementPackage.eNS_URI) : ElementPackage.eINSTANCE);
		EventPackageImpl theEventPackage = (EventPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(EventPackage.eNS_URI) instanceof EventPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(EventPackage.eNS_URI) : EventPackage.eINSTANCE);
		ChannelPackageImpl theChannelPackage = (ChannelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ChannelPackage.eNS_URI) instanceof ChannelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ChannelPackage.eNS_URI) : ChannelPackage.eINSTANCE);
		RulePackageImpl theRulePackage = (RulePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(RulePackage.eNS_URI) instanceof RulePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(RulePackage.eNS_URI) : RulePackage.eINSTANCE);
		StatesPackageImpl theStatesPackage = (StatesPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(StatesPackage.eNS_URI) instanceof StatesPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(StatesPackage.eNS_URI) : StatesPackage.eINSTANCE);
		ArchivePackageImpl theArchivePackage = (ArchivePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ArchivePackage.eNS_URI) instanceof ArchivePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ArchivePackage.eNS_URI) : ArchivePackage.eINSTANCE);
		DomainPackageImpl theDomainPackage = (DomainPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(DomainPackage.eNS_URI) instanceof DomainPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(DomainPackage.eNS_URI) : DomainPackage.eINSTANCE);
		ValidationPackageImpl theValidationPackage = (ValidationPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ValidationPackage.eNS_URI) instanceof ValidationPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ValidationPackage.eNS_URI) : ValidationPackage.eINSTANCE);
		DesigntimelibPackageImpl theDesigntimelibPackage = (DesigntimelibPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(DesigntimelibPackage.eNS_URI) instanceof DesigntimelibPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(DesigntimelibPackage.eNS_URI) : DesigntimelibPackage.eINSTANCE);
		JavaPackageImpl theJavaPackage = (JavaPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(JavaPackage.eNS_URI) instanceof JavaPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(JavaPackage.eNS_URI) : JavaPackage.eINSTANCE);

		// Create package meta-data objects
		theConfigPackage.createPackageContents();
		theModelPackage.createPackageContents();
		theElementPackage.createPackageContents();
		theEventPackage.createPackageContents();
		theChannelPackage.createPackageContents();
		theRulePackage.createPackageContents();
		theStatesPackage.createPackageContents();
		theArchivePackage.createPackageContents();
		theDomainPackage.createPackageContents();
		theValidationPackage.createPackageContents();
		theDesigntimelibPackage.createPackageContents();
		theJavaPackage.createPackageContents();

		// Initialize created meta-data
		theConfigPackage.initializePackageContents();
		theModelPackage.initializePackageContents();
		theElementPackage.initializePackageContents();
		theEventPackage.initializePackageContents();
		theChannelPackage.initializePackageContents();
		theRulePackage.initializePackageContents();
		theStatesPackage.initializePackageContents();
		theArchivePackage.initializePackageContents();
		theDomainPackage.initializePackageContents();
		theValidationPackage.initializePackageContents();
		theDesigntimelibPackage.initializePackageContents();
		theJavaPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theConfigPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ConfigPackage.eNS_URI, theConfigPackage);
		return theConfigPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEngineConfig() {
		return engineConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEngineConfig_Archives() {
		return (EReference)engineConfigEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEngineConfig_EntityResources() {
		return (EReference)engineConfigEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getArchives() {
		return archivesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getArchives_BeArchive() {
		return (EReference)archivesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEntityResources() {
		return entityResourcesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEntityResources_Entity() {
		return (EReference)entityResourcesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConfigFactory getConfigFactory() {
		return (ConfigFactory)getEFactoryInstance();
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
		engineConfigEClass = createEClass(ENGINE_CONFIG);
		createEReference(engineConfigEClass, ENGINE_CONFIG__ARCHIVES);
		createEReference(engineConfigEClass, ENGINE_CONFIG__ENTITY_RESOURCES);

		archivesEClass = createEClass(ARCHIVES);
		createEReference(archivesEClass, ARCHIVES__BE_ARCHIVE);

		entityResourcesEClass = createEClass(ENTITY_RESOURCES);
		createEReference(entityResourcesEClass, ENTITY_RESOURCES__ENTITY);
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
		ArchivePackage theArchivePackage = (ArchivePackage)EPackage.Registry.INSTANCE.getEPackage(ArchivePackage.eNS_URI);
		ModelPackage theModelPackage = (ModelPackage)EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(engineConfigEClass, EngineConfig.class, "EngineConfig", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEngineConfig_Archives(), this.getArchives(), null, "archives", null, 1, 1, EngineConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEngineConfig_EntityResources(), this.getEntityResources(), null, "entityResources", null, 1, 1, EngineConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(archivesEClass, Archives.class, "Archives", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getArchives_BeArchive(), theArchivePackage.getBusinessEventsArchiveResource(), null, "beArchive", null, 1, -1, Archives.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(entityResourcesEClass, EntityResources.class, "EntityResources", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEntityResources_Entity(), theModelPackage.getEntity(), null, "entity", null, 0, -1, EntityResources.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
	}

} //ConfigPackageImpl
