package com.tibco.cep.query.client.console.swing.model.listener;

import com.tibco.cep.query.client.console.swing.ui.ResultPanel;
import com.tibco.cep.query.client.console.swing.util.SwingUtil;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 *
 * @author ksubrama
 */
public class QueryResultModelListener implements TableModelListener {

    private final ResultPanel panel;

    public QueryResultModelListener(ResultPanel panel) {
        this.panel = panel;
    }

    @Override
    public void tableChanged(final TableModelEvent e) {
        int type = e.getType();
        if(!(type == TableModelEvent.INSERT || type == TableModelEvent.DELETE)) {
            return;
        }
        SwingUtil.runInEDT(new SwingUtil.FireAndForgetWork() {

            @Override
            public void doWork() {
                panel.reloadModel();
                return;
            }
        });
    }
}
