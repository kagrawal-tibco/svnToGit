package com.tibco.cep.studio.mapper.ui.data.bind;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.FocusListener;
import java.io.InputStream;
import java.util.Iterator;

import javax.swing.Icon;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.xdata.bind.BindUtilities;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.DefaultStylesheetResolver;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateEditorConfiguration;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportFormulaCache;
import com.tibco.cep.mapper.xml.xdata.bind.virt.BindingVirtualizer;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.FunctionResolver;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.cep.mapper.xml.xdata.xpath.Utilities;
import com.tibco.cep.mapper.xml.xdata.xpath.VariableDefinitionList;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataTree;
import com.tibco.cep.studio.mapper.ui.data.TreeState;
import com.tibco.cep.studio.mapper.ui.data.XPathSoftDragItem;
import com.tibco.cep.studio.mapper.ui.data.utils.SoftDragNDropManager;
import com.tibco.cep.studio.mapper.ui.edittree.EditableTree;
import com.tibco.cep.studio.mapper.ui.edittree.render.NameValueTreeCellEditor;
import com.tibco.cep.studio.mapper.ui.edittree.render.NameValueTreeCellRenderer;
import com.tibco.xml.schema.SmNamespace;
import com.tibco.xml.schema.SmNamespaceProvider;
import com.tibco.xml.schema.xtype.SMDT;

/**
 * An editable display of Bindings input.
 */
public class BindingTree extends EditableTree {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ErrorFinder mErrorFinder; // only set if mStartingState set.
   private TemplateEditorConfiguration mConfiguration;
   private DataTree mInputTree; // ugh, needed for TreeState in wizard.
   private UIAgent uiAgent;
   private ImportRegistry m_importRegistry;
   private final BindingEditor mBindingEditor;
   BindingLines m_bindingLines; // assigned after tree constructed, ugly.
   private NameValueTreeCellRenderer m_cellRenderer;
   private Color mRowSeparatorColor = new Color(240, 240, 255);
   private BindingNodeRendererPlugin m_rendererPlugin;

   private BindingTreeModelessDialogManager m_modelessDialogManager;
   private BindingVirtualizer m_bindingVirtualizer = BindingVirtualizer.INSTANCE;

   private TemplateReportFormulaCache m_formulaCache = new TemplateReportFormulaCache();

   /**
    *
    * @param doc
    * @param xmluiAgent
    * @param bindingEditor
    * @param input
    * @param fResolver
    * @param categoryInputStream the InputStream from which to retrieve the
    * xpath function categories; if null, will use default functions
    */
   public BindingTree(UIAgent uiAgent,
                      BindingEditor bindingEditor,
                      DataTree input, FunctionResolver fResolver,
                      InputStream categoryInputStream) {
      super(uiAgent); // string not visible, just for debugging.
      this.uiAgent = uiAgent;
      mBindingEditor = bindingEditor;
      m_modelessDialogManager =
              new BindingTreeModelessDialogManager(mBindingEditor, this,
                                                   uiAgent,
                                                   fResolver, categoryInputStream);
      BindingNodeRendererPlugin pi = new BindingNodeRendererPlugin(uiAgent, mBindingEditor, this);
      m_rendererPlugin = pi;
      super.setAllowsCrossBranchDrops(true);
      super.setEditable(true);
      super.setInvokesStopCellEditing(true);

      NameValueTreeCellRenderer r = new NameValueTreeCellRenderer(this, pi);
      r.setHasDivider(true);
      m_cellRenderer = r;
      setCellRenderer(r);
      setCellEditor(new NameValueTreeCellEditor(this, r));
      setContextMenuHandler(new BindingTreeContextMenus(bindingEditor));

      SmNamespaceProvider emptySp = new SmNamespaceProvider() {
         public SmNamespace getNamespace(String namespaceURI) {
            return null;
         }

         @SuppressWarnings("rawtypes")
		public Iterator getNamespaces() {
            return null;
         }
      };
      mConfiguration = new TemplateEditorConfiguration(
              new ExprContext(new VariableDefinitionList(), fResolver).createWithInputAndOutputSchemaAndComponentProvider(emptySp, null),
              SMDT.REPEATING_ITEM,
              new TemplateBinding(BindingElementInfo.EMPTY_INFO, "placeholder", null)); // string not visible,

      super.setEditHandler(m_modelessDialogManager);

      mConfiguration.setStylesheetResolver(new DefaultStylesheetResolver());
      mInputTree = input;
      mErrorFinder = new ErrorFinder(m_formulaCache);
      super.setReportRunner(mErrorFinder);
      BindingTreeModel treeModel = new BindingTreeModel(this, new BindingNode(this, mConfiguration.getBinding()));
      setModel(treeModel);
      super.setDragNDropHandler(treeModel); // does double duty.
      super.setCopyPasteHandler(treeModel); // make that a triple.
      super.setExpansionHandler(treeModel); // Quad!

      setEditable(true);
      getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);

      TreeSelectionListener tsl = new TreeSelectionListener() {
         public void valueChanged(TreeSelectionEvent tse) {
            m_modelessDialogManager.applyFormulaChange();
         }
      };
      addTreeSelectionListener(tsl);
   }
   public BindingTree(UIAgent uiAgent,
                      BindingEditor bindingEditor,
                      DataTree input, FunctionResolver fResolver) {
      this(uiAgent, bindingEditor, input, fResolver, null);
   }

   public void setDividerLocation(int location) {
      m_cellRenderer.setDividerLocation(location);
   }

   public int getDividerLocation() {
      return m_cellRenderer.getDividerLocation();
   }

   public ImportRegistry getImportRegistry() {
      return m_importRegistry;
   }

   public void setRootDisplayName(String rootDisplayName) {
      m_rendererPlugin.m_rootDisplayName = rootDisplayName;
   }

   public void setRootDisplayIcon(Icon icon) {
      m_rendererPlugin.m_rootDisplayIcon = icon;
   }

   public Icon getRootDisplayIcon() {
      return m_rendererPlugin.m_rootDisplayIcon;
   }

   public String getRootDisplayName() {
      return m_rendererPlugin.m_rootDisplayName;
   }

   public BindingLines getBindingLines() {
      return m_bindingLines;
   }

   /**
    * The binding tree <b>always</b> has an associated input tree (left hand side tree).<br>
    * It does not contain this tree, but it, for various reasons, needs a reference to it.
    *
    * @return The left hand side (input) tree, never null.
    */
   public DataTree getAssociatedInputTree() {
      return mInputTree;
   }

   /**
    * Implementation detail for the move-in function.
    * <p/>
    * public BasicNodeBuilder buildGroupNode(BasicTreeNode around)
    * {
    * BindingNode bn = (BindingNode) around;
    * TemplateReport tr = bn.getTemplateReport();
    * final Binding b = StatementDialog.showNewDialog(
    * this,
    * mXmluiAgent,
    * m_importRegistry,
    * mDesignerDoc,
    * getStatementPanelManager(),
    * tr,
    * true // true -> we're building a binding to wrap this one.
    * );
    * if (b==null) {
    * return null;
    * }
    * return new BasicNodeBuilder()
    * {
    * public BasicTreeNode buildNode()
    * {
    * return new BindingNode(b.cloneDeep(),(BasicTreeModel)getModel());
    * }
    * };
    * }
    */

   public StatementPanelManager getStatementPanelManager() {
      return mBindingEditor.getStatementPanelManager();
   }

   public void setShowInline(boolean showInline) {
      stopEditing();
      m_cellRenderer.setHasDivider(showInline);
      super.setLineSeparatorColor(showInline ? mRowSeparatorColor : null);
      revalidate();
      repaint();
   }

   public void test_drop(Object object, int row) {
      TreePath path = getPathForRow(row);
      BindingNode l = (BindingNode) path.getLastPathComponent();
      Binding[] bp = l.getBindingArray();

      XPathSoftDragItem sdi = (XPathSoftDragItem) object;

      drop(l, null, bp, sdi.path);
   }

   /**
    * For GUI automation mostly, returns the text formula of the selected node or none if none is selected.
    *
    * @return The selected node text, or null
    */
   public String getSelectedText() {
      BindingNode bn = (BindingNode) getSelectionNode();
      if (bn == null) {
         return null;
      }
      return bn.getBinding().getFormula();
   }

   public void edit(BindingNode bnode) {
      BindingNode node = bnode;
      if (node != null && node.getTemplateReport() == null) {
         // Shouldn't happen, but don't 'splode if it does, somehow.
         System.err.println(">>> no report");
         return;
      }

      m_modelessDialogManager.showEditor(node);
   }

   public void setTemplateEditorConfiguration(TemplateEditorConfiguration input) {
      mConfiguration = input;
      mErrorFinder.setTemplateEditorConfiguration(mConfiguration);
      m_importRegistry = input.getImportRegistry();
      if (m_importRegistry == null) {
         throw new NullPointerException("Import registry required");
      }
      build();
   }

   public TemplateEditorConfiguration getTemplateEditorConfiguration() {
      return mConfiguration;
   }

   void repair() {
      mErrorFinder.setTemplateEditorConfiguration(mConfiguration);
   }

   public void setBindingVirtualizer(BindingVirtualizer bv) {
      m_bindingVirtualizer = bv;
   }

   public BindingVirtualizer getBindingVirtualizer() {
      return m_bindingVirtualizer;
   }

   /*
    * Override so when a paste, or something, changes the root node, we know about it.
    *
   protected void setRootNode(BasicTreeNode node) {
       BindingNode bn = (BindingNode) node;
       if (bn.getBinding() instanceof TemplateBinding)
       {
           mConfiguration.setBinding((TemplateBinding)bn.getBinding());
           mErrorFinder.setTemplateEditorConfiguration(mConfiguration);
           TemplateBinding tb = (TemplateBinding) bn.getBinding();
           StylesheetBinding sb = new StylesheetBinding(BindingElementInfo.EMPTY_INFO);
           sb.addChild(tb);
           super.setRootNode(node);
       }
   }*/

   public void resetUndoManager() {
      getUndoManager().discardAllEdits();
   }

   /**
    * Maybe shouldn't be public, force rebuilding of nodes (with same open-state) after a binding changes.
    */
   public void rebuild() {
      TreeState ts = getTreeState();
      build();
      // ?? is this actually required: waitForReport();
      setTreeState(ts);
   }

   private void build() {
      Binding rootBinding = mConfiguration.getBinding();
      ((BindingTreeModel) getModel()).setRoot(new BindingNode(this, rootBinding));
      mErrorFinder.setTemplateEditorConfiguration(mConfiguration);
      markReportDirty();
   }

   public Rectangle getDropHintBounds() {
      return mDropHintBounds;
   }

   public void dropStopped() {
      mDropHintBounds = null;
   }

   /*
   public int getDataCharWidth()
   {
       return mTextRenderer.getCharWidth();
   }*/

// LAMb: where do we get the render string?
//    protected int getCharacterPosition(int x)
//    {
//        return mTextRenderer.getCharacterPosition(str, x, 0);
//    }

   public TemplateReportFormulaCache getFormulaCache() {
      return m_formulaCache;
   }

   boolean dropOnLines(BindingNode node) {
      return BindingDisplayUtils.dropLeft(this, node);
   }

   /**
    * Implementation override to handle dropping of xpaths.
    */
   public boolean dragOver(SoftDragNDropManager manager, Point at, Object value) {
      if (!(value instanceof XPathSoftDragItem)) {
         mDropHintBounds = null;
         return super.dragOver(manager, at, value);
      }
      if (!isTreeEditable()) {
         return false;
      }
      XPathSoftDragItem dragObject = (XPathSoftDragItem) value;
      TreePath dropLocation = computeDropLocation(at);//,dragObject,manager.isDragCopy());

      mDropHintBounds = null;
      if (dropLocation == null) {
         return false;
      }
      mDropHintBounds = getPathBounds(dropLocation);
      BindingNode node = (BindingNode) dropLocation.getLastPathComponent();
      NameValueTreeCellRenderer r = m_cellRenderer;
      if (at.x > r.getDataOffset(node) + getInsets().left) {
         // dropping INSIDE the text.
         String text = ((BindingNode) dropLocation.getLastPathComponent()).getBinding().getFormula();
         if (text != null) {
            int xin = at.x - (r.getDataOffset(node) + getInsets().left);
            Utilities.DropTextInfo dti = m_rendererPlugin.getXPathTextRenderer().getDropTargetRange(text,
                                                                                                    xin,
                                                                                                    -1, // n/a, we're 1 line.
                                                                                                    -1, // n/a, we don't have selection.
                                                                                                    -1,
                                                                                                    dragObject.isCharacterReference);
            if (dti != null) {
               TextRange tr = dti.range;
               int baseX = r.getDataOffset(node) + getInsets().left + 2;
               mDropHintBounds = m_rendererPlugin.getXPathTextRenderer().getDropTargetHintBounds(text, tr, baseX, mDropHintBounds.y, mDropHintBounds.height, false);
            }
         }
      }

      return true;
   }

   public boolean drop(SoftDragNDropManager manager, Point at, Object value) {
      mDropHintBounds = null;
      if (!(value instanceof XPathSoftDragItem)) {
         return super.drop(manager, at, value);
      }
      if (!isTreeEditable()) {
         return false;
      }
      XPathSoftDragItem dragObject = (XPathSoftDragItem) value;
      TreePath dropLocation = computeDropLocation(at);
      if (dropLocation == null) {
         return false;
      }
      BindingNode node = (BindingNode) dropLocation.getLastPathComponent();
      if (node == null) {
         return false;
      }
      Binding[] path = node.getBindingArray();

      Utilities.DropTextInfo dti = null;
      if (at.x > m_cellRenderer.getDataOffset(node)) {
         // dropping INSIDE the text.
         String text = node.getBinding().getFormula();
         if (text != null) {
            int xin = at.x - m_cellRenderer.getDataOffset(node);
            dti = m_rendererPlugin.getXPathTextRenderer().getDropTargetRange(text,
                                                                             xin,
                                                                             -1, // n/a, we're 1 line.
                                                                             -1,
                                                                             -1,
                                                                             dragObject.isCharacterReference);
         }
      }
      return drop(node, dti, path, dragObject.path);
   }

   boolean drop(final BindingNode node, final Utilities.DropTextInfo dti, final Binding[] path, final String xpath) {
      setSelectionPath(node.getTreePath());
      // The xpath can be 'null' to mean clear?
      if (xpath == null) {
         // fail fast.
         throw new NullPointerException();
      }
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            dropInternal(node, dti, path, xpath);
         }
      });
      return true;
   }

   private void dropInternal(final BindingNode node, Utilities.DropTextInfo dti, Binding[] path, String xpath) {
      int row = getRowForPath(node.getTreePath());
//      ArrayList flashes = new ArrayList();
      if (dti != null) {
         String text = node.getBinding().getFormula();
         String got = Utilities.computeActualDropString(node.getTemplateReport().getContext(),
                                                        text.substring(0, dti.range.getStartPosition()) + dti.prefix,
                                                        xpath,
                                                        null,
                                                        dti.range.offset(dti.prefix.length()));
         got = dti.prefix + got + dti.suffix;

         String result = Utilities.insertAt(text, got, dti.range);
         node.getBinding().setFormula(result);
//         TextRange dropRange = 
        		 Utilities.getInsertedRange(text, got, dti.range);
//WCETODO            flashes.add(new FlashData(node.getTreePath(),dropRange));
      }
      else {
         waitForReport();
         TemplateReport report = ((BindingNode) getRootNode()).getTemplateReport();
         TemplateReport[] tr = BindUtilities.getReportPathFor(report, path);
         BindingDisplayUtils.drop(uiAgent, m_formulaCache, this, tr[tr.length - 1], xpath);
         //flashes.add(new FlashData(node.getTreePath(),dropRange));
      }
      //WCETODO super.setFlashData((FlashData[]) flashes.toArray(new FlashData[0]));
      super.requestFocus();

      super.contentChanged();
      // (No need to mark dirty; already done)

      // reselect.
      try {
         super.setSelectionRow(row);
      }
      catch (Exception e) {
         // should never happen, but if it does, don't want it to percolate.
         e.printStackTrace(System.out);
      }
      reenableButtons();

      repaint();
   }

   public void paint(Graphics g) {
      waitForReport(); // required here.
      super.paint(g);
      //mTextRenderer.initialize(g);

      // Overpaint silly lines here (this is an optimization so that the JTree can use regular (blit) scrolling)
      Point p = SwingUtilities.convertPoint(this, new Point(0, 0), mBindingEditor);
      g.translate(-p.x, -p.y);
      m_bindingLines.drawLines(g, 0, false, true);// true -> only paint right side (over the tree), for optimization.
      g.translate(p.x, p.y);

      // On top of that, paint any DnD hints.
      if (mDropHintBounds != null) {
         g.setColor(Color.green);
         g.drawRect(mDropHintBounds.x, mDropHintBounds.y, mDropHintBounds.width - 1, mDropHintBounds.height - 1);
      }
   }

   private TreePath computeDropLocation(Point at) {
      TreePath path = getClosestPathForLocation(at.x, at.y);
      if (path == null) {
         return null;
      }
      Rectangle rowBounds = getPathBounds(path);

      // If we're over 2 rows away... no
      if (rowBounds.y + 3 * getRowHeight() < at.y) {
         return null;
      }
      // ok.
      return path;
   }

   /**
    * For the paste part of cut-n-paste:
    *
    public BasicTreeNode buildFromXML(BasicTreeNode onNode, String xml) throws SAXException
    {
    if (xml==null || xml.length()==0)
    {
    return null;
    }
    Binding root;
    try
    {
    root = ReadFromXSLT.readFragment(xml);
    }
    catch (Throwable t)
    {
    throw new SAXException(""); // can't paste that!
    }
    if (root==null)
    {
    return null;
    }
    // Virtualize it:
    Binding nroot = m_bindingVirtualizer.virtualize(root,new DefaultCancelChecker());
    // Move all root namespaces north:
    NamespaceImporter ni = BindingNamespaceManipulationUtils.createNamespaceImporter(mConfiguration.getBinding());
    BindingNamespaceManipulationUtils.migrateNamespaceDeclarations(nroot,ni);

    // do some fixup later...
    return new BindingNode(nroot,(BasicTreeModel)getModel());
    }*/

   /*
   public void paste()
   {
       // Requires fixup
       super.paste();
       repair();
       rebuild();
   }*/

   /**
    * Gets the tree path for the binding, or null if not found.
    *
    * @param b The binding to find the path for.
    * @return The path or null if not found.
    */
   public TreePath getPathForBinding(Binding b) {
      BindingNode r = (BindingNode) getRootNode();
      BindingNode bn = r.findForBinding(b, true);
      if (bn == null) {
         return null;
      }
      return bn.getTreePath();
   }

   protected Object getDragObject(TreePath path) {
      return path.getLastPathComponent();
   }

   /*
   public void setPathBackgroundColor(TreePath path, Color color) {
       if (path==null) {
           return;
       }
       BasicTreeNode btn = (BasicTreeNode) path.getLastPathComponent();
       btn.setBackgroundColor(color);
   }*/

   void refreshModelessDialogsForSelection() {
      m_modelessDialogManager.refreshEditor((BindingNode) getSelectionNode());
   }

   void close() {
      m_modelessDialogManager.close();
   }
   public UIAgent getUIAgent() {
	   return uiAgent;
   }
   
   public void addTextAreaFocuslListener( FocusListener fListener){
	   m_rendererPlugin.addTextAreaFocuslListener(fListener);
   }
   
   public void removeTextAreaFocuslListener( FocusListener fListener){
	   m_rendererPlugin.removeTextAreaFocuslListener(fListener);
   }
   
   public void enableIgnoreInlineEditChange(boolean enable){
	   m_rendererPlugin.enableIgnoreInlineEditChange(enable);
   }
}

