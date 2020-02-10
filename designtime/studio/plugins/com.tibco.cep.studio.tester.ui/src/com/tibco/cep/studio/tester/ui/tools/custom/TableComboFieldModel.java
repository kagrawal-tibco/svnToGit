package com.tibco.cep.studio.tester.ui.tools.custom;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 * 
 * @author sasahoo
 * @version 1.0.0
 */
public class TableComboFieldModel implements TableModel, ComboBoxModel{

	private TableModel tableModel;
	private int comboBoxColumnIndex;
	private ListSelectionModel listSelectionModel;
	private List<ListDataListener> listDataListeners;

	/**
	 * @param tablemodel
	 * @param i
	 * @param listselectionmodel
	 */
	public TableComboFieldModel(TableModel tablemodel, int i, ListSelectionModel listselectionmodel)
	{
		listDataListeners = new ArrayList<ListDataListener>();
		tableModel = tablemodel;
		comboBoxColumnIndex = i;
		listSelectionModel = listselectionmodel;
		listselectionmodel.addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent listselectionevent)
			{
				selectedValueChanged(listselectionevent.getSource());
			}
		});
	}

	/**
	 * @param obj
	 */
	private void selectedValueChanged(Object obj)
	{   try {
			fireItemStateChanged(new ListDataEvent(obj, 0, -1, -1));
		} catch (Exception e) {
			//TODO
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount()
	{
		return tableModel.getColumnCount();
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount()
	{
		return tableModel.getRowCount();
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnName(int)
	 */
	public String getColumnName(int i)
	{
		return tableModel.getColumnName(i);
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int i, int j)
	{

		if(i == -1 || j == -1 ){
			return "";
		}
		return tableModel.getValueAt(i, j);
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#addTableModelListener(javax.swing.event.TableModelListener)
	 */
	public void addTableModelListener(TableModelListener tablemodellistener)
	{
		tableModel.addTableModelListener(tablemodellistener);
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnClass(int)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int i)
	{
		return tableModel.getColumnClass(i);
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#isCellEditable(int, int)
	 */
	public boolean isCellEditable(int i, int j)
	{
		return tableModel.isCellEditable(i, j);
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#removeTableModelListener(javax.swing.event.TableModelListener)
	 */
	public void removeTableModelListener(TableModelListener tablemodellistener)
	{
		tableModel.removeTableModelListener(tablemodellistener);
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#setValueAt(java.lang.Object, int, int)
	 */
	public void setValueAt(Object obj, int i, int j)
	{
		tableModel.setValueAt(obj, i, j);
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	public Object getElementAt(int i)
	{
		return getValueAt(i, comboBoxColumnIndex);
	}

	/* (non-Javadoc)
	 * @see javax.swing.ComboBoxModel#getSelectedItem()
	 */
	public Object getSelectedItem()
	{
		return getValueAt(listSelectionModel.getLeadSelectionIndex(), comboBoxColumnIndex);
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListModel#getSize()
	 */
	public int getSize()
	{
		return getRowCount();
	}

	/**
	 * @param listdataevent
	 */
	@SuppressWarnings("rawtypes")
	private void fireItemStateChanged(ListDataEvent listdataevent)
	{
		ListDataListener listdatalistener;
		for(Iterator iterator = listDataListeners.iterator(); iterator.hasNext(); listdatalistener.contentsChanged(listdataevent))
		{
			listdatalistener = (ListDataListener)iterator.next();
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListModel#removeListDataListener(javax.swing.event.ListDataListener)
	 */
	public void removeListDataListener(ListDataListener listdatalistener)
	{
		listDataListeners.remove(listdatalistener);
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListModel#addListDataListener(javax.swing.event.ListDataListener)
	 */
	public void addListDataListener(ListDataListener listdatalistener)
	{
		listDataListeners.add(listdatalistener);
	}

	/* (non-Javadoc)
	 * @see javax.swing.ComboBoxModel#setSelectedItem(java.lang.Object)
	 */
	public void setSelectedItem(Object obj)
	{
		int i = 0;
		for(int j = getRowCount(); i < j; i++)
		{
			if(getValueAt(i, comboBoxColumnIndex) == obj)
			{
				listSelectionModel.setSelectionInterval(i, i);
				selectedValueChanged(this);
			}
		}

	}

}
