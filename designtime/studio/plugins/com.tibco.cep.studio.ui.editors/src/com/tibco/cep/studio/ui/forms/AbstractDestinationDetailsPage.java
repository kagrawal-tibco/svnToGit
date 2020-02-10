package com.tibco.cep.studio.ui.forms;


import static com.tibco.cep.studio.ui.util.StudioUIUtils.addHyperLinkFieldListener;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.createLinkField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IDetailsPage;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ScrolledPageBook;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.ModelPackage;
import com.tibco.cep.designtime.core.model.PropertyMap;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.service.channel.ChannelPackage;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.designtime.core.model.service.channel.DriverConfig;
import com.tibco.cep.designtime.core.model.service.channel.util.DriverManagerConstants;
import com.tibco.cep.studio.common.util.EntityNameHelper;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigProjectUtils;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.ui.editors.channels.ChannelFormEditor;
import com.tibco.cep.studio.ui.editors.channels.ChannelFormFeederDelegate;
import com.tibco.cep.studio.ui.editors.channels.ChannelFormViewer;
import com.tibco.cep.studio.ui.editors.channels.contoller.PropertyConfiguration;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tibco.cep.studio.ui.editors.utils.KafkaEditorUtils;
import com.tibco.cep.studio.ui.editors.utils.KafkaStreamsEditorUtils;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.forms.components.EventSelector;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

/**
 * 
 * @author sasahoo
 *
 */
public abstract class AbstractDestinationDetailsPage implements IDetailsPage {

	protected IManagedForm managedForm;
	protected Destination destination;
	protected EditingDomain editingDomain;
	protected TableViewer viewer;
	protected String type;
	protected ScrolledPageBook destinationDetailsPageBook;
	protected IProject project;
	protected ChannelFormFeederDelegate delegate;
	protected ChannelFormViewer formViewer;
	protected String[] driversArray; 
	protected HashMap<Object,Object> controls;
	protected Composite details;

	protected PropertyMap instance;

	protected static final int HEIGHTHINTnormal = 200;
	protected static final int HEIGHTHINTmin = 3;
	protected static final int SIZEaugend = 5;
	protected static final int LAYOUTmultiplier = 30;

	//	public static final Font mandatoryFont = new Font(Display.getDefault(),"Tahoma", 11, SWT.BOLD);


	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IDetailsPage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	public void createContents(Composite parent) {
		controls = new HashMap<Object, Object>();
		TableWrapLayout layout1 = new TableWrapLayout();
		parent.setLayout(layout1);
		FormToolkit toolkit = managedForm.getToolkit();
		Section section  = toolkit.createSection(parent, Section.NO_TITLE);
		TableWrapData td = new TableWrapData(TableWrapData.FILL, TableWrapData.TOP);
		td.grabHorizontal = true;
		section.setLayoutData(td);
		Composite sectionClient = toolkit.createComposite(section);
		section.setClient(sectionClient);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		sectionClient.setLayout(layout);
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		layoutData.heightHint = 50;
		sectionClient.setLayoutData(layoutData);
		details = toolkit.createComposite(sectionClient);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.heightHint = 380;
		details.setLayoutData(data);
		details.setLayout(new FillLayout());
		destinationDetailsPageBook = toolkit.createPageBook(details, SWT.NONE);
		driversArray = delegate.getConfiguredDriverTypes(project.getName());
		for (String type:driversArray) {
			createDestinationDetailsPage(toolkit, type);	
		}
		destinationDetailsPageBook.showEmptyPage();
		toolkit.paintBordersFor(sectionClient);
	}

	/**
	 * @param toolkit
	 * @param type
	 */
	protected void createDestinationDetailsPage(FormToolkit toolkit, final String type){
		Composite root = destinationDetailsPageBook.createPage(type);
		root.setLayout(new GridLayout(3, false));
		toolkit.createLabel(root, "Name*:");
		final Text name = toolkit.createText(root, "",SWT.BORDER);
		controls.put(type + CommonIndexUtils.DOT + Messages.getString("Name"), name);
		name.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		toolkit.createLabel(root, "");
		name.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				try{
					name.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
					name.setToolTipText("");
					if(!destination.getName().equalsIgnoreCase(name.getText())){
						if(!EntityNameHelper.isValidBEEntityIdentifier(name.getText())){
							name.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
							String problemMessage = com.tibco.cep.studio.ui.util.Messages.getString("BE_Resource_invalidFilename", name.getText());
							name.setToolTipText(problemMessage);
							return;
						}
						if(isDuplicateDestinationName(name.getText())){
							name.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
							String problemMessage = com.tibco.cep.studio.ui.util.Messages.getString("BE_Resource_DestinationExists", name.getText());
							name.setToolTipText(problemMessage);
							return;
						}
						Command command=new SetCommand(editingDomain, destination, ModelPackage.eINSTANCE.getEntity_Name(), name.getText()) ;			
						EditorUtils.executeCommand(formViewer.getEditor(), 
								command);
						viewer.refresh();
					}
				}
				catch(Exception e1){
					e1.printStackTrace();
				}

			}});
		toolkit.createLabel(root, Messages.getString("Description"));
		final Text desc = toolkit.createText(root, "",SWT.BORDER);
		desc.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		toolkit.createLabel(root, "");
		controls.put(type + CommonIndexUtils.DOT + Messages.getString("Description"), desc);
		desc.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				try{
					if(destination.getDescription() == null){
						Command command=new SetCommand(editingDomain, destination, ModelPackage.eINSTANCE.getEntity_Description(),desc.getText()) ;			
						EditorUtils.executeCommand(formViewer.getEditor(), command);
					}
					if(!destination.getDescription().equalsIgnoreCase(desc.getText())){
						Command command=new SetCommand(editingDomain, destination, ModelPackage.eINSTANCE.getEntity_Description(),desc.getText()) ;			
						EditorUtils.executeCommand(formViewer.getEditor(), command);
					}
				}
				catch(Exception e1){
					e1.printStackTrace();
				}

			}});
		if(!type.equalsIgnoreCase("Local")){
//			toolkit.createLabel(root, Messages.getString("channel.destination.details.DefaultEvent"));
			
			Hyperlink link = createLinkField(toolkit, root, Messages.getString("channel.destination.details.DefaultEvent"));
			controls.put(type + CommonIndexUtils.DOT +".link."+Messages.getString("channel.destination.details.DefaultEvent"), link);
			final Text defEventText = toolkit.createText(root, "", SWT.BORDER);
			defEventText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			controls.put(type + CommonIndexUtils.DOT +Messages.getString("channel.destination.details.DefaultEvent"), defEventText);
			StudioUIUtils.setDropTarget(defEventText, ELEMENT_TYPES.DESTINATION);
			defEventText.addModifyListener(new ModifyListener(){

				@Override
				public void modifyText(ModifyEvent e) {
					try {
						defEventText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
						defEventText.setToolTipText("");

						if (controls.get(type + CommonIndexUtils.DOT +"EventProperty") != null) {
							Control testDropDownControl = (Control) controls.get(type + CommonIndexUtils.DOT + "EventProperty");
							if (testDropDownControl instanceof Combo) {
								if (destination.getEventURI() == null || !destination.getEventURI().equalsIgnoreCase(defEventText.getText().trim())) {
									((Combo)testDropDownControl).setText("");
									((Combo)testDropDownControl).removeAll();
								}
								if (!defEventText.getText().trim().equals("")) {
									EntityElement entityElement = ClusterConfigProjectUtils.getEntityElementForPath(project, defEventText.getText().trim());
									if (entityElement != null) {
										ArrayList<String> propertyArrayList = ClusterConfigProjectUtils.getEntityElementProperties(entityElement);
										String[] propertyArray = propertyArrayList.toArray(new String[propertyArrayList.size()]);
										((Combo)testDropDownControl).setItems(propertyArray);
									}
								}
							}
						}
						if (destination.getEventURI() == null && !defEventText.getText().trim().equals("")) {

							if(!validEventURI(defEventText)) {
//								return;
							}

							Command command=new SetCommand(editingDomain, destination,ChannelPackage.eINSTANCE.getDestination_EventURI(), defEventText.getText()) ;			
							EditorUtils.executeCommand(formViewer.getEditor(), command);
							return;
						}
						if (destination.getEventURI() != null && defEventText.getText().trim().equals("")) {
							Command command=new SetCommand(editingDomain, destination,ChannelPackage.eINSTANCE.getDestination_EventURI(), null) ;			
							EditorUtils.executeCommand(formViewer.getEditor(), command);
							return;
						}

						if (!validEventURI(defEventText)) {
//							return;
						}

						if (destination.getEventURI() != null && !destination.getEventURI().equalsIgnoreCase(defEventText.getText())) {

							if (!validEventURI(defEventText)) {
//								return;
							}

							Command command=new SetCommand(editingDomain, destination,ChannelPackage.eINSTANCE.getDestination_EventURI(), defEventText.getText()) ;			
							EditorUtils.executeCommand(formViewer.getEditor(), command);
						}
					}
					catch(Exception e1) {
						e1.printStackTrace();
					}

				}});
			
			addHyperLinkFieldListener(link, defEventText, formViewer.getEditor() , project.getName(), false, false);
			
			Button browseButton = toolkit.createButton(root, Messages.getString("Browse"), SWT.PUSH);
			browseButton.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent e) {
					EventSelector picker = new EventSelector(Display.getDefault().getActiveShell(),project.getName(), defEventText.getText().trim(), false);
					if (picker.open() == Dialog.OK) {
						if(picker.getFirstResult() instanceof Event){
							Event event = (Event) picker.getFirstResult();
							defEventText.setText(event.getFullPath());
						}
					}
				}});
			controls.put(type + CommonIndexUtils.DOT + Messages.getString("Browse"), browseButton);
			toolkit.createLabel(root, Messages.getString("channel.destination.details.SerializerDeserializer"));
			final Combo serializerCombo = new Combo(root, SWT.BORDER | SWT.DROP_DOWN);	// CR BE-12872
			serializerCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			List<String> serializerList = delegate.getSerializerClasses(type);
			serializerCombo.setItems(serializerList.toArray(new String[serializerList.size()]));
			controls.put(type + CommonIndexUtils.DOT + Messages.getString("channel.destination.details.SerializerDeserializer"), serializerCombo);
			serializerCombo.setText(delegate.getDefaultSerializerClass(type));
			serializerCombo.addModifyListener(new ModifyListener(){

				@Override
				public void modifyText(ModifyEvent e) {
					try {
						if (destination.getSerializerDeserializerClass() == null) {
							disableIsJSONPayload(serializerCombo.getText(), type);
							Command command = new SetCommand(editingDomain,(EObject)destination,ChannelPackage.eINSTANCE.getDestination_SerializerDeserializerClass(),serializerCombo.getText());
							EditorUtils.executeCommand(formViewer.getEditor(), command);
							return;
						}
						if (!destination.getSerializerDeserializerClass().equalsIgnoreCase(serializerCombo.getText())) {
							disableIsJSONPayload(serializerCombo.getText(), type);
							Command command = new SetCommand(editingDomain,(EObject)destination,ChannelPackage.eINSTANCE.getDestination_SerializerDeserializerClass(),serializerCombo.getText()) ;			
							EditorUtils.executeCommand(formViewer.getEditor(), command);
						}
					} catch(Exception e1) {
						e1.printStackTrace();
					}
				}});
		}

		//Removing Composite Separator		
		//		toolkit.createLabel(root, "");
		//		Composite comp = toolkit.createCompositeSeparator(root);
		//		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		//		gd.heightHint = 3;
		//		comp.setLayoutData(gd);

		if (!type.equalsIgnoreCase("Local")) {
			toolkit.createLabel(root, "");
		}
		createDestinationComponents(toolkit, root, type);
	}
	
	private void disableIsJSONPayload(String serializerType, final String type) {
		Button  isJSONPayload = (Button)controls.get(type + CommonIndexUtils.DOT + "be.http.jsonPayload");
		if (isJSONPayload != null) {
			if (serializerType.endsWith("SOAPMessageSerializer")) {
				isJSONPayload.setEnabled(false);
			} else {
				isJSONPayload.setEnabled(true);
			}
		}

		Button jmsIsJSONPayload = (Button)controls.get(type + CommonIndexUtils.DOT + "IsJSONPayload");
		if (jmsIsJSONPayload != null) {
			if (serializerType.endsWith("TextMessageSerializer") || serializerType.endsWith("BytesMessageSerializer")) {
				jmsIsJSONPayload.setEnabled(true);
			} else {
				jmsIsJSONPayload.setSelection(false);
				jmsIsJSONPayload.setEnabled(false);
			}
		}
	}

	/**
	 * @param defEventText
	 * @return
	 */
	private boolean validEventURI(Text defEventText){
		if(!isValidEventURI(defEventText.getText())){
			defEventText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
			String problemMessage = Messages.getString("channel.destination.details.DefaultEvent.error", defEventText.getText());
			defEventText.setToolTipText(problemMessage);
			return false;
		}
		return true;
	}

	/**
	 * @param eventURI
	 * @return
	 */
	private boolean isValidEventURI(String eventURI){
		List<Entity> list = CommonIndexUtils.getAllEntities(project.getName(), ELEMENT_TYPES.SIMPLE_EVENT);
		for(Entity entity:list){
			if(entity.getFullPath().equals(eventURI)){
				return true;
			}
		}
		return false;
	}

	/**
	 * @param toolkit
	 * @param parent
	 * @param type
	 */
	private void createDestinationComponents(FormToolkit toolkit,Composite parent, String type) {
		List<PropertyConfiguration> destinationProperties = delegate.getDestinationConfiguration(type);
		EditorUtils editorUtils =  new EditorUtils();
		if(DriverManagerConstants.DRIVER_KAFKA.equals(type)) {
			editorUtils = new KafkaEditorUtils();
		} else if(DriverManagerConstants.DRIVER_KAFKA_STREAMS.equals(type)) {
			editorUtils = new KafkaStreamsEditorUtils();
		}
		
		for (PropertyConfiguration propConfig : destinationProperties) {
			String propDisplayName = propConfig.getDisplayName();
			String propertyLabel = (propDisplayName != null && !propDisplayName.isEmpty()) ? propDisplayName : propConfig.getPropertyName();
			if(propertyLabel.equalsIgnoreCase("Action RuleFunction")) {
				
				/*Hyperlink link = createLinkField(toolkit, parent, Messages.getString("channel.destination.details.DefaultEvent"));
				controls.put(type + CommonIndexUtils.DOT +".link."+Messages.getString("channel.destination.details.DefaultEvent"), link);
				
				addHyperLinkFieldListener(link, defEventText, formViewer.getEditor() , project.getName(), false, false);*/
				editorUtils.renderComponents(toolkit,            
						parent, type,
						propConfig, 
						controls, 
						(ChannelFormEditor)formViewer.getEditor());
				toolkit.createLabel(parent, "");
				continue;
				
			}
			Label label = toolkit.createLabel(parent, propertyLabel + ":");
			if (propConfig.isMandatory()) {
				label.setText(propertyLabel + "*:");
			}
			editorUtils.renderComponents(toolkit,            
					parent, type,
					propConfig, 
					controls, 
					(ChannelFormEditor)formViewer.getEditor());
				toolkit.createLabel(parent, " ");
			}
	}

	/**
	 * Tests if the control under the provided key is currently in the destination details page.
	 * 
	 * @param key
	 *            the control key
	 * @return <code>true</code> if control exists, <code>false</code>
	 *         otherwise.
	 */
	protected boolean hasControl(Object key) {
		return controls.containsKey(key);
	}

	/**
	 * @param toolkit
	 * @param parent
	 * @param span
	 */
	protected void createSpacer(FormToolkit toolkit, Composite parent, int span) {
		Label spacer = toolkit.createLabel(parent, "");
		GridData gd = new GridData();
		gd.horizontalSpan = span;
		spacer.setLayoutData(gd);
	}

	/**
	 * @param parent
	 * @param size
	 */
	protected void updateLayout(Composite parent, int SIZEaddend, String type){
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		if(type.equalsIgnoreCase("LOCAL")){
			data.heightHint = HEIGHTHINTnormal;
		} else if (type.equalsIgnoreCase(DriverManagerConstants.DRIVER_KAFKA)) {
			data.heightHint = ((SIZEaddend + SIZEaugend) * 32) - HEIGHTHINTmin + 200;
		} else if (type.equalsIgnoreCase(DriverManagerConstants.DRIVER_KAFKA_STREAMS)) {
			data.heightHint = ((SIZEaddend + SIZEaugend) * 32) - HEIGHTHINTmin + 400;
		} else if (type.equalsIgnoreCase("MQTT")) {
			data.heightHint = ((SIZEaddend + SIZEaugend) * 34) - HEIGHTHINTmin+150;
		} else if(type.equalsIgnoreCase("JMS")){
			data.heightHint = ((SIZEaddend + SIZEaugend) * 31) - HEIGHTHINTmin+100;
		}else {
			data.heightHint = ((SIZEaddend + SIZEaugend) * LAYOUTmultiplier) - HEIGHTHINTmin+100;
		}
		parent.setLayoutData(data);
		parent.layout();
		managedForm.getForm().layout();
	}

	/**
	 * @param destName
	 * @return
	 */
	private boolean isDuplicateDestinationName(String destName){
		DriverConfig driverConfig = (DriverConfig)destination.eContainer();
		for(Destination destination:driverConfig.getDestinations()){
			if(destination.getName().equalsIgnoreCase(destName)){
				return true;
			}
		}
		return false;
	}
}