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

import com.tibco.cep.designtime.core.model.archive.ArchivePackage;
import com.tibco.cep.designtime.core.model.archive.BEArchiveResource;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>BE Archive Resource</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.impl.BEArchiveResourceImpl#isCompileWithDebug <em>Compile With Debug</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.impl.BEArchiveResourceImpl#isDeleteTempFiles <em>Delete Temp Files</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.impl.BEArchiveResourceImpl#getCompilePath <em>Compile Path</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.impl.BEArchiveResourceImpl#getExtraClassPath <em>Extra Class Path</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BEArchiveResourceImpl extends ArchiveResourceImpl implements BEArchiveResource {
	/**
	 * The default value of the '{@link #isCompileWithDebug() <em>Compile With Debug</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCompileWithDebug()
	 * @generated
	 * @ordered
	 */
	protected static final boolean COMPILE_WITH_DEBUG_EDEFAULT = false;
	/**
	 * The cached value of the '{@link #isCompileWithDebug() <em>Compile With Debug</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCompileWithDebug()
	 * @generated
	 * @ordered
	 */
	protected boolean compileWithDebug = COMPILE_WITH_DEBUG_EDEFAULT;
	/**
	 * The default value of the '{@link #isDeleteTempFiles() <em>Delete Temp Files</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeleteTempFiles()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DELETE_TEMP_FILES_EDEFAULT = false;
	/**
	 * The cached value of the '{@link #isDeleteTempFiles() <em>Delete Temp Files</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeleteTempFiles()
	 * @generated
	 * @ordered
	 */
	protected boolean deleteTempFiles = DELETE_TEMP_FILES_EDEFAULT;
	/**
	 * The default value of the '{@link #getCompilePath() <em>Compile Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompilePath()
	 * @generated
	 * @ordered
	 */
	protected static final String COMPILE_PATH_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getCompilePath() <em>Compile Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompilePath()
	 * @generated
	 * @ordered
	 */
	protected String compilePath = COMPILE_PATH_EDEFAULT;
	/**
	 * The default value of the '{@link #getExtraClassPath() <em>Extra Class Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtraClassPath()
	 * @generated
	 * @ordered
	 */
	protected static final String EXTRA_CLASS_PATH_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getExtraClassPath() <em>Extra Class Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtraClassPath()
	 * @generated
	 * @ordered
	 */
	protected String extraClassPath = EXTRA_CLASS_PATH_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BEArchiveResourceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ArchivePackage.Literals.BE_ARCHIVE_RESOURCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isCompileWithDebug() {
		return compileWithDebug;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCompileWithDebug(boolean newCompileWithDebug) {
		boolean oldCompileWithDebug = compileWithDebug;
		compileWithDebug = newCompileWithDebug;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchivePackage.BE_ARCHIVE_RESOURCE__COMPILE_WITH_DEBUG, oldCompileWithDebug, compileWithDebug));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isDeleteTempFiles() {
		return deleteTempFiles;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDeleteTempFiles(boolean newDeleteTempFiles) {
		boolean oldDeleteTempFiles = deleteTempFiles;
		deleteTempFiles = newDeleteTempFiles;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchivePackage.BE_ARCHIVE_RESOURCE__DELETE_TEMP_FILES, oldDeleteTempFiles, deleteTempFiles));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCompilePath() {
		return compilePath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCompilePath(String newCompilePath) {
		String oldCompilePath = compilePath;
		compilePath = newCompilePath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchivePackage.BE_ARCHIVE_RESOURCE__COMPILE_PATH, oldCompilePath, compilePath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getExtraClassPath() {
		return extraClassPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExtraClassPath(String newExtraClassPath) {
		String oldExtraClassPath = extraClassPath;
		extraClassPath = newExtraClassPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchivePackage.BE_ARCHIVE_RESOURCE__EXTRA_CLASS_PATH, oldExtraClassPath, extraClassPath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ArchivePackage.BE_ARCHIVE_RESOURCE__COMPILE_WITH_DEBUG:
				return isCompileWithDebug();
			case ArchivePackage.BE_ARCHIVE_RESOURCE__DELETE_TEMP_FILES:
				return isDeleteTempFiles();
			case ArchivePackage.BE_ARCHIVE_RESOURCE__COMPILE_PATH:
				return getCompilePath();
			case ArchivePackage.BE_ARCHIVE_RESOURCE__EXTRA_CLASS_PATH:
				return getExtraClassPath();
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
			case ArchivePackage.BE_ARCHIVE_RESOURCE__COMPILE_WITH_DEBUG:
				setCompileWithDebug((Boolean)newValue);
				return;
			case ArchivePackage.BE_ARCHIVE_RESOURCE__DELETE_TEMP_FILES:
				setDeleteTempFiles((Boolean)newValue);
				return;
			case ArchivePackage.BE_ARCHIVE_RESOURCE__COMPILE_PATH:
				setCompilePath((String)newValue);
				return;
			case ArchivePackage.BE_ARCHIVE_RESOURCE__EXTRA_CLASS_PATH:
				setExtraClassPath((String)newValue);
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
			case ArchivePackage.BE_ARCHIVE_RESOURCE__COMPILE_WITH_DEBUG:
				setCompileWithDebug(COMPILE_WITH_DEBUG_EDEFAULT);
				return;
			case ArchivePackage.BE_ARCHIVE_RESOURCE__DELETE_TEMP_FILES:
				setDeleteTempFiles(DELETE_TEMP_FILES_EDEFAULT);
				return;
			case ArchivePackage.BE_ARCHIVE_RESOURCE__COMPILE_PATH:
				setCompilePath(COMPILE_PATH_EDEFAULT);
				return;
			case ArchivePackage.BE_ARCHIVE_RESOURCE__EXTRA_CLASS_PATH:
				setExtraClassPath(EXTRA_CLASS_PATH_EDEFAULT);
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
			case ArchivePackage.BE_ARCHIVE_RESOURCE__COMPILE_WITH_DEBUG:
				return compileWithDebug != COMPILE_WITH_DEBUG_EDEFAULT;
			case ArchivePackage.BE_ARCHIVE_RESOURCE__DELETE_TEMP_FILES:
				return deleteTempFiles != DELETE_TEMP_FILES_EDEFAULT;
			case ArchivePackage.BE_ARCHIVE_RESOURCE__COMPILE_PATH:
				return COMPILE_PATH_EDEFAULT == null ? compilePath != null : !COMPILE_PATH_EDEFAULT.equals(compilePath);
			case ArchivePackage.BE_ARCHIVE_RESOURCE__EXTRA_CLASS_PATH:
				return EXTRA_CLASS_PATH_EDEFAULT == null ? extraClassPath != null : !EXTRA_CLASS_PATH_EDEFAULT.equals(extraClassPath);
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
		result.append(" (compileWithDebug: ");
		result.append(compileWithDebug);
		result.append(", deleteTempFiles: ");
		result.append(deleteTempFiles);
		result.append(", compilePath: ");
		result.append(compilePath);
		result.append(", extraClassPath: ");
		result.append(extraClassPath);
		result.append(')');
		return result.toString();
	}

} //BEArchiveResourceImpl
