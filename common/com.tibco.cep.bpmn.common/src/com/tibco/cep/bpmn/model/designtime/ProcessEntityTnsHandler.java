package com.tibco.cep.bpmn.model.designtime;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.xml.sax.InputSource;

import com.tibco.be.model.rdf.TnsEntityHandler;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.ontology.ProcessAdapter;
import com.tibco.cep.bpmn.model.designtime.ontology.ProcessOntologyAdapter;
import com.tibco.cep.bpmn.model.designtime.utils.CommonECoreHelper;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.IAddOnLoader;
import com.tibco.cep.repo.TnsCacheLoader;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.util.PlatformUtil;

@SuppressWarnings("rawtypes")
public class ProcessEntityTnsHandler implements TnsEntityHandler {

	
	private IAddOnLoader addonLoader;

	public ProcessEntityTnsHandler() {
		// TODO Auto-generated constructor stub
	}
	
	public void setAddOnLoader(IAddOnLoader l) {
		this.addonLoader = l;
	}

	@Override
	public Entity getEntity(InputSource input, String uri) throws Exception {
		Thread thread = Thread.currentThread();
		ClassLoader loader = thread.getContextClassLoader();
		try {
			if (!CommonIndexUtils.sunClassAvailable()) {
				System.setProperty("javax.xml.parsers.SAXParserFactory",
						"org.apache.xerces.jaxp.SAXParserFactoryImpl");
				thread.setContextClassLoader(this.getClass().getClassLoader());
			}
			EObject indexObj = null;
			if(PlatformUtil.INSTANCE.isRuntimePlatform() || PlatformUtil.INSTANCE.isWebStudioPlatform()) {
				indexObj = TnsCacheLoader.getIndex();
			} else {
				Class<?> jobClass = addonLoader.getAddonClass("org.eclipse.core.runtime.jobs.Job");
				Method jobManagerMethod = jobClass.getDeclaredMethod("getJobManager");
				Object jobManager = jobManagerMethod.invoke(null);
				Class jobManagerClass = jobManager.getClass();
				@SuppressWarnings("unchecked")
				Method currentJobMethod = jobManagerClass.getMethod("currentJob");
				Object job = currentJobMethod.invoke(jobManager);
				if (job != null) {
					Class qnClass = addonLoader.getAddonClass("org.eclipse.core.runtime.QualifiedName");
					Method getPropertyMethod= jobClass.getMethod("getProperty", qnClass);
					jobClass = job.getClass();
					Method getKeyMethod = jobClass.getMethod("getKey");
					Object key = getKeyMethod.invoke(job);
					indexObj = (EObject) getPropertyMethod.invoke(job, key);
				} else {
					indexObj = TnsCacheLoader.getIndex();
				}
			}
			assert indexObj != null;
			Map<Object, Object> options = new HashMap<Object, Object>();
			options.put(XMLResource.OPTION_URI_HANDLER, new ProcessURIHandler(indexObj.eResource().getURI(),BpmnMetaModel.INSTANCE.getModelURIMap()));
			options.put(XMLResource.OPTION_ENCODING, "UTF-8");
			File f = null;
			java.net.URI juri = null;
			try {
				juri = new java.net.URI(uri);
			} catch(URISyntaxException se) {
			}
			if(juri != null) {
				String scheme = juri.getScheme();
				if(scheme.equals("file")){
					f = new File(juri);					
				}
			}
			URI resURI = URI.createURI(uri);
			if(f != null && !f.exists()) {
				resURI = URI.createURI(resURI.path());
			}
			EList<EObject> result = CommonECoreHelper.deserializeXMI(
					indexObj.eResource().getResourceSet(), 
					resURI, 
					input.getByteStream(), options);
			if (result.size() > 0) {
				EObject processObj = result.get(0);
				EObjectWrapper<EClass, EObject> processWrapper = EObjectWrapper
						.wrap(processObj);
				String ownerProjectName = processWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_OWNER_PROJECT);
				ResourceSet resourceSet = indexObj.eResource().getResourceSet();
				
				if(resourceSet != null){
					synchronized (resourceSet) {
						List<Resource> resources = new ArrayList<Resource>(resourceSet.getResources());
						for (Resource resource : resources) {
							if (resource != null)
								EcoreUtil.resolveAll(resource);
						}
					}
				}
				
				ProcessAdapter proc =  ProcessAdapterFactory.INSTANCE.createAdapter(processObj,new ProcessOntologyAdapter(ownerProjectName));
				assert !String.valueOf(proc.getRevision()).isEmpty();
				return proc;
			}
		}catch (Throwable t) {
			int i=0;
		} finally {
			thread.setContextClassLoader(loader);
			// this will set it back to sun version (if available),
			// so that Studio can use the sun version elsewhere
			CommonIndexUtils.sunClassAvailable();
		}
		return null;
	}
	
	class ProcessURIHandler extends BpmnMetaModel.ModelUriHandler {
		URI indexUri;
		
		public ProcessURIHandler(URI indexUri,Map<String,URI> uriMap) {
			super(uriMap);
			this.indexUri = indexUri;
		}
		@Override
		public URI resolve(URI uri) {
			if (uri.scheme().equals("platform") && uri.isPlatformResource()) {
				String prefix = "/resource/" + indexUri.authority();
				String s = uri.path().substring(prefix.length());
				String fragment = uri.fragment();
				URI nUri =  URI.createURI(s);
				nUri = nUri.appendFragment(fragment);
				return nUri;
			}
			if(uri.scheme() != null && uri.scheme().equals("bdx") && 
					indexUri.toString().indexOf(uri.opaquePart()) != -1 && 
					indexUri.toString().indexOf(uri.scheme()) != -1) {
				return indexUri.appendFragment(uri.fragment());
			}
			return super.resolve(uri);
		}
		
		@Override
		public URI deresolve(URI uri) {
			// TODO Auto-generated method stub
			return super.deresolve(uri);
		}
	}

}
