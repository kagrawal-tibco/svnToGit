package com.tibco.cep.functions.channel.as;

/**
* Generated Code from String Template.
* Date : Sep 12, 2012
*/

import com.tibco.as.space.PutOptions;
import com.tibco.as.space.TakeOptions;
import com.tibco.be.model.functions.BEFunction;
import com.tibco.be.model.functions.BEPackage;
import com.tibco.be.model.functions.FunctionParamDescriptor;
import static com.tibco.be.model.functions.FunctionDomain.ACTION;

@BEPackage(
        catalog = "ActiveSpaces",
        category = "Metaspace.Space.Options",
        synopsis = "Options functions")
public class OptionsHelper {

    @BEFunction(
        name = "createPutOptions",
        synopsis = "Used to instantiate an instance of a PutOptions object.",
        signature = "Object createPutOptions ()",
        params = {

        },
        freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "The newly created com.tibco.as.space.PutOptions object"),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Used to instantiate an instance of a PutOptions object.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Object createPutOptions () {
        return PutOptions.create();
    }

    @BEFunction(
        name = "getLockWait",
        synopsis = "Retrieve the current setting for the amount of time to wait for the lock on the key of an existing tuple.",
        signature = "long getLockWait (Object options)",
        params = {
                 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "options", type = "Object", desc = "The TakeOptions or PutOptions object")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "long", desc = "Number of milliseconds to wait for a lock."),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieve the current setting for the amount of time to wait for the lock on the key of an existing tuple.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static long getLockWait (Object options) {
        if (options != null && options instanceof PutOptions) {
            return ((PutOptions)options).getLockWait();
        } else if (options != null && options instanceof TakeOptions) {
            return ((TakeOptions)options).getLockWait();
        }
        return -1;
    }

    @BEFunction(
        name = "getTTL",
        synopsis = "Retrieve the current setting for the number of milliseconds to use as an entry's time to live.",
        signature = "long getTTL (Object options)",
        params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "options", type = "Object", desc = "The PutOptions object")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "long", desc = "The current time to live setting in milliseconds."),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieve the current setting for the number of milliseconds to use as an entry's time to live.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static long getTTL (Object options) {
        if (options != null && options instanceof PutOptions) {
            return ((PutOptions)options).getTTL();
        }
        return -1;
    }

    @BEFunction(
        name = "isForget",
        synopsis = "For TakeOptions - Retrieve the current setting for whether or not to return the tuple taken from a space using this TakeOptions object." +
                "<br>For PutOptions -Retrieve whether tuples, which already exist in a space, should be returned as the return values of put operations, or whether the existing tuples should be \"forgotten\" and not returned.",
        signature = "boolean isForget (Object options)",
        params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "options", type = "Object", desc = "The TakeOptions or PutOptions object")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "boolean", desc = "true - Forget tuples. false - Do not forget tuples."),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "For TakeOptions - Retrieve the current setting for whether or not to return the tuple taken from a space using this TakeOptions object." +
                "<br>For PutOptions -Retrieve whether tuples, which already exist in a space, should be returned as the return values of put operations, or whether the existing tuples should be \"forgotten\" and not returned.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static boolean isForget (Object options  ) {
        if (options != null && options instanceof PutOptions) {
            return ((PutOptions)options).isForget();
        }  else if (options != null && options instanceof TakeOptions) {
            return ((TakeOptions)options).isForget();
        }
        return false;
    }

    @BEFunction(
        name = "isLock",
        synopsis = "For TakeOptions - Retrieve the current setting for whether or not to lock the key of a tuple after it is removed from a space." +
                "<br>For PutOptions - Retrieve whether or not to lock the key of a tuple after it is stored into the space.",
        signature = "boolean isLock (Object options)",
        params = {
                @FunctionParamDescriptor(name = "options", type = "Object", desc = "The TakeOptions or PutOptions object")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "boolean", desc = "true - The keys of any tuples stored with this TakeOptions/PutOptions object will be locked after the tuples are removed/stored in the space. false - The keys of any tuples put with this TakeOptions/PutOptions object will not be locked after the tuples are removed/stored in the space."),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "For TakeOptions - Retrieve the current setting for whether or not to lock the key of a tuple after it is removed from a space." +
                "<br>For PutOptions - Retrieve whether or not to lock the key of a tuple after it is stored into the space.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static boolean isLock (Object options) {
        if (options != null && options instanceof PutOptions) {
            return ((PutOptions)options).isLock();
        } else if (options != null && options instanceof TakeOptions) {
            return ((TakeOptions)options).isLock();
        }
        return false;
    }

    @BEFunction(
        name = "isUnlock",
        synopsis = "For TakeOptions - Retrieve the current setting for whether or not to unlock the key of a tuple after it is removed from a space." +
                "<br>For PutOptions - Retrieve whether or not to unlock the key of a tuple after it is stored into a space.",
        signature = "boolean isUnlock (Object options)",
        params = {
                @FunctionParamDescriptor(name = "options", type = "Object", desc = "The TakeOptions or  PutOptions object")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "boolean", desc = "true - Unlock the key if the take/put operation is successful. false - Do not unlock the key after the take/put operation."),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "For TakeOptions - Retrieve the current setting for whether or not to unlock the key of a tuple after it is removed from a space." +
                "<br>For PutOptions - Retrieve whether or not to unlock the key of a tuple after it is stored into a space.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static boolean isUnlock (Object options) {
        if (options != null && options instanceof PutOptions) {
            return ((PutOptions)options).isUnlock();
        } else if (options != null && options instanceof TakeOptions) {
            return ((TakeOptions)options).isUnlock();
        }
        return false;
    }

    @BEFunction(
        name = "setForget",
        synopsis = "For TakeOptions - Specify whether or not to return the tuple taken from a space using this TakeOptions object." +
                "<br>For PutOptions - Specifies whether a tuple, which already exists in the space, should be returned as the return value of a put operation.",
        signature = "Object setForget (Object options, boolean forget)",
        params = {
             @FunctionParamDescriptor(name = "options", type = "Object", desc = "The TakeOptions or PutOptions object"),
             @FunctionParamDescriptor(name = "forget", type = "boolean", desc = "true - Do not return (forget) tuples. false - Return tuples.  ")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "The updated TakeOptions/PutOptions object."),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "For TakeOptions - Specify whether or not to return the tuple taken from a space using this TakeOptions object." +
                "<br>For PutOptions - Specifies whether a tuple, which already exists in the space, should be returned as the return value of a put operation.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Object setForget (Object options, boolean forget) {
        if (options != null && options instanceof PutOptions) {
            return ((PutOptions)options).setForget(forget);
        } else if (options != null && options instanceof TakeOptions) {
            return ((TakeOptions)options).setForget(forget);
        }
        return null;
    }

    @BEFunction(
        name = "setLock",
        synopsis = "For TakeOptions - Specify whether or not to lock the key of a tuple after it is removed from a space." +
                "<br>For PutOptions - Specify whether or not to lock the key of a tuple after the tuple has been stored in a space.",
        signature = "Object setLock (Object options, boolean lock)",
        params = {
             @FunctionParamDescriptor(name = "options", type = "Object", desc = "The TakeOptions or PutOptions object"),
             @FunctionParamDescriptor(name = "lock", type = "boolean", desc = " true - lock the key. false - do not lock the key. ")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "The updated TakeOptions/PutOptions object."),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "For TakeOptions - Specify whether or not to lock the key of a tuple after it is removed from a space." +
                "<br>For PutOptions - Specify whether or not to lock the key of a tuple after the tuple has been stored in a space.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Object setLock (Object options, boolean lock) {
        if (options != null && options instanceof PutOptions) {
            return ((PutOptions)options).setLock(lock);
        } else if (options != null && options instanceof TakeOptions) {
            return ((TakeOptions)options).setLock(lock);
        }
        return null;
    }

    @BEFunction(
        name = "setLockWait",
        synopsis = "For TakeOptions - Specify the amount of time to wait to acquire the lock on an existing locked tuple." +
                "<br>For PutOptions - Specify the amount of time to wait to acquire the lock on the key of an existing tuple.",
        signature = "Object setLockWait (Object options, long lockWait)",
        params = {
             @FunctionParamDescriptor(name = "options", type = "Object", desc = "The TakeOptions or PutOptions object"),
             @FunctionParamDescriptor(name = "lockWait", type = "long", desc = "Number of milliseconds to wait for a lock.")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "The updated TakeOptions/PutOptions object."),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "For TakeOptions - Specify the amount of time to wait to acquire the lock on an existing locked tuple." +
                "<br>For PutOptions - Specify the amount of time to wait to acquire the lock on the key of an existing tuple.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Object setLockWait (Object options, long lockWait) {
        if (options != null && options instanceof PutOptions) {
            return ((PutOptions)options).setLockWait(lockWait);
        } else if (options != null && options instanceof TakeOptions) {
            return ((TakeOptions)options).setLockWait(lockWait);
        }
        return null;
    }

    @BEFunction(
        name = "setTTL",
        synopsis = "Specify the time to live for entries stored into a space using this PutOptions object. WAIT_FOREVER means that an entry will never expire.",
        signature = "Object setTTL (Object options, long ttl)",
        params = {
             @FunctionParamDescriptor(name = "options", type = "Object", desc = "The PutOptions object"),
             @FunctionParamDescriptor(name = "ttl", type = "long", desc = "Time in milliseconds to use as an entry's time to live. ")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "Time in milliseconds to use as an entry's time to live."),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Specify the time to live for entries stored into a space using this PutOptions object. WAIT_FOREVER means that an entry will never expire.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Object setTTL (Object options, long ttl) {
        if (options != null && options instanceof PutOptions) {
            return ((PutOptions)options).setTTL(ttl);
        }
        return null;
    }

    @BEFunction(
        name = "setUnlock",
        synopsis = "For TakeOptions - Specify whether or not to unlock the key of a tuple after it is removed from a space." +
                "<br>For PutOptions - Specify whether or not to unlock the key after storing a tuple into a space",
        signature = "Object setUnlock (Object options, boolean unlock)",
        params = {
             @FunctionParamDescriptor(name = "options", type = "Object", desc = "The TakeOptions or PutOptions object"),
             @FunctionParamDescriptor(name = "unlock", type = "boolean", desc = " true - Unlock the key. false - Do not unlock they key.  ")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "The updated TakeOptions/PutOptions  object"),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "For TakeOptions - Specify whether or not to unlock the key of a tuple after it is removed from a space." +
                "<br>For PutOptions - Specify whether or not to unlock the key after storing a tuple into a space",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Object setUnlock (Object options, boolean unlock) {
        if (options != null && options instanceof PutOptions) {
            return ((PutOptions)options).setUnlock(unlock);
        }  else if (options != null && options instanceof TakeOptions) {
            return ((TakeOptions)options).setUnlock(unlock);
        }
        return null;
    }

    @BEFunction(
        name = "createTakeOptions",
        synopsis = "Used to instantiate an instance of a TakeOptions object.",
    signature = "Object createTakeOptions()",
        params = {

        },
        freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "The newly created com.tibco.as.space.TakeOptions object."),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Used to instantiate an instance of a TakeOptions object.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Object createTakeOptions () {
        return TakeOptions.create();
    }
}
