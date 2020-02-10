/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Component Color Set</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ComponentColorSet#getDisplayName <em>Display Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getComponentColorSet()
 * @model abstract="true"
 *        extendedMetaData="name='ComponentColorSet' kind='elementOnly'"
 * @generated
 */
public interface ComponentColorSet extends BEViewsElement {
	/**
	 * Returns the value of the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Display Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Display Name</em>' attribute.
	 * @see #setDisplayName(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getComponentColorSet_DisplayName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='displayName'"
	 * @generated
	 */
	String getDisplayName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ComponentColorSet#getDisplayName <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Display Name</em>' attribute.
	 * @see #getDisplayName()
	 * @generated
	 */
	void setDisplayName(String value);

} // ComponentColorSet
