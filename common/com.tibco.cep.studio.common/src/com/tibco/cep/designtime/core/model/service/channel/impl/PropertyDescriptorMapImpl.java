/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.service.channel.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import com.tibco.cep.designtime.core.model.service.channel.ChannelPackage;
import com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptorMap;
import com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptorMapEntry;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Property Descriptor Map</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.PropertyDescriptorMapImpl#getDescriptors <em>Descriptors</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PropertyDescriptorMapImpl extends EObjectImpl implements PropertyDescriptorMap {
	/**
	 * The cached value of the '{@link #getDescriptors() <em>Descriptors</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescriptors()
	 * @generated
	 * @ordered
	 */
	protected EList<PropertyDescriptorMapEntry> descriptors;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PropertyDescriptorMapImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ChannelPackage.Literals.PROPERTY_DESCRIPTOR_MAP;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PropertyDescriptorMapEntry> getDescriptors() {
		if (descriptors == null) {
			descriptors = new EObjectResolvingEList<PropertyDescriptorMapEntry>(PropertyDescriptorMapEntry.class, this, ChannelPackage.PROPERTY_DESCRIPTOR_MAP__DESCRIPTORS);
		}
		return descriptors;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ChannelPackage.PROPERTY_DESCRIPTOR_MAP__DESCRIPTORS:
				return getDescriptors();
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
			case ChannelPackage.PROPERTY_DESCRIPTOR_MAP__DESCRIPTORS:
				getDescriptors().clear();
				getDescriptors().addAll((Collection<? extends PropertyDescriptorMapEntry>)newValue);
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
			case ChannelPackage.PROPERTY_DESCRIPTOR_MAP__DESCRIPTORS:
				getDescriptors().clear();
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
			case ChannelPackage.PROPERTY_DESCRIPTOR_MAP__DESCRIPTORS:
				return descriptors != null && !descriptors.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //PropertyDescriptorMapImpl
