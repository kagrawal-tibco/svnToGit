package com.tibco.cep.studio.mapper.ui.data.param;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.nsutils.NamespaceManipulationUtils;
import com.tibco.cep.mapper.xml.nsutils.SimpleNamespaceContextRegistry;
import com.tibco.cep.mapper.xml.xdata.bind.fix.XsdContentModelChangeEvent;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.utils.DocumentNameValidator;
import com.tibco.cep.studio.mapper.ui.data.utils.PreferenceUtils;
import com.tibco.cep.studio.mapper.ui.data.utils.SoftDragNDropHandler;
import com.tibco.cep.studio.mapper.ui.edittree.MasterDetailsTree;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.data.primitive.QName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiFactoryFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiAttribute;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.datamodel.helpers.XiSerializer;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;

/**
 * An xsd content-model editor.
 * After calling the constructor, see {@link #setImportRegistry} and {@link #setNamespaceImporter}, these are required
 * to be set.  (They can be re-set later to recycle the component).
 */
public class ParameterEditor extends JPanel {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private MasterDetailsTree m_mainPanel;
   private ParameterEditorTree m_tree;
   private ParameterDetails m_details;
   private boolean mIsNull = false; // when set, displays message saying it's null.
   private boolean mSetting;
   private Vector<ChangeListener> mListeners = new Vector<ChangeListener>();
   private ContentModelCategory[] mAllCategories;
   private TypeCategory[] m_typeCategories;
   private SoftDragNDropHandler mSoftDragNDropHandler;
   private NamespaceContextRegistry m_namespaceContextRegistry;
   private ImportRegistry m_importRegistry;
   private boolean m_namedVariablesMode;
   private boolean m_allowsExternalReferences = true;// by default on.
   private boolean m_allowsNestedComplex = true;// by default on.
   private boolean m_allowsAttributes = true; // by default on.
   private boolean m_allowsDefaultValue = false;
   private ArrayList<XsdContentModelChangeListener> m_xsdContentModelChangeListeners = new ArrayList<XsdContentModelChangeListener>();
   private boolean m_allowsRootSequence;

   private String m_defaultNewElementName = ParameterEditorResources.DEFAULT_NEW_ELEMENT_NAME;
private UIAgent uiAgent;

   /**
    * The code for cardinality required.
    */
   public static final int CARDINALITY_REQUIRED = 0;
   /**
    * The code for cardinality optional.
    */
   public static final int CARDINALITY_OPTIONAL = 1;
   /**
    * The code for cardinality repeating.
    */
   public static final int CARDINALITY_REPEATING = 2;
   /**
    * The code for cardinality at-least-one.
    */
   public static final int CARDINALITY_AT_LEAST_ONE = 3;

   private static ContentModelCategory[] BUILT_IN = new ContentModelCategory[]
   {
      ElementSequenceCategory.INSTANCE,
      ElementTypeRefCategory.INSTANCE,
      ElementRefCategory.INSTANCE,
      AttributeTypeRefCategory.INSTANCE,
      SequenceCategory.INSTANCE,
      ChoiceCategory.INSTANCE,
      AllGroupCategory.INSTANCE,
      GroupRefCategory.INSTANCE,
      WildcardCategory.INSTANCE,
   };

   /**
    * The xsd type categories (WCETODO needs a better home)
    */
   public static TypeCategory[] BUILT_IN_TYPES = new TypeCategory[]
   {
      StringAndSubtypesCategory.INSTANCE,
      IntegerCategory.INSTANCE,
      DecimalCategory.INSTANCE,
      BooleanCategory.INSTANCE,
      DateTimeCategory.INSTANCE,
      BinaryCategory.INSTANCE,
      AnyURICategory.INSTANCE,
      TypeRefCategory.INSTANCE,
      AnyCategory.INSTANCE,
   };

   public static final TypeCategory[] RESTRICTED_SET = new TypeCategory[]
   {
      StringCategory.INSTANCE,
      IntegerCategory.INSTANCE,
      DecimalCategory.INSTANCE,
      BooleanCategory.INSTANCE
   };

   public static final TypeCategory[] RESTRICTED_SET_WITH_BINARY = new TypeCategory[]
   {
      StringCategory.INSTANCE,
      IntegerCategory.INSTANCE,
      DecimalCategory.INSTANCE,
      BooleanCategory.INSTANCE,
      BinaryCategory.INSTANCE
   };

   /**
    * Constructor, see {@link #setImportRegistry} and {@link #setNamespaceImporter}, these are required.
    *
    * @param doc
    */
   public ParameterEditor(UIAgent uiAgent) {
      this(uiAgent, BUILT_IN_TYPES);
   }

   public ParameterEditor(UIAgent uiAgent, TypeCategory[] typeCategories) {
      this(uiAgent, BUILT_IN, typeCategories);
   }

   public ParameterEditor(UIAgent uiAgent,
                          ContentModelCategory[] cmc,
                          TypeCategory[] typeCategories) {
      super(new BorderLayout());
      m_mainPanel = new MasterDetailsTree();
      add(m_mainPanel, BorderLayout.CENTER);
      this.uiAgent = uiAgent;

      int loc = PreferenceUtils.readInt(uiAgent, "paramEditor.dividerLocation", 180);
      m_mainPanel.setDividerLocation(loc);

      ArrayList<ContentModelCategory> allCats = new ArrayList<ContentModelCategory>();
      mAllCategories = cmc;
      for (int i = 0; i < cmc.length; i++) {
         allCats.add(cmc[i]);
      }
      mAllCategories = allCats.toArray(new ContentModelCategory[0]);
      m_typeCategories = typeCategories;

      m_details = new ParameterDetails(this, uiAgent, mAllCategories, m_typeCategories);
      m_tree = new ParameterEditorTree(uiAgent, this);
      m_mainPanel.setTree(m_tree);
      m_tree.setName("ParameterTree"); // (Internal label)

      m_mainPanel.setDetails(m_details);
      m_tree.reenableButtons();
      m_tree.addContentChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent ce) {
            contentChange();
         }
      });
      mSoftDragNDropHandler = new SoftDragNDropHandler(this, new JComponent[]{m_tree});

      // auto-select the first row:
      m_tree.setSelectionRow(0);
   }

   public void setEnabled(boolean enabled) {
      super.setEnabled(enabled);
      m_tree.setTreeEditable(enabled);
      m_details.setEditable(enabled);
   }

   /**
    * Sets if the picker will allow repeating (including at-least-one) cardinality option.<br>
    * By default, this is <code>true</code>.
    */
   public void setAllowsRepeatingCardinality(boolean allowsRepeating) {
      m_details.setAllowsRepeatingCardinality(allowsRepeating);
   }

   /**
    * Gets if the picker will allow repeating (including at-least-one) cardinality option.
    */
   public boolean getAllowsRepeatingCardinality() {
      return m_details.getAllowsRepeatingCardinality();
   }

   /**
    * Sets if the editor allows external references (i.e. to external schemas) or not.<br>
    * By default, this is <b>true</b>.
    */
   public void setAllowsExternalReferences(boolean allowsExternalRefs) {
      m_allowsExternalReferences = allowsExternalRefs;
   }

   public boolean getAllowsExternalReferences() {
      return m_allowsExternalReferences;
   }

   public void setAllowsDefaultValue(boolean allowsDefaultValue) {
      m_allowsDefaultValue = allowsDefaultValue;
      m_details.setAllowsDefaultValue(allowsDefaultValue);
   }

   public void setContentModelTypeInvisible() {
      m_details.setContentModelTypeInvisible();
   }

   public boolean getAllowsDefaultValue() {
      return m_allowsDefaultValue;
   }

   /**
    * Sets if the editor allows more than 1 level of structure or not.<br>
    * For example, if editing a simple name-value pair list, this should be set to false.<br>
    * By default, this is <b>true</b>.
    */
   public void setAllowsNestedComplex(boolean allowsNestedComplex) {
      m_allowsNestedComplex = allowsNestedComplex;

      m_tree.setTreeMode(allowsNestedComplex);
   }

   public boolean getAllowsNestedComplex() {
      return m_allowsNestedComplex;
   }

   /**
    * Sets if the editor allows XML attributes.<br>
    * By default, this is <b>true</b>.
    */
   public void setAllowsAttributes(boolean allowsAttributes) {
      m_allowsAttributes = allowsAttributes;
   }

   public boolean getAllowsAttributes() {
      return m_allowsAttributes;
   }

   /**
    * Sets the name validator to be used in validating the names of entries.<br>
    * By default, it contains an XML name validator.
    *
    * @param nameValidator The validator to use in verifying names.
    */
   public void setDocumentNameValidator(DocumentNameValidator nameValidator) {
      m_details.setDocumentNameValidator(nameValidator);
      m_tree.setDocumentNameValidator(nameValidator);
   }

   /**
    * Gets the name validator to be used in validating the names of entries.
    */
   public DocumentNameValidator getDocumentNameValidator() {
      return m_details.getDocumentNameValidator();
   }

   public TypeCategory[] getAllTypeCategories() {
      return m_typeCategories;
   }

   TypeCategory getDefaultTypeCategory() {
      return m_typeCategories[0]; // must have at least 1 category...
   }

   /**
    * Internal method for notifying listeners.
    */
   private void contentChange() {
      if (mSetting) {
         return;
      }
      for (int i = 0; i < mListeners.size(); i++) {
         ChangeListener cl = mListeners.get(i);
         cl.stateChanged(new ChangeEvent(this));
      }
   }

   /**
    * Gets the current editing state (expansion and so-forth) of the editor.
    *
    * @return
    */
   public ParameterEditorState getState() {
      return new ParameterEditorState(m_tree.getTreeState());
   }

   public void setState(ParameterEditorState state) {
      if (state == null) {
         return;
      }
      m_tree.setTreeState(state.m_treeState);
   }

   void refreshDetails() {
      m_details.read();
   }

   public void setRootName(String name) {
      getRootNode().setName(name);
      repaint();
   }

   public void paint(Graphics g) {
      super.paint(g);
      mSoftDragNDropHandler.paintDragging(g);
   }

   public ParameterNode getRootNode() {
      return (ParameterNode) m_tree.getRootNode();
   }

   public void addContentChangeListener(ChangeListener cl) {
      mListeners.add(cl);
   }

   public void removeContentChangeListener(ChangeListener cl) {
      mListeners.remove(cl);
   }

   /**
    * Sets if 'no parameters' (i.e. void) is allowed.
    */
   public void setAllowsEmpty(boolean val) {
      m_tree.getParameterEditorTreeModel().setAllowsRootNull(val);
   }

   /**
    * Gets if 'no parameters' is allowed.
    */
   public boolean getAllowsEmpty() {
      return m_tree.getParameterEditorTreeModel().getAllowsRootNull();
   }

   /**
    * Sets if the root can be edited.
    *
    * @param rootEditable
    */
   public void setRootTypeEditable(boolean rootEditable) {
      //m_tree.setRootTypeEditable(rootEditable);
   }

   public XiNode writeSchemaNode(ExpandedName name) {
      XiNode n = XiFactoryFactory.newInstance().createElement(name);
      if (!mIsNull) {
         ParameterNode root = (ParameterNode) m_tree.getRootNode();
         writeSchemaNode(root, n);
      }
      return n;
   }

   public void readSchemaNode(XiNode node) {
      mSetting = true;
      try {
         readSchemaNodeInternal(node);
         /*
         if (getImportRegistry()!=null && getNamespaceImporter()!=null) // hacky startup stuff...
         {
             m_tree.snapshot();
         }*/
      }
      finally {
         mSetting = false;
      }
   }

   private void readSchemaNodeInternal(XiNode node) {
      if (node == null) {
         setToNone();
         return;
      }
      String val = XiAttribute.getStringValue(node, "ref");
      if (val != null) {
         loadRootReference(val);
         return;
      }
      XiNode fc = XiChild.getFirstChild(node);
      if (fc == null) {
         setToNone();
         return;
      }
      ParameterNode n = buildTree(fc, m_namespaceContextRegistry);
      if (n == null) {
         setToNone();
         return;
      }
      m_tree.getEditableModel().setRootNull(false);
      m_tree.getEditableModel().setRoot(n);
      m_tree.clearSelection();
      m_tree.setSelectionRow(0);
      m_tree.repaint();
   }

   protected ParameterNode buildNode(SmSequenceType type, NamespaceContextRegistry ni) {
      SmSequenceType pt = type.prime(); // strip () and repeats.
      for (int i = 0; i < mAllCategories.length; i++) {
         ParameterNode r = mAllCategories[i].fromXType(pt, SmCardinality.EXACTLY_ONE, this, uiAgent, ni);
         if (r != null) {
            return r;
         }
      }
      ParameterNode r = new ParameterNode(this);
      r.setContentModelCategory(TypedValueCategory.INSTANCE);
      return r;
   }

   public TypeCategory[] getTypeCategories() {
      return m_typeCategories;
   }

   public void setTypeCategories(TypeCategory[] cat) {
      if (cat == null) {
         throw new NullPointerException();
      }
      for (int i = 0; i < cat.length; i++) {
         if (cat[i] == null) {
            throw new NullPointerException();
         }
      }
      if (cat.length == 0) {
         throw new IllegalArgumentException("Must have at least 1 category");
      }
      m_typeCategories = cat;
      m_details.setTypeCategory(cat);
   }

   private void writeSchemaNode(ParameterNode root, XiNode on) {
      if (m_tree.getEditableModel().isRootNull()) {
         return;
      }
      String str = root.getContentModelCategory().getAsRootReference(m_namespaceContextRegistry, root.getContentModelDetails());
      if (str != null) {
         XiAttribute.setStringValue(on, "ref", str);
         return;
      }
      XiFactory factory = XiFactoryFactory.newInstance();
      XiNode ret = toNode(factory, root, m_namespaceContextRegistry);
      on.appendChild(ret);
   }

   public String getSchemaText() {
      if (mIsNull) {
         return null;
      }
      return getSchemaText((ParameterNode) m_tree.getRootNode());
   }

   private void setToNone() {
      // set null...
      if (getAllowsEmpty()) {
         m_tree.getParameterEditorTreeModel().setRootNull(true);
//            setRootDisplayNull(true);
      }
      else {
         m_tree.getEditableModel().setRoot(new ParameterNode(this));
      }
      m_tree.reenableButtons();
      m_tree.repaint();
   }

   public void clear() {
      setToNone();
   }

   public void setSchemaText(String text) {
      if (text == null || text.length() == 0) {
         setToNone();
         return;
      }
      if (!text.startsWith("<")) { // schema text.
         int i1 = text.indexOf("}");
         if (i1 < 0) {
            // set null...
            m_tree.getParameterEditorTreeModel().setRootNull(true);
//                setRootDisplayNull(true);
            return;
         }
         loadRootReference(text);
         return;
      }
      /*
      try {
          ParameterNode n = (ParameterNode)m_tree.buildFromXML(null,text);
          mSetting = true;
          setRootDisplayNull(false);
          m_tree.setRootNode(n);
          mSetting = false;
      } catch (org.xml.sax.SAXException se) {
          se.printStackTrace(System.out);
      }*/
      m_tree.clearSelection();
      m_tree.setSelectionRow(0);
   }

   ParameterNode buildTree(XiNode node, NamespaceContextRegistry chainTo) {
      NamespaceContextRegistry ni = NamespaceManipulationUtils.createNamespaceImporter(node, chainTo);
      ParameterNode r = null;
      for (int i = 0; i < mAllCategories.length; i++) {
         ParameterNode pnode = mAllCategories[i].fromNode(node, this, ni);
         if (pnode != null) {
            r = pnode;
            break;
         }
      }
      if (r == null) {
         System.out.println("XXNull w/ " + node.getName());
      }
      return r;
      /*if (n.equals(ANY_NAME))
      {
          ParameterNode newnode = new ParameterNode(this);
          newnode.setContentModelCategory(WildcardCategory.INSTANCE);
          readAttributes(newnode,node,ni);
          return newnode;
      }
      if (n.equals(CHOICE_NAME))
      {
          ParameterNode newnode = new ParameterNode(this);
          newnode.setContentModelCategory(ChoiceCategory.INSTANCE);
          readAttributes(newnode,node,ni);
          readChildren(newnode,node,ni);
          return newnode;
      }
      if (n.equals(SEQUENCE_NAME))
      {
          ParameterNode newnode = new ParameterNode(this);
          newnode.setContentModelCategory(SequenceCategory.INSTANCE);
          readAttributes(newnode,node,ni);
          readChildren(newnode,node,ni);
          return newnode;
      }
      if (n.equals(GROUP_NAME))
      {
          ParameterNode newnode = new ParameterNode(this);
          newnode.setContentModelCategory(GroupRefCategory.INSTANCE);
          readGroupRefAttrs(newnode,node,ni);
          return newnode;
      }
      if (n.equals(ELEMENT_NAME))
      {
          ParameterNode newnode = new ParameterNode(this);
          newnode.setContentModelCategory(WildcardCategory.INSTANCE);
          readAttributes(newnode,node,ni);

          XiNode firstChild = XiChild.getFirstChild(node);
          if (firstChild!=null)
          {
              ExpandedName cn = firstChild.getName();
              if (cn.equals(COMPLEX_TYPE_NAME))
              {
                  newnode.setContentModelCategory(ElementSequenceCategory.INSTANCE);
                  XiNode ffc = XiChild.getFirstChild(firstChild);
                  // By default, a complex type shows a 1,1 sequence underneath it:
                  if (ffc!=null && ffc.getName().equals(SEQUENCE_NAME) && hasNeitherMinNorMaxOccurs(ffc)) //WCETODO add cardinality check.
                  {
                      readChildren(newnode,ffc,ni);
                  }
                  else if (ffc!=null)
                  {
                      newnode.setContentModelCategory(ElementSequenceCategory.INSTANCE);
                      readChildren(newnode,firstChild,ni);
                  }
              }
          }

          return newnode;
      }
      System.out.println("XXNull w/ " + n);
      return null; //??
      */
   }

   private void loadRootReference(String qname) {
      QName qn = new QName(qname);
      ParameterNode n = new ParameterNode(this);
      ContentModelCategory refCat = null;
      for (int i = 0; i < mAllCategories.length; i++) {
         if (mAllCategories[i].canHandleElementReferences()) {
            refCat = mAllCategories[i];
            break;
         }
      }
      n.setContentModelCategory(refCat);
      ExpandedName ename;
      try {
         ename = qn.getExpandedName(m_namespaceContextRegistry);
      }
      catch (Exception e) {
         ename = ExpandedName.makeName(qn.getLocalName());
      }
      n.setContentModelDetails(refCat.readRefDetails(ename));
      m_tree.getEditableModel().setRoot(n);
      m_tree.getEditableModel().setRootNull(false);
      m_tree.clearSelection();
      m_tree.setSelectionRow(0);
      m_tree.reenableButtons();
   }

   public final void setRootNode(ParameterNode root) {
      // leave this for now... when a data model is added, then we'll figure out this mSetting stuff.
      mSetting = true;
      m_tree.getEditableModel().setRoot(root);
      m_tree.clearSelection();
      m_tree.setSelectionRow(0);
      m_tree.reenableButtons();
      mSetting = false;
   }

   /**
    * Makes the root node visible or not. Default is true.
    *
    * @param visible true to make the node visible, false to make it invisible.
    */
   public void setRootVisible(boolean visible) {
      m_tree.setRootVisible(visible);
   }

   /**
    * Gets if root node visible or not.
    */
   public boolean isRootVisible() {
      return m_tree.isRootVisible();
   }

   public void setShowsRootHandles(boolean rootHandlesVisible) {
      m_tree.setShowsRootHandles(rootHandlesVisible);
   }

   public boolean getShowsRootHandles() {
      return m_tree.getShowsRootHandles();
   }

   /**
    * Sets which, if any, item the cardinality is restricted to.<br>
    * By default this is set to no restriction, indicated by a -1.
    *
    * @param cardinalityCode The code, from list {@link #CARDINALITY_REQUIRED}, etc., or -1 for all allowed.
    *                        By default, this is -1, for true.
    */
   public void setAllowsOnlyCardinality(int cardinalityCode) {
      m_details.setAllowsOnlyCardinality(cardinalityCode);
   }

   public int getAllowsOnlyCardinality() {
      return m_details.getAllowsOnlyCardinality();
   }

   /**
    * Sets the default starting name to use when the user adds a new element.
    *
    * @param newElementName The new name, cannot be null
    */
   public void setDefaultNewElementName(String newElementName) {
      if (newElementName == null) {
         throw new NullPointerException();
      }
      m_defaultNewElementName = newElementName;
   }

   public String getDefaultNewElementName() {
      return m_defaultNewElementName;
   }


   /**
    * When set, the top level children behaves as named variables, not elements.
    *
    * @param inMode
    */
   public void setNamedVariablesMode(boolean inMode) {
      m_namedVariablesMode = inMode;
   }

   public boolean getNamedVariablesMode() {
      return m_namedVariablesMode;
   }

   String getSchemaText(ParameterNode root) {
      SimpleNamespaceContextRegistry ni = new SimpleNamespaceContextRegistry();
      XiFactory factory = XiFactoryFactory.newInstance();
      XiNode n = toNode(factory, root, ni);
      NamespaceManipulationUtils.addAllNamespaceDeclarations(NamespaceManipulationUtils.createNamespaceImporter(n),
                                                             ni);
      return XiSerializer.serialize(n);
   }

   XiNode toNode(XiFactory factory, ParameterNode node, NamespaceContextRegistry mapper) {
      ContentModelCategory cat = node.getContentModelCategory();
      return cat.toNode(factory, node, mapper);
   }

   public NamespaceContextRegistry getNamespaceImporter() {
      return m_namespaceContextRegistry;
   }

   /**
    * Sets up the namespace context for this editor.<br>
    * This is <b>mandatory</b> before use.
    *
    * @param namespaceContextRegistry The namespace importer context.
    */
   public void setNamespaceImporter(NamespaceContextRegistry namespaceContextRegistry) {
      if (namespaceContextRegistry == null) {
         throw new NullPointerException("Null namespace importer");
      }
      m_namespaceContextRegistry = namespaceContextRegistry;
      ;
   }

   /**
    * Sets up the import context for this editor.<br>
    * This is <b>mandatory</b> before use.
    *
    * @param importRegistry The import registry in context.
    */
   public void setImportRegistry(ImportRegistry importRegistry) {
      if (importRegistry == null) {
         throw new NullPointerException("Null import registry");
      }
      m_importRegistry = importRegistry;
   }

   public ImportRegistry getImportRegistry() {
      return m_importRegistry;
   }

   /**
    * Call this to ensure preferences are saved.
    */
   public void close() {
      PreferenceUtils.writeInt(uiAgent, "paramEditor.dividerLocation", m_mainPanel.getDividerLocation());
   }

   public void addXsdContentModelChangeListener(XsdContentModelChangeListener cl) {
      m_xsdContentModelChangeListeners.add(cl);
   }

   public void removeXsdContentModelChangeListener(XsdContentModelChangeListener cl) {
      m_xsdContentModelChangeListeners.remove(cl);
   }

   public void removeAllXsdContentModelChangeListeners() {
      m_xsdContentModelChangeListeners.clear();
   }

   ParameterEditorTree getParameterTree() {
      return m_tree;
   }

   boolean hasContentModelChangeListeners() {
      return m_xsdContentModelChangeListeners.size() > 0;
   }

   XsdContentModelChangeListener getMasterChangeListener() {
      return new XsdContentModelChangeListener() {
         public void contentModelChange(XsdContentModelChangeEvent changeEvent) {
            for (int i = 0; i < m_xsdContentModelChangeListeners.size(); i++) {
               XsdContentModelChangeListener cl = m_xsdContentModelChangeListeners.get(i);

               cl.contentModelChange(changeEvent);
            }
         }
      };
   }

   public void setAllowsRootSequence(boolean allowsRootSeq) {
      m_allowsRootSequence = allowsRootSeq;
   }

   public boolean getAllowsRootSequence() {
      return m_allowsRootSequence;
   }

   /*
   private void rebuildDetailsCats()
   {
       ArrayList cats = new ArrayList();
       for (int i=0;i<mAllCategories.length;i++)
       {
           ContentModelCategory tc = mAllCategories[i];
           if (!(tc instanceof SequenceCategory || tc instanceof ChoiceCategory)) // not the most elegant test, but it works.
           {
               cats.add(tc);
           }
           else
           {
               if (m_allowsExternalReferences)
               {
                   cats.add(tc);
               }
           }
       }
       m_details.setCategories((ContentModelCategory[])cats.toArray(new ContentModelCategory[0]),m_typeCategories);
   }*/

   public void delete() {
      if (m_tree.hasFocus()) {
         m_tree.delete();
      }
   }

   public void cut() {
      if (m_tree.hasFocus()) {
         m_tree.cut();
      }
   }

   public void copy() {
      if (m_tree.hasFocus()) {
         m_tree.copy();
      }
   }

   public void paste() {
      if (m_tree.hasFocus()) {
         m_tree.paste();
      }
   }
}
