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
import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Property Group Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.PropertyGroupConfig#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.PropertyGroupConfig#getPropertyGroup <em>Property Group</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.PropertyGroupConfig#getProperty <em>Property</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.PropertyGroupConfig#getComment <em>Comment</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.PropertyGroupConfig#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getPropertyGroupConfig()
 * @model extendedMetaData="name='property-group-type' kind='elementOnly'"
 * @generated
 */
public interface PropertyGroupConfig extends EObject {
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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getPropertyGroupConfig_Group()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='group' name='group:0'"
	 * @generated
	 */
	FeatureMap getGroup();

	/**
	 * Returns the value of the '<em><b>Property Group</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.util.config.cdd.PropertyGroupConfig}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property Group</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property Group</em>' containment reference list.
	 * @see com.tibco.be.util.config.cdd.CddPackage#getPropertyGroupConfig_PropertyGroup()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='property-group' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList<PropertyGroupConfig> getPropertyGroup();

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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getPropertyGroupConfig_Property()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='property' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList<PropertyConfig> getProperty();

	/**
	 * Returns the value of the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Comment</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Comment</em>' attribute.
	 * @see #setComment(Object)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getPropertyGroupConfig_Comment()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='comment'"
	 * @generated
	 */
	Object getComment();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.PropertyGroupConfig#getComment <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Comment</em>' attribute.
	 * @see #getComment()
	 * @generated
	 */
	void setComment(Object value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(Object)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getPropertyGroupConfig_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='name'"
	 * @generated
	 */
	Object getName();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.PropertyGroupConfig#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(Object value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='return new java.util.Properties();'"
	 * @generated
	 */
	Map<Object, Object> toProperties();

} // PropertyGroupConfig
