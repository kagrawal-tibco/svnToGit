/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.common.configuration;

import org.eclipse.emf.common.util.EList;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Classpath Entry</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.common.configuration.JavaClasspathEntry#getClasspathEntryType <em>Classpath Entry Type</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.JavaClasspathEntry#getSourceFolder <em>Source Folder</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.JavaClasspathEntry#getInclusionPattern <em>Inclusion Pattern</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.JavaClasspathEntry#getExclusionPattern <em>Exclusion Pattern</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.JavaClasspathEntry#getOutputLocation <em>Output Location</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.JavaClasspathEntry#getSourceAttachmentPath <em>Source Attachment Path</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.JavaClasspathEntry#getSourceAttachmentRootPath <em>Source Attachment Root Path</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getJavaClasspathEntry()
 * @model
 * @generated
 */
public interface JavaClasspathEntry extends BuildPathEntry {
	/**
	 * Returns the value of the '<em><b>Classpath Entry Type</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.cep.studio.common.configuration.JAVA_CLASSPATH_ENTRY_TYPE}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Classpath Entry Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Classpath Entry Type</em>' attribute.
	 * @see com.tibco.cep.studio.common.configuration.JAVA_CLASSPATH_ENTRY_TYPE
	 * @see #setClasspathEntryType(JAVA_CLASSPATH_ENTRY_TYPE)
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getJavaClasspathEntry_ClasspathEntryType()
	 * @model
	 * @generated
	 */
	JAVA_CLASSPATH_ENTRY_TYPE getClasspathEntryType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.configuration.JavaClasspathEntry#getClasspathEntryType <em>Classpath Entry Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Classpath Entry Type</em>' attribute.
	 * @see com.tibco.cep.studio.common.configuration.JAVA_CLASSPATH_ENTRY_TYPE
	 * @see #getClasspathEntryType()
	 * @generated
	 */
	void setClasspathEntryType(JAVA_CLASSPATH_ENTRY_TYPE value);

	/**
	 * Returns the value of the '<em><b>Source Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Folder</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Folder</em>' attribute.
	 * @see #setSourceFolder(String)
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getJavaClasspathEntry_SourceFolder()
	 * @model
	 * @generated
	 */
	String getSourceFolder();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.configuration.JavaClasspathEntry#getSourceFolder <em>Source Folder</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Folder</em>' attribute.
	 * @see #getSourceFolder()
	 * @generated
	 */
	void setSourceFolder(String value);

	/**
	 * Returns the value of the '<em><b>Inclusion Pattern</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Inclusion Pattern</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Inclusion Pattern</em>' attribute list.
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getJavaClasspathEntry_InclusionPattern()
	 * @model
	 * @generated
	 */
	EList<String> getInclusionPattern();

	/**
	 * Returns the value of the '<em><b>Exclusion Pattern</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exclusion Pattern</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exclusion Pattern</em>' attribute list.
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getJavaClasspathEntry_ExclusionPattern()
	 * @model
	 * @generated
	 */
	EList<String> getExclusionPattern();

	/**
	 * Returns the value of the '<em><b>Output Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Output Location</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Output Location</em>' attribute.
	 * @see #setOutputLocation(String)
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getJavaClasspathEntry_OutputLocation()
	 * @model
	 * @generated
	 */
	String getOutputLocation();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.configuration.JavaClasspathEntry#getOutputLocation <em>Output Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Output Location</em>' attribute.
	 * @see #getOutputLocation()
	 * @generated
	 */
	void setOutputLocation(String value);

	/**
	 * Returns the value of the '<em><b>Source Attachment Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Attachment Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Attachment Path</em>' attribute.
	 * @see #setSourceAttachmentPath(String)
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getJavaClasspathEntry_SourceAttachmentPath()
	 * @model
	 * @generated
	 */
	String getSourceAttachmentPath();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.configuration.JavaClasspathEntry#getSourceAttachmentPath <em>Source Attachment Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Attachment Path</em>' attribute.
	 * @see #getSourceAttachmentPath()
	 * @generated
	 */
	void setSourceAttachmentPath(String value);

	/**
	 * Returns the value of the '<em><b>Source Attachment Root Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Attachment Root Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Attachment Root Path</em>' attribute.
	 * @see #setSourceAttachmentRootPath(String)
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getJavaClasspathEntry_SourceAttachmentRootPath()
	 * @model
	 * @generated
	 */
	String getSourceAttachmentRootPath();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.configuration.JavaClasspathEntry#getSourceAttachmentRootPath <em>Source Attachment Root Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Attachment Root Path</em>' attribute.
	 * @see #getSourceAttachmentRootPath()
	 * @generated
	 */
	void setSourceAttachmentRootPath(String value);

} // JavaClasspathEntry
