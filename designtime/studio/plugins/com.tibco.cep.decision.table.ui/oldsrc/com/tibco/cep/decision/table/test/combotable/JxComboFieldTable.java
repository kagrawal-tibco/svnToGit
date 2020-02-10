package com.tibco.cep.decision.table.test.combotable;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/**
 * 
 * @author sasahoo
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class JxComboFieldTable extends JTable {

	/**
	 * @author sasahoo
	 *
	 */
	class MouseOverHandler extends MouseAdapter implements MouseMotionListener
	{
		int row;
		int column;

		/* (non-Javadoc)
		 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
		 */
		public void mouseMoved(MouseEvent mouseevent)
		{
			row = rowAtPoint(mouseevent.getPoint());
			column = columnAtPoint(mouseevent.getPoint());
			repaint();
		}

		/* (non-Javadoc)
		 * @see java.awt.event.MouseAdapter#mouseExited(java.awt.event.MouseEvent)
		 */
		public void mouseExited(MouseEvent mouseevent)
		{
			row = -1;
			column = -1;
			repaint();
		}

		public void mouseDragged(MouseEvent mouseevent)
		{
		}

		MouseOverHandler(){
			super();
			row = -1;
			column = -1;
		}
	}
	public MouseOverHandler mouseOverHandler;
	private boolean mouseOverActive;

	public JxComboFieldTable()
	{
		mouseOverHandler = new MouseOverHandler();
		setMouseOverActive(true);
	}

	/**
	 * @param flag
	 */
	public void setShowHeaders(boolean flag)
	{
		if(!flag)
		{
			getTableHeader().setPreferredSize(new Dimension(0, 0));
		} else
		{
			getTableHeader().setPreferredSize(getPreferredTableHeaderSize());
		}
	}

	/**
	 * @param flag
	 */
	public void setMouseOverActive(boolean flag)
	{
		mouseOverActive = flag;
		if(flag)
		{
			addMouseMotionListener(mouseOverHandler);
			addMouseListener(mouseOverHandler);
		} else
		{
			removeMouseMotionListener(mouseOverHandler);
			removeMouseListener(mouseOverHandler);
		}
	}

	/**
	 * @return
	 */
	public boolean isMouseOverActive()
	{
		return mouseOverActive;
	}

	/* (non-Javadoc)
	 * @see javax.swing.JTable#prepareRenderer(javax.swing.table.TableCellRenderer, int, int)
	 */
	public Component prepareRenderer(TableCellRenderer tablecellrenderer, int i, int j)
	{
		Component component = super.prepareRenderer(tablecellrenderer, i, j);
		if(isMouseOverActive())
		{
			if(getRowSelectionAllowed() && getColumnSelectionAllowed())
			{
				if(!isSelectedRow(i) || !isSelectedColumn(j))
				{
					if(i == mouseOverHandler.row && j == mouseOverHandler.column)
					{
						component.setBackground(getMouseOverBackground());
						component.setForeground(getSelectionForeground());
					} else
					{
						component.setBackground(getBackground());
						component.setForeground(getForeground());
					}
				}
			} else
				if(getRowSelectionAllowed() && !getColumnSelectionAllowed())
				{
					if(!isSelectedRow(i))
					{
						if(i == mouseOverHandler.row)
						{
							component.setBackground(getMouseOverBackground());
							component.setForeground(getSelectionForeground());
						} else
						{
							component.setBackground(getBackground());
							component.setForeground(getForeground());
						}
					}
				} else
					if(getColumnSelectionAllowed() && !getRowSelectionAllowed() && !isSelectedColumn(j))
					{
						if(j == mouseOverHandler.column)
						{
							component.setBackground(getMouseOverBackground());
							component.setForeground(getSelectionForeground());
						} else
						{
							component.setBackground(getBackground());
							component.setForeground(getForeground());
						}
					}
		}
		return component;
	}

	/**
	 * @return
	 */
	protected Color getMouseOverBackground()
	{
		Color color = getSelectionBackground();
		Color color1 = getBackground();
		int i = (color.getRed() + color1.getRed()) / 2;
		int j = (color.getGreen() + color1.getGreen()) / 2;
		int k = (color.getBlue() + color1.getBlue()) / 2;
		Color color2 = new Color(i, j, k);
		return color2;
	}

	/**
	 * @param i
	 * @return
	 */
	protected boolean isSelectedRow(int i)
	{
		int ai[] = getSelectedRows();
		int j = 0;
		for(int k = ai.length; j < k; j++)
		{
			if(ai[j] == i)
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * @param i
	 * @return
	 */
	protected boolean isSelectedColumn(int i)
	{
		int ai[] = getSelectedColumns();
		int j = 0;
		for(int k = ai.length; j < k; j++)
		{
			if(ai[j] == i)
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * @return
	 */
	protected Dimension getPreferredTableHeaderSize()
	{
		JTableHeader jtableheader = getTableHeader();
		TableCellRenderer tablecellrenderer = jtableheader.getDefaultRenderer();
		int i = 0;
		int j = 0;
		int k = 0;
		for(int l = jtableheader.getColumnModel().getColumnCount(); k < l; k++)
		{
			TableColumn tablecolumn = jtableheader.getColumnModel().getColumn(k);
			TableCellRenderer tablecellrenderer1 = tablecolumn.getHeaderRenderer();
			if(tablecellrenderer1 == null)
			{
				tablecellrenderer1 = tablecellrenderer;
			}
			Dimension dimension = tablecellrenderer1.getTableCellRendererComponent(this, tablecolumn.getHeaderValue(), false, false, 0, k).getPreferredSize();
			i = Math.max(i, dimension.height);
			j += dimension.width;
		}

		return new Dimension(j, i);
	}

	/**
	 * @param i
	 * @param j
	 * @param k
	 */
	protected void resize(int i, int j, int k)
	{
		calcAndSetPreferredCellSizes(i);
		Dimension dimension = getPreferredSize();
		dimension.width += j;
		int l = Math.min(k, getRowCount());
		int i1 = getRowHeight();
		dimension.height = i1 * l;
		setPreferredScrollableViewportSize(dimension);
	}

	/**
	 * @param i
	 */
	protected void calcAndSetPreferredCellSizes(int i)
	{
		TableModel tablemodel = getModel();
		TableCellRenderer tablecellrenderer = getTableHeader().getDefaultRenderer();
		int j = 0;
		for(int k = 0; k < tablemodel.getColumnCount(); k++)
		{
			TableColumn tablecolumn = getColumnModel().getColumn(k);
			int l = 0;
			int i1 = 0;
			TableCellRenderer tablecellrenderer1 = tablecolumn.getHeaderRenderer();
			if(tablecellrenderer1 == null)
			{
				tablecellrenderer1 = tablecellrenderer;
			}
			Component component = tablecellrenderer1.getTableCellRendererComponent(this, tablecolumn.getHeaderValue(), false, false, 0, k);
			l = component.getPreferredSize().width;
			TableCellRenderer tablecellrenderer2 = tablecolumn.getCellRenderer();
			if(tablecellrenderer2 == null)
			{
				tablecellrenderer2 = getDefaultRenderer(tablemodel.getColumnClass(k));
			}
			for(int j1 = 0; j1 < tablemodel.getRowCount(); j1++)
			{
				Component component1 = tablecellrenderer2.getTableCellRendererComponent(this, tablemodel.getValueAt(j1, k), false, false, j1, k);
				int l1 = component1.getPreferredSize().width;
				int i2 = component1.getPreferredSize().height;
				if(l1 > i1)
				{
					i1 = l1;
				}
				if(i2 > j)
				{
					j = i2;
				}
			}

			int k1 = Math.max(l, i1);
			k1 += i;
			tablecolumn.setPreferredWidth(k1);
		}
		setRowHeight(j + getRowMargin());
	}
}
