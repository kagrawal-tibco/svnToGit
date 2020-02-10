/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.service.channel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.designtime.core.model.service.channel.ChannelPackage;
import com.tibco.cep.designtime.core.model.service.channel.HttpChannelDriverConfig;
import com.tibco.cep.designtime.core.model.service.channel.WebApplicationDescriptor;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Http Channel Driver Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.HttpChannelDriverConfigImpl#getWebApplicationDescriptors <em>Web Application Descriptors</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class HttpChannelDriverConfigImpl extends DriverConfigImpl implements HttpChannelDriverConfig {
	/**
	 * The cached value of the '{@link #getWebApplicationDescriptors() <em>Web Application Descriptors</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWebApplicationDescriptors()
	 * @generated
	 * @ordered
	 */
	protected EList<WebApplicationDescriptor> webApplicationDescriptors;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected HttpChannelDriverConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ChannelPackage.Literals.HTTP_CHANNEL_DRIVER_CONFIG;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<WebApplicationDescriptor> getWebApplicationDescriptors() {
		if (webApplicationDescriptors == null) {
			webApplicationDescriptors = new EObjectContainmentEList<WebApplicationDescriptor>(WebApplicationDescriptor.class, this, ChannelPackage.HTTP_CHANNEL_DRIVER_CONFIG__WEB_APPLICATION_DESCRIPTORS);
		}
		return webApplicationDescriptors;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ChannelPackage.HTTP_CHANNEL_DRIVER_CONFIG__WEB_APPLICATION_DESCRIPTORS:
				return ((InternalEList<?>)getWebApplicationDescriptors()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ChannelPackage.HTTP_CHANNEL_DRIVER_CONFIG__WEB_APPLICATION_DESCRIPTORS:
				return getWebApplicationDescriptors();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ChannelPackage.HTTP_CHANNEL_DRIVER_CONFIG__WEB_APPLICATION_DESCRIPTORS:
				getWebApplicationDescriptors().clear();
				getWebApplicationDescriptors().addAll((Collection<? extends WebApplicationDescriptor>)newValue);
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
			case ChannelPackage.HTTP_CHANNEL_DRIVER_CONFIG__WEB_APPLICATION_DESCRIPTORS:
				getWebApplicationDescriptors().clear();
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
			case ChannelPackage.HTTP_CHANNEL_DRIVER_CONFIG__WEB_APPLICATION_DESCRIPTORS:
				return webApplicationDescriptors != null && !webApplicationDescriptors.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //HttpChannelDriverConfigImpl
