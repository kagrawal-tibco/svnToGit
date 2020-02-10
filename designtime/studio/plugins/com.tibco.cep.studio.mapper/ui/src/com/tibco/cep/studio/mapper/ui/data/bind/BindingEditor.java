package com.tibco.cep.studio.mapper.ui.data.bind;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.InputStream;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToggleButton;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingNamespaceManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.bind.CancelChecker;
import com.tibco.cep.mapper.xml.xdata.bind.DefaultCancelChecker;
import com.tibco.cep.mapper.xml.xdata.bind.SharedTemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateEditorConfiguration;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.virt.BindingVirtualizer;
import com.tibco.cep.mapper.xml.xdata.xpath.CoercionSet;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.Parser;
import com.tibco.cep.mapper.xml.xdata.xpath.VariableDefinition;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.cep.studio.mapper.ui.data.DataTypeTreeNode;
import com.tibco.cep.studio.mapper.ui.data.TreeState;
import com.tibco.cep.studio.mapper.ui.data.bind.docview.BindingDocumentationDialog;
import com.tibco.cep.studio.mapper.ui.data.bind.fix.BindingFixerDialog;
import com.tibco.cep.studio.mapper.ui.data.param.TypeCategory;
import com.tibco.cep.studio.mapper.ui.data.utils.ButtonUtils;
import com.tibco.cep.studio.mapper.ui.data.utils.HorzSizedScrollPane;
import com.tibco.cep.studio.mapper.ui.data.utils.InlineToolBar;
import com.tibco.cep.studio.mapper.ui.data.utils.SoftDragNDropHandler;
import com.tibco.cep.studio.mapper.ui.data.xpath.XPathEditDialog;

/**
 * An xslt template content editor; this panel, though, is wrapped by {@link BindingEditorPanel} which provides higher-level
 * access (does a lot of the setup, etc.).
 */
public class BindingEditor extends JComponent {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private JComponent mRightWindow;
   private JScrollPane mRightScrollWindow; // contains mRightWindow.
   private JSplitPane mSplitPane;
   private JComponent mInputButtons;
   private Listener mListener = new Listener();

   private BindingDataTree mLeftTree;
   private BindingTree mBindingTree;
   private JPanel mRootPanel;
   private BindingLines mBindingLines;
   private JScrollPane mLeftScroll;
   private final UIAgent uiAgent;
   private int mTypeDividerLocation = 100;
   private int mDataDividerLocation = 80;
   private SoftDragNDropHandler mSoftDragNDropHandler;
   private StatementPanelManager m_statementPanelManager = StatementPanelManager.DEFAULT_INSTANCE;
   private InlineToolBar m_rightTools;

   private JToggleButton mEditButton;
   private JButton mFixButton;
   private JButton mStatementButton;
   private JButton mMoveUpButton;
   private JButton mMoveDownButton;
   private JButton mMoveInButton;
   private JButton mMoveOutButton;
   private JButton mAddButton;
   private JButton mAddChildButton;
   private JButton mDeleteButton;
   private JToggleButton mInlineEditingButton;
   private BindingDocumentationDialog mDocumentationWindow; // null or set.
   private TemplateEditorConfiguration mBindingConfiguration;
   private java.util.Vector<ChangeListener> mValueChangeListeners = new java.util.Vector<ChangeListener>();
   private Timer m_currentRepaintTimer;
   private boolean m_settingData; // a cheesy marker so that change events fired when setting the configuration are ignored.

   private int m_lastReportComputationCount; // Used to lazily detect when the input tab needs updating (after each process report update)

   static {
      Parser.parse("nothing"); // ensures that the class Expr is loaded so that the right message bundle is loaded.
   }

   public BindingEditor(UIAgent uiAgent) {
      this(uiAgent, null, true);
   }
   public BindingEditor(UIAgent uiAgent,
                        boolean showXmlEdittingButtons) {
      this(uiAgent, null, showXmlEdittingButtons);
   }
   /**
    *
    * @param doc
    * @param xmluiAgent
    * @param fResolver
    * @param categoryInputStream the InputStream from which to retrieve the
    * xpath function categories; if null, will use default functions
    * @param showXmlEdittingButtons
    */
   public BindingEditor(UIAgent uiAgent,
                        InputStream categoryInputStream,
                        boolean showXmlEdittingButtons) {
      this.uiAgent = uiAgent;
      setLayout(new BorderLayout());
      mSplitPane = new JSplitPane();
      mSplitPane.setBorder(BorderFactory.createEmptyBorder());
      mSplitPane.setContinuousLayout(true);
      mRootPanel = new LinesPanel();
      mRootPanel.add(mSplitPane, BorderLayout.CENTER);
      add(mRootPanel, BorderLayout.CENTER);

      mLeftTree = new BindingDataTree(this, uiAgent);
      mLeftTree.setName("BindingInputTree"); // name for internal use only; not visible.
      // Put scroll bar on left:

      mLeftScroll = new HorzSizedScrollPane(mLeftTree);
      mLeftScroll.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

      // This scroll mode seems the most effective:
      mLeftScroll.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);

      mLeftTree.addFocusListener(mListener);

      mRightScrollWindow = new HorzSizedScrollPane();

      // This scroll mode seems the most effective:
      mRightScrollWindow.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);

      mBindingTree = new BindingTree(uiAgent, this, mLeftTree, uiAgent.getFunctionResolver(), categoryInputStream);
      mBindingTree.setName("BindingFormulaTree");

      // Note: Not required to add selection-ends-editing --- JTree is already set with 'selection-ends-editing', so below is superfluous
      // (Code previously was here)

      JComponent bindButtons = buildBindButtons(showXmlEdittingButtons);
      JPanel lbuttons = new JPanel(new BorderLayout());
      JPanel inputButtons = mLeftTree.buildInputButtons(showXmlEdittingButtons, true);

      mInputButtons = inputButtons;
      lbuttons.add(inputButtons, BorderLayout.NORTH);

      JPanel rbuttons = new JPanel(new BorderLayout());

      rbuttons.add(bindButtons, BorderLayout.NORTH);
      rbuttons.add(mRightScrollWindow, BorderLayout.CENTER);
      mSplitPane.setRightComponent(rbuttons);

      setRightWindow(mBindingTree);
      mBindingLines = new BindingLines(this, mLeftScroll, mLeftTree, mLeftScroll, mRightScrollWindow, mBindingTree, inputButtons.getPreferredSize().height);
      mBindingTree.m_bindingLines = mBindingLines;
      mLeftTree.m_bindingLines = mBindingLines;
      lbuttons.add(mBindingLines, BorderLayout.CENTER);
      mSplitPane.setLeftComponent(lbuttons);

      // Because of the lines, just repaint more or less always:
      TreeExpansionListener tel = new TreeExpansionListener() {
         public void treeCollapsed(TreeExpansionEvent tee) {
            mBindingLines.markLinesDirty();
            repaintBodyArea();
         }

         public void treeExpanded(TreeExpansionEvent tee) {
            mBindingLines.markLinesDirty();
            repaintBodyArea();
         }
      };
      mLeftTree.addTreeExpansionListener(tel);
      mBindingTree.addTreeExpansionListener(tel);

      TreeSelectionListener tsl = new TreeSelectionListener() {
         public void valueChanged(TreeSelectionEvent tse) {
            // Believe it or not, as of this writing, in 2003, on a super-fast machine, the repainting was still
            // slow enough to justify this optimization.
            // This optimization basically waits until nothing has happened before 200ms before forcing a repaint
            // (which will draw selected lines correctly)
            //
            // Without this, tree selection is sluggish.
            //
            if (m_currentRepaintTimer != null) {
               m_currentRepaintTimer.stop();
            }
            Timer timer = new Timer(200, new ActionListener() {
               public void actionPerformed(ActionEvent ae) {
                  SwingUtilities.invokeLater(new Runnable() {
                     public void run() {
                        repaintBodyArea();
                        mBindingTree.refreshModelessDialogsForSelection();
                        m_currentRepaintTimer = null;
                     }
                  });
               }
            });
            timer.setRepeats(false);
            timer.start();
            m_currentRepaintTimer = timer;
         }
      };
      mLeftTree.addTreeSelectionListener(tsl);
      mBindingTree.addTreeSelectionListener(tsl);

      mSplitPane.addPropertyChangeListener(new PropertyChangeListener() {
         public void propertyChange(PropertyChangeEvent pce) {
            if (pce.getPropertyName().equals(JSplitPane.DIVIDER_LOCATION_PROPERTY)) {
               repaint();
            }
         }
      });
      mBindingTree.addContentChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent ce) {
            if (m_settingData) {
               // ignore while setting.
               return;
            }
            repaint();
            fireContentChanged();
         }
      });

      mLeftScroll.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
         public void adjustmentValueChanged(AdjustmentEvent ae) {
            // scrolling needs to repaint because of lines:
            mBindingLines.repaintLines();
         }
      });
      mRightScrollWindow.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
         public void adjustmentValueChanged(AdjustmentEvent ae) {
            // scrolling needs to repaint because of lines:
            mBindingLines.repaintLines();
         }
      });

      mBindingTree.addTreeSelectionListener(new TreeSelectionListener() {
         public void valueChanged(TreeSelectionEvent tse) {
            if (mRebuilding) {
               return;
            }
            reenableButtons();
            updateDisplayForNodeSelection();
         }
      });

      mLeftTree.addTreeSelectionListener(new TreeSelectionListener() {
         public void valueChanged(TreeSelectionEvent tse) {
            if (mRebuilding) {
               return;
            }
            reenableButtons();
            updateDisplayForNodeSelection();
         }
      });

      reenableButtons();
      loadPreferences();

      mSoftDragNDropHandler = new SoftDragNDropHandler(this, getDragNDropComponents());

      mBindingTree.setOvershowManager(mSoftDragNDropHandler);
      mLeftTree.setOvershowManager(mSoftDragNDropHandler);
   }

   void fireContentChanged() {
      for (int i = 0; i < mValueChangeListeners.size(); i++) {
         ChangeListener cl = mValueChangeListeners.get(i);
         cl.stateChanged(new ChangeEvent(this));
      }
   }

   /**
    * Repaints everything but the buttons.
    */
   private void repaintBodyArea() {
      int hh = mBindingLines.mHeaderHeight; // don't repaint buttons.
      mRootPanel.repaint(new Rectangle(0, hh, mRootPanel.getWidth(), mRootPanel.getHeight() - hh));
   }

   public void setStatementPanelManager(StatementPanelManager statementPanelManager) {
      m_statementPanelManager = statementPanelManager;
   }

   public StatementPanelManager getStatementPanelManager() {
      return m_statementPanelManager;
   }

   public void setBindingTreeState(TreeState state) {
      if (state != null) {
         mBindingTree.setTreeState(state);
      }
   }

   public TreeState getBindingTreeState() {
      return mBindingTree.getTreeState();
   }

   public void setInputTreeState(TreeState state) {
      if (state != null) {
         mLeftTree.setTreeState(state);
      }
   }

   public TreeState getInputTreeState() {
      return mLeftTree.getTreeState();
   }

   public void expandContent() {
      mLeftTree.expandContent();
      //WCETODO redo mBindingTree.expandContent();
   }

   public void copy() {
      if (mBindingTree.getSelectionCount() > 0) {
         mBindingTree.copy();
      }
      if (mLeftTree.getSelectionCount() > 0) {
         mLeftTree.copy();
      }
      // do some sort if omni-copy.
   }

   public void cut() {
      if (mBindingTree.getSelectionCount() > 0) {
         mBindingTree.cut();
      }
      if (mLeftTree.getSelectionCount() > 0) {
         mLeftTree.cut();
      }
      // do some sort if omni-copy.
   }

   public void delete() {
      if (mBindingTree.getSelectionCount() > 0) {
         mBindingTree.delete();
      }
      if (mLeftTree.getSelectionCount() > 0) {
         mLeftTree.delete();
      }
   }

   public void paste() {
      if (mBindingTree.getSelectionCount() > 0) {
         mBindingTree.paste();
      }
      if (mLeftTree.getSelectionCount() > 0) {
         mLeftTree.paste();
      }
   }

   public void showData(boolean showData) {
      /* data mode off!
      mShowData = showData;
      if (mShowData) {
          if (mInputData==null) {
              mInputData = generateBlankData();
          }
          setDataContextInternal();
      } else {
          setDataContextInternal();
      }*/
   }

   public void showDocumentation(boolean showDocs) {
      if (showDocs == (mDocumentationWindow != null)) {
         // no change.
         return;
      }
      if (!showDocs) {
         mDocumentationWindow.dispose();
         mDocumentationWindow = null;
      }
      else {
         mDocumentationWindow = new BindingDocumentationDialog(this, uiAgent);
         mDocumentationWindow.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
               closeDocumentationWindow();
            }

            public void windowClosing(WindowEvent e) {
               closeDocumentationWindow();
            }
         });
         // show whatever is selected now:
         updateDocumentationWindow();
      }
   }

   public void loadPreferences() {
      BindingPreferences bp = new BindingPreferences();
      bp.read(uiAgent);
      setPreferences(bp);
   }

   /**
    * Causes preferences to be written out.
    */
   public void savePreferences() {
      getPreferences().write(uiAgent);
   }

   public void close() {
      closeDocumentationWindow();
      mBindingTree.close();
   }

   private void closeDocumentationWindow() {
      if (mDocumentationWindow != null) {
         mDocumentationWindow.dispose();
         mDocumentationWindow = null;
         mLeftTree.closeDocumentationWindow();
      }
   }

   private BindingPreferences getPreferences() {
      BindingPreferences p = new BindingPreferences();
      p.inlineEditing = mInlineEditingButton.isSelected();

      p.typeTypeBindSplit = mSplitPane.getDividerLocation();
      p.typeLineBendSplit = mBindingLines.getTypeAreaWidth();
      if (!mBindingLines.getDataMode()) {
         p.typeBindTextSplit = mBindingTree.getDividerLocation();
      }
      else {
         p.typeBindTextSplit = mTypeDividerLocation;
      }

      p.dataDataBindSplit = mSplitPane.getDividerLocation();
      p.dataLineBendSplit = mBindingLines.getDataAreaWidth();
      if (mBindingLines.getDataMode()) {
         p.dataBindTextSplit = mBindingTree.getDividerLocation();
      }
      else {
         p.dataBindTextSplit = mDataDividerLocation;
      }
      p.dataDataTextSplit = mLeftTree.getDividerLocation();

      return p;
   }

   private void setPreferences(BindingPreferences prefs) {
      mInlineEditingButton.setSelected(prefs.inlineEditing);
      inlineEditingChanged();

      mSplitPane.setDividerLocation(prefs.typeTypeBindSplit);
      int lineSplit = prefs.typeLineBendSplit;
      if (prefs.typeTypeBindSplit - prefs.typeLineBendSplit < BindingLines.MINIMUM_WIDTH) {
         lineSplit = prefs.typeTypeBindSplit - BindingLines.MINIMUM_WIDTH;
      }
      mBindingLines.setTypeAreaWidth(lineSplit);
      if (!mBindingLines.getDataMode()) {
         mBindingTree.setDividerLocation(prefs.typeBindTextSplit);
      }
      else {
         mTypeDividerLocation = prefs.typeBindTextSplit;
      }

      // data
      mSplitPane.setDividerLocation(prefs.dataDataBindSplit);
      mBindingLines.setDataAreaWidth(prefs.dataLineBendSplit);
      if (mBindingLines.getDataMode()) {
         mBindingTree.setDividerLocation(prefs.dataBindTextSplit);
      }
      else {
         mDataDividerLocation = prefs.dataBindTextSplit;
      }
      mLeftTree.setDividerLocation(prefs.dataDataTextSplit);
   }

   private boolean mRebuilding; // set while rebuilding.

   public void refresh() {
      mRebuilding = true;
      try {
         mBindingTree.markReportDirty();
         mBindingTree.rebuild();
      }
      finally {
         mRebuilding = false;
      }
      reenableButtons();
      updateDisplayForNodeSelection();
   }

   public void test_setFormula(int row, String formula) {
      BindingNode bn = (BindingNode) mBindingTree.getPathForRow(row).getLastPathComponent();
      bn.setDataValue(formula);
      mBindingTree.markReportDirty();
      mBindingTree.repaint();
   }

   public String test_getFormula(int row) {
      BindingNode bn = (BindingNode) mBindingTree.getPathForRow(row).getLastPathComponent();
      return bn.getDataValue();
   }

   public int[] test_getLinesFrom(int row) {
      TreePath[] paths = mBindingLines.getRightPathsFor(mLeftTree.getPathForRow(row));
      if (paths == null) {
         return new int[0];
      }
      int[] ret = new int[paths.length];
      for (int i = 0; i < ret.length; i++) {
         ret[i] = mBindingTree.getRowForPath(paths[i]);
      }
      return ret;
   }

   public void test_showStatement() {
      showStatement();
   }

   /**
    * For use with SoftDragNDrop window
    */
   private JComponent[] getDragNDropComponents() {
      return new JComponent[]{mLeftTree, mBindingTree};
   }

   /**
    * Fired for every user data change.
    */
   public void addValueChangeListener(ChangeListener cl) {
      mValueChangeListeners.add(cl);
   }

   public void removeValueChangeListener(ChangeListener cl) {
      mValueChangeListeners.remove(cl);
   }

   /**
    * Sets the type categories available to the any substitution dialog
    * (From parameter editor)
    */
   public void setSubstitutionDialogCallback(TypeCategory[] typeCats, TypeCategory[] elCats) {
      mLeftTree.setTypeCategories(typeCats, elCats);
   }

   /**
    * Stops any cell editing (with an 'ok'), if any.
    */
   public void stopEditing() {
      // if it was editing...
      mBindingTree.stopEditing();
   }

   public void setErrorDisplay(JComponent errorDisplay) {
      if (errorDisplay == null) {
         return;
      }
      setRightWindow(errorDisplay);
      if (mRootPanel.getComponent(0) != mSplitPane) {
         mRootPanel.removeAll();
         mRootPanel.add(mSplitPane, BorderLayout.CENTER);
         revalidate();
         repaint();
      }
   }

   /**
    * Sets the data and context for the display.
    */
   public void setTemplateEditorConfiguration(TemplateEditorConfiguration configuration) {
      setTemplateEditorConfiguration(configuration, new DefaultCancelChecker());
   }

   public synchronized void setTemplateEditorConfiguration(TemplateEditorConfiguration bindingTemplate, CancelChecker cancelChecker) {
      // (Is synchronized because we can use this in the lazy loading.
      m_settingData = true; // so any change events are ignored.
      mLeftTree.setSchemaProvider(bindingTemplate.getExprContext().getInputSchemaProvider());
      try {
         mBindingTree.markReportDirty();
         mBindingTree.setTemplateEditorConfiguration(bindingTemplate);
         setRightWindow(mBindingTree);
         mBindingConfiguration = bindingTemplate;
         mLeftTree.setNamespaceImporter(BindingNamespaceManipulationUtils.createNamespaceImporter(bindingTemplate.getBinding()));
         if (cancelChecker.hasBeenCancelled()) {
            return;
         }
         mBindingTree.waitForReport(cancelChecker);
         if (cancelChecker.hasBeenCancelled()) {
            return;
         }
         updateInputTree();
         mBindingLines.markLinesDirty();
         repaint();
      }
      finally {
         m_settingData = false;
      }
   }

   /*
   public void setDataContext(DataContext inputData) {
       if (inputData==mInputData) {
           return;
       }
       mShowData = inputData!=null;
       mLeftTree.mShowDataButton.setSelected(mShowData);
       //ExprContext ec = mBindingConfiguration.getExprContext();
       //mInputData = inputData.cloneIntoSchemaProvider(ec.getVariables(),null, mSchemaProvider);
       setDataContextInternal();
   }

   private void setDataContextInternal() {
       boolean inDataMode = mBindingLines.getDataMode();

       if (mShowData) {
           mBindingTree.setDataContext(mInputData);
           mLeftTree.setDataContext(mInputData);
           mLeftTree.setEditable(mDataTreeEditable);
       } else {
           mBindingTree.setDataContext(null);
           CoercionSet cs = mBindingConfiguration.getCoercionSet();
           mLeftTree.setExprContext(mBindingConfiguration.getExprContext(),cs);
           mLeftTree.setEditable(false);
       }

       if (!mShowData && inDataMode) {
           // switch out of data mode:
           mDataDividerLocation = mBindingTree.getDividerLocation();
           mBindingTree.setDividerLocation(mTypeDividerLocation);
           mBindingLines.setDataMode(false);
           revalidate();
           repaint();
           return;
       }
       if (mShowData && !inDataMode) {
           // switch into data mode:
           mTypeDividerLocation = mBindingTree.getDividerLocation();
           mBindingTree.setDividerLocation(mDataDividerLocation);

           // do this after so it can resize based on above:
           mBindingLines.setDataMode(true);

           revalidate();
           repaint();
           mBindingTree.markReportDirty();
       }
   }*/

   public TemplateEditorConfiguration getTemplateEditorConfiguration() {
      return mBindingTree.getTemplateEditorConfiguration();
   }

   public void setBindingVirtualizer(BindingVirtualizer virtualizer) {
      mBindingTree.setBindingVirtualizer(virtualizer);
   }

   public BindingVirtualizer getBindingVirtualizer() {
      return mBindingTree.getBindingVirtualizer();
   }

   public void setInputLabel(String label) {
      mLeftTree.setLabel(label);
   }

   public String getInputLabel() {
      return mLeftTree.getLabel();
   }

   public void setInputLabelTooltip(String tooltip) {
      mLeftTree.setLabelTooltip(tooltip);
   }

   public String getInputLabelTooltip() {
      return mLeftTree.getLabelTooltip();
   }

   public void setOutputLabel(String outputLabel) {
      m_rightTools.setLabel(outputLabel);
   }

   public String getOutputLabel() {
      return m_rightTools.getLabel();
   }

   public void setInputRootDisplayIcon(Icon icon) {
      mLeftTree.setRootDisplayIcon(icon);
   }

   public Icon getInputRootDisplayIcon() {
      return mLeftTree.getRootDisplayIcon();
   }

   public void setOutputRootDisplayIcon(Icon icon) {
      mBindingTree.setRootDisplayIcon(icon);
   }

   public Icon getOutputRootDisplayIcon() {
      return mBindingTree.getRootDisplayIcon();
   }

   /**
    * Allows overriding of the the name displayed on the 'root' binding element.
    *
    * @param name The override name, or null, for using the default.
    */
   public void setOutputRootDisplayName(String name) {
      mBindingTree.setRootDisplayName(name);
   }

   /**
    * Sets if the root should be name should be displayed in bold.
    *
    * @param isBold
    */
   public void setOutputRootDisplayNameBold(boolean isBold) {
      //mBindingTree.setRootDisplayNameBold(isBold);
   }

   /**
    * @return The override name, or null for none.
    */
   public String getOutputRootDisplayName() {
      return mBindingTree.getRootDisplayName();
   }

   /**
    * Sets, for display purposes, if this represents debugger data or just plain testing data.<br>
    * By default, this is false
    *
    * @param debuggerActive True if the debugger is active.
    */
   public void setIsDebuggerActive(boolean debuggerActive) {
      mLeftTree.setIsDebuggerActive(debuggerActive);
   }

   /**
    * For testing purposes.
    */
   public BindingTree getBindingTree() {
      return mBindingTree;
   }

   /**
    * Expands to and selects the node (if is in the template being edited)
    *
    * @param node
    */
   public void selectNode(Binding node) {
      if (node == null) {
         return;
      }
      BindingNode bn = (BindingNode) mBindingTree.getRootNode();
      BindingNode sel = bn.findForBinding(node, true);
      mBindingTree.selectAndShowNode(sel);
   }

   /**
    * Implementation override, this editor supports read-only mode by setting enabled to <code>false</code>
    *
    * @param enabled
    */
   public void setEnabled(boolean enabled) {
      super.setEnabled(enabled);
      // Use tree-editable rather than enabled, because otherwise you can't even <look> at the tree very well.
      mBindingTree.setTreeEditable(enabled);
   }

   /**
    * For painting the lines over the other components.
    */
   private class LinesPanel extends JPanel {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LinesPanel() {
         super(new BorderLayout());
      }

/*        public boolean isOptimizedDrawingEnabled()
        {
            return false;
        }*/

      public void paint(Graphics g) {
         super.paint(g);
         mBindingLines.drawLines(g, mInputButtons.getHeight(), false, false);
      }
   }

   public void paint(Graphics g) {
      // See if we need to refresh the input tree before painting it (might be dirty)
      int ct = mBindingTree.waitForReport();
      if (ct != m_lastReportComputationCount) {
         m_lastReportComputationCount = ct;
         updateInputTree();
      }
      super.paint(g);
      mSoftDragNDropHandler.paintDragging(g);
   }

   private void setRightWindow(JComponent right) {
      if (mRightWindow == right) {
         return;
      }
      if (mRightWindow != null) {
         mRightWindow.removeFocusListener(mListener);
      }
      mRightWindow = right;
      mRightScrollWindow.setViewportView(mRightWindow);
      if (mRightWindow != null) {
         mRightWindow.addFocusListener(mListener);
      }
   }

   /*public void setUndoManager(UndoManager undoManager) {
       mUndoManager = undoManager;
   }*/

   private class Listener implements FocusListener {
      public void focusLost(FocusEvent fe) {
         // who cares...
      }

      public void focusGained(FocusEvent fe) {
         // Remove the selection from the opposite window:

         if (fe.getSource() == mLeftTree) {
            if (mRightWindow != null && mRightWindow instanceof BindingTree) {
               ((BindingTree) mRightWindow).clearSelection();
            }
            mBindingLines.clearSelection();
         }
         if (fe.getSource() == mRightWindow) {
            if (mLeftTree != null) {
               mLeftTree.clearSelection();
            }
            mBindingLines.clearSelection();
         }
      }
   }

   private JComponent buildBindButtons(boolean showXmlEdittingButtons) {

      mStatementButton = ButtonUtils.makeBarButton(DataIcons.getStatementIcon(), StatementDialog.EDIT_DIALOG_TITLE);
      mStatementButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent ae) {
            showStatement();
         }
      });

      mEditButton = mBindingTree.getButtonManager().getEditButton();
      mEditButton.setToolTipText(XPathEditDialog.getDialogTitle());

      mFixButton = ButtonUtils.makeBarButton(DataIcons.getFixIcon(), BindingFixerDialog.TITLE);
      mFixButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent ae) {
            fixBinding();
         }
      });

      if(showXmlEdittingButtons) {
         mAddButton = mBindingTree.getButtonManager().getAddAtButton();
         mAddChildButton = mBindingTree.getButtonManager().getAddChildButton();
         mMoveDownButton = mBindingTree.getButtonManager().getMoveDownButton();
         mMoveUpButton = mBindingTree.getButtonManager().getMoveUpButton();
         mDeleteButton = mBindingTree.getButtonManager().getDeleteButton();

         mMoveInButton = mBindingTree.getButtonManager().getMoveInButton();
         mMoveOutButton = mBindingTree.getButtonManager().getMoveOutButton();
         
         mMoveDownButton.setEnabled(false);
         mMoveUpButton.setEnabled(false);
         mDeleteButton.setEnabled(false);
         mMoveInButton.setEnabled(false);
         mMoveOutButton.setEnabled(false);
      }

      mInlineEditingButton = ButtonUtils.makeBarToggleButton(DataIcons.getInlineEditIcon(), BindingEditorResources.SHOW_FORMULA_IN_LINE);
      mInlineEditingButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent ae) {
            inlineEditingChanged();
         }
      });

      InlineToolBar tools = new InlineToolBar(uiAgent);

      tools.addButton(mInlineEditingButton);
      tools.addSeparator();

      if(showXmlEdittingButtons) {
         tools.addButton(mMoveUpButton);
         tools.addButton(mMoveDownButton);
         tools.addButton(mMoveOutButton);
         tools.addButton(mMoveInButton);
         tools.addButton(mDeleteButton);
         tools.addButton(mAddButton);
         tools.addButton(mAddChildButton);

         tools.addSeparator();
      }
      tools.addButton(mFixButton);
      tools.addButton(mStatementButton);
      tools.addButton(mEditButton);

      tools.setLabel("<< Fill me in>>");
      m_rightTools = tools;

      return tools;
   }

   private void showStatement() {
      mBindingTree.stopEditing();
      TreePath path = mBindingTree.getSelectionPath();
      if (path != null) {
         BindingNode bn = (BindingNode) path.getLastPathComponent();
         mBindingTree.waitForReport();
         StatementDialog.showEditDialog(mBindingTree,
                                        mBindingTree.getImportRegistry(),
                                        uiAgent,
                                        m_statementPanelManager,
                                        bn);
      }
   }

   /**
    * For when locally declared variables change (or are added, etc.)
    */
   private void updateInputTree() {
      if (mBindingConfiguration == null) {
         return; // for startup..
      }
      mBindingTree.waitForReport();
      TemplateReport rootReport = ((BindingNode) mBindingTree.getRootNode()).getTemplateReport();
      if (rootReport == null) {
         return; // happpened somehow, probably a startup issue.
      }
      SharedTemplateReport shared = rootReport.getSharedTemplateReport();

      ExprContext ec = mBindingConfiguration.getExprContext();
      VariableDefinition[] localVars = shared.getVariables();
      for (int i = 0; i < localVars.length; i++) {
         ec = ec.createWithNewVariable(localVars[i]);
      }
      ec = ec.createWithCurrentGroup(shared.getCurrentGroup());
      CoercionSet cs = mBindingConfiguration.getCoercionSet();
      mLeftTree.setExprContext(ec, cs);
      mBindingLines.markLinesDirty(); // tree changes, so we need to rebuild.
   }

   private void inlineEditingChanged() {
      boolean in = mInlineEditingButton.isSelected();
      mBindingTree.setShowInline(in);
   }

   private void fixBinding() {
      // Always work off the root binding.
      Binding root = mBindingConfiguration.getBinding();
      if (BindingFixerDialog.showDialog(uiAgent, this.mBindingTree, root)) {
         refresh();
      }
   }

   private void updateDocumentationWindow() {
      if (mDocumentationWindow == null) {
         return;
      }
      TreePath bt = mBindingTree.getSelectionPath();
      mBindingTree.waitForReport();
      if (bt != null) {
         BindingNode bn = (BindingNode) bt.getLastPathComponent();
         mDocumentationWindow.set(bn);
      }
      else {
         TreePath tp = mLeftTree.getSelectionPath();
         if (tp != null) {
            DataTypeTreeNode dttn = (DataTypeTreeNode) tp.getLastPathComponent();
            mDocumentationWindow.set(dttn);
         }
         else {
            mDocumentationWindow.clear();
         }
      }
   }

   private void updateDisplayForNodeSelection() {
      // update docs:
      updateDocumentationWindow();
   }

   private void reenableButtons() {
      mEditButton.setEnabled(true); // edit is always available now; it is modeless.
      TreePath tp = mBindingTree.getSelectionPath();
      if (tp == null) {
         mStatementButton.setEnabled(false);
         mFixButton.setEnabled(true); // this is always enabled, even if no selection (works off root)
         return;
      }
      //BindingNode node = (BindingNode) tp.getLastPathComponent();
      //boolean hasParent = node.getParent()!=null;

      mStatementButton.setEnabled(true); // ... allow root to be edited, too, now.
      mFixButton.setEnabled(true);
   }

   void stopInlineEditing() {
      mBindingTree.stopEditing();
   }
   
   public void addTextAreaFocuslListener( FocusListener fListener){
	   mBindingTree.addTextAreaFocuslListener(fListener);
   }
   
   public void removeTextAreaFocuslListener( FocusListener fListener){
	   mBindingTree.removeTextAreaFocuslListener(fListener);
   }

}
