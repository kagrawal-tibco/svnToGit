package com.tibco.cep.studio.rms.history;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.TableColumn;

public class RevisionTableSortSelectionListener extends SelectionAdapter {

	private TableViewer viewer;
	private RevisionTableSorter sorter;
	private int colIndex;

	/**
	 * @param viewer
	 * @param sorter
	 */
	public RevisionTableSortSelectionListener(TableViewer viewer, RevisionTableSorter sorter, int colIndex) {
		this.viewer = viewer;
		this.sorter = sorter;
		this.colIndex = colIndex;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetSelected(SelectionEvent e) {
		try {
			TableColumn column = (TableColumn) e.getSource();
			sorter.setColumn(colIndex);
			int dir = viewer.getTable().getSortDirection();
			if (viewer.getTable().getSortColumn() == column) {
				dir = dir == SWT.UP ? SWT.DOWN : SWT.UP;
			} else {
				dir = SWT.DOWN;
			}
			viewer.getTable().setSortDirection(dir);
			viewer.getTable().setSortColumn(column);
			viewer.refresh();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
