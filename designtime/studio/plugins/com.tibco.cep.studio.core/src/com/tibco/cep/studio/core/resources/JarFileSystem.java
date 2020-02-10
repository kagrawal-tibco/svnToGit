package com.tibco.cep.studio.core.resources;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarException;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.provider.FileSystem;
import org.eclipse.core.runtime.CoreException;

public class JarFileSystem extends FileSystem {
	private static Map jarFileCache = new HashMap();
	private static Map jarFileStoreCache = new HashMap();

	public int attributes() {
		return EFS.ATTRIBUTE_READ_ONLY; // | EFS.ATTRIBUTE_HIDDEN | EFS.ATTRIBUTE_EXECUTABLE etc.
	}
	public static JarItem getItem(URI uri) throws JarException, IOException,
			CoreException {
		if (jarFileCache.containsKey(uri)) {
			return (JarItem) jarFileCache.get(uri);
		} else {
			File file = EFS.getStore(uri).toLocalFile(0, null);
			JarItem item = new JarRootItem(file);
			jarFileCache.put(uri, item);
			return item;
		}
	}

	public IFileStore getStore(URI uri) {
		try {
			if (jarFileStoreCache.containsKey(uri)) {
				return (IFileStore) jarFileStoreCache.get(uri);
			} else {
				JarFileStore fileStore = new JarFileStore("root", null,uri);
				jarFileStoreCache.put(uri, fileStore);
				return fileStore;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
