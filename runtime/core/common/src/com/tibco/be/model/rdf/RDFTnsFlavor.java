package com.tibco.be.model.rdf;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.xml.sax.InputSource;

import com.tibco.be.model.rdf.primitives.RDFBaseTerm;
import com.tibco.be.model.rdf.primitives.RDFPrimitiveTerm;
import com.tibco.be.model.rdf.primitives.RDFUberType;
import com.tibco.cep.designtime.model.AddOnRegistry;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.event.EventPropertyDefinition;
import com.tibco.cep.designtime.model.event.mutable.impl.DefaultMutableEvent;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableOntology;
import com.tibco.cep.designtime.model.process.ProcessModel;
import com.tibco.cep.designtime.model.process.SubProcessModel;
import com.tibco.cep.designtime.model.registry.AddOnType;
import com.tibco.cep.designtime.model.rule.RuleFunction;
import com.tibco.cep.designtime.model.rule.Symbol;
import com.tibco.cep.designtime.model.rule.Symbols;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.channel.PayloadValidationHelper;
import com.tibco.cep.util.PlatformUtil;
import com.tibco.sax.ResolverUtilities;
import com.tibco.xml.data.primitive.DefaultNamespaceContext;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactoryFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.schema.SmComponent;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmModelGroup;
import com.tibco.xml.schema.SmNamespace;
import com.tibco.xml.schema.SmNamespaceProvider;
import com.tibco.xml.schema.SmParticle;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.schema.SmSchema;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.build.MutableComponent;
import com.tibco.xml.schema.build.MutableComponentFactoryTNS;
import com.tibco.xml.schema.build.MutableElement;
import com.tibco.xml.schema.build.MutableSchema;
import com.tibco.xml.schema.build.MutableSupport;
import com.tibco.xml.schema.build.MutableType;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.helpers.NoNamespace;
import com.tibco.xml.schema.impl.DefaultComponentFactory;
import com.tibco.xml.schema.impl.DefaultMetaForeignAttribute;
import com.tibco.xml.schema.impl.DefaultSchema;
import com.tibco.xml.schema.parse.XiNodeParticleTermParser;
import com.tibco.xml.tns.parse.TnsDocument;
import com.tibco.xml.tns.parse.TnsError;
import com.tibco.xml.tns.parse.TnsErrorSeverity;
import com.tibco.xml.tns.parse.TnsFlavor;
import com.tibco.xml.tns.parse.TnsFlavorContext;
import com.tibco.xml.tns.parse.TnsFragment;
import com.tibco.xml.tns.parse.TnsImport;
import com.tibco.xml.tns.parse.helpers.TnsFragmentedDocument;
import com.tibco.xml.tns.parse.impl.TnsImportImpl;


/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jul 11, 2004
 * Time: 3:02:11 PM
 * To change this template use Options | File Templates.
 */
public class RDFTnsFlavor implements TnsFlavor {

	public final static String TNS_CACHE_INIT_CONTEXT="tnscache.initialization.context";
    public final static String BE_NAMESPACE= "www.tibco.com/be/ontology";
    public final static ExpandedName EVENT_BODY=ExpandedName.makeName("payload");
    protected static final ExpandedName XNAME_ANY = ExpandedName.makeName("http://www.w3.org/2001/XMLSchema", "any");
    public final static String SOAP_NAMESPACE= "http://schemas.xmlsoap.org/soap/envelope/";
    Map schemaCache;
    private final static MutableComponentFactoryTNS mcf = DefaultComponentFactory.getTnsAwareInstance();
    private final static String ENVELOPE_XSD ="envelope.xsd";
    private final static String TYPE_CONCEPT ="concept";
    private final static String TYPE_PROCESS ="beprocess";
    private final static String TYPE_EVENT  ="event";
    private final static String TYPE_TIMEEVENT  ="time";
    private final static String TYPE_SCORECARD  ="scorecard";
    private final static String TYPE_RULEFUNCTION  ="rulefunction";
    private final static String TYPE_METRIC  ="metric";

    private static final String RESOURCE_PKG = "com/tibco/be/model/";

    public static final String ENVELOPE_RESOURCE_LOCATION =
    		RESOURCE_PKG + ENVELOPE_XSD;
    
    private static final String BASE_EVENT_RESOURCE_LOCATION =
            RESOURCE_PKG + "_BaseEvent." + TYPE_EVENT;

    private static final String BASE_SOAPEVENT_RESOURCE_LOCATION =
        RESOURCE_PKG + "_BaseSOAPEvent." + TYPE_EVENT;

    private static final String BASE_CONCEPT_RESOURCE_LOCATION =
    		RESOURCE_PKG + "_BaseConcept." + TYPE_CONCEPT;
    
    private static final String BASE_PROCESS_RESOURCE_LOCATION =
            RESOURCE_PKG + "_BaseProcess." + TYPE_PROCESS;

    private static final String BASE_CONTAINEDCONCEPT_RESOURCE_LOCATION =
            RESOURCE_PKG + "_BaseContainedConcept." + TYPE_CONCEPT;

    private static final String BASE_SIMPLEEVENT_RESOURCE_LOCATION =
            RESOURCE_PKG + "_BaseSimpleEvent." + TYPE_EVENT;

    private static final String BASE_TIMEEVENT_RESOURCE_LOCATION =
            RESOURCE_PKG + "_BaseTimeEvent." + TYPE_TIMEEVENT;

    private static final String BASE_ENTITY_RESOURCE_LOCATION =
            RESOURCE_PKG + "_BaseEntity." + TYPE_EVENT;

    private static final String BASE_EXCEPTION_RESOURCE_LOCATION =
            RESOURCE_PKG + "_BaseException." + TYPE_EVENT;

    private static final String BASE_ADVISORY_EVENT_RESOURCE_LOCATION =
            RESOURCE_PKG + "_BaseAdvisoryEvent." + TYPE_EVENT;

    private static final String [] coreResources = {
            BASE_EVENT_RESOURCE_LOCATION,
            BASE_SOAPEVENT_RESOURCE_LOCATION,
            BASE_CONCEPT_RESOURCE_LOCATION,
            BASE_TIMEEVENT_RESOURCE_LOCATION,
            BASE_CONTAINEDCONCEPT_RESOURCE_LOCATION,
            BASE_SIMPLEEVENT_RESOURCE_LOCATION,
            BASE_ENTITY_RESOURCE_LOCATION,
            BASE_EXCEPTION_RESOURCE_LOCATION,
            BASE_ADVISORY_EVENT_RESOURCE_LOCATION,
            //BASE_PROCESS_RESOURCE_LOCATION
            };
    private static String [] staticResources = null;
    
	protected static final ExpandedName NILLABLE_NAME = ExpandedName.makeName("isNillable");

    private static SmType _baseEventType, _baseConceptType,_baseProcessType,_baseTimeEventType, _baseSimpleEventType, _baseContainedConceptType, _baseEntityType, _baseExceptionType, _baseAdvisoryEventType, _baseSOAPEventType;

    

    public RDFTnsFlavor() {
        schemaCache= new HashMap();
    }

    public String[] getExtensions() {
        return new String[] {TYPE_CONCEPT,
                TYPE_EVENT,
                TYPE_TIMEEVENT,
                TYPE_SCORECARD,
                TYPE_RULEFUNCTION,
                TYPE_METRIC,
                TYPE_PROCESS};
    }

    public String[] getStaticResources() {
    	if(staticResources == null) {
    		
    		List<String> res = new ArrayList(Arrays.asList(coreResources));
    		try {
    			if(AddOnRegistry.getInstance().getAddOnMap().containsKey(AddOnType.PROCESS)) {
    				res.add(BASE_PROCESS_RESOURCE_LOCATION);
    			}
    		} catch (Exception e) {
    			if(PlatformUtil.INSTANCE.isRuntimePlatform()|| PlatformUtil.INSTANCE.isWebStudioPlatform()) {
    				Logger logger = LogManagerFactory.getLogManager().getLogger(RDFTnsFlavor.class);
    				logger.log(Level.ERROR, "Failed to load addon registry",e);
    			} else  {
    				e.printStackTrace();
    			}
    		}
    		staticResources =  res.toArray(new String[0]);
    	}
    	return staticResources;
    }

    /**
     *
     * @return
     */
    public static SmType getBaseEventType () {
        return _baseEventType;
    }

    public static SmType getBaseSOAPEventType () {
        return _baseSOAPEventType;
    }
    /**
     *
     * @return
     */
    public static SmType getBaseConceptType () {
    	return _baseConceptType;
    }
    
    public static SmType getBaseProcessType () {
        return _baseProcessType;
    }

    public static SmType getBaseContainedConceptType () {
        return _baseContainedConceptType;
    }

    public static SmType getBaseSimpleEventType () {
        return _baseSimpleEventType;
    }

    public static SmType getBaseTimeEventType () {
        return _baseTimeEventType;
    }

    public static SmType getBaseEntityType () {
        return _baseEntityType;
    }

    public static SmType getBaseExceptionType () {
        return _baseExceptionType;
    }

    public static SmType getBaseAdvisoryEventType () {
        return _baseAdvisoryEventType;
    }

    protected Entity getEntity(InputSource input,String uri) throws Exception {
        return DefaultMutableOntology.deserializeEntity(input);
    }

    MutableSchema getSchema(String ns, boolean createIfNotExists) {

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

    MutableSchema getSchema(Entity e, boolean createIfNotExists) {
        String ns=BE_NAMESPACE+e.getFullPath();
        return getSchema(ns, createIfNotExists);
    }

    String parseConceptName(String conceptFullPath) {
        return conceptFullPath.substring(conceptFullPath.lastIndexOf('/')+1);
    }


    MutableType newSmType(ExpandedName typeName, MutableSchema schema) {
        MutableType smType = mcf.createType();
        smType.setExpandedName(typeName);
        smType.setSchema(schema);
        smType.setComplex();
        smType.setAllowedDerivation(SmComponent.EXTENSION | SmComponent.RESTRICTION);

        schema.addSchemaComponent(smType);

        // Add the element
        MutableElement elem = mcf.createElement();
        elem.setType(smType);
        elem.setSchema(schema);
        elem.setExpandedName(typeName);
        /**
         MutableType typeRef= mcf.createTypeRef();
         typeRef.setReference(schema);
         typeRef.setSchema(schema);
         typeRef.setExpandedName(smType.getExpandedName());
         MutableElement element= MutableSupport.createElement(schema, smType.getExpandedName().getLocalName(), typeRef);
         **/
        schema.addSchemaComponent(elem);
        return smType;
    }

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

    /**
     *
     * @param schema
     * @param typeName
     * @param overWrite
     * @return
     */
    MutableType getSmType(MutableSchema schema, ExpandedName typeName, boolean overWrite) {
        return newSmType(typeName, schema);
    }
    
    private MutableType getBaseProcessTypeReference(MutableSchema schema) {
    	MutableType refType= mcf.createTypeRef();
    	refType.setReference(schema);
    	refType.setSchema(schema);
    	refType.setExpandedName(_baseProcessType.getExpandedName());
    	//TODO: Add the Location
    	((DefaultSchema)schema).getSingleFragment().addImport(new TnsImportImpl(_baseProcessType.getSchema().getLocation() , null, _baseProcessType.getNamespace(),SmNamespace.class));
    	return refType;
    }

    private MutableType getBaseConceptTypeReference(MutableSchema schema) {
    	MutableType refType= mcf.createTypeRef();
    	refType.setReference(schema);
    	refType.setSchema(schema);
    	refType.setExpandedName(_baseConceptType.getExpandedName());
    	//TODO: Add the Location
    	((DefaultSchema)schema).getSingleFragment().addImport(new TnsImportImpl(_baseConceptType.getSchema().getLocation() , null, _baseConceptType.getNamespace(),SmNamespace.class));
    	return refType;
    }
    
    private MutableType getBaseContainedConceptTypeReference(MutableSchema schema) {
        MutableType refType= mcf.createTypeRef();
        refType.setReference(schema);
        refType.setSchema(schema);
        refType.setExpandedName(_baseContainedConceptType.getExpandedName());
        //TODO: Add the Location
        ((DefaultSchema)schema).getSingleFragment().addImport(new TnsImportImpl(_baseContainedConceptType.getSchema().getLocation() , null, _baseContainedConceptType.getNamespace(),SmNamespace.class));
        return refType;
    }

    private MutableType getBaseEventTypeReference(MutableSchema schema) {
        MutableType refType= mcf.createTypeRef();
        refType.setReference(schema);
        refType.setSchema(schema);
        refType.setExpandedName(_baseEventType.getExpandedName());
        //TODO: Add the Location
        ((DefaultSchema)schema).getSingleFragment().addImport(new TnsImportImpl(_baseEventType.getSchema().getLocation() , null, _baseEventType.getNamespace(),SmNamespace.class));
        return refType;
    }

    private MutableType getBaseSOAPEventTypeReference(MutableSchema schema) {
        MutableType refType= mcf.createTypeRef();
        refType.setReference(schema);
        refType.setSchema(schema);
        refType.setExpandedName(_baseSOAPEventType.getExpandedName());
        //TODO: Add the Location
        ((DefaultSchema)schema).getSingleFragment().addImport(new TnsImportImpl(_baseSOAPEventType.getSchema().getLocation() , null, _baseSOAPEventType.getNamespace(),SmNamespace.class));
        return refType;
    }

    private MutableType getBaseSimpleEventTypeReference(MutableSchema schema) {
        MutableType refType= mcf.createTypeRef();
        refType.setReference(schema);
        refType.setSchema(schema);
        refType.setExpandedName(_baseSimpleEventType.getExpandedName());
        //TODO: Add the Location
        ((DefaultSchema)schema).getSingleFragment().addImport(new TnsImportImpl(_baseSimpleEventType.getSchema().getLocation() , null, _baseSimpleEventType.getNamespace(),SmNamespace.class));
        return refType;
    }

    private MutableType getBaseTimeEventTypeReference(MutableSchema schema) {
        MutableType refType= mcf.createTypeRef();
        refType.setReference(schema);
        refType.setSchema(schema);
        refType.setExpandedName(_baseTimeEventType.getExpandedName());
        //TODO: Add the Location
        ((DefaultSchema)schema).getSingleFragment().addImport(new TnsImportImpl(_baseTimeEventType.getSchema().getLocation() , null, _baseTimeEventType.getNamespace(),SmNamespace.class));
        return refType;
    }

    private MutableType getBaseEntityTypeReference(MutableSchema schema) {
        MutableType refType= mcf.createTypeRef();
        refType.setReference(schema);
        refType.setSchema(schema);
        refType.setExpandedName(_baseEntityType.getExpandedName());
        //TODO: Add the Location
        ((DefaultSchema)schema).getSingleFragment().addImport(new TnsImportImpl(_baseTimeEventType.getSchema().getLocation() , null, _baseTimeEventType.getNamespace(),SmNamespace.class));
        return refType;
    }

    private MutableType getEntityTypeReference(MutableSchema schema, String entityFullPath, String loc) {
        String conceptName= parseConceptName(entityFullPath);
        MutableType refType= mcf.createTypeRef();
        refType.setReference(schema);
        refType.setSchema(schema);
        refType.setExpandedName(ExpandedName.makeName(BE_NAMESPACE+entityFullPath, conceptName));
        //TODO: Add the Location
        ((DefaultSchema)schema) .getSingleFragment().addImport(new TnsImportImpl(loc, null, BE_NAMESPACE+entityFullPath,SmNamespace.class));
        return refType;
    }

    private SmType getSmTypeForProperty (MutableSchema schema, int typeId, String conceptType, String baseURL, PropertyDefinition pd, boolean expandRefs) {
        RDFUberType utype = RDFTypes.types[typeId];
        SmType term = utype.getXSDLTerm();

        if (!expandRefs) { 
        	// do not expand @ref references
            if (typeId == RDFTypes.CONCEPT_TYPEID || (typeId == RDFTypes.CONCEPT_REFERENCE_TYPEID && typeIsOverridden(pd))) { // contained concept properties
                String loc = conceptType + "." + TYPE_CONCEPT;
                
    			String absolute = "";
    			if (baseURL.startsWith("jar")) {
            		String uri = baseURL+loc;
            		uri = uri.replaceAll("\\\\", "/");
    				URI locationURI = URI.create(uri);
    				absolute =  locationURI.toString();
    			} else {
    				absolute = ResolverUtilities.makeAbsolute(baseURL, loc
                            .startsWith("/") ? loc.substring(1) : loc);
    			}

                return getEntityTypeReference(schema, conceptType, absolute);
            } else if (typeId == RDFTypes.CONCEPT_REFERENCE_TYPEID) { // concept reference properties
                // String conceptName=parseConceptName(conceptType);
                // MutableType refType =
                // newSmType(ExpandedName.makeName(conceptName + "_ref"),
                // schema);
                MutableType type = MutableSupport.createType(schema, null, null);
                MutableSupport.addAttribute(type, "ref", XSDL.LONG, false);

//				MutableType refType = newSmType(ExpandedName.makeName(pdName),
//						schema);
//				MutableSupport
//						.addOptionalAttribute(refType, "ref", XSDL.STRING);
                return type;
            }
        } else { //if tibco.be.schema.ref.expand is set, always generate full schemas
            String absolute = null;
            MutableType refType = null;
            if (typeId == RDFTypes.CONCEPT_TYPEID
                    || typeId == RDFTypes.CONCEPT_REFERENCE_TYPEID) {
                String loc = conceptType + "." + TYPE_CONCEPT;
                absolute = ResolverUtilities.makeAbsolute(baseURL, loc
                        .startsWith("/") ? loc.substring(1) : loc);
                refType = getEntityTypeReference(schema, conceptType, absolute);
                if (typeId == RDFTypes.CONCEPT_REFERENCE_TYPEID) {
                    MutableSupport
                            .addOptionalAttribute(refType, "ref", XSDL.LONG);
                }
                return refType;
            }

        }
        return term;

    }
    private boolean typeIsOverridden(PropertyDefinition pd) {
        if (pd.getExtendedProperties() != null) {
            String relType = (String) pd.getExtendedProperties().get("REL_TYPE");
            return relType != null && relType.startsWith("C");
        }
        return false;
    }

    public static String getBaseURL(String systemId, String fullPath, String suffix) {
        String fp= fullPath + "." + suffix;
        int idx=systemId.indexOf(fp);
        String baseURI= "";
        if (systemId != null && idx <= systemId.length() && idx >= 0) {
            baseURI= systemId.substring(0,idx);
        }//endif
        return baseURI;
    }

   private MutableSchema processToSmType(ProcessModel processModel, String location,TnsFlavorContext tnsFlavorContext, boolean expandRefs) throws Exception {
    	// Get or create the schema for this concept
    	String fullPath = processModel.getFullPath();
    	boolean isBaseProcess = fullPath.endsWith("_BaseProcess");
    	if(fullPath.endsWith("."+TYPE_PROCESS))
    		fullPath = processModel.getFullPath().substring(0, processModel.getFullPath().indexOf("."+TYPE_PROCESS));
    	
//    	fullPath = com.tibco.be.model.util.ModelNameUtil.modelPathToGeneratedClassName(fullPath);
//    	fullPath = com.tibco.be.model.util.ModelNameUtil.generatedClassNameToModelPath(fullPath);
    	String ns =BE_NAMESPACE+fullPath;
    	
    	if(PlatformUtil.INSTANCE.isRuntimePlatform()) {
    		if(!isBaseProcess){
        		
        		ns+="_"+processModel.getRevision() + "_"+ com.tibco.be.model.util.ModelNameUtil.PROCESS_MARKER;
        	}
    		
    	}
    	MutableSchema schema = getSchema(ns,true);
    	MutableType m_baseType=null;
    	ExpandedName conceptTypeName = null;
    	if(PlatformUtil.INSTANCE.isRuntimePlatform()){
    		if(!isBaseProcess) {
    			conceptTypeName = ExpandedName.makeName(schema.getNamespace(),processModel.getName()+"_"+processModel.getRevision()+ "_"+ com.tibco.be.model.util.ModelNameUtil.PROCESS_MARKER);
    		} else {
    			conceptTypeName = ExpandedName.makeName(schema.getNamespace(),processModel.getName());
    		}
    	} else {
    		conceptTypeName = ExpandedName.makeName(schema.getNamespace(),processModel.getName());
    	}
    	MutableType smType = getSmType(schema,conceptTypeName,true);
    	String baseURL= getBaseURL(location, fullPath, TYPE_PROCESS);
    	boolean isBaseType=false;
    	
    	if (location.equals(BASE_PROCESS_RESOURCE_LOCATION)) {
    		isBaseType=true;
    		_baseProcessType=smType;
    	} 
    	
    	m_baseType=getBaseProcessTypeReference(schema);
        smType.setBaseType(m_baseType);
    	
    	boolean nillableSmElem = System.getProperty("tibco.be.schema.nil.attribs", "false").equals("true");
    	
    	// Handle all properties
    	Iterator allProperties=processModel.getPropertyDefinitions().iterator();
    	while (allProperties.hasNext()) {
    		PropertyDefinition pd = (PropertyDefinition) allProperties.next();
    		int pdType=pd.getType();
    		String pdName=pd.getName();
    		boolean isArray=pd.isArray();
    		String conceptTypePath=pd.getConceptTypePath();
    		if (!isBaseType) {
    			SmType type = getSmTypeForProperty(schema, pdType, conceptTypePath, baseURL, pd, expandRefs);
    			if (type != null) {
    				MutableElement e = MutableSupport.addLocalElement(smType, pdName, type, 0, isArray?Integer.MAX_VALUE:1);
    				if (nillableSmElem) {
    					e.setNillable(true);
    				} else {
    					DefaultMetaForeignAttribute defaultMetaForeignAttribute = new DefaultMetaForeignAttribute(NILLABLE_NAME, "false");
    					defaultMetaForeignAttribute.setNamespaceContext(new DefaultNamespaceContext());
    					e.addForeignAttribute(defaultMetaForeignAttribute);
    				}
    			}
    		} else {
    			MutableSupport.addOptionalAttribute(smType, pdName, XSDL.STRING);
    		}
    	}
    	if(!isBaseType){
    		Collection<SubProcessModel> subProcesses = processModel.getSubProcesses();
    		Iterator<SubProcessModel> iterator = subProcesses.iterator();
    		while (iterator.hasNext()) {
				SubProcessModel subProcessModel = (SubProcessModel) iterator
						.next();
				
				subProcessToSmType(processModel,subProcessModel, schema,smType, baseURL, expandRefs);
				
			}
    	}
    	
    	return schema;
    }
    
	private void subProcessToSmType(ProcessModel parentModel,SubProcessModel subprocessModel,
			MutableSchema schema, SmType parentSmType, String baseURL, boolean expandRefs)
			throws Exception {
		MutableType m_baseType = null;
		
		String ns = subprocessModel.getFullPath();

		if (PlatformUtil.INSTANCE.isRuntimePlatform()) {
			ns += "_" + subprocessModel.getRevision();
		}

		ExpandedName conceptTypeName = ExpandedName.makeName(
				schema.getNamespace(), ns);

		MutableType smType = getSmType(schema, conceptTypeName, true);

		m_baseType = getBaseProcessTypeReference(schema);
		smType.setBaseType(m_baseType);

		boolean nillableSmElem = System.getProperty(
				"tibco.be.schema.nil.attribs", "false").equals("true");

//		MutableElement parentElement = MutableSupport.addLocalElement(smType,
//				"parentJob", parentSmType, 0, 1);
//		if (nillableSmElem) {
//			parentElement.setNillable(true);
//		} else {
//			DefaultMetaForeignAttribute defaultMetaForeignAttribute = new DefaultMetaForeignAttribute(
//					NILLABLE_NAME, "false");
//			defaultMetaForeignAttribute
//					.setNamespaceContext(new DefaultNamespaceContext());
//			parentElement.addForeignAttribute(defaultMetaForeignAttribute);
//		}

		// Handle all properties
		Iterator allProperties = subprocessModel.getPropertyDefinitions()
				.iterator();
		while (allProperties.hasNext()) {
			PropertyDefinition pd = (PropertyDefinition) allProperties.next();
			int pdType = pd.getType();
			String pdName = pd.getName();
			boolean isArray = pd.isArray();
			String conceptTypePath = pd.getConceptTypePath();
			SmType type = getSmTypeForProperty(schema, pdType, conceptTypePath,
					baseURL, pd, expandRefs);
			if (type != null) {
				MutableElement e = MutableSupport.addLocalElement(smType,
						pdName, type, 0, isArray ? Integer.MAX_VALUE : 1);
				if (nillableSmElem) {
					e.setNillable(true);
				} else {
					DefaultMetaForeignAttribute defaultMetaForeignAttribute = new DefaultMetaForeignAttribute(
							NILLABLE_NAME, "false");
					defaultMetaForeignAttribute
							.setNamespaceContext(new DefaultNamespaceContext());
					e.addForeignAttribute(defaultMetaForeignAttribute);
				}
			}
		}
		Collection<SubProcessModel> subProcesses = subprocessModel
				.getSubProcesses();
		Iterator<SubProcessModel> iterator = subProcesses.iterator();
		while (iterator.hasNext()) {
			
			SubProcessModel childModel = (SubProcessModel) iterator.next();
			subProcessToSmType(subprocessModel,childModel, schema, smType, baseURL, expandRefs);
			
		}
	}
    
    
    private MutableSchema conceptToSmType(Concept concept, String location,TnsFlavorContext tnsFlavorContext, boolean expandRefs) throws Exception {
        // Get or create the schema for this concept
        MutableSchema schema = getSchema(concept,true);
        MutableType m_baseType=null;
        ExpandedName conceptTypeName= ExpandedName.makeName(schema.getNamespace(),concept.getName());
        MutableType smType = getSmType(schema,conceptTypeName,true);
        String baseURL= getBaseURL(location, concept.getFullPath(), concept.isAScorecard() ? TYPE_SCORECARD :  TYPE_CONCEPT);
        boolean isBaseType=false;

        if (location.equals(BASE_CONCEPT_RESOURCE_LOCATION)) {
            isBaseType=true;
            _baseConceptType=smType;
        } else if(location.equals(BASE_CONTAINEDCONCEPT_RESOURCE_LOCATION)) {
            isBaseType=true;
            _baseContainedConceptType=smType;
        }
        else {
            smType.setDerivationMethod(SmComponent.EXTENSION);
            String scp= concept.getSuperConceptPath();
            if ((scp != null) && !(scp.trim().equals(""))){
                String loc=scp+"."+TYPE_CONCEPT ;
                String absolute = ResolverUtilities.makeAbsolute(baseURL, loc.startsWith("/") ? loc.substring(1) : loc);
                MutableType parentType= this.getEntityTypeReference(schema, scp, absolute);
                smType.setBaseType(parentType);
            } else {
                m_baseType=getBaseConceptTypeReference(schema);
                smType.setBaseType(m_baseType);
            }
        }

        boolean nillableSmElem = System.getProperty("tibco.be.schema.nil.attribs", "false").equals("true");

        // Handle all properties
        Iterator allProperties=concept.getLocalPropertyDefinitions().iterator();
        while (allProperties.hasNext()) {
            PropertyDefinition pd = (PropertyDefinition) allProperties.next();
            int pdType=pd.getType();
            String pdName=pd.getName();
            boolean isArray=pd.isArray();
            String conceptTypePath=pd.getConceptTypePath();
            if (!isBaseType) {
                SmType type = getSmTypeForProperty(schema, pdType, conceptTypePath, baseURL, pd, expandRefs);
                if (type != null) {
                    MutableElement e = MutableSupport.addLocalElement(smType, pdName, type, 0, isArray?Integer.MAX_VALUE:1);
                    if (nillableSmElem) {
                        e.setNillable(true);
                    } else {
                    	DefaultMetaForeignAttribute defaultMetaForeignAttribute = new DefaultMetaForeignAttribute(NILLABLE_NAME, "false");
                        defaultMetaForeignAttribute.setNamespaceContext(new DefaultNamespaceContext());
                    	e.addForeignAttribute(defaultMetaForeignAttribute);
                    }
                }
            } else {
            	// cannot simply use XSDL.STRING here, try to get the actual property type (BE-14955)
                RDFUberType utype = RDFTypes.types[pdType];
                if (utype != null && utype.getXSDLTerm() != null) {
                	SmType term = utype.getXSDLTerm();
                	MutableSupport.addOptionalAttribute(smType, pdName, term);
                } else {
                	MutableSupport.addOptionalAttribute(smType, pdName, XSDL.STRING);
                }
            }
        }
        return schema;
    }

    private void copyFragments(TnsDocument doc, MutableSchema schema) {
        doc.addFragment(schema.getSingleFragment());
        Iterator allImports=doc.getSingleFragment().getImports();
        while (allImports.hasNext()) {
            TnsImport imprt = (TnsImport) allImports.next();
        }
    }

    public SmElement createSmElement(Entity e, String systemID, boolean expandRefs) {
    	MutableSchema schema = null;
    	try {
    		if(e instanceof ProcessModel) {
    			schema=processToSmType((ProcessModel)e, systemID, null, expandRefs);
    			schema.setLocation(systemID);
    		} else if (e instanceof Concept) {
    			schema=conceptToSmType((Concept)e, systemID, null, expandRefs);
    			schema.setLocation(systemID);
    		} else if (e instanceof Event) {
    			schema=eventToSmType(null,(Event)e, systemID, null);
    			schema.setLocation(systemID);
    		} else if (e instanceof RuleFunction) {
    			schema=ruleFunctionToSmType(null, (RuleFunction)e, systemID, null);
    			schema.setLocation(systemID);
    		} else if (null == e) {
    			System.out.println("Not parsed: " + systemID);
    		} else {
    			System.out.println(e.getClass().getName());
    		}
    	} catch (Exception ex) {
    		String msg = ex.getLocalizedMessage();
    		schema.addError(TnsErrorSeverity.FATAL,
    				", exception during parse, see console output: {0}",
    				msg,
    				TnsError.UNKNOWN,
    				TnsError.UNKNOWN );
    		ex.printStackTrace();
    	}
    	return (SmElement) schema.getSingleFragment().getComponent(e.getName(), SmComponent.ELEMENT_TYPE);
    }
    
    public synchronized TnsDocument parse(InputSource input, TnsFlavorContext tnsFlavorContext) {
        TnsDocument doc = createDocument();
        try {
            SmNamespaceProvider nsp= tnsFlavorContext.getNamespaceProvider();
            /**
             try {
             System.out.println("Base URI=" + tnsFlavorContext.getBaseURI());
             } catch (Exception e) {
             e.printStackTrace();
             }
             **/
            Entity e=getEntity(input,input.getSystemId());
            String sysId = input.getSystemId();
            boolean expandRefs = System.getProperty("tibco.be.schema.ref.expand", "false").equalsIgnoreCase("true");
            if(e instanceof ProcessModel) {
            	MutableSchema schema=processToSmType((ProcessModel)e, sysId, tnsFlavorContext, expandRefs);
                schema.setLocation(sysId);
                copyFragments(doc,schema);
            } else if (e instanceof Concept) {
                MutableSchema schema=conceptToSmType((Concept)e, sysId, tnsFlavorContext, expandRefs);
                schema.setLocation(sysId);
                copyFragments(doc,schema);
            } else if (e instanceof Event) {
                MutableSchema schema=eventToSmType(nsp,(Event)e, sysId, tnsFlavorContext);
                schema.setLocation(sysId);
                copyFragments(doc,schema);
            } else if (e instanceof RuleFunction) {
                MutableSchema schema=ruleFunctionToSmType(nsp, (RuleFunction)e, sysId, tnsFlavorContext);
                schema.setLocation(sysId);
                copyFragments(doc,schema);
                doc = createDocument();
                schema=ruleFunctionToSmTypeRef(nsp, (RuleFunction)e, sysId, tnsFlavorContext);
                schema.setLocation(sysId);
                copyFragments(doc,schema);
            } else if (null == e) {
                System.out.println("Not parsed: " + sysId);
            } else {
                System.out.println(e.getClass().getName());
            }
            

        } catch (Exception e) {
            String msg = e.getLocalizedMessage();
            doc.addError(TnsErrorSeverity.FATAL,
                    ", exception during parse, see console output: {0}",
                    msg,
                    TnsError.UNKNOWN,
                    TnsError.UNKNOWN );
            e.printStackTrace();
        }
        return doc;
    }

    private MutableSchema ruleFunctionToSmType(SmNamespaceProvider nsp, RuleFunction rf, String systemId, TnsFlavorContext tnsFlavorContext) {

        MutableSchema schema = getSchema(rf,true);

        Ontology ont = rf.getOntology();
        //ExpandedName rfInputTypeName= ExpandedName.makeName(schema.getNamespace(),rf.getName()+"_arguments");
        ExpandedName rfInputTypeName= ExpandedName.makeName(schema.getNamespace(),"arguments");
        MutableType rfInType = getSmType(schema,rfInputTypeName,true);
        Symbols syms = rf.getArguments();
        String baseUrl = this.getBaseURL(systemId, rf.getFullPath(), TYPE_RULEFUNCTION);
        for (Iterator itr = syms.getSymbolsList().iterator(); itr.hasNext();)
        {
            Symbol s = (Symbol) itr.next();
            String argName = s.getName();
            String typeName = s.getTypeWithExtension();
            SmType smType = getSmTypeForFunctionArgument(schema, ont, typeName,  baseUrl);
            MutableElement smEle = MutableSupport.addLocalElement(rfInType, argName, smType, 1,1);  //Aha we dont support arrays in RuleFunction
            smEle.setNillable(true);
            if ((smType == _baseEventType) || (smType == _baseConceptType))
                smEle.setAllowedSubstitution(SmType.RESTRICTION + SmType.EXTENSION + SmType.SUBSTITUTION) ;

        }

        //ExpandedName rfOutputTypeName= ExpandedName.makeName(schema.getNamespace(),rf.getName()+"_return");
        ExpandedName rfOutputTypeName= ExpandedName.makeName(schema.getNamespace(),"return");
        String returnTypeName = rf.getReturnTypeWithExtension();
        if ((returnTypeName == null)) return schema;

        SmType smType = getSmTypeForFunctionArgument(schema, ont, returnTypeName,  baseUrl);
        MutableElement smEle = MutableSupport.createElement(schema, rfOutputTypeName.getLocalName(), smType);


        return schema;
    }
    
    private MutableSchema ruleFunctionToSmTypeRef(SmNamespaceProvider nsp, RuleFunction rf, String systemId, TnsFlavorContext tnsFlavorContext) {

        MutableSchema schema = getSchema(rf,true);

        Ontology ont = rf.getOntology();
        //ExpandedName rfInputTypeName= ExpandedName.makeName(schema.getNamespace(),rf.getName()+"_arguments");
        ExpandedName rfInputTypeName= ExpandedName.makeName(schema.getNamespace(),"arguments");
        MutableType rfInType = getSmType(schema,rfInputTypeName,true);
        Symbols syms = rf.getArguments();
        String baseUrl = this.getBaseURL(systemId, rf.getFullPath(), TYPE_RULEFUNCTION);
        for (Iterator itr = syms.getSymbolsList().iterator(); itr.hasNext();)
        {
            Symbol s = (Symbol) itr.next();
            String argName = s.getName();
            String typeName = s.getTypeWithExtension();
            SmType smType = getSmTypeForFunctionArgumentRef(schema, ont, typeName,  baseUrl);
            MutableElement smEle = MutableSupport.addLocalElement(rfInType, argName, smType, 1,1);  //Aha we dont support arrays in RuleFunction
            smEle.setNillable(true);
            if ((smType == _baseEventType) || (smType == _baseConceptType))
                smEle.setAllowedSubstitution(SmType.RESTRICTION + SmType.EXTENSION + SmType.SUBSTITUTION) ;

        }

        //ExpandedName rfOutputTypeName= ExpandedName.makeName(schema.getNamespace(),rf.getName()+"_return");
        ExpandedName rfOutputTypeName= ExpandedName.makeName(schema.getNamespace(),"return");
        String returnTypeName = rf.getReturnTypeWithExtension();
        if ((returnTypeName == null)) return schema;

        SmType smType = getSmTypeForFunctionArgument(schema, ont, returnTypeName,  baseUrl);
        MutableElement smEle = MutableSupport.createElement(schema, rfOutputTypeName.getLocalName(), smType);


        return schema;
    }
    private SmType getSmTypeForFunctionArgumentRef(MutableSchema schema, Ontology ont,  String typeName, String baseUrl) {

        if (typeName == null) return null;
        if ("String".equalsIgnoreCase(typeName)) {
            return XSDL.STRING;
        }
        else if ("int".equalsIgnoreCase(typeName)) {
            return XSDL.INTEGER;
        }
        else if ("long".equalsIgnoreCase(typeName)) {
            return XSDL.LONG;
        }
        else if ("double".equalsIgnoreCase(typeName)) {
            return XSDL.DOUBLE;
        }
        else if ("boolean".equalsIgnoreCase(typeName)) {
            return XSDL.BOOLEAN;
        }
        else if ("DateTime".equalsIgnoreCase(typeName)) {
            return XSDL.DATETIME;
        }
        else if ("Concept".equalsIgnoreCase(typeName)) {
            return  _baseConceptType;
        }
        else if ("Event".equalsIgnoreCase(typeName)) {
            return _baseEventType;
        }
        else if ((typeName.endsWith("." + TYPE_CONCEPT)) || (typeName.endsWith("." + TYPE_EVENT))) {
            String loc = typeName;
            String scp = typeName.substring(0, typeName.indexOf('.'));
            String absolute = ResolverUtilities.makeAbsolute(baseUrl, loc.startsWith("/") ? loc.substring(1) : loc);
           return this.getEntityTypeReference(schema, scp, absolute);
		} else if (typeName != null && !typeName.isEmpty()) {
			RDFBaseTerm term = null;
			String runtimeClassName = com.tibco.be.model.util.ModelNameUtil
					.modelPathToGeneratedClassName(typeName);
			String loc = typeName;
			String scp = typeName.substring(0, typeName.indexOf('.'));
			String absolute = ResolverUtilities.makeAbsolute(baseUrl,
					loc.startsWith("/") ? loc.substring(1) : loc);
			//
			// term = new RDFBaseEntityTerm(runtimeClassName, true);
			// MutableType type = (MutableType) term.getXSDLTerm();
			// type.setExpandedName(ExpandedName.makeName((BE_NAMESPACE+typeName,localName))
			return XSDL.ANY_TYPE;
		}

        return XSDL.ANY_TYPE;
    }
    private SmType getSmTypeForFunctionArgument(MutableSchema schema, Ontology ont,  String typeName, String baseUrl) {

        if (typeName == null) return null;
        if ("String".equalsIgnoreCase(typeName)) {
            return XSDL.STRING;
        }
        else if ("int".equalsIgnoreCase(typeName)) {
            return XSDL.INTEGER;
        }
        else if ("long".equalsIgnoreCase(typeName)) {
            return XSDL.LONG;
        }
        else if ("double".equalsIgnoreCase(typeName)) {
            return XSDL.DOUBLE;
        }
        else if ("boolean".equalsIgnoreCase(typeName)) {
            return XSDL.BOOLEAN;
        }
        else if ("DateTime".equalsIgnoreCase(typeName)) {
            return XSDL.DATETIME;
        }
        else if ("Concept".equalsIgnoreCase(typeName)) {
            return  _baseConceptType;
        }
        else if ("Event".equalsIgnoreCase(typeName)) {
            return _baseEventType;
        }
        else if ((typeName.endsWith("." + TYPE_CONCEPT)) || (typeName.endsWith("." + TYPE_EVENT))) {
            String loc = typeName;
            String scp = typeName.substring(0, typeName.indexOf('.'));
            String absolute = ResolverUtilities.makeAbsolute(baseUrl, loc.startsWith("/") ? loc.substring(1) : loc);
           return this.getEntityTypeReference(schema, scp, absolute);
        }

        return XSDL.ANY_TYPE;
    }


    private TnsDocument createDocument() {
        return new TnsFragmentedDocument() {
        };
    }

    protected void clearAllParticles(MutableType type) {
        SmModelGroup grp  = type.getContentModel();
        if (grp == null) return;
        Iterator itr = grp.getParticles();
        while (itr.hasNext()) {
            SmParticle particle = (SmParticle) itr.next();
            SmParticleTerm particleTerm=particle.getTerm();
            itr.remove();
        }
        return;
    }

    /**
     *
     * @param schemaFragment
     * @param importNodes
     * @param baseURL
     */
    protected static void addImports(TnsFragment schemaFragment,
                                   Iterator importNodes,
                                   String baseURL, String systemId) {
        while (importNodes.hasNext()) {
            XiNode node = (XiNode) importNodes.next();
            String ns =  node.getAttributeStringValue(ExpandedName.makeName("namespace"));
            String loc =  node.getAttributeStringValue(ExpandedName.makeName("schemaLocation"));
            String absolute = ResolverUtilities.makeAbsolute(baseURL, loc.startsWith("/") ? loc.substring(1) : loc);
            ns=NoNamespace.normalizeNamespaceURI(ns);
            if (ns.indexOf("no_namespace_schema") > 0) {
                System.out.println("No Namespace.... " + ns);
                TnsImport imprt = new TnsImportImpl(absolute, null, null, null );
                schemaFragment.addImport(imprt);
            } else {
                TnsImport imprt = new TnsImportImpl(absolute, null, ns, SmNamespace.class);
                schemaFragment.addImport(imprt);
            }
        }
    }

    protected void eventToPayload(MutableSchema schema, Event event, MutableType eventType,String baseURL, String systemId, TnsFlavorContext tnsFlavorContext) throws Exception {
        MutableType payloadType = null;
        SmParticleTerm term = null;
        boolean isTerm = false;

        XiNode eventNode=((DefaultMutableEvent) event).toXiNode(XiFactoryFactory.newInstance());
        XiNode payloadPropertyNode = XiChild.getChild(eventNode, DefaultMutableEvent.PROPERTY_SCHEMA_NAME);
        if (payloadPropertyNode == null) {
            return;
        }

        try {
            Iterator allImportNodes= XiNodeParticleTermParser.getImportNodes(XiChild.getRootElementOrSelf(XiChild.getChild(payloadPropertyNode, EVENT_BODY)));
            addImports(schema.getSingleFragment(),allImportNodes, baseURL, systemId);

            term = XiNodeParticleTermParser.getTerm(XiChild.getChild(payloadPropertyNode, EVENT_BODY), (DefaultSchema) schema);
            if (term == null) {
                if (isSoapEvent(event)) {
                    payloadType = (MutableType)MutableSupport.createComponent((DefaultSchema)schema, SmComponent.TYPE_TYPE);
                    MutableType envT = createBaseSoapSchema(schema);
                    MutableSupport.addOptionalLocalElement(payloadType, "Envelope", envT);
                    MutableSupport.addLocalElement(eventType, EVENT_BODY.getLocalName(), payloadType, 0,1);  //Payload
                }
                return;
            }
            if (term.getComponentType() == SmComponent.ELEMENT_TYPE) {
                payloadType = (MutableType)MutableSupport.createComponent((DefaultSchema)schema, SmComponent.TYPE_TYPE);
                String tNs = term.getNamespace();
                if ((tNs == null) || (tNs.indexOf("no_namespace_schema") > 0)) {
                    ((MutableElement)term).setNamespace(NoNamespace.URI);
//                    MutableSchema nons = mcf.createSchema();
//                    nons.setNamespace(NoNamespace.URI);
//                    MutableComponent mc = MutableSupport.createComponent(nons, SmComponent.ELEMENT_TYPE);
//                    mc.setName(term.getName());
//
//                    MutableComponent mtype = MutableSupport.createComponent(nons, SmComponent.TYPE_TYPE);
//                    mtype.setName(((SmElement)term).getType().getName());
//
//                    ((MutableElement)mc).setType((SmType)mtype);
//                    term = (SmParticleTerm) mc;
////                    MutableElement me = (MutableElement) term.clone();
////                    me.setNamespace(NoNamespace.URI);
//
//                    //((MutableElement)term).setNamespace(NoNamespace.URI);
////                    SmNamespaceProvider smp = tnsFlavorContext.getNamespaceProvider();
////
////                    Iterator r = tnsFlavorContext.getNamespaceProvider().getNamespaces();
////                    while (r.hasNext()) {
////                    SmNamespace sn = (SmNamespace) r.next();
////                        System.out.println("sn : " + sn.getNamespace());
////                    TnsComponent tnsComp = sn.getComponent(term.getName(), SmComponent.ELEMENT_TYPE );
////                    System.out.println("tnsComp" + tnsComp);
////                    term = (SmParticleTerm) tnsComp;
//                    }
                }
               if (isSoapEvent(event)) {
                    MutableType envT = createSoapSchema(schema, payloadType, term);
                    if (envT != null) {
                        MutableSupport.addOptionalLocalElement(payloadType, "message", envT);
                    }
                } else {
                    MutableSupport.addOptionalElement(payloadType, (SmElement)term);
                }

                if (PayloadValidationHelper.ENABLED) {
                    XiNode node = XiChild.getChild(payloadPropertyNode, EVENT_BODY);
                    if (null != node) {
                        node = node.getFirstChild();
                        if ((null != node) && !XNAME_ANY.equals(node.getName())) {
                            final SmParticleTerm pt = XiNodeParticleTermParser.getTerm(node.getParentNode(), (DefaultSchema) schema);
                            schema.addSchemaComponent(pt);
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
                for (Iterator i = ((SmModelGroup) term).getParticles(); i.hasNext(); ) {
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
        }
        catch (Exception e) {
            //TODO - ERROR HANDLING
            e.printStackTrace();
        }
        MutableSupport.addLocalElement(eventType, EVENT_BODY.getLocalName(), payloadType, 0,1);  //Payload

        return;
    }

    /**
     *
     * @param schema
     * @param event
     * @param eventType
     * @param isBaseType
     * @throws Exception
     */
    private void eventToUserProperties(SmSchema schema,
                                       Event event,
                                       MutableType eventType,
                                       boolean isBaseType) throws Exception {

        Iterator props= event.getUserProperties();
        if (props != null) {
            while(props.hasNext()) {
                EventPropertyDefinition propDefn = (EventPropertyDefinition) props.next();
                String name = (String) propDefn.getPropertyName();
                RDFPrimitiveTerm type = (RDFPrimitiveTerm) propDefn.getType();
                SmType xsdType=type.getXSDLTerm();
                if (!isBaseType) {
                	MutableElement e = MutableSupport.addLocalElement(eventType, name, xsdType, 0, 1);
                	DefaultMetaForeignAttribute defaultMetaForeignAttribute = new DefaultMetaForeignAttribute(NILLABLE_NAME, "false");
                    defaultMetaForeignAttribute.setNamespaceContext(new DefaultNamespaceContext());
                	e.addForeignAttribute(defaultMetaForeignAttribute);
                } else {
                    if(type == RDFTypes.EXCEPTION) {
                        MutableSupport.addLocalElement(eventType, name, eventType, 0, 1);
                    } else {
                        MutableSupport.addOptionalAttribute(eventType,name,xsdType);
                    }
                    
                }
            }
            return;
        }
    }

    private MutableSchema eventToSmType(SmNamespaceProvider nsp, Event event, String location,TnsFlavorContext tnsFlavorContext) throws Exception {
        MutableSchema schema = getSchema(event,true);
        MutableType smType = getSmType(schema,ExpandedName.makeName(schema.getNamespace(), event.getName()),true);

        boolean isBaseType=false;
        boolean isTimeEvent = event.getType() == Event.TIME_EVENT;
        String baseURL = getBaseURL(location, event.getFullPath(), !isTimeEvent? TYPE_EVENT: TYPE_TIMEEVENT);

        SmType baseEvent=null;

        if (!isTimeEvent && location.equals(BASE_ENTITY_RESOURCE_LOCATION)) {
            isBaseType=true;
            _baseEntityType=smType;
        } else if (!isTimeEvent && location.equals(BASE_EVENT_RESOURCE_LOCATION)) {
            isBaseType=true;
            _baseEventType=smType;
        } else if (!isTimeEvent && location.equals(BASE_SIMPLEEVENT_RESOURCE_LOCATION)) {
            isBaseType = true;
            _baseSimpleEventType=smType;
        } else if (!isTimeEvent && location.equals(BASE_EXCEPTION_RESOURCE_LOCATION)) {
            isBaseType=true;
            _baseExceptionType=smType;
        } else if (!isTimeEvent && location.equals(BASE_ADVISORY_EVENT_RESOURCE_LOCATION)) {
            isBaseType=true;
            _baseAdvisoryEventType=smType;
        } else if (isTimeEvent && location.equals(BASE_TIMEEVENT_RESOURCE_LOCATION)){
            isBaseType=true;
            _baseTimeEventType=smType;
        } else if (!isTimeEvent && location.equals(BASE_SOAPEVENT_RESOURCE_LOCATION)) {
            isBaseType=true;
            _baseSOAPEventType=smType;
        }

        if (!isBaseType) {
            smType.setDerivationMethod(SmComponent.EXTENSION);
            if (!isTimeEvent) {
                String scp = event.getSuperEventPath();
                if(RDFTypes.SOAP_EVENT.getName().equals(scp)){
                    baseEvent = this.getBaseSOAPEventTypeReference(schema);
                } else if ((scp != null) && !(scp.trim().equals(""))){
                    String loc = scp + "." + TYPE_EVENT ;
                    String absolute = ResolverUtilities.makeAbsolute(baseURL, loc.startsWith("/") ? loc.substring(1) : loc);
                    baseEvent = this.getEntityTypeReference(schema, scp, absolute);
                } else {
                    baseEvent = this.getBaseEventTypeReference(schema);
                }
            } else {
                baseEvent=this.getBaseTimeEventTypeReference(schema);
            }
        }

        smType.setBaseType(baseEvent);

        // User Properties
        eventToUserProperties(schema,event, smType, isBaseType);

        // Payload Code
        if (!isTimeEvent && !isBaseType) {
            eventToPayload(schema,event, smType,baseURL, location, tnsFlavorContext);
        }
        return schema;
    }

    protected MutableType createBaseSoapSchema(MutableSchema schema) {
        MutableType envTRef = (MutableType) MutableSupport.createComponentRef(schema, SmComponent.TYPE_TYPE);
        envTRef.setReference(schema);
        envTRef.setExpandedName(ExpandedName.makeName(SOAP_NAMESPACE, "Envelope"));
        return envTRef;
    }

    /*
     * Create a SOAP Schema, for the given event payload.
     * The payload is expected in a special xml format
     * e.g. message/Envelope/Header, Body/Fault 
     */
    protected MutableType createSoapSchema(MutableSchema schema,
                                         MutableType payloadType,
                                         SmParticleTerm term) {

    	MutableType msgT = null;
        MutableType envT = null;
        Iterator headerParts = null;
        Iterator bodyParts = null;
        Iterator faultParts = null;

        SmParticle envP = null;
        SmParticle bodyP = null;
        SmParticle faultP = null;

        try {
            MutableElement e = (MutableElement) term;
            msgT = (MutableType) e.getType();
            envP = getParticleByName(e, "Envelope");
            envT = (MutableType) ((SmElement)envP.getTerm()).getType();
            
            Iterator rootParts = envT.getContentModel().getParticles();
            while (rootParts.hasNext()) {
                SmParticle p = (SmParticle) rootParts.next();
                if (p.getTerm().getName().equalsIgnoreCase("Header")) {
                    headerParts = getParticleParts(p);
                } else if (p.getTerm().getName().equalsIgnoreCase("Body")) {
                    bodyP = p;
                    bodyParts = getParticleParts(p);
                    faultP = getParticleByName((MutableElement)bodyP.getTerm(), "Fault");
                    faultParts = getParticleParts(faultP);
                }
            }

			msgT = MutableSupport.createType(schema, null, null);
			msgT.setComplex();
			MutableSupport.createElement(schema, "message", msgT);
            
			ExpandedName envName = ExpandedName.makeName(SOAP_NAMESPACE, "Envelope");
			envT = createSmType(envName, schema);
			MutableElement envE = createSmElement(envName, envT);
			MutableSupport.addElement(msgT, envE, 0, 1);

            if (headerParts != null) {
            	ExpandedName headerName = ExpandedName.makeName(SOAP_NAMESPACE, "Header");
            	MutableType headT = createSmType(headerName, schema);
            	MutableElement headerE = createSmElement(headerName, headT);
            	MutableSupport.addElement(envT, headerE, 0, 1);
                while (headerParts.hasNext()) {
                    SmParticle p = (SmParticle) headerParts.next();
                    addNsToType(schema, headT, p, schema.getNamespaceURI()+"/header");
                }
            }

        	ExpandedName bodyName = ExpandedName.makeName(SOAP_NAMESPACE, "Body");
        	MutableType bodyT = createSmType(bodyName, schema);
        	MutableElement bodyE = createSmElement(bodyName, bodyT);
        	MutableSupport.addElement(envT, bodyE, 0, 1);
            if (bodyParts != null) {
                while (bodyParts.hasNext()) {
                    SmParticle p = (SmParticle) bodyParts.next();
                    if(p.getTerm().getName().equalsIgnoreCase("Fault")){
                        continue;
                    }
                    addNsToType(schema, bodyT, p, schema.getNamespaceURI()+"/body");
                }
            }

            if (faultP != null) {
                createFaultType(schema, bodyT, faultParts);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return msgT;

    }

    /*
     * Create a Fault Type and add it to Body Type. And add fault parts to
     * Fault/Detail Type
     * 
     */
    private MutableType createFaultType(MutableSchema schema, MutableType onType, Iterator faultParts) {
    	ExpandedName faultName = ExpandedName.makeName(SOAP_NAMESPACE, "Fault");
    	MutableType faultT = createSmType(faultName, schema);
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
                    if(particle.getTerm().getName().equalsIgnoreCase(termName) && NoNamespace.URI == particle.getTerm().getNamespace()){
                        return particle;
                    }
                }
            }
        }
        return null;
    }

//    /*
//     * Add SOAP namespace qualified attributes to this type
//     */
//    private static void addSoapHeaderAttribs(MutableType onType) {
//        MutableParticle p = null;
//
//    	MutableComponentFactoryTNS mcf= DefaultComponentFactory.getTnsAwareInstance();
//        
//        MutableAttributeGroup attrGrp = mcf.createAttributeGroupRef();
//
//        MutableAttribute mustUnderstand = mcf.createAttributeRef();
//        mustUnderstand.setExpandedName(ExpandedName.makeName(SOAP_NAMESPACE, "mustUnderstand"));
//        mustUnderstand.setSchema(onType.getSchema());
//        
//        p = mcf.createParticle();
//        p.setMinOccurrence(0);
//        p.setMaxOccurrence(1);
//        p.setTerm(mustUnderstand);
//        attrGrp.addParticle(p);
//        
//        MutableAttribute role = mcf.createAttributeRef();
//        role.setExpandedName(ExpandedName.makeName(SOAP_NAMESPACE, "actor"));
//        role.setSchema(onType.getSchema());
//        
//        p = mcf.createParticle();
//        p.setMinOccurrence(0);
//        p.setMaxOccurrence(1);
//        p.setTerm(role);
//        attrGrp.addParticle(p);
//        
//        onType.setAttributeModel(attrGrp);
//    }

    /*private static MutableType addNsToTypeAsBase (MutableSchema schema, MutableType onType, SmParticle p) {
        ExpandedName en = p.getTerm().getExpandedName();
        schema.getSingleFragment().addImport(new TnsImportImpl(null , null, en.namespaceURI ,SmNamespace.class));
        MutableType myComp = (MutableType) MutableSupport.createComponentRef(schema, SmComponent.TYPE_TYPE);
        myComp.setReference(schema);
        myComp.setExpandedName(en);
        MutableType t = MutableSupport.createType(schema, null, myComp);
        t.setDerivationMethod(SmComponent.EXTENSION);
        t.setComplex();
        MutableSupport.addLocalElement(onType, en.localName, t, p.getMinOccurrence(), p.getMaxOccurrence());
        return t;
    }*/

    /*
     * Add type-ref/type/element-ref to onType. Add attributes soap:actor and soap:mustUnderstand
     * to immidiate children of soap:Header.
     * 
     * Limitations: 
     * 1. Adding attributes to Element Reference does not work.
     *    Thats why not supporting Element Reference at present.
     * 2. Also supporting Element type only. Attribute, WildCard
     *    are not supported yet.
     */
    private void addNsToType (MutableSchema schema,
    		MutableType onType,
    		SmParticle p,
    		String nameSpaceUri) {
    	SmParticleTerm pTerm = p.getTerm();
    	if(!(pTerm instanceof SmElement)){ //Supporting Element type only
    		System.out.println("RDFTnsFlavor.addNsToType() can handle SmParticleTerm instanceof SmElement only. Component Type is " + pTerm.getComponentType());
    		return;
    	}
    	int min = p.getMinOccurrence();
    	int max = p.getMaxOccurrence();
    	SmElement element = (SmElement) pTerm;
    	SmType type = element.getType();
    	if (type == null) {// Element referenced from schema

			ExpandedName en = element.getExpandedName();
			MutableComponent c = createComponentRef(schema, en,
					SmComponent.ELEMENT_TYPE);
			MutableSupport.addElement(onType, (MutableElement) c, min, max);
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
    
    private MutableComponent createComponentRef(MutableSchema schema, ExpandedName en, int type){
		MutableComponent mutableComponent =
			MutableSupport.createComponentRef(schema, type);
		mutableComponent.setReference(schema);
		mutableComponent.setExpandedName(en);
		return mutableComponent;
    }
    
    protected boolean isSoapEvent (Event event) {
        String eventSuperPath = (String) event.getSuperEventPath();
        boolean isSoapEvent = RDFTypes.SOAP_EVENT.getName().equals(eventSuperPath);
        return isSoapEvent;
    }
}
