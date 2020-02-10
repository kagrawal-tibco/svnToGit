package com.tibco.cep.studio.mapper.ui.data.bind.wizard;

import com.tibco.cep.studio.mapper.ui.wizard.decision.DecisionWizardPanel;

/**
 * The wizard panel used to fill in unfilled parents on a binding drop.
 */
public final class BindingWizardSupport extends DecisionWizardPanel {
   /*public static void prepDisplay(final BindingTree tree, final Binding binding) {
       SwingUtilities.invokeLater(new Runnable() {
           public void run() {
               tree.rebuild();
               TreePath path = tree.getPathForBinding(binding);
               if (path!=null) {
                   // make sure it's expanded:
                   tree.expandPath(path);
                   // ... and visible
                   tree.scrollPathToVisible(path);
                   tree.setPathBackgroundColor(path,Color.green);
                   tree.repaint();
               }
           }
       });
   }*/
}

;
