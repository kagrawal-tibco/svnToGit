package com.tibco.cep.studio.ui.util;

import org.eclipse.swt.widgets.Table;

public interface TableProviderEditModel extends TableProviderModel {
    public void editItem(Table table, int index);
}
