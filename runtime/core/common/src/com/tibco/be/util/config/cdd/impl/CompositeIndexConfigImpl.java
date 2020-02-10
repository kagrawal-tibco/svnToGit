/**
 */
package com.tibco.be.util.config.cdd.impl;

import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.CompositeIndexConfig;
import com.tibco.be.util.config.cdd.CompositeIndexPropertiesConfig;
import com.tibco.be.util.config.cdd.OverrideConfig;

import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Composite Index Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CompositeIndexConfigImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CompositeIndexConfigImpl#getCompositeIndexProperties <em>Composite Index Properties</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CompositeIndexConfigImpl extends EObjectImpl implements CompositeIndexConfig {
	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig name;

	/**
	 * The cached value of the '{@link #getCompositeIndexProperties() <em>Composite Index Properties</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompositeIndexProperties()
	 * @generated
	 * @ordered
	 */
	protected CompositeIndexPropertiesConfig compositeIndexProperties;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CompositeIndexConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getCompositeIndexConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetName(OverrideConfig newName, NotificationChain msgs) {
		OverrideConfig oldName = name;
		name = newName;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.COMPOSITE_INDEX_CONFIG__NAME, oldName, newName);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(OverrideConfig newName) {
		if (newName != name) {
			NotificationChain msgs = null;
			if (name != null)
				msgs = ((InternalEObject)name).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.COMPOSITE_INDEX_CONFIG__NAME, null, msgs);
			if (newName != null)
				msgs = ((InternalEObject)newName).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.COMPOSITE_INDEX_CONFIG__NAME, null, msgs);
			msgs = basicSetName(newName, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.COMPOSITE_INDEX_CONFIG__NAME, newName, newName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompositeIndexPropertiesConfig getCompositeIndexProperties() {
		return compositeIndexProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCompositeIndexProperties(CompositeIndexPropertiesConfig newCompositeIndexProperties, NotificationChain msgs) {
		CompositeIndexPropertiesConfig oldCompositeIndexProperties = compositeIndexProperties;
		compositeIndexProperties = newCompositeIndexProperties;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.COMPOSITE_INDEX_CONFIG__COMPOSITE_INDEX_PROPERTIES, oldCompositeIndexProperties, newCompositeIndexProperties);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCompositeIndexProperties(CompositeIndexPropertiesConfig newCompositeIndexProperties) {
		if (newCompositeIndexProperties != compositeIndexProperties) {
			NotificationChain msgs = null;
			if (compositeIndexProperties != null)
				msgs = ((InternalEObject)compositeIndexProperties).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.COMPOSITE_INDEX_CONFIG__COMPOSITE_INDEX_PROPERTIES, null, msgs);
			if (newCompositeIndexProperties != null)
				msgs = ((InternalEObject)newCompositeIndexProperties).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.COMPOSITE_INDEX_CONFIG__COMPOSITE_INDEX_PROPERTIES, null, msgs);
			msgs = basicSetCompositeIndexProperties(newCompositeIndexProperties, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.COMPOSITE_INDEX_CONFIG__COMPOSITE_INDEX_PROPERTIES, newCompositeIndexProperties, newCompositeIndexProperties));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map<Object, Object> toProperties() {
		return new java.util.Properties();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.COMPOSITE_INDEX_CONFIG__NAME:
				return basicSetName(null, msgs);
			case CddPackage.COMPOSITE_INDEX_CONFIG__COMPOSITE_INDEX_PROPERTIES:
				return basicSetCompositeIndexProperties(null, msgs);
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
			case CddPackage.COMPOSITE_INDEX_CONFIG__NAME:
				return getName();
			case CddPackage.COMPOSITE_INDEX_CONFIG__COMPOSITE_INDEX_PROPERTIES:
				return getCompositeIndexProperties();
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
			case CddPackage.COMPOSITE_INDEX_CONFIG__NAME:
				setName((OverrideConfig)newValue);
				return;
			case CddPackage.COMPOSITE_INDEX_CONFIG__COMPOSITE_INDEX_PROPERTIES:
				setCompositeIndexProperties((CompositeIndexPropertiesConfig)newValue);
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
			case CddPackage.COMPOSITE_INDEX_CONFIG__NAME:
				setName((OverrideConfig)null);
				return;
			case CddPackage.COMPOSITE_INDEX_CONFIG__COMPOSITE_INDEX_PROPERTIES:
				setCompositeIndexProperties((CompositeIndexPropertiesConfig)null);
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
			case CddPackage.COMPOSITE_INDEX_CONFIG__NAME:
				return name != null;
			case CddPackage.COMPOSITE_INDEX_CONFIG__COMPOSITE_INDEX_PROPERTIES:
				return compositeIndexProperties != null;
		}
		return super.eIsSet(featureID);
	}

} //CompositeIndexConfigImpl
