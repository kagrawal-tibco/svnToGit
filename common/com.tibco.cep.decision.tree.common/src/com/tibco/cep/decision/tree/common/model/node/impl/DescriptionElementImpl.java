/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.tree.common.model.node.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.decision.tree.common.model.FlowElement;
import com.tibco.cep.decision.tree.common.model.node.DescriptionElement;
import com.tibco.cep.decision.tree.common.model.node.NodePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Description Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.node.impl.DescriptionElementImpl#getAssocElement <em>Assoc Element</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DescriptionElementImpl extends NodeElementImpl implements DescriptionElement {
	/**
	 * The cached value of the '{@link #getAssocElement() <em>Assoc Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAssocElement()
	 * @generated
	 * @ordered
	 */
	protected FlowElement assocElement;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DescriptionElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return NodePackage.Literals.DESCRIPTION_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FlowElement getAssocElement() {
		if (assocElement != null && assocElement.eIsProxy()) {
			InternalEObject oldAssocElement = (InternalEObject)assocElement;
			assocElement = (FlowElement)eResolveProxy(oldAssocElement);
			if (assocElement != oldAssocElement) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, NodePackage.DESCRIPTION_ELEMENT__ASSOC_ELEMENT, oldAssocElement, assocElement));
			}
		}
		return assocElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FlowElement basicGetAssocElement() {
		return assocElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAssocElement(FlowElement newAssocElement) {
		FlowElement oldAssocElement = assocElement;
		assocElement = newAssocElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NodePackage.DESCRIPTION_ELEMENT__ASSOC_ELEMENT, oldAssocElement, assocElement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case NodePackage.DESCRIPTION_ELEMENT__ASSOC_ELEMENT:
				if (resolve) return getAssocElement();
				return basicGetAssocElement();
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
			case NodePackage.DESCRIPTION_ELEMENT__ASSOC_ELEMENT:
				setAssocElement((FlowElement)newValue);
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
			case NodePackage.DESCRIPTION_ELEMENT__ASSOC_ELEMENT:
				setAssocElement((FlowElement)null);
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
			case NodePackage.DESCRIPTION_ELEMENT__ASSOC_ELEMENT:
				return assocElement != null;
		}
		return super.eIsSet(featureID);
	}

} //DescriptionElementImpl
