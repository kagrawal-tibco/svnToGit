/**
 */
package com.tibco.be.util.config.cdd;

import java.util.Map;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Entity Set Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.EntitySetConfig#getEntity <em>Entity</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.EntitySetConfig#getGenerateLVFiles <em>Generate LV Files</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.EntitySetConfig#getOutputPath <em>Output Path</em>}</li>
 * </ul>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getEntitySetConfig()
 * @model extendedMetaData="name='entity-set-type' kind='elementOnly'"
 * @generated
 */
public interface EntitySetConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Entity</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.util.config.cdd.EntityConfig}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entity</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entity</em>' containment reference list.
	 * @see com.tibco.be.util.config.cdd.CddPackage#getEntitySetConfig_Entity()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='entity' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<EntityConfig> getEntity();

	/**
	 * Returns the value of the '<em><b>Generate LV Files</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Generate LV Files</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Generate LV Files</em>' containment reference.
	 * @see #setGenerateLVFiles(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getEntitySetConfig_GenerateLVFiles()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='generate-lv-files' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getGenerateLVFiles();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.EntitySetConfig#getGenerateLVFiles <em>Generate LV Files</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Generate LV Files</em>' containment reference.
	 * @see #getGenerateLVFiles()
	 * @generated
	 */
	void setGenerateLVFiles(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Output Path</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Output Path</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Output Path</em>' containment reference.
	 * @see #setOutputPath(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getEntitySetConfig_OutputPath()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='output-path' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getOutputPath();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.EntitySetConfig#getOutputPath <em>Output Path</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Output Path</em>' containment reference.
	 * @see #getOutputPath()
	 * @generated
	 */
	void setOutputPath(OverrideConfig value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='return new java.util.Properties();'"
	 * @generated
	 */
	Map<Object, Object> toProperties();

} // EntitySetConfig
