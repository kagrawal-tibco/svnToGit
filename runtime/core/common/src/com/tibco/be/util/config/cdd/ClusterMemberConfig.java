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
 * A representation of the model object '<em><b>Cluster Member Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.ClusterMemberConfig#getSetProperty <em>Set Property</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.ClusterMemberConfig#getMemberName <em>Member Name</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.ClusterMemberConfig#getPath <em>Path</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getClusterMemberConfig()
 * @model extendedMetaData="name='cluster-member-type' kind='elementOnly'"
 * @generated
 */
public interface ClusterMemberConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Set Property</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.util.config.cdd.SetPropertyConfig}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Set Property</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Set Property</em>' containment reference list.
	 * @see com.tibco.be.util.config.cdd.CddPackage#getClusterMemberConfig_SetProperty()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='set-property' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<SetPropertyConfig> getSetProperty();

	/**
	 * Returns the value of the '<em><b>Member Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Member Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Member Name</em>' attribute.
	 * @see #setMemberName(String)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getClusterMemberConfig_MemberName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='member-name'"
	 * @generated
	 */
	String getMemberName();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.ClusterMemberConfig#getMemberName <em>Member Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Member Name</em>' attribute.
	 * @see #getMemberName()
	 * @generated
	 */
	void setMemberName(String value);

	/**
	 * Returns the value of the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Path</em>' attribute.
	 * @see #setPath(String)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getClusterMemberConfig_Path()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='path'"
	 * @generated
	 */
	String getPath();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.ClusterMemberConfig#getPath <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Path</em>' attribute.
	 * @see #getPath()
	 * @generated
	 */
	void setPath(String value);

} // ClusterMemberConfig
