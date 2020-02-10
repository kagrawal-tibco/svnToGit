/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.be.functions.coherence.filters;


import com.tangosol.util.Filter;
import com.tangosol.util.ValueExtractor;
import com.tangosol.util.filter.*;

import static com.tibco.be.model.functions.FunctionDomain.*;

import com.tibco.be.model.functions.Enabled;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.om.impl.coherence.cluster.util.BetweenLimitIndexAwareFilter;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;

import java.io.Serializable;
import java.util.HashSet;

@com.tibco.be.model.functions.BEPackage(
		catalog = "Coherence",
        category = "Coherence.Filters",
        enabled = @Enabled(property="TIBCO.BE.function.catalog.coherence",value=false),
        synopsis = "Functions for querying the Cache")
public class CoherenceFilterFunctions {

    @com.tibco.be.model.functions.BEFunction(
        name = "C_Equals",
        synopsis = "An equality filter",
        signature = "Object C_Equals(Object value1, Object value2)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value1", type = "Object", desc = "LHS (ChainedExtractor returned by C_XXXAtomGetter)"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value2", type = "Object", desc = "RHS (Constants returned by C_XXXConstant)")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "An equality filter",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Object C_Equals(Object value1, Object value2){
        if (value1 instanceof ValueExtractor) {
            ValueExtractor ve = (ValueExtractor) value1;
            return new EqualsFilter(ve,value2);
        } else {
            throw new RuntimeException("Value1 Expected to be a ChainedExtractor " + value1);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_GreaterEquals",
        synopsis = "A greater than or equals filter",
        signature = "Object C_GreaterEquals(Object value1, Object value2)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value1", type = "Object", desc = "LHS (ChainedExtractor returned by C_XXXAtomGetter)"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value2", type = "Object", desc = "RHS (Constants returned by C_XXXConstant)")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "A greater than or equals filter",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Object C_GreaterEquals(Object value1, Object value2){
        if (value1 instanceof ValueExtractor) {
            ValueExtractor ve = (ValueExtractor) value1;
            return new GreaterEqualsFilter(ve,(Comparable) value2);
        } else {
            throw new RuntimeException("Value1 Expected to be a ChainedExtractor " + value1);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_GreaterThan",
        synopsis = "A greater than filter",
        signature = "Object C_GreaterThan(Object value1, Object value2)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value1", type = "Object", desc = "LHS (ChainedExtractor returned by C_XXXAtomGetter)"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value2", type = "Object", desc = "RHS (Constants returned by C_XXXConstant)")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "A greater than filter",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Object C_GreaterThan(Object value1, Object value2){
        if (value1 instanceof ValueExtractor) {
            ValueExtractor ve = (ValueExtractor) value1;
            return new GreaterFilter(ve,(Comparable) value2);
        } else {
            throw new RuntimeException("Value1 Expected to be a ChainedExtractor " + value1);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_LessThan",
        synopsis = "A less than filter",
        signature = "Object C_LessThan(Object value1, Object value2)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value1", type = "Object", desc = "LHS (ChainedExtractor returned by C_XXXAtomGetter)"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value2", type = "Object", desc = "RHS (Constants returned by C_XXXConstant)")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "A less than filter",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Object C_LessThan(Object value1, Object value2){
        if (value1 instanceof ValueExtractor) {
            ValueExtractor ve = (ValueExtractor) value1;
            return new LessFilter(ve,(Comparable) value2);
        } else {
            throw new RuntimeException("Value1 Expected to be a ChainedExtractor " + value1);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_LessThanEquals",
        synopsis = "A less than or equals filter",
        signature = "Object C_LessThanEquals(Object value1, Object value2)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value1", type = "Object", desc = "LHS (ChainedExtractor returned by C_XXXAtomGetter)"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value2", type = "Object", desc = "RHS (Constants returned by C_XXXConstant)")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "A less than or equals filter",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Object C_LessThanEquals(Object value1, Object value2){
        if (value1 instanceof ValueExtractor) {
            ValueExtractor ve = (ValueExtractor) value1;
            return new LessEqualsFilter(ve,(Comparable) value2);
        } else {
            throw new RuntimeException("Value1 Expected to be a ChainedExtractor " + value1);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_NotEquals",
        synopsis = "An inequality filter",
        signature = "Object C_NotEquals(Object value1, Object value2)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value1", type = "Object", desc = "LHS (ChainedExtractor returned by C_XXXAtomGetter)"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value2", type = "Object", desc = "RHS (Constants returned by C_XXXConstant)")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "An inequality filter",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Object C_NotEquals(Object value1, Object value2){
        if (value1 instanceof ValueExtractor) {
            ValueExtractor ve = (ValueExtractor) value1;
            return new NotEqualsFilter(ve,value2);
        } else {
            throw new RuntimeException("Value1 Expected to be a ChainedExtractor " + value1);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_RuleFunction",
        synopsis = "Use specified rule function as a filter",
        signature = "Object C_RuleFunction(String className, String ruleFunctionURI, Object[] args)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "className", type = "String", desc = "Name of the class of Rule function"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ruleFunctionURI", type = "String", desc = "Name of the Rule function"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "args", type = "Object[]", desc = "Arguments that are passed as is to the rule function")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Use specified rule function as a filter",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Object C_RuleFunction(String className, String ruleFunctionURI, Object [] args){
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        return new RuleFunctionFilter(className, ruleFunctionURI, args);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_And",
        synopsis = "A logical And filter",
        signature = "Object C_And(Object filter1, Object filter2)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "filter1", type = "Object", desc = "LHS"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "filter2", type = "Object", desc = "RHS")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "A logical And filter",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Object C_And(Object filter1, Object filter2){
        if ((filter1 instanceof Filter) && (filter2 instanceof Filter))
        {
            return new AndFilter((Filter) filter1, (Filter) filter2);
        } else {
            throw new RuntimeException("Unsupported Filter Type " + filter1.getClass().getName() + ", " + filter1.getClass().getName());
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_All",
        synopsis = "An All filter",
        signature = "Object C_All(Filter[] filter)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "filters", type = "Object[]", desc = "LHS")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "An All filter",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Object C_All(Object [] filters){
        Filter[] fs= new Filter[filters.length];
        for(int i=0; i < filters.length; i++) {
            fs[i]= (Filter) filters[i];
        }
        return new AllFilter(fs);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_Any",
        synopsis = "An Any filter; e.g., (F1 || F2 || F3)",
        signature = "Object C_Any(Filter[] filter)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "filters", type = "Object[]", desc = "LHS")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "An Any filter; e.g., (F1 || F2 || F3)",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Object C_Any(Object [] filters){
        Filter[] fs= new Filter[filters.length];
        for(int i=0; i < filters.length; i++) {
            fs[i]= (Filter) filters[i];
        }
        return new AnyFilter(fs);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_ContainsString",
        synopsis = "IN {list of string values}",
        signature = "Object C_ContainsString(Object value1, String[] sValues)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value1", type = "Object", desc = "LHS"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sValues", type = "String[]", desc = "RHS")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "IN {list of string values}",
        cautions = "none",
        enabled=@Enabled(property="TIBCO.BE.function.catalog.coherence.filters.ext",value=false),
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Object C_ContainsString(Object value1, String[] sValues){
        if (value1 instanceof ValueExtractor) {
            ValueExtractor ve = (ValueExtractor) value1;
            HashSet set = new HashSet();
            for (int i=0; i < sValues.length; i++) {
                set.add(sValues[i]);
            }
            return new ContainsAllFilter(ve,set);
        } else {
            throw new RuntimeException("Value1 Expected to be a ChainedExtractor " + value1);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_ContainsInt",
        synopsis = "IN {list of int values}",
        signature = "Object C_ContainsInt(Object value1, String[] iValues)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value1", type = "Object", desc = "LHS"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "iValues", type = "int[]", desc = "RHS")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "IN {list of int values}",
        cautions = "none",
        enabled=@Enabled(property="TIBCO.BE.function.catalog.coherence.filters.ext",value=false),
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Object C_ContainsInt(Object value1, int[] iValues){
        if (value1 instanceof ValueExtractor) {
            ValueExtractor ve = (ValueExtractor) value1;
            HashSet set = new HashSet();
            for (int i=0; i < iValues.length; i++) {
                set.add(new Integer(iValues[i]));
            }
            return new ContainsAllFilter(ve,set);
        } else {
            throw new RuntimeException("Value1 Expected to be a ChainedExtractor " + value1);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_ContainsLong",
        synopsis = "IN {list of long values}",
        signature = "Object C_ContainsLong(Object value1, String[] lValues)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value1", type = "Object", desc = "LHS"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "lValues", type = "long[]", desc = "RHS")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "IN {list of long values}",
        cautions = "none",
        enabled=@Enabled(property="TIBCO.BE.function.catalog.coherence.filters.ext",value=false),
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Object C_ContainsLong(Object value1, long[] lValues){
        if (value1 instanceof ValueExtractor) {
            ValueExtractor ve = (ValueExtractor) value1;
            HashSet set = new HashSet();
            for (int i=0; i < lValues.length; i++) {
                set.add(new Long(lValues[i]));
            }
            return new ContainsAllFilter(ve,set);
        } else {
            throw new RuntimeException("Value1 Expected to be a ChainedExtractor " + value1);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_ContainsDouble",
        synopsis = "IN {list of double values}",
        signature = "Object C_ContainsDouble(Object value1, String[] dValues)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value1", type = "Object", desc = "LHS"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "dValues", type = "double[]", desc = "RHS")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "IN {list of double values}",
        cautions = "none",
        enabled=@Enabled(property="TIBCO.BE.function.catalog.coherence.filters.ext",value=false),
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Object C_ContainsDouble(Object value1, double[] dValues){
        if (value1 instanceof ValueExtractor) {
            ValueExtractor ve = (ValueExtractor) value1;
            HashSet set = new HashSet();
            for (int i=0; i < dValues.length; i++) {
                set.add(new Double(dValues[i]));
            }
            return new ContainsAllFilter(ve,set);
        } else {
            throw new RuntimeException("Value1 Expected to be a ChainedExtractor " + value1);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_NextPage",
        synopsis = "Move to the next page",
        signature = "Object C_NextPage(Object filter)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "filter", type = "Object", desc = "Filter used to truncate the results returned by another filter")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Move to the next page",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Object C_NextPage(Object filter) {
        ((LimitFilter) filter).nextPage();
        return filter;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_PreviousPage",
        synopsis = "Move to the previous page",
        signature = "Object C_PreviousPage(Object filter)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "filter", type = "Object", desc = "Filter used to truncate the results returned by another filter")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Move to the previous page",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Object C_PreviousPage(Object filter) {
        ((LimitFilter) filter).previousPage();
        return filter;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_CurrentPage",
        synopsis = "Obtain a current page number (zero-based).",
        signature = "int C_CurrentPage(Object filter)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "filter", type = "Object", desc = "Filter used to truncate the results returned by another filter")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Obtain a current page number (zero-based).",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static int C_CurrentPage(Object filter) {
        return ((LimitFilter) filter).getPage();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_Limit",
        synopsis = "Filter which truncates the results of another filter. This filter is a mutable object that is modified by the query processor.\nClients are supposed to hold a reference to this filter and repetitively pass it to query methods with a desired page size (expressed as a number of entries per page).",
        signature = "Object C_Limit(Object filter, int pageSize)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "filter", type = "Object", desc = "Filter used to truncate the results returned by another filter"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pageSize", type = "int", desc = "Number of entries per page")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Filter which truncates the results of another filter. This filter is a mutable object that is modified by the query processor.\nClients are supposed to hold a reference to this filter and repetitively pass it to query methods with a desired page size (expressed as a number of entries per page).",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Object C_Limit(Object filter, int pageSize) {
        return new LimitFilter((Filter) filter, pageSize);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_BetweenLimitIndexedString",
        synopsis = "Optimized filter that uses the ordered Index for filtering items whose indexed\nvalues are between the given start and end values. Limits the results to the specified number.\nThe Index must be ordered. The valueExtractor provided must be the same as the one used\nto create the Index. The startValue is inclusive and the endValue is exclusive. The endValue\ncan be same as the startValue if needed.",
        signature = "Object C_BetweenLimitIndexedString(String startValue, String endValue, int\nmaxResults, Object valueExtractor)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "startValue", type = "String", desc = "Ordered Index value to define the beginning of the filter. Inclusive."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "endValue", type = "String", desc = "Ordered Index value to define the end of the filter. Exclusive."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "maxResults", type = "int", desc = "Maximum results to return."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "valueExtractor", type = "Object", desc = "Extractor function must be the same as the one used to create the Index.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "The filter."),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Optimized filter that uses the ordered Index for filtering items whose indexed\nvalues are between the given start and end values. Limits the results to the specified number.\nThe Index must be ordered. The valueExtractor provided must be the same as the one used\nto create the Index. The startValue is inclusive and the endValue is exclusive. The endValue\ncan be same as the startValue if needed.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Object C_BetweenLimitIndexedString(String startValue, String endValue,
                                                     int maxResults, Object valueExtractor) {
        return new BetweenLimitIndexAwareFilter(startValue, endValue,
                maxResults, (ValueExtractor) valueExtractor);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_BetweenLimitIndexedLong",
        synopsis = "Optimized filter that uses the ordered Index for filtering items whose indexed\nvalues are between the given start and end values. Limits the results to the specified number.\nThe Index must be ordered. The valueExtractor provided must be the same as the one used\nto create the Index. The startValue is inclusive and the endValue is exclusive. The endValue\ncan be same as the startValue if needed.",
        signature = "Object C_BetweenLimitIndexedLong(long startValue, long endValue, int maxResults,\nObject valueExtractor)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "startValue", type = "long", desc = "Ordered Index value to define the beginning of the filter. Inclusive."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "endValue", type = "long", desc = "Ordered Index value to define the end of the filter. Exclusive."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "maxResults", type = "int", desc = "Maximum results to return."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "valueExtractor", type = "Object", desc = "Extractor function must be the same as the one used to create the Index.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "The filter."),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Optimized filter that uses the ordered Index for filtering items whose indexed\nvalues are between the given start and end values. Limits the results to the specified number.\nThe Index must be ordered. The valueExtractor provided must be the same as the one used\nto create the Index. The startValue is inclusive and the endValue is exclusive. The endValue\ncan be same as the startValue if needed.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Object C_BetweenLimitIndexedLong(long startValue, long endValue,
                                                   int maxResults, Object valueExtractor) {
        return new BetweenLimitIndexAwareFilter(startValue, endValue,
                maxResults, (ValueExtractor) valueExtractor);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_BetweenLimitIndexedDouble",
        synopsis = "Optimized filter that uses the ordered Index for filtering items whose indexed\nvalues are between the given start and end values. Limits the results to the specified number.\nThe Index must be ordered. The valueExtractor provided must be the same as the one used\nto create the Index. The startValue is inclusive and the endValue is exclusive. The endValue\ncan be same as the startValue if needed.",
        signature = "Object C_BetweenLimitIndexedDouble(double startValue, double endValue, int\nmaxResults, Object valueExtractor)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "startValue", type = "double", desc = "Ordered Index value to define the beginning of the filter. Inclusive."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "endValue", type = "double", desc = "Ordered Index value to define the end of the filter. Exclusive."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "maxResults", type = "int", desc = "Maximum results to return."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "valueExtractor", type = "Object", desc = "Extractor function must be the same as the one used to create the Index.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "The filter."),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Optimized filter that uses the ordered Index for filtering items whose indexed\nvalues are between the given start and end values. Limits the results to the specified number.\nThe Index must be ordered. The valueExtractor provided must be the same as the one used\nto create the Index. The startValue is inclusive and the endValue is exclusive. The endValue\ncan be same as the startValue if needed.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Object C_BetweenLimitIndexedDouble(double startValue, double endValue,
                                                     int maxResults, Object valueExtractor) {
        return new BetweenLimitIndexAwareFilter(startValue, endValue,
                maxResults, (ValueExtractor) valueExtractor);
    }

    public static class RuleFunctionFilter implements Filter, Serializable{
        String ruleFunctionURI;
        String className;
        transient RuleFunction ruleFunction;
        Object [] arguments;

        public RuleFunctionFilter() {
        }

        public RuleFunctionFilter (String className, String ruleFunctionURI, Object [] args) {
            this.ruleFunctionURI= ruleFunctionURI;
            this.className=className;
            this.arguments = new Object [args.length+1];
            for (int i=0; i < args.length; i++) {
                arguments[i+1]=args[i];
            }
        }

        public boolean evaluate(Object object) {
            try {
                Cluster cluster= CacheClusterProvider.getInstance().getCacheCluster();
                if (cluster != null) {
                    if(object instanceof Concept) {
                        arguments[0]= object;
                    }
                    else if (object instanceof Event) {
                        arguments[0] = object;
                    } else {
                        return false;
                    }

                    RuleSession session = fetchRuleSession(cluster);

                    Object ret = session.invokeFunction(ruleFunctionURI, arguments, false);
                    return ((Boolean) ret);
                } else {
                    throw new RuntimeException("Cluster Not Registered, Not Possible");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        }
    }

    public static RuleSession fetchRuleSession(Cluster cluster) {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();

        if (session == null) {
            RuleSession[] ruleSessions = cluster.getRuleServiceProvider().getRuleRuntime().getRuleSessions();
            //Just pick one.
            session = ruleSessions[0];

            //Oh..still null. Oops!
            if (session == null) {
                throw new RuntimeException("RuleSession is null");
            }
        }

        return session;
    }
}
