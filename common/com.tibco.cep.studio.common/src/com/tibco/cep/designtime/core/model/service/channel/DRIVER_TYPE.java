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
 * A representation of the model object '<em><b>DRIVER TYPE</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getDRIVER_TYPE()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface DRIVER_TYPE extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	String getName();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	String getSharedResourceExtension();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void setName(String driverTypeName);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void setSharedResourceExtension(String sharedResourceExtension);

} // DRIVER_TYPE
