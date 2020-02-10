package com.tibco.cep.bpmn.model.designtime;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonIndexUtils;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnIndexModificationAdapter;
import com.tibco.cep.bpmn.model.designtime.utils.CommonECoreHelper;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.designtime.model.registry.AddOnType;
import com.tibco.cep.repo.Project;
import com.tibco.cep.repo.ResourceProvider;
import com.tibco.cep.repo.TnsCacheLoader;
import com.tibco.cep.repo.provider.AbstractAddOnIndexResourceProvider;
import com.tibco.cep.studio.common.configuration.ProjectLibraryEntry;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.common.util.Path;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.repo.emf.EMFProject;
import com.tibco.objectrepo.vfile.VFileStream;

public class ProcessIndexResourceProvider extends AbstractAddOnIndexResourceProvider<EMFProject> {

	class ProcessURIHandler extends BpmnMetaModel.ModelUriHandler {
		URI indexUri;

		public ProcessURIHandler(URI indexUri, Map<String, URI> uriMap) {
			super(uriMap);
			this.indexUri = indexUri;
		}

		@Override
		public URI resolve(URI uri) {
			if (uri.scheme() != null && uri.scheme().equals("platform") && uri.isPlatformResource()) {
				String prefix = "/resource/" + ProcessIndexResourceProvider.this.getProject().getName();
				String s = uri.path().substring(prefix.length());
				String fragment = uri.fragment();
				URI nUri =  URI.createURI(s);
				nUri = nUri.appendFragment(fragment);
				return nUri;
			}
//			if (uri.scheme().equals(indexUri.scheme()) && uri.opaquePart().equals(indexUri.opaquePart())) {
//				return indexUri.appendFragment(uri.fragment());
//			}
			return super.resolve(uri);
		}
	}
	
	protected EObject index;
	private ResourceSet resourceSet;
	private Resource indexRes;

	private Map<String, byte[]> xmiResMap = new LinkedHashMap<String, byte[]>();

	public ProcessIndexResourceProvider(EMFProject p) {
		super(p);
		this.resourceSet = BpmnMetaModel.INSTANCE.getResourceSet();
	}

	@Override
	public int deserializeResource(String uri, InputStream is, Project project, VFileStream stream) throws Exception {
		URI resourceUri = null;
		// set context classloader
		Thread thread = Thread.currentThread();
		// ClassLoader loader = thread.getContextClassLoader();
		if (!CommonIndexUtils.sunClassAvailable()) {
			System.setProperty("javax.xml.parsers.SAXParserFactory", "org.apache.xerces.jaxp.SAXParserFactoryImpl");
			thread.setContextClassLoader(CommonIndexUtils.class.getClassLoader());
		}

		Path p = new Path(uri);
		if (BpmnCommonIndexUtils.isBpmnType(p.getFileExtension())) {

			// make a copy of the inputstream bytes
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int c = 0;
			while ((c = is.read()) != -1) {
				baos.write(c);
			}
			is.reset();
			new ByteArrayInputStream(baos.toByteArray());
			EObject index = getIndex();
			EObjectWrapper<EClass, EObject> indexWrapper = EObjectWrapper.wrap(index);
			final File file = new File(uri);
			// If this is a file create file uri
			resourceUri = (file.exists()) ? URI.createFileURI(uri) : URI.createURI("/" + stream.getURI());

			Map<Object, Object> options = new HashMap<Object, Object>();
			options.put(XMLResource.OPTION_URI_HANDLER, new ProcessURIHandler(this.indexRes.getURI(), BpmnMetaModel.INSTANCE.getModelURIMap()));
			EList<EObject> result = CommonECoreHelper.deserializeXMI(this.resourceSet, resourceUri, is, options);
			if (result != null && result.size() > 0) {
				EObject process = result.get(0);
				// Add the process to the Index
				BpmnCommonIndexUtils.addRootElement(indexWrapper, process);
				// CommonECoreHelper.loadImports(indexWrapper,
				// getProject().getTnsCache());
				// EcoreUtil.resolveAll(resourceSet);

				ByteArrayOutputStream xmios = new ByteArrayOutputStream();
				is.reset();
				byte[] data = new byte[16384];
				int nRead = 0;
				while ((nRead = is.read(data, 0, data.length)) != -1) {
					  xmios.write(data, 0, nRead);
				}
				xmios.flush();
//				options.put(XMIResource.OPTION_PROCESS_DANGLING_HREF, XMIResource.OPTION_PROCESS_DANGLING_HREF_DISCARD);
//				XMIResource xmiResource = new XMIResourceImpl(URI.createURI(""));
//				EObject processCopy = EcoreUtil.copy(process);
//				xmiResource.getContents().add(processCopy);
//				xmiResource.save(xmios, options);
				new ByteArrayInputStream(xmios.toByteArray());
				this.xmiResMap.put(uri, xmios.toByteArray());

			}
		}

		return ResourceProvider.SUCCESS_CONTINUE;
	}

	@Override
	public AddOnType getAddOn() {
		return AddOnType.PROCESS;
	}

	@SuppressWarnings("unchecked")
	@Override
	public EObject getIndex() {
		try {
			if (index == null) {

				this.indexRes = resourceSet.createResource(URI.createGenericURI("bdx", getProject().getName(), null));
				EObjectWrapper<EClass, EObject> indexWrapper = EObjectWrapper.createInstance(BpmnModelClass.DEFINITIONS);
				index = indexWrapper.getEInstance();
				indexRes.getContents().add(index);
				indexWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_NAME, getProject().getName());
				indexWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_CREATION_DATE, new Date());
				indexWrapper.adapt(new BpmnIndexModificationAdapter(index));
				BpmnModelCache.getInstance().putIndex(getProject().getName(), index);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return index;
	}

	@Override
	public void init() throws Exception {
		// TODO Auto-generated method stub

	}

	// SS: Pranab - this code seems incomplete
	@Override
	public void loadProjectLibraries(StudioProjectConfiguration projectConfig) throws Exception {
		// load referenced projects first in reverse order, so that any
		// duplicate entities are used
		// from the local project
		for (ProjectLibraryEntry ple : projectConfig.getProjectLibEntries()) {
			String libRef = ple.getPath(ple.isVar());
			//System.out.println("indexing " + libRef);
			File file = new File(libRef);
			if (file.exists()) {
				// JarFile jarFile = new JarFile(libRef);
				// DesignerProject dp =
				// IndexFactory.eINSTANCE.createDesignerProject();
				// dp.setName(jarFile.getName());
				// dp.setRootPath(libRef);
				// dp.setArchivePath(libRef);
				// BaseBinaryStorageIndexCreator creator = new
				// BaseBinaryStorageIndexCreator(getIndex().getName(), dp,
				// jarFile);
				// creator.index();
				// getIndex().getReferencedProjects().add(dp);
			}
		}

	}
	
	

	@Override
	public void preload() {
		BpmnModelCache.getInstance().remove(getProject().getName());
	}

	@Override
	public void postLoad() {
		EObjectWrapper<EClass, EObject> indexWrapper = EObjectWrapper.wrap(index);
		CommonECoreHelper.loadExtensions(indexWrapper);
		CommonECoreHelper.loadImports(indexWrapper, project.getTnsCache(), getProject().getName());
		if (resourceSet != null) {
			List<Resource> resources = new ArrayList<Resource>(resourceSet.getResources());
			EcoreUtil.resolveAll(resourceSet);
			for (Resource resource : resources) {
				if (resource != null)
					EcoreUtil.resolveAll(resource);
			}

		}
		TnsCacheLoader.setIndex(index);
		for (Entry<String, byte[]> entry : this.xmiResMap.entrySet()) {
			ByteArrayInputStream bais = new ByteArrayInputStream(entry.getValue());
			String uri = entry.getKey();
			String extension = uri.substring(uri.lastIndexOf(".") + 1);
			if (BpmnCommonIndexUtils.isTnsCacheResource(extension)) {
				if (bais != null) {
					bais.reset();
					project.getTnsCache().resourceChanged(uri, bais);
				}
			}
		}
		getProject().cacheSmElements();
		xmiResMap.clear();

		TnsCacheLoader.removeIndex();
	}

	@Override
	public void setName(String name) {
		EObject index = getIndex();
		EObjectWrapper<EClass, EObject> indexWrapper = EObjectWrapper.wrap(index);
		indexWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_NAME, name);
	}

	@Override
	public boolean supportsResource(String uri) {
		String ext = uri.substring(uri.lastIndexOf(".") + 1);
		return BpmnCommonIndexUtils.isBpmnType(ext);
	}

}
