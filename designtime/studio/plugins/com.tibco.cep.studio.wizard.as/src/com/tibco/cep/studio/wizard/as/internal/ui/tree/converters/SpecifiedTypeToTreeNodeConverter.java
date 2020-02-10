package com.tibco.cep.studio.wizard.as.internal.ui.tree.converters;

import org.eclipse.core.databinding.conversion.Converter;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.TreeViewer;

public class SpecifiedTypeToTreeNodeConverter extends Converter {

	private TreeViewer treeViewer;

	public SpecifiedTypeToTreeNodeConverter(Class<?> fromType, TreeViewer treeViewer) {
		super(fromType, TreeNode.class);
		this.treeViewer = treeViewer;
	}

	@Override
	public Object convert(Object fromObject) {
		Object toObject = null;

		Class<?> fromType = (Class<?>) getFromType();
		if (fromType.isInstance(fromObject)) {
			TreeNode[] nodes = (TreeNode[]) treeViewer.getInput();
			for (TreeNode node : nodes) {
				if (fromObject == node.getValue()) {
					toObject = node;
				}
			}
		}
		return toObject;
	}

}
