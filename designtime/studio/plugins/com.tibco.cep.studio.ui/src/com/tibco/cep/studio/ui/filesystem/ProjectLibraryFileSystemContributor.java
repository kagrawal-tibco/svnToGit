package com.tibco.cep.studio.ui.filesystem;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ide.fileSystem.FileSystemContributor;

import com.tibco.cep.studio.core.projlib.ProjectLibraryFileSystem;

public class ProjectLibraryFileSystemContributor extends FileSystemContributor{

	public ProjectLibraryFileSystemContributor() {
		super();
	}

	public URI getURI(String pathString) {
		try {
			if (pathString.startsWith(ProjectLibraryFileSystem.PROJLIB_SCHEME))
				return new URI(pathString);
		} catch (URISyntaxException e1) {
			return null;
		}
		if (File.separatorChar != '/')
			pathString = pathString.replace(File.separatorChar, '/');
		final int length = pathString.length();
		StringBuffer pathBuf = new StringBuffer(length + 1);
		pathBuf.append("file:"); //$NON-NLS-1$
		// There must be a leading slash in a hierarchical URI
		if (length > 0 && (pathString.charAt(0) != '/'))
			pathBuf.append('/');
		// additional double-slash for UNC paths to distinguish from host
		// separator
		if (pathString.startsWith("//")) //$NON-NLS-1$
			pathBuf.append('/').append('/');
		pathBuf.append(pathString);
		try {
			//scheme, host, path, query, fragment
			return new URI(ProjectLibraryFileSystem.PROJLIB_SCHEME, null, "/", pathBuf.toString(), null); //$NON-NLS-1$
		} catch (URISyntaxException e) {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.ide.fileSystem.FileSystemContributor#browseFileSystem(java.lang.String, org.eclipse.swt.widgets.Shell)
	 */
	public URI browseFileSystem(String initialPath, Shell shell) {

		FileDialog dialog = new FileDialog(shell);

		if (initialPath.length() > 0)
			dialog.setFilterPath(initialPath);

		dialog.setFilterExtensions(new String[] {"*.projlib"});//$NON-NLS-1$		

		String selectedFile = dialog.open();
		if (selectedFile == null)
			return null;
		return getURI(selectedFile);
	}

	

}
