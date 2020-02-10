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
import com.tibco.cep.decision.tree.common.model.node.condition.BoolCond;
import com.tibco.cep.decision.tree.common.model.node.condition.ConditionPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Bool Cond</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.node.condition.impl.BoolCondImpl#getFalseEdge <em>False Edge</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BoolCondImpl extends CondImpl implements BoolCond {
	/**
	 * The cached value of the '{@link #getFalseEdge() <em>False Edge</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFalseEdge()
	 * @generated
	 * @ordered
	 */
	protected Edge falseEdge;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BoolCondImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ConditionPackage.Literals.BOOL_COND;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Edge getFalseEdge() {
		if (falseEdge != null && falseEdge.eIsProxy()) {
			InternalEObject oldFalseEdge = (InternalEObject)falseEdge;
			falseEdge = (Edge)eResolveProxy(oldFalseEdge);
			if (falseEdge != oldFalseEdge) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ConditionPackage.BOOL_COND__FALSE_EDGE, oldFalseEdge, falseEdge));
			}
		}
		return falseEdge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Edge basicGetFalseEdge() {
		return falseEdge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFalseEdge(Edge newFalseEdge) {
		Edge oldFalseEdge = falseEdge;
		falseEdge = newFalseEdge;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConditionPackage.BOOL_COND__FALSE_EDGE, oldFalseEdge, falseEdge));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ConditionPackage.BOOL_COND__FALSE_EDGE:
				if (resolve) return getFalseEdge();
				return basicGetFalseEdge();
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
			case ConditionPackage.BOOL_COND__FALSE_EDGE:
				setFalseEdge((Edge)newValue);
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
			case ConditionPackage.BOOL_COND__FALSE_EDGE:
				setFalseEdge((Edge)null);
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
			case ConditionPackage.BOOL_COND__FALSE_EDGE:
				return falseEdge != null;
		}
		return super.eIsSet(featureID);
	}

} //BoolCondImpl
