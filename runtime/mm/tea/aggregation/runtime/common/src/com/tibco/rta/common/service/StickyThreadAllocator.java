package com.tibco.rta.common.service;

public interface StickyThreadAllocator {
	int allocateThread(int totalThreads, String key);
}