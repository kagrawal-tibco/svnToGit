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

//import java.lang.IllegalArgumentException;
import COM.TIBCO.hawk.talon.MicroAgentException;

import com.tibco.hawk.jshma.util.NamedArray;
import com.tibco.hawk.jshma.util.NamedTabularData;

/**
 * This class initiates a TIBCO Hawk method subscription. The application has to
 * provide a HawkSubscriptionListener to receive the subscribed data.
 */
public class HawkMethodSubscription extends HawkMethodSubscriber {

	private static String sClassName = HawkMethodSubscription.class.getName();

	private HawkSubscriptionListener mListener;

	/**
	 * Initiate a TIBCO Hawk method subscription
	 * 
	 * @param console
	 *            The Hawk Console handle object
	 * @param agentName
	 *            The target machine TIBCO Hawk agent name (normally, it's the
	 *            same as the machine host name).
	 * @param microAgentName
	 *            The name of the TIBCO Hawk MicroAgent of interest.
	 * @param methodName
	 *            The name of the TIBCO Hawk MicroAgent method to be subscribed.
	 * @param params
	 *            method input parameters The parameters specified in the
	 *            NamedArray does not have to follow the exact order defined in
	 *            the "method description". Also, optional parameters can be
	 *            omitted.
	 * @param interval
	 *            the subscription internal in number of seconds It has to be
	 *            greater than 0 for synchronous methods
	 * @param listener
	 *            the instance implements HawkSubscriptionListener to receive
	 *            onData callback.
	 */

	public HawkMethodSubscription(HawkConsoleBase console, String agentName, String microAgentName, String methodName,
			NamedArray params, int interval, HawkSubscriptionListener listener) throws HawkConsoleException,
			MicroAgentException {

		mListener = listener;
		HawkMethodSubscriberInit(console, agentName, microAgentName, methodName, params, null, null, 0, interval);
	}

	/**
	 * This method is called to get the subscription handle.
	 * 
	 * @return The method subscription handle.
	 */
	public Object getSubscriptionHandle() {
		return mSubscriptionHandle;
	}

	/**
	 * This method is called when data for this method subscription is
	 * available. If "listener" is not specified in constructor, this method can
	 * be overriden to receive the data.
	 * 
	 * @param data
	 *            The method subscribed data.
	 */
	protected void addResult(NamedTabularData data) {
		if (mListener != null)
			mListener.onData(mAgentName, mMicroAgentName, data);
		else
			super.addResult(data);
	}

	/**
	 * This method is called when subscription status is changed. If "listener"
	 * is not specified in constructor, this method can be overriden to receive
	 * the status change.
	 * 
	 * @param status
	 *            The suscription status. It can be
	 *            HawkSubscriptionListener.SUBSCRIPTION_ERROR or
	 *            HawkSubscriptionListener.SUBSCRIPTION_ERROR_CLEARED or
	 *            HawkSubscriptionListener.SUBSCRIPTION_TERMINATED.
	 * @param exception
	 *            It has value only when the status is
	 *            HawkSubscriptionListener.SUBSCRIPTION_ERROR.
	 */
	protected void onSubscriptionStatus(String status, MicroAgentException exception) {
		if (mListener != null)
			mListener.onSubscriptionStatus(mAgentName, mMicroAgentName, this, status, exception);
	}

}
