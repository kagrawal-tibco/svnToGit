/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.common.configuration.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.studio.common.configuration.ConfigurationPackage;
import com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Enterprise Archive Entry</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.EnterpriseArchiveEntryImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.EnterpriseArchiveEntryImpl#getAuthor <em>Author</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.EnterpriseArchiveEntryImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.EnterpriseArchiveEntryImpl#getVersion <em>Version</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.EnterpriseArchiveEntryImpl#getPath <em>Path</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.EnterpriseArchiveEntryImpl#isIncludeServiceLevelGlobalVars <em>Include Service Level Global Vars</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.EnterpriseArchiveEntryImpl#isDebug <em>Debug</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.EnterpriseArchiveEntryImpl#getTempOutputPath <em>Temp Output Path</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.EnterpriseArchiveEntryImpl#isOverwrite <em>Overwrite</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.EnterpriseArchiveEntryImpl#isDeleteTempFiles <em>Delete Temp Files</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.EnterpriseArchiveEntryImpl#isBuildClassesOnly <em>Build Classes Only</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EnterpriseArchiveEntryImpl extends ProjectConfigurationEntryImpl implements EnterpriseArchiveEntry {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getAuthor() <em>Author</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthor()
	 * @generated
	 * @ordered
	 */
	protected static final String AUTHOR_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getAuthor() <em>Author</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthor()
	 * @generated
	 * @ordered
	 */
	protected String author = AUTHOR_EDEFAULT;

	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected String description = DESCRIPTION_EDEFAULT;

	/**
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected static final int VERSION_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected int version = VERSION_EDEFAULT;

	/**
	 * The default value of the '{@link #getPath() <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPath()
	 * @generated
	 * @ordered
	 */
	protected static final String PATH_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getPath() <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPath()
	 * @generated
	 * @ordered
	 */
	protected String path = PATH_EDEFAULT;

	/**
	 * The default value of the '{@link #isIncludeServiceLevelGlobalVars() <em>Include Service Level Global Vars</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIncludeServiceLevelGlobalVars()
	 * @generated
	 * @ordered
	 */
	protected static final boolean INCLUDE_SERVICE_LEVEL_GLOBAL_VARS_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isIncludeServiceLevelGlobalVars() <em>Include Service Level Global Vars</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIncludeServiceLevelGlobalVars()
	 * @generated
	 * @ordered
	 */
	protected boolean includeServiceLevelGlobalVars = INCLUDE_SERVICE_LEVEL_GLOBAL_VARS_EDEFAULT;

	/**
	 * The default value of the '{@link #isDebug() <em>Debug</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDebug()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DEBUG_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isDebug() <em>Debug</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDebug()
	 * @generated
	 * @ordered
	 */
	protected boolean debug = DEBUG_EDEFAULT;

	/**
	 * The default value of the '{@link #getTempOutputPath() <em>Temp Output Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTempOutputPath()
	 * @generated
	 * @ordered
	 */
	protected static final String TEMP_OUTPUT_PATH_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getTempOutputPath() <em>Temp Output Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTempOutputPath()
	 * @generated
	 * @ordered
	 */
	protected String tempOutputPath = TEMP_OUTPUT_PATH_EDEFAULT;

	/**
	 * The default value of the '{@link #isOverwrite() <em>Overwrite</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isOverwrite()
	 * @generated
	 * @ordered
	 */
	protected static final boolean OVERWRITE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isOverwrite() <em>Overwrite</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isOverwrite()
	 * @generated
	 * @ordered
	 */
	protected boolean overwrite = OVERWRITE_EDEFAULT;

	/**
	 * The default value of the '{@link #isDeleteTempFiles() <em>Delete Temp Files</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeleteTempFiles()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DELETE_TEMP_FILES_EDEFAULT = true;

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
	 * The default value of the '{@link #isBuildClassesOnly() <em>Build Classes Only</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isBuildClassesOnly()
	 * @generated
	 * @ordered
	 */
	protected static final boolean BUILD_CLASSES_ONLY_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isBuildClassesOnly() <em>Build Classes Only</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isBuildClassesOnly()
	 * @generated
	 * @ordered
	 */
	protected boolean buildClassesOnly = BUILD_CLASSES_ONLY_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EnterpriseArchiveEntryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ConfigurationPackage.Literals.ENTERPRISE_ARCHIVE_ENTRY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAuthor(String newAuthor) {
		String oldAuthor = author;
		author = newAuthor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__AUTHOR, oldAuthor, author));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__DESCRIPTION, oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVersion(int newVersion) {
		int oldVersion = version;
		version = newVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__VERSION, oldVersion, version));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPath() {
		return path;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPath(String newPath) {
		String oldPath = path;
		path = newPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__PATH, oldPath, path));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIncludeServiceLevelGlobalVars() {
		return includeServiceLevelGlobalVars;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIncludeServiceLevelGlobalVars(boolean newIncludeServiceLevelGlobalVars) {
		boolean oldIncludeServiceLevelGlobalVars = includeServiceLevelGlobalVars;
		includeServiceLevelGlobalVars = newIncludeServiceLevelGlobalVars;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__INCLUDE_SERVICE_LEVEL_GLOBAL_VARS, oldIncludeServiceLevelGlobalVars, includeServiceLevelGlobalVars));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isDebug() {
		return debug;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDebug(boolean newDebug) {
		boolean oldDebug = debug;
		debug = newDebug;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__DEBUG, oldDebug, debug));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTempOutputPath() {
		return tempOutputPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTempOutputPath(String newTempOutputPath) {
		String oldTempOutputPath = tempOutputPath;
		tempOutputPath = newTempOutputPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__TEMP_OUTPUT_PATH, oldTempOutputPath, tempOutputPath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isOverwrite() {
		return overwrite;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOverwrite(boolean newOverwrite) {
		boolean oldOverwrite = overwrite;
		overwrite = newOverwrite;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__OVERWRITE, oldOverwrite, overwrite));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__DELETE_TEMP_FILES, oldDeleteTempFiles, deleteTempFiles));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isBuildClassesOnly() {
		return buildClassesOnly;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBuildClassesOnly(boolean newBuildClassesOnly) {
		boolean oldBuildClassesOnly = buildClassesOnly;
		buildClassesOnly = newBuildClassesOnly;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__BUILD_CLASSES_ONLY, oldBuildClassesOnly, buildClassesOnly));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__NAME:
				return getName();
			case ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__AUTHOR:
				return getAuthor();
			case ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__DESCRIPTION:
				return getDescription();
			case ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__VERSION:
				return getVersion();
			case ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__PATH:
				return getPath();
			case ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__INCLUDE_SERVICE_LEVEL_GLOBAL_VARS:
				return isIncludeServiceLevelGlobalVars();
			case ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__DEBUG:
				return isDebug();
			case ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__TEMP_OUTPUT_PATH:
				return getTempOutputPath();
			case ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__OVERWRITE:
				return isOverwrite();
			case ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__DELETE_TEMP_FILES:
				return isDeleteTempFiles();
			case ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__BUILD_CLASSES_ONLY:
				return isBuildClassesOnly();
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
			case ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__NAME:
				setName((String)newValue);
				return;
			case ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__AUTHOR:
				setAuthor((String)newValue);
				return;
			case ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__VERSION:
				setVersion((Integer)newValue);
				return;
			case ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__PATH:
				setPath((String)newValue);
				return;
			case ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__INCLUDE_SERVICE_LEVEL_GLOBAL_VARS:
				setIncludeServiceLevelGlobalVars((Boolean)newValue);
				return;
			case ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__DEBUG:
				setDebug((Boolean)newValue);
				return;
			case ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__TEMP_OUTPUT_PATH:
				setTempOutputPath((String)newValue);
				return;
			case ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__OVERWRITE:
				setOverwrite((Boolean)newValue);
				return;
			case ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__DELETE_TEMP_FILES:
				setDeleteTempFiles((Boolean)newValue);
				return;
			case ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__BUILD_CLASSES_ONLY:
				setBuildClassesOnly((Boolean)newValue);
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
			case ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__AUTHOR:
				setAuthor(AUTHOR_EDEFAULT);
				return;
			case ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__VERSION:
				setVersion(VERSION_EDEFAULT);
				return;
			case ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__PATH:
				setPath(PATH_EDEFAULT);
				return;
			case ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__INCLUDE_SERVICE_LEVEL_GLOBAL_VARS:
				setIncludeServiceLevelGlobalVars(INCLUDE_SERVICE_LEVEL_GLOBAL_VARS_EDEFAULT);
				return;
			case ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__DEBUG:
				setDebug(DEBUG_EDEFAULT);
				return;
			case ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__TEMP_OUTPUT_PATH:
				setTempOutputPath(TEMP_OUTPUT_PATH_EDEFAULT);
				return;
			case ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__OVERWRITE:
				setOverwrite(OVERWRITE_EDEFAULT);
				return;
			case ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__DELETE_TEMP_FILES:
				setDeleteTempFiles(DELETE_TEMP_FILES_EDEFAULT);
				return;
			case ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__BUILD_CLASSES_ONLY:
				setBuildClassesOnly(BUILD_CLASSES_ONLY_EDEFAULT);
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
			case ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__AUTHOR:
				return AUTHOR_EDEFAULT == null ? author != null : !AUTHOR_EDEFAULT.equals(author);
			case ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__VERSION:
				return version != VERSION_EDEFAULT;
			case ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__PATH:
				return PATH_EDEFAULT == null ? path != null : !PATH_EDEFAULT.equals(path);
			case ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__INCLUDE_SERVICE_LEVEL_GLOBAL_VARS:
				return includeServiceLevelGlobalVars != INCLUDE_SERVICE_LEVEL_GLOBAL_VARS_EDEFAULT;
			case ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__DEBUG:
				return debug != DEBUG_EDEFAULT;
			case ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__TEMP_OUTPUT_PATH:
				return TEMP_OUTPUT_PATH_EDEFAULT == null ? tempOutputPath != null : !TEMP_OUTPUT_PATH_EDEFAULT.equals(tempOutputPath);
			case ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__OVERWRITE:
				return overwrite != OVERWRITE_EDEFAULT;
			case ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__DELETE_TEMP_FILES:
				return deleteTempFiles != DELETE_TEMP_FILES_EDEFAULT;
			case ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY__BUILD_CLASSES_ONLY:
				return buildClassesOnly != BUILD_CLASSES_ONLY_EDEFAULT;
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
		result.append(" (name: ");
		result.append(name);
		result.append(", author: ");
		result.append(author);
		result.append(", description: ");
		result.append(description);
		result.append(", version: ");
		result.append(version);
		result.append(", path: ");
		result.append(path);
		result.append(", includeServiceLevelGlobalVars: ");
		result.append(includeServiceLevelGlobalVars);
		result.append(", debug: ");
		result.append(debug);
		result.append(", tempOutputPath: ");
		result.append(tempOutputPath);
		result.append(", overwrite: ");
		result.append(overwrite);
		result.append(", deleteTempFiles: ");
		result.append(deleteTempFiles);
		result.append(", buildClassesOnly: ");
		result.append(buildClassesOnly);
		result.append(')');
		return result.toString();
	}

} //EnterpriseArchiveEntryImpl
