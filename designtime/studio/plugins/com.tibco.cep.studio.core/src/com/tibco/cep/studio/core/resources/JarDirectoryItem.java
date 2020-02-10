package com.tibco.cep.studio.core.resources;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

public class JarDirectoryItem extends JarItem {
	SortedMap<String,JarItem> children = new TreeMap<String,JarItem>();

	protected JarDirectoryItem(String name, JarDirectoryItem parent) {
		super(name, parent);
	}

	void addChild(JarItem item) {
		children.put(item.getName(), item);
	}

	public Collection<JarItem> getChildren() {
		return children.values();
	}

	public JarItem getItem(String name) {
		return (JarItem) children.get(name);
	}
	public String toString() {
		return super.toString() + children.toString();
	}
}