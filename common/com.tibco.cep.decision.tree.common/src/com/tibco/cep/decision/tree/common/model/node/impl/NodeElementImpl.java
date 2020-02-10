/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.tree.common.model.node.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import com.tibco.cep.decision.tree.common.model.edge.Edge;
import com.tibco.cep.decision.tree.common.model.impl.FlowElementImpl;
import com.tibco.cep.decision.tree.common.model.node.NodeElement;
import com.tibco.cep.decision.tree.common.model.node.NodePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.node.impl.NodeElementImpl#getInEdges <em>In Edges</em>}</li>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.node.impl.NodeElementImpl#getOutEdge <em>Out Edge</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NodeElementImpl extends FlowElementImpl implements NodeElement {
	/**
	 * The cached value of the '{@link #getInEdges() <em>In Edges</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInEdges()
	 * @generated
	 * @ordered
	 */
	protected EList<Edge> inEdges;

	/**
	 * The cached value of the '{@link #getOutEdge() <em>Out Edge</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutEdge()
	 * @generated
	 * @ordered
	 */
	protected Edge outEdge;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NodeElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return NodePackage.Literals.NODE_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Edge> getInEdges() {
		if (inEdges == null) {
			inEdges = new EObjectResolvingEList<Edge>(Edge.class, this, NodePackage.NODE_ELEMENT__IN_EDGES);
		}
		return inEdges;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Edge getOutEdge() {
		if (outEdge != null && outEdge.eIsProxy()) {
			InternalEObject oldOutEdge = (InternalEObject)outEdge;
			outEdge = (Edge)eResolveProxy(oldOutEdge);
			if (outEdge != oldOutEdge) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, NodePackage.NODE_ELEMENT__OUT_EDGE, oldOutEdge, outEdge));
			}
		}
		return outEdge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Edge basicGetOutEdge() {
		return outEdge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOutEdge(Edge newOutEdge) {
		Edge oldOutEdge = outEdge;
		outEdge = newOutEdge;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NodePackage.NODE_ELEMENT__OUT_EDGE, oldOutEdge, outEdge));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case NodePackage.NODE_ELEMENT__IN_EDGES:
				return getInEdges();
			case NodePackage.NODE_ELEMENT__OUT_EDGE:
				if (resolve) return getOutEdge();
				return basicGetOutEdge();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case NodePackage.NODE_ELEMENT__IN_EDGES:
				getInEdges().clear();
				getInEdges().addAll((Collection<? extends Edge>)newValue);
				return;
			case NodePackage.NODE_ELEMENT__OUT_EDGE:
				setOutEdge((Edge)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case NodePackage.NODE_ELEMENT__IN_EDGES:
				getInEdges().clear();
				return;
			case NodePackage.NODE_ELEMENT__OUT_EDGE:
				setOutEdge((Edge)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case NodePackage.NODE_ELEMENT__IN_EDGES:
				return inEdges != null && !inEdges.isEmpty();
			case NodePackage.NODE_ELEMENT__OUT_EDGE:
				return outEdge != null;
		}
		return super.eIsSet(featureID);
	}

} //NodeElementImpl
