/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.service.channel;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Choice Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.ChoiceConfiguration#getPropertyName <em>Property Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.ChoiceConfiguration#getPropertyParent <em>Property Parent</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.ChoiceConfiguration#getConfigType <em>Config Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.ChoiceConfiguration#getChoices <em>Choices</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.ChoiceConfiguration#getDefaultValue <em>Default Value</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.ChoiceConfiguration#getDisplayName <em>Display Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getChoiceConfiguration()
 * @model
 * @generated
 */
public interface ChoiceConfiguration extends EObject {
	/**
	 * Returns the value of the '<em><b>Property Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property Name</em>' attribute.
	 * @see #setPropertyName(String)
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getChoiceConfiguration_PropertyName()
	 * @model
	 * @generated
	 */
	String getPropertyName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.ChoiceConfiguration#getPropertyName <em>Property Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property Name</em>' attribute.
	 * @see #getPropertyName()
	 * @generated
	 */
	void setPropertyName(String value);

	/**
	 * Returns the value of the '<em><b>Property Parent</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property Parent</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property Parent</em>' attribute.
	 * @see #setPropertyParent(String)
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getChoiceConfiguration_PropertyParent()
	 * @model
	 * @generated
	 */
	String getPropertyParent();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.ChoiceConfiguration#getPropertyParent <em>Property Parent</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property Parent</em>' attribute.
	 * @see #getPropertyParent()
	 * @generated
	 */
	void setPropertyParent(String value);

	/**
	 * Returns the value of the '<em><b>Config Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Config Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Config Type</em>' attribute.
	 * @see #setConfigType(String)
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getChoiceConfiguration_ConfigType()
	 * @model
	 * @generated
	 */
	String getConfigType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.ChoiceConfiguration#getConfigType <em>Config Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Config Type</em>' attribute.
	 * @see #getConfigType()
	 * @generated
	 */
	void setConfigType(String value);

	/**
	 * Returns the value of the '<em><b>Choices</b></em>' reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.service.channel.Choice}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Choices</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Choices</em>' reference list.
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getChoiceConfiguration_Choices()
	 * @model
	 * @generated
	 */
	EList<Choice> getChoices();

	/**
	 * Returns the value of the '<em><b>Default Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Value</em>' attribute.
	 * @see #setDefaultValue(Object)
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getChoiceConfiguration_DefaultValue()
	 * @model
	 * @generated
	 */
	Object getDefaultValue();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.ChoiceConfiguration#getDefaultValue <em>Default Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default Value</em>' attribute.
	 * @see #getDefaultValue()
	 * @generated
	 */
	void setDefaultValue(Object value);

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
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getChoiceConfiguration_DisplayName()
	 * @model
	 * @generated
	 */
	String getDisplayName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.ChoiceConfiguration#getDisplayName <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Display Name</em>' attribute.
	 * @see #getDisplayName()
	 * @generated
	 */
	void setDisplayName(String value);

} // ChoiceConfiguration
