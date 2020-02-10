/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.table.model.dtmodel.impl;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Columns</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.impl.ColumnsImpl#getColumn <em>Column</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ColumnsImpl extends EObjectImpl implements Columns {
	/**
	 * The cached value of the '{@link #getColumn() <em>Column</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColumn()
	 * @generated
	 * @ordered
	 */
	protected EList<Column> column;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ColumnsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DtmodelPackage.Literals.COLUMNS;
	}

	/**
	 * This {@link List} should not be modified from outside this class.
	 * @generated NOT
	 */
	public EList<Column> getColumn() {
		return column = getColumnInternal();
	}
	
	/**
	 * 
	 * @return
	 */
	private EList<Column> getColumnInternal() {
		if (column == null) {
			column = new EDataTypeEList<Column>(Column.class, this, DtmodelPackage.COLUMNS__COLUMN);
		}
		return column;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public boolean add(Column column) {
		EList<Column> columnsList = getColumnInternal();
		return columnsList.add(column);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public boolean add(int index, Column column) {
		EList<Column> columnsList = getColumnInternal();
		columnsList.add(index, column);
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Column move(int newPosition, Column column) {
		EList<Column> columnsList = getColumnInternal();
		ECollections.move(columnsList, newPosition, column);
		return column;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Column move(int newIndex, int oldIndex) {
		EList<Column> columnsList = getColumnInternal();
		return ECollections.move(columnsList, newIndex, oldIndex);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DtmodelPackage.COLUMNS__COLUMN:
				return ((InternalEList<?>)getColumn()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DtmodelPackage.COLUMNS__COLUMN:
				return getColumn();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case DtmodelPackage.COLUMNS__COLUMN:
				getColumn().clear();
				getColumn().addAll((Collection<? extends Column>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case DtmodelPackage.COLUMNS__COLUMN:
				getColumn().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case DtmodelPackage.COLUMNS__COLUMN:
				return column != null && !column.isEmpty();
		}
		return super.eIsSet(featureID);
	}
	
	
	
	public Column search(String id){
		if (id == null) return null;
		for (Column col : getColumn()){
			if (col.getId().equals(id)){
				return col;
			}
		}
		return null;
	}
	
	public int getColumnIndexById(String id){
		if (id == null) return -1;
		EList<Column> list = getColumn();
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getId().equals(id)){
				return i;
			}
		}
		return -1;
	}
	
	

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.model.dtmodel.Columns#searchByName(java.lang.String, com.tibco.cep.decision.table.model.dtmodel.ColumnType)
	 */
	public Column searchByName(String columnName, ColumnType columnType) {
		if (columnName == null) return null;
		for (Column col : getColumn()){
			if (col.getName().equals(columnName)
					&& col.getColumnType().equals(columnType)){
				return col;
			}
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.model.dtmodel.Columns#searchByOrdinal(java.lang.String, com.tibco.cep.decision.table.model.dtmodel.ColumnType)
	 */
	public Column searchByOrdinal(String columnName, ColumnType columnType) {
		for (Column col : getColumn()){
			if (col.getName().equals(columnName)
					&& ((col.getColumnType().massagedOrdinal()) == columnType.ordinal())) {
				return col;
			}
		}
		return null;
	}
		
	

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.model.dtmodel.Columns#removeColumnById(java.lang.String)
	 */
	@Override
	public boolean removeColumnById(String id) {
		Column removedColumn = null;
		//Check if any columns exist
		if (this.column != null && !this.column.isEmpty()) {
			for (Column column : this.column) {
				if (id.equals(column.getId())) {
					removedColumn = column;
					break;
				}
			}
			return this.column.remove(removedColumn);
		}
		return false;
	}

	
	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.model.dtmodel.Columns#removeColumnByName(java.lang.String, com.tibco.cep.decision.table.model.dtmodel.ColumnType)
	 */
	public boolean removeColumnByName(String columnName, ColumnType columnType) {
		Column removedColumn = null;
		for (Column column : this.column) {
			if (columnName.equals(column.getName())
					&& column.getColumnType().equals(columnType)) {
				removedColumn = column;
				break;
			}
		}
		this.column.remove(removedColumn);
		return true;
	}

} //ColumnsImpl
