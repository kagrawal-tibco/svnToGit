/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.validation.ModelError;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Entity</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.Entity#getNamespace <em>Namespace</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.Entity#getFolder <em>Folder</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.Entity#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.Entity#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.Entity#getLastModified <em>Last Modified</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.Entity#getGUID <em>GUID</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.Entity#getOntology <em>Ontology</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.Entity#getExtendedProperties <em>Extended Properties</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.Entity#getHiddenProperties <em>Hidden Properties</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.Entity#getTransientProperties <em>Transient Properties</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.Entity#getOwnerProjectName <em>Owner Project Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.ModelPackage#getEntity()
 * @model abstract="true"
 * @generated
 */
public interface Entity extends EObject {
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
	 * @see com.tibco.cep.designtime.core.model.ModelPackage#getEntity_Namespace()
	 * @model required="true"
	 * @generated
	 */
	String getNamespace();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.Entity#getNamespace <em>Namespace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Namespace</em>' attribute.
	 * @see #getNamespace()
	 * @generated
	 */
	void setNamespace(String value);

	/**
	 * Returns the value of the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Folder</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Folder</em>' attribute.
	 * @see #setFolder(String)
	 * @see com.tibco.cep.designtime.core.model.ModelPackage#getEntity_Folder()
	 * @model required="true"
	 * @generated
	 */
	String getFolder();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.Entity#getFolder <em>Folder</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Folder</em>' attribute.
	 * @see #getFolder()
	 * @generated
	 */
	void setFolder(String value);

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
	 * @see com.tibco.cep.designtime.core.model.ModelPackage#getEntity_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.Entity#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see com.tibco.cep.designtime.core.model.ModelPackage#getEntity_Description()
	 * @model required="true"
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.Entity#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Last Modified</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Last Modified</em>' attribute.
	 * @see #setLastModified(String)
	 * @see com.tibco.cep.designtime.core.model.ModelPackage#getEntity_LastModified()
	 * @model required="true"
	 * @generated
	 */
	String getLastModified();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.Entity#getLastModified <em>Last Modified</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Last Modified</em>' attribute.
	 * @see #getLastModified()
	 * @generated
	 */
	void setLastModified(String value);

	/**
	 * Returns the value of the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>GUID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>GUID</em>' attribute.
	 * @see #setGUID(String)
	 * @see com.tibco.cep.designtime.core.model.ModelPackage#getEntity_GUID()
	 * @model required="true"
	 * @generated
	 */
	String getGUID();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.Entity#getGUID <em>GUID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>GUID</em>' attribute.
	 * @see #getGUID()
	 * @generated
	 */
	void setGUID(String value);

	/**
	 * Returns the value of the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ontology</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ontology</em>' reference.
	 * @see #setOntology(EObject)
	 * @see com.tibco.cep.designtime.core.model.ModelPackage#getEntity_Ontology()
	 * @model
	 * @generated
	 */
	EObject getOntology();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.Entity#getOntology <em>Ontology</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ontology</em>' reference.
	 * @see #getOntology()
	 * @generated
	 */
	void setOntology(EObject value);

	/**
	 * Returns the value of the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extended Properties</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Extended Properties</em>' containment reference.
	 * @see #setExtendedProperties(PropertyMap)
	 * @see com.tibco.cep.designtime.core.model.ModelPackage#getEntity_ExtendedProperties()
	 * @model containment="true"
	 * @generated
	 */
	PropertyMap getExtendedProperties();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.Entity#getExtendedProperties <em>Extended Properties</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Extended Properties</em>' containment reference.
	 * @see #getExtendedProperties()
	 * @generated
	 */
	void setExtendedProperties(PropertyMap value);

	/**
	 * Returns the value of the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hidden Properties</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hidden Properties</em>' containment reference.
	 * @see #setHiddenProperties(PropertyMap)
	 * @see com.tibco.cep.designtime.core.model.ModelPackage#getEntity_HiddenProperties()
	 * @model containment="true"
	 * @generated
	 */
	PropertyMap getHiddenProperties();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.Entity#getHiddenProperties <em>Hidden Properties</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Hidden Properties</em>' containment reference.
	 * @see #getHiddenProperties()
	 * @generated
	 */
	void setHiddenProperties(PropertyMap value);

	/**
	 * Returns the value of the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Transient Properties</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transient Properties</em>' containment reference.
	 * @see #setTransientProperties(PropertyMap)
	 * @see com.tibco.cep.designtime.core.model.ModelPackage#getEntity_TransientProperties()
	 * @model containment="true" transient="true"
	 * @generated
	 */
	PropertyMap getTransientProperties();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.Entity#getTransientProperties <em>Transient Properties</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Transient Properties</em>' containment reference.
	 * @see #getTransientProperties()
	 * @generated
	 */
	void setTransientProperties(PropertyMap value);

	/**
	 * Returns the value of the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owner Project Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owner Project Name</em>' attribute.
	 * @see #setOwnerProjectName(String)
	 * @see com.tibco.cep.designtime.core.model.ModelPackage#getEntity_OwnerProjectName()
	 * @model
	 * @generated
	 */
	String getOwnerProjectName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.Entity#getOwnerProjectName <em>Owner Project Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owner Project Name</em>' attribute.
	 * @see #getOwnerProjectName()
	 * @generated
	 */
	void setOwnerProjectName(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 * @generated
	 */
	String getFullPath();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	EList<ModelError> getModelErrors();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 * @generated
	 */
	boolean isValid();

} // Entity
