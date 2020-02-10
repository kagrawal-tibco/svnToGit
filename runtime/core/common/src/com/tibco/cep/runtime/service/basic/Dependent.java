package com.tibco.cep.runtime.service.basic;

/*
* Author: Ashwin Jayaprakash Date: Feb 11, 2009 Time: 11:19:31 AM
*/
public interface Dependent {
    /**
     * {@value}.
     */
    String DEP_KEY_CLUSTER_INIT = "ClusterInit";

    String whoAmI();

    /**
     * The parameters that will be expected in the {@link #onNotify(Object[])} method. The objects
     * might <b>not be in the same order though</b>.
     *
     * @return
     */
    Class[] getNotificationParameterTypes();

    /**
     * Comments for the types returned by {@link #getNotificationParameterTypes()}.
     *
     * @return
     */
    Object[] getNotificationParameterDesc();

    void onNotify(Object... args);
}
