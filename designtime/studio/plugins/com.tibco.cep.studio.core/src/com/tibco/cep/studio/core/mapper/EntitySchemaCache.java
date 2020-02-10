package com.tibco.cep.studio.core.mapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import javax.wsdl.Types;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.XSDSchemaExtensibilityElement;
import org.eclipse.wst.wsdl.util.WSDLResourceImpl;
import org.eclipse.xsd.XSDImport;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDSchemaContent;
import org.eclipse.xsd.impl.XSDImportImpl;
import org.eclipse.xsd.util.XSDConstants;
import org.eclipse.xsd.util.XSDResourceFactoryImpl;
import org.eclipse.xsd.util.XSDResourceImpl;
import org.eclipse.xsd.util.XSDSchemaLocationResolver;
import org.eclipse.xsd.util.XSDSchemaLocator;

import com.tibco.be.model.rdf.RDFTnsFlavor;
import com.tibco.be.util.config.sharedresources.id.IdPackage;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.studio.common.util.Path;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.mapper.resources.SchemaNSResolver;
import com.tibco.cep.studio.core.mapper.resources.StringXSDResourceImpl;
import com.tibco.cep.studio.core.mapper.resources.XSDEntityResourceImpl;
import com.tibco.cep.studio.core.util.SchemaGenerator;
import com.tibco.xml.mappermodel.emfapi.ResourceSetHelper;

public class EntitySchemaCache {

	private class SchemaLocator implements XSDSchemaLocator, XSDSchemaLocationResolver, Adapter {

		@Override
		public XSDSchema locateSchema(XSDSchema xsdSchema, String namespaceURI,
				String rawSchemaLocationURI, String resolvedSchemaLocationURI) {
//			if uri ends with "Envelope", need to get the event's payload schema
			if(namespaceURI != null && namespaceURI.equalsIgnoreCase(RDFTnsFlavor.SOAP_NAMESPACE) && rawSchemaLocationURI != null){
				URI uri = URI.createURI(rawSchemaLocationURI);
				Resource resource = fSchemaResourceSet.getResource(uri, false);
//				if (resource == null) {
//					if (rawSchemaLocationURI.endsWith("Envelope")) {
//						rawSchemaLocationURI = rawSchemaLocationURI.substring(0, rawSchemaLocationURI.length()-"/Envelope".length())+".event";
//					}
//					if (rawSchemaLocationURI.startsWith(RDFTnsFlavor.BE_NAMESPACE)) {
//						rawSchemaLocationURI = fProjectName + rawSchemaLocationURI.substring(RDFTnsFlavor.BE_NAMESPACE.length());
//					}
//					uri = URI.createPlatformResourceURI(rawSchemaLocationURI, false);
//					resource = fSchemaResourceSet.getResource(uri, false);
//				}
				XSDSchema schema = getSchemaFromResource(resource);
				if (schema != null) {
					return schema;
				}
			}
			/*
			if(namespaceURI.equalsIgnoreCase(RDFTnsFlavor.SOAP_NAMESPACE)) {
				URI uri = URI.createURI(namespaceURI);
				Resource resource = fSchemaResourceSet.getResource(uri, false);
				
				XSDSchema schema = getSchemaFromResource(resource);
				if (schema != null) {
					return schema;
				}
			}*/
			
			if (rawSchemaLocationURI == null) {
				URI uri = URI.createURI(namespaceURI);
				Resource resource = fSchemaResourceSet.getResource(uri, false);
				
				XSDSchema schema = getSchemaFromResource(resource);
				if (schema != null) {
					return schema;
				}
			}
			
			if (resolvedSchemaLocationURI != null) {
				URI uri = URI.createURI(resolvedSchemaLocationURI);
				if (uri.scheme() == "jar") {
					uri = toPlatformURI(resolvedSchemaLocationURI);
				}
				Resource resource = getResourceFromURI(uri);
				
				XSDSchema schema = getSchemaFromResource(resource);
				if (schema != null) {
					return schema;
				}
			}

			// look through all resources in the resource set to see if we can find a match
			// has a limitation if there are multiple resources with the same namespace
			if (namespaceURI != null) {
				XSDSchema schema = locateSchemaByNamespace(namespaceURI, rawSchemaLocationURI);
				if (schema != null) {
					return schema;
				}
			}
			
			if (resolvedSchemaLocationURI != null) {
				// web URI?
				URI uri = URI.createURI(resolvedSchemaLocationURI);
				if (uri.fileExtension() == null || "xsd".equalsIgnoreCase(uri.fileExtension())) {
					XSDResourceImpl res = new XSDResourceImpl(uri);
					try {
						res.load(new HashMap<>());
						XSDSchema webschema = res.getSchema();
						if (webschema != null) {
							fSchemaResourceSet.getResources().add(res);
							return webschema;
						}
					} catch (IOException e) {
						System.err.println(e.getLocalizedMessage());
						addError(e);
					}
				}
			}

			return null;
		}

		private XSDSchema locateSchemaByNamespace(String namespaceURI, String rawSchemaLocationURI) {
			EList<Resource> resources = fSchemaResourceSet.getResources();
			List<XSDSchema> matchingSchemas = new ArrayList<>();
			boolean collectAllSchemas = rawSchemaLocationURI != null;
			List<Resource> entityResources = new ArrayList<Resource>();
			for (int j = 0; j < resources.size(); j++) {
				Resource resource = resources.get(j);
				if (resource instanceof XSDEntityResourceImpl && rawSchemaLocationURI != null && "xsd".equalsIgnoreCase(new Path(rawSchemaLocationURI).getFileExtension())) {
					continue; // this can't resolve to an XSDEntityResource, don't force a load to avoid resolution issues
				}
				if (resource instanceof XSDEntityResourceImpl || resource instanceof StringXSDResourceImpl) {
					entityResources.add(resource);
					continue;
				}
				if (resource instanceof XSDResourceImpl) {
					if (!resource.isLoaded()) {
						try {
							resource.load(new HashMap<>());
						} catch (IOException e) {
							StudioCorePlugin.log("Unable to load resource "+resource.getURI(), e);
						};
					}
					XSDSchema schema = ((XSDResourceImpl) resource).getSchema();
					if (schema != null && namespaceURI.equals(schema.getTargetNamespace())) {
						if (collectAllSchemas || matchingSchemas.size() > 0) {
							matchingSchemas.add(schema);
						} else {
							return schema;
						}
					}
					continue;
				}
				if (resource instanceof WSDLResourceImpl) {
					if (!resource.isLoaded()) {
						try {
							resource.load(new HashMap<>());
						} catch (IOException e) {
							StudioCorePlugin.log("Unable to load resource "+resource.getURI(), e);
						};
					}
					Definition definition = ((WSDLResourceImpl) resource).getDefinition();
					Types types = definition.getTypes();
					if (types == null) {
						continue;
					}
					List extensibilityElements = types.getExtensibilityElements();
					for (int i = 0; i < extensibilityElements.size(); i++) {
						Object object = extensibilityElements.get(i);
						if (object instanceof XSDSchemaExtensibilityElement) {
							XSDSchema schema = ((XSDSchemaExtensibilityElement) object).getSchema();
							if (namespaceURI.equals(schema.getTargetNamespace())) {
//								if (collectAllSchemas) {
									matchingSchemas.add(schema);
//								} else {
//									return schema;
//								}
							}
						}
					}
					continue;
				}
			}
			if (!collectAllSchemas && matchingSchemas.size() > 0) {
				for (XSDSchema xsdSchema : matchingSchemas) {
					if (!(xsdSchema.eResource() instanceof WSDLResourceImpl)) {
						// return the first schema whose name matches the last segment of the relative location
						return xsdSchema;
					}
				}
			}

			if (matchingSchemas.size() > 0) {
				if (matchingSchemas.size() == 1) {
					return matchingSchemas.get(0);
				}
				// try to do a best guess as to the matching schema
				Path path = new Path(rawSchemaLocationURI);
				boolean xsd = "xsd".equalsIgnoreCase(path.getFileExtension());
				String lastSeg = path.lastSegment();
				
				// first look for matching names
				for (XSDSchema xsdSchema : matchingSchemas) {
					if (xsdSchema.getSchemaLocation() != null && new Path(xsdSchema.getSchemaLocation()).lastSegment().equals(lastSeg)) {
						// return the first schema whose name matches the last segment of the relative location
						return xsdSchema;
					}
				}
				// then defer to xsd over wsdl
				if (xsd) {
					for (XSDSchema xsdSchema : matchingSchemas) {
						if (xsdSchema.getSchemaLocation() != null && "xsd".equalsIgnoreCase(new Path(xsdSchema.getSchemaLocation()).getFileExtension())) {
							// return the first schema whose name matches the last segment of the relative location
							return xsdSchema;
						}
					}
				}
				// finally just return the first match
				return matchingSchemas.get(0);
			}
			
			Path nsPath = new Path(namespaceURI);
			String localName = nsPath.removeFileExtension().lastSegment();
			try {
				for (int j = 0; j < resources.size(); j++) {
					Resource resource = resources.get(j);
					// take a cursory pass through all to see if the last segment matches as an optimization
					if (localName.equals(new Path(resource.getURI().lastSegment()).removeFileExtension().lastSegment())) {
						if (!resource.isLoaded()) {
							try {
								resource.load(new HashMap<>());
							} catch (IOException e) {
								StudioCorePlugin.log("Unable to load resource "+resource.getURI(), e);
							};
						}
						XSDSchema schema = ((XSDResourceImpl) resource).getSchema();
						if (schema != null && namespaceURI.equals(schema.getTargetNamespace())) {
							return schema;
						}
					}
				}
			} catch (Exception e) {
				// fall through to other lookup
			}

			for (Resource resource : entityResources) {
				if (!resource.isLoaded()) {
					try {
						resource.load(new HashMap<>());
					} catch (IOException e) {
						StudioCorePlugin.log("Unable to load resource "+resource.getURI(), e);
					};
				}
				XSDSchema schema = ((XSDResourceImpl) resource).getSchema();
				if (schema != null && namespaceURI.equals(schema.getTargetNamespace())) {
					if (collectAllSchemas) {
						matchingSchemas.add(schema);
					} else {
						return schema;
					}
				}
				continue;
			
			}
			return null;
		}

		@Override
		public void notifyChanged(Notification notification) {
		}

		@Override
		public Notifier getTarget() {
			return null;
		}

		@Override
		public void setTarget(Notifier newTarget) {
		}

		@Override
		public boolean isAdapterForType(Object type) {
			return false;
		}

		@Override
		public String resolveSchemaLocation(XSDSchema xsdSchema, String namespace, String schemaLocation) {
			if (schemaLocation == null && isBENamespace(namespace)) {
				try {
					// does this happen only in project library cases?
					String relPath = namespace.substring(RDFTnsFlavor.BE_NAMESPACE.length());
					String loc = xsdSchema.getSchemaLocation();
					Path path = new Path(loc);
					Path rel = new Path(relPath);
					String ext = path.getFileExtension();
					path = path.removeLastSegments(rel.segmentCount()).append(rel).addFileExtension(ext);
					return path.toString();
				} catch (Exception e) {
				}
			}
			
			return XSDConstants.resolveSchemaLocation(xsdSchema.getSchemaLocation(), namespace, schemaLocation);
		}

		private boolean isBENamespace(String namespace) {
			return namespace != null && namespace.startsWith(RDFTnsFlavor.BE_NAMESPACE);
		}
		
	}
	
	private class SchemaLocatorAdapterFactory implements AdapterFactory {

		private SchemaLocator fLocator = new SchemaLocator();
		
		@Override
		public boolean isFactoryForType(Object type) {
			return XSDSchemaLocator.class.equals(type) || XSDSchemaLocationResolver.class.equals(type);
		}

		@Override
		public Object adapt(Object object, Object type) {
			return null;
		}

		@Override
		public Adapter adapt(Notifier target, Object type) {
			return null;
		}

		@Override
		public Adapter adaptNew(Notifier target, Object type) {
			return fLocator;
		}

		@Override
		public void adaptAllNew(Notifier notifier) {
		}
		
	}
	
	class PackageHandler implements XMLResource.MissingPackageHandler {

		@Override
		public EPackage getPackage(String nsURI) {
			EPackage.Registry r = EPackage.Registry.INSTANCE;
			return r.getEPackage(nsURI);
		}
		
	}
	
	private XMLResource.MissingPackageHandler handler = new PackageHandler();
	private static final String REPO_TYPES_CONCEPT_XSD = "id.xsd";
//	private static final String REPO_TYPES_CONCEPT_XSD = "repo-types-2002.xsd";
	private static final String REPO_TYPES_NS = "http://www.tibco.com/xmlns/repo/types/2002";
	private static final String BASE_CONCEPT_XSD = "_BaseConcept.xsd";
	private static final String BASE_EVENT_XSD = "_BaseEvent.xsd";
	private static final String BASE_TIME_EVENT_XSD = "_BaseTimeEvent.xsd";
	private static final String BASE_EXCEPTION_XSD = "_BaseException.xsd";
	private static final String BASE_SOAP_EVENT_XSD = "_BaseSOAPEvent.xsd";
//	private static final String BASE_SOAP_XSD = "http://schemas.xmlsoap.org/soap/envelope/";
	private static final String BASE_PROCESS_XSD = "_BaseProcess.xsd";
	private static final String XSD_DIR = "com/tibco/cep/studio/core/mapper/resources/";
	private HashMap<String, XSDResourceImpl> fEntitySchemaMap = new HashMap<>();
	private ResourceSet fSchemaResourceSet = new BESchemaResourceSet();
	private String fProjectURI;
	private Queue<Throwable> fErrors = new LinkedList<Throwable>();
	private Queue<Event> fDeferredSoapEvents = new LinkedList<Event>();
	private SchemaLocatorAdapterFactory fSchemaLocatorAdapterFactory = new SchemaLocatorAdapterFactory();
	private XSDResourceImpl fBaseConceptResource;
	private XSDResourceImpl fBaseEventResource;
	private XSDResourceImpl fBaseSoapEventResource;
	private XSDResourceImpl fBaseProcessResource;
	private XSDResourceImpl fBaseExceptionResource;
	private String fProjectName;
	private XSDResourceImpl fBaseTimeEventResource;
	
	/*
	 * This class exists because the BW6 mapper assumes that all resources have been loaded
	 * in the resource set, so it never calls loadOnDemand.  Since we defer loading of resources,
	 * we create our own resource set and always load resources that have not yet been loaded.
	 */
	private class BESchemaResourceSet extends ResourceSetImpl {

		public BESchemaResourceSet() {
		    getResourceFactoryRegistry().getExtensionToFactoryMap().put("xsd", new XSDResourceFactoryImpl());
		}
		
		@Override
		public Resource getResource(URI uri, boolean loadOnDemand) {
			Resource resource = super.getResource(uri, loadOnDemand);
			if (resource instanceof XSDResourceImpl && !resource.isLoaded()) {
				try {
					resource.load(new HashMap<>());
				} catch (IOException e) {
					StudioCorePlugin.log("Unable to load resource "+resource.getURI(), e);
				}
			}
			return resource;
		}
		
	}
	public EntitySchemaCache(String projectName, String projectURI) {
		this.fProjectName = projectName;
		this.fProjectURI = projectURI;
		initialize();
	}

	public Resource getResourceFromURI(URI uri) {
		Resource resource = fSchemaResourceSet.getResource(uri, false);
		if (resource == null && ((ResourceSetImpl)fSchemaResourceSet).getURIResourceMap() != null) {
			resource = ((ResourceSetImpl)fSchemaResourceSet).getURIResourceMap().get(uri);
		}
		if (resource == null) {
			return null;
		}
		if (!resource.isLoaded()) {
			try {
				resource.load(new HashMap<>());
			} catch (IOException e) {
				StudioCorePlugin.log("Unable to load resource "+resource.getURI(), e);
			};
		}
		return resource;
	}

	public URI toPlatformURI(String resolvedSchemaLocationURI) {
		Path path = new Path(resolvedSchemaLocationURI);
		String[] segments = path.segments();
		String platformPath = "";
		boolean begin = false;
		for (String seg : segments) {
			if (begin) {
				platformPath += Path.SEPARATOR + seg;
				continue;
			}
			if (seg.endsWith(".projlib!")) {
				begin = true;
				platformPath = fProjectName + Path.SEPARATOR + seg.substring(0, seg.length()-1);
			}
		}
		
		return URI.createPlatformResourceURI(platformPath, false);
	}

	public XSDSchema getSchemaFromResource(Resource resource) {
		if (resource == null) {
			return null;
		}
		if (resource instanceof XSDResourceImpl) {
			return ((XSDResourceImpl) resource).getSchema();
		}
		
		if (resource instanceof WSDLResourceImpl) {
			Definition definition = ((WSDLResourceImpl) resource).getDefinition();
			Types types = definition.getTypes();
			List extensibilityElements = types.getExtensibilityElements();
			for (int i = 0; i < extensibilityElements.size(); i++) {
				Object object = extensibilityElements.get(i);
				if (object instanceof XSDSchemaExtensibilityElement) {
					return ((XSDSchemaExtensibilityElement) object).getSchema();
				}
			}
		}
		return null;
	}

	private void initialize() {
		if (!fSchemaResourceSet.getAdapterFactories().contains(fSchemaLocatorAdapterFactory)) {
			fSchemaResourceSet.getAdapterFactories().add(fSchemaLocatorAdapterFactory);
		}
		fSchemaResourceSet.getResources().clear();
		// add the base type XSDs
		HashMap<Object, Object> options = new HashMap<>();
		try {
			EPackage.Registry r = EPackage.Registry.INSTANCE;

			// add base repo types
			URL repoTypesURL = getClass().getClassLoader().getResource(XSD_DIR+REPO_TYPES_CONCEPT_XSD);
			XSDResourceImpl fRepoTypesResource = new XSDResourceImpl(URI.createURI(REPO_TYPES_NS));
			fRepoTypesResource.load(repoTypesURL.openStream(), options);
			fSchemaResourceSet.getResources().add(fRepoTypesResource);
			r.put(REPO_TYPES_NS, IdPackage.eINSTANCE);

			URL conceptURL = getClass().getClassLoader().getResource(XSD_DIR+BASE_CONCEPT_XSD);
			fBaseConceptResource = new XSDResourceImpl(URI.createFileURI(SchemaGenerator.BE_BASE_CONCEPT_NS));
//			fBaseConceptResource = new XSDResourceImpl(URI.createFileURI(fProjectURI+"/"+BASE_CONCEPT_XSD));
			fBaseConceptResource.load(conceptURL.openStream(), options);
			fSchemaResourceSet.getResources().add(fBaseConceptResource);

			URL eventURL = getClass().getClassLoader().getResource(XSD_DIR+BASE_EVENT_XSD);
			fBaseEventResource = new XSDResourceImpl(URI.createFileURI(SchemaGenerator.BE_BASE_EVENT_NS));
//			fBaseEventResource = new XSDResourceImpl(URI.createFileURI(fProjectURI+"/"+BASE_EVENT_XSD));
			fBaseEventResource.load(eventURL.openStream(), options);
			fSchemaResourceSet.getResources().add(fBaseEventResource);
			
			URL timeEventURL = getClass().getClassLoader().getResource(XSD_DIR+BASE_TIME_EVENT_XSD);
			fBaseTimeEventResource = new XSDResourceImpl(URI.createFileURI(SchemaGenerator.BE_BASE_TIME_EVENT_NS));
//			fBaseEventResource = new XSDResourceImpl(URI.createFileURI(fProjectURI+"/"+BASE_EVENT_XSD));
			fBaseTimeEventResource.load(timeEventURL.openStream(), options);
			fSchemaResourceSet.getResources().add(fBaseTimeEventResource);
			
			URL exURL = getClass().getClassLoader().getResource(XSD_DIR+BASE_EXCEPTION_XSD);
			fBaseExceptionResource = new XSDResourceImpl(URI.createFileURI(SchemaGenerator.BE_BASE_EXCEPTION_NS));
//			fBaseEventResource = new XSDResourceImpl(URI.createFileURI(fProjectURI+"/"+BASE_EVENT_XSD));
			fBaseExceptionResource.load(exURL.openStream(), options);
			fSchemaResourceSet.getResources().add(fBaseExceptionResource);
			
			URL soapEventURL = getClass().getClassLoader().getResource(XSD_DIR+BASE_SOAP_EVENT_XSD);
			fBaseSoapEventResource = new XSDResourceImpl(URI.createFileURI(SchemaGenerator.BE_BASE_SOAP_EVENT_NS));
			fBaseSoapEventResource.load(soapEventURL.openStream(), options);
			fSchemaResourceSet.getResources().add(fBaseSoapEventResource);
			/*
			XSDResourceImpl fSoapResource = new XSDResourceImpl(URI.createURI(RDFTnsFlavor.SOAP_NAMESPACE));
			fSoapResource.load(options);
			fSchemaResourceSet.getResources().add(fSoapResource);
			*/
//			URI uri = URI.createURI(BASE_SOAP_XSD);
//			XSDResourceImpl res = new XSDResourceImpl(uri);
//			res.load(new HashMap<>());
//			XSDSchema webschema = res.getSchema();
//			if (webschema != null) {
//				fSchemaResourceSet.getResources().add(res);
//			}
			
			URL processURL = getClass().getClassLoader().getResource(XSD_DIR+BASE_PROCESS_XSD);
			fBaseProcessResource = new XSDResourceImpl(URI.createFileURI(SchemaGenerator.BE_BASE_PROCESS_NS));
//			XSDResourceImpl procres = new XSDResourceImpl(URI.createFileURI(fProjectURI+"/"+BASE_PROCESS_XSD));
			fBaseProcessResource.load(processURL.openStream(), options);
			fSchemaResourceSet.getResources().add(fBaseProcessResource);
			
		} catch (IOException e) {
			StudioCorePlugin.log("Error loading base XSDs", e);
		}
	}

	public void resourceChanged(IFile file, boolean deferLoad) {
		removeFromSchemaMap(IndexUtils.getFullPath(file));
		Resource resource = null;
		String fullPath = file.getFullPath().toString();
		if (IndexUtils.isEntityType(file)) {
			resource = new XSDEntityResourceImpl(getURIFromPath(fullPath), this);
			String key = getCacheKey(file);
			XSDResourceImpl xsdRes = getSchemaResource(key);
			if (xsdRes != null) {
				boolean remove = getResourceSet().getResources().remove(xsdRes);
				if (remove) {
					System.out.println("removed: "+xsdRes.getURI());
				}
			}
			addToSchemaMap(key, (XSDEntityResourceImpl) resource);
			getResourceSet().getResources().add(resource);
			if (IndexUtils.EVENT_EXTENSION.equalsIgnoreCase(file.getFileExtension())) {
				// need to check whether this is a soap event.  If so, process those pseudo-URIs as well
				InputStream is = null;
				try {
					is = file.getContents();
					EObject eObj = IndexUtils.loadEObject(file.getLocationURI().toString(), is);
					if (eObj instanceof Event) {
						Event ev = (Event) eObj;
						if (ev.isSoapEvent()) {
							if (deferLoad) {
								fDeferredSoapEvents.add(ev);
							} else {
								processSoapEvent(ev);
							}
						}
					}
				} catch (CoreException e) {
					addError(e);
				} finally {
					if (is != null) {
						try {
							is.close();
						} catch (IOException e) {
						}
					}
				}
//				EObject eObj = IndexUtils.loadEObject(file.getLocationURI());
//				if (eObj instanceof Event) {
//					Event ev = (Event) eObj;
//					if (ev.isSoapEvent()) {
//						processSoapEvent(ev);
//					}
//				}
			}
		} else {
			// this adds it to the resource set
//			resource = getResourceSet().createResource(URI.createURI(file.getLocationURI().toString()));
			resource = getResourceSet().createResource(getURIFromPath(fullPath));
			if (deferLoad) {
				return;
			}
			try {
				HashMap<Object, Object> options = new HashMap<>();
				options.put(XMLResource.OPTION_MISSING_PACKAGE_HANDLER, handler);
				resource.load(options);
			} catch (IOException e) {
				System.err.println(e.getLocalizedMessage());
				addError(e);
//				e.printStackTrace();
			}
		}
	}
	
	public void loadAll(boolean includeEntityResources) {
		// first, process SOAP events so that WSDL loading is successful
		Event ev = null;
		do {
			// drain the error queue
			ev = fDeferredSoapEvents.poll();
			if(ev != null)
				processSoapEvent(ev);
		} while (ev != null);
		ResourceSet resourceSet = getResourceSet();
		EList<Resource> resources = resourceSet.getResources();
		for (int i = 0; i < resources.size(); i++) {
			Resource resource = resources.get(i);
			if (resource instanceof XSDEntityResourceImpl && !includeEntityResources) {
				continue;
			}
			try {
				HashMap<Object, Object> options = new HashMap<>();
				options.put(XMLResource.OPTION_MISSING_PACKAGE_HANDLER, handler);
				resource.load(options);
			} catch (IOException e) {
				System.err.println(e.getLocalizedMessage());
				addError(e);
//				e.printStackTrace();
			}
		}
	}

	private String getCacheKey(IFile file) {
		IPath path = file.getFullPath().removeFirstSegments(1).removeFileExtension();
		if (file.isLinked(IFile.CHECK_ANCESTORS)) {
			path = path.removeFirstSegments(1);
		}
		String key = path.toString();
		return key;
	}

	public void resourceChanged(String location, InputStream is) {
		fEntitySchemaMap.remove(location);
		EObject eObj = IndexUtils.loadEObject(location, is);
		if (eObj != null) {
			resourceChanged(location, eObj);
		}
	}

	public void resourceChanged(String location, EObject eObj)  {
		Resource resource = null;
		if (eObj instanceof Entity) {
//			resource = new XSDEntityResourceImpl(getURIFromPath(location), this);
			resource = new XSDEntityResourceImpl(eObj.eResource().getURI(), this);
			if (eObj instanceof Event && ((Event) eObj).isSoapEvent()) {
				processSoapEvent((Event)eObj);
			}
			addToSchemaMap(((Entity) eObj).getFullPath(), (XSDEntityResourceImpl) resource);
//			fEntitySchemaMap.put(((Entity) eObj).getFullPath(), (XSDEntityResourceImpl) resource);
		} else if (eObj instanceof XSDSchema) {
			XSDSchema xsdSchema = (XSDSchema) eObj;
			resource = (XSDResourceImpl) xsdSchema.eResource();//new XSDResourceImpl(eObj.eResource().getURI());
		} else if (eObj instanceof Definition) {
			Definition def = (Definition) eObj;
			resource = def.eResource();
		}
		if (resource != null) {
			fSchemaResourceSet.getResources().add(resource);
		}
	}

	private void processSoapEvent(Event event) {
		SchemaGenerator generator;
		try {
			generator = new SchemaGenerator(fProjectName, new SchemaNSResolver(getResourceSet()));
			Map<String, String> generateEnvelopeSchema = generator.generateEnvelopeSchema(event);
			Set<String> keySet = generateEnvelopeSchema.keySet();
			for (String string : keySet) {
				loadSchema(string, generateEnvelopeSchema.get(string));
//				System.out.println( generateEnvelopeSchema.get(string) );
			}
		} catch (Exception e) {
			StudioCorePlugin.log("Unable to process soap event "+event.getFullPath(), e);
		}
		
	}

	public void loadSchema( String Uri , String stringSchema) {
		if ( Uri.startsWith( "/" ) ) {
			Uri =   Uri.substring(1);
		}
		StringXSDResourceImpl resource = null;
		resource =  new StringXSDResourceImpl(URI.createURI( Uri ), stringSchema);
		try {
			getfSchemaResourceSet( ).getResources( ).remove( getfEntitySchemaMap( ).get( Uri ) ) ;
			getfEntitySchemaMap( ).remove( Uri ); 
			getfEntitySchemaMap( ).put( Uri, resource );
			getfSchemaResourceSet( ).getResources( ).add( resource);

		} catch ( Exception e ) { 
			StudioCorePlugin.log("Exception while adding to resource set"+resource.getURI(), e);
		}

		try {
			if (!resource.isLoaded()) {
//				resource.generateXsdSchema(stringSchema);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public XSDSchema getSchema(Entity entity) {
		XSDEntityResourceImpl resource = (XSDEntityResourceImpl) getSchemaResource(entity.getFullPath());
		if (resource == null) {
			if (entity.eContainer() instanceof SharedEntityElement) {
				SharedEntityElement el = (SharedEntityElement) entity.eContainer();
				resource = new XSDEntityResourceImpl(getSharedEntityURI(el), this);
			} else {
				resource = new XSDEntityResourceImpl(entity.eResource().getURI(), this);
			}
			if (entity instanceof Event && ((Event) entity).isSoapEvent()) {
				processSoapEvent((Event)entity);
			}
			fSchemaResourceSet.getResources().add(resource);
			addToSchemaMap(entity.getFullPath(), resource);
//			fEntitySchemaMap.put(entity.getFullPath(), resource);
		}
		try {
			if (!resource.isLoaded()) {
				synchronized (resource) {
					resource.load(new HashMap<>());
				}
			}
		} catch (IOException e) {
			StudioCorePlugin.log("Unable to load resource "+resource.getURI(), e);
		}
		XSDSchema schema = null;
		synchronized (resource) {
			schema = resource.getSchema();
		}
//		processSchemaImports(schema);
		return schema;
	}
	
	
	private XSDResourceImpl getSchemaResource(String key) {
		if (key.length() > 0 && key.charAt(0) != '/') {
			key = '/' + key;
		}
		return fEntitySchemaMap.get(key);
	}

	private void addToSchemaMap(String key, XSDEntityResourceImpl resource) {
		if (key.length() > 0 && key.charAt(0) != '/') {
			key = '/' + key;
		}
		fEntitySchemaMap.put(key, resource);
	}

	private void removeFromSchemaMap(String key) {
		if (key.length() > 0 && key.charAt(0) != '/') {
			key = '/' + key;
		}
		fEntitySchemaMap.remove(key);
	}
	
	public XSDSchema getSchemaUsingURI(String uri) {
		if ( uri.startsWith( "/" ) ) {
			uri = uri.substring(1);
		}
		XSDResourceImpl resource = getSchemaResource(uri);
		if (resource == null) {
			return null ;
		}
		try {
			if (!resource.isLoaded()) {
				resource.load(new HashMap<>());
			}
		} catch (IOException e) {
			StudioCorePlugin.log("Unable to load resource "+resource.getURI(), e);
		}
		XSDSchema schema = resource.getSchema();
		return schema;
	}
	
	private void processSchemaImports(XSDSchema schema) {
		EList<XSDSchemaContent> contents = schema.getContents();
		for (XSDSchemaContent content : contents) {
			if (content instanceof XSDImport) {
				XSDImportImpl imp = (XSDImportImpl) content;
				XSDSchema impSchema = imp.getResolvedSchema();
				if (impSchema == null) {
					imp.reset();
					imp.importSchema();
				} else {
					processSchemaImports(impSchema);
				}
			}
		}
		
	}

	public void addError(Exception e) {
		fErrors.add(e);
	}

	public void resourceRemoved(String location) {
		removeFromSchemaMap(location);
//		fEntitySchemaMap.remove(location);
	}

	public Queue<Throwable> getErrors() {
		return fErrors;
	}
	
	public ResourceSet getResourceSet() {
		return fSchemaResourceSet;
	}
	
	private URI getSharedEntityURI(SharedEntityElement el) {
		String archivePath = el.getArchivePath();
		Path aPath = new Path(archivePath);
		String archiveName = aPath.lastSegment();
		String ownerProjectName = el.getEntity().getOwnerProjectName();
		return getURIFromPath(Path.SEPARATOR + ownerProjectName + Path.SEPARATOR + archiveName + Path.SEPARATOR + el.getFolder() + el.getFileName());
	}

	protected URI getURIFromPath(String fullPath) {
	    String fileName = new StringBuilder(ResourceSetHelper.PLATFORM_RESOURCE).append(fullPath).toString();
	    return URI.createURI(fileName, true);
    }

	public ResourceSet getfSchemaResourceSet() {
		return fSchemaResourceSet;
	}


	public HashMap<String, XSDResourceImpl> getfEntitySchemaMap() {
		return fEntitySchemaMap;
	}

	public String getProjectName() {
		return fProjectName;
	}

	public XSDSchema getSchemaFromURI(URI uri) {
		Resource resourceFromURI = getResourceFromURI(uri);
		if (resourceFromURI == null) {
			return null;
		}
		XSDSchema schema = getSchemaFromResource(resourceFromURI);
		if (schema != null) {
			return schema;
		}
		return null;
	}

}
