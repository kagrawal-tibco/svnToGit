/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.studio.core.index.model.scope.RootScopeBlock;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Rule Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.RuleElement#getRule <em>Rule</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.RuleElement#isVirtual <em>Virtual</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.RuleElement#getScope <em>Scope</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.RuleElement#getGlobalVariables <em>Global Variables</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.RuleElement#getIndexName <em>Index Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getRuleElement()
 * @model
 * @generated
 */
public interface RuleElement extends TypeElement {
	/**
	 * Returns the value of the '<em><b>Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule</em>' containment reference.
	 * @see #setRule(Compilable)
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getRuleElement_Rule()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Compilable getRule();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.RuleElement#getRule <em>Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule</em>' containment reference.
	 * @see #getRule()
	 * @generated
	 */
	void setRule(Compilable value);

	/**
	 * Returns the value of the '<em><b>Virtual</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Virtual</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Virtual</em>' attribute.
	 * @see #setVirtual(boolean)
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getRuleElement_Virtual()
	 * @model
	 * @generated
	 */
	boolean isVirtual();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.RuleElement#isVirtual <em>Virtual</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Virtual</em>' attribute.
	 * @see #isVirtual()
	 * @generated
	 */
	void setVirtual(boolean value);

	/**
	 * Returns the value of the '<em><b>Scope</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Scope</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Scope</em>' containment reference.
	 * @see #setScope(RootScopeBlock)
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getRuleElement_Scope()
	 * @model containment="true"
	 * @generated
	 */
	RootScopeBlock getScope();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.RuleElement#getScope <em>Scope</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Scope</em>' containment reference.
	 * @see #getScope()
	 * @generated
	 */
	void setScope(RootScopeBlock value);

	/**
	 * Returns the value of the '<em><b>Global Variables</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.studio.core.index.model.GlobalVariableDef}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Global Variables</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Global Variables</em>' containment reference list.
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getRuleElement_GlobalVariables()
	 * @model containment="true"
	 * @generated
	 */
	EList<GlobalVariableDef> getGlobalVariables();

	/**
	 * Returns the value of the '<em><b>Index Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Index Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Index Name</em>' attribute.
	 * @see #setIndexName(String)
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getRuleElement_IndexName()
	 * @model required="true"
	 * @generated
	 */
	String getIndexName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.RuleElement#getIndexName <em>Index Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Index Name</em>' attribute.
	 * @see #getIndexName()
	 * @generated
	 */
	void setIndexName(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model visitorType="com.tibco.cep.studio.core.index.model.IStructuredElementVisitor"
	 * @generated
	 */
	void accept(IStructuredElementVisitor visitor);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model visitorType="com.tibco.cep.studio.core.index.model.IStructuredElementVisitor"
	 * @generated
	 */
	void doVisitChildren(IStructuredElementVisitor visitor);

} // RuleElement
