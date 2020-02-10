package com.tibco.cep.runtime.hbase.cacheaside;

import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.txn.RtcTransaction;
import com.tibco.cep.runtime.service.om.api.EntityDao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 7/10/13
 * Time: 2:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class HBaseStore {

    private Cluster cacheCluster;

    public HBaseStore(Cluster cacheCluster) {
        this.cacheCluster = cacheCluster;
    }

    public void saveTransaction(RtcTransaction txn) throws Exception {
        Collection<RtcTransaction> coll = new ArrayList<RtcTransaction>();
        coll.add(txn);
        saveTransaction(coll);
    }

    private void saveTransaction(Collection<RtcTransaction> txns) throws Exception {

        //Currently just a single adaptor
        HbaseAdaptor hbaseAdaptor = new HbaseAdaptor(cacheCluster);
        Iterator<RtcTransaction> allTxns = txns.iterator();
        while (allTxns.hasNext()) {
            saveTransaction(hbaseAdaptor, allTxns.next());
        }

    }

    private void saveTransaction(HbaseAdaptor hbaseAdaptor, RtcTransaction txn) throws Exception {
        if (txn.getDeletedConcepts().isEmpty() == false) {

        }
        if (txn.getAddedConcepts().isEmpty() == false) {
            insertConcepts(hbaseAdaptor, txn.getAddedConcepts());
        }
        if (txn.getModifiedConcepts().isEmpty() == false) {

        }
    }

    private void insertConcepts(HbaseAdaptor hbaseAdaptor, Map<Integer, Map<Long, Concept>> entries) throws Exception {

        try {
            for (Map.Entry<Integer, Map<Long, Concept>> mEntry : entries.entrySet()) {
                int typeId = mEntry.getKey();
                EntityDao provider = cacheCluster.getMetadataCache().getEntityDao(typeId);

                //todo: Check whether HBASE is Enabled for Storage from BE
                //if(...){}
                String className = provider.getEntityClass().getName();
                Map<Long, Concept> txnMap = mEntry.getValue();

                hbaseAdaptor.insertConcepts(className, txnMap);
            }
        } finally {
           // hbaseAdaptor.deleteAll();
        }
    }
}
