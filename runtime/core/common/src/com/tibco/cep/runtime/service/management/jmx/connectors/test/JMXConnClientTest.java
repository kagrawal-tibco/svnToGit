package com.tibco.cep.runtime.service.management.jmx.connectors.test;

import com.tibco.cep.runtime.management.Domain;
import com.tibco.cep.runtime.management.DomainKey;
import com.tibco.cep.runtime.service.management.jmx.connectors.JMXConnClient;
import com.tibco.cep.runtime.service.management.jmx.connectors.JMXConnUtil;
import com.tibco.cep.runtime.util.FQName;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: 1/27/11
 * Time: 8:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class JMXConnClientTest {

    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }


    //Connector client tester.
//    private static final String OBJ_NAME= "com.tibco.be:dir=Methods,Group=Channels";
    private static final String OBJ_NAME= "com.tibco.be:dir=Methods,Group=Management Table";

    //    private final int JMX_PORT = JMXConnUtil.getConnPort(null); //TODO: This test module just got broken with this null
    private static final int JMX_PORT = 4558;

    public static void main(String[] args) {
        try {
            JMXConnClient jmxConnClient =
                    new JMXConnClient(JMXConnUtil.getConnIP(), JMX_PORT, "admin", "admin",true);

            MBeanServerConnection mbsc = jmxConnClient.getMBeanServerConnection();

//            Object domains = mbsc.invoke( new ObjectName(OBJ_NAME),
            Collection<Domain> domains = (Collection<Domain>) mbsc.invoke( new ObjectName(OBJ_NAME),
                    "getDomains",
                    new Object[]{},
                    new String[]{});

            for (Domain domain : domains) {
                FQName domainName = domain.safeGet(DomainKey.FQ_NAME);
                System.err.println("domainName = " + Arrays.toString(domainName.getComponentNames()));
                
                Object o = domain.safeGet(DomainKey.NAME).toString();
                System.err.println(domain.safeGet(DomainKey.NAME).toString());
                System.err.println(domain.safeGet(DomainKey.FQ_NAME).toString());
                System.err.println(domain.safeGet(DomainKey.HOST_IP_ADDRESS).toString());
                System.err.println(domain.safeGet(DomainKey.HOST_PROCESS_ID).toString());
                System.err.println(domain.safeGet(DomainKey.JMX_PROPS_CSV).toString());
                System.err.println(domain.safeGet(DomainKey.HAWK_PROPS_CSV).toString());
            }

            Object obj1 = mbsc.invoke(new ObjectName(OBJ_NAME),
                    "GetChannels",
                    new Object[]{null},
                    new String[]{String.class.getName()});
            readInput();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void readInput(){
        try {
            System.err.println("Type Enter to finish");
            InputStreamReader ir = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(ir);
            br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
