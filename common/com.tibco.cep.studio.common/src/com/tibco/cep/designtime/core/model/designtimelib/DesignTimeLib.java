/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.designtimelib;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Design Time Lib</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.designtimelib.DesignTimeLib#getDesignTimeLibEntry <em>Design Time Lib Entry</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.designtimelib.DesigntimelibPackage#getDesignTimeLib()
 * @model
 * @generated
 */
public interface DesignTimeLib extends EObject {
	/**
	 * Returns the value of the '<em><b>Design Time Lib Entry</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.designtimelib.DesignTimeLibEntry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Design Time Lib Entry</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Design Time Lib Entry</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.designtimelib.DesigntimelibPackage#getDesignTimeLib_DesignTimeLibEntry()
	 * @model containment="true"
	 * @generated
	 */
	EList<DesignTimeLibEntry> getDesignTimeLibEntry();

} // DesignTimeLib
