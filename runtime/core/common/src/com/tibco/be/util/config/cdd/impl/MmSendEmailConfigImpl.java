/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.MmSendEmailConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Mm Send Email Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.MmSendEmailConfigImpl#getCc <em>Cc</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.MmSendEmailConfigImpl#getMsg <em>Msg</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.MmSendEmailConfigImpl#getSubject <em>Subject</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.MmSendEmailConfigImpl#getTo <em>To</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MmSendEmailConfigImpl extends EObjectImpl implements MmSendEmailConfig {
	/**
	 * The default value of the '{@link #getCc() <em>Cc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCc()
	 * @generated
	 * @ordered
	 */
	protected static final String CC_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCc() <em>Cc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCc()
	 * @generated
	 * @ordered
	 */
	protected String cc = CC_EDEFAULT;

	/**
	 * The default value of the '{@link #getMsg() <em>Msg</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMsg()
	 * @generated
	 * @ordered
	 */
	protected static final String MSG_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMsg() <em>Msg</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMsg()
	 * @generated
	 * @ordered
	 */
	protected String msg = MSG_EDEFAULT;

	/**
	 * The default value of the '{@link #getSubject() <em>Subject</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubject()
	 * @generated
	 * @ordered
	 */
	protected static final String SUBJECT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSubject() <em>Subject</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubject()
	 * @generated
	 * @ordered
	 */
	protected String subject = SUBJECT_EDEFAULT;

	/**
	 * The default value of the '{@link #getTo() <em>To</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTo()
	 * @generated
	 * @ordered
	 */
	protected static final String TO_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTo() <em>To</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTo()
	 * @generated
	 * @ordered
	 */
	protected String to = TO_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MmSendEmailConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getMmSendEmailConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCc() {
		return cc;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCc(String newCc) {
		String oldCc = cc;
		cc = newCc;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.MM_SEND_EMAIL_CONFIG__CC, oldCc, cc));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMsg(String newMsg) {
		String oldMsg = msg;
		msg = newMsg;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.MM_SEND_EMAIL_CONFIG__MSG, oldMsg, msg));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSubject(String newSubject) {
		String oldSubject = subject;
		subject = newSubject;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.MM_SEND_EMAIL_CONFIG__SUBJECT, oldSubject, subject));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTo() {
		return to;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTo(String newTo) {
		String oldTo = to;
		to = newTo;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.MM_SEND_EMAIL_CONFIG__TO, oldTo, to));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CddPackage.MM_SEND_EMAIL_CONFIG__CC:
				return getCc();
			case CddPackage.MM_SEND_EMAIL_CONFIG__MSG:
				return getMsg();
			case CddPackage.MM_SEND_EMAIL_CONFIG__SUBJECT:
				return getSubject();
			case CddPackage.MM_SEND_EMAIL_CONFIG__TO:
				return getTo();
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
			case CddPackage.MM_SEND_EMAIL_CONFIG__CC:
				setCc((String)newValue);
				return;
			case CddPackage.MM_SEND_EMAIL_CONFIG__MSG:
				setMsg((String)newValue);
				return;
			case CddPackage.MM_SEND_EMAIL_CONFIG__SUBJECT:
				setSubject((String)newValue);
				return;
			case CddPackage.MM_SEND_EMAIL_CONFIG__TO:
				setTo((String)newValue);
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
			case CddPackage.MM_SEND_EMAIL_CONFIG__CC:
				setCc(CC_EDEFAULT);
				return;
			case CddPackage.MM_SEND_EMAIL_CONFIG__MSG:
				setMsg(MSG_EDEFAULT);
				return;
			case CddPackage.MM_SEND_EMAIL_CONFIG__SUBJECT:
				setSubject(SUBJECT_EDEFAULT);
				return;
			case CddPackage.MM_SEND_EMAIL_CONFIG__TO:
				setTo(TO_EDEFAULT);
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
			case CddPackage.MM_SEND_EMAIL_CONFIG__CC:
				return CC_EDEFAULT == null ? cc != null : !CC_EDEFAULT.equals(cc);
			case CddPackage.MM_SEND_EMAIL_CONFIG__MSG:
				return MSG_EDEFAULT == null ? msg != null : !MSG_EDEFAULT.equals(msg);
			case CddPackage.MM_SEND_EMAIL_CONFIG__SUBJECT:
				return SUBJECT_EDEFAULT == null ? subject != null : !SUBJECT_EDEFAULT.equals(subject);
			case CddPackage.MM_SEND_EMAIL_CONFIG__TO:
				return TO_EDEFAULT == null ? to != null : !TO_EDEFAULT.equals(to);
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
		result.append(" (cc: ");
		result.append(cc);
		result.append(", msg: ");
		result.append(msg);
		result.append(", subject: ");
		result.append(subject);
		result.append(", to: ");
		result.append(to);
		result.append(')');
		return result.toString();
	}

} //MmSendEmailConfigImpl
