/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.tree.common.model.edge.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.decision.tree.common.model.edge.Edge;
import com.tibco.cep.decision.tree.common.model.edge.EdgePackage;
import com.tibco.cep.decision.tree.common.model.impl.FlowElementImpl;
import com.tibco.cep.decision.tree.common.model.node.NodeElement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Edge</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.edge.impl.EdgeImpl#getSrc <em>Src</em>}</li>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.edge.impl.EdgeImpl#getTgt <em>Tgt</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EdgeImpl extends FlowElementImpl implements Edge {
	/**
	 * The cached value of the '{@link #getSrc() <em>Src</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSrc()
	 * @generated
	 * @ordered
	 */
	protected NodeElement src;

	/**
	 * The cached value of the '{@link #getTgt() <em>Tgt</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTgt()
	 * @generated
	 * @ordered
	 */
	protected NodeElement tgt;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EdgeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EdgePackage.Literals.EDGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NodeElement getSrc() {
		if (src != null && src.eIsProxy()) {
			InternalEObject oldSrc = (InternalEObject)src;
			src = (NodeElement)eResolveProxy(oldSrc);
			if (src != oldSrc) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, EdgePackage.EDGE__SRC, oldSrc, src));
			}
		}
		return src;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NodeElement basicGetSrc() {
		return src;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSrc(NodeElement newSrc) {
		NodeElement oldSrc = src;
		src = newSrc;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EdgePackage.EDGE__SRC, oldSrc, src));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NodeElement getTgt() {
		if (tgt != null && tgt.eIsProxy()) {
			InternalEObject oldTgt = (InternalEObject)tgt;
			tgt = (NodeElement)eResolveProxy(oldTgt);
			if (tgt != oldTgt) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, EdgePackage.EDGE__TGT, oldTgt, tgt));
			}
		}
		return tgt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NodeElement basicGetTgt() {
		return tgt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTgt(NodeElement newTgt) {
		NodeElement oldTgt = tgt;
		tgt = newTgt;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EdgePackage.EDGE__TGT, oldTgt, tgt));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case EdgePackage.EDGE__SRC:
				if (resolve) return getSrc();
				return basicGetSrc();
			case EdgePackage.EDGE__TGT:
				if (resolve) return getTgt();
				return basicGetTgt();
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
			case EdgePackage.EDGE__SRC:
				setSrc((NodeElement)newValue);
				return;
			case EdgePackage.EDGE__TGT:
				setTgt((NodeElement)newValue);
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
			case EdgePackage.EDGE__SRC:
				setSrc((NodeElement)null);
				return;
			case EdgePackage.EDGE__TGT:
				setTgt((NodeElement)null);
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
			case EdgePackage.EDGE__SRC:
				return src != null;
			case EdgePackage.EDGE__TGT:
				return tgt != null;
		}
		return super.eIsSet(featureID);
	}

} //EdgeImpl
