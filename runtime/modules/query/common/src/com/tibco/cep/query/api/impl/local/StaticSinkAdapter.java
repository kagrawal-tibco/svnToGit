package com.tibco.cep.query.api.impl.local;

import com.tibco.cep.query.api.QueryStatement;
import com.tibco.cep.query.stream.impl.rete.query.ReteQuery;
import com.tibco.cep.query.stream.io.StaticSink;
import com.tibco.cep.query.stream.monitor.Flags;
import com.tibco.cep.query.stream.tuple.Tuple;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/*
* Author: Ashwin Jayaprakash Date: Mar 21, 2008 Time: 1:49:42 PM
*/

public class StaticSinkAdapter extends SinkAdapter {
    protected StaticSink sink;

    protected Collection<Tuple> results;

    protected Iterator<Tuple> resultIterator;

    protected Tuple currentRow;

    protected int rowCount;

    public StaticSinkAdapter(ReteQuery reteQuery, QueryStatement statement,
                             QueryResultSetManager resultSetManager) {
        super(reteQuery, statement, resultSetManager);

        this.sink = (StaticSink) reteQuery.getSink();
        this.rowCount = -1;
    }

    public Object getObject(int columnIndex) throws IndexOutOfBoundsException {
        return currentRow.getColumn(columnIndex);
    }

    public int getRowCountIfPossible() {
        retrieveResults();

        return rowCount;
    }

    public boolean next() {
        if (retrieveResults() == false) {
            return false;
        }

        boolean b = resultIterator.hasNext();

        if (b) {
            if (Flags.TRACK_TUPLE_REFS) {
                if (currentRow != null) {
                    currentRow.decrementRefCount();
                }
            }

            currentRow = resultIterator.next();

            // 1-AI5HMJ: AppendOnlyQueue$QueueIterator.remove not supported.
            // resultIterator.remove();
        }

        return b;
    }

    /**
     * @return <code>false</code> if results could not be obtained.
     */
    private boolean retrieveResults() {
        if (resultIterator != null) {
            return true;
        }

        for (; ;) {
            try {
                results = sink.pollFinalResult(5, TimeUnit.MILLISECONDS);
            }
            catch (InterruptedException e) {
                //Ignore.
            }

            if (results != null || stop.get() || reteQuery.hasStopped()) {
                break;
            }
        }

        //Still null?
        if (results == null) {
            rowCount = -1;

            return false;
        }

        resultIterator = results.iterator();
        rowCount = results.size();

        return true;
    }

    public boolean isBatchEnd() {
        return false;
    }

    @Override
    public void close() throws Exception {
        stop.set(true);

        if (results != null) {
            results.clear();
        }
        results = null;
        resultIterator = null;

        if (Flags.TRACK_TUPLE_REFS) {
            if (currentRow != null) {
                currentRow.decrementRefCount();
            }
        }
        currentRow = null;

        sink = null;

        super.close();
    }
}
