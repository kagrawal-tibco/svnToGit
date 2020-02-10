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

import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.studio.core.index.model.DecisionTableElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.IndexPackage;
import com.tibco.cep.studio.core.index.model.SharedDecisionTableElement;
import com.tibco.cep.studio.core.index.model.TypeElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Shared Decision Table Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.SharedDecisionTableElementImpl#getFolder <em>Folder</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.SharedDecisionTableElementImpl#getImplementation <em>Implementation</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.SharedDecisionTableElementImpl#getSharedImplementation <em>Shared Implementation</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SharedDecisionTableElementImpl extends SharedElementImpl implements SharedDecisionTableElement {
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
	 * The cached value of the '{@link #getImplementation() <em>Implementation</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplementation()
	 * @generated
	 * @ordered
	 */
	protected EObject implementation;

	/**
	 * The cached value of the '{@link #getSharedImplementation() <em>Shared Implementation</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSharedImplementation()
	 * @generated
	 * @ordered
	 */
	protected EObject sharedImplementation;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SharedDecisionTableElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IndexPackage.Literals.SHARED_DECISION_TABLE_ELEMENT;
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
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.SHARED_DECISION_TABLE_ELEMENT__FOLDER, oldFolder, folder));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EObject getImplementation() {
		return getSharedImplementation();
//		if (implementation != null && implementation.eIsProxy()) {
//			InternalEObject oldImplementation = (InternalEObject)implementation;
//			implementation = eResolveProxy(oldImplementation);
//			if (implementation != oldImplementation) {
//				if (eNotificationRequired())
//					eNotify(new ENotificationImpl(this, Notification.RESOLVE, IndexPackage.SHARED_DECISION_TABLE_ELEMENT__IMPLEMENTATION, oldImplementation, implementation));
//			}
//		}
//		return implementation;
	}

	private EObject loadSharedImpl() {
		String archivePath = getArchivePath();
		String entryPath = getEntryPath()+getFileName();
		InputStream stream = null;
		JarFile jarFile = null;
		try {
			jarFile = new JarFile(archivePath);
			JarEntry entry = (JarEntry) jarFile.getEntry(entryPath);
			stream = jarFile.getInputStream(entry);
			EObject obj = CommonIndexUtils.deserializeEObject(stream);
			if (obj instanceof Table) {
				Table e = (Table) obj;
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
	public EObject basicGetImplementation() {
		return implementation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImplementation(EObject newImplementation) {
		EObject oldImplementation = implementation;
		implementation = newImplementation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.SHARED_DECISION_TABLE_ELEMENT__IMPLEMENTATION, oldImplementation, implementation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EObject getSharedImplementation() {
		if (sharedImplementation == null) {
			// lazily load shared entity from archive
			sharedImplementation = loadSharedImpl();
		}
		return sharedImplementation;
//		if (sharedImplementation != null && sharedImplementation.eIsProxy()) {
//			InternalEObject oldSharedImplementation = (InternalEObject)sharedImplementation;
//			sharedImplementation = eResolveProxy(oldSharedImplementation);
//			if (sharedImplementation != oldSharedImplementation) {
//				if (eNotificationRequired())
//					eNotify(new ENotificationImpl(this, Notification.RESOLVE, IndexPackage.SHARED_DECISION_TABLE_ELEMENT__SHARED_IMPLEMENTATION, oldSharedImplementation, sharedImplementation));
//			}
//		}
//		return sharedImplementation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSharedImplementation(EObject newSharedImplementation, NotificationChain msgs) {
		EObject oldSharedImplementation = sharedImplementation;
		sharedImplementation = newSharedImplementation;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IndexPackage.SHARED_DECISION_TABLE_ELEMENT__SHARED_IMPLEMENTATION, oldSharedImplementation, newSharedImplementation);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSharedImplementation(EObject newSharedImplementation) {
		if (newSharedImplementation != sharedImplementation) {
			NotificationChain msgs = null;
			if (sharedImplementation != null)
				msgs = ((InternalEObject)sharedImplementation).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IndexPackage.SHARED_DECISION_TABLE_ELEMENT__SHARED_IMPLEMENTATION, null, msgs);
			if (newSharedImplementation != null)
				msgs = ((InternalEObject)newSharedImplementation).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IndexPackage.SHARED_DECISION_TABLE_ELEMENT__SHARED_IMPLEMENTATION, null, msgs);
			msgs = basicSetSharedImplementation(newSharedImplementation, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.SHARED_DECISION_TABLE_ELEMENT__SHARED_IMPLEMENTATION, newSharedImplementation, newSharedImplementation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IndexPackage.SHARED_DECISION_TABLE_ELEMENT__SHARED_IMPLEMENTATION:
				return basicSetSharedImplementation(null, msgs);
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
			case IndexPackage.SHARED_DECISION_TABLE_ELEMENT__FOLDER:
				return getFolder();
			case IndexPackage.SHARED_DECISION_TABLE_ELEMENT__IMPLEMENTATION:
				if (resolve) return getImplementation();
				return basicGetImplementation();
			case IndexPackage.SHARED_DECISION_TABLE_ELEMENT__SHARED_IMPLEMENTATION:
				return getSharedImplementation();
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
			case IndexPackage.SHARED_DECISION_TABLE_ELEMENT__FOLDER:
				setFolder((String)newValue);
				return;
			case IndexPackage.SHARED_DECISION_TABLE_ELEMENT__IMPLEMENTATION:
				setImplementation((EObject)newValue);
				return;
			case IndexPackage.SHARED_DECISION_TABLE_ELEMENT__SHARED_IMPLEMENTATION:
				setSharedImplementation((EObject)newValue);
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
			case IndexPackage.SHARED_DECISION_TABLE_ELEMENT__FOLDER:
				setFolder(FOLDER_EDEFAULT);
				return;
			case IndexPackage.SHARED_DECISION_TABLE_ELEMENT__IMPLEMENTATION:
				setImplementation((EObject)null);
				return;
			case IndexPackage.SHARED_DECISION_TABLE_ELEMENT__SHARED_IMPLEMENTATION:
				setSharedImplementation((EObject)null);
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
			case IndexPackage.SHARED_DECISION_TABLE_ELEMENT__FOLDER:
				return FOLDER_EDEFAULT == null ? folder != null : !FOLDER_EDEFAULT.equals(folder);
			case IndexPackage.SHARED_DECISION_TABLE_ELEMENT__IMPLEMENTATION:
				return implementation != null;
			case IndexPackage.SHARED_DECISION_TABLE_ELEMENT__SHARED_IMPLEMENTATION:
				return sharedImplementation != null;
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
				case IndexPackage.SHARED_DECISION_TABLE_ELEMENT__FOLDER: return IndexPackage.TYPE_ELEMENT__FOLDER;
				default: return -1;
			}
		}
		if (baseClass == DecisionTableElement.class) {
			switch (derivedFeatureID) {
				case IndexPackage.SHARED_DECISION_TABLE_ELEMENT__IMPLEMENTATION: return IndexPackage.DECISION_TABLE_ELEMENT__IMPLEMENTATION;
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
				case IndexPackage.TYPE_ELEMENT__FOLDER: return IndexPackage.SHARED_DECISION_TABLE_ELEMENT__FOLDER;
				default: return -1;
			}
		}
		if (baseClass == DecisionTableElement.class) {
			switch (baseFeatureID) {
				case IndexPackage.DECISION_TABLE_ELEMENT__IMPLEMENTATION: return IndexPackage.SHARED_DECISION_TABLE_ELEMENT__IMPLEMENTATION;
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

} //SharedDecisionTableElementImpl
