package com.tibco.store.persistence.stats;

import com.tibco.store.persistence.model.listeners.MemoryTableListener;

public interface MemTableStatsObservable {

	public void registerListener(MemoryTableListener tableListener);

	public void unRegisterListener(MemoryTableListener tableListener);
}
