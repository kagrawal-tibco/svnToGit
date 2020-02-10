package com.tibco.cep.runtime.hbase;

import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.hbase.types.HDataType;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HConnection;

import java.io.IOException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 10/9/13
 * Time: 4:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class HMapCreator {
    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(HMapCreator.class);

    public static <K, V> HMap<K, V> createHMap(Parameters<K, V> parameters) throws IOException {

        HTableDescriptor tableDescriptor = new HTableDescriptor(parameters.getTableName());
        HColumnDescriptor columnDescriptor = new HColumnDescriptor("info-family");
        tableDescriptor.addFamily(columnDescriptor);

        HBaseAdmin hAdmin = parameters.getHAdmin();

        hAdmin.createTable(tableDescriptor);

        return new HMap<K,V>(parameters.getHConnection(), parameters.getTableName());
    }

    public static class Parameters<K, V> {

        private String tableName;
        private HBaseAdmin HAdmin;
        private HConnection hConnection;
        private String columnFamily;
        private Map<String, HDataType> explicitFieldDefs;

        public HConnection gethConnection() {
            return hConnection;
        }

        public String getColumnFamily() {
            return columnFamily;
        }

        public void setColumnFamily(String columnFamily) {
            this.columnFamily = columnFamily;
        }

        public String getTableName() {
            return tableName;
        }

        public HBaseAdmin getHAdmin() {
            return HAdmin;
        }

        public HConnection getHConnection() {
            return hConnection;
        }

        public Parameters<K, V> setTableName(String tableName) {
            this.tableName = tableName;

            return this;
        }

        public Parameters<K, V> setHAdmin(HBaseAdmin HAdmin) {
            this.HAdmin = HAdmin;

            return this;
        }

        public Parameters<K, V> setHConnection(HConnection hConnection) {
            this.hConnection = hConnection;

            return this;
        }

        public void setExplicitFieldDefs(Map<String, HDataType> explicitFieldDefs) {
            this.explicitFieldDefs = explicitFieldDefs;
        }

        public Map<String, HDataType> getExplicitFieldDefs() {
            return explicitFieldDefs;
        }
    }

}
