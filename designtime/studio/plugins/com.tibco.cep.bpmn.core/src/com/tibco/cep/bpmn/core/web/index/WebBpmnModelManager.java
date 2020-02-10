package com.tibco.cep.bpmn.core.web.index;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLResource.URIHandler;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import com.tibco.be.util.BidiMap;
import com.tibco.be.util.DualHashBidiMap;
import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionFactory;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonIndexUtils;
import com.tibco.cep.bpmn.model.designtime.utils.CommonECoreHelper;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.studio.core.util.ModelUtilsCore;

public class WebBpmnModelManager {
	private static Map<String, ResourceSet> resourceSetMap = new HashMap<String, ResourceSet>();
//	private static Map<String, EObject> indexMap = new HashMap<String, EObject>();
	public WebBpmnModelManager() {
//		BpmnMetaModelURIHelper.getUriForWebStudio();
	}
	
	public void loadIndex(String projectPath, String projectName) throws IOException{
		final File baseDir = new File(projectPath);
		final File file = new File(baseDir, projectName + ".bdx");
		 String indexLocation = file.getPath();
		 URI uri = URI.createFileURI(indexLocation);
		 ResourceSet modelResourceSet = getModelResourceSet(projectName);
		 HashMap<String, URI> uriMap = new HashMap<String, URI>();
			uriMap.put(projectName, uri);
			WebBpmnIndexURIHandler bpmnIndexURIHandler = new WebBpmnIndexURIHandler(projectName, uriMap);
		 modelResourceSet.getLoadOptions().put(XMIResource.OPTION_URI_HANDLER,bpmnIndexURIHandler);
		 if(file.exists()){
			 BpmnCommonIndexUtils.loadEObject(modelResourceSet, file.toURI(), true);
		 }
		 else
			 createIndex(projectPath, projectName);
	}
	
	public ResourceSet getModelResourceSet(String projectName){
		ResourceSet resourceSet = resourceSetMap.get(projectName) ;
		if(resourceSet == null)
			createModelResourceSet(projectName);
		
		return resourceSetMap.get(projectName);
	}
	
	public void  createModelResourceSet(String projectName) {
		ResourceFactoryRegistryImpl.INSTANCE.getExtensionToFactoryMap().put(
			"*", new XMIResourceFactoryImpl());
		BpmnMetaModel bpmnMetaModel = BpmnMetaModel.getInstance();
		if (bpmnMetaModel == null) {
			System.err.println("NULL BPMN Meta Model!");
			return;
		}
		else {
			ResourceSet rset = bpmnMetaModel.getResourceSet();
			WebBpmnIndexURIHandler bpmnIndexURIHandler =
				new WebBpmnIndexURIHandler(projectName,new HashMap<String, URI>());
			rset.getLoadOptions().put(XMIResource.OPTION_URI_HANDLER,
					bpmnIndexURIHandler);
			resourceSetMap.put(projectName, rset);
		}
	}
	
	public EObject loadProcess(String projDir, String projectName, String projRelpath) throws Exception{
		File file = new File(projDir+ projRelpath);
		
		if(!file.exists() || !projRelpath.endsWith("beprocess"))
			throw new Exception(file.getAbsolutePath() + " is not valid Bpmn process");
		ResourceSet modelResourceSet = getModelResourceSet(projectName);
		loadIndex(projDir, projectName);
		EObject loadEObject = BpmnCommonIndexUtils.loadEObject(modelResourceSet, file.toURI(), true);
		return loadEObject;
		
	}
	
	public EObject createIndex(String projectPath,String projName) throws IOException{
		EObjectWrapper<EClass, EObject> indexWrapper = EObjectWrapper.createInstance(
				BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.DEFINITIONS));
		indexWrapper.setAttribute(BpmnMetaModel.E_ATTR_NAME, projName);
		indexWrapper.setAttribute(BpmnMetaModel.E_ATTR_CREATION_DATE,new Date());
		loadExtensions(indexWrapper);
		final File baseDir = new File(projectPath);
		final File file = new File(baseDir, projName + ".bdx");
		 String indexLocation = file.getPath();
		 URI uri = URI.createFileURI(indexLocation);
		saveIndex(projName, uri, indexWrapper.getEInstance());
		return indexWrapper.getEInstance();
	}
	
	public static void loadExtensions(EObjectWrapper<EClass, EObject> indexWrapper) {
		Collection<EObject> extDefs = BpmnMetaModel.INSTANCE.getExtensionDefinitions();
		synchronized(extDefs) {
			for(EObject extDef:extDefs) {
				EObject temp = EcoreUtil.copy(extDef);
				EObjectWrapper<EClass, EObject> extn = ExtensionFactory.createExtension(EObjectWrapper.wrap(temp));
				indexWrapper.addToListAttribute(BpmnMetaModel.E_ATTR_EXTENSIONS, extn.getEInstance());
			}		
		}
	}
	
	
	public void saveIndex(String projectname,URI uri, EObject eObject) throws IOException{
		ResourceSet resourceSet = getModelResourceSet(projectname);
		EList<Resource> resources = resourceSet.getResources();
		for (Iterator<?> iterator = resources.iterator(); iterator.hasNext();) {
			Resource r = (Resource) iterator.next();
			if(r != null && r.getURI() != null && r.getURI().equals(uri)) {
				iterator.remove();
				break;
			}
		}

		Resource resource = resourceSet.createResource(uri);
		resource.getContents().add(eObject);
		HashMap<String, URI> uriMap = new HashMap<String, URI>();
		uriMap.put(projectname, uri);
		WebBpmnIndexURIHandler bpmnIndexURIHandler = new WebBpmnIndexURIHandler(projectname, uriMap);
		Map<Object,Object> options = ModelUtilsCore.getPersistenceOptions();
		resourceSet.getLoadOptions().put(XMIResource.OPTION_URI_HANDLER,
				bpmnIndexURIHandler);
		options.put(XMIResource.OPTION_URI_HANDLER, bpmnIndexURIHandler);
		CommonECoreHelper.serializeXMI(resource,options);
	}
	
	public static class WebBpmnIndexURIHandler implements URIHandler {

		URI baseURI;
		String pName;
		
		protected BidiMap<String, URI> locMap;
		Pattern pattern = Pattern
				.compile("(.*@extensions\\[name=')(-?\\d+)('\\].*)");

		public WebBpmnIndexURIHandler(String project, Map<String, URI> bidiMap) {
			this.pName = project;
			this.locMap = new DualHashBidiMap<String, URI>(bidiMap);
		}

		@Override
		public URI deresolve(URI uri) {
			//from C:/project index path.bdx#fragment to bdx:<projectname>#fragment 
			if (uri.hasAbsolutePath()
					&& uri.fileExtension().equals(
							BpmnCommonIndexUtils.BPMN_INDEX_EXTENSION)) {
//				String fileName = uri.devicePath().substring(
//						uri.devicePath().lastIndexOf("/") + 1);
				URI createFileURI = URI.createFileURI(uri.devicePath());// converting
				// to
				// URI
				String projectName = locMap.inverseMap().get(createFileURI);
				// if the file uri does not exist in the map then use the default
				// project name of the handler
				if(projectName == null || !pName.equals(projectName)) {
					projectName = pName;
//					BpmnCorePlugin.debug("Could not resolve project: "+projectName+" using default project:"+pName);
				}
				URI newURI = URI.createGenericURI(
						BpmnCommonIndexUtils.BPMN_INDEX_SCHEME, projectName,
						URI.decode(uri.fragment()));
//				BpmnCorePlugin.debug("DeResolved URI: "+uri.toString()+"\n\t->"+newURI);
				return newURI;
				
			} else
				return uri;
		}

		@Override
		public URI resolve(URI uri) {
			//From bdx:<projectname>#fragment to C:/project index path.bdx#fragment
			if (uri.scheme() != null
					&& uri.scheme().equals(
							BpmnCommonIndexUtils.BPMN_INDEX_SCHEME)) {
				URI fileURI = locMap.get(uri.opaquePart());
				// to handle conversion from int hashcode index key to hex string
				// convert int base name key to lowercase Hex String to
				// match with index
				Matcher m = pattern.matcher(uri.fragment());
				String fragment = null;
				if (m.matches()) {
					String number = Integer.toHexString(Integer.parseInt(m
							.group(2)));
					fragment = m.group(1) + number + m.group(3);
				} else {
					fragment = uri.fragment();
				}
				if(fileURI == null || !pName.equals(uri.opaquePart())) {
					fileURI = locMap.get(pName);
					BpmnCorePlugin.debug("Could not resolve project: "+uri.opaquePart()+" using default project:"+pName);
				}
				if(fileURI != null) {
					URI newURI = fileURI.appendFragment(URI.decode(fragment));
//					BpmnCorePlugin.debug("Resolved URI: "+uri.toString()+"\n\t->"+newURI);
					return newURI;
				} else {
//					BpmnCorePlugin.log("File URI not found: "+uri.toString());
				}
				
			}
			return uri;
		}

		@Override
		public void setBaseURI(URI uri) {
			this.baseURI = uri;
		}

	}
}
