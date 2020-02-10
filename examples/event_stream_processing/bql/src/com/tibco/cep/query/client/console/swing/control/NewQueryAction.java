package com.tibco.cep.query.client.console.swing.control;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.query.client.console.swing.ui.BasePanel;
import com.tibco.cep.query.client.console.swing.ui.NewTabDialog;
import com.tibco.cep.query.client.console.swing.ui.RightPanel;
import com.tibco.cep.query.client.console.swing.util.Registry;
import com.tibco.cep.query.client.console.swing.util.SwingUtil;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author ksubrama
 */
public class NewQueryAction {
    private final Logger logger;
    private static final long serialVersionUID = 1L;
    
    private final BasePanel basePanel;
    private static final String QUERY_PREFIX = "Query ";
    private static final String DEF_QUERY_TITLE = "Query 1";
    
    public NewQueryAction(BasePanel basePanel) {
        this.basePanel = basePanel;
        logger = Registry.getRegistry().getRuleServiceProvider().getLogger(NewQueryAction.class);
    }

    public void addNewTab() {
        final JTabbedPane tabbedPane = basePanel.getTabbedPane();
        int tabCount = tabbedPane.getTabCount();
        final StringBuilder titleStr = new StringBuilder(DEF_QUERY_TITLE);
        int index = 1;
        if(tabCount > 0) {
            String title = tabbedPane.getTitleAt(tabCount - 1);
            String count = title.substring(title.indexOf(" "));
            index = Integer.parseInt(count.trim());
            titleStr.delete(0, titleStr.length());
            titleStr.append(QUERY_PREFIX + ++index);
        }
        final NewTabDialog dialog = new NewTabDialog(titleStr.toString(),
                basePanel, true);
        Thread dialogRunner = new Thread(new Runnable(){
            @Override
            public void run() {
                boolean error = false;
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
                } catch (InterruptedException ex) {
                    logger.log(Level.WARN,
                            "Dialog box interrupted. Using default tab name.", ex);
                    error = true;
                } catch (InvocationTargetException ex) {
                    logger.log(Level.WARN,
                            "Dialog box error. Using default tab name.", ex);
                    error = true;
                }
                if(error == false && dialog.getTabName() != null) {
                    titleStr.delete(0, titleStr.length());
                    titleStr.append(dialog.getTabName());
                }
                SwingUtil.runInEDT(new SwingUtil.FireAndForgetWork() {
                    @Override
                    public void doWork() {
                        RightPanel rightPanel = new RightPanel(titleStr.toString());
                        tabbedPane.addTab(titleStr.toString(), rightPanel);
                        tabbedPane.setTabComponentAt(tabbedPane.indexOfComponent(rightPanel),
                                rightPanel.getHeader());
                        tabbedPane.setSelectedComponent(rightPanel);
                        tabbedPane.validate();
                    }
                });
            }
        });
        dialogRunner.start();
    }

}
