package com.tibco.cep.bpmn.ui.editors.bpmnPalette;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.forms.DetailsPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.MasterDetailsBlock;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.bpmn.common.palette.model.BpmnCommonPaletteGroupEmfItemType;
import com.tibco.cep.bpmn.common.palette.model.BpmnCommonPaletteGroupItemType;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.cep.bpmn.ui.graph.BpmnImages;
import com.tibco.cep.bpmn.ui.graph.model.BpmnSupportedEmfType;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelChangeListener;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteGroup;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteGroupItem;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteIdGenerator;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteModel;
import com.tibco.cep.bpmn.ui.utils.BpmnPaletteResourceUtil;
import com.tibco.cep.studio.common.palette.EmfType;
import com.tibco.cep.studio.common.palette.Help;
import com.tibco.cep.studio.common.palette.PaletteFactory;
import com.tibco.cep.studio.common.palette.PaletteGroup;
import com.tibco.cep.studio.common.palette.PaletteItem;
import com.tibco.cep.studio.ui.editors.utils.BlockUtil;
import com.tibco.xml.data.primitive.ExpandedName;

/*
 * @author   mgoel
 */

public class BpmnPaletteConfigBlock extends MasterDetailsBlock{
	private Tree trPalette;
	protected TreeViewer viewer;
	protected FormPage page;
//	protected BpmnPaletteConfigurationModel model;
	protected BpmnPaletteConfigurationModelMgr modelmgr;
	protected int EXPAND_LEVEL = 2;
	protected Map <String,BpmnPaletteGroupItem> items;
    protected BpmnPaletteModel bpmnPaletteModel;
    private Button bAdd, bRemove,bUp,bDown;
    private ArrayList<String> filter = new ArrayList<String>();
  
    

	public BpmnPaletteConfigBlock(BpmnPalettePage page,
			BpmnPaletteConfigurationModelMgr mdlmgr) {
		this.page = page;
		this.modelmgr = mdlmgr;
//		this.model=mdlmgr.getModel();
		filter.add("*.gif");
		filter.add("*.png");
	
	}

	public void createContent(IManagedForm managedForm) {
		super.createContent(managedForm);
		sashForm.setWeights(new int[]{35,65});
	}

	public void updateBlock() {
		if (viewer != null){
			StructuredSelection selection = (StructuredSelection)viewer.getSelection();
			refreshViewer(viewer, modelmgr.getModel());
			Object firstElement = selection.getFirstElement();
			if (firstElement instanceof BpmnPaletteGroupItem
					|| firstElement instanceof BpmnPaletteGroup) {
				Object[] findElements = findElements(modelmgr.getModel(), new Object[]{firstElement});
				if (findElements.length == 1 ) {
					viewer.setSelection(new StructuredSelection(findElements[0]));
					detailsPart.getCurrentPage().refresh();
				}
			}
		}
	}
	
	public void refreshViewer(TreeViewer treeViewer, BpmnPaletteConfigurationModel model) {
		if (treeViewer == null || treeViewer.getTree().isDisposed())
			return;
		Object expObjs[] = treeViewer.getExpandedElements();
		Object[] findElements = findElements(model, expObjs);
		//ISelection selection = treeViewer.getSelection();
		bpmnPaletteModel= model.getBpmnPaletteModel();
		treeViewer.setInput(model);
		treeViewer.setExpandedElements(findElements);
		//treeViewer.setSelection(selection); //This is not needed
	}
	
	private Object[] findElements(BpmnPaletteConfigurationModel model, Object expObjs[]){
		List<Object> list =new ArrayList<Object>();
		for (int i = 0; i < expObjs.length; i++) {
			if(expObjs[i] instanceof BpmnPaletteModel){
				if(model.getBpmnPaletteModel() != null)
					list.add(model.getBpmnPaletteModel());
			}else if(expObjs[i] instanceof BpmnPaletteGroup){
				BpmnPaletteGroup pGroup = (BpmnPaletteGroup)expObjs[i];
				BpmnPaletteGroup bpmnPaletteGroup = model.getBpmnPaletteModel().getBpmnPaletteGroup(pGroup.getId());
				if(bpmnPaletteGroup != null)
					list.add(bpmnPaletteGroup);
			}else if(expObjs[i] instanceof BpmnPaletteGroupItem){
				BpmnPaletteGroupItem gItem = (BpmnPaletteGroupItem)expObjs[i];
				String grpId = gItem.getParentBpmnPaletteGroup().getId();
				BpmnPaletteGroup bpmnPaletteGroup = model.getBpmnPaletteModel().getBpmnPaletteGroup(grpId);
				BpmnPaletteGroupItem paletteItem = bpmnPaletteGroup.getPaletteItem(gItem.getId());
				if(paletteItem != null)	
					list.add(paletteItem);
			}
		}
		
		return list.toArray();
	}

	@Override
	protected void createMasterPart(final IManagedForm managedForm, Composite parent) {
		bpmnPaletteModel=modelmgr.getModel().getBpmnPaletteModel();
		FormToolkit toolkit = managedForm.getToolkit();
		Section section = toolkit.createSection(parent, Section.TITLE_BAR);
		section.setText(BpmnMessages.getString("bpmnPaletteConfigBlock_palette_label"));
		//section.setDescription("Define the Cluster configuration.");
		section.marginWidth = 5;
		section.marginHeight = 5;
		Composite client = toolkit.createComposite(section, SWT.WRAP);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 2;
		layout.marginHeight = 2;
		client.setLayout(layout);
		
		trPalette = toolkit.createTree(client, SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_BOTH);;
		gd.heightHint = 20;
		gd.widthHint = 80;
		trPalette.setLayoutData(gd);
		toolkit.paintBordersFor(client);
		

		Composite buttonsClient = new Composite(client, SWT.NONE);
		buttonsClient.setLayout(new GridLayout(1, false));
		buttonsClient.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		addButtons(buttonsClient,parent,toolkit);
		
		
		section.setClient(client);
		final SectionPart spart = new SectionPart(section);
		managedForm.addPart(spart);
		viewer = new TreeViewer(trPalette);
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				try {
					managedForm.fireSelectionChanged(spart, event.getSelection());
				} catch (Throwable e) {
					e.printStackTrace();
				}
				
			}
		});
		Transfer[] types = new Transfer[] {LocalSelectionTransfer.getTransfer() };
    	viewer.addDragSupport(DND.DROP_MOVE,types,new PaletteItemDragListener(viewer,bpmnPaletteModel, modelmgr));
		
		addDoubleClickListener(viewer);
		
		viewer.addDropSupport(DND.DROP_MOVE, types, new ViewerDropAdapter(viewer) {
			
			  private Object nextTo = null;
			@Override
			public boolean validateDrop(Object target, int operation,
					TransferData transferType) {
				StructuredSelection selection = (StructuredSelection)viewer.getSelection();
		        Object firstElement = selection.getFirstElement();
		        BpmnPaletteGroupItem item = null;
				if(firstElement instanceof BpmnPaletteGroupItem){
					
					item = (BpmnPaletteGroupItem)firstElement;
				
				if(target instanceof BpmnPaletteGroup){
					nextTo = target;
					if(item != null && (item.getParentBpmnPaletteGroup() != ((BpmnPaletteGroup)nextTo)))
						return true;
				} else if(target instanceof BpmnPaletteGroupItem){
					nextTo = ((BpmnPaletteGroupItem)target).getParentBpmnPaletteGroup();
					if( item != null && (item.getParentBpmnPaletteGroup() != ((BpmnPaletteGroup)nextTo)))
						return true;
				}
			 }
				return false;
			}
			
			@Override
			public boolean performDrop(Object data) {
				 if (data != null)
			        {
			          StructuredSelection selection = (StructuredSelection)LocalSelectionTransfer.getTransfer().getSelection();
					if (nextTo instanceof BpmnPaletteGroup) {
						BpmnPaletteGroup group = ((BpmnPaletteGroup) nextTo);
						BpmnPaletteGroupItem item =(BpmnPaletteGroupItem) selection.getFirstElement();
						if(item.getParentBpmnPaletteGroup() != group){
							item.getParentBpmnPaletteGroup().removePaletteItem(item.getId());
							group.addPaletteItem(item);
							item.setParentBpmnPaletteGroup(group);
							viewer.refresh();
						}
					}
			          
			        }
			        return true;
			}
		});
		
		
		viewer.setContentProvider(new BpmnPaletteContentProvider());
		viewer.setLabelProvider(new BpmnPaletteLabelProvider());
		viewer.setInput(modelmgr.getModel());
		registerContextMenu(); 
		registerSelectionListener();
		//resetActionButtons();
		BlockUtil.expandViewer(viewer, EXPAND_LEVEL);
		
	}

	private void addButtons(Composite buttonsClient, Composite parent, FormToolkit toolkit) {
		
		bAdd = toolkit.createButton(buttonsClient, BpmnMessages.getString("add_label"), SWT.PUSH);
		bAdd.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		bAdd.addListener(SWT.Selection, getPaletteItemAddListener(parent.getShell()));
		
		bRemove = toolkit.createButton(buttonsClient, BpmnMessages.getString("remove_label"), SWT.PUSH);
		bRemove.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		bRemove.addListener(SWT.Selection, getPaletteItemRemoveListener());
		
		bUp = toolkit.createButton(buttonsClient, BpmnMessages.getString("exclusiveGatewayProp_upButton_label"), SWT.PUSH);
		bUp.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		bUp.addListener(SWT.Selection, getItemMoveUpListener());
		
		bDown = toolkit.createButton(buttonsClient, BpmnMessages.getString("exclusiveGatewayProp_downButton_label"), SWT.PUSH);
		bDown.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		bDown.addListener(SWT.Selection, getItemMoveDownListener());

		enableButtons(null);
		
	}

	private Listener getItemMoveDownListener() {
		Listener listener = new Listener() {
			
			@SuppressWarnings("unused")
			@Override
			public void handleEvent(Event event) {
				if (viewer.getSelection() instanceof IStructuredSelection) {
					IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
					for (Object selObj: selection.toList()) {
					Object ssel = selection.getFirstElement();
					movePaletteItemDown(ssel);
					}
				}
			
			}

		};
		return listener;
	}


	private Listener getItemMoveUpListener() {
		Listener listener = new Listener() {
			
			@SuppressWarnings("unused")
			@Override
			public void handleEvent(Event event) {
				if (viewer.getSelection() instanceof IStructuredSelection) {
					IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
					for (Object selObj: selection.toList()) {
					Object ssel = selection.getFirstElement();
					movePaletteItemUp(ssel);
					}
				}
			
			}
				
			
		};
		return listener;
	}

	private Listener getPaletteItemRemoveListener() {
		Listener listener = new Listener() {
			@SuppressWarnings("unused")
			@Override
			public void handleEvent(Event event) {
				if (viewer.getSelection() instanceof IStructuredSelection) {
					IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
					for (Object selObj: selection.toList()) {
						Object ssel = selection.getFirstElement();
						removePaletteItem(ssel);
					}
				}
			}
		};
		return listener;
	}

	private Listener getPaletteItemAddListener(final Shell shell) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (viewer.getSelection() instanceof IStructuredSelection) {
					IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
					if (selection.size() != 1)
						return;
					Object selObj = selection.getFirstElement();
					if (selObj instanceof BpmnPaletteGroup) {
						addPaletteGroupItem(selObj,shell);
					}else if(selObj  instanceof BpmnPaletteModel){
						addPaletteGroup(selObj,shell);
					}
				}
			}

			
		};
		return listener;
	}


	protected void movePaletteItemDown(Object ssel) {
		if(ssel instanceof BpmnPaletteGroupItem){
			BpmnPaletteGroupItem item = (BpmnPaletteGroupItem) ssel;
			List<BpmnPaletteGroupItem> items = item.getParentBpmnPaletteGroup().getPaletteItems();
			int index = items.indexOf(item);
			if(index < (items.size()-1)) {
				bDown.setEnabled(true);
				item.getParentBpmnPaletteGroup().moveDown(item);
			}else{
				bDown.setEnabled(false);
				
			}
			bUp.setEnabled(true);
			enableButtons(ssel);
			viewer.refresh(item.getParentBpmnPaletteGroup());			
		} else if(ssel instanceof BpmnPaletteGroup){
			BpmnPaletteGroup item = (BpmnPaletteGroup) ssel;
			List<BpmnPaletteGroup> items = item.getParent().getBpmnPaletteGroups();
			int index = items.indexOf(item);
			if(index < (items.size()-1)) {
				bDown.setEnabled(true);
				item.getParent().moveDown(item);
			}else{
				bDown.setEnabled(false);
				
			}
			bUp.setEnabled(true);
			enableButtons(ssel);
			viewer.refresh(item.getParent());				
		}
//		bUp.setEnabled(false);

	}
	
	private boolean canMovePaletteDown(Object sel){
		int index = 0;
		int size = 0;
		if(sel instanceof BpmnPaletteGroupItem){
			BpmnPaletteGroupItem item = (BpmnPaletteGroupItem) sel;
		List<BpmnPaletteGroupItem> items = item.getParentBpmnPaletteGroup().getPaletteItems();
		index= items.indexOf(item);
		size = items.size();
		} else if(sel instanceof BpmnPaletteGroup){
			 BpmnPaletteGroup item = (BpmnPaletteGroup) sel;
			List<BpmnPaletteGroup> items = item.getParent().getBpmnPaletteGroups();
		 index = items.indexOf(item);
		 size = items.size();
		}
		
		return !(index == (size-1));
	}
	
	private boolean canMovePaletteUp(Object sel){
		int index = 0;
		if(sel instanceof BpmnPaletteGroupItem){
			BpmnPaletteGroupItem item = (BpmnPaletteGroupItem) sel;
		List<BpmnPaletteGroupItem> items = item.getParentBpmnPaletteGroup().getPaletteItems();
		index= items.indexOf(item);
		} else if(sel instanceof BpmnPaletteGroup){
			 BpmnPaletteGroup item = (BpmnPaletteGroup) sel;
			List<BpmnPaletteGroup> items = item.getParent().getBpmnPaletteGroups();
		 index = items.indexOf(item);
		}
		
		return !(index == 0);
	}

	private void movePaletteItemUp(Object ssel) {
		if(ssel instanceof BpmnPaletteGroupItem){
			BpmnPaletteGroupItem item = (BpmnPaletteGroupItem) ssel;
			List<BpmnPaletteGroupItem> items = item.getParentBpmnPaletteGroup().getPaletteItems();
			int index = items.indexOf(item);
			if(index != 0) {
				bUp.setEnabled(true);
				item.getParentBpmnPaletteGroup().moveUp(item);
			}else{
				bUp.setEnabled(false);
				bDown.setEnabled(true);
			}
			bDown.setEnabled(true);
			enableButtons(ssel);
			viewer.refresh(item.getParentBpmnPaletteGroup());			
		} else if(ssel instanceof BpmnPaletteGroup){
			BpmnPaletteGroup item = (BpmnPaletteGroup) ssel;
			List<BpmnPaletteGroup> items = item.getParent().getBpmnPaletteGroups();
			int index = items.indexOf(item);
			if(index != 0) {
				bUp.setEnabled(true);
				item.getParent().moveUp(item);
			}else{
				bUp.setEnabled(false);
				bDown.setEnabled(true);
			}
			bDown.setEnabled(true);
			enableButtons(ssel);
			viewer.refresh(item.getParent());			
			
		}
//		bDown.setEnabled(false);
	}

	private void removePaletteItem(Object ssel) {
		if (ssel instanceof BpmnPaletteGroupItem) {
			BpmnPaletteGroupItem item = (BpmnPaletteGroupItem) ssel;
//			items = model.getToolDefinitions().getPaletteToolsetsById(id);
//			items.remove(item.getId());
//			model.getToolDefinitions().setPaletteItems(items);
			item.getParentBpmnPaletteGroup().removePaletteItem(item.getId());
			BlockUtil.refreshViewer(viewer);

		} else if (ssel instanceof BpmnPaletteGroup) {
			BpmnPaletteGroup item = (BpmnPaletteGroup) ssel;

			bpmnPaletteModel.removePaletteGroup(item.getId());
			BlockUtil.refreshViewer(viewer);

		}
		ssel=null;
		enableButtons(ssel);
		//bRemove.setEnabled(false);
	}
	
	private void addPaletteGroupItem(Object selObj, Shell shell) {
		String id = BpmnPaletteIdGenerator.getNextIdForBpmnPaletteItem(bpmnPaletteModel);
		String castAsString = BpmnSupportedEmfType.values()[0].getType().castAsString();
		PaletteItem createPaletteItem = PaletteFactory.eINSTANCE.createPaletteItem();
		EmfType createEmfType = PaletteFactory.eINSTANCE.createEmfType();
		createPaletteItem.setEmfItemType(createEmfType);
		createEmfType.setEmfType(castAsString);
		BpmnPaletteGroupItem item = new BpmnPaletteGroupItem((BpmnPaletteGroup) selObj, createPaletteItem);
		item.setId(id);
		((BpmnPaletteGroup) selObj).addPaletteItem(item);
		modelmgr.getModelChangeAdapterFactory().adapt(item.getItem(), ModelChangeListener.class);
		modelmgr.getModelChangeAdapterFactory().adapt(createEmfType, ModelChangeListener.class);
		item.setTooltip("");
		item.setIcon(((BpmnPaletteGroup)selObj).getIcon());
		item.setTitle(id);
		
		// add help
		BpmnCommonPaletteGroupItemType itemType = item.getItemType();
		if(itemType instanceof BpmnCommonPaletteGroupEmfItemType){
			BpmnCommonPaletteGroupEmfItemType eItemType = (BpmnCommonPaletteGroupEmfItemType)itemType;
			ExpandedName emfType = eItemType.getEmfType();
			ExpandedName extendedType = eItemType.getExtendedType();
			String type = "";
			if(emfType != null)
				type = type + emfType.toString();
			if(extendedType != null)
				type = type + extendedType.toString();
			
			if(!type.trim().isEmpty()){
				Map<String, List<Help>> defultHelpMap = BpmnPaletteResourceUtil.getDefultHelpMap();
				List<Help> list = defultHelpMap.get(type);
				if(list != null){
					for (Help help : list) {
						item.addhelpContent(help);
					}
				}
			}
		}
		
		BlockUtil.refreshViewer(viewer);

	}
	
	private void addPaletteGroup(Object selObj, Shell shell) {
		String id = BpmnPaletteIdGenerator.getNextIdForBpmnPaletteGroup(bpmnPaletteModel);
		PaletteGroup createPaletteGroup= PaletteFactory.eINSTANCE.createPaletteGroup();
		BpmnPaletteGroup item = new BpmnPaletteGroup((BpmnPaletteModel) selObj, createPaletteGroup);
		item.setId(id);
		((BpmnPaletteModel) selObj).addPaletteGroup(item);
		modelmgr.getModelChangeAdapterFactory().adapt(item.getGroup(), ModelChangeListener.class);
		item.setTooltip("Default Tooltip");
		item.setIcon(BpmnImages.DEFAULT_PALETTE_ICON);
		item.setTitle(id);
		BlockUtil.refreshViewer(viewer);
	}

	private void registerContextMenu() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void createToolBarActions(IManagedForm managedForm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void registerPages(DetailsPart detailsPart) {
		detailsPart.registerPage(BpmnPaletteGroupItem.class,
				new BpmnPaletteItemConfigPage(modelmgr,
						viewer));
		detailsPart.registerPage(BpmnPaletteGroup.class,
				new BpmnPaletteItemConfigPage(modelmgr,
						viewer));
	}
	
	protected void addDoubleClickListener(final TreeViewer viewer) {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				BlockUtil.refreshViewer(viewer, obj, null);
			}
		});
	}
	
	protected void registerSelectionListener() {
		ISelectionChangedListener listener = new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				if(viewer.getSelection() instanceof IStructuredSelection) {
					IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
					
					Object selObj = selection.getFirstElement();
					if (selObj != null && selection.size() <= 1) {
						enableButtons(selObj);
					}else if(selection.size() > 1)
					{
						bUp.setEnabled(false);
						bDown.setEnabled(false);
						bAdd.setEnabled(false);
						bRemove.setEnabled(false);
					}
				}
			}
		};
		viewer.addSelectionChangedListener(listener);
	}
	
	
	private void enableButtons(Object selObj){
		if(selObj != null){
			if(selObj instanceof BpmnPaletteGroupItem){
				bUp.setEnabled(canMovePaletteUp(selObj));
				bDown.setEnabled(canMovePaletteDown(selObj));
				bAdd.setEnabled(false);
				bRemove.setEnabled(true);
				return;
			}else if(selObj instanceof BpmnPaletteGroup){
				bUp.setEnabled(canMovePaletteUp(selObj));
				bDown.setEnabled(canMovePaletteDown(selObj));
				bAdd.setEnabled(true);
				bRemove.setEnabled(true);
				return;
			}else if(selObj instanceof BpmnPaletteModel){
				bUp.setEnabled(false);
				bDown.setEnabled(false);
				bAdd.setEnabled(true);
				bRemove.setEnabled(false);
				return;
			}
		}
		
		bAdd.setEnabled(false);
		bRemove.setEnabled(false);
		bUp.setEnabled(false);
		bDown.setEnabled(false);
	}
	public  static class BpmnPaletteContentProvider implements ITreeContentProvider {
		//protected BpmnPaletteConfigurationModel model;
		
	/*	public BpmnPaletteContentProvider(ToolDefinitions tooldef){
			this.model=model;
		}*/

		@Override
		public void dispose() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			
			
		}

		@Override
		public Object[] getElements(Object inputElement) {
			
			return getChildren(inputElement);
			//return PaletteLoader.loadDefault();
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			if(parentElement instanceof BpmnPaletteConfigurationModel){
				return new BpmnPaletteModel[]{((BpmnPaletteConfigurationModel)parentElement).getBpmnPaletteModel()};
			}
			else if(parentElement instanceof BpmnPaletteModel){
				BpmnPaletteModel model = (BpmnPaletteModel)parentElement;
				
			//	model.setName(BpmnPaletteRefactoringParticipant.getModelName(model));
				return (Object[])((model.getBpmnPaletteGroups()).toArray());
			}else if(parentElement instanceof BpmnPaletteGroup){
				List<BpmnPaletteGroupItem> paletteItems=((BpmnPaletteGroup) parentElement).getPaletteItems();
		
				return (Object[])(paletteItems.toArray());
			}
			return new Object[]{};
		}

		@Override
		public Object getParent(Object element) {
			if(element instanceof BpmnPaletteGroupItem){
				return ((BpmnPaletteGroupItem)element).getParentBpmnPaletteGroup();
			}else if(element instanceof BpmnPaletteGroup){
				return ((BpmnPaletteGroup)element).getParent();
			}
			return null;
			
		}

		@Override
		public boolean hasChildren(Object element) {
			// TODO Auto-generated method stub
			return getChildren(element).length > 0;
		}

		
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	public static class BpmnPaletteLabelProvider implements ILabelProvider {

	
		private ArrayList listeners;
		
		public BpmnPaletteLabelProvider() { 
			 listeners = new ArrayList();
		}

		@Override
		public void addListener(ILabelProviderListener listener) {
			listeners.add(listener);
		}

		@Override
		public void dispose() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean isLabelProperty(Object element, String property) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void removeListener(ILabelProviderListener listener) {
			listeners.remove(listener);
		}

		@Override
		public Image getImage(Object element) {
			if(element instanceof BpmnPaletteGroup){
				return BpmnImages.getInstance().getImage(((BpmnPaletteGroup) element).getIcon());
			}else if(element instanceof BpmnPaletteGroupItem){
				return  BpmnImages.getInstance().getImage(((BpmnPaletteGroupItem) element).getIcon());
			}
			return null;
		}

		@Override
		public String getText(Object element) {
			if (element == null)
				return ("");
			if (element instanceof BpmnPaletteModel) {
				return (((BpmnPaletteModel)element).getName());
			} else if(element instanceof BpmnPaletteGroup){
				return ((BpmnPaletteGroup) element).getTitle();
			}else if(element instanceof BpmnPaletteGroupItem){
				return ((BpmnPaletteGroupItem) element).getTitle();
			}
			return null;
		}
		
	}
	

	

}

