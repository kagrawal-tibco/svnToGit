/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.archive.config;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Archives</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.config.Archives#getBeArchive <em>Be Archive</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.archive.config.ConfigPackage#getArchives()
 * @model
 * @generated
 */
public interface Archives extends EObject {
	/**
	 * Returns the value of the '<em><b>Be Archive</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Be Archive</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Be Archive</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.archive.config.ConfigPackage#getArchives_BeArchive()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<BusinessEventsArchiveResource> getBeArchive();

} // Archives
