package com.tibco.cep.bpmn.runtime.activity.tasks;

import static com.tibco.cep.driver.soap.SoapConstants.SOAP_ENVELOPE_NAME;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.model.rdf.XiNodeBuilder;
import com.tibco.be.util.XiSupport;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.driver.jms.EmsHelper;
import com.tibco.cep.driver.jms.JMSConstants;
import com.tibco.cep.driver.soap.SoapUtils;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManager;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.ArchiveResourceProvider;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.service.security.BECertPlusKeyIdentity;
import com.tibco.cep.runtime.service.security.BEIdentity;
import com.tibco.cep.runtime.service.security.BEIdentityUtilities;
import com.tibco.cep.runtime.service.security.BEKeystoreIdentity;
import com.tibco.cep.runtime.service.security.BETrustedCertificateManager;
import com.tibco.cep.runtime.service.security.BEUrlIdentity;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.security.Cert;
import com.tibco.security.ObfuscationEngine;
import com.tibco.security.TrustedCerts;
import com.tibco.xml.XiNodeUtilities;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.datamodel.helpers.XiSerializer;

/**
 * 
 * @author majha
 * 
 */
class JmsWebServiceTaskHelper implements WebServiceTaskHelper , JMSConstants {
	private static final String JMS_REPLY_TO = "TempJmsReplyTo";
	// JNDI Connection props
	private String jndiProviderURL;
	private String destinationName;
	private String jndiConnectionFactoryName;
	private String jndiProviderContextFactory;
	private String jndiUserName;
	private String jndiPassword;
	// private String sslPassword;
	private List<EObject> jndiProps;
	String connectionUserName;
	String connectionPassword;

	// message header props
	private int deliveryMode;
	private long ttl;
	private String replyDestinationName;
	private int priority;
	private int ackMode;

	// message props
	private String soapAction;
	private String targetService;
	private String requestUri;
	private String contentType;
	// private String contentEncoding;

	// ws client prop
	private long timeOut;
	private Context jndiContext;
	private boolean isByteMessage;
	private ConnectionFactory connectionFactory;
	private Destination destination;
	private Destination replyDestination;
	private String vendor;
	private String admFactorySslPassword;
	private boolean useSsl;
	private GlobalVariables gv;
	private ArchiveResourceProvider provider;
	private LogManager m_LogManager;
	private Logger m_Logger;
	private boolean isInOnly;

	public JmsWebServiceTaskHelper(EObjectWrapper<EClass, EObject> wrap)
			throws NamingException {
		this.vendor = System.getProperty(SystemProperty.TIBCO_SECURITY_VENDOR.getPropertyName());
        if ((null == this.vendor) || this.vendor.trim().isEmpty()) {
            this.vendor = JMSConstants.J2SE;
        }
        isInOnly = false;
     // Get the LogManager object from LogManagerFactory
     	m_LogManager = LogManagerFactory.getLogManager();
     		// Get the logger from LogManager
     	m_Logger = m_LogManager.getLogger(JmsWebServiceTaskHelper.class);
		setDefaultValues();
		EObjectWrapper<EClass, EObject> addDataExtensionValueWrapper = ExtensionHelper
				.getAddDataExtensionValueWrapper(wrap);
		if (addDataExtensionValueWrapper != null)
			init(addDataExtensionValueWrapper);
	}

	private void setDefaultValues() {
		jndiUserName = "admin";
		jndiPassword = "";
		connectionUserName = "admin";
		connectionPassword = "";
		timeOut = 0;
		ttl = 0;
		deliveryMode = DeliveryMode.PERSISTENT;
		priority = 4;
		isByteMessage = true;
		ackMode = Session.AUTO_ACKNOWLEDGE;
		requestUri = "";
		targetService = "";
		contentType = "application/xml";
		jndiProviderContextFactory = "com.tibco.tibjms.naming.TibjmsInitialContextFactory";
	}

	private void init(EObjectWrapper<EClass, EObject> valueWrapper)
			throws NamingException {
		String outputSchema = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_OUTPUT_PAYLOAD_SCHEMA);		
		if(outputSchema != null)
			isInOnly = outputSchema.equalsIgnoreCase(ServiceTask.ONE_WAY_SOAP_MESSAGE);
		
		EObject jmsConfig = valueWrapper
				.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JMS_CONFIG);
		if (jmsConfig != null) {
			EObjectWrapper<EClass, EObject> wrap = EObjectWrapper
					.wrap(jmsConfig);
			if (wrap.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JNDI_PROVIDER_CONTEXT_FACTORY))
				jndiProviderContextFactory = wrap
						.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JNDI_PROVIDER_CONTEXT_FACTORY);
			if (wrap.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JNDI_PROVIDER_URL))
				jndiProviderURL = wrap
						.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JNDI_PROVIDER_URL);
			if (wrap.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JNDI_CONNECTION_FACTORY_NAME))
				jndiConnectionFactoryName = wrap
						.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JNDI_CONNECTION_FACTORY_NAME);
			if (wrap.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_DESTINATION_NAME))
				destinationName = wrap
						.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_DESTINATION_NAME);
			if (wrap.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JNDI_USER_NAME)){
				jndiUserName = wrap
						.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JNDI_USER_NAME);
				if(jndiUserName == null || jndiUserName.trim().isEmpty())
					jndiUserName = "admin";
			}
			
			if (wrap.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JNDI_PASSWORD)) {
				jndiPassword = wrap
						.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JNDI_PASSWORD);
				if (jndiPassword != null && !jndiPassword.isEmpty()) {
					try {
						char[] decryptedvalue = ObfuscationEngine
								.decrypt(jndiPassword);
						jndiPassword = new String(decryptedvalue);

					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
			if (wrap.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_CONNECTION_USER_NAME)){
				connectionUserName = wrap
						.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_CONNECTION_USER_NAME);
				if(connectionUserName == null || connectionUserName.trim().isEmpty())
					connectionUserName = "admin";
			}
			if (wrap.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_CONNECTION_PASSWORD)){
				connectionPassword = wrap
						.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_CONNECTION_PASSWORD);
				if (connectionPassword != null && !connectionPassword.isEmpty()) {
					try {
						char[] decryptedvalue = ObfuscationEngine
								.decrypt(connectionPassword);
						connectionPassword = new String(decryptedvalue);

					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
			if (valueWrapper
					.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_SOAP_ACTION))
				soapAction = valueWrapper
						.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_SOAP_ACTION);
			if (wrap.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_REPLY_DESTINATION_NAME)){
				replyDestinationName = wrap
						.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_REPLY_DESTINATION_NAME);
			}
			if (wrap.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TARGET_SERVICE))
				targetService = wrap
						.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TARGET_SERVICE);
			if (wrap.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_REQUEST_URI))
				requestUri = wrap
						.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_REQUEST_URI);
			if (wrap.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_CONTENT_TYPE))
				contentType = wrap
						.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_CONTENT_TYPE);
			if (wrap.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_MESSAGE_FORMAT)) {
				EEnumLiteral propType = wrap
						.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_MESSAGE_FORMAT);
				if (propType.equals(BpmnModelClass.ENUM_MESSAGE_FORMAT_TEXT))
					isByteMessage = false;
				else
					isByteMessage = true;
			}
			if (valueWrapper
					.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TIMEOUT)) {
				Long tOut = (Long) valueWrapper
						.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TIMEOUT);
				timeOut = tOut == null ? 0 : tOut * 1000; // converting to
															// millis
			}
			if (wrap.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TIME_TO_LIVE)) {
				Long tOut = (Long) wrap
						.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TIME_TO_LIVE);
				ttl = tOut == null ? 0 : tOut * 1000; // converting to millis
			}
			if (wrap.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_DELIVERY_MODE)) {
				Integer dMode = (Integer) wrap
						.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_DELIVERY_MODE);
				deliveryMode = dMode;
			}

			if (wrap.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_PRIORITY)) {
				Integer pri = (Integer) wrap
						.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_PRIORITY);
				priority = pri;
			}

			if (wrap.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_ACK_MODE)) {
				Integer aMode = (Integer) wrap
						.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_ACK_MODE);
				ackMode = aMode;
			}
			
			if (wrap.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JNDI_PROPS)) {
				jndiProps = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JNDI_PROPS);
				if(jndiProps == null)
					jndiProps = new ArrayList<EObject>();
			}

		}

		Hashtable<Object, Object> env = new Hashtable<Object, Object>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, jndiProviderContextFactory);
		env.put(Context.PROVIDER_URL, jndiProviderURL);

		if (jndiUserName != null) {

			env.put(Context.SECURITY_PRINCIPAL, jndiUserName);

			if (jndiPassword != null)
				env.put(Context.SECURITY_CREDENTIALS, jndiPassword);
		}

		Iterator<EObject> iterator = jndiProps.iterator();
		while (iterator.hasNext()) {
			EObject object = (EObject) iterator.next();
			EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(object);
			String name = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_NAME);
			String type = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TYPE);
			String value = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_VALUE);
			if (value != null && !value.trim().isEmpty()) {
				if (type.equalsIgnoreCase("boolean")
						|| type.equalsIgnoreCase("java.lang.boolean")) {
					try {
						boolean parse = Boolean.parseBoolean(value);
						env.put(name, parse);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}else if (value != null && !value.trim().isEmpty()) {
				if (type.equalsIgnoreCase("short")
						|| type.equalsIgnoreCase("java.lang.short")) {
					try {
						Short parse = Short.parseShort(value);
						env.put(name, parse);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}else if (value != null && !value.trim().isEmpty()) {
				if (type.equalsIgnoreCase("integer") || type.equalsIgnoreCase("int")
						|| type.equalsIgnoreCase("java.lang.integer") ) {
					try {
						Integer parse = Integer.parseInt(value);
						env.put(name, parse);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}else if (value != null && !value.trim().isEmpty()) {
				if (type.equalsIgnoreCase("float") 
						|| type.equalsIgnoreCase("java.lang.Float") ) {
					try {
						Float parse = Float.parseFloat(value);
						env.put(name, parse);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}else if (value != null && !value.trim().isEmpty()) {
				if (type.equalsIgnoreCase("long") 
						|| type.equalsIgnoreCase("java.lang.long") ) {
					try {
						Long parse = Long.parseLong(value);
						env.put(name, parse);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}else if (value != null && !value.trim().isEmpty()) {
				if (type.equalsIgnoreCase("double") || type.equalsIgnoreCase("double")
						|| type.equalsIgnoreCase("java.lang.double") ) {
					try {
						Double parse = Double.parseDouble(value);
						env.put(name, parse);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}else if (value != null && !value.trim().isEmpty()) {
				if (type.equalsIgnoreCase("byte") || type.equalsIgnoreCase("byte")
						|| type.equalsIgnoreCase("java.lang.byte") ) {
					try {
						Byte parse = Byte.parseByte(value);
						env.put(name, parse);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}else{
				env.put(name, value);
			}
			
		}
		
		if(jmsConfig != null){
			EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(jmsConfig);
			try {
				configureSsl(wrap, env);
			} catch (Exception e) {
				m_Logger.log(
						Level.ERROR,
						"Error while configuring SSL in Web service task"+ e.getStackTrace());
			}
		}
		
		jndiContext = new InitialContext(env);

	}
	
	private void configureSsl(EObjectWrapper<EClass, EObject> jmsConfigWrapper, Hashtable<Object, Object> env ) throws Exception{
		RuleServiceProvider rsp = RuleServiceProviderManager.getInstance()
				.getDefaultProvider();
		gv = rsp.getGlobalVariables();
	    provider = rsp.getProject()
				.getSharedArchiveResourceProvider();
       if(jmsConfigWrapper.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_USE_JMS_SSL) && jmsConfigWrapper.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JMS_SSL_CONFIG)){
    	   useSsl = (Boolean)jmsConfigWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_USE_JMS_SSL);
    	   EObject sslConfig = jmsConfigWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JMS_SSL_CONFIG);
    	   if(sslConfig == null)
    		   useSsl = false;
    	   if (useSsl) {
    		   EObjectWrapper<EClass, EObject> sslConfigWrapper = EObjectWrapper.wrap(sslConfig);
               String protocol = SSL;
               env.put(SECURITY_PROTOCOL, protocol);

               env.put(Context.URL_PKG_PREFIXES, PKG_PREFIXES_NAMING);

               env.put(SSL_VENDOR, this.vendor);

               // set trace for client-side operations, loading of certificates
               boolean trace = (Boolean)sslConfigWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TRACE);
               env.put(SSL_TRACE, new Boolean(trace));

               boolean debugTrace = (Boolean)sslConfigWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_DEBUG_TRACE);
               env.put(SSL_DEBUG_TRACE, new Boolean(debugTrace));

               boolean enableVerifyHostName = (Boolean)sslConfigWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_VERIFY_HOST_NAME);
               env.put(SSL_ENABLE_VERIFY_HOSTNAME, new Boolean(enableVerifyHostName));

               String expectedHostname = (String)sslConfigWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_EXPECTED_HOSTNAME);
               if ((expectedHostname != null) && (expectedHostname.length() > 0))
                   env.put(SSL_EXPECTED_HOSTNAME, expectedHostname);

               String trustedCertsURI = (String)sslConfigWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_CERTIFICATE_FOLDER);
               if ((trustedCertsURI != null) && (trustedCertsURI.length() > 0)) {
                   final TrustedCerts trustedCerts = BETrustedCertificateManager.getInstance()
                           .getTrustedCerts(this.provider, this.gv, trustedCertsURI, true);
                   Cert[] certList = trustedCerts.getCertificateList();
                   Vector certs = new Vector();
                   for (int i = 0; i < certList.length; i++) {
                       certs.add(certList[i].getCertificate());
                   }
                   env.put(SSL_TRUSTED_CERTIFICATES, certs);
               }
               	   String idRef = (String)sslConfigWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_IDENTITY);
                   BEIdentity idObj = getIdentity(idRef);
                   if (idObj != null) {
                       setIdentityProps(idObj,env);
                   }
                   
                // get connection factory SSL password
                   admFactorySslPassword = (String)jmsConfigWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_SSL_PASSWORD);
                   if ( (admFactorySslPassword != null) && (admFactorySslPassword.length() > 0) ) {
                      try {
                          char[] decryptedvalue = ObfuscationEngine.decrypt(admFactorySslPassword);
                          admFactorySslPassword = new String(decryptedvalue);
                      } catch (Exception e) {}
                   }

                   // private key password is used if connection factory SSL password is not specified
                   if (admFactorySslPassword == null || admFactorySslPassword.length() == 0) {
                       admFactorySslPassword = (String)env.get(SSL_PASSWORD);
                   }

                   // Dummy random number generator, not secure but for testing with
                   EmsHelper.initRandomNumberGenerator();
           }
       }
       
	}
	
	private BEIdentity getIdentity(String idReference) throws Exception {
        BEIdentity idObj = null;
        if ((idReference != null) && !idReference.trim().isEmpty()) {
            if (idReference.startsWith("/")) {
                // Runtime; identity is passed as a string reference
                idObj = BEIdentityUtilities.fetchIdentity(provider, gv, idReference);
            }
            else {
                throw new Exception("Incorrect Trusted Certificate Folder string: " + idReference);
            }
        }
        return idObj;
    }

    private void setIdentityProps(BEIdentity idObj, Hashtable env) throws Exception {
        if (idObj instanceof BECertPlusKeyIdentity) {
                    BECertPlusKeyIdentity ckIdObj = (BECertPlusKeyIdentity)idObj;
                    String clientCert = ckIdObj.getCertUrl();
                    if ((clientCert != null) && (clientCert.length() > 0)) {
                        env.put(SSL_IDENTITY, clientCert);
                    }
                    String privateKey = ckIdObj.getKeyUrl();
                    if ((privateKey !=null) && (privateKey.length() > 0)) {
                        env.put(SSL_PRIVATE_KEY, privateKey);
                    }
                    String clientPkPwd = ckIdObj.getPassword();
                    if ((clientPkPwd != null) && (clientPkPwd.length() > 0)){
                        env.put(SSL_PASSWORD, clientPkPwd);
                    }
                } else if (idObj instanceof BEUrlIdentity) {
                    BEUrlIdentity ckIdObj = (BEUrlIdentity)idObj;
                    String clientCert = ckIdObj.getUrl();
                    if ((clientCert != null) && (clientCert.length() > 0)) {
                        env.put(SSL_IDENTITY, clientCert);
                    }
                    String clientPkPwd = ckIdObj.getPassword();
                    if ((clientPkPwd != null) && (clientPkPwd.length() > 0)){
                        env.put(SSL_PASSWORD, clientPkPwd);
                    }
                }else if (idObj instanceof BEKeystoreIdentity) {
                    final BEKeystoreIdentity keystoreIdentity = (BEKeystoreIdentity) idObj;
                    final String clientCert = keystoreIdentity.getStrKeystoreURL();
                    if ((clientCert != null) && (clientCert.length() > 0)) {
                        env.put(SSL_IDENTITY, clientCert);
                    }
                    String clientPkPwd = keystoreIdentity.getStrStorePassword();
                    if ((clientPkPwd != null) && (clientPkPwd.length() > 0)) {
                        if (keystoreIdentity.isPasswordObfuscated()) {
                            clientPkPwd = new String(ObfuscationEngine.decrypt(clientPkPwd));
                        }
                        env.put(SSL_PASSWORD , clientPkPwd);
                    }
                } else {
                    throw new RuntimeException("Identity type: " + idObj.getObjectType() + " is not supported.");
                }
        
        
    }

	@Override
	public XiNode sendWebServiceRequest(XiNode soapRequest) throws Exception {
		replyDestinationName = generateReplyDestinationName();
		lookUp();
		return sendMessage(soapRequest);
	}

	private void lookUp() throws Exception {
		connectionFactory = (ConnectionFactory) jndiContext
				.lookup(jndiConnectionFactoryName);
		
		if (useSsl) 
            EmsHelper.setSslPassword(connectionFactory, admFactorySslPassword);
            
		destination = (Destination) jndiContext.lookup(destinationName);
		replyDestination = null;
		if (!isInOnly && replyDestinationName != null
				&& !replyDestinationName.trim().isEmpty()){
			try {
				replyDestination = (Destination) jndiContext
						.lookup(replyDestinationName);
			} catch (NameNotFoundException e) {
				// TODO: handle exception
			}
		}
			

	}

	private XiNode sendMessage(XiNode soapRequest) throws Exception {
		Connection connection = null;
		XiNode response = null;
		Session session = null;
		MessageProducer msgProducer = null;
		MessageConsumer msgConsumer = null;
		try {

			connection = connectionFactory.createConnection(connectionUserName,
					connectionPassword);

			session = connection.createSession(false, ackMode);
			msgProducer = session.createProducer(destination);
			msgProducer.setDeliveryMode(deliveryMode);
			msgProducer.setPriority(priority);
			msgProducer.setTimeToLive(ttl);

			Message message = createMessage(session, soapRequest);
			message.setStringProperty("SOAPJMS_targetService", targetService);
			message.setStringProperty("SOAPJMS_bindingVersion", "1.0");
			message.setStringProperty("SOAPJMS_contentType", contentType);
			message.setStringProperty("Content_Type", contentType);
			message.setStringProperty("SOAPJMS_soapAction", soapAction);
			message.setStringProperty("soapAction", soapAction);
			message.setBooleanProperty("SOAPJMS_isFault", false);
			message.setStringProperty("SOAPJMS_requestURI", requestUri);
			
			if(!isInOnly && replyDestination == null){
				replyDestination = session.createQueue(replyDestinationName);
			}
			if (replyDestination != null) {
				message.setJMSReplyTo(replyDestination);
			}
			msgProducer.send(message);

			if (replyDestination != null) {
				connection.start();
				msgConsumer = session
						.createConsumer(replyDestination);
				m_Logger.log(Level.DEBUG, "Web Service Task: waiting for reponse from Service Uri :"+ soapAction +" On JMS Destination :"+destination.toString());
				Message receive = msgConsumer.receive(timeOut);
				m_Logger.log(Level.DEBUG, "Web Service Task: reponse received from Service Uri :"+ soapAction +" On JMS Destination :"+destination.toString());
				response = deserializeMessage(receive);
			}
		} finally {
			try {
				if(msgConsumer != null)
					msgConsumer.close();
				if(msgProducer != null)
					msgProducer.close();
				if(session != null)
					session.close();
				if (connection != null)
					connection.close();
			} catch (Exception e) {
				m_Logger.log(Level.ERROR,  "Web service jms send request cleanUp Error: " + e.getStackTrace());
			}
		}

		return response;
	}

	private Message createMessage(Session session, XiNode node)
			throws Exception {
		XiNode envelopeNode = null;
		if (node.getName().localName.equals("message")) {
			envelopeNode = XiChild.getChild(node, SOAP_ENVELOPE_NAME);
		}
		if (envelopeNode == null) {
			throw new Exception("Envelope not present in the message node");
		}
		Message message = null;
		if (isByteMessage) {
			message = session.createBytesMessage();
			BytesMessage bmsg = (BytesMessage) message;
			bmsg.writeBytes(toBytes(envelopeNode));

		} else {
			message = session.createTextMessage();
			((TextMessage) message).setText(XiNodeUtilities
					.toString(envelopeNode));
		}

		return message;
	}

	private XiNode deserializeMessage(Message message) throws Exception {
		if(message == null )
			return null;
		if (message instanceof TextMessage) {
			TextMessage msg = (TextMessage) message;
			byte[] bytes = msg.getText().getBytes("UTF-8");// TODO default is
															// utf-8 , should we
															// consider other
															// encoding too?
			return parseDocument(bytes);
		} else if (message instanceof BytesMessage) {
			final BytesMessage bytesMessage = (BytesMessage) message;
			byte[] buffer = new byte[1024];
			int readBytes = -1;
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			while ((readBytes = bytesMessage.readBytes(buffer)) != -1) {
				byteArrayOutputStream.write(buffer, 0, readBytes);
			}
			byteArrayOutputStream.close();
			byte[] bArray = byteArrayOutputStream.toByteArray();
			return parseDocument(bArray);
		} else {
			throw new Exception("Unsupported message type for WebService task"
					+ message.getClass());
		}
	}

	private XiNode parseDocument(byte[] bArray) throws Exception {
		InputStream is = new ByteArrayInputStream(bArray);
		is.reset();

		ExpandedName expandedName = new ExpandedName("message");
		XiNode messageNode = XiSupport.getXiFactory().createElement(
				expandedName);

		// Get an XiNode for it
		XiNode rootNode = XiNodeBuilder.parse(is);
		// cleanTextNodes is called here to be consistent with
		// and this will not allow mixed case content nodes...
		XiNodeUtilities.cleanTextNodes(rootNode);
		XiNode envelopeNode = rootNode.getRootNode().getFirstChild();

		// TODO Check that every immediate header child is namespace qualified
		SoapUtils.validateHeaders(rootNode);

		if (rootNode != null) {
			// Add this as child to main node
			messageNode.appendChild(envelopeNode);
		}
		return messageNode;
		// return node.getFirstChild();
	}

	private byte[] toBytes(XiNode xmlNode) throws Exception {
		// Never had a payload.
		if (xmlNode == null) {
			return null;
		}

		// Fetch it from the XML Node.
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		XiSerializer.serialize(xmlNode, bos, "UTF-8", true);
		bos.flush();

		byte[] bytes = bos.toByteArray();

		return bytes;
	}
	
	private static String generateReplyDestinationName() {
		return JMS_REPLY_TO+"-"+UUID.randomUUID().toString();
    }

}
