/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.archive;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Business Events Archive Resource</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getArchiveType <em>Archive Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#isAllRuleSets <em>All Rule Sets</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#isAllDestinations <em>All Destinations</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getIncludedRuleSets <em>Included Rule Sets</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getStartupActions <em>Startup Actions</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getShutdownActions <em>Shutdown Actions</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getInputDestinations <em>Input Destinations</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getOmCheckPtInterval <em>Om Check Pt Interval</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getOmCacheSize <em>Om Cache Size</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getOmCheckPtOpsLimit <em>Om Check Pt Ops Limit</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#isOmDeletePolicy <em>Om Delete Policy</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#isOmNoRecovery <em>Om No Recovery</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getOmDbEnvironmentDir <em>Om Db Environment Dir</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getOmtgGlobalCache <em>Omtg Global Cache</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getOmtgCacheConfigFile <em>Omtg Cache Config File</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getAdvancedSettings <em>Advanced Settings</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getObjectMgmtType <em>Object Mgmt Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getAgentGroupName <em>Agent Group Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getCacheConfigType <em>Cache Config Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getOmtgCustomRecoveryFactory <em>Omtg Custom Recovery Factory</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getBusinessEventsArchiveResource()
 * @model
 * @generated NOT
 */
public interface BusinessEventsArchiveResource extends BEArchiveResource {
	/**
	 * Returns the value of the '<em><b>Archive Type</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.core.model.archive.BE_ARCHIVE_TYPE}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Archive Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Archive Type</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.archive.BE_ARCHIVE_TYPE
	 * @see #setArchiveType(BE_ARCHIVE_TYPE)
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getBusinessEventsArchiveResource_ArchiveType()
	 * @model
	 * @generated
	 */
	BE_ARCHIVE_TYPE getArchiveType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getArchiveType <em>Archive Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Archive Type</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.archive.BE_ARCHIVE_TYPE
	 * @see #getArchiveType()
	 * @generated
	 */
	void setArchiveType(BE_ARCHIVE_TYPE value);

	/**
	 * Returns the value of the '<em><b>All Rule Sets</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>All Rule Sets</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>All Rule Sets</em>' attribute.
	 * @see #setAllRuleSets(boolean)
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getBusinessEventsArchiveResource_AllRuleSets()
	 * @model
	 * @generated
	 */
	boolean isAllRuleSets();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#isAllRuleSets <em>All Rule Sets</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>All Rule Sets</em>' attribute.
	 * @see #isAllRuleSets()
	 * @generated
	 */
	void setAllRuleSets(boolean value);

	/**
	 * Returns the value of the '<em><b>All Destinations</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>All Destinations</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>All Destinations</em>' attribute.
	 * @see #setAllDestinations(boolean)
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getBusinessEventsArchiveResource_AllDestinations()
	 * @model
	 * @generated
	 */
	boolean isAllDestinations();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#isAllDestinations <em>All Destinations</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>All Destinations</em>' attribute.
	 * @see #isAllDestinations()
	 * @generated
	 */
	void setAllDestinations(boolean value);

	/**
	 * Returns the value of the '<em><b>Included Rule Sets</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Included Rule Sets</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Included Rule Sets</em>' attribute list.
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getBusinessEventsArchiveResource_IncludedRuleSets()
	 * @model
	 * @generated
	 */
	EList<String> getIncludedRuleSets();

	/**
	 * Returns the value of the '<em><b>Startup Actions</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Startup Actions</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Startup Actions</em>' attribute list.
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getBusinessEventsArchiveResource_StartupActions()
	 * @model
	 * @generated
	 */
	EList<String> getStartupActions();

	/**
	 * Returns the value of the '<em><b>Shutdown Actions</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Shutdown Actions</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Shutdown Actions</em>' attribute list.
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getBusinessEventsArchiveResource_ShutdownActions()
	 * @model
	 * @generated
	 */
	EList<String> getShutdownActions();

	/**
	 * Returns the value of the '<em><b>Input Destinations</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.archive.InputDestination}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Input Destinations</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Input Destinations</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getBusinessEventsArchiveResource_InputDestinations()
	 * @model containment="true"
	 * @generated
	 */
	EList<InputDestination> getInputDestinations();

	/**
	 * Returns the value of the '<em><b>Om Check Pt Interval</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Om Check Pt Interval</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Om Check Pt Interval</em>' attribute.
	 * @see #setOmCheckPtInterval(int)
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getBusinessEventsArchiveResource_OmCheckPtInterval()
	 * @model
	 * @generated
	 */
	int getOmCheckPtInterval();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getOmCheckPtInterval <em>Om Check Pt Interval</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Om Check Pt Interval</em>' attribute.
	 * @see #getOmCheckPtInterval()
	 * @generated
	 */
	void setOmCheckPtInterval(int value);

	/**
	 * Returns the value of the '<em><b>Om Cache Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Om Cache Size</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Om Cache Size</em>' attribute.
	 * @see #setOmCacheSize(int)
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getBusinessEventsArchiveResource_OmCacheSize()
	 * @model
	 * @generated
	 */
	int getOmCacheSize();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getOmCacheSize <em>Om Cache Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Om Cache Size</em>' attribute.
	 * @see #getOmCacheSize()
	 * @generated
	 */
	void setOmCacheSize(int value);

	/**
	 * Returns the value of the '<em><b>Om Check Pt Ops Limit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Om Check Pt Ops Limit</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Om Check Pt Ops Limit</em>' attribute.
	 * @see #setOmCheckPtOpsLimit(int)
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getBusinessEventsArchiveResource_OmCheckPtOpsLimit()
	 * @model
	 * @generated
	 */
	int getOmCheckPtOpsLimit();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getOmCheckPtOpsLimit <em>Om Check Pt Ops Limit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Om Check Pt Ops Limit</em>' attribute.
	 * @see #getOmCheckPtOpsLimit()
	 * @generated
	 */
	void setOmCheckPtOpsLimit(int value);

	/**
	 * Returns the value of the '<em><b>Om Delete Policy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Om Delete Policy</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Om Delete Policy</em>' attribute.
	 * @see #setOmDeletePolicy(boolean)
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getBusinessEventsArchiveResource_OmDeletePolicy()
	 * @model
	 * @generated
	 */
	boolean isOmDeletePolicy();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#isOmDeletePolicy <em>Om Delete Policy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Om Delete Policy</em>' attribute.
	 * @see #isOmDeletePolicy()
	 * @generated
	 */
	void setOmDeletePolicy(boolean value);

	/**
	 * Returns the value of the '<em><b>Om No Recovery</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Om No Recovery</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Om No Recovery</em>' attribute.
	 * @see #setOmNoRecovery(boolean)
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getBusinessEventsArchiveResource_OmNoRecovery()
	 * @model
	 * @generated
	 */
	boolean isOmNoRecovery();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#isOmNoRecovery <em>Om No Recovery</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Om No Recovery</em>' attribute.
	 * @see #isOmNoRecovery()
	 * @generated
	 */
	void setOmNoRecovery(boolean value);

	/**
	 * Returns the value of the '<em><b>Om Db Environment Dir</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Om Db Environment Dir</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Om Db Environment Dir</em>' attribute.
	 * @see #setOmDbEnvironmentDir(String)
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getBusinessEventsArchiveResource_OmDbEnvironmentDir()
	 * @model
	 * @generated
	 */
	String getOmDbEnvironmentDir();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getOmDbEnvironmentDir <em>Om Db Environment Dir</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Om Db Environment Dir</em>' attribute.
	 * @see #getOmDbEnvironmentDir()
	 * @generated
	 */
	void setOmDbEnvironmentDir(String value);

	/**
	 * Returns the value of the '<em><b>Omtg Global Cache</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Omtg Global Cache</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Omtg Global Cache</em>' attribute.
	 * @see #setOmtgGlobalCache(String)
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getBusinessEventsArchiveResource_OmtgGlobalCache()
	 * @model
	 * @generated
	 */
	String getOmtgGlobalCache();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getOmtgGlobalCache <em>Omtg Global Cache</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Omtg Global Cache</em>' attribute.
	 * @see #getOmtgGlobalCache()
	 * @generated
	 */
	void setOmtgGlobalCache(String value);

	/**
	 * Returns the value of the '<em><b>Omtg Cache Config File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Omtg Cache Config File</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Omtg Cache Config File</em>' attribute.
	 * @see #setOmtgCacheConfigFile(String)
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getBusinessEventsArchiveResource_OmtgCacheConfigFile()
	 * @model
	 * @generated
	 */
	String getOmtgCacheConfigFile();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getOmtgCacheConfigFile <em>Omtg Cache Config File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Omtg Cache Config File</em>' attribute.
	 * @see #getOmtgCacheConfigFile()
	 * @generated
	 */
	void setOmtgCacheConfigFile(String value);

	/**
	 * Returns the value of the '<em><b>Advanced Settings</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.archive.AdvancedEntitySetting}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Advanced Settings</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Advanced Settings</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getBusinessEventsArchiveResource_AdvancedSettings()
	 * @model containment="true"
	 * @generated
	 */
	EList<AdvancedEntitySetting> getAdvancedSettings();

	/**
	 * Returns the value of the '<em><b>Object Mgmt Type</b></em>' attribute.
	 * The default value is <code>"InMemory"</code>.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.core.model.archive.OBJECT_MGMT_TYPE}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Object Mgmt Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Object Mgmt Type</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.archive.OBJECT_MGMT_TYPE
	 * @see #setObjectMgmtType(OBJECT_MGMT_TYPE)
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getBusinessEventsArchiveResource_ObjectMgmtType()
	 * @model default="InMemory" required="true"
	 * @generated
	 */
	OBJECT_MGMT_TYPE getObjectMgmtType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getObjectMgmtType <em>Object Mgmt Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Object Mgmt Type</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.archive.OBJECT_MGMT_TYPE
	 * @see #getObjectMgmtType()
	 * @generated
	 */
	void setObjectMgmtType(OBJECT_MGMT_TYPE value);

	/**
	 * Returns the value of the '<em><b>Agent Group Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Agent Group Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Agent Group Name</em>' attribute.
	 * @see #setAgentGroupName(String)
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getBusinessEventsArchiveResource_AgentGroupName()
	 * @model
	 * @generated
	 */
	String getAgentGroupName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getAgentGroupName <em>Agent Group Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Agent Group Name</em>' attribute.
	 * @see #getAgentGroupName()
	 * @generated
	 */
	void setAgentGroupName(String value);

	/**
	 * Returns the value of the '<em><b>Cache Config Type</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.core.model.archive.CACHE_CONFIG_TYPE}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cache Config Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cache Config Type</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.archive.CACHE_CONFIG_TYPE
	 * @see #setCacheConfigType(CACHE_CONFIG_TYPE)
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getBusinessEventsArchiveResource_CacheConfigType()
	 * @model
	 * @generated
	 */
	CACHE_CONFIG_TYPE getCacheConfigType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getCacheConfigType <em>Cache Config Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cache Config Type</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.archive.CACHE_CONFIG_TYPE
	 * @see #getCacheConfigType()
	 * @generated
	 */
	void setCacheConfigType(CACHE_CONFIG_TYPE value);

	/**
	 * Returns the value of the '<em><b>Omtg Custom Recovery Factory</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Omtg Custom Recovery Factory</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Omtg Custom Recovery Factory</em>' attribute.
	 * @see #setOmtgCustomRecoveryFactory(String)
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getBusinessEventsArchiveResource_OmtgCustomRecoveryFactory()
	 * @model
	 * @generated
	 */
	String getOmtgCustomRecoveryFactory();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getOmtgCustomRecoveryFactory <em>Omtg Custom Recovery Factory</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Omtg Custom Recovery Factory</em>' attribute.
	 * @see #getOmtgCustomRecoveryFactory()
	 * @generated
	 */
	void setOmtgCustomRecoveryFactory(String value);
	
	Object clone() throws CloneNotSupportedException;	

} // BusinessEventsArchiveResource
