package com.tibco.cep.bpmn.ui.wizards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.cep.bpmn.ui.editors.bpmnPalette.BpmnPaletteConfigBlock.BpmnPaletteContentProvider;
import com.tibco.cep.bpmn.ui.editors.bpmnPalette.BpmnPaletteConfigBlock.BpmnPaletteLabelProvider;
import com.tibco.cep.bpmn.ui.graph.BpmnImages;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteGroup;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteGroupItem;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteIdGenerator;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteModel;
import com.tibco.cep.bpmn.ui.utils.BpmnPaletteResourceUtil;
import com.tibco.cep.studio.common.palette.EmfType;
import com.tibco.cep.studio.common.palette.Help;
import com.tibco.cep.studio.common.palette.JavaType;
import com.tibco.cep.studio.common.palette.ModelType;
import com.tibco.cep.studio.common.palette.PaletteFactory;
import com.tibco.cep.studio.common.palette.PaletteGroup;
import com.tibco.cep.studio.common.palette.PaletteItem;
import com.tibco.cep.studio.common.palette.PaletteModel;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;


/**
 * 
 * @author majha
 *
 */
public class NewBpmnPaletteDetailPage extends WizardPage{

	private static final String Default_ToolSet_Name = "Default";
	private static final String Default_ToolSet_Icon = "com.tibco.cep.bpmn.ui.utils.images.default";
	private CheckboxTreeViewer paletteItemList;
	private Button overrideCheckBox;
	private BpmnPaletteModel defaultModel;
	private BpmnPaletteModel newModel;
	
	public NewBpmnPaletteDetailPage() {
		super("NewBpmnPaletteFileWizardPage");
		setTitle(Messages.getString("new.bpmnpalette.wizard.title"));
        setDescription(Messages.getString("new.bpmnpalette.wizard.desc"));
        setImageDescriptor(EditorsUIPlugin.getImageDescriptor("icons/palette.gif"));
        loadDefaultPalette();
	}
	
	private void loadDefaultPalette(){
		try {
			  defaultModel = BpmnPaletteResourceUtil.loadDefault();
			  PaletteModel createPaletteModel = PaletteFactory.eINSTANCE.createPaletteModel();
			  newModel = new BpmnPaletteModel(createPaletteModel);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				BpmnUIPlugin.log(e);
			}
	}
	
	private BpmnPaletteGroup copyPaletteToolSet(BpmnPaletteGroup paletteToolSet,
			boolean includePaletteItem) {
		PaletteGroup createPaletteGroup = PaletteFactory.eINSTANCE
				.createPaletteGroup();
		BpmnPaletteGroup group = new BpmnPaletteGroup(newModel,
				createPaletteGroup);
		group.setIcon(paletteToolSet.getIcon());
		group.setVisible(true);
		group.setTooltip(paletteToolSet.getTooltip());
		group.setTitle(paletteToolSet.getTitle());
		boolean selection = overrideCheckBox == null? true :overrideCheckBox.getSelection();
		if (selection) {
			String nextIdForBpmnPaletteGroup = BpmnPaletteIdGenerator.getNextIdForBpmnPaletteGroup(newModel);
			group.setId(nextIdForBpmnPaletteGroup);	
		}else{
			group.setId(paletteToolSet.getId());	
		}
		
		newModel.addPaletteGroup(group);
		if (includePaletteItem) {
			List<BpmnPaletteGroupItem> paletteItems = group.getPaletteItems();
			Iterator<BpmnPaletteGroupItem> iterator = paletteItems.iterator();
			while (iterator.hasNext()) {
				BpmnPaletteGroupItem item = iterator.next();
				copyPaletteItem(group, item);
			}
		}
		return group;
	}
	
	private void copyPaletteItem(BpmnPaletteGroup toGroup, BpmnPaletteGroupItem item){
		PaletteItem createPaletteItem = PaletteFactory.eINSTANCE.createPaletteItem();
		copyItemType(createPaletteItem, item);
		BpmnPaletteGroupItem paletteItem =  new BpmnPaletteGroupItem(toGroup, createPaletteItem);
		boolean selection = overrideCheckBox == null? true :overrideCheckBox.getSelection();
		if (selection) {
			String nextIdForBpmnPaletteItem = BpmnPaletteIdGenerator
					.getNextIdForBpmnPaletteItem(newModel);
			paletteItem.setId(nextIdForBpmnPaletteItem);
		}else{
			paletteItem.setId(item.getId());
		}
		paletteItem.setTitle(item.getTitle());
		paletteItem.setIcon(item.getIcon());
		paletteItem.setTooltip(item.getTooltip());
		paletteItem.setParentBpmnPaletteGroup(toGroup);
		EList<Help> helps = item.getItem().getHelpContent();
		for( Help help : helps){
		    paletteItem.addhelpContent(help);
		}
		toGroup.addPaletteItem(paletteItem);
	}
	
	private void copyItemType(PaletteItem toItem, BpmnPaletteGroupItem item){
		if(item.getItem().getEmfItemType()!= null){
			EmfType createEmfType = PaletteFactory.eINSTANCE.createEmfType();
			createEmfType.setEmfType(item.getItem().getEmfItemType().getEmfType());
			createEmfType.setExtendedType(item.getItem().getEmfItemType().getExtendedType());
			toItem.setEmfItemType(createEmfType);
		}else if(item.getItem().getJavaItemType()!= null){
			JavaType createJavaType = PaletteFactory.eINSTANCE.createJavaType();
			createJavaType.getType().addAll((item.getItem().getJavaItemType().getType()));
			toItem.setJavaItemType(createJavaType);
		}else if(item.getItem().getModelItemType()!= null){
			ModelType createModelType = PaletteFactory.eINSTANCE.createModelType();
			EList<String> type = item.getItem().getModelItemType().getType();
			createModelType.getType().addAll(type);
			toItem.setModelItemType(createModelType);
		}
	}
	
	 protected boolean validatePage() {
	    	return paletteItemList.getTree().isVisible();
	    }

	@Override
	public void createControl(Composite parent) {
		Composite createProjectsList = createProjectsList(parent);
		setControl(createProjectsList);
	}
	
	private Composite createProjectsList(Composite workArea) {
		Composite listComposite = new Composite(workArea, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginWidth = 0;
		layout.makeColumnsEqualWidth = false;
		listComposite.setLayout(layout);

		listComposite.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.GRAB_VERTICAL | GridData.FILL_HORIZONTAL));

		Label title = new Label(listComposite, SWT.NONE);
		title.setText(BpmnMessages.getString("bpmnPaletteDetailPage_title_label"));
		GridData gd = new GridData();
		gd.horizontalSpan =2;
		title.setLayoutData(gd);
		
		paletteItemList = new CheckboxTreeViewer(listComposite, SWT.BORDER);
		GridData listData = new GridData(GridData.FILL_HORIZONTAL);
		listData.heightHint = 350;
		paletteItemList.getControl().setLayoutData(listData);

		paletteItemList.setContentProvider(new BpmnPaletteContentProvider(){
			public Object[] getChildren(Object parentElement) {
				if(parentElement instanceof BpmnPaletteModel){
					return (Object[])((((BpmnPaletteModel) parentElement).getBpmnPaletteGroups(false)).toArray());
				}else if(parentElement instanceof BpmnPaletteGroup){
					List<BpmnPaletteGroupItem> itemRef=(((BpmnPaletteGroup) parentElement).getPaletteItems(false));
					Iterator<BpmnPaletteGroupItem> iterator=itemRef.iterator();
					ArrayList<BpmnPaletteGroupItem> paletteItem=new ArrayList<BpmnPaletteGroupItem>();
					while(iterator.hasNext()){
						paletteItem.add(iterator.next());
						
					}
					return (Object[])(paletteItem.toArray());
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
		});
		paletteItemList.setLabelProvider(new BpmnPaletteLabelProvider(){
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
					return ((BpmnPaletteModel)element).getName();
				} else if(element instanceof BpmnPaletteGroup){
					return ((BpmnPaletteGroup) element).getTitle();
				}else if(element instanceof BpmnPaletteGroupItem){
					return ((BpmnPaletteGroupItem) element).getTitle();
				}
				return null;
			}
		});
		paletteItemList.setInput(defaultModel);
		paletteItemList.expandAll();
		paletteItemList.setCheckedElements(getTreeModelList());
		
		paletteItemList.addCheckStateListener(new ICheckStateListener() {
			public void checkStateChanged(CheckStateChangedEvent event) {
				refreshTreeSelections(event.getElement(), event.getChecked());
				setPageComplete(paletteItemList.getCheckedElements().length > 0);
//				paletteItemList.
			}
		});

		setPageComplete(true);
		createSelectionButtons(listComposite);
		
		createOverrideCheckBox(listComposite);
		return listComposite;
	}
	
	private void createOverrideCheckBox(Composite workArea) {
		boolean showCheckBox = false;
		String overrideString = System.getProperty("com.tibco.bpmn.palette.show.override.checkbox", "false");
		try {
			showCheckBox = Boolean.parseBoolean(overrideString);
		} catch (Exception e) {
			showCheckBox = false;
		}
		if(showCheckBox){
			overrideCheckBox = new Button(workArea,SWT.CHECK);
			overrideCheckBox.setText(BpmnMessages.getString("bpmnPaletteDetailPage_overrideCheckBox_label"));
			overrideCheckBox.setSelection(true);
		}

	}
	
	private void refreshTreeSelections(Object element, boolean state) {
		if(element instanceof BpmnPaletteGroup){
			BpmnPaletteGroup toolSet = (BpmnPaletteGroup)element;
			List<BpmnPaletteGroupItem> itemRefs = toolSet.getPaletteItems(false);
			for (BpmnPaletteGroupItem ref : itemRefs) {
				paletteItemList.setChecked(ref, state);
			}
		}else if(element instanceof BpmnPaletteGroupItem){
			BpmnPaletteGroupItem pItem = (BpmnPaletteGroupItem)element;
			BpmnPaletteGroup toolSet = pItem.getParentBpmnPaletteGroup();
			List<BpmnPaletteGroupItem> itemRefs = toolSet.getPaletteItems(false);
			int n = itemRefs.size();
			int n1 = 0;
			for (BpmnPaletteGroupItem ref : itemRefs) {
				boolean checked = paletteItemList.getChecked(ref);
				if(checked == state){
					n1++;
				}
			}
			if(n==n1){
				paletteItemList.setChecked(toolSet, state);
			}else if(!state)
				paletteItemList.setChecked(toolSet, false);
		}
	}
	
	private Object[] getTreeModelList(){
		List<Object> modelList = new ArrayList<Object>();
		List<BpmnPaletteGroup>  bpmnPaletteGroups = defaultModel.getBpmnPaletteGroups(false);
		modelList.addAll(bpmnPaletteGroups);
		for (BpmnPaletteGroup bpmnPaletteGroup : bpmnPaletteGroups) {
			modelList.addAll(bpmnPaletteGroup.getPaletteItems(false));
		}
		return modelList.toArray();
	}
	

	
	private void createSelectionButtons(Composite listComposite) {
		Composite buttonsComposite = new Composite(listComposite, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		buttonsComposite.setLayout(layout);

		buttonsComposite.setLayoutData(new GridData(
				GridData.VERTICAL_ALIGN_BEGINNING));

		Button selectAll = new Button(buttonsComposite, SWT.PUSH);
		selectAll.setText(BpmnMessages.getString("selectAll_label"));
		selectAll.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				paletteItemList.setCheckedElements(getTreeModelList());
			}
		});
		Dialog.applyDialogFont(selectAll);
		setButtonLayoutData(selectAll);

		Button deselectAll = new Button(buttonsComposite, SWT.PUSH);
		deselectAll.setText(BpmnMessages.getString("delectAll_label"));
		deselectAll.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				paletteItemList.setCheckedElements(new Object[0]);
			}
		});
		Dialog.applyDialogFont(deselectAll);
		setButtonLayoutData(deselectAll);
	}
		

	
	public BpmnPaletteModel getNewModel() {
		IWizardPage pPage = getPreviousPage();
		String paletteName = "Bpmn palette";
		if (pPage instanceof NewBpmnPaletteNamePage) {
			paletteName = ((NewBpmnPaletteNamePage) pPage).getFileName();
		}
		int indexOf = paletteName.indexOf(".");
		paletteName = paletteName.substring(0, indexOf);
		newModel.setName(paletteName);
		
		populateNewModel();
		return newModel;
	}
	
	private void populateNewModel(){
		Map<String, BpmnPaletteGroupItem> selectedItemsMap = new HashMap<String, BpmnPaletteGroupItem>();
		List<BpmnPaletteGroup> selectedToolSetList = new ArrayList<BpmnPaletteGroup>();
		Object[] checkedElements = paletteItemList.getCheckedElements();
		for (Object object : checkedElements) {
			if(object instanceof BpmnPaletteGroupItem){
				BpmnPaletteGroupItem item = (BpmnPaletteGroupItem)object;
				selectedItemsMap.put(item.getId(), item);
			}else if(object instanceof BpmnPaletteGroup){
				BpmnPaletteGroup toolSet = (BpmnPaletteGroup)object;
				selectedToolSetList.add(toolSet);
			}
		}
		
		for (BpmnPaletteGroup toolSet : selectedToolSetList) {
			BpmnPaletteGroup copyPaletteToolSet = copyPaletteToolSet(toolSet, false);
			List<BpmnPaletteGroupItem> paletteItems = toolSet.getPaletteItems();
			for (BpmnPaletteGroupItem item : paletteItems) {
				BpmnPaletteGroupItem paletteItem = selectedItemsMap.remove(item.getId());
				if(paletteItem != null){
					BpmnPaletteGroup bpmnPaletteGroup = newModel.getBpmnPaletteGroup(copyPaletteToolSet.getId());
					copyPaletteItem(bpmnPaletteGroup, paletteItem);
				}
			}
		}
		
		if(selectedToolSetList.size() == 0 || selectedItemsMap.size() >0){
			PaletteGroup createPaletteGroup = PaletteFactory.eINSTANCE
					.createPaletteGroup();
			BpmnPaletteGroup defaultPaletteGroup = new BpmnPaletteGroup( newModel, createPaletteGroup );
			newModel.addPaletteGroup(defaultPaletteGroup);
			String nextIdForBpmnPaletteGroup = BpmnPaletteIdGenerator.getNextIdForBpmnPaletteGroup(newModel);
			defaultPaletteGroup.setId(nextIdForBpmnPaletteGroup);
			defaultPaletteGroup.setTitle(Default_ToolSet_Name);
			defaultPaletteGroup.setTooltip(Default_ToolSet_Name);
			defaultPaletteGroup.setIcon(Default_ToolSet_Icon);
			Set<String> keySet = selectedItemsMap.keySet();
			for (String string : keySet) {
				BpmnPaletteGroupItem paletteItem = selectedItemsMap.get(string);
				copyPaletteItem(defaultPaletteGroup, paletteItem);
			}
		}
	}


}
