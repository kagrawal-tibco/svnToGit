package com.tibco.cep.studio.dashboard.ui.viewers;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

/**
 * @
 *  
 */
public class ElementFieldViewer extends TableViewer {

    /**
     * @param parent
     */
    public ElementFieldViewer(Composite parent) {
        super(createTable(parent));
        setContentProvider(new ArrayContentProvider());
        setLabelProvider(new ElementFieldLabelProvider());
        setSorter(new ElementNameSorter());
    }

    private static Table createTable(Composite parent) {
        Table table = new Table(parent, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE);
        table.setLinesVisible(true);
        table.setHeaderVisible(true);

        TableColumn column = new TableColumn(table, SWT.LEFT, 0);
        column.setText("Name");
        column.setWidth(200);

        column = new TableColumn(table, SWT.LEFT, 1);
        column.setText("DataType");
        column.setWidth(100);

        column = new TableColumn(table, SWT.LEFT, 2);
        column.setText("Description");
        column.setWidth(200);

        return table;
    }

}
