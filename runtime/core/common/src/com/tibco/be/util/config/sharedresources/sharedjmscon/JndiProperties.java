/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.sharedjmscon;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Jndi Properties</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.JndiProperties#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.JndiProperties#getRow <em>Row</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.SharedjmsconPackage#getJndiProperties()
 * @model extendedMetaData="name='JNDIProperties-type' kind='elementOnly'"
 * @generated
 */
public interface JndiProperties extends EObject {
	/**
	 * Returns the value of the '<em><b>Group</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Group</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Group</em>' attribute list.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.SharedjmsconPackage#getJndiProperties_Group()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='group' name='group:0'"
	 * @generated
	 */
	FeatureMap getGroup();

	/**
	 * Returns the value of the '<em><b>Row</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.util.config.sharedresources.sharedjmscon.Row}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Row</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Row</em>' containment reference list.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.SharedjmsconPackage#getJndiProperties_Row()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='row' namespace='##targetNamespace' group='group:0'"
	 * @generated
	 */
	EList<Row> getRow();

} // JndiProperties
