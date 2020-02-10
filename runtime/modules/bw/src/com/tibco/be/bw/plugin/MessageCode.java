package com.tibco.be.bw.plugin;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Aug 14, 2004
 * Time: 8:54:41 AM
 * To change this template use File | Settings | File Templates.
 */
/**
errorRole.BW_Plugin.BW-BE-100000=Event Reference Is Required
errorRole.BW_Plugin.BW-BE-100001=Concept Reference Is Required
errorRole.BW_Plugin.BW-BE-100002=MutableChannel For Event Not Specified
errorRole.BW_Plugin.BW-BE-100003=Destination For Event Not Specified
**/

public class MessageCode
{
    public static final String BEBW_EVENT_RESOURCE_REQUIRED                     = "BW-BE-100000";
    public static final String BEBW_CONCEPT_RESOURCE_REQUIRED                   = "BW-BE-100001";
    public static final String BEBW_CHANNELFOREVENT_NOT_SPECIFIED               = "BW-BE-100002";
    public static final String BEBW_DESTINATIONFOREVENT_NOT_SPECIFIED           = "BW-BE-100003";
    public static final String BEBW_UNKNOWN_DESIGN_ERROR                        = "BW-BE-100004";    
}
