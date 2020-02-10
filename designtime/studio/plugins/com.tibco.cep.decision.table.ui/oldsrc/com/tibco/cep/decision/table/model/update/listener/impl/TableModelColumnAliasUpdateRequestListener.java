package com.tibco.cep.decision.table.model.update.listener.impl;

import com.tibco.cep.decision.table.model.update.listener.ITableModelChangeRequestEvent;

/**
 * Update alias for a column here.
 * @author aathalye
 *
 */
public class TableModelColumnAliasUpdateRequestListener extends TableModelColumnNameUpdateRequestListener {

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.model.update.listener.impl.TableModelColumnNameUpdateRequestListener#doChange(com.tibco.cep.decision.table.model.update.listener.ITableModelChangeRequestEvent)
	 */
	@Override
	public <T extends ITableModelChangeRequestEvent> void doChange(T changeRequestEvent) {
		super.doChange(changeRequestEvent);
	}
}
