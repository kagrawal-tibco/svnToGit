/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.common.configuration.impl;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.studio.common.configuration.BpmnPalettePathEntry;
import com.tibco.cep.studio.common.configuration.BpmnProcessPathEntry;
import com.tibco.cep.studio.common.configuration.BpmnProcessSettings;
import com.tibco.cep.studio.common.configuration.ConfigurationPackage;
import com.tibco.cep.studio.common.configuration.NamePrefix;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Bpmn Process Settings</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.BpmnProcessSettingsImpl#getBuildFolder <em>Build Folder</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.BpmnProcessSettingsImpl#getPalettePathEntries <em>Palette Path Entries</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.BpmnProcessSettingsImpl#getSelectedProcessPaths <em>Selected Process Paths</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.BpmnProcessSettingsImpl#getProcessPrefix <em>Process Prefix</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.BpmnProcessSettingsImpl#getRulePrefix <em>Rule Prefix</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.BpmnProcessSettingsImpl#getRuleFunctionPrefix <em>Rule Function Prefix</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.BpmnProcessSettingsImpl#getConceptPrefix <em>Concept Prefix</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.BpmnProcessSettingsImpl#getEventPrefix <em>Event Prefix</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.BpmnProcessSettingsImpl#getTimeEventPrefix <em>Time Event Prefix</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.BpmnProcessSettingsImpl#getScorecardPrefix <em>Scorecard Prefix</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.BpmnProcessSettingsImpl#getNamePrefixes <em>Name Prefixes</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BpmnProcessSettingsImpl extends EObjectImpl implements BpmnProcessSettings {
	/**
	 * The default value of the '{@link #getBuildFolder() <em>Build Folder</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBuildFolder()
	 * @generated
	 * @ordered
	 */
	protected static final String BUILD_FOLDER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBuildFolder() <em>Build Folder</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBuildFolder()
	 * @generated
	 * @ordered
	 */
	protected String buildFolder = BUILD_FOLDER_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPalettePathEntries() <em>Palette Path Entries</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPalettePathEntries()
	 * @generated
	 * @ordered
	 */
	protected EList<BpmnPalettePathEntry> palettePathEntries;

	/**
	 * The cached value of the '{@link #getSelectedProcessPaths() <em>Selected Process Paths</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSelectedProcessPaths()
	 * @generated
	 * @ordered
	 */
	protected EList<BpmnProcessPathEntry> selectedProcessPaths;

	/**
	 * The default value of the '{@link #getProcessPrefix() <em>Process Prefix</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProcessPrefix()
	 * @generated
	 * @ordered
	 */
	protected static final String PROCESS_PREFIX_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProcessPrefix() <em>Process Prefix</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProcessPrefix()
	 * @generated
	 * @ordered
	 */
	protected String processPrefix = PROCESS_PREFIX_EDEFAULT;

	/**
	 * The default value of the '{@link #getRulePrefix() <em>Rule Prefix</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRulePrefix()
	 * @generated
	 * @ordered
	 */
	protected static final String RULE_PREFIX_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRulePrefix() <em>Rule Prefix</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRulePrefix()
	 * @generated
	 * @ordered
	 */
	protected String rulePrefix = RULE_PREFIX_EDEFAULT;

	/**
	 * The default value of the '{@link #getRuleFunctionPrefix() <em>Rule Function Prefix</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRuleFunctionPrefix()
	 * @generated
	 * @ordered
	 */
	protected static final String RULE_FUNCTION_PREFIX_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRuleFunctionPrefix() <em>Rule Function Prefix</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRuleFunctionPrefix()
	 * @generated
	 * @ordered
	 */
	protected String ruleFunctionPrefix = RULE_FUNCTION_PREFIX_EDEFAULT;

	/**
	 * The default value of the '{@link #getConceptPrefix() <em>Concept Prefix</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConceptPrefix()
	 * @generated
	 * @ordered
	 */
	protected static final String CONCEPT_PREFIX_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getConceptPrefix() <em>Concept Prefix</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConceptPrefix()
	 * @generated
	 * @ordered
	 */
	protected String conceptPrefix = CONCEPT_PREFIX_EDEFAULT;

	/**
	 * The default value of the '{@link #getEventPrefix() <em>Event Prefix</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEventPrefix()
	 * @generated
	 * @ordered
	 */
	protected static final String EVENT_PREFIX_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEventPrefix() <em>Event Prefix</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEventPrefix()
	 * @generated
	 * @ordered
	 */
	protected String eventPrefix = EVENT_PREFIX_EDEFAULT;

	/**
	 * The default value of the '{@link #getTimeEventPrefix() <em>Time Event Prefix</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimeEventPrefix()
	 * @generated
	 * @ordered
	 */
	protected static final String TIME_EVENT_PREFIX_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTimeEventPrefix() <em>Time Event Prefix</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimeEventPrefix()
	 * @generated
	 * @ordered
	 */
	protected String timeEventPrefix = TIME_EVENT_PREFIX_EDEFAULT;

	/**
	 * The default value of the '{@link #getScorecardPrefix() <em>Scorecard Prefix</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScorecardPrefix()
	 * @generated
	 * @ordered
	 */
	protected static final String SCORECARD_PREFIX_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getScorecardPrefix() <em>Scorecard Prefix</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScorecardPrefix()
	 * @generated
	 * @ordered
	 */
	protected String scorecardPrefix = SCORECARD_PREFIX_EDEFAULT;

	/**
	 * The cached value of the '{@link #getNamePrefixes() <em>Name Prefixes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamePrefixes()
	 * @generated
	 * @ordered
	 */
	protected EList<NamePrefix> namePrefixes;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BpmnProcessSettingsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ConfigurationPackage.Literals.BPMN_PROCESS_SETTINGS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getBuildFolder() {
		return buildFolder;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBuildFolder(String newBuildFolder) {
		String oldBuildFolder = buildFolder;
		buildFolder = newBuildFolder;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.BPMN_PROCESS_SETTINGS__BUILD_FOLDER, oldBuildFolder, buildFolder));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<BpmnPalettePathEntry> getPalettePathEntries() {
		if (palettePathEntries == null) {
			palettePathEntries = new EObjectContainmentEList<BpmnPalettePathEntry>(BpmnPalettePathEntry.class, this, ConfigurationPackage.BPMN_PROCESS_SETTINGS__PALETTE_PATH_ENTRIES);
		}
		return palettePathEntries;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<BpmnProcessPathEntry> getSelectedProcessPaths() {
		if (selectedProcessPaths == null) {
			selectedProcessPaths = new EObjectContainmentEList<BpmnProcessPathEntry>(BpmnProcessPathEntry.class, this, ConfigurationPackage.BPMN_PROCESS_SETTINGS__SELECTED_PROCESS_PATHS);
		}
		return selectedProcessPaths;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getProcessPrefix() {
		return processPrefix;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProcessPrefix(String newProcessPrefix) {
		String oldProcessPrefix = processPrefix;
		processPrefix = newProcessPrefix;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.BPMN_PROCESS_SETTINGS__PROCESS_PREFIX, oldProcessPrefix, processPrefix));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRulePrefix() {
		return rulePrefix;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRulePrefix(String newRulePrefix) {
		String oldRulePrefix = rulePrefix;
		rulePrefix = newRulePrefix;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.BPMN_PROCESS_SETTINGS__RULE_PREFIX, oldRulePrefix, rulePrefix));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRuleFunctionPrefix() {
		return ruleFunctionPrefix;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRuleFunctionPrefix(String newRuleFunctionPrefix) {
		String oldRuleFunctionPrefix = ruleFunctionPrefix;
		ruleFunctionPrefix = newRuleFunctionPrefix;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.BPMN_PROCESS_SETTINGS__RULE_FUNCTION_PREFIX, oldRuleFunctionPrefix, ruleFunctionPrefix));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getConceptPrefix() {
		return conceptPrefix;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConceptPrefix(String newConceptPrefix) {
		String oldConceptPrefix = conceptPrefix;
		conceptPrefix = newConceptPrefix;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.BPMN_PROCESS_SETTINGS__CONCEPT_PREFIX, oldConceptPrefix, conceptPrefix));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEventPrefix() {
		return eventPrefix;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEventPrefix(String newEventPrefix) {
		String oldEventPrefix = eventPrefix;
		eventPrefix = newEventPrefix;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.BPMN_PROCESS_SETTINGS__EVENT_PREFIX, oldEventPrefix, eventPrefix));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTimeEventPrefix() {
		return timeEventPrefix;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTimeEventPrefix(String newTimeEventPrefix) {
		String oldTimeEventPrefix = timeEventPrefix;
		timeEventPrefix = newTimeEventPrefix;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.BPMN_PROCESS_SETTINGS__TIME_EVENT_PREFIX, oldTimeEventPrefix, timeEventPrefix));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getScorecardPrefix() {
		return scorecardPrefix;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setScorecardPrefix(String newScorecardPrefix) {
		String oldScorecardPrefix = scorecardPrefix;
		scorecardPrefix = newScorecardPrefix;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.BPMN_PROCESS_SETTINGS__SCORECARD_PREFIX, oldScorecardPrefix, scorecardPrefix));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<NamePrefix> getNamePrefixes() {
		if (namePrefixes == null) {
			namePrefixes = new EObjectContainmentEList<NamePrefix>(NamePrefix.class, this, ConfigurationPackage.BPMN_PROCESS_SETTINGS__NAME_PREFIXES);
		}
		return namePrefixes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Map<String, NamePrefix> getNamePrefixMap() {
		Map<String,NamePrefix> namePrefixMap = new Map<String,NamePrefix>() {

			@Override
			public void clear() {
				getNamePrefixes().clear();
				
			}

			@Override
			public boolean containsKey(Object key) {
				for(NamePrefix s: getNamePrefixes()) {
					if(s.getName().equals(key)){
						return true;
					}
				}
				return false;
			}

			@Override
			public boolean containsValue(Object value) {
				if(value instanceof NamePrefix) {
					for(NamePrefix s:getNamePrefixes()) {
						if(s.equals(value)) {
							return true;
						}
					}
				} 
				return false;
			}

			@Override
			public Set<Map.Entry<String, NamePrefix>> entrySet() {
				LinkedHashSet<Map.Entry<String,NamePrefix>> set = new LinkedHashSet<Map.Entry<String,NamePrefix>>();
				for(NamePrefix s:getNamePrefixes()) {
					final NamePrefix sym = s;
					set.add(new Map.Entry<String,NamePrefix>() {

						@Override
						public String getKey() {
							return sym.getName();
						}

						@Override
						public NamePrefix getValue() {
							return sym;
						}

						@Override
						public NamePrefix setValue(NamePrefix value) {
							throw new UnsupportedOperationException();
						}
						
					});
				}
				return set;
			}

			@Override
			public NamePrefix get(Object key) {
				if(key instanceof String) {
					for(NamePrefix s: getNamePrefixes()) {
						if(s.getName().equals((String)key)) {
							return s;
						}
					}
				}
				return null;
			}

			@Override
			public boolean isEmpty() {				
				return getNamePrefixes().size() == 0;
			}

			@Override
			public Set<String> keySet() {
				LinkedHashSet<String> set = new LinkedHashSet<String>();
				for(NamePrefix s:getNamePrefixes()) {
					set.add(s.getName());
				}
				return set;
			}

			@Override
			public int size() {
				return getNamePrefixes().size();
			}

			@Override
			public Collection<NamePrefix> values() {
				return getNamePrefixes();
			}

			@Override
			public NamePrefix put(String key, NamePrefix value) {
				NamePrefix added = null;
				if(getNamePrefixes().add(value)) {
					added = value;
				}
				return added;
			}

			@Override
			public void putAll(Map<? extends String, ? extends NamePrefix> t) {
				for(NamePrefix s: t.values()) {
					getNamePrefixes().add(s);
				}				
			}

			@Override
			public NamePrefix remove(Object key) {
				NamePrefix removed=null;
				if(key instanceof String) {
					for(NamePrefix s: getNamePrefixes()) {
						if(s.getName().equals(key)){
							if(getNamePrefixes().remove(s)) {
								removed = s;
							}
						}
					}
				}
				return removed;
			}
			
		};
		return namePrefixMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS__PALETTE_PATH_ENTRIES:
				return ((InternalEList<?>)getPalettePathEntries()).basicRemove(otherEnd, msgs);
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS__SELECTED_PROCESS_PATHS:
				return ((InternalEList<?>)getSelectedProcessPaths()).basicRemove(otherEnd, msgs);
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS__NAME_PREFIXES:
				return ((InternalEList<?>)getNamePrefixes()).basicRemove(otherEnd, msgs);
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
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS__BUILD_FOLDER:
				return getBuildFolder();
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS__PALETTE_PATH_ENTRIES:
				return getPalettePathEntries();
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS__SELECTED_PROCESS_PATHS:
				return getSelectedProcessPaths();
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS__PROCESS_PREFIX:
				return getProcessPrefix();
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS__RULE_PREFIX:
				return getRulePrefix();
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS__RULE_FUNCTION_PREFIX:
				return getRuleFunctionPrefix();
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS__CONCEPT_PREFIX:
				return getConceptPrefix();
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS__EVENT_PREFIX:
				return getEventPrefix();
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS__TIME_EVENT_PREFIX:
				return getTimeEventPrefix();
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS__SCORECARD_PREFIX:
				return getScorecardPrefix();
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS__NAME_PREFIXES:
				return getNamePrefixes();
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
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS__BUILD_FOLDER:
				setBuildFolder((String)newValue);
				return;
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS__PALETTE_PATH_ENTRIES:
				getPalettePathEntries().clear();
				getPalettePathEntries().addAll((Collection<? extends BpmnPalettePathEntry>)newValue);
				return;
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS__SELECTED_PROCESS_PATHS:
				getSelectedProcessPaths().clear();
				getSelectedProcessPaths().addAll((Collection<? extends BpmnProcessPathEntry>)newValue);
				return;
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS__PROCESS_PREFIX:
				setProcessPrefix((String)newValue);
				return;
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS__RULE_PREFIX:
				setRulePrefix((String)newValue);
				return;
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS__RULE_FUNCTION_PREFIX:
				setRuleFunctionPrefix((String)newValue);
				return;
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS__CONCEPT_PREFIX:
				setConceptPrefix((String)newValue);
				return;
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS__EVENT_PREFIX:
				setEventPrefix((String)newValue);
				return;
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS__TIME_EVENT_PREFIX:
				setTimeEventPrefix((String)newValue);
				return;
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS__SCORECARD_PREFIX:
				setScorecardPrefix((String)newValue);
				return;
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS__NAME_PREFIXES:
				getNamePrefixes().clear();
				getNamePrefixes().addAll((Collection<? extends NamePrefix>)newValue);
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
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS__BUILD_FOLDER:
				setBuildFolder(BUILD_FOLDER_EDEFAULT);
				return;
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS__PALETTE_PATH_ENTRIES:
				getPalettePathEntries().clear();
				return;
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS__SELECTED_PROCESS_PATHS:
				getSelectedProcessPaths().clear();
				return;
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS__PROCESS_PREFIX:
				setProcessPrefix(PROCESS_PREFIX_EDEFAULT);
				return;
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS__RULE_PREFIX:
				setRulePrefix(RULE_PREFIX_EDEFAULT);
				return;
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS__RULE_FUNCTION_PREFIX:
				setRuleFunctionPrefix(RULE_FUNCTION_PREFIX_EDEFAULT);
				return;
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS__CONCEPT_PREFIX:
				setConceptPrefix(CONCEPT_PREFIX_EDEFAULT);
				return;
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS__EVENT_PREFIX:
				setEventPrefix(EVENT_PREFIX_EDEFAULT);
				return;
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS__TIME_EVENT_PREFIX:
				setTimeEventPrefix(TIME_EVENT_PREFIX_EDEFAULT);
				return;
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS__SCORECARD_PREFIX:
				setScorecardPrefix(SCORECARD_PREFIX_EDEFAULT);
				return;
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS__NAME_PREFIXES:
				getNamePrefixes().clear();
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
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS__BUILD_FOLDER:
				return BUILD_FOLDER_EDEFAULT == null ? buildFolder != null : !BUILD_FOLDER_EDEFAULT.equals(buildFolder);
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS__PALETTE_PATH_ENTRIES:
				return palettePathEntries != null && !palettePathEntries.isEmpty();
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS__SELECTED_PROCESS_PATHS:
				return selectedProcessPaths != null && !selectedProcessPaths.isEmpty();
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS__PROCESS_PREFIX:
				return PROCESS_PREFIX_EDEFAULT == null ? processPrefix != null : !PROCESS_PREFIX_EDEFAULT.equals(processPrefix);
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS__RULE_PREFIX:
				return RULE_PREFIX_EDEFAULT == null ? rulePrefix != null : !RULE_PREFIX_EDEFAULT.equals(rulePrefix);
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS__RULE_FUNCTION_PREFIX:
				return RULE_FUNCTION_PREFIX_EDEFAULT == null ? ruleFunctionPrefix != null : !RULE_FUNCTION_PREFIX_EDEFAULT.equals(ruleFunctionPrefix);
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS__CONCEPT_PREFIX:
				return CONCEPT_PREFIX_EDEFAULT == null ? conceptPrefix != null : !CONCEPT_PREFIX_EDEFAULT.equals(conceptPrefix);
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS__EVENT_PREFIX:
				return EVENT_PREFIX_EDEFAULT == null ? eventPrefix != null : !EVENT_PREFIX_EDEFAULT.equals(eventPrefix);
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS__TIME_EVENT_PREFIX:
				return TIME_EVENT_PREFIX_EDEFAULT == null ? timeEventPrefix != null : !TIME_EVENT_PREFIX_EDEFAULT.equals(timeEventPrefix);
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS__SCORECARD_PREFIX:
				return SCORECARD_PREFIX_EDEFAULT == null ? scorecardPrefix != null : !SCORECARD_PREFIX_EDEFAULT.equals(scorecardPrefix);
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS__NAME_PREFIXES:
				return namePrefixes != null && !namePrefixes.isEmpty();
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
		result.append(" (buildFolder: ");
		result.append(buildFolder);
		result.append(", processPrefix: ");
		result.append(processPrefix);
		result.append(", rulePrefix: ");
		result.append(rulePrefix);
		result.append(", ruleFunctionPrefix: ");
		result.append(ruleFunctionPrefix);
		result.append(", conceptPrefix: ");
		result.append(conceptPrefix);
		result.append(", eventPrefix: ");
		result.append(eventPrefix);
		result.append(", timeEventPrefix: ");
		result.append(timeEventPrefix);
		result.append(", scorecardPrefix: ");
		result.append(scorecardPrefix);
		result.append(')');
		return result.toString();
	}

} //BpmnProcessSettingsImpl
