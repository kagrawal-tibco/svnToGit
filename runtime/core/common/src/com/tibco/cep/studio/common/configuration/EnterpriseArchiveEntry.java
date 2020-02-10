/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.common.configuration;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Enterprise Archive Entry</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry#getAuthor <em>Author</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry#getVersion <em>Version</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry#getPath <em>Path</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry#isIncludeServiceLevelGlobalVars <em>Include Service Level Global Vars</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry#isDebug <em>Debug</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry#getTempOutputPath <em>Temp Output Path</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry#isOverwrite <em>Overwrite</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry#isDeleteTempFiles <em>Delete Temp Files</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry#isBuildClassesOnly <em>Build Classes Only</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getEnterpriseArchiveEntry()
 * @model
 * @generated
 */
public interface EnterpriseArchiveEntry extends ProjectConfigurationEntry {
	/**
	 * @generated NOT
	 */
	public static final String BUILD_OUTPUT_FOLDER_NAME = "generated";

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getEnterpriseArchiveEntry_Name()
	 * @model default=""
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Author</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Author</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Author</em>' attribute.
	 * @see #setAuthor(String)
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getEnterpriseArchiveEntry_Author()
	 * @model default=""
	 * @generated
	 */
	String getAuthor();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry#getAuthor <em>Author</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Author</em>' attribute.
	 * @see #getAuthor()
	 * @generated
	 */
	void setAuthor(String value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getEnterpriseArchiveEntry_Description()
	 * @model default=""
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Version</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version</em>' attribute.
	 * @see #setVersion(int)
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getEnterpriseArchiveEntry_Version()
	 * @model default="0"
	 * @generated
	 */
	int getVersion();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(int value);

	/**
	 * Returns the value of the '<em><b>Path</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Path</em>' attribute.
	 * @see #setPath(String)
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getEnterpriseArchiveEntry_Path()
	 * @model default=""
	 * @generated
	 */
	String getPath();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry#getPath <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Path</em>' attribute.
	 * @see #getPath()
	 * @generated
	 */
	void setPath(String value);

	/**
	 * Returns the value of the '<em><b>Include Service Level Global Vars</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Include Service Level Global Vars</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Include Service Level Global Vars</em>' attribute.
	 * @see #setIncludeServiceLevelGlobalVars(boolean)
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getEnterpriseArchiveEntry_IncludeServiceLevelGlobalVars()
	 * @model default="true"
	 * @generated
	 */
	boolean isIncludeServiceLevelGlobalVars();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry#isIncludeServiceLevelGlobalVars <em>Include Service Level Global Vars</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Include Service Level Global Vars</em>' attribute.
	 * @see #isIncludeServiceLevelGlobalVars()
	 * @generated
	 */
	void setIncludeServiceLevelGlobalVars(boolean value);

	/**
	 * Returns the value of the '<em><b>Debug</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Debug</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Debug</em>' attribute.
	 * @see #setDebug(boolean)
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getEnterpriseArchiveEntry_Debug()
	 * @model default="true"
	 * @generated
	 */
	boolean isDebug();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry#isDebug <em>Debug</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Debug</em>' attribute.
	 * @see #isDebug()
	 * @generated
	 */
	void setDebug(boolean value);

	/**
	 * Returns the value of the '<em><b>Temp Output Path</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Output Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Temp Output Path</em>' attribute.
	 * @see #setTempOutputPath(String)
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getEnterpriseArchiveEntry_TempOutputPath()
	 * @model default=""
	 * @generated
	 */
	String getTempOutputPath();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry#getTempOutputPath <em>Temp Output Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Temp Output Path</em>' attribute.
	 * @see #getTempOutputPath()
	 * @generated
	 */
	void setTempOutputPath(String value);

	/**
	 * Returns the value of the '<em><b>Overwrite</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Overwrite</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Overwrite</em>' attribute.
	 * @see #setOverwrite(boolean)
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getEnterpriseArchiveEntry_Overwrite()
	 * @model
	 * @generated
	 */
	boolean isOverwrite();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry#isOverwrite <em>Overwrite</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Overwrite</em>' attribute.
	 * @see #isOverwrite()
	 * @generated
	 */
	void setOverwrite(boolean value);

	/**
	 * Returns the value of the '<em><b>Delete Temp Files</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Delete Temp Files</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Delete Temp Files</em>' attribute.
	 * @see #setDeleteTempFiles(boolean)
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getEnterpriseArchiveEntry_DeleteTempFiles()
	 * @model default="true"
	 * @generated
	 */
	boolean isDeleteTempFiles();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry#isDeleteTempFiles <em>Delete Temp Files</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Delete Temp Files</em>' attribute.
	 * @see #isDeleteTempFiles()
	 * @generated
	 */
	void setDeleteTempFiles(boolean value);

	/**
	 * Returns the value of the '<em><b>Build Classes Only</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Build Classes Only</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Build Classes Only</em>' attribute.
	 * @see #setBuildClassesOnly(boolean)
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getEnterpriseArchiveEntry_BuildClassesOnly()
	 * @model default="true"
	 * @generated
	 */
	boolean isBuildClassesOnly();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry#isBuildClassesOnly <em>Build Classes Only</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Build Classes Only</em>' attribute.
	 * @see #isBuildClassesOnly()
	 * @generated
	 */
	void setBuildClassesOnly(boolean value);

} // EnterpriseArchiveEntry
