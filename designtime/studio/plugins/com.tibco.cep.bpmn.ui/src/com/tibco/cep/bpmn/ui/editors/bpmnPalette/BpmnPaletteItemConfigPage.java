package com.tibco.cep.bpmn.ui.editors.bpmnPalette;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.AssertionFailedException;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.ColorSelector;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IFormPart;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;

import com.tibco.cep.bpmn.common.palette.model.BpmnCommonPaletteGroupEmfItemType;
import com.tibco.cep.bpmn.common.palette.model.BpmnCommonPaletteGroupItemType;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.bpmn.ui.graph.AttachedBEResourceChangeListener;
import com.tibco.cep.bpmn.ui.graph.AttachedBeResourcePanel;
import com.tibco.cep.bpmn.ui.graph.BpmnImages;
import com.tibco.cep.bpmn.ui.graph.model.BpmnSupportedEmfType;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteGroup;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteGroupItem;
import com.tibco.cep.bpmn.ui.utils.BpmnPaletteResourceUtil;
import com.tibco.cep.bpmn.ui.utils.PaletteHelpTextGenerator;
import com.tibco.cep.studio.common.palette.Help;
import com.tibco.cep.studio.ui.doc.ExtendedRichTextEditor;
import com.tibco.cep.studio.ui.doc.RichTextEditorToolkit;
import com.tibco.cep.studio.ui.editors.utils.BlockUtil;
import com.tibco.cep.studio.ui.property.PropertyTypeCombo;
import com.tibco.cep.studio.ui.util.PanelUiUtil;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * 
 * @author mgoel
 *
 */
public class BpmnPaletteItemConfigPage extends BpmnAbstractDetailsPage{
	

	public static final String DIAGRAMCHANGED_COLOR = "diagram.DiagramChangedColor";
	
	BpmnPaletteGroupItem paletteInfo=null;

	
	private ColorSelector textColorSelector;
	
//	protected BpmnPaletteConfigurationModel model;
	protected BpmnPaletteConfigurationModelMgr modelmgr;
	@SuppressWarnings("unused")
	private Label title,tooltip,icon,emfType,colorGrp,trigger,help;
	private Text titleName,tooltipName;
	private PropertyTypeCombo iconName;
	private ComboViewer emfTypeNames,triggerNames,helpNames;
	Group colorComposite;
	@SuppressWarnings("unused")
	private ArrayList<String> extendedType= new ArrayList<String>();
	Label iconimageLabel;

	private org.eclipse.swt.widgets.Button iconbrowsebutton;
	@SuppressWarnings("unused")
	private BpmnPaletteGroup toolset;
	IStructuredSelection selection;
	Object selectedObject;
    boolean refreshing;
    
	private BpmnSupportedEmfType[] suportedEmfTypes;

	private AttachedBeResourcePanel attachedBeResourcePanel;

	private BpmnPaletteConfigurationEditor fEditor;

	private ExtendedRichTextEditor editor;

	private Section helpSection;

	private Composite helpClient;

	public BpmnPaletteGroupItem item;
	private WidgetListener listener1;

	public BpmnPaletteItemConfigPage(BpmnPaletteConfigurationModelMgr modelmgr,TreeViewer viewer) {
		super(viewer);
		this.modelmgr=modelmgr;
//		this.model=modelmgr.getModel();
		suportedEmfTypes = BpmnSupportedEmfType.values();
		listener1 =new WidgetListener();
	}
	
	
	
	@Override
	public void createContents(Composite parent) {
		super.createContents(parent);
		
		selection = (IStructuredSelection)viewer.getSelection();
		selectedObject = ((IStructuredSelection) selection).getFirstElement();
		@SuppressWarnings("unused")
		String paletteToolsetId;
		if(selectedObject instanceof BpmnPaletteGroupItem){
			 item = (BpmnPaletteGroupItem)selectedObject;
			paletteToolsetId=item.getId();
		} else if(selectedObject instanceof BpmnPaletteGroup){
			BpmnPaletteGroup gorup = (BpmnPaletteGroup)selectedObject;
			paletteToolsetId=gorup.getId();
		}
		
		//This is for selecting a user defined icon and getting the label,text box and button on the same line.
		title = PanelUiUtil.createLabel(client, "Title: ");
		titleName = PanelUiUtil.createText(client);
		tooltip= PanelUiUtil.createLabel(client, "Tooltip: ");
		tooltipName = PanelUiUtil.createText(client);
		
		//This is for selecting a user defined icon and getting the label,text box and button on the same line.
		Image image = null;
		if(selectedObject instanceof BpmnPaletteGroupItem){
			BpmnPaletteGroupItem item = (BpmnPaletteGroupItem)selectedObject;
			image= BpmnImages.getInstance().getImage(item.getIcon());
		} else if(selectedObject instanceof BpmnPaletteGroup){
			BpmnPaletteGroup group = (BpmnPaletteGroup)selectedObject;
			image= BpmnImages.getInstance().getImage(group.getIcon());
		}
		iconimageLabel= PanelUiUtil.createLabel(client, "Icon:");
		
		final Composite iconimage=new Composite(client, SWT.None);
		iconimage.setLayout(PanelUiUtil.getCompactGridLayout(2, false));
		iconimage.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		iconName = new PropertyTypeCombo(iconimage, SWT.BORDER | SWT.NONE, false, true);
		iconName.setImage(image);
		iconName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		iconbrowsebutton=PanelUiUtil.createBrowsePushButton(iconimage, tooltipName);
		iconimage.pack();
		iconbrowsebutton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				IconSelectionWizard wizard = new IconSelectionWizard( Messages.getString("bpmn.palette.image.selection.title"), getBpmnPaletteItemConfigPage());
				WizardDialog dialog = new WizardDialog(iconimage.getShell(), wizard) {
					@Override
					protected void createButtonsForButtonBar(Composite parent) {
						super.createButtonsForButtonBar(parent);
						Button finishButton = getButton(IDialogConstants.FINISH_ID);
						finishButton.setText(IDialogConstants.OK_LABEL);
					}
				};
				dialog.create();
				dialog.open();
			}
		});
		titleName.addListener(SWT.Modify, gettitleModifyListener());
		tooltipName.addListener(SWT.Modify, gettooltipModifyListener());

		
		emfType=PanelUiUtil.createLabel(client, "Type:");
		initializeEmfTypesCombo();

		
		trigger=PanelUiUtil.createLabel(client, "Trigger:");
		initializeExtendedTypesCombo();
		toolkit.paintBordersFor(section);
		section.setClient(client);
		final Composite newClient=new Composite(client, SWT.None);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		newClient.setLayout(layout);
		GridData data = new GridData();
		data.horizontalSpan =2;
		newClient.setLayoutData(data);
		createColorGroup(newClient);
		AttachedBEResourceChangeListener listener = new AttachedBEResourceChangeListener() {
			
			@Override
			public void resourceChanged(String newResource) {
				boolean updated = modelmgr.updateAttachResource(selectedObject,newResource);
				if (updated){
					BlockUtil.refreshViewer(viewer);
				}
				
			}
		};
		
		attachedBeResourcePanel = new AttachedBeResourcePanel( listener, modelmgr.getProject() , client, toolkit);
		help = PanelUiUtil.createLabel(client, " Help");
		initializeHelpCombo();
		helpSection = toolkit.createSection(parent, Section.TITLE_BAR | Section.TWISTIE);
		helpSection.marginWidth = 5;
	//	helpSection.setText("");
		
		TableWrapData td = new TableWrapData(TableWrapData.FILL, TableWrapData.TOP);
		td.grabHorizontal = true;
		helpSection.setLayoutData(td);
		helpClient = toolkit.createComposite(helpSection);
		GridLayout glayout = new GridLayout();
		glayout.marginWidth = glayout.marginHeight = 0;
		glayout.numColumns = 2;
		helpClient.setLayout(glayout);
		
		PanelUiUtil.createSpacer(toolkit, helpClient, 2);
		
		toolkit.paintBordersFor(helpSection);
		helpSection.setClient(helpClient);
		helpSection.setExpanded(true);	
		fEditor = modelmgr.getEditor();

		editor = RichTextEditorToolkit.createEditor(helpClient, fEditor.getEditorSite(), false);
		GridData gd = new GridData(GridData.FILL, GridData.BEGINNING, false,true);
		editor.setLayoutData(gd);
		editor.addFocusListener(listener1);
	}
	
	

	private void initializeHelpCombo() {
		helpNames=new ComboViewer(client);
		GridData gridData = new GridData(SWT.BEGINNING);
		gridData.widthHint = 150;
		helpNames.getCombo().setLayoutData(gridData);
		helpNames.setContentProvider((new IStructuredContentProvider(){

			@Override
			public void dispose() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void inputChanged(Viewer viewer, Object oldInput,
					Object newInput) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public Object[] getElements(Object inputElement) {
				if(inputElement instanceof BpmnPaletteGroupItem){
					 List<String> tabs = ((BpmnPaletteGroupItem) inputElement).getAvailableHelpTabs();
					return tabs.toArray();
				}
				return null;
			}
			
		}));
		helpNames.setLabelProvider(new ILabelProvider(){

			@Override
			public void addListener(ILabelProviderListener listener) {
				// TODO Auto-generated method stub
				
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
				// TODO Auto-generated method stub
				
			}

			@Override
			public Image getImage(Object element) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getText(Object element) {
				if(element instanceof String){
					// List<String> tabs = ((BpmnPaletteGroupItem) element).getAvailableHelpTabs();
					return (String) element;
				}
				return null;
			}
			
		});
		helpNames.addSelectionChangedListener(new ISelectionChangedListener(){

			public void selectionChanged(SelectionChangedEvent event) {
				helpSection.setText(helpNames.getCombo().getText());
				String help = PaletteHelpTextGenerator.getHelpText(paletteInfo,helpNames.getCombo().getText()).replace("&nbsp;", "").replace("<ul class=\"noindent\">", "").replace("</ul>", "");
				editor.setText(/*paletteInfo.getHelp(helpNames.getCombo().getText())*/help);
				
			}
			
		});
		if( item != null ) {
		helpNames.setInput(item);
		}
		
	}



	private void initializeEmfTypesCombo(){
		emfTypeNames=new ComboViewer(client);
		GridData gridData = new GridData(SWT.BEGINNING);
		gridData.widthHint = 150;
		emfTypeNames.getCombo().setLayoutData(gridData);
		emfTypeNames.setContentProvider(new IStructuredContentProvider(){

			@Override
			public Object[] getElements(Object inputElement) {
				if(inputElement instanceof BpmnSupportedEmfType[]){
					BpmnSupportedEmfType[] types = (BpmnSupportedEmfType[])inputElement;
					return types;
				}
				return null;
			}

			@Override
			public void dispose() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void inputChanged(Viewer viewer, Object oldInput,
					Object newInput) {
				// TODO Auto-generated method stub
				
			}
			
		});
		emfTypeNames.setLabelProvider(new ILabelProvider(){

			@Override
			public Image getImage(Object element) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getText(Object element) {
				if(element instanceof BpmnSupportedEmfType)
					return ((BpmnSupportedEmfType)element).getType().localName;
					
				return null;
			}

			@Override
			public void addListener(ILabelProviderListener listener) {
				// TODO Auto-generated method stub
				
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
				// TODO Auto-generated method stub
				
			}

		});

		emfTypeNames.addSelectionChangedListener(new ISelectionChangedListener(){

			public void selectionChanged(SelectionChangedEvent event) {
				Object firstElement = ((IStructuredSelection)event.getSelection()).getFirstElement();
				if(firstElement instanceof BpmnSupportedEmfType){
					BpmnSupportedEmfType type = (BpmnSupportedEmfType)firstElement;
					ExpandedName[] extendedTypes = type.getExtendedTypes();
					if(extendedTypes != null && extendedTypes.length >0){
						trigger.setVisible(true);
						triggerNames.getCombo().setVisible(true);
						triggerNames.setInput(firstElement);
					}else{
						trigger.setVisible(false);
						triggerNames.getCombo().setVisible(false);
						triggerNames.setInput(null);
					}
					boolean updated = modelmgr.updateEMFType(selectedObject,type.getType().castAsString());
					if(selectedObject instanceof BpmnPaletteGroupItem){
						BpmnPaletteGroupItem grItem = (BpmnPaletteGroupItem)selectedObject;
						updateExtendedType((BpmnCommonPaletteGroupEmfItemType)grItem.getItemType());
						if(!refreshing)
							updateHelpContent(grItem);
					}
					if (updated){
						paletteInfo.setAttachedResource("");
						attachedBeResourcePanel.selectionChanged(selectedObject);
						BlockUtil.refreshViewer(viewer);
					}
					
				}
				
			}
			
		});
		emfTypeNames.setInput(suportedEmfTypes);
	}
	
	private void initializeExtendedTypesCombo(){
		triggerNames=new ComboViewer(client);
		GridData gridData = new GridData(SWT.BEGINNING);
		gridData.widthHint = 150;
		triggerNames.getCombo().setLayoutData(gridData);
		triggerNames.setContentProvider(new IStructuredContentProvider(){

			@Override
			public Object[] getElements(Object inputElement) {
				if(inputElement instanceof BpmnSupportedEmfType){
					BpmnSupportedEmfType type = (BpmnSupportedEmfType)inputElement;
					return type.getExtendedTypes();
				}
				return null;
			}

			@Override
			public void dispose() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void inputChanged(Viewer viewer, Object oldInput,
					Object newInput) {
				// TODO Auto-generated method stub
				
			}
			
		});
		triggerNames.setLabelProvider(new ILabelProvider(){

			@Override
			public Image getImage(Object element) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getText(Object element) {
				if(element instanceof ExpandedName)
					return ((ExpandedName)element).localName;
					
				return null;
			}

			@Override
			public void addListener(ILabelProviderListener listener) {
				// TODO Auto-generated method stub
				
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
				// TODO Auto-generated method stub
				
			}

		});

		triggerNames.addSelectionChangedListener(new ISelectionChangedListener(){

			public void selectionChanged(SelectionChangedEvent event) {
				Object firstElement = ((IStructuredSelection)event.getSelection()).getFirstElement();
				if(firstElement instanceof ExpandedName){
					ExpandedName type = (ExpandedName)firstElement;
					String toUpdate = type.castAsString();
					if(type.equals(BpmnSupportedEmfType.None_Extended_Type))
						toUpdate = null;
					boolean updated = modelmgr.updateExtendedType(selectedObject,toUpdate);
					if (updated){
						if(selectedObject instanceof BpmnPaletteGroupItem)
						updateHelpContent((BpmnPaletteGroupItem)selectedObject);
						paletteInfo.setAttachedResource("");
						attachedBeResourcePanel.selectionChanged(selectedObject);
						BlockUtil.refreshViewer(viewer);
					}
				}
				
			}
			
		});
		trigger.setVisible(false);
		triggerNames.getCombo().setVisible(false);
	}
	
	private Listener gettooltipModifyListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				boolean updated = modelmgr.updateTooltipName(selectedObject,tooltipName.getText());
				if (updated){
					BlockUtil.refreshViewer(viewer);
				}
			}
		};
		return listener;
	}

	private Listener gettitleModifyListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if(modelmgr.validate(selectedObject, titleName.getText())){
				boolean updated = modelmgr.updateTitleName(selectedObject,titleName.getText());
				if (updated){
					BlockUtil.refreshViewer(viewer);
				}
				}
			}
		};
		return listener;
	
	}

	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		@SuppressWarnings("unused")
		IStructuredSelection ssel = (IStructuredSelection)selection;
		selectedObject = ((IStructuredSelection) selection).getFirstElement();
		
		if (selectedObject != null) {
			if(selectedObject instanceof BpmnPaletteGroupItem){
				paletteInfo=(BpmnPaletteGroupItem) selectedObject;
				emfType.setVisible(true);
				Object firstElement = ((IStructuredSelection)emfTypeNames.getSelection()).getFirstElement();
				if(firstElement instanceof BpmnSupportedEmfType){
					BpmnSupportedEmfType type = (BpmnSupportedEmfType)firstElement;
					ExpandedName[] extendedTypes = type.getExtendedTypes();
					if(extendedTypes != null && extendedTypes.length >0){
						trigger.setVisible(true);
						triggerNames.getCombo().setVisible(true);
						triggerNames.setInput(firstElement);
					}else{
						trigger.setVisible(false);
						triggerNames.getCombo().setVisible(false);
						triggerNames.setInput(null);
					}
				}

				update();
			}else{
				trigger.setVisible(false);
				triggerNames.getCombo().setVisible(false);
				emfType.setVisible(false);
				emfTypeNames.getCombo().setVisible(false);
				setVisible(false);
				update();
			}
	//		editor.addFocusListener(listener1);
		}
	}

	@Override
	public void dispose() {
	//	editor.removeFocusListener(listener1);
		super.dispose();
	}



	public void update() {
		if(selectedObject == null)
			return;
		try {
		refreshing = true;
		 if(selectedObject instanceof BpmnPaletteGroupItem){
			 paletteInfo=(BpmnPaletteGroupItem) selectedObject;
			 if(paletteInfo!=null){
				 	helpSection.setText("");
				 	BpmnCommonPaletteGroupEmfItemType itemType = (BpmnCommonPaletteGroupEmfItemType)paletteInfo.getItemType();
					List<String> tabs = ((BpmnPaletteGroupItem) paletteInfo).getAvailableHelpTabs();
					
				 	String localName = itemType.getEmfType().localName;
				 	
				 	BpmnSupportedEmfType bpmnSupportedEmfType = BpmnSupportedEmfType.getSupportedTypeMap().get(localName);
				 	if(bpmnSupportedEmfType != null)
				 		emfTypeNames.setSelection(new StructuredSelection(bpmnSupportedEmfType));
				 	
					titleName.setText(paletteInfo.getTitle());
					tooltipName.setText(paletteInfo.getTooltip());
					iconName.setImage(BpmnImages.getInstance().getImage(paletteInfo.getIcon()));
					iconName.setText(paletteInfo.getIcon());
					if(bpmnSupportedEmfType.name().equalsIgnoreCase("Note") || bpmnSupportedEmfType.name().equalsIgnoreCase("Association") ||
							bpmnSupportedEmfType.name().equalsIgnoreCase("Sequence")){
						setVisible(false);
						
					} else {
						setVisible(true);
					}
					//if(paletteInfo.getId())
					if(textColorSelector.getColorValue() != paletteInfo.getColor()){
					textColorSelector.setColorValue(paletteInfo.getColor());
					}
					String[] tabsarr = new String[tabs.size()] ;
					java.util.Iterator<String> iterator = tabs.iterator();
					int i = 0 ;
					while ( iterator.hasNext() ) {
						
						tabsarr[i++] = (String) iterator.next();
					}
					helpNames.getCombo().setItems(tabsarr);
					helpNames.getCombo().select(0);
					helpSection.setText(helpNames.getCombo().getText());
				//	helpNames.setSelection(new StructuredSelection(tabsarr[0]));
					String help = PaletteHelpTextGenerator.getHelpText(paletteInfo,tabsarr[0]).replace("&nbsp;", "").replace("<ul class=\"noindent\">", "").replace("</ul>", "");
					editor.setText(/*paletteInfo.getHelp(tabsarr[0])*/help);
					updateExtendedType(itemType);
				 	

				}
				
			} else if(selectedObject instanceof BpmnPaletteGroup){
				BpmnPaletteGroup paletteToolsetInfo=(BpmnPaletteGroup) selectedObject;
				titleName.setText( paletteToolsetInfo.getTitle());
				tooltipName.setText( paletteToolsetInfo.getTooltip());
				iconName.setImage(BpmnImages.getInstance().getImage( paletteToolsetInfo.getIcon()));
				iconName.setText(paletteToolsetInfo.getIcon());	
				setVisible(false);
				help.setVisible(false);
				helpNames.getCombo().setVisible(false);
				helpSection.setVisible(false);
			}
		
		
		 attachedBeResourcePanel.selectionChanged(selectedObject);
		 
		 refreshing = false;
		 }catch(AssertionFailedException e){
		 		
		 	}
	}
	
	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		super.refresh();
		update();
	}
	
	private void updateExtendedType(BpmnCommonPaletteGroupEmfItemType itemType) {

		ExpandedName extendedType = itemType.getExtendedType();
		if (extendedType != null)
			triggerNames.setSelection(new StructuredSelection(extendedType));
		else
			triggerNames.setSelection(new StructuredSelection(
					BpmnSupportedEmfType.None_Extended_Type));
	}
	
	private void updateHelpContent(BpmnPaletteGroupItem it){
		BpmnCommonPaletteGroupItemType itemType = it.getItemType();
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
						it.addhelpContent(help);
					}
				}
			}
			
			String text = helpNames.getCombo().getText();
			if(!text.trim().isEmpty()){
				String h = /*paletteInfo.getHelp(text)*/PaletteHelpTextGenerator.getHelpText(paletteInfo,text).replace("&nbsp;", "").replace("<ul class=\"noindent\">", "").replace("</ul>", "");;
				if(h != null)
					editor.setText(h);
			}
				
				
		}
	}

	protected void createColorGroup(Composite parent) {
	    colorComposite = new Group(parent, SWT.NULL);
		colorComposite.setText(Messages.getString("COLOR"));
		colorComposite.setLayout(new GridLayout(1, false));
		GridData data = new GridData(SWT.CENTER);
		colorComposite.setLayoutData(data);
		Composite group = new Composite(colorComposite, SWT.NULL);
		GridData groupData = new GridData(GridData.FILL_HORIZONTAL);
		group.setLayoutData(groupData);
		group.setLayout(new GridLayout());
		textColorSelector = new ColorSelector(group);
		textColorSelector.setColorValue(new RGB(210, 20, 200));
		textColorSelector.addListener(new IPropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				RGB rgb = textColorSelector.getColorValue();
				if(selectedObject instanceof BpmnPaletteGroupItem){
					paletteInfo.setColor(rgb);
				}else if(selectedObject instanceof BpmnPaletteGroup){
				((BpmnPaletteGroup) selectedObject).setColor(rgb);
				}
				
			}
		});
	
	
	}
	private void setVisible(boolean visible){
		textColorSelector.getButton().setVisible(visible);
		colorComposite.setVisible(visible);
		
	}
	
	public BpmnPaletteItemConfigPage getBpmnPaletteItemConfigPage() {
		return this;
	}
	
	public BpmnPaletteConfigurationModelMgr getBpmnPaletteConfigurationModelMgr() {
		return modelmgr;
	}
	
	
	public Object getSelectedObject () {
		return selectedObject;
	}
	private class WidgetListener implements FocusListener {

		@Override
		public void focusGained(FocusEvent e) {
		}

		@Override
		public void focusLost(FocusEvent e) {

			String text = editor.getText();
			String tabSelected= helpNames.getCombo().getText();
			if(!paletteInfo.getHelp(tabSelected).equals(text)){
					paletteInfo.setHelp(tabSelected, text);
				}
		}
	}
	
}