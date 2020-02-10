/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.table.model.dtmodel;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Columns</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.Columns#getColumn <em>Column</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getColumns()
 * @model
 * @generated
 */
public interface Columns extends EObject {
	/**
	 * Returns the value of the '<em><b>Column</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.decision.table.model.dtmodel.Column}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Column</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Column</em>' containment reference list.
	 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getColumns_Column()
	 * @model containment="true"
	 * @generated
	 */
	EList<Column> getColumn();
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	boolean add(Column column);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	boolean add(int index, Column column);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	Column move(int index, Column column);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	Column move(int newIndex, int oldIndex);

	boolean removeColumnById(String id);
	
	/**
	 * Remove column with this name
	 * @param columnName
	 * @param columnType
	 * @return
	 */
	boolean removeColumnByName(String columnName, ColumnType columnType);
	
	Column search(String id);
	
	int getColumnIndexById(String id);
	
	/**
	 * Search a column with name
	 * @param columnName
	 * @return
	 */
	Column searchByName(String columnName, ColumnType columnType);
	
	
	/**
	 * Search a column with its name and ordinal of the 
	 * column type enum in such a manner that the columntype
	 * for condition is same as that of custom condition,
	 * and ordinal for action is same as that of custom action
	 * @param columnName
	 * @param columnType
	 */
	Column searchByOrdinal(String columnName, ColumnType columnType);

} // Columns
