package com.tibco.cep.query.api.impl.local;

import com.tibco.cep.query.api.QueryListener;
import com.tibco.cep.query.api.QueryResultSet;
import com.tibco.cep.query.api.Row;
import com.tibco.cep.query.stream.core.Registry;
import com.tibco.cep.query.stream.monitor.CustomDaemonThread;
import com.tibco.cep.query.stream.monitor.CustomThreadGroup;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.monitor.ThreadCentral;
import com.tibco.cep.query.stream.tuple.TupleInfo;

/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Mar 19, 2008
* Time: 8:46:16 PM
*/
public class QueryListenerHandler {
    protected static final ResourceId THREAD_GROUP_ID =
            new ResourceId(QueryListenerHandler.class.getName());

    protected TupleInfo tupleInfo;

    protected QueryResultSet resultSet;

    protected QueryListener listener;

    protected HandlerThread handlerThread;

    public QueryListenerHandler(QueryResultSet resultSet, TupleInfo tupleInfo,
                                QueryListener listener, String id) {
        this.resultSet = resultSet;
        this.tupleInfo = tupleInfo;
        this.listener = listener;

        ThreadCentral threadCentral = Registry.getInstance().getComponent(ThreadCentral.class);
        CustomThreadGroup threadGroup = threadCentral.createOrGetThreadGroup(THREAD_GROUP_ID);
        this.handlerThread = new HandlerThread(threadGroup, new ResourceId(id));

        this.handlerThread.start();
    }

    public QueryListener getListener() {
        return listener;
    }

    public QueryResultSet getResultSet() {
        return resultSet;
    }

    //-----------

    protected class HandlerThread extends CustomDaemonThread {
        public HandlerThread(CustomThreadGroup threadGroup, ResourceId resourceId) {
            super(threadGroup, resourceId);
        }

        @Override
        protected void doWorkLoop() throws Exception {
            final TupleInfo cachedTupleInfo = QueryListenerHandler.this.tupleInfo;
            final int numColumns = QueryListenerHandler.this.tupleInfo.getColumnAliases().length;

            while (resultSet.next()) {
                if (resultSet.isBatchEnd()) {
                    listener.onBatchEnd();
                    continue;
                }

                //Copy the columns before they are cleared by the ResultSet.
                Object[] columns = new Object[numColumns];
                for (int i = 0; i < columns.length; i++) {
                    columns[i] = resultSet.getObject(i);
                }

                Row row = new RowImpl(columns, cachedTupleInfo);
                listener.onNewRow(row);
            }

            listener.onQueryEnd();

            signalStop();
        }
    }

    protected static class RowImpl implements Row {
        protected Object[] columns;

        protected TupleInfo tupleInfo;

        public RowImpl(Object[] columns, TupleInfo tupleInfo) {
            this.columns = columns;
            this.tupleInfo = tupleInfo;
        }

        public Object getColumn(String name) {
            int index = tupleInfo.getColumnPosition(name);

            return getColumn(index);
        }

        public Object getColumn(int index) {
            return columns[index];
        }

        public Object[] getColumns() {
            return this.columns;
        }

        public int getSize() {
            return tupleInfo.getColumnTypes().length;
        }

        public void discard() {
            for (int i = 0; i < columns.length; i++) {
                columns[i] = null;
            }
            columns = null;

            tupleInfo = null;
        }
    }
}
