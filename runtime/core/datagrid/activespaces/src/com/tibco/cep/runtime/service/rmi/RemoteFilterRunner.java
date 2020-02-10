package com.tibco.cep.runtime.service.rmi;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

import com.tibco.as.space.ASException;
import com.tibco.as.space.Tuple;
import com.tibco.cep.as.kit.map.KeyValueTupleAdaptor;
import com.tibco.cep.as.kit.map.KeyValueTupleDef;
import com.tibco.cep.as.kit.map.SpaceMap;
import com.tibco.cep.as.kit.rmi.InvocationHelper;
import com.tibco.cep.as.kit.tuple.DataType;
import com.tibco.cep.runtime.service.om.api.Filter;
import com.tibco.cep.runtime.service.rmi.RemoteInvocableMember.ResultMode;

/*
* Author: Ashwin Jayaprakash / Date: Dec 1, 2010 / Time: 2:36:10 PM
*/

public class RemoteFilterRunner {
    public RemoteFilterRunner() {
    }

    public static Set keySet(SpaceMap spaceMap, Filter filter) {
        LinkedHashMap<String, Object> results = new RemoteFilterRunner().invoke(spaceMap, filter, ResultMode.keyset);

        HashSet set = new HashSet();

        Iterator<Entry<String, Object>> entryIterator = results.entrySet().iterator();
        while (entryIterator.hasNext()) {
            Entry<String, Object> entry = entryIterator.next();

            set.add(entry.getValue());

            entryIterator.remove();
        }

        return set;
    }

    public static Set entrySet(SpaceMap spaceMap, Filter filter) {
        LinkedHashMap<String, Object> results = new RemoteFilterRunner().invoke(spaceMap, filter, ResultMode.entryset);

        HashMap map = new HashMap();

        Object key = null;
        Object value = null;

        Iterator<Entry<String, Object>> entryIterator = results.entrySet().iterator();
        while (entryIterator.hasNext()) {
            Entry<String, Object> entry = entryIterator.next();
            if (entry.getKey().startsWith(ResultMode.keyset.getPrefix())) {
            	String valKey = "value." + entry.getKey().substring(4);
                key = entry.getValue();
                value = results.get(valKey);
                map.put(key, value);
            }
        }
//TODO: Revert to this once AS provides ordered-set functionality
//        while (entryIterator.hasNext()) {
//            Entry<String, Object> entry = entryIterator.next();
//
//            if (entry.getKey().startsWith(ResultMode.keyset.getPrefix())) {
//                key = entry.getValue();
//            }
//            else if (entry.getKey().startsWith(ResultMode.entryset.getPrefix())) {
//                value = entry.getValue();
//
//                map.put(key, value);
//            }
//
//            entryIterator.remove();
//        }

        return map.entrySet();
    }

    /**
     * Client should use this.
     *
     * @param spaceMap
     * @param filter
     * @return
     */
    protected <T> LinkedHashMap<String, T> invoke(SpaceMap spaceMap, Filter filter, ResultMode resultMode) {
        KeyValueTupleAdaptor tupleAdaptor = spaceMap.getTupleAdaptor();

        Tuple tuple = Tuple.create();
        ASNonIndexedFilter asFilter = new ASNonIndexedFilter(filter);
        tupleAdaptor.putInTuple(tuple, ASNonIndexedFilter.class.getName(), DataType.SerializedBlob, asFilter);

        KeyValueTupleDef tupleDef = new KeyValueTupleDef(tupleAdaptor);
        tupleAdaptor.putInTuple(tuple, KeyValueTupleDef.class.getName(), DataType.SerializedBlob, tupleDef);

        tupleAdaptor.putInTuple(tuple, ResultMode.class.getName(), DataType.SerializedBlob, resultMode);

        //----------------

        InvocationHelper helper = new InvocationHelper()
                .setSpaceMap(spaceMap)
                .setRemoteCodeForSeeders(RemoteInvocableMember.class)
                .setContextTuple(tuple);

        try {
            LinkedHashMap<String, T> map = new LinkedHashMap<String, T>();

            helper.buildAndInvoke(map);

            return map;
        }
        catch (ASException e) {
        	spaceMap.resetSpaceConnection(e);
            throw new RuntimeException(e);
        }
    }
}
