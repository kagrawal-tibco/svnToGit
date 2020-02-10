package com.tibco.cep.studio.tester.ui.tools.custom;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.TableModel;

/**
 * 
 * @author sasahoo
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class TableComboField extends JxComboField{

	private JxComboFieldTable table;

	/**
	 * @throws ComboBoxTableException
	 */
	public TableComboField()  throws ComboBoxTableException {
		table = new JxComboFieldTable();
		table.setBorder(new EmptyBorder(0, 0, 0, 0));
		table.setColumnSelectionAllowed(false);
		table.setRowSelectionAllowed(true);
		table.setSelectionMode(0);
		table.getTableHeader().setReorderingAllowed(false);
		table.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent mouseevent)
			{
				hidePopup();
				table.mouseOverHandler.column = -1;
				table.mouseOverHandler.row = -1;
			}
		});
		JScrollPane jscrollpane = new JScrollPane(table);
		jscrollpane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setPopupComponent(jscrollpane);
	}

	/**
	 * @param tablemodel
	 * @param i
	 * @throws ComboBoxTableException
	 */
	public TableComboField(TableModel tablemodel, int i) throws ComboBoxTableException
	{
		this();
		setModel(tablemodel, i);
	}

	/**
	 * @param tablemodel
	 * @param comboBoxColumnIndex
	 */
	public void setModel(TableModel tablemodel, final int comboBoxColumnIndex)
	{
		TableComboFieldModel tablecomboboxmodel = new TableComboFieldModel(tablemodel, comboBoxColumnIndex, table.getSelectionModel());
		table.setModel(tablecomboboxmodel);
		setModel(((javax.swing.ComboBoxModel) (tablecomboboxmodel)));
		tablecomboboxmodel.addListDataListener(new ListDataListener() {
			/* (non-Javadoc)
			 * @see javax.swing.event.ListDataListener#contentsChanged(javax.swing.event.ListDataEvent)
			 */
			public void contentsChanged(ListDataEvent listdataevent)
			{
				if(isPopupVisible())
				{
					table.scrollRectToVisible(table.getCellRect(getSelectedIndex(), comboBoxColumnIndex, false));
				}
			}

			public void intervalRemoved(ListDataEvent listdataevent)
			{
			}

			public void intervalAdded(ListDataEvent listdataevent)
			{
			}



		});
		resizeTable();
	}

	/**
	 * @param flag
	 */
	public void setShowTableHeaders(boolean flag)
	{
		table.setShowHeaders(flag);
	}

	/**
	 * @param flag
	 */
	public void setShowTableGrid(boolean flag)
	{
		table.setShowGrid(flag);
	}

	protected void resizeTable()
	{
		if(table != null)
		{
			table.resize(3, getScrollBarWidth(), getMaximumRowCount());
		}
	}

	protected int getScrollBarWidth()
	{
		JScrollPane jscrollpane = (JScrollPane)getPopupComponent();
		if(jscrollpane == null)
		{
			return 0;
		}
		JScrollBar jscrollbar = jscrollpane.getVerticalScrollBar();
		javax.swing.plaf.ScrollBarUI scrollbarui = jscrollbar.getUI();
		int i;
		if(getMaximumRowCount() < table.getRowCount())
		{
			i = scrollbarui.getPreferredSize(jscrollbar).width;
		} else
		{
			i = 0;
		}
		return i;
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComboBox#setMaximumRowCount(int)
	 */
	public void setMaximumRowCount(int i)
	{
		super.setMaximumRowCount(i);
		resizeTable();
	}

	/**
	 * @return
	 */
	public JTable getPopupTable()
	{
		return table;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.test.combo.DomainComboField#updateUI()
	 */
	public void updateUI()
	{
		super.updateUI();
		resizeTable();
	}
}
