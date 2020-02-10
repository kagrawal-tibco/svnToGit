package com.tibco.cep.driver.jms;

import com.tibco.xml.data.primitive.ExpandedName;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jul 7, 2006
 * Time: 7:42:31 AM
 * To change this template use File | Settings | File Templates.
 */
public interface JMSConstants {

    final static ExpandedName CHANNEL_PROPERTY_PROVIDER_URL = ExpandedName.makeName("ProviderURL");
    final static ExpandedName CHANNEL_PROPERTY_USER         = ExpandedName.makeName("UserName");
    final static ExpandedName CHANNEL_PROPERTY_PWD          = ExpandedName.makeName("Password");
    final static ExpandedName CHANNEL_PROPERTY_ISTRANSACTED = ExpandedName.makeName("IsTransacted");
    final static ExpandedName CHANNEL_PROPERTY_USEJNDI      = ExpandedName.makeName("UseJNDI");
    final static ExpandedName CHANNEL_PROPERTY_CLIENTID_PROP= ExpandedName.makeName("ClientID");
    final static ExpandedName CHANNEL_PROPERTY_CLIENTID_REF = ExpandedName.makeName("clientID");
    final static ExpandedName CHANNEL_PROPERTY_AUTO_GEN_CLIENTID_REF = ExpandedName.makeName("autoGenClientID");
    final static ExpandedName CHANNEL_PROPERTY_JNDIUSER     = ExpandedName.makeName("NamingPrincipal");
    final static ExpandedName CHANNEL_PROPERTY_JNDIPWD      = ExpandedName.makeName("NamingCredential");
    final static ExpandedName CHANNEL_PROPERTY_ADM_FACTORY_SSL_PASSWORD = ExpandedName.makeName("AdmFactorySslPassword");
    final static ExpandedName CHANNEL_PROPERTY_QCF          = ExpandedName.makeName("QueueFactoryName");
    final static ExpandedName CHANNEL_PROPERTY_TCF          = ExpandedName.makeName("TopicFactoryName");
    final static ExpandedName CHANNEL_PROPERTY_MAXSESSIONS  = ExpandedName.makeName("MaxSessions");
    final static ExpandedName CHANNEL_PROPERTY_INITIALCTXFACTORY  = ExpandedName.makeName("NamingInitialContextFactory");
    final static ExpandedName CHANNEL_PROPERTY_NAMING_ENVIRONMENT  = ExpandedName.makeName("NamingEnvironment");
    final static ExpandedName CHANNEL_PROPERTY_NAMING_URL = ExpandedName.makeName("NamingURL");
    final static ExpandedName CHANNEL_PROPERTY_CONNECTIONATTRIBUTES = ExpandedName.makeName("ConnectionAttributes");
    final static ExpandedName CHANNEL_PROPERTY_PROPERTIES = ExpandedName.makeName("properties");
    final static ExpandedName CHANNEL_PROPERTY_DESTINATIONS = ExpandedName.makeName("destinations");
    final static ExpandedName CHANNEL_PROPERTY_DESTINATION = ExpandedName.makeName("destination");

    public static final String PKG_PREFIXES_NAMING = "com.tibco.tibjms.naming";
    public static final String SECURITY_PROTOCOL = "com.tibco.tibjms.naming.security_protocol";
    public static final String SSL_TRACE = "com.tibco.tibjms.naming.ssl_trace";
    public static final String SSL_DEBUG_TRACE = "com.tibco.tibjms.naming.ssl_debug_trace";
    public static final String SSL_VENDOR = "com.tibco.tibjms.naming.ssl_vendor";
    public static final String SSL_ENABLE_VERIFY_HOSTNAME = "com.tibco.tibjms.naming.ssl_enable_verify_hostname";
    public static final String SSL_EXPECTED_HOSTNAME = "com.tibco.tibjms.naming.ssl_expected_hostname";
    public static final String SSL_TRUSTED_CERTIFICATES = "com.tibco.tibjms.naming.ssl_trusted_certs";
    public static final String SSL_IDENTITY = "com.tibco.tibjms.naming.ssl_identity";
    public static final String SSL_PRIVATE_KEY = "com.tibco.tibjms.naming.ssl_private_key";
    public static final String SSL_PASSWORD = "com.tibco.tibjms.naming.ssl_password";
    public static final String SSL_CIPHER_SUITES = "com.tibco.tibjms.naming.ssl_cipher_suites";

    public static final String TRACE = "com.tibco.tibjms.ssl.trace";
    public static final String DEBUG_TRACE = "com.tibco.tibjms.ssl.debug_trace";
    public static final String VENDOR = "com.tibco.tibjms.ssl.vendor";
    public static final String ENABLE_VERIFY_HOSTNAME = "com.tibco.tibjms.ssl.enable_verify_hostname";
    public static final String EXPECTED_HOSTNAME = "com.tibco.tibjms.ssl.expected_hostname";
    public static final String TRUSTED_CERTIFICATES = "com.tibco.tibjms.ssl.trusted_certs";
    public static final String IDENTITY = "com.tibco.tibjms.ssl.identity";
    public static final String PRIVATE_KEY = "com.tibco.tibjms.ssl.private_key";
    public static final String PASSWORD = "com.tibco.tibjms.ssl.password";
    public static final String CIPHER_SUITES = "com.tibco.tibjms.ssl.cipher_suites";

    public static final String SSL = "ssl";
    //    public static final String ENTRUST = "entrust6";
    public static final String J2SE = "j2se";
    public static final String J2SE_STRONG_CIPHER_SUITES = "RC4-SHA:RC4-MD5:DES-CBC3-SHA";
    public static final String ENTRUST_STRONG_CIPHER_SUITES = "RC4-SHA:RC4-MD5:DES-CBC3-SHA:EDH-RSA-DES-CBC3-SHA:EDH-RSA-DES-CBC3-SHA";

    public final static String MESSAGE_HEADER_NAMESPACE_PROPERTY="_ns_";
    public final static String MESSAGE_HEADER_NAME_PROPERTY="_nm_";
    public final static String MESSAGE_HEADER_EXTID_PROPERTY="_extId_";


}
