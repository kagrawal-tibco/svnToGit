package com.tibco.cep.query.stream.sort;

import java.util.Comparator;
import java.util.List;

import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.expression.TupleValueExtractor;

/*
 * Author: Ashwin Jayaprakash Date: Mar 20, 2008 Time: 12:35:08 PM
 */

public class Helper {
    public static ContinuousSorter buildSorter(Context context, boolean sourceProducesDStream,
                                               SortInfo sortInfo,
                                               TupleValueExtractor[] extractors,
                                               List<Comparator<Object>> comparators) {
        boolean useDecorator = false;

        for (SortItemInfo itemInfo : sortInfo.getItems()) {
            if (itemInfo.getFirstX(context) < Integer.MAX_VALUE
                    || itemInfo.getFirstXOffset(context) > 0) {
                useDecorator = true;
                break;
            }
        }

        if (useDecorator) {
            return new ContinuousSorterDecorator(sortInfo, extractors, comparators,
                    sourceProducesDStream);
        }

        return new ContinuousSorter(sortInfo, extractors, comparators, sourceProducesDStream);
    }
}
