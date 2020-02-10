
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

import java.io.Serializable;
import java.util.Locale;

import com.tibco.xml.parsers.common.LocalizableMessage;

/**
 * A localized message that uses the {@link MessageBundle} localization mechanism. The
 * implementation wraps {@link BundleMessage} adding {@link Locale} setting and arguments.
 */
public class SerializableLocalizedMessage implements LocalizableMessage, Serializable {

    /**
     * Serialization id
     */
    private static final long serialVersionUID = 6306081301091584420L;

    private final BundleMessage bundleMessage;

    private final Serializable[] serializableArguments;

    /**
     * Creates a <code>LocalizedMessage</code> from the <code>BundleMessage</code> argument in the default
     * <code>Locale</code>.
     * 
     * @param bundleMessage
     *            <code>BundleMessage</code> to localize.
     * @param arguments
     *            Argument array use for the message formatting.
     */
    public SerializableLocalizedMessage(BundleMessage bundleMessage, Object[] arguments) {
        this.bundleMessage = bundleMessage;
        this.serializableArguments = objectArrayToSerializableArray(arguments);
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
    public SerializableLocalizedMessage(BundleMessage bundleMessage, Serializable[] arguments) {
        this.bundleMessage = bundleMessage;
        this.serializableArguments = arguments;
    }
  
    /**
     * Creates a <code>LocalizedMessage</code> from the <code>BundleMessage</code> argument in the default
     * <code>Locale</code>.
     * 
     * @param bundleMessage
     *            <code>BundleMessage</code> to localize.
     */
    public SerializableLocalizedMessage(BundleMessage bundleMessage) {
        this(bundleMessage, null);
    }
        
    /**
     * <p> Return the unique identifier of the message. </p>
     *
     * @return the unique identifier of the message
     * @see com.tibco.xml.parsers.common.LocalizableMessage#getIdentifier()
     */
    public String getIdentifier() {
        return this.bundleMessage.getIdentifier();
    }
    
    /**
     * Get this message's text, localized to the {@link com.tibco.xml.parsers.localized.LocalizedMessage#getThreadLocale
     * thread-local} locale. <p>
     * 
     * @return this message's text, localized to the default locale. <p>
     */
    public String getLocalizedMessage() {
        return getLocalizedMessage(LocalizedMessage.getThreadLocale());
    }

    /**
     * Get this message's text, localized to the specified locale.  If the
     * specified locale is null, localize to the {@link com.tibco.xml.parsers.localized.LocalizedMessage#getThreadLocale
     * thread-local} locale. <p>
     * @param locale the locale to retrieve the message for.
     *
     * @return this message's text, localized to the specified locale
     */
    public String getLocalizedMessage(Locale locale) {
        if (locale == null) {
            return this.bundleMessage.format(LocalizedMessage.getThreadLocale(), this.serializableArguments);

        } else {
            return this.bundleMessage.format(locale, this.serializableArguments);
        }
    }

    /**
     * Get this message's text, localized to the {@link com.tibco.xml.parsers.localized.LocalizedMessage#getThreadLocale
     * thread-local} locale.  Do not add component and error code. <p>
     * 
     * @return this message's text, localized to the default locale. <p>
     */
    public String getLocalizedMessageWithoutErrorCode() {
        return getLocalizedMessageWithoutErrorCode(LocalizedMessage.getThreadLocale());
    }

    /**
     * Get this message's text, localized to the specified locale.  If the
     * specified locale is null, localize to the {@link com.tibco.xml.parsers.localized.LocalizedMessage#getThreadLocale
     * thread-local} locale. Do not add component and error code. <p>
     *
     * @param locale the locale to retrieve the message for.
     * @return this message's text, localized to the specified locale
     */
    public String getLocalizedMessageWithoutErrorCode(Locale locale) {
        if (locale == null) {
            return this.bundleMessage.formatWithoutErrorCode(LocalizedMessage.getThreadLocale(), this.serializableArguments);

        } else {
            return this.bundleMessage.formatWithoutErrorCode(locale, this.serializableArguments);
        }
    }

    /**
     * Get the message.
     * @return this message's text
     */
    public String getMessage() {
        return this.bundleMessage.format(this.serializableArguments);
    }

    /**
     * <p> Return the argument array with which this message was constructed,
     * null or a zero-length array if the message has no arguments. </p>
     *
     * @return this message's argument array
     */
    public Serializable[] getSerializableArguments() {
        return this.serializableArguments;
    }

    /**
     * <p> Return the message object with which this localized message was
     * constructed.  The message object uniquely identifies a message that
     * can be parameterized with arguments and translated into a particular
     * locale.  It also provides additional information such as an error
     * code. </p>
     *
     * @return the message object with which this localized message was
     * constructed
     */
    public BundleMessage getBundleMessage() {
        return this.bundleMessage;
    }

    /**
     * <p> Return the string representation of this message.  Equivalent to
     * {@link #getLocalizedMessage()}. </p>
     *
     * @return the string representation of this message
     */
    public String toString() {
        return getLocalizedMessage();
    }
    
    /**
     * <p> Convert the specified array of objects to an array of serializable objects,
     * by casting elements that are serializable, or converting to strings those that
     * are not serializable. </p>
     *
     * @return the string representation of this message
     */
    private Serializable[] objectArrayToSerializableArray(Object[] objs) {

        if (objs == null) {
            return null;
        }

        Serializable[]result = new Serializable[objs.length];

        for (int i = 0; i < objs.length; ++i) {

            Object obj = objs[i];

            if (obj == null) {
                result[i] = null;

            } else if (obj instanceof Serializable) {
                result[i] = (Serializable) obj;

            } else {
                result[i] = obj.toString();
            }
        }
        return result;
    }
}
