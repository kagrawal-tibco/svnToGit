package com.tibco.cep.studio.ui.editors.utils;

import java.util.HashMap;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.ModelFactory;
import com.tibco.cep.designtime.core.model.ModelPackage;
import com.tibco.cep.designtime.core.model.PropertyMap;
import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.designtime.core.model.service.channel.util.DriverManagerConstants;
//import com.tibco.cep.driver.kafka.KafkaProperties;
import com.tibco.cep.sharedresource.ssl.SslConfigModel;
import com.tibco.cep.sharedresource.ssl.SslConfigurationKafkaDialog;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.util.GvUtil;
import com.tibco.cep.studio.ui.editors.channels.ChannelFormEditor;
import com.tibco.cep.studio.ui.editors.channels.contoller.PropertyConfiguration;
import com.tibco.cep.studio.ui.util.GvField;
import com.tibco.cep.studio.ui.util.GvUiUtil;
import com.tibco.cep.studio.ui.util.PanelUiUtil;
import com.tibco.cep.studio.ui.wizards.RuleFunctionSelector;

/**
 *
 * @author moshaikh
 */
public class KafkaEditorUtils extends EditorUtils {

	@Override
	public Object renderComponents(FormToolkit toolkit, Composite parent, final String driverType,
			final PropertyConfiguration propertyConfiguration, final HashMap<Object, Object> controls,
			final ChannelFormEditor editor) {
		if (EditorUtils.isHiddenProperty(driverType, propertyConfiguration)) {
			final Text textField = toolkit.createText(parent, propertyConfiguration.getDefaultValue());
			textField.setVisible(false);
			textField.addListener(SWT.Modify, EditorUtils.getListener(textField, editor, propertyConfiguration));
			controls.put(driverType + CommonIndexUtils.DOT + propertyConfiguration.getPropertyName(), textField);
			return null;//return null so that this component is not further referenced
		}

		addMissingProperty(propertyConfiguration, editor);

		Object component = null;
		if (propertyConfiguration.getPropertyConfigType().equalsIgnoreCase("Boolean")) {
			component = createBottonField(toolkit, parent, driverType, propertyConfiguration, controls, editor);
		} else if (propertyConfiguration.getDisplayedValues() != null
				&& propertyConfiguration.getDisplayedValues().size() > 0) {
			component = createComboboxField(toolkit, parent, driverType, propertyConfiguration, controls, editor);
		} else if ("message.key.rf".equals(propertyConfiguration.getPropertyName())) {
			component = createRuleFunctionField(toolkit, parent, driverType, propertyConfiguration, controls, editor);
		} else {
			component = createTextField(toolkit, parent, driverType, propertyConfiguration, controls, editor);
		}

		//Create "ConfigureSSL" button after kafka.sasl.mechanism
		if ("kafka.sasl.mechanism".equals(propertyConfiguration.getPropertyName())) {
			toolkit.createLabel(parent, " ");
			Button bSslConfig = PanelUiUtil.createPushButton(parent, "Configure SSL...");
			bSslConfig.addListener(SWT.Selection, getSslConfigListener(parent, driverType, controls, editor));
			controls.put(driverType + CommonIndexUtils.DOT + "configure_ssl_button", bSslConfig);
			return bSslConfig;
		}

		return component;
	}

	/**
	 * Creates Text fields.
	 * @param toolkit
	 * @param parent
	 * @param driverType
	 * @param propertyConfiguration
	 * @param controls
	 * @param editor
	 * @return
	 */
	protected Object createTextField(FormToolkit toolkit, Composite parent, final String driverType,
			final PropertyConfiguration propertyConfiguration, final HashMap<Object, Object> controls,
			final ChannelFormEditor editor) {
		if (propertyConfiguration.isGvToggle()) {
			String displayName = propertyConfiguration.getDisplayName();
			final GvField gvField = createGvTextField(parent, displayName, propertyConfiguration.getDefaultValue(), editor, propertyConfiguration);
			controls.put(driverType + CommonIndexUtils.DOT + propertyConfiguration.getPropertyName(), gvField);
			return gvField;
		} else {
			final Text textField = toolkit.createText(null, "", propertyConfiguration.getPropertyName().contains("password") ? SWT.BORDER| SWT.PASSWORD : SWT.BORDER);
			textField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			textField.setText(propertyConfiguration.getDefaultValue());
			textField.addListener(SWT.Modify, EditorUtils.getListener(textField, editor, propertyConfiguration));
			controls.put(driverType + CommonIndexUtils.DOT + propertyConfiguration.getPropertyName(), textField);
			return textField;
		}
	}

	/**
	 * Create listener for ConfigureSSL button.
	 * @param parent
	 * @param controls
	 * @param editor
	 * @return
	 */
	protected Listener getSslConfigListener(final Composite parent, String driverType, final HashMap<Object, Object> controls, final ChannelFormEditor editor) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				SslConfigModel model = new SslConfigModel();
				Object sslTruststoreField = controls.get(driverType + CommonIndexUtils.DOT + "kafka.trusted.certs.folder");
				if (sslTruststoreField instanceof Text) {
					model.setCert(((Text)sslTruststoreField).getText());
				}
				Object sslKeystoreField = controls.get(driverType + CommonIndexUtils.DOT + "kafka.keystore.identity");
				if (sslKeystoreField instanceof Text) {
					model.setIdentity(((Text)sslKeystoreField).getText());
				}
				Object sslTruststorePassword = controls.get(driverType + CommonIndexUtils.DOT + "kafka.truststore.password");
				if (sslTruststorePassword instanceof Text) {
					model.setTrustStorePasswd(((Text) sslTruststorePassword).getText(),
							GvUtil.isGlobalVar(((Text) sslTruststorePassword).getText()));
				}
				SslConfigurationKafkaDialog dialog = new SslConfigurationKafkaDialog(parent.getShell(), model, editor.getProject(), controls, driverType);
				dialog.initDialog("for Kafka Connection");
				dialog.openDialog();
				if (dialog.isDirty()) {
					editor.modified();
				}
			}
		};
		return listener;
	}

	/**
	 * Creates RuleFunction fields.
	 * @param toolkit
	 * @param parent
	 * @param driverType
	 * @param propertyConfiguration
	 * @param controls
	 * @param editor
	 * @return
	 */
	private GvField createRuleFunctionField(final FormToolkit toolkit, final Composite parent, final String driverType,
			final PropertyConfiguration propertyConfiguration, final HashMap<Object, Object> controls,
			final ChannelFormEditor editor) {
		String displayName = propertyConfiguration.getDisplayName();
//		Hyperlink link = StudioUIUtils.createLinkField(toolkit, parent, displayName);
//		controls.put(driverType + CommonIndexUtils.DOT +".link." + "Action RuleFunction", link);

		final GvField gvField = createGvTextField(parent, displayName, propertyConfiguration.getDefaultValue(), editor, propertyConfiguration);
		controls.put(driverType + CommonIndexUtils.DOT + propertyConfiguration.getPropertyName(), gvField);
		Button browseButton = toolkit.createButton(parent, Messages.getString("Browse"), SWT.PUSH);
		browseButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				RuleFunctionSelector picker = new RuleFunctionSelector(Display.getDefault().getActiveShell(),
						editor.getProject().getName(), gvField.getValue().trim(), false);
				if (picker.open() == Dialog.OK) {
					gvField.setValue(picker.getFirstResult().toString());
				}
			}
		});
		controls.put(driverType + CommonIndexUtils.DOT + Messages.getString("Browse")+ Messages.getString("Property"), browseButton);
		return gvField;
	}

	/**
	 * Add missing (newly added in this release) properties.
	 * @param propertyConfiguration
	 * @param editor
	 */
	protected void addMissingProperty(final PropertyConfiguration propertyConfiguration, final ChannelFormEditor editor) {
		final String includeEventTypeDefaultVal = "ALWAYS";
//		final String securityProtocolDefaultVal = "PLAINTEXT";

		//Handle missing properties in imported projects, initialize them with defaults.
		Channel channel = (Channel) editor.getEntity();
        if ("IncludeEventType".equals(propertyConfiguration.getPropertyName())) {//TODO: security.protocol and other such new properties.
        	EList<Destination> dests = channel.getDriver().getDestinations();
    		for (Destination dest : dests) {
			   PropertyMap pmap = dest.getProperties();
			   if (pmap == null) continue;
			   EList<Entity> props = pmap.getProperties();
			   boolean hasIncludeEventType = false;
			   for (Entity entity : props) {
				   if("IncludeEventType".equals(entity.getName()) || "be.http.IncludeEventType".equals(entity.getName())) {
					   hasIncludeEventType = true;
					   break;
				   }
			   }
			   if(!hasIncludeEventType){
				   SimpleProperty sp = ModelFactory.eINSTANCE.createSimpleProperty();
			       sp.setName("IncludeEventType");
			       sp.setValue(includeEventTypeDefaultVal);
			       sp.setMandatory(true);
			       props.add(0, sp);
			   }
    		}
        }
	}

	/**
	 * Creates ComboBox fields.
	 * @param toolkit
	 * @param parent
	 * @param driverType
	 * @param propertyConfiguration
	 * @param controls
	 * @param editor
	 * @return
	 */
	protected Composite createComboboxField(final FormToolkit toolkit, final Composite parent, final String driverType,
			final PropertyConfiguration propertyConfiguration, final HashMap<Object, Object> controls,
			final ChannelFormEditor editor) {

		if (propertyConfiguration.isGvToggle()) {
			final List<String> displyedValueList = propertyConfiguration.getDisplayedValues();
			final List<String> values = propertyConfiguration.getValues();

			final GvField gvField = GvUiUtil.createComboGv(parent, propertyConfiguration.getDefaultValue());
			gvField.setBackgroundColor(new Color(gvField.getMasterComposite().getDisplay(), 255, 255, 255));

			final Combo combo = (Combo) gvField.getField();
			combo.setItems(displyedValueList.toArray(new String[displyedValueList.size()]));
			combo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

			if (propertyConfiguration.getDefaultValue() != null) {
				combo.setText(propertyConfiguration.getDefaultValue());
			}
			controls.put(driverType + CommonIndexUtils.DOT + propertyConfiguration.getPropertyName(), gvField);

			gvField.setFieldListener(SWT.Selection, new Listener() {
				@Override
				public void handleEvent(org.eclipse.swt.widgets.Event event) {
					SimpleProperty property = (SimpleProperty) combo.getData();
					if (property == null) {
						return;
					}

					String val = getRenderedValue(displyedValueList, values, combo.getText(), false);
					if (property.getValue() == null) {
						Command command = new SetCommand(editor.getEditingDomain(), property, ModelPackage.eINSTANCE
								.getSimpleProperty_Value(), val);
						EditorUtils.executeCommand(editor, command);
						return;
					}

					editorModifiedOnReset(property, editor);
					if (!property.getValue().equalsIgnoreCase(val)) {
						Command command = new SetCommand(editor.getEditingDomain(), property, ModelPackage.eINSTANCE
								.getSimpleProperty_Value(), val);
						EditorUtils.executeCommand(editor, command);
					}

					if ("kafka.security.protocol".equals(property.getName())) {
						handleSecurityProtocolChanged(property.getValue(), driverType, controls);
					}
				}
			});
			gvField.setGvListener(new Listener() {
				@Override
				public void handleEvent(org.eclipse.swt.widgets.Event event) {
					Object fieldData = gvField.getGvText().getData();
					if (fieldData instanceof SimpleProperty) {
						SimpleProperty property = (SimpleProperty) fieldData;
						if (property.getValue() == null) {
							Command command = new SetCommand(editor.getEditingDomain(), property,
									ModelPackage.eINSTANCE.getSimpleProperty_Value(), gvField.getGvText());
							EditorUtils.executeCommand(editor, command);
							return;
						}
						editorModifiedOnReset(property, editor);
						if (!property.getValue().equalsIgnoreCase(gvField.getGvText().getText())) {
							Command command = new SetCommand(editor.getEditingDomain(), property,
									ModelPackage.eINSTANCE.getSimpleProperty_Value(), gvField.getGvText().getText());
							EditorUtils.executeCommand(editor, command);
						}

						if ("kafka.security.protocol".equals(property.getName())) {
							handleSecurityProtocolChanged(gvField.getGvDefinedValue(editor.getProject()), driverType, controls);
						}
					}

					validateField((Text)gvField.getGvText(), propertyConfiguration.getPropertyConfigType(), propertyConfiguration.getDefaultValue(), propertyConfiguration.getDisplayName(),editor.getProject().getName());
				}
			});
			return gvField.getMasterComposite();

		} else {
			final Combo comboField = new Combo(parent, SWT.READ_ONLY);
			final List<String> displyedValueList = propertyConfiguration.getDisplayedValues();
			final List<String> values = propertyConfiguration.getValues();
			comboField.setItems(displyedValueList.toArray(new String[displyedValueList.size()]));
			comboField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			if (propertyConfiguration.getDefaultValue() != null) {
				comboField.setText(propertyConfiguration.getDefaultValue());
			}
			controls.put(driverType + CommonIndexUtils.DOT + propertyConfiguration.getPropertyName(), comboField);
			comboField.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent e) {
					SimpleProperty property = (SimpleProperty) comboField.getData();
					if (property == null) {
						return;
					}

					String val = getRenderedValue(displyedValueList, values, comboField.getText(), false);
					if (property.getValue() == null) {
						Command command = new SetCommand(editor.getEditingDomain(), property, ModelPackage.eINSTANCE
								.getSimpleProperty_Value(), val);
						EditorUtils.executeCommand(editor, command);
						return;
					}

					editorModifiedOnReset(property, editor);
					if (!property.getValue().equalsIgnoreCase(val)) {
						Command command = new SetCommand(editor.getEditingDomain(), property, ModelPackage.eINSTANCE
								.getSimpleProperty_Value(), val);
						EditorUtils.executeCommand(editor, command);
					}
				}
			});

			return comboField;
		}
	}

	/**
	 * Enable/Disable various fiels with respect to the selected security protocol.
	 * @param value
	 * @param driverType
	 * @param controls
	 */
	private void handleSecurityProtocolChanged(String value, String driverType, HashMap<Object, Object> controls) {
		if (value == null) {
			return;
		}
		GvField saslMechanismGvField = (GvField) controls.get(driverType + CommonIndexUtils.DOT + "kafka.sasl.mechanism");
		Button configureSSL = (Button) controls.get(driverType + CommonIndexUtils.DOT + "configure_ssl_button");
		switch(value) {
		case "SASL_PLAINTEXT":
			saslMechanismGvField.setEnabled(true);
			configureSSL.setEnabled(false);
			break;
		case "SASL_SSL":
			saslMechanismGvField.setEnabled(true);
			configureSSL.setEnabled(true);
			break;
		case "SSL":
			saslMechanismGvField.setEnabled(false);
			configureSSL.setEnabled(true);
			break;
		case "PLAINTEXT":
			saslMechanismGvField.setEnabled(false);
			configureSSL.setEnabled(false);
			break;
		}
	}

	/**
	 * Creates CheckBox fields.
	 * @param toolkit
	 * @param parent
	 * @param driverType
	 * @param propertyConfiguration
	 * @param controls
	 * @param editor
	 * @return
	 */
	protected Button createBottonField(final FormToolkit toolkit, final Composite parent, final String driverType,
			final PropertyConfiguration propertyConfiguration, final HashMap<Object, Object> controls,
			final ChannelFormEditor editor) {
		if (propertyConfiguration.isGvToggle()) {
			final GvField gvField = createGvCheckboxField(parent, "", propertyConfiguration.getDefaultValue(), editor, propertyConfiguration, driverType, controls);
			controls.put(driverType + CommonIndexUtils.DOT + propertyConfiguration.getPropertyName(), gvField);
			gvField.setGvListener(new Listener() {
				@Override
				public void handleEvent(org.eclipse.swt.widgets.Event event) {
					validateField((Text)gvField.getGvText(), propertyConfiguration.getPropertyConfigType(), propertyConfiguration.getDefaultValue(), propertyConfiguration.getPropertyName(), editor.getProject().getName());
				}
			});
			final Button buttonGv=(Button)gvField.getField();
			buttonGv.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					SimpleProperty property = (SimpleProperty) buttonGv.getData();
					if (property == null) return;
					if (property.getName().equals("sync.sender")) {
						GvField syncSenderMaxWaitGvField = (GvField) controls.get(driverType + CommonIndexUtils.DOT + "sync.sender.max.wait");
						if (buttonGv.getSelection()) {
							syncSenderMaxWaitGvField.getField().setEnabled(true);
						} else {
							syncSenderMaxWaitGvField.getField().setEnabled(false);
						}
					} else if (property.getName().equals("enable.autocommit")) {
						GvField syncSenderMaxWaitGvField = (GvField) controls.get(driverType + CommonIndexUtils.DOT + "autocommit.interval");
						if (buttonGv.getSelection()) {
							syncSenderMaxWaitGvField.getField().setEnabled(true);
						} else {
							syncSenderMaxWaitGvField.getField().setEnabled(false);
						}
					}
				}
			});
			return buttonGv;
		} else {
			final Button button = toolkit.createButton(parent, "", SWT.CHECK);
			Boolean value = new Boolean(propertyConfiguration.getDefaultValue()).booleanValue();
			button.setSelection(value);
			controls.put(driverType + CommonIndexUtils.DOT + propertyConfiguration.getPropertyName(), button);
			button.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					SimpleProperty property = (SimpleProperty) button.getData();
					if (property == null) return;

					String value = Boolean.toString(button.getSelection());
					if (property.getValue() == null) {
						Command command = new SetCommand(editor.getEditingDomain(), property, ModelPackage.eINSTANCE
								.getSimpleProperty_Value(), value);
						EditorUtils.executeCommand(editor, command);
						return;
					}
					editorModifiedOnReset(property, editor);
					if (!property.getValue().equalsIgnoreCase(value)) {
						Command command = new SetCommand(editor.getEditingDomain(), property, ModelPackage.eINSTANCE
								.getSimpleProperty_Value(), value);
						EditorUtils.executeCommand(editor, command);
					}
				}
			});
			return button;
		}
	}
}
