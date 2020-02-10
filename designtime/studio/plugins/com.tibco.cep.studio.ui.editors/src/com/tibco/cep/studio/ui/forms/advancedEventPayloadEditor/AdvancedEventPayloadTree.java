package com.tibco.cep.studio.ui.forms.advancedEventPayloadEditor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import com.tibco.be.util.wsdl.SOAPEventPayloadBuilder;
import com.tibco.cep.designtime.core.model.ModelFactory;
import com.tibco.cep.designtime.core.model.event.EventFactory;
import com.tibco.cep.designtime.core.model.event.EventPackage;
import com.tibco.cep.designtime.core.model.event.ImportRegistryEntry;
import com.tibco.cep.designtime.core.model.event.NamespaceEntry;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.nsutils.SimpleNamespaceContextRegistry;
import com.tibco.cep.mapper.xml.xdata.NamespaceMapper;
import com.tibco.cep.studio.core.adapters.EventAdapter;
import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.advance.event.payload.ContentModelCategory;
import com.tibco.cep.studio.ui.advance.event.payload.EditableTreeModel;
import com.tibco.cep.studio.ui.advance.event.payload.ElementSequenceCategory;
import com.tibco.cep.studio.ui.advance.event.payload.ElementTypeRefCategory;
import com.tibco.cep.studio.ui.advance.event.payload.ParameterPayloadEditor;
import com.tibco.cep.studio.ui.advance.event.payload.ParameterPayloadNode;
import com.tibco.cep.studio.ui.editors.events.NSUtilitiesConverter;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.forms.components.AbstractHeirachicalViewer;
import com.tibco.cep.studio.ui.util.TreeTableAutoResizeLayout;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.data.primitive.PrefixToNamespaceResolver.PrefixNotFoundException;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiFactoryFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiAttribute;
import com.tibco.xml.datamodel.helpers.XiSerializer;
import com.tibco.xml.datamodel.nodes.Element;

/**
 * @author sshekhar
 * 
 */
public class AdvancedEventPayloadTree extends AbstractHeirachicalViewer {

	private PayloadEditorToolBarProvider toolBarProvider;
	protected List<Object> prList = new ArrayList<Object>();
	private AdvancedEventPayloadTree invokingObj;
	private LinkedList<String> list=new LinkedList<String>();
	private SimpleEvent event1;
	private EditableTreeModel treeModel;
	private static String NAME = Messages.getString("extPropTree_Name");
	private static final String[] PROPS = { NAME};
	private static String PLUGIN_ID ="com.tibco.cep.studio.mapper";
	private NamespaceContextRegistry m_namespaceContextRegistry;
	private ParameterPayloadEditor paramEditor;
	//private ParameterPayloadNode pnode=null;

	public AdvancedEventPayloadTree(AbstractSaveableEntityEditorPart editor) {
		
		super(editor);
		event1=(SimpleEvent) editor.getEntity();
		setInvokingObj(this);
	}
	
	 public AdvancedEventPayloadTree(AbstractSaveableEntityEditorPart editor,
			ParameterPayloadEditor paramEditor) {
			super(editor);
			this.paramEditor=paramEditor;
			event1=(SimpleEvent) editor.getEntity();
			setInvokingObj(this);
			m_namespaceContextRegistry = new SimpleNamespaceContextRegistry();
	}

	enum Images {
  		rootImage("rootImg","ui/src/com/tibco/ui/data/resources/iconClass16.gif"),
  		childStrImage("chldStrImg","ui/src/com/tibco/ui/data/resources/iconString16.gif");

  		private String name;
		private String imageIcon;

		private Images(String name, String path) {
			this.name = name;
			this.imageIcon = path;
		}

		public String getName() {
			return name;
		}

		public String getImageIcon() {
			return imageIcon;
		}

	  	}
	@Override
	public void createHierarchicalTree(Composite parent) {
		super.createHierarchicalTree(parent);
		// m_treeViewer.getTree().addSelectionListener(new SelectionAdapter() {
		// @Override
		// public void widgetSelected(SelectionEvent e) {
		// if(m_treeViewer.getTree().getItemCount()>0) {
		// // toolBarProvider.getDelItem().setEnabled(true);
		// }
		// }
		//
		// });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.studio.ui.forms.components.AbstractHeirachicalViewer#
	 * createToolBar()
	 */
	@Override
	protected void createToolBar() {
		toolBarProvider = new PayloadEditorToolBarProvider();
		toolBarProvider.createPayloadToolBar(comp);

		toolBarProvider.getPayloadAddTopOrChildItem().addSelectionListener(new ToolbarAddSelectionAdapter());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.studio.ui.forms.components.AbstractHeirachicalViewer#
	 * setContentandLabelProvider()
	 */
	@Override
	protected void setContentandLabelProvider() {
		m_treeViewer.setContentProvider(this);
		m_treeViewer.setLabelProvider(this);
		m_treeViewer.setCellModifier(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.studio.ui.forms.components.AbstractHeirachicalViewer#
	 * createTreeColumns(org.eclipse.swt.widgets.Tree,
	 * org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createTreeColumns(Tree tree, Composite treeComp) {
		
		 TreeColumn column1 = new TreeColumn(tree, SWT.LEFT);
	        tree.setLinesVisible(true);
	        column1.setAlignment(SWT.LEFT);
	        column1.setText(NAME);
	        
	        TreeTableAutoResizeLayout tcl = new TreeTableAutoResizeLayout(m_treeViewer.getTree());
	        tcl.addColumnData(column1, new ColumnWeightData(1));
	        treeComp.setLayout(tcl);
	        
	        tree.setHeaderVisible(true);
	        m_treeViewer.setColumnProperties(PROPS);
	        CellEditor[] editors = new CellEditor[2];
	        editors[0] = new TextCellEditor(m_treeViewer.getTree());
	        editors[1] = new TextCellEditor(m_treeViewer.getTree());
	        m_treeViewer.setCellEditors(editors);


	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITreeContentProvider#getElements(java.lang.
	 * Object)
	 */
	@Override
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.
	 * Object)
	 */
	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement == null)
			return new Object[0];
		if (parentElement instanceof List)
			return ((List<?>) parentElement).toArray();
		if (parentElement instanceof PayloadTreeModelParent)
			return ((PayloadTreeModelParent) parentElement).getObjList().toArray();

		return new Object[0];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object
	 * )
	 */
	@Override
	public Object getParent(Object element) {
		if (element == null)
			return null;
		if (element instanceof PayloadTreeModelParent)
			return ((PayloadTreeModelParent) element).getParent();

		if (element instanceof PayloadTreeModelChild) {
			return ((PayloadTreeModelChild) element).getParent();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.
	 * Object)
	 */
	@Override
	public boolean hasChildren(Object element) {
		
		if (element == null)
			return false;
		if (element instanceof List)
			return ((List<?>) element).size() > 0;
		if (element instanceof PayloadTreeModelParent) {
			if (((PayloadTreeModelParent) element).getObjList() != null)
				return (((PayloadTreeModelParent) element).getObjList()).size() > 0;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface
	 * .viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang
	 * .Object, int)
	 */
	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		switch (columnIndex) {
		case 0:
			ImageDescriptor imgDesc = null ;
			if (element instanceof PayloadTreeModelParent) {
				imgDesc = StudioUIPlugin.getImageDescriptor(PLUGIN_ID ,Images.rootImage.getImageIcon());
				return imgDesc.createImage();
			}
			if (element instanceof PayloadTreeModelChild) {
				imgDesc = StudioUIPlugin.getImageDescriptor(PLUGIN_ID ,Images.childStrImage.getImageIcon());
				return imgDesc.createImage();
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang
	 * .Object, int)
	 */
	@Override
	public String getColumnText(Object element, int columnIndex) {
		switch (columnIndex) {
		case 0:
			if (element instanceof PayloadTreeModelParent) {

				return ((PayloadTreeModelParent) element).getName();
			}
			if (element instanceof PayloadTreeModelChild) {
				return ((PayloadTreeModelChild) element).getName();
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.
	 * jface.viewers.ILabelProviderListener)
	 */
	@Override
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang
	 * .Object, java.lang.String)
	 */
	@Override
	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse
	 * .jface.viewers.ILabelProviderListener)
	 */
	@Override
	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITableColorProvider#getForeground(java.lang
	 * .Object, int)
	 */
	@Override
	public Color getForeground(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITableColorProvider#getBackground(java.lang
	 * .Object, int)
	 */
	@Override
	public Color getBackground(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITableFontProvider#getFont(java.lang.Object,
	 * int)
	 */
	@Override
	public Font getFont(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ICellModifier#canModify(java.lang.Object,
	 * java.lang.String)
	 */
	@Override
	public boolean canModify(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ICellModifier#getValue(java.lang.Object,
	 * java.lang.String)
	 */
	@Override
	public Object getValue(Object element, String property) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ICellModifier#modify(java.lang.Object,
	 * java.lang.String, java.lang.Object)
	 */
	@Override
	public void modify(Object element, String property, Object value) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.studio.ui.forms.components.AbstractHeirachicalViewer#setDirty
	 * (boolean)
	 */
	@Override
	protected void setDirty(boolean tf) {
		// TODO Auto-generated method stub

	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.studio.ui.forms.components.AbstractHeirachicalViewer#getDepth
	 * (java.lang.Object)
	 */
	@Override
	protected int getDepth(Object Parentobj) {
		// TODO Auto-generated method stub
		return 0;
	}

	class ToolbarAddSelectionAdapter extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent event) {
			StructuredSelection strSel = (StructuredSelection) m_treeViewer.getSelection();
			Object obj = (Object) strSel.getFirstElement();
			
			if (obj == null) {
				PayloadTreeModelParent prnt = new PayloadTreeModelParent("root", null, null, invokingObj,paramEditor);
				prnt.setObjList(new ArrayList<Object>());
				XiNode node = writeSchemaNode(ExpandedName.makeName("payload"),prnt);
				if (event1.isSoapEvent()) {
					boolean valid = SOAPEventPayloadBuilder.validatePayload((Element) node);
				}
				String schemaText = XiSerializer.serialize(node);
				if (node.getFirstChild() == null && schemaText != null) {
					if (schemaText.contains("<payload/>")) {
						// the payload is empty, set it to null (for instance,
						// to allow other events to inherit from this event
						// is this the best way to check for an empty payload?
						schemaText = null;
					}
				}
				CompoundCommand command = new CompoundCommand();
				command.append(new SetCommand(getEditingDomain(), event1,EventPackage.eINSTANCE.getEvent_PayloadString(),schemaText));
	                
	            try {
					// update namespaces
					event1.getNamespaceEntries().clear(); // not sure we want to
															// clear, or just
															// check for
															// duplicates
					Iterator prefixes = getNamespaceImporter().getPrefixes();
					while (prefixes.hasNext()) {
						String prefix = (String) prefixes.next();
						String namespace = getNamespaceImporter().getNamespaceURIForPrefix(prefix);
						NamespaceEntry entry = EventFactory.eINSTANCE.createNamespaceEntry();
						entry.setPrefix(prefix);
						entry.setNamespace(namespace);
						command.append(new AddCommand(getEditingDomain(),event1, EventPackage.eINSTANCE.getEvent_NamespaceEntries(), entry));
					}
    	
	                	// update imports
	                	event1.getRegistryImportEntries().clear(); // not sure we want to clear, or just check for duplicates
	            		com.tibco.cep.mapper.xml.nsutils.ImportRegistryEntry[] imports =getImportRegistry().getImports();
	            		for (com.tibco.cep.mapper.xml.nsutils.ImportRegistryEntry importEntry : imports) {
	                		ImportRegistryEntry registryEntry = EventFactory.eINSTANCE.createImportRegistryEntry();
	                		registryEntry.setNamespace(importEntry.getNamespaceURI());
	                		registryEntry.setLocation(importEntry.getLocation());
	                		command.append(new AddCommand(getEditingDomain(), event1, EventPackage.eINSTANCE.getEvent_RegistryImportEntries(), registryEntry));
						}

	                } catch (PrefixNotFoundException e1) {
	                	e1.printStackTrace();
	                }
	                EditorUtils.executeCommand(editor, command);
			
			//	prnt.setPnode(new ParameterPayloadNode(paramEditor));
				prList.add(prnt);
//				m_treeViewer.getTree().setSelection(m_treeViewer.getTree().getTopItem());
			}
			if (obj instanceof PayloadTreeModelChild) {
				PayloadTreeModelChild objChld = (PayloadTreeModelChild) obj;
				if (objChld.getParent() != null) {
					PayloadTreeModelChild chld = new PayloadTreeModelChild(addParamRow(objChld.getParent().getList()), "",objChld.getParent(), invokingObj,paramEditor);
						List<Object> tempList = objChld.getParent().getObjList();
						tempList.add(chld);
				} else {
					PayloadTreeModelChild chld = new PayloadTreeModelChild(addParamRow(getList()), "", null, invokingObj,paramEditor);
					getList().add(chld.getName());
					prList.add(chld);
				}
			}
			if (obj instanceof PayloadTreeModelParent) {
				
			/*	ParameterPayloadNode pNode=((PayloadTreeModelParent) obj).getPnode();
				pNode.createNewChild();
			*/	
				
				PayloadTreeModelParent parent = (PayloadTreeModelParent) obj;
				if(parent.getmEditor()==null){
					parent.setmEditor(paramEditor);
				}
					
					
				List<Object> tempList=new ArrayList<Object>();
				PayloadTreeModelChild chld;
				chld = new PayloadTreeModelChild(addParamRow(parent.getList()),"", parent, invokingObj,paramEditor);
				chld.setContentModelCategory(ElementTypeRefCategory.INSTANCE);
				chld.m_typeCategory=paramEditor.getDefaultTypeCategory();
			//	n.m_typeCategory = mEditor.getDefaultTypeCategory();
				
				if(parent.getObjList()==null){
					tempList=new ArrayList<Object>();
				}
				else{
					tempList = parent.getObjList();
				}
     			tempList.add(chld);
				parent.setObjList(tempList);
				
				 XiNode node = writeSchemaNode(ExpandedName.makeName("payload"),parent);
				  if(event1.isSoapEvent()){
	                    boolean valid = SOAPEventPayloadBuilder.validatePayload((Element)node);
//	                    System.out.println("validSOAPPayload---------"+valid);
	                }
	                String schemaText = XiSerializer.serialize(node);
	                if (node.getFirstChild() == null && schemaText != null) {
	                	if (schemaText.contains("<payload/>")) {
	                		// the payload is empty, set it to null (for instance, to allow other events to inherit from this event
	                		// is this the best way to check for an empty payload?
	                		schemaText = null;
	                	}
	                }
	                CompoundCommand command = new CompoundCommand();
	                command.append(new SetCommand(getEditingDomain(), event1, EventPackage.eINSTANCE.getEvent_PayloadString(), schemaText));
	                
	                try {
	                	// update namespaces
	                	event1.getNamespaceEntries().clear(); // not sure we want to clear, or just check for duplicates
	                	Iterator prefixes = getNamespaceImporter().getPrefixes();
	                	while (prefixes.hasNext()) {
	                		String prefix = (String) prefixes.next();
	                		String namespace = getNamespaceImporter().getNamespaceURIForPrefix(prefix);
	                		NamespaceEntry entry = EventFactory.eINSTANCE.createNamespaceEntry();
	                		entry.setPrefix(prefix);
	                		entry.setNamespace(namespace);
	                		command.append(new AddCommand(getEditingDomain(), event1, EventPackage.eINSTANCE.getEvent_NamespaceEntries(), entry));
	                	}
	                	
	                	// update imports
	                	event1.getRegistryImportEntries().clear(); // not sure we want to clear, or just check for duplicates
	            		com.tibco.cep.mapper.xml.nsutils.ImportRegistryEntry[] imports =getImportRegistry().getImports();
	            		for (com.tibco.cep.mapper.xml.nsutils.ImportRegistryEntry importEntry : imports) {
	                		ImportRegistryEntry registryEntry = EventFactory.eINSTANCE.createImportRegistryEntry();
	                		registryEntry.setNamespace(importEntry.getNamespaceURI());
	                		registryEntry.setLocation(importEntry.getLocation());
	                		command.append(new AddCommand(getEditingDomain(), event1, EventPackage.eINSTANCE.getEvent_RegistryImportEntries(), registryEntry));
						}

	                } catch (PrefixNotFoundException e1) {
	                	e1.printStackTrace();
	                }
	                EditorUtils.executeCommand(editor, command);
	                
				/*PayloadTreeModelParent parent = (PayloadTreeModelParent) obj;
				List<Object> tempList=new ArrayList<Object>();
				PayloadTreeModelChild chld;
				chld = new PayloadTreeModelChild(addParamRow(parent.getList()),"", parent, invokingObj,paramEditor);
				if(parent.getObjList()==null){
					tempList=new ArrayList<Object>();
				}
				else{
					tempList = parent.getObjList();
				}
     			tempList.add(chld);
				parent.setObjList(tempList);*/
			}
			// m_treeViewer.setInput(prList);
			m_treeViewer.refresh();
			TreeItem item = (TreeItem) m_treeViewer.getTree().getItem(0);
			if(item !=null)
				m_treeViewer.getTree().setSelection(item);
			setDirty(true);
			// setToExtendedPropModel(map);
		}
	}

	public void setInvokingObj(AdvancedEventPayloadTree invokingObj) {
		this.invokingObj = invokingObj;
	}

	/**
	 * Adds a new property row.
	 * 
	 * @return String name of the property added.
	 */
	public String addParamRow(List<String> list) {
		final String namePrefix = "param_";
		int index = 0;
		String name = null;
		name = namePrefix + index++;
		for(String str:list){
			name = namePrefix + index++;
			if(!list.contains(name)){
				break;
			}
			
		}
		list.add(name);
		return name;
	}

	public List getList() {
		return list;
	}

	public EditableTreeModel getTreeModel() {
		return treeModel;
	}

	public void setTreeModel(EditableTreeModel treeModel) {
		this.treeModel = treeModel;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> getInputFromModel(){
		
		prList.clear();
		getList().clear();
		if(editor!=null && editor.getEntity()!=null ){
			if(editor.getEntity().getExtendedProperties()==null)
				editor.getEntity().setExtendedProperties( ModelFactory.eINSTANCE.createPropertyMap());
			EditorUtils.getExtendedPropertiesForPayloadTree(editor.getEntity().getExtendedProperties(),getList());
			prList=getChldElementList(getList(),null);
		}
		return prList;
	}
	
	
	public List<Object>  getChldElementList(List list,PayloadTreeModelParent parent){
		
		@SuppressWarnings("rawtypes")
		Iterator it = list.iterator();
		List<Object> lst= new ArrayList<Object>();
		while(it.hasNext()){
			final String key = (String) it.next();
			PayloadTreeModelChild chld= new PayloadTreeModelChild();
			 	  chld.setName(key);
			 	  chld.setParent(parent);
			 	  chld.setExtendedTreeViewer(invokingObj);
			 	  lst.add(chld);
		}
		return lst;
		
	}

	public XiNode writeSchemaNode(ExpandedName name,PayloadTreeModelParent parent) {
		XiNode n = XiFactoryFactory.newInstance().createElement(name);
		PayloadTreeModelParent pnode=null;
	//	if (!mIsNull) {
//			ParameterNode root = (ParameterNode) m_tree.getRootNode();
		if(parent==null)
			pnode=new PayloadTreeModelParent(null, null, null, null, paramEditor);
		else
			pnode=parent;
		
		writeSchemaNode(pnode, n);
	//	}
		return n;
	}

	private void writeSchemaNode(Object pnode, XiNode on) {
//		if (m_tree.getEditableModel().isRootNull()) {
//			return;
//		}
		if (pnode instanceof PayloadTreeModelChild) {
			String str = ((PayloadTreeModelChild)pnode).getContentModelCategory().getAsRootReference(paramEditor.getNamespaceImporter(),((PayloadTreeModelChild)pnode).getContentModelDetails());
			if (str != null) {
				XiAttribute.setStringValue(on, "ref", str);
				return;
			}
			XiFactory factory = XiFactoryFactory.newInstance();
			XiNode ret = toNode(factory, pnode, m_namespaceContextRegistry);
			on.appendChild(ret);
		}
		if (pnode instanceof PayloadTreeModelParent) {
			String str = ((PayloadTreeModelParent)pnode).getContentModelCategory().getAsRootReference(paramEditor.getNamespaceImporter(),((PayloadTreeModelParent)pnode).getContentModelDetails());
			if (str != null) {
				XiAttribute.setStringValue(on, "ref", str);
				return;
			}
			XiFactory factory = XiFactoryFactory.newInstance();
			XiNode ret = toNode(factory, pnode, m_namespaceContextRegistry);
			on.appendChild(ret);
		}
	}
	
	 XiNode toNode(XiFactory factory, Object node, NamespaceContextRegistry mapper) {
	      ContentModelCategory cat = ElementSequenceCategory.INSTANCE;
	      return cat.toNode(factory, node, mapper);
	   }

	 protected EditingDomain getEditingDomain() {
			if (editor instanceof IEditingDomainProvider) {
				IEditingDomainProvider editingDomainProvider = (IEditingDomainProvider) editor;
				return editingDomainProvider.getEditingDomain();
			}
			return null;
		}


	    public NamespaceContextRegistry getNamespaceImporter() {
	        Event event = getEventModel();
	        if(event == null) return null;

	        com.tibco.xml.NamespaceMapper beMapper = (com.tibco.xml.NamespaceMapper) event.getPayloadNamespaceImporter();
	        NamespaceMapper bwMapper = NSUtilitiesConverter.ConvertToBWNamespaceMapper(beMapper);
	        return bwMapper;
	    }
	    
	    private Event getEventModel() {
	        return new EventAdapter(event1, null);
	    }
	    
	    public ImportRegistry getImportRegistry() {
	        Event event = getEventModel();
	        if(event == null) return null;

	        com.tibco.xml.ImportRegistry beRegistry = event.getPayloadImportRegistry();
	        ImportRegistry bwRegistry = NSUtilitiesConverter.ConvertToBWImportRegistry(beRegistry);

	        return bwRegistry;
	    }
}
