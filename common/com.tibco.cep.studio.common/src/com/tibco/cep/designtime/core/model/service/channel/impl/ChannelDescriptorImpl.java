/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.service.channel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.designtime.core.model.service.channel.ChannelDescriptor;
import com.tibco.cep.designtime.core.model.service.channel.ChannelPackage;
import com.tibco.cep.designtime.core.model.service.channel.DestinationDescriptor;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Descriptor</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.ChannelDescriptorImpl#getDestinationDescriptor <em>Destination Descriptor</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ChannelDescriptorImpl extends PropertyDescriptorMapImpl implements ChannelDescriptor {
	/**
	 * The cached value of the '{@link #getDestinationDescriptor() <em>Destination Descriptor</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDestinationDescriptor()
	 * @generated
	 * @ordered
	 */
	protected DestinationDescriptor destinationDescriptor;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ChannelDescriptorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ChannelPackage.Literals.CHANNEL_DESCRIPTOR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DestinationDescriptor getDestinationDescriptor() {
		if (destinationDescriptor != null && destinationDescriptor.eIsProxy()) {
			InternalEObject oldDestinationDescriptor = (InternalEObject)destinationDescriptor;
			destinationDescriptor = (DestinationDescriptor)eResolveProxy(oldDestinationDescriptor);
			if (destinationDescriptor != oldDestinationDescriptor) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ChannelPackage.CHANNEL_DESCRIPTOR__DESTINATION_DESCRIPTOR, oldDestinationDescriptor, destinationDescriptor));
			}
		}
		return destinationDescriptor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DestinationDescriptor basicGetDestinationDescriptor() {
		return destinationDescriptor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDestinationDescriptor(DestinationDescriptor newDestinationDescriptor) {
		DestinationDescriptor oldDestinationDescriptor = destinationDescriptor;
		destinationDescriptor = newDestinationDescriptor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChannelPackage.CHANNEL_DESCRIPTOR__DESTINATION_DESCRIPTOR, oldDestinationDescriptor, destinationDescriptor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ChannelPackage.CHANNEL_DESCRIPTOR__DESTINATION_DESCRIPTOR:
				if (resolve) return getDestinationDescriptor();
				return basicGetDestinationDescriptor();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ChannelPackage.CHANNEL_DESCRIPTOR__DESTINATION_DESCRIPTOR:
				setDestinationDescriptor((DestinationDescriptor)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ChannelPackage.CHANNEL_DESCRIPTOR__DESTINATION_DESCRIPTOR:
				setDestinationDescriptor((DestinationDescriptor)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ChannelPackage.CHANNEL_DESCRIPTOR__DESTINATION_DESCRIPTOR:
				return destinationDescriptor != null;
		}
		return super.eIsSet(featureID);
	}

} //ChannelDescriptorImpl
