/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.common.configuration;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Lib Entry</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.common.configuration.JavaLibEntry#getNativeLibraryLocation <em>Native Library Location</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getJavaLibEntry()
 * @model
 * @generated
 */
public interface JavaLibEntry extends BuildPathEntry {

	/**
	 * Returns the value of the '<em><b>Native Library Location</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Native Library Location</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Native Library Location</em>' containment reference.
	 * @see #setNativeLibraryLocation(NativeLibraryPath)
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getJavaLibEntry_NativeLibraryLocation()
	 * @model containment="true"
	 * @generated
	 */
	NativeLibraryPath getNativeLibraryLocation();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.configuration.JavaLibEntry#getNativeLibraryLocation <em>Native Library Location</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Native Library Location</em>' containment reference.
	 * @see #getNativeLibraryLocation()
	 * @generated
	 */
	void setNativeLibraryLocation(NativeLibraryPath value);
} // JavaLibEntry
