package com.tibco.cep.functions.channel.as;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.tibco.as.space.FieldDef;
import com.tibco.as.space.KeyDef;
import com.tibco.as.space.Member;
import com.tibco.as.space.MemberDef;
import com.tibco.as.space.Metaspace;
import com.tibco.as.space.RecoveryOptions;
import com.tibco.as.space.Space;
import com.tibco.as.space.SpaceDef;
import com.tibco.as.space.FieldDef.FieldType;
import com.tibco.as.space.IndexDef.IndexType;
import com.tibco.as.space.Member.DistributionRole;
import com.tibco.as.space.SpaceDef.ReplicationPolicy;
import com.tibco.be.util.config.cdd.BackingStoreConfig;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.session.BEManagedThread;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.util.SystemProperty;

import com.tibco.be.model.functions.Enabled;
import com.tibco.be.model.functions.BEPackage;
import com.tibco.be.model.functions.FunctionParamDescriptor;
import static com.tibco.be.model.functions.FunctionDomain.*;

@BEPackage(
        catalog = "ActiveSpaces",
        category = "Metaspace",
        synopsis = "Metaspace functions",
        enabled = @Enabled(property="com.tibco.cep.functions.as.metaspace"))
public class MetaspaceHelper extends ASHelper {

    @com.tibco.be.model.functions.BEFunction(
            name = "beginTransaction",
            synopsis = "BeginTransaction starts a transaction. Any operations on any Space in this metaspace called from the thread that currently owns the transaction will be in a transactional state, until either commitTransaction or rollbackTransaction is invoked. Transaction is scoped by the current thread context.",
            signature = "void beginTransaction (String metaSpaceName)",
            params = {
                    @FunctionParamDescriptor(name = "metaSpaceName", type = "String", desc = "metaspace name ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "BeginTransaction starts a transaction. Any operations on any Space in this metaspace called from the thread that currently owns the transaction will be in a transactional state, until either commitTransaction or rollbackTransaction is invoked. Transaction is scoped by the current thread context.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static void beginTransaction (String metaSpaceName) throws RuntimeException {
        try {
            MetaspaceInfo msInfo = (MetaspaceInfo) getMetaspaceInfo(metaSpaceName);
            if (msInfo != null) {
                msInfo.getMetaSpace().beginTransaction();
            }
        } catch (Exception e) {
            throw new RuntimeException("beginTransaction() failed", e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "close",
            synopsis = "Frees resources used by the Metaspace object. Invalidates this metaspace reference. If there are no more references, this method will disconnect from metaspace.",
            signature = "void close (String metaSpaceName)",
            params = {
                    @FunctionParamDescriptor(name = "metaSpaceName", type = "String", desc = "metaspace name ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Frees resources used by the Metaspace object. Invalidates this metaspace reference. If there are no more references, this method will disconnect from metaspace.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static void close (String metaSpaceName) throws RuntimeException {
        try {
            MetaspaceInfo msInfo = (MetaspaceInfo) getMetaspaceInfo(metaSpaceName);
            if (msInfo != null) {
                msInfo.getMetaSpace().close();
                cacheMap.remove(metaSpaceName);
            }
        } catch (Exception e) {
            throw new RuntimeException("close() failed", e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "closeAll",
            synopsis = "Frees resources used by the Metaspace object. Immediately closes the connection to the Metaspace. Forces all Spaces, Browsers, and Listeners to be freed, and their handles to become invalid.",
            signature = "void closeAll (String metaSpaceName)",
            params = {
                    @FunctionParamDescriptor(name = "metaSpaceName", type = "String", desc = "metaspace name ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Frees resources used by the Metaspace object. Immediately closes the connection to the Metaspace. Forces all Spaces, Browsers, and Listeners to be freed, and their handles to become invalid.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static void closeAll (String metaSpaceName) throws RuntimeException {
        try {
            MetaspaceInfo msInfo = (MetaspaceInfo) getMetaspaceInfo(metaSpaceName);
            if (msInfo != null) {
                msInfo.getMetaSpace().closeAll();
                cacheMap.remove(metaSpaceName);
            } else {
                throw new RuntimeException(String.format("Metaspace :%s is not available", metaSpaceName));
            }
        } catch (Exception e) {
            throw new RuntimeException("closeAll() failed", e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "commitTransaction",
            synopsis = "Commits all of the Space operations invoked in this thread since beginTransaction was called.",
            signature = "void commitTransaction (String metaSpaceName)",
            params = {
                    @FunctionParamDescriptor(name = "metaSpaceName", type = "String", desc = "metaspace name ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Commits all of the Space operations invoked in this thread since beginTransaction was called.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static void commitTransaction (String metaSpaceName) throws RuntimeException {
        try {
            MetaspaceInfo msInfo = (MetaspaceInfo) getMetaspaceInfo(metaSpaceName);
            if (msInfo != null) {
                msInfo.getMetaSpace().commitTransaction();
            } else {
                throw new RuntimeException(String.format("Metaspace :%s is not available", metaSpaceName));
            }
        } catch (Exception e) {
            throw new RuntimeException("commitTransaction() failed", e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "connect",
            synopsis = "Establishes connection to metaspace using the member definition.",
            signature = "void connect (String metaSpaceName, Object memberDef)",
            params = {
                    @FunctionParamDescriptor(name = "metaSpaceName", type = "String", desc = "metaspace name ") ,
                    @FunctionParamDescriptor(name = "memberDef", type = "Object", desc = "member definition. See MemberDef")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Establishes connection to metaspace using the member definition ",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static void connect(String metaSpaceName, Object memberDef) throws RuntimeException {
        try {
            MetaspaceInfo msInfo = (MetaspaceInfo) getMetaspaceInfo(metaSpaceName);
            if (msInfo == null) {
                msInfo = new MetaspaceInfo(metaSpaceName, (MemberDef)memberDef);
                msInfo.connect();
                cacheMap.put(metaSpaceName, msInfo);
            }
        } catch (Exception e) {
            throw new RuntimeException("connect() failed", e);
        }
        return;
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "getMemberDef",
            synopsis = "Returns the metaspace connection definition attributes.",
            signature = "Object getMemberDef (String metaSpaceName)",
            params = {
                    @FunctionParamDescriptor(name = "metaSpaceName", type = "String", desc = "metaspace name ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "the member definition"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the metaspace connection definition attributes",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object getMemberDef (String metaSpaceName) throws RuntimeException {
        try {
            MetaspaceInfo msInfo = (MetaspaceInfo) getMetaspaceInfo(metaSpaceName);
            if (msInfo != null) {
                return msInfo.getMetaSpace().getMemberDef();
            } else {
                throw new RuntimeException(String.format("Metaspace :%s is not available", metaSpaceName));
            }
        } catch (Exception e) {
            throw new RuntimeException("getMemberDef() failed", e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "getMetaspaceMembers",
            synopsis = "Returns the collection of Member of the metaspace.",
            signature = "Object getMetaspaceMembers (String metaSpaceName)",
            params = {
                    @FunctionParamDescriptor(name = "name", type = "String", desc = "metaspace name ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "the collection of Member of the metaspace "),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the collection of Member of the metaspace",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object getMetaspaceMembers (String metaSpaceName) throws RuntimeException {
        try {
            MetaspaceInfo msInfo = (MetaspaceInfo) getMetaspaceInfo(metaSpaceName);
            if (msInfo != null) {
                return msInfo.getMetaSpace().getMetaspaceMembers();
            } else {
                throw new RuntimeException(String.format("Metaspace :%s is not available", metaSpaceName));
            }
        } catch (Exception e) {
            throw new RuntimeException("getMetaspaceMembers() failed", e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "getMetaspaceRemoteMembers",
            synopsis = "Returns the collection of Member of the metaspace that are connected using remote discovery.",
            signature = "Object getMetaspaceRemoteMembers (String metaSpaceName)",
            params = {
                    @FunctionParamDescriptor(name = "metaSpaceName", type = "String", desc = "metaspace name ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "the collection of Member of the metaspace that are connected using remote discovery"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the collection of Member of the metaspace that are connected using remote discovery.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object getMetaspaceRemoteMembers (String metaSpaceName) throws RuntimeException {
        try {
            MetaspaceInfo msInfo = (MetaspaceInfo) getMetaspaceInfo(metaSpaceName);
            if (msInfo != null) {
                return msInfo.getMetaSpace().getMetaspaceRemoteMembers();
            } else {
                throw new RuntimeException(String.format("Metaspace :%s is not available", metaSpaceName));
            }
        } catch (Exception e) {
            throw new RuntimeException("getMetaspaceRemoteMembers() failed", e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "getSelfMember",
            synopsis = "Returns the Member for this Metaspace connection.",
            signature = "Object getSelfMember (String metaSpaceName)",
            params = {
                    @FunctionParamDescriptor(name = "metaSpaceName", type = "String", desc = "metaspace name ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "the Member for this Metaspace connection"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the Member for this Metaspace connection.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object getSelfMember (String metaSpaceName) throws RuntimeException {
        try {
            MetaspaceInfo msInfo = (MetaspaceInfo) getMetaspaceInfo(metaSpaceName);
            if (msInfo != null) {
                return msInfo.getMetaSpace().getSelfMember();
            } else {
                throw new RuntimeException(String.format("Metaspace :%s is not available", metaSpaceName));
            }
        } catch (Exception e) {
            throw new RuntimeException("getSelfMember() failed", e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "getSpace",
            synopsis = "Returns the space after joining it with role of SEEDER or LEECH, if it is not already joined. The fully qualified space name is derived by concatenating <MetaSpaceName>.<SpaceName> .",
            signature = "Object getSpace (String metaSpaceName, String spaceName, boolean isSeeder)",
            params = {
                    @FunctionParamDescriptor(name = "metaSpaceName", type = "String", desc = "metaspace name ") ,
                    @FunctionParamDescriptor(name = "spaceName", type = "String", desc = "unqualified Space name ") ,
                    @FunctionParamDescriptor(name = "isSeeder", type = "boolean", desc = "SEEDER if true else LEECH ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "the space after joining it with role LEECH, if it is not already joined"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the space after joining it with role of SEEDER or LEECH, if it is not already joined.The fully qualified space name is derived by concatenating <MetaSpaceName>.<SpaceName>",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object getSpace (String metaSpaceName, String spaceName, boolean isSeeder) throws RuntimeException {
        try {
            MetaspaceInfo msInfo = (MetaspaceInfo) getMetaspaceInfo(metaSpaceName);
            if (msInfo != null) {
                Member.DistributionRole role = isSeeder ? Member.DistributionRole.SEEDER:Member.DistributionRole.LEECH;
                Space space = msInfo.getMetaSpace().getSpace(spaceName, role);
                ASSpaceHelper.addSpace(metaSpaceName, spaceName, space);
                return space;
            } else {
                throw new RuntimeException(String.format("Metaspace :%s is not available", metaSpaceName));
            }
        } catch (Exception e) {
            throw new RuntimeException("getSpace() failed", e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "getSpaceDef",
            synopsis = "Gets space definition for the given unqualified Space name by concatenating <MetaSpaceName>.<SpaceName> .",
            signature = "Object getSpaceDef (String metaSpaceName, String spaceName)",
            params = {
                    @FunctionParamDescriptor(name = "metaSpaceName", type = "String", desc = "metaspace name ") ,
                    @FunctionParamDescriptor(name = "spaceName", type = "String", desc = "unqualified space name ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "space definition for the given Space name"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Gets space definition for the given unqualified Space name by concatenating <MetaSpaceName>.<SpaceName>",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object getSpaceDef (String metaSpaceName, String spaceName) throws RuntimeException {
        try {
            MetaspaceInfo msInfo = (MetaspaceInfo) getMetaspaceInfo(metaSpaceName);
            if (msInfo != null) {
                return msInfo.getMetaSpace().getSpaceDef(spaceName);
            } else {
                throw new RuntimeException(String.format("Metaspace :%s is not available", metaSpaceName));
            }
        } catch (Exception e) {
            throw new RuntimeException("getSpaceDef() failed", e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "getSpaceMembers",
            synopsis = "Returns the collection of Member of the space for the given unqualified Space name by concatenating <MetaSpaceName>.<SpaceName> .",
            signature = "Object getSpaceMembers (String metaSpaceName, String spaceName)",
            params = {
                    @FunctionParamDescriptor(name = "metaSpaceName", type = "String", desc = "metaspace name "),
                    @FunctionParamDescriptor(name = "spaceName", type = "String", desc = "space name ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "the collection of Member of the space. "),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the collection of Member of the space for the given unqualified Space name by concatenating <MetaSpaceName>.<SpaceName>",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object getSpaceMembers (String metaSpaceName, String spaceName) throws RuntimeException {
        try {
            MetaspaceInfo msInfo = (MetaspaceInfo) getMetaspaceInfo(metaSpaceName);
            if (msInfo != null) {
                return msInfo.getMetaSpace().getSpaceMembers(spaceName);
            } else {
                throw new RuntimeException(String.format("Metaspace :%s is not available", metaSpaceName));
            }
        } catch (Exception e) {
            throw new RuntimeException("getSpaceDef() failed", e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "getSpaceRemoteMembers",
            synopsis = "Returns the collection of Member of the space remotely connected by this member.",
            signature = "Object getSpaceRemoteMembers (String metaSpaceName, String spaceName)",
            params = {
                    @FunctionParamDescriptor(name = "metaSpaceName", type = "String", desc = "metaspace name"),
                    @FunctionParamDescriptor(name = "spaceName", type = "String", desc = "space name")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "the collection of Member of the space that are connected using remote discovery "),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the collection of Member of the space remotely connected by this member",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object getSpaceRemoteMembers (String metaSpaceName, String spaceName) throws RuntimeException {
        try {
            MetaspaceInfo msInfo = (MetaspaceInfo) getMetaspaceInfo(metaSpaceName);
            if (msInfo != null) {
                return msInfo.getMetaSpace().getSpaceRemoteMembers(spaceName);
            } else {
                throw new RuntimeException(String.format("Metaspace :%s is not available", metaSpaceName));
            }
        } catch (Exception e) {
            throw new RuntimeException("getSpaceRemoteMembers() failed", e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "getUserSpaceNames",
        synopsis = "Returns the user space names as a collection.",
            signature = "Object getUserSpaceNames (String metaSpaceName)",
            params = {
                    @FunctionParamDescriptor(name = "metaSpaceName", type = "String", desc = "metaspace name ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "the user space names "),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the user space names",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object getUserSpaceNames (String metaSpaceName) throws RuntimeException {
        try {
            MetaspaceInfo msInfo = (MetaspaceInfo) getMetaspaceInfo(metaSpaceName);
            if (msInfo != null) {
                return msInfo.getMetaSpace().getUserSpaceNames();
            } else {
                throw new RuntimeException(String.format("Metaspace :%s is not available", metaSpaceName));
            }
        } catch (Exception e) {
            throw new RuntimeException("getUserSpaceNames() failed", e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "releaseContext",
            synopsis = "releaseContext is used to release the Context object from one thread so that it can be transferred to another thread.",
            signature = "Object releaseContext (String metaSpaceName)",
            params = {
                    @FunctionParamDescriptor(name = "metaSpaceName", type = "String", desc = "metaspace name ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "instance of com.tibco.as.space.Context"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "releases context",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object releaseContext (String metaSpaceName) throws RuntimeException {
        try {
            MetaspaceInfo msInfo = (MetaspaceInfo) getMetaspaceInfo(metaSpaceName);
            if (msInfo != null) {
                return msInfo.getMetaSpace().releaseContext();
            } else {
                throw new RuntimeException(String.format("Metaspace :%s is not available", metaSpaceName));
            }
        } catch (Exception e) {
            throw new RuntimeException("releaseContext() failed", e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "resume",
            synopsis = "Resumes the metaspace.",
            signature = "void resume (String metaSpaceName)",
            params = {
                    @FunctionParamDescriptor(name = "metaSpaceName", type = "String", desc = "metaspace name ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Resumes the metaspace.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static void resume (String metaSpaceName) throws RuntimeException {
        try {
            MetaspaceInfo msInfo = (MetaspaceInfo) getMetaspaceInfo(metaSpaceName);
            if (msInfo != null) {
                msInfo.getMetaSpace().resume();
            } else {
                throw new RuntimeException(String.format("Metaspace :%s is not available", metaSpaceName));
            }
        } catch (Exception e) {
            throw new RuntimeException("resume() failed", e);
        }
        return ;
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "resumeSpace",
            synopsis = "Resumes a space.",
            signature = "void resumeSpace (String metaSpaceName, String spaceName)",
            params = {
                    @FunctionParamDescriptor(name = "metaSpaceName", type = "String", desc = "metaspace name "),
                    @FunctionParamDescriptor(name = "spaceName", type = "String", desc = "space name ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "resumes a space.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static void resumeSpace (String metaSpaceName, String spaceName) throws RuntimeException {
        try {
            MetaspaceInfo msInfo = (MetaspaceInfo) getMetaspaceInfo(metaSpaceName);
            if (msInfo != null) {
                msInfo.getMetaSpace().resumeSpace(spaceName);
            } else {
                throw new RuntimeException(String.format("Metaspace :%s is not available", metaSpaceName));
            }
        } catch (Exception e) {
            throw new RuntimeException("resumeSpace() failed", e);
        }
        return ;
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "rollbackTransaction",
            synopsis = "Reverses all of the Space operations invoked in this thread since beginTransaction was called.",
            signature = "void rollbackTransaction (String metaSpaceName)",
            params = {
                    @FunctionParamDescriptor(name = "metaSpaceName", type = "String", desc = "metaspace name ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Reverses all of the Space operations invoked in this thread since beginTransaction was called.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static void rollbackTransaction (String metaSpaceName) throws RuntimeException {
        try {
            MetaspaceInfo msInfo = (MetaspaceInfo) getMetaspaceInfo(metaSpaceName);
            if (msInfo != null) {
                msInfo.getMetaSpace().rollbackTransaction();
            } else {
                throw new RuntimeException(String.format("Metaspace :%s is not available", metaSpaceName));
            }
        } catch (Exception e) {
            throw new RuntimeException("rollbackTransaction() failed", e);
        }
        return ;
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "suspend",
            synopsis = "Suspends the metaspace.",
            signature = "void suspend (String metaSpaceName)",
            params = {
                    @FunctionParamDescriptor(name = "metaSpaceName", type = "String", desc = "metaspace name ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "suspends the metaspace",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static void suspend (String metaSpaceName) throws RuntimeException {
        try {
            MetaspaceInfo msInfo = (MetaspaceInfo) getMetaspaceInfo(metaSpaceName);
            if (msInfo != null) {
                msInfo.getMetaSpace().suspend();
            } else {
                throw new RuntimeException(String.format("Metaspace :%s is not available", metaSpaceName));
            }
        } catch (Exception e) {
            throw new RuntimeException("suspend() failed", e);
        }
        return ;
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "version",
            synopsis = "Retrieves the version of the ActiveSpaces product.",
            signature = "String version ()",
            params = {
            },
            freturn = @FunctionParamDescriptor(name = "", type = "String", desc = "the version of the ActiveSpaces product."),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Retrieves the version of the ActiveSpaces product.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static String version () {
        return Metaspace.version();
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "create",
            synopsis = "Creates Member definition using default connection definition.",
            signature = "Object create ()",
            params = {
            },
            freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "instance of MemberDef"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Creates Member definition using default connection definition",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object create () {
        return MemberDef.create();
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "createUserSpace",
            synopsis = "Creates a user space using the provided field definitions. If space exists, the call is ignored and existing space is returned.",
            signature = "Object createUserSpace (String metaSpaceName, String spaceName, String[] fieldNames, String[] dataTypes, Object ... variableArgs)",
            params = {
                    @FunctionParamDescriptor(name = "metaSpaceName", type = "String", desc = "Metaspace name "),
                    @FunctionParamDescriptor(name = "spaceName", type = "String", desc = "Unqualified Space name "),
                    @FunctionParamDescriptor(name = "fieldNames", type = "String[]", desc = "String array containing names of the fields. The first field is assumed to be the key field (unless explicitly specified through optional arguments). "),
                    @FunctionParamDescriptor(name = "dataTypes", type = "String[]", desc = "String array containing data types of the fields (such as string, integer, boolean, long, blob etc.) "),
                    @FunctionParamDescriptor(name = "variableArgs", type = "Object ...", desc = "Optional arguments: SpaceDef object, followed key fields (String[]), and finally distribution fields (String[]) ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "newly created or existing space"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Creates a user space using the provided field definitions",
            cautions = "none",
            fndomain = {ACTION},
            example = "Metaspace.createUserSpace(\"ms\", \"order\", String[]{\"OrderId\",\"Name\",\"Items\"}, String[]{\"long\",\"string\",\"string\"}, null, String[]{\"OrderId\",\"Name\"}, String[]{\"Name\"})"
    )
    public static Object createUserSpace (String metaSpaceName, String spaceName, String[] fieldNames, String[] dataTypes, Object ... varargs) {
        Space space = null;
        SpaceDef spaceDef = null;
        Metaspace metaspace = null;
        MetaspaceInfo msInfo = (MetaspaceInfo) getMetaspaceInfo(metaSpaceName);
        try {
            if (msInfo != null) {
                metaspace = msInfo.getMetaSpace();
                if (metaspace.getUserSpaceNames().contains(spaceName) == false) {
                    if ((varargs.length > 0) && (varargs[0] instanceof SpaceDef)) { // User provided space cache/persistence options
                        spaceDef = (SpaceDef) varargs[0];
                        spaceDef.setName(spaceName);
                    }
                    else { // System default space cache/persistence options
                        spaceDef = SpaceDef.create();
                        spaceDef.setName(spaceName);
                        String persistence = System.getProperty(SystemProperty.BACKING_STORE_TYPE.getPropertyName(), "none");
                        // Set persistence type
                        if (persistence.equalsIgnoreCase(BackingStoreConfig.PERSISTENCE_OPTION_SHARED_NOTHING) ||
                            persistence.equalsIgnoreCase(BackingStoreConfig.PERSISTENCE_OPTION_SHARED_NOTHING_ALT)) {
                            spaceDef.setPersistenceType(SpaceDef.PersistenceType.SHARE_NOTHING);
                        }
                        // Set persistence policy
                        spaceDef.setPersistencePolicy(SpaceDef.PersistencePolicy.ASYNC);

                        String replicationCountStr = System.getProperty(SystemProperty.CLUSTER_BACKUP_COUNT.getPropertyName(), "1");
                        String limitedStr = System.getProperty(SystemProperty.CLUSTER_IS_CACHE_LIMITED.getPropertyName(), "false");
                        String capacityStr = System.getProperty(SystemProperty.CLUSTER_LIMITED_SIZE.getPropertyName(), "10000");

                        RuleServiceProvider rsp = null;
                        if (RuleSessionManager.getCurrentRuleSession() != null) {
                            rsp = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider() ;
                        }
                        if (rsp!=null) {
                            replicationCountStr = rsp.getGlobalVariables().substituteVariables(replicationCountStr).toString();
                            limitedStr = rsp.getGlobalVariables().substituteVariables(limitedStr).toString();
                            capacityStr = rsp.getGlobalVariables().substituteVariables(capacityStr).toString();
                        }

                        // Set replication
                        spaceDef.setReplicationCount(Integer.parseInt(replicationCountStr));
                        spaceDef.setReplicationPolicy(ReplicationPolicy.SYNC);
                        // Set limited size
                        boolean limited = Boolean.parseBoolean(limitedStr);
                        if (limited) {
                            spaceDef.setCapacity(Long.parseLong(capacityStr));
                        }
                    }

                    List keyFields = new ArrayList();
                    if ((varargs.length > 1) && (varargs[1] instanceof String[])) { // User provided key fields
                        keyFields = Arrays.asList(varargs[1]);
                    }

                    List distFields = new ArrayList();
                    if ((varargs.length > 2) && (varargs[2] instanceof String[])) { // User provided affinity fields
                        distFields = Arrays.asList(varargs[2]);
                    }

                    for (int i = 0; i < fieldNames.length; i++) {
                        FieldDef fieldDef = FieldDef.create(fieldNames[i],
                                        fieldTypeMap.containsKey(dataTypes[i].toLowerCase()) ?
                                        fieldTypeMap.get(dataTypes[i].toLowerCase()) : FieldType.STRING);
                        if (keyFields.contains(fieldNames[i])) {
                            // Leave for later index creation
                        } else {
                            fieldDef.setNullable(true);
                        }
                        spaceDef.putFieldDef(fieldDef);
                    }

                    // Set key fields
                    if (keyFields.size() == 0) {
                        spaceDef.setKeyDef(KeyDef.create().setFieldNames(fieldNames[0]).setIndexType(IndexType.HASH));
                    }
                    else {
                        spaceDef.setKeyDef(KeyDef.create().setFieldNames((String[])varargs[1]).setIndexType(IndexType.HASH));
                    }

                    // Set affinity fields
                    if (distFields.size() > 0) {
                        spaceDef.setDistributionFields((String[])varargs[2]);
                    }

                    msInfo.getMetaSpace().defineSpace(spaceDef);
                }
                space = msInfo.getMetaSpace().getSpace(spaceName);
                space.setDistributionRole(DistributionRole.LEECH);
                ASSpaceHelper.addSpace(metaSpaceName, spaceName, space);
            }
        }
        catch (Exception e) {
            throw new RuntimeException("createUserSpace() failed", e);
        }
        return space;
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "recoverUserSpace",
            synopsis = "Recovers a user space from shared nothing persistence (can not be used with other persistence types).",
            signature = "long recoverUserSpace (String metaSpaceName, String spaceName)",
            params = {
                    @FunctionParamDescriptor(name = "metaSpaceName", type = "String", desc = "Metaspace name "),
                    @FunctionParamDescriptor(name = "spaceName", type = "String", desc = "Unqualified Space name ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "long", desc = "Number of records recovered (-1 if failure)"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Recovers a user space from shared nothing persistence (can not be used with other persistence types)",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static long recoverUserSpace (String metaSpaceName, String spaceName) {
        long records = -1;
        Space space = null;
        Metaspace metaspace = null;
        MetaspaceInfo msInfo = (MetaspaceInfo) getMetaspaceInfo(metaSpaceName);
        try {
            if (msInfo != null) {
                metaspace = msInfo.getMetaSpace();
                space = msInfo.getMetaSpace().getSpace(spaceName);
                long start = System.currentTimeMillis();
                if (space != null) {
                    RecoveryOptions recoveryOptions = RecoveryOptions.create().setLoadWithData(true);
                    //recoveryOptions.setQuorum(ClusterCapability.MINCACHESERVERS);
                    if (BEManagedThread.tryAcquireReadLock()) {
                        try {
                            metaspace.recoverSpace(spaceName, recoveryOptions);
                            for ( ; !space.waitForReady(30 * 1000); ) {
                                LOGGER.log(Level.INFO, "User space [%s] recovery in progress. Current number of items in space [%d]",
                                        space.getName(), space.size());
                            }
                        }
                        finally {
                            BEManagedThread.releaseReadLock();
                        }
                    }
                    LOGGER.log(Level.INFO, "GridHelper Space [%s] recovery completed in %s ms. Current number of items in space [%d]",
                            space.getName(), (System.currentTimeMillis() - start), space.size());
                    records = space.size();
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException("recoverUserSpace() failed", e);
        }
        return records;
    }
}
