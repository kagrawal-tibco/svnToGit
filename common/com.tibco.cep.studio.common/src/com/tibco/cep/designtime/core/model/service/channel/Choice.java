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
 * A representation of the model object '<em><b>Choice</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.Choice#getDisplayedValue <em>Displayed Value</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.Choice#getValue <em>Value</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.Choice#getExtendedConfiguration <em>Extended Configuration</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getChoice()
 * @model
 * @generated
 */
public interface Choice extends EObject {
	/**
	 * Returns the value of the '<em><b>Displayed Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Displayed Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Displayed Value</em>' attribute.
	 * @see #setDisplayedValue(String)
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getChoice_DisplayedValue()
	 * @model
	 * @generated
	 */
	String getDisplayedValue();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.Choice#getDisplayedValue <em>Displayed Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Displayed Value</em>' attribute.
	 * @see #getDisplayedValue()
	 * @generated
	 */
	void setDisplayedValue(String value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getChoice_Value()
	 * @model
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.Choice#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

	/**
	 * Returns the value of the '<em><b>Extended Configuration</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.service.channel.ExtendedConfiguration}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extended Configuration</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Extended Configuration</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getChoice_ExtendedConfiguration()
	 * @model containment="true"
	 * @generated
	 */
	EList<ExtendedConfiguration> getExtendedConfiguration();

} // Choice
