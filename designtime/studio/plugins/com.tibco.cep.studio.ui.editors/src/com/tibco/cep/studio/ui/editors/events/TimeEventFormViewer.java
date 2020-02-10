package com.tibco.cep.studio.ui.editors.events;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import com.tibco.cep.designtime.core.model.ModelPackage;
import com.tibco.cep.designtime.core.model.TIMEOUT_UNITS;
import com.tibco.cep.designtime.core.model.event.EVENT_SCHEDULE_TYPE;
import com.tibco.cep.designtime.core.model.event.EventPackage;
import com.tibco.cep.designtime.core.model.event.TimeEvent;
import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.studio.common.util.TimeOutUnitsUtils;
import com.tibco.cep.studio.core.util.GvUtil;
import com.tibco.cep.studio.core.validation.DefaultResourceValidator;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.EntityDiagramEditorInput;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tibco.cep.studio.ui.editors.utils.EntityImages;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.forms.AbstractEntityFormViewer;
import com.tibco.cep.studio.ui.forms.extendedPropTreeViewer.ExtendedPropTreeViewer;
import com.tibco.cep.studio.ui.util.GvField;
import com.tibco.cep.studio.ui.util.GvUiUtil;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/**
 * 
 * @author sasahoo
 *
 */
public class TimeEventFormViewer extends AbstractEntityFormViewer {
	
//	private Text eventNameText;
	protected TimeEvent event;
	private Text eventDescText;

	private Combo typeCombo;
//	private Text repeatEveryText;
	private Combo repeatEveryUnitCombo;
//	private Text eventIntervalText;
	private GvField eventIntervalGvText,repeatEveryGvText;
	
//	private Label eventIntervalLabel;
///	private Label rELabel;

	private String repeatEvery = "0";
	private String repeatEveryUnit;
	private String eventInterval = "1";
	
	private ExtendedPropTreeViewer extndProp;
	private static final String EXTRA_SPACE = "          ";
	private Map<String,String> gvTypeFieldMap=new LinkedHashMap<String,String>();
	

	public TimeEventFormViewer(TimeEventFormEditor editor) {
		this.editor = editor;
		initialiseGvFieldTypeMap();
		if (editor != null && editor.getEditorInput() instanceof  TimeEventFormEditorInput) {
			this.event = ((TimeEventFormEditorInput) editor.getEditorInput()).getTimeEvent();
		} else{
			this.event = editor.getTimeEvent();
		}
	}
	
	private void initialiseGvFieldTypeMap() {
        gvTypeFieldMap.put("eventIntervalGvText", "Integer");
        gvTypeFieldMap.put("repeatEveryGvText", "Integer");
 	}
	
	public void validateFieldsForGv() {
		validateField((Text)eventIntervalGvText.getGvText(),gvTypeFieldMap.get("eventIntervalGvText"), event.getTimeEventCount(), "eventIntervalGvText", editor.getProject().getName());
		validateField((Text)repeatEveryGvText.getGvText(),gvTypeFieldMap.get("repeatEveryGvText"), event.getInterval(), "repeatEveryGvText", editor.getProject().getName());
	}

	/**
	 * @param container
	 */
	public void createPartControl(Composite container) {
		
		super.createPartControl(container, Messages.getString("time.event.editor.title")+ " " +event.getName(),
				/*EntityImages.getImage(EntityImages.TIME_EVENT)*/EditorsUIPlugin.getDefault().getImage("icons/time-event.gif"));
		
		final ScrolledForm form = getForm();
		diagramAction = new org.eclipse.jface.action.Action("diagram", org.eclipse.jface.action.Action.AS_PUSH_BUTTON) {
			public void run() {
//				if(isChecked()){
					IWorkbenchPage page =editor.getEditorSite().getWorkbenchWindow().getActivePage();
					IProject project = editor.getProject();
					IFile file = project.getFile(project.getName()+".eventview");
					EntityDiagramEditorInput input = new EntityDiagramEditorInput(file,project);
					input.setSelectedEntity(event);
					try {
						page.openEditor(input, EventDiagramEditor.ID);
					} catch (PartInitException e) {
						e.printStackTrace();
					}		
					form.reflow(true);
//				}
			}
		};
		diagramAction.setChecked(false);
		diagramAction.setToolTipText(Messages.getString("event.diagram.tooltip"));
		diagramAction.setImageDescriptor(EntityImages.getImageDescriptor(EntityImages.EVENT_DIAGRAM));
		form.getToolBarManager().add(diagramAction);
		form.updateToolBar();

        dependencyDiagramAction = EditorUtils.createDependencyDiagramAction(editor, getForm(), editor.getProject());
        getForm().getToolBarManager().add(dependencyDiagramAction);
        sequenceDiagramAction =  EditorUtils.createSequenceDiagramAction(editor, getForm(), editor.getProject());
        getForm().getToolBarManager().add(sequenceDiagramAction);
        
		super.createToolBarActions();
		
    	if (enableMetadataConfigurationEnabled())
    		sashForm.setWeights(new int[] {100});
    	
    	if(event.getDescription()!=null)
    		eventDescText.setText(event.getDescription());

    	typeCombo.setText(event.getScheduleType().getLiteral());
    	if(event.getScheduleType() == EVENT_SCHEDULE_TYPE.RULE_BASED){
			hideRepeatTimeEventWidgets();
	    }
    	if(event.getScheduleType() == EVENT_SCHEDULE_TYPE.RUN_ONCE){
			hideRepeatTimeEventWidgets();
	    }
    	if(event.getScheduleType() == EVENT_SCHEDULE_TYPE.REPEAT){
	    	showRepeatTimeEventWidgets();
	    	if(repeatEveryGvText.isGvMode()){
	    		repeatEveryGvText.setGvModeValue(event.getInterval());
	    		repeatEveryGvText.onSetGvMode();
	    	}else{
	    		repeatEveryGvText.setFieldModeValue(event.getInterval());
	    		repeatEveryGvText.onSetFieldMode();
	    	}
	    	
	    	repeatEveryUnitCombo.setText(event.getIntervalUnit().getLiteral());
	    	
	    	if(eventIntervalGvText.isGvMode()){
	    		eventIntervalGvText.setGvModeValue(event.getTimeEventCount());
	    		eventIntervalGvText.onSetGvMode();
	    	}else{
	    		eventIntervalGvText.setFieldModeValue(event.getTimeEventCount());
	    		eventIntervalGvText.onSetFieldMode();
	    	}
	    }
    	
    	  //Making readonly widgets
		if(!getEditor().isEnabled()){
			readOnlyWidgets();
		}
	}

	@Override
	protected void createGeneralPart(final ScrolledForm form,final FormToolkit toolkit) {
		
		Section section = toolkit.createSection(form.getBody(), Section.TITLE_BAR| Section.EXPANDED);
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
		layout = new GridLayout(2,false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		temprepeatEveryComposite.setLayout(layout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		temprepeatEveryComposite.setLayoutData(gd);
		
		toolkit.createLabel(temprepeatEveryComposite, Messages.getString("Description")+EXTRA_SPACE,  SWT.NONE).setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
   
		eventDescText = toolkit.createText(temprepeatEveryComposite,"",  SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.WRAP);
		eventDescText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				try{
					if(event.getDescription() ==  null && !eventDescText.getText().equalsIgnoreCase("")){
						Command command=new SetCommand(getEditingDomain(),event,ModelPackage.eINSTANCE.getEntity_Description(),eventDescText.getText()) ;			
						EditorUtils.executeCommand(editor, command);
						return;
					}
					if(event.getDescription() != null && !event.getDescription().equalsIgnoreCase(eventDescText.getText())){
						Object value = eventDescText.getText().trim().equalsIgnoreCase("")? null: eventDescText.getText().trim();
						Command command=new SetCommand(getEditingDomain(),event,ModelPackage.eINSTANCE.getEntity_Description(),value) ;			
						EditorUtils.executeCommand(editor, command);
					}
				}
				catch(Exception e1){
					e1.printStackTrace();
				}
              }
			});

		GridData condescgd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		condescgd.widthHint = 600;
		condescgd.heightHint = 30;
		eventDescText.setLayoutData(condescgd);

	    toolkit.createLabel(temprepeatEveryComposite, Messages.getString("Type"),  SWT.NONE);

		typeCombo = new Combo(temprepeatEveryComposite, SWT.BORDER | SWT.READ_ONLY);
		
		String[] scarray = new String[EVENT_SCHEDULE_TYPE.VALUES.toArray().length - 1];
		int i=0;
		for(Object obj:EVENT_SCHEDULE_TYPE.VALUES.toArray()){
			if(obj.toString().equals("runOnce")) continue;
				scarray[i] = obj.toString();
			i++;
		}
		typeCombo.setItems(scarray);
		
		typeCombo.addSelectionListener(new SelectionAdapter(){

			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			public void widgetSelected(SelectionEvent e) {
				try{
					EVENT_SCHEDULE_TYPE type = EVENT_SCHEDULE_TYPE.get(typeCombo.getText());
					if(event.getScheduleType() != type){
						Command command=new SetCommand(getEditingDomain(),event,EventPackage.eINSTANCE.getTimeEvent_ScheduleType(),type) ;			
						EditorUtils.executeCommand(editor, command);
						if(type == EVENT_SCHEDULE_TYPE.RULE_BASED /*|| type == EVENT_SCHEDULE_TYPE.RUN_ONCE*/){
							
							RemoveCommand remcommand=new RemoveCommand(getEditingDomain(),event,EventPackage.eINSTANCE.getTimeEvent_Interval(), event.getInterval()) ;		
							EditorUtils.executeCommand(editor, remcommand);

							remcommand=new RemoveCommand(getEditingDomain(), event,EventPackage.eINSTANCE.getTimeEvent_IntervalUnit(),event.getIntervalUnit()) ;			
							EditorUtils.executeCommand(editor, remcommand);

							remcommand=new RemoveCommand(getEditingDomain(),event,EventPackage.eINSTANCE.getTimeEvent_TimeEventCount(),event.getTimeEventCount()) ;			
							EditorUtils.executeCommand(editor, remcommand);
							
							hideRepeatTimeEventWidgets();
						}else{
							
							command=new SetCommand(getEditingDomain(),event,EventPackage.eINSTANCE.getTimeEvent_Interval(), repeatEvery) ;			
							EditorUtils.executeCommand(editor, command);
							
							TIMEOUT_UNITS unit = null;
							
							if(repeatEveryUnit == null){ 
								unit = TIMEOUT_UNITS.SECONDS;}
							else{
								unit = TIMEOUT_UNITS.get(repeatEveryUnit);
							}
							
							command=new SetCommand(getEditingDomain(), event,EventPackage.eINSTANCE.getTimeEvent_IntervalUnit(),unit) ;			
							EditorUtils.executeCommand(editor, command);

							command=new SetCommand(getEditingDomain(),event,EventPackage.eINSTANCE.getTimeEvent_TimeEventCount(),eventInterval) ;			
							EditorUtils.executeCommand(editor, command);

							showRepeatTimeEventWidgets();
							
							((Text)repeatEveryGvText.getField()).setText(event.getInterval());
					    	repeatEveryUnitCombo.setText(event.getIntervalUnit().getName());
					    	eventIntervalGvText.setFieldModeValue((event.getTimeEventCount()));
						}
					}
				}
				catch(Exception e1){
					e1.printStackTrace();
				}

			}});
			
		GridData typeCgd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		typeCgd.widthHint = 250;
		typeCombo.setLayoutData(typeCgd);
			
///		rELabel = toolkit.createLabel(sectionClient, Messages.getString("time.event.RepeatEvery"),  SWT.NONE);
	
		Composite repeatEveryComposite = toolkit.createComposite(sectionClient);
		layout = new GridLayout(3,false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		repeatEveryComposite.setLayout(layout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		repeatEveryComposite.setLayoutData(gd);
		
		
		
		
		
		
		repeatEveryGvText = createGvTextField(repeatEveryComposite, Messages.getString("time.event.RepeatEvery"),"repeatEveryGvText");
//		repeatEveryText = new Text(repeatEveryComposite, SWT.BORDER);
	
/*		repeatEveryText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				try{
					repeatEveryText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
					repeatEveryText.setToolTipText("");

					String newIntervalStr = repeatEveryText.getText().trim();
					int newTimeEventInterval = GvUtil.getPossibleGVAsInt(editor.getProject(), newIntervalStr);
					int oldTimeEventInterval = GvUtil.getPossibleGVAsInt(editor.getProject(), event.getInterval());
					if(newTimeEventInterval != oldTimeEventInterval){
						if(newTimeEventInterval <= -1){
							throw new NumberFormatException();
						}
						
						repeatEvery = newIntervalStr;
						
						Command command=new SetCommand(getEditingDomain(),event,EventPackage.eINSTANCE.getTimeEvent_Interval(), newIntervalStr) ;			
						EditorUtils.executeCommand(editor, command);
					}
				}
				catch(Exception e1){
					
					repeatEveryText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
					String problemMessage = com.tibco.cep.studio.ui.util.Messages.getString("invalid.entry", repeatEveryText.getText(), 
							Messages.getString("time.event.RepeatEvery"));
					repeatEveryText.setToolTipText(problemMessage);
					
					Command command=new SetCommand(getEditingDomain(),event,EventPackage.eINSTANCE.getTimeEvent_Interval(), 0) ;			
					EditorUtils.executeCommand(editor, command);
				}
              }
			});	
		GridData ttlgd = new GridData(GridData.FILL_HORIZONTAL);
		ttlgd.widthHint = 264;
		repeatEveryText.setLayoutData(ttlgd);
*/			
		
		
		repeatEveryUnitCombo = new Combo(repeatEveryComposite, SWT.BORDER | SWT.READ_ONLY);
		//Modified by Anand - 01/17/2011 to fix BE-10395		
		String[] array = new String[TimeOutUnitsUtils.getValidTimeOutUnits().length];
		i = 0;
		for(Object obj:TimeOutUnitsUtils.getValidTimeOutUnits()){
			array[i] = obj.toString();
			i++;
		}
		repeatEveryUnitCombo.setItems(array);
		
		
		repeatEveryUnitCombo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				try{
					repeatEveryUnit = repeatEveryUnitCombo.getText();
					if(event.getIntervalUnit() != TIMEOUT_UNITS.get(repeatEveryUnitCombo.getText())){	
						Command command=new SetCommand(getEditingDomain(), event,EventPackage.eINSTANCE.getTimeEvent_IntervalUnit(),TIMEOUT_UNITS.get(repeatEveryUnitCombo.getText())) ;			
						EditorUtils.executeCommand(editor, command);
					}
				}
				catch(Exception e1){
					e1.printStackTrace();
				}
              }
			});	
		GridData ttlugd = new GridData();
		ttlugd.widthHint = 65;
		repeatEveryUnitCombo.setLayoutData(ttlugd);
		eventIntervalGvText = createGvTextField(repeatEveryComposite, Messages.getString("time.event.interval"),"eventIntervalGvText");
		toolkit.paintBordersFor(sectionClient);
	}

	private void showRepeatTimeEventWidgets(){
//		rELabel.setVisible(true);
		repeatEveryGvText.setEnabled(true);
//		eventIntervalLabel.setVisible(true);
	//	eventIntervalGvText.setVisible(true);
		eventIntervalGvText.setEnabled(true);
		repeatEveryUnitCombo.setEnabled(true);
	}
	
	private void hideRepeatTimeEventWidgets(){
//		rELabel.setVisible(false);
		repeatEveryGvText.setEnabled(false);
		
//		eventIntervalLabel.setVisible(false);
		eventIntervalGvText.setEnabled(false);
		repeatEveryUnitCombo.setEnabled(false);
	}
	
	@Override
	protected void createExtendedPropertiesPart(final IManagedForm managedForm,	Composite parent) {
		if (!enableMetadataConfigurationEnabled())
			return;
		FormToolkit toolkit = managedForm.getToolkit();
		extPropSection = toolkit.createSection(parent, Section.TITLE_BAR | Section.EXPANDED | Section.TWISTIE);
		extPropSection.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		extPropSection.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		extPropSection.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				getForm().reflow(true);
			}
		});

		extPropSection.setText(Messages.getString("EXT_PROPERTIES_SECTION"));

		Composite sectionClient = toolkit.createComposite(extPropSection, SWT.EMBEDDED);
		extPropSection.setClient(sectionClient);

		TableWrapLayout layout = new TableWrapLayout();
		layout.numColumns = 1;

		TableWrapData td = new TableWrapData(TableWrapData.FILL);
		td.grabHorizontal = true;
		td.heightHint = 228;
		extPropSection.setLayoutData(td);

//		Container panel = getSwingContainer(sectionClient);
//		exPanel = new ExtendedPropertiesPanel(panel,editor);
//		panel.add(exPanel, BorderLayout.CENTER);
//		
//		setDefaultExtendedProperties(event,extPropSection);
		
		extndProp= new ExtendedPropTreeViewer(editor);
		extndProp.createHierarchicalTree(sectionClient);
		extPropSection.setEnabled(enableMetadataConfigurationEnabled());
		extPropSection.setExpanded(false);

		toolkit.paintBordersFor(sectionClient);
	}
	
	private void readOnlyWidgets(){
		eventDescText.setEditable(false);
		diagramAction.setEnabled(false);
		typeCombo.setEnabled(false);
///		repeatEveryGvText.setEditable(false);
    	repeatEveryUnitCombo.setEnabled(false);
//    	eventIntervalText.setEditable(false);
    	
	}
	
	public void refreshScheduleWidgets(){
	//	if(event.getScheduleType() == EVENT_SCHEDULE_TYPE.REPEAT){
	//		if(repeatEveryText.getText().trim().equals("")){
	//			repeatEveryText.setText(event.getInterval());
	//		}
	//		if(eventIntervalText.getText().trim().equals("")){
	//			eventIntervalText.setText((event.getTimeEventCount()));
	//		}
	//	}
	}

	@Override
	protected void createPropertiesColumnList() {
		// TODO Auto-generated method stub
	}
	
	private GvField createGvTextField(Composite parent, String label, String key){
		Label lField = PanelUiUtil.createLabel(parent, label);
    	return createGvTextField(parent, event,key);
	}
	
	public GvField createGvTextField(Composite parent, TimeEvent event,String key) {
    	GvField gvField =null;
    	if("repeatEveryGvText".equalsIgnoreCase(key)){
    		gvField= event.getInterval()==null?GvUiUtil.createTextGv(parent,repeatEvery) : GvUiUtil.createTextGv(parent,event.getInterval());
    	}
    	else if("eventIntervalGvText".equalsIgnoreCase(key)){
    		gvField=event.getTimeEventCount()==null? GvUiUtil.createTextGv(parent, eventInterval):  GvUiUtil.createTextGv(parent, event.getTimeEventCount());
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
			public void handleEvent(Event e) {
				if (field instanceof Text) {
					Text textField = (Text)field;
					String textFieldValue = (textField.getText());
					textField.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
					textField.setToolTipText("");
					try{
						int newVal = GvUtil.getPossibleGVAsInt(editor.getProject(), textFieldValue);
						if("repeatEveryGvText".equalsIgnoreCase(key)){
							if(!(textFieldValue.equals(event.getInterval()))){
								Command command=new SetCommand(getEditingDomain(),event,EventPackage.eINSTANCE.getTimeEvent_Interval(),textField.getText()) ;			
								EditorUtils.executeCommand(editor, command);
							}
							validateField((Text)repeatEveryGvText.getGvText(),gvTypeFieldMap.get("repeatEveryGvText"), event.getInterval(), "repeatEveryGvText", editor.getProject().getName());
						}
						else if("eventIntervalGvText".equalsIgnoreCase(key)){
							if(!(textFieldValue.equals(event.getTimeEventCount()))){
								Command command=new SetCommand(getEditingDomain(),event,EventPackage.eINSTANCE.getTimeEvent_TimeEventCount(),textField.getText()) ;			
								EditorUtils.executeCommand(editor, command);
							}
							validateField((Text)eventIntervalGvText.getGvText(),gvTypeFieldMap.get("eventIntervalGvText"), event.getTimeEventCount(), "eventIntervalGvText", editor.getProject().getName());
						}
					}catch(Exception e1){
						textField.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
						String fieldType=Messages.getString("time.event.interval");
						if("repeatEveryGvText".equalsIgnoreCase(key)){
							fieldType = Messages.getString("time.event.RepeatEvery");
						}
						String problemMessage = com.tibco.cep.studio.ui.util.Messages.getString("invalid.entry", textField.getText(), fieldType);
						textField.setToolTipText(problemMessage);
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