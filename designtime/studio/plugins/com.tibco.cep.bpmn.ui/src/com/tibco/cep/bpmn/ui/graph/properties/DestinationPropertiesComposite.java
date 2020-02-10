package com.tibco.cep.bpmn.ui.graph.properties;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.cep.designtime.core.model.service.channel.DRIVER_TYPE;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.designtime.core.model.service.channel.util.DriverManagerConstants;

public class DestinationPropertiesComposite {
	// jms destination rpops
//	private static final String JMS_DESTINATION_NAME = "destinationName";
//	private static final String JMS_QUEUE = "queue";
//	private static final String JMS_PRIORITY = "jmspriority";
//	private static final String JMS_DELIVERY_MODE = "jmsdeliverymode";
//	private static final String JMS_EXPIRATION = "jmsexpiration";
//	private static final String JMS_CORRELATION_ID = "jmscorrelationid";
//
//	// rv props
//	private static final String RV_SUBJECT = "subject";

	private boolean refresh;
	private Composite parentComposite;
	private Composite httpComposite;
	private Composite jmsComposite;
	private Composite defaultComposite;
	private Composite rvComposite;
	private StackLayout stackLayout;
	private TabbedPropertySheetWidgetFactory widgetFactory;
	private Text jmsNameText;
	private CCombo jmsDelModeCombo;
	private Button queueCheckBox;
	private CCombo jmsPriorityCombo;
	private Text jmsTTLText;
	private Text correlationIdText;
	private Text rvSubjectText;
	private String jmsDestName;
	private Boolean queue;
	private Integer priority;
	private Integer deliveryMode;
	private Long expiration;
	private String corrId;
	private String rvSubject;
	private Map<Object, Object> propertiesMap;
	private WidgetListener listener;
	private GeneralNodePropertySection parentPropSection;
	private EObjectWrapper<EClass, EObject> destConfigWrapper;

	public DestinationPropertiesComposite(Composite parent,
			TabbedPropertySheetWidgetFactory factory,
			GeneralNodePropertySection propSec) {
		this.parentComposite = parent;
		this.widgetFactory = factory;
		this.parentPropSection = propSec;
		createControl();
		this.listener = new WidgetListener();
	}

	private void createControl() {
		this.parentComposite = widgetFactory.createComposite(parentComposite);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
//		gridData.widthHint = 750;
		parentComposite.setLayoutData(gridData);
		stackLayout = new StackLayout();
		parentComposite.setLayout(stackLayout);

		httpComposite = widgetFactory
				.createComposite(parentComposite, SWT.NONE);
		httpComposite.setLayout(new GridLayout(2, false));
		jmsComposite = widgetFactory.createGroup(parentComposite,
				BpmnMessages.getString("destinationProp_jmsComposite_label"));
		jmsComposite.setLayout(new GridLayout(2, false));
		rvComposite = widgetFactory.createGroup(parentComposite,
				BpmnMessages.getString("destinationProp_jmsComposite_label"));
		rvComposite.setLayout(new GridLayout(2, false));
		defaultComposite = widgetFactory.createComposite(parentComposite,
				SWT.NONE);
		defaultComposite.setLayout(new GridLayout(2, false));

		createRvSection();
		createJmsSection();
		createHttpSection();

		stackLayout.topControl = defaultComposite;
		parentComposite.layout(true);
	}

	private void createHttpSection() {

	}

	private void createJmsSection() {
		getWidgetFactory().createLabel(jmsComposite, BpmnMessages.getString("destinationProp_jmsNameText_label"));
		jmsNameText = getWidgetFactory().createText(jmsComposite, "");
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
//		data.widthHint = 550;
		jmsNameText.setLayoutData(data);

		getWidgetFactory().createLabel(jmsComposite, "Queue");
		queueCheckBox = getWidgetFactory().createButton(jmsComposite, "",
				SWT.CHECK);
		data = new GridData();
		queueCheckBox.setLayoutData(data);

		getWidgetFactory().createLabel(jmsComposite,BpmnMessages.getString("jmsSection_deliveryMode_label"));
		jmsDelModeCombo = getWidgetFactory().createCCombo(jmsComposite,
				SWT.BORDER);
		jmsDelModeCombo.setEditable(false);
		jmsDelModeCombo.setItems(new String[] { "", "PERSISTENT",
				"NON-PERSISTENT", "RELIABLE(TIBCO Proprietary)" });
		data = new GridData(GridData.FILL_HORIZONTAL);
//		data.widthHint = 550;
		jmsDelModeCombo.setLayoutData(data);

		getWidgetFactory().createLabel(jmsComposite, BpmnMessages.getString("jmsSection_priority_label"));
		jmsPriorityCombo = getWidgetFactory().createCCombo(jmsComposite,
				SWT.BORDER);
		jmsPriorityCombo.setEditable(false);
		jmsPriorityCombo.setItems(new String[] { "0", "1", "2", "3", "4", "5",
				"6", "7", "8", "9", "" });
		data = new GridData(GridData.FILL_HORIZONTAL);
//		data.widthHint = 550;
		jmsPriorityCombo.setLayoutData(data);

		getWidgetFactory().createLabel(jmsComposite,BpmnMessages.getString("jmsSection_TTL_label"));
		jmsTTLText = getWidgetFactory().createText(jmsComposite, "");
		data = new GridData(GridData.FILL_HORIZONTAL);
//		data.widthHint = 550;
		jmsTTLText.setLayoutData(data);
		jmsTTLText.addListener(SWT.Verify, new Listener() {
			public void handleEvent(Event e) {
				String string = e.text;
				char[] chars = new char[string.length()];
				string.getChars(0, chars.length, chars, 0);
				for (int i = 0; i < chars.length; i++) {
					if (!('0' <= chars[i] && chars[i] <= '9')) {
						e.doit = false;
						return;
					}
				}
			}
		});

		getWidgetFactory().createLabel(jmsComposite, BpmnMessages.getString("descriptionProp_correlationIdText_label"));
		correlationIdText = getWidgetFactory().createText(jmsComposite, "");
		data = new GridData(GridData.FILL_HORIZONTAL);
//		data.widthHint = 550;
		correlationIdText.setLayoutData(data);

	}

	private TabbedPropertySheetWidgetFactory getWidgetFactory() {
		return widgetFactory;
	}

	private void createRvSection() {
		getWidgetFactory().createLabel(rvComposite, "Subject    ");
		rvSubjectText = getWidgetFactory().createText(rvComposite, "");
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
//		data.widthHint = 550;
		rvSubjectText.setLayoutData(data);
	}

	public void destinationChanged(Destination dest) {
		if (dest != null) {
			DRIVER_TYPE driverType = dest.getDriverConfig().getDriverType();
			if (driverType.getName().equals(DriverManagerConstants.DRIVER_RV)) {
				stackLayout.topControl = rvComposite;
				parentComposite.layout(true);
			} else if (driverType.getName().equals(
					DriverManagerConstants.DRIVER_JMS)) {
				stackLayout.topControl = jmsComposite;
				parentComposite.layout(true);
			} else if (driverType.getName().equals(
					DriverManagerConstants.DRIVER_HTTP)) {
				stackLayout.topControl = httpComposite;
				parentComposite.layout(true);
			} else {
				stackLayout.topControl = defaultComposite;
				parentComposite.layout(true);
			}
		} else {
			stackLayout.topControl = defaultComposite;
			parentComposite.layout(true);
		}

	}
	
	private void fetchProperties(EObjectWrapper<EClass, EObject> wrapper, Map<Object, Object> map){
		if (stackLayout.topControl == jmsComposite) {
			Object attribute = wrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_DESTINATION_NAME);
			if(attribute != null)
				map.put(BpmnMetaModelExtensionConstants.E_ATTR_DESTINATION_NAME, attribute);
			
			attribute = wrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_QUEUE);
			if(attribute != null)
				map.put(BpmnMetaModelExtensionConstants.E_ATTR_QUEUE, attribute);
			
			attribute = wrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JMSEXPIRATION);
			if(attribute != null)
				map.put(BpmnMetaModelExtensionConstants.E_ATTR_JMSEXPIRATION, attribute);
			
			attribute = wrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JMSDELIVERYMODE);
			if(attribute != null)
				map.put(BpmnMetaModelExtensionConstants.E_ATTR_JMSDELIVERYMODE, attribute);
			
			attribute = wrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JMSPRIORITY);
			if(attribute != null)
				map.put(BpmnMetaModelExtensionConstants.E_ATTR_JMSPRIORITY, attribute);
			
			attribute = wrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JMSCORRELATIONID);
			if(attribute != null)
				map.put(BpmnMetaModelExtensionConstants.E_ATTR_JMSCORRELATIONID, attribute);
			
		}else if (stackLayout.topControl == rvComposite) {
			Object attribute = wrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_SUBJECT);
			if(attribute != null)
				map.put(BpmnMetaModelExtensionConstants.E_ATTR_SUBJECT, attribute);
		}
	}
	
	private void setProperties(EObjectWrapper<EClass, EObject> wrapper, Map<Object, Object> map){
		Set<Object> keySet = map.keySet();
		Iterator<Object> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			String object = (String) iterator.next();
			if(wrapper.containsAttribute(object)){
				wrapper.setAttribute(object, map.get(object));
			}
			
		}
	}

	public void refresh(Destination dest, EObject destConfig) {
		refresh = true;
		destinationChanged(dest);
		if (dest == null)
			return;

		propertiesMap = new HashMap<Object, Object>();
		this.destConfigWrapper = null;
		if(destConfig != null){
			EObjectWrapper<EClass, EObject> wrapper = EObjectWrapper.wrap(destConfig);
			this.destConfigWrapper = wrapper;
			propertiesMap = new HashMap<Object, Object>();
			fetchProperties(wrapper, propertiesMap);
			if (stackLayout.topControl == jmsComposite) {
				jmsNameText.setText("");
				queueCheckBox.setSelection(false);
				jmsDelModeCombo.setText("");
				jmsPriorityCombo.setText("");
				correlationIdText.setText("");
				jmsTTLText.setText("");
				if (propertiesMap != null) {
					jmsDestName = (String) propertiesMap.get(BpmnMetaModelExtensionConstants.E_ATTR_DESTINATION_NAME);
					if (jmsDestName != null) {
						jmsNameText.setText(jmsDestName);
					}

					queue = (Boolean) propertiesMap.get(BpmnMetaModelExtensionConstants.E_ATTR_QUEUE);
					if (queue != null) {
						queueCheckBox.setEnabled(queue);
					}

					priority = (Integer) propertiesMap.get(BpmnMetaModelExtensionConstants.E_ATTR_JMSPRIORITY);
					if (priority != null) {
						jmsPriorityCombo.setText(priority.toString());
					}

					deliveryMode = (Integer) propertiesMap.get(BpmnMetaModelExtensionConstants.E_ATTR_JMSDELIVERYMODE);
					if (deliveryMode != null) {
						switch (deliveryMode) {
						case 1:
							jmsDelModeCombo.setText("NON-PERSISTENT");
							break;
						case 2:
							jmsDelModeCombo.setText("PERSISTENT");
							break;
						case 22:
							jmsDelModeCombo.setText("RELIABLE(TIBCO Proprietary)");
							break;

						default:
							break;
						}
					}

					expiration = (Long) propertiesMap.get(BpmnMetaModelExtensionConstants.E_ATTR_JMSEXPIRATION);
					if (expiration != null) {
						jmsTTLText.setText(expiration.toString());
					}

					corrId = (String) propertiesMap.get(BpmnMetaModelExtensionConstants.E_ATTR_JMSCORRELATIONID);
					if (corrId != null) {
						jmsPriorityCombo.setText(corrId);
					}
				} else {
					propertiesMap = new HashMap<Object, Object>();
				}

			} else if (stackLayout.topControl == rvComposite) {
				rvSubjectText.setText("");
				if (propertiesMap != null) {
					rvSubject = (String) propertiesMap.get(BpmnMetaModelExtensionConstants.E_ATTR_SUBJECT);
					if (rvSubject != null) {
						rvSubjectText.setText(rvSubject);
					}
				}
			}
		}
		
		refresh = false;
	}

	public void addlistener() {
		if (rvComposite != null && !rvComposite.isDisposed()) {
			if (rvSubjectText != null && !rvSubjectText.isDisposed()) {
				rvSubjectText.addFocusListener(listener);
				rvSubjectText.addSelectionListener(listener);
			}
		}

		if (jmsComposite != null && !jmsComposite.isDisposed()) {
			if (jmsNameText != null && !jmsNameText.isDisposed()) {
				jmsNameText.addFocusListener(listener);
				jmsNameText.addSelectionListener(listener);
			}

			if (jmsTTLText != null && !jmsTTLText.isDisposed()) {
				jmsTTLText.addFocusListener(listener);
				jmsTTLText.addSelectionListener(listener);
			}

			if (correlationIdText != null && !correlationIdText.isDisposed()) {
				correlationIdText.addFocusListener(listener);
				correlationIdText.addSelectionListener(listener);
			}

			if (jmsPriorityCombo != null && !jmsPriorityCombo.isDisposed()) {
				jmsPriorityCombo.addSelectionListener(listener);
			}

			if (jmsDelModeCombo != null && !jmsDelModeCombo.isDisposed()) {
				jmsDelModeCombo.addSelectionListener(listener);
			}

			if (queueCheckBox != null && !queueCheckBox.isDisposed()) {
				queueCheckBox.addSelectionListener(listener);
			}

		}

	}

	public void removelistener() {
		if (rvComposite != null && !rvComposite.isDisposed()) {
			if (rvSubjectText != null && !rvSubjectText.isDisposed()) {
				rvSubjectText.removeFocusListener(listener);
				rvSubjectText.removeSelectionListener(listener);
			}
		}

		if (jmsComposite != null && !jmsComposite.isDisposed()) {
			if (jmsNameText != null && !jmsNameText.isDisposed()) {
				jmsNameText.removeFocusListener(listener);
				jmsNameText.removeSelectionListener(listener);
			}

			if (jmsTTLText != null && !jmsTTLText.isDisposed()) {
				jmsTTLText.removeFocusListener(listener);
				jmsTTLText.removeSelectionListener(listener);
			}

			if (correlationIdText != null && !correlationIdText.isDisposed()) {
				correlationIdText.removeFocusListener(listener);
				correlationIdText.removeSelectionListener(listener);
			}

			if (jmsPriorityCombo != null && !jmsPriorityCombo.isDisposed()) {
				jmsPriorityCombo.removeSelectionListener(listener);
			}

			if (jmsDelModeCombo != null && !jmsDelModeCombo.isDisposed()) {
				jmsDelModeCombo.removeSelectionListener(listener);
			}

			if (queueCheckBox != null && !queueCheckBox.isDisposed()) {
				queueCheckBox.removeSelectionListener(listener);
			}

		}

	}

	private void setProperties(String key, Object value) {
		Object object = propertiesMap.get(key);
		boolean modified = false;
		if (value != null && object != null) {
			if (!object.equals(value)) {
				propertiesMap.put(key, value);
				modified = true;
			}
		} else if (value != object) {
			propertiesMap.put(key, value);
			modified = true;
		}
		if (key.equals(BpmnMetaModelExtensionConstants.E_ATTR_DESTINATION_NAME) && value == null && modified) {
			propertiesMap.put(BpmnMetaModelExtensionConstants.E_ATTR_QUEUE, null);
		} else if (key.equals(BpmnMetaModelExtensionConstants.E_ATTR_DESTINATION_NAME) && value != null
				&& modified) {
			Boolean selection = queueCheckBox.getSelection();
			propertiesMap.put(BpmnMetaModelExtensionConstants.E_ATTR_QUEUE, selection);
		}

		if (modified) {
			Map<String, Object> updateList = new HashMap<String, Object>();
			EObjectWrapper<EClass, EObject> wrapper = destConfigWrapper;
			EObject eInstance = wrapper.getEInstance();
			EObject copy = EcoreUtil.copy(eInstance);
			wrapper = EObjectWrapper.wrap(copy);
			if(wrapper == null)
				wrapper = EObjectWrapper.createInstance(BpmnModelClass.EXTN_DESTINATION_CONFIG_DATA);
			setProperties(wrapper, propertiesMap);
			updateList.put(
					BpmnMetaModelExtensionConstants.E_ATTR_DESTINATION_CONFIG,
					wrapper.getEInstance());

			parentPropSection.updatePropertySection(updateList);
		}
	}

	private void handleWidgetModification(Object source) {
		if (refresh)
			return;
		Object value = null;
		String key = null;
		if (source == rvSubjectText) {
			key = BpmnMetaModelExtensionConstants.E_ATTR_SUBJECT;
			String text = rvSubjectText.getText();
			if (!text.trim().isEmpty())
				value = text;
		} else if (source == jmsNameText) {
			key = BpmnMetaModelExtensionConstants.E_ATTR_DESTINATION_NAME;
			String text = jmsNameText.getText();
			if (!text.trim().isEmpty())
				value = text;
		} else if (source == queueCheckBox) {
			key = BpmnMetaModelExtensionConstants.E_ATTR_QUEUE;
			if (jmsNameText.getText().isEmpty())
				value = null;
			else {
				value = queueCheckBox.getSelection();
			}
		} else if (source == jmsPriorityCombo) {
			key = BpmnMetaModelExtensionConstants.E_ATTR_JMSPRIORITY;
			if (!jmsPriorityCombo.getText().trim().isEmpty()) {
				try {
					value = Integer.parseInt(jmsPriorityCombo.getText());
				} catch (Exception e) {
					value = null;
				}
			}

		} else if (source == jmsTTLText) {
			key = BpmnMetaModelExtensionConstants.E_ATTR_JMSEXPIRATION;
			if (!jmsTTLText.getText().trim().isEmpty()) {
				try {
					value = Long.parseLong(jmsTTLText.getText());
				} catch (Exception e) {
					value = null;
				}
			}

		} else if (source == correlationIdText) {
			key = BpmnMetaModelExtensionConstants.E_ATTR_JMSCORRELATIONID;
			if (!correlationIdText.getText().trim().isEmpty())
				value = correlationIdText.getText();
		} else if (source == jmsDelModeCombo) {
			key = BpmnMetaModelExtensionConstants.E_ATTR_JMSDELIVERYMODE;
			if (!jmsDelModeCombo.getText().trim().isEmpty()) {
				String text = jmsDelModeCombo.getText().trim();
				if (text.equals("NON-PERSISTENT"))
					value = 1;
				else if (text.equals("PERSISTENT"))
					value = 2;
				else if (text.equals("RELIABLE(TIBCO Proprietary)"))
					value = 22;

			}
		}

		if (key != null)
			setProperties(key, value);
	}

	private class WidgetListener implements SelectionListener, FocusListener {

		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void focusLost(FocusEvent e) {
			Object source = e.getSource();
			if (source == rvSubjectText || source == jmsNameText
					|| source == correlationIdText || source == jmsTTLText) {
				handleWidgetModification(source);
			}

		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			Object source = e.getSource();
			if (source == jmsPriorityCombo || source == jmsDelModeCombo
					|| source == queueCheckBox) {
				handleWidgetModification(source);
			}

		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			Object source = e.getSource();
			if (source == rvSubjectText || source == jmsNameText
					|| source == correlationIdText || source == jmsTTLText) {
				handleWidgetModification(source);
			}

		}

	}

}
