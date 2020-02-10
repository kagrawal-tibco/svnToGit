package com.tibco.cep.query.client.console.swing.control;

import com.tibco.cep.query.client.console.swing.util.SwingUtil;
import javax.swing.JProgressBar;

/**
 *
 * @author ksubrama
 */
public class ProgressBarControl {

    private final JProgressBar progressBar;
    
    public ProgressBarControl(JProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public void setVisible(final boolean visible) {
        SwingUtil.runInEDT(new SwingUtil.FireAndForgetWork() {

            @Override
            public void doWork() {
                if(progressBar.isVisible() == visible) {
                    return;
                }
                progressBar.setValue(0);
                progressBar.setString(null);
                progressBar.setIndeterminate(true);
                progressBar.setVisible(visible);
                progressBar.getParent().validate();
                return;
            }
        });
    }

    public void setProgress(final String text) {
        SwingUtil.runInEDT(new SwingUtil.FireAndForgetWork() {

            @Override
            public void doWork() {
                progressBar.setStringPainted(true);
                progressBar.setString(text);
                progressBar.validate();
            }
        });
    }
}
