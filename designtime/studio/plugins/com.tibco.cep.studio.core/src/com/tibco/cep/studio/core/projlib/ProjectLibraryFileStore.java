package com.tibco.cep.studio.core.projlib;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.provider.FileInfo;
import org.eclipse.core.filesystem.provider.FileStore;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.tibco.cep.studio.core.StudioCorePlugin;

public class ProjectLibraryFileStore extends FileStore {

	/**
	 * The path of the file store within the project library
	 */
	private IPath path;

	/**
	 * The {@link IFileStore} for the project library file
	 */
	private IFileStore rootStore;

	public ProjectLibraryFileStore(IFileStore store, IPath path) {
		this.rootStore = store;
		this.path = path.makeRelative();
	}

	private ZipEntry[] childEntries(IProgressMonitor pm) throws CoreException {
		Map<String, ZipEntry> entries = new HashMap<String, ZipEntry>();
		ZipInputStream zis = new ZipInputStream(rootStore.openInputStream(EFS.NONE, pm));
		String name = path.toString();

		ZipEntry entry;
		try {
			while ((entry = zis.getNextEntry()) != null) {
				final String entryPath = entry.getName();
				if (isParent(name, entryPath)) {
					entries.put(entryPath, entry);
				} else if (isAncestor(name, entryPath)) {
					int nameLen = name.length() + 1;
					int nameEnd = entryPath.indexOf('/', nameLen);
					String dirName = nameEnd == -1 ? entryPath : entryPath.substring(0, nameEnd + 1);
					if (!entries.containsKey(dirName))
						entries.put(dirName, new ZipEntry(dirName));
				}
			}
		} catch (IOException e) {
			throw new CoreException(new Status(IStatus.ERROR, StudioCorePlugin.PLUGIN_ID, EFS.ERROR_READ, com.tibco.cep.studio.core.util.Messages.getString(
					"Project.Library.FileStore.couldNotRead", rootStore.toString()), e));
		} finally {
			try {
				if (zis != null)
					zis.close();
			} catch (IOException e) {
				//ignore
			}
		}
		return (ZipEntry[]) entries.values().toArray(new ZipEntry[entries.size()]);
	}
	
	
	
	/**
	 * Computes the simple file name for a given zip entry.
	 */
	private String computeName(ZipEntry entry) {
		//the entry name is a relative path, with an optional trailing separator
		//We need to strip off the trailing slash, and then take everything after the 
		//last separator as the name
		String name = entry.getName();
		int end = name.length() - 1;
		if (name.charAt(end) == '/')
			end--;
		return name.substring(name.lastIndexOf('/', end) + 1, end + 1);
	}
	
	/**
	 * Creates a file info object corresponding to a given zip entry
	 * 
	 * @param entry the zip entry
	 * @return The file info for a zip entry
	 */
	private IFileInfo convertZipEntryToFileInfo(ZipEntry entry) {
		FileInfo info = new FileInfo(computeName(entry));
		info.setLastModified(entry.getTime());
		info.setExists(true);
		info.setDirectory(entry.isDirectory());
		info.setLength(entry.getSize());
		return info;
	}

	/**
	 * Returns whether ancestor is a parent of child.
	 * 
	 * @param ancestor
	 *            the potential ancestor
	 * @param child
	 *            the potential child
	 * @return <code>true</code> or <code>false</code>
	 */
	private boolean isAncestor(String ancestor, String child) {
		// children will start with myName and have no child path
		int ancestorLength = ancestor.length();
		if (ancestorLength == 0)
			return true;
		return child.startsWith(ancestor) && child.length() > ancestorLength && child.charAt(ancestorLength) == '/';
	}

	/**
	 * Returns whether the parent is the immediate parent of the child
	 * 
	 * @param parent
	 *            the potential parent
	 * @param child
	 *            the potential child
	 * @return <code>true</code> or <code>false</code>.
	 */
	private boolean isParent(String parent, String child) {
		// children will start with myName and have no child path
		int chop = parent.length() + 1;
		return child.startsWith(parent) && child.length() > chop && child.substring(chop).indexOf('/') == -1;
	}

	@Override
	public Object getAdapter(Class adapter) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.filesystem.IFileStore#childInfos(int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public IFileInfo[] childInfos(int options, IProgressMonitor monitor) throws CoreException {
		ZipEntry[] entries = childEntries(monitor);
		int entryCount = entries.length;
		IFileInfo[] infos = new IFileInfo[entryCount];
		for (int i = 0; i < entryCount; i++)
			infos[i] = convertZipEntryToFileInfo(entries[i]);
		return infos;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.filesystem.IFileStore#childNames(int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public String[] childNames(int options, IProgressMonitor monitor) throws CoreException {
		ZipEntry[] entries = childEntries(monitor);
		int entryCount = entries.length;
		String[] names = new String[entryCount];
		for (int i = 0; i < entryCount; i++)
			names[i] = computeName(entries[i]);
		return names;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.filesystem.IFileStore#fetchInfo(int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public IFileInfo fetchInfo(int options, IProgressMonitor monitor) throws CoreException {
		ZipInputStream zis = new ZipInputStream(rootStore.openInputStream(EFS.NONE, monitor));
		try {
			String myPath = path.toString();
			if (myPath == null || myPath.length() == 0) {
				return createDirectoryInfo(rootStore.fetchInfo().getName());
			}
			ZipEntry current;
			while ((current = zis.getNextEntry()) != null) {
				String currentPath = current.getName();
				if (myPath.equals(currentPath))
					return convertZipEntryToFileInfo(current);
				//directories don't always have their own entry, but it is implied by the existence of a child
				if (isAncestor(myPath, currentPath))
					return createDirectoryInfo(getName());
			}
		} catch (IOException e) {
			throw new CoreException(new Status(IStatus.ERROR, StudioCorePlugin.PLUGIN_ID, EFS.ERROR_READ, com.tibco.cep.studio.core.util.Messages.getString(
					"Project.Library.FileStore.couldNotRead", rootStore.toString()), e));
		} finally {
			try {
				if (zis != null)
					zis.close();
			} catch (IOException e) {
				//ignore
			}
		}
		//does not exist
		return new FileInfo(getName());
	}

	/**
	 * @return A directory info for this file store
	 */
	private IFileInfo createDirectoryInfo(String name) {
		FileInfo result = new FileInfo(name);
		result.setExists(true);
		result.setDirectory(true);
		return result;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.core.filesystem.IFileStore#getChild(java.lang.String)
	 */
	public IFileStore getChild(String name) {
		return new ProjectLibraryFileStore(rootStore, path.append(name));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.filesystem.IFileStore#getName()
	 */
	public String getName() {
		String name = path.lastSegment();
		return name == null ? "" : name; //$NON-NLS-1$
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.filesystem.IFileStore#getParent()
	 */
	public IFileStore getParent() {
		if (path.segmentCount() > 0)
			return new ProjectLibraryFileStore(rootStore, path.removeLastSegments(1));
		//the root entry has no parent
		return null;
	}
	
	/**
	 * Finds the zip entry with the given name in this zip file.  Returns the
	 * entry and leaves the input stream open positioned at the beginning of
	 * the bytes of that entry.  Returns null if the entry could not be found.
	 */
	private ZipEntry findEntry(String name, ZipInputStream in) throws IOException {
		ZipEntry current;
		while ((current = in.getNextEntry()) != null) {
			if (current.getName().equals(name))
				return current;
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.core.filesystem.provider.FileStore#openInputStream(int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public InputStream openInputStream(int options, IProgressMonitor monitor) throws CoreException {
		ZipInputStream zis = new ZipInputStream(rootStore.openInputStream(EFS.NONE, monitor));
		try {
			
			ZipEntry entry = findEntry(path.toString(), zis);
			
			if (entry == null) {
				
				throw new CoreException(new Status(IStatus.ERROR, StudioCorePlugin.PLUGIN_ID, EFS.ERROR_READ, com.tibco.cep.studio.core.util.Messages.getString(
						"Project.Library.FileStore.fileNotFound", rootStore.toString()), null));
			}
			
			if (entry.isDirectory()) {
				
				throw new CoreException(new Status(IStatus.ERROR, StudioCorePlugin.PLUGIN_ID, EFS.ERROR_READ, com.tibco.cep.studio.core.util.Messages.getString(
						"Project.Library.FileStore.notAFile", rootStore.toString()), null));
			}
			return zis;
		} catch (IOException e) {
			try {
				if (zis != null)
					zis.close();
			} catch (IOException e1) {
				//ignore secondary failure
			}
			throw new CoreException(new Status(IStatus.ERROR, StudioCorePlugin.PLUGIN_ID, EFS.ERROR_READ, com.tibco.cep.studio.core.util.Messages.getString(
					"Project.Library.FileStore.couldNotRead", rootStore.toString()), e));
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.filesystem.provider.FileStore#toURI()
	 */
	public URI toURI() {
		try {
			String scheme = ProjectLibraryFileSystem.PROJLIB_SCHEME;//String.format("%s:%s", ProjectLibraryFileSystem.PROJLIB_SCHEME,rootStore.toURI().getScheme());
			String uriPath = "/"+rootStore.toURI().getScheme()+":"+rootStore.toURI().getSchemeSpecificPart();
			String fragment = path.toString();
			URI uri = new URI(scheme, // scheme
					null, // authority
					uriPath, // path
					null, //query
					fragment // fragment
					);
			return uri;
		} catch (URISyntaxException e) {
			//should not happen
			throw new RuntimeException(e);
		}
	}

	@Override
	public File toLocalFile(int options, IProgressMonitor monitor) throws CoreException {
		return rootStore.toLocalFile(options, monitor);
	}
	
	

}
