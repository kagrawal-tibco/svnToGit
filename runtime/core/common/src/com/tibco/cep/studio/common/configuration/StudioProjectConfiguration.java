/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.common.configuration;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Studio Project Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.common.configuration.StudioProjectConfiguration#getThirdpartyLibEntries <em>Thirdparty Lib Entries</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.StudioProjectConfiguration#getCoreInternalLibEntries <em>Core Internal Lib Entries</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.StudioProjectConfiguration#getCustomFunctionLibEntries <em>Custom Function Lib Entries</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.StudioProjectConfiguration#getProjectLibEntries <em>Project Lib Entries</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.StudioProjectConfiguration#getJavaClasspathEntries <em>Java Classpath Entries</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.StudioProjectConfiguration#getJavaSourceFolderEntries <em>Java Source Folder Entries</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.StudioProjectConfiguration#getEnterpriseArchiveConfiguration <em>Enterprise Archive Configuration</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.StudioProjectConfiguration#getBpmnProcessSettings <em>Bpmn Process Settings</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.StudioProjectConfiguration#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.StudioProjectConfiguration#getVersion <em>Version</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.StudioProjectConfiguration#getBuild <em>Build</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.StudioProjectConfiguration#getFileTimeStamp <em>File Time Stamp</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.StudioProjectConfiguration#getXpathVersion <em>Xpath Version</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getStudioProjectConfiguration()
 * @model
 * @generated
 */
public interface StudioProjectConfiguration extends EObject {
	/**
	 * Returns the value of the '<em><b>Thirdparty Lib Entries</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.studio.common.configuration.ThirdPartyLibEntry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Thirdparty Lib Entries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Thirdparty Lib Entries</em>' containment reference list.
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getStudioProjectConfiguration_ThirdpartyLibEntries()
	 * @model containment="true" keys="path"
	 * @generated
	 */
	EList<ThirdPartyLibEntry> getThirdpartyLibEntries();

	/**
	 * Returns the value of the '<em><b>Core Internal Lib Entries</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.studio.common.configuration.CoreJavaLibEntry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Core Internal Lib Entries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Core Internal Lib Entries</em>' containment reference list.
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getStudioProjectConfiguration_CoreInternalLibEntries()
	 * @model containment="true" keys="path" transient="true"
	 * @generated
	 */
	EList<CoreJavaLibEntry> getCoreInternalLibEntries();

	/**
	 * Returns the value of the '<em><b>Custom Function Lib Entries</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.studio.common.configuration.CustomFunctionLibEntry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Custom Function Lib Entries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Custom Function Lib Entries</em>' containment reference list.
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getStudioProjectConfiguration_CustomFunctionLibEntries()
	 * @model containment="true" keys="path"
	 * @generated
	 */
	EList<CustomFunctionLibEntry> getCustomFunctionLibEntries();

	/**
	 * Returns the value of the '<em><b>Project Lib Entries</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.studio.common.configuration.ProjectLibraryEntry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Project Lib Entries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Project Lib Entries</em>' containment reference list.
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getStudioProjectConfiguration_ProjectLibEntries()
	 * @model containment="true" keys="path"
	 * @generated
	 */
	EList<ProjectLibraryEntry> getProjectLibEntries();

	/**
	 * Returns the value of the '<em><b>Java Classpath Entries</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.studio.common.configuration.JavaClasspathEntry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Java Classpath Entries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Java Classpath Entries</em>' containment reference list.
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getStudioProjectConfiguration_JavaClasspathEntries()
	 * @model containment="true"
	 * @generated
	 */
	EList<JavaClasspathEntry> getJavaClasspathEntries();

	/**
	 * Returns the value of the '<em><b>Java Source Folder Entries</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.studio.common.configuration.JavaSourceFolderEntry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Java Source Folder Entries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Java Source Folder Entries</em>' containment reference list.
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getStudioProjectConfiguration_JavaSourceFolderEntries()
	 * @model containment="true"
	 * @generated
	 */
	EList<JavaSourceFolderEntry> getJavaSourceFolderEntries();

	/**
	 * Returns the value of the '<em><b>Enterprise Archive Configuration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enterprise Archive Configuration</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enterprise Archive Configuration</em>' containment reference.
	 * @see #setEnterpriseArchiveConfiguration(EnterpriseArchiveEntry)
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getStudioProjectConfiguration_EnterpriseArchiveConfiguration()
	 * @model containment="true"
	 * @generated
	 */
	EnterpriseArchiveEntry getEnterpriseArchiveConfiguration();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.configuration.StudioProjectConfiguration#getEnterpriseArchiveConfiguration <em>Enterprise Archive Configuration</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enterprise Archive Configuration</em>' containment reference.
	 * @see #getEnterpriseArchiveConfiguration()
	 * @generated
	 */
	void setEnterpriseArchiveConfiguration(EnterpriseArchiveEntry value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getStudioProjectConfiguration_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.configuration.StudioProjectConfiguration#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version</em>' attribute.
	 * @see #setVersion(String)
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getStudioProjectConfiguration_Version()
	 * @model
	 * @generated
	 */
	String getVersion();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.configuration.StudioProjectConfiguration#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(String value);

	/**
	 * Returns the value of the '<em><b>Build</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Build</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Build</em>' attribute.
	 * @see #setBuild(String)
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getStudioProjectConfiguration_Build()
	 * @model
	 * @generated
	 */
	String getBuild();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.configuration.StudioProjectConfiguration#getBuild <em>Build</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Build</em>' attribute.
	 * @see #getBuild()
	 * @generated
	 */
	void setBuild(String value);

	/**
	 * Returns the value of the '<em><b>File Time Stamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>File Time Stamp</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>File Time Stamp</em>' attribute.
	 * @see #setFileTimeStamp(long)
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getStudioProjectConfiguration_FileTimeStamp()
	 * @model transient="true"
	 * @generated
	 */
	long getFileTimeStamp();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.configuration.StudioProjectConfiguration#getFileTimeStamp <em>File Time Stamp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>File Time Stamp</em>' attribute.
	 * @see #getFileTimeStamp()
	 * @generated
	 */
	void setFileTimeStamp(long value);

	/**
	 * Returns the value of the '<em><b>Xpath Version</b></em>' attribute.
	 * The default value is <code>"1.0"</code>.
	 * The literals are from the enumeration {@link com.tibco.cep.studio.common.configuration.XPATH_VERSION}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xpath Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xpath Version</em>' attribute.
	 * @see com.tibco.cep.studio.common.configuration.XPATH_VERSION
	 * @see #setXpathVersion(XPATH_VERSION)
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getStudioProjectConfiguration_XpathVersion()
	 * @model default="1.0"
	 * @generated
	 */
	XPATH_VERSION getXpathVersion();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.configuration.StudioProjectConfiguration#getXpathVersion <em>Xpath Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Xpath Version</em>' attribute.
	 * @see com.tibco.cep.studio.common.configuration.XPATH_VERSION
	 * @see #getXpathVersion()
	 * @generated
	 */
	void setXpathVersion(XPATH_VERSION value);

	/**
	 * Returns the value of the '<em><b>Bpmn Process Settings</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Bpmn Process Settings</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Bpmn Process Settings</em>' containment reference.
	 * @see #setBpmnProcessSettings(BpmnProcessSettings)
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getStudioProjectConfiguration_BpmnProcessSettings()
	 * @model containment="true"
	 * @generated
	 */
	BpmnProcessSettings getBpmnProcessSettings();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.configuration.StudioProjectConfiguration#getBpmnProcessSettings <em>Bpmn Process Settings</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Bpmn Process Settings</em>' containment reference.
	 * @see #getBpmnProcessSettings()
	 * @generated
	 */
	void setBpmnProcessSettings(BpmnProcessSettings value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model many="false"
	 * @generated NOT
	 */
	EList<BuildPathEntry> getEntriesByType(LIBRARY_PATH_TYPE type);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated NOT
	 */
	boolean removeEntriesByType(BuildPathEntry element, LIBRARY_PATH_TYPE type);
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated NOT
	 */
	public int getIndexOfEntryByType(BuildPathEntry element, LIBRARY_PATH_TYPE type);

} // StudioProjectConfiguration
