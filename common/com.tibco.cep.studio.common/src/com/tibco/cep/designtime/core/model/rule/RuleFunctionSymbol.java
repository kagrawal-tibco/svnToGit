/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.rule;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Function Symbol</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.RuleFunctionSymbol#getDirection <em>Direction</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.RuleFunctionSymbol#getResourceType <em>Resource Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.RuleFunctionSymbol#isDomainOverridden <em>Domain Overridden</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.RuleFunctionSymbol#getGraphicalPath <em>Graphical Path</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.RuleFunctionSymbol#getPrimitiveType <em>Primitive Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getRuleFunctionSymbol()
 * @model
 * @generated
 */
public interface RuleFunctionSymbol extends Symbol {
	/**
	 * Returns the value of the '<em><b>Direction</b></em>' attribute.
	 * The default value is <code>"IN"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Direction</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Direction</em>' attribute.
	 * @see #setDirection(String)
	 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getRuleFunctionSymbol_Direction()
	 * @model default="IN"
	 * @generated
	 */
	String getDirection();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.rule.RuleFunctionSymbol#getDirection <em>Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Direction</em>' attribute.
	 * @see #getDirection()
	 * @generated
	 */
	void setDirection(String value);

	/**
	 * Returns the value of the '<em><b>Resource Type</b></em>' attribute.
	 * The default value is <code>"NULL"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resource Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resource Type</em>' attribute.
	 * @see #setResourceType(String)
	 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getRuleFunctionSymbol_ResourceType()
	 * @model default="NULL"
	 * @generated
	 */
	String getResourceType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.rule.RuleFunctionSymbol#getResourceType <em>Resource Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Resource Type</em>' attribute.
	 * @see #getResourceType()
	 * @generated
	 */
	void setResourceType(String value);

	/**
	 * Returns the value of the '<em><b>Domain Overridden</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Domain Overridden</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Domain Overridden</em>' attribute.
	 * @see #setDomainOverridden(boolean)
	 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getRuleFunctionSymbol_DomainOverridden()
	 * @model
	 * @generated
	 */
	boolean isDomainOverridden();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.rule.RuleFunctionSymbol#isDomainOverridden <em>Domain Overridden</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Domain Overridden</em>' attribute.
	 * @see #isDomainOverridden()
	 * @generated
	 */
	void setDomainOverridden(boolean value);

	/**
	 * Returns the value of the '<em><b>Graphical Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Graphical Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Graphical Path</em>' attribute.
	 * @see #setGraphicalPath(String)
	 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getRuleFunctionSymbol_GraphicalPath()
	 * @model
	 * @generated
	 */
	String getGraphicalPath();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.rule.RuleFunctionSymbol#getGraphicalPath <em>Graphical Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Graphical Path</em>' attribute.
	 * @see #getGraphicalPath()
	 * @generated
	 */
	void setGraphicalPath(String value);

	/**
	 * Returns the value of the '<em><b>Primitive Type</b></em>' attribute.
	 * The default value is <code>"NULL"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Primitive Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Primitive Type</em>' attribute.
	 * @see #setPrimitiveType(String)
	 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getRuleFunctionSymbol_PrimitiveType()
	 * @model default="NULL"
	 * @generated
	 */
	String getPrimitiveType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.rule.RuleFunctionSymbol#getPrimitiveType <em>Primitive Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Primitive Type</em>' attribute.
	 * @see #getPrimitiveType()
	 * @generated
	 */
	void setPrimitiveType(String value);

} // RuleFunctionSymbol
