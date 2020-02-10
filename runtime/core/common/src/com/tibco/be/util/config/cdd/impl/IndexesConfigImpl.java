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

import com.tibco.be.util.config.ConfigTools;
import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.IndexConfig;
import com.tibco.be.util.config.cdd.IndexesConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Indexes Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.IndexesConfigImpl#getIndex <em>Index</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class IndexesConfigImpl extends EObjectImpl implements IndexesConfig {
	/**
	 * The cached value of the '{@link #getIndex() <em>Index</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIndex()
	 * @generated
	 * @ordered
	 */
	protected EList<IndexConfig> index;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IndexesConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getIndexesConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<IndexConfig> getIndex() {
		if (index == null) {
			index = new EObjectContainmentEList<IndexConfig>(IndexConfig.class, this, CddPackage.INDEXES_CONFIG__INDEX);
		}
		return index;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Map<Object, Object> toProperties() {
		final Properties p = new Properties();
		int count = 0;
		for (final IndexConfig index : this.getIndex()) {
			p.putAll(ConfigTools.addSuffixToKeys(index.toProperties(),
					"." + count));
			count++;
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
			case CddPackage.INDEXES_CONFIG__INDEX:
				return ((InternalEList<?>)getIndex()).basicRemove(otherEnd, msgs);
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
			case CddPackage.INDEXES_CONFIG__INDEX:
				return getIndex();
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
			case CddPackage.INDEXES_CONFIG__INDEX:
				getIndex().clear();
				getIndex().addAll((Collection<? extends IndexConfig>)newValue);
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
			case CddPackage.INDEXES_CONFIG__INDEX:
				getIndex().clear();
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
			case CddPackage.INDEXES_CONFIG__INDEX:
				return index != null && !index.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //IndexesConfigImpl
