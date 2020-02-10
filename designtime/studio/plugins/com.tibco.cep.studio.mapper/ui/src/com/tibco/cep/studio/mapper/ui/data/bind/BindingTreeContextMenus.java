package com.tibco.cep.studio.mapper.ui.data.bind;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.tree.TreePath;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotUndoException;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.BindingManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.bind.ChooseBinding;
import com.tibco.cep.mapper.xml.xdata.bind.ForEachBinding;
import com.tibco.cep.mapper.xml.xdata.bind.IfBinding;
import com.tibco.cep.mapper.xml.xdata.bind.OtherwiseBinding;
import com.tibco.cep.mapper.xml.xdata.bind.WhenBinding;
import com.tibco.cep.mapper.xml.xdata.bind.virt.VirtualForEachGroupBinding;
import com.tibco.cep.mapper.xml.xdata.bind.virt.VirtualGroupingBinding;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.utils.BetterJDialog;
import com.tibco.cep.studio.mapper.ui.data.utils.OKCancelPanel;
import com.tibco.cep.studio.mapper.ui.edittree.EditableTreeContextMenuHandler;
import com.tibco.cep.studio.mapper.ui.edittree.EditableTreeModel;
import com.tibco.cep.studio.mapper.ui.edittree.EditableTreePathUtils;
import com.tibco.cep.studio.mapper.ui.xmlui.QNamePanel;
import com.tibco.cep.studio.mapper.ui.xmlui.XsdQNamePlugin;
import com.tibco.util.RuntimeWrapException;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmGlobalComponentNotFoundException;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 * The context menu handler for the {@link BindingTree}
 */
class BindingTreeContextMenus implements EditableTreeContextMenuHandler {
   private BindingEditor m_editor;
   protected  UIAgent uiAgent;

   public BindingTreeContextMenus(BindingEditor editor) {
      m_editor = editor;
   }

   public void addContextMenuItems(Object node, JPopupMenu menu) {
      final BindingLines lines = m_editor.getBindingTree().getBindingLines();
      JMenuItem item = new JMenuItem(BindingEditorResources.SHOW_CONNECTED);
      item.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent ae) {
            lines.showRightConnected();
         }
      });
      item.setEnabled(lines.canShowRightConnected());
      menu.add(item);

      menu.addSeparator();

      addStatementActions(menu);

      /* WCETODO Readd these later:
      JMenuItem find = new JMenuItem(BindingEditorResources.FIND);
      JMenuItem findGlobal = new JMenuItem(BindingEditorResources.FIND_IN_PROJECT);
      find.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent ae) {
              m_modelessDialogManager.find(false);
          }
      });
      findGlobal.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent ae) {
              m_modelessDialogManager.find(true);
          }
      });

      menu.add(find);
      menu.add(findGlobal);*/
   }

   private void addStatementActions(JPopupMenu menu) {
      final BindingTree tree = m_editor.getBindingTree();
      JMenu statement = new JMenu(BindingEditorResources.STATEMENT_MENU);
      JMenuItem intoChoice = new JMenuItem(BindingEditorResources.SURROUND_WITH_CHOICE_MENU);
      intoChoice.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            BindingNode node = (BindingNode) tree.getSelectionNode();
            if (node != null) {
               // First establish the # of items:
               surroundWithChoose(tree, node);
            }
         }
      });
      JMenuItem intoIf = new JMenuItem(BindingEditorResources.SURROUND_WITH_IF_MENU);
      intoIf.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            BindingNode node = (BindingNode) tree.getSelectionNode();
            if (node != null) {
               surroundWithIf(tree, node);
            }
         }
      });
      JMenuItem intoForEach = new JMenuItem(BindingEditorResources.SURROUND_WITH_FOR_EACH_MENU);
      intoForEach.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            BindingNode node = (BindingNode) tree.getSelectionNode();
            if (node != null) {
               surroundWithForEach(tree, node);
            }
         }
      });
      JMenuItem intoForEachGroup = new JMenuItem(BindingEditorResources.SURROUND_WITH_FOR_EACH_GROUP_MENU);
      intoForEachGroup.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            BindingNode node = (BindingNode) tree.getSelectionNode();
            if (node != null) {
               surroundWithForEachGroup(tree, node);
            }
         }
      });
      JMenuItem addDuplicate = new JMenuItem(BindingEditorResources.MAKE_COPY_MENU);
      addDuplicate.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            BindingNode node = (BindingNode) tree.getSelectionNode();
            if (node != null) {
               addDuplicate(tree, node);
            }
         }
      });
      JMenuItem addGroup = new JMenuItem(BindingEditorResources.ADD_GROUP + "...");
      addGroup.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            BindingNode node = (BindingNode) tree.getSelectionNode();
            if (node != null) {
               addGroup(tree, node);
            }
         }
      });


      statement.add(intoChoice);
      statement.add(intoIf);
      statement.add(intoForEach);
      statement.add(intoForEachGroup);
      statement.add(addDuplicate);
      statement.add(addGroup);
      boolean surroundAllowed = tree.getSelectionCount() == 1 && tree.getSelectionNode() != tree.getRootNode() && tree.isTreeEditable();
      intoChoice.setEnabled(surroundAllowed);
      intoIf.setEnabled(surroundAllowed);
      intoForEach.setEnabled(surroundAllowed);
      intoForEachGroup.setEnabled(surroundAllowed);
      addDuplicate.setEnabled(surroundAllowed);
      addGroup.setEnabled(surroundAllowed);

      menu.add(statement);
   }

   private static void addDuplicate(BindingTree tree, BindingNode onNode) {
      int[] pospath = EditableTreePathUtils.toPosPath(tree.getEditableModel(), onNode.getTreePath());
      AddAnotherEdit se = new AddAnotherEdit(tree, pospath);
      se.doit();
      tree.getUndoManager().addEdit(se);
   }

   private static void surroundWithIf(BindingTree tree, BindingNode onNode) {
      Binding cloned = onNode.getBinding().cloneDeep();
      int[] pospath = EditableTreePathUtils.toPosPath(tree.getEditableModel(), onNode.getTreePath());
      SurroundWithEdit se = new SurroundWithEdit(tree, pospath, cloned, new IfSurroundWithBuilder(), BindingEditorResources.SURROUND_WITH_IF_MENU);
      se.doit();
      tree.getUndoManager().addEdit(se);
   }

   private static void surroundWithForEach(BindingTree tree, BindingNode onNode) {
      Binding cloned = onNode.getBinding().cloneDeep();
      int[] pospath = EditableTreePathUtils.toPosPath(tree.getEditableModel(), onNode.getTreePath());
      SurroundWithEdit se = new SurroundWithEdit(tree, pospath, cloned, new ForEachSurroundWithBuilder(), BindingEditorResources.SURROUND_WITH_IF_MENU);
      se.doit();
      tree.getUndoManager().addEdit(se);
   }

   private static void surroundWithForEachGroup(BindingTree tree, BindingNode onNode) {
      Binding cloned = onNode.getBinding().cloneDeep();
      int[] pospath = EditableTreePathUtils.toPosPath(tree.getEditableModel(), onNode.getTreePath());
      SurroundWithEdit se = new SurroundWithEdit(tree, pospath, cloned, new ForEachGroupSurroundWithBuilder(), BindingEditorResources.SURROUND_WITH_IF_MENU);
      se.doit();
      tree.getUndoManager().addEdit(se);
   }

   private static void addGroup(BindingTree tree, BindingNode onNode) {
      //Binding cloned = onNode.getBinding().cloneDeep();
      //int[] pospath = EditableTreePathUtils.toPosPath(tree.getEditableModel(),onNode.getTreePath());
      Window w = SwingUtilities.getWindowAncestor(tree);
      AddGroup ag;
      UIAgent uiAgent = tree.getUIAgent();
      NamespaceContextRegistry ni = tree.getTemplateEditorConfiguration().getExprContext().getNamespaceMapper();
      ImportRegistry ir = tree.getTemplateEditorConfiguration().getImportRegistry();
      if (w instanceof Frame) {
         ag = new AddGroup((Frame) w, uiAgent, ni, ir);
      }
      else {
         ag = new AddGroup((JDialog) w, uiAgent, ni, ir);
      }

      if (!ag.getOkHit()) {
         return;
      }

      SmSequenceType gt = null;
      try {
         // (This is not undoable...)
         gt = SmSequenceTypeSupport.getGroup(ag.getSelectedName(),
                                                                  tree.getTemplateEditorConfiguration().getExprContext().getOutputSmComponentProvider());
      }
      catch (SmGlobalComponentNotFoundException e) {
         throw new RuntimeWrapException(e);
      }
      SmSequenceType[] seq = SmSequenceTypeSupport.getAllChoices(gt.prime());
      Binding[] ar = new Binding[seq.length];
      for (int i = 0; i < seq.length; i++) {
         Binding b = BindingManipulationUtils.createAppropriateAttributeOrElementOrMarkerBinding(seq[i], ni);
         ar[i] = b;
      }
      BindingDisplayUtils.instantiateAnyAncestorOrSelfMarkers(onNode.getBinding().getParent());
      BindingManipulationUtils.replaceInParent(onNode.getBinding(), ar);
      //TreePath path =
      //tree.setSelectionPath(path);
      tree.rebuild();
   }

   private static class AddGroup extends JDialog implements ActionListener {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OKCancelPanel m_ok;
      private QNamePanel m_panel;
      private boolean m_okHit;
//      private UIAgent uiAgent;

      public AddGroup(Frame jf, UIAgent uiAgent, NamespaceContextRegistry ni, ImportRegistry ir) {
         super(jf);
         setup(jf, uiAgent, ni, ir);
      }

      public AddGroup(JDialog jd, UIAgent uiAgent, NamespaceContextRegistry ni, ImportRegistry ir) {
         super(jd);
         setup(jd, uiAgent, ni, ir);
      }

      private void setup(Window w, UIAgent uiAgent,
                         NamespaceContextRegistry ni, ImportRegistry ir) {
         setModal(true);
//         this.uiAgent = uiAgent;
         OKCancelPanel ok = new OKCancelPanel();
         m_ok = ok;
         JPanel p = new JPanel(new BorderLayout());
         p.add(ok, BorderLayout.SOUTH);
         QNamePanel qnp = new QNamePanel(uiAgent, XsdQNamePlugin.MODEL_GROUP);
         qnp.setImportRegistry(ir);
         qnp.setNamespaceImporter(ni);
         m_panel = qnp;
         p.add(qnp);
         setContentPane(p);
         ok.getOKButton().addActionListener(this);
         ok.getCancelButton().addActionListener(this);
         setSize(new Dimension(500, 200));
         setTitle(BindingEditorResources.ADD_GROUP);
         setLocationRelativeTo(w);
         setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
         setVisible(true);
      }

      public ExpandedName getSelectedName() {
         return m_panel.getExpandedName();
      }

      public boolean getOkHit() {
         return m_okHit;
      }

      public void actionPerformed(ActionEvent e) {
         if (e.getSource() == m_ok.getOKButton()) {
            m_okHit = true;
         }
         dispose();
      }
   }

   private static void surroundWithChoose(BindingTree tree, BindingNode onNode) {
      int[] pospath = EditableTreePathUtils.toPosPath(tree.getEditableModel(), onNode.getTreePath());
      Window w = SwingUtilities.getWindowAncestor(tree);
      ChooseDialog jd;
      if (w instanceof Frame) {
         jd = new ChooseDialog(tree.getUIAgent(), (Frame) w);
      }
      else {
         jd = new ChooseDialog(tree.getUIAgent(), (JDialog) w);
      }
      ChooseBuilderPanel.Result r = jd.getResult();
      if (r == null) {
         return;
      }
      Binding cloned = onNode.getBinding().cloneDeep();
      SurroundWithEdit se = new SurroundWithEdit(tree, pospath, cloned, new ChooseSurroundWithBuilder(r), BindingEditorResources.SURROUND_WITH_CHOICE_MENU);
      se.doit();
      tree.getUndoManager().addEdit(se);
   }

   public interface SurroundWithBuilder {
      Binding build(Binding copyToEach);
   }

   private static class ChooseSurroundWithBuilder implements SurroundWithBuilder {
      private ChooseBuilderPanel.Result m_result;

      public ChooseSurroundWithBuilder(ChooseBuilderPanel.Result result) {
         m_result = result;
      }

      public Binding build(Binding copyToEach) {
         ChooseBinding cb = new ChooseBinding(BindingElementInfo.EMPTY_INFO);
         for (int i = 0; i < m_result.m_numberOfWhens; i++) {
            WhenBinding wb = new WhenBinding(BindingElementInfo.EMPTY_INFO, "");
            cb.addChild(wb);
            wb.addChild(copyToEach.cloneDeep());
         }
         if (m_result.m_includeOtherwise) {
            OtherwiseBinding ob = new OtherwiseBinding(BindingElementInfo.EMPTY_INFO);
            cb.addChild(ob);
            ob.addChild(copyToEach.cloneDeep());
         }
         return cb;
      }
   }

   private static class IfSurroundWithBuilder implements SurroundWithBuilder {
      public Binding build(Binding copyToEach) {
         IfBinding ob = new IfBinding(BindingElementInfo.EMPTY_INFO);
         ob.addChild(copyToEach.cloneDeep());
         return ob;
      }
   }

   private static class ForEachSurroundWithBuilder implements SurroundWithBuilder {
      public Binding build(Binding copyToEach) {
         ForEachBinding ob = new ForEachBinding(BindingElementInfo.EMPTY_INFO);
         ob.addChild(copyToEach.cloneDeep());
         return ob;
      }
   }

   private static class ForEachGroupSurroundWithBuilder implements SurroundWithBuilder {
      public Binding build(Binding copyToEach) {
         VirtualForEachGroupBinding ob = new VirtualForEachGroupBinding(BindingElementInfo.EMPTY_INFO);
         ob.addChild(new VirtualGroupingBinding(BindingElementInfo.EMPTY_INFO));
         ob.addChild(copyToEach.cloneDeep());
         return ob;
      }
   }

   private static class SurroundWithEdit extends AbstractUndoableEdit {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BindingTree m_tree;
      private final int[] m_path;
      private final Binding m_copiedNode;
      private final SurroundWithBuilder m_builder;
      private final String m_presentationName;

      public SurroundWithEdit(BindingTree tree, int[] path, Binding copyNode, SurroundWithBuilder builder, String presentationName) {
         m_tree = tree;
         m_path = path;
         m_copiedNode = copyNode;
         m_builder = builder;
         m_presentationName = presentationName;
      }

      public String getPresentationName() {
         return m_presentationName;
      }

      public void undo() throws CannotUndoException {
         super.undo();
         m_tree.stopEditing();
         undoit();
      }

      public void redo() throws CannotUndoException {
         super.redo();
         m_tree.stopEditing();
         doit();
      }

      public void doit() {
         TreePath path = EditableTreePathUtils.fromPosPath((EditableTreeModel) m_tree.getModel(), m_path);
         if (path == null) {
            return;
         }
         BindingNode node = (BindingNode) path.getLastPathComponent();
         Binding b = node.getBinding();
         Binding cb = m_builder.build(m_copiedNode);
         BindingManipulationUtils.replaceInParent(b, cb);
         BindingDisplayUtils.instantiateAnyAncestorOrSelfMarkers(cb.getParent());
         m_tree.setSelectionPath(path);
         m_tree.rebuild();
      }

      private void undoit() {
         TreePath path = EditableTreePathUtils.fromPosPath((EditableTreeModel) m_tree.getModel(), m_path);
         if (path == null) {
            return;
         }
         BindingNode node = (BindingNode) path.getLastPathComponent();
         Binding b = node.getBinding();
         BindingManipulationUtils.replaceInParent(b, m_copiedNode.cloneDeep());
         m_tree.setSelectionPath(path);
         m_tree.rebuild();
      }
   }

   private static class AddAnotherEdit extends AbstractUndoableEdit {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BindingTree m_tree;
      private final int[] m_path;

      public AddAnotherEdit(BindingTree tree, int[] path) {
         m_tree = tree;
         m_path = path;
      }

      public String getPresentationName() {
         return BindingEditorResources.MAKE_COPY_MENU;
      }

      public void undo() throws CannotUndoException {
         super.undo();
         m_tree.stopEditing();
         undoit();
      }

      public void redo() throws CannotUndoException {
         super.redo();
         m_tree.stopEditing();
         doit();
      }

      public void doit() {
         TreePath path = EditableTreePathUtils.fromPosPath((EditableTreeModel) m_tree.getModel(), m_path);
         if (path == null) {
            return;
         }
         BindingNode node = (BindingNode) path.getLastPathComponent();
         Binding b = node.getBinding();
         Binding p = b.getParent();
         if (p != null) {
            int ioc = p.getIndexOfChild(b);
            p.addChild(ioc + 1, b.cloneDeep());
            BindingDisplayUtils.instantiateAnyAncestorOrSelfMarkers(p);
            m_tree.setSelectionPath(path);
            m_tree.rebuild();
         }
      }

      private void undoit() {
         TreePath path = EditableTreePathUtils.fromPosPath((EditableTreeModel) m_tree.getModel(), m_path);
         if (path == null) {
            return;
         }
         BindingNode node = (BindingNode) path.getLastPathComponent();
         Binding b = node.getBinding();
         Binding p = b.getParent();
         if (p != null) {
            int ioc = p.getIndexOfChild(b);
            if (p.getChildCount() > ioc + 1) {
               p.removeChild(ioc + 1);
               m_tree.setSelectionPath(path);
               m_tree.rebuild();
            }
         }
      }
   }

   static class ChooseDialog extends BetterJDialog {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean m_okHit;
      private ChooseBuilderPanel m_panel;

      public ChooseDialog(UIAgent uiAgent, Dialog w) {
         super(uiAgent, w);
         setup(uiAgent);
      }

      public ChooseDialog(UIAgent uiAgent, Frame f) {
         super(uiAgent, f);
         setup(uiAgent);
      }

      public ChooseBuilderPanel.Result getResult() {
         if (!m_okHit) {
            return null;
         }
         return m_panel.getResult();
      }

      private void setup(UIAgent uiAgent) {
         setTitle(BindingEditorResources.CHOICE_PARAMETERS_TITLE);
         ChooseBuilderPanel cbp = new ChooseBuilderPanel();
         m_panel = cbp;
         final OKCancelPanel okc = new OKCancelPanel();
         JPanel jp = new JPanel(new BorderLayout());
         jp.add(cbp);
         jp.add(okc, BorderLayout.SOUTH);
         setContentPane(jp);
         setModal(true);
         setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
         ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               if (e.getSource() == okc.getOKButton()) {
                  m_okHit = true;
               }
               dispose();
            }
         };
         okc.getOKButton().addActionListener(al);
         okc.getCancelButton().addActionListener(al);

         pack();
         setLocation(500, 500);
         super.setSizePreferences("binding.choose.dialog", new Dimension(200, 100), new Dimension(300, 150));

         setVisible(true);
      }
   }
}

