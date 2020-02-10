/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model;

import org.eclipse.emf.ecore.EFactory;

import com.tibco.cep.designtime.core.model.element.BaseInstance;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.studio.core.index.model.IndexPackage
 * @generated
 */
public interface IndexFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	IndexFactory eINSTANCE = com.tibco.cep.studio.core.index.model.impl.IndexFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Designer Project</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Designer Project</em>'.
	 * @generated
	 */
	DesignerProject createDesignerProject();

	/**
	 * Returns a new object of class '<em>Folder</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Folder</em>'.
	 * @generated
	 */
	Folder createFolder();

	/**
	 * Returns a new object of class '<em>Element Reference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Element Reference</em>'.
	 * @generated
	 */
	ElementReference createElementReference();

	/**
	 * Returns a new object of class '<em>Entity Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Entity Element</em>'.
	 * @generated
	 */
	EntityElement createEntityElement();

	/**
	 * Returns a new object of class '<em>State Machine Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>State Machine Element</em>'.
	 * @generated
	 */
	StateMachineElement createStateMachineElement();

	/**
	 * Returns a new object of class '<em>Event Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Event Element</em>'.
	 * @generated
	 */
	EventElement createEventElement();

	/**
	 * Returns a new object of class '<em>Decision Table Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Decision Table Element</em>'.
	 * @generated
	 */
	DecisionTableElement createDecisionTableElement();

	/**
	 * Returns a new object of class '<em>Archive Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Archive Element</em>'.
	 * @generated
	 */
	ArchiveElement createArchiveElement();

	/**
	 * Returns a new object of class '<em>Rule Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Rule Element</em>'.
	 * @generated
	 */
	RuleElement createRuleElement();

	/**
	 * Returns a new object of class '<em>Variable Definition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Variable Definition</em>'.
	 * @generated
	 */
	VariableDefinition createVariableDefinition();

	/**
	 * Returns a new object of class '<em>Local Variable Def</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Local Variable Def</em>'.
	 * @generated
	 */
	LocalVariableDef createLocalVariableDef();

	/**
	 * Returns a new object of class '<em>Global Variable Def</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Global Variable Def</em>'.
	 * @generated
	 */
	GlobalVariableDef createGlobalVariableDef();
	

	/**
	 * Returns a new object of class '<em>Instance Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Instance Element</em>'.
	 * @generated
	 */
	<T extends BaseInstance> InstanceElement<T> createInstanceElement();

	/**
	 * Returns a new object of class '<em>Shared Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Shared Element</em>'.
	 * @generated
	 */
	SharedElement createSharedElement();

	/**
	 * Returns a new object of class '<em>Shared Decision Table Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Shared Decision Table Element</em>'.
	 * @generated
	 */
	SharedDecisionTableElement createSharedDecisionTableElement();

	/**
	 * Returns a new object of class '<em>Shared Rule Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Shared Rule Element</em>'.
	 * @generated
	 */
	SharedRuleElement createSharedRuleElement();

	/**
	 * Returns a new object of class '<em>Shared State Machine Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Shared State Machine Element</em>'.
	 * @generated
	 */
	SharedStateMachineElement createSharedStateMachineElement();

	/**
	 * Returns a new object of class '<em>Shared Event Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Shared Event Element</em>'.
	 * @generated
	 */
	SharedEventElement createSharedEventElement();

	/**
	 * Returns a new object of class '<em>Shared Entity Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Shared Entity Element</em>'.
	 * @generated
	 */
	SharedEntityElement createSharedEntityElement();

	/**
	 * Returns a new object of class '<em>Shared Process Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Shared Process Element</em>'.
	 * @generated
	 */
	SharedProcessElement createSharedProcessElement();

	/**
	 * Returns a new object of class '<em>Process Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Process Element</em>'.
	 * @generated
	 */
	ProcessElement createProcessElement();

	/**
	 * Returns a new object of class '<em>Java Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Java Element</em>'.
	 * @generated
	 */
	JavaElement createJavaElement();

	/**
	 * Returns a new object of class '<em>Shared Java Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Shared Java Element</em>'.
	 * @generated
	 */
	SharedJavaElement createSharedJavaElement();

	/**
	 * Returns a new object of class '<em>Java Resource Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Java Resource Element</em>'.
	 * @generated
	 */
	JavaResourceElement createJavaResourceElement();

	/**
	 * Returns a new object of class '<em>Shared Java Resource Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Shared Java Resource Element</em>'.
	 * @generated
	 */
	SharedJavaResourceElement createSharedJavaResourceElement();

	/**
	 * Returns a new object of class '<em>Binding Variable Def</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Binding Variable Def</em>'.
	 * @generated
	 */
	BindingVariableDef createBindingVariableDef();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	IndexPackage getIndexPackage();

} //IndexFactory
