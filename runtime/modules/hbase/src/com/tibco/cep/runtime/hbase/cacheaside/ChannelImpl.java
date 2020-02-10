package com.tibco.cep.runtime.hbase.cacheaside;

import com.tibco.cep.runtime.hbase.cacheaside.Interfaces.HChannel;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HBaseAdmin;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 7/2/13
 * Time: 5:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class ChannelImpl implements HChannel {
    private HBaseAdmin admin;
    private Configuration hConfig;

    //****Make singelton thread safe***
    private static ChannelImpl hChannel = null;

    private ChannelImpl() {
    }

    public static HChannel getInstance() {
        if (hChannel == null) {
            hChannel = new ChannelImpl();
        }

        return hChannel;
    }

    @Override
    public void connect(String zookepperQuorum, String zookepperClientPort) throws Exception {

        Configuration hConfig = null;
        // if (SystemProperty.getConfig() == null) {
        hConfig = HBaseConfiguration.create();
        hConfig.set("hbase.zookeeper.quorum", zookepperQuorum);//"192.168.174.132");//"localhost");//"192.168.174.128");
        hConfig.set("hbase.zookeeper.property.clientPort", zookepperClientPort);//"2181");

        HBaseAdmin.checkHBaseAvailable(hConfig);

        System.out.println("Connection to HBase established succesfully...");

        sethConfig(hConfig);
        // HbaseHelper.setHbaseConnection(true);
        //SystemProperty.setConfig(hConfig);
        //}

        // if (SystemProperty.getAdmin() == null) {
        HBaseAdmin hAdmin = new HBaseAdmin(hConfig);
        hAdmin.getConnection();
        //    SystemProperty.setAdmin(hAdmin);
        //}
        setAdmin(hAdmin);
    }

    @Override
    public HBaseAdmin getAdmin() {
        return admin;
    }

    @Override
    public void setAdmin(HBaseAdmin admin) {
        this.admin = admin;
    }

    @Override
    public Configuration gethConfig() {
        return hConfig;
    }

    @Override
    public void sethConfig(Configuration hConfig) {
        this.hConfig = hConfig;
    }
}
