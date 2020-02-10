package com.tibco.cep.runtime.hbase.cacheaside.Interfaces;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HBaseAdmin;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 7/2/13
 * Time: 5:11 PM
 * To change this template use File | Settings | File Templates.
 */
public interface HChannel {

    public void connect(String zookepperQuorum, String zookepperClientPort) throws Exception;

    HBaseAdmin getAdmin();

    void setAdmin(HBaseAdmin admin);

    Configuration gethConfig();

    void sethConfig(Configuration hConfig);
}
