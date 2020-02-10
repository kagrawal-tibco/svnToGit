/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.rule;

import com.tibco.cep.designtime.core.model.Entity;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Compilable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.Compilable#getCompilationStatus <em>Compilation Status</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.Compilable#getReturnType <em>Return Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.Compilable#getFullSourceText <em>Full Source Text</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.Compilable#getActionText <em>Action Text</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.Compilable#getConditionText <em>Condition Text</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.Compilable#getRank <em>Rank</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getCompilable()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface Compilable extends Entity, ScopeContainer {
	/**
	 * Returns the value of the '<em><b>Compilation Status</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Compilation Status</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Compilation Status</em>' attribute.
	 * @see #setCompilationStatus(int)
	 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getCompilable_CompilationStatus()
	 * @model required="true"
	 * @generated
	 */
	int getCompilationStatus();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.rule.Compilable#getCompilationStatus <em>Compilation Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Compilation Status</em>' attribute.
	 * @see #getCompilationStatus()
	 * @generated
	 */
	void setCompilationStatus(int value);

	/**
	 * Returns the value of the '<em><b>Return Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Return Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Return Type</em>' attribute.
	 * @see #setReturnType(String)
	 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getCompilable_ReturnType()
	 * @model required="true"
	 * @generated
	 */
	String getReturnType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.rule.Compilable#getReturnType <em>Return Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Return Type</em>' attribute.
	 * @see #getReturnType()
	 * @generated
	 */
	void setReturnType(String value);

	/**
	 * Returns the value of the '<em><b>Full Source Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Full Source Text</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Full Source Text</em>' attribute.
	 * @see #setFullSourceText(String)
	 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getCompilable_FullSourceText()
	 * @model required="true" transient="true"
	 * @generated
	 */
	String getFullSourceText();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.rule.Compilable#getFullSourceText <em>Full Source Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Full Source Text</em>' attribute.
	 * @see #getFullSourceText()
	 * @generated
	 */
	void setFullSourceText(String value);

	/**
	 * Returns the value of the '<em><b>Action Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Action Text</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Action Text</em>' attribute.
	 * @see #setActionText(String)
	 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getCompilable_ActionText()
	 * @model required="true"
	 * @generated
	 */
	String getActionText();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.rule.Compilable#getActionText <em>Action Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Action Text</em>' attribute.
	 * @see #getActionText()
	 * @generated
	 */
	void setActionText(String value);

	/**
	 * Returns the value of the '<em><b>Condition Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Condition Text</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Condition Text</em>' attribute.
	 * @see #setConditionText(String)
	 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getCompilable_ConditionText()
	 * @model required="true"
	 * @generated
	 */
	String getConditionText();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.rule.Compilable#getConditionText <em>Condition Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Condition Text</em>' attribute.
	 * @see #getConditionText()
	 * @generated
	 */
	void setConditionText(String value);

	/**
	 * Returns the value of the '<em><b>Rank</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rank</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rank</em>' attribute.
	 * @see #setRank(String)
	 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getCompilable_Rank()
	 * @model required="true"
	 * @generated
	 */
	String getRank();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.rule.Compilable#getRank <em>Rank</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rank</em>' attribute.
	 * @see #getRank()
	 * @generated
	 */
	void setRank(String value);

} // Compilable
