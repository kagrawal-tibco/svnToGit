package com.tibco.cep.query.aggregate;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 11/16/12
 * Time: 3:27 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Aggregator {
    Class<? extends Aggregate> findAggregate(Aggregates type);

    Aggregator add(Class<? extends Aggregate> aggregator, String fieldName,
                   String filter, String... groupByFieldNames);

    Map[] execute();
}
