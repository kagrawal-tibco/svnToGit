package com.tibco.cep.studio.core.resources;

import org.eclipse.core.runtime.IPath;

public abstract class JarItem {
	private String name;
	private JarDirectoryItem parent;

	protected JarItem(String name, JarDirectoryItem parent) {
		this.parent = parent;
		this.name = name;
		if (parent != null)
			parent.addChild(this);
	}

	public JarItem getItem(IPath path) {
		return getItem(path, 0);
	}

	public JarItem getItem(IPath path, int segment) {
		if (segment == path.segmentCount()) {
			return this;
		} else {
			JarItem child = ((JarDirectoryItem) this).getItem(path
					.segment(segment));
			return child.getItem(path, ++segment);
		}
	}

	public String getName() {
		return name;
	}

	public JarItem getParent() {
		return parent;
	}

	protected JarRootItem getRoot() {
		if (parent == null) {
			return (JarRootItem) this;
		} else {
			return parent.getRoot();
		}
	}

	public String toString() {
		return name;
	}
}
