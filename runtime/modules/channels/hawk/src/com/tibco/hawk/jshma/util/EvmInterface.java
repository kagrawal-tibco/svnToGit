// (c) Copyright 2001,2002 TIBCO Software Inc.  All rights reserved.
// LEGAL NOTICE:  This source code is provided to specific authorized end
// users pursuant to a separate license agreement.  You MAY NOT use this
// source code if you do not have a separate license from TIBCO Software
// Inc.  Except as expressly set forth in such license agreement, this
// source code, or any portion thereof, may not be used, modified,
// reproduced, transmitted, or distributed in any form or by any means,
// electronic or mechanical, without written permission from  TIBCO
// Software Inc.

package com.tibco.hawk.jshma.util;

/**
 * Any application wants to use <code>Evm</code> event manager has to implement
 * this interface. Events are identified by names. An application can use
 * multiple events. An applications implements EvemInterface has to provide a
 * method <code>isSet(String event)</code> for the event manager to check
 * whether s specific event is set. It's appliaction's responsibility to clear
 * the event (the method <code>isSet(String event)</code> returns false) after
 * an event has been handled.
 * 
 * @see EvmInterface
 */

public interface EvmInterface {
	/**
	 * Check whether the specified event is set.
	 * 
	 * @param event
	 *            the event name (identifier)
	 * @return true if the specified event is set. This method should return
	 *         false after an event has been handled or when the event condition
	 *         no longer exists.
	 */
	public boolean isSet(String event);

}
