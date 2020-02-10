/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.archive.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.designtime.core.model.archive.ArchivePackage;
import com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource;
import com.tibco.cep.designtime.core.model.archive.EnterpriseArchive;
import com.tibco.cep.designtime.core.model.archive.SharedArchive;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Enterprise Archive</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.impl.EnterpriseArchiveImpl#getVersion <em>Version</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.impl.EnterpriseArchiveImpl#getFolder <em>Folder</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.impl.EnterpriseArchiveImpl#getOwnerProjectName <em>Owner Project Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.impl.EnterpriseArchiveImpl#getFileLocation <em>File Location</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.impl.EnterpriseArchiveImpl#isIncludeGlobalVars <em>Include Global Vars</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.impl.EnterpriseArchiveImpl#getBusinessEventsArchives <em>Business Events Archives</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.impl.EnterpriseArchiveImpl#getSharedArchives <em>Shared Archives</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.impl.EnterpriseArchiveImpl#getProcessArchives <em>Process Archives</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EnterpriseArchiveImpl extends ArchiveResourceImpl implements EnterpriseArchive {
	/**
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected String version = VERSION_EDEFAULT;

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
	 * The default value of the '{@link #getOwnerProjectName() <em>Owner Project Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnerProjectName()
	 * @generated
	 * @ordered
	 */
	protected static final String OWNER_PROJECT_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOwnerProjectName() <em>Owner Project Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnerProjectName()
	 * @generated
	 * @ordered
	 */
	protected String ownerProjectName = OWNER_PROJECT_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getFileLocation() <em>File Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFileLocation()
	 * @generated
	 * @ordered
	 */
	protected static final String FILE_LOCATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFileLocation() <em>File Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFileLocation()
	 * @generated
	 * @ordered
	 */
	protected String fileLocation = FILE_LOCATION_EDEFAULT;

	/**
	 * The default value of the '{@link #isIncludeGlobalVars() <em>Include Global Vars</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIncludeGlobalVars()
	 * @generated
	 * @ordered
	 */
	protected static final boolean INCLUDE_GLOBAL_VARS_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIncludeGlobalVars() <em>Include Global Vars</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIncludeGlobalVars()
	 * @generated
	 * @ordered
	 */
	protected boolean includeGlobalVars = INCLUDE_GLOBAL_VARS_EDEFAULT;

	/**
	 * The cached value of the '{@link #getBusinessEventsArchives() <em>Business Events Archives</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBusinessEventsArchives()
	 * @generated
	 * @ordered
	 */
	protected EList<BusinessEventsArchiveResource> businessEventsArchives;

	/**
	 * The cached value of the '{@link #getSharedArchives() <em>Shared Archives</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSharedArchives()
	 * @generated
	 * @ordered
	 */
	protected EList<SharedArchive> sharedArchives;

	/**
	 * The cached value of the '{@link #getProcessArchives() <em>Process Archives</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProcessArchives()
	 * @generated
	 * @ordered
	 */
	protected EList<SharedArchive> processArchives;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EnterpriseArchiveImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ArchivePackage.Literals.ENTERPRISE_ARCHIVE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVersion(String newVersion) {
		String oldVersion = version;
		version = newVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchivePackage.ENTERPRISE_ARCHIVE__VERSION, oldVersion, version));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ArchivePackage.ENTERPRISE_ARCHIVE__FOLDER, oldFolder, folder));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getOwnerProjectName() {
		return ownerProjectName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOwnerProjectName(String newOwnerProjectName) {
		String oldOwnerProjectName = ownerProjectName;
		ownerProjectName = newOwnerProjectName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchivePackage.ENTERPRISE_ARCHIVE__OWNER_PROJECT_NAME, oldOwnerProjectName, ownerProjectName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFileLocation() {
		return fileLocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFileLocation(String newFileLocation) {
		String oldFileLocation = fileLocation;
		fileLocation = newFileLocation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchivePackage.ENTERPRISE_ARCHIVE__FILE_LOCATION, oldFileLocation, fileLocation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIncludeGlobalVars() {
		return includeGlobalVars;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIncludeGlobalVars(boolean newIncludeGlobalVars) {
		boolean oldIncludeGlobalVars = includeGlobalVars;
		includeGlobalVars = newIncludeGlobalVars;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchivePackage.ENTERPRISE_ARCHIVE__INCLUDE_GLOBAL_VARS, oldIncludeGlobalVars, includeGlobalVars));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<BusinessEventsArchiveResource> getBusinessEventsArchives() {
		if (businessEventsArchives == null) {
			businessEventsArchives = new EObjectContainmentEList<BusinessEventsArchiveResource>(BusinessEventsArchiveResource.class, this, ArchivePackage.ENTERPRISE_ARCHIVE__BUSINESS_EVENTS_ARCHIVES);
		}
		return businessEventsArchives;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SharedArchive> getSharedArchives() {
		if (sharedArchives == null) {
			sharedArchives = new EObjectContainmentEList<SharedArchive>(SharedArchive.class, this, ArchivePackage.ENTERPRISE_ARCHIVE__SHARED_ARCHIVES);
		}
		return sharedArchives;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SharedArchive> getProcessArchives() {
		if (processArchives == null) {
			processArchives = new EObjectContainmentEList<SharedArchive>(SharedArchive.class, this, ArchivePackage.ENTERPRISE_ARCHIVE__PROCESS_ARCHIVES);
		}
		return processArchives;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ArchivePackage.ENTERPRISE_ARCHIVE__BUSINESS_EVENTS_ARCHIVES:
				return ((InternalEList<?>)getBusinessEventsArchives()).basicRemove(otherEnd, msgs);
			case ArchivePackage.ENTERPRISE_ARCHIVE__SHARED_ARCHIVES:
				return ((InternalEList<?>)getSharedArchives()).basicRemove(otherEnd, msgs);
			case ArchivePackage.ENTERPRISE_ARCHIVE__PROCESS_ARCHIVES:
				return ((InternalEList<?>)getProcessArchives()).basicRemove(otherEnd, msgs);
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
			case ArchivePackage.ENTERPRISE_ARCHIVE__VERSION:
				return getVersion();
			case ArchivePackage.ENTERPRISE_ARCHIVE__FOLDER:
				return getFolder();
			case ArchivePackage.ENTERPRISE_ARCHIVE__OWNER_PROJECT_NAME:
				return getOwnerProjectName();
			case ArchivePackage.ENTERPRISE_ARCHIVE__FILE_LOCATION:
				return getFileLocation();
			case ArchivePackage.ENTERPRISE_ARCHIVE__INCLUDE_GLOBAL_VARS:
				return isIncludeGlobalVars();
			case ArchivePackage.ENTERPRISE_ARCHIVE__BUSINESS_EVENTS_ARCHIVES:
				return getBusinessEventsArchives();
			case ArchivePackage.ENTERPRISE_ARCHIVE__SHARED_ARCHIVES:
				return getSharedArchives();
			case ArchivePackage.ENTERPRISE_ARCHIVE__PROCESS_ARCHIVES:
				return getProcessArchives();
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
			case ArchivePackage.ENTERPRISE_ARCHIVE__VERSION:
				setVersion((String)newValue);
				return;
			case ArchivePackage.ENTERPRISE_ARCHIVE__FOLDER:
				setFolder((String)newValue);
				return;
			case ArchivePackage.ENTERPRISE_ARCHIVE__OWNER_PROJECT_NAME:
				setOwnerProjectName((String)newValue);
				return;
			case ArchivePackage.ENTERPRISE_ARCHIVE__FILE_LOCATION:
				setFileLocation((String)newValue);
				return;
			case ArchivePackage.ENTERPRISE_ARCHIVE__INCLUDE_GLOBAL_VARS:
				setIncludeGlobalVars((Boolean)newValue);
				return;
			case ArchivePackage.ENTERPRISE_ARCHIVE__BUSINESS_EVENTS_ARCHIVES:
				getBusinessEventsArchives().clear();
				getBusinessEventsArchives().addAll((Collection<? extends BusinessEventsArchiveResource>)newValue);
				return;
			case ArchivePackage.ENTERPRISE_ARCHIVE__SHARED_ARCHIVES:
				getSharedArchives().clear();
				getSharedArchives().addAll((Collection<? extends SharedArchive>)newValue);
				return;
			case ArchivePackage.ENTERPRISE_ARCHIVE__PROCESS_ARCHIVES:
				getProcessArchives().clear();
				getProcessArchives().addAll((Collection<? extends SharedArchive>)newValue);
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
			case ArchivePackage.ENTERPRISE_ARCHIVE__VERSION:
				setVersion(VERSION_EDEFAULT);
				return;
			case ArchivePackage.ENTERPRISE_ARCHIVE__FOLDER:
				setFolder(FOLDER_EDEFAULT);
				return;
			case ArchivePackage.ENTERPRISE_ARCHIVE__OWNER_PROJECT_NAME:
				setOwnerProjectName(OWNER_PROJECT_NAME_EDEFAULT);
				return;
			case ArchivePackage.ENTERPRISE_ARCHIVE__FILE_LOCATION:
				setFileLocation(FILE_LOCATION_EDEFAULT);
				return;
			case ArchivePackage.ENTERPRISE_ARCHIVE__INCLUDE_GLOBAL_VARS:
				setIncludeGlobalVars(INCLUDE_GLOBAL_VARS_EDEFAULT);
				return;
			case ArchivePackage.ENTERPRISE_ARCHIVE__BUSINESS_EVENTS_ARCHIVES:
				getBusinessEventsArchives().clear();
				return;
			case ArchivePackage.ENTERPRISE_ARCHIVE__SHARED_ARCHIVES:
				getSharedArchives().clear();
				return;
			case ArchivePackage.ENTERPRISE_ARCHIVE__PROCESS_ARCHIVES:
				getProcessArchives().clear();
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
			case ArchivePackage.ENTERPRISE_ARCHIVE__VERSION:
				return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
			case ArchivePackage.ENTERPRISE_ARCHIVE__FOLDER:
				return FOLDER_EDEFAULT == null ? folder != null : !FOLDER_EDEFAULT.equals(folder);
			case ArchivePackage.ENTERPRISE_ARCHIVE__OWNER_PROJECT_NAME:
				return OWNER_PROJECT_NAME_EDEFAULT == null ? ownerProjectName != null : !OWNER_PROJECT_NAME_EDEFAULT.equals(ownerProjectName);
			case ArchivePackage.ENTERPRISE_ARCHIVE__FILE_LOCATION:
				return FILE_LOCATION_EDEFAULT == null ? fileLocation != null : !FILE_LOCATION_EDEFAULT.equals(fileLocation);
			case ArchivePackage.ENTERPRISE_ARCHIVE__INCLUDE_GLOBAL_VARS:
				return includeGlobalVars != INCLUDE_GLOBAL_VARS_EDEFAULT;
			case ArchivePackage.ENTERPRISE_ARCHIVE__BUSINESS_EVENTS_ARCHIVES:
				return businessEventsArchives != null && !businessEventsArchives.isEmpty();
			case ArchivePackage.ENTERPRISE_ARCHIVE__SHARED_ARCHIVES:
				return sharedArchives != null && !sharedArchives.isEmpty();
			case ArchivePackage.ENTERPRISE_ARCHIVE__PROCESS_ARCHIVES:
				return processArchives != null && !processArchives.isEmpty();
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
		result.append(" (version: ");
		result.append(version);
		result.append(", folder: ");
		result.append(folder);
		result.append(", ownerProjectName: ");
		result.append(ownerProjectName);
		result.append(", fileLocation: ");
		result.append(fileLocation);
		result.append(", includeGlobalVars: ");
		result.append(includeGlobalVars);
		result.append(')');
		return result.toString();
	}

} //EnterpriseArchiveImpl
