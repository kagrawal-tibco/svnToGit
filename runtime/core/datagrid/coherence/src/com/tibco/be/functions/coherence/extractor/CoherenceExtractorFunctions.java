/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 11/8/2010
 */

package com.tibco.be.functions.coherence.extractor;

import com.tangosol.util.ValueExtractor;
import com.tangosol.util.extractor.AbstractExtractor;
import com.tangosol.util.extractor.ChainedExtractor;
import com.tangosol.util.extractor.ReflectionExtractor;
import com.tibco.be.functions.coherence.DeserializationDecorator;

import static com.tibco.be.model.functions.FunctionDomain.*;

import com.tibco.be.model.functions.Enabled;
import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;

import java.io.Serializable;

@com.tibco.be.model.functions.BEPackage(
		catalog = "Coherence",
        category = "Coherence.Extractors",
        enabled = @Enabled(property="TIBCO.BE.function.catalog.coherence",value=false),
        synopsis = "Functions for querying the Cache")
public class CoherenceExtractorFunctions {

    @com.tibco.be.model.functions.BEFunction(
        name = "C_StringAtomGetter",
        synopsis = "Returns the <i>ValueExtractor</i> of PropertyAtomString <code>propertyName</code>",
        signature = "Object C_StringAtomGetter(String propertyName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyName", type = "String", desc = "Property name")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "<i>ValueExtractor</i> object"),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the <i>ValueExtractor</i> of PropertyAtomString <code>propertyName</code>",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Object C_StringAtomGetter(String propertyName) {
        try {
            ValueExtractor[] input = new ValueExtractor[]{
                    new ReflectionExtractor("getProperty", new Object[]{propertyName}),
                    new ReflectionExtractor("getString")};
            return new ChainedExtractor(input);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_StringGetter",
        synopsis = "Returns the <i>ValueExtractor</i> for the non-atom String property <code>propertyName</code>.",
        signature = "Object C_StringGetter(String propertyName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyName", type = "String", desc = "Property name")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "<i>ValueExtractor</i> object"),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the <i>ValueExtractor</i> for the event String property <code>propertyName</code>.",
        cautions = "none",
        enabled=@Enabled(property="TIBCO.BE.function.catalog.coherence.extractors.nonAtomGetters", value=false),
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Object C_StringGetter(String propertyName) {
        try {
            return new ReflectionExtractor("getProperty", new Object[]{propertyName});
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "C_IntAtomGetter",
        synopsis = "Returns the <i>ValueExtractor</i> of PropertyAtomInt <code>propertyName</code>",
        signature = "Object C_IntAtomGetter(String propertyName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyName", type = "String", desc = "Property name")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "<i> ValueExtractor </i> object"),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the <i>ValueExtractor</i> of PropertyAtomInt <code>propertyName</code>",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Object C_IntAtomGetter(String propertyName) {
        try {
            ValueExtractor[] input = new ValueExtractor[]{
                    new ReflectionExtractor(
                            "getProperty", new Object[]{propertyName}),
                    new ReflectionExtractor("getInt")};
            return new ChainedExtractor(input);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "C_IntGetter",
        synopsis = "Returns the <i>ValueExtractor</i> for the non-atom Int property <code>propertyName</code>",
        signature = "Object C_IntGetter(String propertyName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyName", type = "String", desc = "Property name")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "<i> ValueExtractor </i> object"),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the <i>ValueExtractor</i> for the non-atom Int property <code>propertyName</code>",
        cautions = "none",
        enabled=@Enabled(property="TIBCO.BE.function.catalog.coherence.extractors.nonAtomGetters",value=false),
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Object C_IntGetter(String propertyName) {
        try {
            return new ReflectionExtractor("getProperty", new Object[]{propertyName});
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "C_DoubleAtomGetter",
        synopsis = "Return the <i>ValueExtractor</i> of PropertyAtomDouble <code>propertyName</code>",
        signature = "Object C_DoubleAtomGetter(String propertyName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyName", type = "String", desc = "Property name")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "<i>ValueExtractor</i> object"),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Return the <i>ValueExtractor</i> of PropertyAtomDouble <code>propertyName</code>",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Object C_DoubleAtomGetter(String propertyName) {
        try {
            ValueExtractor[] input = new ValueExtractor[]{
                    new ReflectionExtractor("getProperty", new Object[]{propertyName}),
                    new ReflectionExtractor("getDouble")};
            return new ChainedExtractor(input);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "C_DoubleGetter",
        synopsis = "Returns the <i>ValueExtractor</i> of non-atom double Property <code>propertyName</code>",
        signature = "Object C_DoubleGetter(String propertyName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyName", type = "String", desc = "Property name")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "<i>ValueExtractor</i> object"),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the <i>ValueExtractor</i> of non-atom double Property <code>propertyName</code>",
        cautions = "none",
        enabled=@Enabled(property="TIBCO.BE.function.catalog.coherence.extractors.nonAtomGetters",value=false),
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Object C_DoubleGetter(String propertyName) {
        try {
            return new ReflectionExtractor("getProperty", new Object[]{propertyName});
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "C_LongAtomGetter",
        synopsis = "Return the <i>ValueExtractor</i> of PropertyAtomLong <code>propertyName</code>",
        signature = "Object C_LongAtomGetter(String propertyName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyName", type = "String", desc = "Property name")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "<i>ValueExtractor</i> object"),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Return the <i>ValueExtractor</i> of PropertyAtomLong <code>propertyName</code>",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Object C_LongAtomGetter(String propertyName) {
        try {
            ValueExtractor[] input = new ValueExtractor[]{
                    new ReflectionExtractor("getProperty", new Object[]{propertyName}),
                    new ReflectionExtractor("getLong")};
            return new ChainedExtractor(input);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_LongGetter",
        synopsis = "Returns the <i>ValueExtractor</i> of non-atom long property <code>propertyName</code>",
        signature = "Object C_LongGetter(String propertyName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyName", type = "String", desc = "Property name")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "<i>ValueExtractor</i> object"),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the <i>ValueExtractor</i> of non-atom long property <code>propertyName</code>",
        cautions = "none",
        enabled=@Enabled(property="TIBCO.BE.function.catalog.coherence.extractors.nonAtomGetters",value=false),
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Object C_LongGetter(String propertyName) {
        try {
            return new ReflectionExtractor("getProperty", new Object[]{propertyName});
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_DateTimeAtomGetter",
        synopsis = "Returns the <i>ValueExtractor</i> of PropertyAtomDateTime <code>propertyName</code>",
        signature = "Object C_DateTimeAtomGetter(String propertyName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyName", type = "String", desc = "Property name")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "<i>ValueExtractor</i> object"),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the <i>ValueExtractor</i> of PropertyAtomDateTime <code>propertyName</code>",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Object C_DateTimeAtomGetter(String propertyName) {
        try {
            ValueExtractor[] input = new ValueExtractor[]{
                    new ReflectionExtractor("getProperty", new Object[]{propertyName}),
                    new ReflectionExtractor("getDateTime")};
            return new ChainedExtractor(input);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "C_DateTimeGetter",
        synopsis = "Returns the <i>ValueExtractor</i> of the non-atom DateTime property <code>propertyName</code>",
        signature = "Object C_DateTimeGetter(String propertyName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyName", type = "String", desc = "Property name")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "<i>ValueExtractor</i> object"),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the <i>ValueExtractor</i> of the non-atom DateTime property <code>propertyName</code>",
        cautions = "none",
        enabled=@Enabled(property="TIBCO.BE.function.catalog.coherence.extractors.nonAtomGetters",value=false),
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Object C_DateTimeGetter(String propertyName) {
        try {
            return new ReflectionExtractor("getProperty", new Object[]{propertyName});
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "C_BooleanAtomGetter",
        synopsis = "Returns the <i>ValueExtractor</i> of PropertyAtomBoolean <code>propertyName</code>",
        signature = "Object C_BooleanAtomGetter(String propertyName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyName", type = "String", desc = "Property name")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "<i>ValueExtractor</i> object"),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the <i>ValueExtractor</i> of PropertyAtomBoolean <code>propertyName</code>",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Object C_BooleanAtomGetter(String propertyName) {
        try {
            ValueExtractor[] input = new ValueExtractor[]{
                    new ReflectionExtractor("getProperty", new Object[]{propertyName}),
                    new ReflectionExtractor("getBoolean")};
            return new ChainedExtractor(input);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "C_BooleanGetter",
        synopsis = "Returns the <i>ValueExtractor</i> of the non-atom Boolean property <code>propertyName</code>",
        signature = "Object C_BooleanGetter(String propertyName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyName", type = "String", desc = "Property name")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "<i>ValueExtractor</i> object"),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the <i>ValueExtractor</i> of the non-atom Boolean property <code>propertyName</code>",
        cautions = "none",
        enabled=@Enabled(property="TIBCO.BE.function.catalog.coherence.extractors.nonAtomGetters",value=false),
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Object C_BooleanGetter(String propertyName) {
        try {
            return new ReflectionExtractor("getProperty", new Object[]{propertyName});
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_EntityExtIdGetter",
        synopsis = "Returns an <i>Extractor</i> to retrieve the $1ExtId$1 of an <i>Entity</i>",
        signature = "Object C_EntityExtIdGetter()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "<i>ValueExtractor</i> object"),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns an <i>Extractor</i> to retrieve the $1ExtId$1 of an <i>Entity</i>",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Object C_EntityExtIdGetter() {
        return new ReflectionExtractor("getExtId");
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_RuleFunctionGetter",
        synopsis = "Returns the <i>ValueExtractor</i> of PropertyAtomString <code>ruleFunctionName</code>",
        signature = "Object C_RuleFunctionGetter(String ruleFunctionName, Object[] args)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ruleFunctionName", type = "String", desc = "Rule function URI"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "args", type = "Object[]", desc = "Arguments passed to the rule function")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "<i>ValueExtractor</i> object"),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the <i>ValueExtractor</i> of PropertyAtomString <code>ruleFunctionName</code>",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Object C_RuleFunctionGetter(String ruleFunctionName, Object[] args) {
        try {
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            return new RuleFunctionExtractor(ruleFunctionName, args);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_EventPropertyGetter",
        synopsis = "Returns the <i>ValueExtractor</i> of PropertyAtomString",
        signature = "Object C_EventPropertyGetter(String propertyName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyName", type = "String", desc = "(PropertyName)")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "<i> ValueExtractor </i> object"),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the <i>ValueExtractor</i> of PropertyAtomString",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Object C_EventPropertyGetter(String propertyName) {
        try {
            ValueExtractor[] input = new ValueExtractor[]{new ReflectionExtractor("getProperty", new Object[]{propertyName})};

            return new ChainedExtractor(input);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static class RuleFunctionExtractor extends AbstractExtractor implements Serializable {
        String ruleFunctionURI;
        Object[] arguments;
        transient RuleFunction ruleFunction;

        public RuleFunctionExtractor(String ruleFunctionURI, Object[] args) {
            this.ruleFunctionURI = ruleFunctionURI;
            this.arguments = new Object[args.length + 1];
            for (int i = 0; i < args.length; i++) {
                arguments[i + 1] = args[i];
            }
        }

        public Object extract(Object object) {
            try {
                Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
                if (cluster != null) {
                    object = DeserializationDecorator.deserialize(object);

                    if (object instanceof Concept) {
                        arguments[0] = object;
                    } else if (object instanceof Event) {
                        arguments[0] = object;
                    } else {
                        return null;
                    }
                    //todo AJ:return cluster.invokeRuleFunction(ruleFunctionURI, arguments);
                    return null; //todo AJ:for compiler
                } else {
                    throw new RuntimeException("Cluster Not Registered, Not Possible");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        }
    }
}
