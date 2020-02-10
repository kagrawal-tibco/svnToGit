package com.tibco.cep.query.stream.query.snapshot;

import java.util.Collection;
import java.util.LinkedHashMap;

import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.context.LocalContext;
import com.tibco.cep.query.stream.core.Stream;
import com.tibco.cep.query.stream.monitor.Flags;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.CustomCollection;
import com.tibco.cep.query.stream.util.WrappedCustomCollection;

/*
* Author: Ashwin Jayaprakash Date: Mar 20, 2008 Time: 4:48:48 PM
*/
public class SimpleBridge extends Bridge {
    protected final LinkedHashMap<Number, Tuple> collectedTuples;

    /**
     * @param source
     * @param id
     */
    public SimpleBridge(Stream source, ResourceId id) {
        super(source, id);

        this.collectedTuples = new LinkedHashMap<Number, Tuple>();
    }

    @Override
    protected void doProcessing(Context context) throws Exception {
        super.doProcessing(context);

        LocalContext lc = context.getLocalContext();

        Collection<? extends Tuple> deadTuples = lc.getDeadTuples();
        if (deadTuples != null) {
            for (Tuple tuple : deadTuples) {
                collectedTuples.remove(tuple.getId());

                if (Flags.TRACK_TUPLE_REFS) {
                    // End of the road for this tuple.
                    tuple.decrementRefCount();
                }
            }
        }

        Collection<? extends Tuple> newTuples = lc.getNewTuples();
        if (newTuples != null) {
            for (Tuple tuple : newTuples) {
                collectedTuples.put(tuple.getId(), tuple);
            }
        }
    }

    @Override
    protected CustomCollection<? extends Tuple> getFinalCollectedTuples(Context context) {
        if (collectedTuples.size() > 0) {
            return new WrappedCustomCollection<Tuple>(collectedTuples.values());
        }

        return null;
    }

    @Override
    protected void clearFinalCollectedTuples(Context context) {
        collectedTuples.clear();
    }
}
