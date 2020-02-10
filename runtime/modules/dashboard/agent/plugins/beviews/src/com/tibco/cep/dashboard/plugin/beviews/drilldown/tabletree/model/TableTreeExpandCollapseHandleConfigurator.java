package com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model;

import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.ExpandCollapseHandleConfigurator;

/**
 * @author rajesh
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TableTreeExpandCollapseHandleConfigurator
	extends ExpandCollapseHandleConfigurator {

	private TableTree table;

	/**
	 * @param table
	 * @param rowExpandedLink
	 */
	public TableTreeExpandCollapseHandleConfigurator(
		TableTree table,
		String rowExpandedLink) {
		super(rowExpandedLink);
		this.table = table;
		setExpandCollapseImagePath(table.getImagePath());
	}

	/**
	 * @param table
	 */
	public TableTreeExpandCollapseHandleConfigurator(TableTree table) {
		super();
		this.table = table;
		setExpandCollapseImagePath(table.getImagePath());
	}
}
