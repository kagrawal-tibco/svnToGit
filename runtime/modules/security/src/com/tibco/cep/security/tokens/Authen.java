/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.security.tokens;

import java.io.Serializable;
import java.util.Date;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Authen</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.security.tokens.Authen#getSessionToken <em>Session Token</em>}</li>
 *   <li>{@link com.tibco.cep.security.tokens.Authen#getAuthInstant <em>Auth Instant</em>}</li>
 *   <li>{@link com.tibco.cep.security.tokens.Authen#getAuthenticatedBy <em>Authenticated By</em>}</li>
 *   <li>{@link com.tibco.cep.security.tokens.Authen#getTimeToLive <em>Time To Live</em>}</li>
 *   <li>{@link com.tibco.cep.security.tokens.Authen#getUser <em>User</em>}</li>
 * </ul>
 * </p>
 *
 */
public interface Authen extends Serializable {
	/**
	 * Returns the value of the '<em><b>Session Token</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Session Token</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Session Token</em>' attribute.
	 * @see #setSessionToken(String)
	 */
	String getSessionToken();

	/**
	 * Sets the value of the '{@link com.tibco.cep.security.tokens.Authen#getSessionToken <em>Session Token</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Session Token</em>' attribute.
	 * @see #getSessionToken()
	 */
	void setSessionToken(String value);

	/**
	 * Returns the value of the '<em><b>Auth Instant</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Auth Instant</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Auth Instant</em>' attribute.
	 * @see #setAuthInstant(Date)
	 */
	Date getAuthInstant();

	/**
	 * Sets the value of the '{@link com.tibco.cep.security.tokens.Authen#getAuthInstant <em>Auth Instant</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Auth Instant</em>' attribute.
	 * @see #getAuthInstant()
	 */
	void setAuthInstant(Date value);

	/**
	 * Returns the value of the '<em><b>Authenticated By</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Authenticated By</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Authenticated By</em>' attribute.
	 * @see #setAuthenticatedBy(String)
	 */
	String getAuthenticatedBy();

	/**
	 * Sets the value of the '{@link com.tibco.cep.security.tokens.Authen#getAuthenticatedBy <em>Authenticated By</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Authenticated By</em>' attribute.
	 * @see #getAuthenticatedBy()
	 */
	void setAuthenticatedBy(String value);

	/**
	 * Returns the value of the '<em><b>Time To Live</b></em>' attribute.
	 * The default value is <code>"-1"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Time To Live</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Time To Live</em>' attribute.
	 * @see #setTimeToLive(int)
	 */
	int getTimeToLive();

	/**
	 * Sets the value of the '{@link com.tibco.cep.security.tokens.Authen#getTimeToLive <em>Time To Live</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Time To Live</em>' attribute.
	 * @see #getTimeToLive()
	 */
	void setTimeToLive(int value);

	/**
	 * Returns the value of the '<em><b>User</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>User</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>User</em>' containment reference.
	 * @see #setUser(User)
	 */
	User getUser();

	/**
	 * Sets the value of the '{@link com.tibco.cep.security.tokens.Authen#getUser <em>User</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>User</em>' containment reference.
	 * @see #getUser()
	 */
	void setUser(User value);

} // Authen
