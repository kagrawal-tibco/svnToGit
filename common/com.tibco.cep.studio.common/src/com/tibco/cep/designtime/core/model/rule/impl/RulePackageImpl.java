/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.rule.impl;

import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
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
import com.tibco.cep.designtime.core.model.rule.ActionContext;
import com.tibco.cep.designtime.core.model.rule.ActionContextSymbol;
import com.tibco.cep.designtime.core.model.rule.ActionType;
import com.tibco.cep.designtime.core.model.rule.Binding;
import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleFactory;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.rule.RuleFunctionSymbol;
import com.tibco.cep.designtime.core.model.rule.RulePackage;
import com.tibco.cep.designtime.core.model.rule.RuleSet;
import com.tibco.cep.designtime.core.model.rule.RuleTemplate;
import com.tibco.cep.designtime.core.model.rule.RuleTemplateSymbol;
import com.tibco.cep.designtime.core.model.rule.RuleTemplateView;
import com.tibco.cep.designtime.core.model.rule.ScopeContainer;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.core.model.rule.Symbols;
import com.tibco.cep.designtime.core.model.rule.Validity;
import com.tibco.cep.designtime.core.model.rule.XsltFunction;
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
public class RulePackageImpl extends EPackageImpl implements RulePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass scopeContainerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass compilableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ruleEClass = null;

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
	private EClass ruleSetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass symbolEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ruleFunctionSymbolEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass symbolsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass symbolMapEntryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ruleTemplateSymbolEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass actionContextSymbolEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ruleTemplateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass actionContextEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass bindingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ruleTemplateViewEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xsltFunctionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum validityEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum actionTypeEEnum = null;

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
	 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private RulePackageImpl() {
		super(eNS_URI, RuleFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link RulePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static RulePackage init() {
		if (isInited) return (RulePackage)EPackage.Registry.INSTANCE.getEPackage(RulePackage.eNS_URI);

		// Obtain or create and register package
		RulePackageImpl theRulePackage = (RulePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof RulePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new RulePackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		ModelPackageImpl theModelPackage = (ModelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI) instanceof ModelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI) : ModelPackage.eINSTANCE);
		ElementPackageImpl theElementPackage = (ElementPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ElementPackage.eNS_URI) instanceof ElementPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ElementPackage.eNS_URI) : ElementPackage.eINSTANCE);
		EventPackageImpl theEventPackage = (EventPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(EventPackage.eNS_URI) instanceof EventPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(EventPackage.eNS_URI) : EventPackage.eINSTANCE);
		ChannelPackageImpl theChannelPackage = (ChannelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ChannelPackage.eNS_URI) instanceof ChannelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ChannelPackage.eNS_URI) : ChannelPackage.eINSTANCE);
		StatesPackageImpl theStatesPackage = (StatesPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(StatesPackage.eNS_URI) instanceof StatesPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(StatesPackage.eNS_URI) : StatesPackage.eINSTANCE);
		ArchivePackageImpl theArchivePackage = (ArchivePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ArchivePackage.eNS_URI) instanceof ArchivePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ArchivePackage.eNS_URI) : ArchivePackage.eINSTANCE);
		ConfigPackageImpl theConfigPackage = (ConfigPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ConfigPackage.eNS_URI) instanceof ConfigPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ConfigPackage.eNS_URI) : ConfigPackage.eINSTANCE);
		DomainPackageImpl theDomainPackage = (DomainPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(DomainPackage.eNS_URI) instanceof DomainPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(DomainPackage.eNS_URI) : DomainPackage.eINSTANCE);
		ValidationPackageImpl theValidationPackage = (ValidationPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ValidationPackage.eNS_URI) instanceof ValidationPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ValidationPackage.eNS_URI) : ValidationPackage.eINSTANCE);
		DesigntimelibPackageImpl theDesigntimelibPackage = (DesigntimelibPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(DesigntimelibPackage.eNS_URI) instanceof DesigntimelibPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(DesigntimelibPackage.eNS_URI) : DesigntimelibPackage.eINSTANCE);
		JavaPackageImpl theJavaPackage = (JavaPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(JavaPackage.eNS_URI) instanceof JavaPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(JavaPackage.eNS_URI) : JavaPackage.eINSTANCE);

		// Create package meta-data objects
		theRulePackage.createPackageContents();
		theModelPackage.createPackageContents();
		theElementPackage.createPackageContents();
		theEventPackage.createPackageContents();
		theChannelPackage.createPackageContents();
		theStatesPackage.createPackageContents();
		theArchivePackage.createPackageContents();
		theConfigPackage.createPackageContents();
		theDomainPackage.createPackageContents();
		theValidationPackage.createPackageContents();
		theDesigntimelibPackage.createPackageContents();
		theJavaPackage.createPackageContents();

		// Initialize created meta-data
		theRulePackage.initializePackageContents();
		theModelPackage.initializePackageContents();
		theElementPackage.initializePackageContents();
		theEventPackage.initializePackageContents();
		theChannelPackage.initializePackageContents();
		theStatesPackage.initializePackageContents();
		theArchivePackage.initializePackageContents();
		theConfigPackage.initializePackageContents();
		theDomainPackage.initializePackageContents();
		theValidationPackage.initializePackageContents();
		theDesigntimelibPackage.initializePackageContents();
		theJavaPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theRulePackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(RulePackage.eNS_URI, theRulePackage);
		return theRulePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getScopeContainer() {
		return scopeContainerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getScopeContainer_Symbols() {
		return (EReference)scopeContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCompilable() {
		return compilableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCompilable_CompilationStatus() {
		return (EAttribute)compilableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCompilable_ReturnType() {
		return (EAttribute)compilableEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCompilable_FullSourceText() {
		return (EAttribute)compilableEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCompilable_ActionText() {
		return (EAttribute)compilableEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCompilable_ConditionText() {
		return (EAttribute)compilableEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCompilable_Rank() {
		return (EAttribute)compilableEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRule() {
		return ruleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRule_Priority() {
		return (EAttribute)ruleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRule_MaxRules() {
		return (EAttribute)ruleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRule_TestInterval() {
		return (EAttribute)ruleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRule_StartTime() {
		return (EAttribute)ruleEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRule_Requeue() {
		return (EAttribute)ruleEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRule_RequeueVars() {
		return (EAttribute)ruleEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRule_BackwardChain() {
		return (EAttribute)ruleEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRule_ForwardChain() {
		return (EAttribute)ruleEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRule_ConditionFunction() {
		return (EAttribute)ruleEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRule_Function() {
		return (EAttribute)ruleEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRule_Author() {
		return (EAttribute)ruleEClass.getEStructuralFeatures().get(10);
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
	public EAttribute getRuleFunction_Validity() {
		return (EAttribute)ruleFunctionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleFunction_Virtual() {
		return (EAttribute)ruleFunctionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleFunction_Alias() {
		return (EAttribute)ruleFunctionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRuleSet() {
		return ruleSetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRuleSet_Rules() {
		return (EReference)ruleSetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSymbol() {
		return symbolEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSymbol_IdName() {
		return (EAttribute)symbolEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSymbol_Type() {
		return (EAttribute)symbolEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSymbol_TypeExtension() {
		return (EAttribute)symbolEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSymbol_Domain() {
		return (EReference)symbolEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSymbol_Array() {
		return (EAttribute)symbolEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRuleFunctionSymbol() {
		return ruleFunctionSymbolEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleFunctionSymbol_Direction() {
		return (EAttribute)ruleFunctionSymbolEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleFunctionSymbol_ResourceType() {
		return (EAttribute)ruleFunctionSymbolEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleFunctionSymbol_DomainOverridden() {
		return (EAttribute)ruleFunctionSymbolEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleFunctionSymbol_GraphicalPath() {
		return (EAttribute)ruleFunctionSymbolEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleFunctionSymbol_PrimitiveType() {
		return (EAttribute)ruleFunctionSymbolEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSymbols() {
		return symbolsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSymbols_SymbolList() {
		return (EReference)symbolsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSymbolMapEntry() {
		return symbolMapEntryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSymbolMapEntry_Key() {
		return (EAttribute)symbolMapEntryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSymbolMapEntry_Value() {
		return (EReference)symbolMapEntryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRuleTemplateSymbol() {
		return ruleTemplateSymbolEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleTemplateSymbol_Expression() {
		return (EAttribute)ruleTemplateSymbolEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getActionContextSymbol() {
		return actionContextSymbolEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getActionContextSymbol_ActionType() {
		return (EAttribute)actionContextSymbolEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRuleTemplate() {
		return ruleTemplateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRuleTemplate_Bindings() {
		return (EReference)ruleTemplateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRuleTemplate_DisplayProperties() {
		return (EReference)ruleTemplateEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRuleTemplate_ActionContext() {
		return (EReference)ruleTemplateEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleTemplate_Views() {
		return (EAttribute)ruleTemplateEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getActionContext() {
		return actionContextEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActionContext_ActionContextSymbols() {
		return (EReference)actionContextEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBinding() {
		return bindingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBinding_Expression() {
		return (EAttribute)bindingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBinding_DomainModelPath() {
		return (EAttribute)bindingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRuleTemplateView() {
		return ruleTemplateViewEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleTemplateView_RuleTemplatePath() {
		return (EAttribute)ruleTemplateViewEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleTemplateView_PresentationText() {
		return (EAttribute)ruleTemplateViewEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleTemplateView_HtmlFile() {
		return (EAttribute)ruleTemplateViewEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getXsltFunction() {
		return xsltFunctionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getValidity() {
		return validityEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getActionType() {
		return actionTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleFactory getRuleFactory() {
		return (RuleFactory)getEFactoryInstance();
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
		scopeContainerEClass = createEClass(SCOPE_CONTAINER);
		createEReference(scopeContainerEClass, SCOPE_CONTAINER__SYMBOLS);

		compilableEClass = createEClass(COMPILABLE);
		createEAttribute(compilableEClass, COMPILABLE__COMPILATION_STATUS);
		createEAttribute(compilableEClass, COMPILABLE__RETURN_TYPE);
		createEAttribute(compilableEClass, COMPILABLE__FULL_SOURCE_TEXT);
		createEAttribute(compilableEClass, COMPILABLE__ACTION_TEXT);
		createEAttribute(compilableEClass, COMPILABLE__CONDITION_TEXT);
		createEAttribute(compilableEClass, COMPILABLE__RANK);

		ruleEClass = createEClass(RULE);
		createEAttribute(ruleEClass, RULE__PRIORITY);
		createEAttribute(ruleEClass, RULE__MAX_RULES);
		createEAttribute(ruleEClass, RULE__TEST_INTERVAL);
		createEAttribute(ruleEClass, RULE__START_TIME);
		createEAttribute(ruleEClass, RULE__REQUEUE);
		createEAttribute(ruleEClass, RULE__REQUEUE_VARS);
		createEAttribute(ruleEClass, RULE__BACKWARD_CHAIN);
		createEAttribute(ruleEClass, RULE__FORWARD_CHAIN);
		createEAttribute(ruleEClass, RULE__CONDITION_FUNCTION);
		createEAttribute(ruleEClass, RULE__FUNCTION);
		createEAttribute(ruleEClass, RULE__AUTHOR);

		ruleFunctionEClass = createEClass(RULE_FUNCTION);
		createEAttribute(ruleFunctionEClass, RULE_FUNCTION__VALIDITY);
		createEAttribute(ruleFunctionEClass, RULE_FUNCTION__VIRTUAL);
		createEAttribute(ruleFunctionEClass, RULE_FUNCTION__ALIAS);

		ruleSetEClass = createEClass(RULE_SET);
		createEReference(ruleSetEClass, RULE_SET__RULES);

		symbolEClass = createEClass(SYMBOL);
		createEAttribute(symbolEClass, SYMBOL__ID_NAME);
		createEAttribute(symbolEClass, SYMBOL__TYPE);
		createEAttribute(symbolEClass, SYMBOL__TYPE_EXTENSION);
		createEReference(symbolEClass, SYMBOL__DOMAIN);
		createEAttribute(symbolEClass, SYMBOL__ARRAY);

		ruleFunctionSymbolEClass = createEClass(RULE_FUNCTION_SYMBOL);
		createEAttribute(ruleFunctionSymbolEClass, RULE_FUNCTION_SYMBOL__DIRECTION);
		createEAttribute(ruleFunctionSymbolEClass, RULE_FUNCTION_SYMBOL__RESOURCE_TYPE);
		createEAttribute(ruleFunctionSymbolEClass, RULE_FUNCTION_SYMBOL__DOMAIN_OVERRIDDEN);
		createEAttribute(ruleFunctionSymbolEClass, RULE_FUNCTION_SYMBOL__GRAPHICAL_PATH);
		createEAttribute(ruleFunctionSymbolEClass, RULE_FUNCTION_SYMBOL__PRIMITIVE_TYPE);

		symbolsEClass = createEClass(SYMBOLS);
		createEReference(symbolsEClass, SYMBOLS__SYMBOL_LIST);

		symbolMapEntryEClass = createEClass(SYMBOL_MAP_ENTRY);
		createEAttribute(symbolMapEntryEClass, SYMBOL_MAP_ENTRY__KEY);
		createEReference(symbolMapEntryEClass, SYMBOL_MAP_ENTRY__VALUE);

		ruleTemplateSymbolEClass = createEClass(RULE_TEMPLATE_SYMBOL);
		createEAttribute(ruleTemplateSymbolEClass, RULE_TEMPLATE_SYMBOL__EXPRESSION);

		actionContextSymbolEClass = createEClass(ACTION_CONTEXT_SYMBOL);
		createEAttribute(actionContextSymbolEClass, ACTION_CONTEXT_SYMBOL__ACTION_TYPE);

		ruleTemplateEClass = createEClass(RULE_TEMPLATE);
		createEReference(ruleTemplateEClass, RULE_TEMPLATE__BINDINGS);
		createEReference(ruleTemplateEClass, RULE_TEMPLATE__DISPLAY_PROPERTIES);
		createEReference(ruleTemplateEClass, RULE_TEMPLATE__ACTION_CONTEXT);
		createEAttribute(ruleTemplateEClass, RULE_TEMPLATE__VIEWS);

		actionContextEClass = createEClass(ACTION_CONTEXT);
		createEReference(actionContextEClass, ACTION_CONTEXT__ACTION_CONTEXT_SYMBOLS);

		bindingEClass = createEClass(BINDING);
		createEAttribute(bindingEClass, BINDING__EXPRESSION);
		createEAttribute(bindingEClass, BINDING__DOMAIN_MODEL_PATH);

		ruleTemplateViewEClass = createEClass(RULE_TEMPLATE_VIEW);
		createEAttribute(ruleTemplateViewEClass, RULE_TEMPLATE_VIEW__RULE_TEMPLATE_PATH);
		createEAttribute(ruleTemplateViewEClass, RULE_TEMPLATE_VIEW__PRESENTATION_TEXT);
		createEAttribute(ruleTemplateViewEClass, RULE_TEMPLATE_VIEW__HTML_FILE);

		xsltFunctionEClass = createEClass(XSLT_FUNCTION);

		// Create enums
		validityEEnum = createEEnum(VALIDITY);
		actionTypeEEnum = createEEnum(ACTION_TYPE);
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
		DomainPackage theDomainPackage = (DomainPackage)EPackage.Registry.INSTANCE.getEPackage(DomainPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		compilableEClass.getESuperTypes().add(theModelPackage.getEntity());
		compilableEClass.getESuperTypes().add(this.getScopeContainer());
		ruleEClass.getESuperTypes().add(this.getCompilable());
		ruleFunctionEClass.getESuperTypes().add(this.getCompilable());
		ruleSetEClass.getESuperTypes().add(theModelPackage.getEntity());
		ruleFunctionSymbolEClass.getESuperTypes().add(this.getSymbol());
		ruleTemplateSymbolEClass.getESuperTypes().add(this.getSymbol());
		actionContextSymbolEClass.getESuperTypes().add(this.getSymbol());
		ruleTemplateEClass.getESuperTypes().add(this.getRule());
		bindingEClass.getESuperTypes().add(this.getSymbol());
		ruleTemplateViewEClass.getESuperTypes().add(theModelPackage.getEntity());
		xsltFunctionEClass.getESuperTypes().add(this.getRuleFunction());

		// Initialize classes and features; add operations and parameters
		initEClass(scopeContainerEClass, ScopeContainer.class, "ScopeContainer", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getScopeContainer_Symbols(), this.getSymbols(), null, "symbols", null, 0, 1, ScopeContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(compilableEClass, Compilable.class, "Compilable", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCompilable_CompilationStatus(), ecorePackage.getEInt(), "compilationStatus", null, 1, 1, Compilable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCompilable_ReturnType(), ecorePackage.getEString(), "returnType", null, 1, 1, Compilable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCompilable_FullSourceText(), ecorePackage.getEString(), "fullSourceText", null, 1, 1, Compilable.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCompilable_ActionText(), ecorePackage.getEString(), "actionText", null, 1, 1, Compilable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCompilable_ConditionText(), ecorePackage.getEString(), "conditionText", null, 1, 1, Compilable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCompilable_Rank(), ecorePackage.getEString(), "rank", null, 1, 1, Compilable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ruleEClass, Rule.class, "Rule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRule_Priority(), ecorePackage.getEInt(), "priority", null, 1, 1, Rule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRule_MaxRules(), ecorePackage.getEInt(), "maxRules", null, 1, 1, Rule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRule_TestInterval(), ecorePackage.getELong(), "testInterval", null, 1, 1, Rule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRule_StartTime(), ecorePackage.getELong(), "startTime", null, 1, 1, Rule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRule_Requeue(), ecorePackage.getEBoolean(), "requeue", null, 0, 1, Rule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRule_RequeueVars(), ecorePackage.getEString(), "requeueVars", null, 0, -1, Rule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRule_BackwardChain(), ecorePackage.getEBoolean(), "backwardChain", null, 0, 1, Rule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRule_ForwardChain(), ecorePackage.getEBoolean(), "forwardChain", null, 0, 1, Rule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRule_ConditionFunction(), ecorePackage.getEBoolean(), "conditionFunction", null, 0, 1, Rule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRule_Function(), ecorePackage.getEBoolean(), "function", null, 0, 1, Rule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRule_Author(), ecorePackage.getEString(), "author", null, 1, 1, Rule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(ruleEClass, ecorePackage.getEBoolean(), "isEmpty", 0, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(ruleEClass, this.getRuleFunction(), "getRankRuleFunction", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(ruleFunctionEClass, RuleFunction.class, "RuleFunction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRuleFunction_Validity(), this.getValidity(), "validity", null, 0, 1, RuleFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleFunction_Virtual(), ecorePackage.getEBoolean(), "virtual", null, 0, 1, RuleFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleFunction_Alias(), ecorePackage.getEString(), "alias", null, 0, 1, RuleFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ruleSetEClass, RuleSet.class, "RuleSet", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRuleSet_Rules(), this.getRule(), null, "rules", null, 0, -1, RuleSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(symbolEClass, Symbol.class, "Symbol", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSymbol_IdName(), ecorePackage.getEString(), "idName", null, 1, 1, Symbol.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSymbol_Type(), ecorePackage.getEString(), "type", null, 1, 1, Symbol.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSymbol_TypeExtension(), ecorePackage.getEString(), "typeExtension", null, 1, 1, Symbol.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSymbol_Domain(), theDomainPackage.getDomain(), null, "domain", null, 0, 1, Symbol.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSymbol_Array(), ecorePackage.getEBoolean(), "array", null, 1, 1, Symbol.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ruleFunctionSymbolEClass, RuleFunctionSymbol.class, "RuleFunctionSymbol", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRuleFunctionSymbol_Direction(), ecorePackage.getEString(), "direction", "IN", 0, 1, RuleFunctionSymbol.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleFunctionSymbol_ResourceType(), ecorePackage.getEString(), "resourceType", "NULL", 0, 1, RuleFunctionSymbol.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleFunctionSymbol_DomainOverridden(), ecorePackage.getEBoolean(), "domainOverridden", null, 0, 1, RuleFunctionSymbol.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleFunctionSymbol_GraphicalPath(), ecorePackage.getEString(), "graphicalPath", null, 0, 1, RuleFunctionSymbol.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleFunctionSymbol_PrimitiveType(), ecorePackage.getEString(), "primitiveType", "NULL", 0, 1, RuleFunctionSymbol.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(symbolsEClass, Symbols.class, "Symbols", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSymbols_SymbolList(), this.getSymbol(), null, "symbolList", null, 0, -1, Symbols.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = addEOperation(symbolsEClass, null, "getSymbolMap", 0, 1, IS_UNIQUE, IS_ORDERED);
		EGenericType g1 = createEGenericType(ecorePackage.getEMap());
		EGenericType g2 = createEGenericType(ecorePackage.getEString());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(this.getSymbol());
		g1.getETypeArguments().add(g2);
		initEOperation(op, g1);

		initEClass(symbolMapEntryEClass, Map.Entry.class, "SymbolMapEntry", !IS_ABSTRACT, !IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSymbolMapEntry_Key(), ecorePackage.getEString(), "key", null, 1, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSymbolMapEntry_Value(), this.getSymbol(), null, "value", null, 1, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ruleTemplateSymbolEClass, RuleTemplateSymbol.class, "RuleTemplateSymbol", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRuleTemplateSymbol_Expression(), ecorePackage.getEString(), "expression", "", 0, 1, RuleTemplateSymbol.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(actionContextSymbolEClass, ActionContextSymbol.class, "ActionContextSymbol", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getActionContextSymbol_ActionType(), this.getActionType(), "actionType", "create", 0, 1, ActionContextSymbol.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ruleTemplateEClass, RuleTemplate.class, "RuleTemplate", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRuleTemplate_Bindings(), this.getBinding(), null, "bindings", null, 0, -1, RuleTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRuleTemplate_DisplayProperties(), theModelPackage.getSimpleProperty(), null, "displayProperties", null, 0, -1, RuleTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRuleTemplate_ActionContext(), this.getActionContext(), null, "actionContext", null, 1, 1, RuleTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleTemplate_Views(), ecorePackage.getEString(), "views", null, 0, -1, RuleTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(actionContextEClass, ActionContext.class, "ActionContext", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getActionContext_ActionContextSymbols(), this.getSymbols(), null, "actionContextSymbols", null, 0, 1, ActionContext.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(bindingEClass, Binding.class, "Binding", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBinding_Expression(), ecorePackage.getEString(), "expression", "", 0, 1, Binding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBinding_DomainModelPath(), ecorePackage.getEString(), "domainModelPath", "", 0, 1, Binding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ruleTemplateViewEClass, RuleTemplateView.class, "RuleTemplateView", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRuleTemplateView_RuleTemplatePath(), ecorePackage.getEString(), "ruleTemplatePath", "", 0, 1, RuleTemplateView.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleTemplateView_PresentationText(), ecorePackage.getEString(), "presentationText", "", 0, 1, RuleTemplateView.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleTemplateView_HtmlFile(), ecorePackage.getEString(), "htmlFile", "", 0, 1, RuleTemplateView.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xsltFunctionEClass, XsltFunction.class, "XsltFunction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Initialize enums and add enum literals
		initEEnum(validityEEnum, Validity.class, "Validity");
		addEEnumLiteral(validityEEnum, Validity.ACTION);
		addEEnumLiteral(validityEEnum, Validity.CONDITION);
		addEEnumLiteral(validityEEnum, Validity.QUERY);

		initEEnum(actionTypeEEnum, ActionType.class, "ActionType");
		addEEnumLiteral(actionTypeEEnum, ActionType.CREATE);
		addEEnumLiteral(actionTypeEEnum, ActionType.MODIFY);
		addEEnumLiteral(actionTypeEEnum, ActionType.CALL);
	}

} //RulePackageImpl
