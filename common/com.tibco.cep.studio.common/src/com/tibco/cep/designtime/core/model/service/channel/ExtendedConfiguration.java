/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.service.channel;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.SimpleProperty;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Extended Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.ExtendedConfiguration#getProperties <em>Properties</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getExtendedConfiguration()
 * @model
 * @generated
 */
public interface ExtendedConfiguration extends EObject {
	/**
	 * Returns the value of the '<em><b>Properties</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.SimpleProperty}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Properties</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Properties</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getExtendedConfiguration_Properties()
	 * @model containment="true"
	 * @generated
	 */
	EList<SimpleProperty> getProperties();

} // ExtendedConfiguration
