package com.tibco.cep.query.client.console.swing.control;

import com.tibco.cep.query.client.console.swing.control.query.Query;
import com.tibco.cep.query.client.console.swing.control.query.QueryExecutor;
import com.tibco.cep.query.client.console.swing.control.query.QueryType;
import com.tibco.cep.query.client.console.swing.ui.ResultPanel;
import com.tibco.cep.query.client.console.swing.ui.RightPanel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author ksubrama
 */
public class RunQueryAction {
    private final RightPanel rightPanel;
    private final ResultPanel resultPanel;
    
    public RunQueryAction(RightPanel panel) {
        this.rightPanel = panel;
        this.resultPanel = panel.getResultPanel();
    }

    public void runQuery(String queryStr, String queryType) {
        queryType = queryType.replace(' ', '_');
        queryType = queryType.toUpperCase();
        Query query = null;
        try {
            query = new Query(queryStr);
            rightPanel.disableRunButton();
            resultPanel.setProgressBarVisible(true);
            executeQueryInBackground(query, QueryType.valueOf(queryType));
        } catch (Exception ex) {
            resultPanel.setProgressBarVisible(false);
            resultPanel.setErrorMessage(ex);

            ex.printStackTrace();
        }
    }

    public void executeQueryInBackground(final Query query, final QueryType type) {
        final QueryExecutor worker = new QueryExecutor(
                query, type, rightPanel);
        worker.getPropertyChangeSupport().addPropertyChangeListener(
                new PropertyChangeListener(){
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("progress".equals(evt.getPropertyName())) {                    
                    resultPanel.setProgress(worker.getMessage());
                } 
            }
        });
        rightPanel.setQueryExecutor(worker);
        worker.execute();
    }
}
