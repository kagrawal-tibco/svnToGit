package com.tibco.cep.studio.core.rdf;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.xml.sax.InputSource;

import com.tibco.be.model.rdf.RDFTnsFlavor;
import com.tibco.be.model.rdf.RDFTnsHelper;
import com.tibco.be.model.rdf.TnsEntityHandler;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.model.AddOnRegistry;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.IAddOnLoader;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.registry.AddOn;
import com.tibco.cep.designtime.model.registry.AddOnType;
import com.tibco.cep.designtime.model.registry.FileExtensionTypes;
import com.tibco.cep.runtime.channel.PayloadValidationHelper;
import com.tibco.cep.studio.common.util.Path;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.util.PlatformUtil;
import com.tibco.io.xml.XMLStreamReader;
import com.tibco.sax.ResolverUtilities;
import com.tibco.xml.ImportRegistry;
import com.tibco.xml.NamespaceImporter;
import com.tibco.xml.QNameLoadSaveUtils;
import com.tibco.xml.data.primitive.DefaultNamespaceContext;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.helpers.XiAttribute;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.schema.SmComponent;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmModelGroup;
import com.tibco.xml.schema.SmNamespace;
import com.tibco.xml.schema.SmParticle;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.build.MutableElement;
import com.tibco.xml.schema.build.MutableSchema;
import com.tibco.xml.schema.build.MutableSupport;
import com.tibco.xml.schema.build.MutableType;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.helpers.NoNamespace;
import com.tibco.xml.schema.impl.DefaultElement;
import com.tibco.xml.schema.impl.DefaultMetaForeignAttribute;
import com.tibco.xml.schema.impl.DefaultParticle;
import com.tibco.xml.schema.impl.DefaultSchema;
import com.tibco.xml.schema.impl.DefaultType;
import com.tibco.xml.schema.parse.XiNodeParticleTermParser;
import com.tibco.xml.tns.parse.TnsDocument;
import com.tibco.xml.tns.parse.TnsFlavor;
import com.tibco.xml.tns.parse.TnsFlavorContext;
import com.tibco.xml.tns.parse.TnsFragment;
import com.tibco.xml.tns.parse.TnsImport;
import com.tibco.xml.tns.parse.impl.TnsImportImpl;
import com.tibco.xml.ws.wsdl.WsWsdl;


/**
 * copied from common/src, as we need access to our adapted entity code, as well as EMF code
 */
public class EMFRDFTnsFlavor extends RDFTnsFlavor {

    ExpandedName XSD_IMPORT = ExpandedName.makeName(XSDL.NAMESPACE, "import");
    String IMPORT_SCHEMA_LOCATION = "schemaLocation";
    
    
    public EMFRDFTnsFlavor() {
		super();
	}
    
    
    @Override
    public String[] getExtensions() {
    	try {
    	List<String> extensions = new ArrayList<String>();
    	for(AddOn addon:AddOnRegistry.getInstance().getAddons()) {
    		for(FileExtensionTypes e:addon.getTnsEntityExtensions().getExtension()) {
    			extensions.add(e.getLiteral());
    		}
    	}
    	return extensions.toArray(new String[extensions.size()]);
    	} catch (Exception e) {
			e.printStackTrace();
			return new String[0];
		}
    }

    private boolean isStaticResource(InputSource input) {
    	String sysId = input.getSystemId();
    	for (String resourceName : getStaticResources()) {
			if (sysId.equals(resourceName)) {
				return true;
			}
		}
    	return false;
    }
    
    protected Entity getEntity(InputSource input,String uri) throws Exception {
    	if (isStaticResource(input)) {
    		return RDFTnsHelper.deserializeEntity(input);
    	}
    	Path p = new Path(uri);
    	if ("be-bw".equalsIgnoreCase(System.getProperty(TNS_CACHE_INIT_CONTEXT))) {
			if (CommonIndexUtils.isRuleFunctionType(p.getFileExtension()) && "file:".equals(p.getDevice())) {
				RuleFunction rf = CommonIndexUtils.parseRuleFunctionFile("", URI.create(uri).getPath());
				input = new InputSource(CommonIndexUtils.getEObjectInputStream(rf));
			}
    	}
    	
		for (AddOn addon : AddOnRegistry.getInstance().getAddons()) {
			try {
				if (addon.getTnsEntityExtensions().getExtension()
						.contains(FileExtensionTypes.get(p.getFileExtension()))) {

					String thClassName = addon.getTnsEntityHandlerClass();
					if (thClassName != null && !thClassName.isEmpty()) {
						Class<?> thClazz = null;
						IAddOnLoader loader = null;
						if(PlatformUtil.INSTANCE.isRuntimePlatform() || PlatformUtil.INSTANCE.isWebStudioPlatform()) {
							thClazz = Class.forName(thClassName);
						} else {
							Map<AddOnType,IAddOnLoader> addonmap = AddOnRegistry.getInstance().getInstalledAddOnMap();
							thClazz = addonmap.get(addon.getType()).getAddonClass(thClassName);
							loader = addonmap.get(addon.getType());
						}
						if (thClazz != null) {
							com.tibco.be.model.rdf.TnsEntityHandler thClazzInstance = (TnsEntityHandler) thClazz
									.newInstance();
							thClazzInstance.setAddOnLoader(loader);
							return thClazzInstance.getEntity(input, uri);
						}
					}
				}
			} catch (Exception e) {
				//TODO
//				e.printStackTrace();
			}

		}
    	return RDFTnsHelper.deserializeEntity(input);
    }
    

    protected void eventToPayload(MutableSchema schema, Event event, MutableType eventType,String baseURL, String systemId, TnsFlavorContext tnsFlavorContext) throws Exception {
        MutableType payloadType = null;
        SmParticleTerm term = null;
        String payloadSchemaAsString = event.getPayloadSchemaAsString();
        if (payloadSchemaAsString == null) {
        	return;
        }
        XiNode payloadPropertyNode = XiParserFactory.newInstance().parse(new InputSource(new StringReader(payloadSchemaAsString)));
        if (payloadPropertyNode == null) {
            return;
        }
        XiNode firstChild = payloadPropertyNode.getFirstChild();
        XiAttribute.setStringValue(firstChild, "namespace", "");
        
        NamespaceImporter importer = event.getPayloadNamespaceImporter();
        ImportRegistry payloadImportRegistry = event.getPayloadImportRegistry();

        QNameLoadSaveUtils.writeNamespaces(firstChild, importer);
        QNameLoadSaveUtils.writeImports(payloadPropertyNode, payloadImportRegistry, XSD_IMPORT, IMPORT_SCHEMA_LOCATION);

		boolean nillableSmElem = System.getProperty(
				"tibco.be.schema.nil.attribs", "false").equals("true");
		
        try {
            Iterator allImportNodes= XiNodeParticleTermParser.getImportNodes(payloadPropertyNode);
            addImports(schema.getSingleFragment(),allImportNodes, baseURL, systemId, tnsFlavorContext);

            XiNode child = XiChild.getChild(payloadPropertyNode, EVENT_BODY);
            term = XiNodeParticleTermParser.getTerm(child, (DefaultSchema) schema);
            if (term == null) {
                if (isSoapEvent(event)) {
                    payloadType = (MutableType)MutableSupport.createComponent((DefaultSchema)schema, SmComponent.TYPE_TYPE);
                    MutableType envT = createBaseSoapSchema(schema);
                    MutableSupport.addOptionalLocalElement(payloadType, "Envelope", envT);
                    MutableElement e = MutableSupport.addLocalElement(eventType, EVENT_BODY.getLocalName(), payloadType, 0,1);  //Payload
                    DefaultMetaForeignAttribute defaultMetaForeignAttribute = new DefaultMetaForeignAttribute(NILLABLE_NAME, "false");
                    defaultMetaForeignAttribute.setNamespaceContext(new DefaultNamespaceContext());
                	e.addForeignAttribute(defaultMetaForeignAttribute);
                }
                return;
            }
            if (term instanceof DefaultElement) {
            	DefaultMetaForeignAttribute defaultMetaForeignAttribute = new DefaultMetaForeignAttribute(NILLABLE_NAME, "false");
                defaultMetaForeignAttribute.setNamespaceContext(new DefaultNamespaceContext());
            	((DefaultElement) term).addForeignAttribute(defaultMetaForeignAttribute);
            	setNillable((DefaultElement) term);
            }

            if (term.getComponentType() == SmComponent.ELEMENT_TYPE) {
                payloadType = (MutableType)MutableSupport.createComponent((DefaultSchema)schema, SmComponent.TYPE_TYPE);

                String tNs = term.getNamespace();
                if ((tNs == null) || (tNs.indexOf("no_namespace_schema") > 0)) {
                    ((MutableElement)term).setNamespace(NoNamespace.URI);
                }
               if (isSoapEvent(event)) {
                    MutableType envT = createSoapSchema(schema, payloadType, term);
                    if (envT != null) {
                    	DefaultMetaForeignAttribute defaultMetaForeignAttribute = new DefaultMetaForeignAttribute(NILLABLE_NAME, "false");
                        defaultMetaForeignAttribute.setNamespaceContext(new DefaultNamespaceContext());
                    	envT.addForeignAttribute(defaultMetaForeignAttribute);
						SmElement component = (SmElement) schema.getComponent(
								SmComponent.ELEMENT_TYPE, "message");
						if (component != null) {
							MutableSupport.addOptionalElement(payloadType,
									component);
						}
						else {
							MutableElement e = MutableSupport.addOptionalLocalElement(payloadType,
									"message", envT);
							DefaultMetaForeignAttribute attr = new DefaultMetaForeignAttribute(NILLABLE_NAME, "false");
		                    defaultMetaForeignAttribute.setNamespaceContext(new DefaultNamespaceContext());
					    	e.addForeignAttribute(attr);
						}

					}
                    schema.getSingleFragment().addImport(
        					new TnsImportImpl(null, null, SOAP_NAMESPACE,
        							SmNamespace.class));

                } else {
                    MutableSupport.addOptionalElement(payloadType, (SmElement)term);
                    if (PayloadValidationHelper.ENABLED) {
                        XiNode node = XiChild.getChild(payloadPropertyNode, EVENT_BODY);
                        if (null != node) {
                        	node.getNamespaceURIForPrefix("");
                            node = node.getFirstChild();
                            if ((null != node) && !XNAME_ANY.equals(node.getName())) {
                                final SmParticleTerm pt = XiNodeParticleTermParser.getTerm(node.getParentNode(), (DefaultSchema) schema);
                                schema.addSchemaComponent(pt);
                            }
                        }
                    }
                }
            }
            else if (term.getComponentType() == SmComponent.TYPE_TYPE) {
                payloadType = (MutableType) term;
            }
            else if (term.getComponentType() == SmComponent.WILDCARD_TYPE) {
                payloadType = (MutableType)MutableSupport.createComponent((DefaultSchema)schema, SmComponent.TYPE_TYPE);
                MutableSupport.addParticleTerm((MutableType)payloadType, term,1,1);
            }
            else if ((term.getComponentType() == SmComponent.MODEL_GROUP_TYPE)
                    && (SmModelGroup.CHOICE == ((SmModelGroup) term).getCompositor())) {
                payloadType = MutableSupport.createType(schema, null, SmModelGroup.CHOICE);
                for (Iterator<?> i = ((SmModelGroup) term).getParticles(); i.hasNext(); ) {
                    final SmParticle p = (SmParticle) i.next();
                    final SmParticleTerm t = p.getTerm();
                    final String tns = t.getNamespace();
                    if ((t instanceof MutableElement)
                            && ((tns == null) || (tns.indexOf("no_namespace_schema") > 0))) {
                        ((MutableElement) t).setNamespace(NoNamespace.URI);
                    }
                    MutableSupport.addParticleTerm(payloadType, p.getTerm(),
                            p.getMinOccurrence(), p.getMaxOccurrence());
                }
            }
            else {
                throw new Exception("Unknown term. -- check the type"); //TODO
            }
            if (payloadType != null) {
            	DefaultMetaForeignAttribute defaultMetaForeignAttribute = new DefaultMetaForeignAttribute(NILLABLE_NAME, "false");
                defaultMetaForeignAttribute.setNamespaceContext(new DefaultNamespaceContext());
            	payloadType.addForeignAttribute(defaultMetaForeignAttribute);
            }
        }
        catch (Exception e) {
            //TODO - ERROR HANDLING
            e.printStackTrace();
        }
        MutableElement e = MutableSupport.addLocalElement(eventType, EVENT_BODY.getLocalName(), payloadType, 0,1);  //Payload
        DefaultMetaForeignAttribute defaultMetaForeignAttribute = new DefaultMetaForeignAttribute(NILLABLE_NAME, "false");
        defaultMetaForeignAttribute.setNamespaceContext(new DefaultNamespaceContext());
        e.addForeignAttribute(defaultMetaForeignAttribute);

        return;
    }
    
    /*
     * payload elements are nillable in 5.1, carry that forward with the new mapper
     */
	private void setNillable(DefaultElement term) {
		SmType type = term.getType();
		if (type == null) {
			return;
		}
		SmModelGroup contentModel = ((DefaultType)type).getContentModel();
		if (contentModel == null) {
			// this is not a complex type, set it to nillable and return
			term.setNillable(true);
			return;
		}
		Iterator particles = contentModel.getParticles();
		while (particles.hasNext()) {
			DefaultParticle part = (DefaultParticle) particles.next();
			SmParticleTerm childTerm = part.getTerm();
			if (childTerm instanceof DefaultElement) {
				setNillable((DefaultElement) childTerm);
			}
		}
		term.setNillable(false);
		type.getMetaForeignAttributes();
	}
	
	protected static final ExpandedName NILLABLE_NAME = ExpandedName.makeName("isNillable");

    protected static void addImports(TnsFragment schemaFragment,
    		Iterator<?> importNodes,
    		String baseURL, String systemId, TnsFlavorContext tnsFlavorContext) {
    	while (importNodes.hasNext()) {
    		XiNode node = (XiNode) importNodes.next();
    		String ns =  node.getAttributeStringValue(ExpandedName.makeName("namespace"));
    		String loc =  node.getAttributeStringValue(ExpandedName.makeName("schemaLocation"));
    		String uri = baseURL+loc;
    		uri = uri.replaceAll("\\\\", "/");
    		uri = uri.replaceAll(" ", "%20");
			String absolute = "";
			if (baseURL.startsWith("jar")) {
				URI locationURI = URI.create(uri);
				absolute =  locationURI.toString();
			} else {
				absolute = ResolverUtilities.makeAbsolute(baseURL, loc.startsWith("/") ? loc.substring(1) : loc);
			}
    		ns=NoNamespace.normalizeNamespaceURI(ns);
    		if (ns.indexOf("no_namespace_schema") > 0) {
    			System.out.println("No Namespace.... " + ns);
    			TnsImport imprt = new TnsImportImpl(absolute, null, null, null );
    			schemaFragment.addImport(imprt);
    		} else if (loc.endsWith(".wsdl")){
    			WsWsdl wsdl = (WsWsdl) parse(absolute, tnsFlavorContext);
//    			Iterator fragments = wsdl.getSingleFragment().getFragments();
//    	        while ( fragments. hasNext() ){
//    	            TnsFragment frag = (TnsFragment) fragments .next();
//        			((DefaultSchemaFragment)schemaFragment).addFragment(frag);
//    	        }
    			// there are issues with the WsdlTnsImport when the wsdl changes
    			TnsImport imprt = new WsdlTnsImport(absolute, null, ns, SmNamespace.class);
    			schemaFragment.addImport(imprt);
    		} else {
    			TnsImport imprt = new TnsImportImpl(absolute, null, ns, SmNamespace.class);
    			schemaFragment.addImport(imprt);
    		}
    	}
    }

    private static TnsDocument parse( String location, TnsFlavorContext context ) {
		InputSource input;
		try {
			java.net.URI uri = new java.net.URI(location);
			FileInputStream stream = new FileInputStream(uri.getPath());
			input = new InputSource(stream);
			input.setSystemId(location);
	        if ( input.getCharacterStream() == null ) {
	            input .setCharacterStream( new XMLStreamReader( input .getByteStream() ) );
	            input .setByteStream( null );
	        }
	    	Iterator<?> interestedFlavors = context.getInterestedFlavors("wsdl");
	    	if (interestedFlavors.hasNext()) {
	    		TnsFlavor flavor = (TnsFlavor) interestedFlavors.next();
	    		return flavor.parse(input, context);
	    	}
//	        WsWsdl wsdl;
//	        try {
//	            wsdl = DefaultFactory.newInstance().parse(input, context);
//	        } catch (WsException e) {
//	            TnsErrorSeverity severity = TnsErrorSeverity.ERROR;
//	            int line = -1;
//	            int column = -1;
//	            if (e.getCulprit() instanceof SAXParseException) {
//	                SAXParseException se = (SAXParseException) e.getCulprit();
//	                line = se.getLineNumber();
//	                column = se.getColumnNumber();
//	                if (se.getMessage().startsWith("WARNING"))
//	                    severity = TnsErrorSeverity.WARNING;
//	            }
//	            wsdl = new Wsdl(WsdlConstants.WSDL_URI_11);    // empty wsdl
//	            wsdl.addError(severity, TnsMsgs.RSC, TnsMsgs.syntactic, new Object[]{e}, line, column);
//	        } catch (Throwable t) {
//	            wsdl = new Wsdl(WsdlConstants.WSDL_URI_11);    // empty wsdl
//	            ((TnsErrorContainerImpl)wsdl).addException(t);
//	        } finally {
//	        	try {
//					stream.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//	        }
//	        return wsdl;
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
    }

}
