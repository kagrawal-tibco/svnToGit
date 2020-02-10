package com.tibco.cep.query.client.console.swing.model.listener;

import com.tibco.cep.query.client.console.swing.model.integ.nodes.AbstractNode;
import com.tibco.cep.query.client.console.swing.ui.PropertiesPanel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author ksubrama
 */
public class OntologyTreeSelectionListener implements TreeSelectionListener {

    private final JTree tree;
    private final PropertiesPanel panel;
    
    public OntologyTreeSelectionListener(JTree tree, PropertiesPanel propertiesPanel) {
        this.panel = propertiesPanel;
        this.tree = tree;
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                       tree.getLastSelectedPathComponent();
        if(node == null) {
            // Nothing has changed.
            panel.clearModel();
        } else {
            if(node.isLeaf() == true) {
                AbstractNode absNode = (AbstractNode)node;
                panel.reloadModel(absNode.getProperties());
            } else {
                // Nothing has changed.
                panel.clearModel();                
            }
        }        
    }

}
