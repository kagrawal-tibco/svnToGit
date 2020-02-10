/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionproject.ontology;



/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Resource</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.AbstractResource#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.AbstractResource#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.AbstractResource#getFolder <em>Folder</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getAbstractResource()
 * @model abstract="true"
 * @generated
 */
public interface AbstractResource extends AccessControlCandidate {
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
	 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getAbstractResource_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decisionproject.ontology.AbstractResource#getName <em>Name</em>}' attribute.
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
	 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getAbstractResource_Description()
	 * @model
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decisionproject.ontology.AbstractResource#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

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
	 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getAbstractResource_Folder()
	 * @model
	 * @generated
	 */
	String getFolder();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decisionproject.ontology.AbstractResource#getFolder <em>Folder</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Folder</em>' attribute.
	 * @see #getFolder()
	 * @generated
	 */
	void setFolder(String value);
	
	String getOwnerProjectName();
	
	void setOwnerProjectName(String project);
	
	String getPath();

} // AbstractResource
