package com.tibco.cep.as.kit.map;

import com.tibco.as.space.ASException;
import com.tibco.as.space.Member.DistributionRole;
import com.tibco.as.space.MemberDef;
import com.tibco.as.space.Metaspace;
import com.tibco.as.space.SpaceDef.DistributionPolicy;
import com.tibco.cep.as.kit.tuple.DefaultTupleCodec;

import java.io.IOException;
import java.util.HashMap;

/*
 * Author: Ashwin Jayaprakash / Date: Dec 9, 2010 / Time: 1:30:00 PM
 */
public class HashTest {
    public static void main(String[] args) throws ASException, IOException {
        String metaspaceName = "Metaspace";
        String listenUrl = "";
        String discoverUrl = "";

        MemberDef cd = MemberDef.create()
                .setDiscovery(discoverUrl)
                .setListen(listenUrl);

        Metaspace metaspace = Metaspace.connect(metaspaceName, cd);

        SpaceMapCreator.Parameters<Integer, String> parameters =
                new SpaceMapCreator.Parameters<Integer, String>()
                        .setSpaceName("test_String_space")
                        .setRole(DistributionRole.LEECH)
                        .setDistributionPolicy(DistributionPolicy.DISTRIBUTED)
                        .setKeyClass(Integer.class)
                        .setValueClass(String.class)
                        .setTupleCodec(new DefaultTupleCodec());

        SpaceMap<Integer, String> spaceMap = SpaceMapCreator.create(metaspace, parameters);

        //-----------

        System.out.println("============ Put test ============");

        for (int i = 0; i < 10000; i++) {
            Integer integer = i;

            spaceMap.put(integer, integer.toString());
        }

        System.out.println("Put total " + spaceMap.size());

        System.out.println("Enter?");
        System.in.read();

        System.out.println("============ PutAll test ============");

        HashMap<Integer, String> map = new HashMap<Integer, String>();

        for (int i = 0; i < 10000; i++) {
            Integer integer = i;

            map.put(integer, integer.toString());
        }

        spaceMap.putAll(map);

        System.out.println("PutAll total " + spaceMap.size());

        System.out.println("Enter?");
        System.in.read();
    }
}
