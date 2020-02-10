package com.tibco.cep.studio.core.resources;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Iterator;
import java.util.jar.JarException;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.provider.FileInfo;
import org.eclipse.core.filesystem.provider.FileStore;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;

import com.tibco.cep.studio.core.StudioCorePlugin;

public class JarFileStore extends FileStore {
	static JarItem getRoot(URI uri) throws URISyntaxException, JarException,
			IOException, CoreException {
		URI jarFile = new URI(uri.getQuery());
		return JarFileSystem.getItem(jarFile);
	}
	private String name;
	private JarFileStore parent;
	private URI uri;

	private JarItem jarItem;

	private JarFileStore(String name, JarFileStore parent) {
		this.name = name;
		this.parent = parent;
	}

	public JarFileStore(String name, JarFileStore parent, URI uri)
			throws JarException, URISyntaxException, IOException, CoreException {
		this(name, parent, getRoot(uri).getItem(new Path(uri.getPath()), 0) );
		this.uri = uri;
	}

	public JarFileStore(String name,  JarFileStore parent, JarItem item) {
		this(name,parent);
		this.jarItem = item;
	}

	public String[] childNames(int options, IProgressMonitor monitor)
			throws CoreException {
		if (isDirectory()) {
			Collection collection = ((JarDirectoryItem) jarItem).getChildren();
			String[] children = new String[collection.size()];
			Iterator iterator = collection.iterator();
			int i = 0;
			while (iterator.hasNext()) {
				JarItem jarItem = (JarItem) iterator.next();
				children[i++] = jarItem.getName();
			}
			return children;
		} else {
			return new String[0];
		}
	}

	public IFileInfo fetchInfo(int options, IProgressMonitor monitor)
			throws CoreException {
		FileInfo fileInfo = new FileInfo(getName());
		if (isDirectory()) {
			fileInfo.setDirectory(true);
		} else {
			fileInfo.setDirectory(false);
			fileInfo.setLastModified(((JarFileItem) jarItem).getLastModified());
		}
		fileInfo.setExists(true);
		fileInfo.setAttribute(EFS.ATTRIBUTE_READ_ONLY, true);
		return fileInfo;
	}

	protected URI getBase() {
		if (parent == null) {
			return uri;
		} else {
			return ((JarFileStore) getParent()).getBase();
		}
	}

	public IFileStore getChild(String name) {
		if (isDirectory()) {
			JarItem child = ((JarDirectoryItem) jarItem).getItem(name);
			return new JarFileStore(name, this, child);
		} else {
			return null;
		}
	}

	public String getName() {
		return name;
	}

	public IFileStore getParent() {
		return parent;
	}

	protected IPath getPath() {
		if (parent == null) {
			return new Path("/");
		} else {
			return parent.getPath().append(getName());
		}
	}
	private boolean isDirectory() {
		return jarItem instanceof JarDirectoryItem;
	}
	public InputStream openInputStream(int options, IProgressMonitor monitor)
			throws CoreException {
		if (!isDirectory()) {
			try {
				return ((JarFileItem) jarItem).openInputStream();
			} catch (IOException e) {
				throw new CoreException(new Status(IStatus.ERROR,StudioCorePlugin.PLUGIN_ID,"Failed to open file",e));
			}
		} else {
			return null;
		}
	}
	public URI toURI() {
		try {
			URI base = getBase();
			return new URI(base.getScheme(),null,null,0,getPath().toString(),base.getQuery(),null);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

}

