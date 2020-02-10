/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model.impl;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.core.index.model.EntityElement;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.studio.core.index.model.IndexPackage;
import com.tibco.cep.studio.core.index.model.ProcessElement;
import com.tibco.cep.studio.core.index.model.SharedProcessElement;
import com.tibco.cep.studio.core.index.model.TypeElement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Shared Process Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.SharedProcessElementImpl#getFolder <em>Folder</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.SharedProcessElementImpl#getEntity <em>Entity</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.SharedProcessElementImpl#getProcess <em>Process</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.SharedProcessElementImpl#getSharedProcess <em>Shared Process</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SharedProcessElementImpl extends SharedElementImpl implements SharedProcessElement {
	/**
	 * The default value of the '{@link #getFolder() <em>Folder</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFolder()
	 * @generated
	 * @ordered
	 */
	protected static final String FOLDER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFolder() <em>Folder</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFolder()
	 * @generated
	 * @ordered
	 */
	protected String folder = FOLDER_EDEFAULT;

	/**
	 * The cached value of the '{@link #getEntity() <em>Entity</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntity()
	 * @generated
	 * @ordered
	 */
	protected Entity entity;

	/**
	 * The cached value of the '{@link #getProcess() <em>Process</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProcess()
	 * @generated
	 * @ordered
	 */
	protected EObject process;

	/**
	 * The cached value of the '{@link #getSharedProcess() <em>Shared Process</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSharedProcess()
	 * @generated
	 * @ordered
	 */
	protected EObject sharedProcess;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SharedProcessElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IndexPackage.Literals.SHARED_PROCESS_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFolder() {
		return folder;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFolder(String newFolder) {
		String oldFolder = folder;
		folder = newFolder;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.SHARED_PROCESS_ELEMENT__FOLDER, oldFolder, folder));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Entity getEntity() {
		if (entity != null && entity.eIsProxy()) {
			InternalEObject oldEntity = (InternalEObject)entity;
			entity = (Entity)eResolveProxy(oldEntity);
			if (entity != oldEntity) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, IndexPackage.SHARED_PROCESS_ELEMENT__ENTITY, oldEntity, entity));
			}
		}
		return entity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Entity basicGetEntity() {
		return entity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEntity(Entity newEntity) {
		Entity oldEntity = entity;
		entity = newEntity;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.SHARED_PROCESS_ELEMENT__ENTITY, oldEntity, entity));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getProcess() {
		return process;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProcess(EObject newProcess, NotificationChain msgs) {
		EObject oldProcess = process;
		process = newProcess;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IndexPackage.SHARED_PROCESS_ELEMENT__PROCESS, oldProcess, newProcess);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProcess(EObject newProcess) {
		if (newProcess != process) {
			NotificationChain msgs = null;
			if (process != null)
				msgs = ((InternalEObject)process).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IndexPackage.SHARED_PROCESS_ELEMENT__PROCESS, null, msgs);
			if (newProcess != null)
				msgs = ((InternalEObject)newProcess).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IndexPackage.SHARED_PROCESS_ELEMENT__PROCESS, null, msgs);
			msgs = basicSetProcess(newProcess, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.SHARED_PROCESS_ELEMENT__PROCESS, newProcess, newProcess));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getSharedProcess() {
		if (sharedProcess != null && sharedProcess.eIsProxy()) {
			InternalEObject oldSharedProcess = (InternalEObject)sharedProcess;
			sharedProcess = eResolveProxy(oldSharedProcess);
			if (sharedProcess != oldSharedProcess) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, IndexPackage.SHARED_PROCESS_ELEMENT__SHARED_PROCESS, oldSharedProcess, sharedProcess));
			}
		}
		return sharedProcess;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetSharedProcess() {
		return sharedProcess;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSharedProcess(EObject newSharedProcess) {
		EObject oldSharedProcess = sharedProcess;
		sharedProcess = newSharedProcess;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.SHARED_PROCESS_ELEMENT__SHARED_PROCESS, oldSharedProcess, sharedProcess));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IndexPackage.SHARED_PROCESS_ELEMENT__PROCESS:
				return basicSetProcess(null, msgs);
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
			case IndexPackage.SHARED_PROCESS_ELEMENT__FOLDER:
				return getFolder();
			case IndexPackage.SHARED_PROCESS_ELEMENT__ENTITY:
				if (resolve) return getEntity();
				return basicGetEntity();
			case IndexPackage.SHARED_PROCESS_ELEMENT__PROCESS:
				return getProcess();
			case IndexPackage.SHARED_PROCESS_ELEMENT__SHARED_PROCESS:
				if (resolve) return getSharedProcess();
				return basicGetSharedProcess();
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
			case IndexPackage.SHARED_PROCESS_ELEMENT__FOLDER:
				setFolder((String)newValue);
				return;
			case IndexPackage.SHARED_PROCESS_ELEMENT__ENTITY:
				setEntity((Entity)newValue);
				return;
			case IndexPackage.SHARED_PROCESS_ELEMENT__PROCESS:
				setProcess((EObject)newValue);
				return;
			case IndexPackage.SHARED_PROCESS_ELEMENT__SHARED_PROCESS:
				setSharedProcess((EObject)newValue);
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
			case IndexPackage.SHARED_PROCESS_ELEMENT__FOLDER:
				setFolder(FOLDER_EDEFAULT);
				return;
			case IndexPackage.SHARED_PROCESS_ELEMENT__ENTITY:
				setEntity((Entity)null);
				return;
			case IndexPackage.SHARED_PROCESS_ELEMENT__PROCESS:
				setProcess((EObject)null);
				return;
			case IndexPackage.SHARED_PROCESS_ELEMENT__SHARED_PROCESS:
				setSharedProcess((EObject)null);
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
			case IndexPackage.SHARED_PROCESS_ELEMENT__FOLDER:
				return FOLDER_EDEFAULT == null ? folder != null : !FOLDER_EDEFAULT.equals(folder);
			case IndexPackage.SHARED_PROCESS_ELEMENT__ENTITY:
				return entity != null;
			case IndexPackage.SHARED_PROCESS_ELEMENT__PROCESS:
				return process != null;
			case IndexPackage.SHARED_PROCESS_ELEMENT__SHARED_PROCESS:
				return sharedProcess != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == TypeElement.class) {
			switch (derivedFeatureID) {
				case IndexPackage.SHARED_PROCESS_ELEMENT__FOLDER: return IndexPackage.TYPE_ELEMENT__FOLDER;
				default: return -1;
			}
		}
		if (baseClass == EntityElement.class) {
			switch (derivedFeatureID) {
				case IndexPackage.SHARED_PROCESS_ELEMENT__ENTITY: return IndexPackage.ENTITY_ELEMENT__ENTITY;
				default: return -1;
			}
		}
		if (baseClass == ProcessElement.class) {
			switch (derivedFeatureID) {
				case IndexPackage.SHARED_PROCESS_ELEMENT__PROCESS: return IndexPackage.PROCESS_ELEMENT__PROCESS;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == TypeElement.class) {
			switch (baseFeatureID) {
				case IndexPackage.TYPE_ELEMENT__FOLDER: return IndexPackage.SHARED_PROCESS_ELEMENT__FOLDER;
				default: return -1;
			}
		}
		if (baseClass == EntityElement.class) {
			switch (baseFeatureID) {
				case IndexPackage.ENTITY_ELEMENT__ENTITY: return IndexPackage.SHARED_PROCESS_ELEMENT__ENTITY;
				default: return -1;
			}
		}
		if (baseClass == ProcessElement.class) {
			switch (baseFeatureID) {
				case IndexPackage.PROCESS_ELEMENT__PROCESS: return IndexPackage.SHARED_PROCESS_ELEMENT__PROCESS;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(" (folder: ");
		result.append(folder);
		result.append(')');
		return result.toString();
	}

} //SharedProcessElementImpl
