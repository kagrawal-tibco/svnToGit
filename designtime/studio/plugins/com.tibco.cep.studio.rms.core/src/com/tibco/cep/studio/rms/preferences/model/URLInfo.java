/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.rms.preferences.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>URL Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.rms.preferences.model.URLInfo#getUrls <em>Urls</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.rms.preferences.model.PreferencesPackage#getURLInfo()
 * @model abstract="true"
 * @generated
 */
public interface URLInfo extends EObject {
	/**
	 * Returns the value of the '<em><b>Urls</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.studio.rms.preferences.model.URL}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Urls</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Urls</em>' containment reference list.
	 * @see com.tibco.cep.studio.rms.preferences.model.PreferencesPackage#getURLInfo_Urls()
	 * @model containment="true"
	 * @generated
	 */
	EList<URL> getUrls();

} // URLInfo
