/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Security Override Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.SecurityOverrideConfig#isOverride <em>Override</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getSecurityOverrideConfig()
 * @model extendedMetaData="name='security-override-type' kind='mixed'"
 * @generated
 */
public interface SecurityOverrideConfig extends OverrideConfig {
	/**
	 * Returns the value of the '<em><b>Override</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Override</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Override</em>' attribute.
	 * @see #setOverride(boolean)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getSecurityOverrideConfig_Override()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	boolean isOverride();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.SecurityOverrideConfig#isOverride <em>Override</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Override</em>' attribute.
	 * @see #isOverride()
	 * @generated
	 */
	void setOverride(boolean value);

} // SecurityOverrideConfig
