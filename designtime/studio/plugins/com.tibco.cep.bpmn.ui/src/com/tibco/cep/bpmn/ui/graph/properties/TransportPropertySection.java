package com.tibco.cep.bpmn.ui.graph.properties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.dialog.WsSslConfigJmsModel;
import com.tibco.cep.bpmn.ui.dialog.WsSslConfigurationJmsDialog;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.cep.bpmn.ui.graph.BpmnGraphUtils;
import com.tibco.cep.sharedresource.jndi.JndiPropsTableProviderModel;
import com.tibco.cep.studio.core.util.GvUtil;
import com.tibco.cep.studio.ui.util.TableProviderUi;
import com.tibco.security.AXSecurityException;
import com.tibco.security.ObfuscationEngine;

/**
 * 
 * @author sasahoo
 * 
 */
public class TransportPropertySection extends AbstractFormPropertySection {

	private boolean refresh;
	protected Section jndiSection;
	protected Section jmsSection;

	private Text endPointUrlText;
	private String endPointUrl;
	private Button configureHttpSSL;

	protected Text jmsNameText;
	protected CCombo jmsMessageTypeCombo;
	protected CCombo jmsDelModeCombo;
	protected CCombo jmsAckModeCombo;
	protected CCombo jmsPriorityCombo;
	protected Text jmsTTLText;
	protected Text jmsReplyToText;

	protected Text userName;
	protected Text userpw;
	protected CCombo jndiCtxFact;
	protected Text jndiConnectionfactory;
	protected Text jndiCtxUrl;
	protected Text jndiUserName;
	protected Text jndiuserpw;
	private Button configureJmsSSL;
	private Button useJmsSSL;
	private Label sslPasswordlabel;
	private Text  sslPasswordtext;
	private Table jndiAddProps;
	private Composite httpComposite;
	private Composite jmsComposite;
	private StackLayout stackLayout;
	private Composite parentComposite;

	private Map<String, Object> httpSslMap;
	private WsSslConfigJmsModel jmsModel;

	private WidgetListener listener;

	public TransportPropertySection() {
		super();
		endPointUrl = "";
		httpSslMap = new HashMap<String, Object>();
		this.listener = new WidgetListener();
		jmsModel = new WsSslConfigJmsModel();
	}

	@Override
	public void createControls(Composite parent,
			TabbedPropertySheetPage tabbedPropertySheetPage) {
		useHelp = false;

		super.createControls(parent, tabbedPropertySheetPage);
		this.parentComposite = parent;
		stackLayout = new StackLayout();
		parent.setLayout(stackLayout);

		httpComposite = getWidgetFactory().createComposite(parent);
		jmsComposite = getWidgetFactory().createComposite(parent);

		httpComposite.setLayout(new GridLayout(2, false));
		jmsComposite.setLayout(new FillLayout());
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.numColumns = 1;
		jmsComposite.setLayout(layout);

		sashForm = new MDSashForm(jmsComposite, SWT.HORIZONTAL);
		sashForm.setData("form", jmsComposite);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.grabExcessVerticalSpace = true;
		sashForm.setLayoutData(gd);
		jmsComposite.setLayoutData(gd);

		createHttpSection();
		createSections(sashForm);

		hookResizeListener();
		stackLayout.topControl = httpComposite;
		parentComposite.layout(true);

	}

	@Override
	public void aboutToBeShown() {
		// TODO Auto-generated method stub
		super.aboutToBeShown();
		refreshLayout();

		if (!httpComposite.isDisposed()) {
			endPointUrlText.addFocusListener(this.listener);
			endPointUrlText.addSelectionListener(this.listener);
			configureHttpSSL.addSelectionListener(this.listener);
		}
		if (!jmsComposite.isDisposed()) {
			userName.addFocusListener(listener);
			userName.addSelectionListener(listener);
			userpw.addFocusListener(listener);
			userpw.addSelectionListener(listener);
			jndiConnectionfactory.addFocusListener(listener);
			jndiConnectionfactory.addSelectionListener(listener);
			jndiCtxFact.addSelectionListener(listener);
			jndiCtxUrl.addFocusListener(listener);
			jndiCtxUrl.addSelectionListener(listener);
			jndiUserName.addFocusListener(listener);
			jndiUserName.addSelectionListener(listener);
			jndiuserpw.addFocusListener(listener);
			jndiuserpw.addSelectionListener(listener);
			sslPasswordtext.addSelectionListener(listener);
			sslPasswordtext.addFocusListener(listener);

			jmsNameText.addFocusListener(listener);
			jmsNameText.addSelectionListener(listener);
			jmsTTLText.addFocusListener(listener);
			jmsTTLText.addSelectionListener(listener);
			jmsReplyToText.addFocusListener(listener);
			jmsReplyToText.addSelectionListener(listener);
			jmsAckModeCombo.addSelectionListener(listener);
			jmsDelModeCombo.addSelectionListener(listener);
			jmsPriorityCombo.addSelectionListener(listener);
			configureJmsSSL.addSelectionListener(listener);
			jmsMessageTypeCombo.addSelectionListener(listener);
			

		}

	}

	@Override
	public void aboutToBeHidden() {
		// TODO Auto-generated method stub
		super.aboutToBeHidden();
		if (!httpComposite.isDisposed()) {
			endPointUrlText.removeFocusListener(this.listener);
			endPointUrlText.removeSelectionListener(this.listener);
			configureHttpSSL.removeSelectionListener(listener);
		}

		if (!jmsComposite.isDisposed()) {
			userName.removeFocusListener(listener);
			userName.removeSelectionListener(listener);
			userpw.removeFocusListener(listener);
			userpw.removeSelectionListener(listener);
			jndiConnectionfactory.removeFocusListener(listener);
			jndiConnectionfactory.removeSelectionListener(listener);
			jndiCtxFact.removeSelectionListener(listener);
			jndiCtxUrl.removeFocusListener(listener);
			jndiCtxUrl.removeSelectionListener(listener);
			jndiUserName.removeFocusListener(listener);
			jndiUserName.removeSelectionListener(listener);
			jndiuserpw.removeFocusListener(listener);
			jndiuserpw.removeSelectionListener(listener);
			sslPasswordtext.removeSelectionListener(listener);
			sslPasswordtext.removeFocusListener(listener);

			jmsNameText.removeFocusListener(listener);
			jmsNameText.removeSelectionListener(listener);
			jmsTTLText.removeFocusListener(listener);
			jmsTTLText.removeSelectionListener(listener);
			jmsReplyToText.removeFocusListener(listener);
			jmsReplyToText.removeSelectionListener(listener);
			jmsAckModeCombo.removeSelectionListener(listener);
			jmsDelModeCombo.removeSelectionListener(listener);
			jmsPriorityCombo.removeSelectionListener(listener);
			configureJmsSSL.removeSelectionListener(listener);
			jmsMessageTypeCombo.removeSelectionListener(listener);
		}
	}

	private void refreshLayout() {
		if (fTSENode != null) {
			EObject userObject = (EObject) fTSENode.getUserObject();
			if (userObject != null) {
				EObjectWrapper<EClass, EObject> flowNodeWrapper = EObjectWrapper
						.wrap(userObject);
				EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
						.getAddDataExtensionValueWrapper(flowNodeWrapper);

				EEnumLiteral propType = valueWrapper
						.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_BINDING_TYPE);
				if (propType == null
						|| propType.equals(BpmnModelClass.ENUM_WS_BINDING_HTTP)) {
					stackLayout.topControl = httpComposite;
					parentComposite.layout(true);
				} else {
					stackLayout.topControl = jmsComposite;
					parentComposite.layout(true);
				}
			}

		}
	}

	public void refresh() {
		this.refresh = true;
		if (fTSENode != null) {
			super.refresh();
			@SuppressWarnings("unused")
			EClass nodeType = (EClass) fTSENode
					.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
			@SuppressWarnings("unused")
			EClass nodeExtType = (EClass) fTSENode
					.getAttributeValue(BpmnUIConstants.NODE_ATTR_EXT_TYPE);
			@SuppressWarnings("unused")
			String nodeName = (String) fTSENode
					.getAttributeValue(BpmnUIConstants.NODE_ATTR_NAME);

			EObject userObject = (EObject) fTSENode.getUserObject();
			EObjectWrapper<EClass, EObject> taskWrapper = EObjectWrapper
					.wrap(userObject);
			refreshWidgetFromModel(taskWrapper);
			if (jndiAddProps != null) {
				ArrayList<ArrayList<String>> tableData = new ArrayList<ArrayList<String>>();
				setJndiAddPropsTable(tableData);
//				jndiAddProps.setData(tableData);
				jndiAddProps.removeAll();
				for (ArrayList<String> rowData : tableData) {
					TableItem item = new TableItem(jndiAddProps, SWT.NONE);
					for (int i = 0; i < jndiAddProps.getColumnCount(); i++) {
						item.setText(i, (String) rowData.get(i));
					}
				}
			}
		}
		if (fTSEEdge != null) {
		}
		if (fTSEGraph != null) {
		}

		// System.out.println("Done with refresh.");
		this.refresh = false;
	}

	private void refreshWidgetFromModel(
			EObjectWrapper<EClass, EObject> taskWrapper) {
		if (taskWrapper.getEClassType().equals(BpmnModelClass.SERVICE_TASK)) {
			EObjectWrapper<EClass, EObject> addDataExtensionValueWrapper = ExtensionHelper
					.getAddDataExtensionValueWrapper(taskWrapper);
			refreshLayout();
			if (addDataExtensionValueWrapper != null) {
				if (stackLayout.topControl == httpComposite) {
					endPointUrl = addDataExtensionValueWrapper
							.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_END_POINT_URL);
					endPointUrlText.setText(String.valueOf(endPointUrl));
					Boolean useSSl = addDataExtensionValueWrapper
							.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_USE_HTTP_SSL);
					if (useSSl == null)
						useSSl = Boolean.FALSE;

					configureHttpSSL.setEnabled(useSSl);
					httpSslMap.clear();
					EObject configdata = addDataExtensionValueWrapper
							.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_HTTP_SSL_CONFIG);
					if (configdata != null) {
						EObjectWrapper<EClass, EObject> wrap = EObjectWrapper
								.wrap(configdata);
						String folder = wrap
								.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_CERTIFICATE_FOLDER);
						if (folder == null)
							folder = "";
						String identity = wrap
								.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_IDENTITY);
						if (identity == null)
							identity = "";
						Boolean verifyHostName = (Boolean) wrap
								.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_VERIFY_HOST_NAME);
						Boolean cipherSuiteOnly = (Boolean) wrap
								.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_STRONG_CIPHER_SUITE_ONLY);
						httpSslMap
								.put(BpmnMetaModelExtensionConstants.E_ATTR_CERTIFICATE_FOLDER,
										folder);
						httpSslMap
								.put(BpmnMetaModelExtensionConstants.E_ATTR_IDENTITY,
										identity);
						httpSslMap
								.put(BpmnMetaModelExtensionConstants.E_ATTR_VERIFY_HOST_NAME,
										verifyHostName);
						httpSslMap
								.put(BpmnMetaModelExtensionConstants.E_ATTR_STRONG_CIPHER_SUITE_ONLY,
										cipherSuiteOnly);

					} else {
						httpSslMap
								.put(BpmnMetaModelExtensionConstants.E_ATTR_CERTIFICATE_FOLDER,
										"");
						httpSslMap
								.put(BpmnMetaModelExtensionConstants.E_ATTR_IDENTITY,
										"");
						httpSslMap
								.put(BpmnMetaModelExtensionConstants.E_ATTR_VERIFY_HOST_NAME,
										false);
						httpSslMap
								.put(BpmnMetaModelExtensionConstants.E_ATTR_STRONG_CIPHER_SUITE_ONLY,
										false);
					}
				} else {
					if (addDataExtensionValueWrapper
							.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JMS_CONFIG)) {
						EObject attribute = addDataExtensionValueWrapper
								.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JMS_CONFIG);
						if (attribute != null) {
							EObjectWrapper<EClass, EObject> wrap = EObjectWrapper
									.wrap(attribute);
							String uName = wrap
									.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_CONNECTION_USER_NAME);
							if (uName == null || uName.trim().isEmpty())
								userName.setText(BpmnMessages.getString("transport_property_username"));
							else
								userName.setText(uName);

							String pwd = wrap
									.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_CONNECTION_PASSWORD);
							if (isEncodedString(pwd))
								pwd = getDecodedString(pwd);
							if (pwd != null)
								userpw.setText(pwd);

							String contextFactory = wrap
									.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JNDI_PROVIDER_CONTEXT_FACTORY);
							if (contextFactory != null
									&& !contextFactory.trim().isEmpty())
								jndiCtxFact.setText(contextFactory);

							String connfactory = wrap
									.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JNDI_CONNECTION_FACTORY_NAME);
							if (connfactory != null)
								jndiConnectionfactory.setText(connfactory);
							else
								jndiConnectionfactory.setText("");

							String providerUrl = wrap
									.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JNDI_PROVIDER_URL);
							if (providerUrl != null)
								jndiCtxUrl.setText(providerUrl);
							else
								jndiCtxUrl.setText("");

							uName = wrap
									.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JNDI_USER_NAME);
							if (uName == null || uName.trim().isEmpty())
								jndiUserName.setText(BpmnMessages.getString("transport_property_username"));
							else
								jndiUserName.setText(uName);

							pwd = wrap
									.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JNDI_PASSWORD);
							if (isEncodedString(pwd))
								pwd = getDecodedString(pwd);
							if (pwd != null)
								jndiuserpw.setText(pwd);

							String destName = wrap
									.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_DESTINATION_NAME);
							if (destName != null)
								jmsNameText.setText(destName);
							else
								jmsNameText.setText("");
							
							if (wrap.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_MESSAGE_FORMAT)) {
								EEnumLiteral propType = wrap
										.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_MESSAGE_FORMAT);
								if (propType.equals(BpmnModelClass.ENUM_MESSAGE_FORMAT_TEXT))
									jmsMessageTypeCombo.select(1);
								else
									jmsMessageTypeCombo.select(0);
							}

							Integer delMode = wrap
									.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_DELIVERY_MODE);
							if (delMode != null) {
								switch (delMode) {
								case 1:
									jmsDelModeCombo.setText(BpmnMessages.getString("transport_property_jmsDelModeCombo_value1"));
									break;

								case 2:
									jmsDelModeCombo.setText(BpmnMessages.getString("transport_property_jmsDelModeCombo_value2"));
									break;

								case 22:
									jmsDelModeCombo
											.setText(BpmnMessages.getString("transport_property_jmsDelModeCombo_value3"));
									break;

								default:
									break;
								}

							}

							Integer pri = wrap
									.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_PRIORITY);
							if (pri != null)
								jmsPriorityCombo.setText(pri.toString());

							Long ttl = wrap
									.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TIME_TO_LIVE);
							if (ttl != null)
								jmsTTLText.setText(ttl.toString());
							else {
								jmsTTLText.setText("0");
							}

							String replyDestinationName = wrap
									.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_REPLY_DESTINATION_NAME);
							if (replyDestinationName != null)
								jmsReplyToText.setText(replyDestinationName);
							else {
								jmsReplyToText.setText("");
							}

							Integer ackMode = wrap
									.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_ACK_MODE);
							if (ackMode != null) {
								switch (ackMode) {
								case 1:
									jmsAckModeCombo.setText("AUTO_ACKNOWLEDGE");
									break;

								case 2:
									jmsAckModeCombo
											.setText("CLIENT_ACKNOWLEDGE");
									break;

								case 3:
									jmsAckModeCombo
											.setText("DUPS_OK_ACKNOWLEDGE");
									break;

								case 23:
									jmsAckModeCombo
											.setText("EXPLICIT_CLIENT_ACKNOWLEDGE(TIBCO Proprietary)");
									break;

								case 24:
									jmsAckModeCombo
											.setText("EXPLICIT_CLIENT_DUPS_OK_ACKNOWLEDGE(TIBCO Proprietary)");
									break;

								case 22:
									jmsAckModeCombo
											.setText("NO_ACKNOWLEDGE(TIBCO Proprietary)");
									break;

								default:
									break;
								}

							}
							
							boolean useJmsSslboolean = (Boolean)wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_USE_JMS_SSL);
							useJmsSSL.setSelection(useJmsSslboolean);
							configureJmsSSL.setEnabled(useJmsSslboolean);
							sslPasswordlabel.setVisible(useJmsSslboolean);
							sslPasswordtext.setVisible(useJmsSslboolean);
							
							String sslPass = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_SSL_PASSWORD);
							sslPasswordtext.setText(getDecodedString(sslPass));
							
							EObject jmsConfig = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JMS_SSL_CONFIG);
							jmsModel = new WsSslConfigJmsModel();
							if(jmsConfig != null) {
								EObjectWrapper<EClass, EObject> jmsConfigWrapper = EObjectWrapper.wrap(jmsConfig);
								String cert = jmsConfigWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_CERTIFICATE_FOLDER);
								if(cert != null)
									jmsModel.cert = cert;
								
								String identity = jmsConfigWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_IDENTITY);
								if(cert != null)
									jmsModel.identity = identity;
								
								String expectedHostname = jmsConfigWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_EXPECTED_HOSTNAME);
								if(cert != null)
									jmsModel.expectedHostName = expectedHostname;
								
								boolean debugTrace = (Boolean)jmsConfigWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_DEBUG_TRACE);
								jmsModel.debugTrace = debugTrace;
								
								boolean trace = (Boolean)jmsConfigWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TRACE);
								jmsModel.trace = trace;
								
								boolean verifyHostName = (Boolean)jmsConfigWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_VERIFY_HOST_NAME);
								jmsModel.verifyHostName = verifyHostName;
								
								boolean cipherSuite = (Boolean)jmsConfigWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_STRONG_CIPHER_SUITE_ONLY);
								jmsModel.cipherSuites = cipherSuite;
							}

						}
					}
				}

			}

		}

	}

	private void createHttpSection() {
		getWidgetFactory()
				.createLabel(httpComposite, BpmnMessages.getString("transportPropSection_endPointUrl_label"), SWT.NONE);
		endPointUrlText = getWidgetFactory().createText(httpComposite, "");
		GridData gd = new GridData();
		gd.widthHint = 450;
		gd.heightHint = 20;
		gd.horizontalIndent = 1;
		endPointUrlText.setLayoutData(gd);

		getWidgetFactory().createLabel(httpComposite, "", SWT.NONE);
		configureHttpSSL = getWidgetFactory().createButton(httpComposite,
				BpmnMessages.getString("jndiSection_configureJmsSSL_label"), SWT.NONE);
		configureHttpSSL.setEnabled(false);

		endPointUrlText.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				endPointUrlModified();
			}
		});

	}

	private void endPointUrlModified() {
		String text = endPointUrlText.getText();
		String upperCase = text.toUpperCase();
		String upperCase2 = endPointUrl.toUpperCase();
		if (!upperCase.equalsIgnoreCase(upperCase2)) {
			if ((upperCase.startsWith("HTTPS") && !upperCase2
					.startsWith("HTTPS"))
					|| (!upperCase.startsWith("HTTPS") && upperCase2
							.startsWith("HTTPS"))) {
				boolean startsWith = upperCase.startsWith("HTTPS");
				configureHttpSSL.setEnabled(startsWith);
				Map<String, Object> updateList = new HashMap<String, Object>();
				updateList.put(
						BpmnMetaModelExtensionConstants.E_ATTR_USE_HTTP_SSL,
						startsWith);
				updateList.put(
						BpmnMetaModelExtensionConstants.E_ATTR_END_POINT_URL,
						text);
				endPointUrl = text;
				updatePropertySection(updateList);
			}
		}
	}

	/**
	 * @param sashForm
	 */
	protected void createSections(SashForm sashForm) {
		createJNDIPart(sashForm);
		createJMSPart(sashForm);
		sashForm.setWeights(new int[] { 40, 40 });
	}

	/**
	 * @param parent
	 */
	protected void createJNDIPart(Composite parent) {
		TableProviderUi taPropsProviderUi;
		jndiSection = getWidgetFactory().createSection(parent,
				Section.TITLE_BAR | Section.EXPANDED/* | Section.TWISTIE */);
		jndiSection.setActiveToggleColor(getWidgetFactory().getHyperlinkGroup()
				.getActiveForeground());
		jndiSection.setToggleColor(getWidgetFactory().getColors().getColor(
				IFormColors.SEPARATOR));
		jndiSection.setText(BpmnMessages.getString("jndiSection_label"));

		Composite client = getWidgetFactory().createComposite(jndiSection,
				SWT.WRAP);
		GridLayout layout = new GridLayout(2, false);
		client.setLayout(layout);
		getWidgetFactory().paintBordersFor(client);
		jndiSection.setClient(client);


		getWidgetFactory().createLabel(client, BpmnMessages.getString("jndiSection_jndiCtxFact_label"));
		jndiCtxFact = getWidgetFactory().createCCombo(client, SWT.READ_ONLY);
		jndiCtxFact.setItems(getFactoryList());
		GridData data = new GridData();
		data.widthHint = 300;
		jndiCtxFact.setLayoutData(data);
		jndiCtxFact.select(0);

		getWidgetFactory().createLabel(client, BpmnMessages.getString("jndiSection_jndiConnectionfactory_label"));
		jndiConnectionfactory = getWidgetFactory().createText(client, "");
		data = new GridData();
		data.widthHint = 300;
		jndiConnectionfactory.setLayoutData(data);

		getWidgetFactory().createLabel(client,  BpmnMessages.getString("jndiSection_jndiCtxUrl_label"));
		jndiCtxUrl = getWidgetFactory().createText(client, "");
		data = new GridData();
		data.widthHint = 300;
		jndiCtxUrl.setLayoutData(data);

		getWidgetFactory().createLabel(client,BpmnMessages.getString("jndiSection_jndiUserName_label"));
		jndiUserName = getWidgetFactory().createText(client, "");
		data = new GridData();
		data.widthHint = 300;
		jndiUserName.setLayoutData(data);

		getWidgetFactory().createLabel(client,BpmnMessages.getString("jndiSection_jndiuserpw_label"));
		jndiuserpw = getWidgetFactory().createText(client, "", SWT.PASSWORD);
		data = new GridData();
		data.widthHint = 300;
		jndiuserpw.setLayoutData(data);

		getWidgetFactory().createLabel(client, BpmnMessages.getString("jndiSection_useJmsSSL_label"), SWT.NONE);
		useJmsSSL = getWidgetFactory().createButton(client, "", SWT.CHECK);

		getWidgetFactory().createLabel(client, "", SWT.NONE);
		configureJmsSSL = getWidgetFactory().createButton(client,
				BpmnMessages.getString("jndiSection_configureJmsSSL_label"), SWT.NONE);
		configureJmsSSL.setEnabled(false);
		
		sslPasswordlabel = getWidgetFactory().createLabel(client, BpmnMessages.getString("jndiSection_sslPassword_label"), SWT.NONE);
		sslPasswordtext = getWidgetFactory().createText(client,"",  SWT.PASSWORD);
		data = new GridData();
		data.widthHint = 300;
		sslPasswordtext.setLayoutData(data);
		sslPasswordtext.setVisible(false);
		sslPasswordlabel.setVisible(false);
		


		getWidgetFactory().createLabel(client, BpmnMessages.getString("transportPropertySection_OptionalJNDIProp"));
		
		
		Composite tableComp = new Composite(client, SWT.WRAP);
		GridData gdtableComp=new GridData(GridData.END);
		gdtableComp.horizontalSpan=3;
		gdtableComp.grabExcessHorizontalSpace=false;
		gdtableComp.grabExcessVerticalSpace=false;
		gdtableComp.widthHint=401;
		tableComp.setLayoutData(gdtableComp/*new GridData(GridData.FILL_BOTH)*/);
		
		GridLayout tablelayout = new GridLayout(2,false);
		tablelayout.numColumns=3;
		tableComp.setLayout(tablelayout);
		String columns[] = new String[]{ BpmnMessages.getString("variable.property.columnName.name"),
											BpmnMessages.getString("variable.property.columnType.name"), 
												BpmnMessages.getString("variable.property.columnValue.name")};
		JndiPropsTableProviderModel tableModel = new JndiPropsTableProviderModel(); 
		taPropsProviderUi = new TableProviderUi(tableComp, columns, true, tableModel);
		jndiAddProps = taPropsProviderUi.createTable();
		GridData gd = new GridData(GridData.BEGINNING);
		gd.heightHint = 100;
		gd.grabExcessHorizontalSpace=false;
		gd.grabExcessVerticalSpace=false;
		jndiAddProps.setHeaderVisible(true);
		jndiAddProps.setLayoutData(gd);
		ArrayList<ArrayList<String>> tableData = new ArrayList<ArrayList<String>>();

		setJndiAddPropsTable(tableData);
		taPropsProviderUi.setTableData(tableData);
		jndiAddProps.addMouseListener(taPropsProviderUi.tableTextModifyMouseListener(jndiAddProps, 0));
		jndiAddProps.addMouseListener(taPropsProviderUi.tableTextModifyMouseListener(jndiAddProps, 1, 0, TableProviderUi.TYPE_COMBO, tableModel.types));
		jndiAddProps.addMouseListener(taPropsProviderUi.tableTextModifyMouseListener(jndiAddProps, 2));
		jndiAddProps.addListener(SWT.Modify, getTableModifyListener());
		
		useJmsSSL.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				configureJmsSSL.setEnabled(useJmsSSL.getSelection());
				sslPasswordtext.setVisible(useJmsSSL.getSelection());
				sslPasswordlabel.setVisible(useJmsSSL.getSelection());
				if(!refresh){
					EObject userObject = (EObject) fTSENode.getUserObject();
					EObjectWrapper<EClass, EObject> taskWrapper = EObjectWrapper
							.wrap(userObject);
					EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
							.getAddDataExtensionValueWrapper(taskWrapper);
					EObject jmsConfig = valueWrapper
							.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JMS_CONFIG);
					if (jmsConfig != null) {
						EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(jmsConfig);
						wrap.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_USE_JMS_SSL, useJmsSSL.getSelection());
						
						EObject sslConfig = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JMS_SSL_CONFIG);
						EObject copy = EcoreUtil.copy(wrap.getEInstance());
						if(sslConfig != null){
							EObjectWrapper<EClass, EObject> copyWrapper = EObjectWrapper.wrap(copy);
							copyWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JMS_SSL_CONFIG, sslConfig);
						}
						
						Map<String, Object> updateList = new HashMap<String, Object>();
						updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_JMS_CONFIG, copy);
						
						updatePropertySection(updateList);
					}
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});

	}

private void setJndiAddPropsTable(ArrayList<ArrayList<String>> tableData){
		if (fTSENode != null) {
		EObject userObject = (EObject) fTSENode.getUserObject();
		EObjectWrapper<EClass, EObject> taskWrapper = EObjectWrapper
				.wrap(userObject);
		EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
				.getAddDataExtensionValueWrapper(taskWrapper);
		EObject jmsConfig = valueWrapper
				.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JMS_CONFIG);
		
		if(jmsConfig!=null){
			EObjectWrapper<EClass, EObject> wrap = EObjectWrapper
					.wrap(jmsConfig);
		EList<EObject> jndiPropsList= wrap.getListAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JNDI_PROPS);
		tableData.clear();
		for(EObject eObj:jndiPropsList){
			ArrayList<String> tempJndiProps= new ArrayList<String>();
			EObjectWrapper<EClass, EObject> jndiPropObj = EObjectWrapper
					.wrap(eObj);
			tempJndiProps.add((String)jndiPropObj.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_NAME));
			tempJndiProps.add((String)jndiPropObj.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_VALUE));
			tempJndiProps.add((String)jndiPropObj.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TYPE));
			tableData.add(tempJndiProps);
		}
		}
		}
	}
	private Listener getTableModifyListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				updateJndiProperties(jndiAddProps);
			}


		};
		return listener;
	}
	
	private void updateJndiProperties(Table jndiAddProps) {
		
		EObject userObject = (EObject) fTSENode.getUserObject();
		EObjectWrapper<EClass, EObject> taskWrapper = EObjectWrapper
				.wrap(userObject);
		EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
				.getAddDataExtensionValueWrapper(taskWrapper);
		EObject jmsConfig = valueWrapper
				.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JMS_CONFIG);
		EObject copy = EcoreUtil.copy(jmsConfig);
		EObjectWrapper<EClass, EObject> wrap = EObjectWrapper
				.wrap(copy);
		EList<EObject> jndiConfig = wrap
				.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JNDI_PROPS);
		ArrayList<EObject> newTable= new ArrayList<EObject>();
		System.out.println("jms1 table size"+jndiConfig.size());
		for(TableItem tblItem:jndiAddProps.getItems()){
			EObjectWrapper<EClass, EObject> createInstance = EObjectWrapper
					.createInstance(BpmnModelClass.EXTN_JMS_CONTEXT_PARAMETER);
			createInstance
				.setAttribute(
					BpmnMetaModelExtensionConstants.E_ATTR_NAME,
					tblItem.getText(0));
			createInstance
				.setAttribute(
					BpmnMetaModelExtensionConstants.E_ATTR_VALUE,
					tblItem.getText(1));
			createInstance
			.setAttribute(
					BpmnMetaModelExtensionConstants.E_ATTR_TYPE,
					tblItem.getText(2));
			newTable.add(createInstance.getEInstance());
			/*wrap
			.addToListAttribute(
					BpmnMetaModelExtensionConstants.E_ATTR_JNDI_PROPS,
					createInstance
							.getEInstance());	*/

		}
		wrap.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JNDI_PROPS, newTable);
		EList<EObject> jndiConfig1 = wrap
				.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JNDI_PROPS);
		System.out.println("jms table size"+jndiConfig1.size());
		Map<String, Object> updateList= new HashMap<String, Object>();
			updateList
					.put(BpmnMetaModelExtensionConstants.E_ATTR_JMS_CONFIG,
							wrap.getEInstance());
			updatePropertySection(updateList);
	}
	/**
	 * @param parent
	 */
	protected void createJMSPart(Composite parent) {
		jmsSection = getWidgetFactory().createSection(parent,
				Section.TITLE_BAR | Section.EXPANDED/* | Section.TWISTIE */);
		jmsSection.setActiveToggleColor(getWidgetFactory().getHyperlinkGroup()
				.getActiveForeground());
		jmsSection.setToggleColor(getWidgetFactory().getColors().getColor(
				IFormColors.SEPARATOR));
		jmsSection.setText(BpmnMessages.getString("jmsSection_label"));

		Composite client = getWidgetFactory().createComposite(jmsSection,
				SWT.WRAP);
		GridLayout layout = new GridLayout(2, false);
		client.setLayout(layout);
		getWidgetFactory().paintBordersFor(client);
		jmsSection.setClient(client);

		getWidgetFactory().createLabel(client,  BpmnMessages.getString("jmsSection_userName_label"));
		userName = getWidgetFactory().createText(client, "");
		GridData data = new GridData();
		data.widthHint = 300;
		userName.setLayoutData(data);
		userName.setText("admin");

		getWidgetFactory().createLabel(client, BpmnMessages.getString("jmsSection_userpw_label"));
		userpw = getWidgetFactory().createText(client, "", SWT.PASSWORD);
		data = new GridData();
		data.widthHint = 300;
		userpw.setLayoutData(data);
		
		getWidgetFactory().createLabel(client, BpmnMessages.getString("jmsSection_jmsDestination_label"));
		jmsNameText = getWidgetFactory().createText(client, "");
		data = new GridData();
		data.widthHint = 300;
		jmsNameText.setLayoutData(data);
		
		getWidgetFactory().createLabel(client,  BpmnMessages.getString("jmsSection_messageType_label"));
		jmsMessageTypeCombo = getWidgetFactory()
				.createCCombo(client, SWT.READ_ONLY);
		jmsMessageTypeCombo.setItems(new String[] { "Byte Message", "Text Message" });
		data = new GridData();
		data.widthHint = 300;
		jmsMessageTypeCombo.setLayoutData(data);
		jmsMessageTypeCombo.setData(jmsMessageTypeCombo.getItem(0), 1);
		jmsMessageTypeCombo.setData(jmsMessageTypeCombo.getItem(1), 2);

		getWidgetFactory().createLabel(client,  BpmnMessages.getString("jmsSection_deliveryMode_label"));
		jmsDelModeCombo = getWidgetFactory()
				.createCCombo(client, SWT.READ_ONLY);
		jmsDelModeCombo.setItems(new String[] { "PERSISTENT", "NON-PERSISTENT",
				"RELIABLE(TIBCO Proprietary)" });
		data = new GridData();
		data.widthHint = 300;
		jmsDelModeCombo.setLayoutData(data);
		jmsDelModeCombo.setData(jmsDelModeCombo.getItem(0), 1);
		jmsDelModeCombo.setData(jmsDelModeCombo.getItem(1), 2);
		jmsDelModeCombo.setData(jmsDelModeCombo.getItem(2), 22);

		getWidgetFactory().createLabel(client, BpmnMessages.getString("jmsSection_ackMode_label"));
		jmsAckModeCombo = getWidgetFactory()
				.createCCombo(client, SWT.READ_ONLY);
		jmsAckModeCombo.setItems(new String[] { "AUTO_ACKNOWLEDGE",
				"CLIENT_ACKNOWLEDGE", "DUPS_OK_ACKNOWLEDGE",
				"EXPLICIT_CLIENT_ACKNOWLEDGE(TIBCO Proprietary)",
				"EXPLICIT_CLIENT_DUPS_OK_ACKNOWLEDGE(TIBCO Proprietary)",
				"NO_ACKNOWLEDGE(TIBCO Proprietary)" });
		data = new GridData();
		data.widthHint = 300;
		jmsAckModeCombo.setLayoutData(data);
		jmsAckModeCombo.setData(jmsAckModeCombo.getItem(0), 1);
		jmsAckModeCombo.setData(jmsAckModeCombo.getItem(1), 2);
		jmsAckModeCombo.setData(jmsAckModeCombo.getItem(2), 3);
		jmsAckModeCombo.setData(jmsAckModeCombo.getItem(3), 23);
		jmsAckModeCombo.setData(jmsAckModeCombo.getItem(4), 24);
		jmsAckModeCombo.setData(jmsAckModeCombo.getItem(5), 22);

		getWidgetFactory().createLabel(client, BpmnMessages.getString("jmsSection_priority_label"));
		jmsPriorityCombo = getWidgetFactory().createCCombo(client,
				SWT.READ_ONLY);
		jmsPriorityCombo.setItems(new String[] { "0", "1", "2", "3", "4", "5",
				"6", "7", "8", "9" });
		data = new GridData();
		data.widthHint = 300;
		jmsPriorityCombo.setLayoutData(data);

		getWidgetFactory().createLabel(client,  BpmnMessages.getString("jmsSection_TTL_label"));
		jmsTTLText = getWidgetFactory().createText(client, "");
		data = new GridData();
		data.widthHint = 300;
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

		Label createLabel = getWidgetFactory().createLabel(client, BpmnMessages.getString("jmsSection_replyTo_label"));
		createLabel.setVisible(false);
		jmsReplyToText = getWidgetFactory().createText(client, "");
		data = new GridData();
		data.widthHint = 300;
		jmsReplyToText.setLayoutData(data);
		jmsReplyToText.setVisible(false);
	}

	@Override
	protected void updatePropertySection(Map<String, Object> updateList) {
		BpmnGraphUtils.fireUpdate(updateList, fTSENode, fPropertySheetPage
				.getEditor().getBpmnGraphDiagramManager());

	}

	@Override
	protected void createPropertyPartControl(IManagedForm form, Composite parent) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void createHelpPartControl(IManagedForm form, Composite parent) {
		// TODO Auto-generated method stub

	}

	public String[] getFactoryList() {
		return new String[] {
				"com.tibco.tibjms.naming.TibjmsInitialContextFactory",
				"com.ibm.websphere.naming.WsnInitialContextFactory",
				"weblogic.jndi.WLInitialContextFactory",
				"com.sun.jndi.fscontext.RefFSContextFactory" };
	}

	private void setProperties(String key, Object value) {
		Map<String, Object> updateList = new HashMap<String, Object>();
		EObject userObject = (EObject) fTSENode.getUserObject();
		if (userObject != null) {
			EObjectWrapper<EClass, EObject> wrap = EObjectWrapper
					.wrap(userObject);
			EObjectWrapper<EClass, EObject> addDataExtensionValueWrapper = ExtensionHelper
					.getAddDataExtensionValueWrapper(wrap);
			if (addDataExtensionValueWrapper != null) {
				if (key.equals(BpmnMetaModelExtensionConstants.E_ATTR_END_POINT_URL)) {
					Object attribute = addDataExtensionValueWrapper
							.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_END_POINT_URL);
					if (attribute != null) {
						if (!attribute.equals(value)) {
							updateList.put(key, value);
						}
					} else {
						updateList.put(key, value);
					}
					updatePropertySection(updateList);
				} else {
					boolean modified = false;
					EObject attribute = addDataExtensionValueWrapper
							.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JMS_CONFIG);
					if (attribute != null) {
						attribute = EcoreUtil.copy(attribute);
						EObjectWrapper<EClass, EObject> configWrapper = EObjectWrapper
								.wrap(attribute);
						if (configWrapper.containsAttribute(key)) {
							Object keyData = configWrapper.getAttribute(key);
							if (keyData != null) {
								if (!keyData.equals(value)) {
									configWrapper.setAttribute(key, value);
									modified = true;
								}
							} else {
								configWrapper.setAttribute(key, value);
								modified = true;
							}
						}
						if (modified)
							updateList
									.put(BpmnMetaModelExtensionConstants.E_ATTR_JMS_CONFIG,
											configWrapper.getEInstance());

					} else {
						EObjectWrapper<EClass, EObject> createInstance = EObjectWrapper
								.createInstance(BpmnModelClass.EXTN_JMS_CONFIG_DATA);
						if (createInstance.containsAttribute(key)) {
							createInstance.setAttribute(key, value);
							updateList
									.put(BpmnMetaModelExtensionConstants.E_ATTR_JMS_CONFIG,
											createInstance.getEInstance());
							modified = true;
						}
					}
					if (modified)
						updatePropertySection(updateList);
				}
			}

		}

	}

	private void handleWidgetModification(Object source) {
		if (refresh)
			return;
		Object value = null;
		String key = null;
		if (source == userName) {
			key = BpmnMetaModelExtensionConstants.E_ATTR_CONNECTION_USER_NAME;
			String text = userName.getText();
			if (!text.trim().isEmpty())
				value = text;
			else
				value = "admin";
		} else if (source == userpw) {
			key = BpmnMetaModelExtensionConstants.E_ATTR_CONNECTION_PASSWORD;
			String text = userpw.getText();
			value = getEncodedString((String) text);
		} else if (source == jndiCtxFact) {
			key = BpmnMetaModelExtensionConstants.E_ATTR_JNDI_PROVIDER_CONTEXT_FACTORY;
			value = jndiCtxFact.getText();
		} else if (source == jndiConnectionfactory) {
			key = BpmnMetaModelExtensionConstants.E_ATTR_JNDI_CONNECTION_FACTORY_NAME;
			if (!jndiConnectionfactory.getText().trim().isEmpty())
				value = jndiConnectionfactory.getText();
		} else if (source == jndiCtxUrl) {
			key = BpmnMetaModelExtensionConstants.E_ATTR_JNDI_PROVIDER_URL;
			if (!jndiCtxUrl.getText().trim().isEmpty())
				value = jndiCtxUrl.getText();
		} else if (source == jndiUserName) {
			key = BpmnMetaModelExtensionConstants.E_ATTR_JNDI_USER_NAME;
			String text = jndiUserName.getText();
			if (!text.trim().isEmpty())
				value = text;
			else
				value = "admin";
		} else if (source == jndiuserpw) {
			key = BpmnMetaModelExtensionConstants.E_ATTR_JNDI_PASSWORD;
			value = jndiuserpw.getText();
			value = getEncodedString((String) value);
		}else if (source == jndiAddProps){ 
				key=BpmnMetaModelExtensionConstants.E_ATTR_JMS_CONFIG;
				value=jndiAddProps;
		} else if (source == jmsNameText) {
			key = BpmnMetaModelExtensionConstants.E_ATTR_DESTINATION_NAME;
			if (!jmsNameText.getText().trim().isEmpty())
				value = jmsNameText.getText();
		} else if (source == jmsDelModeCombo) {
			key = BpmnMetaModelExtensionConstants.E_ATTR_DELIVERY_MODE;
			value = jmsDelModeCombo.getData(jmsDelModeCombo.getText());
		} else if (source == jmsAckModeCombo) {
			key = BpmnMetaModelExtensionConstants.E_ATTR_ACK_MODE;
			value = jmsAckModeCombo.getData(jmsAckModeCombo.getText());
		} else if (source == jmsPriorityCombo) {
			key = BpmnMetaModelExtensionConstants.E_ATTR_PRIORITY;
			value = Integer.parseInt(jmsPriorityCombo.getText());
		} else if (source == jmsTTLText) {
			key = BpmnMetaModelExtensionConstants.E_ATTR_TIME_TO_LIVE;
			if (!jmsTTLText.getText().trim().isEmpty()) {
				try {
					value = Long.parseLong(jmsTTLText.getText());
				} catch (Exception e) {
					value = 0;
				}
			} else {
				value = 0;
			}

		} else if (source == jmsReplyToText) {
			key = BpmnMetaModelExtensionConstants.E_ATTR_REPLY_DESTINATION_NAME;
			value = jmsReplyToText.getText();
		} else if (source == endPointUrlText) {
			key = BpmnMetaModelExtensionConstants.E_ATTR_END_POINT_URL;
			value = endPointUrlText.getText();
		}else if (source == sslPasswordtext) {
			key = BpmnMetaModelExtensionConstants.E_ATTR_SSL_PASSWORD;
			value = getEncodedString((String) sslPasswordtext.getText());
		}else if (source == jmsMessageTypeCombo) {
			key = BpmnMetaModelExtensionConstants.E_ATTR_MESSAGE_FORMAT;
			Integer data = (Integer)jmsMessageTypeCombo.getData(jmsMessageTypeCombo.getText());
			if(data != null && data == 1)
				value = BpmnModelClass.ENUM_MESSAGE_FORMAT_BYTES;
			else
				value = BpmnModelClass.ENUM_MESSAGE_FORMAT_TEXT;
		}

		if (key != null && value != null)
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
			if (source == endPointUrlText || source == jmsNameText
					|| source == jmsTTLText || source == jndiUserName
					|| source == jndiuserpw || source == jndiCtxUrl
					|| source == userName || source == userpw
					|| source == jndiConnectionfactory
					|| source == sslPasswordtext
					|| source == jmsReplyToText) {
				handleWidgetModification(source);
			}

		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			Object source = e.getSource();
			if (source == jndiCtxFact || source == jmsDelModeCombo
					|| source == jmsPriorityCombo || source == jmsAckModeCombo||source==jndiAddProps) {
				handleWidgetModification(source);
			} else if (source == configureHttpSSL) {
				ProcessSSLConfigDialog dialog = new ProcessSSLConfigDialog(
						fEditor.getSite().getShell(), getProject(), httpSslMap);
				if (dialog.open() == Dialog.OK) {
					// TODO: save this and make editor dirty
					Map<String, Object> updateList = dialog.getSslConfigMap();
					if (dialog.isChanged()) {
						EObject userObject = (EObject) fTSENode.getUserObject();
						EObjectWrapper<EClass, EObject> taskWrapper = EObjectWrapper
								.wrap(userObject);
						EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
								.getAddDataExtensionValueWrapper(taskWrapper);
						EObject sslConfig = valueWrapper
								.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_HTTP_SSL_CONFIG);
						if (sslConfig == null)
							sslConfig = EObjectWrapper.createInstance(
									BpmnModelClass.EXTN_HTTP_SSL_CONFIG)
									.getEInstance();

						EObjectWrapper<EClass, EObject> wrap = EObjectWrapper
								.wrap(sslConfig);
						Set<String> keySet = updateList.keySet();
						for (String string : keySet) {
							Object object = updateList.get(string);
							if (wrap.containsAttribute(string))
								wrap.setAttribute(string, object);
						}
						updateList = new HashMap<String, Object>();
						EObject copy = EcoreUtil.copy(sslConfig);
						updateList
								.put(BpmnMetaModelExtensionConstants.E_ATTR_HTTP_SSL_CONFIG,
										copy);
						updatePropertySection(updateList);
					}
				}
			} else if(source == jmsMessageTypeCombo){
				handleWidgetModification(source);
			}else if (source == configureJmsSSL) {
				WsSslConfigurationJmsDialog dialog = new WsSslConfigurationJmsDialog(
						fEditor.getSite().getShell(), jmsModel, getProject());
				dialog.initDialog("");
				dialog.openDialog();
				if (dialog.isDirty()) {
					EObject userObject = (EObject) fTSENode.getUserObject();
					EObjectWrapper<EClass, EObject> taskWrapper = EObjectWrapper
							.wrap(userObject);
					EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
							.getAddDataExtensionValueWrapper(taskWrapper);
					EObject jmsConfig = valueWrapper
							.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JMS_CONFIG);
					if (jmsConfig != null) {
						EObjectWrapper<EClass, EObject> jmsConfigWrap = EObjectWrapper
								.wrap(jmsConfig);
						boolean useJms = (Boolean) jmsConfigWrap
								.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_USE_JMS_SSL);
						if (useJms) {
							EObject jmsSslConfig = jmsConfigWrap
									.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JMS_SSL_CONFIG);
							if (jmsSslConfig == null)
								jmsSslConfig = EObjectWrapper.createInstance(
										BpmnModelClass.EXTN_JMS_SSL_CONFIG)
										.getEInstance();

							EObjectWrapper<EClass, EObject> jmsSslWrapper = EObjectWrapper
									.wrap(jmsSslConfig);
							jmsSslWrapper
									.setAttribute(
											BpmnMetaModelExtensionConstants.E_ATTR_TRACE,
											jmsModel.trace);
							jmsSslWrapper
									.setAttribute(
											BpmnMetaModelExtensionConstants.E_ATTR_DEBUG_TRACE,
											jmsModel.debugTrace);
							jmsSslWrapper
									.setAttribute(
											BpmnMetaModelExtensionConstants.E_ATTR_STRONG_CIPHER_SUITE_ONLY,
											jmsModel.cipherSuites);
							jmsSslWrapper
									.setAttribute(
											BpmnMetaModelExtensionConstants.E_ATTR_VERIFY_HOST_NAME,
											jmsModel.verifyHostName);
							jmsSslWrapper
									.setAttribute(
											BpmnMetaModelExtensionConstants.E_ATTR_EXPECTED_HOSTNAME,
											jmsModel.expectedHostName);
							jmsSslWrapper
									.setAttribute(
											BpmnMetaModelExtensionConstants.E_ATTR_IDENTITY,
											jmsModel.getIdentity());
							jmsSslWrapper
									.setAttribute(
											BpmnMetaModelExtensionConstants.E_ATTR_CERTIFICATE_FOLDER,
											jmsModel.getCert());

							EObject copy = EcoreUtil.copy(jmsConfigWrap
									.getEInstance());
							EObjectWrapper<EClass, EObject> copyWrap = EObjectWrapper
									.wrap(copy);
							copyWrap.setAttribute(
									BpmnMetaModelExtensionConstants.E_ATTR_JMS_SSL_CONFIG,
									jmsSslWrapper.getEInstance());

							HashMap<String, Object> updateList = new HashMap<String, Object>();
							updateList
									.put(BpmnMetaModelExtensionConstants.E_ATTR_JMS_CONFIG,
											copy);
							updatePropertySection(updateList);

						}
					}
				}
			}

		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			Object source = e.getSource();
			if (source == endPointUrlText || source == jmsNameText
					|| source == jmsTTLText || source == jndiUserName
					|| source == jndiuserpw || source == jndiCtxUrl
					|| source == userName || source == userpw
					|| source == jndiConnectionfactory
					|| source == sslPasswordtext
					|| source == jmsReplyToText) {
				handleWidgetModification(source);
			}

		}
	}

	private String getEncodedString(String password) {
		if (GvUtil.isGlobalVar(password))
			return password;
		if (password == null)
			return ("");
		if (password.startsWith("#!"))
			return password;
		String encoded = password;
		try {
			encoded = ObfuscationEngine.encrypt(password.toCharArray());
		} catch (AXSecurityException e) {
			e.printStackTrace();
		}
		return encoded;
	}

	private String getDecodedString(String encoded) {
		if (encoded == null || encoded.trim().equals(""))
			return ("");
		if (GvUtil.isGlobalVar(encoded))
			return encoded;
		char password[] = new char[0];
		try {
			password = ObfuscationEngine.decrypt(encoded);
		} catch (AXSecurityException e) {
			e.printStackTrace();
		}
		return new String(password);
	}

	private boolean isEncodedString(String password) {
		if (password == null || !password.startsWith("#!"))
			return false;
		return true;
	}

}
