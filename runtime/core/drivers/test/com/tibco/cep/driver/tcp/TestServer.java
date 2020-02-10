package com.tibco.cep.driver.tcp;

import com.tibco.cep.driver.tcp.impl.TcpConnectionFactory;
import com.tibco.tibrv.TibrvMsg;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Mar 28, 2005
 * Time: 4:14:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestServer implements TcpListener {
    public static void main(String[] args) {

        try {

            TestServer tt = new TestServer();
            TcpServer srv = TcpConnectionFactory.factory().newServer("ADMIN", null, 8333, tt);
            srv.startListening();
            System.out.println("Tcp Server listening on:" + srv.getInetAddress());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void onMessage(TcpConnection s, byte[] data, int length) throws Exception {

        TibrvMsg msg = new TibrvMsg(data);
        System.out.println(msg.toString());
        msg.update("server", "ADMIN");
        s.send(msg.getAsBytes());
    }


}
