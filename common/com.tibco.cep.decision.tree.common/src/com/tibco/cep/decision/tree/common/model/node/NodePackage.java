/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.tree.common.model.node;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import com.tibco.cep.decision.tree.common.model.ModelPackage;

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
 * @see com.tibco.cep.decision.tree.common.model.node.NodeFactory
 * @model kind="package"
 * @generated
 */
public interface NodePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "node";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http:///com/tibco/cep/decision/tree/model/node";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "node";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	NodePackage eINSTANCE = com.tibco.cep.decision.tree.common.model.node.impl.NodePackageImpl.init();

	/**
	 * The meta object id for the '{@link com.tibco.cep.decision.tree.common.model.node.impl.NodeElementImpl <em>Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decision.tree.common.model.node.impl.NodeElementImpl
	 * @see com.tibco.cep.decision.tree.common.model.node.impl.NodePackageImpl#getNodeElement()
	 * @generated
	 */
	int NODE_ELEMENT = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_ELEMENT__ID = ModelPackage.FLOW_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_ELEMENT__NAME = ModelPackage.FLOW_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_ELEMENT__DESCRIPTION = ModelPackage.FLOW_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_ELEMENT__DATA = ModelPackage.FLOW_ELEMENT__DATA;

	/**
	 * The feature id for the '<em><b>Assoc Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_ELEMENT__ASSOC_EDGE = ModelPackage.FLOW_ELEMENT__ASSOC_EDGE;

	/**
	 * The feature id for the '<em><b>In Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_ELEMENT__IN_EDGES = ModelPackage.FLOW_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Out Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_ELEMENT__OUT_EDGE = ModelPackage.FLOW_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_ELEMENT_FEATURE_COUNT = ModelPackage.FLOW_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decision.tree.common.model.node.impl.LoopImpl <em>Loop</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decision.tree.common.model.node.impl.LoopImpl
	 * @see com.tibco.cep.decision.tree.common.model.node.impl.NodePackageImpl#getLoop()
	 * @generated
	 */
	int LOOP = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOP__ID = NODE_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOP__NAME = NODE_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOP__DESCRIPTION = NODE_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOP__DATA = NODE_ELEMENT__DATA;

	/**
	 * The feature id for the '<em><b>Assoc Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOP__ASSOC_EDGE = NODE_ELEMENT__ASSOC_EDGE;

	/**
	 * The feature id for the '<em><b>In Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOP__IN_EDGES = NODE_ELEMENT__IN_EDGES;

	/**
	 * The feature id for the '<em><b>Out Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOP__OUT_EDGE = NODE_ELEMENT__OUT_EDGE;

	/**
	 * The feature id for the '<em><b>Loop Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOP__LOOP_TYPE = NODE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Loop Condition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOP__LOOP_CONDITION = NODE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Elements</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOP__ELEMENTS = NODE_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Start Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOP__START_NODE = NODE_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>End Nodes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOP__END_NODES = NODE_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Loop</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOP_FEATURE_COUNT = NODE_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decision.tree.common.model.node.impl.DescriptionElementImpl <em>Description Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decision.tree.common.model.node.impl.DescriptionElementImpl
	 * @see com.tibco.cep.decision.tree.common.model.node.impl.NodePackageImpl#getDescriptionElement()
	 * @generated
	 */
	int DESCRIPTION_ELEMENT = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESCRIPTION_ELEMENT__ID = NODE_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESCRIPTION_ELEMENT__NAME = NODE_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESCRIPTION_ELEMENT__DESCRIPTION = NODE_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESCRIPTION_ELEMENT__DATA = NODE_ELEMENT__DATA;

	/**
	 * The feature id for the '<em><b>Assoc Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESCRIPTION_ELEMENT__ASSOC_EDGE = NODE_ELEMENT__ASSOC_EDGE;

	/**
	 * The feature id for the '<em><b>In Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESCRIPTION_ELEMENT__IN_EDGES = NODE_ELEMENT__IN_EDGES;

	/**
	 * The feature id for the '<em><b>Out Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESCRIPTION_ELEMENT__OUT_EDGE = NODE_ELEMENT__OUT_EDGE;

	/**
	 * The feature id for the '<em><b>Assoc Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESCRIPTION_ELEMENT__ASSOC_ELEMENT = NODE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Description Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESCRIPTION_ELEMENT_FEATURE_COUNT = NODE_ELEMENT_FEATURE_COUNT + 1;


	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decision.tree.common.model.node.NodeElement <em>Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Element</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.node.NodeElement
	 * @generated
	 */
	EClass getNodeElement();

	/**
	 * Returns the meta object for the reference list '{@link com.tibco.cep.decision.tree.common.model.node.NodeElement#getInEdges <em>In Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>In Edges</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.node.NodeElement#getInEdges()
	 * @see #getNodeElement()
	 * @generated
	 */
	EReference getNodeElement_InEdges();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.decision.tree.common.model.node.NodeElement#getOutEdge <em>Out Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Out Edge</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.node.NodeElement#getOutEdge()
	 * @see #getNodeElement()
	 * @generated
	 */
	EReference getNodeElement_OutEdge();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decision.tree.common.model.node.Loop <em>Loop</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Loop</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.node.Loop
	 * @generated
	 */
	EClass getLoop();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.tree.common.model.node.Loop#getLoopType <em>Loop Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Loop Type</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.node.Loop#getLoopType()
	 * @see #getLoop()
	 * @generated
	 */
	EAttribute getLoop_LoopType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.tree.common.model.node.Loop#getLoopCondition <em>Loop Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Loop Condition</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.node.Loop#getLoopCondition()
	 * @see #getLoop()
	 * @generated
	 */
	EAttribute getLoop_LoopCondition();

	/**
	 * Returns the meta object for the reference list '{@link com.tibco.cep.decision.tree.common.model.node.Loop#getElements <em>Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Elements</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.node.Loop#getElements()
	 * @see #getLoop()
	 * @generated
	 */
	EReference getLoop_Elements();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.decision.tree.common.model.node.Loop#getStartNode <em>Start Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Start Node</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.node.Loop#getStartNode()
	 * @see #getLoop()
	 * @generated
	 */
	EReference getLoop_StartNode();

	/**
	 * Returns the meta object for the reference list '{@link com.tibco.cep.decision.tree.common.model.node.Loop#getEndNodes <em>End Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>End Nodes</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.node.Loop#getEndNodes()
	 * @see #getLoop()
	 * @generated
	 */
	EReference getLoop_EndNodes();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decision.tree.common.model.node.DescriptionElement <em>Description Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Description Element</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.node.DescriptionElement
	 * @generated
	 */
	EClass getDescriptionElement();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.decision.tree.common.model.node.DescriptionElement#getAssocElement <em>Assoc Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Assoc Element</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.node.DescriptionElement#getAssocElement()
	 * @see #getDescriptionElement()
	 * @generated
	 */
	EReference getDescriptionElement_AssocElement();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	NodeFactory getNodeFactory();

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
		 * The meta object literal for the '{@link com.tibco.cep.decision.tree.common.model.node.impl.NodeElementImpl <em>Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decision.tree.common.model.node.impl.NodeElementImpl
		 * @see com.tibco.cep.decision.tree.common.model.node.impl.NodePackageImpl#getNodeElement()
		 * @generated
		 */
		EClass NODE_ELEMENT = eINSTANCE.getNodeElement();

		/**
		 * The meta object literal for the '<em><b>In Edges</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE_ELEMENT__IN_EDGES = eINSTANCE.getNodeElement_InEdges();

		/**
		 * The meta object literal for the '<em><b>Out Edge</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE_ELEMENT__OUT_EDGE = eINSTANCE.getNodeElement_OutEdge();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decision.tree.common.model.node.impl.LoopImpl <em>Loop</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decision.tree.common.model.node.impl.LoopImpl
		 * @see com.tibco.cep.decision.tree.common.model.node.impl.NodePackageImpl#getLoop()
		 * @generated
		 */
		EClass LOOP = eINSTANCE.getLoop();

		/**
		 * The meta object literal for the '<em><b>Loop Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOOP__LOOP_TYPE = eINSTANCE.getLoop_LoopType();

		/**
		 * The meta object literal for the '<em><b>Loop Condition</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOOP__LOOP_CONDITION = eINSTANCE.getLoop_LoopCondition();

		/**
		 * The meta object literal for the '<em><b>Elements</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LOOP__ELEMENTS = eINSTANCE.getLoop_Elements();

		/**
		 * The meta object literal for the '<em><b>Start Node</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LOOP__START_NODE = eINSTANCE.getLoop_StartNode();

		/**
		 * The meta object literal for the '<em><b>End Nodes</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LOOP__END_NODES = eINSTANCE.getLoop_EndNodes();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decision.tree.common.model.node.impl.DescriptionElementImpl <em>Description Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decision.tree.common.model.node.impl.DescriptionElementImpl
		 * @see com.tibco.cep.decision.tree.common.model.node.impl.NodePackageImpl#getDescriptionElement()
		 * @generated
		 */
		EClass DESCRIPTION_ELEMENT = eINSTANCE.getDescriptionElement();

		/**
		 * The meta object literal for the '<em><b>Assoc Element</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DESCRIPTION_ELEMENT__ASSOC_ELEMENT = eINSTANCE.getDescriptionElement_AssocElement();

	}

} //NodePackage
