package com.tibco.cep.studio.core.repo;


import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tibco.be.model.rdf.RDFTnsFlavor;
import com.tibco.be.util.BEStringUtilities;
import com.tibco.be.util.FastTargetNamespaceParser;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.BETargetNamespaceCache;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.common.configuration.StudioProjectConfigurationCache;
import com.tibco.cep.studio.common.configuration.XPATH_VERSION;
import com.tibco.cep.studio.common.validation.ISimpleProblemHandler;
import com.tibco.cep.studio.common.validation.utils.Messages;
import com.tibco.cep.studio.core.rdf.EMFRDFTnsFlavor;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.io.uri.URI;
import com.tibco.io.xml.XMLStreamReader;
import com.tibco.xml.data.primitive.DefaultNamespaceContext;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContext;
import com.tibco.xml.data.primitive.NamespaceToPrefixResolver.NamespaceNotFoundException;
import com.tibco.xml.data.primitive.PrefixToNamespaceResolver.PrefixNotFoundException;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiFactoryFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParser;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.datamodel.helpers.XiSerializer;
import com.tibco.xml.schema.SmComponent;
import com.tibco.xml.schema.SmComponentProviderEx;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmModelGroup;
import com.tibco.xml.schema.SmNamespace;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.schema.SmSchema;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.helpers.SmComponentProviderExOnSmNamespaceProvider;
import com.tibco.xml.schema.impl.DefaultElement;
import com.tibco.xml.schema.impl.DefaultParticle;
import com.tibco.xml.schema.impl.DefaultSchemaFragment;
import com.tibco.xml.schema.impl.SmNamespaceProviderImpl;
import com.tibco.xml.schema.parse.xsd.XSDLSchema;
import com.tibco.xml.tns.TargetNamespace;
import com.tibco.xml.tns.TnsComponent;
import com.tibco.xml.tns.parse.TnsDocument;
import com.tibco.xml.tns.parse.TnsFragment;
import com.tibco.xml.tns.parse.TnsFragmentedNamespace;
import com.tibco.xml.util.NamespaceCounter;
import com.tibco.xml.util.XSDWriter;
import com.tibco.xml.ws.wsdl.WsException;
import com.tibco.xml.ws.wsdl.WsWsdl;
import com.tibco.xml.ws.wsdl.helpers.DefaultFactory;

/**
 * @author ggrigore
 * 
 */
public class EMFTnsCache extends BETargetNamespaceCache {
	
	/*
	 * This class is used to keep the incoming input stream open
	 * while checking whether the namespace was already registered (see #alreadyRegistered()).
	 * Since the SAXParser always attempts to close the stream,
	 * we want to instead leave it open so that the TNSCache can
	 * properly read it when needed.
	 */
	private class OpenInputStream extends BufferedInputStream {

		boolean shouldClose = false;
		
		public OpenInputStream(InputStream in) {
			super(in);
		}

		@Override
		public void close() throws IOException {
			if (shouldClose) {
				super.close();
			}
		}
		
		public void setShouldClose(boolean shouldClose) {
			this.shouldClose = shouldClose;
		}
	}
	
	private static final String WSDl_EXTENSION = "wsdl";
	private static final String XSD_EXTENSION = "xsd";
	private static XiParser xiParser = XiParserFactory.newInstance();
	private static XiFactory xiFactory = XiFactoryFactory.newInstance();
	private Map<String, Set<String>> wsdlToInternalSchemaMap;
	private Queue<Throwable> errorList = new LinkedList<Throwable>();
    	
	private SmNamespaceProviderImpl smNamespaceProvider;
	private SmComponentProviderExOnSmNamespaceProvider smComponentProvider;
	private String rootURI;
	private boolean checkingNS = false;
	private String projectName;
    private static Logger logger;

    // Initialize this class //
    static {
        logger = LogManagerFactory.getLogManager().getLogger(EMFTnsCache.class);
    }

    public EMFTnsCache(final String rootURI) {
    	this(rootURI, null);
    }
    
    public EMFTnsCache(final String rootURI, final String projectName) {
    	this.rootURI = rootURI;
    	this.projectName = projectName;
    	smNamespaceProvider = new SmNamespaceProviderImpl(getNamespaceProvider()) {
            public SmNamespace getNamespace(String namespaceURI) {
                if (isNoTargetNamespaceNamespace(namespaceURI)) {
                    String n = getLocationFromNoTargetNamespaceNamespace(namespaceURI);
                    String fullURI = getRepoURI(n, rootURI);
                    SmSchema schema = (SmSchema) getDocument(fullURI); // WCETODO hacky, should be a better way.
            		if (schema != null) {
            			return schema;
            		}
                }
                return super.getNamespace(namespaceURI);
            }

        };
        smComponentProvider = new SmComponentProviderExOnSmNamespaceProvider(smNamespaceProvider);
    }
    
    @Override
    public TargetNamespace getNamespace(String namespaceURI, Class kind) {
    	if (isNoTargetNamespaceNamespace(namespaceURI)) {
    		String n = getLocationFromNoTargetNamespaceNamespace(namespaceURI);
    		String fullURI = getRepoURI(n, rootURI);
    		SmSchema schema = (SmSchema) getDocument(fullURI); // WCETODO hacky, should be a better way.
    		if (schema != null) {
    			return schema;
    		}
    	}
    	return super.getNamespace(namespaceURI, kind);
    }

    private static String getRepoURI(String canonicalURI, String rootURI) {
    	//      if (canonicalURI.startsWith("/")) {
    	//          canonicalURI = canonicalURI.substring(1);
    	//      }
    	int index = canonicalURI.indexOf(':');
    	if (index >= 0) { 
    		String striped = canonicalURI.substring(index+3); // remove '://'
    		index = striped.indexOf('/');
    		if (index < 1) {
    			throw new RuntimeException("unknown URI format "+canonicalURI);
    		}
    		canonicalURI = striped.substring(index+1);
    	}
    	if (rootURI.endsWith("/") && canonicalURI.startsWith("/")) {
    		// need to make sure we don't have double slashes here because XMLSDK canonicalization
    		// will get rid of them, but only for file:/// URIs.
    		return rootURI + canonicalURI.substring(1);
    	} else {
    		return rootURI + canonicalURI;
    	}
    }

    public static String getLocationFromNoTargetNamespaceNamespace(String namespace)
    {
        if (isNoTargetNamespaceNamespace(namespace))
        {
            return namespace.substring(NO_NAMESPACE_NS_PREFIX.length());
        }
        return null;
    }

    public static final String NO_NAMESPACE_NS_PREFIX = "http://www.tibco.com/ns/no_namespace_schema_location";

    public static boolean isNoTargetNamespaceNamespace(String namespace)
    {
        if (namespace==null)
        {
            return false;
        }
        return namespace.startsWith(NO_NAMESPACE_NS_PREFIX);
    }

    public SmNamespaceProviderImpl getSmNamespaceProvider() {
		return smNamespaceProvider;
	}

    public SmComponentProviderEx getSmComponentProvider() {
    	return smComponentProvider;
    }
    
    public void resourceRemoved(String sysId) {
    	if(sysId.endsWith(WSDl_EXTENSION)){
    		wsdlRemoved(sysId);
    		return;
    	}
    	this.documentRemoved(sysId);
    }
    
    public void resourceChanged(String sysId, InputStream is) {
    	if(sysId.endsWith(WSDl_EXTENSION)){
    		try {
				wsdlChanged(sysId, is);
			} catch (WsException e) {
				// skip it in case any error
				addError(e);
			}
    		return;
    	}
    	if (sysId.endsWith(XSD_EXTENSION)) {
        	is = new OpenInputStream(is);
        	try {
            	if (is.available() <= 1) {
            		// why is it 1?
            		is.mark(Integer.MAX_VALUE);
            	} else {
            		is.mark(is.available()+1);
            	}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
    		if (alreadyRegistered(sysId, is)) {
    			try {
    				// stream no longer needed - close it
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
    			return;
    		}
    		try {
    			is.reset();
    			((OpenInputStream)is).setShouldClose(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	} else {
    		// still need to make the check for entities
    		if (sysId.startsWith("jar:file:")) {
    			int idx = sysId.indexOf('!');
    			String namespace = RDFTnsFlavor.BE_NAMESPACE;
    			if (idx > 0) {
    				namespace += sysId.substring(idx+1);
    			}
    			idx = namespace.lastIndexOf('.');
    			if (idx > 0) {
    				namespace = namespace.substring(0, idx);
    			}
    			if (nsAlreadyRegistered(sysId, namespace, null, null, false)) {
    				return;
    			}
    		}
    	}
    	// need to call this for every Entity...
        InputSource inSrc = new InputSource(is);
        inSrc.setByteStream(is);
        inSrc.setSystemId(sysId);
        this.documentAddedOrChanged(inSrc);
    }
    
    public boolean alreadyRegistered(String sysId, InputStream bis) {
    	return alreadyRegistered(sysId, bis, null);
    }
    
    public boolean alreadyRegistered(String sysId, InputStream bis, ISimpleProblemHandler problemHandler) {
    	try {
    		SAXParser newSAXParser = SAXParserFactory.newInstance().newSAXParser();
    		
    		FastTargetNamespaceParser handler = new FastTargetNamespaceParser();
    		newSAXParser.parse(new InputSource(bis), handler);
    		List<String> targetNamespaces = handler.getTargetNamespaces();
    		if (targetNamespaces != null) {
    			for (String targetNamespace : targetNamespaces) {
    				if (nsAlreadyRegistered(sysId, targetNamespace, problemHandler, handler.getElementsForTNS().get(targetNamespace), true)) {
    					return true;
    				}
				}
    		}
		} catch (SAXException e) {
			// ignore. invalid xml document, so it won't get added to the cache anyway
//			e.printStackTrace();
		} catch (IOException e) {
			// ignore. invalid xml document, so it won't get added to the cache anyway
//			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} 
		return false;
	}

	private void wsdlRemoved(String sysId){
    	Set<String> schemaList = wsdlToInternalSchemaMap.get(sysId);
    	if(schemaList != null)
    		removeSchemas(schemaList);
    }
    
	private void  wsdlChanged(String sysId, InputStream is) throws WsException {
		if(wsdlToInternalSchemaMap == null)
			wsdlToInternalSchemaMap= new HashMap<String, Set<String>>();
		
		Set<String> schemaList = wsdlToInternalSchemaMap.get(sysId);
		if(schemaList == null){
			schemaList = new HashSet<String>();
			wsdlToInternalSchemaMap.put(sysId, schemaList);
		}
		removeSchemas(schemaList);
		
		DefaultFactory wsdlParser = DefaultFactory.getInstance();
		
		WsWsdl wsdl = null;
		try {
        	is = new OpenInputStream(is);
        	if (is.available() <= 1) {
        		// why is it 1?
        		is.mark(Integer.MAX_VALUE);
        	} else {
        		is.mark(is.available()+1);
        	}
        	InputSource source = new InputSource(is);
        	source.setSystemId(sysId);
//    		if (!alreadyRegistered(sysId, is)) {
//				is.reset();
//				((OpenInputStream)is).setShouldClose(true);
//				this.documentAddedOrChanged(source);
//				return;
//    		}
//			is.reset();
			((OpenInputStream)is).setShouldClose(true);
			wsdl = wsdlParser.parse(source);
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
			return;
		}
		Iterator internalSchemas = wsdl.getInternalSchemas();
		while(internalSchemas.hasNext()){
			XSDLSchema schema = (XSDLSchema)internalSchemas.next();
			InputSource inSrc;
			String schemaNS = schema.getNamespace();
			List<String> namespaces = getNamespacesForSchema(schema);
			if (nsAlreadyRegistered(sysId, schemaNS, null, namespaces, true)) {
				continue;
			}
			try {
				inSrc = getInputSource(sysId, schema, wsdl);
				schemaList.add(inSrc.getSystemId());
			} catch (Exception e) {
				continue;
			}
			this.documentAddedOrChanged(inSrc);
		}
	}

	private List<String> getNamespacesForSchema(XSDLSchema schema) {
		List<String> namespaces = new ArrayList<String>();
        Iterator<?> frags = schema.getFragments();
        while (frags.hasNext()) {
            TnsFragment frag = (TnsFragment) frags.next();
            Iterator<?> r = frag.getComponents(SmComponent.ELEMENT_TYPE);
            while (r.hasNext()) {
                TnsComponent comp = (TnsComponent)r.next();
                ExpandedName expandedName = comp.getExpandedName();
                String locName = expandedName.localName;
                namespaces.add(locName);
            }
        }
		return namespaces;
	}

	@Override
	public void resolveAndCheck() {
		if (checkingNS) {
			return;
		}
		super.resolveAndCheck();
	}

	private boolean nsAlreadyRegistered(String currLoc, String namespace, ISimpleProblemHandler handler, List<String> elementsInNamespace, boolean checkDoc) {
		if (namespace != null && namespace.startsWith(EMFRDFTnsFlavor.BE_NAMESPACE)) {
		} else if (/*isXPath2() || */"true".equalsIgnoreCase(System.getProperty("schema.conflictingNS.ignore", "false"))) { // Do not skip check for XPath 2.0, as the XSDs are generated from the TnsCache, which will contain errors if this check is not made
    		return false;
    	}
		if (elementsInNamespace == null) {
			elementsInNamespace = new ArrayList<String>();
		}
		if (namespace != null) {
			TargetNamespace tns = null;
			if (isNoTargetNamespaceNamespace(namespace)) {
				namespace = null;
			}
			checkingNS = true;
			// m_fragments.getNamespace() returns null for zip/jar entries (i.e. project library entries), so we need to check individual TnsDocuments every time
//			tns = m_fragments.getNamespace(namespace, SmNamespace.class);
			if (tns == null) {
				try {
					Iterator locations = getLocations();
					while (locations.hasNext()) {
						String loc = (String) locations.next();
						TnsDocument document = getDocument(loc);
						if (document != null && document.getSingleFragment() != null) {
							if (namespace == null) {
								if (document.getSingleFragment().getNamespaceURI() == null) {
									tns = document.getSingleFragment();
									if (checkTnsDocument(currLoc, namespace, handler, elementsInNamespace, tns)) {
										return true;
									}
								}
							} else if (namespace.equals(document.getSingleFragment().getNamespaceURI())) {
								if (!checkDoc) {
									String message = Messages.getString("Schema.duplicate.namespace");
									message = MessageFormat.format(message, namespace, namespace, loc, currLoc);
									logger.log(Level.WARN, message);
									return true;
								}
								tns = document.getSingleFragment();
								if (checkTnsDocument(currLoc, namespace, handler, elementsInNamespace, tns)) {
									return true;
								}
							}
						}
					}
				} finally {
					checkingNS = false;
				}
			}
		}
		return false;
	}

	private boolean isXPath2() {
		if ("true".equals(System.getProperty("com.tibco.mapper.swt", "false"))) {
			return true;
		}
		if (projectName != null) {
			StudioProjectConfiguration projConfig = StudioProjectConfigurationCache.getInstance().get(projectName);
			if (projConfig != null && projConfig.getXpathVersion() == XPATH_VERSION.XPATH_20) {
				return true;
			}
		}
		return false;
	}

	private boolean checkTnsDocument(String currLoc, String namespace,
			ISimpleProblemHandler handler, List<String> elementsInNamespace,
			TargetNamespace tns) {
		if (tns instanceof DefaultSchemaFragment) {
			String canonLoc = new URI(currLoc).getFullName(); // TnsDocumentStore first canonicalizes the sysId, so we need to do this to get the proper location
			String location = ((DefaultSchemaFragment)tns).getDocument().getLocation();
			if (!canonLoc.equals(location)) {
		        Iterator<?> frags = ((DefaultSchemaFragment) tns).getDocument().getFragments();
		        while (frags.hasNext()) {
		            TnsFragment frag = (TnsFragment) frags.next();
		            Iterator<?> r = frag.getComponents(SmComponent.ELEMENT_TYPE);
		            while (r.hasNext()) {
		                TnsComponent comp = (TnsComponent)r.next();
		                ExpandedName expandedName = comp.getExpandedName();
		                String locName = expandedName.localName;
		                if (elementsInNamespace.contains(locName)) {
		                	String message;
		                	if (namespace == null) {
		                		message = Messages.getString("Schema.duplicate.nonamespace");
		                		message = MessageFormat.format(message, locName, location, currLoc);
		                	} else {
		                		message = Messages.getString("Schema.duplicate.namespace");
		                		message = MessageFormat.format(message, locName, namespace, location, currLoc);
		                	}
							logger.log(Level.WARN, message);
							if (handler != null) {
								handler.handleProblem(currLoc, message);
							}
							return true;
		                }
		            }
		        }
			}
		} else if (tns instanceof TnsFragmentedNamespace && namespace == null) {
			Iterator<?> frags = ((TnsFragmentedNamespace) tns).getFragments();
			String canonLoc = new URI(currLoc).getFullName(); // TnsDocumentStore first canonicalizes the sysId, so we need to do this to get the proper location
		    while (frags.hasNext()) {
		        TnsFragment frag = (TnsFragment) frags.next();
				String location = frag.getDocument().getLocation();
				if (canonLoc.equals(location)) {
					continue;
				}
		        Iterator<?> r = frag.getComponents(SmComponent.ELEMENT_TYPE);
		        while (r.hasNext()) {
		            TnsComponent comp = (TnsComponent)r.next();
		            ExpandedName expandedName = comp.getExpandedName();
		            String locName = expandedName.localName;
		            if (elementsInNamespace.contains(locName)) {
		            	String message;
		            	if (namespace == null) {
		            		message = Messages.getString("Schema.duplicate.nonamespace");
		            		message = MessageFormat.format(message, locName, location, currLoc);
		            	} else {
		            		message = Messages.getString("Schema.duplicate.namespace");
		            		message = MessageFormat.format(message, locName, namespace, location, currLoc);
		            	}
						logger.log(Level.WARN, message);
						if (handler != null) {
							handler.handleProblem(currLoc, message);
						}
						return true;
		            }
		        }
		    }
		}
		return false;
	}
	
	private InputSource getInputSource(String sysId, XSDLSchema schema, WsWsdl wsdl) throws Exception{
		XSDWriter xsdWriter = new XSDWriter();
		NamespaceCounter nsc = new NamespaceCounter();
		schema.accept(nsc);

		ByteArrayOutputStream oStream = new ByteArrayOutputStream();
		Writer w = new OutputStreamWriter(oStream, XMLStreamReader
				.getJavaEncodingName(XMLStreamReader.UTF8));
		xsdWriter.writeXmlHeader(w, XMLStreamReader
				.getXMLEncodingName(XMLStreamReader.UTF8));
		xsdWriter.setProperty(XSDWriter.SORTED, false);
		ensurePrefixesDeclared(schema);
		xsdWriter.write(schema, w, null);

		w.flush();

		String xsd = oStream.toString("UTF-8");

		xsd = addImports(xsd, nsc);
		InputSource inputSource = new InputSource(new ByteArrayInputStream(xsd.getBytes(ModelUtils.DEFAULT_ENCODING)));
		
		int endIndex = sysId.lastIndexOf("/");
		String substring = sysId.substring(0, endIndex);
		String ns = schema.getNamespace();
		if (ns == null) {
			ns = wsdl.getTargetNamespace();
		}
		String convertToValidTibcoIdentifier = BEStringUtilities.convertToValidTibcoIdentifier(ns, true);
		convertToValidTibcoIdentifier = removeEnclosingBracket(convertToValidTibcoIdentifier);
		inputSource.setSystemId(substring+"/"+convertToValidTibcoIdentifier+".xsd");
		
		return inputSource;
	}
	
	// BE-22815 : ensure that all namespaces have a prefix declared, otherwise errors will result
	private void ensurePrefixesDeclared(XSDLSchema schema) {
		NamespaceContext namespaceContext = schema.getNamespaceContext();
		TnsFragment singleFragment = schema.getSingleFragment();
		Iterator components = singleFragment.getComponents(SmElement.ELEMENT_TYPE);
		while (components.hasNext()) {
			Object object = (Object) components.next();
			if (object instanceof DefaultElement) {
				DefaultElement el = (DefaultElement) object;
				checkElement(namespaceContext, el, new ArrayList<DefaultElement>());
			}
		}
	}

	private void checkElement(NamespaceContext namespaceContext,
			DefaultElement el, List<DefaultElement> checkedElements) {
		if (checkedElements.contains(el)) {
			return;
		}
		checkedElements.add(el);
		String namespace = el.getNamespace();
		int pfxCounter = 0;
		if (namespace != null) {
			try {
				namespaceContext.getPrefixForNamespaceURI(namespace);
			} catch (NamespaceNotFoundException e) {
				// need to declare this namespace, else the XSDWriter will fail
				pfxCounter = getUniqueSuffix(namespaceContext, pfxCounter);
				String pfx = "pfx"+pfxCounter++;
				((DefaultNamespaceContext)namespaceContext).declarePrefix(pfx, namespace);
			}
		}
		
		SmType type = el.getType();
		if (type != null) {
			SmModelGroup contentModel = type.getContentModel();
			if (contentModel == null) {
				return;
			}
			Iterator particles = contentModel.getParticles();
			while (particles.hasNext()) {
				DefaultParticle part = (DefaultParticle) particles.next();
				SmParticleTerm term = part.getTerm();
				if (term instanceof DefaultElement) {
					checkElement(namespaceContext, (DefaultElement) term, checkedElements);
				}
			}
		}
	}

	private int getUniqueSuffix(NamespaceContext namespaceContext,
			int pfxCounter) {
		String pfx = "pfx"+pfxCounter;
		try {
			namespaceContext.getNamespaceURIForPrefix(pfx);
		} catch (PrefixNotFoundException e) {
			return pfxCounter;
		}
		return (getUniqueSuffix(namespaceContext, pfxCounter+1));
	}

	private String addImports(String xsd, NamespaceCounter nsc)
			throws Exception {
		Iterator ns = nsc.getImportedNamespaces();
		XiNode rootNode = xiParser.parse(new InputSource(
				new ByteArrayInputStream(xsd.getBytes(ModelUtils.DEFAULT_ENCODING))));
		XiNode schemaNode = XiChild.getChild(rootNode, ExpandedName.makeName(
				XSDL.NAMESPACE, "schema"));
		String schemaNS = schemaNode.getName().getNamespaceURI();
		while (ns.hasNext()) {
			String importNS = (String) ns.next();
			XiNode importNode = xiFactory.createElement(ExpandedName.makeName(
					schemaNS, "import"));
			importNode.setAttributeStringValue(ExpandedName
					.makeName("namespace"), importNS);
			schemaNode.insertBefore(importNode, XiChild
					.getFirstChild(schemaNode));
		}
		xsd = XiSerializer.serialize(schemaNode);

		return xsd;
	}
	
	private void removeSchemas(Set<String> schemaList){
		Iterator<String> iterator = schemaList.iterator();
		while (iterator.hasNext()) {
			String systemID = (String) iterator.next();
			this.documentRemoved(systemID);
		}
		schemaList.clear();
	}
	
	private String removeEnclosingBracket(String name){
		String validIdentifier = name.replace('(', '_');
		validIdentifier = validIdentifier.replace(')', '_');
		return validIdentifier;
	}
	
	public void addError(Throwable e) {
		errorList.add(e);
	}
	
	public Queue<Throwable> getErrors() {
		return errorList;
	}
	
	@Override
	public String getRootURI() {
		return rootURI;
	}
}
