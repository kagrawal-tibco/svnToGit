package com.tibco.cep.studio.ui.advance.event.payload;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.nsutils.NamespaceManipulationUtils;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.utils.DocumentNameValidator;
import com.tibco.cep.studio.ui.forms.advancedEventPayloadEditor.PayloadTreeModelChild;
import com.tibco.cep.studio.ui.forms.advancedEventPayloadEditor.PayloadTreeModelParent;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.data.primitive.QName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiFactoryFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiAttribute;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;


public class ParameterPayloadEditor {
	 private TypeCategory[] m_typeCategories;
	 private ParameterEditorTree m_tree;
	 private ParameterPayloadDetails m_details;
	 private ContentModelCategory[] mAllCategories;
	 private UIAgent uiAgent;
	 private boolean m_namedVariablesMode;
	 private NamespaceContextRegistry m_namespaceContextRegistry;
	 private ImportRegistry m_importRegistry;
	 private boolean mSetting;
	 private boolean mIsNull = false;
	 List<Object> modelList=new ArrayList<Object>();
	 
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

	
	
	
	public ParameterPayloadEditor(UIAgent uiAgent, ContentModelCategory[] cmc,
			TypeCategory[] typeCategories) {

	/*	m_mainPanel = new MasterDetailsTree();
		add(m_mainPanel, BorderLayout.CENTER);
		this.uiAgent = uiAgent;

		int loc = PreferenceUtils.readInt(uiAgent,
				"paramEditor.dividerLocation", 180);
		m_mainPanel.setDividerLocation(loc);
*/
		ArrayList<ContentModelCategory> allCats = new ArrayList<ContentModelCategory>();
		mAllCategories = cmc;
		for (int i = 0; i < cmc.length; i++) {
			allCats.add(cmc[i]);
		}
		mAllCategories = allCats.toArray(new ContentModelCategory[0]);
		m_typeCategories = typeCategories;
//
		m_details = new ParameterPayloadDetails(this, uiAgent, mAllCategories,m_typeCategories);
		m_tree = new ParameterEditorTree(uiAgent, this);
		m_tree.setName("ParameterTree"); // (Internal label)
//
//		m_mainPanel.setDetails(m_details);
		m_tree.reenableButtons();
//		mSoftDragNDropHandler = new SoftDragNDropHandler(this,
//				new JComponent[] { m_tree });
//
//		// auto-select the first row:
		m_tree.setSelectionRow(0);
	}
	
	public ParameterPayloadEditor(UIAgent uiAgent) {
		this(uiAgent, BUILT_IN_TYPES);
	}

	public ParameterPayloadEditor(UIAgent uiAgent, TypeCategory[] typeCategories) {
		this(uiAgent, BUILT_IN, typeCategories);
	}

	   /**
	    * Gets the name validator to be used in validating the names of entries.
	    */
	   public DocumentNameValidator getDocumentNameValidator() {
	      return m_details.getDocumentNameValidator();
	   }

	
	
	   public TypeCategory getDefaultTypeCategory() {
	      return m_typeCategories[0]; // must have at least 1 category...
	   }
	   
	   XiNode toNode(XiFactory factory, Object node, NamespaceContextRegistry mapper) {
		if(node instanceof PayloadTreeModelChild){   
		      com.tibco.cep.studio.ui.advance.event.payload.ContentModelCategory cat = ((PayloadTreeModelChild)node).getContentModelCategory();
		      return cat.toNode(factory, node, mapper);
			//return null;
		}
		if(node instanceof PayloadTreeModelParent){   
		      com.tibco.cep.studio.ui.advance.event.payload.ContentModelCategory cat = ((PayloadTreeModelParent)node).getContentModelCategory();
		      return cat.toNode(factory, node, mapper);
		}
		return null;
       }
	   
	   Object buildTree(XiNode node, NamespaceContextRegistry chainTo,Object parentNode) {
		   if(parentNode==null){
		      NamespaceContextRegistry ni = NamespaceManipulationUtils.createNamespaceImporter(node, chainTo);
		      PayloadTreeModelParent r = null;
		      for (int i = 0; i < mAllCategories.length; i++) {
		    	  PayloadTreeModelParent pnode = (PayloadTreeModelParent) mAllCategories[i].fromNode(node, this, ni,parentNode);
		         if (pnode != null) {
		            r = pnode;
		            break;
		         }
		      }
		      if (r == null) {
		         System.out.println("XXNull w/ " + node.getName());
		      }
		      return r;
		   }
		   else{
			   NamespaceContextRegistry ni = NamespaceManipulationUtils.createNamespaceImporter(node, chainTo);
			      PayloadTreeModelChild r = null;
			      for (int i = 0; i < mAllCategories.length; i++) {
			    	  PayloadTreeModelChild pnode = (PayloadTreeModelChild) mAllCategories[i].fromNode(node, this, ni,parentNode);
			         if (pnode != null) {
			            r = pnode;
			            break;
			         }
			      }
			      if (r == null) {
			         System.out.println("XXNull w/ " + node.getName());
			      }
			      return r;
		   }
		
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
	   
	protected PayloadTreeModelChild buildNode(SmSequenceType type,
			NamespaceContextRegistry ni) {
		SmSequenceType pt = type.prime(); // strip () and repeats.
		for (int i = 0; i < mAllCategories.length; i++) {
			PayloadTreeModelChild r = (PayloadTreeModelChild) mAllCategories[i].fromXType(pt,SmCardinality.EXACTLY_ONE, this, uiAgent, ni);
			if (r != null) {
				return r;
			}
		}
		PayloadTreeModelChild r = new PayloadTreeModelChild(null, null, null, null, this);
		r.setContentModelCategory(TypedValueCategory.INSTANCE);
		return r;
	}

	public boolean getNamedVariablesMode() {
		return m_namedVariablesMode;
	}

	public TypeCategory[] getTypeCategories() {
		return m_typeCategories;
	}

	/**
	 * Sets up the namespace context for this editor.<br>
	 * This is <b>mandatory</b> before use.
	 *
	 * @param namespaceContextRegistry
	 *            The namespace importer context.
	 */
	public void setNamespaceImporter(
			NamespaceContextRegistry namespaceContextRegistry) {
		if (namespaceContextRegistry == null) {
			throw new NullPointerException("Null namespace importer");
		}
		m_namespaceContextRegistry = namespaceContextRegistry;
		;
	}
	
	public List<Object> readSchemaNode(XiNode node) {
		mSetting = true;
		try {
			return readSchemaNodeInternal(node);
			/*
			 * if (getImportRegistry()!=null && getNamespaceImporter()!=null) //
			 * hacky startup stuff... { m_tree.snapshot(); }
			 */
		} finally {
			mSetting = false;
		}
	}

	private List<Object> readSchemaNodeInternal(XiNode node) {
		if (node == null) {
			setToNone();
			return null;
		}
		String val = XiAttribute.getStringValue(node, "ref");
		if (val != null) {
			loadRootReference(val);
			return null;
		}
		XiNode fc = XiChild.getFirstChild(node);
		if (fc == null) {
			setToNone();
			return null;
		}
		Object n = buildTree(fc, m_namespaceContextRegistry,null);
		if (n == null) {
			setToNone();
			return null;
		}
		
		modelList.add(n);
		/*m_tree.getEditableModel().setRootNull(false);
		m_tree.getEditableModel().setRoot(n);
		m_tree.clearSelection();
		m_tree.setSelectionRow(0);
		m_tree.repaint();*/
		return modelList;
	}
	
	public NamespaceContextRegistry getNamespaceImporter() {
	    return m_namespaceContextRegistry;
	}


	public XiNode writeSchemaNode(ExpandedName name) {
		XiNode n = XiFactoryFactory.newInstance().createElement(name);
		if (!mIsNull) {
			ParameterPayloadNode root = (ParameterPayloadNode) m_tree
					.getRootNode();
			writeSchemaNode(root, n);
		}
		return n;
	}
	   
	private void setToNone() {
		// set null...
		if (getAllowsEmpty()) {
			m_tree.getParameterEditorTreeModel().setRootNull(true);
			// setRootDisplayNull(true);
		} else {
			m_tree.getEditableModel().setRoot(new PayloadTreeModelParent(null, null, null, null, this));
		}
		m_tree.reenableButtons();
		m_tree.repaint();
	}

	/**
	 * Gets if 'no parameters' is allowed.
	 */
	public boolean getAllowsEmpty() {
		return m_tree.getParameterEditorTreeModel().getAllowsRootNull();
	}
	
	private void loadRootReference(String qname) {
		QName qn = new QName(qname);
		PayloadTreeModelParent n = new PayloadTreeModelParent(null,null,null,null,this);
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
		} catch (Exception e) {
			ename = ExpandedName.makeName(qn.getLocalName());
		}
		n.setContentModelDetails(refCat.readRefDetails(ename));
		m_tree.getEditableModel().setRoot(n);
		m_tree.getEditableModel().setRootNull(false);
		m_tree.clearSelection();
		m_tree.setSelectionRow(0);
		m_tree.reenableButtons();
	}

	private void writeSchemaNode(Object root, XiNode on) {
		if (m_tree.getEditableModel().isRootNull()) {
			return;
		}
		String str=null;
		if(root instanceof PayloadTreeModelChild)
			str = ((PayloadTreeModelChild)root).getContentModelCategory().getAsRootReference(m_namespaceContextRegistry, ((PayloadTreeModelChild)root).getContentModelDetails());
		if(root instanceof PayloadTreeModelParent)
			str = ((PayloadTreeModelParent)root).getContentModelCategory().getAsRootReference(m_namespaceContextRegistry, ((PayloadTreeModelParent)root).getContentModelDetails());
		
		
		if (str != null) {
			XiAttribute.setStringValue(on, "ref", str);
			return;
		}
		XiFactory factory = XiFactoryFactory.newInstance();
		XiNode ret = toNode(factory, root, m_namespaceContextRegistry);
		on.appendChild(ret);
	}


	/**
	 * Sets up the import context for this editor.<br>
	 * This is <b>mandatory</b> before use.
	 *
	 * @param importRegistry
	 *            The import registry in context.
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

}
