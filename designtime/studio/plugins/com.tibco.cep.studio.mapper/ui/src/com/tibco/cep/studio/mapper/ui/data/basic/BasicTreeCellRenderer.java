package com.tibco.cep.studio.mapper.ui.data.basic;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessageList;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.cep.studio.mapper.ui.data.ElementTreeNode;
import com.tibco.cep.studio.mapper.ui.data.bind.StringPaintUtils;
import com.tibco.cep.studio.mapper.ui.data.xpath.XPathLineRenderer;
import com.tibco.xml.schema.SmElement;

public final class BasicTreeCellRenderer extends JPanel implements TreeCellRenderer {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
public static final int DATA_TEXT_X_OFFSET = 2; // # of x pixels in to space from start of data area.
   public static final int FLASH_TIME = 500; // ms.  Time the area flashes when text is dropped on it.
   public static final Color FLASH_COLOR = new Color(235, 235, 255);

   private TreeCellStyle style;
   //TODO move more of these style properties into the
   //style class
   private Font mItalicFont;
   private Font mBoldFont;
   private BasicTree mTree;
   private int mAvailableWidth;
   private int mHeight = -1; // constant after first run.
   private XPathLineRenderer mLineRenderer;
   private Font mCardFont;
   boolean mExpanded;
   boolean mSelected;
   boolean mFocus;
   Icon mRootDisplayIcon;
   String mRootDisplayName;
   boolean mRootDisplayNameBold;
   Color mRowSeparatorColor = new Color(240, 240, 255);

   private HashMap<TreePath, TextRange> mFlashMap = new HashMap<TreePath, TextRange>();

   private final Color mBackgroundSelectionColor;
   //private final Color mBackgroundNonSelectionColor;
   private final Color mBorderSelectionColor;
   private final Color mTextSelectionColor;
   private final Color mTextNonSelectionDisabledColor = Color.gray;
   private final Color mDataSolidBackgroundColor = new Color(255, 255, 240);
   private final Color mDataMissingBackgroundColor = new Color(240, 240, 240);
   private final Color mDataLeadingLineColor = new Color(220, 220, 220);

   boolean mHasDivider = true;
   boolean mPaintCardinality = false;
   boolean mLightenMissing = false;

//    public int mMinimumNameWidth = 40;
//    public int mMinimumDataWidth = 40;
   boolean mDrawIcon = true;
   int mTreeExpansionIndent = 10; // # of pixels that 1 level of indent causes.

   /**
    * The indent width of a row.
    */
   int mIndentWidth;

   int mStartingDataOffset;
   boolean mIndentDataOffset = true;
   int mNameOffset;
   private int mCardinalityOffset;

   BasicTreeNode mNode;

   // Unix fix - make the font available to BasicTree for
   // cell height calculation.

   public BasicTreeCellRenderer(BasicTree tree, UIAgent uiAgent) {
      // steal initial colors from default:
      mLineRenderer = new XPathLineRenderer(uiAgent);
      mTree = tree;
      DefaultTreeCellRenderer r = new DefaultTreeCellRenderer();
      mBackgroundSelectionColor = r.getBackgroundSelectionColor();
      //mBackgroundNonSelectionColor = r.getBackgroundNonSelectionColor();
      mBorderSelectionColor = r.getBorderSelectionColor();
      mLineRenderer.setErrorUnderlineColor(Color.red);
      mTextSelectionColor = r.getTextSelectionColor();

      style = new DefaultTreeCellStyle(uiAgent.getAppFont());

      mCardFont = uiAgent.getAppFont();
      mBoldFont = uiAgent.getAppFont().deriveFont(Font.BOLD);
      mItalicFont = uiAgent.getAppFont().deriveFont(Font.ITALIC);

      setOpaque(false);
   }

   public void setTreeCellStyle(TreeCellStyle style) {
      this.style = style;
   }

   void setAvailableWidth(int w) {
      if (w != mAvailableWidth) {
         mAvailableWidth = w;
         rescaled();
      }
   }

   void setFlashData(BasicTree.FlashData[] data) {
      mFlashMap.clear();
      for (int i = 0; i < data.length; i++) {
         BasicTree.FlashData fd = data[i];
         mFlashMap.put(fd.path, fd.range);
      }
      javax.swing.Timer deselectRange = new javax.swing.Timer(FLASH_TIME, new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent ae) {
            mFlashMap.clear();
            mTree.repaint();
         }
      });
      deselectRange.setRepeats(false);
      deselectRange.start();
   }

   public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                 boolean sel,
                                                 boolean expanded,
                                                 boolean leaf, int row,
                                                 boolean hasFocus) {
      mSelected = sel;
      mExpanded = expanded;
      mFocus = hasFocus;
      mNode = (BasicTreeNode) value;
      style.initCell(tree, value, sel, expanded, leaf, row, hasFocus);
      return this;
   }

   public void setDrawIcon(boolean drawIcon) {
      mDrawIcon = drawIcon;
   }

   public boolean getDrawIcon() {
      return mDrawIcon;
   }

   // DSS: Set the tree node value for this renderer. Since we are sharing a single object
   // for the entire tree, it is not available after the paint() method. We depend on this
   // value for painting errors.
   public void setTreeNode(BasicTreeNode node) {
      mNode = node;
   }

   private void paintRepeat(Graphics g, int min, int max, boolean nil) {
      g.setColor(Color.blue);
      g.setFont(mCardFont);
      int xat = mCardinalityOffset;
      if (max > 1) {
         if (min == 0) {
            g.drawString("*", xat, 10);
         }
         else {
            g.drawString("+", xat, 10);
         }
      }
      else {
         if (min == 0) {
            g.drawString("?", xat, 10);
         }
      }
      if (nil) {
         DataIcons.getNilIcon().paintIcon(this, g, 6, 0);
      }
   }

   boolean nameFits(BasicTreeNode node) {
      Graphics g = mTree.getGraphics();
      String dname = node.getDisplayName();
      if (dname == null) {
         dname = "";
      }
      g.setFont(style.getNormalFont());
      int textWidth = g.getFontMetrics().charsWidth(dname.toCharArray(), 0, dname.length()) + mNameOffset + 4;
      g.dispose();
      if (mHasDivider) {
         return textWidth < getRelativeDataOffset(node);
      }
      else {
         return textWidth < getPreferredWidth(node);
      }
   }

   boolean dataFits(BasicTreeNode node) {
      if (!node.isEditable()) {
         return true;
      }
      Graphics g = mTree.getGraphics();
      String data = node.getDataValue();
      if (data == null) {
         data = node.getBlankText();
      }
      if (data == null) {
         return true;
      }
      String[] lines = getAsLines(data);
      if (lines.length == 0) {
         return true;
      }
      if (lines.length > 1) {
         return false; // more than 1 line, won't fit.
      }
      int textWidth = mNode.getDataRenderer().getTextOffset(g, lines[0]);
      g.dispose();
      return textWidth < mAvailableWidth - (getDataOffset(node) + DATA_TEXT_X_OFFSET);
   }

   private int computeTextWidth(Graphics g, String dname, int dataOffset) {
      g.setFont(style.getNormalFont());
      int t = g.getFontMetrics().charsWidth(dname.toCharArray(), 0, dname.length()) + mNameOffset + 4;
      return Math.min(t, dataOffset);
   }

   void paintLabel(Graphics g, int width, int height, int dataOffset) {
      BasicTreeNode node = mNode;

      String dname = mNode.getDisplayName();
      if (dname == null) {
         // for safety.
         dname = "<null>";
      }
      if (mNode.getParent() == null && mRootDisplayName != null) {
         dname = mRootDisplayName;
      }

      int awidth;
      awidth = dataOffset - mNameOffset;
      int selOffset = mNameOffset - 1;
      if (mSelected) {
         g.setColor(mBackgroundSelectionColor);
         int textWidth = computeTextWidth(g, dname, dataOffset);
         g.fillRect(selOffset, 0, textWidth - selOffset, height);
      }
      if (mFocus) {
         int textWidth = computeTextWidth(g, dname, dataOffset);
         g.setColor(mBorderSelectionColor);
         g.drawRect(selOffset, 0, textWidth - (selOffset + 1), height - 1);
      }

      Icon icon = mNode.getIcon();
      if (mNode.getParent() == null && mRootDisplayIcon != null) {
         icon = mRootDisplayIcon;
      }
      if (icon == null) {
         icon = DataIcons.getErrorIcon();// don't crash it.
      }
      icon.paintIcon(this, g, 0, 0);

      if (mNode instanceof ElementTreeNode) {
         ElementTreeNode etn = (ElementTreeNode) mNode;
         if (etn.getIdentityTerm() instanceof SmElement) {
            if (((SmElement) (etn.getIdentityTerm())).isAbstract()) {
               DataIcons.getAnyAbstractIcon().paintIcon(this, g, 0, 0);
            }
         }
      }
      if (mNode.isSubstituted()) {
         // overlay substituted icon:
         DataIcons.getAnySubstitutedIcon().paintIcon(this, g, 0, 0);
      }
      if (mNode.isErrorIcon()) {
         // overlay error icon:
         DataIcons.getErrorIcon().paintIcon(this, g, 0, 0);
      }
      if (mNode.isDisabled()) {
         // overlay disabled icon:
         Color org = mTree.getBackground();
         Color c = new Color(org.getRed(), org.getGreen(), org.getBlue(), 128);
         g.setColor(c);
         for (int i = 0; i < 16; i++) {
            for (int j = i % 2; j < 16; j += 2) {
               g.drawLine(i, j, i, j);
            }
         }
      }
      if (mPaintCardinality) {
         paintRepeat(g, mNode.getMin(), mNode.getMax(), mNode.getNillable());
      }
      g.setFont(style.getNormalFont());
      if (mSelected) {
         if (node.getLineError() != null) {
            g.setColor(mTextSelectionColor);
            if (node.getParent() == null) {
               g.setFont(mBoldFont);
            }
         }
         else {
            g.setColor(mTextSelectionColor);
         }
      }
      else {
         if (node.getLineError() != null) {
            g.setColor(Color.red);
            if (node.getParent() == null) {
               g.setFont(mBoldFont);
            }
         }
         else {
            if (node.isDisabled()) {
               g.setColor(mTextNonSelectionDisabledColor);
            }
            else {
               g.setColor(style.getTextNonSelectionColor());
            }
         }
      }
      if (node.isDisabled()) {
         g.setFont(mItalicFont);
      }
      if (node.getDisplayControl()) {
         g.setFont(mItalicFont);
      }
      if (mNode.getParent() == null && mRootDisplayNameBold) {
         g.setFont(mBoldFont);
      }
      FontMetrics fm = g.getFontMetrics();
      int aty = BasicTextRenderer.computeInlineTextYBaseOffset(fm);
      StringPaintUtils.paintDisplayableString(g, dname, awidth, mNameOffset, aty);
   }

   public void paint(Graphics g) {
      int width = getWidth();
      int height = getHeight();
      int dataAt = getRelativeDataOffset(mNode);

      if (mHasDivider) {
         paintLabel(g, width, height, dataAt);
      }
      else {
         paintLabel(g, width, height, width);
         return; // no more stuff.
      }
      int aty = 0;
      int dataWidth = width - dataAt;

      boolean editable = mNode.isEditable();
      int sheight = height - 1;
      int dataTextOffset = DATA_TEXT_X_OFFSET; // to line up with editor.
      int awidth = (width - dataAt) - dataTextOffset;
      if (editable) {
         String dataValue = mNode.getDataValue();
         // draw the value.
         if (dataValue != null) {
            Color dataBackgroundColor = mNode.getDataBackgroundColor();
            if (dataBackgroundColor == null) {
               dataBackgroundColor = mDataSolidBackgroundColor;
            }
            g.setColor(dataBackgroundColor);
            g.fillRect(dataAt, 0, dataWidth - 1, sheight);
            g.draw3DRect(dataAt, 0, dataWidth - 1, sheight, true);

            if (mFlashMap.size() > 0) {
               if (mFlashMap.containsKey(mNode.getTreePath())) {
                  String text = mNode.getDataValue();
                  if (text != null) {
                     g.setColor(FLASH_COLOR);
                     TextRange r = mFlashMap.get(mNode.getTreePath());
                     int first = r.getStartPosition();
                     int o1 = mNode.getDataRenderer().getTextOffset(g, text.substring(0, first));
                     int end = r.getEndPosition();
                     int o2 = mNode.getDataRenderer().getTextOffset(g, text.substring(0, end));
                     g.fillRect(dataAt + dataTextOffset + o1, 0, o2 - o1, sheight);
                  }
               }
            }
            mNode.getDataRenderer().render(g, dataAt + dataTextOffset, aty, awidth, height, dataValue, false, false, null, mNode);
         }
         else {
            String text = mNode.getBlankText();
            if (text == null) {
               text = "<<null>>"; // bad.
            }
            g.setColor(mDataMissingBackgroundColor);
            g.fillRect(dataAt, 0, dataWidth - 1, sheight);
            g.draw3DRect(dataAt, 0, dataWidth - 1, sheight, true);
            mNode.getDataRenderer().render(g, dataAt + dataTextOffset, aty, awidth, height, text, false, true, null, mNode);
         }
      }
      else {
         String text = mNode.getBlankText();
         if (text != null) {
            mNode.getDataRenderer().render(g, dataAt + dataTextOffset, aty, awidth, height, text, false, true, null, mNode);
         }
      }
      g.setColor(mDataLeadingLineColor);
      g.drawLine(dataAt - 1, 1, dataAt - 1, getHeight() - 3);
   }

   void paintOvershowData(Graphics g, int width, int height) {
      //boolean editable = mNode.isEditable();
      int sheight = height - 1;
      int dataTextOffset = DATA_TEXT_X_OFFSET; // to line up with editor.

      Color dataBackgroundColor = mNode.getDataBackgroundColor();
      if (dataBackgroundColor == null) {
         dataBackgroundColor = mDataSolidBackgroundColor;
      }
      g.setColor(dataBackgroundColor);
      g.fillRect(0, 0, width - 1, sheight);
      g.draw3DRect(0, 0, width - 1, sheight, true);

      if (mFlashMap.size() > 0) {
         if (mFlashMap.containsKey(mNode.getTreePath())) {
            String text = mNode.getDataValue();
            if (text != null) {
               g.setColor(FLASH_COLOR);
               TextRange r = mFlashMap.get(mNode.getTreePath());
               int first = r.getStartPosition();
               int o1 = mNode.getDataRenderer().getTextOffset(g, text.substring(0, first));
               int end = r.getEndPosition();
               int o2 = mNode.getDataRenderer().getTextOffset(g, text.substring(0, end));
               int dataAt = 0;
               g.fillRect(dataAt + dataTextOffset + o1, 0, o2 - o1, sheight);
            }
         }
      }

      String[] lines = getAsLines(mNode.getDataValue());
      ErrorMessageList errors = mNode.getErrorMessages();
      ErrorMessageList[] errorsByLine = null;
      if (errors != null && errors.size() > 0) {
         errorsByLine = errors.divideIntoLines(mNode.getDataValue());
      }
      for (int i = 0; i < lines.length; i++) {
         String lineText = lines[i];

         // draw the value.
         int lineY = mTree.getRowHeight() * i;
         if (i < lines.length - 1) {
            lineText = lineText + "\n "; // hacky, but forces a new-line marker to be painted.
         }
         mNode.getDataRenderer().render(g, dataTextOffset, lineY, width, height, lineText, false, false, null, mNode);
         ErrorMessageList lineErrors = null;
         if (errorsByLine != null && errorsByLine.length > i) {
            lineErrors = errorsByLine[i];
         }
         if (lineErrors != null) {
            mLineRenderer.drawErrorLine(g, lineText.toCharArray(), dataTextOffset, lineY, height, lineErrors);
         }
      }
      g.setColor(mDataLeadingLineColor);
   }

   void rescaled() {
      int xat = 0;
      if (mDrawIcon) {
         xat += 16;
      }
      mCardinalityOffset = xat;
      if (mPaintCardinality) {
         xat += 5;
      }
      xat += 2;
      mNameOffset = xat;
   }

   public void paintErrors(Graphics g, BasicTreeNode node, int offset, int y) {
      ErrorMessageList eml = node.getErrorMessages();
      paintErrors(g, eml, offset, y);
   }

   public void paintErrors(Graphics g, ErrorMessageList eml, int offset, int y) {
      if (eml == null || eml.size() == 0) {
         return;
      }
      int boundAt = offset + 2;

      // Pass the data along
      String dataValue = null;
      if ((mNode != null) && (mNode.getDataValue() != null)) {
         dataValue = mNode.getDataValue();
      }

      // Line index begins with 0
      if (dataValue != null) {
         ErrorMessageList[] byLines = eml.divideIntoLines(dataValue);
         if (byLines.length >= 1) {
            char[] chars = dataValue.toCharArray();
            mLineRenderer.drawErrorLine(g, chars, boundAt, y, getHeight(), byLines[0]);
         }
      }
   }

   public int getActualRowWidth(Graphics g, BasicTreeNode node) {
      g.setFont(style.getNormalFont());
      String text = node.getDisplayName();
      return g.getFontMetrics().charsWidth(text.toCharArray(), 0, text.length()) + mNameOffset + 4;
   }

   /**
    * Gets the offset of the node's data display x location relative to the left-hand side of the tree.
    *
    * @param node The node.
    * @return The offset.
    */
   public int getDataOffset(BasicTreeNode node) {
      if (mIndentDataOffset) {
         return mStartingDataOffset + (node.getDepth()) * mIndentWidth;
      }
      else {
         return mStartingDataOffset;
      }
   }

   /**
    * Gets the offset of the node's data display x location relative to the already offset tree indentation.
    *
    * @param node The node
    * @return The offset
    */
   public int getRelativeDataOffset(BasicTreeNode node) {
      if (mIndentDataOffset) {
         return mStartingDataOffset;
      }
      else {
         return mStartingDataOffset - (node.getDepth()) * mIndentWidth;
      }
   }

   private int getPreferredWidth(BasicTreeNode node) {
      int indent = node.getDepth();
      int avail = mAvailableWidth - indent * mIndentWidth;

      return Math.max(100, avail);
   }

   public Dimension getPreferredSize() {
      if (mHeight < 0) {
         mHeight = super.getPreferredSize().height;
      }
      return new Dimension(getPreferredWidth(mNode), mHeight);
   }

   public Dimension getNameOvershowSize() {
      String dname = mNode.getDisplayName();
      Graphics g = mTree.getGraphics();
      g.setFont(style.getNormalFont());
      int textWidth = g.getFontMetrics().charsWidth(dname.toCharArray(), 0, dname.length()) + mNameOffset + 4;
      g.dispose();
      return new Dimension(textWidth, mTree.getRowHeight());
   }

   Dimension getDataOvershowSize(int availableWidth) {
      Graphics g = mTree.getGraphics();
      String data = mNode.getDataValue();
      if (data == null) {
         data = mNode.getBlankText();
      }
      if (data == null) {
         data = ""; // shouldn't happen.
      }
      //int charWidth = availableWidth / mNode.getDataRenderer().getTextOffset(g,"X");
      String[] lines = getAsLines(data);
      int dw = 0;
      for (int i = 0; i < lines.length; i++) {
         int lineWidth = mNode.getDataRenderer().getTextOffset(g, lines[i]) + computeNewlineSpace(i < lines.length - 1);
         dw = Math.max(dw, lineWidth);
      }
      g.dispose();
      int height = mTree.getRowHeight() * lines.length;
      return new Dimension(Math.min(availableWidth, dw + DATA_TEXT_X_OFFSET), height);
   }

   private int computeNewlineSpace(boolean hasNewLine) {
      return hasNewLine ? 10 : 0;
   }

   public static String[] getAsLines(String text) {
      ArrayList<String> al = new ArrayList<String>();
      StringBuffer sb = new StringBuffer();
      boolean tooManyLines = false;
      if (text == null) {
         return new String[0]; // just in case.
      }
      for (int i = 0; i < text.length(); i++) {
         char c = text.charAt(i);
         if (c == '\n') {// || sb.length()==charsWidth) {
            if (al.size() > 5) {
               if (!tooManyLines) {
                  al.add("...");
               }
               else {
                  tooManyLines = true;
               }
            }
            else {
               al.add(sb.toString());
            }
            sb.setLength(0);
            if (c != '\n') {
               sb.append(c);
            }
            continue;
         }
         sb.append(c);
      }
      if (sb.length() > 0) {
         al.add(sb.toString());
      }
      return al.toArray(new String[al.size()]);
   }
}
