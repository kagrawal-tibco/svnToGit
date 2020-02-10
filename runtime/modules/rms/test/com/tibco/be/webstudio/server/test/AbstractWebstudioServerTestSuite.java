package com.tibco.be.webstudio.server.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 28/3/12
 * Time: 11:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class AbstractWebstudioServerTestSuite {

    protected static final String adminCookieFilePath = "/media/Windows7/dev/be/5.1/runtime/modules/rms/test/com/tibco/be/webstudio/server/test/authn/Admin-Cookie.txt";

    protected static final String businessUserCookieFilePath = "/media/Windows7/dev/be/5.1/runtime/modules/rms/test/com/tibco/be/webstudio/server/test/authn/Jdoe-Cookie.txt";

    protected static final String WS_HOST = System.getProperty("ws.host", "localhost");

    protected static final int WS_PORT = Integer.parseInt(System.getProperty("ws.port", "8090"));

    protected static final String WS_SCHEME = System.getProperty("ws.scheme", "http");
    
    protected static String WS_BASE_URL;

    @BeforeClass
    public static void setUpClass() throws Exception {

    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        WS_BASE_URL =
            new StringBuilder(WS_SCHEME).append("://").append(WS_HOST).append(":").append(WS_PORT).toString();
    }

    @After
    public void tearDown() {
    }
}
