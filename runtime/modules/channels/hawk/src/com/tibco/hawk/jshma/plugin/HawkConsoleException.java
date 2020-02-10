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

import java.lang.reflect.Method;

import com.tibco.tibrv.TibrvException;

/**
 * The class is used for TIBCO Hawk Console related Exceptions.
 */

public class HawkConsoleException extends java.lang.Exception {
	public HawkConsoleException(String s) {
		super(s);
	}

	public HawkConsoleException(String s, Throwable cause) {
		super(s);
		initCause(cause);
	}

	public static Throwable getLowestCause(Throwable cause) {

		if (cause == null)
			return null;

		Throwable lowerCause = null;
		if (cause instanceof TibrvException) {
			lowerCause = ((TibrvException) cause).internal;
		} else
			try {
				Method m = cause.getClass().getMethod("getLinkedException", null);
				if (m != null) {
					lowerCause = (Throwable) m.invoke(cause, null);
				}
			} catch (Throwable ignore) {
			}

		if (lowerCause == null) {
			lowerCause = cause.getCause();
		}

		if (lowerCause == null)
			return cause;
		else
			return getLowestCause(lowerCause);
	}

}
