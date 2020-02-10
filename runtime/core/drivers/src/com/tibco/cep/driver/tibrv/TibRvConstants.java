package com.tibco.cep.driver.tibrv;

import com.tibco.xml.data.primitive.ExpandedName;

/**
 * Created by IntelliJ IDEA.
 * User: hzhang
 * Date: Jul 27, 2006
 * Time: 6:45:11 AM
 * To change this template use File | Settings | File Templates.
 */
public interface TibRvConstants {
    final static String NAME = "RV";

    final static ExpandedName NODE_NAME_ROOT = ExpandedName.makeName("BWSharedResource");
    final static ExpandedName NODE_NAME_CONFIG = ExpandedName.makeName("config");

    final static ExpandedName CHANNEL_PROPERTY_SERVICE = ExpandedName.makeName("Service");
    final static ExpandedName CHANNEL_PROPERTY_NETWORK = ExpandedName.makeName("Network");
    final static ExpandedName CHANNEL_PROPERTY_DAEMON  = ExpandedName.makeName("Daemon");

    final static ExpandedName SHARED_CONFIG_CHANNEL_PROPERTY_SERVICE = ExpandedName.makeName("service");
    final static ExpandedName SHARED_CONFIG_CHANNEL_PROPERTY_NETWORK = ExpandedName.makeName("network");
    final static ExpandedName SHARED_CONFIG_CHANNEL_PROPERTY_DAEMON  = ExpandedName.makeName("daemon");

    final static ExpandedName CHANNEL_PROPERTY_DESTINATIONS = ExpandedName.makeName("destinations");
    final static ExpandedName CHANNEL_PROPERTY_DESTINATION = ExpandedName.makeName("destination");

    final static ExpandedName DESTINATION_PROPERTY_SUBJECT  = ExpandedName.makeName("Subject");
    final static ExpandedName DESTINATION_PROPERTY_ISINPUT  = ExpandedName.makeName("isInput");
    final static ExpandedName DESTINATION_PROPERTY_ISOUTPUT = ExpandedName.makeName("isOutput");
    final static ExpandedName DESTINATION_PROPERTY_EVENT    = ExpandedName.makeName("event");
    final static ExpandedName DESTINATION_PROPERTY_URI      = ExpandedName.makeName("uri");
    final static ExpandedName DESTINATION_PROPERTY_NAME     = ExpandedName.makeName("name");
    final static ExpandedName DESTINATION_PROPERTY_LIMITPOLICY   = ExpandedName.makeName("LimitPolicy");
    final static ExpandedName DESTINATION_PROPERTY_MAXEVENTS     = ExpandedName.makeName("MaxEvents");
    final static ExpandedName DESTINATION_PROPERTY_DISCARDAMOUNT = ExpandedName.makeName("DiscardAmount");
    final static ExpandedName DESTINATION_SERIALIZERDESERIALIZER_NAME = ExpandedName.makeName("serializer");
    final static ExpandedName DESTINATION_PROPERTY_PREREGISTRATION_NAME = ExpandedName.makeName("RVCM Pre Registration");
    final static ExpandedName DESTINATION_PROPERTY_EVENT_TYPE_NAME_PROCESSING = ExpandedName.makeName("EventTypeNameProcessing");

    final static ExpandedName SHARED_CONFIG_CHANNEL_WORKERWEIGHT        = ExpandedName.makeName("workerWeight");
    final static ExpandedName SHARED_CONFIG_CHANNEL_WORKERTASKS         = ExpandedName.makeName("workerTasks");
    final static ExpandedName SHARED_CONFIG_CHANNEL_WORKERCOMPLETETIME  = ExpandedName.makeName("workerCompleteTime");
    final static ExpandedName SHARED_CONFIG_CHANNEL_SCHEDULEWEIGHT      = ExpandedName.makeName("schedulerWeight");
    final static ExpandedName SHARED_CONFIG_CHANNEL_SCHEDULEHEARTBEAT   = ExpandedName.makeName("scheduleHeartbeat");
    final static ExpandedName SHARED_CONFIG_CHANNEL_SCHEDULEACTIVATION  = ExpandedName.makeName("scheduleActivation");
    final static ExpandedName SHARED_CONFIG_CHANNEL_SHOWEXPERTSETTINGS  = ExpandedName.makeName("showExpertSettings");
    final static ExpandedName SHARED_CONFIG_CHANNEL_CMNAME              = ExpandedName.makeName("cmName");
    final static ExpandedName SHARED_CONFIG_CHANNEL_SYNCLEDGER          = ExpandedName.makeName("syncLedger");
    final static ExpandedName SHARED_CONFIG_CHANNEL_LEDGERFILE          = ExpandedName.makeName("ledgerFile");
    final static ExpandedName SHARED_CONFIG_CHANNEL_CMQNAME             = ExpandedName.makeName("cmqName");
    final static ExpandedName SHARED_CONFIG_CHANNEL_PROPERTY_USE_SSL    = ExpandedName.makeName("useSsl");

    final static String AEMETA_SERVICES_2002_NS = "http://www.tibco.com/xmlns/aemeta/services/2002";

    final static ExpandedName SHARED_CONFIG_CHANNEL_PROPERTY_SSL        = ExpandedName.makeName(AEMETA_SERVICES_2002_NS, "ssl");
    final static ExpandedName SHARED_CONFIG_CHANNEL_PROPERTY_CERT       = ExpandedName.makeName(AEMETA_SERVICES_2002_NS, "cert");
    final static ExpandedName SHARED_CONFIG_CHANNEL_PROPERTY_IDENTITY   = ExpandedName.makeName(AEMETA_SERVICES_2002_NS, "identity");

    final static ExpandedName CHANNEL_PROPERTY_PROPERTIES = ExpandedName.makeName("properties");



    public final static String MESSAGE_HEADER_NAMESPACE_PROPERTY="_ns_";
    public final static String MESSAGE_HEADER_NAME_PROPERTY="_nm_";
    public final static String MESSAGE_HEADER_EXTID_PROPERTY="_extId_";


    //Payload Serializer Constants
    /**
     * Assume the structure:
     * <root>
     *     <param0>
     *         value0
     *     </param0>
     *     <param1>
     *         value1
     *     </param1>
     * </root>
     */




        static public final String TIBRV_RAW_MESSAGE_SCHEMA_NS = "http://xmlns.tibco.com/2003/5/bw/plugins/tibrv";

        static public final ExpandedName RAWMSG_EXPANDED_NAME = ExpandedName.makeName(TIBRV_RAW_MESSAGE_SCHEMA_NS, "rawMsg");
        static public final ExpandedName FIELD_EXPANDED_NAME = ExpandedName.makeName(TIBRV_RAW_MESSAGE_SCHEMA_NS, "field");

        static public final ExpandedName ID_EXPANDED_NAME = ExpandedName.makeName(TIBRV_RAW_MESSAGE_SCHEMA_NS, "id");
        static public final ExpandedName NAME_EXPANDED_NAME = ExpandedName.makeName(TIBRV_RAW_MESSAGE_SCHEMA_NS, "name");
        //static public final ExpandedName DATA_EXPANDED_NAME = ExpandedName.makeName(TIBRV_RAW_MESSAGE_SCHEMA_NS, "data");

        static public final ExpandedName SIMPLE_EXPANDED_NAME = ExpandedName.makeName(TIBRV_RAW_MESSAGE_SCHEMA_NS, "simple");
        static public final ExpandedName MSG_EXPANDED_NAME = ExpandedName.makeName(TIBRV_RAW_MESSAGE_SCHEMA_NS, "msg");

        static public final ExpandedName IPPORT_EXPANDED_NAME = ExpandedName.makeName(TIBRV_RAW_MESSAGE_SCHEMA_NS, "ipPort");
        static public final ExpandedName IPADDR_EXPANDED_NAME = ExpandedName.makeName(TIBRV_RAW_MESSAGE_SCHEMA_NS, "ipAddr");

        static public final ExpandedName BODY_EXPANDED_NAME = ExpandedName.makeName("body");

        static public final int MODE_SCHEMA_BASED = 0;
        static public final int MODE_SPECIAL_SCHEMA_BASED = 1;
        static public final int MODE_NONSCHEMA_BASED = 2;
        static public final String DESTINATION_PROPERTY_OVERRIDESUBJECT = "subject";


}
