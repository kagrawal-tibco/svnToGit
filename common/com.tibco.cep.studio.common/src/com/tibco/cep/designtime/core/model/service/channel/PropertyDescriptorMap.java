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
 * A representation of the model object '<em><b>Property Descriptor Map</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptorMap#getDescriptors <em>Descriptors</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getPropertyDescriptorMap()
 * @model
 * @generated
 */
public interface PropertyDescriptorMap extends EObject {
	/**
	 * Returns the value of the '<em><b>Descriptors</b></em>' reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptorMapEntry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Descriptors</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Descriptors</em>' reference list.
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getPropertyDescriptorMap_Descriptors()
	 * @model
	 * @generated
	 */
	EList<PropertyDescriptorMapEntry> getDescriptors();

} // PropertyDescriptorMap
