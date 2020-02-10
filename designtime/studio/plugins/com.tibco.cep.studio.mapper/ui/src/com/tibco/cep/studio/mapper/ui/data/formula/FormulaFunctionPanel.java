package com.tibco.cep.studio.mapper.ui.data.formula;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.Scrollable;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import com.tibco.cep.studio.mapper.ui.PaintUtils;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.cep.studio.mapper.ui.data.XPathSoftDragItem;
import com.tibco.cep.studio.mapper.ui.data.utils.DisplayConstants;
import com.tibco.cep.studio.mapper.ui.data.utils.PreferenceUtils;
import com.tibco.cep.studio.mapper.ui.data.utils.SoftDragNDropManager;
import com.tibco.cep.studio.mapper.ui.data.utils.SoftDragNDropable;
import com.tibco.cep.studio.mapper.ui.data.xpath.XPathEditResources;

/**
 * Used inside {@link FormulaDesignerWindow} to show a list of functions.
 */
public class FormulaFunctionPanel extends JSplitPane implements SoftDragNDropable {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private JTree mTree;
   private JScrollPane mTreeScroll;
   private JLabel mDocs;
   private Font m_font;
   private FormulaFunctionCategory[] m_categories;

   private Rectangle mDragHintBounds;
   private Point mDragPoint;
   private FormulaFunctionEntry mLastEntry;
   private boolean mInitialized;

   private static String MAIN_TOOLTIP;

   private static class DocLabel extends JLabel implements Scrollable {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public boolean getScrollableTracksViewportHeight() {
         return false;
      }

      public boolean getScrollableTracksViewportWidth() {
         return true;
      }

      public int getScrollableBlockIncrement(Rectangle r, int x, int y) {
         return 20;
      }

      public int getScrollableUnitIncrement(Rectangle r, int x, int y) {
         return 10;
      }

      public Dimension getPreferredScrollableViewportSize() {
         return new Dimension(50, 100);//super.getPreferredSize();
      }
   }

   /**
    * @deprecated Use call w/ Designer App.
    */
   public FormulaFunctionPanel(FormulaFunctionCategory[] categories) {
      this(null, categories);
   }

   public FormulaFunctionPanel(UIAgent uiAgent, FormulaFunctionCategory[] categories) {
      super(JSplitPane.VERTICAL_SPLIT);
      super.setResizeWeight(.5);
      m_font = uiAgent == null ? null : uiAgent.getAppFont();
      m_categories = categories;

      initAllStrings();
      setBorder(BorderFactory.createEmptyBorder());

      setLeftComponent(buildTopTree());

      setRightComponent(buildBottomHelp());
      mTree.setSelectionRow(0);
      mTree.addTreeSelectionListener(new TreeSelectionListener() {
         public void valueChanged(TreeSelectionEvent lse) {
            selectionChanged();
         }
      });
      selectionChanged();
   }

   private JComponent buildBottomHelp() {
      mDocs = new DocLabel();
      mDocs.setVerticalAlignment(JLabel.TOP);
      if (m_font != null) {
         // Potential fix for 1-18QN70, where the Japanese version didn't show up correctly; try setting the
         // app font explicitly:
         mDocs.setFont(m_font);
      }

      JPanel docsPanel = new JPanel(new BorderLayout());
      docsPanel.add(mDocs, BorderLayout.CENTER);
      docsPanel.setBackground(Color.white);

      JPanel docsLabelp = new JPanel(new BorderLayout());
      mDocs.setBorder(DisplayConstants.getInternalBorder());
      JScrollPane jsp = new JScrollPane(mDocs);

      docsLabelp.add(jsp, BorderLayout.CENTER);
      docsLabelp.setBorder(DisplayConstants.getInternalBorder());

      return docsLabelp;
   }

   private JComponent buildTopTree() {
      mTree = new JTree();
      mTree.setBorder(DisplayConstants.getTreeContentsBorder());
      String text = MAIN_TOOLTIP;
      mTree.setToolTipText(text);
      mTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

      FormulaFunctionCategory[] cats = m_categories;

      // Build tree
      DefaultMutableTreeNode root = new DefaultMutableTreeNode();
      for (int i = 0; i < cats.length; i++) {
         FormulaFunctionCategory cat = cats[i];
         DefaultMutableTreeNode catNode = new DefaultMutableTreeNode(cat);
         FormulaFunctionEntry[] entries = cat.getEntries();
         for (int j = 0; j < entries.length; j++) {
            FormulaFunctionEntry entry = entries[j];
            DefaultMutableTreeNode entryNode = new DefaultMutableTreeNode(entry);
            catNode.add(entryNode);
         }
         root.add(catNode);
      }
      //addCustomFunctions(root);
      mTree.setModel(new DefaultTreeModel(root));
      mTree.setShowsRootHandles(true);
      mTree.setRootVisible(false);

      // Place tree inside scroll:
      mTreeScroll = new JScrollPane(mTree);

      // and label it:
      JPanel listLabelp = new JPanel(new BorderLayout());
      listLabelp.add(mTreeScroll, BorderLayout.CENTER);
      listLabelp.setBorder(DisplayConstants.getInternalBorder());

      return listLabelp;
   }

   public JTree getTree() {
      return mTree;
   }

   private void initAllStrings() {
      if (MAIN_TOOLTIP != null) {
         return;
      }
      MAIN_TOOLTIP = getBuilderString("tooltip");
      //FUNCTIONS_LABEL = getBuilderString("functions.label");
   }

   public void readPreferences(UIAgent uiAgent, String prefix) {
      int dl = PreferenceUtils.readInt(uiAgent, prefix + ".splitLocation", 150);
      setDividerLocation(dl);
   }

   public void writePreferences(UIAgent uiAgent, String prefix) {
      int dl = getDividerLocation();
      PreferenceUtils.writeInt(uiAgent, prefix + ".splitLocation", dl);
   }

   private static String getBuilderString(String key) {
      String fullKey = "ae.xpath.builder." + key;
      return DataIcons.getString(fullKey);
   }

   /**
    * Implementation override.
    */
   public void doLayout() {
      if (!mInitialized) {
         Graphics g = getGraphics();
         FontMetrics font = g.getFontMetrics();
         char[] chars = "thelongestfunctionname".toCharArray();
         int w = font.charsWidth(chars, 0, chars.length);
         mTreeScroll.setPreferredSize(new Dimension(w, 100));
         mInitialized = true;
      }
      super.doLayout();
   }

   /*
   public void setCurrent(int index) {
       mList.setSelectedIndex(index);
       if (mList.getSelectedIndex()!=index) {
           // just in case.
           mList.setSelectedIndex(0);
       }
   }

   public int getCurrent() {
       return 0;//mList.getSelectedIndex();
   }*/

   private void selectionChanged() {
      TreePath tp = mTree.getSelectionPath();
      if (tp == null) {
         return;
      }
      DefaultMutableTreeNode dmtn = (DefaultMutableTreeNode) tp.getLastPathComponent();
      Object obj = dmtn.getUserObject();
      FormulaFunctionEntry entry;
      if (obj instanceof FormulaFunctionCategory) {
         entry = ((FormulaFunctionCategory) obj).getCategoryEntry();
      }
      else {
         entry = (FormulaFunctionEntry) obj;
      }
      if (mLastEntry == entry) {
         return;
      }
      mLastEntry = entry;

      // Update docs:
      String helpText = entry.getHelpText();
      FormulaFunctionExample[] examples = entry.getExamples();
      if (helpText == null) {
         helpText = "";
      }
      for (int i = 0; i < examples.length; i++) {
         helpText += examples[i].formatHTML();
      }
      if (entry.mSeeResolvedReferences != null) {
         if (entry.mSeeResolvedReferences.length > 0) {
            helpText += "<p></p><i><b>";
            helpText += XPathEditResources.ALSO_SEE_HEADER;
            helpText += "</b></i>";
         }
         for (int i = 0; i < entry.mSeeResolvedReferences.length; i++) {
            // for now, just have the link, no hyperlinking yet.
            helpText += "<br></br>&nbsp;&nbsp;&nbsp;" + entry.mSeeResolvedReferences[i];
         }
      }
      mDocs.setText("<html><font face='helvetica'>" + helpText + "</font></html>"); // unhardcode font here..
   }

   public Object startDrag(SoftDragNDropManager manager, Point pressedAt, Point mouseAt) {
      Point offset = SwingUtilities.convertPoint(this, 0, 0, mTree);
      Point converted = new Point(mouseAt.x + offset.x, mouseAt.y + offset.y);
      if (!mTreeScroll.getViewport().getViewRect().contains(converted)) {
         // handle insets.
         return null;
      }
      TreePath path = mTree.getPathForLocation(converted.x, converted.y);
      if (path == null) {
         return null;
      }
      mTree.setSelectionPath(path);

      DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
      Object obj = node.getUserObject();
      FormulaFunctionEntry entry;
      if (obj instanceof FormulaFunctionEntry) {
         entry = (FormulaFunctionEntry) obj;
      }
      else {
         entry = ((FormulaFunctionCategory) obj).getCategoryEntry();
      }
      if (entry.getDragString() == null) {
         return null; // can't be dragged.
      }
      Rectangle p = mTree.getPathBounds(path);
      p.y -= offset.y;
      p.x -= offset.x;
      mDragHintBounds = p;
      mDragPoint = (Point) mouseAt.clone();
//        return new XPathSoftDragItem(entry.mDragString,this,!mIsFunctions);
      if (entry.getIsCharacter()) {
         return new XPathSoftDragItem(entry.getDragString(), this, entry.getIsCharacter());
      }
      return new XPathSoftDragItem(entry.getDragString(), this, entry.getNamespace());
   }

   public Rectangle getDragHintBounds() {
      return mDragHintBounds;
   }

   public void dragStopped() {
      mDragHintBounds = null;
   }

   public boolean dragOver(SoftDragNDropManager manager, Point mouseAt, Object dragObject) {
      return false;
   }

   public Rectangle getDropHintBounds() {
      return null;
   }

   public void dropStopped() {
   }

   public boolean drop(SoftDragNDropManager manager, Point mouseAt, Object dropObject) {
      return false;
   }

   /**
    * From SoftDragNDropable, implements a cool arrow indicator.
    * (MOVE THIS TO A COMMON PLACE!!!)
    */
   public void drawDraggingIndicator(Graphics g, SoftDragNDropManager mgr, Point mouseAt, Object dragObject) {
      Point dragLoc = mDragPoint;//PaintUtils.getClosestEdgePoint(mDragHintBounds,mouseAt,3);
      g.setColor(Color.green);
      if (!mDragHintBounds.contains(mouseAt)) {
         boolean isHorz = PaintUtils.isOnHorizontalEdge(mDragHintBounds, dragLoc);
         PaintUtils.drawAngledArrow(g, dragLoc.x, dragLoc.y, mouseAt.x, mouseAt.y, Color.green, Color.black, 3, 8, isHorz);
      }
   }

   /**
    * From SoftDragNDropable, implements a cool arrow indicator.
    * (MOVE THIS TO A COMMON PLACE!!!)
    */
   public Rectangle getDraggingIndicatorBounds(SoftDragNDropManager manager, Point mouseAt, Object dragObject) {
      // just return the bounds for the line between the source & where we are:
      Point dragLoc = mDragPoint;//PaintUtils.getClosestEdgePoint(mDragHintBounds,mouseAt,3);
      int minx = Math.min(dragLoc.x, mouseAt.x);
      int miny = Math.min(dragLoc.y, mouseAt.y);
      int maxx = Math.max(dragLoc.x, mouseAt.x);
      int maxy = Math.max(dragLoc.y, mouseAt.y);
      int slop = 10;
      return new Rectangle(minx - slop, miny - slop, 2 * slop + 1 + maxx - minx, 2 * slop + 1 + maxy - miny);
   }
}
