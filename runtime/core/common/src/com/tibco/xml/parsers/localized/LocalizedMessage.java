
//(c) Copyright 2005, TIBCO Software Inc.  All rights reserved.
//LEGAL NOTICE:  This source code is provided to specific authorized end
//users pursuant to a separate license agreement.  You MAY NOT use this
//source code if you do not have a separate license from TIBCO Software
//Inc.  Except as expressly set forth in such license agreement, this
//source code, or any portion thereof, may not be used, modified,
//reproduced, transmitted, or distributed in any form or by any means,
//electronic or mechanical, without written permission from  TIBCO
//Software Inc.

package com.tibco.xml.parsers.localized;

import java.util.Locale;

/**
 * A localized message that uses the {@link MessageBundle} localization mechanism. The
 * implementation wraps {@link BundleMessage} adding {@link Locale} setting and arguments.
 * 
 */
public class LocalizedMessage extends SerializableLocalizedMessage {

	/**
	 * Serialization Id. This class is not serializable since it holds an Object[] passed in one of the constructors.
	 */
	private static final long serialVersionUID = 1L;
    private final Object[] arguments;

    static private ThreadLocal<Locale> threadLocale = new ThreadLocal<Locale>() {
        protected synchronized Locale initialValue() {
            return Locale.getDefault();
        }
    };

    /**
    * Set this thread's copy of the current locale.  Provided as a convenience mechanism
    * so that locale-aware methods don't need to be passed a locale, but can instead use
    * their thread's locale. <p>
    *
    * @param locale this thread's current locale
    */
   static public void setThreadLocale(Locale locale) {
       threadLocale.set(locale);
   }

    /**
    * Get this thread's copy of the current locale.  Provided as a convenience mechanism
    * so that locale-aware methods don't need to be passed a locale, but can instead use
    * their thread's locale. <p>
    *
    * @return this thread's current locale
    */
   static public Locale getThreadLocale() {
       return threadLocale.get();
   }

    /**
     * Creates a <code>LocalizedMessage</code> from the <code>BundleMessage</code> argument in the default
     * <code>Locale</code>.
     * 
     * @param bundleMessage
     *            <code>BundleMessage</code> to localize.
     * @param arguments
     *            Argument array use for the message formatting.
     */
    public LocalizedMessage(BundleMessage bundleMessage, Object[] arguments) {
    	super(bundleMessage, arguments);
    	this.arguments = arguments;
    }
  
    /**
     * Creates a <code>LocalizedMessage</code> from the <code>BundleMessage</code> argument in the default
     * <code>Locale</code>.
     * 
     * @param bundleMessage
     *            <code>BundleMessage</code> to localize.
     */
    public LocalizedMessage(BundleMessage bundleMessage) {
        this(bundleMessage, null);
    }
        
    /**
     * <p> Return the argument array with which this message was constructed,
     * null or a zero-length array if the message has no arguments. </p>
     *
     * @return this message's argument array
     */
    public Object[] getArguments() {
        return this.arguments;
    }
}
