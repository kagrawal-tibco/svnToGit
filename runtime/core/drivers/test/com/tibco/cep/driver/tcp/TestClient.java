package com.tibco.cep.driver.tcp;


/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Mar 28, 2005
 * Time: 4:19:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestClient {

    public static void main(String[] args) {
        try {

//            FileInputStream fr = new FileInputStream("rootServices.cs");
//            byte[] buf = new byte[fr.available()] ;
//            fr.read(buf);
//            System.out.println(buf.length);

//            Tibrv.open();

//            RulesetInfo undeployRuleSet = new RulesetInfo();
//            undeployRuleSet.

//            ExpandedName en = ExpandedName.makeName("$ns");
//            System.out.println(en);
//            XiNode node = XiFactoryFactory.newInstance().createElement(en);
//            System.out.println(XiSerializer.serialize(node));
//
//            RulesetInfo rsetInfo = new RulesetInfo();
//            RuleInfo rInfo = new RuleInfo();
//            rInfo.rulename = "be.gen.RuleSet3.Rule1";
//            rInfo.rsetName = "RuleSet3";
//            rInfo.pathUrl = "file:///C:/tmp/BE/classes/be/gen/RuleSet3/";
//            rInfo.applyToAllObjects = true;
//
//            rsetInfo.addRuleInfo(rInfo);
//            rsetInfo.addRuleInfo("be.gen.RuleSet2.Rule1", "file:///C:/tmp/BE/classes/be/gen/RuleSet2/", true, null);
//            rsetInfo.addRuleInfo("b", false, null);
//            rsetInfo.addRuleInfo("c", true, null);

//            TibBEAdmin admin = new TibBEAdmin("tcp://localhost:8333", null, null, null);
//            StatusInfo sInfo = admin.deployRules(rsetInfo);
            /*
            TibrvMsg msg = rsetInfo.serialize();

            RulesetInfo nsetInfo = RulesetInfo.deserializeFrom(msg);
            Iterator itr = nsetInfo.rules();
            while (itr.hasNext() ) {
                RuleInfo rInfo = (RuleInfo) itr.next();
                System.out.println(rInfo);
            }

            msg.add(CommandIDs.COMMAND_STR, CommandIDs.CMD_GETREPOURL);
            byte[] msgbuf = msg.getAsBytes();

            TcpConnection connection = TcpConnectionFactory.factory().newConnection("localhost", 8333);

            connection.connect();
            //connection.send(buf, true);
            //connection.send("HELLO WORLD -1".getBytes(), true);
            //connection.send("HELLO WORLD -2".getBytes());
            //Thread.sleep(100000);
            System.out.println("Sending data for reply;" +  msg);
            byte[] test = connection.sendWithReply(msgbuf);
            System.out.println("REPLY US: " + new TibrvMsg(test).toString());
            Thread.sleep(20000);
            connection.close();
            */
        }
        catch (Exception e) {
             e.printStackTrace();
        }
    }
}
