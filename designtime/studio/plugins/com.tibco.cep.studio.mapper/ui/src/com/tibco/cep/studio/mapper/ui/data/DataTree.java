package com.tibco.cep.studio.mapper.ui.data;

import javax.swing.Icon;
import javax.swing.SwingUtilities;

import org.xml.sax.SAXException;

import com.tibco.cep.mapper.xml.xdata.bind.CancelChecker;
import com.tibco.cep.mapper.xml.xdata.xpath.CoercionSet;
import com.tibco.cep.mapper.xml.xdata.xpath.DataContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessageList;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.VariableDefinitionList;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.basic.BasicReportRunner;
import com.tibco.cep.studio.mapper.ui.data.basic.BasicTree;
import com.tibco.cep.studio.mapper.ui.data.basic.BasicTreeNode;
import com.tibco.cep.studio.mapper.ui.data.param.TypeCategory;
import com.tibco.cep.studio.mapper.ui.jedit.errcheck.JExtendedEditTextArea;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmNamespaceProvider;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;

public class DataTree extends BasicTree {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private DataContext mDataContext;
   private ExprContext mExprContext;
   protected TypeCategory[] mTypeCats;
   protected TypeCategory[] mElCats;
   private JExtendedEditTextArea mArea = new JExtendedEditTextArea();

   private SmNamespaceProvider mProvider; // for data any substitution.

   static {
      VariableDefinitionList vars = new VariableDefinitionList();
      vars.lock();
   }

   public DataTree(UIAgent uiAgent) {
      super(uiAgent);
      setRootVisible(false);
      setShowsRootHandles(true);
      super.setHasDivider(false);
      super.setLightenMissing(true);
      super.setPaintCardinality(true);
      super.setIndentData(false);
   }

   public void setTypeCategories(com.tibco.cep.studio.mapper.ui.data.param.TypeCategory[] typeCats, com.tibco.cep.studio.mapper.ui.data.param.TypeCategory[] elCats) {
      mTypeCats = typeCats;
      mElCats = elCats;
   }

   public DataContext getDataContext() {
      return mDataContext;
   }

   public void setSchemaProvider(SmNamespaceProvider provider) {
      mProvider = provider;
   }

   public SmNamespaceProvider getSchemaProvider() {
      return mProvider;
   }

   public BasicTreeNode buildFromXML(BasicTreeNode pasteOn, String xml) throws SAXException {
      /*if (pasteOn instanceof DataTreeNode) {
          DataTreeNode dtn = (DataTreeNode) pasteOn;
          return dtn.createFromXML(mProvider,xml);
      }*/
      return super.buildFromXML(pasteOn, xml);
   }

   public void setDataContext(DataContext dataContext) {
      setupForData();
      setRootVisible(false);
      setShowsRootHandles(true);

      if (mDataContext != null) {
         TreeState state = getTreeState();
         mDataContext = dataContext;
//            setRootNode(new DataContextTreeNode(mDataContext));
         setTreeState(state);
      }
      else {
         mDataContext = dataContext;
         if (mDataContext != null) {
//                setRootNode(new DataContextTreeNode(mDataContext));
         }
      }
   }

   /**
    * Sets the data for editing.
    *
    * @param data The data, this accepts null as a crash deterant, it will display garbage in that case.
    */
   public void setRoot(XiNode data) {
//        setupForData();
//        TreeState state = getTreeState();
//        if (data==null) {
//            // As a crash deterant, should never really happen:
//            SmElement el = UtilitySchema.createErrorMarkerElement("(no data)");
//            data = XData.create(el);
//        }
//        setRootNode(new XDataTreeNode(data.getElement(),data,false));
//        setRootVisible(true);
//        setShowsRootHandles(false);
//        setTreeState(state);
   }

   public XiNode getRoot() {
//        XDataTreeNode tn = (XDataTreeNode) getModel().getRoot();
//        return (XData) tn.getData();
      throw new RuntimeException();
   }

   static class BlankNode extends BasicTreeNode {
      public Icon getIcon() {
         return DataIcons.getAnyIcon();
      }

      public String getName() {
         return "<blank>";
      }

      public boolean isLeaf() {
         return true;
      }

      public BasicTreeNode[] buildChildren() {
         return new BasicTreeNode[0];
      }

      public BasicTreeNode[] getChildren() {
         return new BasicTreeNode[0];
      }

      public Object getIdentityTerm() {
         return null;
      }

      public boolean isEditable() {
         return false;
      }
   }

   /**
    * Fills in with blank tree.
    */
   public void setBlank() {
      setupForType();
      setRootVisible(false);
      setShowsRootHandles(false);
      setRootNode(new BlankNode());
   }

   public void setExprContext(ExprContext exprContext) {
      setExprContext(exprContext, null);
   }

   public void setExprContext(ExprContext exprContext, CoercionSet set) {
      setupForType();
      mExprContext = exprContext;
      setRootVisible(false);
      setShowsRootHandles(true);

      TreeState state = getTreeState();
      setRootNode(new ExprContextTreeNode(exprContext, set));
      setTreeState(state);
   }

   public ExprContext getExprContext() {
      return mExprContext;
   }

   /*
   public void setRootElement(SmElement element) {
       //mRootElement = element;
       setupForType();
       setRootVisible(true);
       setShowsRootHandles(false);

       TreeState state = getTreeState();
       setRootNode(new ElementTreeNode(element,1,1,false,null,null));
       setTreeState(state);
   }

   public void setRootType(SmType type) {
       //mRootType = type;
       setupForType();
       setRootVisible(true);
       setShowsRootHandles(false);

       TreeState state = getTreeState();
       setRootNode(new TypeTreeNode(type,1,1,null));
       setTreeState(state);
   }*/

   public void setRootType(SmSequenceType type) {
      setupForType();
      setRootVisible(true);
      setShowsRootHandles(false);

      TreeState state = getTreeState();
      DataTypeTreeNode n;
      try {
         n = DataTypeTreeNode.createNodeForXType(type, SmCardinality.EXACTLY_ONE, null);
      }
      catch (Exception e) {
         // Don't throw out; defect 1-19QVA6 was about this; through some bizarre set of steps a call to 'close'
         // wound up calling this with a corrupt SmParticle causing all hell to break loose; just fail gracefully.
         SmSequenceType tt = SmSequenceTypeFactory.createPreviousError("Internal error, bad type, failed to create: " + e.getMessage());
         n = DataTypeTreeNode.createNodeForXType(tt, SmCardinality.EXACTLY_ONE, null);
      }
      setRootNode(n);
      setTreeState(state);
   }

   private void setupForType() {
      setReportRunner(null);
      super.setHasDivider(false);
   }

   private void setupForData() {
      super.setReportRunner(new ReportRunner());
      super.setHasDivider(true);
   }

   protected boolean allowsCrossBranchDrops() {
      return false;
   }

   class ReportRunner extends BasicReportRunner {
      public Object buildReport(CancelChecker cancelChecker) {
         BasicTreeNode root = DataTree.this.getRootNode();
         return buildForNode(root);
      }

      private DataTreeReport buildForNode(BasicTreeNode bnode) {
         /*
         DataTreeNode node = (DataTreeNode) bnode;
         String lineMsg = node.getLineError();
         ErrorMessageList eml = node.getErrorMessages();
         DataTreeReport[] children;
         boolean childHadError = false;
         if (node.isLeaf() || node.getChildCount()==0) {
             children = NULL_CHILDREN;
         } else {
             if (node.hasBeenExpanded() || lineMsg==null && !node.isNull()) {
                 children = new DataTreeReport[node.getChildCount()];
                 for (int i=0;i<children.length;i++) {
                     children[i] = buildForNode(node.getChild(i));
                     if (children[i].getLineError()!=null) {
                         childHadError = true;
                     }
                 }
             } else {
                 children = NULL_CHILDREN;
             }
         }
         String msg = lineMsg;
         if (msg==null && childHadError) {
             msg = "Content has errors";
         }
         DataTreeReport dtr = new DataTreeReport(node,msg,eml,children);
         return dtr;*/
         return null;
      }

      public Object getReportChild(Object report, int index) {
         DataTreeReport creport = (DataTreeReport) report;
         return creport.getChild(index);
      }

      public boolean getReportHasChildContent(Object report) {
         if (report == null) {
            return false;//@@@@@@ REMOVE
         }
         DataTreeReport creport = (DataTreeReport) report;
         return creport.getChildCount() > 0;
      }

      protected void ensureReportExpanded(Object report) {
         // no work required.
      }
   }

   public ErrorMessageList getEditorErrorMessageList() {
      return null;
   }

   public int getDataCharWidth() {
      return 50;
   }

   public JExtendedEditTextArea initializeEditor(BasicTreeNode node) {
      if (mArea == null) {
         mArea = new JExtendedEditTextArea();
      }
      return mArea;
   }

   void rebuild() {
      getRootNode();
//        if (n instanceof DataContextTreeNode) {
//            DataContextTreeNode ectn = (DataContextTreeNode) n;
//            DataContext ec = ectn.getDataContext();
//            // for data tree to rebuild after big change.
//            TreeState state = getTreeState();
//            super.setRootNode(new DataContextTreeNode(ec));
//            setTreeState(state);
//        }
   }

   /**
    * For subclassing; type nodes will call this method to see if they have 'content'
    * (for binding lines, etc.)
    */
   protected boolean nodeHasChildContent(BasicTreeNode node) {
      return false;
   }

   public void edit(BasicTreeNode node) {
      String ret = DataEditDialog.showDialog(SwingUtilities.getWindowAncestor(this), node.getDataValue());
      if (ret == null) {
         return;
      }
   }
}
