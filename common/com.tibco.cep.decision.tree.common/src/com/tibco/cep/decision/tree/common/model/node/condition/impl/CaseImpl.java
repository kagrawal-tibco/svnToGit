/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.tree.common.model.node.condition.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.decision.tree.common.model.edge.Edge;
import com.tibco.cep.decision.tree.common.model.impl.FlowElementImpl;
import com.tibco.cep.decision.tree.common.model.node.condition.Case;
import com.tibco.cep.decision.tree.common.model.node.condition.ConditionPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Case</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.node.condition.impl.CaseImpl#getOutEdge <em>Out Edge</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CaseImpl extends FlowElementImpl implements Case {
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
	protected CaseImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ConditionPackage.Literals.CASE;
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ConditionPackage.CASE__OUT_EDGE, oldOutEdge, outEdge));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ConditionPackage.CASE__OUT_EDGE, oldOutEdge, outEdge));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ConditionPackage.CASE__OUT_EDGE:
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
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ConditionPackage.CASE__OUT_EDGE:
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
			case ConditionPackage.CASE__OUT_EDGE:
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
			case ConditionPackage.CASE__OUT_EDGE:
				return outEdge != null;
		}
		return super.eIsSet(featureID);
	}

} //CaseImpl
