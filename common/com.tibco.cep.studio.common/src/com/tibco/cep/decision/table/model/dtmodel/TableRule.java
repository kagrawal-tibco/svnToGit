/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.table.model.dtmodel;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Table Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.TableRule#getId <em>Id</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.TableRule#getComment <em>Comment</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.TableRule#getCond <em>Cond</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.TableRule#getAct <em>Act</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.TableRule#isModified <em>Modified</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getTableRule()
 * @model
 * @generated
 */
public interface TableRule extends Metadatable {
	/**
	 * Returns the value of the '<em><b>Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Modified</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Modified</em>' attribute.
	 * @see #setModified(boolean)
	 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getTableRule_Modified()
	 * @model
	 * @generated
	 */
	boolean isModified();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.dtmodel.TableRule#isModified <em>Modified</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Modified</em>' attribute.
	 * @see #isModified()
	 * @generated
	 */
	void setModified(boolean value);

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getTableRule_Id()
	 * @model
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.dtmodel.TableRule#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Comment</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Comment</em>' attribute.
	 * @see #setComment(String)
	 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getTableRule_Comment()
	 * @model
	 * @generated
	 */
	String getComment();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.dtmodel.TableRule#getComment <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Comment</em>' attribute.
	 * @see #getComment()
	 * @generated
	 */
	void setComment(String value);

	

	/**
	 * Returns the value of the '<em><b>Cond</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cond</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cond</em>' containment reference list.
	 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getTableRule_Cond()
	 * @model containment="true"
	 * @generated
	 */
	EList<TableRuleVariable> getCond();

	/**
	 * Returns the value of the '<em><b>Act</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Act</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Act</em>' containment reference list.
	 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getTableRule_Act()
	 * @model containment="true"
	 * @generated
	 */
	EList<TableRuleVariable> getAct();

	/**
	 * Returns the value of the '<em><b>Cond</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cond</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cond</em>' containment reference list.
	 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getTableRule_Cond()
	 * @model containment="true"
	 * @generated NOT
	 */
	EList<TableRuleVariable> getCondition();

	/**
	 * Returns the value of the '<em><b>Act</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Act</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Act</em>' containment reference list.
	 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getTableRule_Act()
	 * @model containment="true"
	 * @generated NOT
	 */
	EList<TableRuleVariable> getAction();

} // TableRule
