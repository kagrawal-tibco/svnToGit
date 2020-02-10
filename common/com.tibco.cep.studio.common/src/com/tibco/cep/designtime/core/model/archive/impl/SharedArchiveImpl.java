/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.archive.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

import com.tibco.cep.designtime.core.model.archive.ArchivePackage;
import com.tibco.cep.designtime.core.model.archive.SharedArchive;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Shared Archive</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.impl.SharedArchiveImpl#getSharedResources <em>Shared Resources</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.impl.SharedArchiveImpl#getSharedFiles <em>Shared Files</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.impl.SharedArchiveImpl#getSharedJarFiles <em>Shared Jar Files</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SharedArchiveImpl extends ArchiveResourceImpl implements SharedArchive {
	/**
	 * The cached value of the '{@link #getSharedResources() <em>Shared Resources</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSharedResources()
	 * @generated
	 * @ordered
	 */
	protected EList<String> sharedResources;
	/**
	 * The cached value of the '{@link #getSharedFiles() <em>Shared Files</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSharedFiles()
	 * @generated
	 * @ordered
	 */
	protected EList<String> sharedFiles;
	/**
	 * The cached value of the '{@link #getSharedJarFiles() <em>Shared Jar Files</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSharedJarFiles()
	 * @generated
	 * @ordered
	 */
	protected EList<String> sharedJarFiles;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SharedArchiveImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ArchivePackage.Literals.SHARED_ARCHIVE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getSharedResources() {
		if (sharedResources == null) {
			sharedResources = new EDataTypeUniqueEList<String>(String.class, this, ArchivePackage.SHARED_ARCHIVE__SHARED_RESOURCES);
		}
		return sharedResources;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getSharedFiles() {
		if (sharedFiles == null) {
			sharedFiles = new EDataTypeUniqueEList<String>(String.class, this, ArchivePackage.SHARED_ARCHIVE__SHARED_FILES);
		}
		return sharedFiles;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getSharedJarFiles() {
		if (sharedJarFiles == null) {
			sharedJarFiles = new EDataTypeUniqueEList<String>(String.class, this, ArchivePackage.SHARED_ARCHIVE__SHARED_JAR_FILES);
		}
		return sharedJarFiles;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ArchivePackage.SHARED_ARCHIVE__SHARED_RESOURCES:
				return getSharedResources();
			case ArchivePackage.SHARED_ARCHIVE__SHARED_FILES:
				return getSharedFiles();
			case ArchivePackage.SHARED_ARCHIVE__SHARED_JAR_FILES:
				return getSharedJarFiles();
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
			case ArchivePackage.SHARED_ARCHIVE__SHARED_RESOURCES:
				getSharedResources().clear();
				getSharedResources().addAll((Collection<? extends String>)newValue);
				return;
			case ArchivePackage.SHARED_ARCHIVE__SHARED_FILES:
				getSharedFiles().clear();
				getSharedFiles().addAll((Collection<? extends String>)newValue);
				return;
			case ArchivePackage.SHARED_ARCHIVE__SHARED_JAR_FILES:
				getSharedJarFiles().clear();
				getSharedJarFiles().addAll((Collection<? extends String>)newValue);
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
			case ArchivePackage.SHARED_ARCHIVE__SHARED_RESOURCES:
				getSharedResources().clear();
				return;
			case ArchivePackage.SHARED_ARCHIVE__SHARED_FILES:
				getSharedFiles().clear();
				return;
			case ArchivePackage.SHARED_ARCHIVE__SHARED_JAR_FILES:
				getSharedJarFiles().clear();
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
			case ArchivePackage.SHARED_ARCHIVE__SHARED_RESOURCES:
				return sharedResources != null && !sharedResources.isEmpty();
			case ArchivePackage.SHARED_ARCHIVE__SHARED_FILES:
				return sharedFiles != null && !sharedFiles.isEmpty();
			case ArchivePackage.SHARED_ARCHIVE__SHARED_JAR_FILES:
				return sharedJarFiles != null && !sharedJarFiles.isEmpty();
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
		result.append(" (sharedResources: ");
		result.append(sharedResources);
		result.append(", sharedFiles: ");
		result.append(sharedFiles);
		result.append(", sharedJarFiles: ");
		result.append(sharedJarFiles);
		result.append(')');
		return result.toString();
	}

} //SharedArchiveImpl
