/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.archive.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.designtime.core.model.archive.AdvancedEntitySetting;
import com.tibco.cep.designtime.core.model.archive.ArchivePackage;
import com.tibco.cep.designtime.core.model.archive.CACHE_MODE;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Advanced Entity Setting</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.impl.AdvancedEntitySettingImpl#getEntity <em>Entity</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.impl.AdvancedEntitySettingImpl#isDeployed <em>Deployed</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.impl.AdvancedEntitySettingImpl#getRecoveryFunction <em>Recovery Function</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.impl.AdvancedEntitySettingImpl#getCacheMode <em>Cache Mode</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AdvancedEntitySettingImpl extends EObjectImpl implements AdvancedEntitySetting {
	/**
	 * The default value of the '{@link #getEntity() <em>Entity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntity()
	 * @generated
	 * @ordered
	 */
	protected static final String ENTITY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEntity() <em>Entity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntity()
	 * @generated
	 * @ordered
	 */
	protected String entity = ENTITY_EDEFAULT;

	/**
	 * The default value of the '{@link #isDeployed() <em>Deployed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeployed()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DEPLOYED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDeployed() <em>Deployed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeployed()
	 * @generated
	 * @ordered
	 */
	protected boolean deployed = DEPLOYED_EDEFAULT;

	/**
	 * The default value of the '{@link #getRecoveryFunction() <em>Recovery Function</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRecoveryFunction()
	 * @generated
	 * @ordered
	 */
	protected static final String RECOVERY_FUNCTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRecoveryFunction() <em>Recovery Function</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRecoveryFunction()
	 * @generated
	 * @ordered
	 */
	protected String recoveryFunction = RECOVERY_FUNCTION_EDEFAULT;

	/**
	 * The default value of the '{@link #getCacheMode() <em>Cache Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCacheMode()
	 * @generated
	 * @ordered
	 */
	protected static final CACHE_MODE CACHE_MODE_EDEFAULT = CACHE_MODE.CACHE_AND_MEMORY;

	/**
	 * The cached value of the '{@link #getCacheMode() <em>Cache Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCacheMode()
	 * @generated
	 * @ordered
	 */
	protected CACHE_MODE cacheMode = CACHE_MODE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AdvancedEntitySettingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ArchivePackage.Literals.ADVANCED_ENTITY_SETTING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEntity() {
		return entity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEntity(String newEntity) {
		String oldEntity = entity;
		entity = newEntity;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchivePackage.ADVANCED_ENTITY_SETTING__ENTITY, oldEntity, entity));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isDeployed() {
		return deployed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDeployed(boolean newDeployed) {
		boolean oldDeployed = deployed;
		deployed = newDeployed;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchivePackage.ADVANCED_ENTITY_SETTING__DEPLOYED, oldDeployed, deployed));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRecoveryFunction() {
		return recoveryFunction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRecoveryFunction(String newRecoveryFunction) {
		String oldRecoveryFunction = recoveryFunction;
		recoveryFunction = newRecoveryFunction;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchivePackage.ADVANCED_ENTITY_SETTING__RECOVERY_FUNCTION, oldRecoveryFunction, recoveryFunction));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CACHE_MODE getCacheMode() {
		return cacheMode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCacheMode(CACHE_MODE newCacheMode) {
		CACHE_MODE oldCacheMode = cacheMode;
		cacheMode = newCacheMode == null ? CACHE_MODE_EDEFAULT : newCacheMode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchivePackage.ADVANCED_ENTITY_SETTING__CACHE_MODE, oldCacheMode, cacheMode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ArchivePackage.ADVANCED_ENTITY_SETTING__ENTITY:
				return getEntity();
			case ArchivePackage.ADVANCED_ENTITY_SETTING__DEPLOYED:
				return isDeployed();
			case ArchivePackage.ADVANCED_ENTITY_SETTING__RECOVERY_FUNCTION:
				return getRecoveryFunction();
			case ArchivePackage.ADVANCED_ENTITY_SETTING__CACHE_MODE:
				return getCacheMode();
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
			case ArchivePackage.ADVANCED_ENTITY_SETTING__ENTITY:
				setEntity((String)newValue);
				return;
			case ArchivePackage.ADVANCED_ENTITY_SETTING__DEPLOYED:
				setDeployed((Boolean)newValue);
				return;
			case ArchivePackage.ADVANCED_ENTITY_SETTING__RECOVERY_FUNCTION:
				setRecoveryFunction((String)newValue);
				return;
			case ArchivePackage.ADVANCED_ENTITY_SETTING__CACHE_MODE:
				setCacheMode((CACHE_MODE)newValue);
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
			case ArchivePackage.ADVANCED_ENTITY_SETTING__ENTITY:
				setEntity(ENTITY_EDEFAULT);
				return;
			case ArchivePackage.ADVANCED_ENTITY_SETTING__DEPLOYED:
				setDeployed(DEPLOYED_EDEFAULT);
				return;
			case ArchivePackage.ADVANCED_ENTITY_SETTING__RECOVERY_FUNCTION:
				setRecoveryFunction(RECOVERY_FUNCTION_EDEFAULT);
				return;
			case ArchivePackage.ADVANCED_ENTITY_SETTING__CACHE_MODE:
				setCacheMode(CACHE_MODE_EDEFAULT);
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
			case ArchivePackage.ADVANCED_ENTITY_SETTING__ENTITY:
				return ENTITY_EDEFAULT == null ? entity != null : !ENTITY_EDEFAULT.equals(entity);
			case ArchivePackage.ADVANCED_ENTITY_SETTING__DEPLOYED:
				return deployed != DEPLOYED_EDEFAULT;
			case ArchivePackage.ADVANCED_ENTITY_SETTING__RECOVERY_FUNCTION:
				return RECOVERY_FUNCTION_EDEFAULT == null ? recoveryFunction != null : !RECOVERY_FUNCTION_EDEFAULT.equals(recoveryFunction);
			case ArchivePackage.ADVANCED_ENTITY_SETTING__CACHE_MODE:
				return cacheMode != CACHE_MODE_EDEFAULT;
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
		result.append(" (entity: ");
		result.append(entity);
		result.append(", deployed: ");
		result.append(deployed);
		result.append(", recoveryFunction: ");
		result.append(recoveryFunction);
		result.append(", cacheMode: ");
		result.append(cacheMode);
		result.append(')');
		return result.toString();
	}

} //AdvancedEntitySettingImpl
