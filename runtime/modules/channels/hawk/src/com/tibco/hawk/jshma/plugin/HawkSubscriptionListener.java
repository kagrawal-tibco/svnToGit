// (c) Copyright 2001,2002 TIBCO Software Inc.  All rights reserved.
// LEGAL NOTICE:  This source code is provided to specific authorized end
// users pursuant to a separate license agreement.  You MAY NOT use this
// source code if you do not have a separate license from TIBCO Software
// Inc.  Except as expressly set forth in such license agreement, this
// source code, or any portion thereof, may not be used, modified,
// reproduced, transmitted, or distributed in any form or by any means,
// electronic or mechanical, without written permission from  TIBCO
// Software Inc.

package com.tibco.hawk.jshma.plugin;

import COM.TIBCO.hawk.talon.MicroAgentException;

import com.tibco.hawk.jshma.util.NamedTabularData;

/**
 * Applications wish to be notified when subscribed Hawk data arrives has to
 * implement this interface and use HawkMethodSubscription or
 * HawkEventSubscription to subscribe.
 */
public interface HawkSubscriptionListener {
	public static final String SUBSCRIPTION_ERROR = "Error";
	public static final String SUBSCRIPTION_ERROR_CLEARED = "Clear";
	public static final String SUBSCRIPTION_TERMINATED = "Terminated";

	/**
	 * The callback to be called when a set of subscribed data has arrived.
	 * 
	 * @param agentName
	 *            The name of the TIBCO Hawk agent (normally, it's the same as
	 *            the machine host name) where this data came from.
	 * @param microagentName
	 *            The name of the TIBCO Hawk MicroAgent where this data came
	 *            from.
	 * @param data
	 *            The subscribed data
	 */
	public void onData(String agentName, String microagentName, NamedTabularData data);

	/**
	 * The callback to be called when a status change on a subscription has
	 * occurred.
	 * 
	 * @param agentName
	 *            The name of the TIBCO Hawk agent (normally, it's the same as
	 *            the machine host name) where this data came from.
	 * @param microagentName
	 *            The name of the TIBCO Hawk MicroAgent where this data came
	 *            from.
	 * @param subscription
	 *            The HawkMethodSubscription object.
	 * @param status
	 *            The suscription status. It can be
	 *            HawkSubscriptionListener.SUBSCRIPTION_ERROR or
	 *            HawkSubscriptionListener.SUBSCRIPTION_ERROR_CLEARED or
	 *            HawkSubscriptionListener.SUBSCRIPTION_TERMINATED.
	 * @param exception
	 *            It has value only when the status is
	 *            HawkSubscriptionListener.SUBSCRIPTION_ERROR.
	 */
	public void onSubscriptionStatus(String agentName, String microagentName, HawkMethodSubscription subscription,
			String status, MicroAgentException exception);

}
