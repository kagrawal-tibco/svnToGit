/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.tree.common.model.edge;

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
 * @see com.tibco.cep.decision.tree.common.model.edge.EdgeFactory
 * @model kind="package"
 * @generated
 */
public interface EdgePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "edge";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http:///com/tibco/cep/decision/tree/model/edge";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "edge";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EdgePackage eINSTANCE = com.tibco.cep.decision.tree.common.model.edge.impl.EdgePackageImpl.init();

	/**
	 * The meta object id for the '{@link com.tibco.cep.decision.tree.common.model.edge.impl.EdgeImpl <em>Edge</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decision.tree.common.model.edge.impl.EdgeImpl
	 * @see com.tibco.cep.decision.tree.common.model.edge.impl.EdgePackageImpl#getEdge()
	 * @generated
	 */
	int EDGE = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE__ID = ModelPackage.FLOW_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE__NAME = ModelPackage.FLOW_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE__DESCRIPTION = ModelPackage.FLOW_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE__DATA = ModelPackage.FLOW_ELEMENT__DATA;

	/**
	 * The feature id for the '<em><b>Assoc Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE__ASSOC_EDGE = ModelPackage.FLOW_ELEMENT__ASSOC_EDGE;

	/**
	 * The feature id for the '<em><b>Src</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE__SRC = ModelPackage.FLOW_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Tgt</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE__TGT = ModelPackage.FLOW_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Edge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_FEATURE_COUNT = ModelPackage.FLOW_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decision.tree.common.model.edge.impl.AssociationImpl <em>Association</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decision.tree.common.model.edge.impl.AssociationImpl
	 * @see com.tibco.cep.decision.tree.common.model.edge.impl.EdgePackageImpl#getAssociation()
	 * @generated
	 */
	int ASSOCIATION = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSOCIATION__ID = EDGE__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSOCIATION__NAME = EDGE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSOCIATION__DESCRIPTION = EDGE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSOCIATION__DATA = EDGE__DATA;

	/**
	 * The feature id for the '<em><b>Assoc Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSOCIATION__ASSOC_EDGE = EDGE__ASSOC_EDGE;

	/**
	 * The feature id for the '<em><b>Src</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSOCIATION__SRC = EDGE__SRC;

	/**
	 * The feature id for the '<em><b>Tgt</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSOCIATION__TGT = EDGE__TGT;

	/**
	 * The number of structural features of the '<em>Association</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSOCIATION_FEATURE_COUNT = EDGE_FEATURE_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decision.tree.common.model.edge.Edge <em>Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Edge</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.edge.Edge
	 * @generated
	 */
	EClass getEdge();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.decision.tree.common.model.edge.Edge#getSrc <em>Src</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Src</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.edge.Edge#getSrc()
	 * @see #getEdge()
	 * @generated
	 */
	EReference getEdge_Src();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.decision.tree.common.model.edge.Edge#getTgt <em>Tgt</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Tgt</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.edge.Edge#getTgt()
	 * @see #getEdge()
	 * @generated
	 */
	EReference getEdge_Tgt();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decision.tree.common.model.edge.Association <em>Association</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Association</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.edge.Association
	 * @generated
	 */
	EClass getAssociation();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	EdgeFactory getEdgeFactory();

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
		 * The meta object literal for the '{@link com.tibco.cep.decision.tree.common.model.edge.impl.EdgeImpl <em>Edge</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decision.tree.common.model.edge.impl.EdgeImpl
		 * @see com.tibco.cep.decision.tree.common.model.edge.impl.EdgePackageImpl#getEdge()
		 * @generated
		 */
		EClass EDGE = eINSTANCE.getEdge();

		/**
		 * The meta object literal for the '<em><b>Src</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EDGE__SRC = eINSTANCE.getEdge_Src();

		/**
		 * The meta object literal for the '<em><b>Tgt</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EDGE__TGT = eINSTANCE.getEdge_Tgt();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decision.tree.common.model.edge.impl.AssociationImpl <em>Association</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decision.tree.common.model.edge.impl.AssociationImpl
		 * @see com.tibco.cep.decision.tree.common.model.edge.impl.EdgePackageImpl#getAssociation()
		 * @generated
		 */
		EClass ASSOCIATION = eINSTANCE.getAssociation();

	}

} //EdgePackage
