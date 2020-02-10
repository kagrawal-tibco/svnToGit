package com.tibco.cep.studio.ui.editors.domain.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.cep.studio.core.util.ImportDomainUtils;

/**
 * 
 * @author smarathe
 *
 */
public class DBTreeContentProvider implements ITreeContentProvider {

	private TreeViewer treeViewer;
	protected static Object[] EMPTY_ARRAY = new Object[0];
	private Map<String, Object> connectionMap;
	private String domainDataType;
	
	public DBTreeContentProvider(TreeViewer treeViewer, Map<String, Object> connectionMap, String domainDataType) {
		this.treeViewer = treeViewer;
		this.connectionMap = connectionMap;
		this.domainDataType = domainDataType;
	}
		
		
	@Override
	public void dispose() {
		

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		

	}
	
	public Object[] getChildren(Object parentElement) {
		try {
			if (parentElement instanceof DBTreeRoot) {
				DBTreeRoot root = (DBTreeRoot)parentElement;
				if( root.getTableList() != null) {
					return root.getTableList().toArray();
				}
				else {
					return new Object[0];
				}
			}
			else if(parentElement instanceof DBTreeTables) {
				DBTreeTables table = (DBTreeTables)parentElement;
				if( table.getColumnList()!= null) {
					return table.getColumnList().toArray();
				} else {
					List<String> columnList = ImportDomainUtils.getColumnList(connectionMap, ((DBTreeTables)table).getTableName(), domainDataType);
					List<DBTreeColumns> columnsDBList = new ArrayList<DBTreeColumns>();
					for(String columnName : columnList) {
						DBTreeColumns column = new DBTreeColumns(columnName, table.getTableName());
						column.setParent(table);
						columnsDBList.add(column);
					}
					((DBTreeTables)table).setColumnList(columnsDBList);
					return columnsDBList.toArray();
				}
			} else if (parentElement instanceof DBTreeColumns) {
			/*	DBTreeColumns column = (DBTreeColumns)parentElement;
				List<DBTreeValues> valuesDBList = new ArrayList<DBTreeValues>();
				if( column.getValuesList() != null) {
					return column.getValuesList().toArray();
				} else {
					List<String> valuesList = ImportDomainUtils.getValuesList(connectionMap, column.getColumnName(), column.getTableName());
					for(String value : valuesList) {
						valuesDBList.add(new DBTreeValues(value, column.getColumnName(), column.getTableName()));
					}
					((DBTreeColumns)column).setValuesList(valuesDBList);
					return valuesDBList.toArray();
				}*/
				return null;
				
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return EMPTY_ARRAY;
	}
	
	@Override
	public Object getParent(Object element) {
		if(element instanceof DBTreeColumns) {
			return ((DBTreeColumns)element).getParent();
		} else {
			return null;
		}
	}

	@Override
	public boolean hasChildren(Object element) {
		return true;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}


}
