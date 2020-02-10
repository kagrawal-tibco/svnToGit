/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.event.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import com.tibco.cep.designtime.core.model.ModelPackage;
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
import com.tibco.cep.designtime.core.model.event.AdvisoryEvent;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.event.EventFactory;
import com.tibco.cep.designtime.core.model.event.EventPackage;
import com.tibco.cep.designtime.core.model.event.ImportRegistryEntry;
import com.tibco.cep.designtime.core.model.event.NamespaceEntry;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.event.TimeEvent;
import com.tibco.cep.designtime.core.model.event.UserProperty;
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
public class EventPackageImpl extends EPackageImpl implements EventPackage {
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
	private EClass namespaceEntryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass importRegistryEntryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass simpleEventEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass timeEventEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass userPropertyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass advisoryEventEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum evenT_TYPEEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum evenT_SCHEDULE_TYPEEEnum = null;

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
	 * @see com.tibco.cep.designtime.core.model.event.EventPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private EventPackageImpl() {
		super(eNS_URI, EventFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link EventPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static EventPackage init() {
		if (isInited) return (EventPackage)EPackage.Registry.INSTANCE.getEPackage(EventPackage.eNS_URI);

		// Obtain or create and register package
		EventPackageImpl theEventPackage = (EventPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof EventPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new EventPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		ModelPackageImpl theModelPackage = (ModelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI) instanceof ModelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI) : ModelPackage.eINSTANCE);
		ElementPackageImpl theElementPackage = (ElementPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ElementPackage.eNS_URI) instanceof ElementPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ElementPackage.eNS_URI) : ElementPackage.eINSTANCE);
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
		theEventPackage.createPackageContents();
		theModelPackage.createPackageContents();
		theElementPackage.createPackageContents();
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
		theEventPackage.initializePackageContents();
		theModelPackage.initializePackageContents();
		theElementPackage.initializePackageContents();
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
		theEventPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(EventPackage.eNS_URI, theEventPackage);
		return theEventPackage;
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
	public EAttribute getEvent_Type() {
		return (EAttribute)eventEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEvent_SuperEvent() {
		return (EReference)eventEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEvent_SuperEventPath() {
		return (EAttribute)eventEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEvent_SubEvents() {
		return (EReference)eventEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEvent_AncestorProperties() {
		return (EReference)eventEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEvent_Payload() {
		return (EReference)eventEClass.getEStructuralFeatures().get(16);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEvent_RetryOnException() {
		return (EAttribute)eventEClass.getEStructuralFeatures().get(17);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNamespaceEntry() {
		return namespaceEntryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNamespaceEntry_Prefix() {
		return (EAttribute)namespaceEntryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNamespaceEntry_Namespace() {
		return (EAttribute)namespaceEntryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getImportRegistryEntry() {
		return importRegistryEntryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getImportRegistryEntry_Location() {
		return (EAttribute)importRegistryEntryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getImportRegistryEntry_Namespace() {
		return (EAttribute)importRegistryEntryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEvent_PayloadString() {
		return (EAttribute)eventEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEvent_Ttl() {
		return (EAttribute)eventEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEvent_TtlUnits() {
		return (EAttribute)eventEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEvent_Properties() {
		return (EReference)eventEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEvent_RuleSet() {
		return (EReference)eventEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEvent_SpecifiedTime() {
		return (EAttribute)eventEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEvent_SerializationFormat() {
		return (EAttribute)eventEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEvent_NamespaceEntries() {
		return (EReference)eventEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEvent_RegistryImportEntries() {
		return (EReference)eventEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEvent_SubEventsPath() {
		return (EAttribute)eventEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEvent_ExpiryAction() {
		return (EReference)eventEClass.getEStructuralFeatures().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSimpleEvent() {
		return simpleEventEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimpleEvent_ChannelURI() {
		return (EAttribute)simpleEventEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimpleEvent_DestinationName() {
		return (EAttribute)simpleEventEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTimeEvent() {
		return timeEventEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTimeEvent_ScheduleType() {
		return (EAttribute)timeEventEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTimeEvent_TimeEventCount() {
		return (EAttribute)timeEventEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTimeEvent_Interval() {
		return (EAttribute)timeEventEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTimeEvent_IntervalUnit() {
		return (EAttribute)timeEventEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getUserProperty() {
		return userPropertyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUserProperty_Name() {
		return (EAttribute)userPropertyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUserProperty_Type() {
		return (EAttribute)userPropertyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getUserProperty_ExtendedProperties() {
		return (EReference)userPropertyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAdvisoryEvent() {
		return advisoryEventEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getEVENT_TYPE() {
		return evenT_TYPEEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getEVENT_SCHEDULE_TYPE() {
		return evenT_SCHEDULE_TYPEEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EventFactory getEventFactory() {
		return (EventFactory)getEFactoryInstance();
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
		eventEClass = createEClass(EVENT);
		createEAttribute(eventEClass, EVENT__TYPE);
		createEReference(eventEClass, EVENT__SUPER_EVENT);
		createEAttribute(eventEClass, EVENT__SUPER_EVENT_PATH);
		createEReference(eventEClass, EVENT__SUB_EVENTS);
		createEReference(eventEClass, EVENT__ANCESTOR_PROPERTIES);
		createEAttribute(eventEClass, EVENT__PAYLOAD_STRING);
		createEAttribute(eventEClass, EVENT__TTL);
		createEAttribute(eventEClass, EVENT__TTL_UNITS);
		createEReference(eventEClass, EVENT__PROPERTIES);
		createEReference(eventEClass, EVENT__RULE_SET);
		createEAttribute(eventEClass, EVENT__SPECIFIED_TIME);
		createEAttribute(eventEClass, EVENT__SERIALIZATION_FORMAT);
		createEReference(eventEClass, EVENT__NAMESPACE_ENTRIES);
		createEReference(eventEClass, EVENT__REGISTRY_IMPORT_ENTRIES);
		createEAttribute(eventEClass, EVENT__SUB_EVENTS_PATH);
		createEReference(eventEClass, EVENT__EXPIRY_ACTION);
		createEReference(eventEClass, EVENT__PAYLOAD);
		createEAttribute(eventEClass, EVENT__RETRY_ON_EXCEPTION);

		namespaceEntryEClass = createEClass(NAMESPACE_ENTRY);
		createEAttribute(namespaceEntryEClass, NAMESPACE_ENTRY__PREFIX);
		createEAttribute(namespaceEntryEClass, NAMESPACE_ENTRY__NAMESPACE);

		importRegistryEntryEClass = createEClass(IMPORT_REGISTRY_ENTRY);
		createEAttribute(importRegistryEntryEClass, IMPORT_REGISTRY_ENTRY__LOCATION);
		createEAttribute(importRegistryEntryEClass, IMPORT_REGISTRY_ENTRY__NAMESPACE);

		simpleEventEClass = createEClass(SIMPLE_EVENT);
		createEAttribute(simpleEventEClass, SIMPLE_EVENT__CHANNEL_URI);
		createEAttribute(simpleEventEClass, SIMPLE_EVENT__DESTINATION_NAME);

		timeEventEClass = createEClass(TIME_EVENT);
		createEAttribute(timeEventEClass, TIME_EVENT__SCHEDULE_TYPE);
		createEAttribute(timeEventEClass, TIME_EVENT__TIME_EVENT_COUNT);
		createEAttribute(timeEventEClass, TIME_EVENT__INTERVAL);
		createEAttribute(timeEventEClass, TIME_EVENT__INTERVAL_UNIT);

		userPropertyEClass = createEClass(USER_PROPERTY);
		createEAttribute(userPropertyEClass, USER_PROPERTY__NAME);
		createEAttribute(userPropertyEClass, USER_PROPERTY__TYPE);
		createEReference(userPropertyEClass, USER_PROPERTY__EXTENDED_PROPERTIES);

		advisoryEventEClass = createEClass(ADVISORY_EVENT);

		// Create enums
		evenT_TYPEEEnum = createEEnum(EVENT_TYPE);
		evenT_SCHEDULE_TYPEEEnum = createEEnum(EVENT_SCHEDULE_TYPE);
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
		ModelPackage theModelPackage = (ModelPackage)EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI);
		ElementPackage theElementPackage = (ElementPackage)EPackage.Registry.INSTANCE.getEPackage(ElementPackage.eNS_URI);
		RulePackage theRulePackage = (RulePackage)EPackage.Registry.INSTANCE.getEPackage(RulePackage.eNS_URI);
		DomainPackage theDomainPackage = (DomainPackage)EPackage.Registry.INSTANCE.getEPackage(DomainPackage.eNS_URI);
		ChannelPackage theChannelPackage = (ChannelPackage)EPackage.Registry.INSTANCE.getEPackage(ChannelPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		eventEClass.getESuperTypes().add(theModelPackage.getRuleParticipant());
		simpleEventEClass.getESuperTypes().add(this.getEvent());
		timeEventEClass.getESuperTypes().add(this.getEvent());
		advisoryEventEClass.getESuperTypes().add(this.getEvent());

		// Initialize classes and features; add operations and parameters
		initEClass(eventEClass, Event.class, "Event", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEvent_Type(), this.getEVENT_TYPE(), "type", null, 0, 1, Event.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEvent_SuperEvent(), this.getEvent(), null, "superEvent", null, 0, 1, Event.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEvent_SuperEventPath(), ecorePackage.getEString(), "superEventPath", null, 0, 1, Event.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEvent_SubEvents(), this.getEvent(), null, "subEvents", null, 0, -1, Event.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEvent_AncestorProperties(), ecorePackage.getEObject(), null, "ancestorProperties", null, 0, -1, Event.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEvent_PayloadString(), ecorePackage.getEString(), "payloadString", null, 0, 1, Event.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEvent_Ttl(), ecorePackage.getEString(), "ttl", "0", 0, 1, Event.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEvent_TtlUnits(), theModelPackage.getTIMEOUT_UNITS(), "ttlUnits", null, 0, 1, Event.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEvent_Properties(), theElementPackage.getPropertyDefinition(), null, "properties", null, 0, -1, Event.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEvent_RuleSet(), theRulePackage.getRuleSet(), null, "ruleSet", null, 0, 1, Event.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEvent_SpecifiedTime(), ecorePackage.getELong(), "specifiedTime", null, 0, 1, Event.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEvent_SerializationFormat(), ecorePackage.getEInt(), "serializationFormat", null, 0, 1, Event.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEvent_NamespaceEntries(), this.getNamespaceEntry(), null, "namespaceEntries", null, 0, -1, Event.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEvent_RegistryImportEntries(), this.getImportRegistryEntry(), null, "registryImportEntries", null, 0, -1, Event.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEvent_SubEventsPath(), ecorePackage.getEString(), "subEventsPath", null, 0, -1, Event.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEvent_ExpiryAction(), theRulePackage.getRule(), null, "expiryAction", null, 0, 1, Event.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEvent_Payload(), ecorePackage.getEObject(), null, "payload", null, 0, 1, Event.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEvent_RetryOnException(), ecorePackage.getEBoolean(), "retryOnException", "true", 0, 1, Event.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(eventEClass, theElementPackage.getPropertyDefinition(), "getAllUserProperties", 0, -1, IS_UNIQUE, IS_ORDERED);

		addEOperation(eventEClass, ecorePackage.getEBoolean(), "isSoapEvent", 0, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(eventEClass, theDomainPackage.getDomainInstance(), "getAllDomainInstances", 0, -1, IS_UNIQUE, IS_ORDERED);

		initEClass(namespaceEntryEClass, NamespaceEntry.class, "NamespaceEntry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNamespaceEntry_Prefix(), ecorePackage.getEString(), "prefix", null, 0, 1, NamespaceEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNamespaceEntry_Namespace(), ecorePackage.getEString(), "namespace", null, 0, 1, NamespaceEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(importRegistryEntryEClass, ImportRegistryEntry.class, "ImportRegistryEntry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getImportRegistryEntry_Location(), ecorePackage.getEString(), "location", null, 0, 1, ImportRegistryEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getImportRegistryEntry_Namespace(), ecorePackage.getEString(), "namespace", null, 0, 1, ImportRegistryEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(simpleEventEClass, SimpleEvent.class, "SimpleEvent", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSimpleEvent_ChannelURI(), ecorePackage.getEString(), "channelURI", null, 0, 1, SimpleEvent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimpleEvent_DestinationName(), ecorePackage.getEString(), "destinationName", null, 0, 1, SimpleEvent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(simpleEventEClass, theChannelPackage.getDestination(), "getDestination", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(timeEventEClass, TimeEvent.class, "TimeEvent", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTimeEvent_ScheduleType(), this.getEVENT_SCHEDULE_TYPE(), "scheduleType", null, 1, 1, TimeEvent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTimeEvent_TimeEventCount(), ecorePackage.getEString(), "timeEventCount", "1", 0, 1, TimeEvent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTimeEvent_Interval(), ecorePackage.getEString(), "interval", "0", 0, 1, TimeEvent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTimeEvent_IntervalUnit(), theModelPackage.getTIMEOUT_UNITS(), "intervalUnit", null, 0, 1, TimeEvent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(userPropertyEClass, UserProperty.class, "UserProperty", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getUserProperty_Name(), ecorePackage.getEString(), "name", null, 0, 1, UserProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUserProperty_Type(), theModelPackage.getPROPERTY_TYPES(), "type", null, 0, 1, UserProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getUserProperty_ExtendedProperties(), theModelPackage.getPropertyMap(), null, "extendedProperties", null, 0, 1, UserProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(advisoryEventEClass, AdvisoryEvent.class, "AdvisoryEvent", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Initialize enums and add enum literals
		initEEnum(evenT_TYPEEEnum, com.tibco.cep.designtime.core.model.event.EVENT_TYPE.class, "EVENT_TYPE");
		addEEnumLiteral(evenT_TYPEEEnum, com.tibco.cep.designtime.core.model.event.EVENT_TYPE.SIMPLE_EVENT);
		addEEnumLiteral(evenT_TYPEEEnum, com.tibco.cep.designtime.core.model.event.EVENT_TYPE.TIME_EVENT);
		addEEnumLiteral(evenT_TYPEEEnum, com.tibco.cep.designtime.core.model.event.EVENT_TYPE.SOAP_EVENT);
		addEEnumLiteral(evenT_TYPEEEnum, com.tibco.cep.designtime.core.model.event.EVENT_TYPE.ADVISORY_EVENT);

		initEEnum(evenT_SCHEDULE_TYPEEEnum, com.tibco.cep.designtime.core.model.event.EVENT_SCHEDULE_TYPE.class, "EVENT_SCHEDULE_TYPE");
		addEEnumLiteral(evenT_SCHEDULE_TYPEEEnum, com.tibco.cep.designtime.core.model.event.EVENT_SCHEDULE_TYPE.RULE_BASED);
		addEEnumLiteral(evenT_SCHEDULE_TYPEEEnum, com.tibco.cep.designtime.core.model.event.EVENT_SCHEDULE_TYPE.REPEAT);
		addEEnumLiteral(evenT_SCHEDULE_TYPEEEnum, com.tibco.cep.designtime.core.model.event.EVENT_SCHEDULE_TYPE.RUN_ONCE);
	}

} //EventPackageImpl
