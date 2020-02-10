/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.studio.core.index.model.DecisionTableElement;
import com.tibco.cep.studio.core.index.model.IStructuredElementVisitor;
import com.tibco.cep.studio.core.index.model.IndexPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Decision Table Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.DecisionTableElementImpl#getImplementation <em>Implementation</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DecisionTableElementImpl extends TypeElementImpl implements DecisionTableElement {
	/**
	 * The cached value of the '{@link #getImplementation() <em>Implementation</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplementation()
	 * @generated
	 * @ordered
	 */
	protected EObject implementation;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DecisionTableElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IndexPackage.Literals.DECISION_TABLE_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getImplementation() {
		if (implementation != null && implementation.eIsProxy()) {
			InternalEObject oldImplementation = (InternalEObject)implementation;
			implementation = eResolveProxy(oldImplementation);
			if (implementation != oldImplementation) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, IndexPackage.DECISION_TABLE_ELEMENT__IMPLEMENTATION, oldImplementation, implementation));
			}
		}
		return implementation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetImplementation() {
		return implementation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImplementation(EObject newImplementation) {
		EObject oldImplementation = implementation;
		implementation = newImplementation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.DECISION_TABLE_ELEMENT__IMPLEMENTATION, oldImplementation, implementation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case IndexPackage.DECISION_TABLE_ELEMENT__IMPLEMENTATION:
				if (resolve) return getImplementation();
				return basicGetImplementation();
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
			case IndexPackage.DECISION_TABLE_ELEMENT__IMPLEMENTATION:
				setImplementation((EObject)newValue);
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
			case IndexPackage.DECISION_TABLE_ELEMENT__IMPLEMENTATION:
				setImplementation((EObject)null);
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
			case IndexPackage.DECISION_TABLE_ELEMENT__IMPLEMENTATION:
				return implementation != null;
		}
		return super.eIsSet(featureID);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.index.model.impl.DesignerElementImpl#accept(com.tibco.cep.studio.core.index.model.IStructuredElementVisitor)
	 * @generated NOT
	 */
	public void accept(IStructuredElementVisitor visitor) {
        visitor.preVisit(this);
        if (visitor.visit(this)) {
        	// do nothing, no children to visit
        }
        visitor.postVisit(this);
	}

} //DecisionTableElementImpl
