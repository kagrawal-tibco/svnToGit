package com.tibco.cep.loadbalancer.impl.client.membership.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.tibco.cep.common.resource.Id;
import com.tibco.cep.loadbalancer.client.core.ActualMember;
import com.tibco.cep.loadbalancer.client.core.ActualSink;
import com.tibco.cep.loadbalancer.impl.membership.file.AbstractFileMembershipPublisher;
import com.tibco.cep.loadbalancer.util.SimpleMemberCodec;
import com.tibco.cep.util.annotation.LogCategory;

/*
* Author: Ashwin Jayaprakash / Date: Mar 23, 2010 / Time: 3:44:33 PM
*/
@LogCategory("loadbalancer.be.client.membership.file")
public class FileBasedMembershipPublisher extends AbstractFileMembershipPublisher<ActualSink, ActualMember> {
    public FileBasedMembershipPublisher() {
    }

    public FileBasedMembershipPublisher(Id id) {
        super(id);
    }

    @Override
    protected void writeToFile(ActualMember container, File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);

        SimpleMemberCodec.write(container, resourceProvider, fos);

        fos.close();
    }
}
