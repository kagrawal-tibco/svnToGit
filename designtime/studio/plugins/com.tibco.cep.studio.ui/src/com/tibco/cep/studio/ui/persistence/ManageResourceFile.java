package com.tibco.cep.studio.ui.persistence;

import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

import com.tibco.cep.studio.core.utils.ModelUtils;

/**
 * 
 * @author sasahoo
 *
 */
public class ManageResourceFile implements IManageResource {

	
	public void saveResource(ResourceSet resourceSet) throws Exception {
		for (Iterator<Resource> i = resourceSet.getResources().iterator(); i.hasNext();) {
			Resource savedResource = i.next();
			savedResource.save(ModelUtils.getPersistenceOptions());
		}
	}

//	private void createResource(final URI resourceURI)	throws InvocationTargetException, InterruptedException, IOException {
//
//		ResourceSet resourceSet = new ResourceSetImpl();
//		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
//				.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
//						new XMIResourceFactoryImpl());
//
//		// Create a resource for this file.
//		Resource resource = resourceSet.createResource(resourceURI);
//
//		// Save the contents of the resource to the file system.
//		resource.save(Collections.EMPTY_MAP);
//	}

	public void loadResource(ResourceSet resourceSet, IFile file) throws Exception {
//		final String baseURI = StudioWorkbenchUtils.getCurrentWorkspacePath();
//		URI uri = URI.createFileURI(baseURI+file.getFullPath().toOSString());
		URI uri = URI.createPlatformResourceURI(file.getFullPath().toOSString(),true);
		resourceSet.getResource(uri, true);
	}


}
