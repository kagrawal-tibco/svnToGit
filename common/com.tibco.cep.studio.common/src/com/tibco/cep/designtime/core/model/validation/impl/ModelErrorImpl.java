/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.validation.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.designtime.core.model.validation.ModelError;
import com.tibco.cep.designtime.core.model.validation.ValidationPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Model Error</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.validation.impl.ModelErrorImpl#getMessage <em>Message</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.validation.impl.ModelErrorImpl#isWarning <em>Warning</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.validation.impl.ModelErrorImpl#getSource <em>Source</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ModelErrorImpl extends EObjectImpl implements ModelError {
	/**
	 * The default value of the '{@link #getMessage() <em>Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMessage()
	 * @generated
	 * @ordered
	 */
	protected static final String MESSAGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMessage() <em>Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMessage()
	 * @generated
	 * @ordered
	 */
	protected String message = MESSAGE_EDEFAULT;

	/**
	 * The default value of the '{@link #isWarning() <em>Warning</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isWarning()
	 * @generated
	 * @ordered
	 */
	protected static final boolean WARNING_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isWarning() <em>Warning</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isWarning()
	 * @generated
	 * @ordered
	 */
	protected boolean warning = WARNING_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSource() <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected EObject source;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ModelErrorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ValidationPackage.Literals.MODEL_ERROR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMessage(String newMessage) {
		String oldMessage = message;
		message = newMessage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ValidationPackage.MODEL_ERROR__MESSAGE, oldMessage, message));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isWarning() {
		return warning;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWarning(boolean newWarning) {
		boolean oldWarning = warning;
		warning = newWarning;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ValidationPackage.MODEL_ERROR__WARNING, oldWarning, warning));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getSource() {
		if (source != null && source.eIsProxy()) {
			InternalEObject oldSource = (InternalEObject)source;
			source = eResolveProxy(oldSource);
			if (source != oldSource) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ValidationPackage.MODEL_ERROR__SOURCE, oldSource, source));
			}
		}
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetSource() {
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSource(EObject newSource) {
		EObject oldSource = source;
		source = newSource;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ValidationPackage.MODEL_ERROR__SOURCE, oldSource, source));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ValidationPackage.MODEL_ERROR__MESSAGE:
				return getMessage();
			case ValidationPackage.MODEL_ERROR__WARNING:
				return isWarning();
			case ValidationPackage.MODEL_ERROR__SOURCE:
				if (resolve) return getSource();
				return basicGetSource();
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
			case ValidationPackage.MODEL_ERROR__MESSAGE:
				setMessage((String)newValue);
				return;
			case ValidationPackage.MODEL_ERROR__WARNING:
				setWarning((Boolean)newValue);
				return;
			case ValidationPackage.MODEL_ERROR__SOURCE:
				setSource((EObject)newValue);
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
			case ValidationPackage.MODEL_ERROR__MESSAGE:
				setMessage(MESSAGE_EDEFAULT);
				return;
			case ValidationPackage.MODEL_ERROR__WARNING:
				setWarning(WARNING_EDEFAULT);
				return;
			case ValidationPackage.MODEL_ERROR__SOURCE:
				setSource((EObject)null);
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
			case ValidationPackage.MODEL_ERROR__MESSAGE:
				return MESSAGE_EDEFAULT == null ? message != null : !MESSAGE_EDEFAULT.equals(message);
			case ValidationPackage.MODEL_ERROR__WARNING:
				return warning != WARNING_EDEFAULT;
			case ValidationPackage.MODEL_ERROR__SOURCE:
				return source != null;
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
		result.append(" (message: ");
		result.append(message);
		result.append(", warning: ");
		result.append(warning);
		result.append(')');
		return result.toString();
	}

} //ModelErrorImpl
