package com.tibco.cep.studio.ui.editors.channels;

import static com.tibco.cep.designtime.core.model.service.channel.util.DriverManagerConstants.DRIVER_AS;
import static com.tibco.cep.designtime.core.model.service.channel.util.DriverManagerConstants.DRIVER_FTL;
import static com.tibco.cep.designtime.core.model.service.channel.util.DriverManagerConstants.DRIVER_HAWK;
import static com.tibco.cep.designtime.core.model.service.channel.util.DriverManagerConstants.DRIVER_HTTP;
import static com.tibco.cep.designtime.core.model.service.channel.util.DriverManagerConstants.DRIVER_JMS;
import static com.tibco.cep.designtime.core.model.service.channel.util.DriverManagerConstants.DRIVER_RV;
import static com.tibco.cep.designtime.core.model.service.channel.util.DriverManagerConstants.DRIVER_SB;
import static com.tibco.cep.designtime.core.model.service.channel.util.DriverManagerConstants.DRIVER_AS3;
import static com.tibco.cep.designtime.core.model.service.channel.util.DriverManagerConstants.DRIVER_MQTT;
import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.createDependencyDiagramAction;
import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.createExtendedConfiguration;
import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.createExtendedPropertyInstanceWidgets;
import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.createPropertyInstanceWidgets;
import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.getInstanceDriverType;
import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.getUniqueName;
import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.isOnlyExtendedPropertiesPresent;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.addLinkFieldListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.CopyCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.be.util.GUIDGenerator;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.ModelPackage;
import com.tibco.cep.designtime.core.model.PropertyMap;
import com.tibco.cep.designtime.core.model.service.channel.CONFIG_METHOD;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.ChannelFactory;
import com.tibco.cep.designtime.core.model.service.channel.ChannelPackage;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.designtime.core.model.service.channel.impl.DestinationImpl;
import com.tibco.cep.designtime.core.model.service.channel.util.DriverManagerConstants;
import com.tibco.cep.studio.common.validation.utils.CommonValidationUtils;
import com.tibco.cep.studio.ui.ChannelFormEditorInput;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.actions.RenameElementAction;
import com.tibco.cep.studio.ui.editors.channels.contoller.PropertyConfiguration;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tibco.cep.studio.ui.editors.utils.EntityImages;
import com.tibco.cep.studio.ui.editors.utils.KafkaEditorUtils;
import com.tibco.cep.studio.ui.editors.utils.KafkaStreamsEditorUtils;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.forms.AbstractChannelFormViewer;
import com.tibco.cep.studio.ui.forms.ChannelDetailsPart;
import com.tibco.cep.studio.ui.util.GvField;
/**
 *
 * @author sasahoo
 *
 */
public class ChannelFormViewer extends AbstractChannelFormViewer {

	public static final String DESTINATION_UNIQUE_IDENTIFIER= "NewDestination_";
//	public static final Font mandatoryFont = new Font(Display.getDefault(),"Tahoma", 11, SWT.BOLD);
	protected org.eclipse.jface.action.Action dependencyDiagramAction;

	private Button browseButton;
	private Link resourceLink;

	/**
	 * @param editor
	 * @param project
	 * @throws PartInitException
	 */
	public ChannelFormViewer(ChannelFormEditor editor, String project) throws PartInitException {
		try {
			this.editor = editor;
			if (editor != null && editor.getEditorInput() instanceof ChannelFormEditorInput) {
				this.channel = ((ChannelFormEditorInput) editor.getEditorInput()).getChannel();
			} else {
				this.channel = editor.getChannel();
			}
			delegate = new ChannelFormFeederDelegate(project);//Instantiating ChannelFomFeederDelegate
			properties = channel.getDriver().getProperties();//Getting the driver Properties instance
			EditorUtils.backwardCompatibility(properties);

			String type = channel.getDriver().getDriverType().getName();
			if (delegate.getExtendedConfiguration(type).size() > 0) {
				if (channel.getDriver().getExtendedConfiguration() == null) {
					createExtendedConfiguration(channel.getDriver(), delegate, type);
				}
			}
		} catch (Exception e) {
			throw new PartInitException(e.getMessage());
		}
	}

	/**
	 * @param container
	 */
	public void createPartControl(Composite container) {
		controls = new HashMap<Object, Object>();

		super.createPartControl(container, Messages.getString("channel.editor.title")+ " " +channel.getName() ,EntityImages.getImage(EntityImages.CHANNEL));

		dependencyDiagramAction = createDependencyDiagramAction(editor, getForm(), editor.getProject());
		getForm().getToolBarManager().add(dependencyDiagramAction);
		getForm().updateToolBar();

		super.createToolBarActions();

		if(channel.getDriver() != null) {
			driverCombo.setText(channel.getDriver().getDriverType().getName());
			if(properties != null)
				createPropertyInstanceWidgets(properties, channel.getDriver().getDriverType().getName(), controls);
		}
		if(channel.getDriver().getExtendedConfiguration()!=null &&
				channel.getDriver().getExtendedConfiguration().getProperties().size()>0) {
			   createExtendedPropertyInstanceWidgets(channel.getDriver().getExtendedConfiguration(), channel.getDriver().getDriverType().getName(), controls);
		}
		setExtendedConfigurationVisible(channel.getDriver().getDriverType().getName());

		//Showing Properties page for default Method Of Config
		if(channel.getDriver().getConfigMethod() == CONFIG_METHOD.PROPERTIES) {
			methodsConfigCombo.setText("Properties");
			List<PropertyConfiguration> driverProperties = delegate.getDriverProperties(channel.getDriver().getDriverType().getName());
			configPageBook.showPage(channel.getDriver().getDriverType().getName());
			GridData data = new GridData(GridData.FILL_HORIZONTAL);
			data.heightHint = driverProperties.size()* 45;
			details.setLayoutData(data);
			configSection.setText("Properties");
			configSection.setEnabled(true);
			configSection.setExpanded(false);
			configSection.setExpanded(true);
			configSection.layout();
		}

		//For HTTP Channel, setMethodConfig as Resource
		//Disabling Methods Of Configuration Combo field
		if (DriverManagerConstants.DRIVER_HTTP.
				equals(channel.getDriver().getDriverType().getName())) {
			methodsConfigCombo.setEnabled(false);
			if (serverTypeCombo != null) {
				serverTypeCombo.setEnabled(false);
			}
		} else if (DriverManagerConstants.DRIVER_KAFKA.
				equals(channel.getDriver().getDriverType().getName())) {
			methodsConfigCombo.setText("Properties");
			methodsConfigCombo.setEnabled(false);
			if (serverTypeCombo != null) {
				serverTypeCombo.setEnabled(false);
			}
		} else if (DriverManagerConstants.DRIVER_KAFKA_STREAMS.
				equals(channel.getDriver().getDriverType().getName())) {
			methodsConfigCombo.setText("Properties");
			methodsConfigCombo.setEnabled(false);
		} else if (DriverManagerConstants.DRIVER_KINESIS.
				equals(channel.getDriver().getDriverType().getName())) {
			methodsConfigCombo.setText("Properties");
			methodsConfigCombo.setEnabled(false);
			if (serverTypeCombo != null) {
				serverTypeCombo.setEnabled(false);
			}
		}

		sashForm.setWeights(new int[] {Ws1,Ws2 }); //Setting default weights for the Destination details
		init();

		if(!editor.isEnabled()) {
			readOnlyWidgets();
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractMasterDetailsFormViewer#createGeneralPart(org.eclipse.ui.forms.widgets.ScrolledForm, org.eclipse.ui.forms.widgets.FormToolkit)
	 */
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
		layout.numColumns = 2;
		sectionClient.setLayout(layout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 50;
		sectionClient.setLayoutData(gd);

		toolkit.createLabel(sectionClient, Messages.getString("Description"),SWT.NONE).setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		channelDescText = toolkit.createText(sectionClient,"", SWT.BORDER  | SWT.MULTI | SWT.V_SCROLL | SWT.WRAP);
		String desc = channel.getDescription();
		channelDescText.setText(desc);
		channelDescText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				try{
					if(!channel.getDescription().equalsIgnoreCase(channelDescText.getText())) {
						Command command=new SetCommand(getEditingDomain(),(EObject)(Entity)channel,ModelPackage.eINSTANCE.getEntity_Description(),channelDescText.getText()) ;
						EditorUtils.executeCommand(editor, command);
					}
				}
				catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 600;
		gd.heightHint = 30;
		channelDescText.setLayoutData(gd);
		toolkit.createLabel(sectionClient,Messages.getString("channel.Driver"));
		driverCombo = new Combo(sectionClient, SWT.BORDER | SWT.READ_ONLY);
		driversArray = delegate.getConfiguredDriverTypes(channel.getOwnerProjectName());
		driverCombo.setItems(driversArray);
		driverCombo.addModifyListener(new DriverModifyListener());
		gd = new GridData();
		//gd.widthHint=100;
		driverCombo.setLayoutData(gd);
		methodsConfigLabel = toolkit.createLabel(sectionClient, Messages.getString("channel.MethodsofConfiguration"));
//		methodsConfigLabel.setFont(mandatoryFont);
		methodsConfigCombo = new Combo(sectionClient, SWT.BORDER | SWT.READ_ONLY);
		String[] array2 = new String[]{"Properties", "Resource"};
		methodsConfigCombo.setItems(array2);
		methodsConfigCombo.setText("Resource");
		gd = new GridData();
		//gd.widthHint=100;
		methodsConfigCombo.setLayoutData(gd);
		toolkit.paintBordersFor(sectionClient);
		methodsConfigCombo.addModifyListener(new ConfigModifyListener());
		createExtendedConfigurationPart(form, toolkit);
		createConfigurationPart(form, toolkit);
	}

	/**
	 * @param toolkit
	 */
	protected void createExtendedConfigurationPart(final ScrolledForm form, final FormToolkit toolkit) {
		extndconfigSection = toolkit.createSection(form.getBody(), Section.TITLE_BAR| Section.EXPANDED | Section.TWISTIE);
		extndconfigSection.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		extndconfigSection.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		extndconfigSection.setLayoutData(gd);
		extndconfigSection.setText(Messages.getString("channel.extd.config"));
		extndconfigSection.setExpanded(true);
		Composite sectionClient = toolkit.createComposite(extndconfigSection);
		extndconfigSection.setClient(sectionClient);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		sectionClient.setLayout(layout);
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		layoutData.heightHint = 50;
		sectionClient.setLayoutData(layoutData);
		extndComposite = toolkit.createComposite(sectionClient);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.heightHint = 50;
		extndComposite.setLayoutData(data);
		extndComposite.setLayout(new FillLayout());
		extndConfigPageBook = toolkit.createPageBook(extndComposite, SWT.NONE);
		for (String driverType:driversArray) {
			createExtendedConfigurationPages(toolkit, driverType);
		}
	}

	/**
	 * @param toolkit
	 * @param driverType
	 */
	protected void createExtendedConfigurationPages(FormToolkit toolkit,String driverType) {
		Composite root = extndConfigPageBook.createPage(driverType);
		GridLayout layout = new GridLayout(1, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		root.setLayout(layout);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		root.setLayoutData(gd);

		Composite extConfComposite = toolkit.createComposite(root);
		layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		extConfComposite.setLayout(layout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		extConfComposite.setLayoutData(gd);
		//For Rendering Extended Configurations
		List<PropertyConfiguration> driverProperties = delegate.getExtendedConfiguration(driverType);
//		EditorUtils editorUtils = DriverManagerConstants.DRIVER_KAFKA.equals(driverType) ? new KafkaEditorUtils()
//				: new EditorUtils();
		EditorUtils editorUtils =  new EditorUtils();
		if(DriverManagerConstants.DRIVER_KAFKA.equals(driverType)) {
			editorUtils = new KafkaEditorUtils();
		} else if(DriverManagerConstants.DRIVER_KAFKA_STREAMS.equals(driverType)) {
			editorUtils = new KafkaStreamsEditorUtils();
		}
		for (PropertyConfiguration propConfig : driverProperties) {
			String propertyLabel = propConfig.getDisplayName();
			Label label = toolkit.createLabel(extConfComposite, propertyLabel + ":" + "\t        ");
			if(propConfig.isMandatory()) {
				label.setText(propertyLabel + "*:" + "\t");
			}
			Object controlObj = editorUtils.renderComponents(toolkit,
							 extConfComposite, driverType,
							 propConfig,
							 controls,
							 (ChannelFormEditor)editor);

			Control control=null;
			if(controlObj instanceof Control){
				control=(Control) controlObj;
				gd = new GridData();
				if(control instanceof Combo) {
					gd.widthHint=150;
				}else if(control instanceof Text) {
					gd.widthHint=400;
				}
				control.setLayoutData(gd);
				if(control instanceof Combo) {
					//Handling Server Type Combo for HTTP
					if (propConfig.getPropertyName().equals("serverType")) {
						serverTypeCombo = (Combo)control;
					}
					((Combo)control).addModifyListener(new ExtendConfigModifyListener());
				}
			}
		}
	}

	/**
	 * @param form
	 * @param toolkit
	 */
	protected void createConfigurationPart(final ScrolledForm form,final FormToolkit toolkit) {
		configSection = toolkit.createSection(form.getBody(), Section.TITLE_BAR| Section.EXPANDED | Section.TWISTIE);
		configSection.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		configSection.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		configSection.setLayoutData(gd);
		configSection.setText(Messages.getString("channel.resource") + "              ");
		configSection.setExpanded(true);
		Composite sectionClient = toolkit.createComposite(configSection);
		configSection.setClient(sectionClient);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		sectionClient.setLayout(layout);
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		layoutData.heightHint = 50;
		sectionClient.setLayoutData(layoutData);
		details = toolkit.createComposite(sectionClient);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.heightHint = 50;
		details.setLayoutData(data);
		details.setLayout(new FillLayout());
		configPageBook = toolkit.createPageBook(details, SWT.NONE);
		createResourceConfigPage(toolkit);
		for (String type : driversArray) {
			createPropertiesConfigPage(toolkit, type);
		}
		configPageBook.showPage(RESOURCE_CONFIG_PAGE);
		toolkit.paintBordersFor(sectionClient);
		createDestinationPart(form, toolkit);
	}

	/**
	 *
	 * @param toolkit
	 * @param driverType
	 */
	private void createPropertiesConfigPage(FormToolkit toolkit,String driverType) {
		try {
			Composite root = configPageBook.createPage(driverType);
			GridLayout layout = new GridLayout(2, false);
			layout.marginWidth = 0;
			layout.marginHeight = 0;
			root.setLayout(layout);
			List<PropertyConfiguration> driverProperties = delegate.getDriverProperties(driverType);

			// BE-24990: workaround for case in which driver.xml was updated with new properties, but the channel was created against old drivers.xml.  In this case, the added properties are missing from .channel file
			if (properties != null && (properties.getProperties().size() < driverProperties.size())) {
				PropertyMap updatedMap = getInstanceDriverType(delegate, channel.getDriver().getDriverType().getName());
				addMissingProperties(properties, updatedMap);
			}
			boolean isOnlyExtendedConfig = isOnlyExtendedPropertiesPresent(driverType, delegate);
//			EditorUtils editorUtils = DriverManagerConstants.DRIVER_KAFKA.equals(driverType) ? new KafkaEditorUtils() : new EditorUtils();
			EditorUtils editorUtils =  new EditorUtils();
			if(DriverManagerConstants.DRIVER_KAFKA.equals(driverType)) {
				editorUtils = new KafkaEditorUtils();
			} else if(DriverManagerConstants.DRIVER_KAFKA_STREAMS.equals(driverType)) {
				editorUtils = new KafkaStreamsEditorUtils();
			}
			for (PropertyConfiguration propConfig : driverProperties) {

				if (!isOnlyExtendedConfig) {
					String propertyLabel = propConfig.getDisplayName();
					if (propertyLabel == null || propertyLabel.trim().isEmpty()) {
						propertyLabel = propConfig.getPropertyName();
					}
					String gap = "";
					if (driverType.equalsIgnoreCase(DRIVER_RV)) {
						gap =  "\t\t        ";
					}
					if (driverType.equalsIgnoreCase(DRIVER_JMS)) {
						gap =  "\t       ";
					}

					boolean hiddenProp = EditorUtils.isHiddenProperty(driverType, propConfig);
					Label label = hiddenProp ? null : toolkit.createLabel(root, propertyLabel + ":" + gap);

					if (!hiddenProp && propConfig.isMandatory()) {
						if (driverType.equalsIgnoreCase(DRIVER_RV)) {
							gap =  "\t\t       ";
						}
						if (driverType.equalsIgnoreCase(DRIVER_JMS)) {
							gap =  "\t        ";
						}
						label.setText(propertyLabel + "*:" + gap);
					}
					Object controlObj = editorUtils.renderComponents(toolkit,
													   root, driverType,
													   propConfig,
													   controls,
													   (ChannelFormEditor)editor);

					Control control=null;
					if(controlObj instanceof Control){
						control=(Control) controlObj;
						GridData gd = new GridData();
						if (control instanceof Combo) {
							gd.widthHint = 250;
						} else if (control instanceof Text) {
							gd.widthHint = 400;
						}
						control.setLayoutData(gd);
					}

				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * @param toolkit
	 */
	private void createResourceConfigPage(FormToolkit toolkit) {
		Composite root = configPageBook.createPage(RESOURCE_CONFIG_PAGE);

		GridLayout layout = new GridLayout(3, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		root.setLayout(layout);

		resourceLink = new Link(root, SWT.NONE);
		resourceLink.setText( "<a>"+ Messages.getString("channel.resource.label") + "</a>" + "                     ");

		resourceText = toolkit.createText(root, "", SWT.BORDER);

		GridData gd = new GridData();
		gd.widthHint= 400;

		resourceText.setLayoutData(gd);
//		resourceText.setText(channel.getDriver().getReference());
		resourceText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				try {
					if (!getEditor().isEnabled()) {
						return;
					}
					resourceText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
					resourceText.setToolTipText("");
					if (channel.getDriver().getReference() ==  null && !resourceText.getText().equalsIgnoreCase("")) {
						Command command=new SetCommand(getEditingDomain(),channel.getDriver(), ChannelPackage.eINSTANCE.getDriverConfig_Reference(), resourceText.getText()) ;
						EditorUtils.executeCommand(editor, command);
						return;
					}

					if (channel.getDriver().getReference() ==  null && resourceText.getText().equalsIgnoreCase("")) {
						return;
					}

					//Checking valid reference Path
					if (!resourceText.getText().trim().equals("") &&
							!CommonValidationUtils.resolveReference(resourceText.getText().trim(), channel.getOwnerProjectName())) {
						resourceText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
						String problemMessage = com.tibco.cep.studio.ui.util.Messages.getString("invalid.entry", resourceText.getText(), Messages.getString("channel.resource"));
						resourceText.setToolTipText(problemMessage);
//						return;
					}

					if (!channel.getDriver().getReference().equalsIgnoreCase(resourceText.getText())) {
						Object value = resourceText.getText().trim().equalsIgnoreCase("") ? null : resourceText.getText().trim();
						Command command =new SetCommand(getEditingDomain(),channel.getDriver(),ChannelPackage.eINSTANCE.getDriverConfig_Reference(),value) ;
						EditorUtils.executeCommand(editor, command);
					}
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		addLinkFieldListener(resourceLink, resourceText, editor, editor.getProject().getName(), false, true);

		browseButton = toolkit.createButton(root, Messages.getString("Browse"), SWT.PUSH);
		browseButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Set<String> extn = new HashSet<String>();
				if (DRIVER_JMS.equals(channel.getDriver().getDriverType().getName())) {
					extn.add("sharedjmscon");
				}
				if (DRIVER_RV.equals(channel.getDriver().getDriverType().getName())) {
					extn.add("rvtransport");
				}
				if (DRIVER_HTTP.equals(channel.getDriver().getDriverType().getName())) {
					extn.add("sharedhttp");
				}
				if (DRIVER_AS.equals(channel.getDriver().getDriverType().getName())) {
					extn.add("sharedascon");
				}
				if (DRIVER_AS3.equals(channel.getDriver().getDriverType().getName())) {
					extn.add("as3");
				}
				if (DRIVER_HAWK.equals(channel.getDriver().getDriverType().getName())) {
					extn.add("hawk");
				}
				if (DRIVER_FTL.equals(channel.getDriver().getDriverType().getName())) {
					extn.add("ftl");
				}
				if (DRIVER_SB.equals(channel.getDriver().getDriverType().getName())) {
					extn.add("sharedsb");
				}
				if (DRIVER_MQTT.equals(channel.getDriver().getDriverType().getName())) {
					extn.add("sharedmqttcon");
				}

				SharedResourcesSelector picker = new SharedResourcesSelector(
						Display.getDefault().getActiveShell(),editor.getProject(),resourceText.getText(),extn);
				if (picker.open() == Dialog.OK) {
					if (picker.getFirstResult() != null) {
						resourceText.setText(picker.getFirstResult().toString());

					}
				}
			}
		});
	}

	/**
	 * @param toolkit
	 * @param composite
	 */
	@SuppressWarnings("unused")
	private void createDriverConfigEmptyDetailsInfo(FormToolkit toolkit, Composite composite) {
		FillLayout fl = new FillLayout();
		fl.marginHeight = 10;
		fl.marginWidth = 10;
		composite.setLayout(fl);
		FormText txt = toolkit.createFormText(composite, false);
		txt	.setText( Messages.getString("channel.driver.empty.config.description"), false, false);
		txt.setForeground(txt.getDisplay().getSystemColor(SWT.COLOR_DARK_GRAY));
	}

	/**
	 * @param form
	 * @param toolkit
	 */
	protected void createDestinationPart(final ScrolledForm form,final FormToolkit toolkit) {
		Section section = toolkit.createSection(form.getBody(), Section.TITLE_BAR );
		section.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		section.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		section.setText(Messages.getString("channel.Destinations"));
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		section.setLayoutData(gd);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractMasterDetailsFormViewer#createMasterPart(org.eclipse.ui.forms.IManagedForm, org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createMasterPart(final IManagedForm managedForm, Composite parent) {
		FormToolkit toolkit = managedForm.getToolkit();
		Section section = toolkit.createSection(parent, Section.NO_TITLE);
		section.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		section.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		section.marginWidth = 2;
		Composite client = toolkit.createComposite(section, SWT.WRAP);
		GridLayout layout = new GridLayout();
		layout.numColumns =1;
		layout.marginWidth = 2;
		layout.marginHeight = 2;
		client.setLayout(layout);
		GridData gd = new GridData(GridData.FILL_BOTH);
		toolkit.paintBordersFor(client);
		createToolbar(client, true);
		section.setClient(client);
		final SectionPart spart = new SectionPart(section);
		managedForm.addPart(spart);
		Table table = toolkit.createTable(client, SWT.NULL);
		gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 20;
		gd.widthHint = 100;
		table.setLayoutData(gd);
		viewer = new TableViewer(table);
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				if(viewer.getSelection().isEmpty()) {
					removeRowButton.setEnabled(false);
					duplicateButton.setEnabled(false);
				}else{
					if(getEditor().isEnabled()) {
						removeRowButton.setEnabled(true);
						duplicateButton.setEnabled(true);
					}
				}
				managedForm.fireSelectionChanged(spart, event.getSelection());
				viewer.getTable().setFocus();
			}
		});

		viewer.setContentProvider(new DestinationContentProvider());
		viewer.setLabelProvider(new DestinationLabelProvider());

		//Rename Context Menu support from Destination Table
		MenuManager popupMenu = new MenuManager();
	    popupMenu.add(new RenameDestinationAction() {});
	    Menu menu = popupMenu.createContextMenu(table);
	    table.setMenu(menu);

		viewer.setInput(channel);
	}

	/**
	 * @param detailsPart
	 */
	protected void registerPages(ChannelDetailsPart detailsPart) {
		channelsDetailsPart.registerPage(DestinationImpl.class, new ChannelDestinationsPage(getEditingDomain(),
				                                                                            editor.getProject(),
				                                                                            viewer,
				                                                                            this));
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractMasterDetailsFormViewer#createDetailsPart(org.eclipse.ui.forms.IManagedForm, org.eclipse.swt.widgets.Composite)
	 */
	protected  void createDetailsPart(final IManagedForm managedForm, Composite parent) {
		channelsDetailsPart = new ChannelDetailsPart(managedForm, parent, SWT.NULL);
		managedForm.addPart(channelsDetailsPart);
		registerPages(channelsDetailsPart);
	}

	private void init() {
		if(viewer == null) {
			return;
		}
		if(viewer.getTable().getItemCount() > 0) {
			//If destination double clicked from Project explorer,
			//then to select the same destination in the Channel editor's Destination Viewer

			Destination destination = null;
			if (editor != null && editor.getEditorInput() instanceof ChannelFormEditorInput) {
				destination = ((ChannelFormEditorInput) editor.getEditorInput()).getSelectedDestination();
			}

			Entity entity = StudioUIPlugin.getDefault().getSelectedEntity();
			if (entity != null && entity instanceof Destination) {
				destination = (Destination)entity;
				setDefaultDestinationSelection(destination.getName());
				getDriverCombo().setEnabled(false);
				removeRowButton.setEnabled(true);
				StudioUIPlugin.getDefault().setSelectedEntity(null);
				return;
			}

			if (destination == null) {
				//By default, details of first destination from the list will be shown up when opening Channel Editor.
				destination = (Destination)viewer.getElementAt(0);
			}

			viewer.setSelection(new StructuredSelection(destination));
			getDriverCombo().setEnabled(false);
			removeRowButton.setEnabled(true);
		} else {
			getDriverCombo().setEnabled(true);
			removeRowButton.setEnabled(false);
		}
	}

	class DestinationLabelProvider extends LabelProvider	implements	ITableLabelProvider {
		public String getColumnText(Object obj, int index) {
			if (obj instanceof Destination) {
				return ((Destination)obj).getName();
			}
			return obj.toString();
		}
		public Image getColumnImage(Object obj, int index) {
			return EntityImages.getImage(EntityImages.DESTINATION);
		}
	}

	class DestinationContentProvider implements IStructuredContentProvider {
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof Channel) {
				EList<Destination> destinations= channel.getDriver().getDestinations();
				return destinations.toArray();
			}
			return new Object[0];
		}
		public void dispose() {
		}
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}


	/**
	 * @param channel
	 */
	public void doRefresh(Channel newChannel) {
		this.channel = newChannel;
		properties = channel.getDriver().getProperties();//Getting the driver Properties instance
		final String reference = this.channel.getDriver().getReference();

		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				if (reference != null) {
					resourceText.setText(reference);
				}else{
					resourceText.setText("");
				}
				String selectedDriverType = channel.getDriver().getDriverType().getName();
				if (properties != null) {
					createPropertyInstanceWidgets(properties, selectedDriverType, controls);
				}
				if (delegate.getExtendedConfiguration(selectedDriverType).size()>0) {
					createExtendedConfiguration(channel.getDriver(), delegate, selectedDriverType);
					createExtendedPropertyInstanceWidgets(channel.getDriver().getExtendedConfiguration(), channel.getDriver().getDriverType().getName(), controls);
				}
				setExtendedConfigurationVisible(selectedDriverType);
				//Showing Properties page for default Method Of Config
				if (channel.getDriver().getConfigMethod() == CONFIG_METHOD.PROPERTIES) {
					methodsConfigCombo.setText("Properties");
					List<PropertyConfiguration> driverProperties = delegate.getDriverProperties(channel.getDriver().getDriverType().getName());
					configPageBook.showPage(channel.getDriver().getDriverType().getName());
					GridData data = new GridData(GridData.FILL_HORIZONTAL);
					data.heightHint = driverProperties.size()* 30;
					details.setLayoutData(data);
					configSection.setText("Properties");
					configSection.setEnabled(true);
					configSection.setExpanded(false);
					configSection.setExpanded(true);
					configSection.layout();
				}
				int index = viewer.getTable().getSelectionIndex();
				viewer.refresh();
				if (index >= 0 && index < viewer.getTable().getItemCount()) {
					viewer.setSelection(new StructuredSelection(channel.getDriver().getDestinations().get(index)));
					viewer.getTable().setFocus();
				}
				if (editor.isDirty()) {
					editor.setModified(false);
					editor.firePropertyChange();
				}
			}});
	}

	private class RenameDestinationAction extends org.eclipse.jface.action.Action {
		public RenameDestinationAction() {
			super("Rename");
		}
		/* (non-Javadoc)
		 * @see org.eclipse.jface.action.Action#run()
		 */
		public void run() {
			if (!viewer.getSelection().isEmpty()) {
				Destination destination = (Destination)((IStructuredSelection)viewer.getSelection()).getFirstElement();
				RenameElementAction act = new RenameElementAction();
				act.selectionChanged(null, new StructuredSelection(destination));
				act.run(null);
			}
		}
	}

	private void readOnlyWidgets() {
		channelDescText.setEditable(false);
		driverCombo.setEnabled(false);
		methodsConfigCombo.setEnabled(false);
		browseButton.setEnabled(false);
		dependencyDiagramAction.setEnabled(false);
		resourceText.setEditable(false);

		addRowButton.setEnabled(false);
		removeRowButton.setEnabled(false);

		resourceLink.setEnabled(false);

		for (Object object: controls.values()) {
			if (object instanceof Text) {
				((Text)object).setEditable(false);
			} else if(object instanceof GvField) {
				((GvField) object).setEnabled(false);
			} else{
				((Control)object).setEnabled(false);
			}
		}
	}

	/**
	 * @param destinationName
	 */
	public void setDefaultDestinationSelection(String destinationName) {
		Destination destination = null;
		int index = 0;
		for (TableItem item : viewer.getTable().getItems()) {
			if (item.getText().equals(destinationName)) {
				destination = (Destination)item.getData();
				break;
			}
			index++;
		}
		if (destination != null) {
			viewer.setSelection(new StructuredSelection(destination));
			viewer.getTable().setTopIndex(index);
		}
	}

	@Override
	protected void add() {

		List<String> destNames = new ArrayList<String>();

		for (Destination dest : channel.getDriver().getDestinations()) {
			destNames.add(dest.getName());
		}

		Destination destination = ChannelFactory.eINSTANCE.createDestination();
		destination.setGUID(GUIDGenerator.getGUID());
		destination.setName(getUniqueName(destNames,DESTINATION_UNIQUE_IDENTIFIER));
		destination.setDriverConfig(channel.getDriver());
		destination.setOwnerProjectName(channel.getOwnerProjectName());

		AddCommand addCommand = new AddCommand(	editor.getEditingDomain(), channel.getDriver().getDestinations(), destination);
		EditorUtils.executeCommand(editor, addCommand);

		viewer.refresh();

		viewer.setSelection(new StructuredSelection(destination));

		if (getDriverCombo().isEnabled()) {
			getDriverCombo().setEnabled(false);
		}

		removeRowButton.setEnabled(true);

	}

	@Override
	protected void remove() {
		int index = viewer.getTable().getSelectionIndex();

		RemoveCommand removeCommand = new RemoveCommand(	editor.getEditingDomain(), channel.getDriver().getDestinations(), channel.getDriver().getDestinations().get(index));
		EditorUtils.executeCommand(editor, removeCommand);

		viewer.refresh();

		if(viewer.getTable().getItemCount() > 0) {
			if (index <=  viewer.getTable().getItemCount() - 1) {
				viewer.setSelection(new StructuredSelection(channel.getDriver().getDestinations().get(index)));
				viewer.getTable().setFocus();
			}
			else if(index - 1  <=  viewer.getTable().getItemCount() - 1) {
				viewer.setSelection(new StructuredSelection(channel.getDriver().getDestinations().get(index-1)));
				viewer.getTable().setFocus();
			}
		}

		if(viewer.getTable().getItemCount() == 0) {
			if(!getDriverCombo().isEnabled()) {
				getDriverCombo().setEnabled(true);
			}
			removeRowButton.setEnabled(false);
		}
	}

	@Override
	protected void duplicate() {
		List<String> destNames = new ArrayList<String>();

		int index = viewer.getTable().getSelectionIndex();
		Destination duplicateFrom = channel.getDriver().getDestinations().get(index);

		CopyCommand.Helper copyHelper = new CopyCommand.Helper();
		CopyCommand copyCommand = new CopyCommand(getEditingDomain(), duplicateFrom, copyHelper);
		EditorUtils.executeCommand(editor, copyCommand);

		Destination destination = (Destination)copyHelper.getCopy(duplicateFrom);

		for (Destination dest : channel.getDriver().getDestinations()) {
			destNames.add(dest.getName());
		}

		destination.setName(getUniqueName(destNames, duplicateFrom.getName() + "_Copy_"));

		AddCommand addCommand = new AddCommand(editor.getEditingDomain(), channel.getDriver().getDestinations(),destination);
		EditorUtils.executeCommand(editor, addCommand);


		if (getDriverCombo().isEnabled()) {
			getDriverCombo().setEnabled(false);
		}

		viewer.refresh();

		viewer.setSelection(new StructuredSelection(destination));

		removeRowButton.setEnabled(true);
		duplicateButton.setEnabled(true);
	}
}
