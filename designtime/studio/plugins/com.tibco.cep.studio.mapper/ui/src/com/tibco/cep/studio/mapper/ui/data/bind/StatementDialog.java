package com.tibco.cep.studio.mapper.ui.data.bind;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.tree.TreePath;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.BindingManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.bind.BindingNamespaceManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.bind.CommentBinding;
import com.tibco.cep.mapper.xml.xdata.bind.ElementBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportArguments;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportFormulaCache;
import com.tibco.cep.mapper.xml.xdata.bind.virt.MarkerBinding;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.cep.studio.mapper.ui.data.utils.BetterJDialog;
import com.tibco.cep.studio.mapper.ui.data.utils.OKCancelPanel;
import com.tibco.cep.studio.mapper.ui.data.utils.PreferenceUtils;
import com.tibco.cep.studio.mapper.ui.edittree.EditableTreeModel;
import com.tibco.cep.studio.mapper.ui.edittree.EditableTreePathUtils;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.SmWildcard;
import com.tibco.xml.schema.xtype.SMDT;

/**
 * For {@link BindingTree} presents a set of element choices for {@link Binding}.
 */
public class StatementDialog extends BetterJDialog {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private boolean mOkHit;
   private JPanel mDetailsPanel;

   private Binding m_startingBinding; // The snapshot of the original (which is live edited & discarded on cancel)
   private Binding mCurrentBinding; // The original to edit.
   private JComboBox mTypes;
   private OKCancelPanel mOKCancel;
   private ActionListener mListener;
   private UIAgent uiAgent;
   private NamespaceContextRegistry m_parentNamespaceContextRegistry; // Since we're editing a possibly unattached element, need parent context.
   private ImportRegistry m_importRegistry;
   private SmSequenceType m_outputContext;
   private SmSequenceType m_remainingType;
   private boolean m_editable = true;

   public static final String EDIT_DIALOG_TITLE = BindingEditorResources.EDIT_STATEMENT_TITLE;

   class Renderer extends DefaultListCellRenderer {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Component getListCellRendererComponent(JList list, Object val, int index, boolean sel, boolean foc) {
         StatementPanel sp = (StatementPanel) val;
         String dn;
         Icon ic;
         if (sp == null) {
            // Just in case, shouldn't happen.
            dn = "<error no statement panel>";
            ic = DataIcons.getErrorIcon();
         }
         else {
            dn = sp.getDisplayName();
            ic = sp.getDisplayIcon(null);
         }
         super.getListCellRendererComponent(list, dn, index, sel, foc);
         setIcon(ic);
         return this;
      }
   }

   StatementDialog(UIAgent uiAgent, Dialog d) {
      super(uiAgent, d);
   }

   StatementDialog(UIAgent uiAgent, Frame d) {
      super(uiAgent, d);
   }

   void init(NamespaceContextRegistry parentNamespaceContextRegistry, ImportRegistry importRegistry,
             Binding targetParentPrototypeBinding, Binding startingBinding,
             Binding defBinding, boolean mustAllowChildren, UIAgent uiAgent,
             StatementPanelManager statementPanels, SmSequenceType outputContext, SmSequenceType remainingType) {
	   this.uiAgent = uiAgent;
	   m_importRegistry = importRegistry;
      m_parentNamespaceContextRegistry = parentNamespaceContextRegistry;
      if (importRegistry == null) {
         throw new NullPointerException("Null import registry");
      }
//      if (importRegistry == null) {
//         throw new NullPointerException("Null parent namespace importer");
//      }
      m_outputContext = outputContext;
      if (m_outputContext == null) {
         throw new NullPointerException("Null output context");
      }
      m_remainingType = remainingType;
      if (m_remainingType == null) {
         throw new NullPointerException("Null remaining type");
      }

      m_startingBinding = startingBinding;
      mCurrentBinding = defBinding;
      if (defBinding == null) {
         // otherwise.... this'll have to do.
         mCurrentBinding = new ElementBinding(BindingElementInfo.EMPTY_INFO, ExpandedName.makeName(""));
      }

      mListener = new ActionListener() {
         public void actionPerformed(ActionEvent ae) {
            action(ae.getSource());
         }
      };
      mOKCancel = new OKCancelPanel();
      mOKCancel.getOKButton().addActionListener(mListener);
      mOKCancel.getCancelButton().addActionListener(mListener);

      mDetailsPanel = new JPanel(new BorderLayout());
      //Border lb = BorderFactory.createLineBorder(Color.black);
      //mDetailsPanel.setBorder(BorderFactory.createCompoundBorder(lb,BorderFactory.createEmptyBorder(8,4,8,4)));
      mDetailsPanel.setBorder(BorderFactory.createEmptyBorder(8, 4, 8, 4));
      getContentPane().add(new JScrollPane(mDetailsPanel), BorderLayout.CENTER);
      getContentPane().add(mOKCancel, BorderLayout.SOUTH);

      mTypes = new JComboBox();
      mTypes.setRenderer(new Renderer());
      // don't ever display markers here, so replace w/ either element or attribute:
      if (mCurrentBinding instanceof MarkerBinding) {
         mCurrentBinding = BindingManipulationUtils.createAppropriateAttributeOrElementBinding((MarkerBinding) mCurrentBinding);
         if (mCurrentBinding == null) {
            // assume it was an element.
            mCurrentBinding = new ElementPanel().createNewInstance(mCurrentBinding);
         }
      }
      loadAppropriatePanels(statementPanels, targetParentPrototypeBinding, mustAllowChildren);

      // do after above to avoid bogus events...
      mTypes.addActionListener(mListener);

      JPanel combo = new JPanel(new BorderLayout());
      combo.add(new JLabel(BindingEditorResources.STATEMENT_TYPE + ":  "), BorderLayout.WEST);
      JPanel spacer = new JPanel(new BorderLayout());
      spacer.add(mTypes, BorderLayout.WEST);
      Dimension ps = mTypes.getPreferredSize();
      // Since the content is dynamic, do a bit to ensure a reasonable minimum size:
      mTypes.setPreferredSize(new Dimension(Math.max(ps.width + 50, 150), ps.height));
      combo.add(spacer);
      combo.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

      getContentPane().add(combo, BorderLayout.NORTH);

      change();

      Dimension sz = PreferenceUtils.readDimension(uiAgent, "StatementDialog.windowSize",
                                                   new Dimension(450, 300),
                                                   Toolkit.getDefaultToolkit().getScreenSize(),
                                                   new Dimension(500, 350));
      Point loc = PreferenceUtils.readLocation(uiAgent, "StatementDialog.windowLocation",
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
   }

   /**
    * Sets if this is editable.<br>
    * By default, this is <code>true</code>
    *
    * @param editable
    */
   public void setEditable(boolean editable) {
      mOKCancel.getOKButton().setEnabled(editable);
      m_editable = editable;
   }

   public boolean isEditable() {
      return m_editable;
   }

   /**
    * Initializes the combo w/ the choices of all the appropriate panels, sorted in order.
    *
    * @param statementPanels
    * @param targetParentPrototypeBinding
    */
   private void loadAppropriatePanels(StatementPanelManager statementPanels, Binding targetParentPrototypeBinding, boolean mustAllowChildren) {
      StatementPanel parentPanel = statementPanels.getStatementPanelFor(targetParentPrototypeBinding);
      StatementPanel startingPanel = statementPanels.getStatementPanelFor(mCurrentBinding);

      StatementPanel[] allPanels = statementPanels.getAllPanels();
      ArrayList<StatementPanel> al = new ArrayList<StatementPanel>();
      for (int i = 0; i < allPanels.length; i++) {
         StatementPanel sp = allPanels[i];
         Binding sampleBindingForPanel = sp.createNewInstance(null);
         // With the exception of 'markers' --- which never get displayed --- we always want be displaying the
         // current panel type, even if it is not legal in this context.
         boolean isStartingPanel = sp == startingPanel;
         boolean canHaveAsChild = parentPanel.canHaveAsChild(targetParentPrototypeBinding, sampleBindingForPanel);
         boolean canBeParent = !mustAllowChildren || !(sampleBindingForPanel instanceof CommentBinding);
         if (isStartingPanel || (canHaveAsChild && canBeParent)) {
            al.add(sp);
         }
      }
      StatementPanel[] all = al.toArray(new StatementPanel[0]);
      Comparator<StatementPanel> c = new Comparator<StatementPanel>() {
         public int compare(StatementPanel o1, StatementPanel o2) {
            StatementPanel p1 = (StatementPanel) o1;
            StatementPanel p2 = (StatementPanel) o2;
            return p1.getDisplayName().compareTo(p2.getDisplayName());
         }
      };
      Arrays.sort(all, c);
      for (int i = 0; i < all.length; i++) {
         StatementPanel sp = all[i];
         mTypes.addItem(all[i]);
         boolean isStartingPanel = sp == startingPanel;
         if (isStartingPanel) {
            mTypes.setSelectedIndex(mTypes.getItemCount() - 1);
         }
      }
   }

   private void change() {
      mDetailsPanel.removeAll();
      StatementPanel panel = (StatementPanel) mTypes.getSelectedItem();
      boolean okok = true;
      if (panel != null) {
         //Add logic if existing one...
         if (m_startingBinding != null && panel.getHandlesBindingClass() == m_startingBinding.getClass()) {
            // If we're on the same editor as the original (starting) binding, just use it unmolested:
            mCurrentBinding = m_startingBinding;
         }
         else {
            mCurrentBinding = panel.createNewInstance(m_startingBinding);
         }
         // Need a namespace importer that's global... (may have a new binding...)
         JComponent got = panel.getDetailsEditor(mCurrentBinding, m_parentNamespaceContextRegistry, m_importRegistry,
        		 uiAgent, m_outputContext, m_remainingType);
         if (got != null) {
            mDetailsPanel.add(got);
         }
      }
      mOKCancel.getOKButton().setEnabled(okok);
      mDetailsPanel.revalidate();
      mDetailsPanel.repaint();
   }

   public void dispose() {
      PreferenceUtils.writeDimension(uiAgent, "StatementDialog.windowSize", getSize());
      PreferenceUtils.writePoint(uiAgent, "StatementDialog.windowLocation", getLocation());
      super.dispose();
   }

   public static boolean showEditDialog(BindingTree tree, ImportRegistry importRegistry,
                                        UIAgent uiAgent,
                                        StatementPanelManager panelManager, BindingNode node) {
      TemplateReport report = node.getTemplateReport();
      StatementDialog sd;
      Window window = SwingUtilities.getWindowAncestor(tree);
      if (window instanceof Dialog) {
         sd = new StatementDialog(uiAgent, (Dialog) window);
      }
      else {
         sd = new StatementDialog(uiAgent, (Frame) window);
      }
      sd.setIconImage(((ImageIcon)DataIcons.getWindowTitleIcon()).getImage());
      if (report.getBinding() instanceof TemplateBinding) {
         // Root needs special treatment, can't replace parent on it.
         return showEditRoot(sd, tree, report, importRegistry, uiAgent, panelManager);
      }
      SmSequenceType outputContext = report.getOutputContext();
      SmSequenceType possibleTypes = computePossibleTypes(report);
      //WCETODO filter remaining by looking at the NEXT one.
      sd.init(BindingNamespaceManipulationUtils.createNamespaceImporter(report.getBinding().getParent()),
              importRegistry,
              report.getBinding().getParent(),
              report.getBinding(),
              report.getBinding().cloneDeep(), // Because we want to be able to cancel/undo, work on a copy of it.
              false, // false -> doesn't need to allow children.
              uiAgent,
              panelManager,
              outputContext,
              possibleTypes);
      sd.setTitle(EDIT_DIALOG_TITLE);
      sd.setEditable(tree.isTreeEditable());
      sd.setVisible(true);

      if (sd.mOkHit) {
         int[] posPath = EditableTreePathUtils.toPosPath(tree.getEditableModel(), node.getTreePath());
         Binding newone = sd.mCurrentBinding;
         BindingManipulationUtils.copyBindingContents(sd.m_startingBinding.cloneDeep(), newone);
         // Markers will be regenerated in the Edit (after inserting & re-running the report)
         BindingManipulationUtils.removeMarkerChildren(newone);
         Edit e = new Edit(tree, posPath, sd.m_startingBinding, newone);
         tree.getUndoManager().addEdit(e);
         e.doit();
      }

      return sd.mOkHit;
   }

   private static boolean showEditRoot(StatementDialog sd, BindingTree tree,
                                       TemplateReport report, ImportRegistry importRegistry,
                                       UIAgent uiAgent,
                                       StatementPanelManager panelManager) {
      SmSequenceType outputContext = report.getOutputContext(); // really n/a here.
      SmSequenceType possibleTypes = computePossibleTypes(report); // really n/a here.
      BindingElementInfo origInfo = report.getBinding().getElementInfo();

      sd.init(BindingNamespaceManipulationUtils.createNamespaceImporter(report.getBinding().getParent()),
              importRegistry,
              report.getBinding().getParent(),
              report.getBinding(),
              report.getBinding(),
              false, // false -> doesn't need to allow children.
              uiAgent,
              panelManager,
              outputContext,
              possibleTypes);
      sd.setTitle(EDIT_DIALOG_TITLE);
      sd.setEditable(tree.isTreeEditable());
      sd.setVisible(true);

      if (!sd.mOkHit) {
         // not undoable yet...

         // Just revert for cancel:
         report.getBinding().setElementInfo(origInfo);
      }
      else {
         tree.contentChanged();
      }

      return sd.mOkHit;
   }

   private static SmSequenceType computePossibleTypes(TemplateReport report) {
      SmSequenceType possibleType = report.getExpectedType(); // want types available at START of this element (not end)
      if (possibleType == null) {
         TemplateReport next = report.getNext();
         if (next == null) {
            return SMDT.VOID;
         }
         return computePossibleTypes(next);
      }
      return possibleType;
   }

   private static class Edit extends AbstractUndoableEdit {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int[] m_posPath;
      private Binding mOld;
      private Binding mNew;
      private BindingTree mTree;

      public Edit(BindingTree tree, int[] posPath, Binding old, Binding newv) {
         m_posPath = posPath;
         mOld = old;
         mNew = newv;
         if (old == null) {
            throw new NullPointerException("Old");
         }
         mTree = tree;
      }

      public String getPresentationName() {
         return BindingEditorResources.STATEMENT_TYPE;
      }

      public void undo() throws CannotUndoException {
         super.undo();
         undoit();
      }

      public void redo() throws CannotRedoException {
         super.redo();
         doit();
      }

      public void undoit() {
         TreePath at = EditableTreePathUtils.fromPosPath((EditableTreeModel) mTree.getModel(), m_posPath);
         if (at == null) {
            return;
         }
         BindingNode atn = (BindingNode) at.getLastPathComponent();
         Binding on = atn.getBinding();
         Binding oldone = mOld.cloneDeep();
         BindingManipulationUtils.replaceInParent(on, oldone);
         mTree.rebuild();
         mTree.contentChanged();

         refreshMarkerChildren(oldone);
      }

      public void doit() {
         TreePath at = EditableTreePathUtils.fromPosPath((EditableTreeModel) mTree.getModel(), m_posPath);
         if (at == null) {
            return;
         }
         BindingNode atn = (BindingNode) at.getLastPathComponent();
         Binding on = atn.getBinding();

         Binding newone = mNew.cloneDeep();
         BindingManipulationUtils.replaceInParent(on, newone);
         BindingDisplayUtils.instantiateAnyAncestorOrSelfMarkers(newone.getParent());
         mTree.rebuild();
         mTree.contentChanged();

         refreshMarkerChildren(newone);
      }

      private void refreshMarkerChildren(Binding newb) {
         // May need to rebuild the marker children of this node (since the element/type may have changed):
         TreePath path = mTree.getPathForBinding(newb);
         if (path != null) {
            BindingNode bn = (BindingNode) path.getLastPathComponent();
            mTree.waitForReport();
            TemplateReport tr = bn.getTemplateReport();
            // Because this may have added or require new markers, need to fill in markers manually:
            if (tr != null) {
               // Because report above was done w/o turning on create missing, we need to re-run this portion with it on:
               TemplateReportArguments args = new TemplateReportArguments();
               args.setRecordingMissing(true);
               TemplateReport tr2 = tr.getBinding().createTemplateReport(null, tr.getContext(), tr.getInitialOutputType(), tr.getOutputContext(), SmWildcard.STRICT, new TemplateReportFormulaCache(), args);
               if (BindingManipulationUtils.insertMarkers(tr2, false)) // false -> only go 1 level deep.
               {
                  // ok changed, so just rebuild again:
                  mTree.rebuild();
               }
            }
         }
      }
   }

   /**
    * Shows a statement dialog for creating a brand new binding.
    *
    * @param inOrAroundBinding The binding that is either being wrapped (if isCreateAround) or having a child added (otherwise)
    * @param isCreateAround    Indicates if the binding is being added as a wrapper around an existing binding, or as a child to a binding.
    * @return The new binding, or null if cancelled.
    */
   public static Binding showNewDialog(BindingTree tree, ImportRegistry importRegistry,
                                       UIAgent uiAgent,
                                       StatementPanelManager panelManager, TemplateReport inOrAroundBinding, boolean isCreateAround) {
      StatementDialog sd;
      Window window = SwingUtilities.getWindowAncestor(tree);
      if (window instanceof Dialog) {
         sd = new StatementDialog(uiAgent, (Dialog) window);
      }
      else {
         sd = new StatementDialog(uiAgent, (Frame) window);
      }
      sd.setIconImage(((ImageIcon)DataIcons.getWindowTitleIcon()).getImage());
      Binding defaultBinding = inOrAroundBinding.getBinding();
      StatementPanel sp = panelManager.getStatementPanelFor(defaultBinding);
      Binding defChild;
      if (isCreateAround) {
         // compute a default for adding around this one:
         defChild = sp.getDefaultAddAroundBinding();
      }
      else {
         // compute a default child for adding to this one:
         defChild = sp.getDefaultAddChildBinding();
      }
      SmSequenceType outputContext;
      SmSequenceType remainingType;
      Binding inBinding; // The future-parent binding of the one we're adding.
      if (isCreateAround) {
         // do some boundary condition checking to avoid NPE 1-2WZDSL
         if (inOrAroundBinding == null) {
            return null;
         }
         if (inOrAroundBinding.getParent() == null) {
            return null;
         }
         outputContext = inOrAroundBinding.getParent().getOutputContext();
         remainingType = inOrAroundBinding.getRemainingOutputType();
         inBinding = inOrAroundBinding.getParent().getBinding();
      }
      else {
         // Create inside:
         outputContext = inOrAroundBinding.getChildOutputContext();
         // WCETODO --- maybe make smarter about exact context position later, for now, just show all possible child types, ignoring position.
         remainingType = inOrAroundBinding.getChildOutputContext(); // make the whole thing the remaining type (so all choices show up)
         inBinding = inOrAroundBinding.getBinding();
      }
      sd.init(BindingNamespaceManipulationUtils.createNamespaceImporter(inBinding),
              importRegistry,
              inBinding,
              null,
              defChild,
              isCreateAround, // If is createaround must allow children.
              uiAgent,
              panelManager,
              outputContext,
              remainingType);
      sd.setTitle(isCreateAround ? BindingEditorResources.MOVE_INTO_NEW_STATEMENT_TITLE : BindingEditorResources.NEW_STATEMENT_TITLE);

      sd.setVisible(true);

      if (sd.mOkHit) {
         Binding result = sd.mCurrentBinding;
         return result;
      }

      return null;
   }

   private void action(Object source) {
      if (source == mOKCancel.getOKButton()) {
         mOkHit = true;
         dispose();
      }
      if (source == mOKCancel.getCancelButton()) {
         mOkHit = false;
         dispose();
      }
      if (source == mTypes) {
         change();
      }
   }
}

