/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ssl Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.SslConfig#getProtocols <em>Protocols</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.SslConfig#getCiphers <em>Ciphers</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.SslConfig#getKeyManagerAlgorithm <em>Key Manager Algorithm</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.SslConfig#getTrustManagerAlgorithm <em>Trust Manager Algorithm</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getSslConfig()
 * @model extendedMetaData="name='ssl-type' kind='elementOnly'"
 * @generated
 */
public interface SslConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Protocols</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Protocols</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Protocols</em>' containment reference.
	 * @see #setProtocols(ProtocolsConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getSslConfig_Protocols()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='protocols' namespace='##targetNamespace'"
	 * @generated
	 */
	ProtocolsConfig getProtocols();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.SslConfig#getProtocols <em>Protocols</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Protocols</em>' containment reference.
	 * @see #getProtocols()
	 * @generated
	 */
	void setProtocols(ProtocolsConfig value);

	/**
	 * Returns the value of the '<em><b>Ciphers</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ciphers</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ciphers</em>' containment reference.
	 * @see #setCiphers(CiphersConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getSslConfig_Ciphers()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='ciphers' namespace='##targetNamespace'"
	 * @generated
	 */
	CiphersConfig getCiphers();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.SslConfig#getCiphers <em>Ciphers</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ciphers</em>' containment reference.
	 * @see #getCiphers()
	 * @generated
	 */
	void setCiphers(CiphersConfig value);

	/**
	 * Returns the value of the '<em><b>Key Manager Algorithm</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Key Manager Algorithm</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Key Manager Algorithm</em>' containment reference.
	 * @see #setKeyManagerAlgorithm(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getSslConfig_KeyManagerAlgorithm()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='key-manager-algorithm' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getKeyManagerAlgorithm();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.SslConfig#getKeyManagerAlgorithm <em>Key Manager Algorithm</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Key Manager Algorithm</em>' containment reference.
	 * @see #getKeyManagerAlgorithm()
	 * @generated
	 */
	void setKeyManagerAlgorithm(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Trust Manager Algorithm</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Trust Manager Algorithm</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Trust Manager Algorithm</em>' containment reference.
	 * @see #setTrustManagerAlgorithm(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getSslConfig_TrustManagerAlgorithm()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='trust-manager-algorithm' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getTrustManagerAlgorithm();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.SslConfig#getTrustManagerAlgorithm <em>Trust Manager Algorithm</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Trust Manager Algorithm</em>' containment reference.
	 * @see #getTrustManagerAlgorithm()
	 * @generated
	 */
	void setTrustManagerAlgorithm(OverrideConfig value);

} // SslConfig
