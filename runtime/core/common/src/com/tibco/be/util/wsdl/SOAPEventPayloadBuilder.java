package com.tibco.be.util.wsdl;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.xml.sax.InputSource;

import com.tibco.xml.NamespaceImporter;
import com.tibco.xml.QNameLoadSaveUtils;
import com.tibco.xml.data.primitive.DefaultNamespaceContext;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.MutableNamespaceContext;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.datamodel.nodes.Element;
import com.tibco.xml.schema.SmComponent;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmNamespace;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.build.MutableElement;
import com.tibco.xml.schema.build.MutableSchema;
import com.tibco.xml.schema.build.MutableSupport;
import com.tibco.xml.schema.build.MutableType;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.flavor.XSDLConstants;
import com.tibco.xml.schema.helpers.NoNamespace;
import com.tibco.xml.schema.impl.DefaultComponentFactory;
import com.tibco.xml.tns.parse.impl.TnsImportImpl;
import com.tibco.xml.util.XSDWriter;

	/*
	 * Generates a soap schema and provides means to add part to
	 * soap body and header.
	 */
	public class SOAPEventPayloadBuilder {
		
		public static final String MESSAGE = "message";
		public static final String ENVELOPE = "Envelope";
		public static final String HEADER = "Header";
		public static final String BODY = "Body";
		public static final String FAULT = "Fault";
		

		protected MutableSchema schema;
		MutableType headerT;
		MutableType bodyT;
		MutableType faultT;

		NamespaceImporter nsImporter;

		public SOAPEventPayloadBuilder() {
			this.schema = DefaultComponentFactory.getTnsAwareInstance().createSchema();
			schema.setNamespace(NoNamespace.URI);
			schema.setFlavor(XSDL.FLAVOR);
			MutableType msgT = MutableSupport.createType(schema, null, null);
			msgT.setComplex();
			MutableSupport.createElement(schema, MESSAGE, msgT);
			
			MutableType envT = MutableSupport.createType(schema, null, null);
			envT.setComplex();
			MutableSupport.addLocalElement(msgT, ENVELOPE, envT, 1, 1);

			headerT = MutableSupport.createType(schema, null, null);
			headerT.setComplex();
			MutableSupport.addLocalElement(envT, HEADER, headerT, 0, 1);

			bodyT = MutableSupport.createType(schema, null, null);
			bodyT.setComplex();
			MutableSupport.addLocalElement(envT, BODY, bodyT, 1, 1);

			faultT = MutableSupport.createType(schema, null, null);
			faultT.setComplex();
			MutableSupport.addLocalElement(bodyT, FAULT, faultT, 0, 1);
		}

		MutableType getHeaderType () {
			return this.headerT;
		}
		MutableType getBodyType () {
			return this.bodyT;
		}
		MutableType getFaultType () {
			return this.faultT;
		}

		public void addHeaderPart(ExpandedName nm, SmElement element) {
			addPart(headerT, nm);
		}

		public void addBodyPart(ExpandedName nm, SmElement element) {
			addPart(bodyT, nm);
		}

		public void addFaultPart(ExpandedName nm, SmElement element) {
			addPart(faultT, nm);
		}
		
		public void addHeaderPart(ExpandedName nm, SmType type) {
			addPart(headerT, nm, type);
		}

		public void addBodyPart(ExpandedName nm, SmType type) {
			addPart(bodyT, nm, type);
		}

		public void addFaultPart(ExpandedName nm, SmType type) {
			addPart(faultT, nm, type);
		}

		private void addPart(MutableType onType, ExpandedName nm) {
			MutableElement er = (MutableElement) MutableSupport.createComponentRef(schema, SmComponent.ELEMENT_TYPE);
			er.setExpandedName(nm);
			er.setReference(schema);
			schema.getSingleFragment().addImport(new TnsImportImpl(null , null, nm.namespaceURI ,SmNamespace.class));
			MutableSupport.addParticleTerm(onType, er, 0, 1);
		}
		
		private void addPart(MutableType onType, ExpandedName nm, SmType type) {
			if(nm.namespaceURI!= null && !"".equals(nm.namespaceURI)) {
				String ns = NoNamespace.normalizeNamespaceURI(nm.namespaceURI);
				schema.getSingleFragment().addImport(new TnsImportImpl(null , null, ns, SmNamespace.class));
			}
			if(type.getNamespace()!= null && !"".equals(type.getNamespace())){
				String ns = NoNamespace.normalizeNamespaceURI(type.getNamespace());
				schema.getSingleFragment().addImport(new TnsImportImpl(null , null, ns, SmNamespace.class));
			}
			MutableSupport.addLocalElement(onType, nm.getLocalName(), type, 0, 1);
		}

		/**
		 * 
		 * @return XiNode element node of soap envelp schema
		 * @throws Exception
		 */
		protected XiNode getEnvelopXiNode() throws Exception {
			String xsdStr = getAsString(schema);
			XiNode documentNode = XiParserFactory.newInstance()
			.parse(
					new InputSource(new ByteArrayInputStream(xsdStr.getBytes("UTF-8"))));
			XiNode schemaNode = XiChild.getFirstChild(documentNode);

			this.nsImporter = QNameLoadSaveUtils.readNamespaces(schemaNode);
			
			XiNode eleNode = XiChild.getChild(schemaNode, ExpandedName.makeName(
					XSDL.NAMESPACE, "element"));
			
			return eleNode;
		}
		
		public Element getPayloadElement() throws Exception {
			XiNode eleNode = getEnvelopXiNode();
			eleNode = eleNode.getParentNode().removeChild(eleNode);
			Element payloadNode = Element.createElement(ExpandedName.makeName("payload"));
			payloadNode.appendChild(eleNode);
			return payloadNode;
		}
		public NamespaceImporter getNamespaceImporter(){
			return this.nsImporter;
		}

		protected static String getAsString (MutableSchema sch) {
			String xsdStr = null;
			try {
				StringWriter stringWriter = new StringWriter();
				XSDWriter xw = new XSDWriter();
				List imports = new LinkedList();
				MutableNamespaceContext nsCtx = new DefaultNamespaceContext();
				nsCtx.add("xsd", XSDL.NAMESPACE);
				xw.setProperty(XSDWriter.PREFERRED_NAMESPACECTX, nsCtx);
				xw.setProperty(XSDWriter.NAMESPACECTX, nsCtx);

				xw.write(sch, stringWriter, imports.listIterator());

				xsdStr = stringWriter.toString();

			} catch (Exception e) {
				e.printStackTrace();
			}
			return xsdStr;
		}
		
		public static boolean validatePayload(Element payload){
			
			boolean containsBody = false;
			if(XiChild.getChildCount(payload) > 1){
				return false;
			}
			String attValue;
			ExpandedName xsdElement;
			ExpandedName attName;
			xsdElement = ExpandedName.makeName(XSDLConstants.NAMESPACE, "element");
			attName = ExpandedName.makeName("name");
			
			XiNode msgNode = XiChild.getChild(payload, xsdElement);
			if(msgNode == null){
				return false;
			}
			attValue = msgNode.getAttributeStringValue(attName);
			if(!"message".equals(attValue)){
				return false;
			}
			
			XiNode sequence = getComplexType(msgNode);
			if(sequence == null){
				return false;
			}
			XiNode envNode = XiChild.getChild(sequence, xsdElement);
			if(envNode == null){
				return false;
			}
			attValue = envNode.getAttributeStringValue(attName);
			if(!"Envelope".equals(attValue)){
				return false;
			}
			
			sequence = getComplexType(envNode);
			if(sequence == null){
				return false;
			}
			Iterator<XiNode> eleIter = XiChild.getIterator(sequence);
			while (eleIter.hasNext()) {
				XiNode xiNode = eleIter.next();
				attValue = xiNode.getAttributeStringValue(attName);
				if(attValue != null){
					if(!"Header".equals(attValue) && !"Body".equals(attValue)){
						return false;
					} else if("Body".equals(attValue) && getComplexType(xiNode) == null){
						return false;
					}
					if("Body".equals(attValue)) {
						containsBody = true;
					}
				} else {
					return false;
				}
			}
			if(containsBody) {
				return true;
			} else {
				return false;
			}
		}

		static XiNode getComplexType(XiNode element){
			ExpandedName xsdComplexType = ExpandedName.makeName(XSDLConstants.NAMESPACE, "complexType");
			ExpandedName xsdSequence = ExpandedName.makeName(XSDLConstants.NAMESPACE, "sequence");
			XiNode complexType = XiChild.getChild(element, xsdComplexType);
			if(complexType != null){
				XiNode sequence = XiChild.getChild(complexType, xsdSequence);
				return sequence;
			}
			return null;
		}
	}