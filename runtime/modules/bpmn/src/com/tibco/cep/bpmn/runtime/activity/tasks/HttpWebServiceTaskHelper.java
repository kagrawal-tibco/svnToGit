package com.tibco.cep.bpmn.runtime.activity.tasks;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.driver.http.client.impl.httpcomponents.HttpComponentsClientService;
import com.tibco.cep.driver.http.server.utils.SSLUtils;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManager;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.ArchiveResourceProvider;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.service.security.BEIdentity;
import com.tibco.cep.runtime.service.security.BEIdentityObjectFactory;
import com.tibco.cep.runtime.service.security.BEKeystoreIdentity;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.security.ObfuscationEngine;
import com.tibco.xml.datamodel.XiNode;

/**
 * 
 * @author majha
 * 
 */
class HttpWebServiceTaskHelper implements WebServiceTaskHelper {

	private String soapAction;
	private long timeOut;
	private String endPointUrl;
	private boolean isSecureRequest;
	private String certificateFolder;
	private String identity;
	private Boolean verifyHostName;
	private Boolean strongCipherSuiteOnly;
	private LogManager m_LogManager;
	private Logger m_Logger;
	private boolean isInOnly;

	public HttpWebServiceTaskHelper(EObjectWrapper<EClass, EObject> wrap) {
		isInOnly = false;
		// Get the LogManager object from LogManagerFactory
		m_LogManager = LogManagerFactory.getLogManager();
		// Get the logger from LogManager
		m_Logger = m_LogManager.getLogger(HttpWebServiceTaskHelper.class);
		init(wrap);
	}

	private void init(EObjectWrapper<EClass, EObject> wrap) {
		EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
				.getAddDataExtensionValueWrapper(wrap);
		
		String outputSchema = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_OUTPUT_PAYLOAD_SCHEMA);		
		if(outputSchema != null)
			isInOnly = outputSchema.equalsIgnoreCase(ServiceTask.ONE_WAY_SOAP_MESSAGE);
		
		soapAction = valueWrapper
				.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_SOAP_ACTION);
		Long tOut = (Long) valueWrapper
				.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TIMEOUT);
		timeOut = tOut == null ? 0 : tOut * 1000; // converting to millis
		endPointUrl = valueWrapper
				.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_END_POINT_URL);
		String upperCase = endPointUrl.toUpperCase();
		if (upperCase.startsWith("HTTPS")) {
			isSecureRequest =(Boolean) valueWrapper
					.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_USE_HTTP_SSL);
			if (isSecureRequest) {
				EObject configdata = valueWrapper
						.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_HTTP_SSL_CONFIG);
				if (configdata != null) {
					EObjectWrapper<EClass, EObject> configdataWrapper = EObjectWrapper
							.wrap(configdata);
					certificateFolder = configdataWrapper
							.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_CERTIFICATE_FOLDER);
					if (certificateFolder == null)
						certificateFolder = "";
					identity = configdataWrapper
							.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_IDENTITY);
					if (identity == null)
						identity = "";
					verifyHostName = (Boolean) configdataWrapper
							.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_VERIFY_HOST_NAME);
					strongCipherSuiteOnly = (Boolean) configdataWrapper
							.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_STRONG_CIPHER_SUITE_ONLY);
				} else {
					isSecureRequest = false;
				}
			}
		}
	}

	@Override
	public XiNode sendWebServiceRequest(XiNode soapRequest) throws Exception {
		if (isSecureRequest) {
			RuleServiceProvider rsp = RuleServiceProviderManager.getInstance()
					.getDefaultProvider();
			GlobalVariables gv = rsp.getGlobalVariables();
			ArchiveResourceProvider provider = rsp.getProject()
					.getSharedArchiveResourceProvider();
			KeyStore trustStore = null;
			KeyStore keystore = null;
			String keystorePass = null;
			String trustStorePassword = "soapRequest";
			if ((certificateFolder != null) && (certificateFolder.length() > 0)) {
				trustStore = SSLUtils.createKeystore(certificateFolder,
						trustStorePassword, provider, gv, true);
			}

			XiNode identityRepoResource = provider
					.getResourceAsXiNode(identity);
			if (null != identityRepoResource) {
				BEIdentity bid = BEIdentityObjectFactory.createIdentityObject(
						identityRepoResource, gv);
				if (bid instanceof BEKeystoreIdentity) {
					BEKeystoreIdentity beKeystoreId = (BEKeystoreIdentity) bid;
					keystore = KeyStore.getInstance(beKeystoreId
							.getStrStoreType());

					if (beKeystoreId.isPasswordObfuscated()) {
						keystorePass = new String(
								ObfuscationEngine.decrypt(beKeystoreId
										.getStrStorePassword()));
					} else {
						keystorePass = beKeystoreId.getStrStorePassword();
					}
					String keystoreFilePath = beKeystoreId.getStrKeystoreURL();
					File keystoreFile = new File(keystoreFilePath);

					if (keystoreFile.exists() && keystoreFile.canRead()
							&& !keystoreFile.isDirectory()) {
						m_Logger.log(Level.INFO,
								"Loading keystore from path %s",
								keystoreFilePath);
						keystore.load(new FileInputStream(keystoreFile),
								keystorePass.toCharArray());
					} else {
						m_Logger.log(
								Level.WARN,
								"Keystore file path specified by %s cannot be read. SSL may not work as expected.",
								keystoreFilePath);
					}
				}
			}

			SSLContext sslContext = SSLUtils.createSSLContext(keystore,
					keystorePass, trustStore, trustStorePassword, null);
			m_Logger.log(Level.DEBUG, "Web Service Task:sending web service request  to "+endPointUrl);
			XiNode soapResponse = HttpComponentsClientService
					.sendSecureSoapRequest(endPointUrl, soapRequest,
							soapAction, sslContext, verifyHostName, timeOut, isInOnly);
			m_Logger.log(Level.DEBUG, "Web Service Task: Web service task completed for "+endPointUrl);
			return soapResponse;

		} else {
			m_Logger.log(Level.DEBUG, "Web Service Task: sending web service request  to "+endPointUrl);
			XiNode soapResponse = HttpComponentsClientService.sendSoapRequest(
					endPointUrl, soapRequest, soapAction, timeOut, isInOnly);
			m_Logger.log(Level.DEBUG, "Web Service Task: Web service task completed for "+endPointUrl);
			return soapResponse;
		}

	}

}
