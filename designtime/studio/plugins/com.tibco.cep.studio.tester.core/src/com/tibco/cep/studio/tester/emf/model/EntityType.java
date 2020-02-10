/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.tester.emf.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Entity Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 				The base entity type representing the BE entity.
 * 			
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.EntityType#getProperty <em>Property</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.EntityType#getModifiedProperty <em>Modified Property</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.EntityType#getExtId <em>Ext Id</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.EntityType#getId <em>Id</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.EntityType#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.EntityType#getNamespace <em>Namespace</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getEntityType()
 * @model extendedMetaData="name='EntityType' kind='elementOnly'"
 * @generated
 */
public interface EntityType extends EObject {
	/**
	 * Returns the value of the '<em><b>Property</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.studio.tester.emf.model.PropertyType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						The property of the BE entity with its name and
	 * 						value.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Property</em>' containment reference list.
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getEntityType_Property()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='property' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<PropertyType> getProperty();

	/**
	 * Returns the value of the '<em><b>Modified Property</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.studio.tester.emf.model.PropertyModificationType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						The modified property of the BE entity with its name and
	 * 						old and new values
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Modified Property</em>' containment reference list.
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getEntityType_ModifiedProperty()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='modifiedProperty' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<PropertyModificationType> getModifiedProperty();

	/**
	 * Returns the value of the '<em><b>Ext Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ext Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ext Id</em>' attribute.
	 * @see #setExtId(String)
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getEntityType_ExtId()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='extId'"
	 * @generated
	 */
	String getExtId();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.tester.emf.model.EntityType#getExtId <em>Ext Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ext Id</em>' attribute.
	 * @see #getExtId()
	 * @generated
	 */
	void setExtId(String value);

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #isSetId()
	 * @see #unsetId()
	 * @see #setId(long)
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getEntityType_Id()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long"
	 *        extendedMetaData="kind='attribute' name='Id'"
	 * @generated
	 */
	long getId();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.tester.emf.model.EntityType#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #isSetId()
	 * @see #unsetId()
	 * @see #getId()
	 * @generated
	 */
	void setId(long value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.studio.tester.emf.model.EntityType#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetId()
	 * @see #getId()
	 * @see #setId(long)
	 * @generated
	 */
	void unsetId();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.studio.tester.emf.model.EntityType#getId <em>Id</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Id</em>' attribute is set.
	 * @see #unsetId()
	 * @see #getId()
	 * @see #setId(long)
	 * @generated
	 */
	boolean isSetId();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getEntityType_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='name'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.tester.emf.model.EntityType#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Namespace</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Namespace</em>' attribute.
	 * @see #setNamespace(String)
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getEntityType_Namespace()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='namespace'"
	 * @generated
	 */
	String getNamespace();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.tester.emf.model.EntityType#getNamespace <em>Namespace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Namespace</em>' attribute.
	 * @see #getNamespace()
	 * @generated
	 */
	void setNamespace(String value);

} // EntityType
