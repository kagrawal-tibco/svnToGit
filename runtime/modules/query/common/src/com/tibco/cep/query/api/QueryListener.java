package com.tibco.cep.query.api;

public interface QueryListener {


    /**
     * Called when the query starts.
     * @param handle QueryListenerHandle
     */
    void onQueryStart(QueryListenerHandle handle);


    /**
     * Called when a new result is received.
     *
     * @param row {@link com.tibco.cep.query.api.Row} containing the column values.
     */
    void onNewRow(Row row);


    /**
     * Called when a batch of results has ended, before the next call to {@link #onNewRow(Row)} .
     */
    void onBatchEnd();


    /**
     * Called if the query stopped.
     */
    void onQueryEnd();
}
 
