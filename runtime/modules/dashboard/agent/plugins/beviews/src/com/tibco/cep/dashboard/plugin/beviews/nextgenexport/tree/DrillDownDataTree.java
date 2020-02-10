package com.tibco.cep.dashboard.plugin.beviews.nextgenexport.tree;

import java.util.Collection;
import java.util.HashSet;

public class DrillDownDataTree {

	private HashSet<DrillDownDataNode> roots;

	private int total;

	public DrillDownDataTree() {
		roots = new HashSet<DrillDownDataNode>();
	}

	public void addRoot(DrillDownDataNode root) {
		if (roots.add(root) == false) {
			throw new IllegalArgumentException(root+" already added");
		}
	}

	public Collection<DrillDownDataNode> getRoots() {
		return roots;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void clear() {
		roots.clear();
		total = 0;
	}

}