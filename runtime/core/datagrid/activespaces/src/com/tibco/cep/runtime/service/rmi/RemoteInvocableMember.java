package com.tibco.cep.runtime.service.rmi;

import com.tibco.as.space.Space;
import com.tibco.as.space.Tuple;
import com.tibco.as.space.remote.MemberInvocable;
import com.tibco.cep.as.kit.collection.DiscardableSet;
import com.tibco.cep.as.kit.map.KeyValueTupleAdaptor;
import com.tibco.cep.as.kit.map.KeyValueTupleDef;
import com.tibco.cep.as.kit.map.SpaceMap;
import com.tibco.cep.as.kit.tuple.TupleCodec;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.dao.impl.tibas.ASDaoProvider;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.service.om.api.ControlDao;
import com.tibco.cep.runtime.service.om.api.EntityDao;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/*
* Author: Ashwin Jayaprakash / Date: 12/13/10 / Time: 1:44 PM
*/
public class RemoteInvocableMember implements MemberInvocable {
	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(RemoteInvocableMember.class);
			
    public RemoteInvocableMember() {
    }

    /**
     * The instance method that will run on the remote nodes.
     *
     * @param space
     * @param tuple Expects {@link ASNonIndexedFilter}, {@link KeyValueTupleDef} and {@link ResultMode}.
     * @return If {@link ResultMode} is {@link ResultMode#keyset} then only keys will be returned other wise both key and value
     *         will be returned. Value always follows the key.
     */
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

        //----------------

        BEClassLoader classLoader = (BEClassLoader) cluster.getRuleServiceProvider().getTypeManager();
        ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();

        Thread.currentThread().setContextClassLoader(classLoader);
        LinkedHashMap<String, Object> filteredKeys;
        try {
            Map<String, Object> tupleContents = tupleCodec.readTupleContents(tuple);

            ASNonIndexedFilter filter = (ASNonIndexedFilter) tupleContents.get(ASNonIndexedFilter.class.getName());

            KeyValueTupleDef tupleDef = (KeyValueTupleDef) tupleContents.get(KeyValueTupleDef.class.getName());

            ResultMode resultMode = (ResultMode) tupleContents.get(ResultMode.class.getName());

            tupleContents.clear();

            //----------------

            if (spaceMap == null) {
                kvTupleAdaptor = new KeyValueTupleAdaptor(tupleDef, tupleCodec);
                spaceMap = new SpaceMap(space, kvTupleAdaptor);
            }

            //----------------
            
            // Enforcing manual limit to prevent hitting AS 2.1gb serialization limit
            // ideally this would be a fairly large number
            long limit = Long.parseLong(System.getProperties().getProperty("be.engine.cluster.as.remote.tuple.limit", "-1"));
            if (limit > 0) LOGGER.log(Level.TRACE, "Remote serialization limit set to [%s]", limit);

            filteredKeys = new LinkedHashMap<String, Object>();
            int fieldNameCounter = 0;

            DiscardableSet<Entry> entrySet = spaceMap.entrySetSeeded();
            
            try {
                String seededMemberId = space.getMetaspace().getSelfMember().getName() + ".";

                for (Entry entry : entrySet) {
                	if (limit > 0 && fieldNameCounter >= limit) break;
                	
                    Object key = entry.getKey();
                    Object value = entry.getValue();

                    boolean success = filter.evaluate(value);
                    if (success) {
                        String fakeFieldName = ResultMode.keyset.prefix + seededMemberId + fieldNameCounter;
                        filteredKeys.put(fakeFieldName, key);

                        //Put value also
                        if (resultMode == ResultMode.entryset) {
                            fakeFieldName = ResultMode.entryset.prefix + seededMemberId + fieldNameCounter;
                            filteredKeys.put(fakeFieldName, value);
                        }

                        fieldNameCounter++;
                    }
                }
                if (fieldNameCounter > 0) LOGGER.log(Level.DEBUG, "Limit [%s] and total processed successfully [%s] for space[%s]", limit, fieldNameCounter, spaceMap.getSpaceName());
            }
            catch (RuntimeException e) {
                throw e;
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
            finally {
                entrySet.discard();
            }
        }
        finally {
            Thread.currentThread().setContextClassLoader(oldClassLoader);
        }

        //----------------

        Tuple result = kvTupleAdaptor.readMapContents(filteredKeys);

        filteredKeys.clear();

        return result;
    }

    //-------------

    public static enum ResultMode {
        keyset("key."),
        /**
         * This will automatically come with {@link #keyset} too.
         */
        entryset("value.");

        protected String prefix;

        ResultMode(String prefix) {
            this.prefix = prefix;
        }

        public String getPrefix() {
            return prefix;
        }
    }
}
