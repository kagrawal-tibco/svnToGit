package com.tibco.cep.query.client.console.swing.control;

import com.tibco.cep.query.client.console.swing.ui.BasePanel;
import com.tibco.cep.query.client.console.swing.util.SwingUtil;

/**
 *
 * @author ksubrama
 */
public class QuitConsoleAction {
    private final BasePanel basePanel;

    public QuitConsoleAction(BasePanel basePanel) {
        this.basePanel = basePanel;
    }

    public void closeConsole() {
        SwingUtil.runInEDT(new SwingUtil.FireAndForgetWork() {

            @Override
            public void doWork() {
                basePanel.setVisible(false);
                return;
            }
        });
    }
}
