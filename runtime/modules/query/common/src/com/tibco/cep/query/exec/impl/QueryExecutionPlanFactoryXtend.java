package com.tibco.cep.query.exec.impl;

import java.util.LinkedHashMap;

import com.tibco.cep.query.exec.descriptors.WindowBuilderDescriptor;
import com.tibco.cep.query.stream.aggregate.AggregateInfo;
import com.tibco.cep.query.stream.aggregate.AggregateItemInfo;
import com.tibco.cep.query.stream.context.DefaultQueryContext;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.core.DefaultTupleValueExtractor;
import com.tibco.cep.query.stream.expression.TupleValueExtractor;
import com.tibco.cep.query.stream.impl.aggregate.CountAggregator;
import com.tibco.cep.query.stream.partition.Window;
import com.tibco.cep.query.stream.partition.WindowOwner;
import com.tibco.cep.query.stream.partition.purge.ImmediateWindowPurgeAdvice;
import com.tibco.cep.query.stream.partition.purge.WindowPurgeAdvisor;
import com.tibco.cep.query.stream.partition.purge.WindowPurgeManager;
import com.tibco.cep.query.stream.partition.purge.WindowStats;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;

/*
* Author: Ashwin Jayaprakash Date: Apr 7, 2009 Time: 6:23:21 PM
*/
public class QueryExecutionPlanFactoryXtend extends QueryExecutionPlanFactoryImpl {
    protected final int purgeAllAtCount;

    public QueryExecutionPlanFactoryXtend() {
        String propName =
                QueryExecutionPlanFactoryXtend.class.getSimpleName() + ".purgeAllAtCount";

        String purgeAllAtCountString = System.getProperty(propName);

        this.purgeAllAtCount = (purgeAllAtCountString == null) ? Integer.MAX_VALUE :
                Integer.parseInt(purgeAllAtCountString);
    }

    @Override
    protected WindowPurgeManager plugInWindowPurgeManager(Window window, WindowOwner windowOwner,
                                                          WindowBuilderDescriptor windowBuilderDescriptor,
                                                          TupleInfo inputTupleInfo) {
        WindowPurgeAdvisor advisor = new WindowPurgeAdvisor() {
            public ImmediateWindowPurgeAdvice advise(GlobalContext globalContext,
                                                     DefaultQueryContext queryContext,
                                                     WindowStats stats) {
                Object currentCount = stats.getStat("count");

                if (currentCount != null) {
                    Number number = (Number) currentCount;

                    if (number.intValue() >= purgeAllAtCount) {
                        return new ImmediateWindowPurgeAdvice(purgeAllAtCount);
                    }
                }

                return null;
            }
        };

        LinkedHashMap<String, AggregateItemInfo> map =
                new LinkedHashMap<String, AggregateItemInfo>();
        TupleValueExtractor extractor = new DefaultTupleValueExtractor() {
            @Override
            public Object extract(GlobalContext globalContext,
                                  QueryContext queryContext, Tuple tuple) {
                // Can return anything for Count.
                return 1;
            }
        };
        map.put("count", new AggregateItemInfo(CountAggregator.CREATOR, extractor));
        AggregateInfo purgeAggregateInfo = new AggregateInfo(map);

        return new WindowPurgeManager(window, windowOwner, advisor, purgeAggregateInfo);
    }
}
