package com.tibco.cep.runtime.service.dao.impl.tibas;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Observer;
import java.util.Set;
import java.util.Map.Entry;

import com.tibco.as.space.ASException;
import com.tibco.as.space.ASStatus;
import com.tibco.as.space.Member;
import com.tibco.as.space.Space;
import com.tibco.as.space.Tuple;
import com.tibco.as.space.remote.MemberInvocable;
import com.tibco.cep.as.kit.map.KeyValueTupleAdaptor;
import com.tibco.cep.as.kit.map.SpaceMap;
import com.tibco.cep.as.kit.rmi.InvocationHelper;
import com.tibco.cep.as.kit.tuple.DataType;
import com.tibco.cep.as.kit.tuple.TupleCodec;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMember;
import com.tibco.cep.runtime.service.om.api.ControlDaoType;
import com.tibco.cep.runtime.service.om.api.DaoProvider;
import com.tibco.cep.runtime.service.om.api.DefaultInvocableResult;
import com.tibco.cep.runtime.service.om.api.Invocable;
import com.tibco.cep.runtime.service.om.api.InvocationService;

/*
 * Author: Bala / Date: Dec 24, 2010 / Time: 4:09:23 PM
 */
public class ASInvocationService implements InvocationService {

    final static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(ASInvocationService.class);

    ASControlDao<Object, Object> invocationServiceDao;
    DaoProvider provider;
    
    public ASInvocationService(ASDaoProvider provider) {
        this.provider = provider;
        //this.invocationServiceDao = (ASControlDao<Object, Object>) provider.createControlDao(Object.class, Object.class, ControlDaoType.InvocationService);
        this.invocationServiceDao = (ASControlDao<Object, Object>) provider.createControlDao(Object.class, Object.class, ControlDaoType.Master);
    }

    @Override
    public Map<String, Invocable.Result> invokeAndObserve(Invocable invocable, GroupMember groupMember, Observer observer) {
        Set<Member> nativeMembers = new HashSet<Member>();
        nativeMembers.add((Member)groupMember.getNativeMember());
        Map resultMap = invoke(invocationServiceDao.getSpaceMap(), invocable, nativeMembers);
        Map<String, Invocable.Result> memberResults = new LinkedHashMap<String, Invocable.Result>();
        for (Iterator iter = resultMap.entrySet().iterator(); iter.hasNext();) {
            Map.Entry entry = (Entry) iter.next();
            String memberId = (String)entry.getKey();
            Invocable.Result result = (Invocable.Result) entry.getValue();
            if (memberId.equals(groupMember.getMemberId().toString())) {
                memberResults.put(memberId, result);
            }
        }
        observer.update(null, memberResults);
        return memberResults;
    }
    
    @Override
    public Map<String, Invocable.Result> invoke(Invocable invocable, Set<GroupMember> members) {
        Set<Member> nativeSet = toNative(members);
        Map resultMap = invoke(invocationServiceDao.getSpaceMap(), invocable, nativeSet);
        return resultMap;
    }
    
    private Set<Member> toNative(Set<GroupMember> members) {
        Set<Member> set = new HashSet<Member>();
        if (members != null) {
            for (GroupMember<Member> m : members) {
                set.add(m.getNativeMember());
            }
        }
        return set;
    }
    
    public static Map<String, Invocable.Result> transformResults(Map result) {
        
        Map<String, Invocable.Result> memberResults = new LinkedHashMap<String, Invocable.Result>();
        Iterator iter = result.entrySet().iterator();
        while(iter.hasNext()) {
            Map.Entry entry = (Entry) iter.next();
            String key = (String) entry.getKey();
            //System.err.println("# client side key = " + key);
            key = key.substring("result.".length());
            Invocable.Result value = (Invocable.Result) entry.getValue();
            memberResults.put(key, value);
        }
        
        return memberResults;
    }
    
    private <T> Map<String, T> invoke(SpaceMap spaceMap, Invocable invocable, Set<Member> members) {
        Member targetMember = null;

        if (members != null && members.size() == 1) {
            targetMember = members.iterator().next();
        }
        
        KeyValueTupleAdaptor tupleAdaptor = spaceMap.getTupleAdaptor();

        Tuple tuple = Tuple.create();
        tupleAdaptor.putInTuple(tuple, Invocable.class.getName(), DataType.SerializedBlob, invocable);

        InvocationHelper helper = new InvocationHelper()
                .setSpaceMap(spaceMap)
                .setRemoteCode(ASInvocableWrapper.class, targetMember)
                .setContextTuple(tuple);

        // In some cases its observed that the remote invocation fails with this message:
        // TIBAS_SYS_ERROR (peer_not_found - member_id=a6161aa-7876-4d5a7a27-3d)
        // Bala: TODO: Remove the retry kludge once AS API is fixed.

        int retryCount = 0;
        while (retryCount < 100) {
            try {
                Map<String, T> map = new LinkedHashMap<String, T>();

                helper.buildAndInvoke(map);

                Map resultMap = transformResults(map);

                return resultMap;
            } catch (ASException | RuntimeException e) {
                if (e.getMessage().contains("peer_not_found")) {
                    retryCount++;
                    // throw new RuntimeException(e);
                    LOGGER.log(Level.DEBUG, "Retrying Invocation. Retry Attempt %s", retryCount);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                    }
                }
                else if (e instanceof ASException && ((ASException)e).getStatus() == ASStatus.INVALID_OBJECT) {
                    // This might be an actor-stopped instance
                    // where the space/metaspace has been closed.
                    LOGGER.log(Level.ERROR, "Invocation failed. Space or metaspace may be closed.");
                    break;
                }
                else if (e instanceof RuntimeException) {
                    throw (RuntimeException)e;
                }
                else {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }

    void start() {
        invocationServiceDao.start();
    }
    
    public static class ASInvocableWrapper implements MemberInvocable {

        @Override
        public Tuple invoke(Space space, Tuple tuple) {

            Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
            String memberName = ((ASDaoProvider)cluster.getDaoProvider()).getMetaspace().getSelfMember().getName();
            ASDaoProvider daoProvider = (ASDaoProvider) cluster.getDaoProvider();
            TupleCodec tupleCodec = daoProvider.getTupleCodec();
            
            DefaultInvocableResult result = new DefaultInvocableResult();

            Map<String, Object> tupleContents = tupleCodec.readTupleContents(tuple);

            Invocable inv = (Invocable) tupleContents.get(Invocable.class.getName());
            
            try {
                if (inv != null) {
                    // Before invoking, add hook for initialization of the invocable?
                    LOGGER.log(Level.DEBUG, "Invoking %s", inv.getClass().getName());
                    Object invocableResult = inv.invoke(null);
                    LOGGER.log(Level.DEBUG, "Invocation %s completed with success [%s]", inv.getClass().getName(), invocableResult);
                    result.setResult(invocableResult);
                    result.setStatus(Invocable.Status.SUCCESS);
                }
            } catch (Exception e) {
                result.setException(e);
                result.setStatus(Invocable.Status.ERROR);
                LOGGER.log(Level.DEBUG, "Invocation %s completed with exception", e, inv.getClass().getName());
            }

            Tuple resultTuple = Tuple.create();
            String resultKey = "result." + (memberName == null ? "UNK" : memberName); //TODO: Fatih - cleanup
            //System.err.println("## remote key = " + resultKey);
            tupleCodec.putInTuple(resultTuple, resultKey, DataType.SerializedBlob, result);

            return resultTuple;
        }
    }
}
