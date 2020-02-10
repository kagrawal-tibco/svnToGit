package com.tibco.cep.studio.core.mapper.resources;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.util.XSDResourceImpl;
import org.xml.sax.InputSource;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.mapper.EntitySchemaCache;
import com.tibco.cep.studio.core.util.SchemaGenerator;
import com.tibco.cep.studio.core.utils.ModelUtils;

public class XSDEntityResourceImpl extends XSDResourceImpl {

	private EntitySchemaCache schemaCache;

	public XSDEntityResourceImpl() {
		super();
	}

	public XSDEntityResourceImpl(URI uri, EntitySchemaCache cache) {
		super(uri);
		this.schemaCache = cache;
	}

	@Override
	public XSDSchema getSchema() {
		if (!isLoaded()) {
			try {
				load(new HashMap<Object, Object>());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return super.getSchema();
	}

	@Override
	protected void doLoad(InputSource inputSource, Map<?, ?> options)
			throws IOException {
		Factory factory = Resource.Factory.Registry.INSTANCE.getFactory(getURI());
		Resource res = null;
		if (factory != null) {
			res = factory.createResource(getURI());
		}
		if (res == null) {
			res = new XMIResourceImpl(getURI());
		}
		res.load(inputSource.getByteStream(), options);
		EObject eObject = res.getContents().get(0);
		if (eObject instanceof Entity) {
			Entity entity = (Entity) eObject;
			String projName = schemaCache.getProjectName();
			if (!projName.equals(entity.getOwnerProjectName())) {
				// project lib resource, reset owner name
				entity.setOwnerProjectName(projName);
			}
			inputSource = processEntity(inputSource, entity);
		} else {
			getContents().addAll(res.getContents());
			return;
		}
		super.doLoad(inputSource, options);
	}

	private InputSource processEntity(InputSource inputSource, Entity entity) {
		List<Entity> entList = new ArrayList<>();
		entList.add(entity);
		SchemaGenerator generator;
		try {
			generator = new SchemaGenerator(entity.getOwnerProjectName(), new SchemaNSResolver(getResourceSet()));
			Map schemas = generator.generateSchemas(entList);
			String schemaString = (String) schemas.get(entity.getFullPath());
//			System.out.println("Loaded string");
//			System.out.println(schemaString);
			if (schemaString == null) {
				StudioCorePlugin.logErrorMessage("Unable to create schema for entity "+entity.getFullPath()+". See console output for more information");
				return null;
			}
			if(entity instanceof Event){
				Event event =(Event)entity;
				if(isSoapEvent(event)){
//					Map<String, String> generateEnvelopeSchema = generator.generateEnvelopeSchema(event);
//					Set<String> keySet = generateEnvelopeSchema.keySet();
//					for (String string : keySet) {
//						loadSchema(string, generateEnvelopeSchema.get(string));
//						System.out.println( generateEnvelopeSchema.get(string) );
//					}
					
				}
			}
			InputStream byteStream = new ByteArrayInputStream(schemaString.getBytes(ModelUtils.DEFAULT_ENCODING));
			inputSource = new InputSource(byteStream);
			
		} catch (Exception e) {
			StudioCorePlugin.log(e);
		}
		return inputSource;
	}

	@Override
	protected void doLoad(InputStream inputStream, Map<?, ?> options)
			throws IOException {
		super.doLoad(inputStream, options);
	}

	@Override
	public void load(Map<?, ?> options) throws IOException {
		// TODO Auto-generated method stub
		super.load(options);
	}
	
	protected boolean isSoapEvent(Event event) {
		String eventSuperPath = (String) event.getSuperEventPath();
		boolean isSoapEvent = RDFTypes.SOAP_EVENT.getName().equals(
				eventSuperPath);
		return isSoapEvent;
	}
	
	public void loadSchema( String Uri , String stringSchema  ) {
		if ( Uri.startsWith( "/" ) ) {
			Uri =   Uri.substring(1);
		}
		StringXSDResourceImpl resource = null;
		resource =  new StringXSDResourceImpl(URI.createURI( Uri ), stringSchema);
		try {
			schemaCache.getfSchemaResourceSet( ).getResources( ).remove( schemaCache.getfEntitySchemaMap( ).get( Uri ) ) ;
			schemaCache.getfEntitySchemaMap( ).remove( Uri ); 
			schemaCache.getfEntitySchemaMap( ).put( Uri, resource );
			schemaCache.getfSchemaResourceSet( ).getResources( ).add( resource);

		} catch ( Exception e ) { 
			System.out.println( "Exception while adding to resource set" );
		}

		try {
			if (!resource.isLoaded()) {
//				resource.generateXsdSchema(stringSchema);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
