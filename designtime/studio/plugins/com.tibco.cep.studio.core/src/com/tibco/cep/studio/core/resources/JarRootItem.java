package com.tibco.cep.studio.core.resources;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarException;
import java.util.jar.JarFile;

import org.eclipse.core.runtime.Path;

public class JarRootItem extends JarDirectoryItem {
	private JarFile file;

	public JarRootItem(File file) throws JarException, IOException {
		this(new JarFile(file));
	}

	public JarRootItem(JarFile file) {
		super(new Path(file.getName()).lastSegment(), null);
		this.file = file;
		parseEntries(file);
	}

	public String getFullName() {
		return file.getName();
	}

	protected InputStream getInputStream(JarEntry entry) throws IOException {
		return file.getInputStream(entry);
	}

	/**
	 * Given a JarFile, return a SortedMap of Maps and FileInfos
	 * 
	 * @param jarFile
	 * @return
	 */
	private void parseEntries(JarFile jarFile) {
		JarDirectoryItem root = this;
		Enumeration entries = jarFile.entries();
		while (entries.hasMoreElements()) {
			JarDirectoryItem parent = root;
			JarEntry entry = (JarEntry) entries.nextElement();
			String fullName = entry.getName();
			Path path = new Path(fullName);
			for (int i = 0; i < path.segmentCount() - 1; i++) {
				String dirName = path.segment(i);
				JarDirectoryItem newParent = (JarDirectoryItem) parent
						.getItem(dirName);
				if (newParent == null) {
					newParent = new JarDirectoryItem(dirName, parent);
				}
				parent = newParent;
			}
			String name = path.lastSegment();
			if (entry.isDirectory()) {
				new JarDirectoryItem(name, parent);
			} else {
				new JarFileItem(name, parent, entry);
			}
		}
	}
}
