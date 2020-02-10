package com.tibco.cep.runtime.hbase.cacheaside;

import com.tibco.cep.runtime.hbase.cacheaside.Interfaces.HChannel;
import com.tibco.cep.runtime.hbase.cacheaside.Interfaces.HConstants_old;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTablePool;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 7/2/13
 * Time: 2:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class HbaseManager {

    private static HbaseManager hbaseManager = null;
    private final HashMap<String, HbaseSpaceDescriptor> hbaseSpaceMap = new HashMap<String, HbaseSpaceDescriptor>();
    private boolean hbaseConnection;
    private Configuration hConfig;
    private HBaseAdmin admin;
    private HTablePool htablePool;

    private HbaseManager(){}

    public Configuration gethConfig() {
        return hConfig;
    }

    public HBaseAdmin getAdmin() {
        return admin;
    }

    public HTablePool getHTablePool() {
        return htablePool;
    }

    public static HbaseManager getInstance() {
        if (hbaseManager == null) {
            hbaseManager = new HbaseManager();
        }

        return hbaseManager;
    }

    public void loadSpaceMap(String conceptName, HbaseSpaceDescriptor hbaseSpaceDescriptor) {
        hbaseSpaceMap.put(conceptName, hbaseSpaceDescriptor);
    }

    public String getFieldType(String conceptName, String fieldName) {
        return hbaseSpaceMap.get(conceptName).get(fieldName);
    }

    /**
     * Create the tables in hbase
     */
    public void init() throws Exception {

        //***Create the connection***
        HChannel hChannel = ChannelImpl.getInstance();
        hChannel.connect("192.168.174.132", "2181");
        this.admin = hChannel.getAdmin();
        this.hConfig = hChannel.gethConfig();

        //The pool size is hard coded for time being
        this.htablePool = new HTablePool(hConfig,10);

        HTableDescriptor tableDescriptor;
        HColumnDescriptor columnDescriptor = null;
        columnDescriptor = new HColumnDescriptor("info");


        for (Map.Entry<String,HbaseSpaceDescriptor> mEntry : hbaseSpaceMap.entrySet()) {
            String conceptName = mEntry.getKey();
            tableDescriptor = new HTableDescriptor(conceptName);

            //***Creating the Columns***
//            HbaseSpaceDescriptor spaceDescriptor = mEntry.getValue();
//            for(String fieldName : spaceDescriptor.getSpaceFields())
//            {
//                columnDescriptor = new HColumnDescriptor(fieldName);
//                tableDescriptor.addFamily(columnDescriptor);
//            }
            tableDescriptor.addFamily(columnDescriptor);
            admin.createTable(tableDescriptor);
        }

        //***Create Index Table***
        tableDescriptor = new HTableDescriptor(HConstants_old.INDEXTABLE);
        //columnDescriptor = new HColumnDescriptor("info");
        tableDescriptor.addFamily(columnDescriptor);
        admin.createTable(tableDescriptor);
    }

    public boolean isHbaseConnection() {
        return hbaseConnection;
    }

    public void setHbaseConnection(boolean hbaseConnection) {
        this.hbaseConnection = hbaseConnection;
    }

    public void deleteAllTables() throws IOException {

        admin.disableTable(HConstants_old.INDEXTABLE);
        admin.deleteTable(HConstants_old.INDEXTABLE);

        for (Map.Entry mEntry : hbaseSpaceMap.entrySet()) {
             admin.disableTable((String)mEntry.getKey());
             admin.deleteTable((String)mEntry.getKey());
        }

       // HConnectionManager.deleteAllConnections();
        admin.close();
    }
}
