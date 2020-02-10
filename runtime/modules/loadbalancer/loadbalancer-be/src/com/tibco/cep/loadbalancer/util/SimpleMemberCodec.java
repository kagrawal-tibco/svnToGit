package com.tibco.cep.loadbalancer.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Properties;

import com.tibco.cep.common.resource.Id;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.impl.common.resource.DefaultId;
import com.tibco.cep.loadbalancer.client.core.ActualMember;
import com.tibco.cep.loadbalancer.client.core.ActualSink;
import com.tibco.cep.loadbalancer.impl.membership.MembershipInfo;
import com.tibco.cep.loadbalancer.impl.message.DefaultDistributionKey;
import com.tibco.cep.loadbalancer.impl.message.DefaultDistributionStrategy;
import com.tibco.cep.loadbalancer.impl.server.core.DefaultMember;
import com.tibco.cep.loadbalancer.impl.transport.TransportConfig;
import com.tibco.cep.loadbalancer.impl.transport.TransportConstants;
import com.tibco.cep.loadbalancer.message.DistributionKey;
import com.tibco.cep.loadbalancer.message.DistributionStrategy;
import com.tibco.cep.loadbalancer.message.MessageCodec;
import com.tibco.cep.loadbalancer.server.core.Member;
import com.tibco.cep.loadbalancer.server.core.sink.Sink;
import com.tibco.cep.util.ReusableDirectAccessByteArrayOS;

/*
* Author: Ashwin Jayaprakash / Date: Apr 2, 2010 / Time: 3:09:18 PM
*/
public class SimpleMemberCodec {
    public static final String DDS_KEY_LIST = "(key-list)";

    public static final String DDS_KEY_SEED = "(key-seed)";

    private static String[] parse(String s) {
        String[] array = s.split(",");

        for (int i = 0; i < array.length; i++) {
            array[i] = array[i].trim();
        }

        return array;
    }

    private static DefaultId toSimpleId(String s) {
        String[] a = parse(s);

        if (a.length == 2) {
            return new DefaultId(a[0], a[1]);
        }

        return new DefaultId(a[0]);
    }

    private static DistributionKey toDefaultDistributionKey(String s) {
        String[] a = parse(s);

        return new DefaultDistributionKey<String>(a[0]);
    }

    public static void write(ActualMember actualMember, ResourceProvider resourceProvider, OutputStream outputStream)
            throws IOException {
        ReusableDirectAccessByteArrayOS baos = new ReusableDirectAccessByteArrayOS();
        PrintWriter printer = new PrintWriter(baos);

        //------------

        Id id = actualMember.getId();
        printer.println(id);

        Collection<? extends ActualSink> sinks = actualMember.getEndpoints();
        printer.println(sinks.size());

        for (ActualSink actualSink : sinks) {
            Id sinkId = actualSink.getId();
            printer.println(sinkId);

            Id sourceId = actualSink.getSourceId();
            printer.println(sourceId);

            Properties properties = actualSink.getProperties();
            if (properties == null) {
                printer.println(0);
            }
            else {
                printer.println(properties.size());

                for (Entry<Object, Object> entry : properties.entrySet()) {
                    printer.println(entry.getKey() + "=" + entry.getValue());
                }
            }

            //------------

            DistributionStrategy distributionStrategy = actualSink.getDistributionStrategy();

            writeDistributionStrategyOfSink(printer, distributionStrategy);
        }

        //------------

        printer.flush();

        byte[] bytes = baos.toByteArray();
        int count = baos.size();
        String signature = Helper.$bytesToHashMD5(bytes, 0, count);

        printer.println(signature);

        //------------

        printer.close();

        bytes = baos.toByteArray();
        count = baos.size();
        outputStream.write(bytes, 0, count);
    }

    private static void writeDistributionStrategyOfSink(PrintWriter printer,
                                                        DistributionStrategy distributionStrategy) {
        DistributionKey[] keys = distributionStrategy.getBootstrapKeys();
        printer.println(keys.length);

        if (distributionStrategy instanceof DefaultDistributionStrategy) {
            DefaultDistributionStrategy dds = (DefaultDistributionStrategy) distributionStrategy;

            printer.println(DDS_KEY_SEED);

            printer.println(dds.getSeed());
        }
        else {
            printer.println(DDS_KEY_LIST);

            for (DistributionKey distributionKey : keys) {
                printer.println(distributionKey);
            }
        }
    }

    public static ServerSideMembershipInfo read(InputStream inputStream, ResourceProvider resourceProvider)
            throws IOException, IllegalAccessException, InstantiationException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String s = reader.readLine();
        DefaultId id = toSimpleId(s);

        DefaultMember member = new DefaultMember(id);
        member.setResourceProvider(resourceProvider);

        ClassLoader classLoader = Utils.$findRspClassLoader();

        //----------------

        s = reader.readLine();
        int numSinks = Integer.parseInt(s);
        for (int i = 0; i < numSinks; i++) {
            s = reader.readLine();
            Id sinkId = toSimpleId(s);

            s = reader.readLine();
            Id sourceId = toSimpleId(s);

            String transport = null;

            s = reader.readLine();
            int numProperties = Integer.parseInt(s);
            Properties properties = new Properties();
            for (int j = 0; j < numProperties; j++) {
                s = reader.readLine();

                String[] kv = s.split("=");
                String key = kv[0];
                String value = (kv.length == 2) ? kv[1] : null;

                if (key.equals(TransportConstants.NAME_TRANSPORT)) {
                    transport = value;
                }
                else {
                    properties.put(key, value);
                }
            }

            //----------------

            TransportConfig chosenTransport = TransportConfig.valueOf(transport);
            Sink sink = chosenTransport.getSinkClass().newInstance();

            sink.setId(sinkId);
            sink.setSourceId(sourceId);

            sink.setResourceProvider(resourceProvider);
            sink.setProperties(properties);

            MessageCodec messageCodec = chosenTransport.getMessageCodecClass().newInstance();
            messageCodec.setClassLoader(classLoader);
            sink.setCodec(messageCodec);

            //----------------

            readDistributionStrategyForSink(reader, sink);

            //----------------

            member.addSink(sink);
        }

        //----------------

        s = reader.readLine();

        return new ServerSideMembershipInfo(member, s, System.currentTimeMillis());
    }

    private static void readDistributionStrategyForSink(BufferedReader reader, Sink sink) throws IOException {
        String s = reader.readLine();
        int numBootstrapKeys = Integer.parseInt(s);

        DefaultDistributionStrategy dds = null;

        s = reader.readLine();
        if (s.equalsIgnoreCase(DDS_KEY_LIST)) {
            Collection<DistributionKey> bootstrapKeys = new LinkedList<DistributionKey>();

            for (int k = 0; k < numBootstrapKeys; k++) {
                s = reader.readLine();

                DistributionKey distributionKey = toDefaultDistributionKey(s);
                bootstrapKeys.add(distributionKey);
            }

            DistributionKey[] bootstrapKeyArray = bootstrapKeys.toArray(new DistributionKey[bootstrapKeys.size()]);

            dds = new DefaultDistributionStrategy(bootstrapKeyArray);
        }
        else if (s.equalsIgnoreCase(DDS_KEY_SEED)) {
            s = reader.readLine();

            dds = new DefaultDistributionStrategy(s, numBootstrapKeys);
        }

        sink.setDistributionStrategy(dds);
    }

    //----------------

    public static class ServerSideMembershipInfo implements MembershipInfo<Sink, Member> {
        protected Member member;

        protected String version;

        protected long timestamp;

        public ServerSideMembershipInfo(Member member, String version, long timestamp) {
            this.member = member;
            this.version = version;
            this.timestamp = timestamp;
        }

        @Override
        public Id getContainerId() {
            return member.getId();
        }

        @Override
        public String getVersion() {
            return version;
        }

        @Override
        public long getTimestamp() {
            return timestamp;
        }

        @Override
        public Member createContainer(ResourceProvider resourceProvider) {
            return member;
        }
    }
}
