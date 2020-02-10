/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ciphers Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.CiphersConfig#getCipher <em>Cipher</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getCiphersConfig()
 * @model extendedMetaData="name='ciphers-type' kind='elementOnly'"
 * @generated
 */
public interface CiphersConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Cipher</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.util.config.cdd.OverrideConfig}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cipher</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cipher</em>' containment reference list.
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCiphersConfig_Cipher()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='cipher' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<OverrideConfig> getCipher();

} // CiphersConfig
