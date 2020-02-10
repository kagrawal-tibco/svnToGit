/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.element;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.designtime.core.model.RuleParticipant;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.designtime.core.model.rule.RuleSet;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.kernel.helper.Sort;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Concept</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.Concept#getSubConcepts <em>Sub Concepts</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.Concept#getProperties <em>Properties</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.Concept#getRuleSet <em>Rule Set</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.Concept#getStateMachinePaths <em>State Machine Paths</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.Concept#getRootStateMachinePath <em>Root State Machine Path</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.Concept#getParentConcept <em>Parent Concept</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.Concept#getParentConceptPath <em>Parent Concept Path</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.Concept#getSuperConcept <em>Super Concept</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.Concept#getSuperConceptPath <em>Super Concept Path</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.Concept#isScorecard <em>Scorecard</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.Concept#isMetric <em>Metric</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.Concept#isContained <em>Contained</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.Concept#isPOJO <em>POJO</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.Concept#isTransient <em>Transient</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.Concept#getImplClass <em>Impl Class</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.Concept#getSubConceptsPath <em>Sub Concepts Path</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.Concept#isAutoStartStateMachine <em>Auto Start State Machine</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getConcept()
 * @model
 * @generated
 */
public interface Concept extends RuleParticipant {
	/**
	 * @generated NOT
	 */
	String[] BASE_ATTRIBUTE_NAMES = Sort.sort(new String[]{"id", "extId", "length"});
	/**
	 * Returns the value of the '<em><b>Sub Concepts</b></em>' reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.element.Concept}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sub Concepts</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sub Concepts</em>' reference list.
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getConcept_SubConcepts()
	 * @model
	 * @generated
	 */
	EList<Concept> getSubConcepts();

	/**
	 * Returns the value of the '<em><b>Properties</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.element.PropertyDefinition}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Properties</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Properties</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getConcept_Properties()
	 * @model containment="true"
	 * @generated
	 */
	EList<PropertyDefinition> getProperties();

	/**
	 * Returns the value of the '<em><b>Rule Set</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule Set</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule Set</em>' reference.
	 * @see #setRuleSet(RuleSet)
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getConcept_RuleSet()
	 * @model
	 * @generated
	 */
	RuleSet getRuleSet();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.element.Concept#getRuleSet <em>Rule Set</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule Set</em>' reference.
	 * @see #getRuleSet()
	 * @generated
	 */
	void setRuleSet(RuleSet value);

	/**
	 * Returns the value of the '<em><b>State Machine Paths</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>State Machine Paths</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>State Machine Paths</em>' attribute list.
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getConcept_StateMachinePaths()
	 * @model
	 * @generated
	 */
	EList<String> getStateMachinePaths();

	/**
	 * Returns the value of the '<em><b>Root State Machine Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Root State Machine Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Root State Machine Path</em>' attribute.
	 * @see #setRootStateMachinePath(String)
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getConcept_RootStateMachinePath()
	 * @model
	 * @generated
	 */
	String getRootStateMachinePath();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.element.Concept#getRootStateMachinePath <em>Root State Machine Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Root State Machine Path</em>' attribute.
	 * @see #getRootStateMachinePath()
	 * @generated
	 */
	void setRootStateMachinePath(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>State Machines</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	EList<StateMachine> getStateMachines();

	/**
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Root State Machine</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	StateMachine getRootStateMachine();

	/**
	 * Returns the value of the '<em><b>Parent Concept</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent Concept</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent Concept</em>' reference.
	 * @see #setParentConcept(Concept)
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getConcept_ParentConcept()
	 * @model transient="true"
	 * @generated
	 */
	Concept getParentConcept();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.element.Concept#getParentConcept <em>Parent Concept</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent Concept</em>' reference.
	 * @see #getParentConcept()
	 * @generated
	 */
	void setParentConcept(Concept value);

	/**
	 * Returns the value of the '<em><b>Super Concept</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Super Concept</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Super Concept</em>' reference.
	 * @see #setSuperConcept(Concept)
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getConcept_SuperConcept()
	 * @model transient="true"
	 * @generated
	 */
	Concept getSuperConcept();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.element.Concept#getSuperConcept <em>Super Concept</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Super Concept</em>' reference.
	 * @see #getSuperConcept()
	 * @generated
	 */
	void setSuperConcept(Concept value);

	/**
	 * Returns the value of the '<em><b>Super Concept Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Super Concept Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Super Concept Path</em>' attribute.
	 * @see #setSuperConceptPath(String)
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getConcept_SuperConceptPath()
	 * @model
	 * @generated
	 */
	String getSuperConceptPath();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.element.Concept#getSuperConceptPath <em>Super Concept Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Super Concept Path</em>' attribute.
	 * @see #getSuperConceptPath()
	 * @generated
	 */
	void setSuperConceptPath(String value);

	/**
	 * Returns the value of the '<em><b>Scorecard</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Scorecard</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Scorecard</em>' attribute.
	 * @see #setScorecard(boolean)
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getConcept_Scorecard()
	 * @model
	 * @generated
	 */
	boolean isScorecard();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.element.Concept#isScorecard <em>Scorecard</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Scorecard</em>' attribute.
	 * @see #isScorecard()
	 * @generated
	 */
	void setScorecard(boolean value);

	/**
	 * Returns the value of the '<em><b>Metric</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Metric</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Metric</em>' attribute.
	 * @see #setMetric(boolean)
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getConcept_Metric()
	 * @model default="false"
	 * @generated
	 */
	boolean isMetric();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.element.Concept#isMetric <em>Metric</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Metric</em>' attribute.
	 * @see #isMetric()
	 * @generated
	 */
	void setMetric(boolean value);

	/**
	 * Returns the value of the '<em><b>Contained</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Contained</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contained</em>' attribute.
	 * @see #setContained(boolean)
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getConcept_Contained()
	 * @model transient="true"
	 * @generated
	 */
	boolean isContained();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.element.Concept#isContained <em>Contained</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Contained</em>' attribute.
	 * @see #isContained()
	 * @generated
	 */
	void setContained(boolean value);

	/**
	 * Returns the value of the '<em><b>POJO</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>POJO</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>POJO</em>' attribute.
	 * @see #setPOJO(boolean)
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getConcept_POJO()
	 * @model
	 * @generated
	 */
	boolean isPOJO();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.element.Concept#isPOJO <em>POJO</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>POJO</em>' attribute.
	 * @see #isPOJO()
	 * @generated
	 */
	void setPOJO(boolean value);

	/**
	 * Returns the value of the '<em><b>Transient</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Transient</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transient</em>' attribute.
	 * @see #setTransient(boolean)
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getConcept_Transient()
	 * @model
	 * @generated
	 */
	boolean isTransient();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.element.Concept#isTransient <em>Transient</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Transient</em>' attribute.
	 * @see #isTransient()
	 * @generated
	 */
	void setTransient(boolean value);

	/**
	 * Returns the value of the '<em><b>Impl Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Impl Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Impl Class</em>' attribute.
	 * @see #setImplClass(String)
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getConcept_ImplClass()
	 * @model
	 * @generated
	 */
	String getImplClass();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.element.Concept#getImplClass <em>Impl Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Impl Class</em>' attribute.
	 * @see #getImplClass()
	 * @generated
	 */
	void setImplClass(String value);
	/**
	 * Returns the value of the '<em><b>Sub Concepts Path</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sub Concepts Path</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sub Concepts Path</em>' attribute list.
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getConcept_SubConceptsPath()
	 * @model
	 * @generated
	 */
	EList<String> getSubConceptsPath();

	/**
	 * Returns the value of the '<em><b>Parent Concept Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent Concept Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent Concept Path</em>' attribute.
	 * @see #setParentConceptPath(String)
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getConcept_ParentConceptPath()
	 * @model transient="true"
	 * @generated
	 */
	String getParentConceptPath();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.element.Concept#getParentConceptPath <em>Parent Concept Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent Concept Path</em>' attribute.
	 * @see #getParentConceptPath()
	 * @generated
	 */
	void setParentConceptPath(String value);

	/**
	 * Returns the value of the '<em><b>Auto Start State Machine</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Auto Start State Machine</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Auto Start State Machine</em>' attribute.
	 * @see #setAutoStartStateMachine(boolean)
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getConcept_AutoStartStateMachine()
	 * @model
	 * @generated
	 */
	boolean isAutoStartStateMachine();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.element.Concept#isAutoStartStateMachine <em>Auto Start State Machine</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Auto Start State Machine</em>' attribute.
	 * @see #isAutoStartStateMachine()
	 * @generated
	 */
	void setAutoStartStateMachine(boolean value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	EList<PropertyDefinition> getAllProperties();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	EList<DomainInstance> getAllDomainInstances();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model many="false"
	 * @generated
	 */
	EList<PropertyDefinition> getPropertyDefinitions(boolean localOnly);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model propsMany="false"
	 * @generated
	 */
	void _getPropertyDefinitions(Concept cept, EList<PropertyDefinition> props);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" many="false"
	 * @generated
	 */
	EList<PropertyDefinition> getAllPropertyDefinitions();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model many="false"
	 * @generated
	 */
	EList<PropertyDefinition> _getAllPropertyDefinitions();

	/**
	 * if local only is true then it does not check super concepts else checks 
	 * @param name
	 * @return
	 *  @generated not
	 */
	PropertyDefinition getPropertyDefinition (String name , boolean localOnly);
	/**
	 * it checks all super Concepts and sub Concepts 
	 * @generated NOT
	 */
	PropertyDefinition getPropertyDefinition(String name) ;
	/**
	 * @generated not
	 */
	boolean isA(Concept concept);
	/**
	 * @generated not
	 */
	PropertyDefinition getAttributeDefinition (String attributeName);
	
	
	/**
	 * @generated NOT
	 */
	Collection<PropertyDefinition> getAttributeDefinitions();
	
	/**
	 * @generated NOT
	 * @param allAttributeDefinitions
	 */
	public void setAttributeDefinitions(List<PropertyDefinition> allAttributeDefinitions);
} // Concept
