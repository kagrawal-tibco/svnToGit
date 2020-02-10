package com.tibco.cep.query.stream.impl.rete.integ.filter.coherence;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.tangosol.util.Filter;
import com.tangosol.util.ValueExtractor;
import com.tangosol.util.filter.AllFilter;
import com.tangosol.util.filter.ExtractorFilter;
import com.tangosol.util.filter.IndexAwareFilter;
import com.tibco.cep.query.service.QueryProperty;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.core.Registry;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.expression.TupleValueExtractor;
import com.tibco.cep.query.stream.impl.expression.EvaluatorToExtractorAdapter;
import com.tibco.cep.query.stream.impl.rete.expression.ReteEntityFilter;
import com.tibco.cep.query.stream.impl.rete.expression.ReteEntityFilterImpl;
import com.tibco.cep.query.stream.impl.rete.integ.Manager;
import com.tibco.cep.query.stream.impl.rete.integ.filter.FilterBuilderContext;
import com.tibco.cep.query.stream.impl.rete.integ.filter.FilterBuilderContextImpl;
import com.tibco.cep.query.stream.impl.rete.integ.filter.FilterStrategy;
import com.tibco.cep.query.stream.impl.rete.integ.filter.InterpretingFilter;
import com.tibco.cep.runtime.service.om.impl.coherence.cluster.CoherenceNonIndexedFilter;

/*
* Author: Ashwin Jayaprakash Date: Jun 16, 2008 Time: 5:29:28 PM
*/
public class CoherenceFilterHelper {
    private static volatile Boolean isFilterOptimizerEnabled;

    public static synchronized boolean isFilterOptimizationEnabled() {
        if (isFilterOptimizerEnabled == null) {
            synchronized (CoherenceFilterHelper.class) {
                if (isFilterOptimizerEnabled == null) {
                    Manager manager = Registry.getInstance().getComponent(Manager.class);
                    Properties properties = manager.getProperties();

                    String optimizerStr = properties.getProperty(QueryProperty.FILTER_OPTIMIZER.getPropName(), "false");
                    isFilterOptimizerEnabled = Boolean.parseBoolean(optimizerStr);

                    Registry.getInstance().register(FilterHelperCache.class, new FilterHelperCache());
                }
            }
        }

        return isFilterOptimizerEnabled.booleanValue();
    }

    protected static synchronized Filter convert(GlobalContext globalContext, QueryContext queryContext,
                                                 ReteEntityFilter entityFilter, FilterStrategy filterStrategy) {
        if (entityFilter instanceof ReteEntityFilterImpl) {
            ReteEntityFilterImpl reteEntityFilter = (ReteEntityFilterImpl) entityFilter;
            TupleValueExtractor extractor = reteEntityFilter.getExtractor();

            if (extractor instanceof EvaluatorToExtractorAdapter) {
                EvaluatorToExtractorAdapter evalAdapter = (EvaluatorToExtractorAdapter) extractor;
                ExpressionEvaluator evaluator = evalAdapter.getEvaluator();
                // Now process this expression evaluator
                FilterBuilderContext<Filter> context = new FilterBuilderContextImpl<Filter>(filterStrategy);
                context.buildFilters(evaluator);

                return context.getFilter();
            }
        }

        InterpretingFilter interpretingFilter = new InterpretingFilter(entityFilter, globalContext, queryContext);

        return new CoherenceNonIndexedFilter(interpretingFilter);
    }

    public static synchronized AllFilter convert(FilterStrategy filterStrategy, GlobalContext globalContext,
                                                 QueryContext queryContext, ReteEntityFilter[] entityFilters) {
        Filter[] tangoFilters = new Filter[entityFilters.length];

        for (int i = 0; i < tangoFilters.length; i++) {
            tangoFilters[i] =
                    CoherenceFilterHelper.convert(globalContext, queryContext, entityFilters[i], filterStrategy);
        }

        return new AllFilter(tangoFilters);
    }

    //-----------

    public static class IndexAwareWrapperFilter extends InterpretingFilter implements IndexAwareFilter, Externalizable {

        protected Filter innerFilter;

        public IndexAwareWrapperFilter() {
        }

        public IndexAwareWrapperFilter(Filter innerFilter) {
            this.innerFilter = innerFilter;
        }

        public void setValue(Object value) throws Exception {
        	Constructor<? extends Filter> constructor = null;
        	if (innerFilter instanceof com.tangosol.util.filter.LessFilter ||
               	innerFilter instanceof com.tangosol.util.filter.LessEqualsFilter ||
               	innerFilter instanceof com.tangosol.util.filter.GreaterFilter ||
               	innerFilter instanceof com.tangosol.util.filter.GreaterEqualsFilter) {
        		constructor = innerFilter.getClass().getDeclaredConstructor(ValueExtractor.class, Comparable.class);
        	}
        	else {
        		constructor = innerFilter.getClass().getDeclaredConstructor(ValueExtractor.class, Object.class);
        	}
            innerFilter = constructor.newInstance(((ExtractorFilter) innerFilter).getValueExtractor(), value);
        }

        public void setValues(Object value_1, Object value_2) throws Exception {
            Constructor<? extends Filter> constructor = innerFilter.getClass().getConstructor(ValueExtractor.class, Comparable.class, Comparable.class);
            innerFilter = constructor.newInstance(((ExtractorFilter) innerFilter).getValueExtractor(), value_1, value_2);
        }

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            out.writeObject(innerFilter);
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            innerFilter = (Filter) in.readObject();
        }

        @Override
        public int calculateEffectiveness(Map map, Set set) {
            if (innerFilter != null && innerFilter instanceof IndexAwareFilter) {
                int effectiveness = ((IndexAwareFilter) innerFilter).calculateEffectiveness(map, set);
                return effectiveness;
            }
            return 0;
        }

        @Override
        public Filter applyIndex(Map map, Set set) {
            if (innerFilter instanceof IndexAwareFilter) {
                return ((IndexAwareFilter) innerFilter).applyIndex(map, set);
            }
            return null;
        }

        @Override
        public boolean evaluateEntry(Map.Entry entry) {
            if (innerFilter instanceof IndexAwareFilter) {
                return ((IndexAwareFilter) innerFilter).evaluateEntry(entry);
            }
            return false;
        }

        @Override
        public boolean evaluate(Object obj) {
            if (innerFilter != null) {
                return innerFilter.evaluate(obj);
            }
            return false;
        }
    }
}
