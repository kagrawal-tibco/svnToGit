package com.tibco.cep.runtime.model.event.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.event.EventPayload;
import com.tibco.cep.runtime.model.event.PayloadFactory;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.util.JSONXiNodeConversionUtil;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlNode;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmModelGroup;
import com.tibco.xml.schema.SmParticle;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.schema.SmSupport;
import com.tibco.xml.schema.impl.DefaultWildcard;

/**
 * This is a factory class for creating payloads of different type. As of now,
 * we support two types of payload - XML and Object. XML payload is
 * serializable. Object payload depends on its actual raw data If the raw data
 * is serializable or externalizable, then it can be persisted, other wise, it
 * will be discarded.
 *
 * All payload follows a standard design pattern To deserialize a stream into a
 * payload use the factory methods, and to serialize onto a stream use the
 * interface (instance) method
 *
 * Example : To create a payload do the following a.> Get a RuleServiceProvider;
 * b.> Get the Payload Factory from the RuleServiceProvider; c.> Create an
 * EventPayload using the factory passing the appropriate object
 *
 * RuleSession sess = RuleSessionManager.getCurrentSession();
 * RuleServiceProvider provider = session.getRuleServiceProvider();
 * PayloadFactory factory = provider.getPayloadFactory(); EventPayload payload =
 * factory.createPayload(event.getClass(), rawData);
 *
 *
 *
 * 
 * Now to Serialize the payload back to some persistent store, the following
 * condition must be met - The underlying raw object must be serializable or
 * atleast externalizable Example : OutputStream os = new
 * ByteArrayOutputStream(); EventPayload payload = event.getPayload();
 * payload.write(outStream);
 *
 *
 *
 */
public class PayloadFactoryImpl implements PayloadFactory {

	TypeManager typeManager;
	static HashMap payloadTypeMap = new HashMap();
	static HashMap type2PayloadMap = new HashMap();
	static HashMap constructorMap = new HashMap();

	static {
		try {
			PayloadFactoryImpl.registerType(XiNodePayload.class, 1);
			PayloadFactoryImpl.registerType(ObjectPayload.class, 2);
		} catch (Exception e) {

		}
	}

	public static void registerType(Class payloadType, int typeId) throws Exception {
//    	System.err.println("## Registering type: " + typeId +" as payloadType");
		Integer l = new Integer(typeId);
		if (payloadTypeMap.get(payloadType) != null) {
//        	System.err.println("## Error Registering type: " + typeId +" as payloadType - already exists");
			return;
//        	throw new Exception("Payload Type [" + payloadType.getName() + "] already exists");
		}
		if (type2PayloadMap.get(l) != null) {
//        	System.err.println("## Error Registering PayloadtypeId: " + typeId +" as payloadTypeId - already exists");
			return;
//        	throw new Exception("Type Id [" + typeId + "] already exists");
		}

		payloadTypeMap.put(payloadType, l);
		type2PayloadMap.put(l, payloadType);
		constructorMap.put(l,
				payloadType.getConstructor(new Class[] { TypeManager.TypeDescriptor.class, byte[].class }));
	}

	public static Class getPayloadType(int typeId) {
		Integer l = new Integer(typeId);
		return (Class) type2PayloadMap.get(l);
	}

	public static int getPayloadTypeId(Class payloadType) {
		return ((Integer) payloadTypeMap.get(payloadType)).intValue();
	}

	public PayloadFactoryImpl(TypeManager typeManager) {
		this.typeManager = typeManager;

	}

	public static EventPayload createPayload(TypeManager.TypeDescriptor descriptor, int typeId, byte[] buf)
			throws Exception {
		Constructor ctor = (Constructor) constructorMap.get(new Integer(typeId));
		if (ctor == null)
			throw new Exception("Invalid typeId specified - PayloadType not registered");
		return (EventPayload) ctor.newInstance(new Object[] { descriptor, buf });
	}

	/**
	 * Construct a new Payload Object based on the entity class and String as input
	 * payload If the entityClass is of Event type, and has an XSD complex Type
	 * specified as payload input, the input payload string will be parsed into an
	 * XML document. The resultant payload in that case will be XiNodePayload, else
	 * it will be Object Payload
	 *
	 * @param entityName
	 * @param payload
	 * @return
	 */
	public EventPayload createPayload(ExpandedName entityName, String payload) throws Exception {
		TypeManager.TypeDescriptor descriptor = typeManager.getTypeDescriptor(entityName);
		SmElement element = descriptor.getSmElement();
		return createPayload(element, payload, checkForJSONPayloadConversion(descriptor));
	}

//    public EventPayload createPayload(Class entityClass, Object payload) {
//        return new ObjectPayload(payload);
//
//    }
//
	public EventPayload createPayload(ExpandedName entityName, Object payload) {
		return new ObjectPayload(payload);
	}
//    public EventPayload createPayload(Class entityClass, byte[] buf) throws Exception{
//        TypeManager.TypeDescriptor descriptor = typeManager.getTypeDescriptor(entityClass);
//        SmElement element = descriptor.getElement();
//        return createPayload(element, buf);
//    }

	public EventPayload createPayload(ExpandedName entityName, byte[] buf) throws Exception {
		TypeManager.TypeDescriptor descriptor = typeManager.getTypeDescriptor(entityName);
		SmElement element = descriptor.getSmElement();
		return createPayload(element, buf, checkForJSONPayloadConversion(descriptor));
	}

	/**
	 * Construct a new Payload Object based on the entity class and String as input
	 * payload If the entityClass is of Event type, and has an XSD complex Type
	 * specified as payload input, the input payload string will be parsed into an
	 * XML document. The resultant payload in that case will be XiNodePayload, else
	 * it will be Object Payload. If payload is json text, its converted to XiNode
	 * as set a XiNodePayload to facilitate mapper.
	 *
	 * @param entityName
	 * @param payload
	 * @param isJSONPayload
	 * @return
	 */
	public EventPayload createPayload(ExpandedName entityName, String payload, boolean isJSONPayload) throws Exception {
		TypeManager.TypeDescriptor descriptor = typeManager.getTypeDescriptor(entityName);
		SmElement element = descriptor.getSmElement();
		return createPayload(element, payload, isJSONPayload);
	}

	public EventPayload createPayload(ExpandedName entityName, byte[] buf, boolean isJSONPayload) throws Exception {
		TypeManager.TypeDescriptor descriptor = typeManager.getTypeDescriptor(entityName);
		SmElement element = descriptor.getSmElement();
		return createPayload(element, buf, isJSONPayload);
	}

//    public EventPayload createPayload(Class entityClass, XiNode node) {
//        TypeManager.TypeDescriptor descriptor = typeManager.getTypeDescriptor(entityClass);
//        SmElement element = descriptor.getElement();
//        SmParticleTerm payloadElement = getPayloadTerm(element);
//        return new XiNodePayload(payloadElement, node);
//    }

	public EventPayload createPayload(ExpandedName entityName, XiNode node) {
		TypeManager.TypeDescriptor descriptor = typeManager.getTypeDescriptor(entityName);
		SmElement element = descriptor.getSmElement();
		SmParticleTerm payloadElement = getPayloadTerm(element);
		return new XiNodePayload(payloadElement, node);
	}

	public EventPayload createPayload(SmElement element, byte[] buf) {
		SmParticleTerm payloadElement = getPayloadTerm(element);
		try {
			return new XiNodePayload(payloadElement, buf);
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	protected EventPayload createPayload(SmElement element, String payload, boolean isJSONPayload) throws Exception {
		SmParticleTerm payTerm = getPayloadTerm(element);
		if (payTerm == null)
			return new ObjectPayload(payload);
		else {
			if (isJSONPayload) {
				String xmlPayload = (String) JSONXiNodeConversionUtil.convertJSONToXiNode(payload, payTerm);
				if (xmlPayload != null && !xmlPayload.isEmpty())
					payload = xmlPayload;
			}
			XiNodePayload xiNodePayload = new XiNodePayload(payTerm, payload);
			xiNodePayload.setJSONPayload(isJSONPayload);

			// We now need to validate the payload namespace since the XiNodePayload is an
			// untyped tree.
			// String nsPT = payTerm.getNamespace();
			if (validPayloadNamespace(payTerm, xiNodePayload))
				return xiNodePayload;
			else
				throw new RuntimeException("Event payload data does not have expected namespace. Payload data: \""
						+ payload + "\" expected namespace: \"" + payTerm.getNamespace()
						+ "\"\n Event payload will not set.");
		}
	}

	protected EventPayload createPayload(SmElement element, byte[] buf, boolean isJSONPayload) throws Exception {
		SmParticleTerm payTerm = getPayloadTerm(element);
		if (payTerm == null)
			return new ObjectPayload(buf);
		else {
			// if JSON, convert JSON to xml and continue the XiNode route
			// this helps to use json based payload in mapper
			if (isJSONPayload) {
				byte[] xmlBuf = (byte[]) JSONXiNodeConversionUtil.convertJSONToXiNode(buf, payTerm);
				if (xmlBuf != null)
					buf = xmlBuf;
			}
			XiNodePayload xiNodePayload = new XiNodePayload(payTerm, buf);
			xiNodePayload.setJSONPayload(isJSONPayload);

			// We now need to validate the payload namespace since the XiNodePayload is an
			// untyped tree.
			// String nsPT = payTerm.getNamespace();
			// TODO: need to know how to validate "Any Element" or "Any Attribute" when the
			// level of validation is "strict" or "Lax"
			if (validPayloadNamespace(payTerm, xiNodePayload))
				return xiNodePayload;
			else
				throw new RuntimeException("Event payload data does not have expected namespace. Payload data: \""
						+ new String(buf) + "\" expected namespace: \"" + payTerm.getNamespace() + "\"");
		}
	}

	public SmParticleTerm getPayloadElement(ExpandedName entityName) {
		TypeManager.TypeDescriptor descriptor = typeManager.getTypeDescriptor(entityName);
		// Modified by Anand to fix BE-10879
		if (descriptor == null) {
			return null;
		}
		return getPayloadTerm(descriptor.getSmElement());
	}

	public static SmParticleTerm getPayloadTerm(SmElement type) {
		if (type == null) {
			return null;
		}
		SmElement ele = SmSupport.getElementInContext(type.getType(), null, EventPayload.PAYLOAD_PROPERTY);
		if (ele == null)
			return null;

		SmModelGroup grp = ele.getType().getContentModel();
		Iterator r = grp.getParticles();
		while (r.hasNext()) {
			SmParticle particle = (SmParticle) r.next();
			return particle.getTerm();
		}

		return ele;

	}

	protected boolean validPayloadNamespace(SmParticleTerm payTerm, XiNodePayload xiNodePayload) throws Exception {
		/*
		 * if (payTerm instanceof DefaultWildcard) { int processContents =
		 * ((DefaultWildcard)payTerm).getProcessContents(); switch (processContents) {
		 * case SmWildcard.SKIP: return true; // skip validation case SmWildcard.STRICT:
		 * case SmWildcard.LAX: // what to do? return true for now return true; default:
		 * throw new RuntimeException("Not supported validation for wildcard type."); }
		 * } else { String nsPT = payTerm.getNamespace(); Iterator r =
		 * xiNodePayload.getNode().getNamespaces(); if (nsPT != null &&
		 * !nsPT.equals("")) { // event payload has namespace while (r.hasNext()) {
		 * String nsPL = ((XmlNode)r.next()).getStringValue(); if (nsPL == nsPT) return
		 * true; } return false; } else { // event payload does not have namespace int
		 * numNS = 0; while (r.hasNext()) { String nsPL =
		 * ((XmlNode)r.next()).getStringValue(); if (nsPL != null && !nsPL.equals(""))
		 * numNS++; } return numNS == 0 ? true : false; } }
		 */
		if (payTerm instanceof DefaultWildcard) {
			return true; // we don't do any validation for Any Element and Any Attribute
		} else {
			final String expectedNS = payTerm.getNamespace();
			if ((expectedNS == null) || (expectedNS.equals(""))) {
				return true; // we don't do any validation if event payload does not use namespace
			} else {
				final XiNode receivedNode = xiNodePayload.getNode();
				String prefix = receivedNode.getPrefix();
				if (prefix == null || prefix.length() == 0) { // can not use xmlNode.getNamespaceURIForPrefix(prefix)
					Iterator r = receivedNode.getNamespaces();
					while (r.hasNext()) {
						String receivedNS = ((XmlNode) r.next()).getStringValue();
						if (expectedNS.equals(receivedNS))
							return true;
					}
					String type = receivedNode.getAttributeStringValue(ExpandedName.makeName("type"));
					if (type != null && !type.isEmpty() && expectedNS.equals(type))
						return true;

					return false;
				} else
					return expectedNS.equals(receivedNode.getNamespaceURIForPrefix(prefix));
			}
		}
	}

	/**
	 * Check if the event is tied to an destination marked for JSON payload
	 * 
	 * @param eventUri
	 * @return
	 * @throws Exception
	 */
	private boolean checkForJSONPayloadConversion(TypeManager.TypeDescriptor descriptor) throws Exception {
		Entity entity = (Entity) descriptor.getImplClass().newInstance();
		if (entity instanceof SimpleEvent) {
			SimpleEvent se = (SimpleEvent) entity;
			RuleServiceProvider RSP = RuleServiceProviderManager.getInstance().getDefaultProvider();
			Channel.Destination dest = RSP.getChannelManager().getDestination(se.getDestinationURI());
			if (dest != null) {
				String channelType = dest.getConfig().getChannelConfig().getType();

				if (!("HTTP".equals(channelType) || "JMS".equals(channelType) || "Kafka".equals(channelType)
						|| "Kafka Streams".equals(channelType))) {
					return false;
				}
				Method isJSONPayloadMethod = null;
				try {
					isJSONPayloadMethod = dest.getClass().getMethod("isJSONPayload");
				} catch (NoSuchMethodException noSuchMethodException) {
					return false;
				}
				if (isJSONPayloadMethod != null) {
					se = null;
					return (Boolean) isJSONPayloadMethod.invoke(dest);
				}
			}
		}

		return false;
	}
}
