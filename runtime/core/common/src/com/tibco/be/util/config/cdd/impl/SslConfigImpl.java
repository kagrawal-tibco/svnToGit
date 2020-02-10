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
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.CiphersConfig;
import com.tibco.be.util.config.cdd.OverrideConfig;
import com.tibco.be.util.config.cdd.ProtocolsConfig;
import com.tibco.be.util.config.cdd.SslConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Ssl Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.SslConfigImpl#getProtocols <em>Protocols</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.SslConfigImpl#getCiphers <em>Ciphers</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.SslConfigImpl#getKeyManagerAlgorithm <em>Key Manager Algorithm</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.SslConfigImpl#getTrustManagerAlgorithm <em>Trust Manager Algorithm</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SslConfigImpl extends EObjectImpl implements SslConfig {
	/**
	 * The cached value of the '{@link #getProtocols() <em>Protocols</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProtocols()
	 * @generated
	 * @ordered
	 */
	protected ProtocolsConfig protocols;

	/**
	 * The cached value of the '{@link #getCiphers() <em>Ciphers</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCiphers()
	 * @generated
	 * @ordered
	 */
	protected CiphersConfig ciphers;

	/**
	 * The cached value of the '{@link #getKeyManagerAlgorithm() <em>Key Manager Algorithm</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKeyManagerAlgorithm()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig keyManagerAlgorithm;

	/**
	 * The cached value of the '{@link #getTrustManagerAlgorithm() <em>Trust Manager Algorithm</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTrustManagerAlgorithm()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig trustManagerAlgorithm;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SslConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getSslConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProtocolsConfig getProtocols() {
		return protocols;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProtocols(ProtocolsConfig newProtocols, NotificationChain msgs) {
		ProtocolsConfig oldProtocols = protocols;
		protocols = newProtocols;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.SSL_CONFIG__PROTOCOLS, oldProtocols, newProtocols);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProtocols(ProtocolsConfig newProtocols) {
		if (newProtocols != protocols) {
			NotificationChain msgs = null;
			if (protocols != null)
				msgs = ((InternalEObject)protocols).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.SSL_CONFIG__PROTOCOLS, null, msgs);
			if (newProtocols != null)
				msgs = ((InternalEObject)newProtocols).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.SSL_CONFIG__PROTOCOLS, null, msgs);
			msgs = basicSetProtocols(newProtocols, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.SSL_CONFIG__PROTOCOLS, newProtocols, newProtocols));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CiphersConfig getCiphers() {
		return ciphers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCiphers(CiphersConfig newCiphers, NotificationChain msgs) {
		CiphersConfig oldCiphers = ciphers;
		ciphers = newCiphers;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.SSL_CONFIG__CIPHERS, oldCiphers, newCiphers);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCiphers(CiphersConfig newCiphers) {
		if (newCiphers != ciphers) {
			NotificationChain msgs = null;
			if (ciphers != null)
				msgs = ((InternalEObject)ciphers).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.SSL_CONFIG__CIPHERS, null, msgs);
			if (newCiphers != null)
				msgs = ((InternalEObject)newCiphers).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.SSL_CONFIG__CIPHERS, null, msgs);
			msgs = basicSetCiphers(newCiphers, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.SSL_CONFIG__CIPHERS, newCiphers, newCiphers));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getKeyManagerAlgorithm() {
		return keyManagerAlgorithm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetKeyManagerAlgorithm(OverrideConfig newKeyManagerAlgorithm, NotificationChain msgs) {
		OverrideConfig oldKeyManagerAlgorithm = keyManagerAlgorithm;
		keyManagerAlgorithm = newKeyManagerAlgorithm;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.SSL_CONFIG__KEY_MANAGER_ALGORITHM, oldKeyManagerAlgorithm, newKeyManagerAlgorithm);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKeyManagerAlgorithm(OverrideConfig newKeyManagerAlgorithm) {
		if (newKeyManagerAlgorithm != keyManagerAlgorithm) {
			NotificationChain msgs = null;
			if (keyManagerAlgorithm != null)
				msgs = ((InternalEObject)keyManagerAlgorithm).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.SSL_CONFIG__KEY_MANAGER_ALGORITHM, null, msgs);
			if (newKeyManagerAlgorithm != null)
				msgs = ((InternalEObject)newKeyManagerAlgorithm).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.SSL_CONFIG__KEY_MANAGER_ALGORITHM, null, msgs);
			msgs = basicSetKeyManagerAlgorithm(newKeyManagerAlgorithm, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.SSL_CONFIG__KEY_MANAGER_ALGORITHM, newKeyManagerAlgorithm, newKeyManagerAlgorithm));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getTrustManagerAlgorithm() {
		return trustManagerAlgorithm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTrustManagerAlgorithm(OverrideConfig newTrustManagerAlgorithm, NotificationChain msgs) {
		OverrideConfig oldTrustManagerAlgorithm = trustManagerAlgorithm;
		trustManagerAlgorithm = newTrustManagerAlgorithm;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.SSL_CONFIG__TRUST_MANAGER_ALGORITHM, oldTrustManagerAlgorithm, newTrustManagerAlgorithm);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTrustManagerAlgorithm(OverrideConfig newTrustManagerAlgorithm) {
		if (newTrustManagerAlgorithm != trustManagerAlgorithm) {
			NotificationChain msgs = null;
			if (trustManagerAlgorithm != null)
				msgs = ((InternalEObject)trustManagerAlgorithm).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.SSL_CONFIG__TRUST_MANAGER_ALGORITHM, null, msgs);
			if (newTrustManagerAlgorithm != null)
				msgs = ((InternalEObject)newTrustManagerAlgorithm).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.SSL_CONFIG__TRUST_MANAGER_ALGORITHM, null, msgs);
			msgs = basicSetTrustManagerAlgorithm(newTrustManagerAlgorithm, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.SSL_CONFIG__TRUST_MANAGER_ALGORITHM, newTrustManagerAlgorithm, newTrustManagerAlgorithm));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.SSL_CONFIG__PROTOCOLS:
				return basicSetProtocols(null, msgs);
			case CddPackage.SSL_CONFIG__CIPHERS:
				return basicSetCiphers(null, msgs);
			case CddPackage.SSL_CONFIG__KEY_MANAGER_ALGORITHM:
				return basicSetKeyManagerAlgorithm(null, msgs);
			case CddPackage.SSL_CONFIG__TRUST_MANAGER_ALGORITHM:
				return basicSetTrustManagerAlgorithm(null, msgs);
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
			case CddPackage.SSL_CONFIG__PROTOCOLS:
				return getProtocols();
			case CddPackage.SSL_CONFIG__CIPHERS:
				return getCiphers();
			case CddPackage.SSL_CONFIG__KEY_MANAGER_ALGORITHM:
				return getKeyManagerAlgorithm();
			case CddPackage.SSL_CONFIG__TRUST_MANAGER_ALGORITHM:
				return getTrustManagerAlgorithm();
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
			case CddPackage.SSL_CONFIG__PROTOCOLS:
				setProtocols((ProtocolsConfig)newValue);
				return;
			case CddPackage.SSL_CONFIG__CIPHERS:
				setCiphers((CiphersConfig)newValue);
				return;
			case CddPackage.SSL_CONFIG__KEY_MANAGER_ALGORITHM:
				setKeyManagerAlgorithm((OverrideConfig)newValue);
				return;
			case CddPackage.SSL_CONFIG__TRUST_MANAGER_ALGORITHM:
				setTrustManagerAlgorithm((OverrideConfig)newValue);
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
			case CddPackage.SSL_CONFIG__PROTOCOLS:
				setProtocols((ProtocolsConfig)null);
				return;
			case CddPackage.SSL_CONFIG__CIPHERS:
				setCiphers((CiphersConfig)null);
				return;
			case CddPackage.SSL_CONFIG__KEY_MANAGER_ALGORITHM:
				setKeyManagerAlgorithm((OverrideConfig)null);
				return;
			case CddPackage.SSL_CONFIG__TRUST_MANAGER_ALGORITHM:
				setTrustManagerAlgorithm((OverrideConfig)null);
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
			case CddPackage.SSL_CONFIG__PROTOCOLS:
				return protocols != null;
			case CddPackage.SSL_CONFIG__CIPHERS:
				return ciphers != null;
			case CddPackage.SSL_CONFIG__KEY_MANAGER_ALGORITHM:
				return keyManagerAlgorithm != null;
			case CddPackage.SSL_CONFIG__TRUST_MANAGER_ALGORITHM:
				return trustManagerAlgorithm != null;
		}
		return super.eIsSet(featureID);
	}

} //SslConfigImpl
