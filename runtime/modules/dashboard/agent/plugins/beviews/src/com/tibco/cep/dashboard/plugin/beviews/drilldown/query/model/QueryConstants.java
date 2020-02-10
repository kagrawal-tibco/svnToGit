package com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model;

/**
 * @author apatil
 *
 */
public interface QueryConstants {

    public static final int USER_NAMESPACE = 1;

    public static final int ROLE_NAMESPACE = 2;

    public static final int GLOBAL_NAMESPACE = -999;

    public static final int[] ALL_NAMESPACES = new int[] {
        QueryConstants.USER_NAMESPACE,
        QueryConstants.ROLE_NAMESPACE,
        QueryConstants.GLOBAL_NAMESPACE
    };

    static final String USER_NAMESPACE_TEXT = "user namespace";

    static final String ROLE_NAMESPACE_TEXT = "role namespace";

    static final String GLOBAL_NAMESPACE_TEXT = "global namespace";

    static final String[] ALL_NAMESPACES_TEXT = new String[] {
        QueryConstants.USER_NAMESPACE_TEXT,
        QueryConstants.ROLE_NAMESPACE_TEXT,
        QueryConstants.GLOBAL_NAMESPACE_TEXT
    };

    static final String TEMP_QUERY_NAME_PREFIX = "temp";
}
