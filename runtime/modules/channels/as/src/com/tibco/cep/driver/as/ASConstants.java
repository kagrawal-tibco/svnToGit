package com.tibco.cep.driver.as;

import com.tibco.as.space.security.AuthenticationInfo.Method;
import com.tibco.xml.data.primitive.ExpandedName;

public interface ASConstants {

     final static ExpandedName  XML_NODE_CONFIG                     = ExpandedName.makeName("config");
     final static ExpandedName  CHANNEL_PROPERTY_METASPACENAME      = ExpandedName.makeName("MetaspaceName");
     final static ExpandedName  CHANNEL_PROPERTY_MEMBERNAME         = ExpandedName.makeName("MemberName");
     final static ExpandedName  CHANNEL_PROPERTY_DISCOVERYURL       = ExpandedName.makeName("DiscoveryUrl");
     final static ExpandedName  CHANNEL_PROPERTY_LISTENURL          = ExpandedName.makeName("ListenUrl");
     final static ExpandedName  CHANNEL_PROPERTY_REMOTELISTENURL    = ExpandedName.makeName("RemoteListenUrl");

     final static ExpandedName  CHANNEL_PROPERTY_ENABLE_SECURITY    = ExpandedName.makeName("EnableSecurity");
     final static ExpandedName  CHANNEL_PROPERTY_SECURITY_ROLE      = ExpandedName.makeName("SecurityRole");
     final static ExpandedName  CHANNEL_PROPERTY_POLICY_FILE        = ExpandedName.makeName("PolicyFile");
     final static ExpandedName  CHANNEL_PROPERTY_TOKEN_FILE         = ExpandedName.makeName("TokenFile");
     final static ExpandedName  CHANNEL_PROPERTY_ID_PASSWORD        = ExpandedName.makeName("IdentityPassword");
     final static ExpandedName  CHANNEL_PROPERTY_AUTH_CREDENTIAL    = ExpandedName.makeName("Credential");
     final static ExpandedName  CHANNEL_PROPERTY_AUTH_DOMAIN        = ExpandedName.makeName("Domain");
     final static ExpandedName  CHANNEL_PROPERTY_AUTH_USERNAME      = ExpandedName.makeName("UserName");
     final static ExpandedName  CHANNEL_PROPERTY_AUTH_PASSWORD      = ExpandedName.makeName("Password");
     final static ExpandedName  CHANNEL_PROPERTY_AUTH_KEY_FILE      = ExpandedName.makeName("KeyFile");
     final static ExpandedName  CHANNEL_PROPERTY_AUTH_PRIVATE_KEY   = ExpandedName.makeName("PrivateKey");

     final static String        AS_SECURITY_ROLE_CONTROLLER         = "Controller";
     final static String        AS_SECURITY_ROLE_REQUESTOR          = "Requestor"; // backward compatibility
     final static String        AS_SECURITY_ROLE_REQUESTER          = "Requester";
     final static String        AS_SECURITY_AUTH_CREDENTIAL_USERPWD = Method.USERPWD.name();
     final static String        AS_SECURITY_AUTH_CREDENTIAL_X509V3  = Method.X509V3.name();

     final static String        K_AS_DEST_PROP_NAME                 = "Name";
     final static String        K_AS_DEST_PROP_DEFAULT_EVENT        = "DefaultEvent";
     final static String        K_AS_DEST_PROP_SERIALIZER           = "Serializer";
     final static String        K_AS_DEST_PROP_SPACE_NAME           = "SpaceName";
     final static String        K_AS_DEST_PROP_DISTRIBUTION_ROLE    = "DistributionRole";
     final static String        K_AS_DEST_PROP_DISTRIBUTION_SCOPE   = "DistributionScope";
     final static String        K_AS_DEST_PROP_FILTER               = "Filter";
     final static String        K_AS_DEST_PROP_CONSUMPTION_MODE     = "ConsumptionMode";
     final static String        K_AS_DEST_PROP_BROWSER_TYPE         = "BrowserType";
     final static String        K_AS_DEST_PROP_TIME_SCOPE           = "TimeScope";
     final static String        K_AS_DEST_PROP_PREFETCH             = "Prefetch";
     final static String        K_AS_DEST_PROP_QUERYLIMIT           = "QueryLimit";  //TODO: Expose this in Studio
     final static String        K_V_AS_DEST_PROP_PUT_EVENT          = "PutEvent";
     final static String        K_V_AS_DEST_PROP_TAKE_EVENT         = "TakeEvent";
     final static String        K_V_AS_DEST_PROP_EXPIRE_EVENT       = "ExpireEvent";
     final static String        V_AS_DEST_PROP_PREFETCH             = "-1";
     final static String        V_AS_DEST_PROP_QUERYLIMIT           = "-1"; //TODO: Expose this in Studio

     final static String        K_BE_EVENT_PROP_CONSUMPTION_MODE    = "consumption_mode";
     final static String        K_BE_EVENT_PROP_EVENT_TYPE          = "event_type";
     final static String        K_BE_EVENT_PROP_BROWSER_TYPE        = "browser_type";

     final static String        K_AS_DEST_CONSUMPTION_MODE_CONDITION_DESTINATION_CONFIG = "CM_Condition_DestinationConfig";
     final static String        K_AS_DEST_CONSUMPTION_MODE_CONDITION_SESSION            = "CM_Condition_Session";

     final static String        K_AS_DEST_START_MODE = "mode";

     enum ConsumptionMode {
         EventListener, EntryBrowser, Router
     }

}
