/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.states.impl;

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
import com.tibco.cep.designtime.core.model.event.EventPackage;
import com.tibco.cep.designtime.core.model.event.impl.EventPackageImpl;
import com.tibco.cep.designtime.core.model.impl.ModelPackageImpl;
import com.tibco.cep.designtime.core.model.java.JavaPackage;
import com.tibco.cep.designtime.core.model.java.impl.JavaPackageImpl;
import com.tibco.cep.designtime.core.model.rule.RulePackage;
import com.tibco.cep.designtime.core.model.rule.impl.RulePackageImpl;
import com.tibco.cep.designtime.core.model.service.channel.ChannelPackage;
import com.tibco.cep.designtime.core.model.service.channel.impl.ChannelPackageImpl;
import com.tibco.cep.designtime.core.model.states.InternalStateTransition;
import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateAnnotationLink;
import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tibco.cep.designtime.core.model.states.StateEnd;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.core.model.states.StateLink;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StateSimple;
import com.tibco.cep.designtime.core.model.states.StateStart;
import com.tibco.cep.designtime.core.model.states.StateSubmachine;
import com.tibco.cep.designtime.core.model.states.StateTransition;
import com.tibco.cep.designtime.core.model.states.StateVertex;
import com.tibco.cep.designtime.core.model.states.StatesFactory;
import com.tibco.cep.designtime.core.model.states.StatesPackage;
import com.tibco.cep.designtime.core.model.validation.ValidationPackage;
import com.tibco.cep.designtime.core.model.validation.impl.ValidationPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class StatesPackageImpl extends EPackageImpl implements StatesPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stateEntityEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stateLinkEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stateAnnotationLinkEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stateTransitionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass internalStateTransitionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stateMachineEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stateVertexEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stateCompositeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stateSubmachineEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stateEndEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stateSimpleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stateStartEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum statE_TIMEOUT_POLICYEEnum = null;

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
	 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private StatesPackageImpl() {
		super(eNS_URI, StatesFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link StatesPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static StatesPackage init() {
		if (isInited) return (StatesPackage)EPackage.Registry.INSTANCE.getEPackage(StatesPackage.eNS_URI);

		// Obtain or create and register package
		StatesPackageImpl theStatesPackage = (StatesPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof StatesPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new StatesPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		ModelPackageImpl theModelPackage = (ModelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI) instanceof ModelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI) : ModelPackage.eINSTANCE);
		ElementPackageImpl theElementPackage = (ElementPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ElementPackage.eNS_URI) instanceof ElementPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ElementPackage.eNS_URI) : ElementPackage.eINSTANCE);
		EventPackageImpl theEventPackage = (EventPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(EventPackage.eNS_URI) instanceof EventPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(EventPackage.eNS_URI) : EventPackage.eINSTANCE);
		ChannelPackageImpl theChannelPackage = (ChannelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ChannelPackage.eNS_URI) instanceof ChannelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ChannelPackage.eNS_URI) : ChannelPackage.eINSTANCE);
		RulePackageImpl theRulePackage = (RulePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(RulePackage.eNS_URI) instanceof RulePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(RulePackage.eNS_URI) : RulePackage.eINSTANCE);
		ArchivePackageImpl theArchivePackage = (ArchivePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ArchivePackage.eNS_URI) instanceof ArchivePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ArchivePackage.eNS_URI) : ArchivePackage.eINSTANCE);
		ConfigPackageImpl theConfigPackage = (ConfigPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ConfigPackage.eNS_URI) instanceof ConfigPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ConfigPackage.eNS_URI) : ConfigPackage.eINSTANCE);
		DomainPackageImpl theDomainPackage = (DomainPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(DomainPackage.eNS_URI) instanceof DomainPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(DomainPackage.eNS_URI) : DomainPackage.eINSTANCE);
		ValidationPackageImpl theValidationPackage = (ValidationPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ValidationPackage.eNS_URI) instanceof ValidationPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ValidationPackage.eNS_URI) : ValidationPackage.eINSTANCE);
		DesigntimelibPackageImpl theDesigntimelibPackage = (DesigntimelibPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(DesigntimelibPackage.eNS_URI) instanceof DesigntimelibPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(DesigntimelibPackage.eNS_URI) : DesigntimelibPackage.eINSTANCE);
		JavaPackageImpl theJavaPackage = (JavaPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(JavaPackage.eNS_URI) instanceof JavaPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(JavaPackage.eNS_URI) : JavaPackage.eINSTANCE);

		// Create package meta-data objects
		theStatesPackage.createPackageContents();
		theModelPackage.createPackageContents();
		theElementPackage.createPackageContents();
		theEventPackage.createPackageContents();
		theChannelPackage.createPackageContents();
		theRulePackage.createPackageContents();
		theArchivePackage.createPackageContents();
		theConfigPackage.createPackageContents();
		theDomainPackage.createPackageContents();
		theValidationPackage.createPackageContents();
		theDesigntimelibPackage.createPackageContents();
		theJavaPackage.createPackageContents();

		// Initialize created meta-data
		theStatesPackage.initializePackageContents();
		theModelPackage.initializePackageContents();
		theElementPackage.initializePackageContents();
		theEventPackage.initializePackageContents();
		theChannelPackage.initializePackageContents();
		theRulePackage.initializePackageContents();
		theArchivePackage.initializePackageContents();
		theConfigPackage.initializePackageContents();
		theDomainPackage.initializePackageContents();
		theValidationPackage.initializePackageContents();
		theDesigntimelibPackage.initializePackageContents();
		theJavaPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theStatesPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(StatesPackage.eNS_URI, theStatesPackage);
		return theStatesPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStateEntity() {
		return stateEntityEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStateEntity_OwnerStateMachine() {
		return (EReference)stateEntityEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStateEntity_OwnerStateMachinePath() {
		return (EAttribute)stateEntityEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStateEntity_Parent() {
		return (EReference)stateEntityEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStateEntity_Timeout() {
		return (EAttribute)stateEntityEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStateEntity_TimeoutUnits() {
		return (EAttribute)stateEntityEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStateLink() {
		return stateLinkEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStateAnnotationLink() {
		return stateAnnotationLinkEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStateAnnotationLink_ToStateEntity() {
		return (EReference)stateAnnotationLinkEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStateAnnotationLink_FromAnnotation() {
		return (EAttribute)stateAnnotationLinkEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStateTransition() {
		return stateTransitionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStateTransition_ToState() {
		return (EReference)stateTransitionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStateTransition_FromState() {
		return (EReference)stateTransitionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStateTransition_GuardRule() {
		return (EReference)stateTransitionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStateTransition_Lambda() {
		return (EAttribute)stateTransitionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStateTransition_FwdCorrelates() {
		return (EAttribute)stateTransitionEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStateTransition_Label() {
		return (EAttribute)stateTransitionEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInternalStateTransition() {
		return internalStateTransitionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInternalStateTransition_Rule() {
		return (EReference)internalStateTransitionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInternalStateTransition_OwnerState() {
		return (EReference)internalStateTransitionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStateMachine() {
		return stateMachineEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStateMachine_StateTransitions() {
		return (EReference)stateMachineEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStateMachine_AnnotationLinks() {
		return (EReference)stateMachineEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStateMachine_FwdCorrelates() {
		return (EAttribute)stateMachineEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStateMachine_Main() {
		return (EAttribute)stateMachineEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStateMachine_OwnerConceptPath() {
		return (EAttribute)stateMachineEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStateVertex() {
		return stateVertexEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStateVertex_IncomingTransitions() {
		return (EReference)stateVertexEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStateVertex_OutgoingTransitions() {
		return (EReference)stateVertexEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getState() {
		return stateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getState_InternalTransitionEnabled() {
		return (EAttribute)stateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getState_EntryAction() {
		return (EReference)stateEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getState_ExitAction() {
		return (EReference)stateEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getState_TimeoutAction() {
		return (EReference)stateEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getState_InternalTransitionRule() {
		return (EReference)stateEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getState_TimeoutExpression() {
		return (EReference)stateEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getState_TimeoutStateGUID() {
		return (EAttribute)stateEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getState_TimeoutTransitionGUID() {
		return (EAttribute)stateEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getState_TimeoutPolicy() {
		return (EAttribute)stateEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getState_TimeoutState() {
		return (EReference)stateEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStateComposite() {
		return stateCompositeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStateComposite_TimeoutComposite() {
		return (EAttribute)stateCompositeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStateComposite_ConcurrentState() {
		return (EAttribute)stateCompositeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStateComposite_Regions() {
		return (EReference)stateCompositeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStateComposite_StateEntities() {
		return (EReference)stateCompositeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStateSubmachine() {
		return stateSubmachineEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStateSubmachine_URI() {
		return (EAttribute)stateSubmachineEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStateSubmachine_CallExplicitly() {
		return (EAttribute)stateSubmachineEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStateSubmachine_PreserveForwardCorrelation() {
		return (EAttribute)stateSubmachineEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStateEnd() {
		return stateEndEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStateSimple() {
		return stateSimpleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStateStart() {
		return stateStartEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getSTATE_TIMEOUT_POLICY() {
		return statE_TIMEOUT_POLICYEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StatesFactory getStatesFactory() {
		return (StatesFactory)getEFactoryInstance();
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
		stateEntityEClass = createEClass(STATE_ENTITY);
		createEReference(stateEntityEClass, STATE_ENTITY__OWNER_STATE_MACHINE);
		createEAttribute(stateEntityEClass, STATE_ENTITY__OWNER_STATE_MACHINE_PATH);
		createEReference(stateEntityEClass, STATE_ENTITY__PARENT);
		createEAttribute(stateEntityEClass, STATE_ENTITY__TIMEOUT);
		createEAttribute(stateEntityEClass, STATE_ENTITY__TIMEOUT_UNITS);

		stateLinkEClass = createEClass(STATE_LINK);

		stateAnnotationLinkEClass = createEClass(STATE_ANNOTATION_LINK);
		createEReference(stateAnnotationLinkEClass, STATE_ANNOTATION_LINK__TO_STATE_ENTITY);
		createEAttribute(stateAnnotationLinkEClass, STATE_ANNOTATION_LINK__FROM_ANNOTATION);

		stateTransitionEClass = createEClass(STATE_TRANSITION);
		createEReference(stateTransitionEClass, STATE_TRANSITION__TO_STATE);
		createEReference(stateTransitionEClass, STATE_TRANSITION__FROM_STATE);
		createEReference(stateTransitionEClass, STATE_TRANSITION__GUARD_RULE);
		createEAttribute(stateTransitionEClass, STATE_TRANSITION__LAMBDA);
		createEAttribute(stateTransitionEClass, STATE_TRANSITION__FWD_CORRELATES);
		createEAttribute(stateTransitionEClass, STATE_TRANSITION__LABEL);

		internalStateTransitionEClass = createEClass(INTERNAL_STATE_TRANSITION);
		createEReference(internalStateTransitionEClass, INTERNAL_STATE_TRANSITION__RULE);
		createEReference(internalStateTransitionEClass, INTERNAL_STATE_TRANSITION__OWNER_STATE);

		stateMachineEClass = createEClass(STATE_MACHINE);
		createEReference(stateMachineEClass, STATE_MACHINE__STATE_TRANSITIONS);
		createEReference(stateMachineEClass, STATE_MACHINE__ANNOTATION_LINKS);
		createEAttribute(stateMachineEClass, STATE_MACHINE__FWD_CORRELATES);
		createEAttribute(stateMachineEClass, STATE_MACHINE__MAIN);
		createEAttribute(stateMachineEClass, STATE_MACHINE__OWNER_CONCEPT_PATH);

		stateVertexEClass = createEClass(STATE_VERTEX);
		createEReference(stateVertexEClass, STATE_VERTEX__INCOMING_TRANSITIONS);
		createEReference(stateVertexEClass, STATE_VERTEX__OUTGOING_TRANSITIONS);

		stateEClass = createEClass(STATE);
		createEAttribute(stateEClass, STATE__INTERNAL_TRANSITION_ENABLED);
		createEReference(stateEClass, STATE__ENTRY_ACTION);
		createEReference(stateEClass, STATE__EXIT_ACTION);
		createEReference(stateEClass, STATE__TIMEOUT_ACTION);
		createEReference(stateEClass, STATE__INTERNAL_TRANSITION_RULE);
		createEReference(stateEClass, STATE__TIMEOUT_EXPRESSION);
		createEAttribute(stateEClass, STATE__TIMEOUT_STATE_GUID);
		createEAttribute(stateEClass, STATE__TIMEOUT_TRANSITION_GUID);
		createEAttribute(stateEClass, STATE__TIMEOUT_POLICY);
		createEReference(stateEClass, STATE__TIMEOUT_STATE);

		stateCompositeEClass = createEClass(STATE_COMPOSITE);
		createEAttribute(stateCompositeEClass, STATE_COMPOSITE__TIMEOUT_COMPOSITE);
		createEAttribute(stateCompositeEClass, STATE_COMPOSITE__CONCURRENT_STATE);
		createEReference(stateCompositeEClass, STATE_COMPOSITE__REGIONS);
		createEReference(stateCompositeEClass, STATE_COMPOSITE__STATE_ENTITIES);

		stateSubmachineEClass = createEClass(STATE_SUBMACHINE);
		createEAttribute(stateSubmachineEClass, STATE_SUBMACHINE__URI);
		createEAttribute(stateSubmachineEClass, STATE_SUBMACHINE__CALL_EXPLICITLY);
		createEAttribute(stateSubmachineEClass, STATE_SUBMACHINE__PRESERVE_FORWARD_CORRELATION);

		stateEndEClass = createEClass(STATE_END);

		stateSimpleEClass = createEClass(STATE_SIMPLE);

		stateStartEClass = createEClass(STATE_START);

		// Create enums
		statE_TIMEOUT_POLICYEEnum = createEEnum(STATE_TIMEOUT_POLICY);
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
		RulePackage theRulePackage = (RulePackage)EPackage.Registry.INSTANCE.getEPackage(RulePackage.eNS_URI);
		ElementPackage theElementPackage = (ElementPackage)EPackage.Registry.INSTANCE.getEPackage(ElementPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		stateEntityEClass.getESuperTypes().add(theModelPackage.getEntity());
		stateLinkEClass.getESuperTypes().add(this.getStateEntity());
		stateAnnotationLinkEClass.getESuperTypes().add(this.getStateLink());
		stateTransitionEClass.getESuperTypes().add(this.getStateLink());
		internalStateTransitionEClass.getESuperTypes().add(this.getStateTransition());
		stateMachineEClass.getESuperTypes().add(this.getStateComposite());
		stateVertexEClass.getESuperTypes().add(this.getStateEntity());
		stateEClass.getESuperTypes().add(this.getStateVertex());
		stateCompositeEClass.getESuperTypes().add(this.getState());
		stateSubmachineEClass.getESuperTypes().add(this.getStateComposite());
		stateEndEClass.getESuperTypes().add(this.getState());
		stateSimpleEClass.getESuperTypes().add(this.getState());
		stateStartEClass.getESuperTypes().add(this.getState());

		// Initialize classes and features; add operations and parameters
		initEClass(stateEntityEClass, StateEntity.class, "StateEntity", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getStateEntity_OwnerStateMachine(), this.getStateMachine(), null, "ownerStateMachine", null, 0, 1, StateEntity.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStateEntity_OwnerStateMachinePath(), ecorePackage.getEString(), "ownerStateMachinePath", null, 0, 1, StateEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStateEntity_Parent(), this.getStateEntity(), null, "parent", null, 0, 1, StateEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStateEntity_Timeout(), ecorePackage.getEInt(), "timeout", null, 1, 1, StateEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStateEntity_TimeoutUnits(), theModelPackage.getTIMEOUT_UNITS(), "timeoutUnits", null, 1, 1, StateEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(stateLinkEClass, StateLink.class, "StateLink", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(stateAnnotationLinkEClass, StateAnnotationLink.class, "StateAnnotationLink", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getStateAnnotationLink_ToStateEntity(), this.getStateEntity(), null, "toStateEntity", null, 0, 1, StateAnnotationLink.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStateAnnotationLink_FromAnnotation(), ecorePackage.getEString(), "fromAnnotation", null, 0, 1, StateAnnotationLink.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(stateTransitionEClass, StateTransition.class, "StateTransition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getStateTransition_ToState(), this.getState(), null, "toState", null, 0, 1, StateTransition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStateTransition_FromState(), this.getState(), null, "fromState", null, 0, 1, StateTransition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStateTransition_GuardRule(), theRulePackage.getRule(), null, "guardRule", null, 0, 1, StateTransition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStateTransition_Lambda(), ecorePackage.getEBoolean(), "lambda", null, 0, 1, StateTransition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStateTransition_FwdCorrelates(), ecorePackage.getEBoolean(), "fwdCorrelates", null, 0, 1, StateTransition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStateTransition_Label(), ecorePackage.getEString(), "label", null, 0, 1, StateTransition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(internalStateTransitionEClass, InternalStateTransition.class, "InternalStateTransition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInternalStateTransition_Rule(), theRulePackage.getRule(), null, "rule", null, 0, 1, InternalStateTransition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInternalStateTransition_OwnerState(), this.getState(), null, "ownerState", null, 0, 1, InternalStateTransition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(stateMachineEClass, StateMachine.class, "StateMachine", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getStateMachine_StateTransitions(), this.getStateTransition(), null, "stateTransitions", null, 0, -1, StateMachine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStateMachine_AnnotationLinks(), this.getStateAnnotationLink(), null, "annotationLinks", null, 0, -1, StateMachine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStateMachine_FwdCorrelates(), ecorePackage.getEBoolean(), "fwdCorrelates", null, 0, 1, StateMachine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStateMachine_Main(), ecorePackage.getEBoolean(), "main", null, 0, 1, StateMachine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStateMachine_OwnerConceptPath(), ecorePackage.getEString(), "ownerConceptPath", null, 0, 1, StateMachine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(stateMachineEClass, theElementPackage.getConcept(), "getOwnerConcept", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(stateVertexEClass, StateVertex.class, "StateVertex", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getStateVertex_IncomingTransitions(), this.getStateTransition(), null, "incomingTransitions", null, 0, -1, StateVertex.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStateVertex_OutgoingTransitions(), this.getStateTransition(), null, "outgoingTransitions", null, 0, -1, StateVertex.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(stateEClass, State.class, "State", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getState_InternalTransitionEnabled(), ecorePackage.getEBoolean(), "internalTransitionEnabled", null, 0, 1, State.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getState_EntryAction(), theRulePackage.getRule(), null, "entryAction", null, 0, 1, State.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getState_ExitAction(), theRulePackage.getRule(), null, "exitAction", null, 0, 1, State.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getState_TimeoutAction(), theRulePackage.getRule(), null, "timeoutAction", null, 0, 1, State.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getState_InternalTransitionRule(), theRulePackage.getRule(), null, "internalTransitionRule", null, 0, 1, State.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getState_TimeoutExpression(), theRulePackage.getRuleFunction(), null, "timeoutExpression", null, 0, 1, State.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getState_TimeoutStateGUID(), ecorePackage.getEString(), "timeoutStateGUID", null, 0, 1, State.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getState_TimeoutTransitionGUID(), ecorePackage.getEString(), "timeoutTransitionGUID", null, 0, 1, State.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getState_TimeoutPolicy(), ecorePackage.getEInt(), "timeoutPolicy", null, 0, 1, State.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getState_TimeoutState(), this.getState(), null, "timeoutState", null, 0, 1, State.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(stateCompositeEClass, StateComposite.class, "StateComposite", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStateComposite_TimeoutComposite(), ecorePackage.getEInt(), "timeoutComposite", null, 0, 1, StateComposite.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStateComposite_ConcurrentState(), ecorePackage.getEBoolean(), "concurrentState", null, 0, 1, StateComposite.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStateComposite_Regions(), this.getStateComposite(), null, "regions", null, 0, -1, StateComposite.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStateComposite_StateEntities(), this.getStateEntity(), null, "stateEntities", null, 0, -1, StateComposite.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(stateCompositeEClass, ecorePackage.getEBoolean(), "isRegion", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(stateSubmachineEClass, StateSubmachine.class, "StateSubmachine", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStateSubmachine_URI(), ecorePackage.getEString(), "URI", null, 0, 1, StateSubmachine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStateSubmachine_CallExplicitly(), ecorePackage.getEBoolean(), "callExplicitly", null, 0, 1, StateSubmachine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStateSubmachine_PreserveForwardCorrelation(), ecorePackage.getEBoolean(), "preserveForwardCorrelation", null, 0, 1, StateSubmachine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(stateEndEClass, StateEnd.class, "StateEnd", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(stateSimpleEClass, StateSimple.class, "StateSimple", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(stateStartEClass, StateStart.class, "StateStart", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Initialize enums and add enum literals
		initEEnum(statE_TIMEOUT_POLICYEEnum, com.tibco.cep.designtime.core.model.states.STATE_TIMEOUT_POLICY.class, "STATE_TIMEOUT_POLICY");
		addEEnumLiteral(statE_TIMEOUT_POLICYEEnum, com.tibco.cep.designtime.core.model.states.STATE_TIMEOUT_POLICY.DETERMINISTIC_STATE_POLICY);
		addEEnumLiteral(statE_TIMEOUT_POLICYEEnum, com.tibco.cep.designtime.core.model.states.STATE_TIMEOUT_POLICY.NON_DETERMINISTIC_STATE_POLICY);
		addEEnumLiteral(statE_TIMEOUT_POLICYEEnum, com.tibco.cep.designtime.core.model.states.STATE_TIMEOUT_POLICY.NO_ACTION_TIMEOUT_POLICY);
	}

} //StatesPackageImpl
