package com.tibco.cep.studio.ui.forms.advancedEventPayloadEditor;

import java.util.LinkedList;

import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.param.ParameterEditor;
import com.tibco.cep.studio.mapper.ui.data.utils.DocumentNameValidator;
import com.tibco.cep.studio.mapper.ui.data.utils.NameValidatingDocument;
import com.tibco.cep.studio.ui.advance.event.payload.ContentModelCategory;
import com.tibco.cep.studio.ui.advance.event.payload.ElementSequenceCategory;
import com.tibco.cep.studio.ui.advance.event.payload.ParameterPayloadEditor;
import com.tibco.cep.studio.ui.advance.event.payload.TypeCategory;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmComponentProviderEx;
import com.tibco.xml.schema.SmParticle;
import com.tibco.xml.schema.SmSequenceType;

public class PayloadTreeModelChild {

	private String Name;
	private String Value;
	private PayloadTreeModelParent parent;
	private AdvancedEventPayloadTree extendedTreeViewer;
//	private ExtendedPropertiesMap map;
	private LinkedList<String> list=new LinkedList<String>();
	
	public PayloadTreeModelChild(String name, String value,
			PayloadTreeModelParent parent, AdvancedEventPayloadTree invokingclass,ParameterPayloadEditor editor) {

		Name = name;
		Value = value;
		this.setParent(parent);
//		map = new ExtendedPropertiesMap();
//		map.put(this.Name, this.Value);
		list.add(this.Name);
		this.extendedTreeViewer = invokingclass;
		mEditor = editor;
	}

	public PayloadTreeModelChild() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getValue() {
		return Value;
	}

	public void setValue(String value) {
		Value = value;
	}

    public PayloadTreeModelParent getParent() {
		return parent;
	}

	public void setParent(PayloadTreeModelParent parent) {
		this.parent = parent;
	}

/*	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + getOuterType().hashCode();
		result = prime * result + ((Name == null) ? 0 : Name.hashCode());
		result = prime * result + ((Value == null) ? 0 : Value.hashCode());
		result = prime * result
				+ ((getParent() == null) ? 0 : getParent().hashCode());
		return result;
	}
*/
/*	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PropTreeModelChild other = (PropTreeModelChild) obj;
		
		 * if (!getOuterType().equals(other.getOuterType())) return false; if
		 * (Name == null) { if (other.Name != null) return false; } else if
		 * (!Name.equals(other.Name)) return false; if (Value == null) { if
		 * (other.Value != null) return false; } else if
		 * (!Value.equals(other.Value)) return false; if (getParent() == null) {
		 * if (other.getParent() != null) return false; } else if
		 * (!getParent().equals(other.getParent())) return false;
		 return true;
	}
*/
	private AdvancedEventPayloadTree getOuterType() {
		return this.extendedTreeViewer;
	}

	public AdvancedEventPayloadTree getExtendedTreeViewer() {
		return extendedTreeViewer;
	}

	public void setExtendedTreeViewer(AdvancedEventPayloadTree extendedTreeViewer) {
		this.extendedTreeViewer = extendedTreeViewer;
	}
	
	///////////////////////////////////////////////
	


	public PayloadTreeModelParent m_parent;
	//private List<PayloadTreeModelChild> m_children;
	private PayloadTreeModelChild[] m_children;


	private ContentModelCategory mCategory = ElementSequenceCategory.INSTANCE;
	TypeCategory m_typeCategory = null;
	private int mMin = 1;
	private int mMax = 1;
	private AdvancedEventPayloadTree payloadTree;
	private int mFixedCardinality = -1;
	private ParameterPayloadEditor mEditor;
	String m_name = "root";
	private Object mContentModelDetails = null;
	private boolean mFixedName = false;
	private String mDefaultVal;

	
	
	 public int getChildCount() {
	      // Do not do this optimization: if (isLeaf()) {
	      //    return 0;
	      //}.. makes it tough for simpl
		  if (m_children == null) {
	         /* Not true for parameter editor right now, fix...if (mModel==null) {
	             throw new NullPointerException("Model not set on " + this);
	         }*/
	         m_children = buildChildren();
	       
	         for (int i = 0; i < m_children.length; i++) {
	 //           m_children[i].m_model = m_model;
	  //          m_child[i].m_parent = this;
	         }
	      }
		 
	      return m_children.length;
	   }

	   public PayloadTreeModelChild getChild(int index) {
	      if (m_children == null) {
	         getChildCount(); // builds 'em.
	      }
	      return (PayloadTreeModelChild) (m_children)[index];
	   }

	public PayloadTreeModelChild createNewChild() {
		PayloadTreeModelChild n = mCategory.createNewChild(mEditor);
		int ac = getAllowsOnlyCardinality();
		if (ac != -1) {
			n.setCardinalityByCode(ac);
		}
		/*
		 * JTB TEMPORARY COMMENT OUT // WCETODO HACKY, fix, make api somewhere,
		 * somehow. if (mEditor instanceof XsltParamTreeEditor) {
		 * n.setContentModelCategory(ElementRefCategory.INSTANCE); }
		 */
		// n.mCategory = ElementTypeRefCategory.INSTANCE;
		// n.m_typeCategory = mEditor.getDefaultTypeCategory();
		n.m_name = makeValidName("param");
		// getTree().setSelectionPath(new TreePath(n));
		return n;
	}

	/**
	 * Sets min and max based on the cardinality code given in
	 * {@link ParameterEditor}.
	 * 
	 * @param code
	 *            The integer code.
	 */
	public void setCardinalityByCode(int code) {
		switch (code) {
		case ParameterPayloadEditor.CARDINALITY_REQUIRED:
			setCardinality(1, 1);
			break;
		case ParameterPayloadEditor.CARDINALITY_OPTIONAL:
			setCardinality(0, 1);
			break;
		case ParameterPayloadEditor.CARDINALITY_REPEATING:
			setCardinality(0, SmParticle.UNBOUNDED);
			break;
		default:
			setCardinality(1, SmParticle.UNBOUNDED);
			break;
		}
	}

	public void setCardinality(int min, int max) {
		setMin(min);
		setMax(max);
	}

	public int getMin() {
		return mMin;
	}

	public void setMin(int min) {
		if (min != mMin) {
			mMin = min;
			contentChanged();
		}
	}

	public int getMax() {
		return mMax;
	}

	public void setMax(int max) {
		if (mMax != max) {
			mMax = max;
			contentChanged();
		}
	}

	private void contentChanged() {
	//	getTree().setContentandLabelProvider();
		// Can change the content of the tree.

	}

	private AdvancedEventPayloadTree getTree() {
		return payloadTree;
	}

	/**
	 * Sets the fixed cardinality code, or -1 for not fixed.
	 */
	public int getAllowsOnlyCardinality() {
		return mFixedCardinality;
	}

	private String makeValidName(String name) {
		DocumentNameValidator dnv = mEditor.getDocumentNameValidator();
		if (dnv == null) {
			return name;
		}
		return NameValidatingDocument.makeValidName(name, dnv);
	}

	public void setContentModelCategory(ContentModelCategory cat) {
		if (cat == mCategory) {
			return;
		}
		mCategory = cat;
	}

	public Object getContentModelDetails() {
		return mContentModelDetails;
	}

	public void setContentModelDetailsNoUpdate(Object details) {
		mContentModelDetails = details;
	}

	public void setContentModelDetails(Object details) {
		if (mContentModelDetails == details) {
			return;
		}
		if (mContentModelDetails != null && details != null
				&& mContentModelDetails.equals(details)) {
			return;
		}
		mContentModelDetails = details;
		contentChanged();
	}

/*	public final int getChildCount() {
		// return child count based on swt tree. currently returning 1.
		return 1;
	}
*/
	public ParameterPayloadEditor getEditor() {
		return mEditor;
	}

/*	public final SimpleTreeNode getChild(int index) {

		// return child of given index. currently returning null;

		return  super.getChild(index);
	}
*/
	public ContentModelCategory getContentModelCategory() {
		return mCategory;
	}

	/**
	 * Used by parse.
	 */
	public void addNode(PayloadTreeModelChild node) {
		if (isLeaf()) {
			return;
		}
		addedAt(getChildCount(), node);
	}

	protected final void addedAt(int index, PayloadTreeModelChild node) {
		/*
		 * int cc = getChildCount(); // make sure mChildren not null.
		 * SimpleTreeNode[] c = new SimpleTreeNode[cc + 1]; int j = 0; for (int
		 * i = 0; i < c.length; i++) { if (i != index) { c[i] = m_children[j];
		 * j++; } else { c[i] = node; } } node.m_parent = this; if (m_model !=
		 * null) { m_model.copyModelTo(node); } m_children = c;
		 */

		// Add node to the given tree at given index. for now keeping blank
	}

	public boolean isLeaf() {
		return mCategory.isLeaf();
	}

	public SmSequenceType[] computeChildrenXType(NamespaceContextRegistry ni,
			UIAgent uiAgent, SmComponentProviderEx smCompProvider) {
		int cc = getChildCount();
		SmSequenceType[] r = new SmSequenceType[cc];
		for (int i = 0; i < cc; i++) {
			r[i] = ((PayloadTreeModelChild) getChild(i)).computeXType(ni,
					uiAgent, smCompProvider);
		}
		return r;
	}

	public SmSequenceType computeXType(NamespaceContextRegistry ni,
			UIAgent uiAgent, SmComponentProviderEx smCompProvider) {
		return getContentModelCategory().computeXType(this, ni, uiAgent,
				smCompProvider);
	}

	public PayloadTreeModelChild getChildParameter(int index) {
		return (PayloadTreeModelChild) getChild(index);
	}

	public String getDisplayName() {
		if (mFixedName
				|| (mEditor.getNamedVariablesMode() && isFirstLevelNode())) {
			return m_name;
		}
		return mCategory.getDisplayName(this);
	}

	boolean isFirstLevelNode() {
		return getParent() != null && getParent().getParent() == null;
	}

	public final PayloadTreeModelChild getNodeParent() {
		return this;
	}

	public String getNodeName() {
		return m_name;
	}
	
	/**
	 * Externally added call, should not be here...
	 * 
	 * @param name
	 */
	public void setNodeName(String name) {
		if (m_name == null || !m_name.equals(name)) {
			m_name = name;
			contentChanged();
		}
	}

	public void setTypeCategory(TypeCategory category) {
		m_typeCategory = category;
		if (getTree() != null) {
	//		getTree().repaint();
	//		getTree().setSelectionPath(getTreePath());
		}
		contentChanged();
	}

	public TypeCategory getTypeCategory() {
		return m_typeCategory;
	}

	public String getDefaultValue() {
		return (mDefaultVal);
	}

	public void setDefaultValue(String val) {
		// check for no change:
		if (mDefaultVal == val) {
			return;
		}
		if (mDefaultVal != null && mDefaultVal.equals(val)) {
			return;
		}
		mDefaultVal = val;
		contentChanged();
	}

	public PayloadTreeModelChild[] buildChildren() {
		// TODO Auto-generated method stub
	/*	List<PayloadTreeModelChild> childList=new ArrayList<PayloadTreeModelChild>();
		childList.add(new PayloadTreeModelChild());
		return childList;*/
		 return new PayloadTreeModelChild[0];
	}



}
