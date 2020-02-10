package com.tibco.be.bw.plugin;

import com.tibco.xml.data.primitive.ExpandedName;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Aug 15, 2004
 * Time: 1:34:44 PM
 * To change this template use File | Settings | File Templates.
 */
public interface BEPluginConstants {
    public static final String NAMESPACE 	                = "www.tibco.com/plugin/be";
    // Global Properties
    public static final ExpandedName ENTITYNS 	            = ExpandedName.makeName("entityNS");
    public static final ExpandedName ENTITYNAME	            = ExpandedName.makeName("entityName");
    public static final ExpandedName OUTPUT_STYLE           = ExpandedName.makeName("outputStyle");
    public static final ExpandedName ENCODING               = ExpandedName.makeName("textEncoding");
    public static final ExpandedName BYTE_ENCODING          = ExpandedName.makeName("byteEncoding");
    public static final ExpandedName IS_TEXT                = ExpandedName.makeName("isText");

    // All Concept References
    public static final ExpandedName CONCEPTREF 	        = ExpandedName.makeName("conceptRef");

    // All Event References
    public static final ExpandedName EVENTREF 	            = ExpandedName.makeName("eventRef");
    public static final ExpandedName EVENTURI 	            = ExpandedName.makeName("uri");

    public static final ExpandedName DESTINATIONREF         = ExpandedName.makeName("destinationRef");

    public static final ExpandedName FACTRESPONSE           = ExpandedName.makeName("reply");
    public static final ExpandedName FACTID 	            = ExpandedName.makeName("Id");
    public static final ExpandedName FACTEXTERNALID         = ExpandedName.makeName("extId");
    public static final ExpandedName RESPONSE_CODE          = ExpandedName.makeName("statusCode");
    public static final ExpandedName RESPONSE_MSG           = ExpandedName.makeName("statusMsg");

    public static final ExpandedName RSPREF                 = ExpandedName.makeName("rspRef");
    public static final ExpandedName RULESESSIONREF         = ExpandedName.makeName("ruleSessionRef");
    public static final ExpandedName RULEFUNCTIONREF         = ExpandedName.makeName("ruleFunctionRef");

    public static final String RSP_TYPE_NAME  = new String("ruleserviceprovider");
    public static final String RSP_ELEMENT_NAME  = new String("ruleserviceprovider");
    public static final String RSP_ELEMENT_ENGINE_NAME  = new String("engineName");
    public static final String RSP_ELEMENT_START_FLAG  = new String("startFlag");
    public static final String RSP_ELEMENT_REPOURL  = new String("repourl");
    public static final String RSP_ELEMENT_CDD  = new String("cdd");
    public static final String RSP_ELEMENT_PUID  = new String("puid");
    //public static final String RSP_ELEMENT_TRAPATH  = new String("trapath");

    public static final ExpandedName XNAME_REPOURL = ExpandedName.makeName(RSP_ELEMENT_REPOURL);
    public static final ExpandedName XNAME_CDD = ExpandedName.makeName(RSP_ELEMENT_CDD);
    public static final ExpandedName XNAME_PUID = ExpandedName.makeName(RSP_ELEMENT_PUID);

}
