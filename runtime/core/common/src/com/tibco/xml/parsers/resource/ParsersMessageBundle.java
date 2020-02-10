//(c) Copyright 2011, TIBCO Software Inc.  All rights reserved.
//LEGAL NOTICE:  This source code is provided to specific authorized end
//users pursuant to a separate license agreement.  You MAY NOT use this
//source code if you do not have a separate license from TIBCO Software
//Inc.  Except as expressly set forth in such license agreement, this
//source code, or any portion thereof, may not be used, modified,
//reproduced, transmitted, or distributed in any form or by any means,
//electronic or mechanical, without written permission from  TIBCO
//Software Inc.
package com.tibco.xml.parsers.resource;

import com.tibco.xml.parsers.localized.BundleMessage;
import com.tibco.xml.parsers.localized.MessageBundle;

public class ParsersMessageBundle extends MessageBundle {
    /**
     * Following member is accessed by MessageBundle.initializeMessages, do
     * not remove!
     */
    static public final String BUNDLE_NAME = "com.tibco.xml.parsers.resource.Resources"; //$NON-NLS-1$

    static {
        MessageBundle.initializeMessages(ParsersMessageBundle.class);
    }

    static public BundleMessage LoadingException;
	static public BundleMessage ClassCastException;

}
