package com.tibco.cep.runtime.service.rmi;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.tibco.as.space.ASException;
import com.tibco.as.space.Space;
import com.tibco.as.space.Tuple;
import com.tibco.as.space.remote.MemberInvocable;
import com.tibco.cep.as.kit.collection.DiscardableSet;
import com.tibco.cep.as.kit.map.KeyValueTupleAdaptor;
import com.tibco.cep.as.kit.map.KeyValueTupleDef;
import com.tibco.cep.as.kit.map.SpaceMap;
import com.tibco.cep.as.kit.map.SpaceMapConstants;
import com.tibco.cep.as.kit.rmi.InvocationHelper;
import com.tibco.cep.as.kit.tuple.DataType;
import com.tibco.cep.as.kit.tuple.TupleCodec;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.dao.impl.tibas.ASDaoProvider;
import com.tibco.cep.runtime.service.om.api.ControlDao;
import com.tibco.cep.runtime.service.om.api.DefaultInvocableResult;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.service.om.api.Filter;
import com.tibco.cep.runtime.service.om.api.Invocable;

/**
 * 
 * @author bgokhale
 *
 * AS wrapper class to marshal and execute FilteredInvocable
 *
 */
public class ASFilteredInvocableWrapper implements MemberInvocable, com.tibco.as.space.remote.Invocable {
    
    public ASFilteredInvocableWrapper() {
    }
    
    /**
     * Client side call.
     * 
     * @param spaceMap 
     * @param filter 
     * @param invocable
     * @return
     */
    public Map invoke(SpaceMap spaceMap, Filter filter, Invocable invocable ) {
        KeyValueTupleAdaptor tupleAdaptor = spaceMap.getTupleAdaptor();

        Tuple tuple = Tuple.create();
        ASNonIndexedFilter asFilter = new ASNonIndexedFilter(filter);
        tupleAdaptor.putInTuple(tuple, ASNonIndexedFilter.class.getName(), DataType.SerializedBlob, asFilter);

        KeyValueTupleDef tupleDef = new KeyValueTupleDef(tupleAdaptor);
        tupleAdaptor.putInTuple(tuple, KeyValueTupleDef.class.getName(), DataType.SerializedBlob, tupleDef);

        tupleAdaptor.putInTuple(tuple, Invocable.class.getName(), DataType.SerializedBlob, invocable);

        //----------------

        InvocationHelper helper = new InvocationHelper()
                .setSpaceMap(spaceMap)
                .setRemoteCodeForSeeders(ASFilteredInvocableWrapper.class)
                .setContextTuple(tuple);

        try {
            LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();

            helper.buildAndInvoke(map);
            
            Map retMap = transformResults(map);

            return retMap;
        }
        catch (ASException e) {
            throw new RuntimeException(e);
        }
    }
    
    public com.tibco.cep.runtime.service.om.api.Invocable.Result invokeWithKey(SpaceMap spaceMap, Object key, Invocable invocable) {
        KeyValueTupleAdaptor tupleAdaptor = spaceMap.getTupleAdaptor();
        KeyValueTupleDef tupleDef = new KeyValueTupleDef(tupleAdaptor.getKeyColumnName(), tupleAdaptor.getKeyType(),
                SpaceMapConstants.TUPLE_COLUMN_NAME_VALUE, DataType.SerializedBlob);

        Tuple tuple = Tuple.create();
        tupleAdaptor.putInTuple(tuple, KeyValueTupleDef.class.getName(), DataType.SerializedBlob, tupleDef);
        tupleAdaptor.putInTuple(tuple, Invocable.class.getName(), DataType.SerializedBlob, invocable);

        //----------------

        InvocationHelper helper = new InvocationHelper()
                .setSpaceMap(spaceMap)
                .setRemoteCode(ASFilteredInvocableWrapper.class, key)
                .setContextTuple(tuple);

        try {
            LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();

            helper.buildAndInvoke(map);
            
            Map retMap = transformResults(map);
            
            if (retMap.size() > 0) {
                return (com.tibco.cep.runtime.service.om.api.Invocable.Result) ((Map.Entry)retMap.entrySet().iterator().next()).getValue();
            }
            return null;
            //return retMap;
        }
        catch (ASException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * This method will run on the remote nodes.
     *
     */
    @Override
    public Tuple invoke(Space space, Tuple tuple) {
        Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
        ASDaoProvider daoProvider = (ASDaoProvider) cluster.getDaoProvider();

        SpaceMap spaceMap = null;
        TupleCodec tupleCodec = null;
        KeyValueTupleAdaptor kvTupleAdaptor = null;

        EntityDao dao = daoProvider.getEntityDao(space.getName());
        if (dao != null) {
            spaceMap = (SpaceMap) dao.getInternal();
            kvTupleAdaptor = spaceMap.getTupleAdaptor();
            tupleCodec = kvTupleAdaptor;
        }

        ControlDao cao = daoProvider.getControlDao(space.getName());
        if (cao != null) {
            spaceMap = (SpaceMap) cao.getInternal();
            kvTupleAdaptor = spaceMap.getTupleAdaptor();
            tupleCodec = kvTupleAdaptor;
        }
        
        if (tupleCodec == null) {
            tupleCodec = daoProvider.getTupleCodec();
        }
        
		Map<String, Object> tupleContents = tupleCodec.readTupleContents(tuple);
		ASNonIndexedFilter filter = (ASNonIndexedFilter) tupleContents.get(ASNonIndexedFilter.class.getName());
		KeyValueTupleDef tupleDef = (KeyValueTupleDef) tupleContents.get(KeyValueTupleDef.class.getName());
		Invocable invocable = (Invocable) tupleContents.get(Invocable.class.getName());
        
        tupleContents.clear();
        
        if (spaceMap == null) {
            kvTupleAdaptor = new KeyValueTupleAdaptor(tupleDef, tupleCodec);
            spaceMap = new SpaceMap(space, kvTupleAdaptor);
        }

        LinkedHashMap<String, Object> filteredKeys = new LinkedHashMap<String, Object>();
        int fieldNameCounter = 0;

        DiscardableSet<Entry> entrySet = spaceMap.entrySetSeeded();
        
        try {
            String seededMemberId = space.getMetaspace().getSelfMember().getName() + ".";

            for (Entry entry : entrySet) {
                Object key = entry.getKey();
                Object value = entry.getValue();

                boolean success = filter.evaluate(value);
                if (success) {
                    DefaultInvocableResult res = new DefaultInvocableResult();
                    try {
                        Object ret = null;
                        if (invocable != null) {
                            ret = invocable.invoke(entry);
                        }
                        res.setResult(ret);
                        res.setStatus(com.tibco.cep.runtime.service.om.api.Invocable.Status.SUCCESS);
                        
                    } catch (Exception e) {
                        res.setResult(e);
                        res.setStatus(com.tibco.cep.runtime.service.om.api.Invocable.Status.ERROR);
                        e.printStackTrace();
                    }
                    
                    String fakeFieldName = "key." + seededMemberId + fieldNameCounter;
                    filteredKeys.put(fakeFieldName, key);
                    fakeFieldName = "value." + seededMemberId + fieldNameCounter;
                    filteredKeys.put(fakeFieldName, res);

                    fieldNameCounter++;
                }
            }
        }
        catch (RuntimeException e) {
            e.printStackTrace();
            throw e;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        finally {
            entrySet.discard();
        }

        Tuple result = kvTupleAdaptor.readMapContents(filteredKeys);
        filteredKeys.clear();
        return result;
    }

    private Map transformResults(Map result) {
        Map map = new HashMap<Object, Invocable.Result>();

        Object key = null;
        Object value = null;

        Iterator<Entry<String, Object>> entryIterator = result.entrySet().iterator();
        while (entryIterator.hasNext()) {
            Entry<String, Object> entry = entryIterator.next();
            if (entry.getKey().startsWith("key.")) {
                key = entry.getValue();
                String valKey = "value." + entry.getKey().substring("key.".length());
                value = result.get(valKey);
                map.put(key, value);
            }
        }
        
        return map;
    }

    @Override
    public Tuple invoke(Space space, Tuple keyTuple, Tuple context) {
        Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
        ASDaoProvider daoProvider = (ASDaoProvider) cluster.getDaoProvider();
        TupleCodec tupleCodec = daoProvider.getTupleCodec();

       
        Map<String, Object> tupleContents = tupleCodec.readTupleContents(context);
        KeyValueTupleDef tupleDef = (KeyValueTupleDef)tupleContents.get(KeyValueTupleDef.class.getName());
        Invocable invocable = (Invocable)tupleContents.get(Invocable.class.getName());
        
        tupleContents.clear();

        KeyValueTupleAdaptor kvTupleAdaptor = new KeyValueTupleAdaptor(tupleDef, tupleCodec);
        Object key = kvTupleAdaptor.extractKey(keyTuple);
        
        LinkedHashMap<String, Object> filteredKeys = new LinkedHashMap<String, Object>();

        DefaultInvocableResult res = new DefaultInvocableResult();
        try {
            try {
                Object ret = null;
                if (invocable != null) {
                    Tuple tupleResult = space.get(keyTuple);
                    if (tupleResult != null) {
                            Map.Entry entry = new MapEntryAdapter(space, kvTupleAdaptor, tupleResult);
                            ret = invocable.invoke(entry);
                    }
                }
                res.setResult(ret);
                res.setStatus(Invocable.Status.SUCCESS);
                
            } catch (Exception e) {
                res.setResult(e);
                res.setStatus(Invocable.Status.ERROR);
                e.printStackTrace();
            }
            String seededMemberId = space.getMetaspace().getSelfMember().getName() + ".";
            String fakeFieldName = "key." + seededMemberId;

            filteredKeys.put(fakeFieldName, key);
            fakeFieldName = "value." + seededMemberId;
            filteredKeys.put(fakeFieldName, res);

            Tuple result = kvTupleAdaptor.readMapContents(filteredKeys);
            filteredKeys.clear();
            return result;
        }
        catch (RuntimeException e) {
            e.printStackTrace();
            throw e;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        finally {
            //entrySet.discard();
        }
    }
    
    static class MapEntryAdapter implements Map.Entry {
        protected Space space;
        protected Tuple tuple;
        protected KeyValueTupleAdaptor kvTupleAdaptor;

        public MapEntryAdapter(Space space, KeyValueTupleAdaptor kvTupleAdaptor,Tuple tuple) {
            this.space = space;
            this.tuple = tuple;
            this.kvTupleAdaptor = kvTupleAdaptor;
        }

        public Object getKey() {
            return kvTupleAdaptor.extractKey(tuple);
        }

        public Object getValue() {
            return kvTupleAdaptor.extractValue(tuple);
        }

        public Object setValue(Object value) {
            Tuple oldTuple = (Tuple)tuple.clone();
            kvTupleAdaptor.setValue(tuple, value);

            try {
                Tuple resultTuple = space.compareAndPut(oldTuple, tuple);
                return kvTupleAdaptor.extractValue(resultTuple);
            } catch ( ASException e ) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}
