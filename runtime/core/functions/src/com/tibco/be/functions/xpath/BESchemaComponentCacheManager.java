package com.tibco.be.functions.xpath;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLStreamException;

import org.genxdm.bridgekit.misc.StringToURIParser;
import org.genxdm.bridgekit.xs.DefaultCatalog;
import org.genxdm.bridgekit.xs.DefaultCatalogResolver;
import org.genxdm.bridgekit.xs.DefaultSchemaCatalog;
import org.genxdm.bridgekit.xs.SchemaCacheFactory;
import org.genxdm.exceptions.GenXDMException;
import org.genxdm.exceptions.PreCondition;
import org.genxdm.io.Resolved;
import org.genxdm.processor.w3c.xs.W3cXmlSchemaParser;
import org.genxdm.processor.wsdl.Cache;
import org.genxdm.processor.wsdl.io.Builder;
import org.genxdm.processor.wsdl.io.Parser;
import org.genxdm.typed.TypedContext;
import org.genxdm.wsdl.ModuleCache;
import org.genxdm.wsdl.io.ModuleBuilder;
import org.genxdm.wsdl.io.WsdlParser;
import org.genxdm.xs.ComponentBag;
import org.genxdm.xs.ComponentProvider;
import org.genxdm.xs.SchemaComponentCache;
import org.genxdm.xs.exceptions.SchemaException;
import org.genxdm.xs.exceptions.SchemaExceptionCatcher;
import org.genxdm.xs.resolve.CatalogResolver;
import org.genxdm.xs.resolve.SchemaCatalog;
import org.xml.sax.InputSource;

import com.tibco.be.model.rdf.RDFTnsFlavor;
import com.tibco.be.util.FastTargetNamespaceParser;
import com.tibco.be.util.SchemaGenerator;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.DeployedProject;
import com.tibco.cep.repo.Project;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.genxdm.bridge.xinode.XiProcessingContext;
import com.tibco.xml.data.primitive.XmlAtomicValue;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.schema.SmSchema;
import com.tibco.xml.schema.build.MutableElement;
import com.tibco.xml.schema.build.MutableSchema;

public class BESchemaComponentCacheManager {

	private class BEComponentResolver implements CatalogResolver {
		private List<Resolved<InputStream>> resources;
		private Map<String, Resolved<InputStream>> nsToResourceMap = new HashMap<String, Resolved<InputStream>>();

		public BEComponentResolver(List<Resolved<InputStream>> resources) {
			this.resources = resources;
		}

		@Override
		public InputStream resolveInputStream(URI uri) throws IOException {
//			if (uri.toString().startsWith(TypeManager.DEFAULT_BE_NAMESPACE_URI)) {
			if (!uri.isAbsolute()) {
				InputStream stream = resolveBEInputStream(uri);
				if (stream != null) {
					return stream;
				}
			}
			// try looking up from registered resources based on ns URI
			Resolved<InputStream> resolved = resolveMappedResource(uri);
			if (resolved != null) {
				InputStream res = resolved.getResource();
				if (res.available() == 0) {
					res.reset();
				}
				return res;
			}

			// try to use the default resolver
			return DefaultCatalogResolver.SINGLETON.resolveInputStream(uri);
		}

		public Resolved<InputStream> resolveMappedResource(URI uri) {
			Resolved<InputStream> resolved = nsToResourceMap.get(uri.toString());
			return resolved;
		}

		private InputStream resolveBEInputStream(URI uri) throws IOException {

		    Iterator<Resolved<InputStream>> iterator = resources.iterator();
			while (iterator.hasNext()) {
				Resolved<java.io.InputStream> resolved = (Resolved<java.io.InputStream>) iterator
						.next();
				if (uri.toString().equals(resolved.getLocation()) || uri.toString().equals(resolved.getSystemId())) {
					InputStream res = resolved.getResource();
					if (res.available() == 0) {
						res.reset();
					}
					return res;
				}
			}
			Resolved<InputStream> resolved = resolveMappedResource(uri);
			if (resolved != null) {
				InputStream res = resolved.getResource();
				if (res.available() == 0) {
					res.reset();
				}
				return res;
			}

			if ("http".equals(uri.getScheme())) {
				return uri.toURL().openStream();
			}
			logger.log(Level.ERROR, "Unable to find input source for '"+uri.toString()+"'. Ensure that the resource exists");
			return null;
		}

		public void mapNStoResource(Resolved<InputStream> resource,
				String namespaceURI) {
			if (!nsToResourceMap.containsKey(namespaceURI)) {
				nsToResourceMap.put(namespaceURI, resource);
			}
		}
		
	}
	
	private class BESchemaCatalog implements SchemaCatalog {
		private DefaultSchemaCatalog defaultSchemaCatalog;
		
		public BESchemaCatalog() {
			defaultSchemaCatalog = new DefaultSchemaCatalog(new DefaultCatalog());
		}

		@Override
		public URI resolveNamespaceAndSchemaLocation(URI baseURI, String namespace, String schemaLocation) {
			if (schemaLocation == null) {
				URI uri = resolveLocation(baseURI, namespace);
				if (uri != null) {
					Resolved<InputStream> res = resolver.resolveMappedResource(uri);
					if (res != null && res.getLocation().endsWith(".wsdl")) {
						// see below regarding BE-22674, BE-22558 : This occurs for SOAP Events where the payload points to a schema in a wsdl file
						return null;
					}
				}
				return uri;
			}
			Resolved<InputStream> resource = resolver.resolveMappedResource(resolveLocation(baseURI, schemaLocation));
			if (resource == null && baseURI.getScheme() != null) {
				// the baseURI is actually the namespace, look up the base resource and use that location as the baseURI
				Resolved<InputStream> baseResource = resolver.resolveMappedResource(baseURI);
				if (baseResource != null) {
					baseURI = URI.create(baseResource.getLocation());
				}
			}
			if (resource != null) {
				if (resource.getLocation().endsWith(".wsdl")) {
					// BE-22674, BE-22558 : There is an issue with the genxdm processing of wsdl imports, where it assumes that everything
					// that gets imported is an xsd (and so attempts to parse it as an xsd).  This fails, throwing errors.  The "fix" is
					// to return null here, which will later resolve the wsdl definition from the schema cache.  We might be able to
					// return null for all things that exist in the nsToResourceMap, but for now, return null only for wsdl references
					return null;
				}
			}
//			if (namespace.startsWith(TypeManager.DEFAULT_BE_NAMESPACE_URI)) {
				return resolveBENSLocation(baseURI, namespace, schemaLocation);
//			}
//			URI uri = defaultSchemaCatalog.resolveNamespaceAndSchemaLocation(baseURI, namespace, schemaLocation);
//			return uri;
		}

		private URI resolveBENSLocation(URI baseURI, String namespace, String schemaLocation) {
			// make URI relative
			int idx = schemaLocation.lastIndexOf('.');
			if (idx >= 0) {
				String fileExt = schemaLocation.substring(idx+1);
				if ("concept".equalsIgnoreCase(fileExt) || "event".equalsIgnoreCase(fileExt)) {
					// for BE entities, lookup via namespace, not location
					return resolveLocation(baseURI, namespace);
				}
			}
			if (schemaLocation.indexOf(':') != -1) {
				// location has a scheme (likely the namespace itself), just return the full location
				return resolveLocation(baseURI, schemaLocation);
			}
			String base = baseURI.toString();
			if (base.startsWith(TypeManager.DEFAULT_BE_NAMESPACE_URI)) {
				base = base.substring(TypeManager.DEFAULT_BE_NAMESPACE_URI.length());
			}
			idx = base.lastIndexOf('/');
			if (idx >= 0) {
				base = base.substring(0, idx+1);
			}
			String[] baseSplit = base.split("\\/");
			String[] split = schemaLocation.split("\\/");
			int count = 0;
			for (String seg : split) {
				if ("..".equals(seg)) {
					count++;
				} else {
					break;
				}
			}
			String fullPath = "";
			for (int i = 0; i < baseSplit.length-count; i++) {
				fullPath += baseSplit[i] + "/";
			}
			for (int i = count; i < split.length; i++) {
				if (".".equals(split[i])) {
					continue;
				}
				fullPath += split[i];
				if (i < split.length - 1) {
					fullPath += "/";
				}
			}
//			String fullPath = base + schemaLocation;
			return resolveLocation(baseURI, fullPath);
		}

		@Override
		public URI resolveLocation(URI baseURI, String schemaLocation) {
			if (schemaLocation != null /*&& schemaLocation.startsWith(TypeManager.DEFAULT_BE_NAMESPACE_URI)*/) {
				if (baseURI.getScheme() == null && (schemaLocation.startsWith(".") || schemaLocation.indexOf('/') == -1)) {
					// relative path OR sibling of baseURI, use the defaultSchemaCatalog to resolve from the baseURI
					return defaultSchemaCatalog.resolveLocation(baseURI, schemaLocation);
				}
				return StringToURIParser.parse((String)PreCondition.assertNotNull(schemaLocation, "schemaLocation"));
			}
			return defaultSchemaCatalog.resolveLocation(baseURI, schemaLocation);
		}
		
	}
	
    private static Logger logger;

    // Initialize this class //
    static {
        logger = LogManagerFactory.getLogManager().getLogger(BESchemaComponentCacheManager.class);
    }
	private static BESchemaComponentCacheManager INSTANCE;
//	private Map<String, SchemaComponentCache> cacheMap = new HashMap<String, SchemaComponentCache>();
	SchemaCacheFactory factory = new SchemaCacheFactory();
	SchemaComponentCache cache;
	TypedContext<XiNode,XmlAtomicValue> typedContext;
	private BEComponentResolver resolver;
	
	public synchronized static BESchemaComponentCacheManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new BESchemaComponentCacheManager();
		}
		return INSTANCE;
	}
	
	public synchronized TypedContext<XiNode, XmlAtomicValue> getTypedContext() {
		if (typedContext == null) {
			XiProcessingContext ctx = new XiProcessingContext();
			typedContext = ctx.getTypedContext(getCache());
			initCache(typedContext);
		}
		return typedContext;
	}


	public SchemaComponentCache getCache() {
		if (cache == null) {
			cache = factory.newSchemaCache();//initCache(null);
		}
		return cache;
	}
	
	private SchemaComponentCache initCache(TypedContext<XiNode,XmlAtomicValue> typedContext) {
		DeployedProject project = RuleServiceProviderManager.getInstance().getDefaultProvider().getProject();
		
		System.out.println("Generating XSDs...... ");
		Ontology ontology = project.getOntology();
		SchemaGenerator generator;
		try {
			generator = new SchemaGenerator(project);
			Collection<Entity> concepts = ontology.getEntities();
			List<Resolved<InputStream>> resources = new ArrayList<>();
			resolver = new BEComponentResolver(resources);
			collectSchemas(project, resources);
			generateSchemas(generator, project, concepts, resources);
			addSchemas(typedContext, resources);
		} catch (Exception e1) {
			e1.printStackTrace();
			logger.log(Level.ERROR, e1.getMessage());
		}

		return cache;
	}

	private void collectSchemas(DeployedProject project,
			List<Resolved<InputStream>> resources) {
		Collection allResourceURI = project.getSharedArchiveResourceProvider().getAllResourceURI();
		Iterator iterator = allResourceURI.iterator();
		
		while (iterator.hasNext()) {
			String uri = (String) iterator.next();
			if (uri.endsWith("xsd") || uri.endsWith("wsdl")) {
				byte[] resourceAsByteArray = project.getSharedArchiveResourceProvider().getResourceAsByteArray(uri);
				ByteArrayInputStream bis = new ByteArrayInputStream(resourceAsByteArray);
				Resolved<InputStream> res = new Resolved<InputStream>(uri, bis, uri);

				try {
					SAXParser newSAXParser = SAXParserFactory.newInstance().newSAXParser();
					FastTargetNamespaceParser handler = new FastTargetNamespaceParser();
					newSAXParser.parse(new InputSource(bis), handler);
					List<String> targetNamespaces = handler.getTargetNamespaces();
					if (targetNamespaces != null) {
						for (String targetNamespace : targetNamespaces) {
							resolver.mapNStoResource(res, targetNamespace);
						}
					}
				} catch (Exception e) {
				}
				resources.add(res);
			}
		}
	}

	private void generateSchemas(SchemaGenerator generator, Project project, Collection<Entity> entities, List<Resolved<InputStream>> resources) throws Exception {
		{
			// Base Concept
			SmSchema schema = RDFTnsFlavor.getBaseConceptType().getSchema();
			String xsd = generator.generateSchema(schema,"",RDFTnsFlavor.getBaseConceptType().getName());
			ByteArrayInputStream bis = new ByteArrayInputStream(xsd.getBytes("UTF-8"));
			Resolved<InputStream> res = new Resolved<InputStream>(schema.getLocation(), bis, schema.getNamespace());
			resources.add(res);
		}
		
		{
			// Base Event
			SmSchema schema = RDFTnsFlavor.getBaseEventType().getSchema();
			String xsd = generator.generateSchema(schema,"",RDFTnsFlavor.getBaseEventType().getName());
			ByteArrayInputStream bis = new ByteArrayInputStream(xsd.getBytes("UTF-8"));
			Resolved<InputStream> res = new Resolved<InputStream>(schema.getLocation(), bis, schema.getNamespace());
			resources.add(res);
		}
		
		{
			// Base SOAP Event
			SmSchema schema = RDFTnsFlavor.getBaseSOAPEventType().getSchema();
			String xsd = generator.generateSchema(schema,"",RDFTnsFlavor.getBaseSOAPEventType().getName());
			ByteArrayInputStream bis = new ByteArrayInputStream(xsd.getBytes("UTF-8"));
			Resolved<InputStream> res = new Resolved<InputStream>(schema.getLocation(), bis, schema.getNamespace());
			resources.add(res);
		}
		
		{
			// Base Time Event
			SmSchema schema = RDFTnsFlavor.getBaseTimeEventType().getSchema();
			String xsd = generator.generateSchema(schema,"",RDFTnsFlavor.getBaseTimeEventType().getName());
			ByteArrayInputStream bis = new ByteArrayInputStream(xsd.getBytes("UTF-8"));
			Resolved<InputStream> res = new Resolved<InputStream>(schema.getLocation(), bis, schema.getNamespace());
			resources.add(res);
		}
		
		{
			// envelope.xsd
			URL envelopeURL = getClass().getClassLoader().getResource(RDFTnsFlavor.ENVELOPE_RESOURCE_LOCATION);
			if (envelopeURL != null) {
				Resolved<InputStream> res = new Resolved<InputStream>(RDFTnsFlavor.SOAP_NAMESPACE, envelopeURL.openStream(), RDFTnsFlavor.SOAP_NAMESPACE);
				resources.add(res);
				// map built in resource to avoid accessing remote URL
				resolver.mapNStoResource(res, RDFTnsFlavor.SOAP_NAMESPACE);
			}
		}
		
		for (Iterator itr = entities.iterator(); itr.hasNext();) {
			Entity ee = (Entity) itr.next();
			MutableElement smElement = (MutableElement) project.getSmElement(ee);
			if (smElement == null) {
				continue;
			}
			MutableSchema schema = (MutableSchema) smElement.getSchema();
			String xsdNamespace = ee.getNamespace();
			String xsdName = ee.getName();
			boolean isSoapEvent = false;
			if (ee instanceof Event) {
				isSoapEvent = ((Event) ee).isSoapEvent();
			}
//			if (isSoapEvent) {
//				Map<String, String> schemas = generator.generateEnvelopeSchema((Event) ee);
//				Iterator<String> iterator = schemas.keySet().iterator();
//				while (iterator.hasNext()) {
//					String ns = (String) iterator.next();
//					String xsd = schemas.get(ns);
//					System.out.println("SOAP Part: \n"+xsd);
//					ByteArrayInputStream bis = new ByteArrayInputStream(xsd.getBytes("UTF-8"));
//					Resolved<InputStream> res = new Resolved<InputStream>(schema.getLocation(), bis, ns);
//					ns = ns.substring(RDFTnsFlavor.BE_NAMESPACE.length());
//					resolver.mapNStoResource(res, ns);
//					resources.add(res);
//				}
//				String xsd = generator.generateSoapEventSchema((Event) ee);
//				System.out.println("Schema for "+xsdName);
//				System.out.println(xsd);
//				ByteArrayInputStream bis = new ByteArrayInputStream(xsd.getBytes("UTF-8"));
//				Resolved<InputStream> res = new Resolved<InputStream>(schema.getLocation(), bis, schema.getNamespace());
//				resources.add(res);
//			} else {
				String xsd = generator.generateSchema(schema, xsdNamespace, xsdName);
//				logger.log(Level.INFO, "Schema for "+xsdName);
//				logger.log(Level.INFO, xsd);
				ByteArrayInputStream bis = new ByteArrayInputStream(xsd.getBytes("UTF-8"));
				Resolved<InputStream> res = new Resolved<InputStream>(schema.getLocation(), bis, schema.getNamespace());
				resources.add(res);
//			}
		}
	}

	protected <N, A> LinkedList<SchemaException> addSchemas(TypedContext<N, A> tpcx, List<Resolved<InputStream>> resources) throws Exception
	{
		// Load the schemas into the processing context.
		final SchemaExceptionCatcher errors = new SchemaExceptionCatcher();
		WsdlParser parser = new Parser();
		ModuleBuilder builder = new Builder();
		ModuleCache cache = new Cache();
		final W3cXmlSchemaParser schemaParser = new W3cXmlSchemaParser();
		parser.setSchemaParser(schemaParser);
		parser.setSchemaComponentCache(getCache());
		
		ComponentProvider bootstrap = tpcx.getSchema().getComponentProvider();
		schemaParser.setComponentProvider(bootstrap);
//		Catalog catalog = new DefaultCatalog();
//		parser.setCatalogResolver(resolver, new DefaultSchemaCatalog(catalog));
			
		SchemaCatalog dsc = new BESchemaCatalog();
		schemaParser.setCatalogResolver(resolver, dsc);
		parser.setCatalogResolver(resolver, dsc);
		
		for (final Resolved<InputStream> resource : resources)
		{
			try {
				// wrap individual resource parsing to allow continuation in the event of a failure
				// this can happen if, for instance, an event has an invalid import of a schema that does not exist.
				// this is not an error in the BE project, but it will throw an error here.
				InputStream stream = resource.getResource();
				if (stream.available() == 0) {
					stream.reset();
				}
				if (resource.getSystemId().endsWith("wsdl")) {
					parser.parse(stream, "UTF-8", builder, cache, resource.getSystemId()); 
					continue;
				}
				ComponentBag scBag = schemaParser.parse(resource.getLocation(), stream, resource.getSystemId(), errors);
				if(!errors.isEmpty())
				{
					for(SchemaException error : errors)
					{
						logger.log(Level.ERROR, "Error in resource "+resource.getSystemId()+" ["+error.getLocalizedMessage()+"]");
//						if (error instanceof SccLocationException) {
//							System.out.println("       at location "+((SccLocationException) error).getLocation().toString());
//						} else {
//							System.out.println();
//						}
					}
				}
				errors.clear();
				tpcx.getSchema().register(scBag);
			} catch (Exception e) {
				logger.log(Level.ERROR, "Unable to parse "+resource.getLocation()+". Reason: " + e.getMessage());
				if (e instanceof GenXDMException && ((GenXDMException)e).getCause() instanceof XMLStreamException) {
					XMLStreamException ex = (XMLStreamException) ((GenXDMException)e).getCause();
					if (ex.getCause() instanceof MalformedURLException) {
						// special error reporting for this error to provide user with a hint that there could be an invalid import in the resource (i.e. an Event importing an invalid resource)
						logger.log(Level.ERROR, "Perhaps an invalid import exists in the resource?");
					}
				}
			}
		}
		
		return errors;
	}


}
