package com.tibco.cep.driver.hawk.util;

import COM.TIBCO.hawk.console.hawkeye.AgentManager;
import COM.TIBCO.hawk.talon.DataElement;
import COM.TIBCO.hawk.talon.MethodSubscription;
import COM.TIBCO.hawk.talon.MicroAgentException;
import COM.TIBCO.hawk.talon.MicroAgentID;
import COM.TIBCO.hawk.talon.Subscription;
import COM.TIBCO.hawk.talon.SubscriptionHandler;

import com.tibco.cep.driver.hawk.HawkConstants;
import com.tibco.hawk.jshma.plugin.HawkConsoleBase;

public class SubscriptionUtil {

	public static Subscription subscribe(HawkConsoleBase hawkConsoleBase,  String[] params,  String arguments,
			String timeInterval, SubscriptionHandler subHandler) throws MicroAgentException {
		Subscription subscription = null;
		// parse uri
		String agentName = params[0];
		String microAgentName = params[1];
		String methodName = params[2];

		MethodSubscription mas;
		DataElement[] args = null;
		boolean isSync = hawkConsoleBase.isSyncMethod(agentName, microAgentName, methodName);
		if (isSync) {
			// parse arguments
			if (arguments != null && !arguments.equals("")) {
				String argPairs[] = arguments.split(";");
				args = new DataElement[argPairs.length];
				for (int i = 0; i < args.length; i++) {
					String pair[] = argPairs[i].split("=");
					if(pair.length > 1){
						args[i] = new DataElement(pair[0], pair[1]);
					}else{
						args[i] = new DataElement(pair[0], null);
					}
				}
			}
			mas = new MethodSubscription(methodName, args, Integer.valueOf(timeInterval) * 1000);
		} else {
			// parse arguments
			if (arguments != null && !arguments.equals("")) {
				String argPairs[] = arguments.split(";");
				args = new DataElement[1 + argPairs.length];
				args[0] = new DataElement(HawkConstants.PROP_TIMEINTERVAL, timeInterval);
				for (int i = 1; i < args.length; i++) {
					String pair[] = argPairs[i - 1].split("=");
					if(pair.length > 1){
						args[i] = new DataElement(pair[0], pair[1]);
					}else{
						args[i] = new DataElement(pair[0], null);
					}
				}
			} else {
				args = new DataElement[1];
				args[0] = new DataElement(HawkConstants.PROP_TIMEINTERVAL, timeInterval);
			}
			mas = new MethodSubscription(methodName, args);
		}

		MicroAgentID[] maids = null;
		AgentManager agentMgr = hawkConsoleBase.getTIBHawkConsole().getAgentManager();
		maids = agentMgr.getMicroAgentIDs(agentName, null, null, microAgentName, 1);

		if (maids != null && maids.length > 0) {
			subscription = agentMgr.subscribe(maids[0], mas, subHandler, null);
		}
		return subscription;
	}

}
