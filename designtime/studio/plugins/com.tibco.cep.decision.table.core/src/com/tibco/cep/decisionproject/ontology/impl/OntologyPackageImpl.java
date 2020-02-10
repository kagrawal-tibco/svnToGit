/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionproject.ontology.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage;
import com.tibco.cep.decision.table.model.dtmodel.impl.DtmodelPackageImpl;
import com.tibco.cep.decisionproject.ontology.AbstractResource;
import com.tibco.cep.decisionproject.ontology.AccessControlCandidate;
import com.tibco.cep.decisionproject.ontology.Argument;
import com.tibco.cep.decisionproject.ontology.ArgumentResource;
import com.tibco.cep.decisionproject.ontology.Arguments;
import com.tibco.cep.decisionproject.ontology.Concept;
import com.tibco.cep.decisionproject.ontology.DTRule;
import com.tibco.cep.decisionproject.ontology.Event;
import com.tibco.cep.decisionproject.ontology.Folder;
import com.tibco.cep.decisionproject.ontology.Header;
import com.tibco.cep.decisionproject.ontology.Implementation;
import com.tibco.cep.decisionproject.ontology.Ontology;
import com.tibco.cep.decisionproject.ontology.OntologyFactory;
import com.tibco.cep.decisionproject.ontology.OntologyPackage;
import com.tibco.cep.decisionproject.ontology.ParentResource;
import com.tibco.cep.decisionproject.ontology.PrimitiveHolder;
import com.tibco.cep.decisionproject.ontology.Property;
import com.tibco.cep.decisionproject.ontology.Rule;
import com.tibco.cep.decisionproject.ontology.RuleFunction;
import com.tibco.cep.decisionproject.ontology.RuleSet;
import com.tibco.cep.decisionproject.ontology.RuleSetParticipant;
import com.tibco.cep.decisionprojectmodel.DecisionProjectModelPackage;
import com.tibco.cep.decisionprojectmodel.impl.DecisionProjectModelPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class OntologyPackageImpl extends EPackageImpl implements OntologyPackage {
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
	private EClass folderEClass = null;

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
	private EClass eventEClass = null;

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
	private EClass propertyEClass = null;

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
	private EClass ontologyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ruleSetParticipantEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dtRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass headerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass argumentsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass implementationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass argumentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass accessControlCandidateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass primitiveHolderEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass argumentResourceEClass = null;

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
	 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private OntologyPackageImpl() {
		super(eNS_URI, OntologyFactory.eINSTANCE);
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
	public static OntologyPackage init() {
		if (isInited) return (OntologyPackage)EPackage.Registry.INSTANCE.getEPackage(OntologyPackage.eNS_URI);

		// Obtain or create and register package
		OntologyPackageImpl theOntologyPackage = (OntologyPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof OntologyPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new OntologyPackageImpl());

		isInited = true;

		// Initialize simple dependencies

		// Obtain or create and register interdependencies
		DecisionProjectModelPackageImpl theDecisionProjectModelPackage = (DecisionProjectModelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(DecisionProjectModelPackage.eNS_URI) instanceof DecisionProjectModelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(DecisionProjectModelPackage.eNS_URI) : DecisionProjectModelPackage.eINSTANCE);
		DtmodelPackageImpl theDtmodelPackage = (DtmodelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(DtmodelPackage.eNS_URI) instanceof DtmodelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(DtmodelPackage.eNS_URI) : DtmodelPackage.eINSTANCE);
		//DomainModelPackageImpl theDomainModelPackage = (DomainModelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(DomainModelPackage.eNS_URI) instanceof DomainModelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(DomainModelPackage.eNS_URI) : DomainModelPackage.eINSTANCE);

		// Create package meta-data objects
		theOntologyPackage.createPackageContents();
		theDecisionProjectModelPackage.createPackageContents();
		theDtmodelPackage.createPackageContents();
		//theDomainModelPackage.createPackageContents();

		// Initialize created meta-data
		theOntologyPackage.initializePackageContents();
		theDecisionProjectModelPackage.initializePackageContents();
		theDtmodelPackage.initializePackageContents();
		//theDomainModelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theOntologyPackage.freeze();

		return theOntologyPackage;
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
	public EAttribute getAbstractResource_Description() {
		return (EAttribute)abstractResourceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAbstractResource_Folder() {
		return (EAttribute)abstractResourceEClass.getEStructuralFeatures().get(2);
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
	public EClass getConcept() {
		return conceptEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConcept_SuperConceptPath() {
		return (EAttribute)conceptEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConcept_ScoreCard() {
		return (EAttribute)conceptEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConcept_Property() {
		return (EReference)conceptEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConcept_DbConcept() {
		return (EAttribute)conceptEClass.getEStructuralFeatures().get(3);
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
	public EAttribute getEvent_Ttl() {
		return (EAttribute)eventEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEvent_TtlUnits() {
		return (EAttribute)eventEClass.getEStructuralFeatures().get(1);
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
	public EAttribute getEvent_DefaultDestinationName() {
		return (EAttribute)eventEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEvent_ExpiryAction() {
		return (EReference)eventEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEvent_UserProperty() {
		return (EReference)eventEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEvent_Type() {
		return (EAttribute)eventEClass.getEStructuralFeatures().get(6);
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
	public EReference getRuleSet_RuleSetParicipant() {
		return (EReference)ruleSetEClass.getEStructuralFeatures().get(0);
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
	public EClass getRuleFunction() {
		return ruleFunctionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRuleFunction_Header() {
		return (EReference)ruleFunctionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleFunction_Body() {
		return (EAttribute)ruleFunctionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRuleFunction_Arguments() {
		return (EReference)ruleFunctionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProperty() {
		return propertyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProperty_DataType() {
		return (EAttribute)propertyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProperty_HistorySize() {
		return (EAttribute)propertyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProperty_HistoryPolicy() {
		return (EAttribute)propertyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProperty_Multiple() {
		return (EAttribute)propertyEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProperty_DefaultValue() {
		return (EAttribute)propertyEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProperty_PropertyResourceType() {
		return (EAttribute)propertyEClass.getEStructuralFeatures().get(5);
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
	public EClass getOntology() {
		return ontologyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOntology_Resource() {
		return (EReference)ontologyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRuleSetParticipant() {
		return ruleSetParticipantEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleSetParticipant_Path() {
		return (EAttribute)ruleSetParticipantEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleSetParticipant_ParentPath() {
		return (EAttribute)ruleSetParticipantEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDTRule() {
		return dtRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDTRule_Table() {
		return (EReference)dtRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getHeader() {
		return headerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getHeader_Property() {
		return (EReference)headerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getArguments() {
		return argumentsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getArguments_Argument() {
		return (EReference)argumentsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getImplementation() {
		return implementationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getImplementation_Style() {
		return (EAttribute)implementationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getImplementation_Implements() {
		return (EAttribute)implementationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getImplementation_Version() {
		return (EAttribute)implementationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getImplementation_LastModifiedBy() {
		return (EAttribute)implementationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getImplementation_LastModified() {
		return (EAttribute)implementationEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getImplementation_Dirty() {
		return (EAttribute)implementationEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getImplementation_Locked() {
		return (EAttribute)implementationEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getImplementation_ShowDescription() {
		return (EAttribute)implementationEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getArgument() {
		return argumentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getArgument_ArgumentEntry() {
		return (EReference)argumentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAccessControlCandidate() {
		return accessControlCandidateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAccessControlCandidate_Modified() {
		return (EAttribute)accessControlCandidateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPrimitiveHolder() {
		return primitiveHolderEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPrimitiveHolder_PrimitiveType() {
		return (EAttribute)primitiveHolderEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getArgumentResource() {
		return argumentResourceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getArgumentResource_Alias() {
		return (EAttribute)argumentResourceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OntologyFactory getOntologyFactory() {
		return (OntologyFactory)getEFactoryInstance();
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
		abstractResourceEClass = createEClass(ABSTRACT_RESOURCE);
		createEAttribute(abstractResourceEClass, ABSTRACT_RESOURCE__NAME);
		createEAttribute(abstractResourceEClass, ABSTRACT_RESOURCE__DESCRIPTION);
		createEAttribute(abstractResourceEClass, ABSTRACT_RESOURCE__FOLDER);

		folderEClass = createEClass(FOLDER);
		createEReference(folderEClass, FOLDER__RESOURCE);

		conceptEClass = createEClass(CONCEPT);
		createEAttribute(conceptEClass, CONCEPT__SUPER_CONCEPT_PATH);
		createEAttribute(conceptEClass, CONCEPT__SCORE_CARD);
		createEReference(conceptEClass, CONCEPT__PROPERTY);
		createEAttribute(conceptEClass, CONCEPT__DB_CONCEPT);

		eventEClass = createEClass(EVENT);
		createEAttribute(eventEClass, EVENT__TTL);
		createEAttribute(eventEClass, EVENT__TTL_UNITS);
		createEAttribute(eventEClass, EVENT__SUPER_EVENT_PATH);
		createEAttribute(eventEClass, EVENT__DEFAULT_DESTINATION_NAME);
		createEReference(eventEClass, EVENT__EXPIRY_ACTION);
		createEReference(eventEClass, EVENT__USER_PROPERTY);
		createEAttribute(eventEClass, EVENT__TYPE);

		ruleSetEClass = createEClass(RULE_SET);
		createEReference(ruleSetEClass, RULE_SET__RULE_SET_PARICIPANT);

		ruleEClass = createEClass(RULE);

		ruleFunctionEClass = createEClass(RULE_FUNCTION);
		createEReference(ruleFunctionEClass, RULE_FUNCTION__HEADER);
		createEReference(ruleFunctionEClass, RULE_FUNCTION__ARGUMENTS);
		createEAttribute(ruleFunctionEClass, RULE_FUNCTION__BODY);

		propertyEClass = createEClass(PROPERTY);
		createEAttribute(propertyEClass, PROPERTY__DATA_TYPE);
		createEAttribute(propertyEClass, PROPERTY__HISTORY_SIZE);
		createEAttribute(propertyEClass, PROPERTY__HISTORY_POLICY);
		createEAttribute(propertyEClass, PROPERTY__MULTIPLE);
		createEAttribute(propertyEClass, PROPERTY__DEFAULT_VALUE);
		createEAttribute(propertyEClass, PROPERTY__PROPERTY_RESOURCE_TYPE);

		parentResourceEClass = createEClass(PARENT_RESOURCE);

		ontologyEClass = createEClass(ONTOLOGY);
		createEReference(ontologyEClass, ONTOLOGY__RESOURCE);

		ruleSetParticipantEClass = createEClass(RULE_SET_PARTICIPANT);
		createEAttribute(ruleSetParticipantEClass, RULE_SET_PARTICIPANT__PATH);
		createEAttribute(ruleSetParticipantEClass, RULE_SET_PARTICIPANT__PARENT_PATH);

		dtRuleEClass = createEClass(DT_RULE);
		createEReference(dtRuleEClass, DT_RULE__TABLE);

		headerEClass = createEClass(HEADER);
		createEReference(headerEClass, HEADER__PROPERTY);

		argumentsEClass = createEClass(ARGUMENTS);
		createEReference(argumentsEClass, ARGUMENTS__ARGUMENT);

		implementationEClass = createEClass(IMPLEMENTATION);
		createEAttribute(implementationEClass, IMPLEMENTATION__STYLE);
		createEAttribute(implementationEClass, IMPLEMENTATION__IMPLEMENTS);
		createEAttribute(implementationEClass, IMPLEMENTATION__VERSION);
		createEAttribute(implementationEClass, IMPLEMENTATION__LAST_MODIFIED_BY);
		createEAttribute(implementationEClass, IMPLEMENTATION__LAST_MODIFIED);
		createEAttribute(implementationEClass, IMPLEMENTATION__DIRTY);
		createEAttribute(implementationEClass, IMPLEMENTATION__LOCKED);
		createEAttribute(implementationEClass, IMPLEMENTATION__SHOW_DESCRIPTION);

		argumentEClass = createEClass(ARGUMENT);
		createEReference(argumentEClass, ARGUMENT__ARGUMENT_ENTRY);

		accessControlCandidateEClass = createEClass(ACCESS_CONTROL_CANDIDATE);
		createEAttribute(accessControlCandidateEClass, ACCESS_CONTROL_CANDIDATE__MODIFIED);

		primitiveHolderEClass = createEClass(PRIMITIVE_HOLDER);
		createEAttribute(primitiveHolderEClass, PRIMITIVE_HOLDER__PRIMITIVE_TYPE);

		argumentResourceEClass = createEClass(ARGUMENT_RESOURCE);
		createEAttribute(argumentResourceEClass, ARGUMENT_RESOURCE__ALIAS);
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
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);
		DtmodelPackage theDtmodelPackage = (DtmodelPackage)EPackage.Registry.INSTANCE.getEPackage(DtmodelPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		abstractResourceEClass.getESuperTypes().add(this.getAccessControlCandidate());
		folderEClass.getESuperTypes().add(this.getParentResource());
		conceptEClass.getESuperTypes().add(this.getParentResource());
		conceptEClass.getESuperTypes().add(this.getArgumentResource());
		eventEClass.getESuperTypes().add(this.getParentResource());
		eventEClass.getESuperTypes().add(this.getArgumentResource());
		ruleSetEClass.getESuperTypes().add(this.getParentResource());
		ruleEClass.getESuperTypes().add(this.getRuleSetParticipant());
		ruleFunctionEClass.getESuperTypes().add(this.getAbstractResource());
		propertyEClass.getESuperTypes().add(this.getAbstractResource());
		parentResourceEClass.getESuperTypes().add(this.getAbstractResource());
		ontologyEClass.getESuperTypes().add(this.getParentResource());
		ruleSetParticipantEClass.getESuperTypes().add(this.getAbstractResource());
		dtRuleEClass.getESuperTypes().add(this.getRuleSetParticipant());
		implementationEClass.getESuperTypes().add(this.getAbstractResource());
		primitiveHolderEClass.getESuperTypes().add(this.getAbstractResource());
		primitiveHolderEClass.getESuperTypes().add(this.getArgumentResource());
		argumentResourceEClass.getESuperTypes().add(this.getAbstractResource());

		// Initialize classes and features; add operations and parameters
		initEClass(abstractResourceEClass, AbstractResource.class, "AbstractResource", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAbstractResource_Name(), ecorePackage.getEString(), "name", null, 0, 1, AbstractResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractResource_Description(), ecorePackage.getEString(), "description", null, 0, 1, AbstractResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractResource_Folder(), ecorePackage.getEString(), "folder", null, 0, 1, AbstractResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(folderEClass, Folder.class, "Folder", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFolder_Resource(), this.getAbstractResource(), null, "resource", null, 0, -1, Folder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(conceptEClass, Concept.class, "Concept", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getConcept_SuperConceptPath(), ecorePackage.getEString(), "superConceptPath", null, 0, 1, Concept.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConcept_ScoreCard(), ecorePackage.getEBoolean(), "scoreCard", null, 0, 1, Concept.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConcept_Property(), this.getProperty(), null, "property", null, 0, -1, Concept.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConcept_DbConcept(), theEcorePackage.getEBoolean(), "dbConcept", null, 0, 1, Concept.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eventEClass, Event.class, "Event", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEvent_Ttl(), ecorePackage.getEInt(), "ttl", null, 0, 1, Event.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEvent_TtlUnits(), ecorePackage.getEInt(), "ttlUnits", null, 0, 1, Event.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEvent_SuperEventPath(), ecorePackage.getEString(), "superEventPath", null, 0, 1, Event.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEvent_DefaultDestinationName(), ecorePackage.getEString(), "defaultDestinationName", null, 0, 1, Event.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEvent_ExpiryAction(), this.getRule(), null, "expiryAction", null, 0, 1, Event.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEvent_UserProperty(), this.getProperty(), null, "userProperty", null, 0, -1, Event.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEvent_Type(), ecorePackage.getEInt(), "type", "-1", 0, 1, Event.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ruleSetEClass, RuleSet.class, "RuleSet", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRuleSet_RuleSetParicipant(), this.getRuleSetParticipant(), null, "ruleSetParicipant", null, 0, -1, RuleSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ruleEClass, Rule.class, "Rule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(ruleFunctionEClass, RuleFunction.class, "RuleFunction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRuleFunction_Header(), this.getHeader(), null, "header", null, 0, 1, RuleFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRuleFunction_Arguments(), this.getArguments(), null, "arguments", null, 0, 1, RuleFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleFunction_Body(), theEcorePackage.getEString(), "body", null, 0, -1, RuleFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(propertyEClass, Property.class, "Property", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getProperty_DataType(), ecorePackage.getEInt(), "dataType", "-1", 0, 1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProperty_HistorySize(), ecorePackage.getEInt(), "historySize", "0", 0, 1, Property.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProperty_HistoryPolicy(), ecorePackage.getEInt(), "historyPolicy", "0", 0, 1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProperty_Multiple(), ecorePackage.getEBoolean(), "multiple", null, 0, 1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProperty_DefaultValue(), ecorePackage.getEString(), "defaultValue", null, 0, 1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProperty_PropertyResourceType(), ecorePackage.getEString(), "propertyResourceType", null, 0, 1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(parentResourceEClass, ParentResource.class, "ParentResource", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		EOperation op = addEOperation(parentResourceEClass, null, "getChildren", 0, 1, IS_UNIQUE, IS_ORDERED);
		EGenericType g1 = createEGenericType(ecorePackage.getETreeIterator());
		EGenericType g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		initEOperation(op, g1);

		initEClass(ontologyEClass, Ontology.class, "Ontology", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getOntology_Resource(), this.getAbstractResource(), null, "resource", null, 0, -1, Ontology.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ruleSetParticipantEClass, RuleSetParticipant.class, "RuleSetParticipant", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRuleSetParticipant_Path(), theEcorePackage.getEString(), "path", null, 0, 1, RuleSetParticipant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleSetParticipant_ParentPath(), theEcorePackage.getEString(), "parentPath", null, 0, 1, RuleSetParticipant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dtRuleEClass, DTRule.class, "DTRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDTRule_Table(), theDtmodelPackage.getTable(), null, "table", null, 0, 1, DTRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(headerEClass, Header.class, "Header", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getHeader_Property(), theDtmodelPackage.getProperty(), null, "property", null, 0, -1, Header.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(argumentsEClass, Arguments.class, "Arguments", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getArguments_Argument(), theDtmodelPackage.getArgument(), null, "argument", null, 0, -1, Arguments.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(implementationEClass, Implementation.class, "Implementation", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getImplementation_Style(), theEcorePackage.getEString(), "style", null, 0, 1, Implementation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getImplementation_Implements(), theEcorePackage.getEString(), "implements", null, 1, 1, Implementation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getImplementation_Version(), theEcorePackage.getEDouble(), "version", null, 1, 1, Implementation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getImplementation_LastModifiedBy(), theEcorePackage.getEString(), "lastModifiedBy", null, 0, 1, Implementation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getImplementation_LastModified(), theEcorePackage.getEDate(), "lastModified", null, 0, 1, Implementation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getImplementation_Dirty(), theEcorePackage.getEBoolean(), "dirty", null, 0, 1, Implementation.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getImplementation_Locked(), theEcorePackage.getEBoolean(), "locked", null, 0, 1, Implementation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getImplementation_ShowDescription(), theEcorePackage.getEBoolean(), "showDescription", null, 0, 1, Implementation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(argumentEClass, Argument.class, "Argument", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getArgument_ArgumentEntry(), this.getAbstractResource(), null, "argumentEntry", null, 0, -1, Argument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(accessControlCandidateEClass, AccessControlCandidate.class, "AccessControlCandidate", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAccessControlCandidate_Modified(), theEcorePackage.getEBoolean(), "modified", null, 0, 1, AccessControlCandidate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(primitiveHolderEClass, PrimitiveHolder.class, "PrimitiveHolder", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPrimitiveHolder_PrimitiveType(), theEcorePackage.getEInt(), "primitiveType", null, 1, 1, PrimitiveHolder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(argumentResourceEClass, ArgumentResource.class, "ArgumentResource", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getArgumentResource_Alias(), theEcorePackage.getEString(), "alias", null, 0, 1, ArgumentResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //OntologyPackageImpl
