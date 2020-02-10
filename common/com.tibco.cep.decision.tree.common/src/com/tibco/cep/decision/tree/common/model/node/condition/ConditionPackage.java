/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.tree.common.model.node.condition;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import com.tibco.cep.decision.tree.common.model.ModelPackage;
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
 * @see com.tibco.cep.decision.tree.common.model.node.condition.ConditionFactory
 * @model kind="package"
 * @generated
 */
public interface ConditionPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "condition";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http:///com/tibco/cep/decision/tree/model/node/condition";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "condition";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ConditionPackage eINSTANCE = com.tibco.cep.decision.tree.common.model.node.condition.impl.ConditionPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.tibco.cep.decision.tree.common.model.node.condition.impl.CondImpl <em>Cond</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decision.tree.common.model.node.condition.impl.CondImpl
	 * @see com.tibco.cep.decision.tree.common.model.node.condition.impl.ConditionPackageImpl#getCond()
	 * @generated
	 */
	int COND = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COND__ID = NodePackage.NODE_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COND__NAME = NodePackage.NODE_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COND__DESCRIPTION = NodePackage.NODE_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COND__DATA = NodePackage.NODE_ELEMENT__DATA;

	/**
	 * The feature id for the '<em><b>Assoc Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COND__ASSOC_EDGE = NodePackage.NODE_ELEMENT__ASSOC_EDGE;

	/**
	 * The feature id for the '<em><b>In Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COND__IN_EDGES = NodePackage.NODE_ELEMENT__IN_EDGES;

	/**
	 * The feature id for the '<em><b>Out Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COND__OUT_EDGE = NodePackage.NODE_ELEMENT__OUT_EDGE;

	/**
	 * The number of structural features of the '<em>Cond</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COND_FEATURE_COUNT = NodePackage.NODE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decision.tree.common.model.node.condition.impl.BoolCondImpl <em>Bool Cond</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decision.tree.common.model.node.condition.impl.BoolCondImpl
	 * @see com.tibco.cep.decision.tree.common.model.node.condition.impl.ConditionPackageImpl#getBoolCond()
	 * @generated
	 */
	int BOOL_COND = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOL_COND__ID = COND__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOL_COND__NAME = COND__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOL_COND__DESCRIPTION = COND__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOL_COND__DATA = COND__DATA;

	/**
	 * The feature id for the '<em><b>Assoc Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOL_COND__ASSOC_EDGE = COND__ASSOC_EDGE;

	/**
	 * The feature id for the '<em><b>In Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOL_COND__IN_EDGES = COND__IN_EDGES;

	/**
	 * The feature id for the '<em><b>Out Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOL_COND__OUT_EDGE = COND__OUT_EDGE;

	/**
	 * The feature id for the '<em><b>False Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOL_COND__FALSE_EDGE = COND_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Bool Cond</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOL_COND_FEATURE_COUNT = COND_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decision.tree.common.model.node.condition.impl.SwitchCondImpl <em>Switch Cond</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decision.tree.common.model.node.condition.impl.SwitchCondImpl
	 * @see com.tibco.cep.decision.tree.common.model.node.condition.impl.ConditionPackageImpl#getSwitchCond()
	 * @generated
	 */
	int SWITCH_COND = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SWITCH_COND__ID = COND__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SWITCH_COND__NAME = COND__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SWITCH_COND__DESCRIPTION = COND__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SWITCH_COND__DATA = COND__DATA;

	/**
	 * The feature id for the '<em><b>Assoc Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SWITCH_COND__ASSOC_EDGE = COND__ASSOC_EDGE;

	/**
	 * The feature id for the '<em><b>In Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SWITCH_COND__IN_EDGES = COND__IN_EDGES;

	/**
	 * The feature id for the '<em><b>Out Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SWITCH_COND__OUT_EDGE = COND__OUT_EDGE;

	/**
	 * The feature id for the '<em><b>Cases</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SWITCH_COND__CASES = COND_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Casegroups</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SWITCH_COND__CASEGROUPS = COND_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Switch Cond</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SWITCH_COND_FEATURE_COUNT = COND_FEATURE_COUNT + 2;


	/**
	 * The meta object id for the '{@link com.tibco.cep.decision.tree.common.model.node.condition.impl.CaseImpl <em>Case</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decision.tree.common.model.node.condition.impl.CaseImpl
	 * @see com.tibco.cep.decision.tree.common.model.node.condition.impl.ConditionPackageImpl#getCase()
	 * @generated
	 */
	int CASE = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CASE__ID = ModelPackage.FLOW_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CASE__NAME = ModelPackage.FLOW_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CASE__DESCRIPTION = ModelPackage.FLOW_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CASE__DATA = ModelPackage.FLOW_ELEMENT__DATA;

	/**
	 * The feature id for the '<em><b>Assoc Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CASE__ASSOC_EDGE = ModelPackage.FLOW_ELEMENT__ASSOC_EDGE;

	/**
	 * The feature id for the '<em><b>Out Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CASE__OUT_EDGE = ModelPackage.FLOW_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Case</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CASE_FEATURE_COUNT = ModelPackage.FLOW_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decision.tree.common.model.node.condition.impl.CaseGroupImpl <em>Case Group</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decision.tree.common.model.node.condition.impl.CaseGroupImpl
	 * @see com.tibco.cep.decision.tree.common.model.node.condition.impl.ConditionPackageImpl#getCaseGroup()
	 * @generated
	 */
	int CASE_GROUP = 4;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CASE_GROUP__ID = CASE__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CASE_GROUP__NAME = CASE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CASE_GROUP__DESCRIPTION = CASE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CASE_GROUP__DATA = CASE__DATA;

	/**
	 * The feature id for the '<em><b>Assoc Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CASE_GROUP__ASSOC_EDGE = CASE__ASSOC_EDGE;

	/**
	 * The feature id for the '<em><b>Out Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CASE_GROUP__OUT_EDGE = CASE__OUT_EDGE;

	/**
	 * The feature id for the '<em><b>Cases</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CASE_GROUP__CASES = CASE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Case Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CASE_GROUP_FEATURE_COUNT = CASE_FEATURE_COUNT + 1;


	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decision.tree.common.model.node.condition.Cond <em>Cond</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Cond</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.node.condition.Cond
	 * @generated
	 */
	EClass getCond();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decision.tree.common.model.node.condition.BoolCond <em>Bool Cond</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Bool Cond</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.node.condition.BoolCond
	 * @generated
	 */
	EClass getBoolCond();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.decision.tree.common.model.node.condition.BoolCond#getFalseEdge <em>False Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>False Edge</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.node.condition.BoolCond#getFalseEdge()
	 * @see #getBoolCond()
	 * @generated
	 */
	EReference getBoolCond_FalseEdge();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decision.tree.common.model.node.condition.SwitchCond <em>Switch Cond</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Switch Cond</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.node.condition.SwitchCond
	 * @generated
	 */
	EClass getSwitchCond();

	/**
	 * Returns the meta object for the reference list '{@link com.tibco.cep.decision.tree.common.model.node.condition.SwitchCond#getCases <em>Cases</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Cases</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.node.condition.SwitchCond#getCases()
	 * @see #getSwitchCond()
	 * @generated
	 */
	EReference getSwitchCond_Cases();

	/**
	 * Returns the meta object for the reference list '{@link com.tibco.cep.decision.tree.common.model.node.condition.SwitchCond#getCasegroups <em>Casegroups</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Casegroups</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.node.condition.SwitchCond#getCasegroups()
	 * @see #getSwitchCond()
	 * @generated
	 */
	EReference getSwitchCond_Casegroups();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decision.tree.common.model.node.condition.Case <em>Case</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Case</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.node.condition.Case
	 * @generated
	 */
	EClass getCase();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.decision.tree.common.model.node.condition.Case#getOutEdge <em>Out Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Out Edge</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.node.condition.Case#getOutEdge()
	 * @see #getCase()
	 * @generated
	 */
	EReference getCase_OutEdge();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decision.tree.common.model.node.condition.CaseGroup <em>Case Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Case Group</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.node.condition.CaseGroup
	 * @generated
	 */
	EClass getCaseGroup();

	/**
	 * Returns the meta object for the reference list '{@link com.tibco.cep.decision.tree.common.model.node.condition.CaseGroup#getCases <em>Cases</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Cases</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.node.condition.CaseGroup#getCases()
	 * @see #getCaseGroup()
	 * @generated
	 */
	EReference getCaseGroup_Cases();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ConditionFactory getConditionFactory();

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
		 * The meta object literal for the '{@link com.tibco.cep.decision.tree.common.model.node.condition.impl.CondImpl <em>Cond</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decision.tree.common.model.node.condition.impl.CondImpl
		 * @see com.tibco.cep.decision.tree.common.model.node.condition.impl.ConditionPackageImpl#getCond()
		 * @generated
		 */
		EClass COND = eINSTANCE.getCond();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decision.tree.common.model.node.condition.impl.BoolCondImpl <em>Bool Cond</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decision.tree.common.model.node.condition.impl.BoolCondImpl
		 * @see com.tibco.cep.decision.tree.common.model.node.condition.impl.ConditionPackageImpl#getBoolCond()
		 * @generated
		 */
		EClass BOOL_COND = eINSTANCE.getBoolCond();

		/**
		 * The meta object literal for the '<em><b>False Edge</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BOOL_COND__FALSE_EDGE = eINSTANCE.getBoolCond_FalseEdge();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decision.tree.common.model.node.condition.impl.SwitchCondImpl <em>Switch Cond</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decision.tree.common.model.node.condition.impl.SwitchCondImpl
		 * @see com.tibco.cep.decision.tree.common.model.node.condition.impl.ConditionPackageImpl#getSwitchCond()
		 * @generated
		 */
		EClass SWITCH_COND = eINSTANCE.getSwitchCond();

		/**
		 * The meta object literal for the '<em><b>Cases</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SWITCH_COND__CASES = eINSTANCE.getSwitchCond_Cases();

		/**
		 * The meta object literal for the '<em><b>Casegroups</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SWITCH_COND__CASEGROUPS = eINSTANCE.getSwitchCond_Casegroups();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decision.tree.common.model.node.condition.impl.CaseImpl <em>Case</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decision.tree.common.model.node.condition.impl.CaseImpl
		 * @see com.tibco.cep.decision.tree.common.model.node.condition.impl.ConditionPackageImpl#getCase()
		 * @generated
		 */
		EClass CASE = eINSTANCE.getCase();

		/**
		 * The meta object literal for the '<em><b>Out Edge</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CASE__OUT_EDGE = eINSTANCE.getCase_OutEdge();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decision.tree.common.model.node.condition.impl.CaseGroupImpl <em>Case Group</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decision.tree.common.model.node.condition.impl.CaseGroupImpl
		 * @see com.tibco.cep.decision.tree.common.model.node.condition.impl.ConditionPackageImpl#getCaseGroup()
		 * @generated
		 */
		EClass CASE_GROUP = eINSTANCE.getCaseGroup();

		/**
		 * The meta object literal for the '<em><b>Cases</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CASE_GROUP__CASES = eINSTANCE.getCaseGroup_Cases();

	}

} //ConditionPackage
