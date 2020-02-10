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
 * A representation of the model object '<em><b>Child Cluster Member Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.ChildClusterMemberConfig#getProperty <em>Property</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.ChildClusterMemberConfig#getPath <em>Path</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.ChildClusterMemberConfig#getTolerance <em>Tolerance</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getChildClusterMemberConfig()
 * @model extendedMetaData="name='child-cluster-member-type' kind='elementOnly'"
 * @generated
 */
public interface ChildClusterMemberConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Property</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.util.config.cdd.PropertyConfig}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property</em>' containment reference list.
	 * @see com.tibco.be.util.config.cdd.CddPackage#getChildClusterMemberConfig_Property()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='property' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<PropertyConfig> getProperty();

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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getChildClusterMemberConfig_Path()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='path'"
	 * @generated
	 */
	String getPath();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.ChildClusterMemberConfig#getPath <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Path</em>' attribute.
	 * @see #getPath()
	 * @generated
	 */
	void setPath(String value);

	/**
	 * Returns the value of the '<em><b>Tolerance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tolerance</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tolerance</em>' attribute.
	 * @see #setTolerance(String)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getChildClusterMemberConfig_Tolerance()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='tolerance'"
	 * @generated
	 */
	String getTolerance();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.ChildClusterMemberConfig#getTolerance <em>Tolerance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tolerance</em>' attribute.
	 * @see #getTolerance()
	 * @generated
	 */
	void setTolerance(String value);

} // ChildClusterMemberConfig
