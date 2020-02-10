/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.serversettingsmodel;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Checkout URL</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.serversettingsmodel.CheckoutURL#getURL <em>URL</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.serversettingsmodel.ServersettingsmodelPackage#getCheckoutURL()
 * @model
 * @generated
 */
public interface CheckoutURL extends EObject {
	/**
	 * Returns the value of the '<em><b>URL</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>URL</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>URL</em>' attribute list.
	 * @see com.tibco.cep.serversettingsmodel.ServersettingsmodelPackage#getCheckoutURL_URL()
	 * @model
	 * @generated
	 */
	EList<String> getURL();

} // CheckoutURL
