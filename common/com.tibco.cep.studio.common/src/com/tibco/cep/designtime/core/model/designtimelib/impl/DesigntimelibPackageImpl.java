/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.designtimelib.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import com.tibco.cep.designtime.core.model.ModelPackage;
import com.tibco.cep.designtime.core.model.archive.ArchivePackage;
import com.tibco.cep.designtime.core.model.archive.config.ConfigPackage;
import com.tibco.cep.designtime.core.model.archive.config.impl.ConfigPackageImpl;
import com.tibco.cep.designtime.core.model.archive.impl.ArchivePackageImpl;
import com.tibco.cep.designtime.core.model.designtimelib.DesignTimeLib;
import com.tibco.cep.designtime.core.model.designtimelib.DesignTimeLibEntry;
import com.tibco.cep.designtime.core.model.designtimelib.DesigntimelibFactory;
import com.tibco.cep.designtime.core.model.designtimelib.DesigntimelibPackage;
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
public class DesigntimelibPackageImpl extends EPackageImpl implements DesigntimelibPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass designTimeLibEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass designTimeLibEntryEClass = null;

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
	 * @see com.tibco.cep.designtime.core.model.designtimelib.DesigntimelibPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private DesigntimelibPackageImpl() {
		super(eNS_URI, DesigntimelibFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link DesigntimelibPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static DesigntimelibPackage init() {
		if (isInited) return (DesigntimelibPackage)EPackage.Registry.INSTANCE.getEPackage(DesigntimelibPackage.eNS_URI);

		// Obtain or create and register package
		DesigntimelibPackageImpl theDesigntimelibPackage = (DesigntimelibPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof DesigntimelibPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new DesigntimelibPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		ModelPackageImpl theModelPackage = (ModelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI) instanceof ModelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI) : ModelPackage.eINSTANCE);
		ElementPackageImpl theElementPackage = (ElementPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ElementPackage.eNS_URI) instanceof ElementPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ElementPackage.eNS_URI) : ElementPackage.eINSTANCE);
		EventPackageImpl theEventPackage = (EventPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(EventPackage.eNS_URI) instanceof EventPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(EventPackage.eNS_URI) : EventPackage.eINSTANCE);
		ChannelPackageImpl theChannelPackage = (ChannelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ChannelPackage.eNS_URI) instanceof ChannelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ChannelPackage.eNS_URI) : ChannelPackage.eINSTANCE);
		RulePackageImpl theRulePackage = (RulePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(RulePackage.eNS_URI) instanceof RulePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(RulePackage.eNS_URI) : RulePackage.eINSTANCE);
		StatesPackageImpl theStatesPackage = (StatesPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(StatesPackage.eNS_URI) instanceof StatesPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(StatesPackage.eNS_URI) : StatesPackage.eINSTANCE);
		ArchivePackageImpl theArchivePackage = (ArchivePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ArchivePackage.eNS_URI) instanceof ArchivePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ArchivePackage.eNS_URI) : ArchivePackage.eINSTANCE);
		ConfigPackageImpl theConfigPackage = (ConfigPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ConfigPackage.eNS_URI) instanceof ConfigPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ConfigPackage.eNS_URI) : ConfigPackage.eINSTANCE);
		DomainPackageImpl theDomainPackage = (DomainPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(DomainPackage.eNS_URI) instanceof DomainPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(DomainPackage.eNS_URI) : DomainPackage.eINSTANCE);
		ValidationPackageImpl theValidationPackage = (ValidationPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ValidationPackage.eNS_URI) instanceof ValidationPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ValidationPackage.eNS_URI) : ValidationPackage.eINSTANCE);
		JavaPackageImpl theJavaPackage = (JavaPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(JavaPackage.eNS_URI) instanceof JavaPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(JavaPackage.eNS_URI) : JavaPackage.eINSTANCE);

		// Create package meta-data objects
		theDesigntimelibPackage.createPackageContents();
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
		theJavaPackage.createPackageContents();

		// Initialize created meta-data
		theDesigntimelibPackage.initializePackageContents();
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
		theJavaPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theDesigntimelibPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(DesigntimelibPackage.eNS_URI, theDesigntimelibPackage);
		return theDesigntimelibPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDesignTimeLib() {
		return designTimeLibEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDesignTimeLib_DesignTimeLibEntry() {
		return (EReference)designTimeLibEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDesignTimeLibEntry() {
		return designTimeLibEntryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDesignTimeLibEntry_Alias() {
		return (EAttribute)designTimeLibEntryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDesignTimeLibEntry_Location() {
		return (EAttribute)designTimeLibEntryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DesigntimelibFactory getDesigntimelibFactory() {
		return (DesigntimelibFactory)getEFactoryInstance();
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
		designTimeLibEClass = createEClass(DESIGN_TIME_LIB);
		createEReference(designTimeLibEClass, DESIGN_TIME_LIB__DESIGN_TIME_LIB_ENTRY);

		designTimeLibEntryEClass = createEClass(DESIGN_TIME_LIB_ENTRY);
		createEAttribute(designTimeLibEntryEClass, DESIGN_TIME_LIB_ENTRY__ALIAS);
		createEAttribute(designTimeLibEntryEClass, DESIGN_TIME_LIB_ENTRY__LOCATION);
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
		initEClass(designTimeLibEClass, DesignTimeLib.class, "DesignTimeLib", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDesignTimeLib_DesignTimeLibEntry(), this.getDesignTimeLibEntry(), null, "designTimeLibEntry", null, 0, -1, DesignTimeLib.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(designTimeLibEntryEClass, DesignTimeLibEntry.class, "DesignTimeLibEntry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDesignTimeLibEntry_Alias(), ecorePackage.getEString(), "alias", null, 0, 1, DesignTimeLibEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDesignTimeLibEntry_Location(), ecorePackage.getEString(), "location", null, 0, 1, DesignTimeLibEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
	}

} //DesigntimelibPackageImpl
