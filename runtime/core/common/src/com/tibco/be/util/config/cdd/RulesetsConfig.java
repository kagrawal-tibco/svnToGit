/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Rulesets Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.RulesetsConfig#getRules <em>Rules</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getRulesetsConfig()
 * @model extendedMetaData="name='rulesets-type' kind='elementOnly'"
 * @generated
 */
public interface RulesetsConfig extends ArtifactConfig {
	/**
	 * Returns the value of the '<em><b>Rules</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.util.config.cdd.RulesConfig}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rules</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rules</em>' containment reference list.
	 * @see com.tibco.be.util.config.cdd.CddPackage#getRulesetsConfig_Rules()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='rules' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<RulesConfig> getRules();

} // RulesetsConfig
