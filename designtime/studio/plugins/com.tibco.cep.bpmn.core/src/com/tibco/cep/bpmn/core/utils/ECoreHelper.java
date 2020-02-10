package com.tibco.cep.bpmn.core.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLResource.URIHandler;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import com.tibco.be.util.BidiMap;
import com.tibco.be.util.DualHashBidiMap;
import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.core.index.BpmnIndexUtils;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelURIHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelURIHelper.URIType;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonIndexUtils;
import com.tibco.cep.bpmn.model.designtime.utils.CommonECoreHelper;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.studio.core.repo.EMFTnsCache;
import com.tibco.cep.studio.core.util.ModelUtilsCore;

public class ECoreHelper extends CommonECoreHelper {

	public static EList<EObject> deserializeModelXMI(IProject project, URI uri,
			boolean resolve) throws Exception {
		Map<Object, Object> options = new HashMap<Object, Object>();
		options.put(XMIResource.OPTION_URI_HANDLER, getURIHandler(project));
		return deserializeModelXMI(getModelResourceSet(uri), uri, options,
				resolve);
	}

	/**
	 * @param file
	 * @param resolve
	 *            TODO
	 * @return
	 * @throws Exception
	 */
	public static EList<EObject> deserializeModelXMI(IResource file,
			boolean resolve) throws Exception {
		// URI.createFileURI(file.getLocation().toOSString())
		URI uri = URI.createPlatformResourceURI(file.getFullPath()
				.toPortableString(), false);
		Map<Object, Object> options = new HashMap<Object, Object>();
		options.put(XMIResource.OPTION_URI_HANDLER,
				getURIHandler(file.getProject()));
		options.put(XMIResource.OPTION_ENCODING,
				"UTF-8");
		return deserializeModelXMI(getModelResourceSet(uri), uri, options,
				resolve);
	}

	/**
	 * @param file
	 * @param resolve
	 * @param handler
	 * @return
	 * @throws Exception
	 */
	public static EList<EObject> deserializeModelXMI(IResource file,
			boolean resolve, URIHandler handler) throws Exception {
		// URI.createFileURI(file.getLocation().toOSString())
		URI uri = URI.createPlatformResourceURI(file.getFullPath()
				.toPortableString(), false);
		Map<Object, Object> options = new HashMap<Object, Object>();
		if (handler != null) {
			options.put(XMIResource.OPTION_URI_HANDLER, handler);
		} else {
			options.put(XMIResource.OPTION_URI_HANDLER,
					getURIHandler(file.getProject()));
		}
		return deserializeModelXMI(getModelResourceSet(uri), uri, options,
				resolve);
	}

	public static EList<EObject> deserializeModelXMI(ResourceSet rset,
			IResource file, boolean resolve, URIHandler handler)
			throws Exception {
		URI uri = URI.createPlatformResourceURI(file.getFullPath()
				.toPortableString(), false);
		Map<Object, Object> options = new HashMap<Object, Object>();
		if (handler != null) {
			options.put(XMIResource.OPTION_URI_HANDLER, handler);
		} else {
			options.put(XMIResource.OPTION_URI_HANDLER,
					getURIHandler(file.getProject()));
		}
		return deserializeModelXMI(getModelResourceSet(uri), uri, options,
				resolve);
	}

	/**
	 * @param file
	 * @param eObj
	 * @param handler
	 * @throws Exception
	 */
	public static void serializeModelXMI(IResource file, EObject eObj,
			URIHandler handler) throws Exception {
		URI uri = URI.createPlatformResourceURI(file.getFullPath()
				.toPortableString(), false);
		Map<Object, Object> options = new HashMap<Object, Object>();
		if (handler != null) {
			options.put(XMIResource.OPTION_URI_HANDLER, handler);
		} else {
			options.put(XMIResource.OPTION_URI_HANDLER,
					getURIHandler(file.getProject()));
		}
		// options.put(XMIResource.OPTION_SKIP_ESCAPE, true);
		// options.put(XMIResource.OPTION_SKIP_ESCAPE_URI, true);
		ECoreHelper.serializeModelXMI(getModelResourceSet(uri), uri, eObj,
				options);
	}

	/**
	 * @param file
	 * @param eObj
	 * @throws Exception
	 */
	public static void serializeModelXMI(IResource file, EObject eObj)
			throws Exception {
		URI uri = URI.createPlatformResourceURI(file.getFullPath()
				.toPortableString(), false);
		Map<Object, Object> options = new HashMap<Object, Object>();
		options.put(XMIResource.OPTION_URI_HANDLER,
				getURIHandler(file.getProject()));
		// options.put(XMIResource.OPTION_SKIP_ESCAPE, true);
		// options.put(XMIResource.OPTION_SKIP_ESCAPE_URI, true);
		ECoreHelper.serializeModelXMI(getModelResourceSet(uri), uri, eObj,
				options);
	}

	/**
	 * @param uri
	 * @param eObject
	 * @throws IOException
	 * @throws CoreException
	 */
	public static void serializeModelXMI(IProject project, URI uri,
			EObject eObject) throws IOException, CoreException {
		ResourceSet resourceSet = getModelResourceSet(uri);
		synchronized (resourceSet) {
			EList<Resource> resources = resourceSet.getResources();
			for (Iterator<?> iterator = resources.iterator(); iterator.hasNext();) {
				Resource r = (Resource) iterator.next();
				if (r != null && r.getURI() != null && r.getURI().equals(uri)) {
					iterator.remove();
					break;
				}
			}

			Resource resource = resourceSet.createResource(uri);
			resource.getContents().add(eObject);

			Map<Object, Object> options = ModelUtilsCore.getPersistenceOptions();
			options.put(XMIResource.OPTION_URI_HANDLER, getURIHandler(project));
			CommonECoreHelper.serializeXMI(resource, options);
		}
		
	}

	/**
	 * @param resource
	 * @return
	 */
	public static EObjectWrapper<EClass, EObject> getProcessModelFromResource(
			Resource resource) {
		for (Iterator<EObject> res = resource.getContents().iterator(); res
				.hasNext();) {
			EObject content = res.next();
			if (content.eClass().getName().equals("Definitions")) {
				return EObjectWrapper.wrap(content);
			}
		}
		return null;
	}

	/**
	 * @return
	 * @throws CoreException
	 */
	public static ResourceSet getModelResourceSet(URI uri) throws CoreException {

		BpmnCorePlugin.debug("URI->" + uri);
		IProject project = null;
		if (uri.isPlatformResource()) {
			IFile file = ResourcesPlugin.getWorkspace().getRoot()
					.getFile(new Path(uri.toPlatformString(true)));
			project = file.getProject();
		} else if (uri.isFile()) {
			Path filePath = new Path(uri.lastSegment());
			String projectName = filePath.removeFileExtension().toString();
			project = ResourcesPlugin.getWorkspace().getRoot()
					.getProject(projectName);
		}

		return BpmnCorePlugin.getDefault().getBpmnModelManager()
				.getModelResourceSet(project);
	}
	
	

	/**
	 * @param project
	 * @return
	 * @throws CoreException
	 */
	public static ResourceSet createModelResourceSet(IProject project)
			throws CoreException {
		final ResourceSet rset = new ResourceSetImpl();
		Map<URIType, URI> uriMap = BpmnMetaModelURIHelper.getUriForEclipse();
		rset.createResource(uriMap.get(URIType.INDEX));
		rset.createResource(uriMap.get(URIType.MODEL));
		rset.createResource(uriMap.get(URIType.EXTN));
		for (EPackage p : BpmnMetaModel.getAllPackages()) {
			if (p.getNsURI() != null) {
				rset.getPackageRegistry().put(p.getNsURI(), p);
			}
		}
		rset.getResourceFactoryRegistry()
				.getExtensionToFactoryMap()
				.put(BpmnCommonIndexUtils.BPMN_PROCESS_EXTENSION,
						new XMIResourceFactoryImpl());
		rset.getLoadOptions().put(XMIResource.OPTION_URI_HANDLER,
				ECoreHelper.getURIHandler(project));
		refreshResourceSet(project, rset);
		return rset;
	}

	/**
	 * @param project
	 * @param rset
	 * @throws CoreException
	 */
	public static void refreshResourceSet(IProject project,
			final ResourceSet rset) throws CoreException {
		if (!project.exists())
			return;
		try {
			project.accept(new IResourceVisitor() {

				@Override
				public boolean visit(IResource resource) throws CoreException {
					if (resource instanceof IProject || resource instanceof IFolder) {
						return true;
					} else if (resource instanceof IFile
							&& resource.getFileExtension() != null
							&& resource.getFileExtension().equals(
									BpmnCommonIndexUtils.BPMN_PROCESS_EXTENSION)) {
						boolean found = false;
						URI uri = URI.createPlatformResourceURI(resource
								.getFullPath().toPortableString(), false);
						synchronized (rset) {
							EList<Resource> resources = rset.getResources();
							for (Resource i : resources) {
								if (i != null && i.getURI() != null && i.getURI().equals(uri)) {
									found = true;
									break;
								}
							}
							if (!found) {
								rset.createResource(uri);
							}
						}
					}
					return false;
				}
			});
		} catch (ConcurrentModificationException e) {
		//  many times this error is thorwn, so lets return silently, project can be refreshed later
		}
		
	}

	/**
	 * Convert a platform resource path to EMF URI
	 * 
	 * @param resource
	 * @return
	 */
	public static URI getPlatformResourceURI(IResource resource) {
		URI uri = URI.createPlatformResourceURI(resource.getFullPath()
				.toPortableString(), false);
		return uri;
	}
	public static URIHandler getURIHandler(IProject project) {
		return new BpmnIndexURIHandler(project,
				BpmnIndexUtils.getIndexLocationMap());
	}
	
	public static List<EObjectWrapper<EClass, EObject>> getItemDefinitionUsingLocation(
			String projName, String entityPath) {
		EMFTnsCache cache = BpmnCorePlugin.getCache(projName);
		EObject index = BpmnCommonIndexUtils.getIndex(projName);
		if (index != null) {
			EObjectWrapper<EClass, EObject> indexWrapper = EObjectWrapper
					.wrap(index);
			return CommonECoreHelper.getItemDefinitionUsingLocation(cache,
					indexWrapper, projName, entityPath);
		}

		return new ArrayList<EObjectWrapper<EClass, EObject>>();
	}

	public static EObjectWrapper<EClass, EObject> getItemDefinitionUsingNameSpace(
			String projName, String nameSpace) {
		EMFTnsCache cache = BpmnCorePlugin.getCache(projName);
		EObject index = BpmnCommonIndexUtils.getIndex(projName);
		if (index != null) {
			EObjectWrapper<EClass, EObject> indexWrapper = EObjectWrapper
					.wrap(index);
			return CommonECoreHelper.getItemDefinitionUsingNameSpace(cache,
					indexWrapper, projName, nameSpace);
		}
		return null;
	}
	


	/**
	 * @param uriMap
	 * @return
	 */
	public static URIHandler getURIHandler(IProject project,
			Map<String, URI> uriMap) {
		return new BpmnIndexURIHandler(project, uriMap);
	}

	/**
	 * @author pdhar
	 * 
	 */
	public static class BpmnIndexURIHandler implements URIHandler {

		URI baseURI;
		IProject project;

		protected BidiMap<String, URI> locMap;
		Pattern pattern = Pattern
				.compile("(.*@extensions\\[name=')(-?\\d+)('\\].*)");

		public BpmnIndexURIHandler(IProject project, Map<String, URI> bidiMap) {
			this.project = project;
			this.locMap = new DualHashBidiMap<String, URI>(bidiMap);
		}

		@Override
		public URI deresolve(URI uri) {
			// from C:/project index path.bdx#fragment to
			// bdx:<projectname>#fragment
			if (uri.hasAbsolutePath()
					&& uri.fileExtension().equals(
							BpmnCommonIndexUtils.BPMN_INDEX_EXTENSION)) {
				// String fileName = uri.devicePath().substring(
				// uri.devicePath().lastIndexOf("/") + 1);
				URI createFileURI = URI.createFileURI(uri.devicePath());// converting
				// to
				// URI
				String projectName = locMap.inverseMap().get(createFileURI);
				// if the file uri does not exist in the map then use the
				// default
				// project name of the handler
				if (projectName == null
						|| !project.getName().equals(projectName)) {
					projectName = project.getName();
					BpmnCorePlugin.debug("Could not resolve project: "
							+ projectName + " using default project:"
							+ project.getName());
				}
				URI newURI = URI.createGenericURI(
						BpmnCommonIndexUtils.BPMN_INDEX_SCHEME, projectName,
						URI.decode(uri.fragment()));
				BpmnCorePlugin.debug("DeResolved URI: " + uri.toString()
						+ "\n\t->" + newURI);
				return newURI;

			} else
				return uri;
		}

		@Override
		public URI resolve(URI uri) {
			// From bdx:<projectname>#fragment to C:/project index
			// path.bdx#fragment
			if (uri.scheme() != null
					&& uri.scheme().equals(
							BpmnCommonIndexUtils.BPMN_INDEX_SCHEME)) {
				URI fileURI = locMap.get(uri.opaquePart());
				// to handle conversion from int hashcode index key to hex
				// string
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
				if (fileURI == null
						|| !project.getName().equals(uri.opaquePart())) {
					fileURI = locMap.get(project.getName());
					BpmnCorePlugin.debug("Could not resolve project: "
							+ uri.opaquePart() + " using default project:"
							+ project.getName());
				}
				if (fileURI != null) {
					URI newURI = fileURI.appendFragment(URI.decode(fragment));
					BpmnCorePlugin.debug("Resolved URI: " + uri.toString()
							+ "\n\t->" + newURI);
					return newURI;
				} else {
					BpmnCorePlugin.debug("File URI not found: " + uri.toString());
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
