package com.tibco.rta.service.rs.test;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 18/2/14
 * Time: 2:47 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractRestServiceTest {

    protected static final String ROOT_URL = "http://localhost:9322/spm";

    protected static Client client;

    @BeforeClass
    public static void setUp() {
        client = ClientBuilder.newClient();
    }

    @AfterClass
    public static void close() {
        client.close();
    }
}
