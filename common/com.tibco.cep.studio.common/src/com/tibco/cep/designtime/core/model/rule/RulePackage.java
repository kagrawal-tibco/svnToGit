/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.rule;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import com.tibco.cep.designtime.core.model.ModelPackage;

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
 * @see com.tibco.cep.designtime.core.model.rule.RuleFactory
 * @model kind="package"
 * @generated
 */
public interface RulePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "rule";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http:///com/tibco/cep/designtime/core/model/rule";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "rule";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	RulePackage eINSTANCE = com.tibco.cep.designtime.core.model.rule.impl.RulePackageImpl.init();

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.rule.ScopeContainer <em>Scope Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.rule.ScopeContainer
	 * @see com.tibco.cep.designtime.core.model.rule.impl.RulePackageImpl#getScopeContainer()
	 * @generated
	 */
	int SCOPE_CONTAINER = 0;

	/**
	 * The feature id for the '<em><b>Symbols</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCOPE_CONTAINER__SYMBOLS = 0;

	/**
	 * The number of structural features of the '<em>Scope Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCOPE_CONTAINER_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.rule.Compilable <em>Compilable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.rule.Compilable
	 * @see com.tibco.cep.designtime.core.model.rule.impl.RulePackageImpl#getCompilable()
	 * @generated
	 */
	int COMPILABLE = 1;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILABLE__NAMESPACE = ModelPackage.ENTITY__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILABLE__FOLDER = ModelPackage.ENTITY__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILABLE__NAME = ModelPackage.ENTITY__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILABLE__DESCRIPTION = ModelPackage.ENTITY__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILABLE__LAST_MODIFIED = ModelPackage.ENTITY__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILABLE__GUID = ModelPackage.ENTITY__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILABLE__ONTOLOGY = ModelPackage.ENTITY__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILABLE__EXTENDED_PROPERTIES = ModelPackage.ENTITY__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILABLE__HIDDEN_PROPERTIES = ModelPackage.ENTITY__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILABLE__TRANSIENT_PROPERTIES = ModelPackage.ENTITY__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILABLE__OWNER_PROJECT_NAME = ModelPackage.ENTITY__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Symbols</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILABLE__SYMBOLS = ModelPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Compilation Status</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILABLE__COMPILATION_STATUS = ModelPackage.ENTITY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILABLE__RETURN_TYPE = ModelPackage.ENTITY_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Full Source Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILABLE__FULL_SOURCE_TEXT = ModelPackage.ENTITY_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Action Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILABLE__ACTION_TEXT = ModelPackage.ENTITY_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Condition Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILABLE__CONDITION_TEXT = ModelPackage.ENTITY_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Rank</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILABLE__RANK = ModelPackage.ENTITY_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Compilable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILABLE_FEATURE_COUNT = ModelPackage.ENTITY_FEATURE_COUNT + 7;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.rule.impl.RuleImpl <em>Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.rule.impl.RuleImpl
	 * @see com.tibco.cep.designtime.core.model.rule.impl.RulePackageImpl#getRule()
	 * @generated
	 */
	int RULE = 2;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__NAMESPACE = COMPILABLE__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__FOLDER = COMPILABLE__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__NAME = COMPILABLE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__DESCRIPTION = COMPILABLE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__LAST_MODIFIED = COMPILABLE__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__GUID = COMPILABLE__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__ONTOLOGY = COMPILABLE__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__EXTENDED_PROPERTIES = COMPILABLE__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__HIDDEN_PROPERTIES = COMPILABLE__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__TRANSIENT_PROPERTIES = COMPILABLE__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__OWNER_PROJECT_NAME = COMPILABLE__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Symbols</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__SYMBOLS = COMPILABLE__SYMBOLS;

	/**
	 * The feature id for the '<em><b>Compilation Status</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__COMPILATION_STATUS = COMPILABLE__COMPILATION_STATUS;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__RETURN_TYPE = COMPILABLE__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Full Source Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__FULL_SOURCE_TEXT = COMPILABLE__FULL_SOURCE_TEXT;

	/**
	 * The feature id for the '<em><b>Action Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__ACTION_TEXT = COMPILABLE__ACTION_TEXT;

	/**
	 * The feature id for the '<em><b>Condition Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__CONDITION_TEXT = COMPILABLE__CONDITION_TEXT;

	/**
	 * The feature id for the '<em><b>Rank</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__RANK = COMPILABLE__RANK;

	/**
	 * The feature id for the '<em><b>Priority</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__PRIORITY = COMPILABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Max Rules</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__MAX_RULES = COMPILABLE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Test Interval</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__TEST_INTERVAL = COMPILABLE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Start Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__START_TIME = COMPILABLE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Requeue</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__REQUEUE = COMPILABLE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Requeue Vars</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__REQUEUE_VARS = COMPILABLE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Backward Chain</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__BACKWARD_CHAIN = COMPILABLE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Forward Chain</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__FORWARD_CHAIN = COMPILABLE_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Condition Function</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__CONDITION_FUNCTION = COMPILABLE_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Function</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__FUNCTION = COMPILABLE_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Author</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__AUTHOR = COMPILABLE_FEATURE_COUNT + 10;

	/**
	 * The number of structural features of the '<em>Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FEATURE_COUNT = COMPILABLE_FEATURE_COUNT + 11;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.rule.impl.RuleFunctionImpl <em>Function</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.rule.impl.RuleFunctionImpl
	 * @see com.tibco.cep.designtime.core.model.rule.impl.RulePackageImpl#getRuleFunction()
	 * @generated
	 */
	int RULE_FUNCTION = 3;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FUNCTION__NAMESPACE = COMPILABLE__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FUNCTION__FOLDER = COMPILABLE__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FUNCTION__NAME = COMPILABLE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FUNCTION__DESCRIPTION = COMPILABLE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FUNCTION__LAST_MODIFIED = COMPILABLE__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FUNCTION__GUID = COMPILABLE__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FUNCTION__ONTOLOGY = COMPILABLE__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FUNCTION__EXTENDED_PROPERTIES = COMPILABLE__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FUNCTION__HIDDEN_PROPERTIES = COMPILABLE__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FUNCTION__TRANSIENT_PROPERTIES = COMPILABLE__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FUNCTION__OWNER_PROJECT_NAME = COMPILABLE__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Symbols</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FUNCTION__SYMBOLS = COMPILABLE__SYMBOLS;

	/**
	 * The feature id for the '<em><b>Compilation Status</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FUNCTION__COMPILATION_STATUS = COMPILABLE__COMPILATION_STATUS;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FUNCTION__RETURN_TYPE = COMPILABLE__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Full Source Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FUNCTION__FULL_SOURCE_TEXT = COMPILABLE__FULL_SOURCE_TEXT;

	/**
	 * The feature id for the '<em><b>Action Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FUNCTION__ACTION_TEXT = COMPILABLE__ACTION_TEXT;

	/**
	 * The feature id for the '<em><b>Condition Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FUNCTION__CONDITION_TEXT = COMPILABLE__CONDITION_TEXT;

	/**
	 * The feature id for the '<em><b>Rank</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FUNCTION__RANK = COMPILABLE__RANK;

	/**
	 * The feature id for the '<em><b>Validity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FUNCTION__VALIDITY = COMPILABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Virtual</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FUNCTION__VIRTUAL = COMPILABLE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Alias</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FUNCTION__ALIAS = COMPILABLE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Function</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FUNCTION_FEATURE_COUNT = COMPILABLE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.rule.impl.RuleSetImpl <em>Set</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.rule.impl.RuleSetImpl
	 * @see com.tibco.cep.designtime.core.model.rule.impl.RulePackageImpl#getRuleSet()
	 * @generated
	 */
	int RULE_SET = 4;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SET__NAMESPACE = ModelPackage.ENTITY__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SET__FOLDER = ModelPackage.ENTITY__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SET__NAME = ModelPackage.ENTITY__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SET__DESCRIPTION = ModelPackage.ENTITY__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SET__LAST_MODIFIED = ModelPackage.ENTITY__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SET__GUID = ModelPackage.ENTITY__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SET__ONTOLOGY = ModelPackage.ENTITY__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SET__EXTENDED_PROPERTIES = ModelPackage.ENTITY__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SET__HIDDEN_PROPERTIES = ModelPackage.ENTITY__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SET__TRANSIENT_PROPERTIES = ModelPackage.ENTITY__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SET__OWNER_PROJECT_NAME = ModelPackage.ENTITY__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Rules</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SET__RULES = ModelPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SET_FEATURE_COUNT = ModelPackage.ENTITY_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.rule.impl.SymbolImpl <em>Symbol</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.rule.impl.SymbolImpl
	 * @see com.tibco.cep.designtime.core.model.rule.impl.RulePackageImpl#getSymbol()
	 * @generated
	 */
	int SYMBOL = 5;

	/**
	 * The feature id for the '<em><b>Id Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOL__ID_NAME = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOL__TYPE = 1;

	/**
	 * The feature id for the '<em><b>Type Extension</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOL__TYPE_EXTENSION = 2;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOL__DOMAIN = 3;

	/**
	 * The feature id for the '<em><b>Array</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOL__ARRAY = 4;

	/**
	 * The number of structural features of the '<em>Symbol</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOL_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.rule.impl.RuleFunctionSymbolImpl <em>Function Symbol</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.rule.impl.RuleFunctionSymbolImpl
	 * @see com.tibco.cep.designtime.core.model.rule.impl.RulePackageImpl#getRuleFunctionSymbol()
	 * @generated
	 */
	int RULE_FUNCTION_SYMBOL = 6;

	/**
	 * The feature id for the '<em><b>Id Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FUNCTION_SYMBOL__ID_NAME = SYMBOL__ID_NAME;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FUNCTION_SYMBOL__TYPE = SYMBOL__TYPE;

	/**
	 * The feature id for the '<em><b>Type Extension</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FUNCTION_SYMBOL__TYPE_EXTENSION = SYMBOL__TYPE_EXTENSION;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FUNCTION_SYMBOL__DOMAIN = SYMBOL__DOMAIN;

	/**
	 * The feature id for the '<em><b>Array</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FUNCTION_SYMBOL__ARRAY = SYMBOL__ARRAY;

	/**
	 * The feature id for the '<em><b>Direction</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FUNCTION_SYMBOL__DIRECTION = SYMBOL_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Resource Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FUNCTION_SYMBOL__RESOURCE_TYPE = SYMBOL_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Domain Overridden</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FUNCTION_SYMBOL__DOMAIN_OVERRIDDEN = SYMBOL_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Graphical Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FUNCTION_SYMBOL__GRAPHICAL_PATH = SYMBOL_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Primitive Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FUNCTION_SYMBOL__PRIMITIVE_TYPE = SYMBOL_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Function Symbol</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FUNCTION_SYMBOL_FEATURE_COUNT = SYMBOL_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.rule.impl.SymbolsImpl <em>Symbols</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.rule.impl.SymbolsImpl
	 * @see com.tibco.cep.designtime.core.model.rule.impl.RulePackageImpl#getSymbols()
	 * @generated
	 */
	int SYMBOLS = 7;

	/**
	 * The feature id for the '<em><b>Symbol List</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOLS__SYMBOL_LIST = 0;

	/**
	 * The number of structural features of the '<em>Symbols</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOLS_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.rule.impl.SymbolMapEntryImpl <em>Symbol Map Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.rule.impl.SymbolMapEntryImpl
	 * @see com.tibco.cep.designtime.core.model.rule.impl.RulePackageImpl#getSymbolMapEntry()
	 * @generated
	 */
	int SYMBOL_MAP_ENTRY = 8;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOL_MAP_ENTRY__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOL_MAP_ENTRY__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Symbol Map Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOL_MAP_ENTRY_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.rule.impl.RuleTemplateSymbolImpl <em>Template Symbol</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.rule.impl.RuleTemplateSymbolImpl
	 * @see com.tibco.cep.designtime.core.model.rule.impl.RulePackageImpl#getRuleTemplateSymbol()
	 * @generated
	 */
	int RULE_TEMPLATE_SYMBOL = 9;

	/**
	 * The feature id for the '<em><b>Id Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE_SYMBOL__ID_NAME = SYMBOL__ID_NAME;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE_SYMBOL__TYPE = SYMBOL__TYPE;

	/**
	 * The feature id for the '<em><b>Type Extension</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE_SYMBOL__TYPE_EXTENSION = SYMBOL__TYPE_EXTENSION;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE_SYMBOL__DOMAIN = SYMBOL__DOMAIN;

	/**
	 * The feature id for the '<em><b>Array</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE_SYMBOL__ARRAY = SYMBOL__ARRAY;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE_SYMBOL__EXPRESSION = SYMBOL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Template Symbol</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE_SYMBOL_FEATURE_COUNT = SYMBOL_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.rule.impl.ActionContextSymbolImpl <em>Action Context Symbol</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.rule.impl.ActionContextSymbolImpl
	 * @see com.tibco.cep.designtime.core.model.rule.impl.RulePackageImpl#getActionContextSymbol()
	 * @generated
	 */
	int ACTION_CONTEXT_SYMBOL = 10;

	/**
	 * The feature id for the '<em><b>Id Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_CONTEXT_SYMBOL__ID_NAME = SYMBOL__ID_NAME;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_CONTEXT_SYMBOL__TYPE = SYMBOL__TYPE;

	/**
	 * The feature id for the '<em><b>Type Extension</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_CONTEXT_SYMBOL__TYPE_EXTENSION = SYMBOL__TYPE_EXTENSION;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_CONTEXT_SYMBOL__DOMAIN = SYMBOL__DOMAIN;

	/**
	 * The feature id for the '<em><b>Array</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_CONTEXT_SYMBOL__ARRAY = SYMBOL__ARRAY;

	/**
	 * The feature id for the '<em><b>Action Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_CONTEXT_SYMBOL__ACTION_TYPE = SYMBOL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Action Context Symbol</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_CONTEXT_SYMBOL_FEATURE_COUNT = SYMBOL_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.rule.impl.RuleTemplateImpl <em>Template</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.rule.impl.RuleTemplateImpl
	 * @see com.tibco.cep.designtime.core.model.rule.impl.RulePackageImpl#getRuleTemplate()
	 * @generated
	 */
	int RULE_TEMPLATE = 11;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE__NAMESPACE = RULE__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE__FOLDER = RULE__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE__NAME = RULE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE__DESCRIPTION = RULE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE__LAST_MODIFIED = RULE__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE__GUID = RULE__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE__ONTOLOGY = RULE__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE__EXTENDED_PROPERTIES = RULE__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE__HIDDEN_PROPERTIES = RULE__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE__TRANSIENT_PROPERTIES = RULE__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE__OWNER_PROJECT_NAME = RULE__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Symbols</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE__SYMBOLS = RULE__SYMBOLS;

	/**
	 * The feature id for the '<em><b>Compilation Status</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE__COMPILATION_STATUS = RULE__COMPILATION_STATUS;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE__RETURN_TYPE = RULE__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Full Source Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE__FULL_SOURCE_TEXT = RULE__FULL_SOURCE_TEXT;

	/**
	 * The feature id for the '<em><b>Action Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE__ACTION_TEXT = RULE__ACTION_TEXT;

	/**
	 * The feature id for the '<em><b>Condition Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE__CONDITION_TEXT = RULE__CONDITION_TEXT;

	/**
	 * The feature id for the '<em><b>Rank</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE__RANK = RULE__RANK;

	/**
	 * The feature id for the '<em><b>Priority</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE__PRIORITY = RULE__PRIORITY;

	/**
	 * The feature id for the '<em><b>Max Rules</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE__MAX_RULES = RULE__MAX_RULES;

	/**
	 * The feature id for the '<em><b>Test Interval</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE__TEST_INTERVAL = RULE__TEST_INTERVAL;

	/**
	 * The feature id for the '<em><b>Start Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE__START_TIME = RULE__START_TIME;

	/**
	 * The feature id for the '<em><b>Requeue</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE__REQUEUE = RULE__REQUEUE;

	/**
	 * The feature id for the '<em><b>Requeue Vars</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE__REQUEUE_VARS = RULE__REQUEUE_VARS;

	/**
	 * The feature id for the '<em><b>Backward Chain</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE__BACKWARD_CHAIN = RULE__BACKWARD_CHAIN;

	/**
	 * The feature id for the '<em><b>Forward Chain</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE__FORWARD_CHAIN = RULE__FORWARD_CHAIN;

	/**
	 * The feature id for the '<em><b>Condition Function</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE__CONDITION_FUNCTION = RULE__CONDITION_FUNCTION;

	/**
	 * The feature id for the '<em><b>Function</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE__FUNCTION = RULE__FUNCTION;

	/**
	 * The feature id for the '<em><b>Author</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE__AUTHOR = RULE__AUTHOR;

	/**
	 * The feature id for the '<em><b>Bindings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE__BINDINGS = RULE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Display Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE__DISPLAY_PROPERTIES = RULE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Action Context</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE__ACTION_CONTEXT = RULE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Views</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE__VIEWS = RULE_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Template</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE_FEATURE_COUNT = RULE_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.rule.impl.ActionContextImpl <em>Action Context</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.rule.impl.ActionContextImpl
	 * @see com.tibco.cep.designtime.core.model.rule.impl.RulePackageImpl#getActionContext()
	 * @generated
	 */
	int ACTION_CONTEXT = 12;

	/**
	 * The feature id for the '<em><b>Action Context Symbols</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_CONTEXT__ACTION_CONTEXT_SYMBOLS = 0;

	/**
	 * The number of structural features of the '<em>Action Context</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_CONTEXT_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.rule.impl.BindingImpl <em>Binding</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.rule.impl.BindingImpl
	 * @see com.tibco.cep.designtime.core.model.rule.impl.RulePackageImpl#getBinding()
	 * @generated
	 */
	int BINDING = 13;

	/**
	 * The feature id for the '<em><b>Id Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINDING__ID_NAME = SYMBOL__ID_NAME;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINDING__TYPE = SYMBOL__TYPE;

	/**
	 * The feature id for the '<em><b>Type Extension</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINDING__TYPE_EXTENSION = SYMBOL__TYPE_EXTENSION;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINDING__DOMAIN = SYMBOL__DOMAIN;

	/**
	 * The feature id for the '<em><b>Array</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINDING__ARRAY = SYMBOL__ARRAY;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINDING__EXPRESSION = SYMBOL_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Domain Model Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINDING__DOMAIN_MODEL_PATH = SYMBOL_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Binding</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINDING_FEATURE_COUNT = SYMBOL_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.rule.impl.RuleTemplateViewImpl <em>Template View</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.rule.impl.RuleTemplateViewImpl
	 * @see com.tibco.cep.designtime.core.model.rule.impl.RulePackageImpl#getRuleTemplateView()
	 * @generated
	 */
	int RULE_TEMPLATE_VIEW = 14;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE_VIEW__NAMESPACE = ModelPackage.ENTITY__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE_VIEW__FOLDER = ModelPackage.ENTITY__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE_VIEW__NAME = ModelPackage.ENTITY__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE_VIEW__DESCRIPTION = ModelPackage.ENTITY__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE_VIEW__LAST_MODIFIED = ModelPackage.ENTITY__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE_VIEW__GUID = ModelPackage.ENTITY__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE_VIEW__ONTOLOGY = ModelPackage.ENTITY__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE_VIEW__EXTENDED_PROPERTIES = ModelPackage.ENTITY__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE_VIEW__HIDDEN_PROPERTIES = ModelPackage.ENTITY__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE_VIEW__TRANSIENT_PROPERTIES = ModelPackage.ENTITY__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE_VIEW__OWNER_PROJECT_NAME = ModelPackage.ENTITY__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Rule Template Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE_VIEW__RULE_TEMPLATE_PATH = ModelPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Presentation Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE_VIEW__PRESENTATION_TEXT = ModelPackage.ENTITY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Html File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE_VIEW__HTML_FILE = ModelPackage.ENTITY_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Template View</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TEMPLATE_VIEW_FEATURE_COUNT = ModelPackage.ENTITY_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.rule.impl.XsltFunctionImpl <em>Xslt Function</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.rule.impl.XsltFunctionImpl
	 * @see com.tibco.cep.designtime.core.model.rule.impl.RulePackageImpl#getXsltFunction()
	 * @generated
	 */
	int XSLT_FUNCTION = 15;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XSLT_FUNCTION__NAMESPACE = RULE_FUNCTION__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XSLT_FUNCTION__FOLDER = RULE_FUNCTION__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XSLT_FUNCTION__NAME = RULE_FUNCTION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XSLT_FUNCTION__DESCRIPTION = RULE_FUNCTION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XSLT_FUNCTION__LAST_MODIFIED = RULE_FUNCTION__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XSLT_FUNCTION__GUID = RULE_FUNCTION__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XSLT_FUNCTION__ONTOLOGY = RULE_FUNCTION__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XSLT_FUNCTION__EXTENDED_PROPERTIES = RULE_FUNCTION__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XSLT_FUNCTION__HIDDEN_PROPERTIES = RULE_FUNCTION__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XSLT_FUNCTION__TRANSIENT_PROPERTIES = RULE_FUNCTION__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XSLT_FUNCTION__OWNER_PROJECT_NAME = RULE_FUNCTION__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Symbols</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XSLT_FUNCTION__SYMBOLS = RULE_FUNCTION__SYMBOLS;

	/**
	 * The feature id for the '<em><b>Compilation Status</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XSLT_FUNCTION__COMPILATION_STATUS = RULE_FUNCTION__COMPILATION_STATUS;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XSLT_FUNCTION__RETURN_TYPE = RULE_FUNCTION__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Full Source Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XSLT_FUNCTION__FULL_SOURCE_TEXT = RULE_FUNCTION__FULL_SOURCE_TEXT;

	/**
	 * The feature id for the '<em><b>Action Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XSLT_FUNCTION__ACTION_TEXT = RULE_FUNCTION__ACTION_TEXT;

	/**
	 * The feature id for the '<em><b>Condition Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XSLT_FUNCTION__CONDITION_TEXT = RULE_FUNCTION__CONDITION_TEXT;

	/**
	 * The feature id for the '<em><b>Rank</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XSLT_FUNCTION__RANK = RULE_FUNCTION__RANK;

	/**
	 * The feature id for the '<em><b>Validity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XSLT_FUNCTION__VALIDITY = RULE_FUNCTION__VALIDITY;

	/**
	 * The feature id for the '<em><b>Virtual</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XSLT_FUNCTION__VIRTUAL = RULE_FUNCTION__VIRTUAL;

	/**
	 * The feature id for the '<em><b>Alias</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XSLT_FUNCTION__ALIAS = RULE_FUNCTION__ALIAS;

	/**
	 * The number of structural features of the '<em>Xslt Function</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XSLT_FUNCTION_FEATURE_COUNT = RULE_FUNCTION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.rule.Validity <em>Validity</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.rule.Validity
	 * @see com.tibco.cep.designtime.core.model.rule.impl.RulePackageImpl#getValidity()
	 * @generated
	 */
	int VALIDITY = 16;


	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.rule.ActionType <em>Action Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.rule.ActionType
	 * @see com.tibco.cep.designtime.core.model.rule.impl.RulePackageImpl#getActionType()
	 * @generated
	 */
	int ACTION_TYPE = 17;


	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.rule.ScopeContainer <em>Scope Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Scope Container</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.ScopeContainer
	 * @generated
	 */
	EClass getScopeContainer();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.rule.ScopeContainer#getSymbols <em>Symbols</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Symbols</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.ScopeContainer#getSymbols()
	 * @see #getScopeContainer()
	 * @generated
	 */
	EReference getScopeContainer_Symbols();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.rule.Compilable <em>Compilable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Compilable</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.Compilable
	 * @generated
	 */
	EClass getCompilable();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.rule.Compilable#getCompilationStatus <em>Compilation Status</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Compilation Status</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.Compilable#getCompilationStatus()
	 * @see #getCompilable()
	 * @generated
	 */
	EAttribute getCompilable_CompilationStatus();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.rule.Compilable#getReturnType <em>Return Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Return Type</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.Compilable#getReturnType()
	 * @see #getCompilable()
	 * @generated
	 */
	EAttribute getCompilable_ReturnType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.rule.Compilable#getFullSourceText <em>Full Source Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Full Source Text</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.Compilable#getFullSourceText()
	 * @see #getCompilable()
	 * @generated
	 */
	EAttribute getCompilable_FullSourceText();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.rule.Compilable#getActionText <em>Action Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Action Text</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.Compilable#getActionText()
	 * @see #getCompilable()
	 * @generated
	 */
	EAttribute getCompilable_ActionText();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.rule.Compilable#getConditionText <em>Condition Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Condition Text</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.Compilable#getConditionText()
	 * @see #getCompilable()
	 * @generated
	 */
	EAttribute getCompilable_ConditionText();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.rule.Compilable#getRank <em>Rank</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Rank</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.Compilable#getRank()
	 * @see #getCompilable()
	 * @generated
	 */
	EAttribute getCompilable_Rank();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.rule.Rule <em>Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rule</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.Rule
	 * @generated
	 */
	EClass getRule();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.rule.Rule#getPriority <em>Priority</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Priority</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.Rule#getPriority()
	 * @see #getRule()
	 * @generated
	 */
	EAttribute getRule_Priority();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.rule.Rule#getMaxRules <em>Max Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max Rules</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.Rule#getMaxRules()
	 * @see #getRule()
	 * @generated
	 */
	EAttribute getRule_MaxRules();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.rule.Rule#getTestInterval <em>Test Interval</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Test Interval</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.Rule#getTestInterval()
	 * @see #getRule()
	 * @generated
	 */
	EAttribute getRule_TestInterval();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.rule.Rule#getStartTime <em>Start Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Start Time</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.Rule#getStartTime()
	 * @see #getRule()
	 * @generated
	 */
	EAttribute getRule_StartTime();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.rule.Rule#isRequeue <em>Requeue</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Requeue</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.Rule#isRequeue()
	 * @see #getRule()
	 * @generated
	 */
	EAttribute getRule_Requeue();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.cep.designtime.core.model.rule.Rule#getRequeueVars <em>Requeue Vars</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Requeue Vars</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.Rule#getRequeueVars()
	 * @see #getRule()
	 * @generated
	 */
	EAttribute getRule_RequeueVars();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.rule.Rule#isBackwardChain <em>Backward Chain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Backward Chain</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.Rule#isBackwardChain()
	 * @see #getRule()
	 * @generated
	 */
	EAttribute getRule_BackwardChain();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.rule.Rule#isForwardChain <em>Forward Chain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Forward Chain</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.Rule#isForwardChain()
	 * @see #getRule()
	 * @generated
	 */
	EAttribute getRule_ForwardChain();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.rule.Rule#isConditionFunction <em>Condition Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Condition Function</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.Rule#isConditionFunction()
	 * @see #getRule()
	 * @generated
	 */
	EAttribute getRule_ConditionFunction();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.rule.Rule#isFunction <em>Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Function</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.Rule#isFunction()
	 * @see #getRule()
	 * @generated
	 */
	EAttribute getRule_Function();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.rule.Rule#getAuthor <em>Author</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Author</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.Rule#getAuthor()
	 * @see #getRule()
	 * @generated
	 */
	EAttribute getRule_Author();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.rule.RuleFunction <em>Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Function</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.RuleFunction
	 * @generated
	 */
	EClass getRuleFunction();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.rule.RuleFunction#getValidity <em>Validity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Validity</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.RuleFunction#getValidity()
	 * @see #getRuleFunction()
	 * @generated
	 */
	EAttribute getRuleFunction_Validity();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.rule.RuleFunction#isVirtual <em>Virtual</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Virtual</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.RuleFunction#isVirtual()
	 * @see #getRuleFunction()
	 * @generated
	 */
	EAttribute getRuleFunction_Virtual();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.rule.RuleFunction#getAlias <em>Alias</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Alias</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.RuleFunction#getAlias()
	 * @see #getRuleFunction()
	 * @generated
	 */
	EAttribute getRuleFunction_Alias();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.rule.RuleSet <em>Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Set</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.RuleSet
	 * @generated
	 */
	EClass getRuleSet();

	/**
	 * Returns the meta object for the reference list '{@link com.tibco.cep.designtime.core.model.rule.RuleSet#getRules <em>Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Rules</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.RuleSet#getRules()
	 * @see #getRuleSet()
	 * @generated
	 */
	EReference getRuleSet_Rules();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.rule.Symbol <em>Symbol</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Symbol</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.Symbol
	 * @generated
	 */
	EClass getSymbol();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.rule.Symbol#getIdName <em>Id Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id Name</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.Symbol#getIdName()
	 * @see #getSymbol()
	 * @generated
	 */
	EAttribute getSymbol_IdName();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.rule.Symbol#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.Symbol#getType()
	 * @see #getSymbol()
	 * @generated
	 */
	EAttribute getSymbol_Type();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.rule.Symbol#getTypeExtension <em>Type Extension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type Extension</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.Symbol#getTypeExtension()
	 * @see #getSymbol()
	 * @generated
	 */
	EAttribute getSymbol_TypeExtension();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.rule.Symbol#getDomain <em>Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Domain</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.Symbol#getDomain()
	 * @see #getSymbol()
	 * @generated
	 */
	EReference getSymbol_Domain();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.rule.Symbol#isArray <em>Array</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Array</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.Symbol#isArray()
	 * @see #getSymbol()
	 * @generated
	 */
	EAttribute getSymbol_Array();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.rule.RuleFunctionSymbol <em>Function Symbol</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Function Symbol</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.RuleFunctionSymbol
	 * @generated
	 */
	EClass getRuleFunctionSymbol();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.rule.RuleFunctionSymbol#getDirection <em>Direction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Direction</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.RuleFunctionSymbol#getDirection()
	 * @see #getRuleFunctionSymbol()
	 * @generated
	 */
	EAttribute getRuleFunctionSymbol_Direction();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.rule.RuleFunctionSymbol#getResourceType <em>Resource Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Resource Type</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.RuleFunctionSymbol#getResourceType()
	 * @see #getRuleFunctionSymbol()
	 * @generated
	 */
	EAttribute getRuleFunctionSymbol_ResourceType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.rule.RuleFunctionSymbol#isDomainOverridden <em>Domain Overridden</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Domain Overridden</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.RuleFunctionSymbol#isDomainOverridden()
	 * @see #getRuleFunctionSymbol()
	 * @generated
	 */
	EAttribute getRuleFunctionSymbol_DomainOverridden();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.rule.RuleFunctionSymbol#getGraphicalPath <em>Graphical Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Graphical Path</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.RuleFunctionSymbol#getGraphicalPath()
	 * @see #getRuleFunctionSymbol()
	 * @generated
	 */
	EAttribute getRuleFunctionSymbol_GraphicalPath();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.rule.RuleFunctionSymbol#getPrimitiveType <em>Primitive Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Primitive Type</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.RuleFunctionSymbol#getPrimitiveType()
	 * @see #getRuleFunctionSymbol()
	 * @generated
	 */
	EAttribute getRuleFunctionSymbol_PrimitiveType();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.rule.Symbols <em>Symbols</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Symbols</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.Symbols
	 * @generated
	 */
	EClass getSymbols();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.rule.Symbols#getSymbolList <em>Symbol List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Symbol List</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.Symbols#getSymbolList()
	 * @see #getSymbols()
	 * @generated
	 */
	EReference getSymbols_SymbolList();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>Symbol Map Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Symbol Map Entry</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDataType="org.eclipse.emf.ecore.EString" keyRequired="true"
	 *        valueType="com.tibco.cep.designtime.core.model.rule.Symbol" valueContainment="true" valueRequired="true"
	 * @generated
	 */
	EClass getSymbolMapEntry();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getSymbolMapEntry()
	 * @generated
	 */
	EAttribute getSymbolMapEntry_Key();

	/**
	 * Returns the meta object for the containment reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getSymbolMapEntry()
	 * @generated
	 */
	EReference getSymbolMapEntry_Value();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.rule.RuleTemplateSymbol <em>Template Symbol</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Template Symbol</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.RuleTemplateSymbol
	 * @generated
	 */
	EClass getRuleTemplateSymbol();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.rule.RuleTemplateSymbol#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Expression</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.RuleTemplateSymbol#getExpression()
	 * @see #getRuleTemplateSymbol()
	 * @generated
	 */
	EAttribute getRuleTemplateSymbol_Expression();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.rule.ActionContextSymbol <em>Action Context Symbol</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Action Context Symbol</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.ActionContextSymbol
	 * @generated
	 */
	EClass getActionContextSymbol();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.rule.ActionContextSymbol#getActionType <em>Action Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Action Type</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.ActionContextSymbol#getActionType()
	 * @see #getActionContextSymbol()
	 * @generated
	 */
	EAttribute getActionContextSymbol_ActionType();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.rule.RuleTemplate <em>Template</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Template</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.RuleTemplate
	 * @generated
	 */
	EClass getRuleTemplate();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.rule.RuleTemplate#getBindings <em>Bindings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Bindings</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.RuleTemplate#getBindings()
	 * @see #getRuleTemplate()
	 * @generated
	 */
	EReference getRuleTemplate_Bindings();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.rule.RuleTemplate#getDisplayProperties <em>Display Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Display Properties</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.RuleTemplate#getDisplayProperties()
	 * @see #getRuleTemplate()
	 * @generated
	 */
	EReference getRuleTemplate_DisplayProperties();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.rule.RuleTemplate#getActionContext <em>Action Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Action Context</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.RuleTemplate#getActionContext()
	 * @see #getRuleTemplate()
	 * @generated
	 */
	EReference getRuleTemplate_ActionContext();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.cep.designtime.core.model.rule.RuleTemplate#getViews <em>Views</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Views</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.RuleTemplate#getViews()
	 * @see #getRuleTemplate()
	 * @generated
	 */
	EAttribute getRuleTemplate_Views();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.rule.ActionContext <em>Action Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Action Context</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.ActionContext
	 * @generated
	 */
	EClass getActionContext();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.rule.ActionContext#getActionContextSymbols <em>Action Context Symbols</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Action Context Symbols</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.ActionContext#getActionContextSymbols()
	 * @see #getActionContext()
	 * @generated
	 */
	EReference getActionContext_ActionContextSymbols();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.rule.Binding <em>Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Binding</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.Binding
	 * @generated
	 */
	EClass getBinding();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.rule.Binding#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Expression</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.Binding#getExpression()
	 * @see #getBinding()
	 * @generated
	 */
	EAttribute getBinding_Expression();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.rule.Binding#getDomainModelPath <em>Domain Model Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Domain Model Path</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.Binding#getDomainModelPath()
	 * @see #getBinding()
	 * @generated
	 */
	EAttribute getBinding_DomainModelPath();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.rule.RuleTemplateView <em>Template View</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Template View</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.RuleTemplateView
	 * @generated
	 */
	EClass getRuleTemplateView();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.rule.RuleTemplateView#getRuleTemplatePath <em>Rule Template Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Rule Template Path</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.RuleTemplateView#getRuleTemplatePath()
	 * @see #getRuleTemplateView()
	 * @generated
	 */
	EAttribute getRuleTemplateView_RuleTemplatePath();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.rule.RuleTemplateView#getPresentationText <em>Presentation Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Presentation Text</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.RuleTemplateView#getPresentationText()
	 * @see #getRuleTemplateView()
	 * @generated
	 */
	EAttribute getRuleTemplateView_PresentationText();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.rule.RuleTemplateView#getHtmlFile <em>Html File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Html File</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.RuleTemplateView#getHtmlFile()
	 * @see #getRuleTemplateView()
	 * @generated
	 */
	EAttribute getRuleTemplateView_HtmlFile();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.rule.XsltFunction <em>Xslt Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xslt Function</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.XsltFunction
	 * @generated
	 */
	EClass getXsltFunction();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.designtime.core.model.rule.Validity <em>Validity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Validity</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.Validity
	 * @generated
	 */
	EEnum getValidity();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.designtime.core.model.rule.ActionType <em>Action Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Action Type</em>'.
	 * @see com.tibco.cep.designtime.core.model.rule.ActionType
	 * @generated
	 */
	EEnum getActionType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	RuleFactory getRuleFactory();

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
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.rule.ScopeContainer <em>Scope Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.rule.ScopeContainer
		 * @see com.tibco.cep.designtime.core.model.rule.impl.RulePackageImpl#getScopeContainer()
		 * @generated
		 */
		EClass SCOPE_CONTAINER = eINSTANCE.getScopeContainer();

		/**
		 * The meta object literal for the '<em><b>Symbols</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCOPE_CONTAINER__SYMBOLS = eINSTANCE.getScopeContainer_Symbols();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.rule.Compilable <em>Compilable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.rule.Compilable
		 * @see com.tibco.cep.designtime.core.model.rule.impl.RulePackageImpl#getCompilable()
		 * @generated
		 */
		EClass COMPILABLE = eINSTANCE.getCompilable();

		/**
		 * The meta object literal for the '<em><b>Compilation Status</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPILABLE__COMPILATION_STATUS = eINSTANCE.getCompilable_CompilationStatus();

		/**
		 * The meta object literal for the '<em><b>Return Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPILABLE__RETURN_TYPE = eINSTANCE.getCompilable_ReturnType();

		/**
		 * The meta object literal for the '<em><b>Full Source Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPILABLE__FULL_SOURCE_TEXT = eINSTANCE.getCompilable_FullSourceText();

		/**
		 * The meta object literal for the '<em><b>Action Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPILABLE__ACTION_TEXT = eINSTANCE.getCompilable_ActionText();

		/**
		 * The meta object literal for the '<em><b>Condition Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPILABLE__CONDITION_TEXT = eINSTANCE.getCompilable_ConditionText();

		/**
		 * The meta object literal for the '<em><b>Rank</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPILABLE__RANK = eINSTANCE.getCompilable_Rank();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.rule.impl.RuleImpl <em>Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.rule.impl.RuleImpl
		 * @see com.tibco.cep.designtime.core.model.rule.impl.RulePackageImpl#getRule()
		 * @generated
		 */
		EClass RULE = eINSTANCE.getRule();

		/**
		 * The meta object literal for the '<em><b>Priority</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE__PRIORITY = eINSTANCE.getRule_Priority();

		/**
		 * The meta object literal for the '<em><b>Max Rules</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE__MAX_RULES = eINSTANCE.getRule_MaxRules();

		/**
		 * The meta object literal for the '<em><b>Test Interval</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE__TEST_INTERVAL = eINSTANCE.getRule_TestInterval();

		/**
		 * The meta object literal for the '<em><b>Start Time</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE__START_TIME = eINSTANCE.getRule_StartTime();

		/**
		 * The meta object literal for the '<em><b>Requeue</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE__REQUEUE = eINSTANCE.getRule_Requeue();

		/**
		 * The meta object literal for the '<em><b>Requeue Vars</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE__REQUEUE_VARS = eINSTANCE.getRule_RequeueVars();

		/**
		 * The meta object literal for the '<em><b>Backward Chain</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE__BACKWARD_CHAIN = eINSTANCE.getRule_BackwardChain();

		/**
		 * The meta object literal for the '<em><b>Forward Chain</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE__FORWARD_CHAIN = eINSTANCE.getRule_ForwardChain();

		/**
		 * The meta object literal for the '<em><b>Condition Function</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE__CONDITION_FUNCTION = eINSTANCE.getRule_ConditionFunction();

		/**
		 * The meta object literal for the '<em><b>Function</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE__FUNCTION = eINSTANCE.getRule_Function();

		/**
		 * The meta object literal for the '<em><b>Author</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE__AUTHOR = eINSTANCE.getRule_Author();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.rule.impl.RuleFunctionImpl <em>Function</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.rule.impl.RuleFunctionImpl
		 * @see com.tibco.cep.designtime.core.model.rule.impl.RulePackageImpl#getRuleFunction()
		 * @generated
		 */
		EClass RULE_FUNCTION = eINSTANCE.getRuleFunction();

		/**
		 * The meta object literal for the '<em><b>Validity</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_FUNCTION__VALIDITY = eINSTANCE.getRuleFunction_Validity();

		/**
		 * The meta object literal for the '<em><b>Virtual</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_FUNCTION__VIRTUAL = eINSTANCE.getRuleFunction_Virtual();

		/**
		 * The meta object literal for the '<em><b>Alias</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_FUNCTION__ALIAS = eINSTANCE.getRuleFunction_Alias();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.rule.impl.RuleSetImpl <em>Set</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.rule.impl.RuleSetImpl
		 * @see com.tibco.cep.designtime.core.model.rule.impl.RulePackageImpl#getRuleSet()
		 * @generated
		 */
		EClass RULE_SET = eINSTANCE.getRuleSet();

		/**
		 * The meta object literal for the '<em><b>Rules</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_SET__RULES = eINSTANCE.getRuleSet_Rules();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.rule.impl.SymbolImpl <em>Symbol</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.rule.impl.SymbolImpl
		 * @see com.tibco.cep.designtime.core.model.rule.impl.RulePackageImpl#getSymbol()
		 * @generated
		 */
		EClass SYMBOL = eINSTANCE.getSymbol();

		/**
		 * The meta object literal for the '<em><b>Id Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SYMBOL__ID_NAME = eINSTANCE.getSymbol_IdName();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SYMBOL__TYPE = eINSTANCE.getSymbol_Type();

		/**
		 * The meta object literal for the '<em><b>Type Extension</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SYMBOL__TYPE_EXTENSION = eINSTANCE.getSymbol_TypeExtension();

		/**
		 * The meta object literal for the '<em><b>Domain</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SYMBOL__DOMAIN = eINSTANCE.getSymbol_Domain();

		/**
		 * The meta object literal for the '<em><b>Array</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SYMBOL__ARRAY = eINSTANCE.getSymbol_Array();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.rule.impl.RuleFunctionSymbolImpl <em>Function Symbol</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.rule.impl.RuleFunctionSymbolImpl
		 * @see com.tibco.cep.designtime.core.model.rule.impl.RulePackageImpl#getRuleFunctionSymbol()
		 * @generated
		 */
		EClass RULE_FUNCTION_SYMBOL = eINSTANCE.getRuleFunctionSymbol();

		/**
		 * The meta object literal for the '<em><b>Direction</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_FUNCTION_SYMBOL__DIRECTION = eINSTANCE.getRuleFunctionSymbol_Direction();

		/**
		 * The meta object literal for the '<em><b>Resource Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_FUNCTION_SYMBOL__RESOURCE_TYPE = eINSTANCE.getRuleFunctionSymbol_ResourceType();

		/**
		 * The meta object literal for the '<em><b>Domain Overridden</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_FUNCTION_SYMBOL__DOMAIN_OVERRIDDEN = eINSTANCE.getRuleFunctionSymbol_DomainOverridden();

		/**
		 * The meta object literal for the '<em><b>Graphical Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_FUNCTION_SYMBOL__GRAPHICAL_PATH = eINSTANCE.getRuleFunctionSymbol_GraphicalPath();

		/**
		 * The meta object literal for the '<em><b>Primitive Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_FUNCTION_SYMBOL__PRIMITIVE_TYPE = eINSTANCE.getRuleFunctionSymbol_PrimitiveType();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.rule.impl.SymbolsImpl <em>Symbols</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.rule.impl.SymbolsImpl
		 * @see com.tibco.cep.designtime.core.model.rule.impl.RulePackageImpl#getSymbols()
		 * @generated
		 */
		EClass SYMBOLS = eINSTANCE.getSymbols();

		/**
		 * The meta object literal for the '<em><b>Symbol List</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SYMBOLS__SYMBOL_LIST = eINSTANCE.getSymbols_SymbolList();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.rule.impl.SymbolMapEntryImpl <em>Symbol Map Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.rule.impl.SymbolMapEntryImpl
		 * @see com.tibco.cep.designtime.core.model.rule.impl.RulePackageImpl#getSymbolMapEntry()
		 * @generated
		 */
		EClass SYMBOL_MAP_ENTRY = eINSTANCE.getSymbolMapEntry();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SYMBOL_MAP_ENTRY__KEY = eINSTANCE.getSymbolMapEntry_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SYMBOL_MAP_ENTRY__VALUE = eINSTANCE.getSymbolMapEntry_Value();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.rule.impl.RuleTemplateSymbolImpl <em>Template Symbol</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.rule.impl.RuleTemplateSymbolImpl
		 * @see com.tibco.cep.designtime.core.model.rule.impl.RulePackageImpl#getRuleTemplateSymbol()
		 * @generated
		 */
		EClass RULE_TEMPLATE_SYMBOL = eINSTANCE.getRuleTemplateSymbol();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_TEMPLATE_SYMBOL__EXPRESSION = eINSTANCE.getRuleTemplateSymbol_Expression();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.rule.impl.ActionContextSymbolImpl <em>Action Context Symbol</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.rule.impl.ActionContextSymbolImpl
		 * @see com.tibco.cep.designtime.core.model.rule.impl.RulePackageImpl#getActionContextSymbol()
		 * @generated
		 */
		EClass ACTION_CONTEXT_SYMBOL = eINSTANCE.getActionContextSymbol();

		/**
		 * The meta object literal for the '<em><b>Action Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTION_CONTEXT_SYMBOL__ACTION_TYPE = eINSTANCE.getActionContextSymbol_ActionType();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.rule.impl.RuleTemplateImpl <em>Template</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.rule.impl.RuleTemplateImpl
		 * @see com.tibco.cep.designtime.core.model.rule.impl.RulePackageImpl#getRuleTemplate()
		 * @generated
		 */
		EClass RULE_TEMPLATE = eINSTANCE.getRuleTemplate();

		/**
		 * The meta object literal for the '<em><b>Bindings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_TEMPLATE__BINDINGS = eINSTANCE.getRuleTemplate_Bindings();

		/**
		 * The meta object literal for the '<em><b>Display Properties</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_TEMPLATE__DISPLAY_PROPERTIES = eINSTANCE.getRuleTemplate_DisplayProperties();

		/**
		 * The meta object literal for the '<em><b>Action Context</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_TEMPLATE__ACTION_CONTEXT = eINSTANCE.getRuleTemplate_ActionContext();

		/**
		 * The meta object literal for the '<em><b>Views</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_TEMPLATE__VIEWS = eINSTANCE.getRuleTemplate_Views();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.rule.impl.ActionContextImpl <em>Action Context</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.rule.impl.ActionContextImpl
		 * @see com.tibco.cep.designtime.core.model.rule.impl.RulePackageImpl#getActionContext()
		 * @generated
		 */
		EClass ACTION_CONTEXT = eINSTANCE.getActionContext();

		/**
		 * The meta object literal for the '<em><b>Action Context Symbols</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTION_CONTEXT__ACTION_CONTEXT_SYMBOLS = eINSTANCE.getActionContext_ActionContextSymbols();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.rule.impl.BindingImpl <em>Binding</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.rule.impl.BindingImpl
		 * @see com.tibco.cep.designtime.core.model.rule.impl.RulePackageImpl#getBinding()
		 * @generated
		 */
		EClass BINDING = eINSTANCE.getBinding();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BINDING__EXPRESSION = eINSTANCE.getBinding_Expression();

		/**
		 * The meta object literal for the '<em><b>Domain Model Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BINDING__DOMAIN_MODEL_PATH = eINSTANCE.getBinding_DomainModelPath();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.rule.impl.RuleTemplateViewImpl <em>Template View</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.rule.impl.RuleTemplateViewImpl
		 * @see com.tibco.cep.designtime.core.model.rule.impl.RulePackageImpl#getRuleTemplateView()
		 * @generated
		 */
		EClass RULE_TEMPLATE_VIEW = eINSTANCE.getRuleTemplateView();

		/**
		 * The meta object literal for the '<em><b>Rule Template Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_TEMPLATE_VIEW__RULE_TEMPLATE_PATH = eINSTANCE.getRuleTemplateView_RuleTemplatePath();

		/**
		 * The meta object literal for the '<em><b>Presentation Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_TEMPLATE_VIEW__PRESENTATION_TEXT = eINSTANCE.getRuleTemplateView_PresentationText();

		/**
		 * The meta object literal for the '<em><b>Html File</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_TEMPLATE_VIEW__HTML_FILE = eINSTANCE.getRuleTemplateView_HtmlFile();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.rule.impl.XsltFunctionImpl <em>Xslt Function</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.rule.impl.XsltFunctionImpl
		 * @see com.tibco.cep.designtime.core.model.rule.impl.RulePackageImpl#getXsltFunction()
		 * @generated
		 */
		EClass XSLT_FUNCTION = eINSTANCE.getXsltFunction();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.rule.Validity <em>Validity</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.rule.Validity
		 * @see com.tibco.cep.designtime.core.model.rule.impl.RulePackageImpl#getValidity()
		 * @generated
		 */
		EEnum VALIDITY = eINSTANCE.getValidity();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.rule.ActionType <em>Action Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.rule.ActionType
		 * @see com.tibco.cep.designtime.core.model.rule.impl.RulePackageImpl#getActionType()
		 * @generated
		 */
		EEnum ACTION_TYPE = eINSTANCE.getActionType();

	}

} //RulePackage
