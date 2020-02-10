package com.tibco.cep.studio.dashboard.ui.forms;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

public abstract class AbstractSelectionListener implements SelectionListener {

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		widgetSelected(e);
	}
	
}