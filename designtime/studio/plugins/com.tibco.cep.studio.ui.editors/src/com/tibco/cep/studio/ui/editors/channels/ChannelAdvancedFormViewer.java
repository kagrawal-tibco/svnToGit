/**
 *(c) Copyright 2011, TIBCO Software Inc.  All rights reserved.
 *
 * LEGAL NOTICE:  This source code is provided to specific authorized end
 * users pursuant to a separate license agreement.  You MAY NOT use this
 * source code if you do not have a separate license from TIBCO Software
 * Inc.  Except as expressly set forth in such license agreement, this
 * source code, or any portion thereof, may not be used, modified,
 * reproduced, transmitted, or distributed in any form or by any means,
 * electronic or mechanical, without written permission from
 * TIBCO Software Inc.
 */

package com.tibco.cep.studio.ui.editors.channels;

import static com.tibco.cep.studio.ui.editors.domain.DomainUtils.isDouble;
import static com.tibco.cep.studio.ui.editors.domain.DomainUtils.isLong;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.ScrolledPageBook;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.designtime.core.model.ModelPackage;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.PropertyMap;
import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.ChannelFactory;
import com.tibco.cep.designtime.core.model.service.channel.ChannelPackage;
import com.tibco.cep.designtime.core.model.service.channel.DriverConfig;
import com.tibco.cep.designtime.core.model.service.channel.ExtendedConfiguration;
import com.tibco.cep.designtime.core.model.service.channel.HttpChannelDriverConfig;
import com.tibco.cep.designtime.core.model.service.channel.WebApplicationDescriptor;
import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.util.GvUtil;
import com.tibco.cep.studio.core.validation.DefaultResourceValidator;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.channels.contoller.PropertyConfiguration;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tibco.cep.studio.ui.editors.utils.EntityImages;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.forms.AbstractMasterDetailsFormViewer;
import com.tibco.cep.studio.ui.util.GvField;
import com.tibco.cep.studio.ui.util.GvUiUtil;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
import com.tibco.cep.studio.ui.util.TableAutoResizeLayout;

/**
 * @author abhijit
 * 
 */
public class ChannelAdvancedFormViewer extends AbstractMasterDetailsFormViewer {

	protected ChannelFormEditor channelEditor;
	protected Channel channel;
	protected ChannelFormFeederDelegate delegate;
	protected PropertyMap properties;
	protected String[] driversArray;
	protected ScrolledPageBook extndConfigPageBook;
	protected HashMap<Object, Object> controls = null;
	private Composite extndComposite;
	private Section section;

	private Section wappConfigSection;

	private String projectName;

	private Table wappsTable;

	private TableEditor tableEditor;

	private Map<TableItem, Button> itemButtonEditorMap = new HashMap<TableItem, Button>();

	EList<SimpleProperty> propList = null;
	private IProject prj;

	public ChannelAdvancedFormViewer(ChannelFormEditor editor, String project,
			Channel channel, IProject prj) throws PartInitException {
		try {
			this.channelEditor = editor;
			this.channel = channel;
			delegate = new ChannelFormFeederDelegate(project);
			this.projectName = project;
			this.prj = prj;
			final String type = channel.getDriver().getDriverType().getName();
			if (delegate.getExtendedConfiguration(type).size() > 0) {
				final List<String> extenConfigChoices = delegate
						.getExtenConfigChoices(type);
				final EList<SimpleProperty> properties = channel.getDriver()
						.getExtendedConfiguration().getProperties();
				String choiceValue = null;
				boolean found = false;
				for (final String choice : extenConfigChoices) {
					for (final SimpleProperty property : properties) {
						if (property.getValue().equals(choice)) {
							choiceValue = property.getValue();
							found = true;
							break;
						}
					}
					if (found) {
						break;
					}
				}
				if (choiceValue != null) {
					if (channel.getDriver().getChoice() == null) {
						EditorUtils.createExtendedConfigurationForChoice(
								channel.getDriver(), delegate, type,
								choiceValue);
					}
				}
			}
		} catch (Exception e) {
			throw new PartInitException(e.getMessage());
		}
	}

	public void createPartControl(Composite container, Channel channel) {
		this.channel = channel;
		super.createPartControl(
				container,
				Messages.getString("channel.editor.title") + " "
						+ channel.getName(),
				EntityImages.getImage(EntityImages.CHANNEL)); //
		this.createToolBarActions();
	}

	public void setExtendedConfigurationVisible(final String choicePage,
			final String driverType) {
		if (choicePage != null) {
			section.setText(Messages.getString("channel.properties.for"));
			GridData data = new GridData(GridData.FILL_HORIZONTAL);
			data.heightHint = (delegate.getPropertiesForChoice(driverType,
					choicePage).size() + 1) * 63;
			extndComposite.setLayoutData(data);
			extndConfigPageBook.showPage(choicePage);
			section.setEnabled(true);
			section.setExpanded(true);
		} else {
			section.setText("");
			GridData data = new GridData(GridData.FILL_HORIZONTAL);
			if (extndConfigPageBook != null) {
				extndConfigPageBook.showEmptyPage();
			}
			extndComposite.setLayoutData(data);
			section.setEnabled(false);
			getForm().getBody().layout();
		}
	}

	@Override
	protected void createGeneralPart(ScrolledForm form, FormToolkit toolkit) {

		createWebAppConfigurationPart(form, toolkit);

		section = toolkit.createSection(form.getBody(), Section.TITLE_BAR
				| Section.EXPANDED);
		section.setActiveToggleColor(toolkit.getHyperlinkGroup()
				.getActiveForeground());
		section.setToggleColor(toolkit.getColors().getColor(
				IFormColors.SEPARATOR));
		section.setText(Messages.getString("GENERAL_SECTION_TITLE"));
		GridData gd = new GridData(GridData.FILL_BOTH);
		section.setLayoutData(gd);

		Composite client = toolkit.createComposite(section);

		section.setClient(client);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		client.setLayout(layout);
		gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 50;
		client.setLayoutData(gd);

		extndComposite = toolkit.createComposite(client);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.heightHint = 50;
		extndComposite.setLayoutData(data);
		extndComposite.setLayout(new FillLayout());

	}

	/**
	 * @param form
	 * @param toolkit
	 */
	protected void createWebAppConfigurationPart(ScrolledForm form,
			FormToolkit toolkit) {
		wappConfigSection = toolkit.createSection(form.getBody(),
				Section.TITLE_BAR | Section.EXPANDED | Section.TWISTIE);
		wappConfigSection.setActiveToggleColor(toolkit.getHyperlinkGroup()
				.getActiveForeground());
		wappConfigSection.setToggleColor(toolkit.getColors().getColor(
				IFormColors.SEPARATOR));
		wappConfigSection.setText(Messages
				.getString("WAR_CONFIG_SECTION_TITLE"));
		GridData gd = new GridData(GridData.FILL_BOTH);
		wappConfigSection.setLayoutData(gd);

		Composite client = toolkit.createComposite(wappConfigSection);

		wappConfigSection.setClient(client);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		client.setLayout(layout);
		gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 130;
		client.setLayoutData(gd);

		createToolbar(client, false);
		removeRowButton.setEnabled(false);
		createWebAppsTable(client);

		if (!channelEditor.isEnabled()) {
			addRowButton.setEnabled(false);
			removeRowButton.setEnabled(false);
			wappsTable.setEnabled(false);
		}
	}

	/**
	 * @param parent
	 * @return
	 */
	protected void createWebAppsTable(Composite parent) {
		wappsTable = new Table(parent, SWT.BORDER | SWT.SINGLE);
		wappsTable.setLayout(new GridLayout());

		GridData data = new GridData(GridData.FILL_BOTH);
		data.heightHint = 80;
		data.widthHint = 400;
		wappsTable.setLayoutData(data);
		wappsTable.setLinesVisible(true);
		wappsTable.setHeaderVisible(true);

		TableColumn contextURIColumn = new TableColumn(wappsTable, SWT.NULL);
		contextURIColumn.setText(Messages.getString("war.context.uri"));

		TableColumn resPathColumn = new TableColumn(wappsTable, SWT.NULL);
		resPathColumn.setText(Messages.getString("war.resourcepath"));

		new TableColumn(wappsTable, SWT.NULL);

		autoTableLayout(wappsTable);

		setWebAppsTableEditableSupport(wappsTable);

		populateWebAppsTable();

		removeRowButton.setEnabled(wappsTable.getItemCount() > 0);
	}

	/**
	 * @param item
	 */
	private void createBrowseButtonTableEditor(final TableItem item) {
		if (item.isDisposed()) {
			return;
		}
		TableEditor editor = new TableEditor(wappsTable);
		Button browseButton = new Button(wappsTable, SWT.CENTER);
		// browseButton.setText(Messages.getString("Browse"));
		browseButton.setImage(EditorsUIPlugin.getDefault().getImage(
				"icons/browse_file_system.gif"));
		browseButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				String text = item.getText(1);
				NewWebArchiveEntrySelectionDialog dialog = new NewWebArchiveEntrySelectionDialog(
						wappsTable.getShell(), text);
				dialog.open();
				String pathtxt = dialog.getPath().toString();
				if (!pathtxt.isEmpty()) {
					setWebInfoCommand(false, item.getText(0), item.getText(1),
							pathtxt);
					item.setText(1, pathtxt);
				}
			}
		});
		browseButton.pack();

		editor.minimumWidth = browseButton.getSize().x;
		editor.horizontalAlignment = SWT.LEFT;
		itemButtonEditorMap.put(item, browseButton);
		editor.setEditor(browseButton, item, 2);
	}

	protected void add() {
		NewWebArchiveEntrySelectionDialog dialog = new NewWebArchiveEntrySelectionDialog(
				wappsTable.getShell(), null);
		int status = dialog.open();
		if (status == Dialog.OK) {
			String pathtxt = dialog.getPath().toString();
			if (!pathtxt.isEmpty()) {
				final IPath path = dialog.getPath();
				if (path.toFile().exists()) {
					final TableItem item = new TableItem(wappsTable, SWT.CENTER);
					// Handling context uri for ".war" file
					String warpath = pathtxt.endsWith(".war") ? path
							.lastSegment().substring(0,
									path.lastSegment().indexOf(".")) : path
							.lastSegment();
					item.setText(0, "/" + warpath);
					item.setText(1, pathtxt);
					item.setText(2, "");
					createWebDescriptorItem(item);
					createBrowseButtonTableEditor(item);
					removeRowButton.setEnabled(true);
				}
			}
		}
	}

	/**
	 * @param table
	 */
	protected void autoTableLayout(Table table) {
		TableAutoResizeLayout autoTableLayout = new TableAutoResizeLayout(table);
		for (int loop = 0; loop < table.getColumns().length; loop++) {
			autoTableLayout.addColumnData(new ColumnWeightData(1));
		}
		table.setLayout(autoTableLayout);
	}

	/**
	 * @param choiceValue
	 * @param propertyName
	 */
	public void createPagePerChoice(final String choiceValue,
			final String propertyName) {
		extndConfigPageBook = managedForm.getToolkit().createPageBook(
				extndComposite, SWT.NONE);
		createPagesPerChoiceProperties(managedForm.getToolkit(), choiceValue,
				propertyName);
	}

	/**
	 * @param toolkit
	 * @param choiceValue
	 * @param propertyName
	 */
	public void createPagesPerChoiceProperties(final FormToolkit toolkit,
			final String choiceValue, final String propertyName) {
		Composite root = extndConfigPageBook.createPage(choiceValue);
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
		gd.horizontalSpan = 2;
		extConfComposite.setLayoutData(gd);
		final String driverType = channel.getDriver().getDriverType().getName();
		// For rendering choice properties
		final List<PropertyConfiguration> choiceProperties = delegate
				.getPropertiesForChoice(driverType, choiceValue);
		if (choiceProperties == null) {
			return;
		}
		propList = channel.getDriver().getChoice().getExtendedConfiguration()
				.get(0).getProperties();
		toolkit.createLabel(extConfComposite, propertyName + ":");
		Text createText = toolkit.createText(extConfComposite, "", SWT.BORDER
				| SWT.READ_ONLY);
		createText.setEditable(false);
		createText.setText(choiceValue);

		createText.setBackground(extConfComposite.getDisplay().getSystemColor(
				SWT.COLOR_GRAY));

		controls = new HashMap<Object, Object>();
		createPropertyControls(toolkit, choiceProperties, driverType,
				extConfComposite);
		EditorUtils.createExtendedPropertyInstanceWidgets(channel.getDriver()
				.getChoice().getExtendedConfiguration().get(0), channel
				.getDriver().getDriverType().getName(), controls);

		if (!channelEditor.isEnabled()) {
			for (Object object : controls.values()) {
				if (object instanceof Text) {
					((Text) object).setEditable(false);
				} else {
					((Control) object).setEnabled(false);
				}
			}
		}
	}

	/**
	 * @param toolkit
	 * @param choiceProperties
	 * @param gSsl
	 */
	private void createPropertyControls(final FormToolkit toolkit,
			final List<PropertyConfiguration> choiceProperties,
			String driverType, Composite composite) {
		GridData gd;
		for (PropertyConfiguration propConfig : choiceProperties) {
			final Label label = toolkit.createLabel(composite,
					propConfig.getDisplayName() + ":" + "\t        ");
			if (propConfig.isMandatory()) {
				label.setText(propConfig.getDisplayName() + "*:" + "\t");
			}
			final GvField control = renderComponents(toolkit, composite,
					driverType, propConfig, controls,
					(ChannelFormEditor) channelEditor);

			gd = new GridData();
			if (control.getField() instanceof Text) {
				gd.widthHint = 400;
			}
			control.getField().setLayoutData(gd);
		}
	}

	/**
	 * @param newChannel
	 */
	public void doRefresh(final Channel newChannel) {
		this.channel = newChannel;

		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				if (controls == null) {
					return;
				}
				final String type = channel.getDriver().getDriverType()
						.getName();
				if (delegate.getExtendedConfiguration(type).size() > 0) {
					final List<String> extenConfigChoices = delegate
							.getExtenConfigChoices(type);
					final EList<SimpleProperty> properties = channel
							.getDriver().getExtendedConfiguration()
							.getProperties();
					String choiceValue = null;
					boolean found = false;
					for (final String choice : extenConfigChoices) {
						for (final SimpleProperty property : properties) {
							if (property.getValue().equals(choice)) {
								choiceValue = property.getValue();
								found = true;
								break;
							}
						}
						if (found) {
							break;
						}
					}
					EditorUtils.createExtendedConfigurationForChoice(
							channel.getDriver(), delegate, type, choiceValue);
					EList<ExtendedConfiguration> extendedConfiguration = channel
							.getDriver().getChoice().getExtendedConfiguration();
					final ExtendedConfiguration extendedConfigurationForChoice = extendedConfiguration
							.get(0);
					EditorUtils.createExtendedPropertyInstanceWidgets(
							extendedConfigurationForChoice, channel.getDriver()
									.getDriverType().getName(), controls);
				}
				if (channelEditor.isDirty()) {
					channelEditor.setModified(false);
					channelEditor.firePropertyChange();
				}
			}
		});
	}

	/**
	 * @return
	 */
	public Composite getExtndComposite() {
		return extndComposite;
	}

	/**
	 * @param toolkit
	 * @param parent
	 * @param propInstance
	 * @param displyedValueList
	 * @param propertyConfiguration
	 * @param controls
	 * @param editor
	 */
	public GvField renderComponents(FormToolkit toolkit, Composite parent,
			String driverType, PropertyConfiguration propertyConfiguration,
			final HashMap<Object, Object> controls,
			final ChannelFormEditor editor) {
		if (propertyConfiguration.getPropertyConfigType().equalsIgnoreCase(
				"Boolean")) {
			
			String value=null;
			for(SimpleProperty prop:propList){
				if(prop.getName().equalsIgnoreCase(propertyConfiguration.getPropertyName())){
					value=prop.getValue();
					break;
				}
			}
			final GvField gvField = createGvCheckboxField(parent,"",value,editor,propertyConfiguration);
			controls.put(driverType + CommonIndexUtils.DOT + propertyConfiguration.getPropertyName(),gvField );

			
			
			
/*			final Button button = toolkit.createButton(parent, "", SWT.CHECK);
			button.setSelection(new Boolean(propertyConfiguration
					.getDefaultValue()).booleanValue());
			controls.put(driverType + CommonIndexUtils.DOT
					+ propertyConfiguration.getPropertyName(), button);

			button.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					SimpleProperty property = (SimpleProperty) button.getData();
					String value = Boolean.toString(button.getSelection());
					if (property.getValue() == null) {
						Command command = new SetCommand(editor
								.getEditingDomain(), property,
								ModelPackage.eINSTANCE
										.getSimpleProperty_Value(), value);
						EditorUtils.executeCommand(editor, command);
						return;
					}
					EditorUtils.editorModifiedOnReset(property, editor);
					if (!property.getValue().equalsIgnoreCase(value)) {
						Command command = new SetCommand(editor
								.getEditingDomain(), property,
								ModelPackage.eINSTANCE
										.getSimpleProperty_Value(), value);
						EditorUtils.executeCommand(editor, command);
					}
				}
			});
*/
			return gvField;

		} else {
			
			String value=null;
			for(SimpleProperty prop:propList){
				if(prop.getName().equalsIgnoreCase(propertyConfiguration.getPropertyName())){
					value=prop.getValue();
					break;
				}
			}
			if(value==null){
				value="";
			}
			
			final GvField gvField = createGvTextField(parent,"",value,editor,propertyConfiguration);
			if(propertyConfiguration.getPropertyName().equalsIgnoreCase("be.http.debugFolder") || propertyConfiguration.getPropertyName().equalsIgnoreCase("be.http.debugLogPattern")){
				
				if(((GvField)controls.get("HTTP.be.http.debug")).getFieldValue().equalsIgnoreCase("false")){
					gvField.getField().setEnabled(false);
					gvField.getGvToggleButton().setEnabled(false);
					gvField.getGvText().setEnabled(false);
				}
				else if(((GvField)controls.get("HTTP.be.http.debug")).getFieldValue().equalsIgnoreCase("true")){
					gvField.getField().setEnabled(true);
					gvField.getGvToggleButton().setEnabled(true);
					gvField.getGvText().setEnabled(true);
				}
				
			}

			controls.put(driverType + CommonIndexUtils.DOT + propertyConfiguration.getPropertyName(), gvField);

			
			
			
/*			final Text textField = toolkit.createText(parent, "",
					propertyConfiguration.isMask() ? SWT.BORDER | SWT.PASSWORD
							: SWT.BORDER);
			textField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			textField.setText(propertyConfiguration.getDefaultValue());
			controls.put(driverType + CommonIndexUtils.DOT
					+ propertyConfiguration.getPropertyName(), textField);

			// The following default settings for Channel Configuration
			textField.setText(propertyConfiguration.getDefaultValue());
			textField.setData("PropertyConfigType",
					propertyConfiguration.getPropertyConfigType());
			textField.setData("DefaultValue",
					propertyConfiguration.getDefaultValue());
			textField.setData("DisplayName",
					propertyConfiguration.getDisplayName());
			textField.setToolTipText(textField.getText());
			textField.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent e) {
					textField.setToolTipText(textField.getText());
					textField.setForeground(Display.getDefault()
							.getSystemColor(SWT.COLOR_BLACK));
					String type = textField.getData("PropertyConfigType")
							.toString();
					String deafultValue = textField.getData("DefaultValue")
							.toString();
					String displayName = textField.getData("DisplayName")
							.toString();
					Object fieldData = textField.getData();
					if (fieldData instanceof SimpleProperty) {
						SimpleProperty property = (SimpleProperty) fieldData;
						boolean validateField = validateField(textField, type,
								deafultValue, displayName);
						if (!validateField) {
							return;
						}
						if (property.getValue() == null) {
							Command command = new SetCommand(editor
									.getEditingDomain(), property,
									ModelPackage.eINSTANCE
											.getSimpleProperty_Value(),
									textField.getText());
							EditorUtils.executeCommand(editor, command);
							return;
						}
						EditorUtils.editorModifiedOnReset(property, editor);
						if (!property.getValue().equalsIgnoreCase(
								textField.getText())) {
							Command command = new SetCommand(editor
									.getEditingDomain(), property,
									ModelPackage.eINSTANCE
											.getSimpleProperty_Value(),
									textField.getText());
							EditorUtils.executeCommand(editor, command);
						}
					}
				}
			});
*/
			return gvField;

		}
	}

	/**
	 * @param textField
	 * @param type
	 * @param deafultValue
	 * @param displayName
	 * @return
	 */
	boolean validateField(Text textField, String type, String deafultValue,
			String displayName) {
		final String problemMessage = validatePropertyValue(type, textField,
				deafultValue, displayName);
		if (problemMessage != null) {
			textField.setForeground(Display.getDefault().getSystemColor(
					SWT.COLOR_RED));
			textField.setToolTipText(problemMessage);
			return false;
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
	private String validatePropertyValue(String type, Text textField,
			String deafultValue, String propertyName) {
		final String message = com.tibco.cep.studio.ui.util.Messages.getString(
				"invalid.property.entry", textField.getText(), propertyName,
				type);
		String text = textField.getText();
		boolean globalVar = false;
		if (text.length() > 4) {
			globalVar = GvUtil.isGlobalVar(text);
		}
		if (globalVar) {
			// check if global var defined
			Map<String, GlobalVariableDescriptor> glbVars = DefaultResourceValidator
					.getGlobalVariableNameValues(projectName);

			GlobalVariableDescriptor gvd = glbVars.get(stripGvMarkers(text));
			if (gvd == null) {
				return Messages.getString("invalid.global.var.doesnotexist",
						text);
			}
			if (!gvd.getType().equals(type.intern())) {
				if (type.intern().equals("int")
						&& gvd.getType().equals("Integer")) {
					return null;
				}
				return Messages.getString("invalid.global.var.typemismatch",
						text, type.intern());
			}
			return null;
		}

		if (type.intern() == PROPERTY_TYPES.INTEGER.getName().intern()) {
			if (!StudioUIUtils.isNumeric(text)) {
				return message;
			}
		}

		if (type.equalsIgnoreCase("Integer")) {
			if (!StudioUIUtils.isNumeric(text)) {
				return message;
			}
		}
		if (type.intern() == PROPERTY_TYPES.DOUBLE.getName().intern()) {
			if (!isDouble(text)) {
				return message;
			}
		}
		if (type.intern() == PROPERTY_TYPES.LONG.getName().intern()) {
			if (!isLong(text)) {
				return message;
			}
		}
		return null;
	}

	private static String stripGvMarkers(String variable) {
		int firstIndex = variable.indexOf("%%");
		String stripVal = variable.substring(firstIndex + 2);
		stripVal = stripVal.substring(0, stripVal.indexOf("%%"));
		return stripVal;
	}

	/**
	 * @param item
	 */
	private void createWebDescriptorItem(TableItem item) {
		DriverConfig driver = channel.getDriver();
		if (driver instanceof HttpChannelDriverConfig) {
			HttpChannelDriverConfig driverConfig = (HttpChannelDriverConfig) driver;
			WebApplicationDescriptor webApplicationDescriptor = ChannelFactory.eINSTANCE
					.createWebApplicationDescriptor();
			webApplicationDescriptor.setContextURI(item.getText(0));
			webApplicationDescriptor.setWebAppSourcePath(item.getText(1));

			AddCommand addCommand = new AddCommand(
					channelEditor.getEditingDomain(),
					driverConfig.getWebApplicationDescriptors(),
					webApplicationDescriptor);
			EditorUtils.executeCommand(channelEditor, addCommand, true);
		}
	}

	/**
	 * @param item
	 */
	private void deleteWebDescriptorItem(TableItem item) {
		DriverConfig driver = channel.getDriver();
		if (driver instanceof HttpChannelDriverConfig) {
			HttpChannelDriverConfig driverConfig = (HttpChannelDriverConfig) driver;
			WebApplicationDescriptor wAppDesc = getWebAppDescriptor(
					item.getText(0), item.getText(1));
			if (wAppDesc != null) {
				RemoveCommand removeCommand = new RemoveCommand(
						channelEditor.getEditingDomain(),
						driverConfig.getWebApplicationDescriptors(), wAppDesc);
				EditorUtils.executeCommand(channelEditor, removeCommand, true);
				item.dispose();
			}
		}
	}

	/**
	 * @param item
	 * @return
	 */
	public WebApplicationDescriptor getWebAppDescriptor(final String uri,
			final String resourcePath) {
		DriverConfig driver = channel.getDriver();
		if (driver instanceof HttpChannelDriverConfig) {
			HttpChannelDriverConfig driverConfig = (HttpChannelDriverConfig) driver;
			for (WebApplicationDescriptor wAppDesc : driverConfig
					.getWebApplicationDescriptors()) {
				if (wAppDesc.getContextURI().equals(uri)
						&& wAppDesc.getWebAppSourcePath().equals(resourcePath)) {
					return wAppDesc;
				}
			}
		}
		return null;
	}

	protected void populateWebAppsTable() {
		itemButtonEditorMap.clear();
		wappsTable.deselectAll();
		TableItem[] items = wappsTable.getItems();
		for (TableItem tableItem : items) {
			tableItem.dispose();
		}
		DriverConfig driver = channel.getDriver();
		if (driver instanceof HttpChannelDriverConfig) {
			HttpChannelDriverConfig driverConfig = (HttpChannelDriverConfig) driver;
			for (WebApplicationDescriptor webApplicationDescriptor : driverConfig
					.getWebApplicationDescriptors()) {
				final TableItem item = new TableItem(wappsTable, SWT.CENTER);
				item.setText(0, webApplicationDescriptor.getContextURI());
				item.setText(1, webApplicationDescriptor.getWebAppSourcePath());
				item.setText(2, "");
				createBrowseButtonTableEditor(item);
			}
		}
	}

	/**
	 * @param table
	 */
	public void setWebAppsTableEditableSupport(final Table table) {
		tableEditor = new TableEditor(table);
		tableEditor.horizontalAlignment = SWT.LEFT;
		tableEditor.grabHorizontal = true;
		table.addListener(SWT.MouseDown, new Listener() {
			public void handleEvent(org.eclipse.swt.widgets.Event event) {
				Rectangle clientArea = table.getClientArea();
				Point pt = new Point(event.x, event.y);
				int index = table.getTopIndex();
				while (index < table.getItemCount()) {
					boolean visible = false;
					final TableItem item = table.getItem(index);
					for (int i = 0; i < table.getColumnCount(); i++) {
						Rectangle rect = item.getBounds(i);
						final int editColumn = i;
						if (i == 0 || i == 1) {
							if (rect.contains(pt)) {
								final int column = i;
								Control tmp = new Text(table, SWT.NONE);
								final Control text = tmp;
								text.addKeyListener(new KeyAdapter() {
									/*
									 * (non-Javadoc)
									 * 
									 * @see
									 * org.eclipse.swt.events.KeyAdapter#keyReleased
									 * (org.eclipse.swt.events.KeyEvent)
									 */
									@Override
									public void keyReleased(KeyEvent e) {
										// if (e.stateMask == SWT.CTRL &&
										// e.keyCode == 'x') {
										// text.cut();
										// }
										if (e.stateMask == SWT.CTRL
												&& e.keyCode == 'c') {
											if (text instanceof Text) {
												((Text) text).copy();
											}
										}
										if (e.stateMask == SWT.CTRL
												&& e.keyCode == 'v') {
											if (text instanceof Text) {
												((Text) text).paste();
											}
										}
									}
								});
								if (text instanceof Text) {
									((Text) text).setEditable(true);
								}
								Listener textListener = new Listener() {

									public void handleEvent(
											final org.eclipse.swt.widgets.Event e) {

										String oldText = null;
										String newText = null;
										switch (e.type) {
										case SWT.FocusOut:
											if (item.isDisposed()) {
												break;
											}
											oldText = item.getText(editColumn);
											String colText = "";
											if (text instanceof Text) {
												colText = ((Text) text)
														.getText();
											}
											item.setText(column, colText);
											newText = item.getText(editColumn);

											if (!isValidWebInfo(oldText,
													newText,
													editColumn == 0 ? true
															: false)) {
												item.setText(column, oldText);
												text.dispose();
												break;
											}

											setWebInfoCommand(
													editColumn == 0 ? true
															: false,
													editColumn == 0 ? oldText
															: item.getText(0),
													editColumn == 1 ? oldText
															: item.getText(1),
													newText);

											text.dispose();
											break;
										case SWT.Traverse:
											switch (e.detail) {
											case SWT.TRAVERSE_RETURN:
												if (item.isDisposed()) {
													break;
												}
												oldText = item
														.getText(editColumn);
												colText = "";
												if (text instanceof Text) {
													colText = ((Text) text)
															.getText();
												}
												item.setText(column, colText);
												newText = item
														.getText(editColumn);

												if (!isValidWebInfo(oldText,
														newText,
														editColumn == 0 ? true
																: false)) {
													item.setText(column,
															oldText);
													text.dispose();
													break;
												}

												setWebInfoCommand(
														editColumn == 0 ? true
																: false,
														editColumn == 0 ? oldText
																: item.getText(0),
														editColumn == 1 ? oldText
																: item.getText(1),
														newText);

												text.dispose();
												break;
											// FALL THROUGH
											case SWT.TRAVERSE_ESCAPE:
												text.dispose();
												e.doit = false;
											}
											break;
										}
									}
								};

								text.addListener(SWT.FocusOut, textListener);
								text.addListener(SWT.Traverse, textListener);

								tableEditor.setEditor(text, item, i);
								if (text instanceof Text) {
									((Text) text).setText(item.getText(i));
									((Text) text).selectAll();
								}
								text.setFocus();
								return;
							}
							if (!visible && rect.intersects(clientArea)) {
								visible = true;
							}
						}
					}
					if (!visible) {
						return;
					}
					index++;
				}
			}
		});
	}

	/**
	 * @param oldText
	 * @param newText
	 * @param contextURI
	 * @return
	 */
	protected boolean isValidWebInfo(String oldText, String newText,
			boolean contextURI) {
		String special = "!@#$%^&*()_";
		String pattern = ".*[" + Pattern.quote(special) + "].*";
		if (newText.matches(pattern)) {
			return false;
		}
		if (!oldText.trim().equals(newText.trim())) {
			boolean present = false;
			DriverConfig driver = channel.getDriver();
			if (driver instanceof HttpChannelDriverConfig) {
				HttpChannelDriverConfig driverConfig = (HttpChannelDriverConfig) driver;
				for (WebApplicationDescriptor wds : driverConfig
						.getWebApplicationDescriptors()) {
					if (contextURI) {
						if (wds.getContextURI().equals(newText)) {
							present = true;
							break;
						}
					} else {
						if (wds.getWebAppSourcePath().equals(newText)) {
							present = true;
							break;
						}
					}
				}
			}
			if (!present) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param contextURI
	 * @param uri
	 * @param resPath
	 * @param value
	 */
	protected void setWebInfoCommand(boolean contextURI, String uri,
			String resPath, String value) {
		WebApplicationDescriptor wdesc = getWebAppDescriptor(uri, resPath);
		Command command = new SetCommand(
				channelEditor.getEditingDomain(),
				wdesc,
				contextURI ? ChannelPackage.eINSTANCE
						.getWebApplicationDescriptor_ContextURI()
						: ChannelPackage.eINSTANCE
								.getWebApplicationDescriptor_WebAppSourcePath(),
				value);
		EditorUtils.executeCommand(channelEditor, command);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.studio.ui.forms.AbstractMasterDetailsFormViewer#
	 * createToolBarActions()
	 */
	protected void createToolBarActions() {
		/** Hiding Vertical and Horizintal Orientation Tools */
	}

	@Override
	protected void remove() {
		TableItem[] selection = wappsTable.getSelection();
		for (TableItem tableItem : selection) {
			if (tableEditor != null) {
				if (tableEditor.getItem() == tableItem) {
					if (tableEditor.getEditor() != null) {
						tableEditor.getEditor().dispose();
					}
				}
			}
			itemButtonEditorMap.get(tableItem).dispose();
			deleteWebDescriptorItem(tableItem);
		}

		if (wappsTable.getItemCount() == 0) {
			removeRowButton.setEnabled(false);
		}
	}

	@Override
	protected void duplicate() {
		// TODO Auto-generated method stub

	}
	
	
    public GvField createGvCheckboxField(Composite parent, String label,String defaultValue, ChannelFormEditor editor,PropertyConfiguration propertyConfiguration) {
//		Label lField = PanelUiUtil.createLabel(parent, label);
    	propertyConfiguration.getValues();
    	
    	GvField gvField = GvUiUtil.createCheckBoxGv(parent, defaultValue);
		setGvFieldListeners(gvField, SWT.Selection, editor,propertyConfiguration);
		return gvField;
	
    }
    
    public GvField createGvTextField(Composite parent, String label, String defaultValue,ChannelFormEditor editor, PropertyConfiguration propertyConfiguration) {
    	GvField gvField = GvUiUtil.createTextGv(parent, defaultValue);
		setGvFieldListeners(gvField, SWT.Modify, editor,propertyConfiguration);
		return gvField;
    }
    
	public Listener getListener(final Control field, final ChannelFormEditor editor, final PropertyConfiguration propertyConfiguration) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {

				if (field instanceof Text) {
					String val=((Text)field).getText();
					String gvVal=null;
					if(GvUtil.isGlobalVar(val)){
						gvVal=GvUtil.getGvDefinedValue(prj,val);
					}
					if(propertyConfiguration.getPropertyName().equalsIgnoreCase("be.http.debug")){
						if(gvVal!=null){
							if(Boolean.parseBoolean(gvVal)==true){
								((GvField)controls.get("HTTP.be.http.debugFolder")).getField().setEnabled(true);
								((GvField)controls.get("HTTP.be.http.debugFolder")).getGvToggleButton().setEnabled(true);
								((GvField)controls.get("HTTP.be.http.debugFolder")).getGvText().setEnabled(true);
								((GvField)controls.get("HTTP.be.http.debugLogPattern")).getField().setEnabled(true);
								((GvField)controls.get("HTTP.be.http.debugLogPattern")).getGvToggleButton().setEnabled(true);
								((GvField)controls.get("HTTP.be.http.debugLogPattern")).getGvText().setEnabled(true);
							}
							else if(Boolean.parseBoolean(gvVal)==false){
								((GvField)controls.get("HTTP.be.http.debugFolder")).getField().setEnabled(false);
								((GvField)controls.get("HTTP.be.http.debugFolder")).getGvToggleButton().setEnabled(false);
								((GvField)controls.get("HTTP.be.http.debugFolder")).getGvText().setEnabled(false);
								((GvField)controls.get("HTTP.be.http.debugLogPattern")).getField().setEnabled(false);
								((GvField)controls.get("HTTP.be.http.debugLogPattern")).getGvToggleButton().setEnabled(false);
								((GvField)controls.get("HTTP.be.http.debugLogPattern")).getGvText().setEnabled(false);

							}
						}
					}
					field.setToolTipText(((Text)field).getText());
					field.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
					Object fieldData = ((Text)field).getData();
					if (fieldData instanceof SimpleProperty) {
						SimpleProperty property = (SimpleProperty)fieldData;
						if (property.getValue() == null) {
							Command command=new SetCommand(editor.getEditingDomain(),property,ModelPackage.eINSTANCE.getSimpleProperty_Value(),
									((Text)field).getText()) ;	
							EditorUtils.executeCommand(editor, command);
							return;
						}
						EditorUtils.editorModifiedOnReset(property, editor);
						if (!property.getValue().equalsIgnoreCase(((Text)field).getText())) {
							Command command=new SetCommand(editor.getEditingDomain(),property,ModelPackage.eINSTANCE.getSimpleProperty_Value(),
									((Text)field).getText()) ;	
							EditorUtils.executeCommand(editor, command);
						}
					}
					validateField((Text)field, propertyConfiguration.getPropertyConfigType(), propertyConfiguration.getDefaultValue(), propertyConfiguration.getDisplayName());
					
				} else if (field instanceof Button) {
					SimpleProperty property = (SimpleProperty)((Button)field).getData();
					String value = Boolean.toString(((Button)field).getSelection());
					if(property.getName().equalsIgnoreCase("be.http.debug")){
						if(Boolean.parseBoolean(value)==true){
							((GvField)controls.get("HTTP.be.http.debugFolder")).getField().setEnabled(true);
							((GvField)controls.get("HTTP.be.http.debugFolder")).getGvToggleButton().setEnabled(true);
							((GvField)controls.get("HTTP.be.http.debugFolder")).getGvText().setEnabled(true);
							((GvField)controls.get("HTTP.be.http.debugLogPattern")).getField().setEnabled(true);
							((GvField)controls.get("HTTP.be.http.debugLogPattern")).getGvToggleButton().setEnabled(true);
							((GvField)controls.get("HTTP.be.http.debugLogPattern")).getGvText().setEnabled(true);
						}
						else{
							((GvField)controls.get("HTTP.be.http.debugFolder")).getField().setEnabled(false);
							((GvField)controls.get("HTTP.be.http.debugFolder")).getGvToggleButton().setEnabled(false);
							((GvField)controls.get("HTTP.be.http.debugFolder")).getGvText().setEnabled(false);
							((GvField)controls.get("HTTP.be.http.debugLogPattern")).getField().setEnabled(false);
							((GvField)controls.get("HTTP.be.http.debugLogPattern")).getGvToggleButton().setEnabled(false);
							((GvField)controls.get("HTTP.be.http.debugLogPattern")).getGvText().setEnabled(false);

						}
					}
					if (property.getValue() == null) {
						Command command=new SetCommand(editor.getEditingDomain(),property,ModelPackage.eINSTANCE.getSimpleProperty_Value(),
											value) ;	
						EditorUtils.executeCommand(editor, command);
						return;
					}
					EditorUtils.editorModifiedOnReset(property, editor);
					if (!property.getValue().equalsIgnoreCase(value)) {
						Command command=new SetCommand(editor.getEditingDomain(),property,ModelPackage.eINSTANCE.getSimpleProperty_Value(),
								value) ;	
						EditorUtils.executeCommand(editor, command);
					}
				}
				
			}
		};
		return listener;
	}
	
    protected void setGvFieldListeners(GvField gvField, int eventType, ChannelFormEditor editor, PropertyConfiguration propertyConfiguration) {
		gvField.setFieldListener(eventType, getListener(gvField.getField(), editor,propertyConfiguration));
		gvField.setGvListener(getListener(gvField.getGvText(), editor,propertyConfiguration));
    }

}