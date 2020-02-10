package com.tibco.cep.decision.table.utils;

import java.util.Locale;

/**
 * Constants used in decision grid product.
 */
public interface DecisionConstants {
    /**
     * A constant for the decision field area for row fields. Used by {@link com.jidesoft.decision.DecisionField#setAreaType(int)}.
     */
    public static final int AREA_CONDITION = 0;

    /**
     * A constant for the decision field area for data fields. Used by {@link com.jidesoft.decision.DecisionField#setAreaType(int)}.
     */
    public static final int AREA_ACTION = 1;

    /**
     * A constant for the decision field area for not assigned fields. Those fields will appear in the field chooser
     * panel only Used by {@link com.jidesoft.decision.DecisionField#setAreaType(int)}.
     */
    public static final int AREA_NOT_ASSIGNED = -1;

    /**
     * All the field area names. The names are localized using Locale.getDefault() as the locale. If you want a
     * different locale, using {@link com.jidesoft.decision.DecisionTablePane#getAreaName(java.util.Locale,int)}
     * instead.
     */
    public static final String[] AREA_NAMES = new String[]{
            DecisionResources.getResourceBundle(Locale.getDefault()).getString("AreaType.condition"),
            DecisionResources.getResourceBundle(Locale.getDefault()).getString("AreaType.action")
    };

    /**
     *
     */
    public static final String VALUE_NULL = "<null>";
}