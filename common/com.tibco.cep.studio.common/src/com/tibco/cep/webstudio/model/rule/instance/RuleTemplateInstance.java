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
 * A representation of the model object '<em><b>Rule Template Instance</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.webstudio.model.rule.instance.RuleTemplateInstance#getConditionFilter <em>Condition Filter</em>}</li>
 *   <li>{@link com.tibco.cep.webstudio.model.rule.instance.RuleTemplateInstance#getActions <em>Actions</em>}</li>
 *   <li>{@link com.tibco.cep.webstudio.model.rule.instance.RuleTemplateInstance#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.cep.webstudio.model.rule.instance.RuleTemplateInstance#getImplements <em>Implements</em>}</li>
 * </ul>
 * </p>
 *
 * @extends Serializable
 * @generated
 */
public interface RuleTemplateInstance extends Serializable, IRuleTemplateInstanceObject {
	
	public final int CONDITION_FILTER_FEATURE_ID = 0;
	public final int ACTIONS_FEATURE_ID = 1;
	public final int NAME_FEATURE_ID = 2;
	public final int IMPLEMENTS_PATH_FEATURE_ID = 3;
	public final int BINDINGS_FEATURE_ID = 4;

	/**
	 * Returns the value of the '<em><b>Condition Filter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Condition Filter</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Condition Filter</em>' containment reference.
	 * @see #setConditionFilter(MultiFilter)
	 * @generated
	 */
	MultiFilter getConditionFilter();

	/**
	 * Sets the value of the '{@link com.tibco.cep.webstudio.model.rule.instance.RuleTemplateInstance#getConditionFilter <em>Condition Filter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Condition Filter</em>' containment reference.
	 * @see #getConditionFilter()
	 * @generated
	 */
	void setConditionFilter(MultiFilter value);

	/**
	 * Returns the value of the '<em><b>Actions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Actions</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Actions</em>' containment reference.
	 * @see #setActions(Actions)
	 * @generated
	 */
	Actions getActions();

	/**
	 * Sets the value of the '{@link com.tibco.cep.webstudio.model.rule.instance.RuleTemplateInstance#getActions <em>Actions</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Actions</em>' containment reference.
	 * @see #getActions()
	 * @generated
	 */
	void setActions(Actions value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.webstudio.model.rule.instance.RuleTemplateInstance#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Implements</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Implements</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Implements</em>' attribute.
	 * @see #setImplementsPath(String)
	 * @generated
	 */
	String getImplementsPath();

	/**
	 * Sets the value of the '{@link com.tibco.cep.webstudio.model.rule.instance.RuleTemplateInstance#getImplements <em>Implements</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Implements</em>' attribute.
	 * @see #getImplementsPath()
	 * @generated
	 */
	void setImplementsPath(String value);

	List<Binding> getBindings();
	
	void addBinding(Binding binding);
	void removeBinding(Binding binding);

    /**
     * @generated NOT
     */
    String getId();


    /**
     * @generated NOT
     */
    void setId(String id);
    
    /**
     * @generated NOT
     * @return
     */
    String getDescription();
    
    /**
     * @generated NOT
     * @param description
     */
    void setDescription(String description);
    
    /**
     * @generated NOT
     * @return
     */
    int getPriority();
    
    /**
     * @generated NOT
     * @param priority
     */
    void setPriority(int priority);

} // RuleTemplateInstance
