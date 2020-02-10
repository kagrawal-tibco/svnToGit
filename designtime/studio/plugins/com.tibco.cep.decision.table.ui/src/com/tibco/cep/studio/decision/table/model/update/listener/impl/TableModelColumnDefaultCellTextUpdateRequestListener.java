package com.tibco.cep.studio.decision.table.model.update.listener.impl;

import com.tibco.cep.studio.decision.table.model.update.listener.ITableModelChangeRequestEvent;


/**
 * Update defaultCellText for a column here.
 * @author yrajput
 *
 */
public class TableModelColumnDefaultCellTextUpdateRequestListener extends TableModelColumnNameUpdateRequestListener {

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.model.update.listener.impl.TableModelColumnNameUpdateRequestListener#doChange(com.tibco.cep.decision.table.model.update.listener.ITableModelChangeRequestEvent)
	 */
	@Override
	public <T extends ITableModelChangeRequestEvent> void doChange(T changeRequestEvent) {
		super.doChange(changeRequestEvent);
	}
}
