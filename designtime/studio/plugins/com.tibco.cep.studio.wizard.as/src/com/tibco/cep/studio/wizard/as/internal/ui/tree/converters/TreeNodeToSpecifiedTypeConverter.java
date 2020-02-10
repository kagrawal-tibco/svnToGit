package com.tibco.cep.studio.wizard.as.internal.ui.tree.converters;

import org.eclipse.core.databinding.conversion.Converter;
import org.eclipse.jface.viewers.TreeNode;

public class TreeNodeToSpecifiedTypeConverter extends Converter {

	public TreeNodeToSpecifiedTypeConverter(Class<?> toType) {
		super(TreeNode.class, toType);
	}

	@Override
	public Object convert(Object fromObject) {
		Object toObject = null;
		if (fromObject instanceof TreeNode) {
			TreeNode node = (TreeNode) fromObject;
			toObject = findSpecifiedTypeParent(node);
		}
		return toObject;
	}

	private Object findSpecifiedTypeParent(TreeNode node) {
		Object found = null;

		if (null != node) {
			Object value = node.getValue();
			Class<?> toType = (Class<?>) getToType();
			if (toType.isInstance(value)) {
				found = value;
			}
			else {
				found = findSpecifiedTypeParent(node.getParent());
			}
		}
		return found;
	}

}