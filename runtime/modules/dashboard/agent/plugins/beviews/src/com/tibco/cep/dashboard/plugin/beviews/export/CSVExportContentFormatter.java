package com.tibco.cep.dashboard.plugin.beviews.export;

import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import com.tibco.cep.dashboard.common.utils.StringUtil;

/**
 * @author rajesh
 *
 */
public class CSVExportContentFormatter implements ExportContentFormatter {

	private static final char COMMA_DELIMETER = ',';

	private boolean exportSystemFields;

	public String transform(ExportContentNode root, boolean exportSystemFields) throws Exception {
		this.exportSystemFields = exportSystemFields;
		StringBuilder buffer = new StringBuilder();
		buffer.append(outputLine(root));
		generateCSV(root, buffer);
		return buffer.toString();
	}

	@SuppressWarnings("unchecked")
	private String outputLine(ExportContentNode contentChildNode) {
		StringBuilder buffer = new StringBuilder(getIndent(contentChildNode));
		if (contentChildNode.getNodeType().intern() == ExportContentNode.TYPE_HEADER_ROW) {
			//add the header name
			buffer.append(contentChildNode.getUserObject());
		} else if (contentChildNode.getNodeType().intern() == ExportContentNode.TYPE_GROUP_ROW) {
			//add the group by header
			buffer.append(contentChildNode.getUserObject());
		} else {
			//add tuples
			List<TupleFieldExportData> fields = (List<TupleFieldExportData>) contentChildNode.getUserObject();
			Iterator<TupleFieldExportData> fieldsIterator = fields.iterator();
			int i = 0;
			while (fieldsIterator.hasNext()) {
				TupleFieldExportData field = (TupleFieldExportData) fieldsIterator.next();
				if (field.isSystem() == true && exportSystemFields == false) {
					continue;
				}
				if (i > 0) {
					buffer.append(COMMA_DELIMETER);
				}
				buffer.append(getCellValue(field));
				i++;
			}
		}
		buffer.append("\n");
		return buffer.toString();
	}

	private String getIndent(ExportContentNode exportContentNode) {
		StringBuilder buffer = new StringBuilder();
		int depth = exportContentNode.getLevel();
		for (int i = 0; i < depth; i++) {
			buffer.append(COMMA_DELIMETER);
		}
		return buffer.toString();
	}

	private String getCellValue(TupleFieldExportData fieldExportData) {
		String fieldValue = String.valueOf(fieldExportData.getValue());
		if (fieldValue.indexOf(',') < 0) {
			// No comma's to worry about
			return fieldValue;
		}
		int length = fieldValue.length();
		for (int i = 0; i < length; i++) {
			if (Character.isDigit(fieldValue.charAt(i)) == false) {
				// Quote the value
				return StringUtil.quote(fieldValue, '"');
			}
		}
		// Remove comma's from numerals
		return fieldValue.replaceAll(",", "");
	}

	@SuppressWarnings("rawtypes")
	private void generateCSV(ExportContentNode contentNode, StringBuilder buffer) {
		Enumeration enumChildNodes = contentNode.children();
		boolean bColumnRowDone = false;
		while (enumChildNodes.hasMoreElements()) {
			ExportContentNode contentChildNode = (ExportContentNode) enumChildNodes.nextElement();
			if (contentChildNode.getNodeType().intern() == ExportContentNode.TYPE_TUPLE_ROW) {
				// Row is instance type. See its column row which is needed once only is created or not.
				if (!bColumnRowDone) {
					buffer.append(outputColumnRowLine(contentChildNode));
					bColumnRowDone = true;
				}
			}
			buffer.append(outputLine(contentChildNode));
			if (!contentChildNode.IsDeleted()) {
				generateCSV(contentChildNode, buffer);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private String outputColumnRowLine(ExportContentNode contentChildNode) {
		StringBuilder buffer = new StringBuilder(getIndent(contentChildNode));
		List<TupleFieldExportData> fields = (List<TupleFieldExportData>) contentChildNode.getUserObject();
		Iterator<TupleFieldExportData> fieldsIterator = fields.iterator();
		int i = 0;
		while (fieldsIterator.hasNext()) {
			TupleFieldExportData field = fieldsIterator.next();
			if (field.isSystem() == true && exportSystemFields == false) {
				continue;
			}
			if ( i > 0 ){
				buffer.append(COMMA_DELIMETER);
			}
			// ColumnName
			buffer.append(field.getName());
			i++;
		}
		buffer.append("\n");
		return buffer.toString();
	}

	public void transform(ExportContentNode root, boolean isSystemFieldSupported, PrintWriter printWriter) throws Exception {
		printWriter.print(transform(root, isSystemFieldSupported));
	}
}
