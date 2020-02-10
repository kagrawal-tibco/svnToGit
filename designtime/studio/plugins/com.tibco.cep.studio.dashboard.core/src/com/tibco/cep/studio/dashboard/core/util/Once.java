package com.tibco.cep.studio.dashboard.core.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Once {

	private static java.util.logging.Logger logger = java.util.logging.Logger.getLogger("ccom.tibco.cep.studio.dashboard.core.util.Once");

	private boolean mFirstTime = true;
	private List<String> mFirstTimeKeys = Collections.synchronizedList(new ArrayList<String>());

	public boolean firstTime() {
		if (!mFirstTime)
			return false;
		mFirstTime = false;
		return true;
	}

	public synchronized boolean firstTimeSyn() {
		return firstTime();
	}

	public boolean firstTimeByKey(String key) {
		if (mFirstTimeKeys.contains(key))
			return false;
		if (mFirstTimeKeys.size() > 1000) {
			// Simply purge all, since this function is used to reduce logging only.
			logger.fine("Too many keys than expected, clearing cache (" + key + ")");
			mFirstTimeKeys.clear();
		}
		mFirstTimeKeys.add(key);
		return true;
	}

	public synchronized boolean firstTimeByKeySyn(String key) {
		return firstTimeByKey(key);
	}
}
