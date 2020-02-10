package com.tibco.cep.query.client.console.swing.model.listener;

import com.tibco.cep.query.api.QueryListener;
import com.tibco.cep.query.api.QueryListenerHandle;
import com.tibco.cep.query.api.Row;
import com.tibco.cep.query.client.console.swing.control.query.Query;
import com.tibco.cep.query.client.console.swing.model.QueryResultModel;
import com.tibco.cep.query.client.console.swing.ui.ResultPanel;
import com.tibco.cep.query.client.console.swing.ui.RightPanel;
import com.tibco.cep.query.client.console.swing.util.SwingUtil;
import com.tibco.cep.query.client.console.swing.util.SwingUtil.FireAndForgetWork;

/**
 *
 * @author ksubrama
 */
public class AsyncQueryListenerImpl implements QueryListener {
    private final QueryResultModel resultModel;
    private final ResultPanel resultPanel;
    private final RightPanel rightPanel;
    private final Query query;
    private QueryListenerHandle handle;

    public AsyncQueryListenerImpl(Query query, RightPanel rightPanel) {
        this.query = query;
        this.resultModel = query.getResultModel();
        this.rightPanel = rightPanel;
        this.resultPanel = rightPanel.getResultPanel();
    }
    
    
    @Override
    public void onBatchEnd() {
        resultModel.insertBatchRecord();
    }

    public void stopQuery() {
        if(handle != null) {
            handle.requestStop();
        }
    }
    
    @Override
    public void onQueryEnd() {
        SwingUtil.runInBackground(new FireAndForgetWork() {
            @Override
            public void doWork() {
                resultPanel.setProgress("Query successfully completed.");
                resultPanel.setProgressBarVisible(false);
                resultPanel.reloadModel();
                rightPanel.enableRunButton();
                query.closeQuery();
            }
        });
    }

    @Override
    public void onQueryStart(QueryListenerHandle qlh) {
        this.handle = qlh;
    }

    @Override
    public void onNewRow(final Row row) {
        resultModel.addRow(getColumns(row));
    }

    private String[] getColumns(Row row) {
        int size = row.getSize();
        String[] values = new String[size];
        for(int i = 0; i < size; i++) {
            String value = String.valueOf(row.getColumn(i));
            values[i] = (value == null? "" : value);
        }
        return values;
    }

}
