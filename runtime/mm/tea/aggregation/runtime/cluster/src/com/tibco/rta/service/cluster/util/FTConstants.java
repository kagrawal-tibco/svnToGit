package com.tibco.rta.service.cluster.util;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 9/4/13
 * Time: 12:19 PM
 * FT/GMP constants.
 */
public class FTConstants {

    /**
     * Message type used for GMP.
     */
    public static final String GMP_JMS_MESSAGE_TYPE = "GMP_MESSAGE_TYPE";

    /**
     * Member ID header used for GMP.
     */
    public static final String GMP_JMS_MESSAGE_MEMBER_ID = "GMP_MEMBER_ID";

    /**
     * Member ID header used for GMP.
     */
    public static final String GMP_JMS_MESSAGE_CLUSTER_STATE = "GMP_CLUSTER_STATE";

    /**
     * ID header used to indicate whether leader or not. Used in heartbeats.
     */
    public static final String GMP_JMS_MESSAGE_MEMBER_STATE = "GMP_MEMBER_STATE";

    /**
     * Member ID header used for GMP.
     */
    public static final String GMP_JMS_MESSAGE_QUORUM_MEMBER_IDS = "GMP_QUORUM_MEMBER_IDS";
}
