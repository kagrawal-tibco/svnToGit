package com.tibco.cep.studio.ui.editors.events;

import static com.tibco.cep.studio.ui.util.StudioUIUtils.addHyperLinkFieldListener;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.createLinkField;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.getSubEventProperties;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import com.tibco.cep.designtime.core.model.ModelPackage;
import com.tibco.cep.designtime.core.model.TIMEOUT_UNITS;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.event.EventPackage;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.designtime.core.model.service.channel.DriverConfig;
import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.studio.common.util.TimeOutUnitsUtils;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.GvUtil;
import com.tibco.cep.studio.core.validation.DefaultResourceValidator;
import com.tibco.cep.studio.ui.actions.EntityRenameElementAction;
import com.tibco.cep.studio.ui.editors.utils.DeclarationSelector;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.forms.AbstractEventFormViewer;
import com.tibco.cep.studio.ui.forms.components.DestinationSelector;
import com.tibco.cep.studio.ui.util.GvField;
import com.tibco.cep.studio.ui.util.GvUiUtil;
import com.tibco.cep.studio.ui.util.PanelUiUtil;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

/**
 *
 * @author sasahoo
 *
 */
public class EventFormDesignViewer extends AbstractEventFormViewer {

	private Text eventDescText;
	private Text inheritsText;
	
	private Button retryOnException;

	private GvField ttlText;
	private Text defaultDestText;
	private Combo ttlUnitCombo;

	private Destination destination;
	private String oldChannelURI;
	private String newChannelURI;

	protected SourceViewer actionsSourceViewer;
	
	private Button browseInheritsButton;
	private Button browseDestButton;
	
	private Hyperlink inheritsLink;
	private Hyperlink destlink;
	
	private Map<String,String> gvTypeFieldMap=new LinkedHashMap<String,String>();
	

	public static final Font swtFont = new Font(Display.getDefault(), "Courier New", 10, SWT.NULL);
	private static final int MAXIMUM_TTL_DAYS_VALUE = 25;
    
	private boolean isDestinationBrowse =  false;
	/**
	 * @param editor
	 */
	public EventFormDesignViewer(EventFormEditor editor) {
		this.editor = editor;
		initialiseGvFieldTypeMap();
		if (editor != null && editor.getEditorInput() instanceof  EventFormEditorInput) {
			this.event = ((EventFormEditorInput) editor.getEditorInput()).getSimpleEvent();
		} else{
			this.event = editor.getSimpleEvent();
		}
	}
	
	private void initialiseGvFieldTypeMap() {
        gvTypeFieldMap.put("ttlText", "Integer");
 	}
	
	public void validateFieldsForGv() {
		validateField((Text)ttlText.getGvText(),gvTypeFieldMap.get("ttlText"), event.getTtl(), "ttlText", editor.getProject().getName());
	}

	/**
	 * @param container
	 */
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractEventFormViewer#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createPartControl(Composite container) {
		super.createPartControl(container);
		
//		if (enableMetadataConfigurationEnabled())
//			sashForm.setWeights(defaultWeightPropertySections); // setting the default weights for the available sections.


		retryOnException.setSelection(event.isRetryOnException());

		if(event.getDescription()!=null){
			eventDescText.setText(event.getDescription());
		
		}
		
		ttlText.setValue(event.getTtl());
		if(event.getSuperEventPath()!=null){
			inheritsText.setText(event.getSuperEventPath());
			
		}
		StringBuilder sb = new StringBuilder();

		oldChannelURI = event.getChannelURI();
		if (oldChannelURI != null) {
			defaultDestText.setText(sb.append(oldChannelURI).append("/").append(event.getDestinationName()).toString());
			
		}
		ttlUnitCombo.setText(event.getTtlUnits().getName());
		/*if(event != null){
			fetchChildren(event);
		}*/
		//Making readonly widgets
		if(!getEditor().isEnabled()){
			readOnlyWidgets();
		}
		
		
	}

	@Override
	protected void createGeneralPart(final ScrolledForm form, final FormToolkit toolkit) {

		Section section = toolkit.createSection(form.getBody(), Section.TITLE_BAR| Section.EXPANDED | Section.TWISTIE);
		section.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		section.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));

		section.setText(Messages.getString("GENERAL_SECTION_TITLE"));

		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		section.setLayoutData(gd);

		Composite sectionClient = toolkit.createComposite(section);
		section.setClient(sectionClient);

		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		sectionClient.setLayout(layout);
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		layoutData.heightHint = 50;
		sectionClient.setLayoutData(layoutData);
		
		Composite temprepeatEveryComposite = toolkit.createComposite(sectionClient);
		GridLayout layout1 = new GridLayout();
		layout1.numColumns = 2;
		//layout = new GridLayout(2,false);
		layout1.marginWidth = 0;
		layout1.marginHeight = 0;
		temprepeatEveryComposite.setLayout(layout1);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		temprepeatEveryComposite.setLayoutData(gd);
		

		toolkit.createLabel(temprepeatEveryComposite, Messages.getString("Description")+ "              ",  SWT.NONE).setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		eventDescText = toolkit.createText(temprepeatEveryComposite,"", SWT.MULTI | SWT.V_SCROLL | SWT.WRAP | SWT.BORDER);
		eventDescText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				onModifyEventDescription(e);
			}
		});
		GridData condescgd = new GridData(/*GridData.FILL_HORIZONTAL*/);
//		condescgd.horizontalSpan=2;
		condescgd.widthHint = 600;
		condescgd.heightHint = 30;
		eventDescText.setTextLimit(180);
		eventDescText.setLayoutData(condescgd);

//		toolkit.createLabel(sectionClient, Messages.getString("InheritsFrom"),  SWT.NONE);
		inheritsLink = createLinkField(toolkit, temprepeatEveryComposite, Messages.getString("InheritsFrom"));
		
		Composite inheritComposite = toolkit.createComposite(temprepeatEveryComposite);
		layout = new GridLayout(2,false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		inheritComposite.setLayout(layout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		inheritComposite.setLayoutData(gd);
		
		//inheritsText = toolkit.createText(sectionClient,"", SWT.SINGLE | SWT.BORDER);
		inheritsText = toolkit.createText(inheritComposite,"", SWT.SINGLE | SWT.BORDER);
		GridData intextgd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		intextgd.widthHint = 245;
		inheritsText.setLayoutData(intextgd);
		//inheritsText.setSize(100,20);
		inheritsText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				onModifyInheritsText(e);
			}
		});
		
		addHyperLinkFieldListener(inheritsLink, inheritsText, editor, editor.getProject().getName(), false, false);
		
		browseInheritsButton = new Button(inheritComposite, SWT.NONE);
		browseInheritsButton.setText(Messages.getString("Browse"));
		browseInheritsButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try{
					if(EditorUtils.saveEventEditor(editor.getSite().getPage(), editor.getProject().getName(), true)){
						if(editor.isDirty() && editor.getSite().getPage().getDirtyEditors().length == 1){
							editor.getSite().getPage().saveEditor(editor, false);
							onBrowseInheritEventSelection(e);
						}else{
							boolean status = MessageDialog.openQuestion(editor.getSite().getShell(),
									"Save Event Editor", "Event editors have been modified. Save changes?");
							if(status){
								EditorUtils.saveEventEditor(editor.getSite().getPage(), editor.getProject().getName(), false);
								onBrowseInheritEventSelection(e);
							}
						}
					}else{
						onBrowseInheritEventSelection(e);
					}
					
				}
				catch(Exception e2){
					e2.printStackTrace();
				}
				
			}
		});

	//	toolkit.createLabel(sectionClient, Messages.getString("event.TTL"),  SWT.NONE);
		
		Composite ttlComposite = toolkit.createComposite(sectionClient);
		layout = new GridLayout(3,false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		ttlComposite.setLayout(layout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		ttlComposite.setLayoutData(gd);
		
		ttlText = createGvTextField(ttlComposite, Messages.getString("event.TTL"),"ttlText");
		
		
		((Text)ttlText.getField()).addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				onModifyTtlText(e);
			}
		});
		
		(ttlText.getGvText()).addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				onModifyTtlText(e);
			}
		});
		/*GridData ttlgd = new GridData();
		ttlgd.widthHint = 80;
		ttlText.setLayoutData(ttlgd);*/

		ttlUnitCombo = new Combo(ttlComposite, SWT.BORDER | SWT.READ_ONLY);
		//Modified by Anand - 01/17/2011 to fix BE-10395		
		String[] array = new String[TimeOutUnitsUtils.getValidTimeOutUnits().length];
		int i=0;
		for(Object obj:TimeOutUnitsUtils.getValidTimeOutUnits()){
			array[i] = obj.toString();
			i++;
		}
		ttlUnitCombo.setItems(array);
		ttlUnitCombo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				onModifyTtlUnits(e);
			}
		});
		GridData ttlugd = new GridData();
		ttlugd.widthHint = 65;
		ttlUnitCombo.setLayoutData(ttlugd);
		
//		Label ttlFiller = new Label(ttlComposite, SWT.NONE);
//		ttlFiller.setText("");
//		ttlFiller.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

//		toolkit.createLabel(sectionClient, Messages.getString("event.DefaultDestination"),  SWT.NONE);
		destlink = createLinkField(toolkit, ttlComposite, Messages.getString("event.DefaultDestination"));
		
		Composite defDestComposite = toolkit.createComposite(ttlComposite);
		layout = new GridLayout(2,false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		defDestComposite.setLayout(layout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		defDestComposite.setLayoutData(gd);

		defaultDestText = toolkit.createText(defDestComposite,"", SWT.SINGLE | SWT.BORDER);
		GridData defdesgd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		defdesgd.widthHint=245;
		defaultDestText.setLayoutData(defdesgd);
		defaultDestText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				onModifyDefaultDestinationText(e);
			}
		});
		
		addHyperLinkFieldListener(destlink, defaultDestText, editor, editor.getProject().getName(), true, false);
		
		browseDestButton = new Button(defDestComposite, SWT.NONE);
		browseDestButton.setText(Messages.getString("Browse"));
		browseDestButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				onBrowseDestinationSelection();
			}
		});
        
		Composite ttlComposite1 = toolkit.createComposite(sectionClient);
		layout = new GridLayout(2,false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		ttlComposite1.setLayout(layout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		ttlComposite1.setLayoutData(gd);
		
		toolkit.createLabel(ttlComposite1,Messages.getString("event.retryOnException")+" ");
		retryOnException = toolkit.createButton(ttlComposite1, "", SWT.CHECK);
		retryOnException.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				Command command=new SetCommand(getEditingDomain(), event,EventPackage.eINSTANCE.getEvent_RetryOnException(),retryOnException.getSelection()) ;
				EditorUtils.executeCommand(editor, command);
			}});
		
		StudioUIUtils.setDropTarget(defaultDestText, ELEMENT_TYPES.SIMPLE_EVENT);

		toolkit.paintBordersFor(sectionClient);
	}

	private void onModifyEventDescription(ModifyEvent modifyEvent) {
		try{
			if(event.getDescription() ==  null && !eventDescText.getText().equalsIgnoreCase("")){
				Command command=new SetCommand(getEditingDomain(),event, ModelPackage.eINSTANCE.getEntity_Description(),eventDescText.getText()) ;
				EditorUtils.executeCommand(editor, command);
				return;
			}
			if(!eventDescText.getText().equalsIgnoreCase(event.getDescription())){
				Object value = eventDescText.getText().trim().equalsIgnoreCase("")? null: eventDescText.getText().trim();
				Command command=new SetCommand(getEditingDomain(),event,ModelPackage.eINSTANCE.getEntity_Description(),value) ;
				EditorUtils.executeCommand(editor, command);
			}
		}
		catch(Exception e1){
			e1.printStackTrace();
		}
	}

	private void onModifyTtlUnits(ModifyEvent me) {
		try{
			if(event.getTtlUnits() != TIMEOUT_UNITS.get(ttlUnitCombo.getText())){
				Command command=new SetCommand(getEditingDomain(),
						event, EventPackage.eINSTANCE.getEvent_TtlUnits(),
						TIMEOUT_UNITS.get(ttlUnitCombo.getText())) ;
				EditorUtils.executeCommand(editor, command);
			} 
			if(ttlUnitCombo.toString().contains("Days") && Integer.parseInt(ttlText.getValue()) >= MAXIMUM_TTL_DAYS_VALUE) {

				/*ttlText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
				String problemMessage = com.tibco.cep.studio.ui.util.Messages.getString("invalid.entry", ttlText.getText(), Messages.getString("event.TTL"));
				ttlText.setToolTipText(problemMessage);
*/				
				Command command=new SetCommand(getEditingDomain(),event,EventPackage.eINSTANCE.getEvent_Ttl(), 0) ;
				EditorUtils.executeCommand(editor, command);
			} else {
				/*ttlText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
				ttlText.setToolTipText("");*/
			}
				
		}
		catch(Exception e1){
			e1.printStackTrace();
		}
	}

	private void onModifyTtlText(ModifyEvent modifyEvent) {
		try{
			String newTtlStr = ttlText.getValue().trim();
			int newTtl = GvUtil.getPossibleGVAsInt(editor.getProject(), newTtlStr);
			int oldTtl = GvUtil.getPossibleGVAsInt(editor.getProject(), event.getTtl());
			if(newTtl != oldTtl){
				if(newTtl < -1){
					throw new NumberFormatException();
				}
			}
			String gvVal=GvUtil.getGvDefinedValue(editor.getProject(),newTtlStr);
			if(gvVal !=null){
				if(ttlUnitCombo.toString().contains("Days") && Integer.parseInt(gvVal) >= MAXIMUM_TTL_DAYS_VALUE) {
					throw new Exception();
				}
			}
			if(!(event.getTtl().equals(newTtlStr))){
				Command command=new SetCommand(getEditingDomain(),event, EventPackage.eINSTANCE.getEvent_Ttl(), newTtlStr) ;
				EditorUtils.executeCommand(editor, command);
			}
		}
		catch(Exception e1){
			
			ttlText.getField().setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
			String problemMessage = com.tibco.cep.studio.ui.util.Messages.getString("invalid.entry", ((Text)ttlText.getField()).getText(), Messages.getString("event.TTL"));
			ttlText.getField().setToolTipText(problemMessage);
			
			Command command=new SetCommand(getEditingDomain(),event,EventPackage.eINSTANCE.getEvent_Ttl(), "0") ;
			EditorUtils.executeCommand(editor, command);
		}
	}

	private void onBrowseDestinationSelection() {
		DestinationSelector picker = new DestinationSelector(Display.getDefault().getActiveShell(),
				                                             editor.getProject().getName(),
				                                             event.getDestination());
		if (picker.open() == Dialog.OK) {
			if(picker.getFirstResult() instanceof Destination) {
				isDestinationBrowse = true;
				destination = (Destination) picker.getFirstResult();
				DriverConfig driverConfig = destination.getDriverConfig();
				Channel channel = driverConfig.getChannel();
				StringBuilder sBuilder = new StringBuilder();
				newChannelURI =  sBuilder.append(channel.getFolder()).append(channel.getName()).toString();
				sBuilder = new StringBuilder();
				String path = sBuilder.append(channel.getFolder())
				.append(channel.getName())
				/*.append(".channel")*/
				.append("/")
				.append(destination.getName())
				.toString();
				defaultDestText.setText(path);
			}
		}
	}

	private boolean onModifyInheritsText(ModifyEvent modifyEvent) {
		inheritsText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
		inheritsText.setToolTipText("");
		if(event.getSuperEventPath() ==  null && !inheritsText.getText().equals("")){
			if(!inheritsText.getText().trim().equals("SOAPEvent") &&
					IndexUtils.getEntity(event.getOwnerProjectName(), inheritsText.getText().trim()) == null){
				inheritsText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
				String problemMessage = com.tibco.cep.studio.ui.util.Messages.getString("invalid.entry", inheritsText.getText(),
						Messages.getString("InheritsFrom"));
				inheritsText.setToolTipText(problemMessage);
//				return true;
			}
			Command command=new SetCommand(getEditingDomain(),event, EventPackage.eINSTANCE.getEvent_SuperEventPath(),inheritsText.getText()) ;
			EditorUtils.executeCommand(editor, command);
			((EventFormEditor)editor).getAdvancedEventFormDesignViewer().addPayload();
			return true;
		}
		//Checking valid Inherit Path
		if(!inheritsText.getText().trim().equals("") && !inheritsText.getText().trim().equals("SOAPEvent") &&
				IndexUtils.getEntity(event.getOwnerProjectName(), inheritsText.getText().trim()) == null){

			inheritsText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
			String problemMessage = com.tibco.cep.studio.ui.util.Messages.getString("invalid.entry", inheritsText.getText(),
					Messages.getString("InheritsFrom"));
			inheritsText.setToolTipText(problemMessage);

//			return true;
		}
		if(event.getSuperEventPath() != null && (!event.getSuperEventPath().equals(inheritsText.getText()))){
			Object value = inheritsText.getText().trim().equals("") ? null: inheritsText.getText().trim();
			if(/*value ==  null &&*/ event.isSoapEvent()){
				Command command  = new SetCommand(getEditingDomain(), event, EventPackage.eINSTANCE.getEvent_PayloadString(), null);
				EditorUtils.executeCommand(editor, command);
				((EventFormEditor)editor).getAdvancedEventFormDesignViewer().getPayloadEditor().getEditorPanel().readSchemaNode(null);
			}
			Command command =new SetCommand(getEditingDomain(),event,EventPackage.eINSTANCE.getEvent_SuperEventPath(),value) ;
			EditorUtils.executeCommand(editor, command);
			((EventFormEditor)editor).getAdvancedEventFormDesignViewer().addPayload();
		}
		return false;
	}

	private void onBrowseInheritEventSelection(SelectionEvent se) {
		DeclarationSelector picker = new DeclarationSelector(Display.getDefault().getActiveShell(),
				Messages.getString("super_event_declration_selector_title"),
				editor.getProject().getName(), ELEMENT_TYPES.SIMPLE_EVENT,event.getFullPath(), event.getSuperEventPath(), false);
		if (picker.open() == Dialog.OK) {
			if(picker.getType()!= null && picker.getType().equals("SOAPEvent") && !event.isSoapEvent() && event.getPayloadString() != null){
				boolean confirm = MessageDialog.openConfirm(getForm().getShell(),Messages.getString("soap.event.title") ,
						Messages.getString("soap.event.message"));
				if(!confirm){
					return;
				}
			}
			if(!picker.getType().equals("SOAPEvent")){
				Event superEvent = IndexUtils.getSimpleEvent(event.getOwnerProjectName(), picker.getType());
				EList<PropertyDefinition> allList = event.getAllUserProperties();
				List<PropertyDefinition> subList = StudioUIUtils.getSubEventProperties(event.getFullPath(), superEvent.getOwnerProjectName());
				allList.addAll(subList);
				if(isPropertyPresent(allList, superEvent.getAllUserProperties())){
					MessageDialog.openError(editor.getSite().getShell(),
							"Duplicate Property", "Duplicate inherit property.");
					return;
				}
			}
			inheritsText.setText(picker.getType());
		}
	}

	/**
	 * @param allList
	 * @param inhList
	 * @return
	 */
	private boolean isPropertyPresent(EList<PropertyDefinition> allList, EList<PropertyDefinition> inhList){
		Set<String> set = new HashSet<String>();
		int size = allList.size() + inhList.size();
		for(PropertyDefinition prop: allList){
			set.add(prop.getName());
		}
		for(PropertyDefinition prop: inhList){
			set.add(prop.getName());
		}
		if(set.size() < size){
			return true;
		}
		return false;
	}
	
	private boolean onModifyDefaultDestinationText(ModifyEvent modifyEvent) {
		if(this.getEditor()!=null && this.getEditor().isEnabled()){
		defaultDestText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
		defaultDestText.setToolTipText("");
		oldChannelURI = event.getChannelURI();
		if(oldChannelURI != null && defaultDestText.getText().trim().equals("")){
			Command channelURIcommand = new SetCommand(getEditingDomain(), event, EventPackage.eINSTANCE.getSimpleEvent_ChannelURI(),null) ;
			EditorUtils.executeCommand(editor, channelURIcommand);
			Command command=new SetCommand(getEditingDomain(),event,EventPackage.eINSTANCE.getSimpleEvent_DestinationName(),null) ;
			EditorUtils.executeCommand(editor, command);
			isDestinationBrowse = false;
			newChannelURI = null;
			destination= null;
			return true;
		}

		StringBuilder sb = new StringBuilder();
		oldChannelURI = event.getChannelURI();
		String oldDestination = null;
		if (oldChannelURI != null) {
			oldDestination = sb.append(oldChannelURI)/*.append(".channel")*/.append("/").append(event.getDestinationName()).toString();
		}
		
		if(!isDestinationBrowse){
			Map<String, Destination> map = CommonIndexUtils.getAllDestinationsURIMaps(editor.getProject().getName());
			if(map.containsKey(defaultDestText.getText())){
				destination = map.get(defaultDestText.getText());
				DriverConfig driverConfig = destination.getDriverConfig();
				Channel channel = driverConfig.getChannel();
				StringBuilder sBuilder = new StringBuilder();
				newChannelURI =  sBuilder.append(channel.getFullPath()).toString();
			}else{
				defaultDestText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
				String problemMessage = com.tibco.cep.studio.ui.util.Messages.getString("invalid.entry",
						defaultDestText.getText().trim(), Messages.getString("event.DefaultDestination"));
				defaultDestText.setToolTipText(problemMessage);
				isDestinationBrowse = false;
				return true;
			}
		}
		
		//check For empty old destination and invalid destination
		if(destination == null && oldDestination == null){
			defaultDestText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
			String problemMessage = com.tibco.cep.studio.ui.util.Messages.getString("invalid.entry",
					defaultDestText.getText().trim(), Messages.getString("event.DefaultDestination"));
			defaultDestText.setToolTipText(problemMessage);
			isDestinationBrowse = false;
//			return true;
		}
		//check for invalid destination
		if(destination == null && oldDestination != null &&
				!defaultDestText.getText().trim().equals(oldDestination)){
			defaultDestText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
			String problemMessage = com.tibco.cep.studio.ui.util.Messages.getString("invalid.entry",
					defaultDestText.getText().trim(), Messages.getString("event.DefaultDestination"));
			defaultDestText.setToolTipText(problemMessage);
			isDestinationBrowse = false;
//			return true;
		}

		if(oldChannelURI ==  null && destination != null){
			Command channelURIcommand = new SetCommand(getEditingDomain(), event,EventPackage.eINSTANCE.getSimpleEvent_ChannelURI(),
					newChannelURI) ;
			EditorUtils.executeCommand(editor, channelURIcommand);
			Command command=new SetCommand(getEditingDomain(),event,EventPackage.eINSTANCE.getSimpleEvent_DestinationName(),
					destination.getName()) ;
			EditorUtils.executeCommand(editor, command);
			
			isDestinationBrowse = false;
			newChannelURI = null;
			destination= null;
			return true;
		}

		String newDestination = null;
		if (destination != null) {
			sb = new StringBuilder();
			newDestination = sb.append(newChannelURI)/*.append(".channel")*/.append("/").append(destination.getName()).toString();
		}
		if(oldDestination!=null && newDestination !=null && !oldDestination.equals(newDestination)){
			Command channelURIcommand = new SetCommand(getEditingDomain(), event,EventPackage.eINSTANCE.getSimpleEvent_ChannelURI(),
					newChannelURI) ;
			EditorUtils.executeCommand(editor, channelURIcommand);
			Command command=new SetCommand(getEditingDomain(),event,EventPackage.eINSTANCE.getSimpleEvent_DestinationName(),
					destination.getName()) ;
			EditorUtils.executeCommand(editor, command);
			
			isDestinationBrowse = false;
			newChannelURI = null;
			destination= null;
		}
		}
		return false;
		
	}

	
	@Override
	protected void createPropertiesPart(final IManagedForm managedForm,	Composite parent) {
		FormToolkit toolkit = managedForm.getToolkit();
		propertiesSection = toolkit.createSection(parent, Section.TITLE_BAR | Section.EXPANDED| Section.TWISTIE);
		propertiesSection.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		propertiesSection.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		propertiesSection.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				adjustPropertySections(e, true);
			}
		});
		propertiesSection.setText(Messages.getString("PROPERTIES_SECTION"));

		Composite sectionClient = toolkit.createComposite(propertiesSection);
		propertiesSection.setClient(sectionClient);
		sectionClient.setLayout(new GridLayout());
		TableWrapLayout layout = new TableWrapLayout();
		layout.numColumns = 1;

		TableWrapData td = new TableWrapData(TableWrapData.FILL);
		td.grabHorizontal = true;
		td.heightHint = 228;
		propertiesSection.setLayoutData(td);


		createPropertiesTable(sectionClient);
		toolkit.paintBordersFor(sectionClient);
	}

	

	/**
	 * @param changedEvent
	 */
	public void doRefresh(final SimpleEvent changedEvent){
		this.event = changedEvent;
		
		//TODO table refresh?
		Display.getDefault().asyncExec(new Runnable(){
			/* (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				((EventFormEditor)editor).getAdvancedEventFormDesignViewer().doRefresh(changedEvent);
				oldChannelURI = event.getChannelURI();
				StringBuilder sb = new StringBuilder();
				if (oldChannelURI != null) {
					defaultDestText.setText(sb.append(oldChannelURI)/*.append(".channel")*/.append("/").append(event.getDestinationName()).toString());
				}else{
					defaultDestText.setText("");
				}
				if(event.getSuperEventPath()!=null){
					inheritsText.setText(event.getSuperEventPath());
				}
				refreshTable();
				if (enableMetadataConfigurationEnabled()) {
					extPropSection.setExpanded(false);
				}
				if (editor.isDirty()) {
					editor.setModified(false);
					editor.firePropertyChange();
				}
			}});
	}
	
	public void refreshFieldOnFocus() {
		oldChannelURI = event.getChannelURI();
		StringBuilder sb = new StringBuilder();
		if (oldChannelURI != null) {
			defaultDestText.setText(sb.append(oldChannelURI)/*.append(".channel")*/.append("/").append(event.getDestinationName()).toString());
		}else{
			defaultDestText.setText("");
		}
		if(event.getSuperEventPath()!=null){
			inheritsText.setText(event.getSuperEventPath());
		}
	}
	
	private void readOnlyWidgets(){
		eventDescText.setEditable(false);
		inheritsText.setEditable(false);
		ttlText.setEnabled(false);
		defaultDestText.setEditable(false);
		
		browseInheritsButton.setEnabled(false);
		browseDestButton.setEnabled(false);
		retryOnException.setEnabled(false);
		ttlUnitCombo.setEnabled(false);
		
		toolBarProvider.getAddItem().setEnabled(false);
		toolBarProvider.getDeleteItem().setEnabled(false);
		
		diagramAction.setEnabled(false);
		dependencyDiagramAction.setEnabled(false);
		sequenceDiagramAction.setEnabled(false);
		
		inheritsLink.setEnabled(false);
		destlink.setEnabled(false);
	}
	
    /* (non-Javadoc)
     * @see com.tibco.cep.studio.ui.forms.AbstractEntityFormViewer#renameElement(com.tibco.cep.designtime.core.model.element.PropertyDefinition)
     */
    protected void renameElement(PropertyDefinition propertyDefinition) {
    	
 		EList<PropertyDefinition> allList = event.getAllUserProperties();
		List<PropertyDefinition> subList = getSubEventProperties(event.getFullPath(), event.getOwnerProjectName());
		allList.addAll(subList);
    	
    	EntityRenameElementAction act = new EntityRenameElementAction(allList);
		act.selectionChanged(null, new StructuredSelection(propertyDefinition));
		act.run(null);
    }
    
    private GvField createGvTextField(Composite parent, String label, String key){
		Label lField = PanelUiUtil.createLabel(parent, label);
    	return createGvTextField(parent, event,key);
	}
	
	public GvField createGvTextField(Composite parent, SimpleEvent event,String key) {
    	GvField gvField =null;
    	if("ttlText".equalsIgnoreCase(key)){
    		gvField= event.getTtl()==null?GvUiUtil.createTextGv(parent,"0") : GvUiUtil.createTextGv(parent,event.getTtl());
    	}
    	setGvFieldListeners(gvField, SWT.Modify, key);
		return gvField;
    }
	
	protected void setGvFieldListeners(GvField gvField, int eventType, String modelId) {
		gvField.setFieldListener(eventType, getListener(gvField.getField(), modelId));
		gvField.setGvListener(getListener(gvField.getGvText(), modelId));
    }
	
	public Listener getListener(final Control field, final String key) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(org.eclipse.swt.widgets.Event e) {
				if (field instanceof Text) {
					String val=((Text)field).getText();
					String gvVal=null;
					if(GvUtil.isGlobalVar(val)){
						gvVal=GvUtil.getGvDefinedValue(editor.getProject(),val);
						if(!(event.getTtl()).equals(val)){
							Command command=new SetCommand(getEditingDomain(),event,EventPackage.eINSTANCE.getTimeEvent_Interval(),val) ;			
							EditorUtils.executeCommand(editor, command);
						}
					}
					if(editor.getProject() !=null){
						validateField((Text)field,gvTypeFieldMap.get(key), event.getTtl(), key, editor.getProject().getName());
					}
				}
			}
		};
		return listener;
	}
	
	/**
	 * @param textField
	 * @param type
	 * @param deafultValue
	 * @param displayName
	 * @return
	 */
	protected static boolean validateField(Text textField, String type, String deafultValue,
			String displayName, String prjName) {
		final String problemMessage = validatePropertyValue(type, textField,
				deafultValue, displayName,prjName);
		if (problemMessage != null) {
			textField.setForeground(Display.getDefault().getSystemColor(
					SWT.COLOR_RED));
			textField.setToolTipText(problemMessage);
			return false;
		}else if(problemMessage == null){
			textField.setForeground(Display.getDefault().getSystemColor(
					SWT.COLOR_BLACK));
			textField.setToolTipText("");
			return true;
		}

		return true;
	}
	
	/**
	 * @param type
	 * @param fieldName
	 * @param deafultValue
	 * @param propertyName
	 * @param propertyInstance
	 * @return
	 */
	protected static String validatePropertyValue(String type, Text textField,
			String deafultValue, String propertyName, String projectName) {
		final String message = com.tibco.cep.studio.ui.util.Messages.getString(
				"invalid.property.entry", textField.getText(), propertyName,
				type);
		String text = textField.getText();
		boolean globalVar = false;
		if (text.length() > 4) {
			globalVar = GvUtil.isGlobalVar(text.trim());
		}
		if (globalVar) {
			// check if global var defined
			Map<String, GlobalVariableDescriptor> glbVars = DefaultResourceValidator
					.getGlobalVariableNameValues(projectName);

			GlobalVariableDescriptor gvd = glbVars.get(stripGvMarkers(text));
			if (gvd == null) {
				return text + " is not defined";
			}
			if (!gvd.getType().equals(type.intern())) {
				if (type.intern().equals("int")
						&& gvd.getType().equals("Integer")) {
					return null;
				}
				return "Type of "+ text +" is not "+ type.intern();
			}
			return null;
		}
		return null;
	}
	
	private static String stripGvMarkers(String variable) {
		int firstIndex = variable.indexOf("%%");
		String stripVal = variable.substring(firstIndex + 2);
		stripVal = stripVal.substring(0, stripVal.indexOf("%%"));
		return stripVal;
	}

  
}