package com.tibco.cep.functions.channel.as;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.tibco.as.space.ASException;
import com.tibco.as.space.InvokeOptions;
import com.tibco.as.space.LockOptions;
import com.tibco.as.space.Member;
import com.tibco.as.space.PutOptions;
import com.tibco.as.space.Space;
import com.tibco.as.space.SpaceDef;
import com.tibco.as.space.SpaceResult;
import com.tibco.as.space.SpaceResultList;
import com.tibco.as.space.TakeOptions;
import com.tibco.as.space.Tuple;
import com.tibco.as.space.UnlockOptions;
import com.tibco.as.space.SpaceDef.CachePolicy;
import com.tibco.as.space.SpaceDef.PersistencePolicy;
import com.tibco.as.space.SpaceDef.PersistenceType;
import com.tibco.as.space.SpaceDef.ReplicationPolicy;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.be.model.functions.BEPackage;
import com.tibco.be.model.functions.Enabled;
import com.tibco.be.model.functions.FunctionParamDescriptor;

/**
 * Generated Code from String Template.
 * Date : Sep 6, 2012
 */

public class ASSpaceHelper {

    static ConcurrentMap<String, Space> spaces = new ConcurrentHashMap<String, Space>();
    static final TakeOptions DEFAULT_TAKE_OPTIONS = TakeOptions.create();
    static final PutOptions DEFAULT_PUT_OPTIONS = PutOptions.create();
    static final LockOptions DEFAULT_LOCK_OPTIONS = LockOptions.create();
    static final InvokeOptions DEFAULT_INVOKE_OPTIONS = InvokeOptions.create();


    public static void close (String spaceName) {
        try {
            Space space = getSpace(spaceName);
            removeSpace(spaceName);
            space.close();
        } catch (ASException e) {
            throw new RuntimeException(e);
        }
        return;
    }

  
    public static void closeAll (String spaceName) {
        try {
            Space space = getSpace(spaceName);
            removeSpace(spaceName);
            space.closeAll();
        } catch (ASException e) {
            throw new RuntimeException(e);
        }
        return;
    }


    public static Object compareAndPut (String spaceName, Object compareTuple, Object updateTuple, Object ... varargs) {
        try {
            PutOptions putOpts = varargs.length == 0 ? DEFAULT_PUT_OPTIONS : (PutOptions) varargs[0];
            Tuple t1 = Tuple.class.cast(compareTuple);
            Tuple t2 = Tuple.class.cast(updateTuple);
            Space space = getSpace(spaceName);
            Tuple ret = space.compareAndPut(t1, t2, putOpts);
            return ret;
        }
        catch (ASException ase) {
            throw new RuntimeException(ase);
        }
    }


    public static Object compareAndPutAll (String spaceName, Object compareTuples, Object updateTuples, Object... varargs) {
        PutOptions putOpts = varargs.length == 0 ? DEFAULT_PUT_OPTIONS : (PutOptions) varargs[0];
        Space space = getSpace(spaceName);
        SpaceResultList resultList = space.compareAndPutAll((Collection)compareTuples, (Collection)updateTuples, putOpts);
        Collection mismatched = new LinkedList();
        for (SpaceResult result : resultList) {
        	if (result.getTuple() != null) {
        		mismatched.add(result.getTuple());
        	}
        }
        return mismatched;
    }


    public static Object compareAndTake (String spaceName, Object tuple2Compare, Object ... varargs) {
        try {
            TakeOptions takeOptions = varargs.length == 0 ? DEFAULT_TAKE_OPTIONS : (TakeOptions) varargs[0];
            Space space = getSpace(spaceName);
            Tuple tuple = Tuple.class.cast(tuple2Compare);
            return space.compareAndTake(tuple, takeOptions);
        }
        catch (ASException ase) {
            throw new RuntimeException(ase);
        }
    }


    public static Object compareAndTakeAll (String spaceName, Object compareTuples, Object... varargs) {
        TakeOptions takeOptions = varargs == null || varargs.length == 0 ? DEFAULT_TAKE_OPTIONS : (TakeOptions) varargs[0];
        Space space = getSpace(spaceName);
        SpaceResultList resultList = space.compareAndTakeAll((Collection)compareTuples, takeOptions);
        Collection mismatched = new LinkedList();
        for (SpaceResult result : resultList) {
        	if (result.getTuple() != null) {
        		mismatched.add(result.getTuple());
        	}
        }
        return mismatched;
    }


    public static Object get (String spaceName, Object keyTuple) {
        try {
            Space space = getSpace(spaceName);
            Tuple tuple = space.get((Tuple)keyTuple);
            return tuple;
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

 
    public static Object getAll (String spaceName, Object keyTuples, Object ... varargs) {
        Space space = getSpace(spaceName);
        return space.getAll((Collection)keyTuples);
    }


    public static String getMetaspaceName (String spaceName) {
        Space space = getSpace(spaceName);
        return space.getMetaspaceName();
    }


    public static Object getSpaceDef (String spaceName) {
        try {
            Space space = getSpace(spaceName);
            if (space != null) {
                return space.getSpaceDef();
            } else {
                return SpaceDef.create();
            }
        } catch (Exception e) {
            return SpaceDef.create();
        }
    }


    public static String getCachePolicy (Object spaceDef) {
        if (spaceDef instanceof com.tibco.as.space.SpaceDef) {
            return ((com.tibco.as.space.SpaceDef)spaceDef).getCachePolicy().name();
        }
        return null;
    }


    public static long getCapacity (Object spaceDef) {
        if (spaceDef instanceof com.tibco.as.space.SpaceDef) {
            return ((com.tibco.as.space.SpaceDef)spaceDef).getCapacity();
        }
        return 0L;
    }


    public static int getReplicationCount (Object spaceDef) {
        if (spaceDef instanceof com.tibco.as.space.SpaceDef) {
            return ((com.tibco.as.space.SpaceDef)spaceDef).getReplicationCount();
        }
        return 0;
    }


    public static String getReplicationPolicy (Object spaceDef) {
        if (spaceDef instanceof com.tibco.as.space.SpaceDef) {
            return ((com.tibco.as.space.SpaceDef)spaceDef).getReplicationPolicy().name();
        }
        return null;
    }


    public static String getPersistenceType (Object spaceDef) {
        if (spaceDef instanceof com.tibco.as.space.SpaceDef) {
            return ((com.tibco.as.space.SpaceDef)spaceDef).getPersistenceType().name();
        }
        return null;
    }


    public static String getPersistencePolicy (Object spaceDef) {
        if (spaceDef instanceof com.tibco.as.space.SpaceDef) {
            return ((com.tibco.as.space.SpaceDef)spaceDef).getPersistencePolicy().name();
        }
        return null;
    }

 
    public static String getSpaceState (String spaceName) {
        Space space = getSpace(spaceName);
        return space.getSpaceState().name();
    }


    public static String setCachePolicy (Object spaceDef, String policy) {
        if (spaceDef instanceof com.tibco.as.space.SpaceDef) {
            String oldValue = ((com.tibco.as.space.SpaceDef)spaceDef).getCachePolicy().name();
            CachePolicy newValue = SpaceDef.CachePolicy.valueOf(policy);
            ((com.tibco.as.space.SpaceDef)spaceDef).setCachePolicy(newValue);
            return oldValue;
        }
        return null;
    }


    public static long setCapacity (Object spaceDef, long capacity) {
        if (spaceDef instanceof com.tibco.as.space.SpaceDef) {
            long oldValue = ((com.tibco.as.space.SpaceDef)spaceDef).getCapacity();
            ((com.tibco.as.space.SpaceDef)spaceDef).setCapacity(capacity);
            return oldValue;
        }
        return 0L;
    }


    public static int setReplicationCount (Object spaceDef, int replication) {
        if (spaceDef instanceof com.tibco.as.space.SpaceDef) {
            int oldValue = ((com.tibco.as.space.SpaceDef)spaceDef).getReplicationCount();
            ((com.tibco.as.space.SpaceDef)spaceDef).setReplicationCount(replication);
            return oldValue;
        }
        return 0;
    }


    public static String setReplicationPolicy (Object spaceDef, String policy) {
        if (spaceDef instanceof com.tibco.as.space.SpaceDef) {
            String oldValue = ((com.tibco.as.space.SpaceDef)spaceDef).getReplicationPolicy().name();
            ReplicationPolicy newValue = ReplicationPolicy.valueOf(policy);
            ((com.tibco.as.space.SpaceDef)spaceDef).setReplicationPolicy(newValue);
            return oldValue;
        }
        return null;
    }


    public static String setPersistenceType (Object spaceDef, String persistenceType) {
        if (spaceDef instanceof com.tibco.as.space.SpaceDef) {
            String oldValue = ((com.tibco.as.space.SpaceDef)spaceDef).getPersistenceType().name();
            PersistenceType newValue = PersistenceType.valueOf(persistenceType.replaceAll("SHARED", "SHARE"));
            ((com.tibco.as.space.SpaceDef)spaceDef).setPersistenceType(newValue);
            return oldValue;
        }
        return null;
    }


    public static String setPersistencePolicy (Object spaceDef, String policy) {
        if (spaceDef instanceof com.tibco.as.space.SpaceDef) {
            String oldValue = ((com.tibco.as.space.SpaceDef)spaceDef).getPersistencePolicy().name();
            PersistencePolicy newValue = PersistencePolicy.valueOf(policy);
            ((com.tibco.as.space.SpaceDef)spaceDef).setPersistencePolicy(newValue);
            return oldValue;
        }
        return null;
    }


    public static Object invoke (String spaceName, Object keyTuple, String codeName, Object contextTuple, Object... varargs) {
        try {
            InvokeOptions invokeOptions = varargs == null || varargs.length == 0 ? DEFAULT_INVOKE_OPTIONS : (InvokeOptions) varargs[0];
            Space space = getSpace(spaceName);
            Tuple ctx = Tuple.create();
            ctx.putAll((Tuple)contextTuple);
            ctx.put(InvocableAdapter.INVOCABLE_CODE_NAME, codeName);
            boolean isRuleFunction = isRuleFunction(codeName);
            if (isRuleFunction)
                ctx.put(InvocableAdapter.INVOCABLE_CODE_TYPE, InvocableAdapter.RULE_FUNCTION);
            else
                ctx.put(InvocableAdapter.INVOCABLE_CODE_TYPE, InvocableAdapter.JAVA_CLASS);

            invokeOptions.setContext(ctx);
            invokeOptions.setInvocable(InvocableAdapter.class.getName());
            return space.invoke((Tuple)keyTuple, invokeOptions);
        }
        catch (ASException ase) {
            throw new RuntimeException(ase);
        }
    }


    public static Object invokeLeeches(String spaceName, String codeName, Object contextTuple, Object... varargs) {
        try {
            InvokeOptions invokeOptions = varargs == null || varargs.length == 0 ? DEFAULT_INVOKE_OPTIONS : (InvokeOptions) varargs[0];
            Space space = getSpace(spaceName);
            Tuple ctx = Tuple.create();
            ctx.putAll((Tuple) contextTuple);
            ctx.put(InvocableAdapter.INVOCABLE_CODE_NAME, codeName);
            boolean isRuleFunction = isRuleFunction(codeName);
            if (isRuleFunction)
                ctx.put(InvocableAdapter.INVOCABLE_CODE_TYPE, InvocableAdapter.RULE_FUNCTION);
            else
                ctx.put(InvocableAdapter.INVOCABLE_CODE_TYPE, InvocableAdapter.JAVA_CLASS);

            invokeOptions.setInvocable(InvocableAdapter.class.getName());
            invokeOptions.setContext(ctx);
            return space.invokeLeeches(invokeOptions);
        } catch (ASException ase) {
            throw new RuntimeException(ase);
        }
    }


    public static void invokeMember (String spaceName, Object member, String codeName, Object contextTuple, Object ... varargs) {
        if (!(member instanceof Member)) {
            return;
        }
        try {
            Member mem = (Member)member;
            InvokeOptions invokeOptions = varargs == null || varargs.length == 0 ? DEFAULT_INVOKE_OPTIONS : (InvokeOptions) varargs[0];
            Space space = getSpace(spaceName);
            //invokeOptions.setInvocationTargetLeech();
            Tuple ctx = Tuple.create();
            ctx.putAll((Tuple) contextTuple);
            ctx.put(InvocableAdapter.INVOCABLE_CODE_NAME, codeName);
            boolean isRuleFunction = isRuleFunction(codeName);
            if (isRuleFunction)
                ctx.put(InvocableAdapter.INVOCABLE_CODE_TYPE, InvocableAdapter.RULE_FUNCTION);
            else
                ctx.put(InvocableAdapter.INVOCABLE_CODE_TYPE, InvocableAdapter.JAVA_CLASS);

            invokeOptions.setContext(ctx);
            invokeOptions.setInvocable(InvocableAdapter.class.getName());
            space.invokeMember(mem, invokeOptions);
        } catch (ASException ase) {
            throw new RuntimeException(ase);
        }
    }


    public static Object invokeMembers (Object arg0, Class arg1, Object arg2) {
        throw new UnsupportedOperationException("Not supported yet.");
        // Suresh TODO : We need to create Catalog functions for General
        // Collection classes such as ArrayList, SkipList, Maps, etc which allow
        // add remove, then we can do this very easily.
    }

 
    public static Object invokeRemoteMembers (java.lang.Class arg0, Object arg1) {
        throw new UnsupportedOperationException("Not supported yet.");
        // Suresh TODO : We need to create Catalog functions for General
        // Collection classes such as ArrayList, SkipList, Maps, etc which allow
        // add remove, then we can do this very easily.
    }

  
    public static Object invokeSeeders (java.lang.Class arg0, Object arg1) {
        throw new UnsupportedOperationException("Not supported yet.");
        // Suresh TODO : We need to create Catalog functions for General
        // Collection classes such as ArrayList, SkipList, Maps, etc which allow
        // add remove, then we can do this very easily.
    }


    public static boolean isReady (String spaceName) {
        Space space = getSpace(spaceName);
        try {
            return space.isReady();
        } catch (ASException e) {
            throw new RuntimeException(e);
        }
    }


    public static void load (String spaceName, Object tuple) {
        try {
            Space space = getSpace(spaceName);
            space.load((Tuple)tuple);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return;
    }


    public static Object loadAll (String spaceName, java.util.Collection tuples) {
        try {
            Space space = getSpace(spaceName);
            return space.loadAll(tuples);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static Object lock (String spaceName, Object tuple, Object ... varargs) {
        LockOptions lockOptions = varargs.length == 0 ? LockOptions.create() : (LockOptions) varargs[0];
        try {
            Space space = getSpace(spaceName);
            return space.lock((Tuple)tuple, lockOptions);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


    public static Object lockAll (String spaceName, Object tuples, Object ... varargs) {
        LockOptions lockOptions = varargs.length == 0 ? LockOptions.create() : (LockOptions) varargs[0];
        try {
            Space space = getSpace(spaceName);
            return space.lockAll((Collection<Tuple>) tuples, lockOptions);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


    public static Object put (String spaceName, Object tuple, Object ... varargs) {
        PutOptions putOptions = varargs.length == 0 ? DEFAULT_PUT_OPTIONS : (PutOptions) varargs[0];
        try {
            Space space = getSpace(spaceName);
            return space.put((Tuple)tuple, putOptions);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


    public static Object putAll (String spaceName, Object tuples, Object ... varargs) {
        PutOptions putOptions = varargs.length == 0 ? DEFAULT_PUT_OPTIONS : (PutOptions) varargs[0];
        try {
            Space space = getSpace(spaceName);
            return space.putAll((Collection<Tuple>) tuples, putOptions);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


    public static void setDistributionRole (String spaceName, String role) {
        Member.DistributionRole distRole = Enum.valueOf(Member.DistributionRole.class, role);
        Space space = getSpace(spaceName);
        try {
            space.setDistributionRole(distRole);
        } catch (ASException e) {
            throw new RuntimeException(e);
        }
        return;
    }


    public static Object setPersister (String spaceName, Object persister) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    public static long size (String spaceName) {
        Space space = getSpace(spaceName);
        try {
            return space.size();
        } catch (ASException e) {
            throw new RuntimeException(e);
        }
    }


    public static void stopPersister (com.tibco.as.space.persistence.Persister arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    public static Object take (String spaceName, Object tuple, Object... varargs) {
        TakeOptions takeOptions = varargs.length == 0 ? TakeOptions.create() : (TakeOptions) varargs[0];
        try {
            Space space = getSpace(spaceName);
            return space.take((Tuple)tuple, takeOptions);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


    public static Object takeAll (String spaceName, Object tuples, Object... varargs) {
        TakeOptions takeOptions = varargs.length == 0 ? TakeOptions.create() : (TakeOptions) varargs[0];
        try {
            Space space = getSpace(spaceName);
            return space.takeAll((Collection)tuples, takeOptions);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


    public static void unlock (String spaceName, Object keyTuple, Object ... varargs) {
        UnlockOptions opts = (UnlockOptions) (varargs.length > 0 ? varargs[0] : UnlockOptions.create());
        try {
            Space space = getSpace(spaceName);
            space.unlock((Tuple)keyTuple, opts);
        }
        catch (ASException ase) {
            throw new RuntimeException(ase);
        }
        return;
    }


    public static Object unlockAll (String spaceName, Object tuples, Object ... varargs) {
        UnlockOptions opts = (UnlockOptions) (varargs.length > 0 ? varargs[0] : UnlockOptions.create());
        Space space = getSpace(spaceName);
        return space.unlockAll((Collection) tuples, opts);
    }


    public static boolean waitForReady (String spaceName, long time2Wait) {
        try {
            Space space = getSpace(spaceName);
            return space.waitForReady(time2Wait);
        }
        catch(ASException ase) {
            throw new RuntimeException(ase);
        }
    }

    static Space getSpace(String spaceName) throws RuntimeException {
        Space space = spaces.get(spaceName);
        if (space == null) {
            String[] tokens = spaceName.split("\\.");
            space = (Space) MetaspaceHelper.getSpace(tokens[0], tokens[1], false);
            addSpace(tokens[0], tokens[1], space);
        }
        return space;
    }

    static void addSpace(String metaspace, String spaceName, Space space) {
        spaces.putIfAbsent(String.format("%s.%s", metaspace, spaceName), space);
    }

    static void removeSpace(String fqnspaceName) {
        spaces.remove(fqnspaceName);
    }

    static PutOptions buildPutOptions(Object[] varargs) {
        if (varargs.length == 0) return DEFAULT_PUT_OPTIONS;
        if (varargs[0] instanceof PutOptions) return (PutOptions)varargs[0];
        PutOptions putOpts = PutOptions.create();
        if (varargs.length >= 1) putOpts.setForget((Boolean)varargs[0]);
        if (varargs.length >= 2) putOpts.setLock((Boolean)varargs[1]);
        if (varargs.length >= 3) putOpts.setLockWait((Long)varargs[2]);
        if (varargs.length >= 4) putOpts.setTTL((Long)varargs[3]);
        if (varargs.length >= 5) putOpts.setUnlock((Boolean)varargs[4]);
        return putOpts;
    }

    static TakeOptions buildTakeOptions(Object[] varargs) {
        if (varargs.length == 0) return DEFAULT_TAKE_OPTIONS;
        if (varargs[0] instanceof TakeOptions) return (TakeOptions)varargs[0];
        TakeOptions takeOpts = TakeOptions.create();
        if (varargs.length >= 1) takeOpts.setForget((Boolean)varargs[0]);
        if (varargs.length >= 2) takeOpts.setLock((Boolean)varargs[1]);
        if (varargs.length >= 3) takeOpts.setLockWait((Long)varargs[2]);
        if (varargs.length >= 4) takeOpts.setUnlock((Boolean)varargs[3]);
        return takeOpts;
    }

    private static boolean isRuleFunction(String codeName){
        RuleSession currentRuleSession = RuleSessionManager.getCurrentRuleSession();
        RuleServiceProvider rsp = currentRuleSession.getRuleServiceProvider();
        RuleFunction ruleFunctionInstance = ((BEClassLoader)rsp.getTypeManager()).getRuleFunctionInstance(codeName);
        boolean isRuleFunction = false;
        if (ruleFunctionInstance != null) {
            isRuleFunction = true;
        }
        return isRuleFunction;
    }
}