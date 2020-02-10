package com.tibco.cep.bpmn.ui.mapper;

import java.io.IOException;
import java.util.HashMap;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xsd.XSDImport;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDSchemaContent;
import org.eclipse.xsd.impl.XSDImportImpl;
import org.eclipse.xsd.impl.XSDSchemaImpl;
import org.eclipse.xsd.util.XSDResourceImpl;
import org.eclipse.xsd.util.XSDSchemaLocator;

import com.tibco.be.model.rdf.RDFTnsFlavor;
import com.tibco.cep.bpmn.ui.graph.properties.BpmnXSDResourceImpl;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.core.mapper.EntitySchemaCache;

public class BpmnSchemaCache {
	
	public BpmnSchemaCache() {
		initialize();
	}
	private EntitySchemaCache esm ;
	private class BpmnSchemaLocator implements XSDSchemaLocator, Adapter {
		
		
		
		@Override
		public XSDSchema locateSchema(XSDSchema xsdSchema, String namespaceURI,
				String rawSchemaLocationURI, String resolvedSchemaLocationURI) {
			
			if(namespaceURI.equalsIgnoreCase(RDFTnsFlavor.SOAP_NAMESPACE)){
//				URI uri = URI.createURI(rawSchemaLocationURI);
				Resource resource = esm.getfSchemaResourceSet().getResource(URI.createURI(rawSchemaLocationURI), false);
				
				XSDSchema schema = esm.getSchemaFromResource(resource);
				if (schema != null) {
					return schema;
				}
			}
			
			if (rawSchemaLocationURI == null) {
				URI uri = URI.createURI(namespaceURI);
				Resource resource = BpmnfSchemaResourceSet.getResource(uri, false);
				
				XSDSchema schema = getSchemaFromResource(resource);
				
				if ( resource == null ) {
					resource = esm.getfSchemaResourceSet().getResource(uri, false);
					 schema = esm.getSchemaFromResource(resource);
				}
				
				if (schema != null) {
					return schema;
				}
			}
			
			if (resolvedSchemaLocationURI != null) {
				URI uri = URI.createURI(resolvedSchemaLocationURI);
				Resource resource = BpmnfSchemaResourceSet.getResource(uri, false);
				XSDSchema schema = getSchemaFromResource(resource);
				if ( resource == null ) {
					resource = esm.getfSchemaResourceSet().getResource(uri, false);
					schema = esm.getSchemaFromResource(resource);
				}
				
				
				if (schema != null) {
					return schema;
				}
				if (resource == null && (uri.fileExtension() == null || "xsd".equalsIgnoreCase(uri.fileExtension()))) {
					// web URI?
					XSDResourceImpl res = new XSDResourceImpl(uri);
					try {
						res.load(new HashMap<>());
						XSDSchema webschema = res.getSchema();
						if (webschema != null) {
							esm.getfSchemaResourceSet().getResources().add(res);
							return webschema;
						}
					} catch (IOException e) {
						System.out.println("Exception occurred while trying to get webservices schema -- May be this is not a webservices task");
					}

				}
			}
			if (namespaceURI != null) {
				EList<Resource> resources = BpmnfSchemaResourceSet.getResources();
				Resource resource ; 
				for (int j = 0; j < resources.size(); j++) {
					 resource = resources.get(j);
//				for (Resource resource : resources) {
					if (resource instanceof XSDResourceImpl) {
						XSDSchema schema = ((XSDResourceImpl) resource).getSchema();
						if (namespaceURI.equals(schema.getTargetNamespace())) {
							return schema;
						}
						continue;
					}
				}
					
					 resources = esm.getfSchemaResourceSet().getResources();
					for (int j = 0; j < resources.size(); j++) {
						 resource = resources.get(j);
//					for (Resource resource : resources) {
						if (resource instanceof XSDResourceImpl) {
							XSDSchema schema = ((XSDResourceImpl) resource).getSchema();
						if (schema != null) {
							System.out.println(schema.getTargetNamespace());
							if (namespaceURI
									.equals(schema.getTargetNamespace())) {
								return schema;
							}
						}
							continue;
						}
					
				}
			 }
			return null ;
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
		
	}
	
	public EntitySchemaCache getEsm() {
		return esm;
	}

	public void setEsm(EntitySchemaCache esm) {
		this.esm = esm;
	}
	
	private class BpmnSchemaLocatorAdapterFactory implements AdapterFactory {

		private BpmnSchemaLocator fLocator = new BpmnSchemaLocator();
		
		@Override
		public boolean isFactoryForType(Object type) {
			return XSDSchemaLocator.class.equals(type);
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
	private ResourceSet BpmnfSchemaResourceSet = XSDSchemaImpl.createResourceSet();
	private BpmnSchemaLocatorAdapterFactory BpmnfSchemaLocatorAdapterFactory = new BpmnSchemaLocatorAdapterFactory();
	private HashMap<String, XSDResourceImpl> BpmnfEntitySchemaMap = new HashMap<>();
	
	private void initialize() {
		if (!BpmnfSchemaResourceSet.getAdapterFactories().contains(BpmnfSchemaLocatorAdapterFactory)) {
			BpmnfSchemaResourceSet.getAdapterFactories().add(BpmnfSchemaLocatorAdapterFactory);
		}
	}
	
	public XSDSchema getSchema(Entity entity) {
		if (entity == null )
			return null;
		XSDResourceImpl resource = (XSDResourceImpl) BpmnfEntitySchemaMap.get(entity.getFullPath());
		XSDSchema schema = null ;
		if ( resource == null ) {
			schema =  esm.getSchema( entity ) ;
			 if ( schema != null ) {
//				 processSchemaImports( schema );
				 return schema;
			 }
		}
		else {
			resource = new BpmnXSDResourceImpl(entity.eResource().getURI());
			BpmnfSchemaResourceSet.getResources().add(resource);
			BpmnfEntitySchemaMap.put(entity.getFullPath(), resource);
		}
		try {
			if (resource != null && !resource.isLoaded()) {
				resource.load(new HashMap<>());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (resource != null)
			schema = resource.getSchema();
//		processSchemaImports(schema);
		return schema;
	}
	
	
	public XSDSchema getSchemaUsingURI(String Uri) {
		if ( Uri.startsWith( "/" ) ) {
			Uri = Uri.substring(1);
		}
		XSDSchema schema ;
		XSDResourceImpl resource = (XSDResourceImpl) BpmnfEntitySchemaMap.get(Uri);
		if (resource == null) {
			schema = esm.getSchemaUsingURI(Uri) ;
			if ( schema != null ) {
//				processSchemaImports( schema );
				return schema ;
			}
		}
		try {
			if (!resource.isLoaded()) {
				resource.load(new HashMap<>());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	  schema = resource.getSchema();
//	  processSchemaImports( schema );
		return schema;
	}
	
	public XSDSchema getSchemaFromResource(Resource resource) {
		if (resource == null) {
			return null;
		}
		if (resource instanceof XSDResourceImpl) {
			return ((XSDResourceImpl) resource).getSchema();
		}
		
		return null;
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
	public ResourceSet getBpmnfSchemaResourceSet() {
		return BpmnfSchemaResourceSet;
	}

	public void setBpmnfSchemaResourceSet(ResourceSet bpmnfSchemaResourceSet) {
		BpmnfSchemaResourceSet = bpmnfSchemaResourceSet;
	}

	public HashMap<String, XSDResourceImpl> getBpmnfEntitySchemaMap() {
		return BpmnfEntitySchemaMap;
	}

	public void setBpmnfEntitySchemaMap(
			HashMap<String, XSDResourceImpl> bpmnfEntitySchemaMap) {
		BpmnfEntitySchemaMap = bpmnfEntitySchemaMap;
	}
}
