package com.tibco.cep.query.client.console.swing.control;

import com.tibco.cep.query.client.console.swing.ui.AboutBQLDialog;
import com.tibco.cep.query.client.console.swing.ui.BasePanel;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author ksubrama
 */
public class AboutBQLAction {

    private final BasePanel basePanel;

    public AboutBQLAction(BasePanel basePanel) {
        this.basePanel = basePanel;
    }

    public void showAboutDialog() {
        final AboutBQLDialog dialog = new AboutBQLDialog(basePanel);
        Thread dialogRunner = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    SwingUtilities.invokeAndWait(new Runnable() {

                        @Override
                        public void run() {
                            dialog.pack();
                            Dimension basePanelSize = basePanel.getSize();
                            Dimension dialogSize = dialog.getSize();
                            if (dialogSize.height > basePanelSize.height) {
                                dialogSize.height = basePanelSize.height;
                            }
                            if (dialogSize.width > basePanelSize.width) {
                                dialogSize.width = basePanelSize.width;
                            }
                            dialog.setLocation( (basePanelSize.width - dialogSize.width)/2,
                            (basePanelSize.height - dialogSize.height)/2 );
                            dialog.validate();
                            dialog.setVisible(true);
                        }
                    });
                } catch (Exception ex) {
                    // Ignore
                }
            }
        });
        dialogRunner.start();
    }
}
