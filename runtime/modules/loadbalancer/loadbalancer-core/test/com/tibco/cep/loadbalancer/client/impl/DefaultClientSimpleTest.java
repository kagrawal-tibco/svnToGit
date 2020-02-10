package com.tibco.cep.loadbalancer.client.impl;

import com.tibco.cep.common.log.LoggerService;
import com.tibco.cep.impl.common.log.DefaultLoggerService;
import com.tibco.cep.impl.common.resource.DefaultResourceProvider;
import com.tibco.cep.loadbalancer.impl.client.DefaultClient;
import com.tibco.cep.loadbalancer.impl.client.core.DefaultActualMember;
import com.tibco.cep.loadbalancer.impl.client.membership.file.FileBasedMembershipPublisher;
import com.tibco.cep.loadbalancer.impl.client.transport.mem.ActualMemSink;
import com.tibco.cep.loadbalancer.impl.message.DefaultDistributionStrategy;
import com.tibco.cep.loadbalancer.message.DistributionKey;

import java.io.File;

import static com.tibco.cep.loadbalancer.impl.message.DistributionKeyMaker.$asKeys;
import static com.tibco.cep.util.Helper.$id;

/*
* Author: Ashwin Jayaprakash / Date: Mar 23, 2010 / Time: 3:36:16 PM
*/
public class DefaultClientSimpleTest {
    public void test() throws Exception {
        DefaultActualMember member1 = new DefaultActualMember($id("member-1"));
        String memSink1AName = "memsink-1a";

        //--------------

        ActualMemSink memSink1A = new ActualMemSink($id(memSink1AName));
        memSink1A.setSourceId($id("memsource-a"));
        DistributionKey[] memSink1ABootKeys =
                $asKeys(memSink1AName, memSink1AName + "-2", memSink1AName + "-3", memSink1AName + "-4");
        memSink1A.setDistributionStrategy(new DefaultDistributionStrategy(memSink1ABootKeys));
        member1.addSink(memSink1A);

        //--------------

        DefaultResourceProvider resourceProvider = new DefaultResourceProvider();
        LoggerService loggerService = new DefaultLoggerService();
        loggerService.start();
        resourceProvider.registerResource(LoggerService.class, loggerService);

        FileBasedMembershipPublisher publisher = new FileBasedMembershipPublisher();
        publisher.setId($id("file-publisher"));
        publisher.setResourceProvider(resourceProvider);
        publisher.setContainer(member1);
        publisher.setPublishToDirectory(new File("C:\\temp\\file_poller"));

        //--------------

        DefaultClient client = new DefaultClient();
        client.setMember(member1);
        client.setMembershipPublisher(publisher);

        //--------------

        client.start();
        client.stop();
    }

    public static void main(String[] args) throws Exception {
        new DefaultClientSimpleTest().test();
    }
}
