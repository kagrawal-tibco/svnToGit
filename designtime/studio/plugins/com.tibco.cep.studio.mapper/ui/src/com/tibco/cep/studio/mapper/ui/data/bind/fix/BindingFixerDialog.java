package com.tibco.cep.studio.mapper.ui.data.bind.fix;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingChangeUndoableEdit;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingEditorResources;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingTree;
import com.tibco.cep.studio.mapper.ui.data.utils.BetterJDialog;
import com.tibco.cep.studio.mapper.ui.data.utils.PreferenceUtils;
import com.tibco.cep.studio.mapper.ui.data.xpath.XPathEditResources;

/**
 * A dialog which shows errors & allows selective choosing of which elements to fix.
 */
public class BindingFixerDialog extends BetterJDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UIAgent uiAgent;
	private BindingFixerPanel mPanel;
   private BindingTree mTree;
   private boolean mOKHit;

   public static final String TITLE = BindingEditorResources.BINDING_FIXER_TITLE;

   private BindingFixerDialog(UIAgent uiAgent, Frame f) {
      super(uiAgent, f);
   }

   private BindingFixerDialog(UIAgent uiAgent, Dialog d) {
      super(uiAgent, d);
   }

   private void init(UIAgent uiAgent, BindingTree tree) {
	   this.uiAgent = uiAgent;
	   mTree = tree;
      BindingFixerPanel panel = new BindingFixerPanel(tree);
      mPanel = panel;
      mPanel.getOKButton().addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            okHit();
         }
      });
      mPanel.getCancelButton().addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            dispose();
         }
      });
      setContentPane(mPanel);

      Dimension sz = PreferenceUtils.readDimension(uiAgent, "BindingFixerDialog.windowSize",
                                                   new Dimension(400, 200),
                                                   Toolkit.getDefaultToolkit().getScreenSize(),
                                                   new Dimension(450, 250));
      Point loc = PreferenceUtils.readLocation(uiAgent, "BindingFixerDialog.windowLocation",
                                               null,
                                               sz);

      setSize(sz);
      setModal(true);
      if (loc == null) {
         setLocationRelativeTo(getOwner());
      }
      else {
         setLocation(loc);
      }

      /*final int tableBreak = PreferenceUtils.readInt(app,"BindingFixerDialog.tableBreak",30);
      SwingUtilities.invokeLater(new Runnable() {
          public void run() {
              mPanel.setTableBreak(tableBreak);
          }
      });*/
      mPanel.readPreferences(uiAgent, "BindingFixerDialog");
      mPanel.load();
      setTitle(TITLE);
      mPanel.setEditable(tree.isTreeEditable());
   }

   private void okHit() {
      if (!mPanel.hasAnySelectedChanges()) {
         // optimize, don't want an undoable thing to happen here..
         dispose();
      }
      else {
         mOKHit = true;
         Binding oldCopy = mTree.getTemplateEditorConfiguration().getBinding().cloneDeep();
         mPanel.applyChanges();
         Binding currentCopy = mTree.getTemplateEditorConfiguration().getBinding();
         BindingChangeUndoableEdit edit = new BindingChangeUndoableEdit(mTree, oldCopy, currentCopy);
         edit.setPresentationName(XPathEditResources.FIX_LABEL);
         mTree.getUndoManager().addEdit(edit);
         edit.doit();
         dispose();
      }
   }

   public static boolean showDialog(UIAgent uiAgent, BindingTree tree, Binding b) {
      Window w = SwingUtilities.getWindowAncestor(tree);
      BindingFixerDialog jd;

      if (w instanceof Frame) {
         jd = new BindingFixerDialog(uiAgent, (Frame) w);
      }
      else {
         jd = new BindingFixerDialog(uiAgent, (Dialog) w);
      }
      jd.setIconImage(((ImageIcon)DataIcons.getWindowTitleIcon()).getImage());
      jd.init(uiAgent, tree);
      jd.setVisible(true);
      jd.setModal(true);
      return jd.mOKHit;
   }

   public void dispose() {
      PreferenceUtils.writeDimension(uiAgent, "BindingFixerDialog.windowSize", getSize());
      PreferenceUtils.writePoint(uiAgent, "BindingFixerDialog.windowLocation", getLocation());
      mPanel.writePreferences(uiAgent, "BindingFixerDialog");
      super.dispose();
   }
}
