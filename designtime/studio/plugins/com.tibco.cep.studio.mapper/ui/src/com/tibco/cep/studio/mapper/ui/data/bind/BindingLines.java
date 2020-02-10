package com.tibco.cep.studio.mapper.ui.data.bind;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javax.swing.tree.TreePath;

import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.ForEachBinding;
import com.tibco.cep.mapper.xml.xdata.bind.ForEachGroupBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.virt.MarkerBinding;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.Utilities;
import com.tibco.cep.studio.mapper.ui.data.DataTree;
import com.tibco.cep.studio.mapper.ui.data.basic.BasicTreeActions;
import com.tibco.cep.studio.mapper.ui.data.basic.BasicTreeResources;
import com.tibco.cep.studio.mapper.ui.data.utils.SoftDragNDropManager;
import com.tibco.cep.studio.mapper.ui.data.utils.SoftDragNDropable;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;

/**
 * An editable display of Bindings input.
 */
final class BindingLines extends JPanel implements SoftDragNDropable {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private BindingTree mOutput;
   private JScrollPane mOutputScroll;
   private JScrollPane mInputScroll;
   private DataTree mInput;
   private JComponent mInputComponent;

   private JSplitPane mSplitPane;
   private JLabel m_linePaintingArea;
//    private JPanel mCenterLabel;
   private JPanel mCenterPanel;

   private int mTypeAreaWidth = 50;
   private int mDataAreaWidth = 30;

   // These are all line drawing specific vars:
   private int mLeftYOffset;
   private int mRightYOffset;

   private int mTopY;
   private int mBottomY;

   private int mLastCount; // counts version # of last report, if changed, must be dirty.

   private JComponent mRepaintComponent;

   private Color mInnerColor = new Color(128, 128, 255);
   private Color mOuterColor = new Color(200, 200, 255);
   private Color mSelInnerColor = mInnerColor.darker();
   private Color mSelOuterColor = mOuterColor.darker();

   private Color mSelForEachInnerColor = new Color(128, 255, 128);
   private Color mForEachInnerColor = new Color(200, 255, 200);
   private Color mSelForEachOuterColor = mSelForEachInnerColor.darker();
   private Color mForEachOuterColor = mForEachInnerColor.darker();


   private Stroke mDashedStroke;
   private Stroke mSelDashedStroke;
   private Stroke mNormalStroke;
   private Stroke mEraseStroke;
   private Stroke mEraseSelStroke;
   private boolean mInDataMode;

   final int mHeaderHeight;

   private Line[][] mBindingLines; // 1 per right side row. (null if dirty)
   private Line[][] mTypeLines; // (reversed from above)

   // Selection from middle area:
   private Line mMiddleSelLine;

   static class Line {
      public Line(int fromRow, int toRow, TreePath from, TreePath to, boolean dotted, boolean foreach) {
         this.to = to;
         this.toRow = toRow;
         this.from = from;
         this.fromRow = fromRow;
         this.dotted = dotted;
         this.foreach = foreach;
      }

      public Line reverse() {
         return new Line(toRow, fromRow, to, from, dotted, foreach);
      }

      public final TreePath from;
      public final TreePath to;
      public final int fromRow;
      public final int toRow;
      public final boolean dotted;
      public final boolean foreach;

      public int hashCode() {
         return fromRow + toRow + (dotted ? 1 : 0) + (foreach ? 1 : 0);
      }

      public boolean equals(Object object) {
         if (object instanceof Line) {
            Line l2 = (Line) object;
            return (l2.fromRow == fromRow && l2.toRow == toRow && l2.dotted == dotted && l2.foreach == foreach);
         }
         return false;
      }
   }

   public static final int MINIMUM_WIDTH = 10;

   public BindingLines(JComponent repaint, JComponent inputComponent, DataTree typeTree, JScrollPane typeScroll, JScrollPane bindingScroll, BindingTree binding, int headerHeight) {
      super(new BorderLayout());
      mHeaderHeight = headerHeight;
      mInputComponent = inputComponent;
      mRepaintComponent = repaint;
      mInput = typeTree;
      mInputScroll = typeScroll;
      mOutput = binding;
      mOutputScroll = bindingScroll;
      mLeftYOffset = (mInput.getRowHeight() / 2) + mHeaderHeight;
      mRightYOffset = (binding.getRowHeight() / 2) + mHeaderHeight;
      build();

      mNormalStroke = new BasicStroke(1);
      mEraseStroke = new BasicStroke(4);
      mEraseSelStroke = new BasicStroke(5); // bigger.
      final float dash1[] = {4.0f};
      mDashedStroke = new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 4.0f, dash1, 0.0f);
      mSelDashedStroke = new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 4.0f, dash1, 0.0f);

      MouseAdapter ma = new MouseAdapter() {
         // 1-20KD29 -- Windows systems trigger popups on mouse release
         public void mouseReleased(MouseEvent me) {
            maybeShowPopup(me);
         }

         // 1-20KD29 -- X-Windows systems trigger popups on mouse press
         public void mousePressed(MouseEvent me) {
            maybeShowPopup(me);
         }

         private void maybeShowPopup(MouseEvent me) {
            if (me.isPopupTrigger()) {
               Point at = SwingUtilities.convertPoint((Component) me.getSource(), me.getPoint(), BindingLines.this);
               showPopup(at.x, at.y);
            }
         }
      };
      getCenterPiece().addMouseListener(ma);

      Dimension d = new Dimension(MINIMUM_WIDTH, 10); // height is n/a here.
      setMinimumSize(d);
      setPreferredSize(d);
   }

   /**
    * Repaints the lines area ONLY; doesn't recompute the line rows.<br>
    * This is used from repaint when scrolling.
    */
   public void repaintLines() {
      Rectangle r = SwingUtilities.convertRectangle(m_linePaintingArea, m_linePaintingArea.getBounds(), mRepaintComponent);//LocalBounds()
      mRepaintComponent.repaint(r);
   }

   private void showPopup(int x, int y) {
      JPopupMenu menu = new JPopupMenu();
      JMenuItem showConnected = new JMenuItem(BindingEditorResources.SHOW_CONNECTED);
      showConnected.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent ae) {
            showConnected();
         }
      });
      showConnected.setEnabled(hasSelection());

      menu.add(showConnected);
      JMenuItem delete = new JMenuItem(BasicTreeResources.DELETE);
      delete.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent ae) {
            delete();
         }
      });
      menu.addSeparator();
      menu.add(delete);
      delete.setEnabled(hasSelection());
      menu.show(this, x, y);
   }

   private void showConnected() {
      TreePath[] left = getLeftPathsFor(getSelectedRightPath());
      showLeftPaths(left);
      TreePath[] right = getRightPathsFor(getSelectedLeftPath());
      showRightPaths(right);
      mInput.clearSelection();
      clearSelection();
   }

   public void showRightConnected() {
      TreePath[] left = getLeftPathsFor(mOutput.getSelectionPath());
      showLeftPaths(left);
   }

   public void showLeftConnected() {
      TreePath[] right = getRightPathsFor(mInput.getSelectionPath());
      showRightPaths(right);
   }

   /**
    * Deletes the 'lines' corresponding to what is selected in the left tree.
    */
   public void deleteLeftSelected(TreePath path) {
      deleteLeft(path);
   }

   public boolean canShowRightConnected() {
      TreePath tp = mOutput.getSelectionPath();
      if (tp == null) {
         return false;
      }
      TreePath[] left = getLeftPathsFor(tp);
      if (left == null || left.length == 0) {
         return false;
      }
      if (left[0] == null) {
         // This is just a constant; no connection.
         return false;
      }
      return true;
   }

   public boolean canShowLeftConnected() {
      TreePath tp = mInput.getSelectionPath();
      if (tp == null) {
         return false;
      }
      TreePath[] right = getRightPathsFor(tp);
      if (right == null || right.length == 0) {
         return false;
      }
      return true;
   }

   private void delete() {
      if (mMiddleSelLine == null) {
         return;
      }
      deleteRight(mMiddleSelLine.to);
   }

   private void deleteLeft(TreePath leftPath) {
      TreePath[] right = getRightPathsFor(leftPath);

      if (right == null) {
         return;
      }
      for (int i = 0; i < right.length; i++) {
         deleteRight(right[i]);
      }
   }

   private void deleteRight(TreePath path) {
      BindingNode n = (BindingNode) path.getLastPathComponent();
      n.clearContent();
      mOutput.contentChanged();
      mOutput.markReportDirty();
      markLinesDirty();
      clearSelection();
      mRepaintComponent.repaint();
   }

   private void showLeftPaths(TreePath[] paths) {
      if (paths != null && paths.length > 0) {
         TreePath firstPath = paths[0];
         if (firstPath == null) {
            // filled path for a constant.
            return;
         }
         if (firstPath.getParentPath() != null) {
            mInput.expandPath(firstPath.getParentPath());
         }
         mInput.scrollPathToVisible(firstPath);
         mInput.setSelectionPath(firstPath);
         markLinesDirty();
      }
   }

   private void showRightPaths(TreePath[] paths) {
      if (paths != null && paths.length > 0) {
         if (paths[0].getParentPath() != null) {
            mOutput.expandPath(paths[0].getParentPath());
         }
         mOutput.scrollPathToVisible(paths[0]);
         mOutput.setSelectionPath(paths[0]);
         markLinesDirty();
      }
   }

   void setDataMode(boolean dataMode) {
      if (dataMode == mInDataMode) {
         return;
      }
      markLinesDirty();
      final boolean wasInDataMode = mInDataMode;
      if (mInDataMode) {
         mDataAreaWidth = getDataAreaWidth();
      }
      else {
         mTypeAreaWidth = getTypeAreaWidth();
      }
      // Need an invoke later for it to stick:
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            if (wasInDataMode) {
               mSplitPane.setDividerLocation(mTypeAreaWidth);
            }
            else {
               mSplitPane.setDividerLocation(mDataAreaWidth);
            }
            revalidate();
         }
      });
      mInDataMode = dataMode;
   }

   boolean getDataMode() {
      return mInDataMode;
   }

   public void setTypeAreaWidth(int width) {
      if (mInDataMode) {
         mTypeAreaWidth = width;
      }
      else {
         mSplitPane.setDividerLocation(width);
      }
   }

   public int getTypeAreaWidth() {
      if (mInDataMode) {
         return mTypeAreaWidth;
      }
      else {
         return mSplitPane.getDividerLocation();
      }
   }

   public void setDataAreaWidth(int width) {
      if (!mInDataMode) {
         mDataAreaWidth = width;
      }
      else {
         mSplitPane.setDividerLocation(width);
      }
   }

   public int getDataAreaWidth() {
      if (!mInDataMode) {
         return mDataAreaWidth;
      }
      else {
         return mSplitPane.getDividerLocation();
      }
   }

   private void build() {
      removeAll();
      if (mSplitPane == null) {
         mSplitPane = new JSplitPane();
         mSplitPane.setResizeWeight(1.0);
         mSplitPane.setDividerSize(2);
         ((BasicSplitPaneUI) mSplitPane.getUI()).getDivider().setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
         if (mInDataMode) {
            mSplitPane.setDividerLocation(mDataAreaWidth);
         }
         else {
            mSplitPane.setDividerLocation(mTypeAreaWidth);
         }
         mSplitPane.setContinuousLayout(true);
         mSplitPane.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent pce) {
               if (pce.getPropertyName().equals(JSplitPane.DIVIDER_LOCATION_PROPERTY)) {
                  mRepaintComponent.repaint();
               }
            }
         });

         m_linePaintingArea = new JLabel();
//                mCenterLabel = new JPanel(new BorderLayout());
         m_linePaintingArea.setMinimumSize(new Dimension(10, 10)); // minimum 10 pixel width for lines.
         m_linePaintingArea.setOpaque(true);
         m_linePaintingArea.setBackground(SystemColor.window);
         m_linePaintingArea.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
               pressed(me);
            }
         });
         mCenterPanel = new JPanel(new BorderLayout());
         mCenterPanel.add(m_linePaintingArea, BorderLayout.CENTER);
         mSplitPane.setBorder(BorderFactory.createEmptyBorder());
      }
      mSplitPane.setLeftComponent(mInputComponent);
      mSplitPane.setRightComponent(mCenterPanel);
      add(mSplitPane);
   }

   public JComponent getCenterPiece() {
      return m_linePaintingArea;
   }

   public boolean hasSelection() {
      return mMiddleSelLine != null;
   }

   public TreePath getSelectedRightPath() {
      if (hasSelection()) {
         return mMiddleSelLine.to;
      }
      return null;
   }

   public TreePath getSelectedLeftPath() {
      if (hasSelection()) {
         return mMiddleSelLine.from;
      }
      return null;
   }

   private void pressed(MouseEvent me) {
      Line[][] ls = getBindingLines();

      int leftScrollOffset = mInputScroll.getVerticalScrollBar().getValue();
      int rightScrollOffset = mOutputScroll.getVerticalScrollBar().getValue();

      int xpos = me.getX();
      int ypos = me.getY() + mHeaderHeight;
      float fractionRight = xpos / (float) (Math.max(1, m_linePaintingArea.getWidth()));
      float fractionLeft = 1 - fractionRight;

      int minDistanceSoFar = 18; // min y distance to select.

      Line bestLine = null;

      // good ol' linear searching:
      for (int i = 0; i < ls.length; i++) {
         Line[] l = ls[i];
         if (l == null) {
            continue;
         }
         for (int j = 0; j < l.length; j++) {
            Line line = l[j];
            Rectangle pb = mOutput.getPathBounds(getDeepestExpandedOutputPath(line.to));
            if (pb == null) {
               // just in case.
               continue;
            }
            int ry = pb.y + mRightYOffset - rightScrollOffset;
            if (line.from == null) {
               continue;
            }
            TreePath deep = getDeepestVisibleInputPath(line.from);
            Rectangle ib = mInput.getPathBounds(deep);
            if (ib == null) {
               continue;
            }
            int ly = ib.y + mLeftYOffset - leftScrollOffset;

            int atY = (int) (fractionLeft * ly + fractionRight * ry);
            int distance = (Math.abs(atY - ypos));
            if (distance < minDistanceSoFar) {
               minDistanceSoFar = distance;
               bestLine = line;
            }
         }
      }
      mMiddleSelLine = bestLine;
      m_linePaintingArea.requestFocus();
      mInput.clearSelection();
      mOutput.clearSelection();
      mRepaintComponent.repaint();
   }

   public void clearSelection() {
      mMiddleSelLine = null;
   }

   public void markLinesDirty() {
      mBindingLines = null;
      mTypeLines = null;
   }

   public TreePath[] getAllInputPathsBelow(TreePath rightPath) {
      // more fun with linear searches!
      ArrayList<TreePath> temp = new ArrayList<TreePath>();
      Line[][] ls = getTypeLines();
      for (int i = 0; i < ls.length; i++) {
         Line[] line = ls[i];
         if (line != null) {
            TreePath path = line[0].from;
            if (rightPath.isDescendant(path)) {
               temp.add(path);
            }
         }
      }
      return temp.toArray(new TreePath[0]);
   }

   public TreePath[] getLeftPathsFor(TreePath rightPath) {
      int row = mOutput.getRowForPath(rightPath);
      if (row < 0) {
         return null;
      }
      Line[][] lines = getBindingLines();
      Line[] l = lines[row];
      if (l == null) {
         return null;
      }
      TreePath[] p = new TreePath[l.length];
      for (int i = 0; i < p.length; i++) {
         p[i] = l[i].from;
      }
      return p;
   }

   public TreePath[] getRightPathsFor(TreePath leftPath) {
      int row = mInput.getRowForPath(leftPath);
      if (row < 0) {
         return null;
      }
      Line[][] lines = getTypeLines();
      Line[] l = lines[row];
      if (l == null) {
         return null;
      }
      TreePath[] p = new TreePath[l.length];
      for (int i = 0; i < p.length; i++) {
         p[i] = l[i].to;
      }
      return p;
   }

   @SuppressWarnings("unchecked")
public Line[][] getTypeLines() {
      if (mTypeLines != null) {
         return mTypeLines;
      }
      Line[][] bindingLines = getBindingLines();
      int rowCount = mInput.getRowCount();
      ArrayList<Line>[] tl = new ArrayList[rowCount];
      for (int i = 0; i < bindingLines.length; i++) {
         Line[] l = bindingLines[i];
         if (l != null) {
            //TreePath outputPath = mOutput.getPathForRow(i);

            for (int j = 0; j < l.length; j++) {
               Line line = l[j];
               if (line.from == null) {
                  // output only line.
                  continue;
               }
               int row = mInput.getRowForPath(getDeepestVisibleInputPath(line.from));
               if (row < 0) {
                  continue; // bug...
               }
               if (tl[row] == null) {
                  tl[row] = new ArrayList<Line>();
               }
               tl[row].add(line);
            }
         }
      }
      Line[][] tr = new Line[rowCount][];
      for (int i = 0; i < rowCount; i++) {
         if (tl[i] != null) {
            tr[i] = (Line[]) tl[i].toArray(new Line[tl[i].size()]);
         }
      }
      mTypeLines = tr;
      return tr;
   }

   public Line[][] getBindingLines() {
      int ct = mOutput.waitForReport();
      if (ct != mLastCount) {
         markLinesDirty();
         mLastCount = ct;
         mBindingLines = null;
      }
      if (mBindingLines != null) {
         return mBindingLines;
      }
      mOutput.waitForReport();
      //long t1 = System.currentTimeMillis();
      int outputRows = mOutput.getRowCount();
      Line[][] ls = new Line[outputRows][];
      ArraySet temp = new ArraySet();
      for (int i = 0; i < outputRows; i++) {
         temp.clear();
         computeLinesForRow(i, temp);
         if (temp.size() > 0) {
            ls[i] = (Line[]) temp.toArray(new Line[temp.size()]);
         }
      }
      //System.out.println("Got lines in:" + (System.currentTimeMillis()-t1));
      mBindingLines = ls;
      return mBindingLines;
   }

   public void drawLines(Graphics g, int yoffset, boolean leftSideOnly, boolean rightSideOnly) {

      // Without antialiasing, the lines look pretty cruddy:
      Graphics2D g2 = (Graphics2D) g;
      Object oldAA = g2.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      // Clip the top part:
      Rectangle r = g.getClipBounds();

      // This offset is a hack in order that line painting, when only over the right-side tree, gets the bottom
      // few lines (some offset problem has them being clipped; since we're already clipped to the tree in that
      // situation, just do this hack)
      int hackClipOffset = rightSideOnly || leftSideOnly ? 100 : 0;

      g.setClip(r.x, yoffset, r.width, m_linePaintingArea.getHeight() + hackClipOffset);
      mTopY = yoffset;
      mBottomY = mTopY + m_linePaintingArea.getHeight() + hackClipOffset;

      Stroke old = g2.getStroke();

      drawLines1(g, leftSideOnly, rightSideOnly);
      g2.setStroke(old);
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, oldAA);
      // restore old clip:
      g2.setClip(r);
   }

   private void drawLines1(Graphics g, boolean leftSideOnly, boolean rightSideOnly) {

      Line[][] lines = null;
      try {
         lines = getBindingLines();
      }
      catch (Exception e) {
         // hacky, for now, just don't paint the lines.
         e.printStackTrace(System.err);
         return;
      }

      int rc = lines.length;
      int leftScrollOffset = mInputScroll.getVerticalScrollBar().getValue();
      int rightScrollOffset = mOutputScroll.getVerticalScrollBar().getValue();
      int xscrollsize = 0;
      if (mInputScroll.getVerticalScrollBar().isVisible()) {
         xscrollsize = mInputScroll.getVerticalScrollBar().getWidth();
      }
      boolean[] selByRow = new boolean[lines.length];
      int[] selOutputRows = mOutput.getSelectionRows();
      if (selOutputRows != null) {
         for (int i = 0; i < selOutputRows.length; i++) {
            selByRow[selOutputRows[i]] = true;
         }
      }
      int[] selInputRows = mInput.getSelectionRows();
      boolean[] selByInputRow = new boolean[mInput.getRowCount()];
      if (selInputRows != null) {
         for (int i = 0; i < selInputRows.length; i++) {
            selByInputRow[selInputRows[i]] = true;
         }
      }

      Rectangle outputVisibleRect = mOutputScroll.getViewport().getViewRect();
      boolean[] outputVisibleRows = new boolean[rc];
      for (int i = 0; i < outputVisibleRows.length; i++) {
         Rectangle rb = mOutput.getRowBounds(i);
         outputVisibleRows[i] = rb == null ? false : outputVisibleRect.intersects(rb);  // sanity on rb being null.
      }
      Rectangle inputVisibleRect = mInputScroll.getViewport().getViewRect();
      boolean[] inputVisibleRows = new boolean[mInput.getRowCount()];
      for (int i = 0; i < inputVisibleRows.length; i++) {
         Rectangle r = mInput.getRowBounds(i);
         inputVisibleRows[i] = r == null ? false : inputVisibleRect.intersects(r); // sanity on r being null.
      }
      for (int i = 0; i < rc; i++) {
         boolean sel = selByRow[i];
         drawLinesForRow(lines[i], selByInputRow, g, sel, xscrollsize, leftScrollOffset, rightScrollOffset, false, leftSideOnly, rightSideOnly, inputVisibleRows, outputVisibleRows);
      }
      // Draw solid after (for overpaint)
      for (int i = 0; i < rc; i++) {
         boolean sel = selByRow[i];
         drawLinesForRow(lines[i], selByInputRow, g, sel, xscrollsize, leftScrollOffset, rightScrollOffset, true, leftSideOnly, rightSideOnly, inputVisibleRows, outputVisibleRows);
      }
   }

   /**
    * A set that preserves order but is reasonably fast.
    */
   static class ArraySet {
      private ArrayList<Object> m_list = new ArrayList<Object>();
      private HashSet<Object> m_set = new HashSet<Object>();

      public void add(Object object) {
         if (m_set.contains(object)) {
            return;
         }
         m_set.add(object);
         m_list.add(object);
      }

      public Object[] toArray(Object[] l) {
         return m_list.toArray(l);
      }

      public int size() {
         return m_list.size();
      }

      public void clear() {
         m_list.clear();
         m_set.clear();
      }
   }

   private void computeLinesForRow(int row, ArraySet temp) {
      TreePath outputPath = mOutput.getPathForRow(row);
      if (!mOutput.isExpanded(outputPath)) {
         computeLinesForChildren(outputPath, (BindingNode) outputPath.getLastPathComponent(), false, temp);
      }
      else {
         computeLinesForRowItself(outputPath, (BindingNode) outputPath.getLastPathComponent(), false, temp);
      }
   }

   private void computeLinesForChildren(TreePath outputPath, BindingNode node, boolean dotted, ArraySet temp) {
      if (node.getTemplateReport() == null) {
         // fix..
         return;
      }
      computeLinesForRowItself(outputPath, node, dotted, temp);
      if (!(node.getBinding() instanceof MarkerBinding)) {
         int cc = node.getChildCount();
         for (int i = 0; i < cc; i++) {
            BindingNode child = (BindingNode) node.getChild(i);
            TreePath cpath = outputPath.pathByAddingChild(child);
            computeLinesForChildren(cpath, child, true, temp); // true -> we're inside an unexpanded node, dot it.
         }
      }
   }

   private void computeLinesForRowItself(TreePath outputPath, BindingNode outputNode, boolean dotted, ArraySet temp) {
      TemplateReport report = outputNode.getTemplateReport();
      if (report == null) {
         // shouldn't happen, but don't crash if it does...
         return;
      }
      Expr expr = report.getXPathExpression();
      if (expr == null) {
         return;
      }
      Binding b = report.getBinding();
      boolean isForEach = b instanceof ForEachBinding || b instanceof ForEachGroupBinding;
      NamespaceContextRegistry nr = report.getContext().getNamespaceMapper();
      String[] relPath = report.getContext().getInput().getNodePath(nr);
      if (relPath == null) {
         relPath = new String[0];
      }
      String[][] paths = Utilities.getPaths(relPath, expr);
      if (paths.length == 0) {
         // always at least draw 1 line minimal line.
         int toRow = mOutput.getRowForPath(getDeepestExpandedOutputPath(outputPath));
         // these conventions are for 'formula' but no lines.
         temp.add(new Line(-1, toRow, null, outputPath, dotted, isForEach));
         return;
      }
      for (int i = 0; i < paths.length; i++) {
         String[] path = paths[i];
         TreePath pathx;
         pathx = BasicTreeActions.getTreePathFromXPath(mInput,
                                                       path,
                                                       nr);
         if (pathx != null) {
            TreePath deepestInput = getDeepestVisibleInputPath(pathx);
            int fromRow = mInput.getRowForPath(deepestInput);
            int toRow = mOutput.getRowForPath(getDeepestExpandedOutputPath(outputPath));
            if (fromRow >= 0 && toRow >= 0) {
               temp.add(new Line(fromRow, toRow, pathx, outputPath, dotted, isForEach));
            }
         }
      }
   }

   private void drawLinesForRow(Line[] rowLines, boolean[] selInputRows, Graphics g, boolean sel, int xscrollsize, int leftScrollOffset, int rightScrollOffset, boolean solid, boolean leftSideOnly, boolean rightSideOnly, boolean[] inputVisibleRows, boolean[] outputVisibleRows) {
      if (rowLines == null) {
         return;
      }
      if (rowLines.length == 0) {
         return;
      }
      for (int i = 0; i < rowLines.length; i++) {
         Line line = rowLines[i];

         // prune based on visibility (if either side visible, draw it)
         if (outputVisibleRows[line.toRow] || line.fromRow < 0 || inputVisibleRows[line.fromRow]) {
            drawLine(line, selInputRows, g, sel, xscrollsize, leftScrollOffset, rightScrollOffset, solid, leftSideOnly, rightSideOnly);
         }
      }
   }

   private void drawLine(Line line, boolean[] selInputRows, Graphics g, boolean sel, int xscrollsize, int leftScrollOffset, int rightScrollOffset, boolean solid, boolean leftSideOnly, boolean rightSideOnly) {
      Rectangle outBounds = mOutput.getPathBounds(getDeepestExpandedOutputPath(line.to));
      if (outBounds == null) {
         // shouldn't happen often, can happen when selected is collapsed, etc.
         return;
      }
      int outx = outBounds.x - 10; // 10 = inset.;// - mXRightInset;
      int outy = outBounds.y + mRightYOffset - rightScrollOffset;
      boolean msel = sel;
      if (line.fromRow > 0) { // <0 if it is a to-only line.
         if (selInputRows[line.fromRow]) {
            msel = true;
         }
      }
      if (mMiddleSelLine != null) {
         if (mMiddleSelLine.fromRow == line.fromRow && mMiddleSelLine.toRow == line.toRow) {
            msel = true;
         }
      }
      drawLinesFor(line.from, g, xscrollsize, outx, outy, msel, leftScrollOffset, line.dotted, line.foreach, solid, leftSideOnly, rightSideOnly);
   }

   private TreePath getDeepestExpandedOutputPath(TreePath path) {
      TreePath pp = path.getParentPath();
      if (pp == null) {
         return path;
      }
      TreePath dpe = getDeepestExpandedOutputPath(pp);
      if (dpe == pp) {
         if (mOutput.isExpanded(pp)) {
            return path;
         }
         return pp;
      }
      else {
         return dpe;
      }
   }

   /**
    * For a tree path, returns the longest path that is visible (by virtue of expansion, doesn't take into account scrollbars)
    *
    * @param path
    * @return The deepest visible path.
    */
   private TreePath getDeepestVisibleInputPath(TreePath path) {
      TreePath pp = path.getParentPath();
      // The root is always visible...
      if (pp == null) {
         return path;
      }
      if (mInput.isExpanded(pp)) {
         // If our parent is expanded, then we are visible:
         return path;
      }
      else {
         // otherwise, step up 1 level & try again.
         return getDeepestVisibleInputPath(pp);
      }
   }

   private void drawLinesFor(TreePath fromPath, Graphics g, int xscrollSize, int rightX, int rightY, boolean sel, int leftScrollOffset, boolean isDotted, boolean isForEach, boolean solid, boolean leftSideOnly, boolean rightSideOnly) {
      TreePath drawPath;
      if (fromPath == null) {
         drawPath = null;
      }
      else {
         drawPath = getDeepestVisibleInputPath(fromPath);
      }
      isDotted = isDotted || fromPath != drawPath;
      if (isDotted == solid) {
         return;
      }
      if (drawPath == null) {
         // happens when this is just a 1 piece line (i.e. it is a constant.)
         //int xil = mInputScroll.getWidth()+4;
         int left = mSplitPane.getWidth();
         int constantOffset = 8;
         drawFlatConnectyLine(g, left + constantOffset, left + rightX, rightY, sel, isForEach);
         return;
      }
      Rectangle b = mInput.getPathBounds(drawPath);
      if (b == null) {
         // Once again, paranoia, shouldn't happen because we checked that it was expanded.
         return;
      }
      int actualWidth = mInput.getActualWidth(g, drawPath);
      int leftY = b.y + mLeftYOffset - leftScrollOffset;

      int xil = mInputScroll.getWidth() + 4;
      int xir = mSplitPane.getWidth();
      int left = xscrollSize + b.x + actualWidth + 10;
      drawConnectyLine(g, left, leftY, xil, xir, rightX, rightY, sel, isDotted, isForEach, leftSideOnly, rightSideOnly);
   }

   private void drawConnectyLine(Graphics g, int x1, int y1, int xil, int xir, int x2, int y2, boolean sel, boolean isDotted, boolean isForEach, boolean leftSideOnly, boolean rightSideOnly) {

      // These outer white lines give it a pseudo-bridge look:
      Graphics2D g2d = (Graphics2D) g;
      if (sel) {
         g2d.setStroke(mEraseSelStroke);
      }
      else {
         g2d.setStroke(mEraseStroke);
      }
      g.setColor(Color.white);
      drawConnectyLinePiece(g, x1, y1, xil, xir, x2, y2, sel, leftSideOnly, rightSideOnly);

      if (isForEach) {
         g.setColor(sel ? mSelForEachOuterColor : mForEachOuterColor);
      }
      else {
         g.setColor(sel ? mSelOuterColor : mOuterColor);
      }
      if (isDotted) {
         if (sel) {
            g2d.setStroke(mSelDashedStroke);
         }
         else {
            g2d.setStroke(mDashedStroke);
         }
         drawConnectyLinePiece(g, x1, y1, xil, xir, x2, y2, sel, leftSideOnly, rightSideOnly);
      }
      else {
         g2d.setStroke(mNormalStroke);

         drawConnectyLinePiece(g, x1, y1 - 1, xil, xir, x2, y2 - 1, sel, leftSideOnly, rightSideOnly);
         if (sel) {
            drawConnectyLinePiece(g, x1, y1 - 2, xil, xir, x2, y2 - 2, sel, leftSideOnly, rightSideOnly);
         }

         // looks WAY better if the inside is painted afterwards (antialiasing stuff I think)

         if (isForEach) {
            g.setColor(sel ? mSelForEachInnerColor : mForEachInnerColor);
         }
         else {
            g.setColor(sel ? mSelInnerColor : mInnerColor);
         }

         drawConnectyLinePiece(g, x1, y1, xil, xir, x2, y2, sel, leftSideOnly, rightSideOnly);
      }
   }

   private void drawFlatConnectyLine(Graphics g, int x1, int x2, int y, boolean sel, boolean isForEach) {

      // These outer white lines give it a pseudo-bridge look:
      Graphics2D g2d = (Graphics2D) g;
      if (sel) {
         g2d.setStroke(mEraseSelStroke);
      }
      else {
         g2d.setStroke(mEraseStroke);
      }
      g.setColor(Color.white);
      g2d.drawLine(x1, y, x2, y);

      if (isForEach) {
         g.setColor(sel ? mSelForEachOuterColor : mForEachOuterColor);
      }
      else {
         g.setColor(sel ? mSelOuterColor : mOuterColor);
      }
      g2d.setStroke(mNormalStroke);

      g2d.drawLine(x1, y - 1, x2, y - 1);
      if (sel) {
         g2d.drawLine(x1, y - 2, x2, y - 2);
      }

      // looks WAY better if the inside is painted afterwards (antialiasing stuff I think)

      if (isForEach) {
         g.setColor(sel ? mSelForEachInnerColor : mForEachInnerColor);
      }
      else {
         g.setColor(sel ? mSelInnerColor : mInnerColor);
      }

      g2d.drawLine(x1, y, x2, y);
      g2d.fillOval(x1 - 2, y - 2, 4, 4);
   }

   private void drawConnectyLinePiece(Graphics g, int x1, int y1, int xil, int xir, int x2, int y2, boolean sel, boolean leftSideOnly, boolean rightSideOnly) {
      int width = xir - xil;
      Graphics2D g2d = (Graphics2D) g;
      boolean y1inRange = inRange(y1);
      boolean y2inRange = inRange(y2);
      boolean slopeTooGreat = width < 5 || ((!y1inRange || !y2inRange) && Math.abs(((y2 - y1) / width)) >= (sel ? 8 : 4));

      if (!rightSideOnly) {
         if (!leftSideOnly) {
            if (!slopeTooGreat) {
               g.drawLine(xil, y1, xir, y2);
            }
         }
         if (y1inRange) {
            int l = Math.min(xil - 4, x1);
            g.drawLine(l, y1, xil, y1);
            if (slopeTooGreat) {
               int dirOffset = (y1 < y2) ? 8 : -8;
               g2d.drawLine(xil, y1, xil + 4, y1 + dirOffset);
            }
         }
      }
      if (!leftSideOnly) {
         if (y2inRange) {
            if (x2 > 0) {
               g.drawLine(xir, y2, xir + x2, y2);
            }
            if (slopeTooGreat) {
               int dirOffset = (y1 > y2) ? 8 : -8;
               g2d.drawLine(xir, y2, xir - 4, y2 + dirOffset);
            }
         }
      }
   }

   private boolean inRange(int y) {
      if (y < mTopY + 4) {
         return false;
      }
      if (y > mBottomY - 4) {
         return false;
      }
      return true;
   }

   public boolean dragOver(SoftDragNDropManager manager, Point mouseAt, Object dragObject) {
      return false;
   }

   public void dragStopped() {
   }

   public void drawDraggingIndicator(Graphics g, SoftDragNDropManager manager, Point mouseAt, Object dragObject) {
   }

   public boolean drop(SoftDragNDropManager manager, Point mouseAt, Object dropObject) {
      if (dropObject instanceof BindingNode) {
         return mOutput.dropOnLines((BindingNode) dropObject);
      }
      return false;
   }

   public void dropStopped() {
   }

   public Rectangle getDraggingIndicatorBounds(SoftDragNDropManager manager, Point mouseAt, Object dragObject) {
      return null;
   }

   public Rectangle getDragHintBounds() {
      return null;
   }

   public Rectangle getDropHintBounds() {
      return null;
   }

   public Object startDrag(SoftDragNDropManager manager, Point pressedAt, Point mouseAt) {
      return null;
   }
}

