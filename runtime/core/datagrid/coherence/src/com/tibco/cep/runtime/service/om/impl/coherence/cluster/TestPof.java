/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 19/8/2010
 */

package com.tibco.cep.runtime.service.om.impl.coherence.cluster;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Apr 10, 2008
 * Time: 5:59:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestPof {

    public static void main(String args[]) {

        try {
//            SimplePofContext pc = new SimplePofContext();
//            pc.registerUserType(1001, DataObject.class, new  PortableObjectSerializer(1001));

//            File file = new File(m_strURI);
//
//            FileInputStream fis = new FileInputStream(file);
//            DataInputStream dis = new DataInputStream(fis);
//            data = new byte[dis.available()];
//            dis.readFully(data);
//            strXml = new String(data);
//
//            PofContext ctx = new ConfigurablePofContext("test-pof-config.xml");
//            NamedCache nc=CacheFactory.getCache("dist-hello");
//
//            DataObject doo = new DataObject();
//            ByteArrayWriteBuffer buf = new ByteArrayWriteBuffer(1024);
//            ctx.serialize(buf.getBufferOutput(), doo);
//            nc.put(1,  (PortableObject) doo);
//            doo = (DataObject) nc.get(1);
//            System.out.println("Got " + doo.s);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}


