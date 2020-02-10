package com.tibco.cep.studio.mapper.ui.data.bind.coerce;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

import com.tibco.cep.mapper.xml.nsutils.NamespaceManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.xpath.Coercion;
import com.tibco.cep.mapper.xml.xdata.xpath.CoercionSet;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.studio.mapper.ui.PaintUtils;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.edittree.DefaultTreeNodeEditableTreeModel;
import com.tibco.cep.studio.mapper.ui.edittree.EditableTree;
import com.tibco.cep.studio.mapper.ui.edittree.EditableTreePathUtils;
import com.tibco.cep.studio.mapper.ui.edittree.render.ExtendedTreeCellRenderer;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.data.primitive.QName;
import com.tibco.xml.schema.SmCardinality;

/**
 * The list (1-level tree) used for the coercion editing.
 */
class CoercionXPathList extends EditableTree {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String m_currentXPath;

   public CoercionXPathList(UIAgent uiAgent, CoercionSet initialVals, String xpath, ExprContext exprContext) {
      super(uiAgent);
      super.setRootVisible(false);
      super.setReportRunner(new CoercionReportRunner(exprContext));

      m_currentXPath = xpath;
      Node r = new Node();
      Node firstChild = null;
      Node matchingXPath = null;
      // Build the initial tree:
      for (int i = 0; i < initialVals.getCount(); i++) {
         Coercion c = initialVals.get(i);
         Node n = new Node();
         if (firstChild == null) {
            firstChild = n;
         }
         String xp = c.getXPath();
         if (xp != null && xp.equals(xpath)) {
            matchingXPath = n;
         }
         n.setXPath(xp);
         n.setType(c.getType());
         String tn = c.getTypeOrElementName();
         QName qn = new QName(tn);
         ExpandedName en;
         try {
            en = qn.getExpandedName(exprContext.getNamespaceMapper());
         }
         catch (Exception e) {
            en = null;
         }
         if (en == null) {
            // be lenient:
            en = ExpandedName.makeName(qn.getLocalName());
         }
         n.setOccurrence(c.getOccurrence());
         n.setExpandedName(en);
         r.insert(n, i);
      }
      super.setEditableModel(new DefaultTreeNodeEditableTreeModel(r) {
         /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Object createNewChild(Object parent) {
            Node n = new Node();
            n.setXPath(m_currentXPath == null ? "" : m_currentXPath);
            return n;
         }
      });

      // Preselect anything with matching xpath (first) or the first one (second)
      Node selNode = matchingXPath == null ? firstChild : matchingXPath;
      if (selNode != null) {
         TreePath path = EditableTreePathUtils.getTreePath(getEditableModel(), selNode);
         setSelectionPath(path);
      }
      setCellRenderer(new Renderer());
   }

   class Renderer extends DefaultTreeCellRenderer implements ExtendedTreeCellRenderer {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Node m_node;

      public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                    boolean sel,
                                                    boolean expanded,
                                                    boolean leaf, int row,
                                                    boolean hasFocus) {
         m_node = (Node) value;
         waitForReport();
         setFont(uiAgent.getScriptFont());
         Component c = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
         return c;
      }

      public Color getBackgroundColor(Object node) {
         return null;
      }

      public boolean isCapturingMouse() {
         return false;
      }

      public String getToolTipText(Object node, Point rendererRelative) {
         if (m_node.getReport() == null) {
            return null;
         }
         if (m_node.getReport().getError() == null) {
            return null;
         }
         return m_node.getReport().getError().getMessage();
      }

      public void paint(Graphics g) {
         super.paint(g);
         if (m_node.getReport() == null) {
            return;
         }
         if (m_node.getReport().getError() == null) {
            return;
         }
         Font f = getFont();
         g.setFont(f);
         FontMetrics fm = g.getFontMetrics();
         int h = fm.getHeight();
         int w = fm.charWidth('X') / 2;
         g.setColor(Color.red);
         PaintUtils.drawWavyLine(g, w, w, 18, getWidth(), h);
      }
   }


   public class Node extends DefaultMutableTreeNode {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String m_xpath = "";
      private int m_type; // From Coercion, i.e. COERCION_TYPE, COERCION_ELEMENT or COERCION_GROUP
      private ExpandedName m_name;
      private CoercionReportRunner.Report m_cachedReport;
      private SmCardinality m_optionalOccurrence;

      public Node() {
         m_name = ExpandedName.makeName("");
      }

      public CoercionReportRunner.Report getReport() {
         return m_cachedReport;
      }

      public void setReport(CoercionReportRunner.Report report) {
         m_cachedReport = report;
      }

      public String getXPath() {
         return m_xpath;
      }

      public void setXPath(String xpath) {
         m_xpath = xpath;
      }

      public int getType() {
         return m_type;
      }

      public void setType(int type) {
         m_type = type;
      }

      public ExpandedName getExpandedName() {
         return m_name;
      }

      public void setExpandedName(ExpandedName name) {
         m_name = name;
      }

      /**
       * Gets the optional cardinality (may be null)
       */
      public SmCardinality getOccurrence() {
         return m_optionalOccurrence;
      }

      public void setOccurrence(SmCardinality c) {
         m_optionalOccurrence = c;
      }

      // Display stuff:
      public String toString() {
         if (m_xpath == null || m_xpath.length() == 0) {
            return "()"; // better than empty string.
         }
         return m_xpath;
      }

      public Coercion createCoercion(NamespaceContextRegistry ni) {
         String xp = getXPath();
         QName qn = NamespaceManipulationUtils.getOrAddQNameFromExpandedName(getExpandedName(), ni);
         SmCardinality card = getOccurrence();
         Coercion cc = new Coercion(xp, qn.toString(), getType(), card);
         return cc;
      }

      public boolean isLeaf() {
         // Everything but the root is a leaf.
         return getParent() != null;
      }
   }
}

