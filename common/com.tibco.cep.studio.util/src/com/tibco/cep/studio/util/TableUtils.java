package com.tibco.cep.studio.util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.JTextComponent;

/**
 * 
 * @author sasahoo
 *
 */
public class TableUtils {

	private static final int DEFAULT_COLUMN_PADDING = 5;

	/**
	 * @param JTable table, to resize the columns on
	 * @param boolean includeColumnHeaderWidth,  use the Column Header width as a minimum width
	 * @param Map map, contains table columns' preferred width
	 * @return The Table Width
	 */
	public static int resizeAllTableColumns ( JTable table, 
			                                  boolean includeColumnHeaderWidth, 
			                                  Map<String, Integer> map) {
		return resizeAllTableColumns(table, includeColumnHeaderWidth, DEFAULT_COLUMN_PADDING, map);
	}

	/**
	 * @param JTable table, to resize the columns on
	 * @param boolean includeColumnHeaderWidth, use the Column Header width as a minimum width
	 * @param int columnPadding, column padding to adjust
     * @param Map map, contains table columns' preferred width
	 * @return The Table Width
	 */
	public static int resizeAllTableColumns ( JTable table, 
			                                  boolean includeColumnHeaderWidth, 
			                                  int columnPadding, 
			                                  Map<String, Integer> map ) {
		int columnCount = table.getColumnCount();
		int tableWidth = 0;
		Dimension cellSpacing = table.getIntercellSpacing();
		if ( columnCount > 0 ) {
			int columnWidth[] = new int [ columnCount ];
			for ( int i=0; i < columnCount; i++ ) {
				columnWidth[i] = getMaxColumnWidth ( table, i, true, columnPadding );
				tableWidth += columnWidth[i];
			}
			
			//handling cell spacing
			tableWidth += ( ( columnCount - 1 ) * cellSpacing.width );
			
			//change the size of the column names area
			JTableHeader tableHeader = table.getTableHeader();
			Dimension headerDim = tableHeader.getPreferredSize();
			headerDim.width = tableWidth;
			tableHeader.setPreferredSize ( headerDim );
			Dimension interCellSpacing = table.getIntercellSpacing();
			Dimension dim = new Dimension();
			int rowHeight = table.getRowHeight();
			if ( rowHeight == 0 ) {
				rowHeight = 16;// keeping default row height
			}
			dim.height = headerDim.height + ( ( rowHeight + interCellSpacing.height ) * table.getRowCount() );
			dim.width = tableWidth;
			TableColumnModel tableColumnModel = table.getColumnModel();
			TableColumn tableColumn;
			for ( int i=0; i < columnCount; i++ ) {
				tableColumn = tableColumnModel.getColumn ( i );
				//calculating default column width
				int maxWidth = Math.max ( tableColumn.getWidth(), columnWidth[i] );
				/*if (maxWidth == tableColumn.getWidth()) {
					maxWidth = map.get(tableColumn.getHeaderValue().toString().trim());
				}*/
				tableColumn.setPreferredWidth ( maxWidth );
			}
			table.invalidate();
			table.doLayout();
			table.repaint();
		}
		return tableWidth;
	}

	/**
	 * @param JTable table, to resize the columns on
	 * @param int columnNo, the column number, starting at zero, to calculate the maximum width on
	 * @param boolean includeColumnHeaderWidth, use the Column Header width as a minimum width
	 * @param columnPadding, column padding to adjust
	 * @return, max column width
	 */
	private static int getMaxColumnWidth ( JTable table, 
			                               int columnNo, 
			                               boolean includeColumnHeaderWidth,
			                               int columnPadding ) {
		TableColumn column = table.getColumnModel().getColumn (columnNo);
		Component component = null;
		int maxWidth = 0;
		if (includeColumnHeaderWidth) {
			TableCellRenderer headerRenderer = column.getHeaderRenderer();
			if (headerRenderer != null) {
				component = headerRenderer.getTableCellRendererComponent ( table, column.getHeaderValue(), false, false, 0, columnNo );
				if ( component instanceof JTextComponent ) {
					JTextComponent jtextComp = (JTextComponent)component;
					String text = jtextComp.getText();
					Font font = jtextComp.getFont();
					FontMetrics fontMetrics = jtextComp.getFontMetrics ( font );
					maxWidth = SwingUtilities.computeStringWidth ( fontMetrics, text );
				} else {
					maxWidth = component.getPreferredSize().width;
				}
			} else {
				try {
					String headerText = (String)column.getHeaderValue();
					JLabel defaultLabel = new JLabel ( headerText );
					Font font = defaultLabel.getFont();
					FontMetrics fontMetrics = defaultLabel.getFontMetrics ( font );
					maxWidth = SwingUtilities.computeStringWidth ( fontMetrics, headerText );
				}
				catch ( ClassCastException ce ) {
					ce.printStackTrace();
					maxWidth = 0;
				}
			}
		}
		TableCellRenderer tableCellRenderer;
		int cellWidth= 0;
		for (int i = 0; i < table.getRowCount(); i++) {
			tableCellRenderer = table.getCellRenderer ( i, columnNo );
			component = tableCellRenderer.getTableCellRendererComponent(table, table.getValueAt(i, columnNo ), false, false, i, columnNo );
			if (component instanceof JTextComponent) {
				JTextComponent jtextComp = (JTextComponent)component;
				String text = jtextComp.getText();
				Font font = jtextComp.getFont();
				FontMetrics fontMetrics = jtextComp.getFontMetrics ( font );
				int textWidth = SwingUtilities.computeStringWidth ( fontMetrics, text );
				maxWidth = Math.max ( maxWidth, textWidth );
			} else {
				cellWidth = component.getPreferredSize().width;
				maxWidth = Math.max ( maxWidth, cellWidth );
			}
		}
		return maxWidth + columnPadding;
	}
}