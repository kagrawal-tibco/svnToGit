package com.tibco.cep.runtime.hbase.cacheaside;

import com.tibco.cep.runtime.hbase.cacheaside.Interfaces.Adaptor;
import com.tibco.cep.runtime.hbase.cacheaside.Interfaces.HChannel;
import com.tibco.cep.runtime.hbase.cacheaside.Interfaces.HConstants_old;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.ConceptSerializer;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 7/2/13
 * Time: 4:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class HbaseAdaptor implements Adaptor {

    private RuleServiceProvider rsp;
    HChannel hChannel;

    public HbaseAdaptor(Cluster cluster) {
        this.rsp = cluster.getRuleServiceProvider();
    }

    @Override
    public void insertConcepts(String className, Map<Long, Concept> entries) throws IOException {
        HbaseManager hbaseManager = HbaseManager.getInstance();
        LinkedHashMap<Long, ConceptSerializer> tempStore = new LinkedHashMap<Long, ConceptSerializer>();
        String conceptUri = null;

        for (Map.Entry<Long, Concept> mEntry : entries.entrySet()) {
            Long id = mEntry.getKey();
            Concept cept = mEntry.getValue();

            if (conceptUri == null) {
                conceptUri = rsp.getTypeManager().getTypeDescriptor(cept.getExpandedName()).getURI();
                if (conceptUri.contains("/")) {
                    conceptUri = conceptUri.replaceAll("/", "_");
                }
            }

            HBaseConceptSerializer serializer = new HBaseConceptSerializer(conceptUri);
            cept.serialize(serializer);

            //***Storing concepts in temporary cache for Batch Processing****
            tempStore.put(id, serializer);

            System.out.println("Done Serializing");
        }

        //todo : ***Use the tempStore to perform Batch Inserts***
        put(tempStore, conceptUri);

    }

    @Override
    public void deleteAll() throws IOException {
        HbaseManager hbaseManager = HbaseManager.getInstance();
        hbaseManager.deleteAllTables();
    }

    private void put(Map<Long, ConceptSerializer> putStore, String uri) throws IOException {
        HbaseManager hbaseManager = HbaseManager.getInstance();
        HTablePool hTablePool = hbaseManager.getHTablePool();

        //***Get the Table from the Pool***
        HTableInterface conceptTable = hTablePool.getTable(uri);

        //***Get the IndexTable from the Pool***
        HTableInterface indexTable = hTablePool.getTable(HConstants_old.INDEXTABLE);

        List<Put> putList = new LinkedList<Put>();
        Put p = null;

        List<Put> indexPutList = new LinkedList<Put>();
        Put indexPut = null;

        for (Map.Entry<Long, ConceptSerializer> mEntry : putStore.entrySet()) {
            Map<String, byte[]> fieldDataMap = ((HBaseConceptSerializer) mEntry.getValue()).getFieldDataMap();
            p = new Put(fieldDataMap.get(HConstants_old.ID));
            indexPut = new Put(fieldDataMap.get(HConstants_old.EXTID));

            for (Map.Entry<String, byte[]> entry : fieldDataMap.entrySet()) {
                p.add(HConstants_old.INFOBYTES, Bytes.toBytes(entry.getKey()), entry.getValue());
            }

            indexPut.add(HConstants_old.INFOBYTES, HConstants_old.IDBYTES, fieldDataMap.get(HConstants_old.ID));

            putList.add(p);
            indexPutList.add(indexPut);
        }

        conceptTable.put(putList);
        indexTable.put(indexPutList);
        conceptTable.close();
        indexTable.close();
    }
}

