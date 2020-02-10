package com.tibco.cep.decision.table.utils;

import java.util.Locale;
import java.util.ResourceBundle;

public class DecisionResources {
    static final String BASENAME = "com.tibco.cep.decision.table.utils";

    static final ResourceBundle RB = ResourceBundle.getBundle(BASENAME);

    public static ResourceBundle getResourceBundle(Locale locale) {
        return ResourceBundle.getBundle(BASENAME, locale);
    }
}