/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.webstudio.model.rule.instance;

import java.io.Serializable;
import java.util.List;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Actions</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.webstudio.model.rule.instance.Actions#getActions <em>Actions</em>}</li>
 * </ul>
 * </p>
 *
 * @extends Serializable
 * @generated
 */
public interface Actions extends Serializable, IRuleTemplateInstanceObject {
	
	public final int ACTIONS_FEATURE_ID = 0;
	
	/**
	 * Returns the value of the '<em><b>Actions</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.webstudio.model.rule.instance.Command}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Actions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Actions</em>' containment reference list.
	 * @generated
	 */
	List<Command> getActions();

	void removeAction(Command command);

	void addAction(Command command);

} // Actions
