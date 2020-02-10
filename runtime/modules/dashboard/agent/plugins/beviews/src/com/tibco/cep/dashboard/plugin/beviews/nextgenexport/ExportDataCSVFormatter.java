package com.tibco.cep.dashboard.plugin.beviews.nextgenexport;

import java.util.Iterator;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.plugin.beviews.nextgenexport.tree.DrillDownDataNode;
import com.tibco.cep.dashboard.plugin.beviews.nextgenexport.tree.DrillDownDataTree;
import com.tibco.cep.dashboard.psvr.common.NonFatalException;

public class ExportDataCSVFormatter implements ExportDataFormatter {

	private static final char COMMA_DELIMETER = ',';

	@Override
	public String convert(DrillDownDataTree tree, boolean includeSystemFields) throws NonFatalException {
		if (tree == null || tree.getRoots().isEmpty() == true) {
			return "";
		}
		StringBuilder buffer = new StringBuilder();
		for (DrillDownDataNode root : tree.getRoots()) {
			addLine(buffer, 0, root, includeSystemFields);
			addLines(buffer, 1, root, includeSystemFields);
		}
		return buffer.toString();
	}

	private void addLine(StringBuilder buffer, int depth, DrillDownDataNode dataNode, boolean includeSystemFields) {
		for (int i = 0; i < depth; i++) {
			buffer.append(COMMA_DELIMETER);
		}
		switch (dataNode.getKind()) {
			case TYPE:
			case GROUP_BY:
				buffer.append(dataNode.getName());
				break;
			case INSTANCE:
				Iterator<String> fieldNames = dataNode.getFieldNames(includeSystemFields);
				while (fieldNames.hasNext()) {
					String fieldName = fieldNames.next();
					String value = dataNode.getFormattedValue(fieldName);
					if (value.indexOf(COMMA_DELIMETER) != -1) {
						value = StringUtil.quote(value, '"');
					}
					buffer.append(value);
					if (fieldNames.hasNext() == true) {
						buffer.append(COMMA_DELIMETER);
					}
				}
				break;
			default:
				throw new IllegalArgumentException("Unsupported node kind [" + dataNode.getKind() + "]");
		}
		buffer.append("\n");
	}

	private void addLines(StringBuilder buffer, int depth, DrillDownDataNode dataNode, boolean includeSystemFields) {
		boolean tupleHeaderAdded = false;
		for (DrillDownDataNode childDataNode : dataNode.getChildren()) {
			if (childDataNode.getKind().compareTo(DrillDownDataNode.KIND.INSTANCE) == 0 && tupleHeaderAdded == false) {
				addHeaderLine(buffer, depth, childDataNode, includeSystemFields);
				tupleHeaderAdded = true;
			}
			addLine(buffer, depth, childDataNode, includeSystemFields);
			addLines(buffer, depth + 1, childDataNode, includeSystemFields);
		}
	}

	private void addHeaderLine(StringBuilder buffer, int depth, DrillDownDataNode dataNode, boolean includeSystemFields) {
		for (int i = 0; i < depth; i++) {
			buffer.append(COMMA_DELIMETER);
		}
		Iterator<String> fieldNames = dataNode.getFieldNames(includeSystemFields);
		while (fieldNames.hasNext()) {
			buffer.append(dataNode.getDisplayName(fieldNames.next()));
			if (fieldNames.hasNext() == true) {
				buffer.append(COMMA_DELIMETER);
			}
		}
		buffer.append("\n");
	}

}