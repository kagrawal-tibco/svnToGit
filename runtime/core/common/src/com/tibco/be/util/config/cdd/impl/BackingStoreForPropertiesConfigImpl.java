/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd.impl;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.BackingStoreForPropertiesConfig;
import com.tibco.be.util.config.cdd.BackingStoreForPropertyConfig;
import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.DomainObjectConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Backing Store For Properties Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.BackingStoreForPropertiesConfigImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.BackingStoreForPropertiesConfigImpl#getProperty <em>Property</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BackingStoreForPropertiesConfigImpl extends ArtifactConfigImpl implements BackingStoreForPropertiesConfig {
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
	protected BackingStoreForPropertiesConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getBackingStoreForPropertiesConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getGroup() {
		if (group == null) {
			group = new BasicFeatureMap(this, CddPackage.BACKING_STORE_FOR_PROPERTIES_CONFIG__GROUP);
		}
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<BackingStoreForPropertyConfig> getProperty() {
		return getGroup().list(CddPackage.eINSTANCE.getBackingStoreForPropertiesConfig_Property());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.BACKING_STORE_FOR_PROPERTIES_CONFIG__GROUP:
				return ((InternalEList<?>)getGroup()).basicRemove(otherEnd, msgs);
			case CddPackage.BACKING_STORE_FOR_PROPERTIES_CONFIG__PROPERTY:
				return ((InternalEList<?>)getProperty()).basicRemove(otherEnd, msgs);
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
			case CddPackage.BACKING_STORE_FOR_PROPERTIES_CONFIG__GROUP:
				if (coreType) return getGroup();
				return ((FeatureMap.Internal)getGroup()).getWrapper();
			case CddPackage.BACKING_STORE_FOR_PROPERTIES_CONFIG__PROPERTY:
				return getProperty();
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
			case CddPackage.BACKING_STORE_FOR_PROPERTIES_CONFIG__GROUP:
				((FeatureMap.Internal)getGroup()).set(newValue);
				return;
			case CddPackage.BACKING_STORE_FOR_PROPERTIES_CONFIG__PROPERTY:
				getProperty().clear();
				getProperty().addAll((Collection<? extends BackingStoreForPropertyConfig>)newValue);
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
			case CddPackage.BACKING_STORE_FOR_PROPERTIES_CONFIG__GROUP:
				getGroup().clear();
				return;
			case CddPackage.BACKING_STORE_FOR_PROPERTIES_CONFIG__PROPERTY:
				getProperty().clear();
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
			case CddPackage.BACKING_STORE_FOR_PROPERTIES_CONFIG__GROUP:
				return group != null && !group.isEmpty();
			case CddPackage.BACKING_STORE_FOR_PROPERTIES_CONFIG__PROPERTY:
				return !getProperty().isEmpty();
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


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Map<Object, Object> toProperties() {
		Map<Object, Object> properties = super.toProperties();
		EList<BackingStoreForPropertyConfig> propertyList = getProperty();
		if (propertyList != null && propertyList.isEmpty() == false) {
			for (BackingStoreForPropertyConfig property : propertyList) {
				if (property.getMaxSize() != null) {
					properties.put("be.engine.cluster.${DomainObject}.property."+CddTools.getValueFromMixed(property.getName())+".maxSize", CddTools.getValueFromMixed(property.getMaxSize()));
				}
				if (property.getReverseReferences() != null) {
					properties.put("be.engine.cluster.${DomainObject}.property."+CddTools.getValueFromMixed(property.getName())+".reverseReferences", CddTools.getValueFromMixed(property.getReverseReferences()));
				}
			}
		}
		return properties;
	}
} //BackingStoreForPropertiesConfigImpl
