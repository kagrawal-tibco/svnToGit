/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.DestinationConfig;
import com.tibco.be.util.config.cdd.DestinationsConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Destinations Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.DestinationsConfigImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.DestinationsConfigImpl#getRef <em>Ref</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.DestinationsConfigImpl#getDestination <em>Destination</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DestinationsConfigImpl extends ArtifactConfigImpl implements DestinationsConfig {
	/**
	 * The cached value of the '{@link #getGroup() <em>Group</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGroup()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap group;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DestinationsConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getDestinationsConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getGroup() {
		if (group == null) {
			group = new BasicFeatureMap(this, CddPackage.DESTINATIONS_CONFIG__GROUP);
		}
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DestinationsConfig> getRef() {
		return getGroup().list(CddPackage.eINSTANCE.getDestinationsConfig_Ref());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DestinationConfig> getDestination() {
		return getGroup().list(CddPackage.eINSTANCE.getDestinationsConfig_Destination());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<DestinationConfig> getAllDestinations() {
		final EList<DestinationConfig> all = new BasicEList<DestinationConfig>();		
	    
		all.addAll(this.getDestination());
	    
		final EList<DestinationsConfig> refs = this.getRef(); 
	    if (null != refs) {
	    	for (final DestinationsConfig ref : refs) {
	    		all.addAll(ref.getAllDestinations());
	    	}
	    }
	    
	    return all;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.DESTINATIONS_CONFIG__GROUP:
				return ((InternalEList<?>)getGroup()).basicRemove(otherEnd, msgs);
			case CddPackage.DESTINATIONS_CONFIG__DESTINATION:
				return ((InternalEList<?>)getDestination()).basicRemove(otherEnd, msgs);
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
			case CddPackage.DESTINATIONS_CONFIG__GROUP:
				if (coreType) return getGroup();
				return ((FeatureMap.Internal)getGroup()).getWrapper();
			case CddPackage.DESTINATIONS_CONFIG__REF:
				return getRef();
			case CddPackage.DESTINATIONS_CONFIG__DESTINATION:
				return getDestination();
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
			case CddPackage.DESTINATIONS_CONFIG__GROUP:
				((FeatureMap.Internal)getGroup()).set(newValue);
				return;
			case CddPackage.DESTINATIONS_CONFIG__REF:
				getRef().clear();
				getRef().addAll((Collection<? extends DestinationsConfig>)newValue);
				return;
			case CddPackage.DESTINATIONS_CONFIG__DESTINATION:
				getDestination().clear();
				getDestination().addAll((Collection<? extends DestinationConfig>)newValue);
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
			case CddPackage.DESTINATIONS_CONFIG__GROUP:
				getGroup().clear();
				return;
			case CddPackage.DESTINATIONS_CONFIG__REF:
				getRef().clear();
				return;
			case CddPackage.DESTINATIONS_CONFIG__DESTINATION:
				getDestination().clear();
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
			case CddPackage.DESTINATIONS_CONFIG__GROUP:
				return group != null && !group.isEmpty();
			case CddPackage.DESTINATIONS_CONFIG__REF:
				return !getRef().isEmpty();
			case CddPackage.DESTINATIONS_CONFIG__DESTINATION:
				return !getDestination().isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (group: ");
		result.append(group);
		result.append(')');
		return result.toString();
	}

} //DestinationsConfigImpl
