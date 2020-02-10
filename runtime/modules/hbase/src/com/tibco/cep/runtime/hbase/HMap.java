package com.tibco.cep.runtime.hbase;

import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HTableInterface;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 10/9/13
 * Time: 4:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class HMap<K,V> extends AbstractMap<K,V> implements Map<K,V> {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(HMap.class);

    HConnection hConnection;
    String cacheName;

    public HMap(HConnection hConnection, String cacheName) {
        this.hConnection = hConnection;
        this.cacheName = cacheName;
    }



    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public V put(K key, V value) {
        HTableInterface tableInterface;
        try {
            tableInterface = hConnection.getTable(cacheName);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

       // Put put = new Put(Bytes.toBytes(key))
         return  null;
    }

}
