/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Data Source</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.DataSource#getQuery <em>Query</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.DataSource#getSrcElement <em>Src Element</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.DataSource#getParams <em>Params</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getDataSource()
 * @model
 * @generated
 */
public interface DataSource extends BEViewsElement {
	/**
	 * Returns the value of the '<em><b>Query</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Query</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Query</em>' attribute.
	 * @see #setQuery(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getDataSource_Query()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getQuery();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DataSource#getQuery <em>Query</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Query</em>' attribute.
	 * @see #getQuery()
	 * @generated
	 */
	void setQuery(String value);

	/**
	 * Returns the value of the '<em><b>Src Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Src Element</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Src Element</em>' reference.
	 * @see #setSrcElement(EObject)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getDataSource_SrcElement()
	 * @model
	 * @generated
	 */
	EObject getSrcElement();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DataSource#getSrcElement <em>Src Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Src Element</em>' reference.
	 * @see #getSrcElement()
	 * @generated
	 */
	void setSrcElement(EObject value);

	/**
	 * Returns the value of the '<em><b>Params</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.beviewsconfig.QueryParam}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Params</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Params</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getDataSource_Params()
	 * @model containment="true"
	 * @generated
	 */
	EList<QueryParam> getParams();

} // DataSource
