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

/**
 * Applications wish to be notified when a new MicroAgent is added or removed
 * has to implement this interface and use HawkConsoleBase.addMicroAgentListener
 * to register or remove a listener.
 */
public interface HawkMicroagentListener {

	/**
	 * The callback to be called when a new MicroAgent is added.
	 */
	public void onMicroAgentAdded(String agentName, String microagentName);

	/**
	 * The callback to be called when a new MicroAgent is removed.
	 */
	public void onMicroAgentRemoved(String agentName, String microagentName);

}
