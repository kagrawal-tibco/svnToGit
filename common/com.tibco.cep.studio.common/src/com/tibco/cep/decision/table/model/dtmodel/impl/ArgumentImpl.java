/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.table.model.dtmodel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.decision.table.model.dtmodel.Argument;
import com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty;
import com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Argument</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.impl.ArgumentImpl#getProperty <em>Property</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.impl.ArgumentImpl#getDirection <em>Direction</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ArgumentImpl extends EObjectImpl implements Argument {
	/**
	 * The cached value of the '{@link #getProperty() <em>Property</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperty()
	 * @generated
	 * @ordered
	 */
	protected ArgumentProperty property;

	/**
	 * The default value of the '{@link #getDirection() <em>Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDirection()
	 * @generated
	 * @ordered
	 */
	protected static final String DIRECTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDirection() <em>Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDirection()
	 * @generated
	 * @ordered
	 */
	protected String direction = DIRECTION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ArgumentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DtmodelPackage.Literals.ARGUMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArgumentProperty getProperty() {
		return property;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProperty(ArgumentProperty newProperty, NotificationChain msgs) {
		ArgumentProperty oldProperty = property;
		property = newProperty;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DtmodelPackage.ARGUMENT__PROPERTY, oldProperty, newProperty);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProperty(ArgumentProperty newProperty) {
		if (newProperty != property) {
			NotificationChain msgs = null;
			if (property != null)
				msgs = ((InternalEObject)property).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DtmodelPackage.ARGUMENT__PROPERTY, null, msgs);
			if (newProperty != null)
				msgs = ((InternalEObject)newProperty).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DtmodelPackage.ARGUMENT__PROPERTY, null, msgs);
			msgs = basicSetProperty(newProperty, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DtmodelPackage.ARGUMENT__PROPERTY, newProperty, newProperty));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDirection() {
		return direction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDirection(String newDirection) {
		String oldDirection = direction;
		direction = newDirection;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DtmodelPackage.ARGUMENT__DIRECTION, oldDirection, direction));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DtmodelPackage.ARGUMENT__PROPERTY:
				return basicSetProperty(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DtmodelPackage.ARGUMENT__PROPERTY:
				return getProperty();
			case DtmodelPackage.ARGUMENT__DIRECTION:
				return getDirection();
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
			case DtmodelPackage.ARGUMENT__PROPERTY:
				setProperty((ArgumentProperty)newValue);
				return;
			case DtmodelPackage.ARGUMENT__DIRECTION:
				setDirection((String)newValue);
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
			case DtmodelPackage.ARGUMENT__PROPERTY:
				setProperty((ArgumentProperty)null);
				return;
			case DtmodelPackage.ARGUMENT__DIRECTION:
				setDirection(DIRECTION_EDEFAULT);
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
			case DtmodelPackage.ARGUMENT__PROPERTY:
				return property != null;
			case DtmodelPackage.ARGUMENT__DIRECTION:
				return DIRECTION_EDEFAULT == null ? direction != null : !DIRECTION_EDEFAULT.equals(direction);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (direction: ");
		result.append(direction);
		result.append(')');
		return result.toString();
	}

} //ArgumentImpl
