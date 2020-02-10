/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.model.topology;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Processing Unit Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.ProcessingUnitConfig#getId <em>Id</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.ProcessingUnitConfig#getJmxport <em>Jmxport</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.ProcessingUnitConfig#getPuid <em>Puid</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.ProcessingUnitConfig#isUseAsEngineName <em>Use As Engine Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getProcessingUnitConfig()
 * @model extendedMetaData="name='processing-unit-config_._type' kind='empty'"
 * @generated
 */
public interface ProcessingUnitConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getProcessingUnitConfig_Id()
	 * @model id="true" dataType="org.eclipse.emf.ecore.xml.type.ID"
	 *        extendedMetaData="kind='attribute' name='id'"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.ProcessingUnitConfig#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Jmxport</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Jmxport</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Jmxport</em>' attribute.
	 * @see #setJmxport(String)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getProcessingUnitConfig_Jmxport()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='jmxport'"
	 * @generated
	 */
	String getJmxport();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.ProcessingUnitConfig#getJmxport <em>Jmxport</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Jmxport</em>' attribute.
	 * @see #getJmxport()
	 * @generated
	 */
	void setJmxport(String value);

	/**
	 * Returns the value of the '<em><b>Puid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Puid</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Puid</em>' attribute.
	 * @see #setPuid(String)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getProcessingUnitConfig_Puid()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='puid'"
	 * @generated
	 */
	String getPuid();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.ProcessingUnitConfig#getPuid <em>Puid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Puid</em>' attribute.
	 * @see #getPuid()
	 * @generated
	 */
	void setPuid(String value);

	/**
	 * Returns the value of the '<em><b>Use As Engine Name</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Use As Engine Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Use As Engine Name</em>' attribute.
	 * @see #isSetUseAsEngineName()
	 * @see #unsetUseAsEngineName()
	 * @see #setUseAsEngineName(boolean)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getProcessingUnitConfig_UseAsEngineName()
	 * @model default="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='attribute' name='use-as-engine-name'"
	 * @generated
	 */
	boolean isUseAsEngineName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.ProcessingUnitConfig#isUseAsEngineName <em>Use As Engine Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Use As Engine Name</em>' attribute.
	 * @see #isSetUseAsEngineName()
	 * @see #unsetUseAsEngineName()
	 * @see #isUseAsEngineName()
	 * @generated
	 */
	void setUseAsEngineName(boolean value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.studio.core.model.topology.ProcessingUnitConfig#isUseAsEngineName <em>Use As Engine Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetUseAsEngineName()
	 * @see #isUseAsEngineName()
	 * @see #setUseAsEngineName(boolean)
	 * @generated
	 */
	void unsetUseAsEngineName();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.studio.core.model.topology.ProcessingUnitConfig#isUseAsEngineName <em>Use As Engine Name</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Use As Engine Name</em>' attribute is set.
	 * @see #unsetUseAsEngineName()
	 * @see #isUseAsEngineName()
	 * @see #setUseAsEngineName(boolean)
	 * @generated
	 */
	boolean isSetUseAsEngineName();

} // ProcessingUnitConfig
