/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.IndexPackage;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.model.TypeElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Shared Entity Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.SharedEntityElementImpl#getFolder <em>Folder</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.SharedEntityElementImpl#getEntity <em>Entity</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.SharedEntityElementImpl#getSharedEntity <em>Shared Entity</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SharedEntityElementImpl extends SharedElementImpl implements SharedEntityElement {
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
	protected SharedEntityElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IndexPackage.Literals.SHARED_ENTITY_ELEMENT;
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
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.SHARED_ENTITY_ELEMENT__FOLDER, oldFolder, folder));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Entity getEntity() {
		return getSharedEntity();
//		if (entity != null && entity.eIsProxy()) {
//			InternalEObject oldEntity = (InternalEObject)entity;
//			entity = (Entity)eResolveProxy(oldEntity);
//			if (entity != oldEntity) {
//				if (eNotificationRequired())
//					eNotify(new ENotificationImpl(this, Notification.RESOLVE, IndexPackage.SHARED_ENTITY_ELEMENT__ENTITY, oldEntity, entity));
//			}
//		}
//		return entity;
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
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.SHARED_ENTITY_ELEMENT__ENTITY, oldEntity, entity));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Entity getSharedEntity() {
		if (sharedEntity == null) {
			// lazily load shared entity from archive
			sharedEntity = loadSharedEntity();
		}
		return sharedEntity;
	}

	private Entity loadSharedEntity() {
		String archivePath = getArchivePath();
		String entryPath = getEntryPath()+getFileName();
		InputStream stream = null;
		JarFile jarFile = null;
		try {
			jarFile = new JarFile(archivePath);
			JarEntry entry = (JarEntry) jarFile.getEntry(entryPath);
			stream = jarFile.getInputStream(entry);
			EObject obj = CommonIndexUtils.deserializeEObject(stream);
			if (obj instanceof Entity) {
				Entity e = (Entity) obj;
				EObject rootContainer = CommonIndexUtils.getRootContainer(this);
				if (rootContainer instanceof DesignerProject) {
					e.setOwnerProjectName(((DesignerProject) rootContainer).getName());
				}
				return e;
			}
		} catch (Exception e) {
			// TODO : report error
			e.printStackTrace();
		} finally {
			
			try {
				if (stream != null){
					stream.close();
				}
				if (jarFile != null) {
					jarFile.close();
				}
			} catch (IOException e) {				
				e.printStackTrace();
			}
			
		}
		return null;
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IndexPackage.SHARED_ENTITY_ELEMENT__SHARED_ENTITY, oldSharedEntity, newSharedEntity);
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
				msgs = ((InternalEObject)sharedEntity).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IndexPackage.SHARED_ENTITY_ELEMENT__SHARED_ENTITY, null, msgs);
			if (newSharedEntity != null)
				msgs = ((InternalEObject)newSharedEntity).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IndexPackage.SHARED_ENTITY_ELEMENT__SHARED_ENTITY, null, msgs);
			msgs = basicSetSharedEntity(newSharedEntity, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.SHARED_ENTITY_ELEMENT__SHARED_ENTITY, newSharedEntity, newSharedEntity));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IndexPackage.SHARED_ENTITY_ELEMENT__SHARED_ENTITY:
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
			case IndexPackage.SHARED_ENTITY_ELEMENT__FOLDER:
				return getFolder();
			case IndexPackage.SHARED_ENTITY_ELEMENT__ENTITY:
				if (resolve) return getEntity();
				return basicGetEntity();
			case IndexPackage.SHARED_ENTITY_ELEMENT__SHARED_ENTITY:
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
			case IndexPackage.SHARED_ENTITY_ELEMENT__FOLDER:
				setFolder((String)newValue);
				return;
			case IndexPackage.SHARED_ENTITY_ELEMENT__ENTITY:
				setEntity((Entity)newValue);
				return;
			case IndexPackage.SHARED_ENTITY_ELEMENT__SHARED_ENTITY:
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
			case IndexPackage.SHARED_ENTITY_ELEMENT__FOLDER:
				setFolder(FOLDER_EDEFAULT);
				return;
			case IndexPackage.SHARED_ENTITY_ELEMENT__ENTITY:
				setEntity((Entity)null);
				return;
			case IndexPackage.SHARED_ENTITY_ELEMENT__SHARED_ENTITY:
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
			case IndexPackage.SHARED_ENTITY_ELEMENT__FOLDER:
				return FOLDER_EDEFAULT == null ? folder != null : !FOLDER_EDEFAULT.equals(folder);
			case IndexPackage.SHARED_ENTITY_ELEMENT__ENTITY:
				return entity != null;
			case IndexPackage.SHARED_ENTITY_ELEMENT__SHARED_ENTITY:
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
		if (baseClass == TypeElement.class) {
			switch (derivedFeatureID) {
				case IndexPackage.SHARED_ENTITY_ELEMENT__FOLDER: return IndexPackage.TYPE_ELEMENT__FOLDER;
				default: return -1;
			}
		}
		if (baseClass == EntityElement.class) {
			switch (derivedFeatureID) {
				case IndexPackage.SHARED_ENTITY_ELEMENT__ENTITY: return IndexPackage.ENTITY_ELEMENT__ENTITY;
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
				case IndexPackage.TYPE_ELEMENT__FOLDER: return IndexPackage.SHARED_ENTITY_ELEMENT__FOLDER;
				default: return -1;
			}
		}
		if (baseClass == EntityElement.class) {
			switch (baseFeatureID) {
				case IndexPackage.ENTITY_ELEMENT__ENTITY: return IndexPackage.SHARED_ENTITY_ELEMENT__ENTITY;
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

} //SharedEntityElementImpl
