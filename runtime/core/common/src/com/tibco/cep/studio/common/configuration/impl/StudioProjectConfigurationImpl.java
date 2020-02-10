/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.common.configuration.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.studio.common.configuration.BpmnProcessSettings;
import com.tibco.cep.studio.common.configuration.BuildPathEntry;
import com.tibco.cep.studio.common.configuration.ConfigurationPackage;
import com.tibco.cep.studio.common.configuration.CoreJavaLibEntry;
import com.tibco.cep.studio.common.configuration.CustomFunctionLibEntry;
import com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry;
import com.tibco.cep.studio.common.configuration.JavaClasspathEntry;
import com.tibco.cep.studio.common.configuration.JavaSourceFolderEntry;
import com.tibco.cep.studio.common.configuration.LIBRARY_PATH_TYPE;
import com.tibco.cep.studio.common.configuration.ProjectLibraryEntry;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.common.configuration.ThirdPartyLibEntry;
import com.tibco.cep.studio.common.configuration.XPATH_VERSION;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Studio Project Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.StudioProjectConfigurationImpl#getThirdpartyLibEntries <em>Thirdparty Lib Entries</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.StudioProjectConfigurationImpl#getCoreInternalLibEntries <em>Core Internal Lib Entries</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.StudioProjectConfigurationImpl#getCustomFunctionLibEntries <em>Custom Function Lib Entries</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.StudioProjectConfigurationImpl#getProjectLibEntries <em>Project Lib Entries</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.StudioProjectConfigurationImpl#getJavaClasspathEntries <em>Java Classpath Entries</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.StudioProjectConfigurationImpl#getJavaSourceFolderEntries <em>Java Source Folder Entries</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.StudioProjectConfigurationImpl#getEnterpriseArchiveConfiguration <em>Enterprise Archive Configuration</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.StudioProjectConfigurationImpl#getBpmnProcessSettings <em>Bpmn Process Settings</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.StudioProjectConfigurationImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.StudioProjectConfigurationImpl#getVersion <em>Version</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.StudioProjectConfigurationImpl#getBuild <em>Build</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.StudioProjectConfigurationImpl#getFileTimeStamp <em>File Time Stamp</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.StudioProjectConfigurationImpl#getXpathVersion <em>Xpath Version</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class StudioProjectConfigurationImpl extends EObjectImpl implements StudioProjectConfiguration {
	/**
	 * The cached value of the '{@link #getThirdpartyLibEntries() <em>Thirdparty Lib Entries</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getThirdpartyLibEntries()
	 * @generated
	 * @ordered
	 */
	protected EList<ThirdPartyLibEntry> thirdpartyLibEntries;

	/**
	 * The cached value of the '{@link #getCoreInternalLibEntries() <em>Core Internal Lib Entries</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCoreInternalLibEntries()
	 * @generated
	 * @ordered
	 */
	protected EList<CoreJavaLibEntry> coreInternalLibEntries;

	/**
	 * The cached value of the '{@link #getCustomFunctionLibEntries() <em>Custom Function Lib Entries</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCustomFunctionLibEntries()
	 * @generated
	 * @ordered
	 */
	protected EList<CustomFunctionLibEntry> customFunctionLibEntries;

	/**
	 * The cached value of the '{@link #getProjectLibEntries() <em>Project Lib Entries</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProjectLibEntries()
	 * @generated
	 * @ordered
	 */
	protected EList<ProjectLibraryEntry> projectLibEntries;

	/**
	 * The cached value of the '{@link #getJavaClasspathEntries() <em>Java Classpath Entries</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJavaClasspathEntries()
	 * @generated
	 * @ordered
	 */
	protected EList<JavaClasspathEntry> javaClasspathEntries;

	/**
	 * The cached value of the '{@link #getJavaSourceFolderEntries() <em>Java Source Folder Entries</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJavaSourceFolderEntries()
	 * @generated
	 * @ordered
	 */
	protected EList<JavaSourceFolderEntry> javaSourceFolderEntries;

	/**
	 * The cached value of the '{@link #getEnterpriseArchiveConfiguration() <em>Enterprise Archive Configuration</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEnterpriseArchiveConfiguration()
	 * @generated
	 * @ordered
	 */
	protected EnterpriseArchiveEntry enterpriseArchiveConfiguration;

	/**
	 * The cached value of the '{@link #getBpmnProcessSettings() <em>Bpmn Process Settings</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBpmnProcessSettings()
	 * @generated
	 * @ordered
	 */
	protected BpmnProcessSettings bpmnProcessSettings;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

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
	 * The default value of the '{@link #getBuild() <em>Build</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBuild()
	 * @generated
	 * @ordered
	 */
	protected static final String BUILD_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBuild() <em>Build</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBuild()
	 * @generated
	 * @ordered
	 */
	protected String build = BUILD_EDEFAULT;

	/**
	 * The default value of the '{@link #getFileTimeStamp() <em>File Time Stamp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFileTimeStamp()
	 * @generated
	 * @ordered
	 */
	protected static final long FILE_TIME_STAMP_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getFileTimeStamp() <em>File Time Stamp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFileTimeStamp()
	 * @generated
	 * @ordered
	 */
	protected long fileTimeStamp = FILE_TIME_STAMP_EDEFAULT;

	/**
	 * The default value of the '{@link #getXpathVersion() <em>Xpath Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXpathVersion()
	 * @generated
	 * @ordered
	 */
	protected static final XPATH_VERSION XPATH_VERSION_EDEFAULT = XPATH_VERSION.XPATH_10;

	/**
	 * The cached value of the '{@link #getXpathVersion() <em>Xpath Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXpathVersion()
	 * @generated
	 * @ordered
	 */
	protected XPATH_VERSION xpathVersion = XPATH_VERSION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StudioProjectConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ConfigurationPackage.Literals.STUDIO_PROJECT_CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ThirdPartyLibEntry> getThirdpartyLibEntries() {
		if (thirdpartyLibEntries == null) {
			thirdpartyLibEntries = new EObjectContainmentEList<ThirdPartyLibEntry>(ThirdPartyLibEntry.class, this, ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__THIRDPARTY_LIB_ENTRIES);
		}
		return thirdpartyLibEntries;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<CoreJavaLibEntry> getCoreInternalLibEntries() {
		if (coreInternalLibEntries == null) {
			coreInternalLibEntries = new EObjectContainmentEList<CoreJavaLibEntry>(CoreJavaLibEntry.class, this, ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__CORE_INTERNAL_LIB_ENTRIES);
		}
		return coreInternalLibEntries;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<CustomFunctionLibEntry> getCustomFunctionLibEntries() {
		if (customFunctionLibEntries == null) {
			customFunctionLibEntries = new EObjectContainmentEList<CustomFunctionLibEntry>(CustomFunctionLibEntry.class, this, ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__CUSTOM_FUNCTION_LIB_ENTRIES);
		}
		return customFunctionLibEntries;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ProjectLibraryEntry> getProjectLibEntries() {
		if (projectLibEntries == null) {
			projectLibEntries = new EObjectContainmentEList<ProjectLibraryEntry>(ProjectLibraryEntry.class, this, ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__PROJECT_LIB_ENTRIES);
		}
		return projectLibEntries;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<JavaClasspathEntry> getJavaClasspathEntries() {
		if (javaClasspathEntries == null) {
			javaClasspathEntries = new EObjectContainmentEList<JavaClasspathEntry>(JavaClasspathEntry.class, this, ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__JAVA_CLASSPATH_ENTRIES);
		}
		return javaClasspathEntries;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<JavaSourceFolderEntry> getJavaSourceFolderEntries() {
		if (javaSourceFolderEntries == null) {
			javaSourceFolderEntries = new EObjectContainmentEList<JavaSourceFolderEntry>(JavaSourceFolderEntry.class, this, ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__JAVA_SOURCE_FOLDER_ENTRIES);
		}
		return javaSourceFolderEntries;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EnterpriseArchiveEntry getEnterpriseArchiveConfiguration() {
		return enterpriseArchiveConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEnterpriseArchiveConfiguration(EnterpriseArchiveEntry newEnterpriseArchiveConfiguration, NotificationChain msgs) {
		EnterpriseArchiveEntry oldEnterpriseArchiveConfiguration = enterpriseArchiveConfiguration;
		enterpriseArchiveConfiguration = newEnterpriseArchiveConfiguration;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__ENTERPRISE_ARCHIVE_CONFIGURATION, oldEnterpriseArchiveConfiguration, newEnterpriseArchiveConfiguration);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnterpriseArchiveConfiguration(EnterpriseArchiveEntry newEnterpriseArchiveConfiguration) {
		if (newEnterpriseArchiveConfiguration != enterpriseArchiveConfiguration) {
			NotificationChain msgs = null;
			if (enterpriseArchiveConfiguration != null)
				msgs = ((InternalEObject)enterpriseArchiveConfiguration).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__ENTERPRISE_ARCHIVE_CONFIGURATION, null, msgs);
			if (newEnterpriseArchiveConfiguration != null)
				msgs = ((InternalEObject)newEnterpriseArchiveConfiguration).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__ENTERPRISE_ARCHIVE_CONFIGURATION, null, msgs);
			msgs = basicSetEnterpriseArchiveConfiguration(newEnterpriseArchiveConfiguration, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__ENTERPRISE_ARCHIVE_CONFIGURATION, newEnterpriseArchiveConfiguration, newEnterpriseArchiveConfiguration));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__NAME, oldName, name));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__VERSION, oldVersion, version));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getBuild() {
		return build;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBuild(String newBuild) {
		String oldBuild = build;
		build = newBuild;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__BUILD, oldBuild, build));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getFileTimeStamp() {
		return fileTimeStamp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFileTimeStamp(long newFileTimeStamp) {
		long oldFileTimeStamp = fileTimeStamp;
		fileTimeStamp = newFileTimeStamp;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__FILE_TIME_STAMP, oldFileTimeStamp, fileTimeStamp));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XPATH_VERSION getXpathVersion() {
		return xpathVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setXpathVersion(XPATH_VERSION newXpathVersion) {
		XPATH_VERSION oldXpathVersion = xpathVersion;
		xpathVersion = newXpathVersion == null ? XPATH_VERSION_EDEFAULT : newXpathVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__XPATH_VERSION, oldXpathVersion, xpathVersion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BpmnProcessSettings getBpmnProcessSettings() {
		return bpmnProcessSettings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBpmnProcessSettings(BpmnProcessSettings newBpmnProcessSettings, NotificationChain msgs) {
		BpmnProcessSettings oldBpmnProcessSettings = bpmnProcessSettings;
		bpmnProcessSettings = newBpmnProcessSettings;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__BPMN_PROCESS_SETTINGS, oldBpmnProcessSettings, newBpmnProcessSettings);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBpmnProcessSettings(BpmnProcessSettings newBpmnProcessSettings) {
		if (newBpmnProcessSettings != bpmnProcessSettings) {
			NotificationChain msgs = null;
			if (bpmnProcessSettings != null)
				msgs = ((InternalEObject)bpmnProcessSettings).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__BPMN_PROCESS_SETTINGS, null, msgs);
			if (newBpmnProcessSettings != null)
				msgs = ((InternalEObject)newBpmnProcessSettings).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__BPMN_PROCESS_SETTINGS, null, msgs);
			msgs = basicSetBpmnProcessSettings(newBpmnProcessSettings, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__BPMN_PROCESS_SETTINGS, newBpmnProcessSettings, newBpmnProcessSettings));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@SuppressWarnings("rawtypes")
	public EList<BuildPathEntry> getEntriesByType(LIBRARY_PATH_TYPE type) {
		EList elist = null;
		switch(type.getValue()) {
		case LIBRARY_PATH_TYPE.CORE_INTERNAL_LIBRARY_VALUE:
			elist = getCoreInternalLibEntries();
			break;
		case LIBRARY_PATH_TYPE.CUSTOM_FUNCTION_LIBRARY_VALUE:
			elist = getCustomFunctionLibEntries();
			break;
		case LIBRARY_PATH_TYPE.PROJECT_LIBRARY_VALUE:
			elist = getProjectLibEntries();
			break;
		case LIBRARY_PATH_TYPE.THIRD_PARTY_LIBRARY_VALUE:
			elist = getThirdpartyLibEntries();
			break;
		case LIBRARY_PATH_TYPE.JAVA_CLASSPATH_ENTRY_VALUE:
			elist = getJavaClasspathEntries();
			break;
		case LIBRARY_PATH_TYPE.JAVA_SOURCE_FOLDER_VALUE:
			elist = getJavaSourceFolderEntries();
			break;
		}
		EDataTypeEList<BuildPathEntry> list = new EDataTypeEList<BuildPathEntry>(BuildPathEntry.class, this,ConfigurationPackage.BUILD_PATH_ENTRY);
		if(elist != null) {
			for(Object entry: elist) {
				if(entry instanceof BuildPathEntry) {
					BuildPathEntry bpe = (BuildPathEntry) entry;
					list.add(bpe);
				}
			}
			return list;
		}
		return list;
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public boolean removeEntriesByType(BuildPathEntry element, LIBRARY_PATH_TYPE type) {
		switch(type.getValue()) {
		case LIBRARY_PATH_TYPE.CORE_INTERNAL_LIBRARY_VALUE:
			return getCoreInternalLibEntries().remove(element);
		case LIBRARY_PATH_TYPE.CUSTOM_FUNCTION_LIBRARY_VALUE:
			return getCustomFunctionLibEntries().remove(element);
		case LIBRARY_PATH_TYPE.PROJECT_LIBRARY_VALUE:
			return getProjectLibEntries().remove(element);
		case LIBRARY_PATH_TYPE.THIRD_PARTY_LIBRARY_VALUE:
			return getThirdpartyLibEntries().remove(element);
		case LIBRARY_PATH_TYPE.JAVA_CLASSPATH_ENTRY_VALUE:
			return getJavaClasspathEntries().remove(element);
		case LIBRARY_PATH_TYPE.JAVA_SOURCE_FOLDER_VALUE:
			return getJavaSourceFolderEntries().remove(element);	
		default:
			return false;
		}
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public int getIndexOfEntryByType(BuildPathEntry element, LIBRARY_PATH_TYPE type) {
		switch(type.getValue()) {
		case LIBRARY_PATH_TYPE.CORE_INTERNAL_LIBRARY_VALUE:
			return getCoreInternalLibEntries().indexOf(element);
		case LIBRARY_PATH_TYPE.CUSTOM_FUNCTION_LIBRARY_VALUE:
			return getCustomFunctionLibEntries().indexOf(element);
		case LIBRARY_PATH_TYPE.PROJECT_LIBRARY_VALUE:
			return getProjectLibEntries().indexOf(element);
		case LIBRARY_PATH_TYPE.THIRD_PARTY_LIBRARY_VALUE:
			return getThirdpartyLibEntries().indexOf(element);
		case LIBRARY_PATH_TYPE.JAVA_CLASSPATH_ENTRY_VALUE:
			return getJavaClasspathEntries().indexOf(element);
		case LIBRARY_PATH_TYPE.JAVA_SOURCE_FOLDER_VALUE:
			return getJavaSourceFolderEntries().indexOf(element);	
		default:
			return -1;
		}
	}

	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__THIRDPARTY_LIB_ENTRIES:
				return ((InternalEList<?>)getThirdpartyLibEntries()).basicRemove(otherEnd, msgs);
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__CORE_INTERNAL_LIB_ENTRIES:
				return ((InternalEList<?>)getCoreInternalLibEntries()).basicRemove(otherEnd, msgs);
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__CUSTOM_FUNCTION_LIB_ENTRIES:
				return ((InternalEList<?>)getCustomFunctionLibEntries()).basicRemove(otherEnd, msgs);
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__PROJECT_LIB_ENTRIES:
				return ((InternalEList<?>)getProjectLibEntries()).basicRemove(otherEnd, msgs);
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__JAVA_CLASSPATH_ENTRIES:
				return ((InternalEList<?>)getJavaClasspathEntries()).basicRemove(otherEnd, msgs);
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__JAVA_SOURCE_FOLDER_ENTRIES:
				return ((InternalEList<?>)getJavaSourceFolderEntries()).basicRemove(otherEnd, msgs);
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__ENTERPRISE_ARCHIVE_CONFIGURATION:
				return basicSetEnterpriseArchiveConfiguration(null, msgs);
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__BPMN_PROCESS_SETTINGS:
				return basicSetBpmnProcessSettings(null, msgs);
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
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__THIRDPARTY_LIB_ENTRIES:
				return getThirdpartyLibEntries();
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__CORE_INTERNAL_LIB_ENTRIES:
				return getCoreInternalLibEntries();
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__CUSTOM_FUNCTION_LIB_ENTRIES:
				return getCustomFunctionLibEntries();
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__PROJECT_LIB_ENTRIES:
				return getProjectLibEntries();
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__JAVA_CLASSPATH_ENTRIES:
				return getJavaClasspathEntries();
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__JAVA_SOURCE_FOLDER_ENTRIES:
				return getJavaSourceFolderEntries();
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__ENTERPRISE_ARCHIVE_CONFIGURATION:
				return getEnterpriseArchiveConfiguration();
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__BPMN_PROCESS_SETTINGS:
				return getBpmnProcessSettings();
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__NAME:
				return getName();
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__VERSION:
				return getVersion();
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__BUILD:
				return getBuild();
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__FILE_TIME_STAMP:
				return getFileTimeStamp();
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__XPATH_VERSION:
				return getXpathVersion();
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
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__THIRDPARTY_LIB_ENTRIES:
				getThirdpartyLibEntries().clear();
				getThirdpartyLibEntries().addAll((Collection<? extends ThirdPartyLibEntry>)newValue);
				return;
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__CORE_INTERNAL_LIB_ENTRIES:
				getCoreInternalLibEntries().clear();
				getCoreInternalLibEntries().addAll((Collection<? extends CoreJavaLibEntry>)newValue);
				return;
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__CUSTOM_FUNCTION_LIB_ENTRIES:
				getCustomFunctionLibEntries().clear();
				getCustomFunctionLibEntries().addAll((Collection<? extends CustomFunctionLibEntry>)newValue);
				return;
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__PROJECT_LIB_ENTRIES:
				getProjectLibEntries().clear();
				getProjectLibEntries().addAll((Collection<? extends ProjectLibraryEntry>)newValue);
				return;
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__JAVA_CLASSPATH_ENTRIES:
				getJavaClasspathEntries().clear();
				getJavaClasspathEntries().addAll((Collection<? extends JavaClasspathEntry>)newValue);
				return;
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__JAVA_SOURCE_FOLDER_ENTRIES:
				getJavaSourceFolderEntries().clear();
				getJavaSourceFolderEntries().addAll((Collection<? extends JavaSourceFolderEntry>)newValue);
				return;
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__ENTERPRISE_ARCHIVE_CONFIGURATION:
				setEnterpriseArchiveConfiguration((EnterpriseArchiveEntry)newValue);
				return;
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__BPMN_PROCESS_SETTINGS:
				setBpmnProcessSettings((BpmnProcessSettings)newValue);
				return;
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__NAME:
				setName((String)newValue);
				return;
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__VERSION:
				setVersion((String)newValue);
				return;
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__BUILD:
				setBuild((String)newValue);
				return;
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__FILE_TIME_STAMP:
				setFileTimeStamp((Long)newValue);
				return;
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__XPATH_VERSION:
				setXpathVersion((XPATH_VERSION)newValue);
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
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__THIRDPARTY_LIB_ENTRIES:
				getThirdpartyLibEntries().clear();
				return;
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__CORE_INTERNAL_LIB_ENTRIES:
				getCoreInternalLibEntries().clear();
				return;
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__CUSTOM_FUNCTION_LIB_ENTRIES:
				getCustomFunctionLibEntries().clear();
				return;
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__PROJECT_LIB_ENTRIES:
				getProjectLibEntries().clear();
				return;
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__JAVA_CLASSPATH_ENTRIES:
				getJavaClasspathEntries().clear();
				return;
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__JAVA_SOURCE_FOLDER_ENTRIES:
				getJavaSourceFolderEntries().clear();
				return;
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__ENTERPRISE_ARCHIVE_CONFIGURATION:
				setEnterpriseArchiveConfiguration((EnterpriseArchiveEntry)null);
				return;
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__BPMN_PROCESS_SETTINGS:
				setBpmnProcessSettings((BpmnProcessSettings)null);
				return;
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__VERSION:
				setVersion(VERSION_EDEFAULT);
				return;
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__BUILD:
				setBuild(BUILD_EDEFAULT);
				return;
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__FILE_TIME_STAMP:
				setFileTimeStamp(FILE_TIME_STAMP_EDEFAULT);
				return;
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__XPATH_VERSION:
				setXpathVersion(XPATH_VERSION_EDEFAULT);
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
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__THIRDPARTY_LIB_ENTRIES:
				return thirdpartyLibEntries != null && !thirdpartyLibEntries.isEmpty();
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__CORE_INTERNAL_LIB_ENTRIES:
				return coreInternalLibEntries != null && !coreInternalLibEntries.isEmpty();
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__CUSTOM_FUNCTION_LIB_ENTRIES:
				return customFunctionLibEntries != null && !customFunctionLibEntries.isEmpty();
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__PROJECT_LIB_ENTRIES:
				return projectLibEntries != null && !projectLibEntries.isEmpty();
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__JAVA_CLASSPATH_ENTRIES:
				return javaClasspathEntries != null && !javaClasspathEntries.isEmpty();
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__JAVA_SOURCE_FOLDER_ENTRIES:
				return javaSourceFolderEntries != null && !javaSourceFolderEntries.isEmpty();
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__ENTERPRISE_ARCHIVE_CONFIGURATION:
				return enterpriseArchiveConfiguration != null;
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__BPMN_PROCESS_SETTINGS:
				return bpmnProcessSettings != null;
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__VERSION:
				return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__BUILD:
				return BUILD_EDEFAULT == null ? build != null : !BUILD_EDEFAULT.equals(build);
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__FILE_TIME_STAMP:
				return fileTimeStamp != FILE_TIME_STAMP_EDEFAULT;
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION__XPATH_VERSION:
				return xpathVersion != XPATH_VERSION_EDEFAULT;
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
		result.append(", version: ");
		result.append(version);
		result.append(", build: ");
		result.append(build);
		result.append(", fileTimeStamp: ");
		result.append(fileTimeStamp);
		result.append(", xpathVersion: ");
		result.append(xpathVersion);
		result.append(')');
		return result.toString();
	}

} //StudioProjectConfigurationImpl
