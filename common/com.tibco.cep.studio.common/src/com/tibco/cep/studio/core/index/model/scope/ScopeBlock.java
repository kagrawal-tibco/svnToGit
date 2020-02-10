/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model.scope;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.IStructuredElementVisitor;
import com.tibco.cep.studio.core.index.model.LocalVariableDef;
import com.tibco.cep.studio.core.index.model.StructuredElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Block</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.scope.ScopeBlock#getType <em>Type</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.scope.ScopeBlock#getParentScopeDef <em>Parent Scope Def</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.scope.ScopeBlock#getChildScopeDefs <em>Child Scope Defs</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.scope.ScopeBlock#getDefs <em>Defs</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.scope.ScopeBlock#getRefs <em>Refs</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.scope.ScopeBlock#getOffset <em>Offset</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.scope.ScopeBlock#getLength <em>Length</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.index.model.scope.ScopePackage#getScopeBlock()
 * @model
 * @generated
 */
/**
 * @author pdhar
 *
 */
public interface ScopeBlock extends StructuredElement {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(int)
	 * @see com.tibco.cep.studio.core.index.model.scope.ScopePackage#getScopeBlock_Type()
	 * @model
	 * @generated
	 */
	int getType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.scope.ScopeBlock#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(int value);

	/**
	 * Returns the value of the '<em><b>Parent Scope Def</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link com.tibco.cep.studio.core.index.model.scope.ScopeBlock#getChildScopeDefs <em>Child Scope Defs</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent Scope Def</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent Scope Def</em>' container reference.
	 * @see #setParentScopeDef(ScopeBlock)
	 * @see com.tibco.cep.studio.core.index.model.scope.ScopePackage#getScopeBlock_ParentScopeDef()
	 * @see com.tibco.cep.studio.core.index.model.scope.ScopeBlock#getChildScopeDefs
	 * @model opposite="childScopeDefs" transient="false"
	 * @generated
	 */
	ScopeBlock getParentScopeDef();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.scope.ScopeBlock#getParentScopeDef <em>Parent Scope Def</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent Scope Def</em>' container reference.
	 * @see #getParentScopeDef()
	 * @generated
	 */
	void setParentScopeDef(ScopeBlock value);

	/**
	 * Returns the value of the '<em><b>Child Scope Defs</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.studio.core.index.model.scope.ScopeBlock}.
	 * It is bidirectional and its opposite is '{@link com.tibco.cep.studio.core.index.model.scope.ScopeBlock#getParentScopeDef <em>Parent Scope Def</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Child Scope Defs</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Child Scope Defs</em>' containment reference list.
	 * @see com.tibco.cep.studio.core.index.model.scope.ScopePackage#getScopeBlock_ChildScopeDefs()
	 * @see com.tibco.cep.studio.core.index.model.scope.ScopeBlock#getParentScopeDef
	 * @model opposite="parentScopeDef" containment="true"
	 * @generated
	 */
	EList<ScopeBlock> getChildScopeDefs();

	/**
	 * Returns the value of the '<em><b>Defs</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.studio.core.index.model.LocalVariableDef}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Defs</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Defs</em>' containment reference list.
	 * @see com.tibco.cep.studio.core.index.model.scope.ScopePackage#getScopeBlock_Defs()
	 * @model containment="true"
	 * @generated
	 */
	EList<LocalVariableDef> getDefs();

	/**
	 * Returns the value of the '<em><b>Refs</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.studio.core.index.model.ElementReference}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Refs</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Refs</em>' containment reference list.
	 * @see com.tibco.cep.studio.core.index.model.scope.ScopePackage#getScopeBlock_Refs()
	 * @model containment="true"
	 * @generated
	 */
	EList<ElementReference> getRefs();

	/**
	 * Returns the value of the '<em><b>Offset</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Offset</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Offset</em>' attribute.
	 * @see #setOffset(int)
	 * @see com.tibco.cep.studio.core.index.model.scope.ScopePackage#getScopeBlock_Offset()
	 * @model required="true"
	 * @generated
	 */
	int getOffset();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.scope.ScopeBlock#getOffset <em>Offset</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Offset</em>' attribute.
	 * @see #getOffset()
	 * @generated
	 */
	void setOffset(int value);

	/**
	 * Returns the value of the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Length</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Length</em>' attribute.
	 * @see #setLength(int)
	 * @see com.tibco.cep.studio.core.index.model.scope.ScopePackage#getScopeBlock_Length()
	 * @model required="true"
	 * @generated
	 */
	int getLength();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.scope.ScopeBlock#getLength <em>Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Length</em>' attribute.
	 * @see #getLength()
	 * @generated
	 */
	void setLength(int value);

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



} // ScopeBlock
