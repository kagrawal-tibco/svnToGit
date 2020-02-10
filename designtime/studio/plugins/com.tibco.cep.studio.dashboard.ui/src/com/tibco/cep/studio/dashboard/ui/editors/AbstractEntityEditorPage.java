package com.tibco.cep.studio.dashboard.ui.editors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.preference.ColorSelector;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.studio.dashboard.core.enums.InternalStatusEnum;
import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynRequiredProperty;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDAttributeDeclaration;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynBooleanType;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynColorType;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynDescriptionType;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynImageURLType;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynNameType;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynPrimitiveType;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalEntity;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalParticle;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.dialogs.ImageSelectionDialog;
import com.tibco.cep.studio.dashboard.ui.editors.util.Messages;
import com.tibco.cep.studio.dashboard.ui.editors.util.PropertyLinkSelectionListener;
import com.tibco.cep.studio.dashboard.ui.images.KnownImageKeys;
import com.tibco.cep.studio.dashboard.ui.utils.SynColorUtils;

public abstract class AbstractEntityEditorPage extends FormPage {

	protected Composite formBodyClient;

	private LocalElement localElement;

	// key can be synproperty or localparticle name
	// value can be widget or colorselector
	protected Map<String, Object> propertyWidgets = new HashMap<String, Object>();

	protected boolean processSWTEvents = true;

	public AbstractEntityEditorPage(FormEditor editor, LocalElement localElement) {
		super(editor, localElement.getID(), localElement.getName());
		setLocalElement(localElement);
	}

	public AbstractEntityEditorPage(FormEditor editor, LocalElement localElement, String id, String title) {
		super(editor, id, title);
		setLocalElement(localElement);
	}

	public LocalElement getLocalElement() {
		return localElement;
	}

	public void setLocalElement(LocalElement localElement) {
		if (this.localElement != null) {
			throw new IllegalStateException();
		}
		this.localElement = localElement;
	}

	@Override
	protected void createFormContent(IManagedForm mform) {
		try {
			ScrolledForm form = mform.getForm();
			form.setText(getElementTypeName() + ": " + getLocalElement().getName()); //$NON-NLS-1$
			form.setBackgroundImage(DashboardUIPlugin.getInstance().getImageRegistry().get(KnownImageKeys.IMG_KEY_FORM_BANNER));

			Composite scrollableFormBody = form.getBody();
			FillLayout layout = new FillLayout();
			layout.marginWidth = layout.marginHeight = 5;
			scrollableFormBody.setLayout(layout);

			createControl(mform, scrollableFormBody);

			Display.getCurrent().asyncExec(new Runnable() {

				@Override
				public void run() {
					try {
						populateControl(getLocalElement());
					} catch (Exception e) {
						DashboardUIPlugin.getInstance().getLog().log(new Status(IStatus.ERROR, DashboardUIPlugin.PLUGIN_ID, "could not initialize " + getEditorInput().getName(), e));
					}
				}

			});


		} catch (Exception e) {
			DashboardUIPlugin.getInstance().getLog().log(new Status(IStatus.ERROR, DashboardUIPlugin.PLUGIN_ID, "could not initialize " + getEditorInput().getName(), e));
		}

	}

	protected String getElementTypeName() {
		String elementType = getLocalElement().getElementType();
		if (elementType.startsWith("Insight")) {
			elementType = elementType.substring("Insight".length());
		}
		return elementType;
	}

	protected void createControl(IManagedForm mform, Composite parent) throws Exception {
		FormToolkit toolkit = mform.getToolkit();
		formBodyClient = toolkit.createComposite(parent);

		GridLayout layout = new GridLayout();
		layout.marginWidth = layout.marginHeight = 0;
		formBodyClient.setLayout(layout);

		createProperties(mform, formBodyClient);

		toolkit.paintBordersFor(formBodyClient);
	}

	protected void createProperties(IManagedForm mform, Composite client) throws Exception {
		FormToolkit toolkit = mform.getToolkit();

		SynProperty descriptionProperty = null;

		List<SynProperty> boolProps = new ArrayList<SynProperty>();

		Composite allPropertiesPanel = toolkit.createComposite(client);
		allPropertiesPanel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		GridLayout gLayout = new GridLayout(2, false);
		gLayout.marginHeight = gLayout.marginWidth = 0;
		allPropertiesPanel.setLayout(gLayout);

		Composite nonDescPropSection = createPropertiesSection(mform, allPropertiesPanel);
		GridLayout nonDescPropLayout = new GridLayout(3, false);
		nonDescPropLayout.marginHeight = nonDescPropLayout.marginWidth = 5;
		nonDescPropLayout.verticalSpacing = 10;
		nonDescPropSection.setLayout(nonDescPropLayout);

		// first show the display name property
		if (getLocalElement().hasProperty(LocalConfig.PROP_KEY_DISPLAY_NAME)) {
			createTextField(mform, nonDescPropSection, (SynProperty) getLocalElement().getProperty(LocalConfig.PROP_KEY_DISPLAY_NAME));
		}

		/*
		 * Follows by misc properties
		 */
		for (Iterator<ISynXSDAttributeDeclaration> iter = getLocalElement().getProperties().iterator(); iter.hasNext();) {
			SynProperty property = (SynProperty) iter.next();

			if (false == shouldShowProperty(property) || property.getName().equals(LocalConfig.PROP_KEY_DISPLAY_NAME)) {
				// skip display name property
				continue;
			}

			SynPrimitiveType type = (SynPrimitiveType) property.getTypeDefinition();

			if (type instanceof SynDescriptionType) {
				descriptionProperty = property;
				// description goes to it own section
				/*
				 * Skipping description because it goes at the end
				 */
				continue;
			} else if (type instanceof SynBooleanType) {
				// boolean go the end of the section
				boolProps.add(property);
				continue;
			} else if (type instanceof SynColorType) {
				// color based widget
				createColorField(mform, nonDescPropSection, property);
				continue;
			} else if (type instanceof SynImageURLType) {
				// image browsing widget
				createImageBrowsingField(mform, nonDescPropSection, property);
				continue;
			} else if (isComboProperty(property) || false == type.getEnumerations(property.getName()).isEmpty()) {
				// combo is enumerations are present
				createComboField(mform, nonDescPropSection, property);
			} else {
				if (type instanceof SynNameType) {
					createTextField(mform, nonDescPropSection, property);
				} else {
					createTextField(mform, nonDescPropSection, property);
				}

			}
		}

		/*
		 * Boolean after text and numeric
		 */
		for (Iterator<SynProperty> iter = boolProps.iterator(); iter.hasNext();) {
			SynProperty property = iter.next();
			createBooleanField(mform, nonDescPropSection, property);
		}

		/*
		 * Create extended properties. This class just has empty place holder,
		 * sub class may extend it to add more properties.
		 */
		createExtendedProperties(mform, nonDescPropSection);

		/*
		 * Description is last
		 */
		createDescriptionWidget(mform, descriptionProperty, allPropertiesPanel, false);

		createMiddleSection(mform, client);
		createFieldSection(mform, client);
	}

	protected void createDescriptionWidget(IManagedForm mform, SynProperty descriptionProperty, Composite allPropertiesPanel, boolean grabExcessVerticalSpace) throws Exception {
		Composite descPropertySection = createDescriptionSection(mform, allPropertiesPanel, descriptionProperty.getName(), grabExcessVerticalSpace);

		descPropertySection.setLayout(new GridLayout());

		final Text text = createTextArea(mform, descPropertySection, descriptionProperty);

		GridData layoutData = new GridData(GridData.FILL, GridData.FILL, true, true);
		layoutData.heightHint = 50;
		text.setLayoutData(layoutData);

	}

	private Composite createPropertiesSection(IManagedForm mform, Composite client) {
		FormToolkit toolkit = mform.getToolkit();

		Section section = toolkit.createSection(client, ExpandableComposite.TITLE_BAR);
		// section.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		// section.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		// toolkit.createCompositeSeparator(section);
		section.setText(Messages.getString("EventMetadataEditorPage.propertiesSection.title")); //$NON-NLS-1$

		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		section.setLayoutData(gd);

		section.setLayout(new FillLayout());

		Composite subClient = toolkit.createComposite(section, SWT.NULL);
		// subClient.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
		// true));
		toolkit.paintBordersFor(subClient);
		section.setClient(subClient);
		return subClient;

	}

	private Composite createDescriptionSection(final IManagedForm mform, Composite client, String name, boolean grabExcessVerticalSpace) throws Exception {
		FormToolkit toolkit = mform.getToolkit();

		Section section = toolkit.createSection(client, ExpandableComposite.TITLE_BAR);
		// section.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		// section.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		// toolkit.createCompositeSeparator(section);
		section.setText(name);

		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, grabExcessVerticalSpace);
		// gd.widthHint = 1000;
		section.setLayoutData(gd);

		section.setLayout(new GridLayout());

		Composite subClient = toolkit.createComposite(section, SWT.NULL);
		// subClient.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false,
		// true));
		toolkit.paintBordersFor(subClient);

		section.setClient(subClient);
		return subClient;
	}

	/**
	 * Filters the properties that are shown in the properties section.
	 *
	 * @param property
	 *            <code>SynProperty</code> to check
	 * @return <code>true</code> if the property shown be shown in the
	 *         properties section
	 * @throws Exception
	 */
	protected boolean shouldShowProperty(SynProperty property) throws Exception {
		if (true == property.isSystem()) {
			return false;
		}
		String propertyName = property.getName();
		if (true == propertyName.equals(LocalElement.PROP_KEY_NAME)) {
			return false;
		}
		return true;
	}

	/**
	 * Default implementation is to return false. The sub class can force for a
	 * specific property to be rendered as combo.
	 *
	 * @param property
	 * @return
	 * @throws Exception
	 */
	protected boolean isComboProperty(SynProperty property) throws Exception {
		return false;
	}

	/**
	 * This class just has empty place holder, sub class may extend it to add
	 * more properties.
	 *
	 * @param toolkit
	 * @param propertiesPanel
	 */
	protected void createExtendedProperties(IManagedForm mform, Composite propertiesPanel) throws Exception {

	}

	private ColorSelector createColorField(IManagedForm mform, Composite client, final SynProperty property) throws Exception {
		FormToolkit toolkit = mform.getToolkit();

		StringBuilder labelText = new StringBuilder(property.getName());
		if (property instanceof SynRequiredProperty) {
			labelText.append("*"); //$NON-NLS-1$
		}
		labelText.append(":"); //$NON-NLS-1$
		final Label label = toolkit.createLabel(client, labelText.toString());
		PropertyLinkSelectionListener listener = new PropertyLinkSelectionListener(property);
		label.addListener(SWT.MouseDown, listener);

		final ColorSelector cs = new ColorSelector(client);
		final Button button = cs.getButton();
		button.setLocation(0, 0);
		GridData gd = new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false);
		gd.horizontalSpan = 2;
		button.setLayoutData(gd);

		cs.addListener(new IPropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent event) {
				try {
					RGB newRGB = cs.getColorValue();
					String newValue = SynColorUtils.RGBToString(newRGB);
					property.setValue(newValue);
					button.setToolTipText(newValue);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});

		propertyWidgets.put(property.getName(), cs);

		return cs;
	}

	protected void updateColorField(ColorSelector cs, SynProperty property) throws Exception {
		RGB rgb = SynColorUtils.StringToRGB(property.getValue());
		cs.getButton().setToolTipText(property.getValue());
		cs.setColorValue(rgb);

	}

	private Control createImageBrowsingField(IManagedForm mform, Composite client, final SynProperty property) throws Exception {
		FormToolkit toolkit = mform.getToolkit();

		StringBuilder labelText = new StringBuilder(property.getName());
		if (property instanceof SynRequiredProperty) {
			labelText.append("*"); //$NON-NLS-1$
		}
		labelText.append(":"); //$NON-NLS-1$
		final Label label = toolkit.createLabel(client, labelText.toString());
		PropertyLinkSelectionListener listener = new PropertyLinkSelectionListener(property);
		label.addListener(SWT.MouseDown, listener);

		final Text text = toolkit.createText(client, "", SWT.NONE);
		text.setEnabled(false);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, false);
		text.setLayoutData(gd);

		final Button button = toolkit.createButton(client, "Browse", SWT.PUSH);
//		gd = new GridData(SWT.FILL, SWT.FILL, true, false);
//		button.setLayoutData(gd);

		button.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				widgetDefaultSelected(e);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				if (processSWTEvents == false){
					return;
				}
				try {
					String projectName = getLocalElement().getPropertyValue(LocalEntity.PROP_KEY_OWNER_PROJECT);
					String[] allowedImageTypes = ((SynImageURLType)property.getTypeDefinition()).getAllowedImageTypes();
					ImageSelectionDialog dialog = new ImageSelectionDialog(getEditorSite().getShell(), projectName,allowedImageTypes);
					int status = dialog.open();
					if (status == ImageSelectionDialog.OK) {
						IResource resource = (IResource) dialog.getFirstResult();
						property.setValue("/"+resource.getProjectRelativePath().toString()); //$NON-NLS-1$
						text.setText(property.getValue());
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		propertyWidgets.put(property.getName(), text);

		return text;
	}

//	protected void updateImageBrowsingField(Text text, SynProperty property) throws Exception {
//		text.setText(property.getValue());
//	}

	protected void createMiddleSection(IManagedForm mform, Composite client) throws Exception {
		// To be used to create section between properties and field viewer
		// Will be subclassed to add this section
	}

	protected void createFieldSection(final IManagedForm mform, Composite parent) throws Exception {
	}

	protected Section createSection(final IManagedForm mform, Composite parent, String title) throws Exception {
		FormToolkit toolkit = mform.getToolkit();
		Section section = toolkit.createSection(parent, /*
														 * Section.TWISTIE |
														 * Section.EXPANDED
														 */ExpandableComposite.TITLE_BAR);
		section.setText(title);
		// toolkit.createCompositeSeparator(section);

		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.horizontalSpan = 3;
		section.setLayout(new GridLayout());
		section.setLayoutData(gd);

		return section;
	}

	protected Control createBooleanField(IManagedForm mform, Composite client, final SynProperty property) throws Exception {
		FormToolkit toolkit = mform.getToolkit();

		toolkit.createLabel(client, ""); //$NON-NLS-1$

		final Button checkBox = toolkit.createButton(client, property.getName(), SWT.CHECK);
		checkBox.setEnabled(!property.isSystem());
		GridData gd = new GridData();
		gd.horizontalSpan = 2;
		checkBox.setLayoutData(gd);

		checkBox.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				widgetDefaultSelected(event);
			}

			public void widgetDefaultSelected(SelectionEvent event) {
				if (processSWTEvents == false){
					return;
				}
				try {
					property.setValue(checkBox.getSelection() + ""); //$NON-NLS-1$
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		propertyWidgets.put(property.getName(), checkBox);

		return checkBox;
	}

	protected void updateBooleanField(Button checkBox, SynProperty property) throws Exception {
		checkBox.setSelection(property.getValue().equalsIgnoreCase("true")); //$NON-NLS-1$
	}

	protected Control createTextField(IManagedForm mform, Composite client, final SynProperty property) throws Exception {
		FormToolkit toolkit = mform.getToolkit();

		StringBuilder labelText = new StringBuilder(property.getName());
		if (property instanceof SynRequiredProperty) {
			labelText.append("*"); //$NON-NLS-1$
		}
		labelText.append(":"); //$NON-NLS-1$
		final Label label = toolkit.createLabel(client, labelText.toString());
		PropertyLinkSelectionListener listener = new PropertyLinkSelectionListener(property);
		label.addListener(SWT.MouseDown, listener);

		final Text text = toolkit.createText(client, "", SWT.NONE);
		text.setEnabled(!property.isSystem());
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, false);
		gd.horizontalSpan = 2;
		gd.widthHint = 100;
		text.setLayoutData(gd);

		text.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent ev) {
				if (processSWTEvents == false){
					return;
				}
				try {
					property.setValue(text.getText());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		propertyWidgets.put(property.getName(), text);

		return text;
	}

	protected Text createTextArea(IManagedForm mform, Composite client, final SynProperty property) throws Exception {
		FormToolkit toolkit = mform.getToolkit();

		final Text text = toolkit.createText(client, "", SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL); //$NON-NLS-1$

		text.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent ev) {
				if (processSWTEvents == false){
					return;
				}
				try {
					property.setValue(text.getText());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		propertyWidgets.put(property.getName(), text);

		return text;
	}

	protected void updateText(Text textField, SynProperty property) throws Exception {
		textField.setText(property.getValue());
	}

	protected CCombo createComboField(IManagedForm mform, Composite client, final SynProperty property) throws Exception {
		FormToolkit toolkit = mform.getToolkit();

		StringBuilder labelText = new StringBuilder(property.getName());
		if (property instanceof SynRequiredProperty) {
			labelText.append("*"); //$NON-NLS-1$
		}
		labelText.append(":"); //$NON-NLS-1$
		/*final Label label = */toolkit.createLabel(client, labelText.toString());

		final CCombo myCombo = new CCombo(client, SWT.FLAT | SWT.READ_ONLY);
		myCombo.setEnabled(!property.isSystem());
		SynPrimitiveType type = (SynPrimitiveType) property.getTypeDefinition();
		for (Object enumeration : type.getEnumerations(property.getName())) {
			myCombo.add((enumeration instanceof LocalElement) ? (((LocalElement) enumeration).getName()) : enumeration.toString());
		}
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, false);
		gd.horizontalSpan = 2;
		gd.widthHint = 200;
		myCombo.setLayoutData(gd);
		myCombo.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);

		myCombo.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				widgetDefaultSelected(e);
			}

			public void widgetDefaultSelected(SelectionEvent ev) {
				if (processSWTEvents == false){
					return;
				}
				processSelection(myCombo, property);
			}
		});

		toolkit.adapt(myCombo);
		toolkit.paintBordersFor(client);

		propertyWidgets.put(property.getName(), myCombo);

		return myCombo;
	}

	protected void updateComboField(CCombo combo, SynProperty property) throws Exception {
		combo.setText(property.getValue());
	}

	protected CCombo createComboField(IManagedForm mform, Composite client, final LocalParticle particle) throws Exception {
		FormToolkit toolkit = mform.getToolkit();

		StringBuilder labelText = new StringBuilder(particle.getName());
		if (particle.getMinOccurs() > 0) {
			labelText.append("*"); //$NON-NLS-1$
		}
		labelText.append(":"); //$NON-NLS-1$

		/* final Label label = */toolkit.createLabel(client, labelText.toString());

		final CCombo myCombo = new CCombo(client, SWT.FLAT | SWT.READ_ONLY);

		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, false);
		gd.horizontalSpan = 2;
		gd.widthHint = 200;
		myCombo.setLayoutData(gd);
		myCombo.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);

		myCombo.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				widgetDefaultSelected(e);
			}

			public void widgetDefaultSelected(SelectionEvent ev) {
				if (processSWTEvents == false){
					return;
				}
				processSelection(myCombo, particle);
			}
		});

		toolkit.adapt(myCombo);
		toolkit.paintBordersFor(client);

		propertyWidgets.put(particle.getName(), myCombo);

		return myCombo;
	}

	protected void updateComboField(CCombo combo, LocalParticle particle) throws Exception {
		combo.removeAll();
		LocalElement particleElement = getLocalElement().getElement(particle.getName());
		for (Object enumeration : particle.getParent().getEnumerations(particle.getName())) {
			combo.add(((LocalElement) enumeration).getScopeName());
		}
		String selectedElementName = (particleElement != null ? particleElement.getScopeName() : "");
		combo.setText(selectedElementName);
	}

	protected final void populateControl(LocalElement localElement) throws Exception {
		InternalStatusEnum existingStatus = getLocalElement().getInternalStatus();
		try {
			processSWTEvents = false;
			getLocalElement().setBulkOperation(true);
			doPopulateControl(localElement);
			getLocalElement().setBulkOperation(false);
		} finally {
			getLocalElement().setBulkOperation(true);
			getLocalElement().setInternalStatus(existingStatus);
			getLocalElement().setBulkOperation(false);
			processSWTEvents = true;
		}
	}

	protected void doPopulateControl(LocalElement localElement) throws Exception {
		for (String key : propertyWidgets.keySet()) {
			Object value = propertyWidgets.get(key);
			if (localElement.getPropertyNames().contains(key) == true) {
				SynProperty property = (SynProperty) localElement.getProperty(key);
				if (value instanceof Button) {
					updateBooleanField((Button) value, property);
				} else if (value instanceof Text) {
					updateText((Text) value, property);
				} else if (value instanceof CCombo) {
					updateComboField((CCombo) value, property);
				} else if (value instanceof ColorSelector) {
					updateColorField((ColorSelector) value, property);
				} else {
					update(value, property);
				}
			} else if (localElement.getParticleNames(true).contains(key) == true) {
				LocalParticle particle = localElement.getParticle(key);
				if (value instanceof CCombo) {
					updateComboField((CCombo) value, particle);
				} else {
					update(value, particle);
				}
			}
		}
	}

	protected void update(Object widget, SynProperty property) {
		// do nothing
	}

	protected void update(Object widget, LocalParticle particle) {
		// do nothing
	}

	protected void handleOutsideElementChange(int change, LocalElement element) {
		// do nothing
	}

	protected void processSelection(final CCombo combo, final LocalParticle particle) {
		try {
			for (Object enumeration : particle.getParent().getEnumerations(particle.getName())) {
				LocalElement elementOption = (LocalElement) enumeration;
				if (elementOption.getScopeName().equals(combo.getText()) == true) {
					getLocalElement().setElement(particle.getName(), elementOption);
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		throw new IllegalStateException(combo.getText() + " was not found in " + particle.getName() + " under " + getLocalElement().getName());
	}

	protected void processSelection(final CCombo combo, final SynProperty property) {
		try {
			property.setValue(combo.getText());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
