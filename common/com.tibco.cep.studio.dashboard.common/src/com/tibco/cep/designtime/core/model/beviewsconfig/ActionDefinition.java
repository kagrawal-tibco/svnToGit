/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Action Definition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 				Represents the action which can be associated with a
 * 				visualization
 * 			
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ActionDefinition#getText <em>Text</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ActionDefinition#getType <em>Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ActionDefinition#getCommandURL <em>Command URL</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getActionDefinition()
 * @model extendedMetaData="name='ActionDefinition' kind='elementOnly'"
 * @generated
 */
public interface ActionDefinition extends BEViewsElement {
	/**
	 * Returns the value of the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 								The name which is to be shown in the
	 * 								menu items
	 * 							
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Text</em>' attribute.
	 * @see #setText(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getActionDefinition_Text()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='text'"
	 * @generated
	 */
	String getText();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ActionDefinition#getText <em>Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Text</em>' attribute.
	 * @see #getText()
	 * @generated
	 */
	void setText(String value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.core.model.beviewsconfig.ActionEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ActionEnum
	 * @see #isSetType()
	 * @see #unsetType()
	 * @see #setType(ActionEnum)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getActionDefinition_Type()
	 * @model unsettable="true" required="true"
	 *        extendedMetaData="kind='element' name='type'"
	 * @generated
	 */
	ActionEnum getType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ActionDefinition#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ActionEnum
	 * @see #isSetType()
	 * @see #unsetType()
	 * @see #getType()
	 * @generated
	 */
	void setType(ActionEnum value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ActionDefinition#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetType()
	 * @see #getType()
	 * @see #setType(ActionEnum)
	 * @generated
	 */
	void unsetType();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ActionDefinition#getType <em>Type</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Type</em>' attribute is set.
	 * @see #unsetType()
	 * @see #getType()
	 * @see #setType(ActionEnum)
	 * @generated
	 */
	boolean isSetType();

	/**
	 * Returns the value of the '<em><b>Command URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 								Represents the URL which is to be
	 * 								launched if the user clicks on the menu
	 * 								item
	 * 							
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Command URL</em>' attribute.
	 * @see #setCommandURL(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getActionDefinition_CommandURL()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='commandURL'"
	 * @generated
	 */
	String getCommandURL();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ActionDefinition#getCommandURL <em>Command URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Command URL</em>' attribute.
	 * @see #getCommandURL()
	 * @generated
	 */
	void setCommandURL(String value);

} // ActionDefinition
