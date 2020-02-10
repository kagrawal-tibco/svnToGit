package com.tibco.cep.bpmn.ui.graph.properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.cep.bpmn.ui.graph.BpmnGraphUtils;
import com.tibco.cep.bpmn.ui.graph.palette.PaletteLoader;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteGroupItem;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteModel;
import com.tibco.cep.bpmn.ui.utils.PaletteHelpTextGenerator;
import com.tibco.cep.studio.ui.util.TableAutoResizeLayout;
import com.tomsawyer.graph.TSGraph;

/**
 * 
 * @author majha
 * 
 */
public class ReplyEventPropertySection extends AbstractFormPropertySection{
	private static final String REPLY_TO=BpmnMessages.getString("replyEventPropertySection_Column_replyTo");
	private static final String CONSUME=BpmnMessages.getString("replyEventPropertySection_Column_consume");
	private static final String MESSAGE_STARTERS=BpmnMessages.getString("replyEventPropertySection_Column_messageStarters");
	
	public final String[] PROPS = { MESSAGE_STARTERS, REPLY_TO, CONSUME };
	
	protected Composite composite;
	private boolean refresh;
	
	private TableViewer replyEventTableViewer;
	
	private Map<String, MessageStarter> allStarters;
	private Map<String, EObjectWrapper<EClass, EObject>> starterNodesMap;
	private Map<String, String> namesMap;
//	private EObject userObject;
	private ArrayList<TableEditor> editors;
	private boolean isEndEvent;
	
	protected FormText browser;
	
	protected Section helpSection;
	protected Composite browserComposite;
	
	protected String help = "";

	public ReplyEventPropertySection() {
		super();
		allStarters = new HashMap<String, ReplyEventPropertySection.MessageStarter>();
		starterNodesMap = new HashMap<String, EObjectWrapper<EClass, EObject>>();
		namesMap= new HashMap<String, String>();
		editors = new ArrayList<TableEditor>();
	}

	@Override
	public void aboutToBeHidden() {
		if (!composite.isDisposed()) {
			super.aboutToBeHidden();

		}
	}

	@Override
	public void aboutToBeShown() {
		if (!composite.isDisposed()) {
			super.aboutToBeShown();

		}
	}

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createPropertyPartControl(IManagedForm form, Composite parent) {
		
		composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(1, false));

		Composite topComoposite = getWidgetFactory().createComposite(composite);
		topComoposite.setLayout(new GridLayout(2, false));
		createTableViewer(topComoposite);
	}
	
	private void createTableViewer(Composite parent){
		replyEventTableViewer = new TableViewer(parent, SWT.BORDER|SWT.FULL_SELECTION |SWT.V_SCROLL);

		GridData data = new GridData();
		data.heightHint = 150;
		data.widthHint = 400;
		data.verticalSpan = 4;
		data.horizontalSpan = 1;
		replyEventTableViewer.getTable().setLayoutData(data);
//		replyEventTableViewer.getTable().setLinesVisible(true);
		replyEventTableViewer.getTable().setHeaderVisible(true);

		TableViewerColumn col = new TableViewerColumn(replyEventTableViewer,
				SWT.NONE);
		TableColumn idColumn = col.getColumn();
		idColumn.setText(MESSAGE_STARTERS);
		col.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				MessageStarter messageStarter = (MessageStarter) cell.getElement();
				String id = messageStarter.id;
				String name = namesMap.get(id);
				if(id==null){
					EObjectWrapper<EClass, EObject> eObjectWrapper = starterNodesMap.get(id);
					name = id;
					if(eObjectWrapper != null){
						String label = eObjectWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
						if(label!=null && !label.trim().isEmpty()){
							name = label;
						}
					}
				}
				cell.setText(name);
			}

		});
		
		
		col = new TableViewerColumn(replyEventTableViewer,
				SWT.NONE);
		TableColumn replyTo = col.getColumn();
		replyTo.setText(REPLY_TO);
		replyTo.setAlignment(SWT.CENTER);
		col.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				final MessageStarter messageStarter = (MessageStarter) cell
						.getElement();
				TableItem item = (TableItem) cell.getItem();
				TableEditor editor = new TableEditor(replyEventTableViewer
						.getTable());

				final Button checkButton = new Button(replyEventTableViewer
						.getTable(), SWT.CHECK);
				checkButton.pack();

				editor.minimumWidth = checkButton.getSize().x;
				editor.horizontalAlignment = SWT.CENTER;
				editor.setEditor(checkButton, item, 1);
				editors.add(editor);
				checkButton.setSelection(messageStarter.isReplyTo());
				checkButton.addSelectionListener(new SelectionListener() {
					
					@Override
					public void widgetSelected(SelectionEvent e) {
						setValue(messageStarter, checkButton.getSelection(), true);
						
					}
					
					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
			}

		});
		
		
//		col = new TableViewerColumn(replyEventTableViewer,
//				SWT.NONE);
//		final TableColumn cosume = col.getColumn();
//		cosume.setText(CONSUME);
//		cosume.setAlignment(SWT.CENTER);
//		col.setLabelProvider(new CellLabelProvider() {
//			@Override
//			public void update(ViewerCell cell) {
//				final MessageStarter messageStarter = (MessageStarter) cell
//						.getElement();
//				TableItem item = (TableItem) cell.getItem();
//				TableEditor editor = new TableEditor(replyEventTableViewer
//						.getTable());
//
//				final Button checkButton = new Button(replyEventTableViewer
//						.getTable(), SWT.CHECK);
//				checkButton.pack();
//
//				editor.minimumWidth = checkButton.getSize().x;
//				editor.horizontalAlignment = SWT.CENTER;
//				editor.setEditor(checkButton, item, 2);
//				editors.add(editor);
//				if(isEndEvent){
//					checkButton.setEnabled(false);
//					checkButton.setSelection(messageStarter.isConsume());
//					item.setBackground(2, COLOR_GRAY);
//				}else
//					checkButton.setSelection(messageStarter.isConsume());
//				
//				if(!isEndEvent){
//					checkButton.addSelectionListener(new SelectionListener() {
//
//						@Override
//						public void widgetSelected(SelectionEvent e) {
//							setValue(messageStarter, checkButton.getSelection(),
//									false);
//
//						}
//
//						@Override
//						public void widgetDefaultSelected(SelectionEvent e) {
//							// TODO Auto-generated method stub
//
//						}
//					});
//				}
//				
//			}
//
//		});
		

		
		replyEventTableViewer.setContentProvider(new MessageStarterContentProvider());
		
		autoTableLayout(replyEventTableViewer.getTable());
	}
	
	protected void autoTableLayout(Table table) {
		TableAutoResizeLayout autoTableLayout = new TableAutoResizeLayout(
				table);
		for (int loop = 0; loop < table.getColumns().length; loop++) {
			autoTableLayout.addColumnData(new ColumnWeightData(1));
		}
		table.setLayout(autoTableLayout);
	}
	
	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		super.refresh();
		this.refresh = true;
		if (fTSENode != null) {
			EClass nodeType = (EClass) fTSENode
					.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
			EClass nodeExtType = (EClass) fTSENode
					.getAttributeValue(BpmnUIConstants.NODE_ATTR_EXT_TYPE);
			
			isEndEvent = false;
			allStarters.clear();
			starterNodesMap.clear();
			namesMap.clear();
			for(TableEditor editor: editors){
				if(editor != null){
					Control control = editor.getEditor();
					if(control != null && !control.isDisposed())
						control.dispose();
					editor.dispose();
				}
			}
			replyEventTableViewer.getTable().layout(true);
			EObject userObject = (EObject) fTSENode.getUserObject();
			EObjectWrapper<EClass, EObject> eventWrapper = EObjectWrapper.wrap(userObject);
			//Get Help for the Node from the Palette Model
			BpmnPaletteModel _toolDefinition = PaletteLoader.getBpmnPaletteModel(fProject);
			if (_toolDefinition != null) {
				String id = (String) ExtensionHelper.getExtensionAttributeValue(eventWrapper,	BpmnMetaModelExtensionConstants.E_ATTR_TOOL_ID);
				BpmnPaletteGroupItem item = null;
				if (id != null && !id.isEmpty()) {
					item = _toolDefinition.getPaletteItemById(id);
				} 
				if(item == null){
					List<BpmnPaletteGroupItem> paletteToolByType = _toolDefinition
							.getPaletteToolItemByType(nodeType, nodeExtType);
					if (paletteToolByType.size() > 0)
						item = paletteToolByType.get(0);
				}
				if (item != null) {
					String h = /*item.getHelp(BpmnUIConstants.REPLY_EVENT_HELP_SECTION)*/PaletteHelpTextGenerator.getHelpText(item,BpmnUIConstants.REPLY_EVENT_HELP_SECTION);
					if(h!= null){
						help = h.replace("&nbsp;", "").replace("<ul class=\"noindent\">", "").replace("</ul>", "");
						help = "<form>" + help + "</form>";
						browser.setText(help, true, false);
					}
				}
			}
			if(BpmnModelClass.END_EVENT.isSuperTypeOf(eventWrapper.getEClassType())){
				isEndEvent = true;	
			}
			
			EObjectWrapper<EClass, EObject> addDataExtensionValueWrapper = ExtensionHelper
					.getAddDataExtensionValueWrapper(eventWrapper);
			if(addDataExtensionValueWrapper.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_MESSAGE_STARTERS)){
				updateMessageStartersModel();
				EList<EObject> listAttribute = addDataExtensionValueWrapper.getListAttribute(BpmnMetaModelExtensionConstants.E_ATTR_MESSAGE_STARTERS);
				if(listAttribute != null && listAttribute.size() >0){
					for (EObject eObject : listAttribute) {
						EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(eObject);
						String id = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_MESSAGE_STARTER);
						boolean replyTo = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_REPLY_TO);
						boolean consume = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_CONSUME);
						if(isEndEvent)
							consume = true;
						if (allStarters.containsKey(id)) { 
							MessageStarter messageStarter = new MessageStarter(wrap,
									id, replyTo, consume);
							allStarters.put(id, messageStarter);
						}
					}
				}
				setInput();
			}
		}
		
		this.refresh = false;
	}
	
	private void updateMessageStartersModel(){
		EObjectWrapper<EClass, EObject> modelRoot = getDiagramManager().getModelController().getModelRoot();
		if(fTSENode != null){
			TSGraph ownerGraph = fTSENode.getOwnerGraph();
			if(ownerGraph != null){
				Object userObject = ownerGraph.getUserObject();
				if(userObject != null && userObject instanceof EObject){
					EObject eObj = (EObject)userObject;
					if(eObj.eClass().equals(BpmnModelClass.SUB_PROCESS)){
						modelRoot = EObjectWrapper.wrap(eObj);
					}
				}
			}
			
		}
		Collection<EObject> flowNodes = BpmnModelUtils.getFlowNodes(modelRoot, BpmnModelClass.START_EVENT);
		flowNodes.addAll(BpmnModelUtils.getFlowNodes(modelRoot, BpmnModelClass.RECEIVE_TASK));
		for (EObject eObject : flowNodes) {
			if(BpmnModelClass.EVENT.isSuperTypeOf(eObject.eClass())){
				EObjectWrapper<EClass, EObject> eventWrapper = EObjectWrapper.wrap(eObject);
				if (eventWrapper
						.containsAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITIONS)) {
					EList<EObject> listAttribute = eventWrapper
							.getListAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITIONS);
					if (listAttribute.isEmpty()) {
						continue;
					}
				}
			}
			EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(eObject);
			String id = wrap.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
			if (!allStarters.containsKey(id)) {
				boolean consume = false;
				if(isEndEvent)
					consume = true;
				MessageStarter messageStarter = new MessageStarter(null,
						id, false, consume);
				allStarters.put(id, messageStarter);
			}
			starterNodesMap.put(id, wrap);
			
			String name = id;
			if(wrap != null){
				String label = wrap.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
				if(label!=null && !label.trim().isEmpty()){
					name = label;
				}
			}
			Set<String> keySet = namesMap.keySet();
			Set<String> foundIds = new HashSet<String>();
			for (String idName : keySet) {
				String string = namesMap.get(idName);
				if(string.equalsIgnoreCase(name)){
					foundIds.add(idName);
				}
			} 
			
			if(foundIds.size()>0){
				for (String string : foundIds) {
					String newName = namesMap.get(string)+"("+string+")";
					namesMap.put(string, newName);
				}
				name = name+"("+id+")";
				namesMap.put(id, name);
			}else{
				namesMap.put(id, name);
			}
		}
	}
	
	private void setInput(){
		Set<String> keySet = allStarters.keySet();
		List<MessageStarter> list = new ArrayList<ReplyEventPropertySection.MessageStarter>();
		for (String string : keySet) {
			list.add(allStarters.get(string));
		}
		replyEventTableViewer.setInput(list);
	}
	
	protected void updatePropertySection(Map<String, Object> updateList) {
		if (updateList.size() == 0)
			return;

		BpmnGraphUtils.fireUpdate(updateList, fTSENode, fPropertySheetPage
				.getEditor().getBpmnGraphDiagramManager());
	}
	
	
	protected void setValue(MessageStarter starter, boolean value,
			boolean replyTo) {
		if (refresh)
			return;
		EObject userObject = (EObject) fTSENode.getUserObject();
		EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(userObject);
		EObjectWrapper<EClass, EObject> addDataExtensionValueWrapper = ExtensionHelper
				.getAddDataExtensionValueWrapper(wrap);
		List<EObject> temp = addDataExtensionValueWrapper
				.getListAttribute(BpmnMetaModelExtensionConstants.E_ATTR_MESSAGE_STARTERS);
		List<EObject> listAttribute = new ArrayList<EObject>();
		if(temp != null){
			listAttribute.addAll(temp);
		}
		if(replyTo)
			starter.setReplyTo((Boolean) value);
		else
			starter.setConsume((Boolean) value);
		if (starter.eObject != null) {
			if (replyTo)
				starter.eObject.setAttribute(
						BpmnMetaModelExtensionConstants.E_ATTR_REPLY_TO, value);
			else
				starter.eObject.setAttribute(
						BpmnMetaModelExtensionConstants.E_ATTR_CONSUME, value);

		} else {
			EObjectWrapper<EClass, EObject> impl = EObjectWrapper
					.createInstance(BpmnModelClass.EXTN_MESSAGE_STARTER_DATA);
			impl.setAttribute(
					BpmnMetaModelExtensionConstants.E_ATTR_MESSAGE_STARTER,
					starter.id);
			if (replyTo) {
				impl.setAttribute(
						BpmnMetaModelExtensionConstants.E_ATTR_REPLY_TO, value);
				impl.setAttribute(
						BpmnMetaModelExtensionConstants.E_ATTR_CONSUME, false);
			} else {
				impl.setAttribute(
						BpmnMetaModelExtensionConstants.E_ATTR_REPLY_TO, false);
				impl.setAttribute(
						BpmnMetaModelExtensionConstants.E_ATTR_CONSUME, value);
			}
			starter.eObject = impl;
			listAttribute.add(impl.getEInstance());
		}
		Map<String, Object> updateList = new HashMap<String, Object>();
		updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_MESSAGE_STARTERS,
				listAttribute);
		updatePropertySection(updateList);
//		replyEventTableViewer.refresh();
	}
	
	public class MessageStarterContentProvider implements
			IStructuredContentProvider {

		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof List) {
				@SuppressWarnings("unchecked")
				List<MessageStarter> starterList = (List<MessageStarter>) inputElement;
				return starterList.toArray();
			}
			return new Object[0];
		}

		public void dispose() {
			// TODO Auto-generated method stub

		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			// TODO Auto-generated method stub

		}

	}
	
	private class MessageStarter{
		private String id;
		private boolean replyTo;
		private boolean consume;
		private EObjectWrapper<EClass, EObject> eObject;

		public MessageStarter(EObjectWrapper<EClass, EObject> eObject, String id, boolean replyTo, boolean consume){
			this.eObject = eObject;
			this.id = id;
			this.replyTo=replyTo;
			this.consume = consume;
		}

		public boolean isReplyTo() {
			return replyTo;
		}

		public void setReplyTo(boolean replyTo) {
			this.replyTo = replyTo;
		}

		public boolean isConsume() {
			return consume;
		}

		public void setConsume(boolean consume) {
			this.consume = consume;
		}
		
		
	}
	

	@Override
	protected void createHelpPartControl(IManagedForm form, Composite parent) {
		FormToolkit toolkit = form.getToolkit();
		helpSection = getWidgetFactory().createSection(parent, Section.TITLE_BAR | Section.EXPANDED);
		helpSection.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		helpSection.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		helpSection.setText(BpmnMessages.getString("helpSection_Label"));
		
//		configureHelpToolBar(helpSection); TODO: later
		
		browserComposite = getWidgetFactory().createComposite(helpSection);
		helpSection.setClient(browserComposite);
		
		browserComposite.setLayout(new GridLayout(1,false));
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.heightHint = 500;
    	browserComposite.setLayoutData(data);

    	createBrowserContentArea(browserComposite);
    	data = new GridData(GridData.FILL_BOTH);
		data.widthHint = 100;
		data.heightHint = 500;
		browser.setLayoutData(data);
	}
	
	protected void createBrowserContentArea(Composite parent) {
		FormToolkit toolkit = new FormToolkit(parent.getDisplay());
		browser = toolkit.createFormText(parent, true);
		browser.setForeground(new Color(Display.getDefault(), 63, 95, 191));
	}
	
}