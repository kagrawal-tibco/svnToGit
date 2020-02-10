package com.tibco.cep.studio.mapper.ui.data.bind;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.FocusListener;

import javax.swing.Icon;

import com.tibco.cep.mapper.xml.xdata.bind.BindingNamespaceManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessage;
import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessageList;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.basic.BasicTreeCellRenderer;
import com.tibco.cep.studio.mapper.ui.data.param.DataIconRenderer;
import com.tibco.cep.studio.mapper.ui.data.xpath.XPathTextRenderer;
import com.tibco.cep.studio.mapper.ui.edittree.render.DefaultNameValueTreeCellPlugin;
import com.tibco.cep.studio.mapper.ui.edittree.render.DefaultTextRenderer;
import com.tibco.cep.studio.mapper.ui.edittree.render.TextRenderer;
import com.tibco.cep.studio.mapper.ui.jedit.errcheck.JExtendedEditTextArea;

/**
 * The renderer plugin for {@link BindingTree}.<br>
 * Handles the painting, tool-tip finding, etc., using data from {@link BindingNode}.
 */
class BindingNodeRendererPlugin extends DefaultNameValueTreeCellPlugin {
   private DataIconRenderer m_renderer;
   private Font m_constantFont;
   private Font m_commentFont;
   private DefaultTextRenderer m_nameRenderer;
   private Font m_nameRendererNormalFont;
   private Font m_nameRendererMarkerFont;
   private BindingXPathRenderer m_xpathTextRenderer;
   private BindingXPathRenderer m_avtTextRenderer;
   private DefaultTextRenderer m_commentTextRenderer;
   private DefaultTextRenderer m_constantTextRenderer;
   private BindingXPathTextArea m_xpathField;
   private JExtendedEditTextArea m_commentEditor;
   private ConstantTextArea m_constantEditor;
   private BindingEditor m_bindingEditor;

   String m_rootDisplayName;// allows the root display name to be overridden.
   Icon m_rootDisplayIcon;// allows the root display icon to be overridden.

   public BindingNodeRendererPlugin(UIAgent uiAgent, BindingEditor ed, BindingTree tree) {
      super(uiAgent);

      m_bindingEditor = ed;
      m_renderer = new DataIconRenderer(tree.getBackground(), uiAgent);
      m_constantFont = uiAgent.getScriptFont().deriveFont(Font.BOLD);
      m_commentFont = uiAgent.getScriptFont().deriveFont(Font.ITALIC);

      m_xpathTextRenderer = new BindingXPathRenderer(uiAgent);

      m_avtTextRenderer = new BindingXPathRenderer(uiAgent);
      m_avtTextRenderer.setAVTMode(true);

      m_constantTextRenderer = new DefaultTextRenderer(m_constantFont);

      m_commentTextRenderer = new DefaultTextRenderer(m_commentFont);

      m_nameRendererNormalFont = uiAgent.getAppFont();
      m_nameRendererMarkerFont = m_nameRendererNormalFont.deriveFont(Font.ITALIC);
      m_nameRenderer = new DefaultTextRenderer(uiAgent.getAppFont());

      m_xpathField = new BindingXPathTextArea(uiAgent, ed);
      m_xpathField.setEditable(true);
   }
   
   private String getNodeName(BindingNode bn) {
      if (bn.getParent() == null && m_rootDisplayName != null) {
         return m_rootDisplayName;
      }
      return bn.getDisplayName();
   }

   public XPathTextRenderer getXPathTextRenderer() {
      return m_xpathTextRenderer.getRenderer();
   }

   public Color getBackgroundColor(Object node) {
      BindingNode bn = (BindingNode) node;
      return bn.getBackgroundColor();
   }

   public TextRenderer getNameRenderer(Object node, boolean isSel, boolean isFocus) {
      BindingNode bn = (BindingNode) node;
      m_nameRenderer.setSelected(isSel);
      m_nameRenderer.setError(bn.getLineError() != null && !isSel);
      m_nameRenderer.setFont(bn.isMarker() ? m_nameRendererMarkerFont : m_nameRendererNormalFont);
      m_nameRenderer.setText(getNodeName(bn));
      return m_nameRenderer;
   }

   public String getNameToolTip(Object node) {
      BindingNode bn = (BindingNode) node;
      return bn.getLineError();
   }

   public String getDataToolTip(Object node, Point dataRelativePoint) {
      BindingNode bn = (BindingNode) node;
      ErrorMessageList eml = bn.getErrorMessages();
      if (eml == null || eml.size() == 0) {
         return null;
      }
      // Find closest error:
      int xinset = dataRelativePoint.x;
      int columnsIn = 0 + getCharacterPosition(xinset);
      int smallestDistance = 100;
      ErrorMessage[] errs = eml.getMessages();
      for (int i = 0; i < errs.length; i++) {
         ErrorMessage em = errs[i];
         int distance = computeColumnDistance(em.getTextRange(), columnsIn);
         smallestDistance = Math.min(smallestDistance, distance);
      }
      // If you weren't even close, forget it:
      if (smallestDistance > 3) {
         return null;
      }

      // Go back & find the closest one:
      for (int i = 0; i < errs.length; i++) {
         ErrorMessage em = errs[i];
         int distance = computeColumnDistance(em.getTextRange(), columnsIn);
         if (distance == smallestDistance) {
            return em.getMessage();
         }
      }
      // not reachable, actually.
      return null;
   }

   /**
    * Utility function that returns the number of columns away from the location
    */
   private static int computeColumnDistance(TextRange tl, int colNo) {
      if (tl.getStartPosition() <= colNo) {
         if (tl.getEndPosition() >= colNo) {
            return 0;
         }
         return colNo - tl.getEndPosition();
      }
//        tl.getDistance
      return tl.getStartPosition() - colNo;
   }

   private int getCharacterPosition(int x) {
      int w = m_xpathTextRenderer.getCharWidth();
      if (w == 0) { // can happen at startup.
         return 0;
      }
      return (x / w);
   }

   public Dimension getDataOvershowSize(Object onode, Graphics g, int availableWidth, int rowHeight) {
      BindingNode node = (BindingNode) onode;
      String data = node.getDataValue();
      if (data == null) {
         data = node.getBlankText();
      }
      if (data == null) {
         data = ""; // shouldn't happen.
      }
      String[] lines = BasicTreeCellRenderer.getAsLines(data);
      int dw = 0;
      for (int i = 0; i < lines.length; i++) {
         int lineWidth = getTextOffset(g, lines[i]) + computeNewlineSpace(i < lines.length - 1);
         dw = Math.max(dw, lineWidth);
      }
      g.dispose();
      int height = rowHeight * lines.length;
      int DATA_TEXT_X_OFFSET = 0;
      return new Dimension(Math.min(availableWidth, dw + DATA_TEXT_X_OFFSET), height);
   }

   private int computeNewlineSpace(boolean hasNewLine) {
      return hasNewLine ? 10 : 0;
   }

   public int getTextOffset(Graphics g, String text) {
      g.setFont(m_xpathTextRenderer.getRenderer().getFont());
      return g.getFontMetrics().charsWidth(text.toCharArray(), 0, text.length());
   }

   public void paintOvershowData(Object onode, Graphics g, Dimension size, int rowHeight) {
      BindingNode node = (BindingNode) onode;
      int width = size.width;
      int height = size.height;

      /*
      if (mFlashMap.size()>0)
      {
          if (mFlashMap.containsKey(m_node.getTreePath())) {
              String text = m_node.getDataValue();
              if (text!=null) {
                  g.setColor(FLASH_COLOR);
                  TextRange r = (TextRange)mFlashMap.get(m_node.getTreePath());
                  int first = r.getStartPosition();
                  int o1 = m_node.getDataRenderer().getTextOffset(g,text.substring(0,first));
                  int end = r.getEndPosition();
                  int o2 = m_node.getDataRenderer().getTextOffset(g,text.substring(0,end));
                  int dataAt = 0;
                  g.fillRect(dataAt+dataTextOffset+o1,0,o2-o1,sheight);
              }
          }
      }*/

      String[] lines = BasicTreeCellRenderer.getAsLines(node.getDataValue());
      ErrorMessageList errors = node.getErrorMessages();
      ErrorMessageList[] errorsByLine = null;
      if (errors != null && errors.size() > 0) {
         errorsByLine = errors.divideIntoLines(node.getDataValue());
      }
      for (int i = 0; i < lines.length; i++) {
         String lineText = lines[i];

         // draw the value.
         int lineY = rowHeight * i;
         if (i < lines.length - 1) {
            lineText = lineText + "\n "; // hacky, but forces a new-line marker to be painted.
         }
         ErrorMessageList lineErrors = null;
         if (errorsByLine != null && errorsByLine.length > i) {
            lineErrors = errorsByLine[i];
         }
         getDataRenderer(node, lineText, lineErrors).render(g, 0, lineY, width, height);
      }
   }

   public TextRenderer getDataRenderer(Object node) {
      BindingNode n = (BindingNode) node;
      String formula = n.getDataValue();
      return getDataRenderer(n, formula, n.getErrorMessages());
   }

   private TextRenderer getDataRenderer(BindingNode n, String txt, ErrorMessageList eml) {
      int editableFieldType = n.getEditableFieldType();
      if (txt == null) {
         txt = "";
      }
      if (editableFieldType == StatementPanel.FIELD_TYPE_CONSTANT) {
         m_constantTextRenderer.setText(txt);
         return m_constantTextRenderer;
      }
      if (editableFieldType == StatementPanel.FIELD_TYPE_COMMENT) {
         m_commentTextRenderer.setText(txt);
         return m_commentTextRenderer;
      }
      if (editableFieldType == StatementPanel.FIELD_TYPE_ATTRIBUTE_VALUE_TEMPLATE) {
         m_avtTextRenderer.setText(txt);
         m_avtTextRenderer.setErrors(eml);
         return m_avtTextRenderer;
      }
      // XPath:
      m_xpathTextRenderer.setText(txt);
      m_xpathTextRenderer.setErrors(eml);
      return m_xpathTextRenderer;
   }

   public JExtendedEditTextArea getDataEditor(Object node) {
      BindingNode bn = (BindingNode) node;
      TemplateReport report = bn.getTemplateReport();
      if (m_constantEditor == null) {
         Font f = m_constantFont;
         m_constantEditor = new ConstantTextArea(m_bindingEditor);
         m_constantEditor.setFont(f);
         m_constantEditor.setEditable(true);
      }
      if (m_commentEditor == null) {
         Font f = m_commentFont;
         m_commentEditor = new JExtendedEditTextArea();
         m_commentEditor.setFont(f);
         m_commentEditor.setEditable(true);
      }
      if (report == null) {
         // Shouldn't happen, just in case:
         System.err.println("Null report:" + bn.getName());
      }
      int ft = bn.getEditableFieldType();
      switch (ft) {
         case StatementPanel.FIELD_TYPE_XPATH:
            {
               m_xpathField.setAVTMode(false);
               m_xpathField.setText(bn.getDataValue());
               m_xpathField.setExprContext(report.getContext());
               m_xpathField.setNamespaceImporter(BindingNamespaceManipulationUtils.createNamespaceImporter(report.getBinding()));
               m_xpathField.setTypeChecker(new BindingXPathTypeChecker(report));
               return m_xpathField;
            }
         case StatementPanel.FIELD_TYPE_ATTRIBUTE_VALUE_TEMPLATE:
            {
               m_xpathField.setAVTMode(true);
               m_xpathField.setText(bn.getDataValue());
               m_xpathField.setExprContext(report.getContext());
               m_xpathField.setNamespaceImporter(BindingNamespaceManipulationUtils.createNamespaceImporter(report.getBinding()));
               m_xpathField.setTypeChecker(new BindingXPathTypeChecker(report));
               return m_xpathField;
            }
         case StatementPanel.FIELD_TYPE_CONSTANT:
            {
               m_constantEditor.setBackground(getDataBackgroundColor(node));
               m_constantEditor.setText(bn.getDataValue());
               return m_constantEditor;
            }
         case StatementPanel.FIELD_TYPE_COMMENT:
            {
               m_commentEditor.setBackground(getDataBackgroundColor(node));
               m_commentEditor.setText(bn.getDataValue());
               return m_commentEditor;
            }
         default:
            {
               // shouldn't happen, whatever:
               return m_xpathField;
            }
      }
   }

   public int getIconWidth() {
      return m_renderer.getIconWidth();
   }

   public Icon getIcon(Object node) {
      BindingNode pn = (BindingNode) node;
      if (pn.getParent() == null && m_rootDisplayIcon != null) {
         return m_rootDisplayIcon;
      }

      m_renderer.setBaseIcon(pn.getIcon());
      m_renderer.setMin(pn.getMin());
      m_renderer.setMax(pn.getMax());
      m_renderer.setNilled(pn.getNillable());
      m_renderer.setError(pn.isErrorIcon());
      m_renderer.setIsDisabled(pn.isDisabled());
      m_renderer.setSubstituted(pn.isSubstituted());

      return m_renderer;
   }

   public Color getDataBackgroundColor(Object node) {
      BindingNode n = (BindingNode) node;
      return n.getDataBackgroundColor();
   }

   public boolean isMultiLineData(Object node) {
      BindingNode bn = (BindingNode) node;
      String d = bn.getDataValue();
      if (d == null) {
         return false;
      }
      String[] lines = BasicTreeCellRenderer.getAsLines(d);
      return lines.length > 1;
   }

   public boolean isDataEditable(Object node) {
      BindingNode bn = (BindingNode) node;
      return bn.isEditable();
   }

   public void dataEditingFinished(Object node, JExtendedEditTextArea edit) {
      BindingNode bn = (BindingNode) node;
      bn.setDataValue(edit.getText());
   }
   
   public void addTextAreaFocuslListener( FocusListener fListener){
	   if(m_xpathField!= null)
		   m_xpathField.addFocusListener(fListener);
   }
   
   public void removeTextAreaFocuslListener( FocusListener fListener){
	   if(m_xpathField!= null)
		   m_xpathField.removeFocusListener(fListener);
   }
   
   public void enableIgnoreInlineEditChange(boolean enable){
	   m_xpathField.enableIgnoreInlineEditChange(enable);
   }
}

