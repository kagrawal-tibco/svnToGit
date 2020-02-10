/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.common.configuration;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Build Path Entry</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.common.configuration.BuildPathEntry#getEntryType <em>Entry Type</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.BuildPathEntry#getPath <em>Path</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.BuildPathEntry#getTimestamp <em>Timestamp</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.BuildPathEntry#isVar <em>Var</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.BuildPathEntry#isReadOnly <em>Read Only</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.BuildPathEntry#isDeprecated <em>Deprecated</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.BuildPathEntry#getResolvedPath <em>Resolved Path</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getBuildPathEntry()
 * @model
 * @generated
 */
public interface BuildPathEntry extends ProjectConfigurationEntry {
	/**
	 * Returns the value of the '<em><b>Entry Type</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.cep.studio.common.configuration.LIBRARY_PATH_TYPE}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entry Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entry Type</em>' attribute.
	 * @see com.tibco.cep.studio.common.configuration.LIBRARY_PATH_TYPE
	 * @see #setEntryType(LIBRARY_PATH_TYPE)
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getBuildPathEntry_EntryType()
	 * @model
	 * @generated
	 */
	LIBRARY_PATH_TYPE getEntryType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.configuration.BuildPathEntry#getEntryType <em>Entry Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Entry Type</em>' attribute.
	 * @see com.tibco.cep.studio.common.configuration.LIBRARY_PATH_TYPE
	 * @see #getEntryType()
	 * @generated
	 */
	void setEntryType(LIBRARY_PATH_TYPE value);

	/**
	 * Returns the value of the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Path</em>' attribute.
	 * @see #setPath(String)
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getBuildPathEntry_Path()
	 * @model
	 * @generated
	 */
	String getPath();
	
	/**
	 * Returns the value of the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If path contains variable and input parameter is true,
	 * It resolve the path before returning it
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Path</em>' attribute.
	 * @see #setPath(String)
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getBuildPathEntry_Path()
	 * @model
	 * @generated NOT
	 */
	String getPath(boolean resolve);

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.configuration.BuildPathEntry#getPath <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Path</em>' attribute.
	 * @see #getPath()
	 * @generated
	 */
	void setPath(String value);
	
	/**
	 * Sets the resolved value of the '{@link com.tibco.cep.studio.common.configuration.BuildPathEntry#getPath <em>Path</em>}' attribute, in case variable used in classpath.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Path</em>' attribute.
	 * @see #getPath()
	 * @generated NOT
	 */
	void setResolvedPath(String value);

	/**
	 * Returns the value of the '<em><b>Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Timestamp</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Timestamp</em>' attribute.
	 * @see #setTimestamp(long)
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getBuildPathEntry_Timestamp()
	 * @model
	 * @generated
	 */
	long getTimestamp();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.configuration.BuildPathEntry#getTimestamp <em>Timestamp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Timestamp</em>' attribute.
	 * @see #getTimestamp()
	 * @generated
	 */
	void setTimestamp(long value);

	/**
	 * Returns the value of the '<em><b>Var</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Var</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Var</em>' attribute.
	 * @see #setVar(boolean)
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getBuildPathEntry_Var()
	 * @model default="false"
	 * @generated
	 */
	boolean isVar();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.configuration.BuildPathEntry#isVar <em>Var</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Var</em>' attribute.
	 * @see #isVar()
	 * @generated
	 */
	void setVar(boolean value);

	/**
	 * Returns the value of the '<em><b>Read Only</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Read Only</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Read Only</em>' attribute.
	 * @see #setReadOnly(boolean)
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getBuildPathEntry_ReadOnly()
	 * @model default="false"
	 * @generated
	 */
	boolean isReadOnly();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.configuration.BuildPathEntry#isReadOnly <em>Read Only</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Read Only</em>' attribute.
	 * @see #isReadOnly()
	 * @generated
	 */
	void setReadOnly(boolean value);

	/**
	 * Returns the value of the '<em><b>Deprecated</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Deprecated</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Deprecated</em>' attribute.
	 * @see #setDeprecated(boolean)
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getBuildPathEntry_Deprecated()
	 * @model default="false"
	 * @generated
	 */
	boolean isDeprecated();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.configuration.BuildPathEntry#isDeprecated <em>Deprecated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Deprecated</em>' attribute.
	 * @see #isDeprecated()
	 * @generated
	 */
	void setDeprecated(boolean value);

	/**
	 * Returns the value of the '<em><b>Resolved Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resolved Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resolved Path</em>' attribute.
	 * @see #setResolvedPath(String)
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getBuildPathEntry_ResolvedPath()
	 * @model transient="true"
	 * @generated
	 */
	String getResolvedPath();

} // BuildPathEntry
