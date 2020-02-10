/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model.impl;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.designtime.core.model.java.JavaSource;

import com.tibco.cep.studio.core.index.model.IndexPackage;
import com.tibco.cep.studio.core.index.model.SharedElement;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.model.JavaElement;
import com.tibco.cep.studio.core.index.model.SharedJavaElement;
import com.tibco.cep.studio.core.index.model.TypeElement;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Shared Java Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.SharedJavaElementImpl#getArchivePath <em>Archive Path</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.SharedJavaElementImpl#getEntryPath <em>Entry Path</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.SharedJavaElementImpl#getFileName <em>File Name</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.SharedJavaElementImpl#getSharedEntity <em>Shared Entity</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SharedJavaElementImpl extends JavaElementImpl implements SharedJavaElement {
	/**
	 * The default value of the '{@link #getArchivePath() <em>Archive Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArchivePath()
	 * @generated
	 * @ordered
	 */
	protected static final String ARCHIVE_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getArchivePath() <em>Archive Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArchivePath()
	 * @generated
	 * @ordered
	 */
	protected String archivePath = ARCHIVE_PATH_EDEFAULT;

	/**
	 * The default value of the '{@link #getEntryPath() <em>Entry Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntryPath()
	 * @generated
	 * @ordered
	 */
	protected static final String ENTRY_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEntryPath() <em>Entry Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntryPath()
	 * @generated
	 * @ordered
	 */
	protected String entryPath = ENTRY_PATH_EDEFAULT;

	/**
	 * The default value of the '{@link #getFileName() <em>File Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFileName()
	 * @generated
	 * @ordered
	 */
	protected static final String FILE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFileName() <em>File Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFileName()
	 * @generated
	 * @ordered
	 */
	protected String fileName = FILE_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSharedEntity() <em>Shared Entity</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSharedEntity()
	 * @generated
	 * @ordered
	 */
	protected Entity sharedEntity;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SharedJavaElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IndexPackage.Literals.SHARED_JAVA_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getArchivePath() {
		return archivePath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setArchivePath(String newArchivePath) {
		String oldArchivePath = archivePath;
		archivePath = newArchivePath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.SHARED_JAVA_ELEMENT__ARCHIVE_PATH, oldArchivePath, archivePath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEntryPath() {
		return entryPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEntryPath(String newEntryPath) {
		String oldEntryPath = entryPath;
		entryPath = newEntryPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.SHARED_JAVA_ELEMENT__ENTRY_PATH, oldEntryPath, entryPath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFileName(String newFileName) {
		String oldFileName = fileName;
		fileName = newFileName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.SHARED_JAVA_ELEMENT__FILE_NAME, oldFileName, fileName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Entity getSharedEntity() {
		return sharedEntity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSharedEntity(Entity newSharedEntity, NotificationChain msgs) {
		Entity oldSharedEntity = sharedEntity;
		sharedEntity = newSharedEntity;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IndexPackage.SHARED_JAVA_ELEMENT__SHARED_ENTITY, oldSharedEntity, newSharedEntity);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSharedEntity(Entity newSharedEntity) {
		if (newSharedEntity != sharedEntity) {
			NotificationChain msgs = null;
			if (sharedEntity != null)
				msgs = ((InternalEObject)sharedEntity).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IndexPackage.SHARED_JAVA_ELEMENT__SHARED_ENTITY, null, msgs);
			if (newSharedEntity != null)
				msgs = ((InternalEObject)newSharedEntity).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IndexPackage.SHARED_JAVA_ELEMENT__SHARED_ENTITY, null, msgs);
			msgs = basicSetSharedEntity(newSharedEntity, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.SHARED_JAVA_ELEMENT__SHARED_ENTITY, newSharedEntity, newSharedEntity));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IndexPackage.SHARED_JAVA_ELEMENT__SHARED_ENTITY:
				return basicSetSharedEntity(null, msgs);
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
			case IndexPackage.SHARED_JAVA_ELEMENT__ARCHIVE_PATH:
				return getArchivePath();
			case IndexPackage.SHARED_JAVA_ELEMENT__ENTRY_PATH:
				return getEntryPath();
			case IndexPackage.SHARED_JAVA_ELEMENT__FILE_NAME:
				return getFileName();
			case IndexPackage.SHARED_JAVA_ELEMENT__SHARED_ENTITY:
				return getSharedEntity();
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
			case IndexPackage.SHARED_JAVA_ELEMENT__ARCHIVE_PATH:
				setArchivePath((String)newValue);
				return;
			case IndexPackage.SHARED_JAVA_ELEMENT__ENTRY_PATH:
				setEntryPath((String)newValue);
				return;
			case IndexPackage.SHARED_JAVA_ELEMENT__FILE_NAME:
				setFileName((String)newValue);
				return;
			case IndexPackage.SHARED_JAVA_ELEMENT__SHARED_ENTITY:
				setSharedEntity((Entity)newValue);
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
			case IndexPackage.SHARED_JAVA_ELEMENT__ARCHIVE_PATH:
				setArchivePath(ARCHIVE_PATH_EDEFAULT);
				return;
			case IndexPackage.SHARED_JAVA_ELEMENT__ENTRY_PATH:
				setEntryPath(ENTRY_PATH_EDEFAULT);
				return;
			case IndexPackage.SHARED_JAVA_ELEMENT__FILE_NAME:
				setFileName(FILE_NAME_EDEFAULT);
				return;
			case IndexPackage.SHARED_JAVA_ELEMENT__SHARED_ENTITY:
				setSharedEntity((Entity)null);
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
			case IndexPackage.SHARED_JAVA_ELEMENT__ARCHIVE_PATH:
				return ARCHIVE_PATH_EDEFAULT == null ? archivePath != null : !ARCHIVE_PATH_EDEFAULT.equals(archivePath);
			case IndexPackage.SHARED_JAVA_ELEMENT__ENTRY_PATH:
				return ENTRY_PATH_EDEFAULT == null ? entryPath != null : !ENTRY_PATH_EDEFAULT.equals(entryPath);
			case IndexPackage.SHARED_JAVA_ELEMENT__FILE_NAME:
				return FILE_NAME_EDEFAULT == null ? fileName != null : !FILE_NAME_EDEFAULT.equals(fileName);
			case IndexPackage.SHARED_JAVA_ELEMENT__SHARED_ENTITY:
				return sharedEntity != null;
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
		if (baseClass == SharedElement.class) {
			switch (derivedFeatureID) {
				case IndexPackage.SHARED_JAVA_ELEMENT__ARCHIVE_PATH: return IndexPackage.SHARED_ELEMENT__ARCHIVE_PATH;
				case IndexPackage.SHARED_JAVA_ELEMENT__ENTRY_PATH: return IndexPackage.SHARED_ELEMENT__ENTRY_PATH;
				case IndexPackage.SHARED_JAVA_ELEMENT__FILE_NAME: return IndexPackage.SHARED_ELEMENT__FILE_NAME;
				default: return -1;
			}
		}
		if (baseClass == SharedEntityElement.class) {
			switch (derivedFeatureID) {
				case IndexPackage.SHARED_JAVA_ELEMENT__SHARED_ENTITY: return IndexPackage.SHARED_ENTITY_ELEMENT__SHARED_ENTITY;
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
		if (baseClass == SharedElement.class) {
			switch (baseFeatureID) {
				case IndexPackage.SHARED_ELEMENT__ARCHIVE_PATH: return IndexPackage.SHARED_JAVA_ELEMENT__ARCHIVE_PATH;
				case IndexPackage.SHARED_ELEMENT__ENTRY_PATH: return IndexPackage.SHARED_JAVA_ELEMENT__ENTRY_PATH;
				case IndexPackage.SHARED_ELEMENT__FILE_NAME: return IndexPackage.SHARED_JAVA_ELEMENT__FILE_NAME;
				default: return -1;
			}
		}
		if (baseClass == SharedEntityElement.class) {
			switch (baseFeatureID) {
				case IndexPackage.SHARED_ENTITY_ELEMENT__SHARED_ENTITY: return IndexPackage.SHARED_JAVA_ELEMENT__SHARED_ENTITY;
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
		result.append(" (archivePath: ");
		result.append(archivePath);
		result.append(", entryPath: ");
		result.append(entryPath);
		result.append(", fileName: ");
		result.append(fileName);
		result.append(')');
		return result.toString();
	}

} //SharedJavaElementImpl
