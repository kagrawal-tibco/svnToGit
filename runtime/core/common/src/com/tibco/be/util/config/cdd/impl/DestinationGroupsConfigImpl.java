/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd.impl;

import java.util.Collection;
import java.util.Map;
import java.util.Properties;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.DestinationGroupsConfig;
import com.tibco.be.util.config.cdd.DestinationsConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Destination Groups Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.DestinationGroupsConfigImpl#getDestinations <em>Destinations</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DestinationGroupsConfigImpl extends EObjectImpl implements DestinationGroupsConfig {
	/**
	 * The cached value of the '{@link #getDestinations() <em>Destinations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDestinations()
	 * @generated
	 * @ordered
	 */
	protected EList<DestinationsConfig> destinations;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DestinationGroupsConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getDestinationGroupsConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DestinationsConfig> getDestinations() {
		if (destinations == null) {
			destinations = new EObjectContainmentEList<DestinationsConfig>(DestinationsConfig.class, this, CddPackage.DESTINATION_GROUPS_CONFIG__DESTINATIONS);
		}
		return destinations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Map<Object, Object> toProperties() {
		final Properties p = new Properties();
		
		for (final DestinationsConfig destinations : this.getDestinations()) {
			p.putAll(destinations.toProperties());
		}
		
		return p;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.DESTINATION_GROUPS_CONFIG__DESTINATIONS:
				return ((InternalEList<?>)getDestinations()).basicRemove(otherEnd, msgs);
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
			case CddPackage.DESTINATION_GROUPS_CONFIG__DESTINATIONS:
				return getDestinations();
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
			case CddPackage.DESTINATION_GROUPS_CONFIG__DESTINATIONS:
				getDestinations().clear();
				getDestinations().addAll((Collection<? extends DestinationsConfig>)newValue);
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
			case CddPackage.DESTINATION_GROUPS_CONFIG__DESTINATIONS:
				getDestinations().clear();
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
			case CddPackage.DESTINATION_GROUPS_CONFIG__DESTINATIONS:
				return destinations != null && !destinations.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //DestinationGroupsConfigImpl
