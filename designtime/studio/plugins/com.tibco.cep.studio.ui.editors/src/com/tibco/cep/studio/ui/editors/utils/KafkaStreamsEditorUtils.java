package com.tibco.cep.studio.ui.editors.utils;

import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.ui.editors.channels.ChannelFormEditor;
import com.tibco.cep.studio.ui.editors.channels.contoller.PropertyConfiguration;
import com.tibco.cep.studio.ui.editors.utils.kstreams.TopologyEditor;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/**
 *
 * @author schelwa
 */
public class KafkaStreamsEditorUtils extends KafkaEditorUtils {

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

		Object component = null;
		if (propertyConfiguration.getPropertyConfigType().equalsIgnoreCase("Boolean")) {
			component = createBottonField(toolkit, parent, driverType, propertyConfiguration, controls, editor);
		} else if (propertyConfiguration.getDisplayedValues() != null
				&& propertyConfiguration.getDisplayedValues().size() > 0) {
			component = createComboboxField(toolkit, parent, driverType, propertyConfiguration, controls, editor);
		} else if ("processor.topology".equals(propertyConfiguration.getPropertyName())) {
			TopologyEditor topologyEditor = TopologyEditor.newInstance(editor);
			topologyEditor.createTopologyPart(toolkit, parent, controls);
			controls.put(driverType + CommonIndexUtils.DOT + propertyConfiguration.getPropertyName(), topologyEditor.getTableViewer());
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
}
