/**
 */
package com.tibco.be.util.config.cdd;

import java.util.Map;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Entity Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.EntityConfig#getUri <em>Uri</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.EntityConfig#getFilter <em>Filter</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.EntityConfig#getEnableTableTrimming <em>Enable Table Trimming</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.EntityConfig#getTrimmingField <em>Trimming Field</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.EntityConfig#getTrimmingRule <em>Trimming Rule</em>}</li>
 * </ul>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getEntityConfig()
 * @model extendedMetaData="name='entity-type' kind='elementOnly'"
 * @generated
 */
public interface EntityConfig extends ArtifactConfig {
	/**
	 * Returns the value of the '<em><b>Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Uri</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Uri</em>' attribute.
	 * @see #setUri(String)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getEntityConfig_Uri()
	 * @model dataType="com.tibco.be.util.config.cdd.OntologyUriConfig" required="true"
	 *        extendedMetaData="kind='element' name='uri' namespace='##targetNamespace'"
	 * @generated
	 */
	String getUri();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.EntityConfig#getUri <em>Uri</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Uri</em>' attribute.
	 * @see #getUri()
	 * @generated
	 */
	void setUri(String value);

	/**
	 * Returns the value of the '<em><b>Filter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Filter</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Filter</em>' containment reference.
	 * @see #setFilter(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getEntityConfig_Filter()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='filter' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getFilter();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.EntityConfig#getFilter <em>Filter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Filter</em>' containment reference.
	 * @see #getFilter()
	 * @generated
	 */
	void setFilter(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Enable Table Trimming</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enable Table Trimming</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enable Table Trimming</em>' containment reference.
	 * @see #setEnableTableTrimming(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getEntityConfig_EnableTableTrimming()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='enable-table-trimming' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getEnableTableTrimming();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.EntityConfig#getEnableTableTrimming <em>Enable Table Trimming</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enable Table Trimming</em>' containment reference.
	 * @see #getEnableTableTrimming()
	 * @generated
	 */
	void setEnableTableTrimming(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Trimming Field</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Trimming Field</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Trimming Field</em>' containment reference.
	 * @see #setTrimmingField(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getEntityConfig_TrimmingField()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='trimming-field' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getTrimmingField();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.EntityConfig#getTrimmingField <em>Trimming Field</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Trimming Field</em>' containment reference.
	 * @see #getTrimmingField()
	 * @generated
	 */
	void setTrimmingField(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Trimming Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Trimming Rule</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Trimming Rule</em>' containment reference.
	 * @see #setTrimmingRule(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getEntityConfig_TrimmingRule()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='trimming-rule' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getTrimmingRule();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.EntityConfig#getTrimmingRule <em>Trimming Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Trimming Rule</em>' containment reference.
	 * @see #getTrimmingRule()
	 * @generated
	 */
	void setTrimmingRule(OverrideConfig value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='return new java.util.Properties();'"
	 * @generated
	 */
	Map<Object, Object> toProperties();

} // EntityConfig
