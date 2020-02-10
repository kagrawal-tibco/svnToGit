package com.tibco.cep.query.client.console.swing;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.query.client.console.swing.control.query.Query;
import com.tibco.cep.query.client.console.swing.model.integ.nodes.NodeCache;
import com.tibco.cep.query.client.console.swing.ui.BasePanel;
import com.tibco.cep.query.client.console.swing.util.Registry;
import com.tibco.cep.query.service.QuerySession;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.session.RuleServiceProvider;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 *
 * @author ksubrama
 */
public class BQLConsole {    
    public static final String BQL_RULE_SESSION = "BQL_RULE_SESSION";
    private static Logger logger;

    /**
     * 
     * @param name
     * @param querySession
     */
    public void showConsole(final String name, QuerySession querySession) {
        initRegistry(querySession);
        RuleServiceProvider ruleServiceProvider = querySession.getRuleSession().getRuleServiceProvider();
        logger = ruleServiceProvider.getLogger(BQLConsole.class);
        try {
            LookAndFeelInfo[] lookAndFeelInfos = UIManager.getInstalledLookAndFeels();
            boolean done = false;

            if (lookAndFeelInfos != null) {
                for (LookAndFeelInfo lookAndFeelInfo : lookAndFeelInfos) {
                    if (lookAndFeelInfo.getName().equalsIgnoreCase("nimbus")) {
                        UIManager.setLookAndFeel(lookAndFeelInfo.getClassName());

                        done = true;
                        break;
                    }
                }
            }

            if (!done) {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
        } catch (Exception ex) {
            logger.log(Level.WARN, "Unable to set look and feel for BQL Console", ex);
        }


        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                BasePanel basePanel = new BasePanel();
                basePanel.setTitle(basePanel.getTitle() + " (" + name + ")");
                basePanel.addWindowListener(new WindowListener(){

                    @Override
                    public void windowOpened(WindowEvent e) {
                    }

                    @Override
                    public void windowClosing(WindowEvent e) {
                        Query.closeAllQueries();
                    }

                    @Override
                    public void windowClosed(WindowEvent e) {
                    }

                    @Override
                    public void windowIconified(WindowEvent e) {
                    }

                    @Override
                    public void windowDeiconified(WindowEvent e) {
                    }

                    @Override
                    public void windowActivated(WindowEvent e) {
                    }

                    @Override
                    public void windowDeactivated(WindowEvent e) {
                    }
                });
                basePanel.setVisible(true);
            }
        });
    }

    private void initRegistry(QuerySession querySession) {
        Registry registry = Registry.getRegistry();
        registry.setQuerySession(querySession);
        registry.setRuleServiceProvider(querySession.getRuleSession().getRuleServiceProvider());
        registry.setBeClassLoader(
                (BEClassLoader) Thread.currentThread().getContextClassLoader());
        registry.setNodeCache(new NodeCache());
    }
}
