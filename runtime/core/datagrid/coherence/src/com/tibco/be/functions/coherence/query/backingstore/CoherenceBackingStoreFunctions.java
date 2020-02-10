/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.be.functions.coherence.query.backingstore;

import java.util.Iterator;

import com.tibco.be.model.functions.Enabled;

import static com.tibco.be.model.functions.FunctionDomain.*;

@com.tibco.be.model.functions.BEPackage(
		catalog = "Coherence",
        category = "Coherence.Query.BackingStore",
        enabled = @Enabled(property="TIBCO.BE.function.catalog.coherence",value=false),
        synopsis = "Functions for querying the Cache")
public class CoherenceBackingStoreFunctions {

    @com.tibco.be.model.functions.BEFunction(
        name = "registerQuery",
        synopsis = "Flushes the write behind queue for all the distributed cache engines",
        signature = "Object registerQuery(String cacheName, String key, String query, Object[] args)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cacheName", type = "String", desc = ""),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "key", type = "String", desc = ""),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "query", type = "String", desc = ""),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "args", type = "Object[]", desc = "")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Flushes the write behind queue for all the distributed cache engines",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Object registerQuery(String cacheName, String key, String query, Object [] args) {
//        try {
//            RuleSession session = RuleSessionManager.getCurrentRuleSession();
//            if (session != null) {
//                BaseObjectManager om = (BaseObjectManager) session.getObjectManager();
//                if (om instanceof DefaultDistributedCacheBasedStore) {
//                    BEMember cacheMember=((DefaultDistributedCacheBasedStore)om).getCacheMember();
//                    if (cacheMember != null) {
//                        BEMember.QueryHandle handle = cacheMember.registerQueryBackingStore(cacheName, key, query, args);
//                        return handle;
//                    } else {
//                        throw new RuntimeException("Cache Member is Not Ready");
//                    }
//                }else {
//                    throw new RuntimeException("Coherence OM should be configured for this rule Session");
//                }
//            } else {
//                    throw new RuntimeException("Rule Session is NULL");
//            }
//        } catch (Exception ex) {
//            throw new RuntimeException(ex);
//        }
        return null;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "findHandle",
        synopsis = "",
        signature = "Object findHandle(String cacheName, String key)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cacheName", type = "String", desc = ""),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "key", type = "String", desc = "")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Object findHandle(String cacheName, String key) {
//        try {
//            RuleSession session = RuleSessionManager.getCurrentRuleSession();
//            if (session != null) {
//                BaseObjectManager om = (BaseObjectManager) session.getObjectManager();
//                if (om instanceof DefaultDistributedCacheBasedStore) {
//                    BEMember cacheMember=((DefaultDistributedCacheBasedStore)om).getCacheMember();
//                    if (cacheMember != null) {
//                        BEMember.QueryHandle handle = cacheMember.findHandle(cacheName, key);
//                        return handle;
//                    } else {
//                        throw new RuntimeException("Cache Member is Not Ready");
//                    }
//                }else {
//                    throw new RuntimeException("Coherence OM should be configured for this rule Session");
//                }
//            } else {
//                    throw new RuntimeException("Rule Session is NULL");
//            }
//        } catch (Exception ex) {
//            throw new RuntimeException(ex);
//        }
        return null;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "closeQuery",
        synopsis = "",
        signature = "void closeQuery(Object queryHandle)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "queryHandle", type = "Object", desc = "")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void closeQuery(Object queryHandle) {
//        try {
//            RuleSession session = RuleSessionManager.getCurrentRuleSession();
//            if (session != null) {
//                BaseObjectManager om = (BaseObjectManager) session.getObjectManager();
//                if (om instanceof DefaultDistributedCacheBasedStore) {
//                    BEMember cacheMember=((DefaultDistributedCacheBasedStore)om).getCacheMember();
//                    if (cacheMember != null) {
//                        cacheMember.closeQueryBackingStore((BEMember.QueryHandle) queryHandle);
//                    } else {
//                        throw new RuntimeException("Cache Member is Not Ready");
//                    }
//                }else {
//                    throw new RuntimeException("Coherence OM should be configured for this rule Session");
//                }
//            } else {
//                    throw new RuntimeException("Rule Session is NULL");
//            }
//        } catch (Exception ex) {
//            throw new RuntimeException(ex);
//        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getRowCount",
        synopsis = "",
        signature = "int getRowCount(Object queryHandle)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "queryHandle", type = "Object", desc = "")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static int getRowCount(Object queryHandle) {
//        try {
//            return ((BEMember.QueryHandle) queryHandle).getRowCount();
//        } catch (Exception ex) {
//            throw new RuntimeException(ex);
//        }
        return 0;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getRows",
        synopsis = "",
        signature = "Object getRows(Object queryHandle, int startRow, int endRow)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "queryHandle", type = "Object", desc = ""),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "startRow", type = "int", desc = ""),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "endRow", type = "int", desc = "")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Object getRows(Object queryHandle, int startRow, int endRow) {
//        try {
//            RuleSession session = RuleSessionManager.getCurrentRuleSession();
//            if (session != null) {
//                BaseObjectManager om = (BaseObjectManager) session.getObjectManager();
//                if (om instanceof DefaultDistributedCacheBasedStore) {
//                    BEMember cacheMember=((DefaultDistributedCacheBasedStore)om).getCacheMember();
//                    if (cacheMember != null) {
//                        return cacheMember.getResultsQueryBackingStore((BEMember.QueryHandle) queryHandle, startRow, endRow).iterator();
//                    } else {
//                        throw new RuntimeException("Cache Member Is Null");
//                    }
//                } else {
//                    throw new RuntimeException("Coherence OM should be configured for this rule Session");
//                }
//            } else {
//                throw new RuntimeException("Rule Session is NULL");
//            }
//        } catch (Exception ex) {
//            throw new RuntimeException(ex);
//        }
        return 0;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "hasNext",
        synopsis = "",
        signature = "boolean hasNext(Object queryResultSet)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "queryResultSet", type = "Object", desc = "")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static boolean hasNext(Object queryResultSet) {
        try {
            return ((Iterator) queryResultSet).hasNext();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "next",
        synopsis = "",
        signature = "Object[] next(Object queryResultSet)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "queryResultSet", type = "Object", desc = "")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Object[] next(Object queryResultSet) {
        try {
            return (Object[]) ((Iterator) queryResultSet).next();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
