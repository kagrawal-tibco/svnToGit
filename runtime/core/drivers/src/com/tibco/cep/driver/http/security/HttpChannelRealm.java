package com.tibco.cep.driver.http.security;

import java.security.Principal;
import java.util.HashMap;

import org.apache.catalina.Realm;
import org.apache.catalina.realm.RealmBase;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Mar 27, 2008
 * Time: 2:31:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class HttpChannelRealm extends RealmBase implements Realm {
    protected String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    protected String getPassword(String username) {
        throw new IllegalArgumentException("TomcatRepoRealm doesn't support this method call");
    }

    protected Principal getPrincipal(String username) {
        throw new IllegalArgumentException("TomcatRepoRealm doesn't support this method call");
    }

//    public synchronized void start() throws LifecycleException {
//        super.start();
//        System.out.println("HttpChannelRealm initialized.");
//    }

    public Principal authenticate(String username, String credentials) {

        //System.out.println("Trying to authenticate the user: " + username + " - with the credentials: " + credentials);
        Principal principal = null;
        if (username != null && !username.equals("") && credentials != null && !credentials.equals("")) {
            principal = null;//new GenericPrincipal(this, username);
            throw new RuntimeException("Not implemented in TomcatRepoRealm");
        }
        return principal;
    }

    public Principal authenticate(String username, String clientDigest,
                                  String nOnce, String nc, String cnonce,
                                  String qop, String realm,
                                  String md5a2) {
        //System.out.println("Trying to authenticate the user: " + username + " - with: " + clientDigest + " - " + nOnce + " - " +
        //    nc + " - " + cnonce + " - " + qop + " - " + realm + " - " + md5a2);
        Principal principal = null;
        if (username != null) {
            principal = null;//new GenericPrincipal(this, username, "password");
            throw new RuntimeException("Not implemented in TomcatRepoRealm");
        }
        return principal;
    }

    protected final String info = "com.tibco.be.rms.driver.http.security.HttpChannelRealm/1.0";

    protected static final String name = "HttpChannelRealm";

    private HashMap principals = new HashMap();

    private boolean started = false;
}