package com.tibco.cep.loadbalancer.impl.server.membership.file;

import java.io.File;
import java.io.FileInputStream;

import com.tibco.cep.common.resource.Id;
import com.tibco.cep.loadbalancer.impl.membership.MembershipInfo;
import com.tibco.cep.loadbalancer.impl.membership.file.AbstractFileMembershipChangeProvider;
import com.tibco.cep.loadbalancer.server.core.Member;
import com.tibco.cep.loadbalancer.server.core.sink.Sink;
import com.tibco.cep.loadbalancer.util.SimpleMemberCodec;
import com.tibco.cep.loadbalancer.util.SimpleMemberCodec.ServerSideMembershipInfo;
import com.tibco.cep.util.annotation.LogCategory;

/*
* Author: Ashwin Jayaprakash / Date: Mar 24, 2010 / Time: 10:58:19 AM
*/

@LogCategory("loadbalancer.be.server.membership.file")
public class FileBasedMembershipChangeProvider extends AbstractFileMembershipChangeProvider<Sink, Member> {
    public FileBasedMembershipChangeProvider() {
    }

    public FileBasedMembershipChangeProvider(Id id) {
        super(id);
    }

    @Override
    protected MembershipInfo<Sink, Member> readFromFile(File file) {
        try {
            FileInputStream inputStream = new FileInputStream(file);

            ServerSideMembershipInfo membershipInfo = SimpleMemberCodec.read(inputStream, resourceProvider);

            inputStream.close();

            return membershipInfo;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}