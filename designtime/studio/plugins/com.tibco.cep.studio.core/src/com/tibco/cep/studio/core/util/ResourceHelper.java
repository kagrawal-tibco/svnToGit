package com.tibco.cep.studio.core.util;

import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;

/**
 * @author pdhar
 *
 */
public class ResourceHelper {
	
	/**
	 * Returns the absolute URI of this resource, 
	 * or <code>null</code> if no URI can be determined.
	 * <p>
	 * If this resource is the workspace root, this method returns
	 * the absolute location of the platform working area.
	 * </p><p>
	 * If this resource is a project that exists in the workspace, this method
	 * returns the URI to the project's local content area. This is true regardless
	 * of whether the project is open or closed. This value will be null in the case
	 * where the location is relative to an undefined workspace path variable.
	 * </p><p>
	 * If this resource is a linked resource under a project that is open, this
	 * method returns the resolved URI to the linked resource's local contents.
	 * This value will be null in the case where the location is relative to an
	 * undefined workspace path variable.
	 * </p><p>
	 * If this resource is a file or folder under a project that exists, or a
	 * linked resource under a closed project, this method returns a (non-
	 * <code>null</code>) URI computed from the location of the project's local
	 * content area and the project- relative path of the file or folder. This is
	 * true regardless of whether the file or folders exists, or whether the project
	 * is open or closed. In the case of linked resources, the location of a linked resource
	 * within a closed project is computed from the location of the
	 * project's local content area and the project-relative path of the resource. If the
	 * linked resource resides in an open project then its location is computed
	 * according to the link.
	 * </p><p>
	 * If this resource is a project that does not exist in the workspace,
	 * or a file or folder below such a project, this method returns
	 * <code>null</code>.
	 * </p>
	 * 
	 * @return the absolute URI of this resource,
	 *  or <code>null</code> if no URI can be determined
	 * @see #getRawLocation()
	 * @see  IProjectDescription#setLocation(IPath)
	 * @see Platform#getLocation()
	 * @see java.net.URI
	 * @since 3.2
	 */
	public static URI getLocationURI(IResource res) {
		if (res.isLinked(IResource.CHECK_ANCESTORS)) {
			IResource parent=getLinkedResourceParent(res);
			URI locationURI = parent.getLocationURI();
			//			String ssp = res.getLocationURI().getSchemeSpecificPart();
			String ssp = res.getFullPath().makeRelativeTo(parent.getFullPath()).makeAbsolute().toPortableString();
			try {
				URI archiveEntryURI = new URI(locationURI.getScheme(), locationURI.getSchemeSpecificPart(),ssp);
				return archiveEntryURI;
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		if(res.getLocationURI() !=null && res.getLocationURI().getAuthority() == null ) {
			return res.getLocationURI();
		} else {
			if (res.getLocation() != null) {
				return res.getLocation().toFile().toURI();
			} else {
				return null;
			}
		}
	}
	
	
	/**
	 * Returns the parent of the linked resource
	 * @param linkedResource
	 * @return
	 */
	public static IResource getLinkedResourceParent(IResource linkedResource) {
		IResource parent=linkedResource.getParent();
		while (parent != null) {
			if (parent.isLinked()) {
				break;
			}
			parent = parent.getParent();
		}
		return parent;
	}


	public static String getFileLocation(IResource resource) {
		if (resource.getLocation() != null) {
			return resource.getLocation().toString();
		}
		URI locationURI = resource.getLocationURI();
		if ("projlib".equalsIgnoreCase(locationURI.getScheme())) {
			String schemeSpecificPart = locationURI.getRawSchemeSpecificPart();
			if (schemeSpecificPart != null && schemeSpecificPart.charAt(0) == '/') {
				schemeSpecificPart = schemeSpecificPart.substring(1);
			}
			locationURI = URI.create(schemeSpecificPart);
		}
		String jarPath = locationURI.getSchemeSpecificPart();
		return jarPath;
	}

}
