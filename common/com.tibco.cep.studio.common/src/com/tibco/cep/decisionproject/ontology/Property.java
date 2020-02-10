/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionproject.ontology;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Property</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.Property#getDataType <em>Data Type</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.Property#getHistorySize <em>History Size</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.Property#getHistoryPolicy <em>History Policy</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.Property#isMultiple <em>Multiple</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.Property#getDefaultValue <em>Default Value</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.Property#getPropertyResourceType <em>Property Resource Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getProperty()
 * @model
 * @generated
 */
public interface Property extends AbstractResource {
	/**
	 * Returns the value of the '<em><b>Data Type</b></em>' attribute.
	 * The default value is <code>"-1"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Data Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data Type</em>' attribute.
	 * @see #setDataType(int)
	 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getProperty_DataType()
	 * @model default="-1"
	 * @generated
	 */
	int getDataType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decisionproject.ontology.Property#getDataType <em>Data Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Data Type</em>' attribute.
	 * @see #getDataType()
	 * @generated
	 */
	void setDataType(int value);

	/**
	 * Returns the value of the '<em><b>History Size</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>History Size</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>History Size</em>' attribute.
	 * @see #setHistorySize(int)
	 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getProperty_HistorySize()
	 * @model default="0" volatile="true"
	 * @generated
	 */
	int getHistorySize();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decisionproject.ontology.Property#getHistorySize <em>History Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>History Size</em>' attribute.
	 * @see #getHistorySize()
	 * @generated
	 */
	void setHistorySize(int value);

	/**
	 * Returns the value of the '<em><b>History Policy</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>History Policy</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>History Policy</em>' attribute.
	 * @see #setHistoryPolicy(int)
	 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getProperty_HistoryPolicy()
	 * @model default="0"
	 * @generated
	 */
	int getHistoryPolicy();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decisionproject.ontology.Property#getHistoryPolicy <em>History Policy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>History Policy</em>' attribute.
	 * @see #getHistoryPolicy()
	 * @generated
	 */
	void setHistoryPolicy(int value);

	/**
	 * Returns the value of the '<em><b>Multiple</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Multiple</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Multiple</em>' attribute.
	 * @see #setMultiple(boolean)
	 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getProperty_Multiple()
	 * @model
	 * @generated
	 */
	boolean isMultiple();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decisionproject.ontology.Property#isMultiple <em>Multiple</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Multiple</em>' attribute.
	 * @see #isMultiple()
	 * @generated
	 */
	void setMultiple(boolean value);

	/**
	 * Returns the value of the '<em><b>Default Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Value</em>' attribute.
	 * @see #setDefaultValue(String)
	 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getProperty_DefaultValue()
	 * @model
	 * @generated
	 */
	String getDefaultValue();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decisionproject.ontology.Property#getDefaultValue <em>Default Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default Value</em>' attribute.
	 * @see #getDefaultValue()
	 * @generated
	 */
	void setDefaultValue(String value);

	/**
	 * Returns the value of the '<em><b>Property Resource Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property Resource Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property Resource Type</em>' attribute.
	 * @see #setPropertyResourceType(String)
	 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getProperty_PropertyResourceType()
	 * @model
	 * @generated
	 */
	String getPropertyResourceType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decisionproject.ontology.Property#getPropertyResourceType <em>Property Resource Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property Resource Type</em>' attribute.
	 * @see #getPropertyResourceType()
	 * @generated
	 */
	void setPropertyResourceType(String value);

} // Property
