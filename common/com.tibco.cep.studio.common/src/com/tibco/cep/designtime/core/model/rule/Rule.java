/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.rule;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.Rule#getPriority <em>Priority</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.Rule#getMaxRules <em>Max Rules</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.Rule#getTestInterval <em>Test Interval</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.Rule#getStartTime <em>Start Time</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.Rule#isRequeue <em>Requeue</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.Rule#getRequeueVars <em>Requeue Vars</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.Rule#isBackwardChain <em>Backward Chain</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.Rule#isForwardChain <em>Forward Chain</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.Rule#isConditionFunction <em>Condition Function</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.Rule#isFunction <em>Function</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.Rule#getAuthor <em>Author</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getRule()
 * @model
 * @generated
 */
public interface Rule extends Compilable {
	/**
	 * Returns the value of the '<em><b>Priority</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Priority</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Priority</em>' attribute.
	 * @see #setPriority(int)
	 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getRule_Priority()
	 * @model required="true"
	 * @generated
	 */
	int getPriority();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.rule.Rule#getPriority <em>Priority</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Priority</em>' attribute.
	 * @see #getPriority()
	 * @generated
	 */
	void setPriority(int value);

	/**
	 * Returns the value of the '<em><b>Max Rules</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Max Rules</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max Rules</em>' attribute.
	 * @see #setMaxRules(int)
	 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getRule_MaxRules()
	 * @model required="true"
	 * @generated
	 */
	int getMaxRules();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.rule.Rule#getMaxRules <em>Max Rules</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Rules</em>' attribute.
	 * @see #getMaxRules()
	 * @generated
	 */
	void setMaxRules(int value);

	/**
	 * Returns the value of the '<em><b>Test Interval</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Test Interval</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Test Interval</em>' attribute.
	 * @see #setTestInterval(long)
	 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getRule_TestInterval()
	 * @model required="true"
	 * @generated
	 */
	long getTestInterval();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.rule.Rule#getTestInterval <em>Test Interval</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Test Interval</em>' attribute.
	 * @see #getTestInterval()
	 * @generated
	 */
	void setTestInterval(long value);

	/**
	 * Returns the value of the '<em><b>Start Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Start Time</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Start Time</em>' attribute.
	 * @see #setStartTime(long)
	 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getRule_StartTime()
	 * @model required="true"
	 * @generated
	 */
	long getStartTime();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.rule.Rule#getStartTime <em>Start Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Start Time</em>' attribute.
	 * @see #getStartTime()
	 * @generated
	 */
	void setStartTime(long value);

	/**
	 * Returns the value of the '<em><b>Requeue</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Requeue</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Requeue</em>' attribute.
	 * @see #setRequeue(boolean)
	 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getRule_Requeue()
	 * @model
	 * @generated
	 */
	boolean isRequeue();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.rule.Rule#isRequeue <em>Requeue</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Requeue</em>' attribute.
	 * @see #isRequeue()
	 * @generated
	 */
	void setRequeue(boolean value);

	/**
	 * Returns the value of the '<em><b>Requeue Vars</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Requeue Vars</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Requeue Vars</em>' attribute list.
	 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getRule_RequeueVars()
	 * @model
	 * @generated
	 */
	EList<String> getRequeueVars();

	/**
	 * Returns the value of the '<em><b>Backward Chain</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Backward Chain</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Backward Chain</em>' attribute.
	 * @see #setBackwardChain(boolean)
	 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getRule_BackwardChain()
	 * @model
	 * @generated
	 */
	boolean isBackwardChain();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.rule.Rule#isBackwardChain <em>Backward Chain</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Backward Chain</em>' attribute.
	 * @see #isBackwardChain()
	 * @generated
	 */
	void setBackwardChain(boolean value);

	/**
	 * Returns the value of the '<em><b>Forward Chain</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Forward Chain</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Forward Chain</em>' attribute.
	 * @see #setForwardChain(boolean)
	 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getRule_ForwardChain()
	 * @model
	 * @generated
	 */
	boolean isForwardChain();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.rule.Rule#isForwardChain <em>Forward Chain</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Forward Chain</em>' attribute.
	 * @see #isForwardChain()
	 * @generated
	 */
	void setForwardChain(boolean value);

	/**
	 * Returns the value of the '<em><b>Condition Function</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Condition Function</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Condition Function</em>' attribute.
	 * @see #setConditionFunction(boolean)
	 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getRule_ConditionFunction()
	 * @model
	 * @generated
	 */
	boolean isConditionFunction();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.rule.Rule#isConditionFunction <em>Condition Function</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Condition Function</em>' attribute.
	 * @see #isConditionFunction()
	 * @generated
	 */
	void setConditionFunction(boolean value);

	/**
	 * Returns the value of the '<em><b>Function</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Function</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Function</em>' attribute.
	 * @see #setFunction(boolean)
	 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getRule_Function()
	 * @model
	 * @generated
	 */
	boolean isFunction();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.rule.Rule#isFunction <em>Function</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Function</em>' attribute.
	 * @see #isFunction()
	 * @generated
	 */
	void setFunction(boolean value);

	/**
	 * Returns the value of the '<em><b>Author</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Author</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Author</em>' attribute.
	 * @see #setAuthor(String)
	 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getRule_Author()
	 * @model required="true"
	 * @generated
	 */
	String getAuthor();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.rule.Rule#getAuthor <em>Author</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Author</em>' attribute.
	 * @see #getAuthor()
	 * @generated
	 */
	void setAuthor(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	boolean isEmpty();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	RuleFunction getRankRuleFunction();

} // Rule
