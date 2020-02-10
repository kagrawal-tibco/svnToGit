//(c) Copyright 2005, TIBCO Software Inc.  All rights reserved.
//LEGAL NOTICE:  This source code is provided to specific authorized end
//users pursuant to a separate license agreement.  You MAY NOT use this
//source code if you do not have a separate license from TIBCO Software
//Inc.  Except as expressly set forth in such license agreement, this
//source code, or any portion thereof, may not be used, modified,
//reproduced, transmitted, or distributed in any form or by any means,
//electronic or mechanical, without written permission from  TIBCO
//Software Inc.

package com.tibco.xml.parsers.common;

import java.util.Locale;

/**
 * This interface is OBSOLETE.  Don't use it. <p>
 *
 * <i>The only implementation of this interface is
 * <code>com.tibco.neo.localized.LocalizedMessage</code></i>.  There should
 * be no need for further implementations; again, the reason for this
 * interface is not because we think there should be multiple
 * implementations, but because we want to keep the implementation above the
 * service container shared class path.
 */
public interface LocalizableMessage {
    
    /**
     * Returns the unique identifier of the message.
     * @return the unique identifier of the message.
     */
    String getIdentifier();
    
    /**
     * Creates a localized version of this message.
     * 
     * @param locale locale in which the message has to be returned.
     * @return a localized version of this message.
     */
    String getLocalizedMessage(Locale locale);
    
    /**
     * Creates a version of this message for the default locale.
     * 
     * @return a version of this message for the default locale.
     */
    String getMessage();
}
