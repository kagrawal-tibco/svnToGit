/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model;

import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IStructured Element Visitor</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getIStructuredElementVisitor()
 * @model interface="true" abstract="true"
 * @generated NOT
 */
public interface IStructuredElementVisitor {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	boolean preVisit(StructuredElement element);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	boolean postVisit(StructuredElement element);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	boolean visitChildren(StructuredElement element);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	boolean visit(StructuredElement element);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	boolean visitDesignerProject(DesignerProject element);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	boolean visitFolder(Folder element);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	boolean visitStateMachineElement(StateMachineElement element);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	boolean visitEntityElement(EntityElement element);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	boolean visitDecisionTableElement(DecisionTableElement element);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	boolean visitRuleElement(RuleElement element);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	boolean visitLocalVariableDefinition(LocalVariableDef element);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	boolean visitGlobalVariableDefinition(GlobalVariableDef element);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	boolean visitScopeBlock(ScopeBlock element);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	boolean visitArchiveElement(ArchiveElement element);

} // IStructuredElementVisitor
