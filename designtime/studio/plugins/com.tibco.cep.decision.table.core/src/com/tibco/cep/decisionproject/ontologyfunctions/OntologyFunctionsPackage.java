/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionproject.ontologyfunctions;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.tibco.cep.decisionproject.ontologyfunctions.OntologyFunctionsFactory
 * @model kind="package"
 * @generated
 */
public interface OntologyFunctionsPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "ontologyfunctions";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http:///com/tibco/cep/ontologyfunctions/model/ontologyfunctions.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "onf";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	OntologyFunctionsPackage eINSTANCE = com.tibco.cep.decisionproject.ontologyfunctions.impl.OntologyFunctionsPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.tibco.cep.decisionproject.ontologyfunctions.impl.OntologyFunctionsRootImpl <em>Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.impl.OntologyFunctionsRootImpl
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.impl.OntologyFunctionsPackageImpl#getOntologyFunctionsRoot()
	 * @generated
	 */
	int ONTOLOGY_FUNCTIONS_ROOT = 0;

	/**
	 * The feature id for the '<em><b>Ontology Functions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONTOLOGY_FUNCTIONS_ROOT__ONTOLOGY_FUNCTIONS = 0;

	/**
	 * The number of structural features of the '<em>Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONTOLOGY_FUNCTIONS_ROOT_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decisionproject.ontologyfunctions.impl.AbstractResourceImpl <em>Abstract Resource</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.impl.AbstractResourceImpl
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.impl.OntologyFunctionsPackageImpl#getAbstractResource()
	 * @generated
	 */
	int ABSTRACT_RESOURCE = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_RESOURCE__NAME = 0;

	/**
	 * The number of structural features of the '<em>Abstract Resource</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_RESOURCE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decisionproject.ontologyfunctions.impl.ParentResourceImpl <em>Parent Resource</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.impl.ParentResourceImpl
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.impl.OntologyFunctionsPackageImpl#getParentResource()
	 * @generated
	 */
	int PARENT_RESOURCE = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARENT_RESOURCE__NAME = ABSTRACT_RESOURCE__NAME;

	/**
	 * The number of structural features of the '<em>Parent Resource</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARENT_RESOURCE_FEATURE_COUNT = ABSTRACT_RESOURCE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decisionproject.ontologyfunctions.impl.OntologyFunctionsImpl <em>Ontology Functions</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.impl.OntologyFunctionsImpl
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.impl.OntologyFunctionsPackageImpl#getOntologyFunctions()
	 * @generated
	 */
	int ONTOLOGY_FUNCTIONS = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONTOLOGY_FUNCTIONS__NAME = PARENT_RESOURCE__NAME;

	/**
	 * The feature id for the '<em><b>Resource</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONTOLOGY_FUNCTIONS__RESOURCE = PARENT_RESOURCE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Ontology Functions</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONTOLOGY_FUNCTIONS_FEATURE_COUNT = PARENT_RESOURCE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decisionproject.ontologyfunctions.impl.ConceptImpl <em>Concept</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.impl.ConceptImpl
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.impl.OntologyFunctionsPackageImpl#getConcept()
	 * @generated
	 */
	int CONCEPT = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT__NAME = ABSTRACT_RESOURCE__NAME;

	/**
	 * The feature id for the '<em><b>Function Label</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT__FUNCTION_LABEL = ABSTRACT_RESOURCE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Concept</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT_FEATURE_COUNT = ABSTRACT_RESOURCE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decisionproject.ontologyfunctions.impl.FolderImpl <em>Folder</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.impl.FolderImpl
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.impl.OntologyFunctionsPackageImpl#getFolder()
	 * @generated
	 */
	int FOLDER = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER__NAME = PARENT_RESOURCE__NAME;

	/**
	 * The feature id for the '<em><b>Resource</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER__RESOURCE = PARENT_RESOURCE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Folder</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER_FEATURE_COUNT = PARENT_RESOURCE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decisionproject.ontologyfunctions.impl.EventImpl <em>Event</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.impl.EventImpl
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.impl.OntologyFunctionsPackageImpl#getEvent()
	 * @generated
	 */
	int EVENT = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__NAME = ABSTRACT_RESOURCE__NAME;

	/**
	 * The feature id for the '<em><b>Function Label</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__FUNCTION_LABEL = ABSTRACT_RESOURCE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_FEATURE_COUNT = ABSTRACT_RESOURCE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decisionproject.ontologyfunctions.impl.RuleFunctionImpl <em>Rule Function</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.impl.RuleFunctionImpl
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.impl.OntologyFunctionsPackageImpl#getRuleFunction()
	 * @generated
	 */
	int RULE_FUNCTION = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FUNCTION__NAME = ABSTRACT_RESOURCE__NAME;

	/**
	 * The feature id for the '<em><b>Function Label</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FUNCTION__FUNCTION_LABEL = ABSTRACT_RESOURCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Validity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FUNCTION__VALIDITY = ABSTRACT_RESOURCE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Rule Function</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FUNCTION_FEATURE_COUNT = ABSTRACT_RESOURCE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decisionproject.ontologyfunctions.impl.FunctionLabelImpl <em>Function Label</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.impl.FunctionLabelImpl
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.impl.OntologyFunctionsPackageImpl#getFunctionLabel()
	 * @generated
	 */
	int FUNCTION_LABEL = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_LABEL__NAME = ABSTRACT_RESOURCE__NAME;

	/**
	 * The number of structural features of the '<em>Function Label</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_LABEL_FEATURE_COUNT = ABSTRACT_RESOURCE_FEATURE_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decisionproject.ontologyfunctions.OntologyFunctionsRoot <em>Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Root</em>'.
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.OntologyFunctionsRoot
	 * @generated
	 */
	EClass getOntologyFunctionsRoot();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.decisionproject.ontologyfunctions.OntologyFunctionsRoot#getOntologyFunctions <em>Ontology Functions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Ontology Functions</em>'.
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.OntologyFunctionsRoot#getOntologyFunctions()
	 * @see #getOntologyFunctionsRoot()
	 * @generated
	 */
	EReference getOntologyFunctionsRoot_OntologyFunctions();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decisionproject.ontologyfunctions.OntologyFunctions <em>Ontology Functions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Ontology Functions</em>'.
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.OntologyFunctions
	 * @generated
	 */
	EClass getOntologyFunctions();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.decisionproject.ontologyfunctions.OntologyFunctions#getResource <em>Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Resource</em>'.
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.OntologyFunctions#getResource()
	 * @see #getOntologyFunctions()
	 * @generated
	 */
	EReference getOntologyFunctions_Resource();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decisionproject.ontologyfunctions.AbstractResource <em>Abstract Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Resource</em>'.
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.AbstractResource
	 * @generated
	 */
	EClass getAbstractResource();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decisionproject.ontologyfunctions.AbstractResource#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.AbstractResource#getName()
	 * @see #getAbstractResource()
	 * @generated
	 */
	EAttribute getAbstractResource_Name();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decisionproject.ontologyfunctions.Concept <em>Concept</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Concept</em>'.
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.Concept
	 * @generated
	 */
	EClass getConcept();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.decisionproject.ontologyfunctions.Concept#getFunctionLabel <em>Function Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Function Label</em>'.
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.Concept#getFunctionLabel()
	 * @see #getConcept()
	 * @generated
	 */
	EReference getConcept_FunctionLabel();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decisionproject.ontologyfunctions.ParentResource <em>Parent Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parent Resource</em>'.
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.ParentResource
	 * @generated
	 */
	EClass getParentResource();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decisionproject.ontologyfunctions.Folder <em>Folder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Folder</em>'.
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.Folder
	 * @generated
	 */
	EClass getFolder();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.decisionproject.ontologyfunctions.Folder#getResource <em>Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Resource</em>'.
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.Folder#getResource()
	 * @see #getFolder()
	 * @generated
	 */
	EReference getFolder_Resource();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decisionproject.ontologyfunctions.Event <em>Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Event</em>'.
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.Event
	 * @generated
	 */
	EClass getEvent();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.decisionproject.ontologyfunctions.Event#getFunctionLabel <em>Function Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Function Label</em>'.
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.Event#getFunctionLabel()
	 * @see #getEvent()
	 * @generated
	 */
	EReference getEvent_FunctionLabel();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decisionproject.ontologyfunctions.RuleFunction <em>Rule Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rule Function</em>'.
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.RuleFunction
	 * @generated
	 */
	EClass getRuleFunction();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.decisionproject.ontologyfunctions.RuleFunction#getFunctionLabel <em>Function Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Function Label</em>'.
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.RuleFunction#getFunctionLabel()
	 * @see #getRuleFunction()
	 * @generated
	 */
	EReference getRuleFunction_FunctionLabel();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decisionproject.ontologyfunctions.RuleFunction#getValidity <em>Validity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Validity</em>'.
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.RuleFunction#getValidity()
	 * @see #getRuleFunction()
	 * @generated
	 */
	EAttribute getRuleFunction_Validity();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decisionproject.ontologyfunctions.FunctionLabel <em>Function Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Function Label</em>'.
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.FunctionLabel
	 * @generated
	 */
	EClass getFunctionLabel();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	OntologyFunctionsFactory getOntologyFunctionsFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link com.tibco.cep.decisionproject.ontologyfunctions.impl.OntologyFunctionsRootImpl <em>Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decisionproject.ontologyfunctions.impl.OntologyFunctionsRootImpl
		 * @see com.tibco.cep.decisionproject.ontologyfunctions.impl.OntologyFunctionsPackageImpl#getOntologyFunctionsRoot()
		 * @generated
		 */
		EClass ONTOLOGY_FUNCTIONS_ROOT = eINSTANCE.getOntologyFunctionsRoot();

		/**
		 * The meta object literal for the '<em><b>Ontology Functions</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ONTOLOGY_FUNCTIONS_ROOT__ONTOLOGY_FUNCTIONS = eINSTANCE.getOntologyFunctionsRoot_OntologyFunctions();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decisionproject.ontologyfunctions.impl.OntologyFunctionsImpl <em>Ontology Functions</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decisionproject.ontologyfunctions.impl.OntologyFunctionsImpl
		 * @see com.tibco.cep.decisionproject.ontologyfunctions.impl.OntologyFunctionsPackageImpl#getOntologyFunctions()
		 * @generated
		 */
		EClass ONTOLOGY_FUNCTIONS = eINSTANCE.getOntologyFunctions();

		/**
		 * The meta object literal for the '<em><b>Resource</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ONTOLOGY_FUNCTIONS__RESOURCE = eINSTANCE.getOntologyFunctions_Resource();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decisionproject.ontologyfunctions.impl.AbstractResourceImpl <em>Abstract Resource</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decisionproject.ontologyfunctions.impl.AbstractResourceImpl
		 * @see com.tibco.cep.decisionproject.ontologyfunctions.impl.OntologyFunctionsPackageImpl#getAbstractResource()
		 * @generated
		 */
		EClass ABSTRACT_RESOURCE = eINSTANCE.getAbstractResource();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_RESOURCE__NAME = eINSTANCE.getAbstractResource_Name();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decisionproject.ontologyfunctions.impl.ConceptImpl <em>Concept</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decisionproject.ontologyfunctions.impl.ConceptImpl
		 * @see com.tibco.cep.decisionproject.ontologyfunctions.impl.OntologyFunctionsPackageImpl#getConcept()
		 * @generated
		 */
		EClass CONCEPT = eINSTANCE.getConcept();

		/**
		 * The meta object literal for the '<em><b>Function Label</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONCEPT__FUNCTION_LABEL = eINSTANCE.getConcept_FunctionLabel();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decisionproject.ontologyfunctions.impl.ParentResourceImpl <em>Parent Resource</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decisionproject.ontologyfunctions.impl.ParentResourceImpl
		 * @see com.tibco.cep.decisionproject.ontologyfunctions.impl.OntologyFunctionsPackageImpl#getParentResource()
		 * @generated
		 */
		EClass PARENT_RESOURCE = eINSTANCE.getParentResource();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decisionproject.ontologyfunctions.impl.FolderImpl <em>Folder</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decisionproject.ontologyfunctions.impl.FolderImpl
		 * @see com.tibco.cep.decisionproject.ontologyfunctions.impl.OntologyFunctionsPackageImpl#getFolder()
		 * @generated
		 */
		EClass FOLDER = eINSTANCE.getFolder();

		/**
		 * The meta object literal for the '<em><b>Resource</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FOLDER__RESOURCE = eINSTANCE.getFolder_Resource();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decisionproject.ontologyfunctions.impl.EventImpl <em>Event</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decisionproject.ontologyfunctions.impl.EventImpl
		 * @see com.tibco.cep.decisionproject.ontologyfunctions.impl.OntologyFunctionsPackageImpl#getEvent()
		 * @generated
		 */
		EClass EVENT = eINSTANCE.getEvent();

		/**
		 * The meta object literal for the '<em><b>Function Label</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EVENT__FUNCTION_LABEL = eINSTANCE.getEvent_FunctionLabel();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decisionproject.ontologyfunctions.impl.RuleFunctionImpl <em>Rule Function</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decisionproject.ontologyfunctions.impl.RuleFunctionImpl
		 * @see com.tibco.cep.decisionproject.ontologyfunctions.impl.OntologyFunctionsPackageImpl#getRuleFunction()
		 * @generated
		 */
		EClass RULE_FUNCTION = eINSTANCE.getRuleFunction();

		/**
		 * The meta object literal for the '<em><b>Function Label</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_FUNCTION__FUNCTION_LABEL = eINSTANCE.getRuleFunction_FunctionLabel();

		/**
		 * The meta object literal for the '<em><b>Validity</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_FUNCTION__VALIDITY = eINSTANCE.getRuleFunction_Validity();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decisionproject.ontologyfunctions.impl.FunctionLabelImpl <em>Function Label</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decisionproject.ontologyfunctions.impl.FunctionLabelImpl
		 * @see com.tibco.cep.decisionproject.ontologyfunctions.impl.OntologyFunctionsPackageImpl#getFunctionLabel()
		 * @generated
		 */
		EClass FUNCTION_LABEL = eINSTANCE.getFunctionLabel();

	}

} //OntologyFunctionsPackage
