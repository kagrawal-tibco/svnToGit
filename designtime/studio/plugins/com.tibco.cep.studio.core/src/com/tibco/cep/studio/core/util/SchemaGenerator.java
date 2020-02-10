package com.tibco.cep.studio.core.util;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.xml.sax.InputSource;

import com.tibco.be.model.rdf.RDFTnsFlavor;
import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.event.NamespaceEntry;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.studio.common.util.Path;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.rdf.EMFRDFTnsFlavor;
import com.tibco.cep.studio.core.repo.EMFTnsCache;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.util.coll.Iterators;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParser;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.datamodel.helpers.XiSerializer;
import com.tibco.xml.datamodel.nodes.Document;
import com.tibco.xml.schema.SmComponent;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmModelGroup;
import com.tibco.xml.schema.SmNamespace;
import com.tibco.xml.schema.SmNamespaceProvider;
import com.tibco.xml.schema.SmParticle;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.schema.SmSchema;
import com.tibco.xml.schema.SmSchemaFragment;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.build.MutableElement;
import com.tibco.xml.schema.build.MutableSchema;
import com.tibco.xml.schema.build.MutableSupport;
import com.tibco.xml.schema.build.MutableType;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.impl.DefaultComponentFactory;
import com.tibco.xml.schema.parse.xsd.XSDLSchema;
import com.tibco.xml.serialization.DefaultXmlContentSerializer;
import com.tibco.xml.tns.parse.TnsErrorSeverity;
import com.tibco.xml.tns.parse.TnsImport;
import com.tibco.xml.tns.parse.impl.TnsImportImpl;
import com.tibco.xml.util.XSDWriter;

/**
 * 
 * @author majha
 * Generate XSD schemas for a BE project.
 * 
 */

public class SchemaGenerator {
	public final static String BE_BASE_CONCEPT_NS = "www.tibco.com/be/ontology/_BaseConcept";
	public final static String BE_BASE_EVENT_NS = "www.tibco.com/be/ontology/_BaseEvent";
	public final static String BE_BASE_TIME_EVENT_NS = "www.tibco.com/be/ontology/_BaseTimeEvent";
	public final static String BE_BASE_SOAP_EVENT_NS = "www.tibco.com/be/ontology/_BaseSOAPEvent";
	public static final String BE_BASE_PROCESS_NS = "www.tibco.com/be/ontology/_BaseProcess";
	public static final String BE_BASE_EXCEPTION_NS = "www.tibco.com/be/ontology/_BaseException";
	private static final ExpandedName IS_NILLABLE_NAME = ExpandedName.makeName("isNillable");
	private static final ExpandedName NILLABLE_NAME = ExpandedName.makeName("nillable");

	String projectName;
	String generateLocation;
	
	/**
	 * This base namespace will be used so as to avoid namespace clashes with BE namespaces
	 * For example if a namespace is provided like www.my-org.com/schemas 
	 * then the namespace like www.tibco.com/be/ontology/cepts/MyCept.xsd 
	 * will be converted to
	 *  
	 * www.my-org.com/schemas/cepts/MyCept.xsd
	 * 
	 */
	String baseNamespace = "www.tibco.com/be/ontology";
	private EMFTnsCache cache;
	private boolean baseSoapSchemaGenerated;
	private boolean baseEventSchemaGenerated;
	private ISchemaNSResolver nsResolver;
	private boolean replaceExtension = false;


	public void setBaseNamespace(String baseNamespace) {
		this.baseNamespace = trim(baseNamespace);
	}
	
	public SchemaGenerator(String projectName, boolean replaceExtension) throws Exception {
		this(projectName, null);
		this.replaceExtension  = replaceExtension;
	}
	
	public SchemaGenerator(String projectName, ISchemaNSResolver nsResolver) throws Exception {
		this.projectName = projectName;
		this.cache = StudioCorePlugin.getCache(projectName);
		this.nsResolver = nsResolver;
	}


	public Map<String, String> generateSchemas(List<?> entityList) throws Exception {

		Map<String, String> m = new HashMap<String, String>();
		
		for (Iterator<?> itr = entityList.iterator(); itr.hasNext();) {
			Entity ee = (Entity) itr.next();
			if(ee instanceof Event && isSoapEvent((Event)ee)){
				String xsdNamespace = ee.getNamespace();
				String xsdName = ee.getName();
				String xsd = generateSoapEventSchema((Event)ee);
				m.put(xsdNamespace + xsdName, xsd);
			}else{
				MutableElement smElement = (MutableElement) getSmElementFromPath(projectName, ee.getFullPath());
				MutableSchema schema = (MutableSchema) smElement.getSchema();
				String xsdNamespace = ee.getNamespace();
				String xsdName = ee.getName();
				Map<String, String> namespaces = null;
				if (ee instanceof Event) {
					namespaces = new HashMap<String, String>();
					EList<NamespaceEntry> namespaceEntries = ((Event) ee).getNamespaceEntries();
					for (NamespaceEntry namespaceEntry : namespaceEntries) {
						namespaces.put(namespaceEntry.getPrefix(), namespaceEntry.getNamespace());
					}
				}
				String xsd = generateSchema(schema, xsdNamespace, xsdName, namespaces);
				m.put(xsdNamespace + xsdName, xsd);
			}
			
		}
		return m;

	}
	
	private String generateSoapEventSchema(Event ee) throws Exception {
		MutableElement smElement = (MutableElement) getSmElementFromPath(
				projectName, ee.getFullPath());
		MutableSchema schema = (MutableSchema) smElement.getSchema();
		String xsdNamespace = ee.getNamespace();
		String xsdName = ee.getName();
		String xsd = generateSchema(schema, xsdNamespace, xsdName, schema.getNamespace()
				+ "/Envelope");

		return xsd;
	}
	
	public Map<String, String> generateEnvelopeSchema(Event ee) throws Exception {
		if (!isSoapEvent(ee))
			return null;

		MutableElement smElement = (MutableElement) getSmElementFromPath(
				projectName, ee.getFullPath());
		MutableSchema eventSchema = (MutableSchema) smElement.getSchema();
		Map<String, SmSchema> schemas = getEnvelopeSchema(eventSchema, smElement);
		if (schemas == null) {
			return null;
		}
		Map<String, String> xsdSchemas = new LinkedHashMap<String, String>();
		Set<String> keySet = schemas.keySet();
		for (String string : keySet) {
			SmSchema smSchema = schemas.get(string);
			String xsdNamespace = smSchema.getNamespace();
			String xsdName = string;
			String xsd=null;
			
				xsd = generateSchema(smSchema, xsdNamespace, xsdName);
				if(smSchema.getNamespace().equalsIgnoreCase(RDFTnsFlavor.SOAP_NAMESPACE))
					xsdSchemas.put(eventSchema.getNamespace()+"/Envelope", xsd);
				else
					xsdSchemas.put(xsdNamespace, xsd);
				
		}
		
		return xsdSchemas;
		
	}
	
   

	
	private Map<String,SmSchema> getEnvelopeSchema(MutableSchema eventSchema , MutableElement smElement){
		SmElement smElementFromName = getSmElementFromName(smElement, "payload");
		if(smElementFromName != null){
			smElementFromName = getSmElementFromName(smElementFromName, "message");
			if(smElementFromName != null){
				smElementFromName = getSmElementFromName(smElementFromName, "Envelope");
				if(smElementFromName != null){
					return createSoapSchema(eventSchema, smElementFromName);
				}
			}
		}
		return null;
	}
	
	private SmElement getSmElementFromName(SmElement smElement, String name){
		MutableType elementType = (MutableType) smElement.getType();
		elementType = (MutableType) elementType.clone();
		SmModelGroup smModelGroup = elementType.getContentModel();
		if (smModelGroup != null) {
			Iterator<?> particles = smModelGroup.getParticles();
			while (particles.hasNext()) {
				SmParticle child = (SmParticle) particles.next();
				if(((SmElement)child.getTerm()).getName().equalsIgnoreCase(name)){
					return (SmElement)child.getTerm();
				}
			}
		}
		
		return null;
	}

	
    /*
     * Get Iterator over SmParticle of a type
     */
    private Iterator getParticleParts(SmParticle p) {
        Iterator partsI = null;
        if (p != null) {
            MutableElement element = (MutableElement) p.getTerm();
            if (element != null) {
                MutableType elementType = (MutableType) element.getType();
                SmModelGroup smModelGroup = elementType.getContentModel();
                //This will be null for simple types
                if (smModelGroup != null) {
                    return smModelGroup.getParticles();
                }
            }
        }
        return partsI;
    }

    /*
     * Finds a particles by name in an Element
     */
    private SmParticle getParticleByName(MutableElement p, String termName){
        if (p != null) {
        	MutableElement element = p;
            if (element != null) {
                MutableType elementType = (MutableType) element.getType();
                Iterator partsI = elementType.getContentModel().getParticles();
                while (partsI.hasNext()) {
                    SmParticle particle = (SmParticle) partsI.next();
                    if(termName.equalsIgnoreCase(particle.getTerm().getName())){
                        return particle;
                    }
                }
            }
        }
        return null;
    }
	
    MutableType createSmType(ExpandedName typeName, MutableSchema schema) {
        MutableType smType = DefaultComponentFactory.getTnsAwareInstance().createType();
        smType.setExpandedName(typeName);
        smType.setSchema(schema);
        smType.setComplex();
        return smType;
    }
    
    private SmSchema createFaultType(MutableSchema eventSchema, MutableSchema schema, MutableType onType, Iterator faultParts) {
    	ExpandedName faultName = ExpandedName.makeName(RDFTnsFlavor.SOAP_NAMESPACE, "Fault");
    	MutableType faultT = createSmType(faultName, schema);
    	MutableElement faultE = createSmElement(faultName, faultT);
    	MutableSupport.addElement(onType, faultE, 0, 1);
        MutableSupport.addRequiredLocalElement(faultT, "faultcode", XSDL.STRING);
        MutableSupport.addRequiredLocalElement(faultT, "faultstring", XSDL.STRING);
        MutableSupport.addOptionalLocalElement(faultT, "faultactor", XSDL.URI_REFERENCE);
        schema.addSchemaComponent(faultT);
        ExpandedName detailName = ExpandedName.makeName("detail");
        MutableType detailT  = MutableSupport.createType(schema, null, null);
        detailT.setComplex();
        MutableElement detailE = createSmElement(detailName, detailT);
        MutableSupport.addElement(faultT, detailE, 0, 1);
        if (faultParts != null && faultParts.hasNext()) {
        	MutableSchema faultPartSchema = DefaultComponentFactory
					.getTnsAwareInstance().createSchema();
        	faultPartSchema.setNamespace(eventSchema.getNamespaceURI()
					+ "/fault");
        	faultPartSchema.setFlavor(XSDL.FLAVOR);

			while (faultParts.hasNext()) {
				SmParticle p = (SmParticle) faultParts.next();
				if (p.getTerm().getName().equalsIgnoreCase("detail")) {
					Iterator detailPartsParts = getParticleParts(p);
					if(detailPartsParts!= null){
						while (detailPartsParts.hasNext()) {
							addNsToType(faultPartSchema, schema, detailT, (SmParticle)detailPartsParts.next(),
									eventSchema.getNamespaceURI() + "/fault");
						}
					}
					
				}
			}
            return faultPartSchema;
        }
        return null;
    }

    
	protected Map<String,SmSchema> createSoapSchema(MutableSchema eventSchema, SmElement envelopeElement) {
		Map<String, SmSchema> schemaMaps = new LinkedHashMap<String, SmSchema>();
		MutableSchema schema = DefaultComponentFactory
				.getTnsAwareInstance().createSchema();
		schema.setNamespace(envelopeElement.getNamespace());
		schema.setFlavor(XSDL.FLAVOR);
		Iterator imports = eventSchema.getSingleFragment().getImports();
		List<String> importedNamespaces = new ArrayList<String>();
		while (imports.hasNext()) {
			TnsImport imp = (TnsImport) imports.next();
			if (RDFTnsFlavor.SOAP_NAMESPACE.equals(imp.getNamespaceURI())) {
				// the target NS is already SOAP_NAMESPACE, do not re-import
				continue;
			}
			if (!importedNamespaces.contains(imp.getNamespaceURI())) {
				addImport(schema, (TnsImport) imp.clone());
				importedNamespaces.add(imp.getNamespaceURI());
			}
		}
		MutableType envT = null;
		Iterator headerParts = null;
		Iterator bodyParts = null;
		Iterator faultParts = null;

		SmParticle envP = null;
		SmParticle bodyP = null;
		SmParticle faultP = null;

		try {
			envT = (MutableType) envelopeElement.getType();

			Iterator rootParts = envT.getContentModel().getParticles();
			while (rootParts.hasNext()) {
				SmParticle p = (SmParticle) rootParts.next();
				if ("Header".equalsIgnoreCase(p.getTerm().getName())) {
					headerParts = getParticleParts(p);
				} else if ("Body".equalsIgnoreCase(p.getTerm().getName())) {
					bodyP = p;
					bodyParts = getParticleParts(p);
					faultP = getParticleByName(
							(MutableElement) bodyP.getTerm(), "Fault");
					faultParts = getParticleParts(faultP);
				}
			}


			ExpandedName envName = ExpandedName.makeName(envelopeElement.getNamespace(),
					envelopeElement.getName());
			envT = createSmType(envName, schema);
			MutableElement envE = createSmElement(envName, envT);
			envE.setSchema(schema);
			schema.addSchemaComponent(envT);
			schema.addSchemaComponent(envE);
			ExpandedName headerName = ExpandedName.makeName(RDFTnsFlavor.SOAP_NAMESPACE,
					"Header");
			MutableType headT = createSmType(headerName, schema);
			schema.addSchemaComponent(headT);
			MutableElement headerE = createSmElement(headerName, headT);
			MutableSupport.addElement(envT, headerE, 0, 1);
			if (headerParts != null) {
				if (headerParts.hasNext()) {
					MutableSchema headerPartSchema = DefaultComponentFactory
							.getTnsAwareInstance().createSchema();
					headerPartSchema.setNamespace(eventSchema.getNamespaceURI()
							+ "/header");
					headerPartSchema.setFlavor(XSDL.FLAVOR);
					schemaMaps.put("header", headerPartSchema);
					while (headerParts.hasNext()) {
						SmParticle p = (SmParticle) headerParts.next();
						addNsToType(headerPartSchema, schema, headT, p, eventSchema.getNamespaceURI()
								+ "/header");
					}
				}
			}

			ExpandedName bodyName = ExpandedName.makeName(RDFTnsFlavor.SOAP_NAMESPACE,
					"Body");
			MutableType bodyT = createSmType(bodyName, schema);
			schema.addSchemaComponent(bodyT);
			MutableElement bodyE = createSmElement(bodyName, bodyT);
			MutableSupport.addElement(envT, bodyE, 0, 1);
			if (bodyParts != null && bodyParts.hasNext()) {
				MutableSchema bodyPartSchema = DefaultComponentFactory
						.getTnsAwareInstance().createSchema();
				bodyPartSchema.setNamespace(eventSchema.getNamespaceURI()
						+ "/body");
				bodyPartSchema.setFlavor(XSDL.FLAVOR);
				schemaMaps.put("body", bodyPartSchema);
				while (bodyParts.hasNext()) {
					SmParticle p = (SmParticle) bodyParts.next();
					if ("Fault".equalsIgnoreCase(p.getTerm().getName())) {
						continue;
					}
					addNsToType(bodyPartSchema, schema, bodyT, p, eventSchema.getNamespaceURI()
							+ "/body");
				}
			}

			if (faultP != null) {
				SmSchema faultPartSchema = createFaultType(eventSchema, schema, bodyT, faultParts);
				if(faultPartSchema != null)
					schemaMaps.put("fault", faultPartSchema);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		MutableType dummyT = createSmType(ExpandedName.makeName(RDFTnsFlavor.SOAP_NAMESPACE,"hack"), schema);
		schema.addSchemaComponent(dummyT);
		schemaMaps.put(envelopeElement.getName(), schema);
		return schemaMaps;

	}

	private void addImport(MutableSchema schema, TnsImport imp) {
		Iterator imports = schema.getSingleFragment().getImports();
		while (imports.hasNext()) {
			TnsImport existingImp = (TnsImport) imports.next();
			if (imp.getNamespaceURI().equals(existingImp.getNamespaceURI())) {
				// this schema already imports the provided namespace.  If the new import has a location
				// but the existing one does not, use the location of the new import
				if (existingImp.getLocation() == null && imp.getLocation() != null) {
					((TnsImportImpl)existingImp).setLocation(imp.getLocation());
				}
				return;
			}
		}
		schema.getSingleFragment().addImport(imp);
	}
	
    protected void addNsToType (MutableSchema envelopePartSchema, MutableSchema schema,
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
    		SmNamespaceProvider snp = cache.getSmNamespaceProvider();
            SmNamespace sm =  snp.getNamespace(element.getNamespace());
    		SmElement ele = (SmElement)sm.getComponent(SmComponent.ELEMENT_TYPE, element.getName());
			ExpandedName en = element.getExpandedName();
			// MutableComponent c = createComponentRef(schema, en,
			// SmComponent.ELEMENT_TYPE);
			MutableSupport.addElement(onType, (MutableElement) ele, min, max);

			addImport(schema, new TnsImportImpl(null, null, en.namespaceURI,
					SmNamespace.class));
		} else {//Allow for in place schema elements
    		String localName = pTerm.getExpandedName().localName;
    		ExpandedName en = type.getExpandedName();
    		if(en != null && en.namespaceURI != null && type.getAtomicType() == null){
    			ExpandedName expName = ExpandedName.makeName(type.getExpandedName().namespaceURI, localName);
            	MutableElement newElement = createSmElement(expName, type);
            	newElement.setSchema(envelopePartSchema);
            	envelopePartSchema.addSchemaComponent(newElement);
            	// BE-22671 : This check tries to determine whether this is an "Element of Type" or an "XML Element Reference".  There's probably a better way to do this.
            	if (en.namespaceURI.equals(element.getNamespace())) {
            		MutableSupport.addElement(onType, newElement, min, max);
            	} else {
            		MutableSupport.addLocalElement(onType, localName, type, min, max);
            	}
            	addImport(envelopePartSchema, new TnsImportImpl(null, null, en.namespaceURI, SmNamespace.class));
        		addImport(schema, new TnsImportImpl(null, null, pTerm.getExpandedName().namespaceURI, SmNamespace.class));
    		} else {// This is a local type
    			ExpandedName expName = ExpandedName.makeName(pTerm.getExpandedName().namespaceURI, pTerm.getExpandedName().localName);
            	MutableElement newElement = createSmElement(expName, type);
            	newElement.setSchema(envelopePartSchema);
            	envelopePartSchema.addSchemaComponent(newElement);
            	
    			MutableSupport.addElement(onType, newElement, min, max);
    			addImport(schema, new TnsImportImpl(null, null, pTerm.getExpandedName().namespaceURI, SmNamespace.class));
    		}
    	}
    }
	
    MutableElement createSmElement(ExpandedName typeName, SmType smType){
    	MutableElement elem = DefaultComponentFactory.getTnsAwareInstance().createElement();
        elem.setType(smType);
        elem.setSchema(smType.getSchema());
        elem.setExpandedName(typeName);
        return elem;
    }
    
	private SmElement getSmElementFromPath(String projectName, String path) {
        SmNamespaceProvider snp = cache.getSmNamespaceProvider();
        SmNamespace sm =  snp.getNamespace(EMFRDFTnsFlavor.BE_NAMESPACE +  path);
        String[] terms = path.split("/");
        String elname = terms[terms.length - 1];

        if(sm != null) {
            return (SmElement)sm.getComponent(SmComponent.ELEMENT_TYPE, elname);
        } else {
            return null;
        }
    }

	public void generateConceptSchemas (IProgressMonitor monitor) throws Exception {
		if(monitor == null)
			monitor = new NullProgressMonitor();
		

		Map<String, String> m = new HashMap<String, String>();
		List<Entity> ceptList = CommonIndexUtils.getAllEntities(projectName, new ELEMENT_TYPES[] { ELEMENT_TYPES.CONCEPT, ELEMENT_TYPES.SCORECARD });
		if(ceptList.size() > 0){
			//generate base concept schema only when concepts exist in project
			SmType baseConceptType = EMFRDFTnsFlavor.getBaseConceptType();
			String baseConceptXsd = generateSchema(baseConceptType.getSchema(),"",baseConceptType.getName());
			m.put("/"+baseConceptType.getName(),baseConceptXsd);
		}
		
		Map<String, String> ceptsMap = generateSchemas(ceptList);
		m.putAll(ceptsMap);
		
		m = addOverridenNamespace(m);
		
		if (m.size() > 0) {
			monitor.beginTask("Generating Concept Schema...", m.size() + 1);
			monitor.worked(1);
		}
		
		writeToFile(m, generateLocation, monitor);
		monitor.done();
	}
	
	public void generateEventSchemas(IProgressMonitor monitor) throws Exception {
		if(monitor == null)
			monitor = new NullProgressMonitor();
		
		Map<String, String> m = new HashMap<String, String>();

		List<Event> eventList = CommonIndexUtils.getAllEvents(projectName);
		
//		if(eventList.size() > 0) {
//			//generate base event schema only when events exist in project
//			SmType baseEventType = EMFRDFTnsFlavor.getBaseEventType();
//			String baseEventXsd = generateSchema(baseEventType.getSchema(),"",baseEventType.getName());
//			m.put("/"+baseEventType.getName(),baseEventXsd);
//		}
		for (Event event : eventList) {
			if(isSoapEvent(event))
				generateBaseSoapEventSchema(m);
			else
				generateBaseEventSchema(m);
		}
		
		Map<String, String> eventsMap = generateSchemas(eventList);
		m.putAll(eventsMap);
		
		m = addOverridenNamespace(m);
		
		if (m.size() > 0) {
			monitor.beginTask("Generating Event Schema...", m.size() + 1);
			monitor.worked(1);
		}
		
		writeToFile(m, generateLocation, monitor);
		
		monitor.done();
	}
	
	private void generateBaseEventSchema(Map<String, String> m) throws Exception {
		if (!baseEventSchemaGenerated) {
			SmType baseEventType = EMFRDFTnsFlavor.getBaseEventType();
			String baseEventXsd = generateSchema(baseEventType.getSchema(), "",
					baseEventType.getName());
			m.put("/" + baseEventType.getName(), baseEventXsd);
			baseEventSchemaGenerated = true;
		}
	}
	
	private void generateBaseSoapEventSchema(Map<String, String> m) throws Exception {
		if (!baseSoapSchemaGenerated) {
			SmType baseEventType = EMFRDFTnsFlavor.getBaseSOAPEventType();
			String baseEventXsd = generateSchema(baseEventType.getSchema(), "",
					baseEventType.getName());
			m.put("/" + baseEventType.getName(), baseEventXsd);
			baseSoapSchemaGenerated = true;
		}
	}
	
    private boolean isSoapEvent (Event event) {
        String eventSuperPath = (String) event.getSuperEventPath();
        boolean isSoapEvent = RDFTypes.SOAP_EVENT.getName().equals(eventSuperPath);
        return isSoapEvent;
    }
	
	public static void writeToFile(Map<String, String> m, String rootDir, IProgressMonitor monitor) throws Exception, IOException {
		Iterator<Entry<String, String>> i = m.entrySet().iterator();

		while (i.hasNext()) {

			Entry<String, String> e = i.next();
			
			
			String xsdNamespace = e.getKey();
			String xsd =  e.getValue();
			String dirName = rootDir;
			if (xsdNamespace.indexOf("/") != -1) {
				dirName = rootDir + xsdNamespace.substring(0, xsdNamespace.lastIndexOf("/"));
			}
			dirName = dirName.replace('/', File.separatorChar).replace('\\',
					File.separatorChar);
			String xsdFileName = xsdNamespace.substring(xsdNamespace.lastIndexOf("/")+1) +".xsd";
			
			writeToFile(dirName, xsdFileName, xsd);
			monitor.worked(1);
		}
	}
	
	public String generateSchema(SmSchema schema, String xsdNamespace, String xsdName) throws Exception {
		return generateSchema(schema, xsdNamespace, xsdName, (HashMap<String, String>)null);
	}
	
	public String generateSchema(SmSchema schema, String xsdNamespace, String xsdName, Map<String, String> importedNamespaces) throws Exception {
		
		StringWriter stringWriter = new StringWriter();
		XSDWriter xw = new XSDWriter();
		ImportSchemaLocator importTns = new ImportSchemaLocator(xsdNamespace);

		xw.setProperty(XSDWriter.IMPORT, importTns);

		List<Object> imports = new LinkedList<Object>();

		DefaultXmlContentSerializer serializer = new DefaultXmlContentSerializer(stringWriter, null);
		SmSchemaFragment schemaFrag = (SmSchemaFragment) ((XSDLSchema)schema).getSingleFragment();
		Iterator schemaImports = schemaFrag.getImports();
		int ctr = 0;
		List<String> processedImports = new ArrayList<String>();
		while (schemaImports.hasNext()) {
			TnsImport imp = (TnsImport) schemaImports.next();
			if(imp.hasErrors(TnsErrorSeverity.WARNING, true)) {
				((TnsImportImpl)imp).setLocation(null);
			}
			if (replaceExtension && imp.getLocation() != null) {
				String fileExt = new Path(imp.getLocation()).getFileExtension();
				String loc = imp.getLocation();
				if (IndexUtils.CONCEPT_EXTENSION.equalsIgnoreCase(fileExt)) {
					loc = loc.substring(0, loc.length()-IndexUtils.CONCEPT_EXTENSION.length()) + IndexUtils.XSD_EXTENSION;
				} else if (IndexUtils.EVENT_EXTENSION.equalsIgnoreCase(fileExt)) {
					loc = loc.substring(0, loc.length()-IndexUtils.EVENT_EXTENSION.length()) + IndexUtils.XSD_EXTENSION;
				}
				((TnsImportImpl)imp).setLocation(loc);
			}
			if (!processedImports.contains(imp.getNamespaceURI())) {
				imports.add(imp);
				if (imp.getNamespaceURI() != null) {
					processedImports.add(imp.getNamespaceURI());
					serializer.prefixMapping("ns"+ctr++, imp.getNamespaceURI());
				}
			} else {
				// check whether this one has a location.  If so, use that location for the import (only for soap schemas?)
			}
		}
		if (importedNamespaces != null) {
			Set<String> keySet = importedNamespaces.keySet();
			Iterator<String> iterator = keySet.iterator();
			while (iterator.hasNext()) {
				String prefix = (String) iterator.next();
				String ns = importedNamespaces.get(prefix);
				if ("xsd".equals(prefix)) {
					continue;
				}
				if (!processedImports.contains(ns)) {
					serializer.prefixMapping(prefix, ns); // there is a potential to have duplicate prefixes here...
					addImport((MutableSchema) schema, new TnsImportImpl(null, null, ns,
							SmNamespace.class));
				}
			}
		}
		String xsd = null;
		try {
			xw.write(schema, serializer, imports.listIterator());
			xsd = stringWriter.toString();
			xsd = normalizeXsd(xsd);
		} catch (Exception e) {
			System.err.println("Error writing schema");
			e.printStackTrace();
			return null;
		}
		return xsd;
	}
	
	public String generateSchema(SmSchema schema, String xsdNamespace, String xsdName, String location) throws Exception {
		

		StringWriter stringWriter = new StringWriter();
		XSDWriter xw = new XSDWriter();
		ImportSchemaLocator importTns = new ImportSchemaLocator(xsdNamespace, location);

		xw.setProperty(XSDWriter.IMPORT, importTns);

		List<?> imports = new LinkedList<Object>();

		xw.write(schema, stringWriter, imports.listIterator());
		String xsd = stringWriter.toString();

		xsd = normalizeXsd(xsd);
		return xsd;
	}


	public static void writeToFile( String dirName, String fileName, String xsd)
			throws Exception, IOException {
		File dir = new File(dirName);
		if (!dir.exists()) {
			if (!dir.mkdirs()) {
				throw new Exception("Can't create schema folder: " + dirName);
			}
		}

		File file = new File(dir, fileName);
		OutputStreamWriter out = null;
		try {
			out = new OutputStreamWriter(new FileOutputStream(file),ModelUtils.DEFAULT_ENCODING);
			out.write(xsd);
			out.flush();
		} catch (Exception e) {
		} finally {
			out.close();
		}
	}

	class ImportSchemaLocator implements XSDWriter.DeclareImportsTns {
		String namespace;
		String [] myns = new String[0];
		private String schemaLocation;
		public ImportSchemaLocator (String namespace) {
			if (!namespace.equals("/")) {
				if (namespace.startsWith("/")) {
					namespace = namespace.substring(1);
				}
				this.namespace = namespace;
				myns = namespace.split("/");
			}
		}
		
		public ImportSchemaLocator (String namespace, String loc) {
			if (!namespace.equals("/")) {
				if (namespace.startsWith("/")) {
					namespace = namespace.substring(1);
				}
				this.namespace = namespace;
				myns = namespace.split("/");
			}
			this.schemaLocation = loc;
		}
		
		public String getLocation(String ns, String loc) {
			if (BE_BASE_CONCEPT_NS.equals(ns)) {
				return null;//getRelativeLocation(ns, "xsd");
			} else if (BE_BASE_EVENT_NS.equals(ns)) {
				return null;//getRelativeLocation(ns, "xsd");
			} else if (BE_BASE_SOAP_EVENT_NS.equals(ns)) {
				return null;//getRelativeLocation(ns, "xsd");
			} else if (BE_BASE_PROCESS_NS.equals(ns)) {
				return null;//getRelativeLocation(ns, "xsd");
			} else if (BE_BASE_TIME_EVENT_NS.equals(ns)) {
				return null;//getRelativeLocation(ns, "xsd");
			}
			String relative_location = null;
			if(loc != null) {
				loc = loc.replaceAll("\\\\" ,"/");
				if (!loc.endsWith("xsd")) {
					int idx = loc.lastIndexOf('.');
					String extension = null;
					if (idx > -1) {
						extension = loc.substring(idx+1);
					} else {
						extension = "xsd";
					}
					relative_location = getRelativeLocation(ns, extension);
				} else {
					try {
						String rootURI = cache.getRootURI();
						URI locURI = new URI(loc);
						if (locURI.isAbsolute()) {
							if (ns == null) {
								relative_location = "";
								for (int i = 0; i < myns.length; i++) {
									relative_location += "..";
									if (i < myns.length - 1) {
										relative_location += "/";
									}
								}
								locURI.toURL().getPath();
								String path = locURI.toURL().getPath();
								while (path.startsWith("/")) {
									path = path.substring(1);
								}
								while (rootURI.startsWith("/")) {
									// needed on UNIX
									rootURI = rootURI.substring(1);
								}
								relative_location += path.substring(rootURI.length());
								return relative_location;
							}
							if ("jar".equals(locURI.getScheme())) {
								return locURI.toString();
							}
							File file = new File(locURI.toURL().getFile());
							if (!file.exists() && nsResolver != null) {
								URI location = nsResolver.getLocation(ns);
								locURI = location;
							}
						}
						if (locURI == null) {
							return null;
						}
						String path = locURI.toURL().getPath();
						while (path.startsWith("/")) {
							path = path.substring(1);
						}
						while (rootURI.startsWith("/")) {
							// needed on UNIX
							rootURI = rootURI.substring(1);
						}
						if (path.startsWith(rootURI)) {
							relative_location = "";
							for (int i = 0; i < myns.length; i++) {
								relative_location += "..";
								if (i < myns.length - 1) {
									relative_location += "/";
								}
							} 
							relative_location += path.substring(rootURI.length());
						}
					} catch (URISyntaxException e) {
						e.printStackTrace();
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
				}
			}

			return relative_location;
		}

		public String getLocation(String location) {
			if(location != null && location.equalsIgnoreCase(RDFTnsFlavor.SOAP_NAMESPACE) && schemaLocation != null)
				return schemaLocation;
			return null;
		}

		public boolean declareImport(String s) {
			return true;
		}

		private String getRelativeLocation(String ns, String extension) {
			if (ns.contains(TypeManager.DEFAULT_BE_NAMESPACE_URI)) {
				ns = ns
						.substring(TypeManager.DEFAULT_BE_NAMESPACE_URI
								.length() + 1);
			}
			
			String[] st = ns.split("/");

			String relNs = "";
			 if (myns.length <= st.length - 1) {
				 
				int len_limit = myns.length;

				int j=0;
				for (j=0; j<len_limit; j++) {
					if (!myns[j].equals(st[j])) {
						break;
					}
				}
				int k=0;
				for (k=len_limit-1; k>=j; k--) {
					relNs += "../";
				}
				
				for ( k=j; k<st.length - 1; k++) {
					relNs = relNs + st[k] + "/";
				}
				relNs += st[st.length - 1];
			} else  {
				
				for (int i=myns.length; i>st.length - 1; i--) {
					relNs += "../";
				}
				
				int len_limit = st.length - 1;
				int j=0;
				for (j=0; j<len_limit; j++) {
					if (!myns[j].equals(st[j])) {
						break;
					}
				}
				int k=0;
				for (k=len_limit-1; k>=j; k--) {
					relNs += "../";
				}
				
				for ( k=j; k<st.length - 1; k++) {
					relNs = relNs + st[k] + "/";
				}
				relNs += st[st.length - 1];
				
			}
			return relNs + '.' + extension;
		}
	}

	private String normalizeXsd(String xsd) throws Exception {
		XiNode rootNode;
		XiParser parser;

		parser = XiParserFactory.newInstance();

		rootNode = parser.parse(new InputSource(new ByteArrayInputStream(xsd.getBytes(ModelUtils.DEFAULT_ENCODING))));
		XiNode schemaNode = XiChild.getChild(rootNode, ExpandedName
				.makeName(XSDL.NAMESPACE, "schema"));
		
		int countComplexTypeNodes = XiChild.getChildCount(schemaNode,
				ExpandedName.makeName(XSDL.NAMESPACE, "complexType"));
		if(countComplexTypeNodes > 1){
			Iterator<?> ComplexTypeIter = XiChild.getIterator(schemaNode,
					ExpandedName.makeName(XSDL.NAMESPACE, "complexType"));
			while (true) {
				XiNode complexTypeNode = (XiNode) ComplexTypeIter.next();
				if (!ComplexTypeIter.hasNext()) {
					schemaNode.removeChild(complexTypeNode);
					break;
				}
			}
		}
		removeAttributeRecursively(rootNode, IS_NILLABLE_NAME);
		// We WANT this attribute on the elements, IF it has been set on the DefaultElement in the TnsCache
//		removeAttributeRecursively(rootNode, NILLABLE_NAME);
		StringWriter w = new StringWriter();
		XiSerializer.serialize(rootNode, w);
		w.flush();
		xsd = w.toString();
		return xsd;
	}
	
	
	private void removeAttributeRecursively(XiNode node, ExpandedName attributeExpandedName){
		if(node == null )
			return;
		if (!(node instanceof Document)) {
			try{
				Iterator<?> attributes = node.getAttributes();
				if ((attributes != Iterators.EMPTY)) {
					XiNode attribute = node.getAttribute(attributeExpandedName);
					if (attribute != null)
						node.removeAttribute(attributeExpandedName);
				}
			}catch (Exception e){
				
			}

		}
		Iterator<?> children = node.getChildren();
		while(children.hasNext()){
			XiNode next = (XiNode)children.next();
			removeAttributeRecursively(next, attributeExpandedName);
		}
	}

	private Map<String, String> addOverridenNamespace(Map<String, String> xsdMap) {
		if (baseNamespace.equals("")) {
			return xsdMap;
		}
		Map<String, String> newMap = new LinkedHashMap<String, String>();
		Iterator<Entry<String, String>> i = xsdMap.entrySet().iterator();
		
		while (i.hasNext()) {
			Entry<String, String> e = i.next();
			String xsdNamespace =  e.getKey();
			String xsd =  e.getValue();

			xsd = xsd.replaceAll(TypeManager.DEFAULT_BE_NAMESPACE_URI, baseNamespace);
			i.remove();
			newMap.put(xsdNamespace, xsd);
		}

		return newMap;
	}



	public String getGenerateLocation() {
		return generateLocation;
	}

	public void setGenerateLocation(String generateLocation) {
		this.generateLocation = trim(generateLocation);
	}
	
	private static String trim (String str) {
		if (str == null) {
			return "";
		}
		if (str != null && !str.equals("")) {
			//strip trailing "/" or "\"
			if (str.endsWith("/") || str.endsWith("\\")) {
				str = str.substring(0, str.length() - 1);
			}
		}
		return str;
	}
}