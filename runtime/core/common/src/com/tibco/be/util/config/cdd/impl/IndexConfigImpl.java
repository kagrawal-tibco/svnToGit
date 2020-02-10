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

import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.IndexConfig;
import com.tibco.be.util.config.cdd.OverrideConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Index Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.IndexConfigImpl#getProperty <em>Property</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class IndexConfigImpl extends EObjectImpl implements IndexConfig {
	/**
	 * The cached value of the '{@link #getProperty() <em>Property</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperty()
	 * @generated
	 * @ordered
	 */
	protected EList<OverrideConfig> property;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IndexConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getIndexConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<OverrideConfig> getProperty() {
		if (property == null) {
			property = new EObjectContainmentEList<OverrideConfig>(OverrideConfig.class, this, CddPackage.INDEX_CONFIG__PROPERTY);
		}
		return property;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Map<Object, Object> toProperties() {
		final Properties p = new Properties();
		final StringBuffer sb = new StringBuffer();
		boolean addComma = false;
		for (final OverrideConfig property : this.getProperty()) {
			final String value = CddTools.getValueFromMixed(property, true);
			if (null != value) {
				if (addComma) {
					sb.append(",");
				} else {
					addComma = true;
				}
				sb.append(value);
			}
		}
		if (sb.length() > 0) {
			p.put("be.engine.cluster.${DomainObject}.index", sb.toString());
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
			case CddPackage.INDEX_CONFIG__PROPERTY:
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
			case CddPackage.INDEX_CONFIG__PROPERTY:
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
			case CddPackage.INDEX_CONFIG__PROPERTY:
				getProperty().clear();
				getProperty().addAll((Collection<? extends OverrideConfig>)newValue);
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
			case CddPackage.INDEX_CONFIG__PROPERTY:
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
			case CddPackage.INDEX_CONFIG__PROPERTY:
				return property != null && !property.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //IndexConfigImpl
