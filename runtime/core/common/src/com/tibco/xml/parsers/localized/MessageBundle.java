
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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Common superclass for all message bundle classes. A proper message bundle class should define the static field named
 * BUNDLE_NAME and a set of static fields of {@link BundleMessage} type. They should also call
 * {@link #initializeMessages(Class)} in their static initializer:
 * 
 * <code>
 *      class MyMessages extends MessageBundle {
 *              
 *              public final static String BUNDLE_NAME = "mypackage.mymessages";
 *      
 *              static {
 *                      MessageBundle.initializeMessages(MyMessages.class);
 *              }
 *      
 *      static public BundleMessage A;
 *      }
 * </code>
 * 
 * The format of {@link java.util.PropertyResourceBundle} is as usual messageKey=value with the value being the text or pattern of
 * the message. The messageKey can be either the name of the field with exactly the same syntax as defined in the
 * message bundle class. Or the name of field followed by a dot, followed by the message ID.
 * 
 * TODO document this class as a marker
 */
public class MessageBundle {

    /**
     * Common suffix for error code keys.  Public for shared use by generator tools in R2D2.
     */
    static public final String ERROR_CODE_KEY_SUFFIX = ".errorCode";

    /**
     * Special key to hold for component ID.  Public for shared use by generator tools in R2D2.
     */
    static public final String COMPONENT_ID_KEY = "ResourceList.componentID";

    /**
     * static field name that extending classes should use for defining the resource bundle name.
     */
    protected final static String BUNDLE_FIELDNAME = "BUNDLE_NAME";

    /**
     * Protected constructor to avoid creation of instances of this class while allowing extensions of this class.
     */
    protected MessageBundle() {
    }

    /**
     * Initializes the given class with the values from the specified message class.
     * 
     * @param clazz
     *            class inheriting from the <code>MessageBundle</code> class.
     * @throws IllegalArgumentException
     *             when the class does not defines a static field called BUNDLE_NAME.
     */
    public static void initializeMessages(final Class<?> clazz) throws IllegalArgumentException {
        MessageBundle.initializeMessages(Locale.getDefault(), clazz);
    }

    /**
     * Initializes the given class with the values from the specified message class.
     * 
     * @param locale
     *            locale in wich the message must be represented.
     * @param clazz
     *            class inheriting from the <code>MessageBundle</code> class.
     * 
     * @throws IllegalArgumentException
     *             when the class does not defines a static field called BUNDLE_NAME.
     */
    private static void initializeMessages(final Locale locale, final Class<?> clazz) throws IllegalArgumentException {
        if (!MessageBundle.class.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException("clazz must inherit from MessageBundle."); //$NON-NLS-1$
        }

        final String bundleName;
        try {
            bundleName = (String) clazz.getField(MessageBundle.BUNDLE_FIELDNAME).get(null);
        } catch (Exception e) {
            throw new IllegalArgumentException("clazz must define BUNDLE_NAME."); //$NON-NLS-1$
        }

        final ResourceBundle resourceBundle = ResourceBundle.getBundle(bundleName, locale, clazz.getClassLoader());
        if (resourceBundle == null) {
            throw new IllegalArgumentException("Must be able to find a MessageBundle with name: " + bundleName); //$NON-NLS-1$
        }

        // Iterate over the fields in the class to assign them and
        // make sure that there aren't any empty ones
        final Field[] fields = clazz.getDeclaredFields();
        final int MOD_EXPECTED = Modifier.PUBLIC | Modifier.STATIC;
        final int MOD_MASK = MOD_EXPECTED | Modifier.FINAL;
        final int numFields = fields.length;

        String componentID = maybeGetComponentID(resourceBundle); // common for all keys in this bundle

        for (int i = 0; i < numFields; i++) {
            Field field = fields[i];
            if (!BundleMessage.class.isAssignableFrom(field.getType())
                    || (field.getModifiers() & MOD_MASK) != MOD_EXPECTED) {
                continue;
            }
            try {
                // Set the value into the field if its empty. We should never
                // get an exception here because
                // we know we have a public static non-final field. If we do get
                // an exception, silently
                // log it and continue. This means that the field will (most
                // likely) be un-initialized and
                // will fail later in the code and if so then we will see both
                // the NPE and this error.
                if (field.get(null) == null) {
                    String key = field.getName();
                    String value;
                    try {
                        value = resourceBundle.getString(key);
                    } catch (MissingResourceException exception) {
                        value = "Missing message: " + field.getName() + " for locale: " + locale.getDisplayName() + " in bundle: " + bundleName; //$NON-NLS-1$ //$NON-NLS-2$
                    }

                    int    errorCode = maybeGetErrorCode(key, resourceBundle);

                    field.set(null, new BundleMessage(bundleName, key, value, errorCode, componentID, clazz.getClassLoader()));
                }
            } catch (IllegalAccessException e1) {
                // Field cannot be accessed, we just ignore it.
            }
        }
    }

    /**
     * Check the specified resource bundle for a key variant whose value is
     * an error code.  If such exists, convert the error code string to a
     * number and return it.  If no such key variant exists, return -1.  If
     * the key variant's value is not a numeric string, return -1. <p>
     *
     * @param key the base key to look for
     * @param resourceBundle the bundle in which to look
     * @return the error code, -1 if not found or invalid
     */
    static private int maybeGetErrorCode(String key, ResourceBundle resourceBundle) {
        try {
            return Integer.parseInt(resourceBundle.getString(key + ERROR_CODE_KEY_SUFFIX));

        } catch (MissingResourceException ignore) {
            return -1;

        } catch (NumberFormatException ignore) {
            return -1;
        }
    }

    /**
     * Check the specified resource bundle for a key whose value is a
     * component ID.  If such exists, return it otherwise return null. <p>
     *
     * @param resourceBundle the bundle in which to look
     */
    static private String maybeGetComponentID(ResourceBundle resourceBundle) {
        try {
            return resourceBundle.getString(COMPONENT_ID_KEY);

        } catch (MissingResourceException ignore) {
            return null;
        }
    }
}
