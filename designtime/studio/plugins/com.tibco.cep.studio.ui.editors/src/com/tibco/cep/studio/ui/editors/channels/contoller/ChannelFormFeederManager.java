/**
 * 
 */
package com.tibco.cep.studio.ui.editors.channels.contoller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author aathalye
 *
 */
public class ChannelFormFeederManager {
	
	/**
	 * Key is the project. There will be one feeder for a project
	 */
	private Map<String, ChannelFormFeeder> formFeeders = new HashMap<String, ChannelFormFeeder>();
	
	public final static ChannelFormFeederManager INSTANCE = new ChannelFormFeederManager();
	
	/**
	 * @param project
	 * @return
	 * @throws Exception
	 */
	public ChannelFormFeeder getFormFeeder(String project) throws Exception {
		ChannelFormFeeder feeder = null;
		if (formFeeders.containsKey(project)) {
			feeder = formFeeders.get(project);
		} else {
			//Create a feeder for this project
			feeder = new ChannelFormFeeder();
			feeder.initialize(project);
			formFeeders.put(project, feeder);
		}
		return feeder;
	}
	
	/**
	 * @return
	 */
	public Iterator<ChannelFormFeeder> getWorkspaceFeeders() {
		return formFeeders.values().iterator();
	}
	
	/**
	 * @param formFeeder
	 * @return
	 */
	public boolean isFeederInitialized(ChannelFormFeeder formFeeder) {
		Iterator<ChannelFormFeeder> feeders = getWorkspaceFeeders();
		while (feeders.hasNext()) {
			ChannelFormFeeder next = feeders.next();
			if (next.equals(formFeeder)) {
				//Check if it has been initialized
				return next.isInitialized();
			}
		}
		return false;
	}
}
