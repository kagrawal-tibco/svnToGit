/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.service.channel;

import com.tibco.cep.designtime.core.model.Entity;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Channel</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.Channel#getDriver <em>Driver</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.Channel#getDriverManager <em>Driver Manager</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getChannel()
 * @model
 * @generated
 */
public interface Channel extends Entity {
	/**
	 * Returns the value of the '<em><b>Driver</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Driver</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Driver</em>' containment reference.
	 * @see #setDriver(DriverConfig)
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getChannel_Driver()
	 * @model containment="true"
	 * @generated
	 */
	DriverConfig getDriver();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.Channel#getDriver <em>Driver</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Driver</em>' containment reference.
	 * @see #getDriver()
	 * @generated
	 */
	void setDriver(DriverConfig value);

	/**
	 * Returns the value of the '<em><b>Driver Manager</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Driver Manager</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Driver Manager</em>' reference.
	 * @see #setDriverManager(DriverManager)
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getChannel_DriverManager()
	 * @model transient="true"
	 * @generated
	 */
	DriverManager getDriverManager();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.Channel#getDriverManager <em>Driver Manager</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Driver Manager</em>' reference.
	 * @see #getDriverManager()
	 * @generated
	 */
	void setDriverManager(DriverManager value);

} // Channel
