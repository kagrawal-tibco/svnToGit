/**
 */
package com.tibco.be.util.config.cdd.impl;

import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.EntityConfig;
import com.tibco.be.util.config.cdd.EntitySetConfig;
import com.tibco.be.util.config.cdd.OverrideConfig;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Entity Set Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.EntitySetConfigImpl#getEntity <em>Entity</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.EntitySetConfigImpl#getGenerateLVFiles <em>Generate LV Files</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.EntitySetConfigImpl#getOutputPath <em>Output Path</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EntitySetConfigImpl extends EObjectImpl implements EntitySetConfig {
	/**
	 * The cached value of the '{@link #getEntity() <em>Entity</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntity()
	 * @generated
	 * @ordered
	 */
	protected EList<EntityConfig> entity;

	/**
	 * The cached value of the '{@link #getGenerateLVFiles() <em>Generate LV Files</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGenerateLVFiles()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig generateLVFiles;

	/**
	 * The cached value of the '{@link #getOutputPath() <em>Output Path</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutputPath()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig outputPath;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EntitySetConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getEntitySetConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EntityConfig> getEntity() {
		if (entity == null) {
			entity = new EObjectContainmentEList<EntityConfig>(EntityConfig.class, this, CddPackage.ENTITY_SET_CONFIG__ENTITY);
		}
		return entity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getGenerateLVFiles() {
		return generateLVFiles;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetGenerateLVFiles(OverrideConfig newGenerateLVFiles, NotificationChain msgs) {
		OverrideConfig oldGenerateLVFiles = generateLVFiles;
		generateLVFiles = newGenerateLVFiles;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.ENTITY_SET_CONFIG__GENERATE_LV_FILES, oldGenerateLVFiles, newGenerateLVFiles);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGenerateLVFiles(OverrideConfig newGenerateLVFiles) {
		if (newGenerateLVFiles != generateLVFiles) {
			NotificationChain msgs = null;
			if (generateLVFiles != null)
				msgs = ((InternalEObject)generateLVFiles).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.ENTITY_SET_CONFIG__GENERATE_LV_FILES, null, msgs);
			if (newGenerateLVFiles != null)
				msgs = ((InternalEObject)newGenerateLVFiles).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.ENTITY_SET_CONFIG__GENERATE_LV_FILES, null, msgs);
			msgs = basicSetGenerateLVFiles(newGenerateLVFiles, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.ENTITY_SET_CONFIG__GENERATE_LV_FILES, newGenerateLVFiles, newGenerateLVFiles));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getOutputPath() {
		return outputPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOutputPath(OverrideConfig newOutputPath, NotificationChain msgs) {
		OverrideConfig oldOutputPath = outputPath;
		outputPath = newOutputPath;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.ENTITY_SET_CONFIG__OUTPUT_PATH, oldOutputPath, newOutputPath);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOutputPath(OverrideConfig newOutputPath) {
		if (newOutputPath != outputPath) {
			NotificationChain msgs = null;
			if (outputPath != null)
				msgs = ((InternalEObject)outputPath).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.ENTITY_SET_CONFIG__OUTPUT_PATH, null, msgs);
			if (newOutputPath != null)
				msgs = ((InternalEObject)newOutputPath).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.ENTITY_SET_CONFIG__OUTPUT_PATH, null, msgs);
			msgs = basicSetOutputPath(newOutputPath, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.ENTITY_SET_CONFIG__OUTPUT_PATH, newOutputPath, newOutputPath));
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
			case CddPackage.ENTITY_SET_CONFIG__ENTITY:
				return ((InternalEList<?>)getEntity()).basicRemove(otherEnd, msgs);
			case CddPackage.ENTITY_SET_CONFIG__GENERATE_LV_FILES:
				return basicSetGenerateLVFiles(null, msgs);
			case CddPackage.ENTITY_SET_CONFIG__OUTPUT_PATH:
				return basicSetOutputPath(null, msgs);
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
			case CddPackage.ENTITY_SET_CONFIG__ENTITY:
				return getEntity();
			case CddPackage.ENTITY_SET_CONFIG__GENERATE_LV_FILES:
				return getGenerateLVFiles();
			case CddPackage.ENTITY_SET_CONFIG__OUTPUT_PATH:
				return getOutputPath();
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
			case CddPackage.ENTITY_SET_CONFIG__ENTITY:
				getEntity().clear();
				getEntity().addAll((Collection<? extends EntityConfig>)newValue);
				return;
			case CddPackage.ENTITY_SET_CONFIG__GENERATE_LV_FILES:
				setGenerateLVFiles((OverrideConfig)newValue);
				return;
			case CddPackage.ENTITY_SET_CONFIG__OUTPUT_PATH:
				setOutputPath((OverrideConfig)newValue);
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
			case CddPackage.ENTITY_SET_CONFIG__ENTITY:
				getEntity().clear();
				return;
			case CddPackage.ENTITY_SET_CONFIG__GENERATE_LV_FILES:
				setGenerateLVFiles((OverrideConfig)null);
				return;
			case CddPackage.ENTITY_SET_CONFIG__OUTPUT_PATH:
				setOutputPath((OverrideConfig)null);
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
			case CddPackage.ENTITY_SET_CONFIG__ENTITY:
				return entity != null && !entity.isEmpty();
			case CddPackage.ENTITY_SET_CONFIG__GENERATE_LV_FILES:
				return generateLVFiles != null;
			case CddPackage.ENTITY_SET_CONFIG__OUTPUT_PATH:
				return outputPath != null;
		}
		return super.eIsSet(featureID);
	}

} //EntitySetConfigImpl
