package com.tibco.cep.query.client.console.swing.control.query;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.query.client.console.swing.model.QueryResultModel;
import com.tibco.cep.query.client.console.swing.ui.ResultPanel;
import com.tibco.cep.query.client.console.swing.ui.RightPanel;
import com.tibco.cep.query.client.console.swing.util.Registry;
import com.tibco.cep.query.client.console.swing.util.SessionUtil;
import com.tibco.cep.query.functions.ResultSetFunctions;
import com.tibco.cep.query.functions.ResultSetMetadataFunctions;

import javax.swing.*;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author ksubrama
 */
public class QueryExecutor extends SwingWorker<Object, Object> {
    private final Logger logger;
    private String message;
    private final Query query;
    private final int batchSize = 100;
    private final QueryType type;
    private final RightPanel rightPanel;
    private final ResultPanel resultPanel;

    public QueryExecutor(Query query, QueryType type, RightPanel panel) {
        this.query = query;
        this.type = type;
        this.rightPanel = panel;
        this.resultPanel = panel.getResultPanel();
        logger = Registry.getRegistry().getRuleServiceProvider().getLogger(QueryExecutor.class);
    }

    public Query getQuery() {
        return this.query;
    }

    public void stop() {

    }

    @Override
    protected Object doInBackground() throws Exception {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(Registry.getRegistry().getBeClassLoader());
        SessionUtil.initSession();
        message = "Query Session initialized";
        setProgress(10);
        message = "Executing Query";
        setProgress(20);
        query.executeQuery(type, rightPanel);
        message = "Executed Query";
        setProgress(30);
        this.resultPanel.setQuery(query);
        if(type == QueryType.SNAPSHOT || type == QueryType.SNAPSHOT_THEN_CONTINUOUS) {
            message = "Iterating over query result";
            loadResultsToModel();
        }
        setProgress(40);
        Thread.currentThread().setContextClassLoader(contextClassLoader);
        return null;
    }

    public String getMessage() {
        return message;
    }

    private void loadResultsToModel() {
        QueryResultModel resultModel = query.getResultModel();
        String resultSetName = query.getResultsetName();
        int colCount = ResultSetMetadataFunctions.getColumnCount(resultSetName);
            List<String[]> batch = new ArrayList<String[]>(batchSize);
            int index = 1;
            if (ResultSetFunctions.isOpen(resultSetName) == true) {
                while (ResultSetFunctions.next(resultSetName) && isCancelled() == false) {
                    String[] row = new String[colCount];
                    for (int i = 0; i < colCount; i++) {
                        Object rawValue = ResultSetFunctions.get(resultSetName, i);
                        String value = String.valueOf(rawValue);
                        if (rawValue instanceof Calendar) {
                        	value = MessageFormat.format("{0,date,yyyy-MM-dd'T'HH:mm:ss.SSSZ}", ((Calendar) rawValue).getTime());
                        }
                        row[i] = (value == null? "" : value);
                    }
                    batch.add(row);
                    if(index++ % 100 == 0) {
                        resultModel.addRows(batch);
                        batch = new ArrayList<String[]>(batchSize);
                    }
                }
                if(batch.size() != 0 && isCancelled() == false) {
                    resultModel.addRows(batch);
                } else if(isCancelled() == true) {
                    message = "Query Execution cancelled.";
                    setProgress(100);
                    logger.log(Level.WARN, "Query Execution cancelled.");
                    query.closeQuery();
                }
            }
    }

    @Override
    protected void done() {
        if(type == QueryType.SNAPSHOT) {
            setProgress(100);
            this.resultPanel.setProgressBarVisible(false);
            this.resultPanel.reloadModel();
            this.rightPanel.enableRunButton();
            query.closeQuery();
        } else {
            message = "Waiting for results";
            setProgress(50);
        }
    }
}
