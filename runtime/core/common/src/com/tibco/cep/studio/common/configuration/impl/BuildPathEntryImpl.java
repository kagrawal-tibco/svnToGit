/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.common.configuration.impl;

import java.io.File;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.studio.common.configuration.BuildPathEntry;
import com.tibco.cep.studio.common.configuration.ConfigurationPackage;
import com.tibco.cep.studio.common.configuration.LIBRARY_PATH_TYPE;


/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Build Path Entry</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.BuildPathEntryImpl#getEntryType <em>Entry Type</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.BuildPathEntryImpl#getPath <em>Path</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.BuildPathEntryImpl#getTimestamp <em>Timestamp</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.BuildPathEntryImpl#isVar <em>Var</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.BuildPathEntryImpl#isReadOnly <em>Read Only</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.BuildPathEntryImpl#isDeprecated <em>Deprecated</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.BuildPathEntryImpl#getResolvedPath <em>Resolved Path</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BuildPathEntryImpl extends ProjectConfigurationEntryImpl implements BuildPathEntry {
	/**
	 * The default value of the '{@link #getEntryType() <em>Entry Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntryType()
	 * @generated
	 * @ordered
	 */
	protected static final LIBRARY_PATH_TYPE ENTRY_TYPE_EDEFAULT = LIBRARY_PATH_TYPE.PROJECT_LIBRARY;

	/**
	 * The cached value of the '{@link #getEntryType() <em>Entry Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntryType()
	 * @generated
	 * @ordered
	 */
	protected LIBRARY_PATH_TYPE entryType = ENTRY_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getPath() <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPath()
	 * @generated
	 * @ordered
	 */
	protected static final String PATH_EDEFAULT = null;

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
	 * The default value of the '{@link #getTimestamp() <em>Timestamp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimestamp()
	 * @generated
	 * @ordered
	 */
	protected static final long TIMESTAMP_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getTimestamp() <em>Timestamp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimestamp()
	 * @generated
	 * @ordered
	 */
	protected long timestamp = TIMESTAMP_EDEFAULT;

	/**
	 * The default value of the '{@link #isVar() <em>Var</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isVar()
	 * @generated
	 * @ordered
	 */
	protected static final boolean VAR_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isVar() <em>Var</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isVar()
	 * @generated
	 * @ordered
	 */
	protected boolean var = VAR_EDEFAULT;

	/**
	 * The default value of the '{@link #isReadOnly() <em>Read Only</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReadOnly()
	 * @generated
	 * @ordered
	 */
	protected static final boolean READ_ONLY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isReadOnly() <em>Read Only</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReadOnly()
	 * @generated
	 * @ordered
	 */
	protected boolean readOnly = READ_ONLY_EDEFAULT;

	/**
	 * The default value of the '{@link #isDeprecated() <em>Deprecated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeprecated()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DEPRECATED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDeprecated() <em>Deprecated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeprecated()
	 * @generated
	 * @ordered
	 */
	protected boolean deprecated = DEPRECATED_EDEFAULT;

	/**
	 * The default value of the '{@link #getResolvedPath() <em>Resolved Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResolvedPath()
	 * @generated
	 * @ordered
	 */
	protected static final String RESOLVED_PATH_EDEFAULT = null;

	/**
	 * in case of user variable used it cache the resolved path, else is same as path
	 * @generated 
	 * 
	 */
	protected String resolvedPath = RESOLVED_PATH_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BuildPathEntryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ConfigurationPackage.Literals.BUILD_PATH_ENTRY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LIBRARY_PATH_TYPE getEntryType() {
		return entryType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEntryType(LIBRARY_PATH_TYPE newEntryType) {
		LIBRARY_PATH_TYPE oldEntryType = entryType;
		entryType = newEntryType == null ? ENTRY_TYPE_EDEFAULT : newEntryType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.BUILD_PATH_ENTRY__ENTRY_TYPE, oldEntryType, entryType));
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
	 * @generated NOT
	 */
	public String getPath(boolean resolve) {
		if(resolve){
			String path = getResolvedPath();
			if(path == null)
				path = getPath();
			if(path!=null){
				File libRefFile = new File(path);
				/**
				 * if the path variable is not resolved then resolve by looking at system properties
				 */
				if(!libRefFile.exists() && isVar()) {
					path = path.replace("\\", "/");
					String[] segments = path.split("/");
					String varName = segments[0];
					String varValue = System.getProperty(varName,null);
					if(varValue == null){
						return getPath();
						// throw new IllegalStateException(String.format("Path variable %s not found.", varName));
					}
					String varpath = path.replace(varName, varValue);
					// check if the varPath is valid
					File varFile = new File(varpath);
					if(!varFile.exists()){
						return getPath();
						//throw new IllegalStateException(String.format("Path variable %s folder does not exist.", varpath));
					}
					path = varpath;
				}
			}
			return path;
		}
		return getPath();
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
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.BUILD_PATH_ENTRY__PATH, oldPath, path));
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setResolvedPath(String newResolvedPath) {
		String oldResolvedPath = resolvedPath;
		resolvedPath = newResolvedPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.BUILD_PATH_ENTRY__RESOLVED_PATH, oldResolvedPath, resolvedPath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTimestamp(long newTimestamp) {
		long oldTimestamp = timestamp;
		timestamp = newTimestamp;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.BUILD_PATH_ENTRY__TIMESTAMP, oldTimestamp, timestamp));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isVar() {
		return var;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVar(boolean newVar) {
		boolean oldVar = var;
		var = newVar;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.BUILD_PATH_ENTRY__VAR, oldVar, var));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isReadOnly() {
		return readOnly;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReadOnly(boolean newReadOnly) {
		boolean oldReadOnly = readOnly;
		readOnly = newReadOnly;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.BUILD_PATH_ENTRY__READ_ONLY, oldReadOnly, readOnly));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isDeprecated() {
		return deprecated;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDeprecated(boolean newDeprecated) {
		boolean oldDeprecated = deprecated;
		deprecated = newDeprecated;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.BUILD_PATH_ENTRY__DEPRECATED, oldDeprecated, deprecated));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getResolvedPath() {
		return resolvedPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ConfigurationPackage.BUILD_PATH_ENTRY__ENTRY_TYPE:
				return getEntryType();
			case ConfigurationPackage.BUILD_PATH_ENTRY__PATH:
				return getPath();
			case ConfigurationPackage.BUILD_PATH_ENTRY__TIMESTAMP:
				return getTimestamp();
			case ConfigurationPackage.BUILD_PATH_ENTRY__VAR:
				return isVar();
			case ConfigurationPackage.BUILD_PATH_ENTRY__READ_ONLY:
				return isReadOnly();
			case ConfigurationPackage.BUILD_PATH_ENTRY__DEPRECATED:
				return isDeprecated();
			case ConfigurationPackage.BUILD_PATH_ENTRY__RESOLVED_PATH:
				return getResolvedPath();
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
			case ConfigurationPackage.BUILD_PATH_ENTRY__ENTRY_TYPE:
				setEntryType((LIBRARY_PATH_TYPE)newValue);
				return;
			case ConfigurationPackage.BUILD_PATH_ENTRY__PATH:
				setPath((String)newValue);
				return;
			case ConfigurationPackage.BUILD_PATH_ENTRY__TIMESTAMP:
				setTimestamp((Long)newValue);
				return;
			case ConfigurationPackage.BUILD_PATH_ENTRY__VAR:
				setVar((Boolean)newValue);
				return;
			case ConfigurationPackage.BUILD_PATH_ENTRY__READ_ONLY:
				setReadOnly((Boolean)newValue);
				return;
			case ConfigurationPackage.BUILD_PATH_ENTRY__DEPRECATED:
				setDeprecated((Boolean)newValue);
				return;
			case ConfigurationPackage.BUILD_PATH_ENTRY__RESOLVED_PATH:
				setResolvedPath((String)newValue);
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
			case ConfigurationPackage.BUILD_PATH_ENTRY__ENTRY_TYPE:
				setEntryType(ENTRY_TYPE_EDEFAULT);
				return;
			case ConfigurationPackage.BUILD_PATH_ENTRY__PATH:
				setPath(PATH_EDEFAULT);
				return;
			case ConfigurationPackage.BUILD_PATH_ENTRY__TIMESTAMP:
				setTimestamp(TIMESTAMP_EDEFAULT);
				return;
			case ConfigurationPackage.BUILD_PATH_ENTRY__VAR:
				setVar(VAR_EDEFAULT);
				return;
			case ConfigurationPackage.BUILD_PATH_ENTRY__READ_ONLY:
				setReadOnly(READ_ONLY_EDEFAULT);
				return;
			case ConfigurationPackage.BUILD_PATH_ENTRY__DEPRECATED:
				setDeprecated(DEPRECATED_EDEFAULT);
				return;
			case ConfigurationPackage.BUILD_PATH_ENTRY__RESOLVED_PATH:
				setResolvedPath(RESOLVED_PATH_EDEFAULT);
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
			case ConfigurationPackage.BUILD_PATH_ENTRY__ENTRY_TYPE:
				return entryType != ENTRY_TYPE_EDEFAULT;
			case ConfigurationPackage.BUILD_PATH_ENTRY__PATH:
				return PATH_EDEFAULT == null ? path != null : !PATH_EDEFAULT.equals(path);
			case ConfigurationPackage.BUILD_PATH_ENTRY__TIMESTAMP:
				return timestamp != TIMESTAMP_EDEFAULT;
			case ConfigurationPackage.BUILD_PATH_ENTRY__VAR:
				return var != VAR_EDEFAULT;
			case ConfigurationPackage.BUILD_PATH_ENTRY__READ_ONLY:
				return readOnly != READ_ONLY_EDEFAULT;
			case ConfigurationPackage.BUILD_PATH_ENTRY__DEPRECATED:
				return deprecated != DEPRECATED_EDEFAULT;
			case ConfigurationPackage.BUILD_PATH_ENTRY__RESOLVED_PATH:
				return RESOLVED_PATH_EDEFAULT == null ? resolvedPath != null : !RESOLVED_PATH_EDEFAULT.equals(resolvedPath);
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
		result.append(" (entryType: ");
		result.append(entryType);
		result.append(", path: ");
		result.append(path);
		result.append(", timestamp: ");
		result.append(timestamp);
		result.append(", var: ");
		result.append(var);
		result.append(", readOnly: ");
		result.append(readOnly);
		result.append(", deprecated: ");
		result.append(deprecated);
		result.append(", resolvedPath: ");
		result.append(resolvedPath);
		result.append(')');
		return result.toString();
	}

} //BuildPathEntryImpl
