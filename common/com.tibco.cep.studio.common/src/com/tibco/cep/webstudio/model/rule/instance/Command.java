/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.webstudio.model.rule.instance;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Command</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.webstudio.model.rule.instance.Command#getActionType <em>Action Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public interface Command extends Clause {
	
	public final int ACTION_TYPE_FEATURE_ID = 1;
	public final int TYPE_FEATURE_ID = 2;
	public final int ALIAS_TYPE_FEATURE_ID = 3;

	/**
	 * Returns the value of the '<em><b>Action Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Action Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Action Type</em>' attribute.
	 * @see #setActionType(String)
	 * @generated
	 */
	String getActionType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.webstudio.model.rule.instance.Command#getActionType <em>Action Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Action Type</em>' attribute.
	 * @see #getActionType()
	 * @generated
	 */
	void setActionType(String value);
	
	String getAlias();
	String getType();
	
	void setAlias(String alias);
	void setType(String type);

} // Command
