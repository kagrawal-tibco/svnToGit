package com.tibco.cep.runtime.hbase.cacheaside;

import com.tibco.cep.runtime.hbase.cacheaside.Interfaces.HChannel;
import com.tibco.cep.runtime.hbase.cacheaside.Interfaces.HConstants_old;
import org.apache.hadoop.hbase.client.HBaseAdmin;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 7/12/13
 * Time: 2:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class TempClean {

    public void cleanUp() throws Exception {
        HChannel hChannel = ChannelImpl.getInstance();
        hChannel.connect("192.168.174.132", "2181");

        HBaseAdmin admin =  hChannel.getAdmin();

//        admin.disableTable("IndexTable");
//        admin.deleteTable("IndexTable");

        admin.disableTable(HConstants_old.INDEXTABLE);
        admin.deleteTable(HConstants_old.INDEXTABLE);

        admin.disableTable("_ConceptModel_Securities");
        admin.deleteTable("_ConceptModel_Securities");

        admin.disableTable("_ConceptModel_Trade");
        admin.deleteTable("_ConceptModel_Trade");

        admin.disableTable("_DataGenerator_Entities_Counter");
        admin.deleteTable("_DataGenerator_Entities_Counter");
    }

    public static void main(String args[]) throws Exception {
        TempClean tempClean = new TempClean();
        tempClean.cleanUp();
    }
}

