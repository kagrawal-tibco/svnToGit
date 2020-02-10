/*
 * Copyright (c) 2005 TIBCO Software Inc.
 * All Rights Reserved.
 *
 * LEGAL NOTICE: This source code is provided to specific authorized end
 * users pursuant to a separate license agreement.  You MAY NOT use this
 * source code if you do not have a separate license from TIBCO Software Inc.
 * Except as expressly set forth in such license agreement, this source code,
 * or any portion thereof, may not be used, modified, reproduced,
 * transmitted, or distributed in any form or by any means, electronic or
 * mechanical, without written permission from TIBCO Software Inc.
 */
package com.tibco.xml.parsers.localized;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * A localized message that can formatted in the same way as {@link
 * MessageFormat}.  Instances of this class are created by the {@link
 * MessageBundle} class in order to encapsulate the bundle name and message
 * key for further resolution.  The message text for the default locale is
 * provided at construction time.  Text for alternate locales is created on
 * demand using the JDK <code>ResourceBundle</code> infrastructure.  The
 * class also supports the notion of error code and component ID, which if
 * present are prepended to any formatting results. <p>
 *
 * For development and testing purposes, we provide a system property called
 * <code>com.tibco.neo.localized.BundleMessage.disableResourceCache</code>.
 * When the property value is set to "true", this class will perform no
 * caching of localized text, and will furthermore disable the JDK
 * <code>ResourceBundle</code>'s caching of localized text.  This behavior
 * allows developers to see changes to <code>Resources.properties</code> files
 * reflected in their application without a JVM restart. <p>
 *
 * @version $Revision: $, $Date: $
 * @author <a href="mailto:ldomenec@tibco.com">Laurent Domenech</a>
 */
public final class BundleMessage implements Serializable {

    /**
     * Serialization Id
     */
    private static final long serialVersionUID = 8254826380458026110L;

    /**
     * A development and testing flag which forces all message lookup to go
     * through the JDK resource bundle infrastructure and disables resource
     * bundle caching.  This behavior allows developers to see changes to
     * <code>Resources.properties</code> files reflected in their application
     * without a JVM restart. <p>
     */
    static private boolean cacheDisabled;

    private final String messageBundleName;

    private final String messageKey;

    private final String message;

    private final int errorCode;

    private final String componentID;

    private transient final ClassLoader classLoader;

    /**
     * Contructs a new instance of BundleMessage.
     * 
     * @param messageBundleName
     *            name of the bundle.
     * @param messageKey
     *            messageKey of the message.
     * @param message
     *            message for the default {@link Locale}.
     * @param classLoader 
     *            class loader to use when loading resource bundles
     *            for non-default locales during invocation of {@link #format(Locale)}
     *            and variants
     */
    public BundleMessage(final String  messageBundleName, final String messageKey, final String message, final ClassLoader classLoader) {
        this(messageBundleName, messageKey, message, -1, null, classLoader);
    }

    /**
     * Contructs a new instance of BundleMessage.
     * 
     * @param messageBundleName
     *            name of the bundle.
     * @param messageKey
     *            messageKey of the message.
     * @param message
     *            message for the default {@link Locale}.
     * @param errorCode
     *            optional message error code, -1 for none
     * @param componentID
     *            optional message component ID, null for none
     * @param classLoader 
     *            class loader to use when loading resource bundles
     *            for non-default locales during invocation of {@link #format(Locale)}
     *            and variants
     */
    public BundleMessage(final String  messageBundleName, final String messageKey, final String message, final int errorCode, final String componentID,
                         final ClassLoader classLoader) {

        this.messageBundleName = messageBundleName;
        this.messageKey = messageKey;
        this.message = message;
        this.errorCode = errorCode;
        this.componentID = componentID;
        this.classLoader = classLoader;
    }

    /**
     * @return the message bundle name.
     */
    public String getBundleName() {
        return this.messageBundleName;
    }

    /**
     * @return the message messageKey.
     */
    public String getMessageKey() {
        return this.messageKey;
    }
    
    /**
     * @return the message error code, -1 if none set.
     */
    public int getErrorCode() {
        return this.errorCode;
    }
    
    /**
     * @return the message component ID, null if none set.
     */
    public String getComponentID() {
        return this.componentID;
    }
    
    /** 
     * Returns the unique identifier of the message.  The identifier consists
     * of the {@link MessageBundle} name followed by the message key,
     * separated by the dot '.' character. <p>
     *
     * @return the unique identifier of the message
     */
    public String getIdentifier() {
        return this.messageBundleName + "." + this.messageKey;
    }
    
    /**
     * Formats the given message's.
     * 
     * @return the formatted String
     */
    public String format() {
        return format((Object[]) null);
    }

    /**
     * Formats the given message's substitution locations with the given string
     * values.
     * 
     * @param argument
     *            the object to be inserted into the message
     * @return the formatted String
     */
    public String format(final Object argument) {
        return format(new Object[] { argument });
    }

    /**
     * Formats the given message's substitution locations with the given string
     * values.
     * 
     * @param argument1
     *            An object to be inserted into the message
     * @param argument2
     *            A second object to be inserted into the message
     * @return the formatted String
     */
    public String format(final Object argument1, final Object argument2) {
        return format(new Object[] { argument1, argument2 });
    }

    /**
     * Formats the given message's substitution locations with the given string
     * values.
     * 
     * @param argument1
     *            An object to be inserted into the message
     * @param argument2
     *            A second object to be inserted into the message
     * @param argument3
     *            A third object to be inserted into the message
     * @return the formatted String
     */
    public String format(final Object argument1, final Object argument2, final Object argument3) {
        return format(new Object[] { argument1, argument2, argument3 });
    }

    /**
     * Formats the given message's substitution locations with the given string
     * values.
     * 
     * @param argument1
     *            An object to be inserted into the message
     * @param argument2
     *            A second object to be inserted into the message
     * @param argument3
     *            A third object to be inserted into the message
     * @param argument4
     *            A forth object to be inserted into the message
     * @return the formatted String
     */
    public String format(final Object argument1, final Object argument2, final Object argument3, final Object argument4) {
        return format(new Object[] { argument1, argument2, argument3, argument4 });
    }

    /**
     * Formats the given message's substitution locations with the given string
     * values.
     * 
     * @param arguments
     *            An array of objects to be inserted into the message
     * @return the formatted String
     */
    public String format(final Object[] arguments) {

        Locale locale = LocalizedMessage.getThreadLocale(); // use thread-local locale if present
        
        if (locale == null) {
            locale = Locale.getDefault();
        }

        if (BundleMessage.cacheDisabled) {
            return format(locale, arguments);

        } else if (this.message != null) {
            return format(this.componentID, this.errorCode, this.message, locale, arguments);

        } else if (this.messageBundleName == null || this.messageKey == null) {
            return "Missing message '"
                + this.messageKey 
                + "' for default locale in bundle '" 
                + this.messageBundleName + "'."; //$NON-NLS-1$

        } else {
            return format(locale, arguments);
        }
    }
    
    /**
     * Formats the given message's using the specified locale.
     * 
     * @param locale
     *            locale to use for the formatting of the message
     * @return the formatted String
     */
    public String format(final Locale locale) {
        return format(locale, (Object[]) null);
    }

    /**
     * Formats the given message's substitution locations with the given string
     * values using the specified locale.
     * 
     * @param locale
     *            locale to use for the formatting of the message
     * @param argument
     *            the object to be inserted into the message
     * @return the formatted String
     */
    public String format(final Locale locale, final Object argument) {
        return format(locale, new Object[] { argument });
    }

    /**
     * Formats the given message's substitution locations with the given string
     * values using the specified locale.
     * 
     * @param locale
     *            locale to use for the formatting of the message
     * @param argument1
     *            An object to be inserted into the message
     * @param argument2
     *            A second object to be inserted into the message
     * @return the formatted String
     */
    public String format(final Locale locale, final Object argument1, final Object argument2) {
        return format(locale, new Object[] { argument1, argument2 });
    }

    /**
     * Formats the given message's substitution locations with the given string
     * values using the specified locale.
     * 
     * @param locale
     *            locale to use for the formatting of the message
     * @param argument1
     *            An object to be inserted into the message
     * @param argument2
     *            A second object to be inserted into the message
     * @param argument3
     *            A third object to be inserted into the message
     * @return the formatted String
     */
    public String format(final Locale locale, final Object argument1, final Object argument2, final Object argument3) {
        return format(locale, new Object[] { argument1, argument2, argument3 });
    }

    /**
     * Formats the given message's substitution locations with the given string
     * values using the specified locale.
     * 
     * @param locale
     *            locale to use for the formatting of the message
     * @param argument1
     *            An object to be inserted into the message
     * @param argument2
     *            A second object to be inserted into the message
     * @param argument3
     *            A third object to be inserted into the message
     * @return the formatted String
     */
    public String format(final Locale locale, final Object argument1, final Object argument2, final Object argument3,
                         final Object argument4) {

        return format(locale, new Object[] { argument1, argument2, argument3, argument4 });
    }

    /**
     * Formats the given message's substitution locations with the given string
     * values using the specified locale.
     * 
     * @param locale
     *            locale to use for the formatting of the message
     * @param arguments
     *            An array of objects to be inserted into the message
     * @return the formatted String
     */
    public String format(final Locale locale, final Object[] arguments) {
        if (((!Locale.getDefault().equals(locale)) || this.message == null || BundleMessage.cacheDisabled) && 
            this.messageBundleName != null && this.messageKey != null) {
            
            if (BundleMessage.cacheDisabled) {
                clearResourceBundleCache();
            }
            
            ClassLoader loader = this.classLoader;
            if (loader == null) {
                loader = Thread.currentThread().getContextClassLoader();
            }

            ResourceBundle bundle = ResourceBundle.getBundle(this.messageBundleName, locale, loader);
            
            if (bundle != null) {
                String pattern; 
                
                try {
                    pattern = bundle.getString(this.messageKey);
                } catch (MissingResourceException exception) {
                    pattern = "Missing message '"
                        + this.messageKey
                        + "' for locale '"
                        + locale.getDisplayName()
                        + "' in bundle '" + this.messageBundleName + "'."; //$NON-NLS-1$
                }
                return format(this.componentID, this.errorCode, pattern, locale, arguments);
            }
        }
        return format(this.componentID, this.errorCode, this.message, locale, arguments);
    }

    /**
     * <p> Formats the given message's substitution locations with the given string
     * values using the specified locale.  Do not include the message's component
     * or error code. </p>
     * 
     * @param locale
     *            locale to use for the formatting of the message
     * @param arguments
     *            An array of objects to be inserted into the message
     * @return the formatted String
     */
    public String formatWithoutErrorCode(final Locale locale, final Object[] arguments) {
        if (((!Locale.getDefault().equals(locale)) || this.message == null || BundleMessage.cacheDisabled) && 
            this.messageBundleName != null && this.messageKey != null) {
            
            if (BundleMessage.cacheDisabled) {
                clearResourceBundleCache();
            }
            
            ClassLoader loader = this.classLoader;
            if (loader == null) {
                loader = Thread.currentThread().getContextClassLoader();
            }

            ResourceBundle bundle = ResourceBundle.getBundle(this.messageBundleName, locale, loader);
            
            if (bundle != null) {
                String pattern; 
                
                try {
                    pattern = bundle.getString(this.messageKey);
                } catch (MissingResourceException exception) {
                    pattern = "Missing message '"
                        + this.messageKey
                        + "' for locale '"
                        + locale.getDisplayName()
                        + "' in bundle '" + this.messageBundleName + "'."; //$NON-NLS-1$
                }
                return format(null, -1, pattern, locale, arguments);
            }
        }
        return format(null, -1, this.message, locale, arguments);
    }

    /**
     * Formats the given message's substitution locations with the given string
     * values using the specified locale.
     * 
     * @param componentID 
     *             an optional string which, if null, is prepended to the
     * formatted output.  Both this argument and <code>errorCode</code> must be
     * present for the information to be prepended.
     * @param errorCode 
     *             an optional error code which, if not -1, is prepended
     * along with the component ID to the formatted output.  Both this
     * argument and <code>componentID</code> must be present for the
     * information to be prepended.
     * @param pattern
     *            pattern to use with the {@link java.text.MessageFormat}
     * @param locale
     *            locale to use with the {@link java.text.MessageFormat}
     * @param arguments
     *            An array of objects to be inserted into the message
     * @return the formatted String
     */
    static String format(final String componentID, final int errorCode, final String pattern, final Locale locale, final Object[] arguments) {

        String prefix;
        
        if (componentID != null && errorCode != -1) {
            prefix = MessageFormat.format("{0}-{1,number,000000}: ", componentID, errorCode); //$NON-NLS-1$

        } else {
            prefix = null;
        }

        if (pattern == null) {            
            return "Missing message for locale '" + locale.getDisplayName() + "'."; //$NON-NLS-1$
        }
        
        if (arguments == null) {
            return prefix == null ? pattern : prefix + pattern;
        }

        final MessageFormat messageFormat;

        if (locale != null) {
             messageFormat = new MessageFormat(pattern, locale);
             
        } else {
            messageFormat = new MessageFormat(pattern);
        }

        String res = messageFormat.format(arguments);

        return prefix == null ? res : prefix + res;
    }
    
    /**
     * Returns the message in the specified locale.
     * 
     * @param locale
     *            locale to use for the formatting of the message.
     * @return the message String in the specified locale.
     */
    public String toString(final Locale locale) {
        return format(locale);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return format();
    }

    /**
     * A special hack to clear the JDK <code>ResourceBundle</code>
     * implementation's cache of bundles.  Clearing the cache allows changes
     * to a Resources.properties file to be immediately reflected in a 
     * running application, which is useful during development and testing. <p>
     *
     * <i>This method assumes the <code>ResourceBundle</code> implementation as
     * of JDK1.4, and its successful use is therefore quite fragile.  Any failure 
     * is ignored, and the cache remains in place.</i>
     */
    @SuppressWarnings({ "rawtypes" })
    static private void clearResourceBundleCache() {

        java.util.Map cacheList;

        try {
            java.lang.reflect.Field field = java.util.ResourceBundle.class.getDeclaredField("cacheList");

            field.setAccessible(true);

            if ((cacheList = (java.util.Map) field.get(null)) != null) {
                synchronized(cacheList) {
                    cacheList.clear();
                }
            }
        } catch(Throwable ignore) {
        }
    }

    static {
        BundleMessage.cacheDisabled = Boolean.getBoolean("com.tibco.neo.localized.BundleMessage.disableResourceCache");
    }
}
