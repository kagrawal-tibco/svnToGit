/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd.impl;

import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.OverrideConfig;
import com.tibco.be.util.config.cdd.RevisionConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Revision Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.RevisionConfigImpl#getVersion <em>Version</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.RevisionConfigImpl#getAuthor <em>Author</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.RevisionConfigImpl#getDate <em>Date</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.RevisionConfigImpl#getComment <em>Comment</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RevisionConfigImpl extends EObjectImpl implements RevisionConfig {
	/**
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected String version = VERSION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAuthor() <em>Author</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthor()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig author;

	/**
	 * The cached value of the '{@link #getDate() <em>Date</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDate()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig date;

	/**
	 * The cached value of the '{@link #getComment() <em>Comment</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComment()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig comment;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RevisionConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getRevisionConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVersion(String newVersion) {
		String oldVersion = version;
		version = newVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.REVISION_CONFIG__VERSION, oldVersion, version));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getAuthor() {
		return author;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAuthor(OverrideConfig newAuthor, NotificationChain msgs) {
		OverrideConfig oldAuthor = author;
		author = newAuthor;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.REVISION_CONFIG__AUTHOR, oldAuthor, newAuthor);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAuthor(OverrideConfig newAuthor) {
		if (newAuthor != author) {
			NotificationChain msgs = null;
			if (author != null)
				msgs = ((InternalEObject)author).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.REVISION_CONFIG__AUTHOR, null, msgs);
			if (newAuthor != null)
				msgs = ((InternalEObject)newAuthor).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.REVISION_CONFIG__AUTHOR, null, msgs);
			msgs = basicSetAuthor(newAuthor, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.REVISION_CONFIG__AUTHOR, newAuthor, newAuthor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getDate() {
		return date;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDate(OverrideConfig newDate, NotificationChain msgs) {
		OverrideConfig oldDate = date;
		date = newDate;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.REVISION_CONFIG__DATE, oldDate, newDate);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDate(OverrideConfig newDate) {
		if (newDate != date) {
			NotificationChain msgs = null;
			if (date != null)
				msgs = ((InternalEObject)date).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.REVISION_CONFIG__DATE, null, msgs);
			if (newDate != null)
				msgs = ((InternalEObject)newDate).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.REVISION_CONFIG__DATE, null, msgs);
			msgs = basicSetDate(newDate, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.REVISION_CONFIG__DATE, newDate, newDate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getComment() {
		return comment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetComment(OverrideConfig newComment, NotificationChain msgs) {
		OverrideConfig oldComment = comment;
		comment = newComment;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.REVISION_CONFIG__COMMENT, oldComment, newComment);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComment(OverrideConfig newComment) {
		if (newComment != comment) {
			NotificationChain msgs = null;
			if (comment != null)
				msgs = ((InternalEObject)comment).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.REVISION_CONFIG__COMMENT, null, msgs);
			if (newComment != null)
				msgs = ((InternalEObject)newComment).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.REVISION_CONFIG__COMMENT, null, msgs);
			msgs = basicSetComment(newComment, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.REVISION_CONFIG__COMMENT, newComment, newComment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map<Object, Object> toProperties() {
		return new java.util.Properties();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.REVISION_CONFIG__AUTHOR:
				return basicSetAuthor(null, msgs);
			case CddPackage.REVISION_CONFIG__DATE:
				return basicSetDate(null, msgs);
			case CddPackage.REVISION_CONFIG__COMMENT:
				return basicSetComment(null, msgs);
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
			case CddPackage.REVISION_CONFIG__VERSION:
				return getVersion();
			case CddPackage.REVISION_CONFIG__AUTHOR:
				return getAuthor();
			case CddPackage.REVISION_CONFIG__DATE:
				return getDate();
			case CddPackage.REVISION_CONFIG__COMMENT:
				return getComment();
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
			case CddPackage.REVISION_CONFIG__VERSION:
				setVersion((String)newValue);
				return;
			case CddPackage.REVISION_CONFIG__AUTHOR:
				setAuthor((OverrideConfig)newValue);
				return;
			case CddPackage.REVISION_CONFIG__DATE:
				setDate((OverrideConfig)newValue);
				return;
			case CddPackage.REVISION_CONFIG__COMMENT:
				setComment((OverrideConfig)newValue);
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
			case CddPackage.REVISION_CONFIG__VERSION:
				setVersion(VERSION_EDEFAULT);
				return;
			case CddPackage.REVISION_CONFIG__AUTHOR:
				setAuthor((OverrideConfig)null);
				return;
			case CddPackage.REVISION_CONFIG__DATE:
				setDate((OverrideConfig)null);
				return;
			case CddPackage.REVISION_CONFIG__COMMENT:
				setComment((OverrideConfig)null);
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
			case CddPackage.REVISION_CONFIG__VERSION:
				return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
			case CddPackage.REVISION_CONFIG__AUTHOR:
				return author != null;
			case CddPackage.REVISION_CONFIG__DATE:
				return date != null;
			case CddPackage.REVISION_CONFIG__COMMENT:
				return comment != null;
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
		result.append(" (version: ");
		result.append(version);
		result.append(')');
		return result.toString();
	}

} //RevisionConfigImpl
