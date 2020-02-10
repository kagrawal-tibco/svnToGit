/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;

import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Rule Config Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.RuleConfigConfig#getClusterMember <em>Cluster Member</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getRuleConfigConfig()
 * @model extendedMetaData="name='rule-config-type' kind='elementOnly'"
 * @generated
 */
public interface RuleConfigConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Cluster Member</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.util.config.cdd.ClusterMemberConfig}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cluster Member</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cluster Member</em>' containment reference list.
	 * @see com.tibco.be.util.config.cdd.CddPackage#getRuleConfigConfig_ClusterMember()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='cluster-member' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<ClusterMemberConfig> getClusterMember();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='return new java.util.Properties();'"
	 * @generated
	 */
	Map<Object, Object> toProperties();

} // RuleConfigConfig
