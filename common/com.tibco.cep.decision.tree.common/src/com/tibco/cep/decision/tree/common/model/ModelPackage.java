/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.tree.common.model;

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
 * @see com.tibco.cep.decision.tree.common.model.ModelFactory
 * @model kind="package"
 * @generated
 */
public interface ModelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "model";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http:///com/tibco/cep/decision/tree/model/DecisionTree.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "tree";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ModelPackage eINSTANCE = com.tibco.cep.decision.tree.common.model.impl.ModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.tibco.cep.decision.tree.common.model.impl.DecisionTreeImpl <em>Decision Tree</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decision.tree.common.model.impl.DecisionTreeImpl
	 * @see com.tibco.cep.decision.tree.common.model.impl.ModelPackageImpl#getDecisionTree()
	 * @generated
	 */
	int DECISION_TREE = 0;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_TREE__FOLDER = 0;

	/**
	 * The feature id for the '<em><b>Owner Project</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_TREE__OWNER_PROJECT = 1;

	/**
	 * The feature id for the '<em><b>Creation Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_TREE__CREATION_DATE = 2;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_TREE__LAST_MODIFIED = 3;

	/**
	 * The feature id for the '<em><b>Nodes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_TREE__NODES = 4;

	/**
	 * The feature id for the '<em><b>Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_TREE__EDGES = 5;

	/**
	 * The feature id for the '<em><b>Start Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_TREE__START_NODE = 6;

	/**
	 * The number of structural features of the '<em>Decision Tree</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_TREE_FEATURE_COUNT = 7;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decision.tree.common.model.impl.FlowElementImpl <em>Flow Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decision.tree.common.model.impl.FlowElementImpl
	 * @see com.tibco.cep.decision.tree.common.model.impl.ModelPackageImpl#getFlowElement()
	 * @generated
	 */
	int FLOW_ELEMENT = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_ELEMENT__ID = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_ELEMENT__NAME = 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_ELEMENT__DESCRIPTION = 2;

	/**
	 * The feature id for the '<em><b>Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_ELEMENT__DATA = 3;

	/**
	 * The feature id for the '<em><b>Assoc Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_ELEMENT__ASSOC_EDGE = 4;

	/**
	 * The number of structural features of the '<em>Flow Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_ELEMENT_FEATURE_COUNT = 5;


	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decision.tree.common.model.DecisionTree <em>Decision Tree</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Decision Tree</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.DecisionTree
	 * @generated
	 */
	EClass getDecisionTree();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.tree.common.model.DecisionTree#getFolder <em>Folder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Folder</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.DecisionTree#getFolder()
	 * @see #getDecisionTree()
	 * @generated
	 */
	EAttribute getDecisionTree_Folder();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.tree.common.model.DecisionTree#getOwnerProject <em>Owner Project</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Owner Project</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.DecisionTree#getOwnerProject()
	 * @see #getDecisionTree()
	 * @generated
	 */
	EAttribute getDecisionTree_OwnerProject();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.tree.common.model.DecisionTree#getCreationDate <em>Creation Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Creation Date</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.DecisionTree#getCreationDate()
	 * @see #getDecisionTree()
	 * @generated
	 */
	EAttribute getDecisionTree_CreationDate();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.tree.common.model.DecisionTree#getLastModified <em>Last Modified</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Last Modified</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.DecisionTree#getLastModified()
	 * @see #getDecisionTree()
	 * @generated
	 */
	EAttribute getDecisionTree_LastModified();

	/**
	 * Returns the meta object for the reference list '{@link com.tibco.cep.decision.tree.common.model.DecisionTree#getNodes <em>Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Nodes</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.DecisionTree#getNodes()
	 * @see #getDecisionTree()
	 * @generated
	 */
	EReference getDecisionTree_Nodes();

	/**
	 * Returns the meta object for the reference list '{@link com.tibco.cep.decision.tree.common.model.DecisionTree#getEdges <em>Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Edges</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.DecisionTree#getEdges()
	 * @see #getDecisionTree()
	 * @generated
	 */
	EReference getDecisionTree_Edges();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.decision.tree.common.model.DecisionTree#getStartNode <em>Start Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Start Node</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.DecisionTree#getStartNode()
	 * @see #getDecisionTree()
	 * @generated
	 */
	EReference getDecisionTree_StartNode();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decision.tree.common.model.FlowElement <em>Flow Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Flow Element</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.FlowElement
	 * @generated
	 */
	EClass getFlowElement();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.tree.common.model.FlowElement#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.FlowElement#getId()
	 * @see #getFlowElement()
	 * @generated
	 */
	EAttribute getFlowElement_Id();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.tree.common.model.FlowElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.FlowElement#getName()
	 * @see #getFlowElement()
	 * @generated
	 */
	EAttribute getFlowElement_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.tree.common.model.FlowElement#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.FlowElement#getDescription()
	 * @see #getFlowElement()
	 * @generated
	 */
	EAttribute getFlowElement_Description();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.tree.common.model.FlowElement#getData <em>Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Data</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.FlowElement#getData()
	 * @see #getFlowElement()
	 * @generated
	 */
	EAttribute getFlowElement_Data();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.decision.tree.common.model.FlowElement#getAssocEdge <em>Assoc Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Assoc Edge</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.FlowElement#getAssocEdge()
	 * @see #getFlowElement()
	 * @generated
	 */
	EReference getFlowElement_AssocEdge();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ModelFactory getModelFactory();

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
		 * The meta object literal for the '{@link com.tibco.cep.decision.tree.common.model.impl.DecisionTreeImpl <em>Decision Tree</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decision.tree.common.model.impl.DecisionTreeImpl
		 * @see com.tibco.cep.decision.tree.common.model.impl.ModelPackageImpl#getDecisionTree()
		 * @generated
		 */
		EClass DECISION_TREE = eINSTANCE.getDecisionTree();

		/**
		 * The meta object literal for the '<em><b>Folder</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DECISION_TREE__FOLDER = eINSTANCE.getDecisionTree_Folder();

		/**
		 * The meta object literal for the '<em><b>Owner Project</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DECISION_TREE__OWNER_PROJECT = eINSTANCE.getDecisionTree_OwnerProject();

		/**
		 * The meta object literal for the '<em><b>Creation Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DECISION_TREE__CREATION_DATE = eINSTANCE.getDecisionTree_CreationDate();

		/**
		 * The meta object literal for the '<em><b>Last Modified</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DECISION_TREE__LAST_MODIFIED = eINSTANCE.getDecisionTree_LastModified();

		/**
		 * The meta object literal for the '<em><b>Nodes</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DECISION_TREE__NODES = eINSTANCE.getDecisionTree_Nodes();

		/**
		 * The meta object literal for the '<em><b>Edges</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DECISION_TREE__EDGES = eINSTANCE.getDecisionTree_Edges();

		/**
		 * The meta object literal for the '<em><b>Start Node</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DECISION_TREE__START_NODE = eINSTANCE.getDecisionTree_StartNode();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decision.tree.common.model.impl.FlowElementImpl <em>Flow Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decision.tree.common.model.impl.FlowElementImpl
		 * @see com.tibco.cep.decision.tree.common.model.impl.ModelPackageImpl#getFlowElement()
		 * @generated
		 */
		EClass FLOW_ELEMENT = eINSTANCE.getFlowElement();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FLOW_ELEMENT__ID = eINSTANCE.getFlowElement_Id();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FLOW_ELEMENT__NAME = eINSTANCE.getFlowElement_Name();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FLOW_ELEMENT__DESCRIPTION = eINSTANCE.getFlowElement_Description();

		/**
		 * The meta object literal for the '<em><b>Data</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FLOW_ELEMENT__DATA = eINSTANCE.getFlowElement_Data();

		/**
		 * The meta object literal for the '<em><b>Assoc Edge</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FLOW_ELEMENT__ASSOC_EDGE = eINSTANCE.getFlowElement_AssocEdge();

	}

} //ModelPackage
