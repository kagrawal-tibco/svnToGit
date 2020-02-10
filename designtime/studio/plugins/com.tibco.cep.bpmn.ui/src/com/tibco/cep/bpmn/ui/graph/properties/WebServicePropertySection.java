package com.tibco.cep.bpmn.ui.graph.properties;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.model.rdf.RDFTnsFlavor;
import com.tibco.be.util.wsdl.SOAPEventPayloadBuilder;
import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.core.wsdl.WsdlWrapper;
import com.tibco.cep.bpmn.core.wsdl.WsdlWrapperFactory;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmComponent;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmModelGroup;
import com.tibco.xml.schema.SmNamespace;
import com.tibco.xml.schema.SmParticle;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.build.MutableComponentFactoryTNS;
import com.tibco.xml.schema.build.MutableElement;
import com.tibco.xml.schema.build.MutableSchema;
import com.tibco.xml.schema.build.MutableSupport;
import com.tibco.xml.schema.build.MutableType;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.helpers.NoNamespace;
import com.tibco.xml.schema.impl.DefaultComponentFactory;
import com.tibco.xml.tns.parse.TnsFragment;
import com.tibco.xml.tns.parse.TnsImport;
import com.tibco.xml.tns.parse.impl.TnsImportImpl;
import com.tibco.xml.ws.wsdl.WsExtensionElement;
import com.tibco.xml.ws.wsdl.WsMessage;
import com.tibco.xml.ws.wsdl.WsMessageKind;
import com.tibco.xml.ws.wsdl.WsMessagePart;
import com.tibco.xml.ws.wsdl.WsOperationMessage;
import com.tibco.xml.ws.wsdl.WsOperationMessageReference;
import com.tibco.xml.ws.wsdl.WsOperationReference;
import com.tibco.xml.ws.wsdl.ext.mime.WsMimeMultipartRelated;
import com.tibco.xml.ws.wsdl.ext.mime.WsMimePart;
import com.tibco.xml.ws.wsdl.ext.soap11.WsSoapBody;
import com.tibco.xml.ws.wsdl.ext.soap11.WsSoapHeader;
import com.tibco.xml.ws.wsdl.ext.soap12.WsSoapBody_12;
import com.tibco.xml.ws.wsdl.ext.soap12.WsSoapHeader_12;

/**
 * 
 * @author majha
 *
 */

public abstract class WebServicePropertySection extends MapperPropertySection {
	private final static MutableComponentFactoryTNS mcf = DefaultComponentFactory.getTnsAwareInstance();

	public WebServicePropertySection() {
		super();
		 
	}
	protected boolean containsLoop ;
	protected SmElement buildOutputSoapSchema(
			EObjectWrapper<EClass, EObject> webServiceTask) {
		MutableElement createSoapSchema = null;
		WsSchemaBuilder schemaBuilder = new WsSchemaBuilder();
		String attachedResource = (String) BpmnModelUtils
				.getAttachedResource(webServiceTask.getEInstance());
		if (attachedResource != null && !attachedResource.trim().isEmpty()) {
			WsdlWrapper wsdl = WsdlWrapperFactory.getWsdl(fProject,
					attachedResource);
			if (wsdl != null) {
				EObjectWrapper<EClass, EObject> addDataExtensionValueWrapper = ExtensionHelper
						.getAddDataExtensionValueWrapper(webServiceTask);
				if (addDataExtensionValueWrapper != null) {
					String operationName = addDataExtensionValueWrapper
							.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_OPERATION);
					String service = addDataExtensionValueWrapper
							.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_SERVICE);
					String port = addDataExtensionValueWrapper
							.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_PORT);
					WsOperationReference operationRef = wsdl.getOperationRef(
							port, service, operationName);
					if (operationRef != null) {
						try {
							if(operationRef.getOperation()!=null && operationRef.getOperation()
									.getMessages(WsMessageKind.OUTPUT).length >0){
								buildSoapSchema(schemaBuilder, operationRef,
										WsMessageKind.OUTPUT);
								MutableElement payloadElement = (MutableElement) schemaBuilder
										.getSchema()
										.getComponent(SmComponent.ELEMENT_TYPE,
												"message");
								MutableSchema schema = getSchema(RDFTnsFlavor.SOAP_NAMESPACE);
								createSoapSchema = createSoapSchema(schema,
										payloadElement, WsMessageKind.OUTPUT);
								getMapper().wsdlWrapper = wsdl ;
							}
						} catch (Exception e) {
							BpmnUIPlugin.log(e);
						}

					}
				}
			}
		}
		return createSoapSchema;
	}

	
	protected void buildInputSoapSchema(
			EObjectWrapper<EClass, EObject> webServiceTask) {
		WsSchemaBuilder schemaBuilder = new WsSchemaBuilder();
		String attachedResource = (String) BpmnModelUtils
				.getAttachedResource(webServiceTask.getEInstance());
		currentXslt = getInputMapperXslt();

		if (attachedResource != null && !attachedResource.trim().isEmpty()) {
			WsdlWrapper wsdl = WsdlWrapperFactory.getWsdl(fProject,
					attachedResource);
			if (wsdl != null) {
				EObjectWrapper<EClass, EObject> addDataExtensionValueWrapper = ExtensionHelper
						.getAddDataExtensionValueWrapper(webServiceTask);
				if (addDataExtensionValueWrapper != null) {
					String operationName = addDataExtensionValueWrapper
							.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_OPERATION);
					String service = addDataExtensionValueWrapper
							.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_SERVICE);
					String port = addDataExtensionValueWrapper
							.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_PORT);
					WsOperationReference operationRef = wsdl.getOperationRef(
							port, service, operationName);
					if (operationRef != null) {
						try {
							buildSoapSchema(schemaBuilder, operationRef,
									WsMessageKind.INPUT);
							MutableElement payloadElement = (MutableElement) schemaBuilder
									.getSchema()
									.getComponent(SmComponent.ELEMENT_TYPE,
											"message");
							MutableSchema schema = getSchema(RDFTnsFlavor.SOAP_NAMESPACE);

							MutableElement createSoapSchema = createSoapSchema(
									schema, payloadElement, WsMessageKind.INPUT);

							getMapper().wsdlWrapper = wsdl ;
							getMapper().buildWsdlElement(attachedResource,
									createSoapSchema, wsdl.getWsdlName(),
									currentXslt);
						} catch (Exception e) {
							BpmnUIPlugin.log(e);
							setNullMapper();
						}

					}
				}

			}
		}
		return;
	}
	
    MutableSchema getSchema(String ns) {

        /**
         MutableSchema cached=(MutableSchema) schemaCache.get(ns);
         **/

        //if (cached == null) {
        MutableSchema cached;
        cached= mcf.createSchema();
        cached.setNamespace(ns);
        cached.setFlavor(XSDL.FLAVOR);
        //schemaCache.put(ns, cached);
        //}
        return cached;
    }
	
	private void buildSoapSchema(WsSchemaBuilder builder,
			WsOperationReference opRef, WsMessageKind kind) throws Exception {
		List<?> opMsgRefList = getOperationMessage(
				opRef, kind);
		WsOperationMessage opMsg = null;
		if (opMsgRefList.size() > 0) {
			// for input and output message there will be only one msg
			// reference, hence using index 0
			opMsg = (WsOperationMessage)((WsOperationMessageReference)opMsgRefList.get(0)).getMessage();
		} else {
			WsOperationMessage[] opMsgs = opRef.getOperation()
					.getMessages(kind);
			if (opMsgs.length > 0) {
				opMsg = opMsgs[0];
		}
		}

		if (opMsg == null) {
			return ;
		}

		if (opMsgRefList.size() > 0) {
			// Add message parts to payload schema
			handleWsOperationMessageReference((WsOperationMessageReference)opMsgRefList.get(0), builder);
		} else if (opMsg != null) {
			Iterator<?> msgPartIter = opMsg.getMessage()
					.getMessageParts();
			while (msgPartIter.hasNext()) {
				addMsgAsBodyPart((WsMessagePart)msgPartIter.next(), builder);
			}
		}

		// add fault message parts to payload
		if (kind == WsMessageKind.OUTPUT) {
			List<WsOperationMessageReference> opFaultMsgRefList = getOperationMessage(
					opRef, WsMessageKind.FAULT);
			if (opFaultMsgRefList.size() > 0) {
				for (WsOperationMessageReference opFaultMsgRef : opFaultMsgRefList) {
					ExpandedName faultMsgName = opFaultMsgRef.getName();
					WsOperationMessage opFaultMsg = opRef.getOperation()
							.getMessage(faultMsgName);
					Iterator<?> msgPartIter = opFaultMsg
							.getMessage().getMessageParts();
					while (msgPartIter.hasNext()) {
						addMsgAsFaultPart((WsMessagePart) msgPartIter.next(), builder);
					}
				}

			} else {
				WsOperationMessage[] opMsgs = opRef.getOperation().getMessages(
						WsMessageKind.FAULT);
				for (int i = 0; i < opMsgs.length; i++) {
					WsOperationMessage opFaultMsg = opMsgs[i];
					Iterator<?> msgPartIter = opFaultMsg
							.getMessage().getMessageParts();
					while (msgPartIter.hasNext()) {
						addMsgAsFaultPart((WsMessagePart) msgPartIter.next(), builder);
					}
				}
			}
		}

		return;
	}
	
	protected MutableElement createSoapSchema(MutableSchema schema, MutableElement ele, WsMessageKind kind) {

//		MutableSchema schema = DefaultComponentFactory
//				.getTnsAwareInstance().createSchema();
//		schema.setNamespace(RDFTnsFlavor.SOAP_NAMESPACE);
//		schema.setFlavor(XSDL.FLAVOR);
		
		MutableType msgT = null;
		MutableType envT = null;
		Iterator<?> headerParts = null;
		Iterator<?> bodyParts = null;
		Iterator<?> faultParts = null;
		MutableElement createElement = null;

		SmParticle envP = null;
		SmParticle bodyP = null;
		SmParticle faultP = null;

		try {
			msgT = (MutableType) ele.getType();
			envP = getParticleByName(ele, "Envelope");
			envT = (MutableType) ((SmElement) envP.getTerm()).getType();

			Iterator<?> rootParts = envT.getContentModel().getParticles();
			while (rootParts.hasNext()) {
				SmParticle p = (SmParticle) rootParts.next();
				if (p.getTerm().getName().equalsIgnoreCase("Header")) {
					headerParts = getParticleParts(p);
				} else if (p.getTerm().getName().equalsIgnoreCase("Body")) {
					bodyP = p;
					bodyParts = getParticleParts(p);
					faultP = getParticleByName(
							(MutableElement) bodyP.getTerm(), "Fault");
					faultParts = getParticleParts(faultP);
				}
			}

			msgT = MutableSupport.createType(schema, null, null);
			msgT.setComplex();
			createElement = MutableSupport.createElement(schema, "message", msgT);

			ExpandedName envName = ExpandedName.makeName(RDFTnsFlavor.SOAP_NAMESPACE,
					"Envelope");
			envT = createSmType(envName, schema);
			schema.addSchemaComponent(envT);
			MutableElement envE = createSmElement(envName, envT);
			if (containsLoop) {
				MutableSupport.addElement(msgT, envE, 0, Integer.MAX_VALUE);
			}
			else {
				MutableSupport.addElement(msgT, envE, 0, 1);
			}

			if (headerParts != null) {
				ExpandedName headerName = ExpandedName.makeName(RDFTnsFlavor.SOAP_NAMESPACE,
						"Header");
				MutableType headT = createSmType(headerName, schema);
				schema.addSchemaComponent(headT);
				MutableElement headerE = createSmElement(headerName, headT);
				MutableSupport.addElement(envT, headerE, 0, 1);
				while (headerParts.hasNext()) {
					SmParticle p = (SmParticle) headerParts.next();
					ExpandedName expandedName = getExpandedName(p);
					MutableType heapPType = createSmType(expandedName, schema);
					MutableElement headerPElement = createSmElement(expandedName, heapPType);
					MutableSupport.addElement(headT, headerPElement, 0, 1);
					addSoapHeaderAttribs(heapPType);
					Iterator<?> particleParts = getParticleParts(p);
					if(particleParts != null){
						while (particleParts.hasNext()) {
							SmParticle child = (SmParticle) particleParts.next();
							addNsToType(schema, heapPType, child, schema.getNamespaceURI()
									+ "/header");
						}
					}
				}
			}

			ExpandedName bodyName = ExpandedName.makeName(RDFTnsFlavor.SOAP_NAMESPACE,
					"Body");
			MutableType bodyT = createSmType(bodyName, schema);
			schema.addSchemaComponent(bodyT);
			MutableElement bodyE = createSmElement(bodyName, bodyT);
			MutableSupport.addElement(envT, bodyE, 0, 1);
			if (bodyParts != null) {
				while (bodyParts.hasNext()) {
					SmParticle p = (SmParticle) bodyParts.next();
					if (p.getTerm().getName().equalsIgnoreCase("Fault")) {
						continue;
					}
					addNsToType(schema, bodyT, p, schema.getNamespaceURI()
							+ "/body");
				}
			}

			if (faultP != null && kind == WsMessageKind.OUTPUT) {
				createFaultType(schema, bodyT, faultParts);
			}
			
			MutableType dummyT = createSmType(ExpandedName.makeName(RDFTnsFlavor.SOAP_NAMESPACE,"hack"), schema);
			schema.addSchemaComponent(dummyT);
			
		} catch (Exception e) {
			BpmnUIPlugin.log(e);
		}
		return createElement;

	}
	
	
		
    private MutableType createFaultType(MutableSchema schema, MutableType onType, Iterator<?> faultParts) {
    	ExpandedName faultName = ExpandedName.makeName(RDFTnsFlavor.SOAP_NAMESPACE, "Fault");
    	MutableType faultT = createSmType(faultName, schema);
    	schema.addSchemaComponent(faultT);
    	MutableElement faultE = createSmElement(faultName, faultT);
    	MutableSupport.addElement(onType, faultE, 0, 1);
        MutableSupport.addRequiredLocalElement(faultT, "faultcode", XSDL.STRING);
        MutableSupport.addRequiredLocalElement(faultT, "faultstring", XSDL.STRING);
        MutableSupport.addOptionalLocalElement(faultT, "faultactor", XSDL.URI_REFERENCE);
     
        if (faultParts != null) {
        	ExpandedName detailName = ExpandedName.makeName("detail");
            MutableType detailT = createSmType(detailName, schema);
            MutableElement detailE = createSmElement(detailName, detailT);
            MutableSupport.addElement(faultT, detailE, 0, 1);
            while (faultParts.hasNext()) {
                SmParticle p = (SmParticle) faultParts.next();
                addNsToType(schema, detailT, p, schema.getNamespaceURI()+"/fault");
            }
        }
        return faultT;
    }
    
    private void addNsToType (MutableSchema schema,
    		MutableType onType,
    		SmParticle p,
    		String nameSpaceUri) {
    	SmParticleTerm pTerm = p.getTerm();
    	if(!(pTerm instanceof SmElement)){ //Supporting Element type only
    		return;
    	}
    	int min = p.getMinOccurrence();
    	int max = p.getMaxOccurrence();
    	SmElement element = (SmElement) pTerm;
    	SmType type = element.getType();
    	if (type == null) {// Element referenced from schema
    		ExpandedName makeName = ExpandedName.makeName( element.getNamespace(), element.getName());
    		SmElement ele = (SmElement)mctx.getBEProject()
					.getSmElement(makeName);
			ExpandedName en = element.getExpandedName();
			// MutableComponent c = createComponentRef(schema, en,
			// SmComponent.ELEMENT_TYPE);
			MutableSupport.addElement(onType, (MutableElement) ele, min, max);

			schema.getSingleFragment().addImport(
					new TnsImportImpl(null, null, en.namespaceURI,
							SmNamespace.class));
		} else {//Allow for in place schema elements
    		MutableType mutableType = null;
    		String localName = pTerm.getExpandedName().localName;
    		ExpandedName en = type.getExpandedName();
    		if(en != null && en.namespaceURI != null && type.getAtomicType() == null){
    			// This is a referenced type
    			mutableType = MutableSupport.createType(schema, null, type);
    			mutableType.setExpandedName(en);
    			mutableType.setDerivationMethod(SmComponent.EXTENSION);
    			mutableType.setComplex();
    			ExpandedName expName = ExpandedName.makeName(nameSpaceUri, localName);
            	MutableElement newElement = createSmElement(expName, mutableType);
            	MutableSupport.addElement(onType, newElement, min, max);
//        		MutableSupport.addLocalElement(onType, localName, mutableType, min, max);
        		schema.getSingleFragment().addImport(new TnsImportImpl(null, null, en.namespaceURI, SmNamespace.class));
    		} else {// This is a local type
    			ExpandedName expName = ExpandedName.makeName(nameSpaceUri, localName);
            	MutableElement newElement = createSmElement(expName, type);
    			MutableSupport.addElement(onType, newElement, min, max);
    			
    		}
    	}
    	
    	
    }
    
    private static void addSoapHeaderAttribs(MutableType onType) {
    	MutableSupport.addAttribute(onType, "mustUnderstand", XSDL.INT, false);
    	MutableSupport.addAttribute(onType, "actor", XSDL.STRING, false);
    }
    
    protected void addImports(TnsFragment schemaFragment,
    		Iterator<?> importNodes) {
    	while (importNodes.hasNext()) {
    		TnsImport node = (TnsImport) importNodes.next();
    		TnsImport imprt = new TnsImportImpl(node.getLocation(), node.getXmlBase(), node.getNamespaceURI(), node.getNamespaceType());
			schemaFragment.addImport(imprt);
    	}
    }
    
//    private MutableComponent createComponentRef(MutableSchema schema, ExpandedName en, int type){
//		MutableComponent mutableComponent =
//			MutableSupport.createComponentRef(schema, type);
//		mutableComponent.setReference(schema);
//		mutableComponent.setExpandedName(en);
//		return mutableComponent;
//    }
	

    MutableType createSmType(ExpandedName typeName, MutableSchema schema) {
        MutableType smType = mcf.createType();
        smType.setExpandedName(typeName);
        smType.setSchema(schema);
        smType.setComplex();
        return smType;
    }
    
    MutableElement createSmElement(ExpandedName typeName, SmType smType){
    	MutableElement elem = mcf.createElement();
        elem.setType(smType);
        elem.setSchema(smType.getSchema());
        elem.setExpandedName(typeName);
        return elem;
    }
	

    /*
     * Get Iterator over SmParticle of a type
     */
    private Iterator<?> getParticleParts(SmParticle p) {
        Iterator<?> partsI = null;
        if (p != null) {
            MutableElement element = (MutableElement) p.getTerm();
            if (element != null) {
                MutableType elementType = (MutableType) element.getType();
				if (elementType != null) {
					SmModelGroup smModelGroup = elementType.getContentModel();
					// This will be null for simple types
					if (smModelGroup != null) {
						return smModelGroup.getParticles();
					}
                }else{
                	ExpandedName makeName = ExpandedName.makeName( element.getNamespace(), element.getName());
            		SmElement ele = (SmElement)mctx.getBEProject()
        					.getSmElement(makeName);
            		SmType type = ele.getType();
            		if (type != null) {
    					SmModelGroup smModelGroup = type.getContentModel();
    					// This will be null for simple types
    					if (smModelGroup != null) {
    						return smModelGroup.getParticles();
    					}
                    }
            		
                }
            }
        }
        return partsI;
    }
    
    private ExpandedName getExpandedName(SmParticle p) {
        ExpandedName exName = null;
        if (p != null) {
            MutableElement element = (MutableElement) p.getTerm();
            if (element != null) {
                MutableType elementType = (MutableType) element.getType();
				if (elementType != null) {
					exName = element.getExpandedName();
                }else{
                	ExpandedName makeName = ExpandedName.makeName( element.getNamespace(), element.getName());
            		SmElement ele = (SmElement)mctx.getBEProject()
        					.getSmElement(makeName);
            		exName = ele.getExpandedName();
            		
                }
            }
        }
        return exName;
    }

    /*
     * Finds a particles by name in an Element
     */
    private SmParticle getParticleByName(MutableElement p, String termName){
        if (p != null) {
        	MutableElement element = p;
            if (element != null) {
                MutableType elementType = (MutableType) element.getType();
                Iterator<?> partsI = elementType.getContentModel().getParticles();
                while (partsI.hasNext()) {
                    SmParticle particle = (SmParticle) partsI.next();
                    if(particle.getTerm().getName().equalsIgnoreCase(termName) && NoNamespace.URI == particle.getTerm().getNamespace()){
                        return particle;
                    }
                }
            }
        }
        return null;
    }
	
	private List<WsOperationMessageReference> getOperationMessage(WsOperationReference opRef,
			WsMessageKind kind) {
		List<WsOperationMessageReference> list = new ArrayList<WsOperationMessageReference>();
		Iterator<?> iter = opRef.getMessages();
		while (iter.hasNext()) {
			WsOperationMessageReference wsOpMsgRef = (WsOperationMessageReference)iter.next();
			if (wsOpMsgRef.getMessageKind() == kind) {
				list.add(wsOpMsgRef);
			}
		}
		return list;
	}

	
	private void handleWsOperationMessageReference(
			WsOperationMessageReference opMsgRef,
			SOAPEventPayloadBuilder builder) throws Exception {
		WsMessage msg = opMsgRef.getMessage().getMessage();
		Iterator<?> opMsgRefExtnIter = opMsgRef
				.getExtensionElements();
		while (opMsgRefExtnIter.hasNext()) {
			WsExtensionElement wsExtensionElement = (WsExtensionElement) opMsgRefExtnIter
					.next();
			if (wsExtensionElement instanceof WsSoapBody) {
				WsSoapBody soapBody = (WsSoapBody) wsExtensionElement;
				addSoapBodyPartsToPayload(soapBody, msg, builder);
			} else if ( wsExtensionElement instanceof WsSoapBody_12) {
				WsSoapBody_12 soapBody = (WsSoapBody_12) wsExtensionElement;
				addSoapBodyPartsToPayload(soapBody, msg, builder);
			} else if (wsExtensionElement instanceof WsSoapHeader) {
				WsSoapHeader soapHeader = (WsSoapHeader) wsExtensionElement;
				ExpandedName msgName = soapHeader.getMessage();
				if (msgName != null) {
					String part = soapHeader.getPart();
					if (part != null) {
						WsMessage message = opMsgRef.getWsdl().getMessage(
								msgName);
						WsMessagePart wsMsgPart = getMessagePart(message, part);
						addMsgAsHeaderPart(wsMsgPart, builder);
					} else {
						Iterator<?> msgPartIter = msg
								.getMessageParts();
						while (msgPartIter.hasNext()) {
							addMsgAsHeaderPart((WsMessagePart)msgPartIter.next(), builder);
						}
					}
				}
			} else if (wsExtensionElement instanceof WsSoapHeader_12) {
				WsSoapHeader_12 soapHeader = (WsSoapHeader_12) wsExtensionElement;
				ExpandedName msgName = soapHeader.getMessage();
				if (msgName != null) {
					String part = soapHeader.getPart();
					if (part != null) {
						WsMessage message = opMsgRef.getWsdl().getMessage(
								msgName);
						WsMessagePart wsMsgPart = getMessagePart(message, part);
						addMsgAsHeaderPart(wsMsgPart, builder);
					} else {
						Iterator<?> msgPartIter = msg
								.getMessageParts();
						while (msgPartIter.hasNext()) {
							addMsgAsHeaderPart((WsMessagePart)msgPartIter.next(), builder);
						}
					}
				}
			} else if (wsExtensionElement instanceof WsMimeMultipartRelated) {
				WsMimeMultipartRelated mimeMultipart = (WsMimeMultipartRelated) wsExtensionElement;
				Iterator<?> iter = mimeMultipart.getParts();
				while (iter.hasNext()) {
					WsMimePart wsMimePart = (WsMimePart) iter.next();
					Iterator<?> iterSoapBodies = wsMimePart
							.getSoapBodies();
					if (iterSoapBodies.hasNext()) {
						WsSoapBody soapBody = (WsSoapBody)iterSoapBodies.next();
						addSoapBodyPartsToPayload(soapBody, msg, builder);
					} else {
						continue;
					}
				}
			}
		}
	}
	
	private void addMsgAsBodyPart(WsMessagePart wsMsgPart,
			SOAPEventPayloadBuilder builder) {
		if (wsMsgPart.getElement() != null) {
			builder.addBodyPart(wsMsgPart.getElementName(), wsMsgPart
					.getElement());
		} else if (wsMsgPart.getType() != null) {
			builder.addBodyPart(wsMsgPart.getName(), wsMsgPart.getType());
		}
	}
	
	private void addMsgAsFaultPart(WsMessagePart wsMsgPart,
			SOAPEventPayloadBuilder builder) {
		if (wsMsgPart.getElement() != null) {
			builder.addFaultPart(wsMsgPart.getElementName(), wsMsgPart
					.getElement());
		} else if (wsMsgPart.getType() != null) {
			builder.addFaultPart(wsMsgPart.getName(), wsMsgPart.getType());
		}
	}

	private void addMsgAsHeaderPart(WsMessagePart wsMsgPart,
			SOAPEventPayloadBuilder builder) {
		if (wsMsgPart.getElement() != null) {
			builder.addHeaderPart(wsMsgPart.getElementName(), wsMsgPart
					.getElement());
		} else if (wsMsgPart.getType() != null) {
			builder.addHeaderPart(wsMsgPart.getName(), wsMsgPart.getType());
		}
	}

	private void addSoapBodyPartsToPayload(WsSoapBody soapBody, WsMessage msg,
			SOAPEventPayloadBuilder builder) throws Exception {
		String[] parts = soapBody.getParts();
		
		if (parts != null) {
			for (String part : parts) {
				WsMessagePart wsMsgPart = getMessagePart(msg, part);
				addMsgAsBodyPart(wsMsgPart, builder);
			}
		} else {
			Iterator<?> msgPartIter = msg.getMessageParts();
			while (msgPartIter.hasNext()) {
				addMsgAsBodyPart((WsMessagePart)msgPartIter.next(), builder);
			}
		}
	}
	
	private void addSoapBodyPartsToPayload(WsSoapBody_12 soapBody, WsMessage msg,
			SOAPEventPayloadBuilder builder) throws Exception {
		String[] parts = soapBody.getParts();
		if (parts != null) {
			for (String part : parts) {
				WsMessagePart wsMsgPart = getMessagePart(msg, part);
				if (wsMsgPart != null)
					addMsgAsBodyPart(wsMsgPart, builder);
			}
		} else {
			Iterator<?> msgPartIter = msg.getMessageParts();
			while (msgPartIter.hasNext()) {
				addMsgAsBodyPart((WsMessagePart)msgPartIter.next(), builder);
			}
		}
	}
	
	private WsMessagePart getMessagePart(WsMessage message, String partName) {
		Iterator<?> iter = message.getMessageParts();
		while (iter.hasNext()) {
			WsMessagePart wsMessagePart = (WsMessagePart) iter.next();
			String name = wsMessagePart.getName().getLocalName();
			if (name != null && name.equals(partName)) {
				return wsMessagePart;
			}
		}
		return null;
	}

	private class WsSchemaBuilder extends SOAPEventPayloadBuilder{
		
		public WsSchemaBuilder() {
			super();
		}
		
		
		public MutableSchema getSchema(){
			return schema;
		}
		
//		public String getSchemaAsString(){
//			return getAsString(getSchema());
//		}
//		
//		public String getSchemaAsString(MutableSchema schema){
//			return getAsString(schema);
//		}
	}

}
