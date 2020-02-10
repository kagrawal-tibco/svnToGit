package com.tibco.cep.bemm.security.dataprovider.impl;

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
	public static final String BEMM_AUTH_FILE_LOCATION = "be.mm.auth.file.location";

	//LDAP auth constants
    public static final String PROP_BEMM_LDAP_HOST = "be.mm.auth.ldap.host";
    public static final String PROP_BEMM_LDAP_PORT = "be.mm.auth.ldap.port";
	public static final String PROP_BEMM_LDAP_SSL = "be.mm.auth.ldap.ssl";
    public static final String PROP_BEMM_LDAP_ADMINDN = "be.mm.auth.ldap.adminDN";
    public static final String PROP_BEMM_LDAP_ADMINPASSWD = "be.mm.auth.ldap.adminPassword";
    public static final String PROP_BEMM_LDAP_BASEDN = "be.mm.auth.ldap.baseDN";
    public static final String PROP_BEMM_LDAP_UIDATTR = "be.mm.auth.ldap.uidattr";
    public static final String PROP_BEMM_LDAP_ROLEATTR = "be.mm.auth.ldap.roleAttr";
    //How distinguishd name is represented in the DS
    public static final String PROP_BEMM_LDAP_DN_ATTR = "be.mm.auth.ldap.dnAttr";
    public static final String PROP_BEMM_LDAP_OBJECTCLASS_ATTR = "be.mm.auth.ldap.objectClass";
    //Property to indicate whether role dn should be retrieved or only common name.
    public static final String PROP_BEMM_USE_ROLE_DN = "be.mm.auth.ldap.useRoleDN";
    public static final String ATTR_USER_PRINCIPAL_NAME = "userPrincipalName";
    public static final String ATTR_COMMON_NAME = "cn";
    public static final String ATTR_DISTINGUISHED_NAME = "distinguishedName";
    //SSL properties
    public static final String JAVAX_NET_SSL_TRUSTSTOREPASSWORD = "javax.net.ssl.trustStorePassword";
}