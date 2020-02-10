package com.tibco.cep.security.dataprovider.impl;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: Mar 18, 2009
 * Time: 12:16:09 PM
 * <!--
 * Add Description of the class here
 * -->
 */
public class DataProviderConstants {

	public static final String MD5_ALGORITHM = "MD5";

	//File Auth Constants
	public static final String BE_AUTH_FILE_LOCATION = "be.auth.file.location";

	//LDAP auth constants
    public static final String PROP_LDAP_HOST = "be.auth.ldap.host";
    public static final String PROP_LDAP_PORT = "be.auth.ldap.port";
    public static final String PROP_LDAP_SSL = "be.auth.ldap.ssl";
    public static final String PROP_LDAP_ADMINDN = "be.auth.ldap.adminDN";
    public static final String PROP_LDAP_ADMINPASSWD = "be.auth.ldap.adminPassword";
    public static final String PROP_LDAP_BASEDN = "be.auth.ldap.baseDN";
    public static final String PROP_LDAP_UIDATTR = "be.auth.ldap.uidattr";
    public static final String PROP_LDAP_ROLEATTR = "be.auth.ldap.roleAttr";
    //How distinguishd name is represented in the DS
    public static final String PROP_LDAP_DN_ATTR = "be.auth.ldap.dnAttr";
    public static final String PROP_LDAP_OBJECTCLASS_ATTR = "be.auth.ldap.objectClass";
    //Property to indicate whether role dn should be retrieved or only common name.
    public static final String PROP_USE_ROLE_DN = "be.auth.ldap.useRoleDN";
	public static final String ATTR_USER_PRINCIPAL_NAME = "userPrincipalName";
    public static final String ATTR_COMMON_NAME = "cn";
    public static final String ATTR_DISTINGUISHED_NAME = "distinguishedName";
    //Email notify properties constants
	public static final String BE_USER_NOTIFY_FILE_LOCATION = "ws.notify.file.location";
    public static final String PROP_LDAP_USER_NOTIFY_ID_ATTR = "ws.notify.ldap.userNotifyIdAttr";
    //SSL properties
    public static final String JAVAX_NET_SSL_TRUSTSTOREPASSWORD = "javax.net.ssl.trustStorePassword";}