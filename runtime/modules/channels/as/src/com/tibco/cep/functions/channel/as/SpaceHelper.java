package com.tibco.cep.functions.channel.as;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.tibco.cep.functions.channel.as.ASSpaceHelper;
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

@BEPackage(
        catalog = "ActiveSpaces",
        category = "Metaspace.Space",
        synopsis = "Space functions")
public class SpaceHelper {

    @com.tibco.be.model.functions.BEFunction(
            name = "close",
            synopsis = "Close the Space. The space name specified must be prefixed with the Metaspace name as ms.S1",
            signature = "void close (String spaceName)",
            params = {
                    @FunctionParamDescriptor(name = "spaceName", type = "String", desc = "The space name specified must be prefixed with the Metaspace name. Example ms.S1")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Close the Space. The space name specified must be prefixed with the Metaspace name as ms.S1",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static void close (String spaceName) {
        ASSpaceHelper.close(spaceName);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "closeAll",
            synopsis = "Close the Space and release all resources. The space name specified must be prefixed with the Metaspace name as ms.S1",
            signature = "void closeAll (String spaceName)",
            params = {
                    @FunctionParamDescriptor(name = "spaceName", type = "String", desc = "The space name specified must be prefixed with the Metaspace name. Example ms.S1")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Close the Space and release all resources. The space name specified must be prefixed with the Metaspace name as ms.S1",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static void closeAll (String spaceName) {
    	ASSpaceHelper.closeAll(spaceName);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "compareAndPut",
            synopsis = "Compare the tuple in the space with the tuple passed as the compareTuple. If comparison result to True, set the tuple in the space with the specified tuple. " +
                    " Return the tuple that is currently in the space. The varargs can be PutOptions object, or individually specified value",
            signature = "Object compareAndPut (String spaceName, Tuple compareTuple, Tuple updateTuple, Object... varargs)",
            params = {
                    @FunctionParamDescriptor(name = "spaceName", type = "String", desc = "The space name specified must be prefixed with the Metaspace name. Example ms.S1"),
                    @FunctionParamDescriptor(name = "compareTuple", type = "Object", desc = "The tuple to be compared "),
                    @FunctionParamDescriptor(name = "updateTuple", type = "Object", desc = "The tuple to be replaced if the comparison is true."),
                    @FunctionParamDescriptor(name = "varargs", type = "Object", desc = "Optional PutOptions specified")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "New Tuple on success, otherwise a Tuple containing the current values from the entry."),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Compare the tuple in the space with the tuple passed as the compareTuple. If comparison result to True, set the tuple in the space with the specified tuple. " +
                    " Return the tuple that is currently in the space.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object compareAndPut (String spaceName, Object compareTuple, Object updateTuple, Object ... varargs) {
    	return ASSpaceHelper.compareAndPut(spaceName, compareTuple, updateTuple, varargs);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "compareAndPutAll",
            synopsis = "Batch compareAndPut operation.",
            signature = "Object compareAndPutAll (String spaceName, Object compareTuples, Object updateTuples, Object ... varags)",
            params = {
                    @FunctionParamDescriptor(name = "spaceName", type = "String", desc = "The space name specified must be prefixed with the Metaspace name. Example ms.S1"),
                    @FunctionParamDescriptor(name = "compareTuples", type = "Object", desc = "The tuple to be compared "),
                    @FunctionParamDescriptor(name = "updateTuples", type = "Object", desc = "The tuple to be replaced if the comparison is true."),
                    @FunctionParamDescriptor(name = "varargs", type = "Object", desc = "Optional PutOptions specified")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "List containing the result of each compareAndPut (current tuple values). If comparison is succeeded, this list should match the update-list. Otherwise, the returned list could be used for next compareAndPutAll call's compare-list."),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Batch compareAndPut operation.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object compareAndPutAll (String spaceName, Object compareTuples, Object updateTuples, Object... varargs) {
    	return ASSpaceHelper.compareAndPutAll(spaceName, compareTuples, updateTuples, varargs);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "compareAndTake",
            synopsis = "Compare the entry with the entry passed; and if equals, then take (atomic remove) it.",
            signature = "Object compareAndTake (String spaceName, Object compareTuple, Object ... varargs)",
            params = {
                    @FunctionParamDescriptor(name = "spaceName", type = "String", desc = "The space name specified must be prefixed with the Metaspace name. Example ms.S1"),
                    @FunctionParamDescriptor(name = "compareTuple", type = "Object", desc = "The tuple whose fields have to be compared, and take if successful "),
                    @FunctionParamDescriptor(name = "varargs", type = "Object", desc = "Optional TakeOptions specified")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "The Tuple that was taken."),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Compare the entry with the entry passed, and if equals, then take (atomic remove) it.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object compareAndTake (String spaceName, Object tuple2Compare, Object ... varargs) {
    	return ASSpaceHelper.compareAndTake(spaceName, tuple2Compare, varargs);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "compareAndTakeAll",
            synopsis = "Batch compareAndTake operation.",
            signature = "Object compareAndTakeAll (String spaceName, Object compareTuples, Object ... varargs)",
            params = {
                    @FunctionParamDescriptor(name = "spaceName", type = "String", desc = "The space name specified must be prefixed with the Metaspace name. Example ms.S1"),
                    @FunctionParamDescriptor(name = "compareTuples", type = "Object", desc = "The Collection of tuples whose fields have to be compared, and taken if successful"),
                    @FunctionParamDescriptor(name = "varargs", type = "Object", desc = "Optional TakeOptions specified")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "List of Tuples where comparison/take failed. If the returned list is empty, full collection was successfully matched."),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Batch compareAndTake operation.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object compareAndTakeAll (String spaceName, Object compareTuples, Object... varargs) {
    	return ASSpaceHelper.compareAndTakeAll(spaceName, compareTuples, varargs);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "get",
            synopsis = "Get the value from the Space for the specified key.",
            signature = "Object get (String spaceName, Object keyTuple)",
            params = {
                    @FunctionParamDescriptor(name = "spaceName", type = "String", desc = "The space name specified must be prefixed with the Metaspace name. Example ms.S1"),
                    @FunctionParamDescriptor(name = "keyTuple", type = "Object", desc = "Key passed in a Tuple format. ")
            },
            freturn = @FunctionParamDescriptor(name = "valueTuple", type = "Object", desc = "The value as a Tuple"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Get the value from the Space for the specified key.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object get (String spaceName, Object keyTuple) {
    	return ASSpaceHelper.get(spaceName, keyTuple);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "getAll",
            synopsis = "Batch get operation.",
            signature = "Object getAll (String spaceName, Object tupleList)",
            params = {
                    @FunctionParamDescriptor(name = "spaceName", type = "String", desc = "The space name specified must be prefixed with the Metaspace name. Example ms.S1"),
                    @FunctionParamDescriptor(name = "tupleList", type = "Object", desc = "Collection of tuple keys")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "SpaceResultList containing the result of each get"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Batch get operation.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object getAll (String spaceName, Object keyTuples, Object ... varargs) {
    	return ASSpaceHelper.getAll(spaceName, keyTuples, varargs);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "getMetaspaceName",
            synopsis = "Retrieve the name of the Metaspace that the Space belongs to.",
            signature = "String getMetaspaceName (String spaceName)",
            params = {
                    @FunctionParamDescriptor(name = "spaceName", type = "String", desc = "The space name specified must be prefixed with the Metaspace name. Example ms.S1")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "String", desc = "The name of the Metaspace that the Space belongs to"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Retrieve the name of the Metaspace that the Space belongs to.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static String getMetaspaceName (String spaceName) {
    	return ASSpaceHelper.getMetaspaceName(spaceName);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "getSpaceDef",
            synopsis = "Retrieve the attributes used to create the Space. If requested space is not found, then a default space definition will be returned.",
            signature = "Object getSpaceDef (String spaceName)",
            params = {
                    @FunctionParamDescriptor(name = "spaceName", type = "String", desc = "The space name specified must be prefixed with the Metaspace name. Example ms.S1")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "SpaceDef", desc = "The attributes used to create the Space "),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Retrieve the attributes used to create the Space. If requested space is not found, then a default space definition will be returned.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object getSpaceDef (String spaceName) {
    	return ASSpaceHelper.getSpaceDef(spaceName);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "getCachePolicy",
            synopsis = "Gets cache policy for provided space definition.",
            signature = "String getCachePolicy (Object spaceDef)",
            params = {
                    @FunctionParamDescriptor(name = "spaceDef", type = "Object", desc = "Space definition ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "String", desc = "Cache policy can be READ_THROUGH, or READ_WRITE_THROUGH"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Gets cache policy for provided space definition",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static String getCachePolicy (Object spaceDef) {
    	return ASSpaceHelper.getCachePolicy(spaceDef);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "getCapacity",
            synopsis = "Gets cache capacity for provided space definition.",
            signature = "long getCapacity (Object spaceDef)",
            params = {
                    @FunctionParamDescriptor(name = "spaceDef", type = "Object", desc = "Space definition ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "long", desc = "Space cache capacity"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Gets cache capacity for provided space definition",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static long getCapacity (Object spaceDef) {
    	return ASSpaceHelper.getCapacity(spaceDef);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "getReplicationCount",
            synopsis = "Gets cache replication count for provided space definition.",
            signature = "int getReplicationCount (Object spaceDef)",
            params = {
                    @FunctionParamDescriptor(name = "spaceDef", type = "Object", desc = "Space definition ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "int", desc = "Space replication count"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Gets replication count for provided space definition",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static int getReplicationCount (Object spaceDef) {
    	return ASSpaceHelper.getReplicationCount(spaceDef);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "getReplicationPolicy",
            synopsis = "Gets replication policy for provided space definition.",
            signature = "String getReplicationPolicy (Object spaceDef)",
            params = {
                    @FunctionParamDescriptor(name = "spaceDef", type = "Object", desc = "Space definition ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "String", desc = "Space replication policy can be SYNC, or ASYNC "),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Gets replication policy for provided space definition",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static String getReplicationPolicy (Object spaceDef) {
    	return ASSpaceHelper.getReplicationPolicy(spaceDef);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "getPersistenceType",
            synopsis = "Gets persistence type for provided space definition.",
            signature = "String getPersistenceType (Object spaceDef)",
            params = {
                    @FunctionParamDescriptor(name = "spaceDef", type = "Object", desc = "Space definition ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "String", desc = "Space persistence type can be NONE, SHARE_NOTHING, or SHARE_ALL "),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Gets persistence type for provided space definition",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static String getPersistenceType (Object spaceDef) {
    	return ASSpaceHelper.getPersistenceType(spaceDef);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "getPersistencePolicy",
            synopsis = "Gets persistence policy for provided space definition.",
            signature = "String getPersistencePolicy (Object spaceDef)",
            params = {
                    @FunctionParamDescriptor(name = "spaceDef", type = "Object", desc = "Space definition ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "String", desc = "Space persistence policy can be NONE, SYNC, or ASYNC "),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Gets persistence policy for provided space definition",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static String getPersistencePolicy (Object spaceDef) {
    	return ASSpaceHelper.getPersistencePolicy(spaceDef);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "getSpaceState",
            synopsis = "Return state of space",
            signature = "String getSpaceState (String spaceName)",
            params = {
                    @FunctionParamDescriptor(name = "spaceName", type = "String", desc = "The space name specified must be prefixed with the Metaspace name. Example ms.S1")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "String", desc = "SpaceDef.SpaceState, an Enum for the current state of the Space"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Return state of space",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static String getSpaceState (String spaceName) {
    	return ASSpaceHelper.getSpaceState(spaceName);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "setCachePolicy",
            synopsis = "Sets cache policy for provided space definition.",
            signature = "String setCachePolicy (Object spaceDef, String policy)",
            params = {
                    @FunctionParamDescriptor(name = "spaceDef", type = "Object", desc = "Space definition "),
                    @FunctionParamDescriptor(name = "policy", type = "String", desc = "Desired policy as READ_THROUGH, or READ_WRITE_THROUGH")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "String", desc = "Previous cache policy"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Sets cache policy for provided space definition",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static String setCachePolicy (Object spaceDef, String policy) {
    	return ASSpaceHelper.setCachePolicy(spaceDef, policy);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "setCapacity",
            synopsis = "Sets cache capacity for provided space definition.",
            signature = "long setCapacity (Object spaceDef, long capacity)",
            params = {
                    @FunctionParamDescriptor(name = "spaceDef", type = "Object", desc = "Space definition "),
                    @FunctionParamDescriptor(name = "capacity", type = "long", desc = "Desired capacity ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "long", desc = "Previous space cache capacity"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Sets cache capacity for provided space definition",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static long setCapacity (Object spaceDef, long capacity) {
    	return ASSpaceHelper.setCapacity(spaceDef, capacity);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "setReplicationCount",
            synopsis = "Sets cache replication count for provided space definition.",
            signature = "int setReplicationCount (Object spaceDef, int replication)",
            params = {
                    @FunctionParamDescriptor(name = "spaceDef", type = "Object", desc = "Space definition "),
                    @FunctionParamDescriptor(name = "replication", type = "int", desc = "Desired replication count ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "int", desc = "Previous space replication count"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Sets replication count for provided space definition",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static int setReplicationCount (Object spaceDef, int replication) {
    	return ASSpaceHelper.setReplicationCount(spaceDef, replication);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "setReplicationPolicy",
            synopsis = "Sets replication policy for provided space definition.",
            signature = "int setReplicationPolicy (Object spaceDef, String policy)",
            params = {
                    @FunctionParamDescriptor(name = "spaceDef", type = "Object", desc = "Space definition "),
                    @FunctionParamDescriptor(name = "policy", type = "String", desc = "Desired space replication policy as SYNC, or ASYNC ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "String", desc = "Previous space replication policy"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Sets replication policy for provided space definition",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static String setReplicationPolicy (Object spaceDef, String policy) {
    	return ASSpaceHelper.setReplicationPolicy(spaceDef, policy);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "setPersistenceType",
            synopsis = "Sets persistence type for provided space definition.",
            signature = "String setPersistenceType (Object spaceDef, String persistenceType)",
            params = {
                    @FunctionParamDescriptor(name = "spaceDef", type = "Object", desc = "Space definition "),
                    @FunctionParamDescriptor(name = "persistenceType", type = "String", desc = "Desired persistence type as NONE, SHARE_NOTHING, or SHARE_ALL ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "String", desc = "Previous space persistence type "),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Sets persistence type for provided space definition",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static String setPersistenceType (Object spaceDef, String persistenceType) {
    	return ASSpaceHelper.setPersistenceType(spaceDef, persistenceType);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "setPersistencePolicy",
            synopsis = "Sets persistence policy for provided space definition.",
            signature = "String setPersistencePolicy (Object spaceDef, String policy)",
            params = {
                    @FunctionParamDescriptor(name = "spaceDef", type = "Object", desc = "Space definition "),
                    @FunctionParamDescriptor(name = "policy", type = "String", desc = "Desired space persistence policy as NONE, SYNC, or ASYN ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "String", desc = "Previous space persistence policy"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Sets persistence policy for provided space definition",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static String setPersistencePolicy (Object spaceDef, String policy) {
    	return ASSpaceHelper.setPersistencePolicy(spaceDef, policy);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "invoke",
            synopsis = "Causes execution of the invoke method from the specified class on the Member of the Space where the entry associated \nwith the key specified in tuple is stored. If an entry for the key does not exist in the Space, the invoke\nmethod is executed on the Member of the Space where the entry would have been stored. ",
            signature = "void invoke (String spaceName, Object keyTuple, String codeName, Object contextTuple, Object... varargs)",
            params = {
                    @FunctionParamDescriptor(name = "spaceName", type = "String", desc = "The space name specified must be prefixed with the Metaspace name. Example ms.S1"),
                    @FunctionParamDescriptor(name = "keyTuple", type = "Object", desc = "Key passed in a Tuple format "),
                    @FunctionParamDescriptor(name = "codeName", type = "String", desc = "Java className or BE RuleFunction name. Default is BE RuleFunction Name "),
                    @FunctionParamDescriptor(name = "contextTuple", type = "Object", desc = "Specify context Tuple if available, otherwise null is ok "),
                    @FunctionParamDescriptor(name = "varargs", type = "Object[]", desc = "Optional varargs of type InvokeOptions ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "Null if the InvokeOptions has set the callback handler, otherwise the InvokeResult"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Causes execution of the invoke method from the specified class on the Member of the Space where the entry associated \nwith the key specified in tuple is stored. If an entry for the key does not exist in the Space, the invoke\nmethod is executed on the Member of the Space where the entry would have been stored.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object invoke (String spaceName, Object keyTuple, String codeName, Object contextTuple, Object... varargs) {
    	return ASSpaceHelper.invoke(spaceName, keyTuple, codeName, contextTuple, varargs);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "invokeLeeches",
            synopsis = "Invokes in parallel the appropriate invoke method of the specified class on all of the members of the Space which have a distribution role of leech. ",
            signature = "Object invokeLeeches(String spaceName, String codeName, Object contextTuple, Object... varargs)",
            params = {
                     @FunctionParamDescriptor(name = "spaceName", type = "String", desc = "The space name specified must be prefixed with the Metaspace name. Example ms.S1"),
                     @FunctionParamDescriptor(name = "codeName", type = "String", desc = "Java className or BE RuleFunction name. Default is BE RuleFunction Name "),
                     @FunctionParamDescriptor(name = "contextTuple", type = "Object", desc = "Specify context Tuple if available, otherwise null is ok "),
                     @FunctionParamDescriptor(name = "varargs", type = "Object[]", desc = " Optional varargs of type InvokeOptions ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "InvokeResultList", desc = "Result of the invocation"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Invokes in parallel the appropriate invoke method of the specified class on all of the members of the Space which have a distribution role of leech. ",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object invokeLeeches(String spaceName, String codeName, Object contextTuple, Object... varargs) {
    	return ASSpaceHelper.invokeLeeches(spaceName, codeName, contextTuple, varargs);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "invokeMember",
            synopsis = "Causes execution of the invoke method of the specified class on the specific Member of the Space passed as an argument. ",
            signature = "void invokeMember (String spaceName, Object member, String codeName, Object contextTuple, Object... varargs)",
            params = {
                    @FunctionParamDescriptor(name = "spaceName", type = "String", desc = "The space name specified must be prefixed with the Metaspace name. Example ms.S1"),
                    @FunctionParamDescriptor(name = "member", type = "Object", desc = "com.tibco.as.space.Member to execute the invoke method on. "),
                    @FunctionParamDescriptor(name = "codeName", type = "String", desc = " Java className or BE RuleFunction name. Default is BE RuleFunction Name "),
                    @FunctionParamDescriptor(name = "contextTuple", type = "Object", desc = "Specify context Tuple if available, otherwise null is ok "),
                    @FunctionParamDescriptor(name = "varargs", type = "Object[]", desc = " Optional varargs of type InvokeOptions ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Causes execution of the invoke method of the specified class on the specific Member of the Space passed as an argument.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static void invokeMember (String spaceName, Object member, String codeName, Object contextTuple, Object ... varargs) {
    	ASSpaceHelper.invokeMember(spaceName, member, codeName, contextTuple, varargs);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "invokeMembers",
            synopsis = "Causes execution of the invoke method of the specified class on all the members.",
			enabled = @Enabled(value=false),
            signature = "Object invokeMembers (Object arg0, Class arg1, Object arg2)",
            params = {
                    @FunctionParamDescriptor(name = "arg0", type = "Collection", desc = " "),
                    @FunctionParamDescriptor(name = "arg1", type = "java.lang.Class", desc = " "),
                    @FunctionParamDescriptor(name = "arg2", type = "Object", desc = " ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "com.tibco.as.space.remote.InvokeResultList", desc = ""),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Causes execution of the invoke method of the specified class on all the members.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object invokeMembers (Object arg0, Class arg1, Object arg2) {
    	return ASSpaceHelper.invokeMembers(arg0, arg1, arg2);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "invokeRemoteMembers",
            synopsis = "Causes execution of the invoke method of the specified class on all remote members.",
            signature = "Object invokeRemoteMembers (Class arg0, Object arg1)",
            params = {
                    @FunctionParamDescriptor(name = "arg0", type = "java.lang.Class", desc = " "),
                    @FunctionParamDescriptor(name = "arg1", type = "Object", desc = " ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "com.tibco.as.space.remote.InvokeResultList", desc = ""),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Causes execution of the invoke method of the specified class on all remote members.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object invokeRemoteMembers (java.lang.Class arg0, Object arg1) {
    	return ASSpaceHelper.invokeRemoteMembers(arg0, arg1);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "invokeSeeders",
            synopsis = "Causes execution of the invoke method of the specified class on all the seeders.",
            signature = "Object invokeSeeders (Class arg0, Object arg1)",
            params = {
                    @FunctionParamDescriptor(name = "arg0", type = "Class", desc = " "),
                    @FunctionParamDescriptor(name = "arg1", type = "Object", desc = " ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "com.tibco.as.space.remote.InvokeResultList", desc = ""),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Causes execution of the invoke method of the specified class on all the seeders.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object invokeSeeders (java.lang.Class arg0, Object arg1) {
    	return ASSpaceHelper.invokeSeeders(arg0, arg1);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "isReady",
            synopsis = "Check whether the Space is ready for operations. ",
            signature = "boolean isReady (String spaceName)",
            params = {
                    @FunctionParamDescriptor(name = "spaceName", type = "String", desc = "The space name specified must be prefixed with the Metaspace name. Example ms.S1")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "boolean", desc = "True, indicates the Space is ready for operations. False is returned otherwise. "),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Check whether the Space is ready for operations. ",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static boolean isReady (String spaceName) {
    	return ASSpaceHelper.isReady(spaceName);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "load",
            synopsis = "Load behaves exactly as a put, except that it does not trigger 'Persister.onWrite(com.tibco.as.space.persistence.WriteAction)' method.",
            signature = "void load (String space, Object tuple)",
            params = {
                    @FunctionParamDescriptor(name = "spaceName", type = "String", desc = "The space name specified must be prefixed with the Metaspace name. Example ms.S1"),
                    @FunctionParamDescriptor(name = "tuple", type = "Object", desc = "A Tuple containing values to be stored into the Space ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Load behaves exactly as a put, except that it does not trigger 'Persister.onWrite(com.tibco.as.space.persistence.WriteAction)' method.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static void load (String spaceName, Object tuple) {
    	ASSpaceHelper.load(spaceName, tuple);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "loadAll",
            synopsis = "Batch load operation.",
            signature = "Object loadAll (String spaceName, java.util.Collection tuples)",
            params = {
                    @FunctionParamDescriptor(name = "spaceName", type = "String", desc = "The space name specified must be prefixed with the Metaspace name. Example ms.S1"),
                    @FunctionParamDescriptor(name = "tuples", type = "java.util.Collection", desc = " ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = ""),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Batch load operation.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object loadAll (String spaceName, java.util.Collection tuples) {
    	return ASSpaceHelper.loadAll(spaceName, tuples);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "lock",
            synopsis = "Locks the entry stored in the Space which has the key specified in the given Tuple.",
            signature = "Object lock (String spaceName, Object tuple, Object ... varargs)",
            params = {
                    @FunctionParamDescriptor(name = "spaceName", type = "String", desc = "The space name specified must be prefixed with the Metaspace name. Example ms.S1"),
                    @FunctionParamDescriptor(name = "tuple", type = "Object", desc = "Tuple containing the key to search for. "),
                    @FunctionParamDescriptor(name = "varargs", type = "Object", desc = "Optional LockOptions specifies custom lock wait/forget choices. ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "On success, a Tuple containing the same values as the entry that was just locked is returned. On failure, null is returned. "),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Locks the entry stored in the Space which has the key specified in the given Tuple.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object lock (String spaceName, Object tuple, Object ... varargs) {
    	return ASSpaceHelper.lock(spaceName, tuple, varargs);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "lockAll",
            synopsis = "Batch lock operation.",
            signature = "Object lockAll (String spaceName, Object tuples, Object... varargs)",
            params = {
                    @FunctionParamDescriptor(name = "spaceName", type = "String", desc = "The space name specified must be prefixed with the Metaspace name. Example ms.S1"),
                    @FunctionParamDescriptor(name = "tuples", type = "Object", desc = "Collection of tuples to be loaded"),
                    @FunctionParamDescriptor(name = "varargs", type = "Object", desc = "Optional LockOptions specifies custom lock wait/forget choices. ")
            },
            freturn = @FunctionParamDescriptor(name = "spaceResultList", type = "Object", desc = " List containing the result of each lock."),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Batch lock operation.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object lockAll (String spaceName, Object tuples, Object ... varargs) {
    	return ASSpaceHelper.lockAll(spaceName, tuples, varargs);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "put",
            synopsis = "Stores a Tuple into the Space and returns any entry already stored in the Space with the same key. This version of put allows you to specify a lock wait value, lock or unlock value, and a forget value. ",
            signature = "Object put(String spaceName, Object tuple, Object... varargs)",
            params = {
                    @FunctionParamDescriptor(name = "spaceName", type = "String", desc = "The space name specified must be prefixed with the Metaspace name. Example ms.S1"),
                    @FunctionParamDescriptor(name = "tuple", type = "Object", desc = "The Tuple to set. "),
                    @FunctionParamDescriptor(name = "varargs", type = "Object", desc = "Optional LockOptions specifies custom lock wait/forget choices ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "A Tuple containing the values of the previously existing entry with the same key, if one exists. "),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Stores a Tuple into the Space and returns any entry already stored in the Space with the same key. This version of put allows you to specify a lock wait value, lock or unlock value, and a forget value. ",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object put (String spaceName, Object tuple, Object ... varargs) {
    	return ASSpaceHelper.put(spaceName, tuple, varargs);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "putAll",
            synopsis = "Batch put operation. ",
            signature = "Object putAll (String spaceName, Object tuples, Object... varargs)",
            params = {
                    @FunctionParamDescriptor(name = "spaceName", type = "String", desc = "The space name specified must be prefixed with the Metaspace name. Example ms.S1"),
                    @FunctionParamDescriptor(name = "tuples", type = "Object", desc = "Collection of Tuple "),
                    @FunctionParamDescriptor(name = "varargs", type = "Object", desc = "Optional LockOptions specifies custom lock wait/forget choices ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "List containing the result of each put."),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Batch put operation. ",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object putAll (String spaceName, Object tuples, Object ... varargs) {
    	return ASSpaceHelper.putAll(spaceName, tuples, varargs);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "setDistributionRole",
            synopsis = "Sets the distribution role of the calling Member of the Space. ",
            signature = "void setDistributionRole (String spaceName, String role)",
            params = {
                    @FunctionParamDescriptor(name = "spaceName", type = "String", desc = "The space name specified must be prefixed with the Metaspace name. Example ms.S1"),
                    @FunctionParamDescriptor(name = "role", type = "String", desc = " The valid values are LEECH, SEEDER. Case sensitive ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Sets the distribution role of the calling Member of the Space. ",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static void setDistributionRole (String spaceName, String role) {
    	ASSpaceHelper.setDistributionRole(spaceName, role);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "setPersister",
            synopsis = "Registers the given Persister object as a persister for the Space.",
            signature = "Object setPersister (String spaceName, Object persister)",
            params = {
                    @FunctionParamDescriptor(name = "spaceName", type = "String", desc = "The space name specified must be prefixed with the Metaspace name. Example ms.S1"),
                    @FunctionParamDescriptor(name = "persister", type = "Object", desc = "The persister object ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "com.tibco.as.space.persistence.Persister", desc = "The registered Persister object. "),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Registers the given Persister object as a persister for the Space.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object setPersister (String spaceName, Object persister) {
    	return ASSpaceHelper.setPersister(spaceName, persister);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "size",
            synopsis = "Retrieves the number of entries stored in the Space.",
            signature = "long size (String spaceName)",
            params = {
                    @FunctionParamDescriptor(name = "spaceName", type = "String", desc = "The space name specified must be prefixed with the Metaspace name. Example ms.S1")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "long", desc = "The number of entries in the Space which match the specified filter. "),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Retrieves the number of entries stored in the Space.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static long size (String spaceName) {
    	return ASSpaceHelper.size(spaceName);
    }


    @com.tibco.be.model.functions.BEFunction(
            name = "take",
            synopsis = "Returns and atomically removes from the Space the entry (if one exists) whose key field matches the key field of the Tuple provided. This method allows you to specify lock wait, lock/unlock/forget options.",
            signature = "Object take (String spaceName, Object tuple, Object... varargs)",
            params = {
                    @FunctionParamDescriptor(name = "spaceName", type = "String", desc = "The space name specified must be prefixed with the Metaspace name. Example ms.S1"),
                    @FunctionParamDescriptor(name = "tuple", type = "Object", desc = "Tuple to take "),
                    @FunctionParamDescriptor(name = "varargs", type = "Object", desc = "Optional TakeOptions argument ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "Tuple itself"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns and atomically removes from the Space the entry (if one exists) whose key field matches the key field of the Tuple provided. This method allows you to specify lock wait, lock/unlock/forget options.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object take (String spaceName, Object tuple, Object... varargs) {
    	return ASSpaceHelper.take(spaceName, tuple, varargs);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "takeAll",
            synopsis = "Batch take operation. ",
            signature = "Object takeAll (String spaceName, Object tuples, Object... varargs)",
            params = {
                    @FunctionParamDescriptor(name = "spaceName", type = "String", desc = "The space name specified must be prefixed with the Metaspace name. Example ms.S1"),
                    @FunctionParamDescriptor(name = "tuple", type = "Object", desc = "Collection of tuples "),
                    @FunctionParamDescriptor(name = "varargs", type = "Object", desc = "Optional TakeOptions argument ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "List containing the result of each take."),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Batch take operation. ",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object takeAll (String spaceName, Object tuples, Object... varargs) {
    	return ASSpaceHelper.takeAll(spaceName, tuples, varargs);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "unlock",
            synopsis = "Unlocks the entry stored in the Space which has the key specified in the given Tuple. ",
            signature = "void unlock (String spaceName, Object tuple, Object ... varargs)",
            params = {
                    @FunctionParamDescriptor(name = "spaceName", type = "String", desc = "The space name specified must be prefixed with the Metaspace name. Example ms.S1"),
                    @FunctionParamDescriptor(name = "tuple", type = "Object", desc = " The key tuple. Object has to be of type Tuple "),
                    @FunctionParamDescriptor(name = "varargs", type = "Object", desc = "Optional UnlockOptions specifies custom unlock choices ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Unlocks the entry stored in the Space which has the key specified in the given Tuple. ",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static void unlock (String spaceName, Object keyTuple, Object ... varargs) {
    	ASSpaceHelper.unlock(spaceName, keyTuple, varargs);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "unlockAll",
            synopsis = "Batch unlock operation.",
            signature = "Object unlockAll (String spaceName, Object tuples, Object... varargs)",
            params = {
                    @FunctionParamDescriptor(name = "spaceName", type = "String", desc = "The space name specified must be prefixed with the Metaspace name. Example ms.S1"),
                    @FunctionParamDescriptor(name = "tuples", type = "Object", desc = "The key tuples. Object has to be of type Collection "),
                    @FunctionParamDescriptor(name = "varargs", type = "Object", desc = "Optional UnlockOptions specifies custom unlock choices ")
            },
            freturn = @FunctionParamDescriptor(name = "resultList", type = "Object", desc = "Collection of SpaceResult"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Batch unlock operation.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object unlockAll (String spaceName, Object tuples, Object ... varargs) {
    	return ASSpaceHelper.unlockAll(spaceName, tuples, varargs);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "waitForReady",
            synopsis = "Causes the client code to block until the Space is ready for operations or the specified timeout is reached. ",
            signature = "boolean waitForReady (String spaceName, long time2Wait)",
            params = {
                    @FunctionParamDescriptor(name = "spaceName", type = "String", desc = "The space name specified must be prefixed with the Metaspace name. Example ms.S1"),
                    @FunctionParamDescriptor(name = "time2Wait", type = "long", desc = "Timeout in milliseconds. ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "boolean", desc = "True indicates the Space is ready for operations. False is returned otherwise."),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Causes the client code to block until the Space is ready for operations or the specified timeout is reached. ",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static boolean waitForReady (String spaceName, long time2Wait) {
    	return ASSpaceHelper.waitForReady(spaceName, time2Wait);
    }
}