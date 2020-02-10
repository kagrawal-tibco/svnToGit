package com.tibco.cep.bpmn.ui.graph.properties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.cep.bpmn.ui.graph.model.factory.sequence.SequenceFlowEdgeFactory;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;



/**
 * 
 * @author majha
 *
 */
public class GeneralExclusiveGatewayPropertySection extends GeneralGatewayPropertySection {

	private boolean refresh;
	private CCombo defaultCombo;
	private DefaultSequenceWidgetListener selectionListener;
	String defaultSeq;
	private Label outgoingSequenceLabel;
	private Composite sequenceComposite;
	private ListViewer outgoingSequenceListViewer;
	private Map<String, EObject> outGoingSequenceMap;
	private String[] listInput;

	private ToolBar toolBar;
	private ToolItem upButton;
	private ToolItem downButton;
	
	public GeneralExclusiveGatewayPropertySection() {
		super();
		defaultSeq = "";
		selectionListener = new DefaultSequenceWidgetListener();
		outGoingSequenceMap = new HashMap<String, EObject>();
	}
	
	@Override
	public void createControls(Composite parent,
			TabbedPropertySheetPage tabbedPropertySheetPage) {
		// TODO Auto-generated method stub
		super.createControls(parent, tabbedPropertySheetPage);
//		nodeTypeBrowseButton.setEnabled(false);
//		nodeTypeText.setEnabled(false);
	}
	
	@Override
	public void aboutToBeHidden() {
		// TODO Auto-generated method stub
		super.aboutToBeHidden();
		if(defaultCombo != null && !defaultCombo.isDisposed())
			defaultCombo.removeSelectionListener(selectionListener);
	}
	
	@Override
	public void aboutToBeShown() {
		// TODO Auto-generated method stub
		super.aboutToBeShown();
		if(defaultCombo != null && !defaultCombo.isDisposed())
			defaultCombo.addSelectionListener(selectionListener);
	}
	
	
	@Override
	public void refresh() {
		try{
			this.refresh = true;
			if (fTSENode != null) { 
				super.refresh();
				EObject userObject = (EObject) fTSENode.getUserObject();
				EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper.wrap(userObject);
				EList<EObject> listAttribute = userObjWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_OUTGOING);
				defaultCombo.removeAll();
				defaultSeq = "";
				if(userObjWrapper.containsAttribute(BpmnMetaModelConstants.E_ATTR_DEFAULT) && listAttribute.size() >0){
					for (EObject eObject : listAttribute) {
						 EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(eObject);
						 String id = wrap.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
						 defaultCombo.add(id);
						 defaultCombo.setData(id, wrap.getEInstance());
					}
					
					EObject attribute = (EObject)userObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_DEFAULT);
					if(attribute == null ){
						defaultCombo.setText("");
						defaultSeq ="";
					}else{
						EObjectWrapper<EClass, EObject> seqWrap = EObjectWrapper.wrap(attribute);
						String id = (String)seqWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
						defaultSeq = id;
						defaultCombo.setText(id);
					}
					int itemCount = defaultCombo.getItemCount();
					if(itemCount == 1){
						defaultCombo.select(0);
						defaultCombo.setEnabled(false);
					}
					else
						defaultCombo.setEnabled(true);
				}else{
					defaultSeq = "";
					defaultCombo.setText("");
					defaultCombo.setEnabled(false);
				}
				
				refreshOutgoingSeqList();
			}
			
			if (fTSEEdge != null) {
			}
			
			if (fTSEGraph != null) {
			}
			
		}finally{
			this.refresh = false;
		}
	}
	
	private void refreshOutgoingSeqList(){
		EObject userObject = (EObject) fTSENode.getUserObject();
		EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper.wrap(userObject);
		EList<EObject> listAttribute = userObjWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_OUTGOING);
		if(isOutgoingSequenceOrderApplicable()){
			if(listAttribute.size() > 1){
				outgoingSequenceLabel.setVisible(true);
				sequenceComposite.setVisible(true);
				outGoingSequenceMap.clear();
				listInput = new String[listAttribute.size() -1];
				int i = 0;
				for (EObject eObject : listAttribute) {
					 EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(eObject);
					 String id = wrap.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
					 outGoingSequenceMap.put(id, wrap.getEInstance());
					 if(!id.equals(defaultSeq)){
						 if( i < listInput.length ){
							 listInput[i] = id;
							 i++;
						 }
					 }
				}
				outgoingSequenceListViewer.setInput(listInput);
				refreshUpAndDownButton();
				
			}else{
				outgoingSequenceLabel.setVisible(true);
				sequenceComposite.setVisible(true);
			}
		} else {
			if (outgoingSequenceLabel != null)
				outgoingSequenceLabel.setVisible(false);
			if (sequenceComposite != null)
				sequenceComposite.setVisible(false);
		}
	}
	
	private void refreshUpAndDownButton(){
		if(isOutgoingSequenceOrderApplicable()){
			upButton.setEnabled(false);
			downButton.setEnabled(false);
			int itemCount = listInput.length;
			if(itemCount >1){
				IStructuredSelection sel = (IStructuredSelection)outgoingSequenceListViewer.getSelection();
				if(sel != null){
					Object firstElement = sel.getFirstElement();
					if(firstElement != null){
						String string = (String)firstElement;
						if(string.equals(listInput[0])){
							upButton.setEnabled(false);
							downButton.setEnabled(true);
						}else if(string.equals(listInput[itemCount-1])){
							upButton.setEnabled(true);
							downButton.setEnabled(false);
						}else{
							upButton.setEnabled(true);
							downButton.setEnabled(true);
						}
					}
				}
				
			}
		}
	}
	
	protected void insertChildSpecificComponents() {
		super.insertChildSpecificComponents();
		getWidgetFactory().createLabel(composite, BpmnMessages.getString("exclusiveGatewayProp_defaultCombo_label"),
				SWT.NONE);
		defaultCombo = getWidgetFactory().createCCombo(composite,
				SWT.READ_ONLY);
		GridData gd = new GridData();
		gd.minimumWidth = 150;
		gd.heightHint = 20;
		gd.horizontalIndent = 1;
		defaultCombo.setLayoutData(gd);
		defaultCombo.setEnabled(false);
		
		if(isOutgoingSequenceOrderApplicable()){
			gd = new GridData(GridData.FILL_HORIZONTAL);
			// Add Event
			outgoingSequenceLabel = getWidgetFactory().createLabel(composite,
					BpmnMessages.getString("exclusiveGatewayProp_outgoingSequenceLabel_label"), SWT.NONE);
	
			sequenceComposite = getWidgetFactory().createComposite(
					composite);
			GridLayout layout = new GridLayout(2, false);
			layout.marginWidth = 0;
			layout.marginHeight = 0;
			sequenceComposite.setLayout(layout);
			gd = new GridData(GridData.FILL_HORIZONTAL);
			sequenceComposite.setLayoutData(gd);
	
			outgoingSequenceListViewer = new ListViewer(sequenceComposite,
					SWT.BORDER| SWT.SINGLE );
			gd = new GridData(/* GridData.FILL_HORIZONTAL */);
			gd.widthHint = 562;
			gd.heightHint = 100;
			outgoingSequenceListViewer.getList().setLayoutData(gd);
			outgoingSequenceListViewer.setContentProvider(new ArrayContentProvider());
			outgoingSequenceListViewer.getList().addSelectionListener(selectionListener);
			
			createToolbar(sequenceComposite);
			
			outgoingSequenceLabel.setVisible(false);
			sequenceComposite.setVisible(false);
		} 

	}
	
	protected ToolBar createToolbar(Composite parent) {
		toolBar = new ToolBar(parent, SWT.VERTICAL | SWT.RIGHT
				| SWT.FLAT);
		toolBar.setBackground(Display.getDefault().getSystemColor(
				SWT.COLOR_WHITE));
		toolBar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		upButton = new ToolItem(toolBar, SWT.PUSH);
		Image upImg = EditorsUIPlugin.getDefault().getImage(
				"icons/arrow_up.png");
		upButton.setImage(upImg);
		upButton.setToolTipText(BpmnMessages.getString("exclusiveGatewayProp_upButton_label"));
		upButton.setText(BpmnMessages.getString("exclusiveGatewayProp_upButton_label"));
		upButton.setEnabled(false);
		upButton.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				IStructuredSelection sel = (IStructuredSelection)outgoingSequenceListViewer.getSelection();
				if(sel != null){
					Object firstElement = sel.getFirstElement();
					if(firstElement != null){
						int i =0;
						boolean found = false;
						for (String s : listInput) {
							if(s.equals(firstElement)){
								found = true;
								break;
							}
							i++;
						}
						if(found && i > 0){
							String temp = listInput[i-1];
							listInput[i-1]= listInput[i];
							listInput[i]= temp;
						}
					}
				}
				outGoingSequenceChanged();
				refreshUpAndDownButton();
				outgoingSequenceListViewer.refresh(true);
			}
		});

		downButton = new ToolItem(toolBar, SWT.PUSH);
		Image downImg = EditorsUIPlugin.getDefault().getImage(
				"icons/arrow_down.png");
		downButton.setImage(downImg);
		downButton.setToolTipText(BpmnMessages.getString("exclusiveGatewayProp_downButton_label"));
		downButton.setText(BpmnMessages.getString("exclusiveGatewayProp_downButton_label"));
		downButton.setEnabled(false);
		downButton.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				IStructuredSelection sel = (IStructuredSelection)outgoingSequenceListViewer.getSelection();
				if(sel != null){
					Object firstElement = sel.getFirstElement();
					if(firstElement != null){
						int i =0;
						boolean found = false;
						for (String s : listInput) {
							if(s.equals(firstElement)){
								found = true;
								break;
							}
							i++;
						}
						if(found && i < (listInput.length -1)){
							String temp = listInput[i+1];
							listInput[i+1]= listInput[i];
							listInput[i]= temp;
						}
					}
				}
				outGoingSequenceChanged();
				refreshUpAndDownButton();
				outgoingSequenceListViewer.refresh(true);
			}
		});

		GridData gridData = new GridData();
		gridData.verticalIndent = 20;
		toolBar.setLayoutData(gridData);
		
		toolBar.pack();
		return toolBar;
	}
	
	private void outGoingSequenceChanged(){
		if(listInput == null || listInput.length ==0)
			return;
		List<EObject> outgoingList= new ArrayList<EObject>();
		String[] items = listInput;
		for (String string : items) {
			EObject eObject = outGoingSequenceMap.get(string);
			if(eObject != null)
				outgoingList.add(eObject);
		}
		
		EObject eObject = outGoingSequenceMap.get(defaultSeq);
		if(eObject != null)
			outgoingList.add(eObject);
		
		Map<String, Object> updateList = new HashMap<String, Object>();
		updateList.put(BpmnMetaModelConstants.E_ATTR_OUTGOING, outgoingList);
		
		updatePropertySection(updateList);
		
	}
	
	protected boolean isOutgoingSequenceOrderApplicable() {
		if (fTSENode != null) { 
//			EClass nodeType = (EClass) fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
//			EClass nodeExtType = (EClass) fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_EXT_TYPE);
			EObject userObject = (EObject) fTSENode.getUserObject();
			EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper.wrap(userObject);
			if(userObjWrapper.isInstanceOf(BpmnModelClass.FLOW_NODE)){
				EList<EObject> outgoing = userObjWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_OUTGOING);
				return outgoing.size() > 1;
			}
			
		}
		return true;
	}
	
	private class DefaultSequenceWidgetListener implements SelectionListener{

		@Override
		public void widgetSelected(SelectionEvent e) {
			if(refresh)
				return;
			Widget widget = e.widget;
			if(widget == defaultCombo){
				String text = defaultCombo.getText();
				if(!defaultSeq.equals(text)){
					Object data = defaultCombo.getData(text);
					Map<String, Object> updateList = new HashMap<String, Object>();
					updateList.put(BpmnMetaModelConstants.E_ATTR_DEFAULT, data);
					
					updatePropertySection(updateList);
					defaultSeq = text;
					refreshOutgoingSeqList();
					SequenceFlowEdgeFactory.refreshEdgeUiAttachededToGateway(fTSENode, getDiagramManager());
				}
			}else if(widget == outgoingSequenceListViewer.getList()){
				refreshUpAndDownButton();
			}
			
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			
		}
		
	}
	
}