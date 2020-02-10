/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model.search;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Non Entity Match</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.search.NonEntityMatch#getProjectName <em>Project Name</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.search.NonEntityMatch#getFilePath <em>File Path</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.search.NonEntityMatch#getMatch <em>Match</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.index.model.search.SearchPackage#getNonEntityMatch()
 * @model
 * @generated
 */
public interface NonEntityMatch extends EObject {
	/**
	 * Returns the value of the '<em><b>Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Project Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Project Name</em>' attribute.
	 * @see #setProjectName(String)
	 * @see com.tibco.cep.studio.core.index.model.search.SearchPackage#getNonEntityMatch_ProjectName()
	 * @model transient="true"
	 * @generated
	 */
	String getProjectName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.search.NonEntityMatch#getProjectName <em>Project Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Project Name</em>' attribute.
	 * @see #getProjectName()
	 * @generated
	 */
	void setProjectName(String value);

	/**
	 * Returns the value of the '<em><b>File Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>File Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>File Path</em>' attribute.
	 * @see #setFilePath(String)
	 * @see com.tibco.cep.studio.core.index.model.search.SearchPackage#getNonEntityMatch_FilePath()
	 * @model transient="true"
	 * @generated
	 */
	String getFilePath();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.search.NonEntityMatch#getFilePath <em>File Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>File Path</em>' attribute.
	 * @see #getFilePath()
	 * @generated
	 */
	void setFilePath(String value);

	/**
	 * Returns the value of the '<em><b>Match</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Match</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Match</em>' attribute.
	 * @see #setMatch(Object)
	 * @see com.tibco.cep.studio.core.index.model.search.SearchPackage#getNonEntityMatch_Match()
	 * @model transient="true"
	 * @generated
	 */
	Object getMatch();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.search.NonEntityMatch#getMatch <em>Match</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Match</em>' attribute.
	 * @see #getMatch()
	 * @generated
	 */
	void setMatch(Object value);

} // NonEntityMatch
