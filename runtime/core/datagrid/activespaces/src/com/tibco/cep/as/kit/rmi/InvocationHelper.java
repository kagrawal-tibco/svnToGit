package com.tibco.cep.as.kit.rmi;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import com.tibco.as.space.ASException;
import com.tibco.as.space.InvokeOptions;
import com.tibco.as.space.Member;
import com.tibco.as.space.Tuple;
import com.tibco.as.space.remote.Invocable;
import com.tibco.as.space.remote.InvokeResult;
import com.tibco.as.space.remote.InvokeResultList;
import com.tibco.as.space.remote.MemberInvocable;
import com.tibco.cep.as.kit.map.SpaceMap;
import com.tibco.cep.as.kit.tuple.TupleCodec;
import com.tibco.cep.runtime.service.dao.impl.tibas.ASConstants;
import com.tibco.cep.runtime.util.SystemProperty;

/*
 * Author: Ashwin Jayaprakash / Date: Dec 1, 2010 / Time: 11:26:49 AM
 */
public class InvocationHelper<K, V> {
    protected SpaceMap<K, V> spaceMap;

    protected Class remoteCode;

    //@Optional
    protected Tuple contextTuple;

    //@Optional
    K targetKey;

    //@Optional
    protected Member targetMember;

    //@Optional
    protected boolean targetSeedersOnly;
    
    private Long invocationTimeout = null;

	public InvocationHelper() {
		Properties properties = System.getProperties();
		String invocationTimeoutProp = properties.getProperty(ASConstants.PROP_AS_INVOCATION_TIMEOUT);
		if (invocationTimeoutProp != null) {
			this.invocationTimeout = Long.parseLong(invocationTimeoutProp);
		}
    }

    public SpaceMap<K, V> getSpaceMap() {
        return spaceMap;
    }

    public Class getRemoteCode() {
        return remoteCode;
    }

    public Tuple getContextTuple() {
        return contextTuple;
    }

    public K getTargetKey() {
        return targetKey;
    }

    public Member getTargetMember() {
        return targetMember;
    }

    public boolean isTargetSeedersOnly() {
        return targetSeedersOnly;
    }

    public InvocationHelper setSpaceMap(SpaceMap<K, V> spaceMap) {
        this.spaceMap = spaceMap;

        return this;
    }

    public InvocationHelper setRemoteCode(Class<? extends Invocable> invocableClass, K key) {
        this.remoteCode = invocableClass;
        this.targetKey = key;
        this.targetMember = null;
        this.targetSeedersOnly = true;

        return this;
    }

    public InvocationHelper setRemoteCode(Class<? extends MemberInvocable> MemberInvocableClass, Member targetMember) {
        this.remoteCode = MemberInvocableClass;
        this.targetKey = null;
        this.targetMember = targetMember;
        this.targetSeedersOnly = false;

        return this;
    }

    public InvocationHelper setRemoteCodeForMembers(Class<? extends MemberInvocable> MemberInvocableClass) {
        this.remoteCode = MemberInvocableClass;
        this.targetKey = null;
        this.targetMember = null;
        this.targetSeedersOnly = false;

        return this;
    }

    public InvocationHelper setRemoteCodeForSeeders(Class<? extends MemberInvocable> MemberInvocableClass) {
        this.remoteCode = MemberInvocableClass;
        this.targetKey = null;
        this.targetMember = null;
        this.targetSeedersOnly = true;

        return this;
    }

    public InvocationHelper setContextTuple(Tuple tuple) {
        contextTuple = tuple;

        return this;
    }

    /**
     * This instance cannot be used after this call.
     *
     * @param resultCollector The invocation results will be added to this.
     * @throws ASException
     */
    public <A> void buildAndInvoke(Map<String, A> resultCollector) throws ASException {
        if (spaceMap == null) {
            throw new IllegalArgumentException("Parameter [spaceMap] must be set");
        }

        //--------------

        if (remoteCode == null) {
            throw new IllegalArgumentException("Parameter [remoteCode] must be set");
        }

        //--------------
        try {
        	InvokeOptions invokeOptions = InvokeOptions.create().setContext(contextTuple);
        	if (invocationTimeout != null) {
            	invokeOptions.setTimeout(invocationTimeout);
            }
        	
            if (targetKey != null) {
                Tuple keyTuple = spaceMap.getTupleAdaptor().makeTuple(targetKey);
                InvokeResult invokeResult = spaceMap.getSpace().invoke(keyTuple,remoteCode.getName(), invokeOptions);

                collectInto(invokeResult, resultCollector, spaceMap.getTupleAdaptor());

            } else if (targetMember == null) {
                InvokeResultList invokeResultList = null;

                if (targetSeedersOnly) {
                    invokeResultList = spaceMap.getSpace().invokeSeeders(remoteCode.getName(), invokeOptions);
                } else {
                    invokeResultList = spaceMap.getSpace().invokeMembers(remoteCode.getName(), invokeOptions);
                    invokeResultList.addAll(spaceMap.getSpace().invokeRemoteMembers(remoteCode.getName(), InvokeOptions.create().setContext(contextTuple)));
                }

                for (InvokeResult invokeResult : invokeResultList) {
                    collectInto(invokeResult, resultCollector, spaceMap.getTupleAdaptor());
                }
            } else {
                InvokeResult invokeResult = spaceMap.getSpace().invokeMember(targetMember, remoteCode.getName(), invokeOptions);
                collectInto(invokeResult, resultCollector, spaceMap.getTupleAdaptor());
            }
        }
        catch (ASException ae) {
            SpaceMap.traceAsException(ae, "Space or metaspace may be closed.");
        }
    }

    /**
     * This instance cannot be used after this call.
     *
     * @return
     * @throws ASException
     */
    public <T> LinkedHashMap<String, T> buildAndInvoke() throws ASException {
        LinkedHashMap<String, T> results = new LinkedHashMap<String, T>();

        buildAndInvoke(results);

        return results;
    }

    private <T> void collectInto(InvokeResult invokeResult, Map<String, T> destination, TupleCodec tupleCodec)
            throws ASException {
        if (invokeResult.hasError()) {
            throw invokeResult.getError();
        }

        Tuple resultTuple = invokeResult.getReturn();
        tupleCodec.readTupleContentsInto(resultTuple, (Map<String, Object>) destination);
    }
}
