/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.archive.impl;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.designtime.core.model.archive.AdvancedEntitySetting;
import com.tibco.cep.designtime.core.model.archive.ArchiveFactory;
import com.tibco.cep.designtime.core.model.archive.ArchivePackage;
import com.tibco.cep.designtime.core.model.archive.BE_ARCHIVE_TYPE;
import com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource;
import com.tibco.cep.designtime.core.model.archive.CACHE_CONFIG_TYPE;
import com.tibco.cep.designtime.core.model.archive.InputDestination;
import com.tibco.cep.designtime.core.model.archive.OBJECT_MGMT_TYPE;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Business Events Archive Resource</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.impl.BusinessEventsArchiveResourceImpl#getArchiveType <em>Archive Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.impl.BusinessEventsArchiveResourceImpl#isAllRuleSets <em>All Rule Sets</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.impl.BusinessEventsArchiveResourceImpl#isAllDestinations <em>All Destinations</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.impl.BusinessEventsArchiveResourceImpl#getIncludedRuleSets <em>Included Rule Sets</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.impl.BusinessEventsArchiveResourceImpl#getStartupActions <em>Startup Actions</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.impl.BusinessEventsArchiveResourceImpl#getShutdownActions <em>Shutdown Actions</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.impl.BusinessEventsArchiveResourceImpl#getInputDestinations <em>Input Destinations</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.impl.BusinessEventsArchiveResourceImpl#getOmCheckPtInterval <em>Om Check Pt Interval</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.impl.BusinessEventsArchiveResourceImpl#getOmCacheSize <em>Om Cache Size</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.impl.BusinessEventsArchiveResourceImpl#getOmCheckPtOpsLimit <em>Om Check Pt Ops Limit</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.impl.BusinessEventsArchiveResourceImpl#isOmDeletePolicy <em>Om Delete Policy</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.impl.BusinessEventsArchiveResourceImpl#isOmNoRecovery <em>Om No Recovery</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.impl.BusinessEventsArchiveResourceImpl#getOmDbEnvironmentDir <em>Om Db Environment Dir</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.impl.BusinessEventsArchiveResourceImpl#getOmtgGlobalCache <em>Omtg Global Cache</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.impl.BusinessEventsArchiveResourceImpl#getOmtgCacheConfigFile <em>Omtg Cache Config File</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.impl.BusinessEventsArchiveResourceImpl#getAdvancedSettings <em>Advanced Settings</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.impl.BusinessEventsArchiveResourceImpl#getObjectMgmtType <em>Object Mgmt Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.impl.BusinessEventsArchiveResourceImpl#getAgentGroupName <em>Agent Group Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.impl.BusinessEventsArchiveResourceImpl#getCacheConfigType <em>Cache Config Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.impl.BusinessEventsArchiveResourceImpl#getOmtgCustomRecoveryFactory <em>Omtg Custom Recovery Factory</em>}</li>
 * </ul>
 * </p>
 *
 * @generated NOT
 */
public class BusinessEventsArchiveResourceImpl extends BEArchiveResourceImpl implements BusinessEventsArchiveResource, Cloneable {
	/**
	 * The default value of the '{@link #getArchiveType() <em>Archive Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArchiveType()
	 * @generated
	 * @ordered
	 */
	protected static final BE_ARCHIVE_TYPE ARCHIVE_TYPE_EDEFAULT = BE_ARCHIVE_TYPE.INFERENCE;

	/**
	 * The cached value of the '{@link #getArchiveType() <em>Archive Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArchiveType()
	 * @generated
	 * @ordered
	 */
	protected BE_ARCHIVE_TYPE archiveType = ARCHIVE_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #isAllRuleSets() <em>All Rule Sets</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAllRuleSets()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ALL_RULE_SETS_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isAllRuleSets() <em>All Rule Sets</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAllRuleSets()
	 * @generated
	 * @ordered
	 */
	protected boolean allRuleSets = ALL_RULE_SETS_EDEFAULT;

	/**
	 * The default value of the '{@link #isAllDestinations() <em>All Destinations</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAllDestinations()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ALL_DESTINATIONS_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isAllDestinations() <em>All Destinations</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAllDestinations()
	 * @generated
	 * @ordered
	 */
	protected boolean allDestinations = ALL_DESTINATIONS_EDEFAULT;

	/**
	 * The cached value of the '{@link #getIncludedRuleSets() <em>Included Rule Sets</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIncludedRuleSets()
	 * @generated
	 * @ordered
	 */
	protected EList<String> includedRuleSets;

	/**
	 * The cached value of the '{@link #getStartupActions() <em>Startup Actions</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartupActions()
	 * @generated
	 * @ordered
	 */
	protected EList<String> startupActions;

	/**
	 * The cached value of the '{@link #getShutdownActions() <em>Shutdown Actions</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getShutdownActions()
	 * @generated
	 * @ordered
	 */
	protected EList<String> shutdownActions;

	/**
	 * The cached value of the '{@link #getInputDestinations() <em>Input Destinations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInputDestinations()
	 * @generated
	 * @ordered
	 */
	protected EList<InputDestination> inputDestinations;

	/**
	 * The default value of the '{@link #getOmCheckPtInterval() <em>Om Check Pt Interval</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOmCheckPtInterval()
	 * @generated
	 * @ordered
	 */
	protected static final int OM_CHECK_PT_INTERVAL_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getOmCheckPtInterval() <em>Om Check Pt Interval</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOmCheckPtInterval()
	 * @generated
	 * @ordered
	 */
	protected int omCheckPtInterval = OM_CHECK_PT_INTERVAL_EDEFAULT;

	/**
	 * The default value of the '{@link #getOmCacheSize() <em>Om Cache Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOmCacheSize()
	 * @generated
	 * @ordered
	 */
	protected static final int OM_CACHE_SIZE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getOmCacheSize() <em>Om Cache Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOmCacheSize()
	 * @generated
	 * @ordered
	 */
	protected int omCacheSize = OM_CACHE_SIZE_EDEFAULT;

	/**
	 * The default value of the '{@link #getOmCheckPtOpsLimit() <em>Om Check Pt Ops Limit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOmCheckPtOpsLimit()
	 * @generated
	 * @ordered
	 */
	protected static final int OM_CHECK_PT_OPS_LIMIT_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getOmCheckPtOpsLimit() <em>Om Check Pt Ops Limit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOmCheckPtOpsLimit()
	 * @generated
	 * @ordered
	 */
	protected int omCheckPtOpsLimit = OM_CHECK_PT_OPS_LIMIT_EDEFAULT;

	/**
	 * The default value of the '{@link #isOmDeletePolicy() <em>Om Delete Policy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isOmDeletePolicy()
	 * @generated
	 * @ordered
	 */
	protected static final boolean OM_DELETE_POLICY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isOmDeletePolicy() <em>Om Delete Policy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isOmDeletePolicy()
	 * @generated
	 * @ordered
	 */
	protected boolean omDeletePolicy = OM_DELETE_POLICY_EDEFAULT;

	/**
	 * The default value of the '{@link #isOmNoRecovery() <em>Om No Recovery</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isOmNoRecovery()
	 * @generated
	 * @ordered
	 */
	protected static final boolean OM_NO_RECOVERY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isOmNoRecovery() <em>Om No Recovery</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isOmNoRecovery()
	 * @generated
	 * @ordered
	 */
	protected boolean omNoRecovery = OM_NO_RECOVERY_EDEFAULT;

	/**
	 * The default value of the '{@link #getOmDbEnvironmentDir() <em>Om Db Environment Dir</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOmDbEnvironmentDir()
	 * @generated
	 * @ordered
	 */
	protected static final String OM_DB_ENVIRONMENT_DIR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOmDbEnvironmentDir() <em>Om Db Environment Dir</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOmDbEnvironmentDir()
	 * @generated
	 * @ordered
	 */
	protected String omDbEnvironmentDir = OM_DB_ENVIRONMENT_DIR_EDEFAULT;

	/**
	 * The default value of the '{@link #getOmtgGlobalCache() <em>Omtg Global Cache</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOmtgGlobalCache()
	 * @generated
	 * @ordered
	 */
	protected static final String OMTG_GLOBAL_CACHE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOmtgGlobalCache() <em>Omtg Global Cache</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOmtgGlobalCache()
	 * @generated
	 * @ordered
	 */
	protected String omtgGlobalCache = OMTG_GLOBAL_CACHE_EDEFAULT;

	/**
	 * The default value of the '{@link #getOmtgCacheConfigFile() <em>Omtg Cache Config File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOmtgCacheConfigFile()
	 * @generated
	 * @ordered
	 */
	protected static final String OMTG_CACHE_CONFIG_FILE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOmtgCacheConfigFile() <em>Omtg Cache Config File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOmtgCacheConfigFile()
	 * @generated
	 * @ordered
	 */
	protected String omtgCacheConfigFile = OMTG_CACHE_CONFIG_FILE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAdvancedSettings() <em>Advanced Settings</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAdvancedSettings()
	 * @generated
	 * @ordered
	 */
	protected EList<AdvancedEntitySetting> advancedSettings;

	/**
	 * The default value of the '{@link #getObjectMgmtType() <em>Object Mgmt Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObjectMgmtType()
	 * @generated
	 * @ordered
	 */
	protected static final OBJECT_MGMT_TYPE OBJECT_MGMT_TYPE_EDEFAULT = OBJECT_MGMT_TYPE.IN_MEMORY;

	/**
	 * The cached value of the '{@link #getObjectMgmtType() <em>Object Mgmt Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObjectMgmtType()
	 * @generated
	 * @ordered
	 */
	protected OBJECT_MGMT_TYPE objectMgmtType = OBJECT_MGMT_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getAgentGroupName() <em>Agent Group Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAgentGroupName()
	 * @generated
	 * @ordered
	 */
	protected static final String AGENT_GROUP_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAgentGroupName() <em>Agent Group Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAgentGroupName()
	 * @generated
	 * @ordered
	 */
	protected String agentGroupName = AGENT_GROUP_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getCacheConfigType() <em>Cache Config Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCacheConfigType()
	 * @generated
	 * @ordered
	 */
	protected static final CACHE_CONFIG_TYPE CACHE_CONFIG_TYPE_EDEFAULT = CACHE_CONFIG_TYPE.XML_RESOURCE;

	/**
	 * The cached value of the '{@link #getCacheConfigType() <em>Cache Config Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCacheConfigType()
	 * @generated
	 * @ordered
	 */
	protected CACHE_CONFIG_TYPE cacheConfigType = CACHE_CONFIG_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getOmtgCustomRecoveryFactory() <em>Omtg Custom Recovery Factory</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOmtgCustomRecoveryFactory()
	 * @generated
	 * @ordered
	 */
	protected static final String OMTG_CUSTOM_RECOVERY_FACTORY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOmtgCustomRecoveryFactory() <em>Omtg Custom Recovery Factory</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOmtgCustomRecoveryFactory()
	 * @generated
	 * @ordered
	 */
	protected String omtgCustomRecoveryFactory = OMTG_CUSTOM_RECOVERY_FACTORY_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BusinessEventsArchiveResourceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ArchivePackage.Literals.BUSINESS_EVENTS_ARCHIVE_RESOURCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BE_ARCHIVE_TYPE getArchiveType() {
		return archiveType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setArchiveType(BE_ARCHIVE_TYPE newArchiveType) {
		BE_ARCHIVE_TYPE oldArchiveType = archiveType;
		archiveType = newArchiveType == null ? ARCHIVE_TYPE_EDEFAULT : newArchiveType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__ARCHIVE_TYPE, oldArchiveType, archiveType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAllRuleSets() {
		return allRuleSets;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAllRuleSets(boolean newAllRuleSets) {
		boolean oldAllRuleSets = allRuleSets;
		allRuleSets = newAllRuleSets;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__ALL_RULE_SETS, oldAllRuleSets, allRuleSets));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAllDestinations() {
		return allDestinations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAllDestinations(boolean newAllDestinations) {
		boolean oldAllDestinations = allDestinations;
		allDestinations = newAllDestinations;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__ALL_DESTINATIONS, oldAllDestinations, allDestinations));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getIncludedRuleSets() {
		if (includedRuleSets == null) {
			includedRuleSets = new EDataTypeUniqueEList<String>(String.class, this, ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__INCLUDED_RULE_SETS);
		}
		return includedRuleSets;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getStartupActions() {
		if (startupActions == null) {
			startupActions = new EDataTypeUniqueEList<String>(String.class, this, ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__STARTUP_ACTIONS);
		}
		return startupActions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getShutdownActions() {
		if (shutdownActions == null) {
			shutdownActions = new EDataTypeUniqueEList<String>(String.class, this, ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__SHUTDOWN_ACTIONS);
		}
		return shutdownActions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<InputDestination> getInputDestinations() {
		if (inputDestinations == null) {
			inputDestinations = new EObjectContainmentEList<InputDestination>(InputDestination.class, this, ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__INPUT_DESTINATIONS);
		}
		return inputDestinations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getOmCheckPtInterval() {
		return omCheckPtInterval;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOmCheckPtInterval(int newOmCheckPtInterval) {
		int oldOmCheckPtInterval = omCheckPtInterval;
		omCheckPtInterval = newOmCheckPtInterval;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_CHECK_PT_INTERVAL, oldOmCheckPtInterval, omCheckPtInterval));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getOmCacheSize() {
		return omCacheSize;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOmCacheSize(int newOmCacheSize) {
		int oldOmCacheSize = omCacheSize;
		omCacheSize = newOmCacheSize;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_CACHE_SIZE, oldOmCacheSize, omCacheSize));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getOmCheckPtOpsLimit() {
		return omCheckPtOpsLimit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOmCheckPtOpsLimit(int newOmCheckPtOpsLimit) {
		int oldOmCheckPtOpsLimit = omCheckPtOpsLimit;
		omCheckPtOpsLimit = newOmCheckPtOpsLimit;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_CHECK_PT_OPS_LIMIT, oldOmCheckPtOpsLimit, omCheckPtOpsLimit));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isOmDeletePolicy() {
		return omDeletePolicy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOmDeletePolicy(boolean newOmDeletePolicy) {
		boolean oldOmDeletePolicy = omDeletePolicy;
		omDeletePolicy = newOmDeletePolicy;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_DELETE_POLICY, oldOmDeletePolicy, omDeletePolicy));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isOmNoRecovery() {
		return omNoRecovery;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOmNoRecovery(boolean newOmNoRecovery) {
		boolean oldOmNoRecovery = omNoRecovery;
		omNoRecovery = newOmNoRecovery;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_NO_RECOVERY, oldOmNoRecovery, omNoRecovery));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getOmDbEnvironmentDir() {
		return omDbEnvironmentDir;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOmDbEnvironmentDir(String newOmDbEnvironmentDir) {
		String oldOmDbEnvironmentDir = omDbEnvironmentDir;
		omDbEnvironmentDir = newOmDbEnvironmentDir;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_DB_ENVIRONMENT_DIR, oldOmDbEnvironmentDir, omDbEnvironmentDir));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getOmtgGlobalCache() {
		return omtgGlobalCache;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOmtgGlobalCache(String newOmtgGlobalCache) {
		String oldOmtgGlobalCache = omtgGlobalCache;
		omtgGlobalCache = newOmtgGlobalCache;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OMTG_GLOBAL_CACHE, oldOmtgGlobalCache, omtgGlobalCache));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getOmtgCacheConfigFile() {
		return omtgCacheConfigFile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOmtgCacheConfigFile(String newOmtgCacheConfigFile) {
		String oldOmtgCacheConfigFile = omtgCacheConfigFile;
		omtgCacheConfigFile = newOmtgCacheConfigFile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OMTG_CACHE_CONFIG_FILE, oldOmtgCacheConfigFile, omtgCacheConfigFile));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AdvancedEntitySetting> getAdvancedSettings() {
		if (advancedSettings == null) {
			advancedSettings = new EObjectContainmentEList<AdvancedEntitySetting>(AdvancedEntitySetting.class, this, ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__ADVANCED_SETTINGS);
		}
		return advancedSettings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OBJECT_MGMT_TYPE getObjectMgmtType() {
		return objectMgmtType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setObjectMgmtType(OBJECT_MGMT_TYPE newObjectMgmtType) {
		OBJECT_MGMT_TYPE oldObjectMgmtType = objectMgmtType;
		objectMgmtType = newObjectMgmtType == null ? OBJECT_MGMT_TYPE_EDEFAULT : newObjectMgmtType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OBJECT_MGMT_TYPE, oldObjectMgmtType, objectMgmtType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAgentGroupName() {
		return agentGroupName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAgentGroupName(String newAgentGroupName) {
		String oldAgentGroupName = agentGroupName;
		agentGroupName = newAgentGroupName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__AGENT_GROUP_NAME, oldAgentGroupName, agentGroupName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CACHE_CONFIG_TYPE getCacheConfigType() {
		return cacheConfigType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCacheConfigType(CACHE_CONFIG_TYPE newCacheConfigType) {
		CACHE_CONFIG_TYPE oldCacheConfigType = cacheConfigType;
		cacheConfigType = newCacheConfigType == null ? CACHE_CONFIG_TYPE_EDEFAULT : newCacheConfigType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__CACHE_CONFIG_TYPE, oldCacheConfigType, cacheConfigType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getOmtgCustomRecoveryFactory() {
		return omtgCustomRecoveryFactory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOmtgCustomRecoveryFactory(String newOmtgCustomRecoveryFactory) {
		String oldOmtgCustomRecoveryFactory = omtgCustomRecoveryFactory;
		omtgCustomRecoveryFactory = newOmtgCustomRecoveryFactory;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OMTG_CUSTOM_RECOVERY_FACTORY, oldOmtgCustomRecoveryFactory, omtgCustomRecoveryFactory));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__INPUT_DESTINATIONS:
				return ((InternalEList<?>)getInputDestinations()).basicRemove(otherEnd, msgs);
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__ADVANCED_SETTINGS:
				return ((InternalEList<?>)getAdvancedSettings()).basicRemove(otherEnd, msgs);
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
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__ARCHIVE_TYPE:
				return getArchiveType();
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__ALL_RULE_SETS:
				return isAllRuleSets() ? Boolean.TRUE : Boolean.FALSE;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__ALL_DESTINATIONS:
				return isAllDestinations() ? Boolean.TRUE : Boolean.FALSE;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__INCLUDED_RULE_SETS:
				return getIncludedRuleSets();
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__STARTUP_ACTIONS:
				return getStartupActions();
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__SHUTDOWN_ACTIONS:
				return getShutdownActions();
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__INPUT_DESTINATIONS:
				return getInputDestinations();
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_CHECK_PT_INTERVAL:
				return new Integer(getOmCheckPtInterval());
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_CACHE_SIZE:
				return new Integer(getOmCacheSize());
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_CHECK_PT_OPS_LIMIT:
				return new Integer(getOmCheckPtOpsLimit());
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_DELETE_POLICY:
				return isOmDeletePolicy() ? Boolean.TRUE : Boolean.FALSE;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_NO_RECOVERY:
				return isOmNoRecovery() ? Boolean.TRUE : Boolean.FALSE;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_DB_ENVIRONMENT_DIR:
				return getOmDbEnvironmentDir();
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OMTG_GLOBAL_CACHE:
				return getOmtgGlobalCache();
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OMTG_CACHE_CONFIG_FILE:
				return getOmtgCacheConfigFile();
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__ADVANCED_SETTINGS:
				return getAdvancedSettings();
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OBJECT_MGMT_TYPE:
				return getObjectMgmtType();
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__AGENT_GROUP_NAME:
				return getAgentGroupName();
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__CACHE_CONFIG_TYPE:
				return getCacheConfigType();
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OMTG_CUSTOM_RECOVERY_FACTORY:
				return getOmtgCustomRecoveryFactory();
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
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__ARCHIVE_TYPE:
				setArchiveType((BE_ARCHIVE_TYPE)newValue);
				return;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__ALL_RULE_SETS:
				setAllRuleSets(((Boolean)newValue).booleanValue());
				return;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__ALL_DESTINATIONS:
				setAllDestinations(((Boolean)newValue).booleanValue());
				return;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__INCLUDED_RULE_SETS:
				getIncludedRuleSets().clear();
				getIncludedRuleSets().addAll((Collection<? extends String>)newValue);
				return;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__STARTUP_ACTIONS:
				getStartupActions().clear();
				getStartupActions().addAll((Collection<? extends String>)newValue);
				return;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__SHUTDOWN_ACTIONS:
				getShutdownActions().clear();
				getShutdownActions().addAll((Collection<? extends String>)newValue);
				return;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__INPUT_DESTINATIONS:
				getInputDestinations().clear();
				getInputDestinations().addAll((Collection<? extends InputDestination>)newValue);
				return;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_CHECK_PT_INTERVAL:
				setOmCheckPtInterval(((Integer)newValue).intValue());
				return;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_CACHE_SIZE:
				setOmCacheSize(((Integer)newValue).intValue());
				return;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_CHECK_PT_OPS_LIMIT:
				setOmCheckPtOpsLimit(((Integer)newValue).intValue());
				return;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_DELETE_POLICY:
				setOmDeletePolicy(((Boolean)newValue).booleanValue());
				return;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_NO_RECOVERY:
				setOmNoRecovery(((Boolean)newValue).booleanValue());
				return;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_DB_ENVIRONMENT_DIR:
				setOmDbEnvironmentDir((String)newValue);
				return;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OMTG_GLOBAL_CACHE:
				setOmtgGlobalCache((String)newValue);
				return;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OMTG_CACHE_CONFIG_FILE:
				setOmtgCacheConfigFile((String)newValue);
				return;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__ADVANCED_SETTINGS:
				getAdvancedSettings().clear();
				getAdvancedSettings().addAll((Collection<? extends AdvancedEntitySetting>)newValue);
				return;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OBJECT_MGMT_TYPE:
				setObjectMgmtType((OBJECT_MGMT_TYPE)newValue);
				return;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__AGENT_GROUP_NAME:
				setAgentGroupName((String)newValue);
				return;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__CACHE_CONFIG_TYPE:
				setCacheConfigType((CACHE_CONFIG_TYPE)newValue);
				return;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OMTG_CUSTOM_RECOVERY_FACTORY:
				setOmtgCustomRecoveryFactory((String)newValue);
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
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__ARCHIVE_TYPE:
				setArchiveType(ARCHIVE_TYPE_EDEFAULT);
				return;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__ALL_RULE_SETS:
				setAllRuleSets(ALL_RULE_SETS_EDEFAULT);
				return;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__ALL_DESTINATIONS:
				setAllDestinations(ALL_DESTINATIONS_EDEFAULT);
				return;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__INCLUDED_RULE_SETS:
				getIncludedRuleSets().clear();
				return;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__STARTUP_ACTIONS:
				getStartupActions().clear();
				return;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__SHUTDOWN_ACTIONS:
				getShutdownActions().clear();
				return;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__INPUT_DESTINATIONS:
				getInputDestinations().clear();
				return;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_CHECK_PT_INTERVAL:
				setOmCheckPtInterval(OM_CHECK_PT_INTERVAL_EDEFAULT);
				return;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_CACHE_SIZE:
				setOmCacheSize(OM_CACHE_SIZE_EDEFAULT);
				return;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_CHECK_PT_OPS_LIMIT:
				setOmCheckPtOpsLimit(OM_CHECK_PT_OPS_LIMIT_EDEFAULT);
				return;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_DELETE_POLICY:
				setOmDeletePolicy(OM_DELETE_POLICY_EDEFAULT);
				return;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_NO_RECOVERY:
				setOmNoRecovery(OM_NO_RECOVERY_EDEFAULT);
				return;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_DB_ENVIRONMENT_DIR:
				setOmDbEnvironmentDir(OM_DB_ENVIRONMENT_DIR_EDEFAULT);
				return;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OMTG_GLOBAL_CACHE:
				setOmtgGlobalCache(OMTG_GLOBAL_CACHE_EDEFAULT);
				return;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OMTG_CACHE_CONFIG_FILE:
				setOmtgCacheConfigFile(OMTG_CACHE_CONFIG_FILE_EDEFAULT);
				return;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__ADVANCED_SETTINGS:
				getAdvancedSettings().clear();
				return;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OBJECT_MGMT_TYPE:
				setObjectMgmtType(OBJECT_MGMT_TYPE_EDEFAULT);
				return;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__AGENT_GROUP_NAME:
				setAgentGroupName(AGENT_GROUP_NAME_EDEFAULT);
				return;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__CACHE_CONFIG_TYPE:
				setCacheConfigType(CACHE_CONFIG_TYPE_EDEFAULT);
				return;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OMTG_CUSTOM_RECOVERY_FACTORY:
				setOmtgCustomRecoveryFactory(OMTG_CUSTOM_RECOVERY_FACTORY_EDEFAULT);
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
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__ARCHIVE_TYPE:
				return archiveType != ARCHIVE_TYPE_EDEFAULT;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__ALL_RULE_SETS:
				return allRuleSets != ALL_RULE_SETS_EDEFAULT;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__ALL_DESTINATIONS:
				return allDestinations != ALL_DESTINATIONS_EDEFAULT;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__INCLUDED_RULE_SETS:
				return includedRuleSets != null && !includedRuleSets.isEmpty();
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__STARTUP_ACTIONS:
				return startupActions != null && !startupActions.isEmpty();
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__SHUTDOWN_ACTIONS:
				return shutdownActions != null && !shutdownActions.isEmpty();
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__INPUT_DESTINATIONS:
				return inputDestinations != null && !inputDestinations.isEmpty();
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_CHECK_PT_INTERVAL:
				return omCheckPtInterval != OM_CHECK_PT_INTERVAL_EDEFAULT;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_CACHE_SIZE:
				return omCacheSize != OM_CACHE_SIZE_EDEFAULT;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_CHECK_PT_OPS_LIMIT:
				return omCheckPtOpsLimit != OM_CHECK_PT_OPS_LIMIT_EDEFAULT;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_DELETE_POLICY:
				return omDeletePolicy != OM_DELETE_POLICY_EDEFAULT;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_NO_RECOVERY:
				return omNoRecovery != OM_NO_RECOVERY_EDEFAULT;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_DB_ENVIRONMENT_DIR:
				return OM_DB_ENVIRONMENT_DIR_EDEFAULT == null ? omDbEnvironmentDir != null : !OM_DB_ENVIRONMENT_DIR_EDEFAULT.equals(omDbEnvironmentDir);
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OMTG_GLOBAL_CACHE:
				return OMTG_GLOBAL_CACHE_EDEFAULT == null ? omtgGlobalCache != null : !OMTG_GLOBAL_CACHE_EDEFAULT.equals(omtgGlobalCache);
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OMTG_CACHE_CONFIG_FILE:
				return OMTG_CACHE_CONFIG_FILE_EDEFAULT == null ? omtgCacheConfigFile != null : !OMTG_CACHE_CONFIG_FILE_EDEFAULT.equals(omtgCacheConfigFile);
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__ADVANCED_SETTINGS:
				return advancedSettings != null && !advancedSettings.isEmpty();
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OBJECT_MGMT_TYPE:
				return objectMgmtType != OBJECT_MGMT_TYPE_EDEFAULT;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__AGENT_GROUP_NAME:
				return AGENT_GROUP_NAME_EDEFAULT == null ? agentGroupName != null : !AGENT_GROUP_NAME_EDEFAULT.equals(agentGroupName);
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__CACHE_CONFIG_TYPE:
				return cacheConfigType != CACHE_CONFIG_TYPE_EDEFAULT;
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__OMTG_CUSTOM_RECOVERY_FACTORY:
				return OMTG_CUSTOM_RECOVERY_FACTORY_EDEFAULT == null ? omtgCustomRecoveryFactory != null : !OMTG_CUSTOM_RECOVERY_FACTORY_EDEFAULT.equals(omtgCustomRecoveryFactory);
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
		result.append(" (archiveType: ");
		result.append(archiveType);
		result.append(", allRuleSets: ");
		result.append(allRuleSets);
		result.append(", allDestinations: ");
		result.append(allDestinations);
		result.append(", includedRuleSets: ");
		result.append(includedRuleSets);
		result.append(", startupActions: ");
		result.append(startupActions);
		result.append(", shutdownActions: ");
		result.append(shutdownActions);
		result.append(", omCheckPtInterval: ");
		result.append(omCheckPtInterval);
		result.append(", omCacheSize: ");
		result.append(omCacheSize);
		result.append(", omCheckPtOpsLimit: ");
		result.append(omCheckPtOpsLimit);
		result.append(", omDeletePolicy: ");
		result.append(omDeletePolicy);
		result.append(", omNoRecovery: ");
		result.append(omNoRecovery);
		result.append(", omDbEnvironmentDir: ");
		result.append(omDbEnvironmentDir);
		result.append(", omtgGlobalCache: ");
		result.append(omtgGlobalCache);
		result.append(", omtgCacheConfigFile: ");
		result.append(omtgCacheConfigFile);
		result.append(", objectMgmtType: ");
		result.append(objectMgmtType);
		result.append(", agentGroupName: ");
		result.append(agentGroupName);
		result.append(", cacheConfigType: ");
		result.append(cacheConfigType);
		result.append(", omtgCustomRecoveryFactory: ");
		result.append(omtgCustomRecoveryFactory);
		result.append(')');
		return result.toString();
	}
	
	
	public Object clone() throws CloneNotSupportedException {
		BusinessEventsArchiveResource clone = 
			ArchiveFactory.eINSTANCE.createBusinessEventsArchiveResource();
		clone.setCompilePath(this.getCompilePath());
		List<InputDestination> inputDestinations = this.getInputDestinations();
		for (InputDestination originalDest : inputDestinations) {
			clone.getInputDestinations().add((InputDestination)originalDest.clone());
		}
		clone.setAgentGroupName(this.getAgentGroupName());
		clone.setArchiveType(this.getArchiveType());
		clone.setAuthor(this.getAuthor());
		clone.setObjectMgmtType(this.getObjectMgmtType());
		clone.setOmCheckPtInterval(this.getOmCheckPtInterval());
		clone.setOmCacheSize(this.getOmCacheSize());
		clone.setCompileWithDebug(this.isCompileWithDebug());
		clone.setAllRuleSets(this.isAllRuleSets());
		clone.setCacheConfigType(this.getCacheConfigType());
		clone.setAllDestinations(this.isAllDestinations());
		clone.setOmDeletePolicy(this.isOmDeletePolicy());
		return clone;
	}
	
} //BusinessEventsArchiveResourceImpl
