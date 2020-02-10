package com.tibco.cep.studio.core.resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarEntry;

public class JarFileItem extends JarItem {
	private JarEntry entry;

	public JarFileItem(String name, JarDirectoryItem parent, JarEntry entry) {
		super(name, parent);
		this.entry = entry;
	}

	public String getFullName() {
		return entry.getName();
	}

	public long getLastModified() {
		return entry.getTime();
	}

	public InputStream openInputStream() throws IOException {
		return getRoot().getInputStream(entry);
	}
}

