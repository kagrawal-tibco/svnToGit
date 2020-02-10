package com.tibco.cep.studio.core.projlib;

import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.provider.FileSystem;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

public class ProjectLibraryFileSystem extends FileSystem {
	
	public static final String PROJLIB_SCHEME="projlib"; //$NON-NLS-1$

	public ProjectLibraryFileSystem() {
	}

	@Override
	public IFileStore getStore(URI uri) {
		if(PROJLIB_SCHEME.equals(uri.getScheme())){
			String fragment = uri.getFragment();
			IPath relativePath = fragment == null ? new Path(""): new Path(fragment);
			try {
				String path=uri.getPath();
				if(path.startsWith("/")){
					path = path.substring(1);
				}
				
				return new ProjectLibraryFileStore(EFS.getStore(new URI(path)), relativePath);
			} catch (URISyntaxException e) {
				//ignore and fall through below
			} catch (CoreException e) {
				//ignore and fall through below
			}
		}
		return EFS.getNullFileSystem().getStore(uri);
	}

}
