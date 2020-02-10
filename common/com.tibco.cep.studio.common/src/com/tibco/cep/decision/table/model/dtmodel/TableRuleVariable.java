/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.table.model.dtmodel;

import com.tibco.cep.decisionproject.ontology.AccessControlCandidate;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Table Rule Variable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable#getComment <em>Comment</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable#isEnabled <em>Enabled</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable#getMetatData <em>Metat Data</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable#getId <em>Id</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable#getColId <em>Col Id</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable#getExpr <em>Expr</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable#getColumnName <em>Column Name</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable#getPath <em>Path</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable#getDisplayValue <em>Display Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getTableRuleVariable()
 * @model
 * @generated
 */
public interface TableRuleVariable extends AccessControlCandidate {
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
	 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getTableRuleVariable_Comment()
	 * @model
	 * @generated
	 */
	String getComment();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable#getComment <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Comment</em>' attribute.
	 * @see #getComment()
	 * @generated
	 */
	void setComment(String value);

	/**
	 * Returns the value of the '<em><b>Enabled</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enabled</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enabled</em>' attribute.
	 * @see #setEnabled(boolean)
	 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getTableRuleVariable_Enabled()
	 * @model default="true"
	 * @generated
	 */
	boolean isEnabled();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable#isEnabled <em>Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enabled</em>' attribute.
	 * @see #isEnabled()
	 * @generated
	 */
	void setEnabled(boolean value);

	/**
	 * Returns the value of the '<em><b>Metat Data</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Metat Data</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Metat Data</em>' containment reference.
	 * @see #setMetatData(MetaData)
	 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getTableRuleVariable_MetatData()
	 * @model containment="true"
	 * @generated
	 */
	MetaData getMetatData();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable#getMetatData <em>Metat Data</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Metat Data</em>' containment reference.
	 * @see #getMetatData()
	 * @generated
	 */
	void setMetatData(MetaData value);

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
	 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getTableRuleVariable_Id()
	 * @model
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Col Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Col Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Col Id</em>' attribute.
	 * @see #setColId(String)
	 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getTableRuleVariable_ColId()
	 * @model
	 * @generated
	 */
	String getColId();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable#getColId <em>Col Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Col Id</em>' attribute.
	 * @see #getColId()
	 * @generated
	 */
	void setColId(String value);

	/**
	 * Returns the value of the '<em><b>Expr</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Expr</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expr</em>' attribute.
	 * @see #setExpr(String)
	 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getTableRuleVariable_Expr()
	 * @model
	 * @generated
	 */
	String getExpr();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable#getExpr <em>Expr</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expr</em>' attribute.
	 * @see #getExpr()
	 * @generated
	 */
	void setExpr(String value);

	/**
	 * Returns the value of the '<em><b>Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Column Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Column Name</em>' attribute.
	 * @see #setColumnName(String)
	 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getTableRuleVariable_ColumnName()
	 * @model
	 * @generated
	 */
	String getColumnName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable#getColumnName <em>Column Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Column Name</em>' attribute.
	 * @see #getColumnName()
	 * @generated
	 */
	void setColumnName(String value);

	/**
	 * Returns the value of the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Path</em>' attribute.
	 * @see #setPath(String)
	 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getTableRuleVariable_Path()
	 * @model
	 * @generated
	 */
	String getPath();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable#getPath <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Path</em>' attribute.
	 * @see #getPath()
	 * @generated
	 */
	void setPath(String value);

	/**
	 * Returns the value of the '<em><b>Display Value</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Display Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Display Value</em>' attribute.
	 * @see #setDisplayValue(String)
	 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getTableRuleVariable_DisplayValue()
	 * @model default=""
	 * @generated
	 */
	String getDisplayValue();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable#getDisplayValue <em>Display Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Display Value</em>' attribute.
	 * @see #getDisplayValue()
	 * @generated
	 */
	void setDisplayValue(String value);

} // TableRuleVariable
