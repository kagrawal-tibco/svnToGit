/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.studio.core.index.model.IStructuredElementVisitor;
import com.tibco.cep.studio.core.index.model.IndexPackage;
import com.tibco.cep.studio.core.index.model.SharedElement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Shared Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.SharedElementImpl#getArchivePath <em>Archive Path</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.SharedElementImpl#getEntryPath <em>Entry Path</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.SharedElementImpl#getFileName <em>File Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SharedElementImpl extends DesignerElementImpl implements SharedElement {
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SharedElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IndexPackage.Literals.SHARED_ELEMENT;
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
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.SHARED_ELEMENT__ARCHIVE_PATH, oldArchivePath, archivePath));
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
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.SHARED_ELEMENT__ENTRY_PATH, oldEntryPath, entryPath));
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
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.SHARED_ELEMENT__FILE_NAME, oldFileName, fileName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case IndexPackage.SHARED_ELEMENT__ARCHIVE_PATH:
				return getArchivePath();
			case IndexPackage.SHARED_ELEMENT__ENTRY_PATH:
				return getEntryPath();
			case IndexPackage.SHARED_ELEMENT__FILE_NAME:
				return getFileName();
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
			case IndexPackage.SHARED_ELEMENT__ARCHIVE_PATH:
				setArchivePath((String)newValue);
				return;
			case IndexPackage.SHARED_ELEMENT__ENTRY_PATH:
				setEntryPath((String)newValue);
				return;
			case IndexPackage.SHARED_ELEMENT__FILE_NAME:
				setFileName((String)newValue);
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
			case IndexPackage.SHARED_ELEMENT__ARCHIVE_PATH:
				setArchivePath(ARCHIVE_PATH_EDEFAULT);
				return;
			case IndexPackage.SHARED_ELEMENT__ENTRY_PATH:
				setEntryPath(ENTRY_PATH_EDEFAULT);
				return;
			case IndexPackage.SHARED_ELEMENT__FILE_NAME:
				setFileName(FILE_NAME_EDEFAULT);
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
			case IndexPackage.SHARED_ELEMENT__ARCHIVE_PATH:
				return ARCHIVE_PATH_EDEFAULT == null ? archivePath != null : !ARCHIVE_PATH_EDEFAULT.equals(archivePath);
			case IndexPackage.SHARED_ELEMENT__ENTRY_PATH:
				return ENTRY_PATH_EDEFAULT == null ? entryPath != null : !ENTRY_PATH_EDEFAULT.equals(entryPath);
			case IndexPackage.SHARED_ELEMENT__FILE_NAME:
				return FILE_NAME_EDEFAULT == null ? fileName != null : !FILE_NAME_EDEFAULT.equals(fileName);
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
		result.append(" (archivePath: ");
		result.append(archivePath);
		result.append(", entryPath: ");
		result.append(entryPath);
		result.append(", fileName: ");
		result.append(fileName);
		result.append(')');
		return result.toString();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.index.model.DesignerElement#accept(com.tibco.cep.studio.core.index.IStructuredElementVisitor)
	 * @generated NOT
	 */
	@Override
	public void accept(IStructuredElementVisitor visitor) {
		visitor.visit(this);
	}

} //SharedElementImpl
