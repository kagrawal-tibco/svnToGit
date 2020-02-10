/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.common.configuration;

import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Bpmn Process Settings</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.common.configuration.BpmnProcessSettings#getBuildFolder <em>Build Folder</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.BpmnProcessSettings#getPalettePathEntries <em>Palette Path Entries</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.BpmnProcessSettings#getSelectedProcessPaths <em>Selected Process Paths</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.BpmnProcessSettings#getProcessPrefix <em>Process Prefix</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.BpmnProcessSettings#getRulePrefix <em>Rule Prefix</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.BpmnProcessSettings#getRuleFunctionPrefix <em>Rule Function Prefix</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.BpmnProcessSettings#getConceptPrefix <em>Concept Prefix</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.BpmnProcessSettings#getEventPrefix <em>Event Prefix</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.BpmnProcessSettings#getTimeEventPrefix <em>Time Event Prefix</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.BpmnProcessSettings#getScorecardPrefix <em>Scorecard Prefix</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.BpmnProcessSettings#getNamePrefixes <em>Name Prefixes</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getBpmnProcessSettings()
 * @model
 * @generated
 */
public interface BpmnProcessSettings extends EObject {
	/**
	 * Returns the value of the '<em><b>Build Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Build Folder</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Build Folder</em>' attribute.
	 * @see #setBuildFolder(String)
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getBpmnProcessSettings_BuildFolder()
	 * @model
	 * @generated
	 */
	String getBuildFolder();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.configuration.BpmnProcessSettings#getBuildFolder <em>Build Folder</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Build Folder</em>' attribute.
	 * @see #getBuildFolder()
	 * @generated
	 */
	void setBuildFolder(String value);

	/**
	 * Returns the value of the '<em><b>Palette Path Entries</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.studio.common.configuration.BpmnPalettePathEntry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Palette Path Entries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Palette Path Entries</em>' containment reference list.
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getBpmnProcessSettings_PalettePathEntries()
	 * @model containment="true"
	 * @generated
	 */
	EList<BpmnPalettePathEntry> getPalettePathEntries();

	/**
	 * Returns the value of the '<em><b>Selected Process Paths</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.studio.common.configuration.BpmnProcessPathEntry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Selected Process Paths</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Selected Process Paths</em>' containment reference list.
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getBpmnProcessSettings_SelectedProcessPaths()
	 * @model containment="true"
	 * @generated
	 */
	EList<BpmnProcessPathEntry> getSelectedProcessPaths();

	/**
	 * Returns the value of the '<em><b>Process Prefix</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Process Prefix</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Process Prefix</em>' attribute.
	 * @see #setProcessPrefix(String)
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getBpmnProcessSettings_ProcessPrefix()
	 * @model
	 * @generated
	 */
	String getProcessPrefix();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.configuration.BpmnProcessSettings#getProcessPrefix <em>Process Prefix</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Process Prefix</em>' attribute.
	 * @see #getProcessPrefix()
	 * @generated
	 */
	void setProcessPrefix(String value);

	/**
	 * Returns the value of the '<em><b>Rule Prefix</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule Prefix</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule Prefix</em>' attribute.
	 * @see #setRulePrefix(String)
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getBpmnProcessSettings_RulePrefix()
	 * @model
	 * @generated
	 */
	String getRulePrefix();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.configuration.BpmnProcessSettings#getRulePrefix <em>Rule Prefix</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule Prefix</em>' attribute.
	 * @see #getRulePrefix()
	 * @generated
	 */
	void setRulePrefix(String value);

	/**
	 * Returns the value of the '<em><b>Rule Function Prefix</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule Function Prefix</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule Function Prefix</em>' attribute.
	 * @see #setRuleFunctionPrefix(String)
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getBpmnProcessSettings_RuleFunctionPrefix()
	 * @model
	 * @generated
	 */
	String getRuleFunctionPrefix();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.configuration.BpmnProcessSettings#getRuleFunctionPrefix <em>Rule Function Prefix</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule Function Prefix</em>' attribute.
	 * @see #getRuleFunctionPrefix()
	 * @generated
	 */
	void setRuleFunctionPrefix(String value);

	/**
	 * Returns the value of the '<em><b>Concept Prefix</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Concept Prefix</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Concept Prefix</em>' attribute.
	 * @see #setConceptPrefix(String)
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getBpmnProcessSettings_ConceptPrefix()
	 * @model
	 * @generated
	 */
	String getConceptPrefix();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.configuration.BpmnProcessSettings#getConceptPrefix <em>Concept Prefix</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Concept Prefix</em>' attribute.
	 * @see #getConceptPrefix()
	 * @generated
	 */
	void setConceptPrefix(String value);

	/**
	 * Returns the value of the '<em><b>Event Prefix</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Event Prefix</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Event Prefix</em>' attribute.
	 * @see #setEventPrefix(String)
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getBpmnProcessSettings_EventPrefix()
	 * @model
	 * @generated
	 */
	String getEventPrefix();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.configuration.BpmnProcessSettings#getEventPrefix <em>Event Prefix</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Event Prefix</em>' attribute.
	 * @see #getEventPrefix()
	 * @generated
	 */
	void setEventPrefix(String value);

	/**
	 * Returns the value of the '<em><b>Time Event Prefix</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Time Event Prefix</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Time Event Prefix</em>' attribute.
	 * @see #setTimeEventPrefix(String)
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getBpmnProcessSettings_TimeEventPrefix()
	 * @model
	 * @generated
	 */
	String getTimeEventPrefix();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.configuration.BpmnProcessSettings#getTimeEventPrefix <em>Time Event Prefix</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Time Event Prefix</em>' attribute.
	 * @see #getTimeEventPrefix()
	 * @generated
	 */
	void setTimeEventPrefix(String value);

	/**
	 * Returns the value of the '<em><b>Scorecard Prefix</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Scorecard Prefix</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Scorecard Prefix</em>' attribute.
	 * @see #setScorecardPrefix(String)
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getBpmnProcessSettings_ScorecardPrefix()
	 * @model
	 * @generated
	 */
	String getScorecardPrefix();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.configuration.BpmnProcessSettings#getScorecardPrefix <em>Scorecard Prefix</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Scorecard Prefix</em>' attribute.
	 * @see #getScorecardPrefix()
	 * @generated
	 */
	void setScorecardPrefix(String value);

	/**
	 * Returns the value of the '<em><b>Name Prefixes</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.studio.common.configuration.NamePrefix}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name Prefixes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name Prefixes</em>' containment reference list.
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getBpmnProcessSettings_NamePrefixes()
	 * @model containment="true" keys="name"
	 * @generated
	 */
	EList<NamePrefix> getNamePrefixes();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	Map<String, NamePrefix> getNamePrefixMap();

} // BpmnProcessSettings
