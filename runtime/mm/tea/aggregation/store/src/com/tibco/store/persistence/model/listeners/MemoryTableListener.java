package com.tibco.store.persistence.model.listeners;

import java.util.EventListener;

public interface MemoryTableListener extends EventListener {
	public void onTupleAdded(TupleAddedEvent e);
	public void onTupleRemoved(TupleRemovedEvent e);
	public void onTableCleared();
	public long getTableSizeInBytes();
}
