/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.ConnectionConfig;
import com.tibco.be.util.config.cdd.OverrideConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Connection Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ConnectionConfigImpl#getInitialSize <em>Initial Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ConnectionConfigImpl#getMinSize <em>Min Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ConnectionConfigImpl#getMaxSize <em>Max Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ConnectionConfigImpl#getUri <em>Uri</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ConnectionConfigImpl extends ArtifactConfigImpl implements ConnectionConfig {
	/**
	 * The cached value of the '{@link #getInitialSize() <em>Initial Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitialSize()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig initialSize;

	/**
	 * The cached value of the '{@link #getMinSize() <em>Min Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMinSize()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig minSize;

	/**
	 * The cached value of the '{@link #getMaxSize() <em>Max Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxSize()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig maxSize;

	/**
	 * The default value of the '{@link #getUri() <em>Uri</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUri()
	 * @generated
	 * @ordered
	 */
	protected static final String URI_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUri() <em>Uri</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUri()
	 * @generated
	 * @ordered
	 */
	protected String uri = URI_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConnectionConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getConnectionConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getInitialSize() {
		return initialSize;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInitialSize(OverrideConfig newInitialSize, NotificationChain msgs) {
		OverrideConfig oldInitialSize = initialSize;
		initialSize = newInitialSize;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.CONNECTION_CONFIG__INITIAL_SIZE, oldInitialSize, newInitialSize);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInitialSize(OverrideConfig newInitialSize) {
		if (newInitialSize != initialSize) {
			NotificationChain msgs = null;
			if (initialSize != null)
				msgs = ((InternalEObject)initialSize).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.CONNECTION_CONFIG__INITIAL_SIZE, null, msgs);
			if (newInitialSize != null)
				msgs = ((InternalEObject)newInitialSize).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.CONNECTION_CONFIG__INITIAL_SIZE, null, msgs);
			msgs = basicSetInitialSize(newInitialSize, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.CONNECTION_CONFIG__INITIAL_SIZE, newInitialSize, newInitialSize));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getMinSize() {
		return minSize;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMinSize(OverrideConfig newMinSize, NotificationChain msgs) {
		OverrideConfig oldMinSize = minSize;
		minSize = newMinSize;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.CONNECTION_CONFIG__MIN_SIZE, oldMinSize, newMinSize);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMinSize(OverrideConfig newMinSize) {
		if (newMinSize != minSize) {
			NotificationChain msgs = null;
			if (minSize != null)
				msgs = ((InternalEObject)minSize).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.CONNECTION_CONFIG__MIN_SIZE, null, msgs);
			if (newMinSize != null)
				msgs = ((InternalEObject)newMinSize).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.CONNECTION_CONFIG__MIN_SIZE, null, msgs);
			msgs = basicSetMinSize(newMinSize, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.CONNECTION_CONFIG__MIN_SIZE, newMinSize, newMinSize));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getMaxSize() {
		return maxSize;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMaxSize(OverrideConfig newMaxSize, NotificationChain msgs) {
		OverrideConfig oldMaxSize = maxSize;
		maxSize = newMaxSize;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.CONNECTION_CONFIG__MAX_SIZE, oldMaxSize, newMaxSize);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaxSize(OverrideConfig newMaxSize) {
		if (newMaxSize != maxSize) {
			NotificationChain msgs = null;
			if (maxSize != null)
				msgs = ((InternalEObject)maxSize).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.CONNECTION_CONFIG__MAX_SIZE, null, msgs);
			if (newMaxSize != null)
				msgs = ((InternalEObject)newMaxSize).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.CONNECTION_CONFIG__MAX_SIZE, null, msgs);
			msgs = basicSetMaxSize(newMaxSize, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.CONNECTION_CONFIG__MAX_SIZE, newMaxSize, newMaxSize));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUri(String newUri) {
		String oldUri = uri;
		uri = newUri;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.CONNECTION_CONFIG__URI, oldUri, uri));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.CONNECTION_CONFIG__INITIAL_SIZE:
				return basicSetInitialSize(null, msgs);
			case CddPackage.CONNECTION_CONFIG__MIN_SIZE:
				return basicSetMinSize(null, msgs);
			case CddPackage.CONNECTION_CONFIG__MAX_SIZE:
				return basicSetMaxSize(null, msgs);
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
			case CddPackage.CONNECTION_CONFIG__INITIAL_SIZE:
				return getInitialSize();
			case CddPackage.CONNECTION_CONFIG__MIN_SIZE:
				return getMinSize();
			case CddPackage.CONNECTION_CONFIG__MAX_SIZE:
				return getMaxSize();
			case CddPackage.CONNECTION_CONFIG__URI:
				return getUri();
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
			case CddPackage.CONNECTION_CONFIG__INITIAL_SIZE:
				setInitialSize((OverrideConfig)newValue);
				return;
			case CddPackage.CONNECTION_CONFIG__MIN_SIZE:
				setMinSize((OverrideConfig)newValue);
				return;
			case CddPackage.CONNECTION_CONFIG__MAX_SIZE:
				setMaxSize((OverrideConfig)newValue);
				return;
			case CddPackage.CONNECTION_CONFIG__URI:
				setUri((String)newValue);
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
			case CddPackage.CONNECTION_CONFIG__INITIAL_SIZE:
				setInitialSize((OverrideConfig)null);
				return;
			case CddPackage.CONNECTION_CONFIG__MIN_SIZE:
				setMinSize((OverrideConfig)null);
				return;
			case CddPackage.CONNECTION_CONFIG__MAX_SIZE:
				setMaxSize((OverrideConfig)null);
				return;
			case CddPackage.CONNECTION_CONFIG__URI:
				setUri(URI_EDEFAULT);
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
			case CddPackage.CONNECTION_CONFIG__INITIAL_SIZE:
				return initialSize != null;
			case CddPackage.CONNECTION_CONFIG__MIN_SIZE:
				return minSize != null;
			case CddPackage.CONNECTION_CONFIG__MAX_SIZE:
				return maxSize != null;
			case CddPackage.CONNECTION_CONFIG__URI:
				return URI_EDEFAULT == null ? uri != null : !URI_EDEFAULT.equals(uri);
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
		result.append(" (uri: ");
		result.append(uri);
		result.append(')');
		return result.toString();
	}

} //ConnectionConfigImpl
