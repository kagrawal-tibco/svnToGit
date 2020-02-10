package com.tibco.cep.query.api.impl.local;

import com.tibco.cep.query.api.QueryStatement;
import com.tibco.cep.query.stream.impl.rete.query.ReteQuery;
import com.tibco.cep.query.stream.io.StreamedSink;
import com.tibco.cep.query.stream.monitor.Flags;
import com.tibco.cep.query.stream.tuple.Tuple;

import java.util.concurrent.TimeUnit;

/*
* Author: Ashwin Jayaprakash Date: Mar 21, 2008 Time: 1:32:05 PM
*/

public class StreamedSinkAdapter extends SinkAdapter {
    protected StreamedSink sink;

    protected Tuple batchEndMarker;

    protected Tuple currentRow;

    public StreamedSinkAdapter(ReteQuery reteQuery, QueryStatement statement,
                               QueryResultSetManager resultSetManager) {
        super(reteQuery, statement, resultSetManager);

        this.sink = (StreamedSink) reteQuery.getSink();
        this.batchEndMarker = sink.getBatchEndMarker();
    }

    public Object getObject(int columnIndex) throws IndexOutOfBoundsException {
        return currentRow.getColumn(columnIndex);
    }

    /**
     * @return -1
     */
    public int getRowCountIfPossible() {
        return -1;
    }

    public boolean next() {
        if (Flags.TRACK_TUPLE_REFS) {
            if (currentRow != null) {
                currentRow.decrementRefCount();
            }
        }

        for (; ;) {
            try {
                currentRow = sink.poll(5, TimeUnit.MILLISECONDS);
            }
            catch (InterruptedException e) {
                //Ignore.
            }

            if (currentRow != null || stop.get() || reteQuery.hasStopped()) {
                break;
            }
        }

        return currentRow != null;
    }

    public boolean isBatchEnd() {
        return currentRow == batchEndMarker;
    }

    @Override
    public void close() throws Exception {
        stop.set(true);

        if (Flags.TRACK_TUPLE_REFS) {
            if (currentRow != null) {
                currentRow.decrementRefCount();
            }
        }
        currentRow = null;

        super.close();
    }
}
