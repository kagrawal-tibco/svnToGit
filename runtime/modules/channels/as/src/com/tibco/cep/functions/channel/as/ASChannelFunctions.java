package com.tibco.cep.functions.channel.as;

import static com.tibco.be.model.functions.FunctionDomain.*;

import com.tibco.be.model.functions.BEMapper;
import com.tibco.as.space.LockOptions;
import com.tibco.as.space.UnlockOptions;
import com.tibco.be.model.functions.FunctionParamDescriptor;

import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.event.SimpleEvent;

/**
 * @.category AS
 * @.synopsis AS Client Functions
 */
@com.tibco.be.model.functions.BEPackage(
        catalog = "ActiveSpaces",
        category = "AS",
        synopsis = "AS Client Functions")
public class ASChannelFunctions {

    static final LockOptions DEFAULT_LOCK_OPTIONS = LockOptions.create();
    static final UnlockOptions DEFAULT_UNLOCK_OPTIONS = UnlockOptions.create();

    @com.tibco.be.model.functions.BEFunction(
        name = "getConsumptionMode",
        synopsis = "Gets the Event consumption mode.",
        signature = "String getConsumptionMode(SimpleEvent event)",
        params = {
            @FunctionParamDescriptor(name = "event", type = "SimpleEvent", desc = "The event being tested")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "String", desc = "The consumption mode if the event is transferred by AS Channel. The range of value is EntryBrowser and EventListener"),
        version = "5.1",
        see = "",
        mapper = @BEMapper(),
        description = "Gets the Event consumption mode.",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static String getConsumptionMode(SimpleEvent event) {
        return ASChannelFunctionsImpl.getConsumptionMode(event);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getEventType",
        synopsis = "Gets the event type.",
        signature = "String getEventType(SimpleEvent event)",
        params = {
            @FunctionParamDescriptor(name = "event", type = "SimpleEvent", desc = "The event being tested")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "String", desc = "The event type of the event if the consumption mode of destination is set to be EventListener.\nThe range of value is 'PutEvent', 'TakeEvent' and 'ExpireEvent'"),
        version = "5.1",
        see = "",
        mapper = @BEMapper(),
        description = "Gets the Event type.",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static String getEventType(SimpleEvent event) {
        return ASChannelFunctionsImpl.getEventType(event);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "beginTransaction",
        synopsis = "Start a transaction",
        signature = "void beginTransaction(String channelUri)",
        params = {
            @FunctionParamDescriptor(name = "channelUri", type = "String", desc = "AS Channel URI")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.1",
        see = "",
        mapper = @BEMapper(),
        description = "Start a transaction.",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static void beginTransaction(String channelUri) {
        ASChannelFunctionsImpl.beginTransaction(channelUri);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "commitTransaction",
        synopsis = "Commit a transaction",
        signature = "void commitTransaction(String channelUri)",
        params = {
            @FunctionParamDescriptor(name = "channelUri", type = "String", desc = "AS Channel URI")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.1",
        see = "",
        mapper = @BEMapper(),
        description = "Commit a transaction.",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static void commitTransaction(String channelUri) {
        ASChannelFunctionsImpl.commitTransaction(channelUri);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "rollbackTransaction",
        synopsis = "Rollback a transaction",
        signature = "void rollbackTransaction(String channelUri)",
        params = {
            @FunctionParamDescriptor(name = "channelUri", type = "String", desc = "AS Channel URI")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.1",
        see = "",
        mapper = @BEMapper(),
        description = "Rollback a transaction.",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static void rollbackTransaction(String channelUri) {
        ASChannelFunctionsImpl.rollbackTransaction(channelUri);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getEvent",
        synopsis = "Get the event with specific key from Space",
        signature = "Object getEvent(String destinationUri, SimpleEvent key)",
        params = {
            @FunctionParamDescriptor(name = "destinationUri", type = "String", desc = "AS Destination URI"),
            @FunctionParamDescriptor(name = "key", type = "SimpleEvent", desc = "The key of event")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "SimpleEvent or Object[N][2]", desc = "The event with specific key, null if not found."),
        version = "5.1",
        see = "",
        mapper = @BEMapper(),
        description = "Get the event with specific key from Space.",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Object getEvent(String destinationUri, SimpleEvent key) {
        return ASChannelFunctionsImpl.getEvent(destinationUri, key);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getEvents",
        synopsis = "Get the event result set",
        signature = "Object[] getEvents(String destinationUri, SimpleEvent[] keys)",
        params = {
            @FunctionParamDescriptor(name = "destinationUri", type = "String", desc = "AS Destination URI"),
            @FunctionParamDescriptor(name = "keys", type = "SimpleEvent[]", desc = "The key array of events")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "SimpleEvent[] or Object[M][N][2]", desc = "The event result set with specific key, null if not found."),
        version = "5.1",
        see = "",
        mapper = @BEMapper(),
        description = "Get the event result set.",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Object[] getEvents(String destinationUri, SimpleEvent[] keys) {
        return ASChannelFunctionsImpl.getEvents(destinationUri, keys);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "takeEvent",
        synopsis = "Returns and atomically removes from the space the event (if one exists) whose event key fields match the key fields of the event provided",
        signature = "Object takeEvent(String destinationUri, SimpleEvent key)",
        params = {
            @FunctionParamDescriptor(name = "destinationUri", type = "String", desc = "AS Destination URI"),
            @FunctionParamDescriptor(name = "key", type = "SimpleEvent", desc = "The key of event")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "SimpleEvent or Object[N][2]", desc = "The event with specific key, null if not found"),
        version = "5.1",
        see = "",
        mapper = @BEMapper(),
        description = "Returns and atomically removes from the space the event (if one exists) whose event key fields match the key fields of the event provided.",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Object takeEvent(String destinationUri, SimpleEvent key) {
        return ASChannelFunctionsImpl.takeEvent(destinationUri, key);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "takeEvents",
        synopsis = "Batch take operation",
        signature = "Object[] takeEvents(String destinationUri, SimpleEvent[] keys)",
        params = {
            @FunctionParamDescriptor(name = "destinationUri", type = "String", desc = "AS Destination URI"),
            @FunctionParamDescriptor(name = "keys", type = "SimpleEvent[]", desc = "The key array of events")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "SimpleEvent[] or Object[M][N][2]", desc = "The event result set with specific key, null if not found."),
        version = "5.1",
        see = "",
        mapper = @BEMapper(),
        description = "Batch take operation.",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Object[] takeEvents(String destinationUri, SimpleEvent[] keys) {
        return ASChannelFunctionsImpl.takeEvents(destinationUri, keys);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "putEvent",
        synopsis = "Stores a event into the Space and returns the corresponding entry",
        signature = "Object putEvent(String destinationUri, SimpleEvent event)",
        params = {
            @FunctionParamDescriptor(name = "destinationUri", type = "String", desc = "AS Destination URI"),
            @FunctionParamDescriptor(name = "event", type = "SimpleEvent", desc = "The event to be put into Space")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "SimpleEvent or Object[N][2]", desc = "This method is supposed to return the existing (OLD) tuple.<br/>It is expected to return null the first time it is used."),
        version = "5.1",
        see = "",
        mapper = @BEMapper(),
        description = "Stores a event into the Space and returns the corresponding entry.",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Object putEvent(String destinationUri, SimpleEvent event) {
        return ASChannelFunctionsImpl.putEvent(destinationUri, event);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "putEvents",
        synopsis = "Batch put operation",
        signature = "Object[] putEvents(String destinationUri, SimpleEvent[] keys)",
        params = {
            @FunctionParamDescriptor(name = "destinationUri", type = "String", desc = "AS Destination URI"),
            @FunctionParamDescriptor(name = "keys", type = "SimpleEvent[]", desc = "The key array of events to be put into space")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "SimpleEvent[] or Object[M][N][2]", desc = "The put events"),
        version = "5.1",
        see = "",
        mapper = @BEMapper(),
        description = "Batch put operation.",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Object[] putEvents(String destinationUri, SimpleEvent[] keys) {
        return ASChannelFunctionsImpl.putEvents(destinationUri, keys);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "lockEvent",
        synopsis = "Locks the event associated with a key event value. If lock operation is a success, matching " +
                    "tuple is returned (or null if no match is found), and lock must be released subsequently. " +
                    "If lock operation fails (e.g. due to specified timeout) then an exception is thrown with " +
                    "'LOCKED (entry_lock_timeout)' message, which must be handled by the caller.",
        signature = "Object lockEvent(String destinationUri, SimpleEvent event, Object... options)",
        params = {
            @FunctionParamDescriptor(name = "destinationUri", type = "String", desc = "AS Destination URI"),
            @FunctionParamDescriptor(name = "event", type = "SimpleEvent", desc = "The event to be locked"),
            @FunctionParamDescriptor(name = "options", type = "Object", desc = "Optional lock wait time in milliseconds")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "SimpleEvent or Object[N][2]", desc = "The locked event"),
        version = "5.1",
        see = "",
        mapper = @BEMapper(),
        description = "Locks the event associated with a key event value. If lock operation is a success, matching " +
                    "tuple is returned (or null if no match is found), and lock must be released subsequently. " +
                    "If lock operation fails (e.g. due to specified timeout) then an exception is thrown with " +
                    "'LOCKED (entry_lock_timeout)' message, which must be handled by the caller.",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Object lockEvent(String destinationUri, SimpleEvent event, Object... options) {
        LockOptions lockOptions = DEFAULT_LOCK_OPTIONS;
        if (options.length > 0) {
            long lockWait = Long.parseLong(String.valueOf(options[0]));
            lockOptions = LockOptions.create();
            lockOptions.setLockWait(lockWait);
        }
        return ASChannelFunctionsImpl.lockEvent(destinationUri, event, lockOptions);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "lockEvents",
        synopsis = "Batch lock operation",
        signature = "Object[] lockEvents(String destinationUri, SimpleEvent[] keys, Object... options)",
        params = {
            @FunctionParamDescriptor(name = "destinationUri", type = "String", desc = "AS Destination URI"),
            @FunctionParamDescriptor(name = "keys", type = "SimpleEvent[]", desc = "The key array of events to be locked"),
            @FunctionParamDescriptor(name = "options", type = "Object", desc = "Optional lock wait time in milliseconds")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "SimpleEvent[] or Object[M][N][2]", desc = "The locked events"),
        version = "5.1",
        see = "",
        mapper = @BEMapper(),
        description = "Batch lock operation.",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Object[] lockEvents(String destinationUri, SimpleEvent[] keys, Object... options) {
        LockOptions lockOptions = DEFAULT_LOCK_OPTIONS;
        if (options.length > 0) {
            long lockWait = Long.parseLong(String.valueOf(options[0]));
            lockOptions = LockOptions.create();
            lockOptions.setLockWait(lockWait);
        }
        return ASChannelFunctionsImpl.lockEvents(destinationUri, keys, lockOptions);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "unlockEvent",
        synopsis = "Unlocks the event associated with a key event value. If unlock operation fails " +
                    "(e.g. due to specified timeout) then an exception is thrown with " +
                    "'LOCKED (entry_lock_timeout)' message, which must be handled by the caller.",
        signature = "void unlockEvent(String destinationUri, SimpleEvent event, Object... options)",
        params = {
            @FunctionParamDescriptor(name = "destinationUri", type = "String", desc = "AS Destination URI"),
            @FunctionParamDescriptor(name = "event", type = "SimpleEvent", desc = "The event to be unlocked"),
            @FunctionParamDescriptor(name = "options", type = "Object", desc = "Optional lock wait time in milliseconds")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.1",
        see = "",
        mapper = @BEMapper(),
        description = "Unlocks the event associated with a key event value. If unlock operation fails " +
                        "(e.g. due to specified timeout) then an exception is thrown with " +
                        "'LOCKED (entry_lock_timeout)' message, which must be handled by the caller.",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static void unlockEvent(String destinationUri, SimpleEvent event, Object... options) {
        UnlockOptions unlockOptions = DEFAULT_UNLOCK_OPTIONS;
        if (options.length > 0) {
            long lockWait = Long.parseLong(String.valueOf(options[0]));
            unlockOptions = UnlockOptions.create();
            unlockOptions.setLockWait(lockWait);
        }
        ASChannelFunctionsImpl.unlockEvent(destinationUri, event, unlockOptions);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "unlockEvents",
        synopsis = "Batch unlock operation",
        signature = "Object[] unlockEvents(String destinationUri, SimpleEvent[] keys, Object... options)",
        params = {
            @FunctionParamDescriptor(name = "destinationUri", type = "String", desc = "AS Destination URI"),
            @FunctionParamDescriptor(name = "keys", type = "SimpleEvent[]", desc = "The key array of events to be unlocked"),
            @FunctionParamDescriptor(name = "options", type = "Object", desc = "Optional lock wait time in milliseconds")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "SimpleEvent[] or Object[M][N][2]", desc = "The unlocked events"),
        version = "5.1",
        see = "",
        mapper = @BEMapper(),
        description = "Batch unlock operation.",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Object[] unlockEvents(String destinationUri, SimpleEvent[] keys, Object... options) {
        UnlockOptions unlockOptions = DEFAULT_UNLOCK_OPTIONS;
        if (options.length > 0) {
            long lockWait = Long.parseLong(String.valueOf(options[0]));
            unlockOptions = UnlockOptions.create();
            unlockOptions.setLockWait(lockWait);
        }
        return ASChannelFunctionsImpl.unlockEvents(destinationUri, keys, unlockOptions);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "takeSnapshot",
        synopsis = "Take snapshot of the entries that match the conditions you specified in the associated Space",
        signature = "Object[] takeSnapshot(String destinationUri, SimpleEvent eventTemplate, String browserTypeStr, String distributionScope, int timeout, long prefetch, String filter)",
        params = {
            @FunctionParamDescriptor(name = "destinationUri", type = "String", desc = "AS Destination URI"),
            @FunctionParamDescriptor(name = "eventTemplate", type = "SimpleEvent", desc = "SimpleEvent Template"),
            @FunctionParamDescriptor(name = "browserTypeStr", type = "String", desc = "BrowserType: GET or TAKE"),
            @FunctionParamDescriptor(name = "distributionScope", type = "String", desc = "DistributionScope: ALL or SEEDED"),
            @FunctionParamDescriptor(name = "timeout", type = "int", desc = "Timeout"),
            @FunctionParamDescriptor(name = "prefetch", type = "long", desc = "The number of events will be prefetched"),
            @FunctionParamDescriptor(name = "filter", type = "String", desc = "Query condition")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "SimpleEvent[] or Object[M][N][2]", desc = "The matched events"),
        version = "5.1",
        see = "",
        mapper = @BEMapper(),
        description = "Take snapshot of the entries that match the conditions you specified in the associated Space.",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Object[] takeSnapshot(String destinationUri, SimpleEvent eventTemplate,
            String browserTypeStr, String distributionScope, int timeout,
            long prefetch, String filter) {
        return ASChannelFunctionsImpl.takeSnapshot(destinationUri, eventTemplate,
                browserTypeStr, distributionScope, timeout, prefetch, filter);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "query",
        synopsis = "Retrieves the entries that match the conditions you specified in the associated Space",
        signature = "Object[] query(String destinationUri, SimpleEvent eventTemplate, String browserTypeStr, String timeScope, String distributionScope, int timeout, long prefetch, String filter)",
        params = {
            @FunctionParamDescriptor(name = "destinationUri", type = "String", desc = "AS Destination URI"),
            @FunctionParamDescriptor(name = "eventTemplate", type = "SimpleEvent", desc = "SimpleEvent Template"),
            @FunctionParamDescriptor(name = "browserTypeStr", type = "String", desc = "BrowserType: GET or TAKE"),
            @FunctionParamDescriptor(name = "timeScope", type = "String", desc = "TimeScope: SNAPSHOT, ALL or NEW"),
            @FunctionParamDescriptor(name = "distributionScope", type = "String", desc = "DistributionScope: ALL or SEEDED"),
            @FunctionParamDescriptor(name = "timeout", type = "int", desc = "Timeout"),
            @FunctionParamDescriptor(name = "prefetch", type = "long", desc = "The number of events will be prefetched"),
            @FunctionParamDescriptor(name = "filter", type = "String", desc = "Query condition")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "SimpleEvent[] or Object[M][N][2]", desc = "The matched events"),
        version = "5.1",
        see = "",
        mapper = @BEMapper(),
        description = "Retrieves the entries that match the conditions you specified in the associated Space.",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Object[] query(String destinationUri, SimpleEvent eventTemplate, String browserTypeStr,
            String timeScope, String distributionScope, int timeout,
            long prefetch, String filter) {
        return ASChannelFunctionsImpl.query(destinationUri, eventTemplate, browserTypeStr,
                timeScope, distributionScope, timeout, prefetch, filter);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "queryIterator",
        synopsis = "Retrieves the iterator of entries that match the conditions you specified in the associated Space",
        signature = "Object queryIterator(String destinationUri, SimpleEvent eventTemplate, String browserTypeStr, String timeScope, String distributionScope, int timeout, long prefetch, String filter)",
        params = {
                @FunctionParamDescriptor(name = "destinationUri", type = "String", desc = "AS Destination URI"),
                @FunctionParamDescriptor(name = "eventTemplate", type = "SimpleEvent", desc = "SimpleEvent Template"),
                @FunctionParamDescriptor(name = "browserTypeStr", type = "String", desc = "BrowserType: GET or TAKE"),
                @FunctionParamDescriptor(name = "timeScope", type = "String", desc = "TimeScope: SNAPSHORT, ALL or NEW"),
                @FunctionParamDescriptor(name = "distributionScope", type = "String", desc = "DistributionScope: ALL or SEEDED"),
                @FunctionParamDescriptor(name = "timeout", type = "int", desc = "Timeout"),
                @FunctionParamDescriptor(name = "prefetch", type = "long", desc = "The number of events will be prefetched"),
                @FunctionParamDescriptor(name = "filter", type = "String", desc = "Query condition")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "Iterator", desc = "The iterator for matched Events"),
        version = "5.1",
        see = "",
        mapper = @BEMapper(),
        description = "Retrieves the iterator of entries that match the conditions you specified in the associated Space.",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Object queryIterator(String destinationUri, SimpleEvent eventTemplate, String browserTypeStr,
            String timeScope, String distributionScope, int timeout,
            long prefetch, String filter) {
        return ASChannelFunctionsImpl.queryIterator(destinationUri, eventTemplate, browserTypeStr,
                timeScope, distributionScope, timeout, prefetch, filter);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "releaseTransaction",
        synopsis = "Return TransationId if the thread is in a transaction. TransactionId can then be given to another thread to continue transaction",
        signature = "Object releaseTransaction(String channelUri)",
        params = {
            @FunctionParamDescriptor(name = "channelUri", type = "String", desc = "Channel URI"),
            @FunctionParamDescriptor(name = "transactionId", type = "TransactionId", desc = "Instance of TransactionId")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.1",
        see = "",
        mapper = @BEMapper(),
        description = "Return TransationId if the thread is in a transaction. TransactionId can then be given to another thread to continue transaction..",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Object releaseTransaction(String channelUri) {
        return ASChannelFunctionsImpl.releaseTransaction(channelUri);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "takeTransaction",
        synopsis = "Take TransactionId to continue transaction",
        signature = "void takeTransaction(String channelUri, Object transactionId)",
        params = {
            @FunctionParamDescriptor(name = "channelUri", type = "String", desc = "Channel URI"),
            @FunctionParamDescriptor(name = "transactionId", type = "TransactionId", desc = "Instance of TransactionId")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.1",
        see = "",
        mapper = @BEMapper(),
        description = "Take TransactionId to continue transaction.",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static void takeTransaction(String channelUri, Object transactionId) {
        ASChannelFunctionsImpl.takeTransaction(channelUri, transactionId);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getConceptBlobFromPayload",
        synopsis = "Get Concept's Blob from the payload of SimpleEvent",
        signature = "Object getConceptBlobFromPayload(SimpleEvent event, String blobFieldName, Concept conceptTemplate)",
        params = {
            @FunctionParamDescriptor(name = "event", type = "SimpleEvent", desc = "SimpleEvent with payload"),
            @FunctionParamDescriptor(name = "blobFieldName", type = "String", desc = "The field name of Blob"),
            @FunctionParamDescriptor(name = "conceptTemplate", type = "Concept", desc = "The template of Concept")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "byte[] or Concept", desc = "Return null if there's no payload or the field name is nonexistent"),
        version = "5.1",
        see = "",
        mapper = @BEMapper(),
        description = "Get Concept's Blob from the payload of SimpleEvent.",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Object getConceptBlobFromPayload(SimpleEvent event, String blobFieldName, Concept conceptTemplate) {
        return ASChannelFunctionsImpl.getConceptBlobFromPayload(event, blobFieldName, conceptTemplate);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getSimpleEventBlobFromPayload",
        synopsis = "Get SimpleEvent's Blob from the payload of SimpleEvent",
        signature = "Object getSimpleEventBlobFromPayload(SimpleEvent event, String blobFieldName, SimpleEvent eventTemplate)",
        params = {
            @FunctionParamDescriptor(name = "event", type = "SimpleEvent", desc = "SimpleEvent with payload"),
            @FunctionParamDescriptor(name = "blobFieldName", type = "String", desc = "The field name of Blob"),
            @FunctionParamDescriptor(name = "eventTemplate", type = "SimpleEvent", desc = "The template of SimpleEvent")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "byte[] or SimpleEvent", desc = "Return null if there's no payload or the field name is nonexistent"),
        version = "5.1",
        see = "",
        mapper = @BEMapper(),
        description = "Get SimpleEvent's Blob from the payload of SimpleEvent.",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Object getSimpleEventBlobFromPayload(SimpleEvent event, String blobFieldName, SimpleEvent eventTemplate) {
        return ASChannelFunctionsImpl.getSimpleEventBlobFromPayload(event, blobFieldName, eventTemplate);
    }
}
