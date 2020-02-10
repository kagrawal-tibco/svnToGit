package com.tibco.cep.studio.ui.forms.extendedPropTreeViewer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

import com.tibco.cep.designtime.core.model.ModelFactory;
import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.editors.utils.ToolBarProvider;
import com.tibco.cep.studio.ui.forms.components.AbstractHeirachicalViewer;
import com.tibco.cep.studio.ui.forms.components.ExtendedPropertiesMap;
import com.tibco.cep.studio.ui.util.TreeTableAutoResizeLayout;
/*
 * @author sshekhar
 */
public class ExtendedPropTreeViewer extends AbstractHeirachicalViewer{
	
	private static String NAME = Messages.getString("extPropTree_Name");
	private static String VALUE = Messages.getString("extpropTree_Value");
	private static final String[] PROPS = { NAME, VALUE };
	private ExtendedPropTreeViewer invokingObj;
	private Boolean isStateMachineEditor = false;
	protected ToolBarProvider toolBarProvider;
	protected List<Object> prList = new ArrayList<Object>();
	private ExtendedPropertiesMap map = new ExtendedPropertiesMap();
 
	public ExtendedPropTreeViewer(AbstractSaveableEntityEditorPart editor) {
		super(editor);
		setInvokingObj(this);
	}


	@Override
	public void createHierarchicalTree(Composite parent) {
		super.createHierarchicalTree(parent);
		
		toolBarProvider.getAddGroupItem().addSelectionListener(new ToolbarAddGrpSelectionAdapter());
		toolBarProvider.getAddItem().addSelectionListener(new ToolbarAddSelectionAdapter());
		toolBarProvider.getDelItem().addSelectionListener(new ToolbarDelSelectionAdapter());
		
        m_treeViewer.getTree().addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		if(m_treeViewer.getTree().getItemCount()>0)
        		  toolBarProvider.getDelItem().setEnabled(true);
        	}

			});
        
		m_treeViewer.addDropSupport(DND.DROP_COPY | DND.DROP_MOVE,  new Transfer[] {TextTransfer.getInstance()}, new ViewerDropAdapter(m_treeViewer) {

			private PropTreeModelChild fTarget;

			@Override
			public boolean validateDrop(Object target, int operation, TransferData transferType) {
				if (target instanceof PropTreeModelChild) {
					String name = ((PropTreeModelChild) target).getName();
					if ("SCHEMA_NAME".equals(name) || "OBJECT_NAME".equals(name)
							|| "OBJECT_TYPE".equals(name)) {
						this.fTarget = (PropTreeModelChild) target;
						return true;
					}
					return false; // do we support GVs for all metadata properties?
				}
				return false;
			}

			@Override
			public boolean performDrop(Object data) {
				if (this.fTarget != null && data instanceof String) {
					this.fTarget.getExtendedTreeViewer().modify(this.fTarget, ExtendedPropTreeViewer.VALUE, data);
					m_treeViewer.refresh();
					editor.modified();
					return true;
				}
				return false;
			}
		});
	}
	
	
	@Override
	protected void createToolBar(){
		toolBarProvider=new ToolBarProvider(comp,null);
		toolBarProvider.CreateHierarchicalToolbar();
		toolBarProvider.getDelItem().setEnabled(false);
	}
	@Override
	protected void setContentandLabelProvider(){
	 	m_treeViewer.setContentProvider(this);
        m_treeViewer.setLabelProvider(this);
        m_treeViewer.setCellModifier(this);
	}
	
	@Override
	protected void createTreeColumns(Tree tree ,Composite treeComp){

        TreeColumn column1 = new TreeColumn(tree, SWT.LEFT);
        tree.setLinesVisible(true);
        column1.setAlignment(SWT.LEFT);
        column1.setText(NAME);
        TreeColumn column2 = new TreeColumn(tree, SWT.RIGHT);
        column2.setAlignment(SWT.LEFT);
        column2.setText(VALUE);
        
        TreeTableAutoResizeLayout tcl = new TreeTableAutoResizeLayout(m_treeViewer.getTree());
        tcl.addColumnData(column1, new ColumnWeightData(1));
        tcl.addColumnData(column2, new ColumnWeightData(1));
        treeComp.setLayout(tcl);
        
        tree.setHeaderVisible(true);
        m_treeViewer.setColumnProperties(PROPS);
        CellEditor[] editors = new CellEditor[2];
        editors[0] = new TextCellEditor(m_treeViewer.getTree());
        editors[1] = new TextCellEditor(m_treeViewer.getTree());
        m_treeViewer.setCellEditors(editors);
        
	}
	 /**
     * Is this field dirty?
     */
    public boolean isDirty() {
        // return the current dirty state
        return this.isDirty;
    }


    protected void setDirty(boolean tf) {
    	isDirty = tf;
    	if(tf){
    		if(editor != null){
    			EditorUtils.persistExtendedProperties(entity, getMap(), editor);
    		}
    	}
    }

    
	/**
     * Adds a new group row.
     * @return String name of the group added.
     */
    public String addGroupRow(ExtendedPropertiesMap map) {
        final String namePrefix = "group_";
        int index = 0;
        String name;
        for (name = namePrefix + index; null != map.get(name); index++) {
            name = namePrefix + index;
        }
        map.put(name, new ExtendedPropertiesMap());
        return name;
    }
	
    
    /**
     * Adds a new property row.
     * @return String name of the property added.
     */
    public String addPropertyRow(ExtendedPropertiesMap map) {
        final String namePrefix = "property_";
        int index = 0;
        String name;
        for (name = namePrefix + index; null != map.get(name); index++) {
            name = namePrefix + index;
        }
        map.put(name, "");
        return name;
    }

	@SuppressWarnings("unchecked")
	public List<Object> getInputFromModel(){
		
		prList.clear();
		getMap().clear();
		if(editor!=null && editor.getEntity()!=null ){
			if(editor.getEntity().getExtendedProperties()==null)
				editor.getEntity().setExtendedProperties( ModelFactory.eINSTANCE.createPropertyMap());
			EditorUtils.getExtendedProperties(editor.getEntity().getExtendedProperties(),getMap());
//			setToExtendedPropModel(map,null);
//			if(map)
			prList=getChldElementList(getMap(),null);
		}
			return prList;
	}
	
	
	public List<Object>  getChldElementList(ExtendedPropertiesMap map,PropTreeModelParent parent){
		
		@SuppressWarnings("rawtypes")
		Iterator it = map.keySet().iterator();
		List<Object> lst= new ArrayList<Object>();
		while(it.hasNext()){
			final String key = (String) it.next();
			 if(map.get(key) instanceof Map){
				 PropTreeModelParent pr= new PropTreeModelParent();
				 pr.setName(key);
				 pr.setMap(new ExtendedPropertiesMap());
				 pr.getMap().setDelegate((Map)map.get(key));
				 pr.setParent(parent);
				 pr.setObjList(getChldElementList(pr.getMap(),pr));
				 pr.setExtendedTreeViewer(invokingObj);
				 lst.add(pr);
			 }
			 else{
				  PropTreeModelChild chld= new PropTreeModelChild();
			 	  chld.setName(key);
			 	  chld.setValue((String)map.get(key));
			 	  chld.setParent(parent);
			 	  chld.setExtendedTreeViewer(invokingObj);
			 	  lst.add(chld);
			 }
		}
		return lst;
		
	}

	
	
	
	
	
	 String errMsg ;
		private void displayErrorMessage(String str){
			errMsg=str;	
		Display.getDefault().asyncExec(new Runnable(){
			public void run() {
				MessageDialog.openError(Display.getDefault().getActiveShell(), "Invalid Property", errMsg);
			}});
		
		}

	@Override
	protected int getDepth(Object Parentobj) {
		PropTreeModelParent pr ;
		if (Parentobj instanceof PropTreeModelParent) {
			 pr = (PropTreeModelParent) Parentobj;
		}
		else
			return 0;
		int i = 0;
		while (pr.getParent() != null) {
			pr = pr.getParent();
			i++;
		}
		return i;
	}

	
	class ToolbarDelSelectionAdapter extends SelectionAdapter{
		@Override
		public void widgetSelected(SelectionEvent event){
			StructuredSelection strSel=(StructuredSelection)m_treeViewer.getSelection();
			//Object obj=(Object)strSel.getFirstElement();
			Iterator<Object> iter=strSel.iterator();
			while(iter.hasNext()){
				Object obj=iter.next();

				if(obj instanceof PropTreeModelChild){
					PropTreeModelChild objChld=(PropTreeModelChild)obj;
					PropTreeModelParent pr=objChld.getParent();
					if(pr!=null){
						pr.getObjList().remove(objChld);
						pr.getMap().getDelegate().remove(objChld.getName());
					}
					else{
						getMap().remove(objChld.getName());
						prList.remove(objChld);
					}
				}
				if(obj instanceof PropTreeModelParent){
					PropTreeModelParent objPrnt=(PropTreeModelParent)obj;
					PropTreeModelParent pr;
					if(objPrnt.getParent() == null){
						pr=objPrnt;
						prList.remove(pr);
						getMap().getDelegate().remove(pr.getName());
					}
					else{
						pr=objPrnt.getParent();
						pr.getObjList().remove(objPrnt);
						pr.getMap().getDelegate().remove(objPrnt.getName());
					}

				}
			}
			m_treeViewer.setInput(prList);
			m_treeViewer.refresh();	
			toolBarProvider.getDelItem().setEnabled(false);
			setDirty(true);
		}

	}
	
	class ToolbarAddSelectionAdapter extends SelectionAdapter{
		@Override
    	public void widgetSelected(SelectionEvent event){
			StructuredSelection strSel=(StructuredSelection)m_treeViewer.getSelection();
			Object obj=(Object)strSel.getFirstElement();
			if(obj==null)
				{
					PropTreeModelChild chld= new PropTreeModelChild(addPropertyRow(getMap()),"",null,invokingObj);
					getMap().put(chld.getName(), chld.getValue());
					prList.add(chld);
				}				
			if(obj instanceof PropTreeModelChild){
				PropTreeModelChild objChld=(PropTreeModelChild)obj;
				if(objChld.getParent()!=null){
					PropTreeModelChild chld= new PropTreeModelChild(addPropertyRow(objChld.getParent().getMap()),"",objChld.getParent(),invokingObj);
					List<Object>tempList=objChld.getParent().getObjList();
					tempList.add(chld);
				}else
				{
					PropTreeModelChild chld= new PropTreeModelChild(addPropertyRow(getMap()),"",null,invokingObj);
					getMap().put(chld.getName(), chld.getValue());
					prList.add(chld);
				}	
			}
			if(obj instanceof PropTreeModelParent){
				PropTreeModelParent parent=(PropTreeModelParent)obj;
				List<Object>tempList;
				PropTreeModelChild chld;
				chld= new PropTreeModelChild(addPropertyRow(parent.getMap()),"",parent,invokingObj);
				tempList=parent.getObjList();
				tempList.add(chld);
				parent.setObjList(tempList);
				
			}
//			m_treeViewer.setInput(prList);
			m_treeViewer.refresh();	
			setDirty(true);
//			setToExtendedPropModel(map);
		}
	}
	class ToolbarAddGrpSelectionAdapter extends SelectionAdapter{
		
		@Override
    	public void widgetSelected(SelectionEvent event){
			StructuredSelection strSel=(StructuredSelection)m_treeViewer.getSelection();
			Object obj=(Object)strSel.getFirstElement();
			
			if(obj==null){
				PropTreeModelParent prnt= new PropTreeModelParent(addGroupRow(getMap()),"",new ArrayList<Object>(),null,invokingObj);
				prnt.setMap((ExtendedPropertiesMap)getMap().get(prnt.getName()));
				prList.add(prnt);
			}
			if(obj instanceof PropTreeModelChild){
				PropTreeModelChild objChld=(PropTreeModelChild)obj;
			
				
				if(objChld.getParent()!=null){
					PropTreeModelParent prnt= new PropTreeModelParent(addGroupRow(objChld.getParent().getMap()),"",new ArrayList<Object>(),objChld.getParent(),invokingObj);
					prnt.setMap((ExtendedPropertiesMap)objChld.getParent().getMap().get(prnt.getName()));
					List<Object>tempList=objChld.getParent().getObjList();
					tempList.add(prnt);
					objChld.getParent().setObjList(tempList);
				}else{
					PropTreeModelParent prnt= new PropTreeModelParent(addGroupRow(getMap()),"",new ArrayList<Object>(),null,invokingObj);
					prnt.setMap((ExtendedPropertiesMap)getMap().get(prnt.getName()));
					prList.add(prnt);
				}
			}
			if(obj instanceof PropTreeModelParent ){
				PropTreeModelParent objPrnt=(PropTreeModelParent)obj;
				PropTreeModelParent parentObj=null;
					parentObj=objPrnt;
				
					PropTreeModelParent prnt= new PropTreeModelParent(addGroupRow(objPrnt.getMap()),"",new ArrayList<Object>(),parentObj,invokingObj);
					prnt.setMap((ExtendedPropertiesMap)objPrnt.getMap().get(prnt.getName()));
					List<Object>tempList=objPrnt.getObjList();
					tempList.add(prnt);
					objPrnt.setObjList(tempList);
					
			}
//			m_treeViewer.setInput(prList);
			m_treeViewer.refresh();
			
			setDirty(true);
//			setToExtendedPropModel(map);
		}
	}
	
	public ExtendedPropTreeViewer getInvokingObj() {
		return invokingObj;
	}

	public void setInvokingObj(ExtendedPropTreeViewer invokingObj) {
		this.invokingObj = invokingObj;
	}


	public ExtendedPropertiesMap getMap() {
		return map;
	}


	public void setMap(ExtendedPropertiesMap map) {
		this.map = map;
	}

	
	public Boolean getIsStateMachineEditor() {
		return isStateMachineEditor;
	}


	public void setIsStateMachineEditor(Boolean isStateMachineEditor) {
		this.isStateMachineEditor = isStateMachineEditor;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getElements(java.lang.Object)
	 */
	@Override
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 */

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement == null)
			return new Object[0];
		if (parentElement instanceof List)
			return ((List<?>) parentElement).toArray();
		if (parentElement instanceof PropTreeModelParent)
			return ((PropTreeModelParent) parentElement).getObjList().toArray();

		return new Object[0];
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
	 */
	@Override
	public Object getParent(Object element) {
		if (element == null)
			return null;
		if (element instanceof PropTreeModelParent)
			return ((PropTreeModelParent) element).getParent();

		if (element instanceof PropTreeModelChild) {
			return ((PropTreeModelChild) element).getParent();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
	 */
	@Override
	public boolean hasChildren(Object element) {
		if (element == null)
			return false;
		if (element instanceof List)
			return ((List<?>) element).size() > 0;
		if (element instanceof PropTreeModelParent) {
			if (((PropTreeModelParent) element).getObjList() != null)
				return (((PropTreeModelParent) element).getObjList()).size() > 0;
		}
		return false;
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	@Override
	public void dispose() {
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
	 */
	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
	 */
	@Override
	public String getColumnText(Object element, int columnIndex) {
		switch (columnIndex) {
		case 0:
			if (element instanceof PropTreeModelParent) {

				return ((PropTreeModelParent) element).getName();
			}
			if (element instanceof PropTreeModelChild) {
				return ((PropTreeModelChild) element).getName();
			}
		case 1:
			if (element instanceof PropTreeModelParent)
				return ((PropTreeModelParent) element).getValue();
			if (element instanceof PropTreeModelChild)
				return ((PropTreeModelChild) element).getValue();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 */
	@Override
	public void addListener(ILabelProviderListener listener) {
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object, java.lang.String)
	 */
	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 */
	@Override
	public void removeListener(ILabelProviderListener listener) {
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITableColorProvider#getForeground(java.lang.Object, int)
	 */
	@Override
	public Color getForeground(Object element, int columnIndex) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITableColorProvider#getBackground(java.lang.Object, int)
	 */
	@Override
	public Color getBackground(Object element, int columnIndex) {
		return null;
	}

	private static Font boldFont = null;

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITableFontProvider#getFont(java.lang.Object, int)
	 */
	@Override
	public Font getFont(Object element, int columnIndex) {
		switch (columnIndex) {
		case 0:
			if (element instanceof PropTreeModelParent) {
				return getBoldFont();
			}
		}
		return null;
	}

	private Font getBoldFont() {
		if (boldFont == null) {
			boldFont = new Font(null, "Arial", 10, SWT.BOLD);
		}
		return boldFont;
	}


	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ICellModifier#canModify(java.lang.Object, java.lang.String)
	 */
	@Override
	public boolean canModify(Object element, String property) {
		if (element instanceof PropTreeModelParent
				&& property.equalsIgnoreCase(ExtendedPropTreeViewer.VALUE))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ICellModifier#getValue(java.lang.Object, java.lang.String)
	 */
	@Override
	public Object getValue(Object element, String property) {
		if (element instanceof PropTreeModelParent) {
			PropTreeModelParent obj = (PropTreeModelParent) element;
			if (ExtendedPropTreeViewer.NAME.equals(property)) {
				return obj.getName();
			}
			if (ExtendedPropTreeViewer.VALUE.equals(property))
				return null;
		}
		if (element instanceof PropTreeModelChild) {
			PropTreeModelChild obj = (PropTreeModelChild) element;
			if (ExtendedPropTreeViewer.NAME.equals(property))
				return obj.getName();
			if (ExtendedPropTreeViewer.VALUE.equals(property))
				return obj.getValue();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ICellModifier#modify(java.lang.Object, java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void modify(Object element, String property, Object value) {
		if (element instanceof Item)
			element = ((Item) element).getData();

		if (element instanceof PropTreeModelChild) {
			PropTreeModelChild obj = (PropTreeModelChild) element;
			if (ExtendedPropTreeViewer.NAME.equals(property)) {
				if (((String) value).isEmpty())
					return;
				if (obj.getName().equals((String) value))
					return;
				if (obj.getParent() != null
						&& obj.getParent().getMap().getDelegate()
								.containsKey((String) value)) {
					displayErrorMessage("Duplicate Name.");
					return;
				}
				String oldName = obj.getName();
				if (obj.getParent() != null) {
					obj.getParent().getMap().getDelegate()
							.remove(obj.getName());
					obj.setName((String) value);
					obj.getParent().getMap().getDelegate()
							.put((String) value, obj.getValue());
				} else {

					if (getMap().getDelegate().containsKey((String) value)) {
						displayErrorMessage("Duplicate Name.");
						return;
					}

					obj.setName((String) value);
					getMap().put((String) value, getMap().get(oldName));
					getMap().remove(oldName);

				}
				// updateMap(obj.getParent());
			}
			if (ExtendedPropTreeViewer.VALUE.equals(property)) {
				if (((String) value).isEmpty())
					return;
				if (obj.getValue().equals((String) value))
					return;
				if (obj.getParent() != null) {
					obj.setValue((String) value);
					obj.getParent().getMap().getDelegate()
							.put(obj.getName(), (String) value);
				} else {
					obj.setValue((String) value);
					getMap().put(obj.getName(), (String) value);
				}
				// updateMap(obj.getParent());
			}
		}

		if (element instanceof PropTreeModelParent) {
			PropTreeModelParent objParent = (PropTreeModelParent) element;
			if (ExtendedPropTreeViewer.NAME.equals(property)) {
				String oldName = objParent.getName();
				if (((String) value).isEmpty()
						|| oldName.equals((String) value))
					return;
				if (objParent.getParent() != null) {
					if (objParent.getParent().getMap().getDelegate()
							.containsKey((String) value)) {
						displayErrorMessage("Duplicate Name.");
						return;
					}
					objParent
							.getParent()
							.getMap()
							.getDelegate()
							.put((String) value,
									objParent.getParent().getMap().get(oldName));
					objParent.setName((String) value);
					objParent.getParent().getMap().getDelegate()
							.remove(oldName);
				} else {
					if (getMap().getDelegate().containsKey((String) value)) {
						displayErrorMessage("Duplicate Name.");
						return;
					}

					objParent.setName((String) value);
					getMap().put((String) value, getMap().get(oldName));
					getMap().remove(oldName);
				}
			}
		}
		setDirty(true);
		m_treeViewer.refresh();
	}
}

