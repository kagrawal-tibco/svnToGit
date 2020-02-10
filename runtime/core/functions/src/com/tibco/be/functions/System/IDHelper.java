/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 26/8/2010
 */

package com.tibco.be.functions.System;

import static com.tibco.be.model.functions.FunctionDomain.*;

import com.tibco.cep.kernel.service.NamedIdGenerator;
import com.tibco.cep.runtime.service.om.ObjectBasedStore;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.session.impl.NamedIdGeneratorImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Oct 15, 2008
 * Time: 12:34:15 PM
 * To change this template use File | Settings | File Templates.
 */

@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "System.ID",
        synopsis = "System Wide Functions")
public class IDHelper {

    //private static final Map<String, NamedIdGenerator> genMap = Collections.synchronizedMap(new HashMap<String, NamedIdGenerator>());

	private static final Map<String, NamedIdGenerator> genMap = new ConcurrentHashMap<String, NamedIdGenerator>();


    @com.tibco.be.model.functions.BEFunction(
        name = "nextId",
        synopsis = "For In Memory object management, it returns a system generated unique id for a given sequence name.",
        signature = "long nextId(String name)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "name", type = "String", desc = "a specified sequence name.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "long", desc = ""),
        version = "3.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "For In Memory object management, it returns a system generated unique id for a given sequence name. It is only supported for in-memory objects, not cache-based.",
        cautions = "",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static long nextId(String name) {
        NamedIdGenerator idgen = null;
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        try {
            if (!genMap.containsKey(name)) {
                if (session.getObjectManager() instanceof ObjectBasedStore) {
                    //Suresh TODO: disabled
                    //idgen = new ClusterIdGenerator(name);
                    throw new RuntimeException("Class deprecated...");
                } else {
                    idgen = new NamedIdGeneratorImpl(name);
                }
                genMap.put(name, idgen);
            } else {
                idgen = genMap.get(name);
            }
            return idgen.nextEntityId();

        } catch (Throwable e) {
            e.printStackTrace();
        }
        return -1;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "reset",
        synopsis = "Sets the sequence identified by the given name to the given value if the given value is greater than the current value.",
        signature = "void reset(String name, long value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "name", type = "String", desc = "a specified sequence name."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "sequence", desc = "id value")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sets the sequence identified by the given name to the given value if the given value is greater than the current value.",
        cautions = "",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static void reset(String name,long value) {
        NamedIdGenerator idgen = null;
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        try {
            if (!genMap.containsKey(name)) {
                throw new RuntimeException("Sequence Id generator identified by name="+name+" does not exist.");
            } else {
                idgen = genMap.get(name);
            }
            idgen.setMinEntityId(value);

        } catch (Throwable e) {
            e.printStackTrace();
        }
        return;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "remove",
        synopsis = "Removes the sequence identified by the given name.",
        signature = "void remove(String name)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "name", type = "String", desc = "a specified sequence name.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Removes the sequence identified by the given name.",
        cautions = "",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static void remove(String name) {
        NamedIdGenerator idgen = null;
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        try {
            if (!genMap.containsKey(name)) {
                throw new RuntimeException("Sequence Id generator identified by name="+name+" does not exist.");
            } else {
                idgen = genMap.remove(name);
            }
            idgen.remove();

        } catch (Throwable e) {
            e.printStackTrace();
        }
        return;
    }

//    /**
//     * @param name String a specified sequence name.
//     * @.name lastId
//     * @.synopsis Returns a last unique id that was generated for a given sequence name.
//     * @.signature long lastId(String name)
//     * @.version 3.0
//     * @.see
//     * @.mapper false
//     * @.description Returns a last unique id that was generated for a given sequence name.
//     * @.domain action
//     * @.example
//     */
//    public static long lastId(String name) {
//        NamedIdGenerator idgen = null;
//        RuleSession session = RuleSessionManager.getCurrentRuleSession();
//        try {
//            if (!genMap.containsKey(name)) {
//                if (session.getObjectManager() instanceof ObjectBasedStore) {
//                    idgen = new ClusterIdGenerator(name);
//                } else {
//                    idgen = new NamedIdGeneratorImpl(name);
//                }
//                genMap.put(name, idgen);
//            } else {
//                idgen = genMap.get(name);
//            }
//            return idgen.lastEntityId();
//
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }
//        return -1;
//    }
}
