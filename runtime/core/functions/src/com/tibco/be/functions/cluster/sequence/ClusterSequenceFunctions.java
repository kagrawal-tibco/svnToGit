package com.tibco.be.functions.cluster.sequence;

import static com.tibco.be.model.functions.FunctionDomain.*;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.session.sequences.SequenceManager;

/**
 * Created by IntelliJ IDEA.
 * User: kpang
 * Date: Apr 2, 2009
 * Time: 11:22:57 PM
 * To change this template use File | Settings | File Templates.
 */

@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "Cluster.Sequence",
        synopsis = "Functions to operate on Sequence across the cluster")
public class ClusterSequenceFunctions {

    @com.tibco.be.model.functions.BEFunction(
        name = "createSequence",
        synopsis = "Creates a sequence across the cluster. If the sequence already exists, the call is ignored internally.",
        signature = "void createSequence(String sequenceName, long start, long end, int cacheSize, boolean useDB)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sequenceName", type = "String", desc = "A unique key"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "start", type = "long", desc = "The starting point for the sequence"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "end", type = "long", desc = "The end point for the sequence"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cacheSize", type = "int", desc = "The number of entries that will be cached by local instance"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "useDB", type = "boolean", desc = "true if the database should be used to persist the sequence. This flag is only valid for cache aside")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Creates a sequence across the cluster. If the sequence already exists, the call is ignored internally.",
        cautions = "none",
        fndomain = {ACTION, QUERY},
        example = ""
    )
    public final static void createSequence(String sequenceName, long start, long end, int cacheSize, boolean useDB) {
        Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
        if (cluster != null) {
            try {
                if (end > 0) {
                    if (end <= start) {
                        throw new RuntimeException("Sequence " + sequenceName + " cannot be created with end <= start");
                    }
                    if ((end - start) < cacheSize) {
                        throw new RuntimeException("Sequence " + sequenceName + " cannot be created with (end - start) < cacheSize");
                    }
                }

                SequenceManager seqMgr = cluster.getSequenceManager();
                seqMgr.createSequence(sequenceName, start, end > 0 ? end : Long.MAX_VALUE, cacheSize, useDB);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } else {
            throw new RuntimeException("Cluster = NULL");
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "removeSequence",
        synopsis = "Removes a sequence across the cluster",
        signature = "void removeSequence(String sequenceName)",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Removes a sequence across the cluster",
        cautions = "none",
        fndomain = {ACTION, QUERY},
        example = ""
    )
    public final static void removeSequence(String sequenceName) {
        Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
        if (cluster != null) {
            try {
                SequenceManager seqMgr=cluster.getSequenceManager();
                seqMgr.removeSequence(sequenceName);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } else {
            throw new RuntimeException("Cluster = NULL");
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "nextSequence",
        synopsis = "Returns the next sequence number",
        signature = "Long nextSequence(String sequenceName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sequenceName", type = "String", desc = "A unique key")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Long", desc = ""),
        version = "3.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the next sequence number",
        cautions = "none",
        fndomain = {ACTION, QUERY},
        example = ""
    )
    public final static Long nextSequence(String sequenceName) {
        Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
        if (cluster != null) {
            try {
                SequenceManager seqMgr=cluster.getSequenceManager();
                return seqMgr.nextSequence(sequenceName);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } else {
            throw new RuntimeException("Cluster = NULL");
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "resetSequence",
        synopsis = "Resets a sequence across the cluster",
        signature = "void resetSequence(String sequenceName, long start)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sequenceName", type = "String", desc = "A unique key")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Resets a sequence across the cluster",
        cautions = "none",
        fndomain = {ACTION, QUERY},
        example = ""
    )
    public final static void resetSequence(String sequenceName, long start) {
        Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
        if (cluster != null) {
            try {
                SequenceManager seqMgr=cluster.getSequenceManager();
                seqMgr.resetSequence(sequenceName, start);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } else {
            throw new RuntimeException("Cluster = NULL");
        }
    }
}
