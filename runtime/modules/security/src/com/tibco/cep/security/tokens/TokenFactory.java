/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.security.tokens;


/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 */
public interface TokenFactory {
    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    TokenFactory INSTANCE = com.tibco.cep.security.tokens.impl.TokenFactoryImpl.getInstance();

    /**
     * Returns a new object of class '<em>Auth Token</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Auth Token</em>'.
     */
    AuthToken createAuthToken();

    /**
     * Returns a new object of class '<em>Authen</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Authen</em>'.
     */
    Authen createAuthen();

    /**
     * Returns a new object of class '<em>Authz</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Authz</em>'.
     */
    Authz createAuthz();

    /**
     * Returns a new object of class '<em>Role</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Role</em>'.
     */
    Role createRole();

    /**
     * Returns a new object of class '<em>User</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>User</em>'.
     */
    User createUser();

} 
