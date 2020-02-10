/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.service.channel;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Http Channel Driver Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.HttpChannelDriverConfig#getWebApplicationDescriptors <em>Web Application Descriptors</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getHttpChannelDriverConfig()
 * @model
 * @generated
 */
public interface HttpChannelDriverConfig extends DriverConfig {
	/**
	 * Returns the value of the '<em><b>Web Application Descriptors</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.service.channel.WebApplicationDescriptor}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Web Application Descriptors</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Web Application Descriptors</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getHttpChannelDriverConfig_WebApplicationDescriptors()
	 * @model containment="true"
	 * @generated
	 */
	EList<WebApplicationDescriptor> getWebApplicationDescriptors();

} // HttpChannelDriverConfig
