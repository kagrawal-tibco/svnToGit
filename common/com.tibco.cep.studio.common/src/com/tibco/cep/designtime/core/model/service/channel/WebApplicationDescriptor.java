/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.service.channel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Web Application Descriptor</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.WebApplicationDescriptor#getContextURI <em>Context URI</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.WebApplicationDescriptor#getWebAppSourcePath <em>Web App Source Path</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getWebApplicationDescriptor()
 * @model
 * @generated
 */
public interface WebApplicationDescriptor extends EObject {
	/**
	 * Returns the value of the '<em><b>Context URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Context URI</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Context URI</em>' attribute.
	 * @see #setContextURI(String)
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getWebApplicationDescriptor_ContextURI()
	 * @model
	 * @generated
	 */
	String getContextURI();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.WebApplicationDescriptor#getContextURI <em>Context URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Context URI</em>' attribute.
	 * @see #getContextURI()
	 * @generated
	 */
	void setContextURI(String value);

	/**
	 * Returns the value of the '<em><b>Web App Source Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Web App Source Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Web App Source Path</em>' attribute.
	 * @see #setWebAppSourcePath(String)
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getWebApplicationDescriptor_WebAppSourcePath()
	 * @model
	 * @generated
	 */
	String getWebAppSourcePath();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.WebApplicationDescriptor#getWebAppSourcePath <em>Web App Source Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Web App Source Path</em>' attribute.
	 * @see #getWebAppSourcePath()
	 * @generated
	 */
	void setWebAppSourcePath(String value);

} // WebApplicationDescriptor
