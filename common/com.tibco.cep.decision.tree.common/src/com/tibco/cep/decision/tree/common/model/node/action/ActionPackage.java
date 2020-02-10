/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.tree.common.model.node.action;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import com.tibco.cep.decision.tree.common.model.node.NodePackage;

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
 * @see com.tibco.cep.decision.tree.common.model.node.action.ActionFactory
 * @model kind="package"
 * @generated
 */
public interface ActionPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "action";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http:///com/tibco/cep/decision/tree/model/node/action";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "action";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ActionPackage eINSTANCE = com.tibco.cep.decision.tree.common.model.node.action.impl.ActionPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.tibco.cep.decision.tree.common.model.node.action.impl.ActionImpl <em>Action</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decision.tree.common.model.node.action.impl.ActionImpl
	 * @see com.tibco.cep.decision.tree.common.model.node.action.impl.ActionPackageImpl#getAction()
	 * @generated
	 */
	int ACTION = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__ID = NodePackage.NODE_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__NAME = NodePackage.NODE_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__DESCRIPTION = NodePackage.NODE_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__DATA = NodePackage.NODE_ELEMENT__DATA;

	/**
	 * The feature id for the '<em><b>Assoc Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__ASSOC_EDGE = NodePackage.NODE_ELEMENT__ASSOC_EDGE;

	/**
	 * The feature id for the '<em><b>In Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__IN_EDGES = NodePackage.NODE_ELEMENT__IN_EDGES;

	/**
	 * The feature id for the '<em><b>Out Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__OUT_EDGE = NodePackage.NODE_ELEMENT__OUT_EDGE;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__TEXT = NodePackage.NODE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Action</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_FEATURE_COUNT = NodePackage.NODE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decision.tree.common.model.node.action.impl.AssignmentImpl <em>Assignment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decision.tree.common.model.node.action.impl.AssignmentImpl
	 * @see com.tibco.cep.decision.tree.common.model.node.action.impl.ActionPackageImpl#getAssignment()
	 * @generated
	 */
	int ASSIGNMENT = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSIGNMENT__ID = ACTION__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSIGNMENT__NAME = ACTION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSIGNMENT__DESCRIPTION = ACTION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSIGNMENT__DATA = ACTION__DATA;

	/**
	 * The feature id for the '<em><b>Assoc Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSIGNMENT__ASSOC_EDGE = ACTION__ASSOC_EDGE;

	/**
	 * The feature id for the '<em><b>In Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSIGNMENT__IN_EDGES = ACTION__IN_EDGES;

	/**
	 * The feature id for the '<em><b>Out Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSIGNMENT__OUT_EDGE = ACTION__OUT_EDGE;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSIGNMENT__TEXT = ACTION__TEXT;

	/**
	 * The number of structural features of the '<em>Assignment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSIGNMENT_FEATURE_COUNT = ACTION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decision.tree.common.model.node.action.impl.CallRFImpl <em>Call RF</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decision.tree.common.model.node.action.impl.CallRFImpl
	 * @see com.tibco.cep.decision.tree.common.model.node.action.impl.ActionPackageImpl#getCallRF()
	 * @generated
	 */
	int CALL_RF = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALL_RF__ID = ACTION__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALL_RF__NAME = ACTION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALL_RF__DESCRIPTION = ACTION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALL_RF__DATA = ACTION__DATA;

	/**
	 * The feature id for the '<em><b>Assoc Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALL_RF__ASSOC_EDGE = ACTION__ASSOC_EDGE;

	/**
	 * The feature id for the '<em><b>In Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALL_RF__IN_EDGES = ACTION__IN_EDGES;

	/**
	 * The feature id for the '<em><b>Out Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALL_RF__OUT_EDGE = ACTION__OUT_EDGE;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALL_RF__TEXT = ACTION__TEXT;

	/**
	 * The number of structural features of the '<em>Call RF</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALL_RF_FEATURE_COUNT = ACTION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decision.tree.common.model.node.action.impl.CallDecisionTreeActionImpl <em>Call Decision Tree Action</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decision.tree.common.model.node.action.impl.CallDecisionTreeActionImpl
	 * @see com.tibco.cep.decision.tree.common.model.node.action.impl.ActionPackageImpl#getCallDecisionTreeAction()
	 * @generated
	 */
	int CALL_DECISION_TREE_ACTION = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALL_DECISION_TREE_ACTION__ID = ACTION__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALL_DECISION_TREE_ACTION__NAME = ACTION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALL_DECISION_TREE_ACTION__DESCRIPTION = ACTION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALL_DECISION_TREE_ACTION__DATA = ACTION__DATA;

	/**
	 * The feature id for the '<em><b>Assoc Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALL_DECISION_TREE_ACTION__ASSOC_EDGE = ACTION__ASSOC_EDGE;

	/**
	 * The feature id for the '<em><b>In Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALL_DECISION_TREE_ACTION__IN_EDGES = ACTION__IN_EDGES;

	/**
	 * The feature id for the '<em><b>Out Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALL_DECISION_TREE_ACTION__OUT_EDGE = ACTION__OUT_EDGE;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALL_DECISION_TREE_ACTION__TEXT = ACTION__TEXT;

	/**
	 * The feature id for the '<em><b>Call Tree</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALL_DECISION_TREE_ACTION__CALL_TREE = ACTION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Call Decision Tree Action</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALL_DECISION_TREE_ACTION_FEATURE_COUNT = ACTION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decision.tree.common.model.node.action.impl.CallDecisionTableActionImpl <em>Call Decision Table Action</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decision.tree.common.model.node.action.impl.CallDecisionTableActionImpl
	 * @see com.tibco.cep.decision.tree.common.model.node.action.impl.ActionPackageImpl#getCallDecisionTableAction()
	 * @generated
	 */
	int CALL_DECISION_TABLE_ACTION = 4;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALL_DECISION_TABLE_ACTION__ID = ACTION__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALL_DECISION_TABLE_ACTION__NAME = ACTION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALL_DECISION_TABLE_ACTION__DESCRIPTION = ACTION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALL_DECISION_TABLE_ACTION__DATA = ACTION__DATA;

	/**
	 * The feature id for the '<em><b>Assoc Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALL_DECISION_TABLE_ACTION__ASSOC_EDGE = ACTION__ASSOC_EDGE;

	/**
	 * The feature id for the '<em><b>In Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALL_DECISION_TABLE_ACTION__IN_EDGES = ACTION__IN_EDGES;

	/**
	 * The feature id for the '<em><b>Out Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALL_DECISION_TABLE_ACTION__OUT_EDGE = ACTION__OUT_EDGE;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALL_DECISION_TABLE_ACTION__TEXT = ACTION__TEXT;

	/**
	 * The number of structural features of the '<em>Call Decision Table Action</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALL_DECISION_TABLE_ACTION_FEATURE_COUNT = ACTION_FEATURE_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decision.tree.common.model.node.action.Action <em>Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Action</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.node.action.Action
	 * @generated
	 */
	EClass getAction();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.tree.common.model.node.action.Action#getText <em>Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Text</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.node.action.Action#getText()
	 * @see #getAction()
	 * @generated
	 */
	EAttribute getAction_Text();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decision.tree.common.model.node.action.Assignment <em>Assignment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Assignment</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.node.action.Assignment
	 * @generated
	 */
	EClass getAssignment();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decision.tree.common.model.node.action.CallRF <em>Call RF</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Call RF</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.node.action.CallRF
	 * @generated
	 */
	EClass getCallRF();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decision.tree.common.model.node.action.CallDecisionTreeAction <em>Call Decision Tree Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Call Decision Tree Action</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.node.action.CallDecisionTreeAction
	 * @generated
	 */
	EClass getCallDecisionTreeAction();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.decision.tree.common.model.node.action.CallDecisionTreeAction#getCallTree <em>Call Tree</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Call Tree</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.node.action.CallDecisionTreeAction#getCallTree()
	 * @see #getCallDecisionTreeAction()
	 * @generated
	 */
	EReference getCallDecisionTreeAction_CallTree();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decision.tree.common.model.node.action.CallDecisionTableAction <em>Call Decision Table Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Call Decision Table Action</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.node.action.CallDecisionTableAction
	 * @generated
	 */
	EClass getCallDecisionTableAction();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ActionFactory getActionFactory();

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
		 * The meta object literal for the '{@link com.tibco.cep.decision.tree.common.model.node.action.impl.ActionImpl <em>Action</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decision.tree.common.model.node.action.impl.ActionImpl
		 * @see com.tibco.cep.decision.tree.common.model.node.action.impl.ActionPackageImpl#getAction()
		 * @generated
		 */
		EClass ACTION = eINSTANCE.getAction();

		/**
		 * The meta object literal for the '<em><b>Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTION__TEXT = eINSTANCE.getAction_Text();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decision.tree.common.model.node.action.impl.AssignmentImpl <em>Assignment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decision.tree.common.model.node.action.impl.AssignmentImpl
		 * @see com.tibco.cep.decision.tree.common.model.node.action.impl.ActionPackageImpl#getAssignment()
		 * @generated
		 */
		EClass ASSIGNMENT = eINSTANCE.getAssignment();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decision.tree.common.model.node.action.impl.CallRFImpl <em>Call RF</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decision.tree.common.model.node.action.impl.CallRFImpl
		 * @see com.tibco.cep.decision.tree.common.model.node.action.impl.ActionPackageImpl#getCallRF()
		 * @generated
		 */
		EClass CALL_RF = eINSTANCE.getCallRF();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decision.tree.common.model.node.action.impl.CallDecisionTreeActionImpl <em>Call Decision Tree Action</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decision.tree.common.model.node.action.impl.CallDecisionTreeActionImpl
		 * @see com.tibco.cep.decision.tree.common.model.node.action.impl.ActionPackageImpl#getCallDecisionTreeAction()
		 * @generated
		 */
		EClass CALL_DECISION_TREE_ACTION = eINSTANCE.getCallDecisionTreeAction();

		/**
		 * The meta object literal for the '<em><b>Call Tree</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CALL_DECISION_TREE_ACTION__CALL_TREE = eINSTANCE.getCallDecisionTreeAction_CallTree();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decision.tree.common.model.node.action.impl.CallDecisionTableActionImpl <em>Call Decision Table Action</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decision.tree.common.model.node.action.impl.CallDecisionTableActionImpl
		 * @see com.tibco.cep.decision.tree.common.model.node.action.impl.ActionPackageImpl#getCallDecisionTableAction()
		 * @generated
		 */
		EClass CALL_DECISION_TABLE_ACTION = eINSTANCE.getCallDecisionTableAction();

	}

} //ActionPackage
