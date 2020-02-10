package com.tibco.cep.diagramming;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public abstract class AbstractTableOverview extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2036626735167661110L;
	public abstract JScrollPane getScrollPane();
	public abstract void setScrollPane(JScrollPane scrollPane);
}
