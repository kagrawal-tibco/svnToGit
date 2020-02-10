/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.FeatureMap;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Functions Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.FunctionsConfig#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.FunctionsConfig#getRef <em>Ref</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.FunctionsConfig#getUri <em>Uri</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getFunctionsConfig()
 * @model extendedMetaData="name='functions-type' kind='elementOnly'"
 * @generated
 */
public interface FunctionsConfig extends UrisAndRefsConfig {

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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getFunctionsConfig_Group()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='group' name='group:1'"
	 * @generated
	 */
	FeatureMap getGroup();

	/**
	 * Returns the value of the '<em><b>Ref</b></em>' reference list.
	 * The list contents are of type {@link com.tibco.be.util.config.cdd.FunctionsConfig}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ref</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ref</em>' reference list.
	 * @see com.tibco.be.util.config.cdd.CddPackage#getFunctionsConfig_Ref()
	 * @model resolveProxies="false" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='ref' namespace='##targetNamespace' group='#group:1'"
	 * @generated
	 */
	EList<FunctionsConfig> getRef();

	/**
	 * Returns the value of the '<em><b>Uri</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Uri</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Uri</em>' attribute list.
	 * @see com.tibco.be.util.config.cdd.CddPackage#getFunctionsConfig_Uri()
	 * @model unique="false" dataType="com.tibco.be.util.config.cdd.OntologyUriConfig" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='uri' namespace='##targetNamespace' group='#group:1'"
	 * @generated
	 */
	EList<String> getUri();
} // FunctionsConfig
