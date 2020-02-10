package com.tibco.cep.runtime.hbase;

import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 10/9/13
 * Time: 1:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class HResourceManager {

    private static HResourceManager resourceManager = new HResourceManager();
    private Configuration hConfig;
    private HBaseAdmin hAdmin;
    Logger logger;

    private HResourceManager() {
        logger = LogManagerFactory.getLogManager().getLogger(HResourceManager.class);
    }

    public static HResourceManager getInstance()
    {
        return resourceManager;
    }

    public HBaseAdmin getHbaseAdmin() {
        return hAdmin;
    }

    public Configuration getHbaseConfig() {
        return hConfig;
    }

    public void init() throws MasterNotRunningException, ZooKeeperConnectionException {

        String zookeeperQuorum = null;
        String zookeeperClientPort = null;

        if(hConfig == null)
        {
            hConfig = HBaseConfiguration.create();
            hConfig.set("hbase.zookeeper.quorum", zookeeperQuorum);//"192.168.174.132");//"localhost");//"192.168.174.128");
            hConfig.set("hbase.zookeeper.property.clientPort", zookeeperClientPort);//"2181");

            HBaseAdmin.checkHBaseAvailable(hConfig);

            hAdmin =  new HBaseAdmin(hConfig);
        }
    }




}
